/**
 * File Name: ExcelDemoData.java
 * Date: 2020-04-14 16:03:53
 */
package me.belucky.easytool.util.excel;

import org.apache.poi.ss.usermodel.BorderStyle;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;

/**
 * Description: excel的demo数据
 * @author shenzulun
 * @date 2020-04-14
 * @version 1.0
 */
//@ContentStyle(borderLeft=BorderStyle.THIN,borderRight=BorderStyle.THIN,borderTop=BorderStyle.THIN,borderBottom=BorderStyle.THIN)
public class ExcelDemoData {
	
	@ExcelIgnore
	private int id;
	
	@ExcelProperty(value = "客户号", index = 0)
	private String khh;
	
	@ExcelProperty(value = "客户名称", index = 1)
	private String khmc;
	
	public ExcelDemoData() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKhh() {
		return khh;
	}

	public void setKhh(String khh) {
		this.khh = khh;
	}

	public String getKhmc() {
		return khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}

}
