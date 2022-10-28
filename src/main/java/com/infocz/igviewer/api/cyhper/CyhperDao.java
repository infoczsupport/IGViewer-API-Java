package com.infocz.igviewer.api.cyhper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class CyhperDao {
	@Autowired private JdbcTemplate jdbcTemplate; 

	public SqlRowSet executeForRowSet(String sql) {
		return jdbcTemplate.queryForRowSet(sql);     
	}

}