package step3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @ClassName: MergeUserMood
 * @Description: 输入：user_moodX.csv；
 *               MaxSubgraph_user_post_friends_friendspost.txt
 *               输出：UserTimeMood.txt;
 * @author zeze
 * @date 2016年3月22日 下午4:30:47
 *
 */
public class MergeUserMood {
	private static String dir = "I:\\毕业设计\\数据集\\201511TO201602\\";
	private static String scr0 = dir + "FilterMood\\";
	private static String scr1 = dir + "Disjoint\\MaxSubgraph_user_post_friends_friendspost.txt";
	private static String[] fileNameIn = new String[] { "user_mood0.csv", "user_mood1.csv", "user_mood2.csv", "user_mood3.csv" };
	private static Map<String, Integer> UserCnt = new LinkedHashMap<String, Integer>();

	public static void main(String args[]) {
		for (int i = 0; i < 4; i++) {
			BufferedReader br = null;
			FileReader reader = null;
			try {
				reader = new FileReader(new File(scr0 + fileNameIn[i]));
				br = new BufferedReader(reader);
				String str = null;
				String[] ss = null;
				int tempCnt = 0;
				while ((str = br.readLine()) != null) {
					ss = str.split("\\|\\|");
					if (UserCnt.containsKey(ss[0])) {// 存在用户名
						tempCnt = UserCnt.get(ss[0]) + Integer.parseInt(ss[1]);// 加上该条微博的情绪值
						UserCnt.put(ss[0], tempCnt);
					}
					else {
						tempCnt = Integer.parseInt(ss[1]);
						// System.out.println(tempCnt);
						UserCnt.put(ss[0], tempCnt);
					}
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
