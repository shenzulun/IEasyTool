/**
 * File Name: ExcelModel.java
 * Date: 2020-04-14 16:03:24
 */
package me.belucky.easytool.util.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Excel模型
 * @author shenzulun
 * @date 2020-04-14
 * @version 1.0
 */
public class ExcelModel {

	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * sheet页数据
	 */
	private List<ExcelSheetModel> sheets;
	/**
	 * 模板名字
	 */
	private String templateName;
	
	public ExcelModel() {}
	
	public ExcelModel(String fileName, List<ExcelSheetModel> sheets) {
		super();
		this.fileName = fileName;
		this.sheets = sheets;
	}
	
	/**
	 * 创建多sheet页excel
	 * @param fileName
	 * @param sheets
	 * @return
	 */
	public static ExcelModel build(String fileName, List<ExcelSheetModel> sheets) {
		return new ExcelModel(fileName, sheets);
	}

	/**
	 * 创建单sheet页excel
	 * @param fileName
	 * @param sheet
	 * @return
	 */
	public static ExcelModel build(String fileName, ExcelSheetModel sheet) {
		ExcelModel model = new ExcelModel();
		List<ExcelSheetModel> list = new ArrayList<ExcelSheetModel>();
		list.add(sheet);
		model.setFileName(fileName);
		model.setSheets(list);
		return model;
	}
	
	/**
	 * 创建单sheet页excel
	 * @param fileName
	 * @param sheetName
	 * @param data
	 * @return
	 */
	public static ExcelModel build(String fileName, String sheetName, List data) {
		ExcelModel model = new ExcelModel();
		List<ExcelSheetModel> list = new ArrayList<ExcelSheetModel>();
		ExcelSheetModel sheet = new ExcelSheetModel();
		sheet.setSheetName(sheetName);
		sheet.setData(data);
		list.add(sheet);
		model.setFileName(fileName);
		model.setSheets(list);
		return model;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<ExcelSheetModel> getSheets() {
		return sheets;
	}

	public void setSheets(List<ExcelSheetModel> sheets) {
		this.sheets = sheets;
	}

	public String getTemplateName() {
		return templateName;
	}

	public ExcelModel setTemplateName(String templateName) {
		this.templateName = templateName;
		return this;
	}
	
	
}
