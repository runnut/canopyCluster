package runJob;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import canopyCluster.canopy.CanopyDriver;
import canopyCluster.conversion.InputDriver;
import canopyCluster.distance.DistanceMeasure;
import canopyCluster.distance.SimilarDistanceMeasure;

public class RunTest {

	private static final Logger log = LoggerFactory.getLogger(InputDriver.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            Path input = new Path("testdata2");
            Path output = new Path("output2");
            System.out.println(output.toUri());
            InputDriver.runJob(input, output);
        } catch (Exception var14) {
            log.error("Exception parsing command line: ", var14);
        }
		
		
		Configuration conf=new Configuration();
		Path input=new Path("output2");
		Path output=new Path("canopyout2");
		DistanceMeasure measure=new SimilarDistanceMeasure();
	    Double	t1=4D; Double t2=10D;
	    Double	t3=4D; Double t4=10D;
	    int clusterFilter=2;
	    try {
		CanopyDriver.buildClustersMR(conf, input, output, measure, t1, t2, t3, t4, clusterFilter);
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		

	}

}
