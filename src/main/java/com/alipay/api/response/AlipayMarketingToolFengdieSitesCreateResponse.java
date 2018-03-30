package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.domain.FengdieActivityCreateModel;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.marketing.tool.fengdie.sites.create response.
 * 
 * @author auto create
 * @since 1.0, 2018-02-02 10:58:25
 */
public class AlipayMarketingToolFengdieSitesCreateResponse extends AlipayResponse {

	private static final long serialVersionUID = 8841985158468149269L;

	/** 
	 * 创建站点的返回值模型
	 */
	@ApiField("data")
	private FengdieActivityCreateModel data;

	public void setData(FengdieActivityCreateModel data) {
		this.data = data;
	}
	public FengdieActivityCreateModel getData( ) {
		return this.data;
	}

}
