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
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.StringUtils;

import canopyCluster.canopy.Canopy;
import canopyCluster.canopy.CanopyClusterer;
import canopyCluster.conversion.ListTextArrayWritable;
import canopyCluster.conversion.TextArrayWritable;
import canopyCluster.distance.SimilarDistanceMeasure;

public class ClusterClassificationReducery extends Reducer<Text, TextArrayWritable,Text, ListTextArrayWritable>  {

	private List<String[]> clusterModels;
	private double t1;
	private double t2;
	private Map<String,ArrayList<String[]>> resmap=new HashMap<String,ArrayList<String[]>>();
	
	public ClusterClassificationReducery() {
		
	}
	 @Override
	    protected void reduce(Text arg0, Iterable<TextArrayWritable> values, Reducer<Text, TextArrayWritable, Text, ListTextArrayWritable>.Context context) throws IOException, InterruptedException {
		 //在canopyout文件中读取该lenth的中心点
		//得到聚类中心点数据
		 	Configuration conf=context.getConfiguration();
			Path clusterInput=new Path("canopyout\\clusters-0-final");
			FileSystem fs=clusterInput.getFileSystem(conf);
			clusterModels=new ArrayList();
			if(fs.exists(clusterInput)) {
				FileStatus[] status=fs.listStatus(clusterInput);
				for(FileStatus file:status) {
					
					 if (!file.getPath().getName().startsWith("part-r")) {  
			                continue;  
			            }  
					 FSDataInputStream inputStream=fs.open(file.getPath());
						BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
						String count;
						while((count=br.readLine())!=null) {
							 String[] strs=count.split("	");
							 if(strs[0].trim().equals(arg0));
					          System.out.println(strs.length);
					          clusterModels.add(strs[1].split(","));
						}
				}
			
			}else {
				throw new NullPointerException("clusterModels not found!!!");
			}
		 //将每个长度相等的点分别于clusterModels进行match
		 TextArrayWritable[] writables=new TextArrayWritable[20];
		 int i=0;
		 Iterator i$ = values.iterator();
	        while(i$.hasNext()) {
	        	TextArrayWritable value = (TextArrayWritable)i$.next();
	        	if (!this.clusterModels.isEmpty()) {
	                SimilarDistanceMeasure smdist=new SimilarDistanceMeasure();
	                Iterator itor=this.clusterModels.iterator();
	                for(int j=0;j<clusterModels.size();j++) {
	             	   double dist=smdist.distance(value.toStrings(), clusterModels.get(j));
	             	   if(dist<t2) {
	             		   /*if(resmap.get("i")==null) {
	             			   List<String[]> lst=new ArrayList<String[]>();
	             			   lst.add(value.toStrings());
	             		   }else {
	             		   resmap.get("i").add(value.toStrings());
	             		   }*/
	             		writables[i]=value;
	      	        	i++;
	             	   }
	                }         
	             }
	        	
	        	
	        	
	        }

	        ListTextArrayWritable listArray=new ListTextArrayWritable(writables);  
	       context.write(arg0, listArray);
	    }

	    protected void setup(Reducer<Text, TextArrayWritable, Text, ListTextArrayWritable>.Context context) throws IOException, InterruptedException {
	    	super.setup(context);	
		}
}
