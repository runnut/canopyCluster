package canopyCluster.canopy;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import canopyCluster.conversion.TextArrayWritable;
import canopyCluster.distance.DistanceMeasure;
import canopyCluster.distance.SimilarDistanceMeasure;

public class CanopyDriver {
	
	public static void main(String[] args) {
		Configuration conf=new Configuration();
		Path input=new Path("output2");
		Path output=new Path("canopyout9");
		DistanceMeasure measure=new SimilarDistanceMeasure();
	    Double	t1=4D; Double t2=6D;
	    Double	t3=4D; Double t4=6D;
	    int clusterFilter=2;
	    try {
		buildClustersMR(conf, input, output, measure, t1, t2, t3, t4, clusterFilter);
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}

	public static Path buildClustersMR(Configuration conf, Path input, Path output, DistanceMeasure measure, double t1, double t2, double t3, double t4, int clusterFilter) throws IOException, InterruptedException, ClassNotFoundException {
        conf.set("org.apache.mahout.clustering.canopy.measure", measure.getClass().getName());
        conf.set("org.apache.mahout.clustering.canopy.t1", String.valueOf(t1));
        conf.set("org.apache.mahout.clustering.canopy.t2", String.valueOf(t2));
        conf.set("org.apache.mahout.clustering.canopy.t3", String.valueOf(t3));
        conf.set("org.apache.mahout.clustering.canopy.t4", String.valueOf(t4));
        conf.set("org.apache.mahout.clustering.canopy.canopyFilter", String.valueOf(clusterFilter));
        //conf.set("mapred.min.split.size","30MB");
        Job job = new Job(conf, "Canopy Driver running buildClusters over input: " + input);
        job.setInputFormatClass(SequenceFileInputFormat.class);
        //job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setMapperClass(CanopyShuffleMapper.class);
        job.setReducerClass(CanopyReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TextArrayWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setNumReduceTasks(10);
        job.setJarByClass(CanopyDriver.class);
        //设置map的分片数控制map个数
       // FileInputFormat.setMaxInputSplitSize(job, 31457280);
        FileInputFormat.addInputPath(job, input);
        Path canopyOutputDir = new Path(output, "clusters-0-final");
        FileOutputFormat.setOutputPath(job, canopyOutputDir);
        if (!job.waitForCompletion(true)) {
            throw new InterruptedException("Canopy Job failed processing " + input);
        } else {
        	System.out.println("Job Finished");
            return canopyOutputDir;
        }
    }
}
