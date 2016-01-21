package excelDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import excelTool.MergeCellBean;
import excelTool.PersonalFinanceTemplate;

/**
 * 
 * @Description: 生成表格测试
 * @author 王航
 * @date 2015年12月30日 下午3:17:11
 */
public class ExcelTest {
	public static void main(String[] args) throws FileNotFoundException {
		List<String> list = Lists.newArrayList();
		List<Map<String, Object>> listContent = Lists.newArrayList();
		
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("G");
		list.add("H");
		list.add("I");
		list.add("J");
		list.add("K");
		list.add("L");
		list.add("M");
		list.add("N");
		list.add("O");
		list.add("P");
		list.add("Q");
		list.add("R");
		list.add("S");
		list.add("T");
		list.add("U");
		list.add("V");
		list.add("W");
		list.add("X");
		list.add("Y");
		list.add("Z");
		list.add("AA");
		list.add("AB");
		list.add("AC");
		list.add("AD");
		list.add("AE");
		list.add("AF");
		
		Map<String, Object> map = Maps.newHashMap();
		for(int i=0, len=list.size(); i< len; i++){
			map.put(list.get(i)+"", i);
		}
		listContent.add(map);
		Map<String, Object> map1 = Maps.newHashMap();
		for(int i=0, len=list.size(); i< len; i++){
			map1.put(list.get(i)+"", i+10.8);
		}
		listContent.add(map1);
		
		File f = new File("E:\\aa.xls");
		FileOutputStream os = new FileOutputStream(f);
		PersonalFinanceTemplate.exportExcel(os, "sheet1", "S61商业银行个人理财产品统计表", "报送口径：境内分地区数据", "报表日期：年月  ", list, listContent);
		
	}
}
