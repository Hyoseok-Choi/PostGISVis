package hs.choi.spring.dao;

public class SeoulDong implements Seoul {
	
	protected int gid;
	protected String the_geom;
	protected String ea004_nam;
		
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
	
	

	public String getEa004_nam() {
		return ea004_nam;
	}

	public void setEa004_nam(String ea002_nam) {
		this.ea004_nam = ea002_nam;
	}
}
