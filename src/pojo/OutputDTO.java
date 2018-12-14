package pojo;

public class OutputDTO {
	private String start_time;
	private int acs_id;
	private int entrance_id;
	private int lane_id;
	private int passing_vehicle_number;
	
	public String getStartTime() {
		return start_time;
	}
	public int getAcsId() {
		return acs_id;
	}
	public int getEntranceId() {
		return entrance_id;
	}
	public int getLaneId() {
		return lane_id;
	}
	public int getPassingVehicleNumber() {
		return passing_vehicle_number;
	}
	public void setStartTime(String start_time) {
		this.start_time=start_time;
	}
	public void setAcsId(int acs_id) {
		this.acs_id=acs_id;
	}
	public void setEntranceId(int entrance_id) {
		this.entrance_id=entrance_id;
	}
	public void setLaneId(int lane_id) {
		this.lane_id=lane_id;
	}
	public void setPassingVehicleNumber(int passing_vehicle_number) {
		this.passing_vehicle_number=passing_vehicle_number;
	}
	
}
