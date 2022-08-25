package fractales;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class PanelFractalSierpinski extends JComponent implements MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private final static int FPS = 60;
	private final static int TARGET_TIME = 1_000_000_000 / FPS;
	private double sin60 = Math.sin(Math.PI / 3.);

	private Graphics2D g2;
	private BufferedImage image;
	private int widthPanel;
	private int heightPanel;
	private boolean start = true;
	private int nivel_de_recursividad = 1;

	public void start() {
		widthPanel = getWidth();
		heightPanel = getHeight();
		image = new BufferedImage(widthPanel, heightPanel, BufferedImage.TYPE_INT_ARGB);
		g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setFont(new Font("Monospaced", Font.PLAIN, 18));
		addMouseMotionListener(this);
		var thread = new Thread(() -> {
			while (start) {
				long startTime = System.nanoTime();
				drawBackGround();
				drawFractal();
				render();
				long endTime = System.nanoTime();
				long time = endTime - startTime;
				if (time < TARGET_TIME) {
					long sleep = (TARGET_TIME - time) / 1_000_000;
					sleep(sleep);
				}
			}
		});
		thread.start();
	}

	private void render() {
		var g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}

	private void paintRecursivo(int i, double xp12, double yp12, double xp22, double yp22) {

		double dx = (xp22 - xp12) / 2.;
		double dy = (yp22 - yp12) / 2.;
		double xp32 = xp12 + dx - 2 * dy * sin60;
		double yp32 = yp12 + dy + 2 * dx * sin60;

		double dx1 = (xp22 + xp12) / 2.;
		double dy1 = (yp22 + yp12) / 2.;
		double dx2 = (xp32 + xp22) / 2.;
		double dy2 = (yp32 + yp22) / 2.;
		double dx3 = (xp12 + xp32) / 2.;
		double dy3 = (yp12 + yp32) / 2.;

		if (i <= 0) {
			g2.drawLine((int) xp12, (int) yp12, (int) xp22, (int) yp22);
			g2.drawLine((int) xp22, (int) yp22, (int) xp32, (int) yp32);
			g2.drawLine((int) xp32, (int) yp32, (int) xp12, (int) yp12);
		} else {
			paintRecursivo(i - 1, xp12, yp12, dx1, dy1);
			paintRecursivo(i - 1, dx1, dy1, xp22, yp22);
			paintRecursivo(i - 1, dx3, dy3, dx2, dy2);
		}
	}

	private void drawFractal() {
		double xp1 = 1050;
		double yp1 = 700;
		double xp2 = 250;
		double yp2 = 700;

		g2.setColor(Color.BLACK);
		g2.drawString("n= " + nivel_de_recursividad, 240, 100);
		paintRecursivo(nivel_de_recursividad, xp1, yp1, xp2, yp2);
	}

	private void drawBackGround() {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, widthPanel, heightPanel);
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		double y = e.getY() / 100.;

		if (y < 2) {
			nivel_de_recursividad = 1;
		} else if (y <= 2.5) {
			nivel_de_recursividad = 2;
		}
		else if (y <= 3) {
			nivel_de_recursividad = 3;
		}
		else if (y <= 3.5) {
			nivel_de_recursividad = 4;
		}
		else if (y <= 4) {
			nivel_de_recursividad = 5;
		}
		else if (y <= 4.5) {
			nivel_de_recursividad = 6;
		}
		else if (y <= 5) {
			nivel_de_recursividad = 7;
		}
		else if (y <= 5.5) {
			nivel_de_recursividad = 8;
		}
		else {
			nivel_de_recursividad = 8;
		}
		//System.out.println("x: " + e.getX() + " y: " +e.getY() );
	}

}
