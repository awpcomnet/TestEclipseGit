package excelTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.common.Logger;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @Description: G06商业银行理财业务统计表模板导出
 * @author 王航
 * @date 2015年12月31日 上午11:04:57
 */
public class G60BusinessTemplate {
	
	
	/**
	 * @fromFile 源模板位置
	 * @toFile 导出位置
	 * @mapContent key：数据起始行坐标， 格式 :   col|row
	 * 			   value:横行数据
	 */
	public static boolean exportExcel(File fromFile, File toFile, Map<String, List<Object>> mapContent) throws Exception{
		boolean isSuccess = false;
		Workbook fromWorkbook = null;
		WritableWorkbook toWorkbook = null;
		
		//读取源模板
		fromWorkbook = getTemplate(fromFile, true);
		
		//动态数据添加
		insertionData(toFile, mapContent, fromWorkbook);
		
		return isSuccess;
	}
	
	/**
	 * 获取导出模板
	 * @throws Exception 
	 */
	private static Workbook getTemplate(File fromFile ,Boolean rewrite ) throws Exception{
		//读取源模板
		if(!fromFile.exists())
			throw new Exception("源模板不存在");
		if(!fromFile.isFile())
			throw new Exception("源模板为非法文件");
		if(!fromFile.canRead())
			throw new Exception("源模板不可读取");
        
		InputStream ins = new FileInputStream(fromFile);
		Workbook workBook = Workbook.getWorkbook(ins);
		return workBook;
	}
	
	/**
	 * 出入数据
	 * @throws Exception 
	 */
	private static void insertionData(File toFile, Map<String, List<Object>> mapContent, Workbook fromWorkbook) throws Exception{
		OutputStream os = null;
		WritableWorkbook toWorkbook = null;
		
		try {
			os = new FileOutputStream(toFile);
			toWorkbook = Workbook.createWorkbook(os);
			
			Sheet fromSheet = fromWorkbook.getSheet(0);
			toWorkbook.importSheet(fromSheet.getName(), 0, fromSheet);
			WritableSheet toSheet = toWorkbook.getSheet(0);
			
			writeToExcel(toSheet, mapContent);
			
			toWorkbook.write();
		} catch (Exception e) {
			LOG.error("写入数据错误,原因:{}", e);
			// 判断是否为文件  
	        if (toFile.isFile()) {
	            toFile.delete();  
	        }
			throw e;
		} finally {
			if(toWorkbook != null){
				toWorkbook.close();
			}
			if(fromWorkbook != null){
				fromWorkbook.close();
			}
		}
		
	}
	
	/**
	 * 写入数据
	 */
	private static void writeToExcel(WritableSheet toSheet, Map<String, List<Object>> mapContent) throws Exception{
		for (Map.Entry<String, List<Object>> entry : mapContent.entrySet()) {
			String key = entry.getKey();
			String colAndRow[] = key.split("\\|");
			if(colAndRow.length != 2)
				continue;
			
			Integer col = Integer.valueOf(colAndRow[0]);
			Integer row = Integer.valueOf(colAndRow[1]);
			List<Object> list = entry.getValue();
			for(Object obj : list){
				//获取单元格
				Cell cell = toSheet.getCell(col, row);
				CellType type = cell.getType();
//				if(CellType.NUMBER.equals(type) || CellType.NUMBER_FORMULA.equals(type)){
//					if(obj == null || "".equals(obj)){
//						obj = 0;
//					}
//					jxl.write.Number number = new jxl.write.Number(cell.getColumn(), cell.getRow(), Double.parseDouble(obj+""), cell.getCellFormat()); 
//					toSheet.addCell(number);
//				}else if(CellType.DATE.equals(type) || CellType.DATE_FORMULA.equals(type)){
//					//时间不处理
//					//System.out.println(obj);
//					//jxl.write.DateTime date = new jxl.write.DateTime(cell.getColumn(), cell.getRow(), new Date(obj+""), cell.getCellFormat());
//				}else if(CellType.LABEL.equals(type) || CellType.STRING_FORMULA.equals(type)){
//					Label label = new Label(cell.getColumn(), cell.getRow(), obj+"", cell.getCellFormat()); 
//					toSheet.addCell(label);
//				}else if(CellType.BOOLEAN.equals(type) || CellType.BOOLEAN_FORMULA.equals(type)){
//					jxl.write.Boolean bool = new jxl.write.Boolean(cell.getColumn(), cell.getRow(), (boolean)obj, cell.getCellFormat());
//					toSheet.addCell(bool);
//				}else{
					if(cell.getCellFormat() == null)
						continue;
					boolean isNumber = G60BusinessTemplate.isNumeric(obj+"");
					if(isNumber){
						jxl.write.Number number = new jxl.write.Number(cell.getColumn(), cell.getRow(), Double.parseDouble(obj+""), cell.getCellFormat()); 
						toSheet.addCell(number);
					}else{
						Integer cola = cell.getColumn();
						Integer rowa = cell.getRow();
						
						
						Label label = new Label(cell.getColumn(), cell.getRow(), obj+"", cell.getCellFormat()); 
						toSheet.addCell(label);
					}
//				}
				
				col++;
			}
		}
	}
	
	private static boolean isNumeric(String str)
    {
          Pattern pattern = Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?");
          Matcher isNum = pattern.matcher(str);
          if( !isNum.matches() )
          {
                return false;
          }
          return true;
    }
	
	private static final Logger LOG = Logger.getLogger(G60BusinessTemplate.class);
}
