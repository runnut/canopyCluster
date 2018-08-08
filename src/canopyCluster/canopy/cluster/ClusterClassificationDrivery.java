package canopyCluster.canopy.cluster;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


import canopyCluster.conversion.ListTextArrayWritable;
import canopyCluster.conversion.TextArrayWritable;
import canopyCluster.distance.DistanceMeasure;
import canopyCluster.distance.SimilarDistanceMeasure;

public class ClusterClassificationDrivery {
	public static void main(String[] args) {
		run();
	}

	public static void run() {
		Configuration conf=new Configuration();
		Path input=new Path("output2");
		Path output=new Path("cluster");
		DistanceMeasure measure=new SimilarDistanceMeasure();
	    Double	t1=10D; Double t2=4D;
	    Double	t3=10D; Double t4=4D;
	    int clusterFilter=2;
	    try {
	    	clusterClassificationMR(conf, input, output, measure, t1, t2, t3, t4, clusterFilter);
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	public static void clusterClassificationMR(Configuration conf,Path input,Path output,DistanceMeasure measure, double t1, double t2, double t3, double t4, int clusterFilter )throws IOException, InterruptedException, ClassNotFoundException {
		conf.set("org.apache.mahout.clustering.canopy.measure", measure.getClass().getName());
        conf.set("org.apache.mahout.clustering.canopy.t1", String.valueOf(t1));
        conf.set("org.apache.mahout.clustering.canopy.t2", String.valueOf(t2));
        conf.set("org.apache.mahout.clustering.canopy.t3", String.valueOf(t3));
        conf.set("org.apache.mahout.clustering.canopy.t4", String.valueOf(t4));
        conf.set("org.apache.mahout.clustering.canopy.canopyFilter", String.valueOf(clusterFilter));
		Job job=new Job(conf,"Cluster Classification Driver running over input:"+ input);
		job.setJarByClass(ClusterClassificationDrivery.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setMapperClass(ClusterClassificationMappery.class);
		job.setReducerClass(ClusterClassificationReducery.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TextArrayWritable.class);
		FileInputFormat.addInputPath(job, input);
		Path clusterOutputDir = new Path(output, "clusters-0-final");
		FileOutputFormat.setOutputPath(job, clusterOutputDir);
		 if (!job.waitForCompletion(true)) {
	            throw new InterruptedException("Cluster Classification Driver Job failed processing " + input);
	        }
	}
	
}
