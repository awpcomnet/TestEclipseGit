package jodaTimeDemo;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * 
 * @Description: 使用joda time生成工作日历
 * @author 王航
 * @date 2015年12月11日 上午10:34:50
 */
public class WorkDayCalendar {
	public static final String DATE_FORMATE = "yyyy年MM月dd日 HH:mm:ss";
	
	
	public static void main(String[] args) {
		DateTime dateTime = WorkDayCalendar.getNextYearDate();
		System.out.println(dateTime.toString(WorkDayCalendar.DATE_FORMATE));
		
//		boolean isLeapYear = WorkDayCalendar.isLeapYear(dateTime.getYear());
//		int sumDay = 365;
//		
//		if(isLeapYear)
//			sumDay = 366;
		
		
		int year = dateTime.getYear();
		
		while(year == dateTime.getYear()){
			System.out.println(dateTime.getYear()+"年"+dateTime.getMonthOfYear()+"月"+dateTime.getDayOfMonth()+"日      周"+dateTime.getDayOfWeek());
			dateTime = dateTime.plusDays(1);
		}
		
		System.out.println(dateTime.getYear()+"年是否为闰年："+ (WorkDayCalendar.isLeapYear(dateTime.getYear()) ? "是" : "否"));
			
	}
	
	/**
	 * 获取下一年的DateTime对象
	 */
	public static DateTime getNextYearDate(){
		DateTime dateTime = new DateTime(new Date());
		Integer year = dateTime.getYear();
		return new DateTime(year, 1, 1, 0, 0, 0, 0);
	}
	
	/**
	 * 判断是否为闰年
	 */
	public static boolean isLeapYear(Integer year){
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
		    return true;
		}else{
		    return false;
		}
	}
}
