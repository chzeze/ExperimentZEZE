package step1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import util.FileReadUtil;



/**
 * 
 * @ClassName: FilterPair3
 * @Description: (从数据集中分离屏幕名发布的消息ID，分离屏幕名转发的消息的ID)
 *               输入(目录：FilterMood)： mess_emoticonX.csv 
 *               输出(目录：FilterPairUID)：msgiduserid_name.csv(消息ID}||}用户ID);
 *                                      usernameid_msgid.csv(用户ID}||}转发消息)
 * @author zeze
 * @date 2016年3月16日 上午10:39:19
 *
 */

public class FilterPair3 {
	public static void main(String[] args) {
		// Map<String, Integer> maps = new HashMap<String, Integer>();
		String messDir = "I:\\毕业设计\\数据集\\201511TO201602\\FilterMood";
		String destDir = "I:\\毕业设计\\数据集\\201511TO201602\\FilterPairUID\\";

		// Map<String, String> msgIduserNameMaps=new LinkedHashMap<String,
		// String>();
		// StringBuilder strSubMess=null;
		BufferedReader br = null;
		FileReader reader = null;
		FileWriter fw1 = null;
		BufferedWriter bw1 = null;
		PrintWriter pw1 = null;// "J:\\user_id_pair.txt"
		FileWriter fw2 = null;
		BufferedWriter bw2 = null;
		PrintWriter pw2 = null;// "J:\\user_id_pair.txt"
		// Pattern p = Pattern.compile("\\[.{1,30}?\\]");// 最短匹配法
		String destFile1 = destDir + "msgiduserid_name0.csv";
		String destFile2 = destDir + "usernameid_msgid.csv";
		File file1 = new File(messDir);
		int cnt=1;
		int cntpair0=0;
		int cntpair1=1;
		int page=1;
		File[] files1 = file1.listFiles();
		for (File f1 : files1) {
			if (f1.getName().endsWith(".csv")) {
				String fileName = f1.getPath();// messDir+f1.getName();
				System.out.println(cnt+" : "+fileName);
				cnt++;
				try {
					reader = new FileReader(new File(fileName));
					br = FileReadUtil.getReadStream(reader);
					fw1 = new FileWriter(new File(destFile1), true);
					bw1 = new BufferedWriter(fw1);
					pw1 = new PrintWriter(bw1);
					fw2 = new FileWriter(new File(destFile2), true);
					bw2 = new BufferedWriter(fw2);
					pw2 = new PrintWriter(bw2);
					
					String str = null;
					// strSubMess=new StringBuilder();
					
					while ((str = br.readLine()) != null) {
						String[] ss = str.split(",");
						if (ss.length != 16)
							continue;
						// 消息ID}||}屏幕名
						if (!"".equals(ss[3])) {// 屏幕名不为空
							if(cntpair0/1000000>0){
								pw1.close();
								destFile1= destDir + "msgiduserid_name"+page+".csv";
								fw1 = new FileWriter(new File(destFile1), true);
								bw1 = new BufferedWriter(fw1);
								pw1 = new PrintWriter(bw1);
								cntpair0=0;
								page++;
								
							}
							if(!"".equals(ss[3])){
								pw1.write(ss[0] + "}||}" + ss[1] + "\n");
							}
							cntpair0++;
						}
						// 屏幕名}||}转发消息
						if (!"".equals(ss[4])) {// 转发ID不为空
							pw2.write(ss[1] + "}||}" + ss[4] + "\n");
							cntpair1++;
						}

					}

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
					if (pw1 != null) {
						pw1.close();
					}
					if (bw1 != null) {
						try {
							bw1.close();
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fw1 != null) {
						try {
							fw1.close();
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (pw2 != null) {
							pw2.close();
						}
						if (bw2 != null) {
							try {
								bw2.close();
							}
							catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (fw2 != null) {
							try {
								fw2.close();
							}
							catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
			}

		}
		System.out.println("消息数据总数："+(cntpair0+1000000*(page-1)));
		System.out.println("转发消息总数："+cntpair1);
		System.out.println("End!");
	}
}