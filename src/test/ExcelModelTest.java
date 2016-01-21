package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import excelDemo.User;
import excelTool.ExcelModel;


public class ExcelModelTest {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		System.out.println("Excel开始导出....");
		String fileName = "用户信息.xls";
		
		LinkedHashMap title = new LinkedHashMap();
		title.put("org", "组织名称");
		title.put("userId", "用户ID");
		title.put("type", "用户类型");
		
		System.out.println(title);
		
		List<Object> listContent = new ArrayList<Object>();
		for(int i=0; i<500000; i++){
			User user = new User("CCAV"+i, i+"", "x");
			listContent.add(user);
		}
		
		try {
			File f = new File("f:" + File.separator + fileName);
			OutputStream os = new FileOutputStream(f);
			ExcelModel.exportExcelForSingleSheet(os, "用户信息", title, listContent);
		} catch (Exception e) {
			System.out.println("Excel开始导出失败，原因："+e.toString());
		}
		
		System.out.println("Excel开始导出完毕");
		long endTime = System.currentTimeMillis();
		System.out.println("耗时："+(endTime-startTime) +"s");
	}
}
