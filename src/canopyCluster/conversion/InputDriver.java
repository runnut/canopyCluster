package canopyCluster.conversion;

import org.slf4j.LoggerFactory;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.slf4j.Logger;
import org.apache.hadoop.io.*;

public class InputDriver {
	private static final Logger log = LoggerFactory.getLogger(InputDriver.class);

    private InputDriver() {
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
    
        try {
            Path input = new Path("testdata2");
            Path output = new Path("output2");
            System.out.println(output.toUri());
            runJob(input, output);
        } catch (Exception var14) {
            log.error("Exception parsing command line: ", var14);
        }

    }

    public static void runJob(Path input, Path output) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Input Driver running over input: " + input);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(TextArrayWritable.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setMapperClass(InputMapper.class);
        job.setNumReduceTasks(0);
        job.setJarByClass(InputDriver.class);
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);
        boolean succeeded = job.waitForCompletion(true);
        if (!succeeded) {
            throw new IllegalStateException("Job failed!");
        }else {
        	System.out.println("done");
        }
    }

}
