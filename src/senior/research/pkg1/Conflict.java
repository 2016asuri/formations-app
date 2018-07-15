/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senior.research.pkg1;

import java.awt.Point;
import java.util.ArrayList;

public class Conflict
{
   private Point place;
   private Dancer dancer1;
   private Dancer dancer2;
   private int index; //index of path where the two dancers intersect - i.e. time of conflict
   private int di1;
   private int di2; 


   // public Conflict(Dancer dance1, Dancer dance2, Point p, int i)
   // {
   //    dancer1 = dance1;
   //    dancer2 = dance2;
   //    index = i;
   //    place = p;
   // }

   public Conflict(int d1, int d2, Point p, int i)
   {
      index = i;
      place = p;
      di1 = d1;
      di2 = d2;
   }
   //not sure if i need this
   public Conflict(int d1, int d2, Point p, int i, ArrayList<Dancer> form)
   {
      dancer1 = form.get(d1);
      dancer2 = form.get(d2);
      place = p;
      index = i;
      di1 = d1;
      di2 = d2;
   }

   public Point getPlace()
   {
      return place;
   }

   public int getIndex()
   {
      return index;
   }

   public int getDancerIndex1()
   {
      return di1;
   }

   public int getDancerIndex2()
   {
      return di2;
   }
}
