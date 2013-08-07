package hs.choi.spring.dao;

public class SeoulRoad implements Seoul{
	
	protected int gid;
	protected String rd_nm;
	protected double shape_len;
	protected String the_geom;
	
	public int getGid() {
		return gid;
	}
	
	public void setGid(int gid) {
		this.gid = gid;
	}
	
	public String getThe_geom() {
		return the_geom;
	}

	public void setThe_geom(String the_geom) {
		this.the_geom = the_geom;
	}
	
	
	
	
	public String getRd_nm() {
		return rd_nm;
	}
	
	public void setRd_nm(String rd_nm) {
		this.rd_nm = rd_nm;
	}
	
	public double getShape_len() {
		return shape_len;
	}
	
	public void setShape_len(double shape_len) {
		this.shape_len = shape_len;
	}



}
