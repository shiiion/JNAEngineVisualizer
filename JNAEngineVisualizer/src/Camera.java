import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Camera implements KeyListener, MouseMotionListener, MouseListener
{
	public Vector3 pos = new Vector3();
	public float pitch;
	public float yaw;
	public float roll;
	
	private float sensitivity = 0.005f;
	private float speed = 1.f;
	
	private int lastX;
	private int lastY;
	private boolean first = false;
	
	public void SetSensitivity(float s)
	{
		sensitivity = s;
	}
	
	public void SetSpeed(float s)
	{
		speed = s;
	}
	
	public void keyPressed(KeyEvent arg0) 
	{
		Globals.inputLock.lock();
		if(arg0.getKeyCode() == 65)
		{
			Vector3 direction = Vector3.FromEuler(yaw + (float)(Math.PI / 2.f), 0, roll, speed);
			pos.z += direction.z;
			pos.y += direction.y;
			pos.x += direction.x;
		}
		if(arg0.getKeyCode() == 68)
		{
			Vector3 direction = Vector3.FromEuler(yaw + (float)(Math.PI / 2.f), 0, roll, speed);
			pos.z -= direction.z;
			pos.y -= direction.y;
			pos.x -= direction.x;
		}
		if(arg0.getKeyCode() == 87)
		{
			Vector3 direction = Vector3.FromEuler(yaw, pitch, roll, speed);
			pos.z += direction.z;
			pos.y += direction.y;
			pos.x += direction.x;
			
		}
		if(arg0.getKeyCode() == 83)
		{
			Vector3 direction = Vector3.FromEuler(yaw, pitch, roll, speed);
			pos.z -= direction.z;
			pos.y -= direction.y;
			pos.x -= direction.x;
		}
		
		Globals.inputLock.unlock();
	}
	
	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	public void mouseDragged(MouseEvent arg0) 
	{
		if(first)
		{
			first = false;
			lastX = arg0.getXOnScreen();
			lastY = arg0.getYOnScreen();
		}
		Globals.inputLock.lock();
		pitch += (float)(arg0.getYOnScreen() - lastY) * sensitivity;
		yaw -= (float)(arg0.getXOnScreen() - lastX) * sensitivity;
		if(pitch < -(Math.PI / 2.f))
			Globals.camera.pitch = (float) (-Math.PI / 2.f);
		if(pitch > (Math.PI / 2.f))
			pitch = (float) (Math.PI / 2.f);
			
		Globals.inputLock.unlock();
		lastX = arg0.getXOnScreen();
		lastY = arg0.getYOnScreen();
	}

	public void mouseMoved(MouseEvent arg0) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mousePressed(MouseEvent e) 
	{
		first = true;
	}
}
