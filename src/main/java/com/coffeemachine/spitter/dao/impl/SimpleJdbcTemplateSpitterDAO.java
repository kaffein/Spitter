package com.coffeemachine.spitter.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.coffeemachine.spitter.dao.SpitterDAO;
import com.coffeemachine.spitter.domain.Spitter;

public class SimpleJdbcTemplateSpitterDAO implements SpitterDAO {
	private static final String SQL_INSERT_SPITTER = "insert into spitter (username, password, fullname, email, updateByEmail) values (?, ?, ?, ?, ?)";
	private static final String SQL_INSERT_SPITTER_WITH_NAMEDPARAMETER = "insert into spitter (username, password, fullname, email, updateByEmail) values (:username, :password, :fullname, :email, :updateByEmail)";
	private static final String SQL_SELECT_SPITTER_BY_ID = "select id, username, fullname from spitter where id = ?";
	private SimpleJdbcTemplate jdbcTemplate;

	public SimpleJdbcTemplateSpitterDAO() {
	}

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void addSpitter(Spitter spitter) {
		jdbcTemplate.update(SQL_INSERT_SPITTER, spitter.getUsername(),
				spitter.getPassword(), spitter.getFullName(),
				spitter.getEmail(), spitter.isUpdateByEmail());
	}
	
	public void addSpitterWithNamedParameters(Spitter spitter) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", spitter.getUsername());
		params.put("password", spitter.getPassword());
		params.put("fullname", spitter.getFullName());
		params.put("email", spitter.getEmail());
		params.put("updateByEmail", spitter.isUpdateByEmail());
		
		jdbcTemplate.update(SQL_INSERT_SPITTER_WITH_NAMEDPARAMETER, params);
	}

	public Spitter getSpitterById(long id) {
		return jdbcTemplate.queryForObject(SQL_SELECT_SPITTER_BY_ID,
				new RowMapper<Spitter>() {

					public Spitter mapRow(ResultSet rs, int nbRow)
							throws SQLException {
						Spitter spitter = new Spitter();
						spitter.setId(rs.getLong(1));
						spitter.setUsername(rs.getString(2));
						spitter.setFullName(rs.getString(3));

						return spitter;
					}
				}, 1);
	}
}