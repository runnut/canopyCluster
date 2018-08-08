package org.jw.hczz.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.hadoop.util.StringUtils;
import org.junit.Test;

import canopyCluster.canopy.util.CanopyCheckUtil;

public class ForFormTest extends Object {

	//@Test
	public void test() {
		/*Collection sss=new ArrayList<String>() ;
		sss.add("111");
		sss.add("22");
		sss.add("333");
		
		int y=1;
		for(Iterator $i=sss.iterator();$i.hasNext();System.out.println($i.next())) {
			
			System.out.println(y++);
		}
		Integer a=new Integer(1);*/
		//int[] ints= {1,2,3,4};
		
		//数组转化为字符串
		/*String[] strs= {"123","serwe"};
		System.out.println(StringUtils.join(",", strs));*/
		double a=1.0D;
		a++;a++;
		System.out.println(++a);
	}
	@Test
	public void test2() {
		String[] point= {"HXZB1024.32120180","","XZB", "H"," 1", null," 2018-03-21"," 07:16:33", "8108809", "8108809","8108809","8108809","8108809","8108809","8108809", "8108809","8108809","8108809","8108809","8108809","8108809"};
		System.out.println(CanopyCheckUtil.checkPoint(point));
		
	}

}
