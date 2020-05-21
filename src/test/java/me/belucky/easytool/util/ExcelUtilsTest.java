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
	
//	@Test
	public void testWrite(){
		List<ExcelDemoData> demoDataList = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			ExcelDemoData data = new ExcelDemoData();
			data.setId(1);
			data.setKhh("khh" + i);
			data.setKhmc("khmc" + i);
			demoDataList.add(data);
        }
		ExcelModel excelModel = ExcelModel.build("C:/Users/shenzl/Desktop/demo-easyexcel-20200415.xlsx", "sheet1", demoDataList);
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
		
		ExcelModel excelModel = ExcelModel.build("C:/Users/shenzl/Desktop/demo-easyexcel-20200415-m.xlsx", sheetList);
		ExcelUtils.writeExcel(excelModel);
	}
	
	@Test
	public void testWriteWithTemplate(){
		List<ExcelDemoData> demoDataList = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			ExcelDemoData data = new ExcelDemoData();
			data.setId(1);
			data.setKhh("khh" + i);
			data.setKhmc("khmc" + i);
			demoDataList.add(data);
        }
		ExcelModel excelModel = ExcelModel.build("C:/Users/shenzl/Desktop/excel/demo-easyexcel-template.xlsx", "sheet1", demoDataList)
										  .setTemplateName("C:/Users/shenzl/Desktop/excel/template_cust.xlsx");
		ExcelUtils.writeExcel(excelModel);
	}
}
