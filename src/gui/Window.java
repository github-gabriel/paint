package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import settings.Settings;

public class Window extends JFrame implements ActionListener {

	JMenu file, brush;

	JMenuBar menuBar;

	JMenuItem save, color, size;

	DrawingArea panel = new DrawingArea();

	public Window() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.add(panel);

		file = new JMenu("File");

		menuBar = new JMenuBar();

		save = new JMenuItem("Save");
		save.addActionListener(this);

		file.add(save);

		menuBar.add(file);

		brush = new JMenu("Brush");

		color = new JMenuItem("Color");
		color.addActionListener(this);
		size = new JMenuItem("Size");
		size.addActionListener(this);

		brush.add(color);
		brush.add(size);

		menuBar.add(brush);

		this.setJMenuBar(menuBar);

		this.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == save) {

			JFileChooser fileChooser = new JFileChooser();

			int feedback = fileChooser.showOpenDialog(null);

			if (feedback == JFileChooser.APPROVE_OPTION) {
				System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
				this.save(fileChooser.getSelectedFile().getAbsolutePath() + ".png", this.getWidth(), this.getHeight());
			}

		}
		if (e.getSource() == color) {

			Color color = JColorChooser.showDialog(this, "Choose Brush Color", Color.BLACK);

			if (color != null) {
				panel.setColor(color);
			}

		}
		if (e.getSource() == size) {
			JOptionPane optionPane = new JOptionPane();
			JSlider slider = getSlider(optionPane);
			optionPane.setMessage(new Object[] { "Select a value: ", slider });
			optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
			optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
			JDialog dialog = optionPane.createDialog(this, "Brush Size");
			dialog.setVisible(true);
			Settings.BRUSH_WIDTH = (int) optionPane.getInputValue();
		}

	}

	public void save(String filepath, int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = bufferedImage.createGraphics();
		panel.paintAll(g2D);
		try {
			if (ImageIO.write(bufferedImage, "png", new File(filepath))) {
				System.out.println("saved");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static JSlider getSlider(final JOptionPane optionPane) {
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(5);
		slider.setMaximum(50);
		slider.setValue(Settings.BRUSH_WIDTH);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		ChangeListener changeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				JSlider theSlider = (JSlider) changeEvent.getSource();
				if (!theSlider.getValueIsAdjusting()) {
					optionPane.setInputValue(theSlider.getValue());
				}

			}
		};
		slider.addChangeListener(changeListener);
		return slider;
	}

}
