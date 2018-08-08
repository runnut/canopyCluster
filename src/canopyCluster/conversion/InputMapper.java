package canopyCluster.conversion;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import jdk.jfr.events.ExceptionThrownEvent;

public class InputMapper extends Mapper<Object,Text,Text,TextArrayWritable> {
	 private static final Pattern SPACE = Pattern.compile(",");

	    public InputMapper() {
	    }

	    protected void map(Object key, Text values, Mapper<Object, Text, Text, TextArrayWritable>.Context context) throws IOException, InterruptedException {
	        String[] strs = SPACE.split(values.toString());
	        int len=strs.length;
        
	        if (len>8) {
	            try {
	                TextArrayWritable textArrayWritable = new TextArrayWritable(strs);
	                context.write(new Text(String.valueOf(len)), textArrayWritable);
	                System.out.println(len+"_____"+strs);
	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
	        }

	    }

	    protected void setup(Mapper<Object, Text, Text, TextArrayWritable>.Context context) throws IOException, InterruptedException {
	        super.setup(context);
	        Configuration conf = context.getConfiguration();

	    }
	
}
