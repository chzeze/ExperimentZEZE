package step2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import util.FileReadUtil;

/**
 * 
 * @ClassName: FilterNegativeReadFileBZ2
 * @Description: 提取分月源数据中带有消极情感表情的微博
 *               输入(目录：201511TO201602):negative.txt;
 *                  (目录：SortDataMonth):message_2015_0X_Group
 *               输出(目录：FilterMood)： mess_emoticonX.csv
 * @author zeze
 * @date 2016年3月8日 上午10:20:21
 *
 */
public class FilterNegativeReadFileCsv {
	public static void main(String[] args) {
		BufferedReader br = null;
		FileReader reader1 = null;
		String srcdir = "I:\\毕业设计\\数据集\\201511TO201602\\";
		File file = null;
		file = new File(srcdir + "negative.txt");
		Set<String> set = new LinkedHashSet<String>();
		// StringBuffer sbBuffer=new StringBuffer();
		try {
			reader1 = new FileReader(file);
			br = FileReadUtil.getReadStream(reader1);
			String str = null;
			while ((str = br.readLine()) != null) {
				set.add(str);
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
			if (br != null)
				try {
					br.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (reader1 != null)
				try {
					reader1.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("总共" + set.size() + "个表情符");
		for (String ss : set) {
			System.out.println(ss);
		}
		String dir = "I:\\毕业设计\\数据集\\201511TO201602\\SortDataMonth\\";
		String dir2 = "I:\\毕业设计\\数据集\\201511TO201602\\FilterMood\\";
		InputStream is = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		FileReader reader = null;
		int cnt = 0;

		String fileName1 = dir;
		// System.out.println(fileName1);

		File file1 = new File(fileName1);
		File[] files1 = file1.listFiles();
		int count=0;
		int total=0;
		for (File f1 : files1) {
			if (f1.getName().endsWith(".csv")) {
				System.out.println(f1.getPath());
				// 读取
				try {
					total=0;
					fw = new FileWriter(new File(dir2 + "mess_emoticon" +(count++)+ ".csv"), true);
					bw = new BufferedWriter(fw);
					pw = new PrintWriter(bw);
					reader = new FileReader(new File(f1.getPath()));
					br = FileReadUtil.getReadStream(reader);
					String s = null;
					// System.out.println(br.readLine());
					while ((s = br.readLine()) != null) {
						// System.out.println(s);
						String[] ss = s.split(",");
						if (ss.length != 16)
							continue;
						String content = ss[5];
						for (String emoction : set) {
							if (content.contains(emoction)) {//过滤包含有消极表情的微博
				//0消息ID,1用户ID,2用户名,3屏幕名,4转发消息ID,5消息内容,6消息URL,7来源,8图片URL,9音频URL,10视频URL,11地理坐标,12转发数,13评论数,14赞数,15发布时间
								pw.write(s + "}|||}"+"-1"+ "\n");
								cnt++;
								total++;
								break;
							}
						}						
					}
					System.out.println(count+":"+total);
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
						if (is != null)
							try {
								is.close();
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


		System.out.println("Negative 总计：" + cnt);
	}
}