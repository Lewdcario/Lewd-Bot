package commands;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import structures.Command;

import java.util.List;

public class Ban implements Command {
    public final String name = "Ban";
    public final String[] aliases = { "b" };

    @Override
    public void run(Message message) {
        if (!message.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            message.getChannel().sendMessage("You do not have the `BAN_MEMBERS` permission.");
        }
        if (message.getMentionedUsers().isEmpty()) {
            message.getChannel().sendMessage("You must mention 1 or more users to be banned!").queue();
        }
        else {
            Guild guild = message.getGuild();
            Member selfMember = guild.getSelfMember();

            if (!selfMember.hasPermission(Permission.BAN_MEMBERS)) {
                message.getChannel().sendMessage("Sorry! I don't have permission to ban members in this server!").queue();
                return;
            }

            List<User> mentionedUsers = message.getMentionedUsers();
            for (User user : mentionedUsers) {
                Member member = guild.getMember(user);

                if (!selfMember.canInteract(member)) {
                    message.getChannel().sendMessage("Cannot ban member: " + member.getEffectiveName() +", they are higher " + "in the hierarchy than I am!").queue();
                    continue;   //Continue to the next mentioned user to be banned.
                }

                guild.getController().kick(member).queue(
                        success -> message.getChannel().sendMessage("Banned " + member.getEffectiveName() + "! Cya!").queue(),
                        error -> {
                            if (error instanceof PermissionException) {
                                // PermissionException pe = (PermissionException) error;
                                // Permission missingPermission = pe.getPermission();
                                message.getChannel().sendMessage("PermissionError banning [" + member.getEffectiveName()
                                        + "]: " + error.getMessage()).queue();
                            }
                            else {
                                message.getChannel().sendMessage("Unknown error while banning [" + member.getEffectiveName()
                                        + "]: " + "<" + error.getClass().getSimpleName() + ">: " + error.getMessage()).queue();
                            }
                        });
            }
        }
    }
}
