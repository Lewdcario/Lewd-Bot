package structures;

import net.dv8tion.jda.core.entities.Message;

public class Command {
	public String[] aliases = {""};
	public String name = "";
	public String[] template = {""};

	public String getName() {
		return this.name;
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public String[] getTemplate() {
		return this.template;
	}

	public void run(Message message, Object[] args) throws Exception {
		throw new Error("This class does not implement a run() method.");
	}

}

interface Argument {
	// default ParsedArgument;
	String name = null;
	String parse(String string);
	String type = null;
	String validate(String string);
}
