package canopyCluster.canopy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import canopyCluster.canopy.util.CanopyCheckUtil;
import canopyCluster.distance.DistanceMeasure;
import canopyCluster.util.StringUtil;


public class CanopyClusterer {
	private static final Logger log = LoggerFactory.getLogger(CanopyClusterer.class);
    private int nextCanopyId;
    private double t1;
    private double t2;
    private double t3;
    private double t4;
    private DistanceMeasure measure;
    //map和Reduce共用一个距离测量
    public CanopyClusterer(DistanceMeasure measure, double t1, double t2) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t1;
        this.t4 = t2;
        this.measure = measure;
    }
    //map用t1/t2 reduce t3/t4
    public CanopyClusterer(DistanceMeasure measure, double t1, double t2,double t3, double t4) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.measure = measure;
    }

    public double getT1() {
        return this.t1;
    }

    public double getT2() {
        return this.t2;
    }

    public double getT3() {
        return this.t3;
    }

    public double getT4() {
        return this.t4;
    }

    public void useT3T4() {
        this.t1 = this.t3;
        this.t2 = this.t4;
    }
    //增加点，到canopy中，主要方法
    public void addPointToCanopies(String[] point, Collection<Canopy> canopies) {
        boolean pointStronglyBound = false;

        double dist;
        for(Iterator i$ = canopies.iterator(); i$.hasNext(); pointStronglyBound = pointStronglyBound || dist < this.t2) {
            Canopy canopy = (Canopy)i$.next();
            dist = this.measure.distance(canopy.getCenter(), point);
            if (dist < this.t1) {
            	  //System.out.println("distanceS1___"+dist);
                if (log.isDebugEnabled()) {
                   // log.debug("Added point: {} to canopy: {}", AbstractCluster.formatVector(point, (String[])null), canopy.getIdentifier());
                }
                canopy.asertS1();
                if(dist<this.t2) {
                	 // System.out.println("distanceS2___"+dist);
                	canopy.asertS2();
                	 System.out.println("newCanopyCenter__"+StringUtil.strsTstr(point));
                	 System.out.println("pointCenter___"+StringUtil.strsTstr(canopy.getCenter()));
                }
            }
        }

        if (!pointStronglyBound) {
            if (log.isDebugEnabled()) {
              //log.debug("Created new Canopy:{} at center:{}", this.nextCanopyId, AbstractCluster.formatVector(point, (String[])null));
            }
           if(CanopyCheckUtil.checkPoint(point)) {
            canopies.add(new Canopy(point, this.nextCanopyId++));
           }
        }

    }

    public boolean canopyCovers(Canopy canopy, String[] point) {
        return this.measure.distance( canopy.getCenter(), point) < this.t1;
    }

    /*public static List<Canopy> createCanopies(List<Vector> points, DistanceMeasure measure, double t1, double t2) {
        List<Canopy> canopies = Lists.newArrayList();
        int var7 = 0;

        while(!points.isEmpty()) {
            Iterator<Vector> ptIter = points.iterator();
            Vector p1 = (Vector)ptIter.next();
            ptIter.remove();
            Canopy canopy = new Canopy(p1, var7++, measure);
            canopies.add(canopy);

            while(ptIter.hasNext()) {
                Vector p2 = (Vector)ptIter.next();
                double dist = measure.distance(p1, p2);
                if (dist < t1) {
                    canopy.observe(p2);
                }

                if (dist < t2) {
                    ptIter.remove();
                }
            }

            Iterator i$ = canopies.iterator();

            while(i$.hasNext()) {
                Canopy c = (Canopy)i$.next();
                c.computeParameters();
            }
        }

        return canopies;
    }*/

    public static ArrayList<String[]> getCenters(Iterable<Canopy> canopies) {
        ArrayList<String[]> result = Lists.newArrayList();
        Iterator i$ = canopies.iterator();

        while(i$.hasNext()) {
            Canopy canopy = (Canopy)i$.next();
            result.add(canopy.getCenter());
        }

        return result;
    }

    /*public static void updateCentroids(Iterable<Canopy> canopies) {
        Iterator i$ = canopies.iterator();

        while(i$.hasNext()) {
            Canopy canopy = (Canopy)i$.next();
            canopy.computeParameters();
        }

    }*/

    public void setT3(double t3) {
        this.t3 = t3;
    }

    public void setT4(double t4) {
        this.t4 = t4;
    }

}
