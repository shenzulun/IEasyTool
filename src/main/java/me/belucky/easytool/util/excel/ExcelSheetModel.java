/**
 * File Name: ExcelSheetModel.java
 * Date: 2020-04-14 16:06:55
 */
package me.belucky.easytool.util.excel;

import java.util.List;

/**
 * Description: excel的sheet页模型
 * @author shenzulun
 * @date 2020-04-14
 * @version 1.0
 */
public class ExcelSheetModel {

	/**
	 * sheet页名称
	 */
	private String sheetName;
	/**
	 * sheet页数据的class
	 * 当data为空时,该字段生效
	 */
	private Class<?> dataClass;
	/**
	 * 数据
	 */
	private List data;
	
	public ExcelSheetModel(String sheetName, List data) {
		super();
		this.sheetName = sheetName;
		this.data = data;
	}

	public ExcelSheetModel() {}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public Class<?> getDataClass() {
		return dataClass;
	}

	public void setDataClass(Class<?> dataClass) {
		this.dataClass = dataClass;
	}
	
	
}
