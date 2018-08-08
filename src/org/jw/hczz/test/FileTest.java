package org.jw.hczz.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.jw.hczz.format.AeiTrainFormat;
import org.jw.hczz.format.TrainFormat;

public class FileTest {

	//@Test
	public void test() {
		File file1=new File("C:\\Users\\runnut\\Desktop\\res3.csv");
		File file2=new File("C:\\Users\\runnut\\Desktop\\train_format5");
		try {
		TrainFormat.WriteLine(file1,file2);
		System.out.println("prduced done!!!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		File file1=new File("C:\\Users\\runnut\\Desktop\\¾ÛÀà³µºÅ²¹È«MR\\oracleExpres7.csv");
		File file2=new File("C:\\Users\\runnut\\Desktop\\aei_train_format7");
		try {
		AeiTrainFormat.WriteLine(file1,file2);
		System.out.println("prduced done!!!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
