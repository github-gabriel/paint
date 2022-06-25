package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import settings.Settings;

public class DrawingArea extends JPanel implements MouseMotionListener, KeyListener {

	private int x, y;

	List<Integer> listX = new ArrayList<Integer>();
	List<Integer> listY = new ArrayList<Integer>();
	List<Color> listColor = new ArrayList<Color>();
	List<Integer> listWidth = new ArrayList<Integer>();

	Color color = Color.BLACK;

	BufferedImage bufferedImage;

	Graphics2D g2D;

	public DrawingArea() {

		this.setPreferredSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
		this.setForeground(color);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		bufferedImage = new BufferedImage(Settings.WIDTH, Settings.HEIGHT, BufferedImage.TYPE_INT_ARGB);

	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		x = e.getX();
		y = e.getY();

		listX.add(x);
		listY.add(y);
		listColor.add(color);
		listWidth.add(Settings.BRUSH_WIDTH);
		
		Graphics g = getGraphics();
		g2D = (Graphics2D) g;

		g2D.setColor(color);

		g2D.fillRoundRect(x, y, Settings.BRUSH_WIDTH, Settings.BRUSH_WIDTH, 100, 100);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < listX.size(); i++) {
			g.setColor(listColor.get(i));
			g.fillRoundRect(listX.get(i), listY.get(i), listWidth.get(i), listWidth.get(i), 100, 100);
		}

	}

	public Graphics2D getG2D(){
		return g2D;
	}

}
