package commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.impl.TextChannelImpl;
import structures.Command;

import java.util.Arrays;

public class Say extends Command {
	private String name = "say";
	private String[] template = {"channel"};

	public String getName() { return this.name; }
	public String[] getTemplate() { return this.template; }

	public void run(Message message, Object[] args) throws Exception {
		TextChannelImpl ch = (TextChannelImpl) args[0];
		String[] rem = Arrays.toString(args).split(", ",2)[1].split("]")[0].split(", ");
		ch.sendMessage(String.join(" ", rem)).queue();
	}
}
