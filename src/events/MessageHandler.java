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
import structures.Bot;
import structures.Command;

public class MessageHandler extends ListenerAdapter {
    /**
     * NOTE THE @Override!
     * This method is actually overriding a method in the ListenerAdapter class! We place an @Override annotation
     *  right before any method that is overriding another to guarantee to ourselves that it is actually overriding
     *  a method from a super class properly. You should do this every time you override a method!
     *
     * As stated above, this method is overriding a hook method in the
     * {@link net.dv8tion.jda.core.hooks.ListenerAdapter ListenerAdapter} class. It has convience methods for all JDA events!
     * Consider looking through the events it offers if you plan to use the ListenerAdapter.
     *
     * In this example, when a message is received it is printed to the console.
     *
     * @param event
     *          An event containing information about a {@link net.dv8tion.jda.core.entities.Message Message} that was
     *          sent in a channel.
     */
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //These are provided with every event in JDA
        // JDA jda = event.getJDA();                       //JDA, the core of the api.
        // long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        // MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
                                                        //  This could be a TextChannel, PrivateChannel, or Group!
        final String content = message.getContent();  
                                                       
        if (event.isFromType(ChannelType.TEXT)) {
            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

            String name = null;
            if (message.isWebhookMessage()) name = author.getName();                                        // with the User, thus we default to the author for name.
            else name = member.getEffectiveName();
            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, content);
        }
        else if (event.isFromType(ChannelType.PRIVATE)) {
            // PrivateChannel privateChannel = event.getPrivateChannel();
            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), content);
        }
        else if (event.isFromType(ChannelType.GROUP)) {
            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";  //A group name can be null due to it being unnamed.
            System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), content);
        }
        
        if (!content.startsWith(Config.PREFIX) || !message.isFromType(ChannelType.TEXT) || author.isBot()) return;
        
        String name = content.substring(1, content.length()).split(" ")[0];
        name = new String("" + name.charAt(0)).toUpperCase() + name.substring(1, name.length());

        Command command = Bot.commands.get("commands." + name);
        if (command != null) {
        	command.run(message);
        	System.out.println("COMMAND in " + message.getChannel().getName());
        	return;
        }
    }
}