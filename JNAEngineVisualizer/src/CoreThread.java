import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


public class CoreThread implements Runnable
{
	private volatile boolean running;
	private Graphics2D bufferGraphics;

	private ArrayList<Pointer> entities;
	private CoreDLL nst;

	public interface CoreDLL extends Library
	{
		CoreDLL INSTANCE = (CoreDLL)Native.loadLibrary("core.dll", CoreDLL.class);

		void startCore();
		void tickCore();
		void sleepTickTime();
		Pointer createAddEntity(float x, float y, float z, float xr, float yr, float zr, float xv, float yv, float zv, float xe, float ye, float ze, float rebound, int dynamic, float mass, float friction);

		float getEntityLoc(Pointer entity, int ind);
		float getAxis(Pointer entity, int ind, int ind2);
	}

	public CoreThread(Graphics2D g)
	{
		running = true;
		bufferGraphics = g;
		nst = CoreDLL.INSTANCE;
		nst.startCore();
		entities = new ArrayList<Pointer>();
//		entities.add(nst.createAddEntity(0, 0, 0, 0.1f, 0, 0, 0, 0, 0, 100, 1.f, 100f, 0, 0, 1, 100));
		//entities.add(nst.createAddEntity(0, 0, 0, 0, 0, 0, 10f, 0, 0, 30.f, 1.f, 30.f, 0, 1, 1f, 0.01f));
//		entities.add(nst.createAddEntity(0, 7, 0, 0, 0, 0, 10, 0, 0, 1.f, 1.f, 1.f, 0, 1, 1f, 100));
		entities.add(nst.createAddEntity(0, -10, 0, 0, 0, 0, 0, 0, 0, 100, 1.f, 100f, 1, 0, 1, 100));
		for (int x = -20; x <= 20; x += 3) {
			for (int y = 5; y <= 45; y += 3) {
				float rnd = (float)Math.random() * 40 - 20;
				float xv = 0 - x;
				float yv = 0 - y;
				float zv = 0 - rnd;
				entities.add(nst.createAddEntity((float)x, (float)y, (float)rnd, 0, 0, 0, xv, yv, zv, 1.f, 1.f, 1.f, 1, 1, 1f, 10f));
			}
		}
		
		Globals.camera.pos = new Vector3(6, -5, -40);


	}



	private void DrawWorld(Graphics2D g)
	{
		for(Pointer e : entities)
		{
			DrawEntity(e, g);
		}
	}

	private void DrawEntity(Pointer entity, Graphics2D g)
	{
		float x, y, z;
		x = nst.getEntityLoc(entity, 0); y = -nst.getEntityLoc(entity, 1); z = nst.getEntityLoc(entity, 2) + 15;
		Vector3 c = new Vector3(x, y, z);
		Vector3 xa = new Vector3(nst.getAxis(entity, 0, 0), -nst.getAxis(entity, 0, 1), nst.getAxis(entity, 0, 2));
		Vector3 ya = new Vector3(nst.getAxis(entity, 1, 0), -nst.getAxis(entity, 1, 1), nst.getAxis(entity, 1, 2));
		Vector3 za = new Vector3(nst.getAxis(entity, 2, 0), -nst.getAxis(entity, 2, 1), nst.getAxis(entity, 2, 2));

		DrawBox(g, c, xa, ya, za);
	}

	private void DrawBox(Graphics2D g, Vector3 center, Vector3 xAxis, Vector3 yAxis, Vector3 zAxis)
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
					g.drawString("X: " + center.x, pc.x, pc.y);
					g.drawString("Y: " + -center.y, pc.x, pc.y + 13);
					g.drawString("Z: " + center.z, pc.x, pc.y + 26);
				}
	}

	public void run()
	{
		Globals.camera.yaw = 0;
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(running)
		{
			Globals.frameBufferLock.lock();
			Globals.inputLock.lock();
			long ct = System.nanoTime();
			nst.tickCore();
			System.out.println((double)(System.nanoTime() - ct) / 1000000);
			DrawWorld(bufferGraphics);
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
