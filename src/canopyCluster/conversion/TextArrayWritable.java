package canopyCluster.conversion;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;



public class TextArrayWritable extends ArrayWritable{
	private String[] strs;
	public TextArrayWritable() {
		super(Text.class);
	}
	 public TextArrayWritable(String[] strings) {
		 super(Text.class);
		 this.strs=strings;
		 Text[] texts = new Text[strings.length];
		 for (int i = 0; i < strings.length; i++) {
		 texts[i] = new Text(strings[i]);
		 }
		 set(texts);
		 }
	 
}
