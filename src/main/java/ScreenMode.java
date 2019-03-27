import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;

public class ScreenMode {

	private GraphicsDevice graph;

	public ScreenMode() {

		GraphicsEnvironment egraph = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graph = egraph.getDefaultScreenDevice();

	}

	public void setFullScreen(DisplayMode dm, JFrame window) {
		window.setUndecorated(true);
		window.setResizable(false);

		graph.setFullScreenWindow(window);

		if (dm != null && graph.isDisplayChangeSupported()) {
			try {
				graph.setDisplayMode(dm);

			} catch (Exception e) {
                e.printStackTrace();
			}
		}

	}

	private Window getFullScreenWindow() {
		return graph.getFullScreenWindow();

	}

	public void restoreScreen() {
		Window w = graph.getFullScreenWindow();
		if (w != null) {
			w.dispose();
		}
		graph.setFullScreenWindow(null);
	}
}