package bean;

public class Propagation {
	
	private int id;
	private String Tag;
    private String uid;
  	private String post;
  	private String friends;
  	private String friendspost;
  	private String timewindow;
  	
  	
  	
  	public Propagation(String tag, String uid, String post, String friends, String friendspost, String timewindow) {
		super();
		Tag = tag;
		this.uid = uid;
		this.post = post;
		this.friends = friends;
		this.friendspost = friendspost;
		this.timewindow = timewindow;
	}

	public String getTimewindow() {
		return timewindow;
	}

	public void setTimewindow(String timewindow) {
		this.timewindow = timewindow;
	}

	public Propagation() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return Tag;
	}

	public void setTag(String tag) {
		Tag = tag;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	public String getFriendspost() {
		return friendspost;
	}

	public void setFriendspost(String friendspost) {
		this.friendspost = friendspost;
	}
	
  	
  	

}
