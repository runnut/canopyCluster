package org.jw.hczz.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

public class HdfsTest {

	//@Test
	public void test()  {
		try {
		Configuration conf=new Configuration();
		Path input=new Path("hdfs://192.168.1.240:8020/user/hdfs/testdata/train_format1");
		Path output=new Path("hdfs://192.168.1.240:8020/user/hdfs/testdata");		
		FileSystem fs=input.getFileSystem(conf);
		FSDataInputStream inputStream=fs.open(input);
		BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
		String count;
		while((count=br.readLine())!=null) {
			 String u = count.toUpperCase();
			 String[] strs=u.split(",");
	          System.out.println(strs.length);
		}
		byte[] b=new byte[1000];
		int bite=inputStream.read(b);
		FileStatus[] fileStatus= fs.listStatus(input);
		System.out.println("done!");
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	@Test
	public void test2()  {
		try {
		Configuration conf=new Configuration();
			
		Path clusterInput=new Path("hdfs://192.168.1.240:8020/user/hdfs/canopyout9/clusters-0-final/");
		FileSystem fs=clusterInput.getFileSystem(conf);
		if(fs.exists(clusterInput)) {
			FileStatus[] status=fs.listStatus(clusterInput);
			for(FileStatus file:status) {
			 List<String[]> clusterModels=new ArrayList();
				 if (!file.getPath().getName().startsWith("part-r")) {  
		                continue;  
		            }  
				 FSDataInputStream inputStream=fs.open(file.getPath());
					BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
					String count;
					while((count=br.readLine())!=null) {
						 String[] strs=count.split("	");
						 if(strs[0].equals(123));
				          System.out.println(strs.length);
				          clusterModels.add(strs[1].split(","));
					}
			}
		
		}else {
			throw new NullPointerException("clusterModels not found!!!");
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
}
