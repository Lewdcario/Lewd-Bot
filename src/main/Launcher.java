package main;

import net.dv8tion.jda.core.AccountType;
import structures.Bot;

public class Launcher {
	private static Bot bot = null;

	public static Bot getBot() {
		return bot;
	}
	private static void setBot(Bot bot) {
		Launcher.bot = bot;
	}

	public static void main(String[] args) {
    	Launcher.setBot(new Bot(AccountType.BOT));
    }
}
