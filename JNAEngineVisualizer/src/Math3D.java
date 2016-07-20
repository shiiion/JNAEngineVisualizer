public class Math3D 
{
	public static void Rotate(Vector3 point, float yaw, float pitch, float roll)
	{
		float x = point.x;
		float y = point.y;
		float z = point.z;
		
		float finalx = (x * (float)(Math.cos(yaw)) + 
				(z * (float)(Math.sin(yaw))));
		float finalz = (x * (float)(-Math.sin(yaw)) + 
				(z * (float)(Math.cos(yaw))));

		float finaly = (y * (float)(Math.cos(pitch)) + 
				(finalz * (float)(-Math.sin(pitch))));
		finalz = (y * (float)(Math.sin(pitch)) + 
				(finalz * (float)(Math.cos(pitch))));
		
		point.x = finalx;
		point.y = finaly;
		point.z = finalz;
	}
	
	public static Vector3 Perspective(Vector3 c)
	{
		float x, y, z, xp, yp;
		x = c.x - Globals.camera.pos.x;
		y = c.y - Globals.camera.pos.y;
		z = c.z - Globals.camera.pos.z;
		
		float yaw = Globals.camera.yaw;
		float pitch = Globals.camera.pitch;
		
		float finalx = (x * (float)(Math.cos(yaw)) + 
				(z * (float)(Math.sin(yaw))));
		float finalz = (x * (float)(-Math.sin(yaw)) + 
				(z * (float)(Math.cos(yaw))));

		float finaly = (y * (float)(Math.cos(pitch)) + 
				(finalz * (float)(-Math.sin(pitch))));
		finalz = (y * (float)(Math.sin(pitch)) + 
				(finalz * (float)(Math.cos(pitch))));

		
		xp = finalx / finalz;
		yp = finaly / finalz;
		
		xp = xp * Globals.DIMENSION_W;
		yp = yp * Globals.DIMENSION_H;

		xp += Globals.DIMENSION_W / 2;
		yp += Globals.DIMENSION_H / 2;
		
		return new Vector3(xp, yp, finalz);
	}
}
