import javax.swing.JFrame;

public class SlopeNode {
		public double slope;
		public Point[] points;
		private Boolean isdrawn;
		private JFrame frame;
		
	    private static void renderLine(JFrame frame, Point p1, Point p2) {
	        p1.lineTo(p2, frame.getGraphics(), frame.getWidth(), frame.getHeight());
	    }
		
		public SlopeNode(double slope, Point origin, Point firstpoint,JFrame frame)   {
			this.slope=slope;
			this.points = new Point[3];
			this.points[0] = origin;
			this.points[1] = firstpoint;
			this.points[2] = null;
			this.frame = frame;
			this.isdrawn=false;
		}
		
		public void add(Point point) {
			if(points[2]==null) points[2] = point;
			else {
				if(isdrawn) {
					renderLine(frame, points[0], point);
				}else {
					renderLine(frame, points[0], point);
					renderLine(frame, points[0], points[1]);
					renderLine(frame, points[0], points[2]);
					isdrawn = true;
				}
			}
		}
		
		
	}