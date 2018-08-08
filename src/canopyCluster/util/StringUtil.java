package canopyCluster.util;

public class StringUtil {

	//将字符串数组转化成字符串
	public static String strsTstr(String[] strs) {
		String str="";
		for(int i=0;i<strs.length;i++) {
			str=str+strs[i];
		}
		return str;
	}
	//判断字符串是否符合车号规则
	/*public static boolean checkCarNum(String carNum) {
		if(carNum.length()==7) {
			
		}
	}*/
}
