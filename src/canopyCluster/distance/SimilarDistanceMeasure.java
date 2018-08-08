package canopyCluster.distance;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class SimilarDistanceMeasure implements DistanceMeasure {

	//�Ƚ�center��point�����ƶ�
	public double distance(ArrayList<String> center,ArrayList<String> point) {
		double dist=0.0D;
		dist+=Math.abs(center.size()-point.size());
		return dist;
	}
	//	���ƶȾ������
	// point��center�����ȱȽ�
	@Override
	public double distance(String[] center, String[] point) {
		double dist=0.0D;
		int len1=center.length;
		int len2=point.length;
		if(len1>8&&len2>8) {
				
			/*T1:500 T2:100
		        * train_no ������Ҷ���Ϊ�� dis=0 else dis=200
		        * ����ʱ��һ����֮�ڵ�dis<100
		        * stn_name��ͬ��dis=0
		        * in_out�Ժ���ȥ�ж�
		        * source��ͬdis=0 ,source��ͬdis=50
		        * car_no��ͬ��Ϊ0 dis=0,car_no��ͬdis=100
		        * */
		        //���յľ���finalDistance
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
		        //�жϱ������Ƿ���ͬ
		        if(center[0].trim().equals(point[0].trim())&&!center[0].trim().equals("")){
		            return 0L;
		        }
		        //����train_no�Ƿ����
		        /*double td=center.get(train_no);
		        double td1=vector1.get(train_no);
		        if(td!=0&&td1!=0&&td!=td1){
		            fd=fd+200;
		        }*/
		        //Ӧ����ӳ��Ÿ�ʽ��ȷ�Ե��ж�
		        if(Math.abs(len1-len2)>3) {
		        return	10D;
		        }
		        int minvs=Math.min(len1,len2);
		        int dismatchCount=0;
		        int matchCount=0;
		        //���ϸ�ĳ���
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
			System.out.println("distance:"+center.length+"--or--"+point.length+"lengthС��8");
			System.out.println("<<<__________incorrect point______>>>>");*/
		}
		return 0.0D;
	}
	
}
