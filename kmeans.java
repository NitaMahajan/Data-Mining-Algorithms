/* Implementation of kmeans algorithm on a 2-dimensional dataset forming 2 clusters */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class kmeans {

	static ArrayList<Point> points;
	public static void main(String args[]) throws Exception{
		
		/* Loading data from a csv file */
		BufferedReader br = new BufferedReader(new FileReader("path for csv file"));
		String line = null;
		Scanner sc = null;
		
		
		/* Initialize the points hashset */
		points = new ArrayList<>();
		
		
		/* Reading from csv file making points
		 * and adding in hashset */
		while((line=br.readLine())!=null){
			
			line = line.trim();
			String d[]=line.split(",");
			Point p = null;
			try{
				p = new Point(Double.parseDouble(d[0]),Double.parseDouble(d[1]));
				points.add(p);
			}catch(NumberFormatException e){
				
			}
		}
		System.out.println("Points length: "+points.size());
    
		/* Calling kmeans method with clusters=2*/
		kmeans(2);
    
	}
	static void kmeans(int number_of_clusters){
		
		//Initialize the centroid of the two clusters
		
		Cluster c1 = new Cluster();
		c1.updateCentroid(points.get(0).x,points.get(0).y);
		
		Cluster c2 = new Cluster();
		c2.updateCentroid(points.get(points.size()-1).x,points.get(points.size()-1).y);
		
		//Traverse each point and find euclidean distance
		boolean updatec1 = true;
		boolean updatec2 = true;
		int iterations = 0;
		while(updatec1==true && updatec2==true){
			iterations++;
			for(Point p:points){
				
				//Find euclidean distance from two centroids
				
				double dis1;
				double dis2;
				
				dis1 = Math.pow((Math.pow((p.x - c1.getX()),2) + Math.pow((p.y - c1.getY()),2)),0.5); 
				
				dis2 = Math.pow((Math.pow((p.x - c2.getX()),2) + Math.pow((p.y - c2.getY()),2)),0.5);
				
				if(dis1 < dis2){
					//enter p into cluster 1 and remove from cluster 1 if present
					c1.addPoint(p);
					c2.removePoint(p);
					
				}else{
					c2.addPoint(p);
					c1.removePoint(p);
				}
			}
			//update centroid
			updatec1 = c1.calculateAndUpdateCentroid();
			updatec2 = c2.calculateAndUpdateCentroid();
			
		}
		
		System.out.println("NUMBER OF ITERATIONS : "+iterations+"\n");
		System.out.println("CLUSTER 1 ");
		c1.print();
		System.out.println("\n");
		System.out.println("CLUSTER 2 ");
		c2.print();
		
	}
}

class Point{
	double x;
	double y;
	Point(double x, double y){
		this.x = x;
		this.y = y;
	}
}

class Cluster{
	double x_centroid;
	double y_centroid;
	HashSet<Point> pointsInCluster;
	Cluster(){
		pointsInCluster = new HashSet<>();
	}
	boolean calculateAndUpdateCentroid(){
		double xavg = 0;
		double yavg = 0;
		for(Point p:pointsInCluster){
			xavg+=p.x;
			yavg+=p.y;
		}
		xavg = xavg/pointsInCluster.size();
		yavg = yavg/pointsInCluster.size();
		boolean did = updateCentroid(xavg,yavg);
		return did;
	}
	boolean updateCentroid(double x, double y){
		if(Math.abs(x-x_centroid) > 0.1 || Math.abs(y-y_centroid) > 0.5){
			x_centroid = x;
			y_centroid = y;
			return true;
		}
		return false;
		
	}
	void addPoint(Point point){
		pointsInCluster.add(point);
	}
	void removePoint(Point point){
		pointsInCluster.remove(point);
	}
	double getX(){
		return x_centroid;
	}
	double getY(){
		return y_centroid;
	}
	public void print(){
		System.out.println("--- CLUSTER STARTS --- \nX centroid = "+x_centroid+"   Y centroid = "+y_centroid);
		System.out.println("--- Points in this cluster : "+pointsInCluster.size()+" --- ");
		for(Point p:pointsInCluster){
			System.out.println("X: "+p.x+" Y: "+p.y);
		}
		System.out.println("--- CLUSTER ENDS ---");
	}
}
