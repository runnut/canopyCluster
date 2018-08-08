package canopyCluster.canopy;

import java.io.IOException;
import java.util.Collection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.common.collect.Lists;

import canopyCluster.conversion.TextArrayWritable;
import canopyCluster.distance.SimilarDistanceMeasure;

public class CanopyShuffleMapper extends Mapper<WritableComparable<?>, TextArrayWritable, Text, TextArrayWritable> {
	
	private final Collection<Canopy> canopies = Lists.newArrayList();
	private CanopyClusterer canopyClusterer;
	private int clusterFilter;
	private int mapPointCount=0;
	CanopyShuffleMapper(){
		
	}
	
	protected void map(WritableComparable<?> key, TextArrayWritable text, Mapper<WritableComparable<?>, TextArrayWritable, Text, TextArrayWritable>.Context context) throws IOException, InterruptedException  {
		//System.out.println("mapValue__"+text.toStrings());
	//System.out.println(text.toString()+"judge__");
		/*this.canopyClusterer.addPointToCanopies(text.toStrings(), this.canopies);
		mapPointCount++;*/
		String[] trains=text.toStrings();
		int trainLen=trains.length;
		if(trainLen>14) {
		context.write(new Text(trainLen+""),text);
		}
	}
	
	protected void setup(Mapper<WritableComparable<?>, TextArrayWritable, Text, TextArrayWritable>.Context context) throws IOException, InterruptedException{
		System.out.println("setup_begin");
		super.setup(context);
		Configuration conf=context.getConfiguration();
		double t1 = Double.parseDouble(conf.get("org.apache.mahout.clustering.canopy.t1"));
	    double t2 = Double.parseDouble(conf.get("org.apache.mahout.clustering.canopy.t2"));
		this.canopyClusterer=new CanopyClusterer(new SimilarDistanceMeasure(),t1,t2);
        this.clusterFilter = Integer.parseInt(context.getConfiguration().get("org.apache.mahout.clustering.canopy.canopyFilter"));
        System.out.println("setup_end");
		
	}
	
	/*protected void cleanup(Mapper<WritableComparable<?>, TextArrayWritable, Text, TextArrayWritable>.Context context)throws IOException, InterruptedException  {
		Iterator i$ = this.canopies.iterator();
			int count=0;
        while(i$.hasNext()) {
        	count++;
            Canopy canopy = (Canopy)i$.next();
            System.out.println("s1_____"+canopy.getS1());
            System.out.println("s2_____"+canopy.getS2());
            //先不进行处理
                context.write(new Text("centroid"),new TextArrayWritable(canopy.getCenter()));
               // context.write(new Text("centroid22"), new Text(canopy.getCenter().toString()));
                //System.out.println(canopy.getCenter().toString()+"_________center_____clean");
        }
        System.out.println("map center count is ____"+count+"///map point count is____"+mapPointCount);

        super.cleanup(context);
	}*/
	

}
