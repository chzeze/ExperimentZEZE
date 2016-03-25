package step3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.SSLException;

import org.omg.CORBA.TIMEOUT;

import Dao.PairDao;
import Dao.PropagationDao;
import Dao.TimeMoodDao;
import Dao.UserFeatureDao;
import bean.Pair;
import bean.Propagation;
import bean.UserFeature;
import bean.UserTimeMood;

/**
 * 
 * @ClassName: JudgePropagation
 * @Description: 运用模型，判断是否发生情感传播
 *               输出(目录：201511TO201602)：propagation.csv(格式：0/1,uid,post,friends,friendspost,TimeWindow)
 *               插入数据库表：propagation
 * @author zeze
 * @date 2016年3月23日 上午11:53:42
 *
 */
public class JudgePropagation {
	private static int cnt = 0;
	private static int Ucnt = 0;
	private static float[] ST = { 0, 0, 0, 0 };// 初始站点的总体情绪
	private static int[][] TimeList = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 2, 3 } };// Ti,Tj待选列表
	private static int Ti = 0;// Ti时刻
	private static int Tj = 0;// Tj时刻
	private static float U_Tjmood = 0;// 用户集U在Ti时刻的总体情绪
	private static float U_Timood = 0;// 用户集U在Tj时刻的总体情绪
	private static float user_Timood = 0;// 用户u在Ti时刻的总体情绪
	private static float user_Tjmood = 0;// 用户u在Tj时刻的总体情绪

	// 计算在T时刻站点的总体情绪
	public static float CntSiteMood(int T) {
		float mood = 0;
		int tmp = 0;
		List<UserTimeMood> userTimeMoodsList = new ArrayList<UserTimeMood>();
		TimeMoodDao timeMoodDao = new TimeMoodDao();
		userTimeMoodsList = timeMoodDao.QuerySiteAllMood(T);
		Iterator<UserTimeMood> userTimeMoodsList2 = userTimeMoodsList.iterator();
		UserTimeMood userTimeMood = new UserTimeMood();
		while (userTimeMoodsList2.hasNext()) {
			userTimeMood = (UserTimeMood) userTimeMoodsList2.next();
			mood += (float) userTimeMood.getMood();
			tmp++;
		}
		System.out.println("T" + T + "时刻，站点人数=" + tmp + ",总体情绪mood=" + (mood / (float) tmp));
		return mood / (float) tmp;
	}

	// 计算T时刻用户ID为uid的用户集的总体情绪
	public static float CntUMood(String Uid, int T) {
		float mood = 0;
		float tempMood = 0;
		int tempCnt = 0;
		PairDao pairDao = new PairDao();
		List<Pair> pairslist = new ArrayList<Pair>();// 创建list队列
		pairslist = pairDao.QueryUserPair(Uid);// 在pair中查询用户集组U
		// System.out.println("待查询用户集U:" + pairslist.size());

		if (pairslist.size() != 0) {// 用户组U不等于0
			Iterator<Pair> pairslist2 = pairslist.iterator();// 在iterator容器中迭代输出list队列
			Pair pair = new Pair();
			while (pairslist2.hasNext()) {
				pair = (Pair) pairslist2.next();
				String followid = pair.getFollow();// 得到用户集中的一个用户的uid
				tempMood = QueryUidMood(followid, T);
				if (tempMood != -4040) {
					mood += tempMood;// 查询该用户的情绪值
					tempCnt++;
				}

			}
		}
		System.out.println("在T" + T + "实际用户集U:" + tempCnt);
		return mood;
	}

	// 查询T时刻的用户uid的总体情绪
	public static float QueryUidMood(String Uid, int T) {
		float mood = 0;
		TimeMoodDao timeMoodDao = new TimeMoodDao();
		UserTimeMood userTimeMood = new UserTimeMood();
		userTimeMood = timeMoodDao.QueryUserTimeMood(Uid, T);

		if (userTimeMood.getId() != -404) {// 查询到情绪值
			// System.out.println(Uid + "在" +userTimeMood.getTime()+"的总体情绪="+
			// userTimeMood.getMood());
			mood = (float) userTimeMood.getMood();
		}
		else {
			return -4040;// 没有查询到Uid的情绪值
		}
		return mood;
	}

	public static void QueryMaxSubgraph() {

		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		String fileName="I:\\毕业设计\\数据集\\201511TO201602\\propagation.csv";

		String uid = null;// 用户u
		String post = null;// 发布的微博数
		String friends = null;// 用户的朋友数
		String friendspost = null;// 用户朋友的发布微博数
		
		float A = 0;
		float B = 0;
		float C = 0;

		try {
			fw = new FileWriter(new File(fileName), true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			

			UserFeatureDao userFeatureDao = new UserFeatureDao();// 新建Dao类
			List<UserFeature> userFeatureslist = new ArrayList<UserFeature>();// 创建list队列
			userFeatureslist = userFeatureDao.QueryUserFeature();// 查询最大子图maxsubraph中的所用用户的特征

			Iterator<UserFeature> userFeatureslist2 = userFeatureslist.iterator();// 在iterator容器中迭代输出list队列
			UserFeature userFeature = new UserFeature();

			while (userFeatureslist2.hasNext()) {
				userFeature = (UserFeature) userFeatureslist2.next();
				Ucnt = 0;

				// 取出最大子图中的一个uid
				uid = userFeature.getUid();// 用户u
				post = userFeature.getPost();// 发布的微博数
				friends = userFeature.getFriends();// 用户的朋友数
				friendspost = userFeature.getFriendspost();// 用户朋友的发布微博数
				
				PropagationDao propagationDao=new PropagationDao();

				System.out.println(cnt + ": 当前处理用户 ：" + uid);

				for (int i = 0; i < 6; i++) {

					Ti = TimeList[i][0];
					Tj = TimeList[i][1];

					user_Timood = QueryUidMood(uid, Ti);// 查询用户在Tj的总体情绪
					user_Tjmood = QueryUidMood(uid, Tj);// 查询用户在Tj的总体情绪

					if (user_Timood != -4040 && user_Tjmood != -4040) {// 查询到

						System.out.println("该用户在T" + Ti + "的总体情绪=" + user_Timood);
						System.out.println("该用户在T" + Tj + "的总体情绪=" + user_Tjmood);

						U_Timood = CntUMood(uid, Ti);// 查询用户集在Ti的总体情绪
						U_Tjmood = CntUMood(uid, Tj);// 查询用户集在Tj的总体情绪

						System.out.println("用户集U在T" + Ti + "时刻的总体情绪=" + U_Timood);
						System.out.println("用户集U在T" + Tj + "时刻的总体情绪=" + U_Tjmood);

						A = Math.abs(U_Timood - user_Tjmood);
					    B = Math.abs(ST[Ti] - user_Tjmood);
					    C = Math.abs(U_Timood - user_Timood);

						if (A <= B && A <= C) {// 用户uid在Tj发生了情感传播
							System.out.println("1" + "," + uid + "," + post + "," + friends + "," + friendspost + "," + Tj);// 打印特征
							
							//插入文件propagation.csv
							pw.write("1" + "," + uid + "," + post + "," + friends + "," + friendspost + "," + Tj+"\n");
							//插入数据库表propagation
							Propagation propagation=new Propagation("1", uid, post, friends, friendspost, Integer.toString(Tj));
							propagationDao.AddPropagation(propagation);
						}
						else {
							System.out.println("0" + "," + uid + "," + post + "," + friends + "," + friendspost + "," + Tj);// 打印特征
							
							//插入文件propagation.csv
							pw.write("0" + "," + uid + "," + post + "," + friends + "," + friendspost + "," + Tj+"\n");
							//插入数据库表propagation
							Propagation propagation=new Propagation("0", uid, post, friends, friendspost, Integer.toString(Tj));
							propagationDao.AddPropagation(propagation);
							
						}
					}
					else {
						System.err.println("没有查询到用户在T" + Ti + " T" + Tj + "情绪值");
					}
					System.out.println("");
				}
				System.out.println("");
				cnt++;
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (pw != null) {
				pw.close();
			}
			if (bw != null) {
				try {
					bw.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获取两个时间的毫秒差异
		System.out.println("开始运行");
		Date nowDate = new Date();

		// 初始化每个时刻站点总体情绪ST
		ST[0] = CntSiteMood(0);
		ST[1] = CntSiteMood(1);
		ST[2] = CntSiteMood(2);
		ST[3] = CntSiteMood(3);
		// System.out.println(ST[0] + " " + ST[1] + " " + ST[2] + " " + ST[3]);

		new JudgePropagation().QueryMaxSubgraph();

		System.out.println("已完成");
		Date enDate = new Date();
		long diff = enDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒
		long sec = diff % nd % nh % nm / ns;
		// 计算差多少毫秒
		long ms = diff % nd % nh % nm % ns;
		System.out.println(day + " 天  " + hour + " 小时  " + min + " 分钟  " + sec + " 秒 " + ms + " 毫秒");
	}

}
