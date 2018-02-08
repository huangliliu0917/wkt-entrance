package com.wkt.entrance.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmj
 * @since 2018-02-07
 */
@TableName("persistent_logins")
public class Persistent_logins extends Model<Persistent_logins> {

    private static final long serialVersionUID = 1L;

	private String username;
	private String series;
	private String token;
	private Date last_used;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLast_used() {
		return last_used;
	}

	public void setLast_used(Date last_used) {
		this.last_used = last_used;
	}

	@Override
	protected Serializable pkVal() {
		return this.series;
	}

	@Override
	public String toString() {
		return "Persistent_logins{" +
			", username=" + username +
			", series=" + series +
			", token=" + token +
			", last_used=" + last_used +
			"}";
	}
}
