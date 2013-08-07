package hs.choi.spring.project;


public class ResultGeometry implements ResultQuery {
	
	private String tableName;
	private String the_geom;

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getThe_Geom() {
		return the_geom;
	}
	public void setThe_Geom(String the_geom) {
		this.the_geom = the_geom;
	}

}

