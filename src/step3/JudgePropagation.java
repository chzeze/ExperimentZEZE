package step3;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.omg.CORBA.TIMEOUT;

import Dao.PairDao;
import Dao.TimeMoodDao;
import Dao.UserFeatureDao;
import bean.Pair;
import bean.UserFeature;
import bean.UserTimeMood;

/**
 * 
 * @ClassName: JudgePropagation
 * @Description: 运用模型，判断是否发生情感传播
 * @author zeze
 * @date 2016年3月23日 上午11:53:42
 *
 */
public class JudgePropagation {
	private static int cnt = 0;
	private static int Ucnt = 0;
	private static float[] ST={100,100,100,100};//初始站点的总体情绪

	// 计算T时刻用户ID为uid的用户集的总体情绪
	public static float CntUMood(String Uid, int T) {
		float mood = 0;
		float tempMood = 0;
		int tempCnt=0;
		PairDao pairDao = new PairDao();
		List<Pair> pairslist = new ArrayList<Pair>();// 创建list队列
		pairslist = pairDao.QueryUserPair(Uid);// 在pair中查询用户集组U
		System.out.println("待查询用户集U:" + pairslist.size());

		if (pairslist.size() != 0) {// 用户组U不等于0
			Iterator<Pair> pairslist2 = pairslist.iterator();// 在iterator容器中迭代输出list队列
			Pair pair = new Pair();
			while (pairslist2.hasNext()) {
				pair = (Pair) pairslist2.next();
				String followid = pair.getFollow();// 得到用户集中的一个用户的uid				
				tempMood = QueryUidMood(followid, T);
				if (tempMood != -4040){
					mood += tempMood;// 查询该用户的情绪值
					tempCnt++;
					//System.out.println(tempCnt+":"+followid);
				}
				
			}
		}
		System.out.println("实际用户集U:"+tempCnt);
		return mood;
	}

	// 查询T时刻的用户uid的总体情绪
	public static float QueryUidMood(String Uid, int T) {
		float mood = 0;
		TimeMoodDao timeMoodDao = new TimeMoodDao();
		UserTimeMood userTimeMood = new UserTimeMood();
		userTimeMood = timeMoodDao.QueryUserTimeMood(Uid, T);

		if (userTimeMood.getId() != -404) {// 查询到情绪值
			//System.out.println(Uid + "在" +userTimeMood.getTime()+"的总体情绪="+ userTimeMood.getMood());
			mood = (float) userTimeMood.getMood();
		}
		else {
			return -4040;// 没有查询到Uid的情绪值
		}
		return mood;
	}

	public static void QueryMaxSubgraph() {

		UserFeatureDao userFeatureDao = new UserFeatureDao();// 新建Dao类
		List<UserFeature> userFeatureslist = new ArrayList<UserFeature>();// 创建list队列
		userFeatureslist = userFeatureDao.QueryUserFeature();// 查询最大子图maxsubraph中的所用用户的特征

		Iterator<UserFeature> userFeatureslist2 = userFeatureslist.iterator();// 在iterator容器中迭代输出list队列
		UserFeature userFeature = new UserFeature();

		while (userFeatureslist2.hasNext()) {
			userFeature = (UserFeature) userFeatureslist2.next();
			Ucnt = 0;
			// 取出最大子图中的一个uid
			String uid = userFeature.getUid();// 用户u
			
			int Ti=0;
			int Tj=2;
			float U_Tjmood=0;
			float U_Timood=0;
			float user_Timood=0;
			float user_Tjmood=0;
						
            System.out.println(cnt+": 当前处理用户 ："+uid);
            
            user_Tjmood=QueryUidMood(uid, Tj);
            if(user_Tjmood!=-4040){//查询到
            	System.out.println("该用户在T" +Tj+"的总体情绪="+ user_Tjmood);
            }
            else {
				System.err.println("没有查询到用户在T"+Tj+"情绪值");
			}
            
            U_Tjmood=CntUMood(uid, Tj);    
            System.out.println("用户集U在T"+Tj+"时刻的总体情绪=" + U_Tjmood);//计算T时刻用户ID为uid的用户集的总体情绪
            
			// 查询该uid用户的在所有情绪窗口中的情绪值
            /*List<UserTimeMood> userTimeMoodsList = new ArrayList<UserTimeMood>();
			TimeMoodDao timeMoodDao = new TimeMoodDao();
			userTimeMoodsList = timeMoodDao.QueryUserAllMood(uid);
			Iterator<UserTimeMood> userTimeMoodList2 = userTimeMoodsList.iterator();
			UserTimeMood userTimeMood = new UserTimeMood();
			while (userTimeMoodList2.hasNext()) {
				userTimeMood=(UserTimeMood)userTimeMoodList2.next();
				System.out.println(userTimeMood.getUid()+"||"+userTimeMood.getMood()+"||"+userTimeMood.getTime());
				
			}*/
			System.out.println("");
			

			if (cnt >= 3)
				break;
			cnt++;

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
