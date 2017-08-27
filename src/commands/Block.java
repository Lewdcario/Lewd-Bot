package commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import structures.Command;

public class Block implements Command {
	public final String name = "block";
	
	public String getName() {
		return this.name;
	}
	
	public String[] getAliases() {
		return this.aliases;
	}
	
	public void run(Message message) {
		//This is an example of how to use the complete() method on RestAction. The complete method acts similarly to how
        // JDABuilder's buildBlocking works, it waits until the request has been sent before continuing execution.
        //Most developers probably wont need this and can just use queue. If you use complete, JDA will still handle ratelimit
        // control, however if shouldQueue is false it won't queue the Request to be sent after the ratelimit retry after time is past. It
        // will instead fire a RateLimitException!
        //One of the major advantages of complete() is that it returns the object that queue's success consumer would have,
        // but it does it in the same execution context as when the request was made. This may be important for most developers,
        // but, honestly, queue is most likely what developers will want to use as it is faster.

        try {
            //Note the fact that complete returns the Message object!
            //The complete() overload queues the Message for execution and will return when the message was sent
            //It does handle rate limits automatically
            Message sentMessage = message.getChannel().sendMessage("I blocked and will return the message!").complete();
            //This should only be used if you are expecting to handle rate limits yourself
            //The completion will not succeed if a rate limit is breached and throw a RateLimitException
            @SuppressWarnings("unused")
			Message sentRatelimitMessage = message.getChannel().sendMessage("I expect rate limitation and know how to handle it!").complete(false);

            System.out.println("Sent a message using blocking! Luckly I didn't get Ratelimited... MessageId: " + sentMessage.getId());
        }
        catch (RateLimitedException e) {
            System.out.println("Whoops! Got ratelimited when attempting to use a .complete() on a RestAction! RetryAfter: " + e.getRetryAfter());
        }
        //Note that RateLimitException is the only checked-exception thrown by .complete()
        catch (RuntimeException e) {
            System.out.println("Unfortunately something went wrong when we tried to send the Message and .complete() threw an Exception.");
            e.printStackTrace();
        }
    }
}

