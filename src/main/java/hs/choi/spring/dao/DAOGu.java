package hs.choi.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DAOGu implements DAOSeoul {
	
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
		
	// return List<Seoul>
		public List<Seoul> selectAll() {

		String sql = "select gid, ea002_nam, the_geom from gunew;";
	
		// RowMapper는 ResultSet의 레코드와 객체를 매핑
		RowMapper mapper = new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
				
				SeoulGu gu = new SeoulGu();
				
				gu.setGid(rs.getInt("gid"));
				gu.setEa002_nam(rs.getString("ea002_nam"));
				gu.setThe_geom(rs.getString("the_geom"));
				
				return gu;
				
			}
		};
		//		return guList;
		return jdbcTemplate.query(sql, mapper);
	}

}
