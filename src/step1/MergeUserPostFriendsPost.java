package step1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import util.FileWriteUtil;

/**
 * 
	* @ClassName: MergeUserPostFriendsPost 
	* @Description:  合并用户发帖数和朋友发布数
	*                输入(目录：DisJoint)：user_post.txt;
	                    (目录：DisJoint): user_friends_friendspost.txt;
                                                             输出(目录：DisJoint)：user_post_friends_friendspost.txt;(格式:uid+post+friends+FriendsPost)

	* @author zeze
	* @date 2016年3月18日 下午3:40:22 
	*
 */
public class MergeUserPostFriendsPost {
	private static String dir = "I:\\毕业设计\\数据集\\201511TO201602\\Disjoint\\";
	private static String src0 = dir+"user_post.txt";
	private static String src1 = dir+"user_friends_friendspost.txt";
	private static StringBuffer sBuilder = new StringBuffer();
	private static String destfile =dir+"user_post_friends_friendspost.txt";
	private static Map<String, Integer> UserPost = new LinkedHashMap<String, Integer>();
	private static int cnt=0;

	public static void main(String[] args) {
		System.out.println("Start!");
		new MergeUserPostFriendsPost().UserPostRead();
		new MergeUserPostFriendsPost().merge();
		FileWriteUtil.WriteDocument(destfile, sBuilder.toString());
		System.out.println("总数："+cnt);
		System.out.println("End!");
	}
	
	public void UserPostRead(){
		BufferedReader bReader = null;
		FileReader reader = null;
		String messDir = src0;
		try {
			reader = new FileReader(new File(messDir));
			bReader = new BufferedReader(reader);
			String string = null;
			String[] ss = null;
			while ((string = bReader.readLine()) != null) {
				ss=string.split("\\|\\|");
				UserPost.put(ss[0], Integer.parseInt(ss[1]));//将用户发帖数数目放入HashMap中
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

	public void merge() {
		BufferedReader br = null;
		FileReader reader = null;

		try {
			reader = new FileReader(new File(src1));
			br = new BufferedReader(reader);
				
			String str = null;
			String[] ss = null;
			
			while ((str = br.readLine()) != null) {
				ss=str.split("\\|\\|");
				if(UserPost.containsKey(ss[0])){
					sBuilder.append(ss[0]+"||"+UserPost.get(ss[0])+"||"+ss[1]+"||"+ss[2]+"\n");
					System.out.println(ss[0]+"||"+UserPost.get(ss[0])+"||"+ss[1]+"||"+ss[2]);
					cnt++;
				}
				 
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
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
