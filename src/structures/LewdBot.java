package structures;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import constants.Config;
import events.MessageHandler;
import events.ReadyHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class LewdBot extends JDABuilder {
	public static Map<String, Command> commands = new HashMap<>();
	public static Map<String, String> aliases = new HashMap<>();

	public LewdBot(AccountType accountType) {
		super(accountType);
		Config.loadFile();
		super
			.setGame(Game.of("Java LewdBot :]"))
			.setToken(Config.TOKEN)
			.addEventListener(new MessageHandler())
			.addEventListener(new ReadyHandler());
		try {
			super.buildBlocking();
			this.loadCommands();
		} catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		
	}
	
	private LewdBot loadCommands() throws SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
		final long startTime = System.nanoTime();

		Reflections reflections = new Reflections("commands", new SubTypesScanner(true));
		Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);
		
		Iterator<Class<? extends Command>> iterator = allClasses.iterator();
	    while (iterator.hasNext()) {
	    	Class<? extends Command> commandClass = iterator.next();
	        String name = commandClass.getName();
			Command instance = (Command) Class.forName(name).newInstance();
			
			LewdBot.commands.put(name, instance);
			for (String alias: instance.getAliases()){
				LewdBot.aliases.put(alias, name);
			}
	    }
	    
	    final long stopTime = System.nanoTime();
	    
	    System.out.println("Command loading took " + (stopTime - startTime)/1000000 + " ms!");
	    
	    return this;
	}
}
