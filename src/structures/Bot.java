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

public class Bot extends JDABuilder {
	public static Map<String, Command> commands = new HashMap<String, Command>();

	public Bot(AccountType accountType) {
		super(accountType);
		Config.loadFile();
		super
			.setGame(Game.of("Java Bot :]"))
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


	private Bot loadCommands() throws SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
		final long startTime = System.nanoTime();

		Reflections reflections = new Reflections("commands", new SubTypesScanner(true));
		Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);
		
		Iterator<Class<? extends Command>> iterator = allClasses.iterator();
	    while (iterator.hasNext()) {
	    	Class<? extends Object> commandClass = iterator.next();
	        String name = commandClass.getName();
	        Class<?> myClass = Class.forName(name);
			Command instance = (Command) myClass.newInstance();
			
			Bot.commands.put(name, instance);
			
			/*
			Class<?>[] cArg = new Class[1];
			cArg[0] = String.class;
			Method x = myClass.getMethod("run", cArg);
			
			Object[] args = {"lewdcario"};
			x.invoke(instance, args);

			System.out.println(x.toString());
			*/
			
			/*
	        Class[] types = {Double.TYPE, this.getClass()};
	        Constructor constructor = myClass.getConstructor(types);

	        Object[] parameters = { new Double(0), this };
	        Object instanceOfMyClass = constructor.newInstance(parameters);
	        */
	    }
	    
	    final long stopTime = System.nanoTime();
	    
	    System.out.println("Command loading took " + (stopTime - startTime)/1000000 + " ms!");
	    
	    return this;
	}
}
