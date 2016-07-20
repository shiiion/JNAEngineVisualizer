public class Vector3 
{
	public float x;
	public float y;
	public float z;
	
	public Vector3()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void Scale(float size)
	{
		x *= size;
		y *= size;
		z *= size;
	}
	
	public float Magnitude()
	{
		float mag = (float) Math.sqrt((x * x) + (y * y) + (z * z));
		return mag;
	}
	
	public void Normalize()
	{
		float mag = Magnitude();
		x /= mag;
		y /= mag;
		z /= mag;
	}
	
	public void Add(Vector3 other)
	{
		x += other.x;
		y += other.y;
		z += other.z;
	}
	
	public void Copy(Vector3 other)
	{
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	public Vector3 mul(float val)
	{
		return new Vector3(x * val, y * val, z * val);
	}
	
	public Vector3 add(Vector3 other)
	{
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	public static Vector3 FromEuler(float yaw, float pitch, float roll, float size)
	{
		float xv = -(float)(Math.sin(yaw) * Math.cos(pitch));
		float zv = (float)(Math.cos(pitch) * Math.cos(yaw));
		float yv = (float)(Math.sin(pitch));
		
		return new Vector3(size * xv, size * yv, size * zv);
	}
	
	public void RotateAbout(Vector3 point, float yaw, float pitch, float roll)
	{
		x -= point.x;
		y -= point.y;
		z -= point.z;
		Math3D.Rotate(this, yaw, pitch, roll);
		x += point.x;
		y += point.y;
		z += point.z;
	}
}
