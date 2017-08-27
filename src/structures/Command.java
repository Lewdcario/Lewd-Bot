package structures;

import net.dv8tion.jda.core.entities.Message;

// TODO: Make this a class to extend
public interface Command {
	public String[] aliases = null;
	public Argument[] Args = null;
	public String name = null;
	
	public String getName();
	public String[] getAliases();
	public void run(Message message);
}

interface Argument {
	// default ParsedArgument;
	String name = null;
	String parse(String string);
	String type = null;
	String validate(String string);
}
