package hs.choi.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DAOPoi implements DAOSeoul {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public DataSource getDataSource(){
		
		return dataSource;

	}
	
	public void setDataSource(DataSource dataSource){
		
		this.dataSource = dataSource;
		
		// dataSource를 가지고 JdbcTemplate를 생성
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}
			
		public List<Seoul> selectAll() {

		String sql = "select * from kpoinew;";
	
		// RowMapper는 ResultSet의 레코드와 객체를 매핑
		RowMapper mapper = new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
				
				SeoulPoi poi = new SeoulPoi();
				
				poi.setGid(rs.getInt("gid"));
				
				poi.setPoi_name(rs.getString("poi_name"));
				poi.setRegiontel(rs.getString("regiontel"));
				poi.setTelno(rs.getString("telno"));
				poi.setTelextno(rs.getString("telextno"));
				poi.setZipcode(rs.getString("zipcode"));
				poi.setDo_name(rs.getString("do_name"));
				poi.setGun_name(rs.getString("gun_name"));
				poi.setYub_name(rs.getString("yub_name"));
				poi.setLotno(rs.getString("lotno"));
				poi.setDetailname(rs.getString("detailname"));
				poi.setKindvalueb(rs.getString("kindvalueb"));
				poi.setKindvaluem(rs.getString("kindvaluem"));
				poi.setKindvalues(rs.getString("kindvalues"));
				poi.setName_mo(rs.getString("name_mo"));
				
				poi.setThe_geom(rs.getString("the_geom"));
				
				return poi;
				
			}
		};
		//		return List;
		return jdbcTemplate.query(sql, mapper);
	}

}
