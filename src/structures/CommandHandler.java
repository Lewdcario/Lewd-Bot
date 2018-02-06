package structures;

import net.dv8tion.jda.core.entities.Message;

import java.util.Arrays;

public class CommandHandler {
	public static void handle(Message message, String content) {
		String[] args = content.substring(1, content.length()).split(" ");
		String name = args[0];
		name = ("" + name.charAt(0)).toUpperCase() + name.substring(1, name.length());

		Command command = LewdBot.commands.get("commands." + name);
		try {
			command.getTemplate();
			runCommand(message, command, args);
		} catch(NullPointerException e) {
			args = Arrays.toString(args).split(", ",2)[1].split("]")[0].split(", ");
			try {
				Object[] parsed = CommandParser.parse(message, command.getTemplate(), args);
				runCommand(message, command, parsed);
			} catch(ClassCastException | NullPointerException err) {
				message.getChannel().sendMessage("Invalid args~").queue();
			}

		}
	}

	private static void runCommand(Message message, Command command, Object[] args) {
		if (command != null) {
			command.run(message, args);
			System.out.println("COMMAND " + command.getName() + " in " + message.getChannel().getName());
			return;
		}
		else {
			// String casting is hack until I'm not lazy, just don't run commands stupidly for now /s
			command = LewdBot.commands.get(LewdBot.aliases.get(((String)args[0]).toLowerCase()));
			if (command != null) {
				command.run(message, args);
				System.out.println("COMMAND " + command.getName() + " in " + message.getChannel().getName());
				return;
			}
		}
	}
}
