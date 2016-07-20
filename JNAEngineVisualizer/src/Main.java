import javax.swing.JFrame;

public class Main
{
	private static Main Window;
	
	private JFrame frame;
	private VPanel drawPanel;
	
	public Main()
	{
		frame = new JFrame("Engine Testing");
		Globals.mainWindow = frame;
		try
		{
			drawPanel = new VPanel();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		drawPanel.setPreferredSize(Globals.WINDOW_DIMENSIONS);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(drawPanel);
		frame.addKeyListener(Globals.camera);
		frame.addMouseListener(Globals.camera);
		frame.addMouseMotionListener(Globals.camera);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void pixelLoop()
	{
		while(true)
		{
			drawPanel.repaint();
			try 
			{
				Thread.sleep(1);
			} 
			catch (InterruptedException e) {}
		}
	}
	
	public static void main(String[] args)
	{
		Window = new Main();
		
		Window.pixelLoop();
	}

	
}
