package canopyCluster.canopy.cluster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

import canopyCluster.conversion.TextArrayWritable;
import canopyCluster.distance.SimilarDistanceMeasure;

public class ClusterClassificationMappery extends Mapper<WritableComparable<?>, TextArrayWritable, Text,TextArrayWritable >{
	private List<String[]> clusterModels;
	private double t1;
	private double t2;
	private Map<String,ArrayList<String[]>> resmap=new HashMap<String,ArrayList<String[]>>();
	public ClusterClassificationMappery() {
		
	}
	
	protected void setup(Mapper<WritableComparable<?>, TextArrayWritable, Text,TextArrayWritable >.Context context) throws IOException, InterruptedException  {
		super.setup(context);
		Configuration conf=context.getConfiguration();
		t1 = Double.parseDouble(conf.get("org.apache.mahout.clustering.canopy.t1"));
	    t2 = Double.parseDouble(conf.get("org.apache.mahout.clustering.canopy.t2"));
		//得到聚类中心点数据
		/*Path clusterInput=new Path("canopyout\\clusters-0-final\\part-r-00001");
		FileSystem fs=clusterInput.getFileSystem(conf);
		if(fs.exists(clusterInput)) {
		FSDataInputStream inputStream=fs.open(clusterInput);
		BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
		String count;
		clusterModels=new ArrayList();
		while((count=br.readLine())!=null) {
			 String[] strs=count.split(",");
	          System.out.println(strs.length);
	          clusterModels.add(strs);
		}
		}else {
			throw new NullPointerException("clusterModels not found!!!");
		}*/
		//get and set ClusterModels
		
	}
	protected void map(WritableComparable<?> key, TextArrayWritable value,Mapper<WritableComparable<?>, TextArrayWritable, Text,TextArrayWritable >.Context context) throws IOException, InterruptedException{
		String[] trains=value.toStrings();
		String len=trains.length+"";
		context.write(new Text(len), value);
		
		/*if (!this.clusterModels.isEmpty()) {
           SimilarDistanceMeasure smdist=new SimilarDistanceMeasure();
           Iterator itor=this.clusterModels.iterator();
           for(int i=0;i<clusterModels.size();i++) {
        	   double dist=smdist.distance(value.toStrings(), clusterModels.get(i));
        	   if(dist<t2) {
        		   context.write(new Text("i"),value );
        		   if(resmap.get("i")==null) {
        			   List<String[]> lst=new ArrayList<String[]>();
        			   lst.add(value.toStrings());
        		   }else {
        		   resmap.get("i").add(value.toStrings());
        		   }
        	   }
           }         
        }*/		
	}	
}
