package commands;

import net.dv8tion.jda.core.entities.Message;
import structures.Command;

public class Ping extends Command {
	private String name = "ping";
	private String[] aliases = { "p" };
	
	public String getName() {
		return this.name;
	}
	
	public String[] getAliases() {
		return this.aliases;
	}
	
	public void run(Message message, Object[] args) {
		message.getChannel().sendMessage("pong!").queue();
	}
}
