/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senior.research.pkg1;
import java.util.*;
import java.awt.*;

public class ShortestPath
{
	private static ArrayList<Dancer> form1 = new ArrayList<>();
	private static ArrayList<Dancer> form2 = new ArrayList<>();
	private static StageMap stage = new StageMap();
	private static ArrayList<Path> paths;
	private static int rad = 1;

	//for testing:
	// public static void main(String[] args)
	// {
	// 	//test
	// 	form1.add(new Dancer(new Point(0, 0), "A"));
	// 	form1.add(new Dancer(new Point(10, 0), "B"));
	// 	form2.add(new Dancer(new Point(0, 10), "B")); 
	// 	form2.add(new Dancer(new Point(10, 10), "A"));
		
	// 	form2 = matchOrder();
	// 	System.out.println(form1);
	// 	System.out.println(form2);
		
	// 	findPaths();
	// 	System.out.println(paths);
	// }

	public ShortestPath(ArrayList<Dancer> f1, ArrayList<Dancer> f2)
	{
		form1 = f1;
		form2 = f2;
		form2 = matchOrder();
	}

	public static ArrayList<Dancer> matchOrder()
	{
		Dancer[] temp = new Dancer[form2.size()];
		for(int i=0; i<form2.size(); i++)
			temp[i] = form2.get(i);
		form2.clear();
		for(int i=0; i<form1.size(); i++)
		{
			String d = form1.get(i).getName();
			for(int j=0; j<temp.length; j++)
			{
				if(temp[j].getName().equals(d))
				{
					form2.add(temp[j]);
					break;
				}
			}
		}
                //System.out.println(form2);
		return form2;
	}

	public static void checkForConflicts(ArrayList<Path> paths)
	{
		//for each point in the first path, check each point at that index in all the other paths. if they match, addConstraint
		if(paths.size() < 1)
                    return;
                //System.out.println(paths);
                for (int index=0; index<paths.get(0).getLength(); index++) 
		{
			//for each path
                        for(int path1=0; path1<paths.size(); path1++)
			{
				if(paths.get(path1).getLength() > index)
				{
					Point s1 = paths.get(path1).getStep(index).getPoint();
					//for each other path; check if points at the same index match
					for(int path2=path1+1; path2<paths.size(); path2++)
					{
						if(paths.get(path2).getLength() > index)
						{
							Point s2 = paths.get(path2).getStep(index).getPoint();
							if(isConflict(s1, s2, rad))
							//if(s1.x == s2.x && s1.y == s2.y)
							{
								Conflict c = new Conflict(path1, path2, s1, index);
								addConstraint(paths.get(path1), paths.get(path2), c);
								//return;
							}
						}
					}
				}
			}
		}
	}

	public static boolean isConflict(Point s1, Point s2, int radius)
	{
		double maxX = s1.x + radius*Math.cos(0);
		double minX = s1.x + radius*Math.cos(Math.PI);
		double maxY = s1.y + radius*Math.sin(Math.PI/2);
		double minY = s1.y + radius*Math.sin(3*Math.PI/2);
		return (s2.x <= maxX && s2.x >= minX &&
				s2.y <= maxY && s2.y >= minY);
	}

	public static void addConstraint(Path p1, Path p2, Conflict con)
	{	
                System.out.println("conflict: " + con);
		AStarPathFinder pathFinder = new AStarPathFinder(stage, 34, true, con); //500 is arbitrary
		Dancer d1 = new Dancer(p1.getStart(), "d1");
		Dancer d2 = new Dancer(p2.getStart(), "d2");
		
		Path p1new = p1;
		//Path p1new = pathFinder.findPath(d1, p1.getStart().x, p1.getStart().y, p1.getEnd().x, p1.getEnd().y);
		Path p2new = pathFinder.findPath(d2, p2.getStart().x, p2.getStart().y, p2.getEnd().x, p2.getEnd().y);
		paths.set(con.getDancerIndex1(), p1new);
		paths.set(con.getDancerIndex2(), p2new);
                
                //System.out.println(paths);
	}

	public static void findPaths()
	{
		paths = new ArrayList<Path>();
		AStarPathFinder pathFinder = new AStarPathFinder(stage, 34, true);

                
		for(int i=0; i<Math.min(form1.size(), form2.size()); i++)
		{
			Dancer d1 = form1.get(i);
			Dancer d2 = form2.get(i);
			Point p1 = d1.getExit().getStart();
			Point p2 = d2.getEnter().getStart();

			Path p = pathFinder.findPath(d1, p1.x, p1.y, p2.x, p2.y);
			d1.setExit(p);
			d2.setEnter(p);
                        System.out.println("path: " + p);
			paths.add(p);
		}
                //System.exit(0);
		checkForConflicts(paths);
	}
	public static ArrayList<Path> getPaths()
	{
		return paths;
	}

}
