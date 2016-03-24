package step1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import util.FileWriteUtil;

/**
 * 
 * @ClassName: MergeFriendPost
 * @Description:计算合并每个用户朋友的发帖数
 *              输入(目录：DisJoint)：user_post.csv
 *                 (目录：FilterPairUID):userid_pair.csv
 *              输出(目录：DisJoint)：user_friends_post.txt(格式：uid+friends_post)
 * @author zeze
 * @date 2016年3月18日 下午12:59:04
 *
 */
public class CntFriendPost {
	private static String src0 = "I:\\毕业设计\\数据集\\201511TO201602\\FilterPairUID\\userid_pair.csv";
	private static String src1 = "I:\\毕业设计\\数据集\\201511TO201602\\Disjoint\\user_post.txt";
	private static Map<String, Integer> FriendsPost = new LinkedHashMap<String, Integer>();
	private static Map<String, Integer> UserPost = new LinkedHashMap<String, Integer>();
	private static StringBuffer sBuilder = new StringBuffer();
	private static String destfile = "I:\\毕业设计\\数据集\\201511TO201602\\Disjoint\\user_friendspost.txt";

	public static void main(String[] args) {
		
		
		new CntFriendPost().UserPostRead();
		new CntFriendPost().cnt();
		
		// 遍历HashMap
		for (Map.Entry<String, Integer> entry : FriendsPost.entrySet()) {// 遍历所有用户列表，打印用户新编号和Post数目
			System.out.println(entry.getKey() + "||" + entry.getValue());
			sBuilder.append(entry.getKey() + "||" + entry.getValue() + "\n");
		}
		FileWriteUtil.WriteDocument(destfile, sBuilder.toString());
		System.out.println("用户总数："+FriendsPost.size());
	}
	
	public void UserPostRead(){
		BufferedReader bReader = null;
		FileReader reader = null;
		String messDir = src1;
		try {
			reader = new FileReader(new File(messDir));
			bReader = new BufferedReader(reader);
			String string = null;
			String[] ss = null;
			int tempCnt;
			while ((string = bReader.readLine()) != null) {
				ss=string.split("\\|\\|");
				UserPost.put(ss[0], Integer.parseInt(ss[1]));//将用户发帖数放入HashMap中
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (bReader != null) {
				try {
					bReader.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
			
	}

	public void cnt() {
		BufferedReader bReader = null;
		FileReader reader = null;
		String messDir = src0;
		try {
			reader = new FileReader(new File(messDir));
			bReader = new BufferedReader(reader);
			String string = null;
			String[] ss = null;
			int tempCnt;
			while ((string = bReader.readLine()) != null) {
				ss = string.split("\\|\\|");
				if (FriendsPost.containsKey(ss[0])) {// 存在用户名
					tempCnt = UserPost.get(ss[1])+FriendsPost.get(ss[0]);
					FriendsPost.put(ss[0], tempCnt);// 数目加1
				}
				else {
					FriendsPost.put(ss[0], UserPost.get(ss[1]));
				}
				if (FriendsPost.containsKey(ss[1])) {// 存在用户名
					tempCnt = UserPost.get(ss[0])+FriendsPost.get(ss[1]);//朋友发帖
					FriendsPost.put(ss[1], tempCnt);// 数目加1
				}
				else {
					FriendsPost.put(ss[1], UserPost.get(ss[0]));
				}
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (bReader != null) {
				try {
					bReader.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}
}
