package net.argus.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import net.argus.graphic.Function;
import net.argus.graphic.Vector2;

public class GraphicsPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4924292783008457853L;
	
	private List<Vector2> vactors = new ArrayList<Vector2>();
	private Function graphicsListener;
	
	private int lineSize = 2;
	private Color color;
	private int x, y;
	
	
	public GraphicsPanel(Function graphicsListener) {this.graphicsListener = graphicsListener;}
	
	public GraphicsPanel() {}
	
	/**
	 * Ajoute un vecteur au graphique
	 * @param vector
	 */
	public void addPoint(Vector2 vector) {
		vactors.add(vector);
		getParent().repaint();
	}
	
	/****GETTERS****/
	public Function getGraphicsListener() {return graphicsListener;}
	public Color getColor() {return color;}
	public int getLineSize() {return lineSize;}
	
	/****SETTERS****/
	public void setColor(Color color) {this.color = color;}
	public void setLineSize(int lineSize) {this.lineSize = lineSize;}
	public void addGraphicsListener(Function graphicsListener) {this.graphicsListener = graphicsListener;}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		x = getParent().getWidth() / 2;
		y = getParent().getHeight() / 2;
		
		g2.fillRect(0, y, getParent().getWidth(), 2);
		g2.fillRect(x, 0, 2, getParent().getHeight());
		
		g2.setColor(color!=null?color:g2.getColor());
		
		g2.setStroke(new BasicStroke(lineSize));
		
		if(graphicsListener != null) 
			for(float i = -10000; i < 10000; i += 1f)
				g2.draw(new Line2D.Float(x + i, y + graphicsListener.f(i, 0), x + i + 1, y + graphicsListener.f(i + 1, 0)));
		
		else
			for(int i = 0; i < vactors.size() - 1; i++)
				g2.draw(new Line2D.Float(vactors.get(i).getX(), vactors.get(i).getY(), vactors.get(i + 1).getX(), vactors.get(i + 1).getY()));
	}
	
	public static void main(String[] args) throws InterruptedException {
		//InitializationSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		//Properties config = new Properties("config", "bin");
		
		//Splash sp = new Splash("test", Icon.getIcon(FileManager.getPath("res/logo.png"), Display.getWidth() - 20), 2000);
		//sp.play();
		
		//Frame fen = new Frame("Graphics", FileManager.getPath("res/favicon32x32.png"), config);
		//fen.setIcon(FileManager.getPath("res/favicon16x16.png"));
		JFrame fen = new JFrame();
		GraphicsPanel gp = new GraphicsPanel();
		gp.setColor(Color.BLACK);
		
		fen.add(gp);
		gp.addPoint(new Vector2(0, 0));
		gp.addPoint(new Vector2(-100, 100));
		
		
		fen.setVisible(true);
		
	}
}
