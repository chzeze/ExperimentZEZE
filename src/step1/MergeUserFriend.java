package step1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import util.FileWriteUtil;

/**
 * 
 * @ClassName: MergeUserFriend
 * @Description: 合并用户朋友数和朋友发布数 
 *               输入(目录：DisJoint)：user_friends.txt;
	                (目录：DisJoint):user_friendspost.txt;
                                                  输出(目录：DisJoint)：user_friends_friendspost.txt;(格式:uid+friends+FriendsPost)

 * @author zeze
 * @date 2016年3月18日 下午3:05:22
 *
 */
public class MergeUserFriend {
	private static String dir = "I:\\毕业设计\\数据集\\201511TO201602\\Disjoint\\";
	private static String src0 = dir + "user_friends.txt";
	private static String src1 = dir + "user_friendspost.txt";
	private static StringBuffer sBuilder = new StringBuffer();
	private static String destfile = dir + "user_friends_friendspost.txt";
	private static int cnt = 0;

	public static void main(String[] args) {
		System.out.println("Start!");
		new MergeUserFriend().merge();
		FileWriteUtil.WriteDocument(destfile, sBuilder.toString());
		System.out.println("总数：" + cnt);
		System.out.println("End!");
	}

	public void merge() {
		BufferedReader br0 = null;
		FileReader reader0 = null;

		BufferedReader br1 = null;
		FileReader reader1 = null;
		try {
			reader0 = new FileReader(new File(src0));
			br0 = new BufferedReader(reader0);

			reader1 = new FileReader(new File(src1));
			br1 = new BufferedReader(reader1);

			String sFriends = null;
			String sFriendsPost = null;
			String[] ssFriends = null;
			String[] ssFriendsPost = null;
			int tempCnt;

			while ((sFriends = br0.readLine()) != null) {
				ssFriends = sFriends.split("\\|\\|");
				sFriendsPost = br1.readLine();
				ssFriendsPost = sFriendsPost.split("\\|\\|");
				// System.out.println(ssFriends[0]+" "+ssFriendsPost[0]);

				if (ssFriends[0].equals(ssFriendsPost[0])) {
					sBuilder.append(ssFriends[0] + "||" + ssFriends[1] + "||" + ssFriendsPost[1] + "\n");
					System.out.println(cnt + ssFriends[0] + "||" + ssFriends[1] + "||" + ssFriendsPost[1]);
					cnt++;
				}
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (br0 != null) {
				try {
					br0.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if (reader0 != null) {
				try {
					reader0.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}
}
