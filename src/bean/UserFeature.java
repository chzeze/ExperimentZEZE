package bean;

public class UserFeature {
    private int id;
    private String uid;
  	private String post;
  	private String friends;
  	private String friendspost;
  	
  	public UserFeature() {
	}
  	  
	public UserFeature(int id, String uid, String post, String friends, String friendspost) {
		super();
		this.id = id;
		this.uid = uid;
		this.post = post;
		this.friends = friends;
		this.friendspost = friendspost;
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
