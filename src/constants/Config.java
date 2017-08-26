package constants;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Download JARs: http://home.dv8tion.net:8080/job/JDA/lastBuild/
 * JDA Repo: https://github.com/DV8FromTheWorld/JDA
 */

public class Config {
	public final static String PATH = "./src/config.txt";
	public static String TOKEN = null;
	public final static String PREFIX = "!";
	
	// The config.txt file should only be one line, consisting of the TOKEN.
	public static void loadFile() {
		File f = new File(Config.PATH);
		if (!f.exists() || f.isDirectory()) System.err.println(Thread.currentThread().getStackTrace()[1]);
		else {
			try {
				Config.TOKEN = new String(Files.readAllBytes(Paths.get(Config.PATH)), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
