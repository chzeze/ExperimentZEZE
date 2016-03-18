package step0;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import util.FileReadUtil;



/**
 * 
	* @ClassName: MergePair (获取有转发关系的用户对)
	* @Description:  输入：msgidusername.csv(消息ID}||}屏幕名);usernamemsgid.csv(屏幕名}||}转发消息) 
	*                输出：pair.csv(有转发关系的用户)
	* @author zeze
	* @date 2016年3月8日 下午4:44:36 
	*
 */
public class MergePair {
	public static  int cnt=0;
	public static int cnt1=0;
	public static void main(String[] args) {
		String path = "I:\\毕业设计\\数据集\\FilterPair\\";
		File file1=new File(path);
		File[] files1=file1.listFiles();
		for(File f1: files1){
			if(f1.getName().startsWith("msgidusername")){		
				new MergePair().pairs(f1.getName());
			}
		}
		System.out.println("用户关系对总数:"+cnt);
	}

	public void pairs(String destfile) {
		BufferedReader br = null;
		FileReader reader = null;

		String messDir = "I:\\毕业设计\\数据集\\FilterPair\\";
		String destFile1 = messDir + destfile;
		String destFile2 = messDir + "usernamemsgid.csv";
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
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		try {
			reader = new FileReader(new File(destFile2));
			br = FileReadUtil.getReadStream(reader);
			fw = new FileWriter(new File(messDir + "pair.csv"), true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			String str = null;
			System.out.println("开始写入...");
			// strSubMess=new StringBuilder();
			while ((str = br.readLine()) != null) {
				String[] ss = str.split("\\}\\|\\|\\}");
				String username = ss[0];
				String followId = ss[1];
				if (msgIdUsernameMaps.containsKey(followId)) {//用户转发关系列表中找原消息博主
					//System.out.println(username + "||" + msgIdUsernameMaps.get(followId));
					pw.write(username + "||" + msgIdUsernameMaps.get(followId) + "\n");
					cnt++;
				}
			}
			System.out.println("写入数据条数："+cnt);
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
