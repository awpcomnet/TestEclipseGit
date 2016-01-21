package excelTool;

import java.awt.Color;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.common.Logger;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @Description: 个人理财excel模板
 * @author 王航
 * @date 2015年12月29日 下午5:36:11
 */
public class PersonalFinanceTemplate {
	
	private static final String SEQUENCE[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF"};
	
	private PersonalFinanceTemplate(){
		
	}
	
	/**
	 * 导出excel
	 * os：输出流
	 * sheetName: 工作表名称，缺省为sheet1
	 * titleKey: 文件列关键字，用于从listcontent中顺序获取数据
	 * listContent: 文件内容
	 */
	public final static boolean exportExcel(OutputStream os, String sheetName, String title, String bskj, String bsrq,List<String> titleKey, List<Map<String, Object>> listContent){
		boolean isSuccess = false;
		
		//定义工作簿
		WritableWorkbook workBook = null;
		try {
			//创建工作簿
			workBook = Workbook.createWorkbook(os);
			
			//工作簿设置
			setWorkBook(workBook);
			
			//创建工作表
			WritableSheet sheet = workBook.createSheet(	(sheetName == null) ? "sheet1" : ("".equals(sheetName.trim()) ? "sheet1" : sheetName), 0);
			
			/*基础信息设置*/
			//设置打印方向,纵向，默认纵向
			jxl.SheetSettings sheetSet = sheet.getSettings();
			sheetSet.setProtected(false);
			
			//生成整个EXCEL
			generatorExcel(sheet, titleKey, listContent, title, bskj, bsrq);
			
			//将缓存总内容写入Excel
			workBook.write();
			
			//导出成功
			isSuccess = true;
			
		} catch (Exception e) {
			LOG.error("导出excel失败，原因:{}", e);
		} finally {
			if(workBook != null){
				try {
					workBook.close();
				} catch (Exception e2) {
					LOG.error("工作簿关闭失败，原因:{}", e2);
				}
			}
		}
		
		
		return isSuccess;
	}
	
	/**
	 * 生成Excel
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private static void generatorExcel(WritableSheet sheet, List<String> titleKey, List<Map<String, Object>> listContent, String title, String bskj, String bsrq) throws RowsExceededException, WriteException{
		//合并单元格处理
		marginCellProcess(sheet, getMergeCellParams());
		
		//生成表
		generatorHeader(sheet, titleKey, listContent, title, bskj, bsrq);
		
	}
	
	/**
	 * 生成表头
	 * @throws WriteException 
	 */
	private static void generatorHeader(WritableSheet sheet,  List<String> titleKey, List<Map<String, Object>> listContent, String title, String bskj, String bsrq) throws WriteException{
		//获取画笔
		WritableFont font_black_20 = new WritableFont(WritableFont.TIMES, 20);
		WritableFont font_fs_10 = new WritableFont(WritableFont.createFont("仿宋_GB2312"),10);
		WritableFont font_s_12 = new WritableFont(WritableFont.createFont("宋体"),12);
		WritableFont font_s_10 = new WritableFont(WritableFont.createFont("宋体"),10);
		WritableFont font_s_9 = new WritableFont(WritableFont.createFont("宋体"),9);
		
		//设置数字格式
		NumberFormat nf2 = new NumberFormat("0.00");    //设置数字格式
		NumberFormat nf0 = new NumberFormat("#");    //设置数字格式
		
		
		//获取单元格模板
		Map noneMap = Maps.newHashMap();
		noneMap.put("none", "0");
		WritableCellFormat wcf_noborder_b20 = getCellModelForNormal(font_black_20, null, noneMap, null);
		WritableCellFormat wcf_noborder_fs10 = getCellModelForNormal(font_fs_10, null, noneMap, null);
		WritableCellFormat wcf_noborder_s10 = getCellModelForNormal(font_s_10, null, noneMap, null);
		WritableCellFormat wcf_noborder_s10_bgblue = getCellModelForNormal(font_s_10, Colour.BLUE2, noneMap, null);
		WritableCellFormat wcf_noborder_s10_bggray = getCellModelForNormal(font_s_10, Colour.GRAY_25, noneMap, null);
		
		Map topMap = Maps.newHashMap();
		topMap.put("top", BorderLineStyle.THICK);
		topMap.put("right", BorderLineStyle.THIN);
		topMap.put("bottom", BorderLineStyle.THIN);
		topMap.put("left", BorderLineStyle.THIN);
		WritableCellFormat wcf_topborder_s10 = getCellModelForNormal(font_s_10, null, topMap, null);
		WritableCellFormat wcf_topborder_s9 = getCellModelForNormal(font_s_9, null, topMap, null);
		
		Map normalMap = Maps.newHashMap();
		normalMap.put("all", BorderLineStyle.THIN);
		WritableCellFormat wcf_normalborder_s12 = getCellModelForNormal(font_s_12, null, normalMap, null);
		WritableCellFormat wcf_normalborder_s10 = getCellModelForNormal(font_s_10, null, normalMap, null);
		WritableCellFormat wcf_normalborder_s9 = getCellModelForNormal(font_s_9, null, normalMap, null);
		WritableCellFormat wcf_normalborder_s10_bgblue = getCellModelForNormal(font_s_10, Colour.BLUE2, normalMap, null);
		WritableCellFormat wcf_normalborder_s10_bggray = getCellModelForNormal(font_s_10, Colour.GRAY_25, normalMap, null);
		WritableCellFormat wcf_normalborder_s10_bgblue_nf2 = getCellModelForNormal(font_s_10, Colour.BLUE2, normalMap, nf2);
		WritableCellFormat wcf_normalborder_s10_nf2 = getCellModelForNormal(font_s_10, null, normalMap, nf2);
		WritableCellFormat wcf_normalborder_s10_nf0 = getCellModelForNormal(font_s_10, null, normalMap, nf0);
		
		Map bottomMap = Maps.newHashMap();
		bottomMap.put("top", BorderLineStyle.THIN);
		bottomMap.put("right", BorderLineStyle.THIN);
		bottomMap.put("bottom", BorderLineStyle.THICK);
		bottomMap.put("left", BorderLineStyle.THIN);
		WritableCellFormat wcf_bottomborder_s10 = getCellModelForNormal(font_s_10, null, bottomMap, null);
		WritableCellFormat wcf_bottomborder_s9 = getCellModelForNormal(font_s_9, null, bottomMap, null);
		WritableCellFormat wcf_bottomborder_s10_bggray = getCellModelForNormal(font_s_10, Colour.GRAY_25, bottomMap, null);
		WritableCellFormat wcf_bottomborder_s10_bgblue = getCellModelForNormal(font_s_10, Colour.BLUE2, bottomMap, null);
		WritableCellFormat wcf_bottomborder_s10_bgblue_nf2 = getCellModelForNormal(font_s_10, Colour.BLUE2, bottomMap, nf2);
		WritableCellFormat wcf_bottomborder_s10_bgblue_nf0 = getCellModelForNormal(font_s_10, Colour.BLUE2, bottomMap, nf0);
		
		/*----------------绘制开始---------------------*/
		sheet.addCell(new Label(0, 0, title , wcf_noborder_b20));
		sheet.addCell(new Label(0, 1, bskj, wcf_noborder_fs10));
		sheet.addCell(new Label(2, 1, bsrq, wcf_noborder_fs10));
		sheet.addCell(new Label(14, 1, "货币单位：万元", wcf_noborder_s10));
		sheet.addCell(new Label(0, 2, "序号", wcf_topborder_s9));
		for(int i=0; i<SEQUENCE.length; i++){
			sheet.addCell(new Label(i+1, 2, SEQUENCE[i], wcf_topborder_s10));
		}
		sheet.addCell(new Label(1, 3, "产品基本情况", wcf_normalborder_s10));
		sheet.addCell(new Label(9, 3, "募集资金情况", wcf_normalborder_s10));
		sheet.addCell(new Label(13,3, "尚在存续期产品情况", wcf_normalborder_s10));
		sheet.addCell(new Label(20,3, "到期产品情况", wcf_normalborder_s10));
		sheet.addCell(new Label(25,3, "投诉情况", wcf_normalborder_s10));
		sheet.addCell(new Label(27,3, "诉讼情况", wcf_normalborder_s10));
		sheet.addCell(new Label(32,3, "备注", wcf_normalborder_s10));
		sheet.addCell(new Label(1, 4, "产品代码", wcf_normalborder_s9));
		sheet.addCell(new Label(2, 4, "产品名称", wcf_normalborder_s9));
		sheet.addCell(new Label(3, 4, "发布币种", wcf_normalborder_s9));
		sheet.addCell(new Label(4, 4, "起点销售金额", wcf_normalborder_s9));
		sheet.addCell(new Label(5, 4, "产品类型", wcf_normalborder_s9));
		sheet.addCell(new Label(6, 4, "收益类型", wcf_normalborder_s9));
		sheet.addCell(new Label(7, 4, "起息日", wcf_normalborder_s9));
		sheet.addCell(new Label(8, 4, "到期日", wcf_normalborder_s9));
		sheet.addCell(new Label(9, 4, "有到期日产品当期募集金额", wcf_normalborder_s9));
		sheet.addCell(new Label(10,4, "无到期日开放式产品", wcf_normalborder_s9));
		sheet.addCell(new Label(10,5, "当期募集金额", wcf_normalborder_s9));
		sheet.addCell(new Label(11,5, "当期赎回金额", wcf_normalborder_s9));
		sheet.addCell(new Label(12,5, "当期净募集金额", wcf_normalborder_s9));
		sheet.addCell(new Label(13,4, "期末理财资金状况", wcf_normalborder_s9));
		sheet.addCell(new Label(13,5, "期末理财资金余额", wcf_normalborder_s9));
		sheet.addCell(new Label(14,5, "期末客户数", wcf_normalborder_s9));
		sheet.addCell(new Label(15,4, "期末理财投资损益状况", wcf_normalborder_s9));
		sheet.addCell(new Label(15,5, "期末理财投资总成本", wcf_normalborder_s9));
		sheet.addCell(new Label(16,5, "期末理财投资总价值", wcf_normalborder_s9));
		sheet.addCell(new Label(17,5, "未实现损益总额", wcf_normalborder_s9));
		sheet.addCell(new Label(18,5, "已实现损益总额", wcf_normalborder_s9));
		sheet.addCell(new Label(19,5, "年化收益率（%）", wcf_normalborder_s9));
		sheet.addCell(new Label(20,4, "理财资金初始募集金额", wcf_normalborder_s9));
		sheet.addCell(new Label(21,4, "理财资金到期实际兑付净额", wcf_normalborder_s9));
		sheet.addCell(new Label(22,4, "理财投资到期实际收回净额", wcf_normalborder_s9));
		sheet.addCell(new Label(23,4, "手续费收入", wcf_normalborder_s9));
		sheet.addCell(new Label(24,4, "客户端实际年化收益率（%）", wcf_normalborder_s9));
		sheet.addCell(new Label(25,4, "当期收到投诉件数", wcf_normalborder_s9));
		sheet.addCell(new Label(26,4, "期末未处理完成投诉总件数", wcf_normalborder_s9));
		sheet.addCell(new Label(27,4, "当期发生诉讼案件件数", wcf_normalborder_s9));
		sheet.addCell(new Label(28,4, "当期和解件数", wcf_normalborder_s9));
		sheet.addCell(new Label(29,4, "当期胜诉件数", wcf_normalborder_s9));
		sheet.addCell(new Label(30,4, "当期败诉件数", wcf_normalborder_s9));
		sheet.addCell(new Label(31,4, "期末未结案诉讼总件数", wcf_normalborder_s9));
		
		//插入动态数据
		int row = 6, num=1;//从第六行开始插入动态数据,序列值为1
		for(int i=0,len=listContent.size(); i<len; i++){
			Map<String, Object> map = listContent.get(i);
			int col=0;
			sheet.addCell(new Label(col, row, num+"", wcf_normalborder_s12));
			col++;
			for(String key : titleKey){
				if(col == 4 || (col >= 9 && col <= 13) || (col >= 15 && col <= 18) || (col >= 20 && col <= 23)){//保留两位小数的单元格
					if(map.containsKey(key)){
						sheet.addCell(new jxl.write.Number(col, row, Double.parseDouble(map.get(key)+""), wcf_normalborder_s10_nf2));
					}
				} else if(col == 14 || (col >= 25 && col <= 31)){//整数的单元格
					if(map.containsKey(key)){
						sheet.addCell(new jxl.write.Number(col, row, Double.parseDouble(map.get(key)+""), wcf_normalborder_s10_nf0));
					}
				} else if(map.containsKey(key)){
					sheet.addCell(new Label(col, row, map.get(key)+"", wcf_normalborder_s10));
				}
				col++;
			}
			//添加公式列
			sheet.addCell(new Formula(12, row, "K"+(row+1)+"-L"+(row+1), wcf_normalborder_s10_bgblue_nf2));
			sheet.addCell(new Formula(16, row, "P"+(row+1)+"+R"+(row+1)+"+S"+(row+1), wcf_normalborder_s10_bgblue_nf2));
			row++;
			num++;
		}
		
		//合计行
		sheet.addCell(new Label(0, row, "合计", wcf_bottomborder_s9));
		sheet.addCell(new Label(1, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Label(2, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Label(3, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Formula(4, row, "SUM(E7:E"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Label(5, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Label(6, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Label(7, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Label(8, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Formula(9, row, "SUM(J7:J"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(10, row, "SUM(K7:K"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(11, row, "SUM(L7:L"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(12, row, "K"+(row+1)+"-L"+(row+1), wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(13, row, "SUM(N7:N"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(14, row, "SUM(O7:O"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Formula(15, row, "SUM(P7:P"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(16, row, "SUM(Q7:Q"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(17, row, "SUM(R7:R"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(18, row, "SUM(S7:S"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Label(19, row, "", wcf_bottomborder_s10));
		sheet.addCell(new Formula(20, row, "SUM(U7:U"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(21, row, "SUM(V7:V"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(22, row, "SUM(W7:W"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Formula(23, row, "SUM(X7:X"+row+")", wcf_bottomborder_s10_bgblue_nf2));
		sheet.addCell(new Label(24, row, "", wcf_bottomborder_s10_bggray));
		sheet.addCell(new Formula(25, row, "SUM(Z7:Z"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Formula(26, row, "SUM(AA7:AA"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Formula(27, row, "SUM(AB7:AB"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Formula(28, row, "SUM(AC7:AC"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Formula(29, row, "SUM(AD7:AD"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Formula(30, row, "SUM(AE7:AE"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Formula(31, row, "SUM(AF7:AF"+row+")", wcf_bottomborder_s10_bgblue_nf0));
		sheet.addCell(new Label(32, row, "", wcf_bottomborder_s10_bggray));
		
		//填表人
		row++;
		sheet.mergeCells(0, row, 1, row);
		sheet.addCell(new Label(0, row, "填表人：", wcf_noborder_fs10));
		sheet.addCell(new Label(6, row, "复核人：", wcf_noborder_fs10));
		sheet.addCell(new Label(13, row, "负责人：", wcf_noborder_fs10));
		
		//版本号
		row++;
		sheet.addCell(new Label(1, row, "版本号:1010", wcf_noborder_s10));
		
		//标注
		row++;
		sheet.addCell(new Label(1, row, "", wcf_noborder_s10_bgblue));
		sheet.addCell(new Label(2, row, "蓝色底为含公式区域", wcf_noborder_s10));
		row++;
		sheet.addCell(new Label(1, row, "", wcf_noborder_s10_bggray));
		sheet.addCell(new Label(2, row, "无数据部分不填写数据", wcf_noborder_s10));
		
	}
	
	/**
	 * 标准单元格
	 * @throws WriteException 
	 */
	private static WritableCellFormat getCellModelForNormal(WritableFont font, Colour backgroud, Map borderStyle, NumberFormat nf) throws WriteException{
		WritableCellFormat wcf = null;
		if(nf != null){
			wcf = new WritableCellFormat(font, nf);
		}else{
			wcf = new WritableCellFormat(font);
		}
		if(borderStyle.containsKey("all")){
			wcf.setBorder(Border.ALL, (BorderLineStyle)borderStyle.get("all"));//边框
		} else if(borderStyle.containsKey("none")){
			//不设置边框
		} else {
			if(borderStyle.containsKey("top"))
				wcf.setBorder(Border.TOP, (BorderLineStyle)borderStyle.get("top"));
			if(borderStyle.containsKey("right"))
				wcf.setBorder(Border.RIGHT, (BorderLineStyle)borderStyle.get("right"));
			if(borderStyle.containsKey("bottom"))
				wcf.setBorder(Border.BOTTOM, (BorderLineStyle)borderStyle.get("bottom"));
			if(borderStyle.containsKey("left"))
				wcf.setBorder(Border.LEFT, (BorderLineStyle)borderStyle.get("left"));
		}
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//文字垂直对齐
		wcf.setAlignment(Alignment.CENTRE);//文字居中对齐
		wcf.setWrap(true);//是否换行
		if(backgroud != null)
			wcf.setBackground(backgroud);//背景色
		return wcf;
	}
	
	/**
	 * 合并单元格
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private static WritableSheet marginCellProcess(WritableSheet sheet, List<MergeCellBean> list) throws RowsExceededException, WriteException{
		if(list == null || list.size() <= 0)
			return sheet;
		
		for(MergeCellBean mergeCell : list){
			Integer leftRow = mergeCell.getLeftRow();
			Integer leftCol = mergeCell.getLeftCol();
			Integer rightRow = mergeCell.getRightRow();
			Integer rightCol = mergeCell.getRigetCol();
			
			sheet.mergeCells(leftCol, leftRow, rightCol, rightRow);
		}
		
		return sheet;
	}
	
	/**
	 * 这是要合并的单元格
	 */
	private static List<MergeCellBean> getMergeCellParams(){
		List<MergeCellBean> list = Lists.newArrayList();
		
		//设置要合并的单元格
		list.add(new MergeCellBean(0,0,0,32));
		list.add(new MergeCellBean(1,0,1,1));
		list.add(new MergeCellBean(2,0,5,0));
		list.add(new MergeCellBean(3,1,3,8));
		list.add(new MergeCellBean(3,9,3,12));
		list.add(new MergeCellBean(3,13,3,19));
		list.add(new MergeCellBean(3,20,3,24));
		list.add(new MergeCellBean(3,25,3,26));
		list.add(new MergeCellBean(3,27,3,31));
		list.add(new MergeCellBean(3,32,5,32));
		list.add(new MergeCellBean(4,1,5,1));
		list.add(new MergeCellBean(4,2,5,2));
		list.add(new MergeCellBean(4,3,5,3));
		list.add(new MergeCellBean(4,4,5,4));
		list.add(new MergeCellBean(4,5,5,5));
		list.add(new MergeCellBean(4,6,5,6));
		list.add(new MergeCellBean(4,7,5,7));
		list.add(new MergeCellBean(4,8,5,8));
		list.add(new MergeCellBean(4,9,5,9));
		list.add(new MergeCellBean(4,10,4,12));
		list.add(new MergeCellBean(4,13,4,14));
		list.add(new MergeCellBean(4,15,4,19));
		list.add(new MergeCellBean(4,20,5,20));
		list.add(new MergeCellBean(4,21,5,21));
		list.add(new MergeCellBean(4,22,5,22));
		list.add(new MergeCellBean(4,23,5,23));
		list.add(new MergeCellBean(4,24,5,24));
		list.add(new MergeCellBean(4,25,5,25));
		list.add(new MergeCellBean(4,26,5,26));
		list.add(new MergeCellBean(4,27,5,27));
		list.add(new MergeCellBean(4,28,5,28));
		list.add(new MergeCellBean(4,29,5,29));
		list.add(new MergeCellBean(4,30,5,30));
		
		return list;
		
	}
	
	private static void setWorkBook(WritableWorkbook workBook){
		//设置自定义颜色
		Color color = Color.decode("#FFE1FF");
		workBook.setColourRGB(Colour.BLUE2, color.getRed(), color.getGreen(), color.getBlue());
	}
	
	private static final Logger LOG = Logger.getLogger(PersonalFinanceTemplate.class);
}
