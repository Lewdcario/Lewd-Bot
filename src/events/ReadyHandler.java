package events;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

// List of Events: http://home.dv8tion.net:8080/job/JDA/lastSuccessfulBuild/javadoc/net/dv8tion/jda/core/hooks/ListenerAdapter.html

public class ReadyHandler extends ListenerAdapter {
	@Override
	public void onReady(ReadyEvent event) {
		System.out.println("Logged in and ready to serve!");
	}
}
