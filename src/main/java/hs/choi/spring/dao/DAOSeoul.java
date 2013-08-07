package hs.choi.spring.dao;

import java.util.List;

import javax.sql.DataSource;

public interface DAOSeoul {
	
	public DataSource getDataSource();
	
	public void setDataSource(DataSource dataSource);
	
	public List<Seoul> selectAll();
}
