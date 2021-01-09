package net.argus.example.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.argus.example.client.gui.Connect;
import net.argus.example.client.gui.GUIClient;
import net.argus.file.FileManager;
import net.argus.file.css.CSSEngine;
import net.argus.gui.Icon;
import net.argus.gui.Look;
import net.argus.gui.TextField;
import net.argus.gui.splash.SplashScreen;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Display;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class MainClient {
	
	private SplashScreen splash;
	
	private ExampleClient client;
	private MainClient thisObj;
	
	public static String icon16;
	public static String icon32;
	
	public MainClient() {
		splash = new SplashScreen(Icon.getIcon(FileManager.getPath("res/logo.png"), Display.getWidhtDisplay() - 50), 1000);
		splash.view();
		
		icon16 = FileManager.getPath("res/favicon16x16.png");
		icon32 = FileManager.getPath("res/favicon32x32.png");
		
		CSSEngine.run("client", "bin/css");
		
		thisObj = this;
		
		GUIClient.addFastAction(getFastActionListener());
		GUIClient.addJoinAction(getJoinActionListener());
		GUIClient.addLeaveAction(getLeaveActionListener());
		
		GUIClient.addSendAction(getSendActionListener());
		
		while(!splash.isFinnish())
			GUIClient.setVisible(false);
			
		GUIClient.setVisible(true);
		
	}
	
	public ActionListener getFastActionListener() {
		return (ActionEvent e) -> new Connect(thisObj, true).start();
	}
	
	public ActionListener getJoinActionListener() {
		return (ActionEvent e) -> new Connect(thisObj, false).start();
	}
	
	public ActionListener getLeaveActionListener() {
		return (ActionEvent e) -> client.logOut();
	}
	
	public ActionListener getSendActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TextField field = GUIClient.panChat.getTextField();
				String msg = field.getText();
				
				if(msg.toCharArray()[0] == '/')
					client.sendPackage(new Package(new PackageBuilder(PackageType.COMMANDE.getId()).addValue("command", msg)));
				else
					client.sendPackage(new Package(new PackageBuilder(PackageType.MESSAGE.getId()).addValue("message", msg)));
			
				GUIClient.addMessage(new String[] {"Vous", msg});
			
				field.copyData();
				field.setText("");
			}
		};
	
	}
	
	public void connect(String host, String pseudo, String password) throws UnknownHostException, IOException {
		client = new ExampleClient(host, pseudo, password);
		
		client.addClientManager(new ClientManagerChat(client));
		client.addProcessListener(new ProcessListenerChat());

		try {
			client.start();
			
			GUIClient.addFrameListener(client.getFrameListener());
			
			GUIClient.connect();
		}catch(IOException e) {
			GUIClient.leave();
			
			String errmsg = "";
			if(e instanceof SocketException) errmsg = "Time Out";
			else if(e instanceof UnknownHostException) errmsg = "Unknown Host";
			
			JOptionPane.showMessageDialog(GUIClient.getFrame(), errmsg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		Thread.currentThread().setName("client");
		
		Debug.addBlackList(ThreadManager.UPDATE_UI);
		
		Look.chageLook(UIManager.getSystemLookAndFeelClassName());
		
		new MainClient();
	}

}
