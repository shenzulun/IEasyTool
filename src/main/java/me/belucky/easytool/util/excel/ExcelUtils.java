/**
 * File Name: ExcelUtils.java
 * Date: 2020-03-27 14:36:57
 */
package me.belucky.easytool.util.excel;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: Excel处理工具类
 * <pre>
  *   基于阿里的<code>easyexcel</code>做了层封装
  *   源码：<a>https://github.com/alibaba/easyexcel</a>
 * </pre>
 * @author shenzulun
 * @date 2020-03-27
 * @version 1.0
 */
public class ExcelUtils {
	protected static Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	
	
	/**
	 * 写excel
	 * @param excelModel  excel模型
	 */
	public static void writeExcel(ExcelModel excelModel) {
		String excelName = excelModel.getFileName();
		if(StringUtils.isNull(excelName)) {
			log.error("excel的文件名不能为空,文件名包含绝对路径...");
			return;
		}
		List<ExcelSheetModel> sheets = excelModel.getSheets();
		if(sheets == null || sheets.size() == 0) {
			log.error("sheet页内容不能为空...");
			return;
		}
		ExcelWriter excelWriter = null;
		String templateName = excelModel.getTemplateName();
		if(StringUtils.isNotNull(templateName)) {
			excelWriter = EasyExcel.write(excelName).withTemplate(templateName).build();
		}else {
			excelWriter = EasyExcel.write(excelName).build();
		}
		for(int i=0,len=sheets.size();i<len;i++) {
			ExcelSheetModel sheetModel = sheets.get(i);
			Class<?> className = sheetModel.getDataClass();
			if(sheetModel.getData() != null && sheetModel.getData().size() > 0) {
				className = sheetModel.getData().get(0).getClass();
			}
			WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetModel.getSheetName()).head(className).build();
            excelWriter.write(sheetModel.getData(), writeSheet);
		}
		excelWriter.finish();
	}
	
	public static void readExcel(String fileName) {
		
	}

}
