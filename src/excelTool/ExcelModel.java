package excelTool;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 描述：获取excel模板
 * 
 * @author wanghang
 *
 */
public class ExcelModel {

	/**
	 * 描述：导出单工作表Excel
	 * 
	 * @param os
	 * @param sheetName
	 *            工作表名称,缺省值为sheet1
	 * @param title
	 *            文件第一列标题集合
	 * @param listContent
	 *            文件内容正文
	 * @param maxSheet
	 * 			  Excel工作表的最大数据量
	 * @return
	 */
	public final static boolean exportExcelForSingleSheet(OutputStream os, String sheetName, LinkedHashMap title,	List<Object> listContent) {
		boolean isSuccess = true;
		//如果设定的工作表的最大数据不合法，则默认最大数值
		//maxSheet = (maxSheet > 65536 || maxSheet <= 0) ? 65536 : maxSheet;
		//计算总页数
		//int sheetTotalNum = listContent.size()%maxSheet == 0 ? listContent.size()/maxSheet : listContent.size()/maxSheet+1;
		
		WritableWorkbook workBook = null;
		try {
			// 创建工作簿
			workBook = Workbook.createWorkbook(os);

			// 创建工作表
			WritableSheet sheet = workBook.createSheet(	(sheetName == null) ? "sheet1" : ("".equals(sheetName.trim()) ? "sheet1" : sheetName), 0);

			// 设置纵横打印，默认纵打印
			jxl.SheetSettings sheetSet = sheet.getSettings();
			sheetSet.setProtected(false);
			
			// 获取单元格模板
			WritableCellFormat wcf_title = ExcelModel.getCellModelForTitle(10);
			WritableCellFormat wcf_content = ExcelModel.getCellModelForContent(10);
			
			// 写入标题内容
			ExcelModel.writeTitle(sheet, wcf_title, title, 1, 1);
			ExcelModel.writeContent(sheet, wcf_content, title, listContent, 2, 1);
			
			//将缓存总内容写入Excel
			workBook.write();
			
		} catch (Exception e) {
			// 打印错误日志
			isSuccess = false;
			System.out.println("导出Excel失败，原因:" + e.toString());
			e.printStackTrace();
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (Exception e2) {
					//打印错误日志
					e2.printStackTrace();
				}
			}
		}
		return isSuccess;
	}

	/**
	 * 描述：获取单元格模型,标题
	 * 
	 * @param fontSize 字体大小,缺省值 12
	 * @return
	 */
	private final static WritableCellFormat getCellModelForTitle(int fontSize) throws Exception{
		WritableCellFormat wcf = null;
		try {
			// 获取画笔
			WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize <= 0 ? 12 : fontSize, WritableFont.BOLD);

			// 设置单元格
			wcf = new WritableCellFormat(font);
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//文字垂直对齐
			wcf.setAlignment(Alignment.CENTRE);//文字居中对齐
			wcf.setBackground(Colour.YELLOW);
			wcf.setWrap(false);
		} catch (Exception e) {
			throw e;
		}
		return wcf;
	}
	
	/**
	 * 描述：获取单元格模型,内容
	 * 
	 * @param fontSize 字体大小,缺省值 10
	 * @return
	 */
	private final static WritableCellFormat getCellModelForContent(int fontSize) throws Exception {
		WritableCellFormat wcf = null;
		// 获取画笔
		WritableFont font = new WritableFont(WritableFont.ARIAL,
				fontSize <= 0 ? 10 : fontSize);

		// 设置单元格
		wcf = new WritableCellFormat(font);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);// 文字垂直对齐
		wcf.setAlignment(Alignment.LEFT);// 文字居中对齐
		wcf.setWrap(false);
		return wcf;
	}
	
	/**
	 * 写入标题内容
	 * @param sheet 工作簿
	 * @param wcf 单元格格式
	 * @param map 标题内容
	 * @param rowNum 行数，0为第一行
	 * @param colNum 列数，缺省值0
	 * @return
	 */
	private final static WritableSheet writeTitle(WritableSheet sheet, WritableCellFormat wcf, LinkedHashMap map, int rowNum, int colNum) throws Exception{
		rowNum = rowNum < 0 ? 0: rowNum;
		colNum = colNum < 0 ? 0: colNum;
		for(Iterator iter = map.entrySet().iterator();iter.hasNext();){ 
            Map.Entry element = (Map.Entry)iter.next(); 
            Object strObj = element.getValue(); 
            sheet.addCell(new Label(colNum, rowNum, strObj.toString(), wcf));
            colNum++;
		} 
		
		return sheet;
	}
	
	/**
	 * 写入正文内容
	 * @param sheet 工作簿
	 * @param wcf 单元格格式
	 * @param map 标题对应
	 * @param listContent 正文内容
	 * @param rowNum 起始行，缺省值为1
	 * @param colNum 起始列，缺省值为0
	 * @param os 用于定时清理缓存
	 * @return
	 * @throws Exception
	 */
	private final static WritableSheet writeContent(WritableSheet sheet, WritableCellFormat wcf, LinkedHashMap map,	List<Object> listContent, int rowNum, int colNum) throws Exception {
		rowNum = rowNum < 0 ? 1 : rowNum;
		colNum = colNum < 0 ? 0 : colNum;
		
		List<Map> list = ExcelModel.getListContent(listContent);
		for (Map contentMap : list) {
			// 根据标题顺序写入正文内容
			int col = colNum;
			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
				Map.Entry element = (Map.Entry) iter.next();
				Object key = element.getKey();
				String content = (String) contentMap.get(key);

				sheet.addCell(new Label(col, rowNum, content, wcf));
				col++;
			}
			rowNum++;
		}

		return sheet;
	}
	
	/**
	 * 将领域模型转换为map
	 * @param listContent
	 * @return
	 */
	private final static List<Map> getListContent(List<Object> listContent) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		if(listContent == null){
			return list;
		}
		
		Field[] fields = null;
		 for(Object obj:listContent){
			 fields = obj.getClass().getDeclaredFields();
			 Map contentMap = new HashMap();
			 for(Field v : fields){
				 v.setAccessible(true);
                 Object va = v.get(obj);
                 String name = v.getName();
                 if(va == null){
                	 va = "";
                 }
                 contentMap.put(name, va.toString());
			 }
			 
			 list.add(contentMap);
		 }
		 return list;
	}
}
