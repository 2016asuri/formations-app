package senior.research.pkg1;


import java.awt.Point;
import java.util.ArrayList;

public class Path 
{

	private ArrayList steps = new ArrayList();

	public Path() 
	{
	}

	//no path - stays in one place, e.g. if this is enterPath and the dancer starts in position
	public Path(Point p1) 
	{
		Step s = new Step(p1);
		steps.add(s);
	}
   
	public void addPaths(Path p)
	{
		steps.addAll(p.getSteps());
	}
   
	public Point getStart()
	{
		Step s = (Step)steps.get(0);
		return s.getPoint();
	}

	public Point getEnd()
	{
		Step s = (Step)steps.get(steps.size()-1);
		return s.getPoint();
	}

	public int getLength() {
		return steps.size();
	}

	public Step getStep(int index) {
		return (Step) steps.get(index);
	}

	public int getX(int index) {
		return getStep(index).x;
	}

	public int getY(int index) {
		return getStep(index).y;
	}

	public void appendStep(int x, int y) {
		steps.add(new Step(x,y));
	}

	public void prependStep(int x, int y) {
		steps.add(0, new Step(x, y));
	}

	public boolean contains(int x, int y) {
		return steps.contains(new Step(x,y));
	}

	public ArrayList<Step> getSteps()
	{
		return steps;
	}

	public String toString()
	{
		return "" + getSteps() + "\n";
	}

	/******************************************************************************************************/

	public class Step 
	{

		private int x;
		private int y;

		public Step(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Step(Point p)
		{
			x = p.x;
			y = p.y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int hashCode() {
			return x*y;
		}

		public Point getPoint()
		{
			return new Point(x, y);
		}

		public boolean equals(Object other) 
		{
			if (other instanceof Step) 
			{
				Step o = (Step) other;

				return (o.x == x) && (o.y == y);
			}

			return false;
		}

		public String toString()
		{
			return "(" + x + ", " + y + ") ";
		}
	}
}