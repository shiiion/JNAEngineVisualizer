import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class VPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage frameBuffer = new BufferedImage(Globals.DIMENSION_W, Globals.DIMENSION_H, BufferedImage.TYPE_4BYTE_ABGR);
	private Graphics2D bufferGraphics;
	Color alphaLayer = new Color(0, 0, 0, 100);
	private Thread emitterThread;
	
	public VPanel()
	{
		bufferGraphics = (Graphics2D) frameBuffer.getGraphics();
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		bufferGraphics.setRenderingHints(rh);
		CoreThread ct = new CoreThread(bufferGraphics);
		emitterThread = new Thread(ct);
		emitterThread.start();
	}
	
	public void increaseAlpha()
	{
		bufferGraphics.setColor(alphaLayer);
		bufferGraphics.fillRect(0, 0, Globals.DIMENSION_W, Globals.DIMENSION_H);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		Globals.frameBufferLock.lock();
		increaseAlpha();
		g.drawImage(frameBuffer, 0, 0, null);
		Globals.frameBufferLock.unlock();
	}
}
