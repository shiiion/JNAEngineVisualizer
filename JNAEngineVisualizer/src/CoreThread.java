import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


public class CoreThread implements Runnable
{
	private volatile boolean running;
	private Graphics2D bufferGraphics;

	private Pointer e1;
	private Pointer e2;
	private Pointer e3;
	private Pointer e4;
	private CoreDLL nst;
	
	public interface CoreDLL extends Library
	{
		CoreDLL INSTANCE = (CoreDLL)Native.loadLibrary("core.dll", CoreDLL.class);
		
		void startCore();
		void tickCore();
		void sleepTickTime();
		Pointer createAddEntity(float x, float y, float z, float xr, float yr, float zr, float xv, float yv, float zv, float xe, float ye, float ze, float rebound, int dynamic, float mass);
		
		float getEntityLoc(Pointer entity, int ind);
		float getAxis(Pointer entity, int ind, int ind2);
	}

	public CoreThread(Graphics2D g)
	{
		running = true;
		bufferGraphics = g;
		nst = CoreDLL.INSTANCE;
		nst.startCore();
		e1 = nst.createAddEntity(0, 0   , 0, (float)Math.toRadians(0), (float)Math.toRadians(0), (float)Math.toRadians(0), 0, 0, 0, 100, 0.5f, 100f, 1, 0, 1);
		e2 = nst.createAddEntity(0, 15f, 0, 0   , 0, 0   , 30, 0, 0, 5f, 5f, 5f, 0, 1, 1);
		e3 = nst.createAddEntity(0, 3.5f, 0, 0, 0, 0, 0, 0, 0, 0.5f, 0.5f, 0.5f, 0.5f, 1, 1);
		e4 = nst.createAddEntity(0, 6.5f, 0, 0, 0, 0, 0, 0, 0, 0.5f, 0.5f, 0.5f, 0.5f, 1, 1);
		Globals.camera.pos = new Vector3(10, -5, -20);
	}
	
	private void DrawAxes(Graphics2D g)
	{
		float x = nst.getEntityLoc(e1, 0), y = -nst.getEntityLoc(e1, 1), z = nst.getEntityLoc(e1, 2) + 15;
		Vector3 c1 = new Vector3(x, y, z);
		Vector3 xa1 = new Vector3(nst.getAxis(e1, 0, 0), -nst.getAxis(e1, 0, 1), nst.getAxis(e1, 0, 2));
		Vector3 ya1 = new Vector3(nst.getAxis(e1, 1, 0), -nst.getAxis(e1, 1, 1), nst.getAxis(e1, 1, 2));
		Vector3 za1 = new Vector3(nst.getAxis(e1, 2, 0), -nst.getAxis(e1, 2, 1), nst.getAxis(e1, 2, 2));
		
		x = nst.getEntityLoc(e2, 0); y = -nst.getEntityLoc(e2, 1); z = nst.getEntityLoc(e2, 2) + 15;
		Vector3 c2 = new Vector3(x, y, z);
		Vector3 xa2 = new Vector3(nst.getAxis(e2, 0, 0), -nst.getAxis(e2, 0, 1), nst.getAxis(e2, 0, 2));
		Vector3 ya2 = new Vector3(nst.getAxis(e2, 1, 0), -nst.getAxis(e2, 1, 1), nst.getAxis(e2, 1, 2));
		Vector3 za2 = new Vector3(nst.getAxis(e2, 2, 0), -nst.getAxis(e2, 2, 1), nst.getAxis(e2, 2, 2));
		
		x = nst.getEntityLoc(e3, 0); y = -nst.getEntityLoc(e3, 1); z = nst.getEntityLoc(e3, 2) + 15;
		Vector3 c3 = new Vector3(x, y, z);
		Vector3 xa3 = new Vector3(nst.getAxis(e3, 0, 0), nst.getAxis(e3, 0, 1), nst.getAxis(e3, 0, 2));
		Vector3 ya3 = new Vector3(nst.getAxis(e3, 1, 0), nst.getAxis(e3, 1, 1), nst.getAxis(e3, 1, 2));
		Vector3 za3 = new Vector3(nst.getAxis(e3, 2, 0), nst.getAxis(e3, 2, 1), nst.getAxis(e3, 2, 2));
		
		x = nst.getEntityLoc(e4, 0); y = -nst.getEntityLoc(e4, 1); z = nst.getEntityLoc(e4, 2) + 15;
		Vector3 c4 = new Vector3(x, y, z);
		Vector3 xa4 = new Vector3(nst.getAxis(e3, 0, 0), nst.getAxis(e3, 0, 1), nst.getAxis(e3, 0, 2));
		Vector3 ya4 = new Vector3(nst.getAxis(e3, 1, 0), nst.getAxis(e3, 1, 1), nst.getAxis(e3, 1, 2));
		Vector3 za4 = new Vector3(nst.getAxis(e3, 2, 0), nst.getAxis(e3, 2, 1), nst.getAxis(e3, 2, 2));

		DrawAxes2(g, c1, xa1, ya1, za1);
		DrawAxes2(g, c2, xa2, ya2, za2);
		DrawAxes2(g, c3, xa3, ya3, za3);
		DrawAxes2(g, c4, xa4, ya4, za4);
	}

	private void DrawAxes2(Graphics2D g, Vector3 center, Vector3 xAxis, Vector3 yAxis, Vector3 zAxis)
	{
		g.setColor(new Color(0xFFFFFFFF));

		Vector3 p1 = Math3D.Perspective(xAxis.add(yAxis.add(zAxis)).add(center));
		Vector3 p2 = Math3D.Perspective(xAxis.mul(-1).add(yAxis.add(zAxis)).add(center));
		Vector3 p3 = Math3D.Perspective(xAxis.mul(-1).add(yAxis.mul(-1).add(zAxis)).add(center));
		Vector3 p4 = Math3D.Perspective(xAxis.add(yAxis.mul(-1).add(zAxis)).add(center));
		Vector3 p5 = Math3D.Perspective(xAxis.add(yAxis.add(zAxis.mul(-1))).add(center));
		Vector3 p6 = Math3D.Perspective(xAxis.mul(-1).add(yAxis.add(zAxis.mul(-1))).add(center));
		Vector3 p7 = Math3D.Perspective(xAxis.mul(-1).add(yAxis.mul(-1).add(zAxis.mul(-1))).add(center));
		Vector3 p8 = Math3D.Perspective(xAxis.add(yAxis.mul(-1).add(zAxis.mul(-1))).add(center));

		if(p1.z > 0 && p2.z > 0)
			g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
		if(p2.z > 0 && p3.z > 0)
			g.drawLine((int)p2.x, (int)p2.y, (int)p3.x, (int)p3.y);
		if(p3.z > 0 && p4.z > 0)
			g.drawLine((int)p3.x, (int)p3.y, (int)p4.x, (int)p4.y);
		if(p4.z > 0 && p1.z > 0)
			g.drawLine((int)p4.x, (int)p4.y, (int)p1.x, (int)p1.y);

		if(p1.z > 0 && p5.z > 0)
			g.drawLine((int)p1.x, (int)p1.y, (int)p5.x, (int)p5.y);
		if(p2.z > 0 && p6.z > 0)
			g.drawLine((int)p2.x, (int)p2.y, (int)p6.x, (int)p6.y);
		if(p3.z > 0 && p7.z > 0)
			g.drawLine((int)p3.x, (int)p3.y, (int)p7.x, (int)p7.y);
		if(p4.z > 0 && p8.z > 0)
			g.drawLine((int)p4.x, (int)p4.y, (int)p8.x, (int)p8.y);

		if(p5.z > 0 && p6.z > 0)
			g.drawLine((int)p5.x, (int)p5.y, (int)p6.x, (int)p6.y);
		if(p6.z > 0 && p7.z > 0)
			g.drawLine((int)p6.x, (int)p6.y, (int)p7.x, (int)p7.y);
		if(p7.z > 0 && p8.z > 0)
			g.drawLine((int)p7.x, (int)p7.y, (int)p8.x, (int)p8.y);
		if(p8.z > 0 && p1.z > 0)
			g.drawLine((int)p8.x, (int)p8.y, (int)p5.x, (int)p5.y);
		
		Vector3 pc = Math3D.Perspective(center);
		if(pc.z > 0)
		{
			g.drawString("X: " + center.x, (int)pc.x, (int)pc.y);
			g.drawString("Y: " + center.y, (int)pc.x, (int)pc.y + 13);
			g.drawString("Z: " + center.z, (int)pc.x, (int)pc.y + 26);
		}
	}

	public void run()
	{
		Globals.camera.yaw = 0;
		while(running)
		{
			Globals.frameBufferLock.lock();
			Globals.inputLock.lock();
			nst.tickCore();
			DrawAxes(bufferGraphics);
			Globals.inputLock.unlock();
			Globals.frameBufferLock.unlock();
			
			try
			{
				Thread.sleep(1);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
