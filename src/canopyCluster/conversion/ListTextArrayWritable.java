package canopyCluster.conversion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableFactories;

public class ListTextArrayWritable implements Writable{
	//声明一个类作为value的类
	private Class<? extends Writable> valueClass =TextArrayWritable.class;
	private Writable[] values;

	/*public ListTextArrayWritable(Class<? extends Writable> valueClass) {
		if(valueClass ==null) {
			throw new IllegalArgumentException("valueClass argument is null");
		}
		this.valueClass=valueClass;
	}*/
	public ListTextArrayWritable(TextArrayWritable[] values) {
		this.valueClass=TextArrayWritable.class;
		this.values=values;
	}
	
	
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		 out.writeInt(values.length);                 // write values
		    for (int i = 0; i < values.length; i++) {
		      values[i].write(out);
		    }
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		 values = new Writable[in.readInt()];          // construct values
		    for (int i = 0; i < values.length; i++) {
		      Writable value = WritableFactories.newInstance(valueClass);
		      value.readFields(in);                       // read a value
		      values[i] = value;                          // store it in values
		    }
	}

	
	
	
}
