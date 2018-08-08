package canopyCluster.canopy;

import java.util.ArrayList;
/*
 * canopy类
 * 中心点，size，id不知道加不加
 */
public class Canopy {
	private int id;
	private String[] center;
	private int centerLen;
	private int s1=0;
	private int s2=0;
	//列车车辆数
	private int carSum;
	//车辆车号不正确数
	private int errSum;
	//空车数
	private int emptySum;
	  public Canopy() {
	    }

	  
	    public Canopy(String[]  center,int id) {
	       this.center=center;
	       this.id=id;
	       centerLen=center.length;
	    }

	    public void asertS1() {
	    	s1++;
	    }
	    public void asertS2() {
	    	s2++;
	    }
	    
	    public int getS1() {
			return s1;
		}


		public void setS1(int s1) {
			this.s1 = s1;
		}


		public int getS2() {
			return s2;
		}


		public void setS2(int s2) {
			this.s2 = s2;
		}


		public String[]  getCenter() {
			return center;
		}


		public void setCenter(String[]  center) {
			this.center = center;
		}


	    public int getCenterLen() {
			return centerLen;
		}


		public void setCenterLen(int centerLen) {
			this.centerLen = centerLen;
		}


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public int getCarSum() {
			return carSum;
		}


		public void setCarSum(int carSum) {
			this.carSum = carSum;
		}


		public int getErrSum() {
			return errSum;
		}


		public void setErrSum(int errSum) {
			this.errSum = errSum;
		}


		public int getEmptySum() {
			return emptySum;
		}


		public void setEmptySum(int emptySum) {
			this.emptySum = emptySum;
		}

		
		
		

	   
}
