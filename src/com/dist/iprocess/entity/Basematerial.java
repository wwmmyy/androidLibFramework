package com.dist.iprocess.entity;
 
/**
 * 此部分表为会议系统模块
 * @author 王明远
 *
 */ 
public class Basematerial implements java.io.Serializable { 
	private static final long serialVersionUID = -8219149048538349220L;
	private String id;
	private String meetingid;
	private String msize;
	private String murl;
	private String name; 
	private String filetype;//文件的存储名称

	// Constructors

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	/** default constructor */
	public Basematerial() {
	}

	/** minimal constructor */
	public Basematerial(String meetingid) {
		this.meetingid = meetingid;
	}

	/** full constructor */
	public Basematerial(String meetingid, String msize, String murl, String name) {
		this.meetingid = meetingid;
		this.msize = msize;
		this.murl = murl;
		this.name = name;
	}
 
	 
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
	public String getMeetingid() {
		return this.meetingid;
	}

	public void setMeetingid(String meetingid) {
		this.meetingid = meetingid;
	}
 
	public String getMsize() {
		return this.msize;
	}

	public void setMsize(String msize) {
		this.msize = msize;
	}
 
	public String getMurl() {
		return this.murl;
	}

	public void setMurl(String murl) {
		this.murl = murl;
	}
 
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}