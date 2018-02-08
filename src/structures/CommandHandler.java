package structures;

import net.dv8tion.jda.core.entities.Message;

import java.util.Arrays;

public class CommandHandler {
	public static void handle(Message message, String content) {
		String[] args = content.substring(1, content.length()).split(" ");
		String name = args[0];
		name = ("" + name.charAt(0)).toUpperCase() + name.substring(1, name.length());

		Command command = LewdBot.commands.get("commands." + name);
		if (command.getTemplate().length == 0) {
			try {
				runCommand(message, command, args);
			} catch (Exception e) {
				message.getChannel().sendMessage("this should not have happened").queue();
				System.err.println(e.getMessage() + "\n" + e.getStackTrace());
			}
		}
		else {
			Object[] parsedArgs;
			try {
				args = Arrays.toString(args).split(", ", 2)[1].split("]")[0].split(", ");
				// Make class? and cast to that
				parsedArgs = (Object[])CommandParser.parse(message, command.getTemplate(), args);
				runCommand(message, command, parsedArgs);
			} catch (Exception e) {
				message.getChannel().sendMessage("Missing input for `" + command.getTemplate()[command.getTemplate().length - 1] + "`.").queue();
			}
		}
	}

	private static void runCommand(Message message, Command command, Object[] args) throws Exception {
		if (command != null) {
			command.run(message, args);
			System.out.println("COMMAND " + command.getName() + " in " + message.getChannel().getName());
		}
		else {
			// String casting is hack until I'm not lazy, just don't run commands stupidly for now /s
			command = LewdBot.commands.get(LewdBot.aliases.get(((String)args[0]).toLowerCase()));
			if (command != null) {
				command.run(message, args);
				System.out.println("COMMAND " + command.getName() + " in " + message.getChannel().getName());
			}
		}
	}
}
