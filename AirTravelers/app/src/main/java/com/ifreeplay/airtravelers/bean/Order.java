package com.ifreeplay.airtravelers.bean;

import java.util.Date;

public class Order {
	private static final long serialVersionUID = 1L;
	
	public enum Status {
		OPEN, // 新建
		PAYED, // 已支付
		REFUND, // 已退款
		CANCELED // 已取消
	}

	public enum CurrencyTypes {
		CNY, // 人民币
		USD, // 美元
		HKD, // 港币
		JPY, // 日元
		GBP, // 英镑
		EUR // 欧元
	}
	
	private long orderNumber;
	private long gameId;
	private long productId;
	private String productName;//商品名称
	private long playerId;
	private Status status;
	private int price; // 商品单价
	private int totalPrice; // 订单总价
	private int dealPrice; // 订单成交价
	private Date createDateTime; // 订单创建时间
	private String spbillCreateIp;//终端IP
	private CurrencyTypes currencyTypes;//货币类型 paypal需要

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(int dealPrice) {
		this.dealPrice = dealPrice;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public CurrencyTypes getCurrencyTypes() {
		return currencyTypes;
	}

	public void setCurrencyTypes(CurrencyTypes currencyTypes) {
		this.currencyTypes = currencyTypes;
	}
}
