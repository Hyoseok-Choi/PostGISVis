package hs.choi.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DAORoad implements DAOSeoul {
	
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

		String sql = "select gid, rd_nm, shape_len, the_geom from roadsnew;";
	
		// RowMapper는 ResultSet의 레코드와 객체를 매핑
		RowMapper mapper = new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
				
				SeoulRoad road = new SeoulRoad();
				
				road.setGid(rs.getInt("gid"));
				road.setRd_nm(rs.getString("rd_nm"));
				road.setShape_len(rs.getDouble("shape_len"));
				road.setThe_geom(rs.getString("the_geom"));
				
				return road;
				
			}
		};
		//		return List;
		return jdbcTemplate.query(sql, mapper);
	}

}
