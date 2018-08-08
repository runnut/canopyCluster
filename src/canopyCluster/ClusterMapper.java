package canopyCluster;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import canopyCluster.conversion.TextArrayWritable;

public class ClusterMapper extends Mapper<LongWritable, Text, Text, TextArrayWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
	
	}

}
