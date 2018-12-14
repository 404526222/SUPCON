package pojo;

public class InputDTO {
	private String start_time;
	private String end_time;
	private int acs_id;
	private int object;	
	private int frequency;
	
	public String getStartTime() {
		return start_time;
	}
	public String getEndTime() {
		return end_time;
	}
	public int getAcsId() {
		return acs_id;
	}
	public int getObject() {
		return object;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setStartTime(String start_time) {
		this.start_time=start_time;
	}
	public void setEndTime(String end_time) {
		this.end_time=end_time;
	}
	public void setAcsId(int acs_id) {
		this.acs_id=acs_id;
	}
	
	/**
	 * @param object
	 * 1 means entrance
	 * 2 means lane
	 */
	public void setObject(int object) {
		this.object=object;
	}
	
	/**
	 * @param frequency
	 * 0 means signal cycle
	 * 5 means 5 minutes
	 * 10 means 10 minutes
	 * 15 means 15 minutes
	 * 30 means 30 minutes
	 * 1 means 1 hour
	 */
	public void setFrequency(int frequency) {
		this.frequency=frequency;
	}
}
