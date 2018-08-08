package org.apache.hadoop.examples;
import java.io.IOException;
import java.util.StringTokenizer;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
 
public class WordFind {
 
  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
	 
     
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
       
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	System.out.println("mapbegin__");
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, one);      }
    }
  }
   
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();
 
    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
    	System.out.println("reduce__begin");
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }
 
  public static void main(String[] args) throws Exception {
	  System.err.println("xxxxxxx_____");
	  System.out.println("sdfsadffffffffffffffffffdf");
    Configuration conf = new Configuration();
    //conf.set("mapred.job.tracker", "192.168.1.240:9001");
    String[] ars=new String[]{"testdata","output"};
   // String[] otherArgs = new GenericOptionsParser(conf, ars).getRemainingArgs();
    System.out.println(ars.length);
    if (ars.length != 2) {
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
    job.setInputFormatClass(TextInputFormat.class);
    FileInputFormat.addInputPath(job, new Path(ars[0]));
    FileOutputFormat.setOutputPath(job, new Path(ars[1]));
    System.out.println(ars[0]);
    System.out.println(ars[1]);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
