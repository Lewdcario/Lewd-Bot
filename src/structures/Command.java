package structures;

import net.dv8tion.jda.core.entities.Message;

public class Command {
	public String[] aliases = null;
	public Argument[] Args = null;
	public String name = null;
	public String[] template = null;

	public String getName() {
		System.err.println("This class does not implement a getName() method.");
		return this.name;
	}

	public String[] getAliases() {
		System.err.println("This class does not implement a getAliases() method.");
		return this.aliases;
	}

	public String[] getTemplate() {
		System.err.println("This class does not implement a getTemplate() method.");
		return this.template;
	}

	public void run(Message message) {
		System.err.println("This class does not implement a run() method.");
	}

	public void run(Message message, Object[] args) {
		System.err.println("This class does not implement a run() method.");
	}

}

interface Argument {
	// default ParsedArgument;
	String name = null;
	String parse(String string);
	String type = null;
	String validate(String string);
}
