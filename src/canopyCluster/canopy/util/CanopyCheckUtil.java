package canopyCluster.canopy.util;

public class CanopyCheckUtil {
	public static boolean checkPoint(String[] point) {
		if (point.length<14) {
			return false;
		}
		int k=0;//记录格式正确车的数量
		for(int i=7;i<point.length;i++) {
			if(point[i].trim().length()==7) {
				k++;
			}
		}
		if((float)k/(float)point.length<0.7) {
			return 	false;
		}
		return true;
	}

}
