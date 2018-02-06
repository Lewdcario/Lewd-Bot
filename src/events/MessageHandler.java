package events;

import constants.Config;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import structures.CommandHandler;


public class MessageHandler extends ListenerAdapter {
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();
        Message message = event.getMessage();
        final String content = message.getContent();
        if (author.isBot()) return;
                                                       
        if (event.isFromType(ChannelType.TEXT)) {
            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();
            Member member = event.getMember();

            String name;
            if (message.isWebhookMessage()) name = author.getName();
            else name = member.getEffectiveName();
            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, content);
        }
        else if (event.isFromType(ChannelType.PRIVATE)) {
            // PrivateChannel privateChannel = event.getPrivateChannel();
            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), content);
        }
        else if (event.isFromType(ChannelType.GROUP)) {
            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";
            System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), content);
        }
        
        if (!content.startsWith(Config.PREFIX) || !message.isFromType(ChannelType.TEXT) || author.isBot()) return;

		CommandHandler.handle(message, content);
    }
}