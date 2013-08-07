package hs.choi.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DAODong implements DAOSeoul {
	
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

		String sql = "select gid, ea004_nam, the_geom from dongnew;";
	
		// RowMapper는 ResultSet의 레코드와 객체를 매핑
		RowMapper mapper = new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
				
				SeoulDong dong = new SeoulDong();
				
				dong.setGid(rs.getInt("gid"));
				dong.setEa004_nam(rs.getString("ea004_nam"));
				dong.setThe_geom(rs.getString("the_geom"));
				
				return dong;
				
			}
		};
		//		return List;
		return jdbcTemplate.query(sql, mapper);
	}

}
