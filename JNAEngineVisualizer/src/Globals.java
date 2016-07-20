import java.awt.Dimension;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;

public class Globals 
{
	public static JFrame mainWindow;
	public static final Dimension WINDOW_DIMENSIONS = new Dimension(800, 800);
	public static final int DIMENSION_W = 800;
	public static final int DIMENSION_H = 800;
	public static ReentrantLock frameBufferLock = new ReentrantLock();
	public static ReentrantLock inputLock = new ReentrantLock();
	public static Camera camera = new Camera();
	
}
