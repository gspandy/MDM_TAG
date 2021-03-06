package com.multigold.mdm.web.controller.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.multigold.common.constants.Constants;
import com.multigold.common.service.BaseService;
import com.multigold.common.util.JsonUtil;
import com.multigold.common.web.controller.BaseCRUDAction;
import com.multigold.mdm.web.controller.util.UserSessionProvider;
import com.multigold.t6.service.api.po.PaymentSettlementService;
import com.multigold.t6.service.api.po.RdrecordService;
import com.multigold.t6.service.api.query.InvoiceQueryService;
import com.multigold.t6.vo.po.InvoiceVO;
import com.multigold.t6.vo.po.PaymentSettlementVO;
import com.multigold.t6.vo.po.RdrecordDetailQueryVO;

/**
 * 
 * @author yangjun
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/query/invoiceQuery")
public class InvoiceQueryController extends BaseCRUDAction<InvoiceVO, String>{

	@Autowired
	InvoiceQueryService<InvoiceVO, String> invoiceQueryService;
	@Autowired
	PaymentSettlementService<PaymentSettlementVO, String> paymentSettlementService;
	@Autowired
	RdrecordService<InvoiceVO, String> rdrecordService;
	@Override
	public BaseService<InvoiceVO, String> getBaseService() {
		return invoiceQueryService;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request, InvoiceVO invoiceVO,
			String operateFlag) {
		invoiceVO.setInsertBy(UserSessionProvider.getUserSerssion(request).getAccount());
		invoiceVO.setModifiedBy(UserSessionProvider.getUserSerssion(request).getAccount());
	}
	
	/*
	 * @Title: invoiceQuery 
	 * @Description: 采购发票单列表
	 * @return String
	 */
	@RequestMapping(value = "invoiceQuery", method = RequestMethod.GET)
	public String invoiceQueryList() {
		return viewName("invoiceQuery");
	}
	
	/*
	 * 获取发票单列表查询条件
	 * @return
	 */
	@RequestMapping(value = "invoiceQueryLists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getInvoiceLists(HttpServletRequest request, String invoiceId,String businessType, String venCode, String status, Long page, Integer rows) {
		Map<String, Object> resultMap = Maps.newHashMap();
		Map<String, Object> map = Maps.newHashMap();
		if (!StringUtils.isEmpty(invoiceId)) {
			map.put("invoiceId", invoiceId);
		}
		if (!StringUtils.isEmpty(venCode)) {
			map.put("venCode", venCode);
		}
		if (!StringUtils.isEmpty(status)) {
			map.put("status", status);
		}
		if (!StringUtils.isEmpty(businessType)) {
			map.put("businessType", businessType);
		}
		map.put("page", page);
		map.put("rows", rows);
		resultMap= invoiceQueryService.getInvoiceQueryList(map);
		return resultMap;
		
	}
	
	@RequestMapping(value = "commitByIds", method = RequestMethod.POST)
	@ResponseBody
	public void getCommitByIds(HttpServletRequest request,String rowsData) {

		List<InvoiceVO> psvList  = new ArrayList<InvoiceVO>();
		if(null!=rowsData && !("").equals(rowsData)){
			psvList = (List<InvoiceVO>)JsonUtil.jsonToList(rowsData, InvoiceVO.class);
		}
		for (InvoiceVO p : psvList) {
			p.setStatus(1);
			invoiceQueryService.commitByIds(p);
		}
		
		
	}
	/*
	 * 查询入库单明细
	 */
	@RequestMapping(value = "queryRdRecordLists", method = RequestMethod.POST)
	@ResponseBody
	public List<RdrecordDetailQueryVO> queryRdRecordLists(HttpServletRequest request,int id) {

		InvoiceVO invoiceVO = new InvoiceVO();
		invoiceVO.setId(id);
		List<RdrecordDetailQueryVO> list = rdrecordService.getInvRdrecordDetail(id);
		
		return list;
		
	}
	
//	/*
//	 * @Description: 选择列表 发票
//	 * @return Map
//	 */
//	@RequestMapping(value = "queryRdRecordLists", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> queryRdRecordLists(HttpServletRequest request, String id, Long page, Integer rows, String vendorSelectVal, String paymentId, String redFlag){
//		Map<String, Object> resultMap = Maps.newHashMap();
//		Map<String, Object> map = Maps.newHashMap();
//		map.put("page", page);
//		map.put("rows", rows);
//		map.put("venCode", vendorSelectVal);
//		map.put("paymentId", paymentId);
//		map.put("redFlag", redFlag);
//		map.put("status", Constants.InvoiceAdmin.INVOICE_STATUS_2);
//		map.put("businessType", Constants.InvoiceAdmin.BUSINESS_TYPE_1);
//		resultMap = paymentSettlementService.getInvoice(map);
//		return resultMap;
//	}
	
//	@RequestMapping(value = "queryRdRecordLists", method = RequestMethod.GET)
//	@ResponseBody
//
//	public List<RdrecordDetailQueryVO> queryRdRecordLists(HttpServletRequest request,int id) {
//
//		RdrecordDetailQueryVO rdrecordDetailQueryVO = new RdrecordDetailQueryVO();
//		rdrecordDetailQueryVO.setId(id);
//		List<RdrecordDetailQueryVO> list = rdrecordService.getInvRdrecordDetail(id);
//		
//		return list;
//		
//	}
	
//	/*
//	 * @Title: mg_rd_and_inv 
//	 * @Description: 选择列表 关联关系表
//	 * @return list
//	 */
//	@RequestMapping(value = "queryRdRecordLists", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> queryRdRecordLists(HttpServletRequest request, String id,Long page, Integer rows){
//		Map<String, Object> resultMap = Maps.newHashMap();
//		Map<String, Object> map = Maps.newHashMap();
//		//MgRdAndInvVO mgRdAndInvVO = new MgRdAndInvVO();
//		//String strVendorSelectVal = vendorSelectVal;
//		map.put("page", page);
//		map.put("rows", rows);
//		
//		map.put("id", id);
//		
//		map.put("cvouchType", Constants.InvoiceAdmin.INVOICE_CVOUCHTYPE_01);
//		map.put("aStatus", Constants.InvoiceAdmin.AUDIT_STATUS_3);
//		resultMap = rdrecordService.pageQueryRdrecordForMRAIList(map);
//		
////		List<MgRdAndInvVO> list = invoiceService.queryMgRdAndInv();
//		return resultMap;
//	}
}
