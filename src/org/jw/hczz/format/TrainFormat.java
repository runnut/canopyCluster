package org.jw.hczz.format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TrainFormat {
	
	/*
	 * 
	 */
	public static String WriteLine(File file1,File file2) throws Exception{
		InputStream inr=new FileInputStream(file1);
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(inr)); 
		//创建输出流
		BufferedWriter bufWriter= createIO(file2);
		String str=bufReader.readLine();
		String rpt_name="";
		List<String> lst=null;
		//空车号计算
		int carcount=0;
		int emCars=0;
		
		int count=0;
		while( count<2000000) {
			count++;
			String[] strs=str.split(",");
			/*
			 * 列模型样式：rpt_name,train_no,stn_name,source,aei_no,in_out_flag,arr_date,arr_time,car_no...
			 */
			
			if(!rpt_name.equals(strs[4])) {
				try {
					
				System.out.println(lst.toString());
				if(carcount-emCars>3) {
				bufWriter.write(lst.toString()+"\n");
				}
				carcount=0;
				emCars=0;
				}catch(Exception e) {
					e.getMessage();
				}
				carcount++;
				lst=new ArrayList<String>();
				rpt_name=strs[4];
				lst.add(strs[4]);//rpt_name
				lst.add(strs[7]);//train_no
				lst.add(strs[0]);//stn_name
				lst.add(strs[5]);//source
				lst.add(strs[9]);//aei_no
				lst.add(strs[3]);//in_out
				lst.add(strs[12]);//arr_date
				lst.add(strs[2]);//arr_time
				lst.add(strs[1]);//car_no
				if(strs[1].trim().equals("")) {
					emCars++;
					
				}
			}else {
				lst.add(strs[1]);
				carcount++;
				if(strs[1].trim().equals("")) {
					emCars++;
				}
			}
			str=bufReader.readLine();
		
		}
		bufWriter.close();
		bufReader.close();
		inr.close();
		return null;
	}
	public static BufferedWriter createIO(File file) throws Exception {
		OutputStream outstr=new FileOutputStream(file);
		BufferedWriter bufferWriter=new BufferedWriter(new OutputStreamWriter(outstr));
		return bufferWriter;
	}
}
