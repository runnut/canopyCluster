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

public class AeiTrainFormat {
	
	/*
	 * 从zxlc的oracle数据库中取出数据输出成列模型进行比较
	 */
	public static String WriteLine(File file1,File file2) throws Exception{
		InputStream inr=new FileInputStream(file1);
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(inr)); 
		//创建输出流
		BufferedWriter bufWriter= createIO(file2);
		String str=bufReader.readLine();
		String train_serial="";
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
			
			if(!train_serial.equals(strs[0])) {
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
				/*"TRAIN_SERIAL","AEI_CODE","TRAIN_ID","DIRECTION","ARRIVE_TIME","VEHICLE_NUMBER","CAR_SEQ","CAR_ID","ADD_TIME"
				"2018050338942117","TWY","H    84019","1","2018/5/2 5:55:38","51","34","1737397","2018/5/3 14:23:28"*/
				lst=new ArrayList<String>();
				train_serial=strs[0];
				lst.add(strs[0]);//train_serial
				lst.add(strs[1]);//aei_code
				lst.add(strs[2]);//train_id
				lst.add(strs[3]);//direction
				lst.add(strs[4]);//arrive_time
				lst.add(strs[5]);//vehicle_number
				lst.add(strs[9]);//arr_date
				lst.add(strs[10]);//arr_time
				lst.add(/*strs[6]+"_"+*/strs[7]);//car_no
				if(strs[7].trim().equals("")) {
					emCars++;
					
				}
			}else {
				
				lst.add(/*strs[6]+"_"+*/strs[7]);
				
				carcount++;
				if(strs[7].trim().equals("")) {
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
