package commands;

import java.util.Random;

import net.dv8tion.jda.core.entities.Message;
import structures.Command;

public class Roll implements Command {
	public final String name = "roll";
	public final String[] aliases = { "r" };
	
	public String getName() {
		return this.name;
	}
	
	public String[] getAliases() {
		return this.aliases;
	}
	
	public void run(Message message) {
		Random rand = new Random();
        int roll = rand.nextInt(6) + 1;
        message.getChannel().sendMessage("Your roll: " + roll).queue(sentMessage -> {                                                               
            if (roll < 3) {
            	 message.getChannel().sendMessage("The roll for messageId: " + sentMessage.getId() + " wasn't very good... Must be bad luck!\n").queue();
            }
        });
	}
}
