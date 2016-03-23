package bean;
/**
 * 
	* @ClassName: Pair 
	* @Description:用户和转发用户类  
	* @author zeze
	* @date 2016年3月23日 上午9:31:04 
	*
 */
public class Pair {
	
	private int id;
	private String user;
	private String follow;
	
	public Pair() {
	}
	
	

	public Pair(int id, String user, String follow) {
		super();
		this.id = id;
		this.user = user;
		this.follow = follow;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
	}
	
	
	
	
	
	
	

}
