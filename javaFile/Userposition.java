public class Userposition {
	private Integer upid;
	private String userid;
	private Float longitude;
	private Float latitude;
	private java.util.Date happentime;
	
	public Integer getUpid() {
		return upid;
	}

	public void setUpid(Integer upid) {
		this.upid = upid;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	
	public java.util.Date getHappentime() {
		return happentime;
	}

	public void setHappentime(java.util.Date happentime) {
		this.happentime = happentime;
	}
}