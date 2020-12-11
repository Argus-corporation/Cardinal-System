package net.argus.http;

import java.io.IOException;
import java.net.ServerSocket;

import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class HTTP {
	
	private ServerSocket server;
	
	private boolean running;
	
	public HTTP() throws IOException {
		server = new ServerSocket(80);
	}
	
	public void start() throws IOException {
		running = true;
		loop();
	}
	
	public void loop() throws IOException {
		Runnable r = () -> {
			while(running)
				try {new HTTPClient(server.accept());}catch(IOException e) {e.printStackTrace();}
		};
		new Thread(r).start();
		
	}
	
	public static void main(String[] args) throws IOException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		new HTTP().start();
	}

}
