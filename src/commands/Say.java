package commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.impl.TextChannelImpl;
import structures.Command;

import java.util.Arrays;

public class Say extends Command {
	private String name = "ping";
	private String[] aliases = { "p" };
	public String[] template = {"channel", "string"};

	public String getName() {
		return this.name;
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public String[] getTemplate(){
		return this.template;
	}

	public void run(Message message, Object[] args) {
		TextChannelImpl ch = (TextChannelImpl) args[0];
		String[] rem = Arrays.toString(args).split(", ",2)[1].split("]")[0].split(", ");
		ch.sendMessage(String.join(" ", rem)).queue();
	}
}
