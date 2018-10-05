/* Implementation of kmeans algorithm on a 1-dimensional dataset to form 2 clusters */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class kmeans1d {

	static ArrayList<Double> points;

    public static void main(String[] args) throws Exception{

          BufferedReader br = new BufferedReader(new FileReader("path")); //path contains the 1-dimensional dataset
          String line = null;
          Scanner sc = null; 
          points = new ArrayList<>();

          while((line=br.readLine())!=null){
                line = line.trim();
                String d[]=line.split(",");
                for(int i=0;i<d.length;i++) {
                      points.add(Double.parseDouble(d[i]));
                }
          }
          System.out.println("The data is : ");
          System.out.println(points);
          System.out.println("\nPoints length: "+points.size());
          br.close(); 
          kmeans();
    }

    static void kmeans() {

          Cluster1d cluster1 = new Cluster1d();
          cluster1.centroid = points.get(0);

         

          Cluster1d cluster2 = new Cluster1d();
          cluster2.centroid = points.get(2);

          boolean update1 = true;
          boolean update2 = true;
          int iterations = 0;
          while(update1==true && update2==true){
                iterations++;
                for(Double p:points){
                      double dis1,dis2;
                      dis1 = Math.pow((Math.pow((p-cluster1.getCentroid()),2)),0.5);
                    
                      dis2 = Math.pow((Math.pow((p-cluster2.getCentroid()),2)),0.5);
              
                      if(dis1 < dis2){
                            //enter p into cluster 1 and remove from cluster 1 if present
                            cluster1.addPoint(p);
                            cluster2.removePoint(p);
                      }else{
                            cluster2.addPoint(p);
                            cluster1.removePoint(p);
                      }

                }              
                update1 = cluster1.calculateAndUpdateCentroid();
                update2 = cluster2.calculateAndUpdateCentroid();
          }
          System.out.println("NUMBER OF ITERATIONS : "+iterations+"\n");
          System.out.println("CLUSTER 1 ");
          cluster1.print();
          System.out.println("\n");
          System.out.println("CLUSTER 2 ");
          cluster2.print();
    }
}

class Cluster1d{

    double centroid;
    HashSet<Double> pointsInCluster;
    Cluster1d(){
          pointsInCluster = new HashSet<>();
    }

    boolean calculateAndUpdateCentroid(){
          double avg = 0;
          for(Double p:pointsInCluster){
                avg+=p;
          }
          avg = avg/pointsInCluster.size();
          boolean did = updateCentroid(avg);
          return did;
    }

    boolean updateCentroid(double x){
          if(Math.abs(x-centroid) > 0.01){
                centroid = x;
                return true;
          }
          return false; 
    } 

    double getCentroid() {
          return centroid;
    }

    void addPoint(double val){
          pointsInCluster.add(val);
    }

    void removePoint(double val) {
          pointsInCluster.remove(val);
    }

    public void print(){
          System.out.println("------------ CLUSTER STARTS ------------ \nCentroid = "+centroid);
          System.out.println("--- Points in this cluster : "+pointsInCluster.size()+" --- ");
          for(Double p:pointsInCluster){
                System.out.println("P: "+p);
          }
          System.out.println("------------ CLUSTER ENDS ------------");

    }
}
