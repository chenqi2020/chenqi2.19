package com.chenqi.cms.vo;

import com.chenqi.cms.domain.Complain;

public class ComplainVO extends Complain{
	
	private Integer startNum;
	private Integer endNum;
	private String startTime;
	private String endTime;
	
	private String orderName;//排序名稱
	private String orderMethod;//排序方法、、 asc,desc	
	
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderMethod() {
		return orderMethod;
	}
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}
	public Integer getStartNum() {
		return startNum;
	}
	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	public Integer getEndNum() {
		return endNum;
	}
	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	

}
