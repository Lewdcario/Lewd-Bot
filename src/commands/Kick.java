package commands;

import java.util.List;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import structures.Command;

public class Kick extends Command {
	public final String name = "kick";
	public final String[] aliases = { "k" };
	
	public String getName() {
		return this.name;
	}
	
	public String[] getAliases() {
		return this.aliases;
	}
	
	// TODO: Fix permission checks that aren't working??
	public void run(Message message) {
		if (!message.getMember().hasPermission(Permission.KICK_MEMBERS)) {
			message.getChannel().sendMessage("You do not have the `KICK_MEMBERS` permission.");
		}
		if (message.getMentionedUsers().isEmpty()) {
            message.getChannel().sendMessage("You must mention 1 or more Users to be kicked!").queue();
        }
        else {
            Guild guild = message.getGuild();
            Member selfMember = guild.getSelfMember();                                          

            if (!selfMember.hasPermission(Permission.KICK_MEMBERS)) {
            	message.getChannel().sendMessage("Sorry! I don't have permission to kick members in this Guild!").queue();
                return;
            }

            List<User> mentionedUsers = message.getMentionedUsers();
            for (User user : mentionedUsers) {
                Member member = guild.getMember(user);
             
                if (!selfMember.canInteract(member)) {
                	message.getChannel().sendMessage("Cannot kick member: " + member.getEffectiveName() +", they are higher " + "in the hierarchy than I am!").queue();
                    continue;   //Continue to the next mentioned user to be kicked.
                }

                guild.getController().kick(member).queue(
                	success -> message.getChannel().sendMessage("Kicked " + member.getEffectiveName() + "! Cya!").queue(),
                    error -> {	
                        if (error instanceof PermissionException) {
                            // PermissionException pe = (PermissionException) error;
                            // Permission missingPermission = pe.getPermission();
                            message.getChannel().sendMessage("PermissionError kicking [" + member.getEffectiveName()
                                    + "]: " + error.getMessage()).queue();
                        }
                        else {
                        	message.getChannel().sendMessage("Unknown error while kicking [" + member.getEffectiveName()
                                    + "]: " + "<" + error.getClass().getSimpleName() + ">: " + error.getMessage()).queue();
                        }
                    });
            }
        }
	}
}
