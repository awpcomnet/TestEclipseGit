package excelDemo;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import excelTool.G60BusinessTemplate;

/**
 * @Description: 测试导出Excel另一种方式
 * @author 王航
 * @date 2015年12月31日 上午11:29:18
 */
public class G60Test {
	public static void main(String[] args) throws Exception {
		File fromFile = new File("E:\\workspace\\manage-common\\src\\main\\resources\\com\\jhcz\\manage\\common\\template\\G06.xls");
		File toFile = new File("E:\\G06.xls");
		Map<String, List<Object>> mapContent = Maps.newHashMap();
		//第4列 第5行数据
		List<Object> list = Lists.newArrayList();
		list.add("12");
		list.add("15");
		list.add("11.25");
		list.add("25");
		list.add("4443");
		list.add("998.25");
		list.add("11");
		list.add("11");
		mapContent.put("3|4", list);
		
		G60BusinessTemplate.exportExcel(fromFile, toFile, mapContent);
		
	}
}
