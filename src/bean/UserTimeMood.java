package bean;

public class UserTimeMood {

	private int id;
	private String uid;
	private int mood;
	private int time;

	public UserTimeMood() {
	}

	public UserTimeMood(int id, String uid, int mood, int time) {
		super();
		this.id = id;
		this.uid = uid;
		this.mood = mood;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getMood() {
		return mood;
	}

	public void setMood(int mood) {
		this.mood = mood;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
