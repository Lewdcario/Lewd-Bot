package structures;

/*
	Array of unparsed Arguents:
	["channelid", "userid"]

	Array of what the command expects (the String[] template)
	["channel", "member']

	Array of boolean optionals?
	[false, true] -> true for is optional

	Array of default values?
	[false, "ecks dee", ""] -> false for no default, "" for empty default
 */

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandParser {
	public static Object[] parse(Message message, String[] template, String[] unparsedArgs) {
		Object[] parsedArgs = new Object[template.length];
		int i = 0;
		for (String arg: unparsedArgs) {
			parsedArgs[i] = CommandParser.handle(message, template[i], arg);
			i++;
		}
		return parsedArgs;
	}

	private static Object handle(Message message, String type, String arg) {
		if (type == "string") return arg;
		if (type == "member") return CommandParser.parseMember(message, arg);
		if (type == "channel") return CommandParser.parseChannel(message, arg);
		return null;
	}

	public static Member parseMember(Message message, String arg) {
		return message.getGuild().getMemberById(Long.parseLong(arg));
	}

	public static TextChannel parseChannel(Message message, String arg) {
		return message.getGuild().getTextChannelById(Long.parseLong(arg));
	}
}
