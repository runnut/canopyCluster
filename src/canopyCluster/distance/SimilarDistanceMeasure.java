package canopyCluster.distance;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class SimilarDistanceMeasure implements DistanceMeasure {

	//比较center和point的相似度
	public double distance(ArrayList<String> center,ArrayList<String> point) {
		double dist=0.0D;
		dist+=Math.abs(center.size()-point.size());
		return dist;
	}
	//	相似度距离测量
	// point和center完整度比较
	@Override
	public double distance(String[] center, String[] point) {
		double dist=0.0D;
		int len1=center.length;
		int len2=point.length;
		if(len1>8&&len2>8) {
				
			/*T1:500 T2:100
		        * train_no 不相等且都不为空 dis=0 else dis=200
		        * 到达时间一个月之内的dis<100
		        * stn_name相同的dis=0
		        * in_out以后再去判断
		        * source相同dis=0 ,source不同dis=50
		        * car_no相同或为0 dis=0,car_no不同dis=100
		        * */
		        //最终的距离finalDistance
		        double fd=0L;
		        if (len1<14||len2<14){
		            return 1000D;
		        }

		        /*int rpt_name=0;
		        int train_no=1;
		        int arr_time=2;
		        int stn_name=3;
		        int source=4;
		        int aei_no=5;
		        int in_out=6;*/
		        //判断报文名是否相同
		        if(center[0].trim().equals(point[0].trim())&&!center[0].trim().equals("")){
		            return 0L;
		        }
		        //测试train_no是否相近
		        /*double td=center.get(train_no);
		        double td1=vector1.get(train_no);
		        if(td!=0&&td1!=0&&td!=td1){
		            fd=fd+200;
		        }*/
		        //应该添加车号格式正确性的判断
		        if(Math.abs(len1-len2)>3) {
		        return	10D;
		        }
		        int minvs=Math.min(len1,len2);
		        int dismatchCount=0;
		        int matchCount=0;
		        //不合格的车号
		        int disformat=0;
		        for (int i=8;i<minvs;i++){
		           String carNo1=center[i].trim();
		           String carNo2=point[i].trim();
		           if(carNo1.length()==7&&carNo2.length()==7) {
		           if(!carNo1.equals(carNo2)) {
		        	   dismatchCount++;
		        	   if (dismatchCount>10) {
		        		   return 10D;
		        	   }
		           }else {
		        	   matchCount++;
		           }
		           }else {
		        	   disformat++;
		           }
		        }
		        if(matchCount<10) {
		        	return 10D;
		        }
		        return dismatchCount ;
		}else {
			System.out.println("__________incorrect point______");
			/*System.out.println(StringUtils.join(center));
			System.out.println(StringUtils.join(point));
			System.out.println("distance:"+center.length+"--or--"+point.length+"length小于8");
			System.out.println("<<<__________incorrect point______>>>>");*/
		}
		return 0.0D;
	}
	
}
