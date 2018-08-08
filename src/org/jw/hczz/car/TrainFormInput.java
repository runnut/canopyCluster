package org.jw.hczz.car;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.examples.WordFind;
import org.apache.hadoop.examples.WordFind.IntSumReducer;
import org.apache.hadoop.examples.WordFind.TokenizerMapper;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TrainFormInput {

	
	public static class TokenizerMapper 
    extends Mapper<Object, Text, Text, IntWritable>{
  
		 private final static IntWritable one = new IntWritable(1);
		 private Text word = new Text();
		    
		 public void map(Object key, Text value, Context context
		                 ) throws IOException, InterruptedException {
		   StringTokenizer itr = new StringTokenizer(value.toString());
		   System.out.println(key);
		   while (itr.hasMoreTokens()) {
			   
		     word.set(itr.nextToken());
		    
		     context.write(word, one);      
		     }
		   //System.out.println("------------------jieshu");
		 }
		}
		
		public static class IntSumReducer 
		    extends Reducer<Text,IntWritable,Text,IntWritable> {
		 private IntWritable result = new IntWritable();
		
		 public void reduce(Text key, Iterable<IntWritable> values, 
		                    Context context
		                    ) throws IOException, InterruptedException {
		   int sum = 0;
		   for (IntWritable val : values) {
			   System.out.println(key+":"+val);
		     sum += val.get();
		     System.out.println(sum);
		   }
		   System.out.println(",,,");
		   result.set(sum);
		   context.write(key, result);
		 }
		}
		
		public static void main(String[] args) throws Exception {
		 Configuration conf = new Configuration();
		 conf.set("mapred.job.tracker", "192.168.1.240:9001");
		 String[] ars=new String[]{"testdata2","part-222"};
		 String[] otherArgs = new GenericOptionsParser(conf, ars).getRemainingArgs();
		 if (otherArgs.length != 2) {
		   System.err.println("Usage: wordcount  ");
		   System.exit(2);
		 }
		 Job job = new Job(conf, "word count");
		 job.setJarByClass(WordFind.class);
		 job.setMapperClass(TokenizerMapper.class);
		 job.setCombinerClass(IntSumReducer.class);
		 job.setReducerClass(IntSumReducer.class);
		 job.setOutputKeyClass(Text.class);
		 job.setOutputValueClass(IntWritable.class);
		 FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		 FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		 System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
}
