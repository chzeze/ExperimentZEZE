package step3;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

	public static void main(String[] args) {

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获取两个时间的毫秒差异
		System.out.println("开始运行");
		Date nowDate = new Date();

		int cnt = 1;
		UserFeatureDao userFeatureDao = new UserFeatureDao();// 新建Dao类
		List<UserFeature> userFeatureslist = new ArrayList<UserFeature>();// 创建list队列
		userFeatureslist = userFeatureDao.QueryUserFeature();// 查询最大子图maxsubraph中的所用用户的特征

		Iterator<UserFeature> userFeatureslist2 = userFeatureslist.iterator();// 在iterator容器中迭代输出list队列
		UserFeature userFeature = new UserFeature();
		while (userFeatureslist2.hasNext()) {
			userFeature = (UserFeature) userFeatureslist2.next();
			// System.out.println(cnt+":uid:"+userFeature.getUid()+";post:"+userFeature.getPost()+";friends:"+userFeature.getFriends()+";friendspost:"+userFeature.getFriendspost());

			// 拿出最大子图中的一个uid
			String uid = userFeature.getUid();// 用户u

			Pair pair = new Pair();
			pair.setUser(uid);

			PairDao pairDao = new PairDao();
			List<Pair> pairslist = new ArrayList<Pair>();// 创建list队列
			pairslist = pairDao.QueryUserPair(pair);// 在pair中查询用户集组U
			System.out.println(cnt + " : " + pairslist.size());
			if (pairslist.size() != 0) {// 用户组U不等于0

				Iterator<Pair> pairslist2 = pairslist.iterator();// 在iterator容器中迭代输出list队列
				Pair pair2 = new Pair();
				float mood=0;
				int Ucnt=1;
				
				while (pairslist2.hasNext()) {
					pair2 = (Pair) pairslist2.next();
					String followid=pair2.getFollow();//得到用户集中的一个用户的uid
					//查询用户集中uid的情绪值
					//System.out.println(followid);
					
					TimeMoodDao timeMoodDao=new TimeMoodDao();
					UserTimeMood userTimeMood=new UserTimeMood();
					userTimeMood=timeMoodDao.QueryUserTimeMood(followid,0);
				
					if(userTimeMood.getId()!=-404){//查询到情绪值
					   System.out.println(Ucnt+":"+followid+"||"+userTimeMood.getMood()+"||"+userTimeMood.getTime());
					   mood+=(float)userTimeMood.getMood();
					   Ucnt++;
					}
				}
			}
			if (cnt > 5)
					break;
			cnt++;

		}

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
