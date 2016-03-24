package step1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import Dao.UserFeatureDao;
import bean.UserFeature;
import util.FileWriteUtil;

/**
 * 
 * @ClassName: FilterMaxSubgraphUser
 * @Description: 过滤不在最大子图中的用户信息,并且用户发布信息大于5条，朋友数大于5
 *               输入(目录：DisJoint):user_post_friends_friendspost.txt;
                    (目录：DisJoint): maximum_subgraph_user_name_id.txt;
                                                  输出：MaxSubgraph_user_post_friends_friendspost.txt;
                     (格式:uid+post+friends+FriendsPost)
                                                  插入数据库表：maxsubgraph

 * @author zeze
 * @date 2016年3月18日 下午3:40:22
 *
 */
public class FilterMaxSubgraphUser {
	private static String dir = "I:\\毕业设计\\数据集\\201511TO201602\\Disjoint\\";
	private static String src0 = dir + "maximum_subgraph_user_name_id.txt";
	private static String src1 = dir + "user_post_friends_friendspost.txt";
	private static StringBuffer sBuilder = new StringBuffer();
	private static String destfile = dir + "MaxSubgraph_user_post_friends_friendspost.txt";
	private static Map<String, Integer> UserPost = new LinkedHashMap<String, Integer>();
	private static int cnt = 0;

	public static void main(String[] args) {
		System.out.println("Start!");
		new FilterMaxSubgraphUser().UserPostRead();
		new FilterMaxSubgraphUser().merge();
		FileWriteUtil.WriteDocument(destfile, sBuilder.toString());
		System.out.println("总数：" + cnt);
		System.out.println("End!");
	}

	public void UserPostRead() {
		BufferedReader bReader = null;
		FileReader reader = null;
		String messDir = src0;
		try {
			reader = new FileReader(new File(messDir));
			bReader = new BufferedReader(reader);
			String string = null;
			String[] ss = null;
			while ((string = bReader.readLine()) != null) {
				ss = string.split("\\|\\|");
				UserPost.put(ss[0], Integer.parseInt(ss[1]));// 将最大子图中的用户放入HashMap中
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
				ss = str.split("\\|\\|");
				if (UserPost.containsKey(ss[0])) {// 判断是否在最大子图中
					// 格式：uid+post+friends+FriendsPost
					// 并且用户发布信息大于5条，朋友数大于5
					if (Integer.parseInt(ss[1]) >= 5 && Integer.parseInt(ss[2]) >= 5) {
						sBuilder.append(ss[0] + "||" + ss[1] + "||" + ss[2] + "||" + ss[3] + "\n");
						System.out.println(ss[0] + "||" + ss[1] + "||" + ss[2] + "||" + ss[3]);
						cnt++;
						
						UserFeature userFeature=new UserFeature();
						userFeature.setUid(ss[0]);
						userFeature.setPost(ss[1]);
						userFeature.setFriends(ss[2]);
						userFeature.setFriendspost(ss[3]);
						
						UserFeatureDao userFeatureDao=new UserFeatureDao();
						userFeatureDao.AdduserFeature(userFeature);
					}
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
