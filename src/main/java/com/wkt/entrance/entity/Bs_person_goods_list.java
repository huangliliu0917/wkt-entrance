package com.wkt.entrance.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户群列表
 * </p>
 *
 * @author zmj
 * @since 2018-02-07
 */
@TableName("bs_person_goods_list")
public class Bs_person_goods_list extends Model<Bs_person_goods_list> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	private String ClientID;
    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 群id
     */
	private String GoodsID;
    /**
     * 状态
     */
	private Integer State;


	public String getClientID() {
		return ClientID;
	}

	public void setClientID(String ClientID) {
		this.ClientID = ClientID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodsID() {
		return GoodsID;
	}

	public void setGoodsID(String GoodsID) {
		this.GoodsID = GoodsID;
	}

	public Integer getState() {
		return State;
	}

	public void setState(Integer State) {
		this.State = State;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Bs_person_goods_list{" +
			", ClientID=" + ClientID +
			", id=" + id +
			", GoodsID=" + GoodsID +
			", State=" + State +
			"}";
	}
}
