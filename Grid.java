package squareCells;
import java.awt.Color;

public class Grid
{
	private int m;
	private int n;
	public double rate = 100;
	private Node[][] node;
	
	Grid(int M, int N)
	{
		m = M;
		n = N;
		node = new Node[m][n];
		
		initialize();
	}
	
	public int relaunch(int x, int y, double M, double d, int h, int c, boolean f, boolean a)
	{
		m = x;
		n = y;
		node = new Node[m][n];
		
		initialize(M, d, h, c, f, a);
		return 1;
	}
	
	public void grab()
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j].grab();
			}
		}
	}
	
	public void update()
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j] = mutate(node[i][j], rate);
				node[i][j].update();
			}
		}
	}
	
	public void setMix(double x)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j].setMix(x);
			}
		}
	}
	
	public void setDrain(double x)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j].setDrain(x);
			}
		}
	}
	
	public void setDeathRate(int x)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j].setDeathRate(x);
			}
		}
	}
	
	public void setCoolMax(int x)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j].setCoolMax(x);
			}
		}
	}
	
	public void setFullColor(boolean x)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j].setFullColor(x);
			}
		}
	}
	
	public void setDeathType(boolean x)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j].setDeathType(x);
			}
		}
	}
	
	public void setRate(double x)
	{
		rate = x;
	}
	
	public int getM()
	{
		return m;
	}
	
	public int getN()
	{
		return n;
	}
	
	public Node[][] getNodeGrid()
	{
		return node;
	}
	
	public Color[][] getColorGrid()
	{
		Color[][] colorgrid = new Color[m][n];
		
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				colorgrid[i][j] = node[i][j].getColor();
			}
		}
		
		return colorgrid;
	}
	
	public Node mutate(Node t, double chance)
	{
		int mutate = (int)(Math.random()*((m * n * chance)/30));
		int type = (int)(Math.random()*3);
		int amt = (int)(Math.random()*255);
		
		if(mutate == 0)
		{
			if(type==0)
			{
				t.red = amt;
				t.green = 0;
				t.blue = 0;
			}
			else if(type==1)
			{
				t.red = 0;
				t.green = amt;
				t.blue = 0;
			}
			else if(type==2)
			{
				t.red = 0;
				t.green = 0;
				t.blue = amt;
			}
		}
		
		return t;
	}
	
	public void initialize()
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j] = new Node();
				//node[i][j] = mutate(node[i][j], 4); //INITIAL MUTATIONS
			}
		}
		
		// INITIAL CONTITIONS
		//node[0][0].red = 255;
		//node[m-1][n-1].green = 255;
		//node[m/2][n/2].blue = 255;
		
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				if(i-1 > -1)
				{
					node[i][j].setUp(node[i-1][j]);
				}
				if(i+1 < m)
				{
					node[i][j].setDown(node[i+1][j]);
				}
				if(j-1 > -1)
				{
					node[i][j].setLeft(node[i][j-1]);
				}
				if(j+1 < n)
				{
					node[i][j].setRight(node[i][j+1]);
				}
			}
		}
	}
	
	public void initialize(double M, double d, int h, int c, boolean f, boolean a)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				node[i][j] = new Node(M, d, h, c, f, a);
			}
		}
		
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				if(i-1 > -1)
				{
					node[i][j].setUp(node[i-1][j]);
				}
				if(i+1 < m)
				{
					node[i][j].setDown(node[i+1][j]);
				}
				if(j-1 > -1)
				{
					node[i][j].setLeft(node[i][j-1]);
				}
				if(j+1 < n)
				{
					node[i][j].setRight(node[i][j+1]);
				}
			}
		}
	}
}