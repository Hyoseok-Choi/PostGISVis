package hs.choi.spring.project;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DAOGeometry {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		// dataSource�� ������ JdbcTemplate�� ����
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// return List<ResultQuery>
	public List<ResultQuery> selectGeometry(String mysql) {

		String sql = mysql;

		// RowMapper�� ResultSet�� ���ڵ�� ��ü�� ����
		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				ResultGeometry geo = new ResultGeometry();

				geo.setThe_Geom(rs.getString("the_geom"));

				return geo;

			}
		
		};
		// return guList;
		return jdbcTemplate.query(sql, mapper);
	}

}
