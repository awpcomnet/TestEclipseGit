package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import excelDemo.LearnExcel;
import excelDemo.User;

public class ExportExcelTest {
    public static void main(String[] args) {
        System.out.println("=============>Excel导出开始");

        try {
            String fileName = "客户资料.xls";

            // HttpServletResponse response = null;
            //
            // response.setContentType("application/vnd.ms-excel;charset=utf-8");
            // response.setHeader("Content-Disposition", "attachment;filename="
            // + new String(fileName.getBytes(),"iso-8859-1"));
            //
            // OutputStream os = response.getOutputStream();

            File f = new File("d:" + File.separator + fileName); // 声明File对象
            // 第2步、通过子类实例化父类对象
            OutputStream out = null; // 准备好一个输出的对象
            out = new FileOutputStream(f); // 通过对象多态性，进行实例化

            String[] Title = { "机构ID", "会员编号", "类别" };
            List<Object> li = new ArrayList<Object>();

            User user1 = new User("CCAV", "0", "x");
            User user2 = new User("KKLI", "1", "特");
            User user3 = new User("UFO", "2", "BO");
            li.add(user1);
            li.add(user2);
            li.add(user3);

            LearnExcel.exportExcel(out, fileName, Title, li);
        } catch (Exception e) {
            System.out.println("=============>Excel导出异常");
            e.printStackTrace();
        }

        System.out.println("=============>Excel导出结束");
    }

}