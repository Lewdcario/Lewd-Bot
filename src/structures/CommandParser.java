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

class CommandParser {
	static Object parse(Message message, String[] template, String[] unparsedArgs) {
		Object[] parsedArgs = new Object[template.length];
		int i = 0;
		for (String arg: unparsedArgs) {
			try {
				parsedArgs[i] = CommandParser.handle(message, template[i], arg);
				if (CommandParser.validate(message, template[i], arg) != true /* && !isDefault */) {
					message.getChannel().sendMessage("Input for `" + template[i] + "` is invalid.").queue();
					return template;
				}
				i++;
			} catch(Exception e) {
				message.getChannel().sendMessage("Input for `" + template[i] + "` is invalid.").queue();
				return template;
			}

		}
		return parsedArgs;
	}

	private static boolean validate(Message message, String type, String arg) {
		return true; // TODO: proper checking
	}

	private static Object handle(Message message, String type, String arg) throws Exception {
		if (type == "string") return arg;
		if (type == "member") return CommandParser.parseMember(message, arg);
		if (type == "channel") return CommandParser.parseChannel(message, arg);
		return null;
	}

	public static Member parseMember(Message message, String arg) throws Exception {
		return message.getGuild().getMemberById(Long.parseLong(arg));
	}

	public static TextChannel parseChannel(Message message, String arg) throws Exception {
		return message.getGuild().getTextChannelById(Long.parseLong(arg));
	}
}
