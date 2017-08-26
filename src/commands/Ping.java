package commands;

import net.dv8tion.jda.core.entities.Message;
import structures.Command;

public class Ping implements Command {
	public final String name = "ping";
	public final String[] aliases = { "p" };
	
	public void run(Message message) {
		message.getChannel().sendMessage("pong!").queue();
	}
}
