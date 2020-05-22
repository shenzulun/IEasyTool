/**
 * File Name: ExcelUtilsTest.java
 * Date: 2020-04-15 08:33:22
 */
package me.belucky.easytool.util;

import java.util.List;
import org.junit.Test;
import com.google.common.collect.Lists;
import me.belucky.easytool.util.excel.ExcelDemoData;
import me.belucky.easytool.util.excel.ExcelModel;
import me.belucky.easytool.util.excel.ExcelSheetModel;
import me.belucky.easytool.util.excel.ExcelUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-15
 * @version 1.0
 */
public class ExcelUtilsTest {
	
	@Test
	public void testWrite(){
		List<ExcelDemoData> demoDataList = Lists.newArrayList();
		for (int i = 0; i < 1000; i++) {
			ExcelDemoData data = new ExcelDemoData();
			data.setId(1);
			data.setKhh("khh" + i);
			data.setKhmc("khmc" + i);
			demoDataList.add(data);
        }
		ExcelModel excelModel = ExcelModel.build("C:/Users/shenzl/Desktop/mix/excel/demo-easyexcel-" + System.currentTimeMillis() + ".xlsx", "sheet1", demoDataList)
										  .setCommonCellStyle();
		ExcelUtils.writeExcel(excelModel);
	}
	
//	@Test
	public void testWriteMultiSheet(){
		int sheetSize = 3;
		List<ExcelSheetModel> sheetList = Lists.newArrayList();
		for(int a=0;a<sheetSize;a++) {
			List<ExcelDemoData> demoDataList = Lists.newArrayList();
			for (int i = 0; i < 10; i++) {
				ExcelDemoData data = new ExcelDemoData();
				data.setId(1);
				data.setKhh("khh-" + a + "-" + i);
				data.setKhmc("khmc-" + a + "-" + i);
				demoDataList.add(data);
	        }
			sheetList.add(new ExcelSheetModel("sheet-" + a, demoDataList));
		}
		
		ExcelModel excelModel = ExcelModel.build("C:/Users/shenzl/Desktop/mix/excel/demo-easyexcel-20200415-m.xlsx", sheetList);
		ExcelUtils.writeExcel(excelModel);
	}
	
//	@Test
	public void testWriteWithTemplate(){
		List<ExcelDemoData> demoDataList = Lists.newArrayList();
		for (int i = 0; i < 10000; i++) {
			ExcelDemoData data = new ExcelDemoData();
			data.setId(1);
			data.setKhh("khh" + i);
			data.setKhmc("khmc" + i);
			demoDataList.add(data);
        }
		List<ExcelSheetModel> sheets = Lists.newArrayList();
		
		ExcelSheetModel model1 = new ExcelSheetModel();
		model1.setSheetName("客户信息1");
		model1.setData(demoDataList);
		model1.setDataClass(ExcelDemoData.class);
		sheets.add(model1);
		
		ExcelSheetModel model2 = new ExcelSheetModel();
		model2.setSheetName("客户信息2");
		model2.setData(demoDataList);
		model2.setDataClass(ExcelDemoData.class);
		sheets.add(model2);
		
		ExcelModel excelModel = ExcelModel.build("C:/Users/shenzl/Desktop/mix/excel/demo-easyexcel-" + System.currentTimeMillis() + ".xlsx", sheets)
										  .setTemplateName("C:/Users/shenzl/Desktop/mix/excel/template_cust.xlsx")
										  .setCommonCellStyle();
		
//		ExcelModel excelModel = ExcelModel.build("C:/Users/shenzl/Desktop/mix/excel/demo-easyexcel-template.xlsx", "sheet1", demoDataList)
//										  .setTemplateName("C:/Users/shenzl/Desktop/mix/excel/template_cust.xlsx");
		ExcelUtils.writeExcel(excelModel);
	}
}
