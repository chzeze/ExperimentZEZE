package step3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import Dao.TimeMoodDao;
import bean.UserTimeMood;
import util.FileWriteUtil;
/**
 * 
	* @ClassName: CntTimeUserMood 
	* @Description: 计算不同时间窗口用户的情绪值
	*              输入(目录：FilterMood)：mess_emoticonX.csv
                                                         输出(目录：UserMood)：user_moodX.csv (uid+mood+Time)
                                                         插入数据库表：usertimemood

	* @author zeze
	* @date 2016年3月22日 下午3:46:37 
	*
 */
public class CntTimeUserMood {
	private static String dir = "I:\\毕业设计\\数据集\\201511TO201602\\FilterMood\\";
	private static String path = "I:\\毕业设计\\数据集\\201511TO201602\\UserMood\\";
	private static String[] fileNameIn = new String[] { "mess_emoticon0.csv", "mess_emoticon1.csv", "mess_emoticon2.csv", "mess_emoticon3.csv" };
	private static String[] fileNameOut=new String[]{"user_mood0.csv","user_mood1.csv","user_mood2.csv","user_mood3.csv"};
	private static StringBuffer sBuilder=new StringBuffer();
	public static void main(String[] args) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获取两个时间的毫秒差异
		System.out.println("开始运行");
		Date nowDate = new Date();
		
		for(int i=0;i<4;i++){
			int cnt=0;
			sBuilder=new StringBuffer();
			Map<String, Integer> UserCnt = new LinkedHashMap<String, Integer>();
			BufferedReader br=null;
			FileReader reader=null;
			String messDir=dir+fileNameIn[i];
			System.out.println("读取："+messDir);
			try {
				reader=new FileReader(new File(messDir));
				br=new BufferedReader(reader);
				String str=null;
				String[] ss=null;
				String[] source=null;
				int tempCnt=0;
				while((str=br.readLine())!=null){
					ss=str.split("\\}\\|\\|\\|\\}");//原数据集}||}1
					source=ss[0].split(",");//ss[0]为原数据集
					//source[1]用户ID ss[1]情绪值
					if(UserCnt.containsKey(source[1])){//存在用户名
						tempCnt=UserCnt.get(source[1])+Integer.parseInt(ss[1]);//加上该条微博的情绪值
						UserCnt.put(source[1], tempCnt);
					}
					else{
						tempCnt=Integer.parseInt(ss[1]);
						//System.out.println(tempCnt);
						UserCnt.put(source[1], tempCnt);
					}
				}
				for (Map.Entry<String, Integer> entry : UserCnt.entrySet()) {// 遍历所有用户列表，打印用户新编号和情感极性
					//System.out.println(entry.getKey() + "||" + entry.getValue());
					sBuilder.append(entry.getKey() + "||" + entry.getValue()+"||"+i+"\n");
					cnt++;					
					
					UserTimeMood userTimeMood=new UserTimeMood();
					userTimeMood.setUid(entry.getKey());
					userTimeMood.setMood(entry.getValue());
					userTimeMood.setTime(i);
					
					TimeMoodDao timeMoodDao=new TimeMoodDao();
					timeMoodDao.AdduserTimeMood(userTimeMood);//插入usertimemood数据表
				}
				String destfile=path+fileNameOut[i];
				FileWriteUtil.WriteDocument(destfile, sBuilder.toString());
				System.out.println(destfile+" 写入："+cnt+"条");
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			UserCnt.clear();
		}
	System.out.println("运行结束。");
	
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
