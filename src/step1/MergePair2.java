package step1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import Dao.PairDao;
import bean.Pair;
import util.FileReadUtil;



/**
 * 
	* @ClassName: MergePair 
	* @Description:获取有转发关系的用户ID对 
	*              输入(目录：FilterPairUID)：msgid_usernameid.csv(消息ID}||}用户ID);
	*                                     usernameid_msgid.csv(用户ID}||}转发消息) 
	*              输出(目录：FilterPairUID)：userid_pair.csv(有转发关系的用户)
	* @author zeze
	* @date 2016年3月23日 下午4:44:36 
	*
 */
public class MergePair2 {
	public static  int cnt=0;
	public static int cnt1=0;
	private static Map<String, Integer> PairMap = new LinkedHashMap<String, Integer>();
	private static String messDir = "I:\\毕业设计\\数据集\\201511TO201602\\FilterPairUID\\";
	
	public static void main(String[] args) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获取两个时间的毫秒差异
		System.out.println("开始运行");
		Date nowDate = new Date();
		
		
		String path = "I:\\毕业设计\\数据集\\201511TO201602\\FilterPairUID\\";
		File file1=new File(path);
		File[] files1=file1.listFiles();
		for(File f1: files1){
			if(f1.getName().startsWith("msgiduserid_name")){		
				new MergePair2().pairs(f1.getName());
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		System.out.println("总数："+PairMap.size());
		try {
			System.out.println("开始写入文件：userid_pair.txt;写入数据表：pair");
			fw = new FileWriter(new File(messDir + "userid_pair.csv"), true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			for (Map.Entry<String, Integer> entry : PairMap.entrySet()) {// 遍历所有用户列表，打印用户新编号和Post数目
				cnt++;
				String[] ss=entry.getKey().split("\\|\\|");
				pw.write(ss[0]+ "||" + ss[1] + "\n");
				System.out.println("Write No. :"+cnt);
				//插入数据库中
				Pair pair=new Pair();
				PairDao pairDao=new PairDao();
				
				pair.setUser(ss[0]);
				pair.setFollow(ss[1]);				
				pairDao.Addpair(pair);//插入uid+followid
				
				pair.setUser(ss[1]);
				pair.setFollow(ss[0]);
				pairDao.Addpair(pair);//插入followid+uid
			}
			System.out.println("写入结束！用户关系总数："+cnt);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
			
		System.out.println("用户关系对总数:"+cnt);
		
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

	public void pairs(String destfile) {
		BufferedReader br = null;
		FileReader reader = null;

		
		String destFile1 = messDir + destfile;
		String destFile2 = messDir + "usernameid_msgid.csv";
		Map<String, String> msgIdUsernameMaps = new LinkedHashMap<String, String>();
		try {
			reader = new FileReader(new File(destFile1));
			br = FileReadUtil.getReadStream(reader);
			String str = null;
			str = br.readLine();
			
			String[] ss=null;
			System.out.println("开始读取："+destFile1);
			while ((str = br.readLine()) != null) {
				ss = str.split("\\}\\|\\|\\}");
				// 消息id与用户名
				msgIdUsernameMaps.put(ss[0], ss[1]);
				//System.out.println(cnt+" : "+ss[0]+"--"+ss[1]);
				cnt1++;
			}
			br.close();
			System.out.println("读取数据条数："+cnt1);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (br != null)
				try {
					br.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (reader != null)
				try {
					reader.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		try {
			reader = new FileReader(new File(destFile2));
			br = FileReadUtil.getReadStream(reader);
			
			String str = null;
			System.out.println("开始处理...");
			// strSubMess=new StringBuilder();
			while ((str = br.readLine()) != null) {
				String[] ss = str.split("\\}\\|\\|\\}");
				String username = ss[0];
				String followId = ss[1];
				if (msgIdUsernameMaps.containsKey(followId)) {//用户转发关系列表中找原消息博主
					//System.out.println(username + "||" + msgIdUsernameMaps.get(followId));
					//pw.write(username + "||" + msgIdUsernameMaps.get(followId) + "\n");
					String pairMap=username + "||" + msgIdUsernameMaps.get(followId);
					PairMap.put(pairMap, 1);//添加进入hashMap保证关系对的唯一性
					
				}
			}
			System.out.println("处理结束！");
			msgIdUsernameMaps.clear();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if (br != null)
				try {
					br.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (reader != null)
				try {
					reader.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
}
