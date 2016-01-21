package excelDemo;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

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

public class LearnExcel {

    /**************************
     * @param fileName Excel文件名称
     * @param Title Excel文件第一行列标题集合
     * @param listContent Excel文件正文数据集合
     * @return
     */
    public final static String exportExcel(OutputStream os, String fileName, String[] Title, List<Object> listContent){
        String result = "Excel文件导出成功!";
        //开始输出到Excel
        WritableWorkbook workbook = null;
        try {
            /*//定义输出流，用于打开保存对话框  ------开始
            HttpServletResponse response = ServletActionContext.getResponse();
            OutputStream os = response.getOutputStream();//取的输出流
            response.reset();//清空输出流
            //设定输出文件头
            response.setHeader("Context-disposition", "attachment;fileName="+new String(fileName.getBytes("GB2312"), "ISO8859-1"));

            response.setContentType("application/msexcel");//定义输出类型
            //定义输出流，用于打开保存对话框  ------结束
*/            
            /********创建工作薄**********/
            workbook = Workbook.createWorkbook(os);

            /********创建工作表************/
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            /********设置纵横打印（默认为纵打印）、打印纸******************/
            jxl.SheetSettings sheetset = sheet.getSettings();
            sheetset.setProtected(false);


            /*********设置单元格字体****************/
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);//普通字体
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);//黑体
            //WritableFont font3=new WritableFont(WritableFont.createFont("宋体"),12,WritableFont.NO_BOLD );

            /**********一下设置是那种单元格样式，灵活备用*******************/
            //用于标题居中
            WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);//使用黑子笔
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);//所有边框，细线
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);//文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE);//文字居中对齐
            wcf_center.setBackground(Colour.YELLOW);
            wcf_center.setWrap(false);

            //用于正文居左
            WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);//使用普通笔
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN);//无边框
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);//文字垂直对齐
            wcf_left.setAlignment(Alignment.LEFT);//文字水平居左
            wcf_left.setWrap(false);//文字是否换行

            //用于正文居右
            WritableCellFormat wcf_right = new WritableCellFormat(NormalFont);//使用普通笔
            wcf_right.setBorder(Border.LEFT, BorderLineStyle.THICK);//左边框   粗体
            wcf_right.setVerticalAlignment(VerticalAlignment.CENTRE);//文字垂直对齐
            wcf_right.setAlignment(Alignment.RIGHT);//文字水平居右
            wcf_right.setWrap(true);//文字是否换行

            /*********以下是Excel正文数据*************/
            //sheet.mergeCells(0, 0, colWidth, 0);
            //sheet.addCell(new Label(0,0,"XX报表", wcf_center));

            /**********以下是Excel第一行列标题*****************************/
            for(int i=0; i<Title.length; i++){
                /**
                 * 添加单元格，坐标示意==> 列   行    内容     格式  
                 * 其中  列  和   行   起始计数  0
                 */
                sheet.addCell(new Label(i, 0 , Title[i], wcf_center));
            }

            /***********以下是Excel正文数据******************/
            Field[] fields = null;
            int i=1;
            for(Object obj: listContent){
                fields = obj.getClass().getDeclaredFields();
                int j=0;
                for(Field v : fields){
                    v.setAccessible(true);
                    Object va = v.get(obj);
                    if(va == null){
                        va = "";
                    }
                    sheet.addCell(new Label(j, i, va.toString(), wcf_left));
                    j++;
                }
                i++;
            }

            /***
             * 循环中可以控制每一定量数据进行一次输出
             */
//            os.flush();

            /**********将以上缓存中的内容写到Excel文件中**************/
            workbook.write();




        } catch (Exception e) {
            result = "Excel文件导出失败，原因："+e.toString();
            System.out.println(result);
            e.printStackTrace();
        } finally {
            /**********关闭文件***********************/
            if(workbook != null){
                try {
                    workbook.close();
                } catch (Exception e2) {
                }
            }
        }

        return result;
    }
}