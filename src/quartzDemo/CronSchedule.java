package quartzDemo;

import javax.print.attribute.IntegerSyntax;

/**
 * @Description: Cron时间表达式解析
 * @author 王航
 * @date 2015年12月28日 下午2:55:27
 */
public class CronSchedule {

	public static void main(String[] args) throws Exception {
		CronSchedule.analyzeCron("5/20 * * * * *");
	}
	
	public static void analyzeCron(String CronExpression) throws Exception{
		String resultMsg = "";
		String Expressions[] = CronExpression.split(" ");
		System.out.println(Expressions.length);
		
		if(Expressions.length < 6)
			System.out.println("表达式缺失");
		
		resultMsg += CronSchedule.getSecondExp(Expressions[0])+" ";
		resultMsg += CronSchedule.getMinuteExp(Expressions[1])+" ";
		resultMsg += CronSchedule.getHourExp(Expressions[2])+" ";
		System.out.println("处理结果:"+resultMsg);
		
	}
	
	
	/**
	 * 秒处理
	 */
	public static String getSecondExp(String Exp) throws Exception{
		String resultMsg = "";
		if("*".equals(Exp)){
			resultMsg = "每秒钟";
		}else if(Exp.contains(",")){
			resultMsg = "每";
			String secs[] = Exp.split(",");
			for(int i=0, len=secs.length; i<len; i++){
				if(Integer.parseInt(secs[i])<0 || Integer.parseInt(secs[i])>59)
					throw new Exception("表达式无效");
				resultMsg += secs[i]+"秒";
				if(i != len - 1)
					resultMsg += ",";
			}
		}else if(Exp.contains("-")){
			String secs[] = Exp.split("-");
			if(secs.length != 2)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0])<0 || Integer.parseInt(secs[0])>59)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[1])<0 || Integer.parseInt(secs[1])>59)
				throw new Exception("表达式无效");
			resultMsg = "从第"+secs[0]+"秒到"+secs[1]+"秒";
		}else if(Exp.contains("/")){
			String secs[] = Exp.split("/");
			if(secs.length != 2)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0])<0 || Integer.parseInt(secs[0])>59)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[1])<0 || Integer.parseInt(secs[1])>59)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0]) == 0){
				resultMsg = "每"+secs[1]+"秒";
			}else{
				resultMsg = "从第"+secs[0]+"秒开始，每"+secs[1]+"秒";
			}
		}else{
			int sec = Integer.parseInt(Exp);
			if(sec<0 || sec>59)
				throw new Exception("表达式无效");
			resultMsg = "第"+sec+"秒";
		}
		
		return resultMsg;
	}
	
	/**
	 * 分钟处理
	 * @throws Exception 
	 */
	public static String getMinuteExp(String Exp) throws Exception{
		String resultMsg = "";
		if("*".equals(Exp)){
			resultMsg = "每分钟";
		}else if(Exp.contains(",")){
			resultMsg = "每";
			String secs[] = Exp.split(",");
			for(int i=0, len=secs.length; i<len; i++){
				if(Integer.parseInt(secs[i])<0 || Integer.parseInt(secs[i])>59)
					throw new Exception("表达式无效");
				resultMsg += secs[i]+"分钟";
				if(i != len - 1)
					resultMsg += ",";
			}
		}else if(Exp.contains("-")){
			String secs[] = Exp.split("-");
			if(secs.length != 2)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0])<0 || Integer.parseInt(secs[0])>59)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[1])<0 || Integer.parseInt(secs[1])>59)
				throw new Exception("表达式无效");
			resultMsg = "从第"+secs[0]+"分钟到"+secs[1]+"分钟";
		}else if(Exp.contains("/")){
			String secs[] = Exp.split("/");
			if(secs.length != 2)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0])<0 || Integer.parseInt(secs[0])>59)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[1])<0 || Integer.parseInt(secs[1])>59)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0]) == 0){
				resultMsg = "每"+secs[1]+"分钟";
			}else{
				resultMsg = "从第"+secs[0]+"分钟开始，每"+secs[1]+"分钟";
			}
		}else{
			int sec = Integer.parseInt(Exp);
			if(sec<0 || sec>59)
				throw new Exception("表达式无效");
			resultMsg = "第"+sec+"分钟";
		}
		
		
		return resultMsg;
	}
	
	/**
	 * 小时处理
	 */
	public static String getHourExp(String Exp) throws Exception{
		String resultMsg = "";
		if("*".equals(Exp)){
			resultMsg = "每小时";
		}else if(Exp.contains(",")){
			resultMsg = "每";
			String secs[] = Exp.split(",");
			for(int i=0, len=secs.length; i<len; i++){
				if(Integer.parseInt(secs[i])<0 || Integer.parseInt(secs[i])>24)
					throw new Exception("表达式无效");
				resultMsg += secs[i]+"小时";
				if(i != len - 1)
					resultMsg += ",";
			}
		}else if(Exp.contains("-")){
			String secs[] = Exp.split("-");
			if(secs.length != 2)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0])<0 || Integer.parseInt(secs[0])>24)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[1])<0 || Integer.parseInt(secs[1])>24)
				throw new Exception("表达式无效");
			resultMsg = "从第"+secs[0]+"小时到"+secs[1]+"小时";
		}else if(Exp.contains("/")){
			String secs[] = Exp.split("/");
			if(secs.length != 2)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0])<0 || Integer.parseInt(secs[0])>24)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[1])<0 || Integer.parseInt(secs[1])>24)
				throw new Exception("表达式无效");
			if(Integer.parseInt(secs[0]) == 0){
				resultMsg = "每"+secs[1]+"小时";
			}else{
				resultMsg = "从第"+secs[0]+"小时开始，每"+secs[1]+"小时";
			}
		}else{
			int sec = Integer.parseInt(Exp);
			if(sec<0 || sec>24)
				throw new Exception("表达式无效");
			resultMsg = "第"+sec+"小时";
		}
		
		
		return resultMsg;
	}
}
