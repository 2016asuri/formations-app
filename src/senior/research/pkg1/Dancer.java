/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senior.research.pkg1;

import javax.swing.*;
import java.awt.*;

public class Dancer implements Mover
{
   private int side; //front, back, left, right
   private String[] sides = {"front", "back", "left", "right"};
   private Path enterPath; 
   private Path exitPath;
   private Color color;
   private String name;
   private Point pos;
   private int size = 30;

   private int index = 0;
   
   public Dancer(String n, int s)
   {
       side = s;
//       enterPath = new Path(p);
//       exitPath = new Path(p);
       color = new Color(0, 0, 0);
       name = n;
       pos = new Point(-1, -1); 
       enterPath = new Path(pos);
       exitPath = new Path(pos);
   }
   
   public Dancer(Point p, String n) //default - Dancer isn't created until the user gives it a position
   {
      side = 0; 
      enterPath = new Path(p);
      exitPath = new Path(p);
      color = new Color(0, 0, 0);
      name = n;
      pos = p;
   }
   
   public Dancer(int s, Point p, String n) //image of each "sided" dancer on the bar
   {
      side = s;
      enterPath = new Path(p);
      exitPath = new Path(p);
      color = new Color(0, 0, 0);
      name = n;
      pos = p; 
   }
   
   public int getSide()
   {
      return side;
   }
   
   public String getSideString()
   {
       return sides[side];
   }
   
   public Path getEnter()
   {
      return enterPath;
   }
   
   public Path getExit()
   {
      return exitPath;
   }

   public Point getPos()
   {
      return enterPath.getEnd();
   }
   
   public Color getColor()
   {
      return color;
   }
   
   public String getName()
   {
      return name;
   }
   
   public Image getImage()
   {
      if(side == 0)
         return (new ImageIcon("front1.png")).getImage();
      if(side == 1)
         return (new ImageIcon("back1.png")).getImage();
      if(side == 2)
         return (new ImageIcon("left1.png")).getImage();
      return (new ImageIcon("right1.png")).getImage();
   }
   
   public int getSize()
   {
      return size;
   }
   
   public void setSide(int s)
   {
      side = s;
   }
   
   public void setPos(Point p)
   {
       enterPath = new Path(p);
       exitPath = new Path(p);
       pos = p;
   }
   
   public void setEnter(Path p)
   {
      enterPath = p;  
   }
   
   public void setExit(Path p)
   {
      exitPath = p;
   }
   
   public void setColor(Color c)
   {
      color = c;
   }
   
   public void setName(String s)
   {
      name = s;
   }

   public boolean addConstraint(Point p, int i, boolean b) //can or cannot be at point p at index i in path
   {
      //if((exitPath.getPoints()[i] == p && b == false) || (exitPath.getPoints()[i] != p && b == true))
      if((exitPath.getSteps().get(i).getPoint() == p && b == false) || 
         (exitPath.getSteps().get(i).getPoint() != p && b == true))
         return false;
      return true;
   }
   
   public String toString()
   {
      return name + " (" + getPos().getX() + ", " + getPos().getY() + ") " + sides[side];
   }

   
   public boolean intersects(Dancer d)
   {
      return(getPos().getX()-d.getPos().getX() <= size &&
             getPos().getY()-d.getPos().getY() <= size);
   }

   public void move()
   {
      //Point p = idk
      index++;
   }
}
