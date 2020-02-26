package squareCells;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

public class Blitz extends JPanel
{
	// m,n The dimensions of the grid of colors
	int m, n, size, WIDTH, HEIGHT, speed;
	Color[][] colors;
	
	Blitz(int M, int N, int S)
	{
		m = M;
		n = N;
		size = S;
		WIDTH = size*m + 14;
		HEIGHT = size*n + 34;
		colors = new Color[m][n];
		speed = 100;
	}
	
	public void setSpeed(int x)
	{
		speed = x;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		for(int i = 0; i < m; i++)
        {
			for(int j = 0; j < n; j++)
	        {
				g.setColor(colors[i][j]);
				g.fillRect(i*size, j*size, size, size);
				//g.fillOval(i*size, j*size, size, size);
	        }
        }
	}
	
	public static void main(String[] args) throws InterruptedException, IOException
	{
		int w = 1920/50, h = 1080/50, s = 600;
		Blitz game = new Blitz(w, h, s/w);
		Grid grid = new Grid(game.m, game.n);
		int mix = 166;
		int drain = 200;
		int cooldown = 40, deathRate = 20;
		
		
		
		// --------------------------------------------------------------------------	Create Frame
		JFrame frame = new JFrame("RGB's Game Of Life");
		
		ImageIcon img = new ImageIcon("C:\\Users\\hp\\Pictures\\Screenshots\\ogcbm2.png");
		frame.setIconImage(img.getImage());
		
		frame.setBackground(Color.BLACK);
		frame.getContentPane().add(game);
		frame.setSize(game.WIDTH, game.HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // --------------------------------------------------------------------------	Create Modulator Frame
        JFrame mod = new JFrame("Master Modulator");
        JPanel display = new JPanel(new GridBagLayout());
        JPanel topDisplay = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // --------------------------------------------------------------------------	Relaunch Settings
        JTextField xAmt = new JTextField(4); xAmt.setText(w + "");
        xAmt.setToolTipText("#cells horizontal");
        
        JTextField yAmt = new JTextField(4); yAmt.setText(h + "");
        yAmt.setToolTipText("#cells vertical");
        
        JTextField worldSize = new JTextField(4); worldSize.setText(s + "");
        worldSize.setToolTipText("width of window screen");
        
        JButton restart = new JButton("Relaunch");
        restart.setToolTipText("Restart Program");
        
        JLabel xLabel = new JLabel("x amt");
        JLabel yLabel = new JLabel("y amt");
        JLabel sizeLabel = new JLabel("width");
        
        // --------------------------------------------------------------------------	Labels and Tool Tips
        float fontSize = 15.0f;
        
        JLabel mixLabel = new JLabel("mix: " + (double)mix/100);	mixLabel.setFont (mixLabel.getFont().deriveFont (fontSize));
        JLabel drainLabel = new JLabel("drain: " + (double)drain/100);	drainLabel.setFont (drainLabel.getFont().deriveFont (fontSize));
        JLabel deathLabel = new JLabel("hurt: " + deathRate);	deathLabel.setFont (deathLabel.getFont().deriveFont (fontSize));
        JLabel coolLabel = new JLabel("revive: " + cooldown);	coolLabel.setFont (coolLabel.getFont().deriveFont (fontSize));
        JLabel speedLabel = new JLabel("speed: " + game.speed);	speedLabel.setFont (speedLabel.getFont().deriveFont (fontSize));
        JLabel rateLabel = new JLabel("spawn rate: " + (int)grid.rate);	rateLabel.setFont (rateLabel.getFont().deriveFont (fontSize));
        
        mixLabel.setToolTipText("Mix is how strongly cells are affected by cells of differing colors.");
        drainLabel.setToolTipText("Drain is how strongly cells are affected by neighboring empty cells.");
        deathLabel.setToolTipText("Hurt is how strongly cells are damaged once they get old and begin to die");
        coolLabel.setToolTipText("Revive is how quickly a dead cell comes back to life");
        speedLabel.setToolTipText("Change the speed of the simulation. The smaller number, the faster the speed");
        rateLabel.setToolTipText("Change the rate at which new cells are randomly born. The smaller number, the faster the speed");
        
        // --------------------------------------------------------------------------	Sliders
        JSlider mixMod = new JSlider(JSlider.HORIZONTAL,-1000, 1000, mix);
        JSlider drainMod = new JSlider(JSlider.HORIZONTAL,-1000, 1000, drain);
        JSlider deathMod = new JSlider(JSlider.HORIZONTAL,0, 255, 20);
        JSlider coolMod = new JSlider(JSlider.HORIZONTAL,0, 1000, 40);
        JSlider speedMod = new JSlider(JSlider.HORIZONTAL,0, 2000, 100);
        JSlider rateMod = new JSlider(JSlider.HORIZONTAL,0, 2000, 100);
        
        // Labels on Sliders
        Hashtable<Integer, JLabel> labels_01 = new Hashtable<>();
        labels_01.put(1000, new JLabel("Dense "));
        labels_01.put(-1000, new JLabel("Sparse"));
        mixMod.setLabelTable(labels_01);
        mixMod.setPaintLabels(true);
        mixMod.setMinorTickSpacing(100);
        mixMod.setMajorTickSpacing(500);
        mixMod.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> labels_02 = new Hashtable<>();
        labels_02.put(1000, new JLabel("Loose "));
        labels_02.put(-1000, new JLabel("Stikcy"));
        drainMod.setLabelTable(labels_02);
        drainMod.setPaintLabels(true);
        drainMod.setMinorTickSpacing(100);
        drainMod.setMajorTickSpacing(500);
        drainMod.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> labels_03 = new Hashtable<>();
        labels_03.put(255, new JLabel("No Gain "));
        labels_03.put(0, new JLabel("No Pain"));
        deathMod.setLabelTable(labels_03);
        deathMod.setPaintLabels(true);
        deathMod.setMinorTickSpacing(12);
        deathMod.setMajorTickSpacing(60);
        deathMod.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> labels_04 = new Hashtable<>();
        labels_04.put(0, new JLabel("Instant"));
        labels_04.put(1000, new JLabel("None"));
        coolMod.setLabelTable(labels_04);
        coolMod.setPaintLabels(true);
        coolMod.setMinorTickSpacing(50);
        coolMod.setMajorTickSpacing(250);
        coolMod.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> labels_05 = new Hashtable<>();
        labels_05.put(0, new JLabel("Fast"));
        labels_05.put(2000, new JLabel("Slow"));
        speedMod.setLabelTable(labels_05);
        speedMod.setPaintLabels(true);
        speedMod.setMinorTickSpacing(100);
        speedMod.setMajorTickSpacing(500);
        speedMod.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> labels_06 = new Hashtable<>();
        labels_06.put(0, new JLabel("High"));
        labels_06.put(2000, new JLabel("Low"));
        rateMod.setLabelTable(labels_06);
        rateMod.setPaintLabels(true);
        rateMod.setMinorTickSpacing(100);
        rateMod.setMajorTickSpacing(500);
        rateMod.setPaintTicks(true);
        
        // Edit Slider Length (probably useless cuz of gridbag constraints.HORIZONTAL)
        int extend = 40;
        Dimension d = mixMod.getPreferredSize();
        mixMod.setPreferredSize(new Dimension(d.width+extend,d.height));
        d = drainMod.getPreferredSize();
        drainMod.setPreferredSize(new Dimension(d.width+extend,d.height));
        d = deathMod.getPreferredSize();
        deathMod.setPreferredSize(new Dimension(d.width+extend,d.height));
        d = coolMod.getPreferredSize();
        coolMod.setPreferredSize(new Dimension(d.width+extend,d.height));
        d = speedMod.getPreferredSize();
        speedMod.setPreferredSize(new Dimension(d.width+extend,d.height));
        d = rateMod.getPreferredSize();
        rateMod.setPreferredSize(new Dimension(d.width+extend,d.height));
        
        // --------------------------------------------------------------------------	Check Box
        JCheckBox fullcolor = new JCheckBox("Full Color");
        JCheckBox altDeath = new JCheckBox("alt");
        
        fullcolor.setToolTipText("RGB only or Full Range of Color");
        altDeath.setToolTipText("Alternate between 2 growing algorithms");
        
        // --------------------------------------------------------------------------	Layout Constraints
        
        // ----------- LINE 1 ----------- // Input Titles
        c.insets = new Insets(0, 20, 0, 10);
        c.gridx = 0; c.gridy = 0; display.add(xLabel, c);
        c.gridx = 1; c.gridy = 0; display.add(yLabel, c);
        c.gridx = 2; c.gridy = 0; display.add(sizeLabel, c);
        
        // ----------- LINE 2 ----------- // Input Boxes & Button
        c.insets = new Insets(0, 20, 0, 10);
        c.gridx = 0; c.gridy = 1; display.add(xAmt, c);
        c.gridx = 1; c.gridy = 1; display.add(yAmt, c);
        
        c.insets = new Insets(0, 20, 0, 0);
        c.gridx = 2; c.gridy = 1; display.add(worldSize, c);
        
        c.insets = new Insets(0, 10, 0, 50);
        c.gridx = 3; c.gridy = 1; display.add(restart, c);
        
        // ----------- LINE 3 ----------- // Mix Modulator
        c.insets = new Insets(31, 20, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 2; c.gridwidth = 3; display.add(mixMod, c);
        c.insets = new Insets(0, 20, 0, 0);
        c.gridx = 3; c.gridy = 2; c.gridwidth = 1; display.add(mixLabel, c);
        
        // ----------- LINE 4 ----------- // Drain Modulator
        c.insets = new Insets(31, 20, 0, 0);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 3; display.add(drainMod, c);
        c.insets = new Insets(0, 20, 0, 0);
        c.gridx = 3; c.gridy = 3; c.gridwidth = 1; display.add(drainLabel, c);
        
        // ----------- LINE 5 ----------- // Hurt Modulator
        c.insets = new Insets(31, 20, 0, 0);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 3; display.add(deathMod, c);
        c.insets = new Insets(0, 20, 0, 0);
        c.gridx = 3; c.gridy = 4; c.gridwidth = 1; display.add(deathLabel, c);
        
        // ----------- LINE 6 ----------- // Revive Modulator
        c.insets = new Insets(31, 20, 0, 0);
        c.gridx = 0; c.gridy = 5; c.gridwidth = 3; display.add(coolMod, c);
        c.insets = new Insets(0, 20, 0, 0);
        c.gridx = 3; c.gridy = 5; c.gridwidth = 1; display.add(coolLabel, c);
        
        // ----------- LINE 7 ----------- // Speed Modulator
        c.insets = new Insets(31, 20, 0, 0);
        c.gridx = 0; c.gridy = 6; c.gridwidth = 3; display.add(speedMod, c);
        c.insets = new Insets(0, 20, 0, 0);
        c.gridx = 3; c.gridy = 6; c.gridwidth = 1; display.add(speedLabel, c);
        
        // ----------- LINE 8 ----------- // Rate Modulator
        c.insets = new Insets(31, 20, 0, 0);
        c.gridx = 0; c.gridy = 7; c.gridwidth = 3; display.add(rateMod, c);
        c.insets = new Insets(0, 20, 0, 0);
        c.gridx = 3; c.gridy = 7; c.gridwidth = 1; display.add(rateLabel, c);
        
        // ----------- LINE 9 ----------- // Check Boxes
        c.insets = new Insets(31, 50, 20, 0);
        c.gridx = 1; c.gridy = 8; display.add(fullcolor, c);
        c.gridx = 2; c.gridy = 8; display.add(altDeath, c);//*/
        
        // --------------------------------------------------------------------------	Add to mod
        mod.add(topDisplay);
        mod.add(display);
        mod.pack();
        mod.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mod.setVisible(true);
        
        // --------------------------------------------------------------------------	Event Handlers
        mixMod.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
        	  double x = (double)mixMod.getValue() / 100;
        	  mixLabel.setText("mix: " + x);
        	  grid.setMix(x);
          }
        });
        
        drainMod.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
        	  double x = (double)drainMod.getValue() / 100;
        	  drainLabel.setText("drain: " + x);
        	  grid.setDrain(x);
          }
        });
        
        deathMod.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
        	  int x = deathMod.getValue();
        	  deathLabel.setText("hurt: " + x);
        	  grid.setDeathRate(x);
          }
        });
        
        coolMod.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
        	  int x = coolMod.getValue();
        	  coolLabel.setText("revive: " + x);
        	  grid.setCoolMax(x);
          }
        });
        
        speedMod.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
        	  game.setSpeed(speedMod.getValue());
        	  speedLabel.setText("speed: " + speedMod.getValue());
          }
        });
        
        rateMod.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
        	  grid.setRate(rateMod.getValue());
        	  rateLabel.setText("spawn rate: " + rateMod.getValue());
          }
        });
        
        fullcolor.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
        	  boolean x = fullcolor.isSelected();
        	  grid.setFullColor(x);
          }
        });
        
        altDeath.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
        	  boolean x = altDeath.isSelected();
        	  grid.setDeathType(x);
          }
        });
        
        restart.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  int x, y, s;
        	  int tempSpeed = game.speed;
        	  
        	  // get current values for relaunch
        	  try
        	  {
        		  x = Integer.parseInt(xAmt.getText());
        	  }
        	  catch(Exception z )
        	  {
        		  x = game.m;
        	  }
        	  
        	  try
        	  {
        		  y = Integer.parseInt(yAmt.getText());
        	  }
        	  catch(Exception z )
        	  {
        		  y = game.n;
        	  }
        	  
        	  try
        	  {
        		  s = Integer.parseInt(worldSize.getText());
        	  }
        	  catch(Exception z )
        	  {
        		  s = game.size*x;
        	  }
        	  
        	// pause game - breaks otherwise
        	  game.speed = 2000;
        	  try {
  				Thread.sleep((x*y)/700);
  			} catch (InterruptedException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
        	  
        	  s /= x;
        	  game.WIDTH = s*x + 14;
        	  game.HEIGHT = s*y + 34;
        	  game.colors = new Color[x][y];
        	  
        	  game.m = x;
        	  game.n = y;
        	  game.size = s;
        	  
        	  // return int to ensure relaunch completes before continuing
        	  //(don't know if thats how it works)
        	  int finished = grid.relaunch(x, y, (double)mixMod.getValue() / 100,
        			  				(double)drainMod.getValue() / 100,
        			  				deathMod.getValue(),
        			  				deathMod.getValue(),
        			  				fullcolor.isSelected(),
        			  				altDeath.isSelected());
      		
        	  frame.setSize(game.WIDTH, game.HEIGHT);
        	  
        	  grid.grab();
		      grid.update();
		      game.colors = grid.getColorGrid();
		      game.repaint();
        	  game.speed = tempSpeed;
          }
        });
        
        // --------------------------------------------------------------------------	Repaint
        while(true)
        {
        	if(game.speed < 2000)
        	{
		        grid.grab();
		        grid.update();
		        game.colors = grid.getColorGrid();
		        game.repaint();
        	}
        	
        	Thread.sleep(game.speed);
        }
	}
}
