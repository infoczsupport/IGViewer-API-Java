package com.infocz.igviewer.api.servive.gdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class GdbDao {
	@Qualifier("jdbcTemplateGdb")
	@Autowired private JdbcTemplate jdbcTemplateGdb; 

	public SqlRowSet executeForRowSet(String sql) {
		return jdbcTemplateGdb.queryForRowSet(sql);     
	}

}