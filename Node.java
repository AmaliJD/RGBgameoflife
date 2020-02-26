package squareCells;
import java.awt.Color;

public class Node
{
	public int red, green, blue, cr, cg, cb, colormode;
	public int cooldown, coolMax, deathRate;
	
	private Node up, down, left, right;
	private Color color;
	
	public boolean dying, full, cCheck, altDeath;
	public double mix, drain;
	
	public Node()
	{
		red = 0;
		green = 0;
		blue = 0;
		colormode = 0;
		
		color = Color.BLACK;
		dying = false;
		coolMax = 40;
		cooldown = coolMax;
		deathRate = 20;
		
		mix = 1.66;
		drain = 2;
		full = false;
		altDeath = false;
		
		up = null;
		down = null;
		left = null;
		right = null;
	}
	
	public Node(double m, double d, int h, int c, boolean f, boolean a)
	{
		red = 0;
		green = 0;
		blue = 0;
		colormode = 0;
		
		color = Color.BLACK;
		dying = false;
		coolMax = c;
		cooldown = coolMax;
		deathRate = h;
		
		mix = m;
		drain = d;
		full = f;
		altDeath = a;
		
		up = null;
		down = null;
		left = null;
		right = null;
	}
	
	public void setUp(Node n)
	{
		up = n;
	}
	
	public void setDown(Node n)
	{
		down = n;
	}
	
	public void setLeft(Node n)
	{
		left = n;
	}
	
	public void setRight(Node n)
	{
		right = n;
	}
	
	public Node getUp()
	{
		return up;
	}
	
	public Node getDown()
	{
		return down;
	}
	
	public Node getLeft()
	{
		return left;
	}
	
	public Node getRight()
	{
		return right;
	}
	
	public boolean isDying()
	{
		boolean maybe = false;
		
		if((colormode == 1 && red == 255))
		{
			maybe = true;
		}
		else if((colormode == 2 && green == 255))
		{
			maybe = true;
		}
		if((colormode == 3 && blue == 255))
		{
			maybe = true;
		}
		else if(colormode == 0)
		{
			maybe = false;
		}
		
		if(maybe)
		{
			if((up!=null && up.colormode != colormode && up.colormode != 0) ||
				(down!=null && down.colormode != colormode && down.colormode != 0) ||
				(left!=null && left.colormode != colormode && left.colormode != 0) ||
				(right!=null && right.colormode != colormode && right.colormode != 0))
			{
				dying = false;
			}
			else
			{
				dying = true;
			}
		}
		else
		{
			cooldown--;
			if(coolMax == 1000)
			{
				cooldown++;
			}
			if(cooldown <= 0)
			{
				dying = false;
				cooldown=coolMax;
			}
		}
		
		return dying;
	}
	
	// function to add a portion of a neighbors color
	public int adjust(double i)
	{
		return (int)((i/4) * Math.random());
	}
	
	// Pull info about neighbor node
	public void pull(Node n)
	{
		// Default			1.66	2
		// Slow Wall Hugger .021	-4
		// Good Wall Hugger .1		-4
		// Mixed			4		1
		// Emerger			.03		1
		
		// Chose not to modulate these
		double leak = .2, tolerance = 20;
		
		if(n.colormode==1)
		{
			if(colormode != 1) {cr += adjust(mix*n.red);}
			else{cr += adjust(n.red);}
			
			cg -= adjust(n.red);
			
			cb -= adjust(n.red);
		}
		else if(n.colormode==2)
		{
			cr -= adjust(n.green);
			
			if(colormode != 2) {cg += adjust(mix*n.green);}
			else{cg += adjust(n.green);}
			
			cb -= adjust(n.green);
		}
		else if(n.colormode==3)
		{
			cr -= adjust(n.blue);
			
			cg -= adjust(n.blue);
			
			if(colormode != 3) {cb += adjust(mix*n.blue);}
			else{cb += adjust(n.blue);}
		}
		else if(n.colormode==6) // ==6 skipped this option
		{
			if(n.red > tolerance){cr += adjust(leak*n.red);}else{cr -= adjust(drain*red);}
			if(n.green > tolerance){cg += adjust(leak*n.green);}else{cg -= adjust(drain*green);}
			if(n.blue > tolerance){cb += adjust(leak*n.blue);}else{cb -= adjust(drain*blue);}
		}
		else if(n.colormode==0)
		{
			cr -= adjust(drain*red);
			cg -= adjust(drain*green);
			cb -= adjust(drain*blue);
		}
	}
	
	public void grab()
	{
		if(altDeath)
		{
			grab1();
		}
		else
		{
			grab0();
		}
	}
	
	// get neighbors rgb values and store in c
	public void grab1()
	{
		double d = deathRate;
		cr = 0;
		cg = 0;
		cb = 0;
		
		if(isDying())
		{
			cr -= d;
			cg -= d;
			cb -= d;
		}
		else
		{
			if(up!=null)
			{
				pull(up);
			}
			
			if(down!=null)
			{
				pull(down);
			}
			
			if(left!=null)
			{
				pull(left);
			}
			
			if(right!=null)
			{
				pull(right);
			}
		}
	}
	
	// get neighbors rgb values and store in c
	public void grab0()
	{
		double d = deathRate;
		cr = 0;
		cg = 0;
		cb = 0;
		
		if(up!=null)
		{
			if(isDying())
			{
				cr -= d;
				cg -= d;
				cb -= d;
			}
			else
			{
				pull(up);
			}
		}
		
		if(down!=null)
		{
			if(isDying())
			{
				cr -= d;
				cg -= d;
				cb -= d;
			}
			else
			{
				pull(down);
			}
		}
		
		if(left!=null)
		{
			if(isDying())
			{
				cr -= d;
				cg -= d;
				cb -= d;
			}
			else
			{
				pull(left);
			}
		}
		
		if(right!=null)
		{
			if(isDying())
			{
				cr -= d;
				cg -= d;
				cb -= d;
			}
			else
			{
				pull(right);
			}
		}
	}
	
	// update rgb with c
	public void update()
	{
		red += cr;
		green += cg;
		blue += cb;
		
		checkColor();
		setColor();
	}
	
	// determine the overall color of the node
	public void setColor()
	{
		if(red>green && red>blue){colormode = 1; color = new Color(red, 0, 0);}
		else if(green>red && green>blue){colormode = 2; color = new Color(0, green, 0);}
		else if(blue>green && blue>red){colormode = 3; color = new Color(0, 0, blue);}
		else {colormode = 0; color = new Color(0, 0, 0);}
		
		if(full)
		{
			color = new Color(red, green, blue);
		}
	}
	
	// ensure rgb values within range
	public void checkColor()
	{
		if(red>255) {red=255;}
		if(green>255) {green=255;}
		if(blue>255) {blue=255;}
		
		if(red<=0) {red=0;}
		if(green<=0) {green=0;}
		if(blue<=0) {blue=0;}
	}
	
	public void addRed(int r)
	{
		red += r;
		if(red>255) {red=255;}
		if(red<0) {red=0;}
	}
	
	public void addGreen(int g)
	{
		green += g;
		if(green>255) {green=255;}
		if(green<0) {green=0;}
	}
	
	public void addBlue(int b)
	{
		blue += b;
		if(blue>255) {blue=255;}
		if(blue<0) {blue=0;}
	}
	
	public int getRed()
	{
		return red;
	}
	
	public int getGreen()
	{
		return green;
	}
	
	public int getBlue()
	{
		return blue;
	}
	
	public int getColorMode()
	{
		return colormode;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setMix(double x)
	{
		mix = x;
	}
	
	public void setDrain(double x)
	{
		drain = x;
	}
	
	public void setDeathRate(int x)
	{
		deathRate = x;
	}
	
	public void setCoolMax(int x)
	{
		coolMax = x;
	}
	
	public void setFullColor(boolean x)
	{
		full = x;
	}
	
	public void setDeathType(boolean x)
	{
		altDeath = x;
	}
}