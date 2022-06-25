package main;

import gui.Window;

public class PaintMain {

	static Window window;

	public static void main(String[] args) {
		
		window = new Window();
		
	}

	public static Window getWindow(){
		return window;
	}

}
