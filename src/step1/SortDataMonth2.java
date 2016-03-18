package step1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import util.FileReadUtil;

/**
 * 
 * @ClassName: SortDataMonth2
 * @Description:按2015年月份分类源数据集，输出为： message_2015_0X_Group.csv
 * @author zeze
 * @date 2016年3月9日 下午3:31:03
 *
 */
public class SortDataMonth2 {
	public static void main(String[] args) {

		FileReader reader = null;
		BufferedReader br = null;

		//FileWriter fw1 = null;
		//FileWriter fw2 = null;
		FileWriter fw3 = null;
		FileWriter fw4 = null;
		FileWriter fw5 = null;
		FileWriter fw6 = null;
		//BufferedWriter bw1 = null;
		//BufferedWriter bw2 = null;
		BufferedWriter bw3 = null;
		BufferedWriter bw4 = null;
		BufferedWriter bw5 = null;
		BufferedWriter bw6 = null;
		//PrintWriter pw1 = null;
		//PrintWriter pw2 = null;
		PrintWriter pw3 = null;
		PrintWriter pw4 = null;
		PrintWriter pw5 = null;
		PrintWriter pw6 = null;
		String destfile = "I:\\毕业设计\\数据集\\201511TO201602\\SortDataMonth\\";
		//String destFile1 = destfile + "message_2015_09_Group.csv";
		//String destFile2 = destfile + "message_2015_10_Group.csv";
		String destFile3 = destfile + "message_2015_11_Group.csv";
		String destFile4 = destfile + "message_2015_12_Group.csv";
		String destFile5 = destfile + "message_2016_01_Group.csv";
		String destFile6 = destfile + "message_2016_02_Group.csv";
		
		int total=0;

		int[] cnt = new int[15];
		for (int i = 1; i < cnt.length; i++) {
			cnt[i] = 1;
		}
		int i = 1;
		String dir = "I:\\毕业设计\\sinaweb\\";
		File file1 = new File(dir);
		File[] files1 = file1.listFiles();
		for (File f1 : files1) {
			String fileName = null;
			if (f1.getName().endsWith(".csv")) {//读取数据集
				fileName = f1.getPath();
				System.out.println(i + " : " + fileName);
				i++;

				try {
					reader = new FileReader(new File(fileName));
					br = FileReadUtil.getReadStream(reader);
					/*fw1 = new FileWriter(new File(destFile1), true);
					bw1 = new BufferedWriter(fw1);
					pw1 = new PrintWriter(bw1);
					fw2 = new FileWriter(new File(destFile2), true);
					bw2 = new BufferedWriter(fw2);
					pw2 = new PrintWriter(bw2);*/
					fw3 = new FileWriter(new File(destFile3), true);
					bw3 = new BufferedWriter(fw3);
					pw3 = new PrintWriter(bw3);
					fw4 = new FileWriter(new File(destFile4), true);
					bw4 = new BufferedWriter(fw4);
					pw4 = new PrintWriter(bw4);
					fw5 = new FileWriter(new File(destFile5), true);
					bw5 = new BufferedWriter(fw5);
					pw5 = new PrintWriter(bw5);
					fw6 = new FileWriter(new File(destFile6), true);
					bw6 = new BufferedWriter(fw6);
					pw6 = new PrintWriter(bw6);
					String str = null;

					while ((str = br.readLine()) != null) {
						total++;
						String[] ss = str.split(",");// 按照逗号分隔
						int posttimeArr = ss.length - 1;
						String posttime = ss[posttimeArr];// 发布时间
						if (ss.length < 15)
							continue;
						// 0消息ID,1用户ID,2用户名,3屏幕名,4转发消息ID,5消息内容,6消息URL,7来源,8图片URL,9音频URL,10视频URL,11地理坐标,12转发数,13评论数,14赞数,15发布时间
						if (posttime.length() == 13 && !"".equals(posttime)) {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Long timeTran = new Long(Long.parseLong(posttime));
							String data = format.format(timeTran);
							Long time = Long.parseLong(posttime);
/*
							// 2015年9月：1441036800000~1443542400000
							Long startMonth = Long.parseLong("1441036800000");
							Long endMonth = Long.parseLong("1443542400000");
							if (time >= startMonth && time <= endMonth) {
								// System.out.println(cnt[9] + " " + ss[3] + "
								// 发布时间：" + data);
								pw1.write(str + "\n");
								cnt[9]++;
							}

							// 2015年10月：1443628800000~1446220800000
							startMonth = Long.parseLong("1443628800000");
							endMonth = Long.parseLong("1446220800000");
							if (time >= startMonth && time <= endMonth) {
								// System.out.println(cnt[10] + " " + ss[3] + "
								// 发布时间：" + data);
								pw2.write(str + "\n");
								cnt[10]++;
							}
*/
							// 2015年11月：1446307200000~1448812800000
							Long startMonth = Long.parseLong("1446307200000");
							Long endMonth = Long.parseLong("1448812800000");
							if (time >= startMonth && time <= endMonth) {
								// System.out.println(cnt[11] + " " + ss[3] + "
								// 发布时间：" + data);
								pw3.write(str + "\n");
								cnt[11]++;
							}

							// 2015年12月：1448899200000~1451491200000
							startMonth = Long.parseLong("1448899200000");
							endMonth = Long.parseLong("1451491200000");
							if (time >= startMonth && time <= endMonth) {
								// System.out.println(cnt[12] + " " + ss[3] + "
								// 发布时间：" + data);
								pw4.write(str + "\n");
								cnt[12]++;
							}

							// 2016年01月：1451577600000~1454169600000
							startMonth = Long.parseLong("1451577600000");
							endMonth = Long.parseLong("1454169600000");
							if (time >= startMonth && time <= endMonth) {
								// System.out.println(cnt[12] + " " + ss[3] + "
								// 发布时间：" + data);
								pw5.write(str + "\n");
								cnt[13]++;
							}

							// 2016年02月：1454256000000~1456675200000
							startMonth = Long.parseLong("1454256000000");
							endMonth = Long.parseLong("1456675200000");
							if (time >= startMonth && time <= endMonth) {
								// System.out.println(cnt[12] + " " + ss[3] + "
								// 发布时间：" + data);
								pw6.write(str + "\n");
								cnt[14]++;
							}

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
					if (pw3 != null) {
						pw3.close();
					}
					if (bw3 != null) {
						try {
							bw3.close();
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fw3 != null) {
						try {
							fw3.close();
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (pw3 != null) {
							pw3.close();
						}
						if (bw3 != null) {
							try {
								bw3.close();
							}
							catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (fw4 != null) {
							try {
								fw4.close();
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
        System.out.println("数据总量为:"+total);
		int sum = 0;
		for (i = 11; i <= 14; i++) {
			if (i <= 12)
				System.out.println(i + "月:" + cnt[i]);
			else
				System.out.println((i - 12) + "月：" + cnt[i]);
			sum += cnt[i];
		}
		System.out.println("201511 to 201602 数量：" + sum);
	}

}