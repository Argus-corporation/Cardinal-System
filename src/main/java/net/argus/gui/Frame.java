package net.argus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import net.argus.file.FileManager;
import net.argus.file.Properties;
import net.argus.file.css.CSSEngine;
import net.argus.gui.animation.FrameAnimation;
import net.argus.lang.Lang;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Direction;
import net.argus.util.ListenerManager;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6058458405056389502L;

	protected Properties config;
	
	protected TopPanel topPan;
	protected Panel mainPan = new Panel();
	
	protected ImageIcon iconFrame;
	protected ImageIcon iconOs;
	
	protected boolean undecorated;
	protected boolean fullScreen;
	protected boolean maximized;
	
	protected String imagePath;
	
	protected Dimension normalSize;
	
	protected Point position;
	
	protected ListenerManager<FrameListener> frameManager = new ListenerManager<FrameListener>();
	//protected FrameListener fenListener;

	public Frame(String title, String pathIcon, boolean[] but, Properties config) {
		
		Lang.setLang(config);
		this.config = config;
		this.undecorated = config.getBoolean("frame.undecorated");
		
		mainPan = new Panel();
		
		iconFrame = new ImageIcon(pathIcon);
		iconOs = new ImageIcon(pathIcon);
		
		this.setIconImage(iconOs.getImage());
		
		iconFrame = Icon.getIcon(pathIcon);
		
		this.setSize(config.getDimension("frame.size"));
		this.normalSize = this.getSize();
		
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(undecorated);
		this.setAlwaysOnTop(config.getBoolean("frame.alwaysontop"));
		
		if(undecorated) {
			topPan = new TopPanel(this, iconFrame, but, config);
			super.add(BorderLayout.NORTH, topPan);
			super.add(BorderLayout.CENTER, mainPan);
			
			getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#DADADA")));
		}
		
		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F11 && !maximized) {
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					maximized = true;
				}else if(e.getKeyCode() == KeyEvent.VK_F11 && maximized) {
					setSize(normalSize);
					maximized = false;
				}
				
			}
		});

	}
	
	protected Frame(String title, String iconPath, Properties config) {
		this.config = config;
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setSize(config.getDimension("frame.size"));
		this.setAlwaysOnTop(config.getBoolean("frame.alwaysontop"));
	}
	
	public void removeAll() {
		super.removeAll();
		this.add(BorderLayout.NORTH, topPan);
		this.repaint();
	}
	
	public void setIcon(String iconPath) {
		iconFrame = Icon.getIcon(iconPath);
		topPan.setIcon(iconFrame);
	}
	
	public void setTitleUndeco(String title) {topPan.setTitle(title);}
	
	public boolean isFullScreen() {return fullScreen;}
	public void setFullScreen(boolean isFullScreen) {this.fullScreen = isFullScreen;}
	
	public TopPanel getTopPanel() {return this.topPan;}
	public Dimension getNormalSize() {return normalSize;}
	public Point getSavePosition() {return position;}
	
	public void setNoramlSize(Dimension normalSize) {this.normalSize = normalSize;}
	public void addFrameListener(FrameListener fenListener) {this.frameManager.addListener(fenListener);}
	
	public void savePosition() {this.position = this.getLocation();}
	
	public Dimension getFrameSize() {return new Dimension(getSize().width, getSize().height - getTopPanel().getPreferredSize().height);}
	
	@Override
	public Component add(Component comp) {
		return mainPan.add(comp);
	}
	
	@Override
	public Component add(String name, Component comp) {
		return mainPan.add(name, comp);
	}
	
	public void setMainLayout(LayoutManager manager) {
		mainPan.setLayout(manager);
	}
	
	public LayoutManager getMainLayout() {
		return mainPan.getLayout();
	}
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		
		Properties config = new Properties("config", "bin");
		String sIcon = FileManager.getPath("res/logo.png");
		boolean[] isE = new boolean[] {true, true, true};
		Frame fen = new Frame("armin", sIcon, isE, config);
		//new Splash("dfsd", sIcon, fen, 1000, config);
		
		fen.addFrameListener(new FrameListener() {
			
			@Override
			public void frameResizing() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void frameMinimalized() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void frameClosing() {
				new FrameAnimation(fen).play(); 
				UserSystem.exit(0);
			}
		});
	
		CSSEngine.run("test", "bin");
		
		Label lab = new Label("bonjour", true);
		fen.add(lab);
		//Element.repaintElement();
		fen.setVisible(true);
		//fen.moveBar(false, fen.topPan);
		int height = -fen.topPan.getHeight();
		for(int i = 0; i != height; i--) {
			fen.topPan.setBounds(fen.topPan.getX(), i, fen.topPan.getWidth(), fen.topPan.getHeight());
			//System.out.println(i + "\n" + height);
			Thread.sleep(20);
		}
		//System.out.println("ll");
		for(int i = height; i < 0; i++) {
			fen.topPan.setBounds(fen.topPan.getX(), i, fen.topPan.getWidth(), fen.topPan.getHeight());
			Thread.sleep(20);
		}
	}
	@Deprecated
	public void moveBar(Direction dir, TopPanel tp) {
		int height = -tp.getHeight();
		
		if(dir == Direction.UP) {
			for(int i = 0; i != height; i--) {
				tp.setBounds(tp.getX(), i, tp.getWidth(), tp.getHeight());
				
				try {Thread.sleep(20);} catch(InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	
}
