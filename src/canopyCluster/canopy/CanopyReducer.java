package canopyCluster.canopy;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.StringUtils;

import com.google.common.collect.Lists;

import canopyCluster.conversion.TextArrayWritable;
import canopyCluster.distance.SimilarDistanceMeasure;

public class CanopyReducer extends Reducer<Text, TextArrayWritable,Text, Text>  {
	 private final Collection<Canopy> canopies = Lists.newArrayList();
	    private CanopyClusterer canopyClusterer;
	    private int clusterFilter;

	    public CanopyReducer() {
	    }

	    CanopyClusterer getCanopyClusterer() {
	        return this.canopyClusterer;
	    }
	    @Override
	    protected void reduce(Text arg0, Iterable<TextArrayWritable> values, Reducer<Text, TextArrayWritable, Text, Text>.Context context) throws IOException, InterruptedException {
	        Iterator i$ = values.iterator();
	        while(i$.hasNext()) {
	        	TextArrayWritable value = (TextArrayWritable)i$.next();
	            String[] point = value.toStrings();
	           // System.out.println("reducepoint___"+point.toString());
	            this.canopyClusterer.addPointToCanopies(point, this.canopies);
	        }

	        i$ = this.canopies.iterator();
	       // System.out.println("__________________"+this.canopies.size());
	        while(i$.hasNext()) {
	        	//System.out.println("canopyhas___++++");
	            Canopy canopy = (Canopy)i$.next();
	            //if(canopy.getS1()>0) {
	            	//System.out.println(canopy.getS1()+"___"+canopy.getS2());
	            String centerStr=StringUtils.join(",",canopy.getCenter());
	            //System.out.println(String.valueOf(canopy.getId())+"___"+centerStr);
	                context.write(new Text(arg0),new Text(centerStr));
	            //}
	            }
	        

	    }

	    protected void setup(Reducer<Text, TextArrayWritable, Text, Text>.Context context) throws IOException, InterruptedException {
	    	super.setup(context);	
	    	System.out.println("reduce_____setup_______________");
			Configuration conf=context.getConfiguration();
			double t1 = Double.parseDouble(conf.get("org.apache.mahout.clustering.canopy.t1"));
		    double t2 = Double.parseDouble(conf.get("org.apache.mahout.clustering.canopy.t2"));
			this.canopyClusterer=new CanopyClusterer(new SimilarDistanceMeasure(),t1,t2);
	        this.clusterFilter = Integer.parseInt(context.getConfiguration().get("org.apache.mahout.clustering.canopy.canopyFilter"));
		}
}
