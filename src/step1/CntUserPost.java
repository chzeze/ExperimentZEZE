package step1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import util.FileWriteUtil;

/**
 * 
 * @ClassName: CntUserPost
 * @Description:计算用户发布帖子的数量
 *              输入(目录：FilterPairUID)：msgiduserid_nameX.csv
 *              输出(目录：DisJoint)：user_post.txt
 * @author zeze
 * @date 2016年3月18日 下午12:59:04
 *
 */
public class CntUserPost {
	private static String path = "I:\\毕业设计\\数据集\\201511TO201602\\FilterPairUID\\";
	private static Map<String, Integer> UserCnt = new LinkedHashMap<String, Integer>();
	private static StringBuffer sBuilder = new StringBuffer();
	private static String destfile = "I:\\毕业设计\\数据集\\201511TO201602\\Disjoint\\user_post.txt";

	public static void main(String[] args) {
		File file = new File(path);
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.getName().startsWith("msgiduserid_name")) {
				System.out.println(f.getName());
				new CntUserPost().cnt(f.getName());
			}
		}
		// 遍历HashMap

		for (Map.Entry<String, Integer> entry : UserCnt.entrySet()) {// 遍历所有用户列表，打印用户新编号和Post数目
			System.out.println(entry.getKey() + "||" + entry.getValue());
			sBuilder.append(entry.getKey() + "||" + entry.getValue()+"\n");
		}
		FileWriteUtil.WriteDocument(destfile, sBuilder.toString());
		System.out.println("用户总数："+UserCnt.size());
	}

	public void cnt(String file) {
		BufferedReader bReader = null;
		FileReader reader = null;
		String messDir = path + file;
		try {
			reader = new FileReader(new File(messDir));
			bReader = new BufferedReader(reader);
			String string = null;
			String[] ss = null;
			int tempCnt;
			while ((string = bReader.readLine()) != null) {
				ss = string.split("\\}\\|\\|\\}");
				if (UserCnt.containsKey(ss[1])) {// 存在用户名
					tempCnt = UserCnt.get(ss[1]) + 1;
					UserCnt.put(ss[1], tempCnt);// 数目加1
				}
				else {
					UserCnt.put(ss[1], 1);
				}
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
