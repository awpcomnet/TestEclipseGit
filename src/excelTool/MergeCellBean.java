package excelTool;

/**
 * @Description: 合并单元格参数
 * @author 王航
 * @date 2015年12月30日 下午1:42:01
 */
public class MergeCellBean {
	/**
	 * 左上角单元格-横坐标
	 */
	private Integer leftRow;
	/**
	 * 左上角单元格-纵坐标
	 */
	private Integer leftCol;
	/**
	 * 右上角单元格-横坐标
	 */
	private Integer rightRow;
	/**
	 * 右上角单元格-纵坐标
	 */
	private Integer rigetCol;
	
	public MergeCellBean(Integer leftRow, Integer leftCol, Integer rightRow, Integer rightCol){
		this.leftRow = leftRow;
		this.leftCol = leftCol;
		this.rightRow = rightRow;
		this.rigetCol = rightCol;
	}

	public Integer getLeftRow() {
		return leftRow;
	}

	public Integer getLeftCol() {
		return leftCol;
	}

	public Integer getRightRow() {
		return rightRow;
	}

	public Integer getRigetCol() {
		return rigetCol;
	}

}
