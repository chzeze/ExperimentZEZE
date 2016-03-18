package step0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import util.FileWriteUtil;


/**
 * 
 * @ClassName: Maximum_subgraph_user_name_id
 * @Description: 获取最大子图中用户名和ID关系 
 *               输入：belong.txt 
 *                   subgraph.txt
 *               输出：maximum_subgraph_user_name_id.txt
 * 
 * @author zeze
 * @date 2016年3月14日 下午3:02:41
 *
 */
public class Maximum_subgraph_user_name_id {
	public void user_name_id_process() {
		String path = "I:\\毕业设计\\数据集\\Disjoint\\";
		File file = new File(path + "subgraph.txt");
		FileReader reader = null;
		BufferedReader br = null;
		int max_subgraph_id = 0;
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			String str = null;
			str = br.readLine();// 第一行为最大子图：子图号||数目
			max_subgraph_id = Integer.parseInt(str.split("\\|\\|")[0]);

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (br != null)
				try {
					br.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			if (reader != null)
				try {
					reader.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
		}
		file =new File(path+"belong.txt");//belong.txt保存的是每一个点所属的子图ID
		HashMap<Integer, String> belongMaps=new LinkedHashMap<Integer,String>();
		int n=0;//计算器
		int id=0;//存储id号码
		try {
			reader=new FileReader(file);
			br=new BufferedReader(reader);
			String str=null;
			while((str=br.readLine())!=null){
				++n;
				id=Integer.parseInt(str);
				if(max_subgraph_id==id){//属于最大子图
					belongMaps.put(n, "1");
				}
				else{
					belongMaps.put(n, "0");
				}
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (br != null)
				try {
					br.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			if (reader != null)
				try {
					reader.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
		}
		String dir="I:\\毕业设计\\数据集\\SubgraphProcess1\\";
		file= new File(dir+"user_name_id.txt");
		StringBuffer sbUserNameID=new StringBuffer();
		try {
			reader=new FileReader(file);
			br=new BufferedReader(reader);
			String str=null;
			int cnt=0;
			while((str=br.readLine())!=null){
				String[] ss=str.split("\\|\\|");
				String name=ss[0];
				int idTemp=Integer.parseInt(ss[1]);
				if("1".equals(belongMaps.get(idTemp))){//属于最大子图
					System.out.println(name+"||"+idTemp);
					sbUserNameID.append(name+"||"+idTemp+"\n");
					cnt++;
				}
			}
			System.out.println("最大子图的用户数目为："+cnt);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
	   		if (br != null)
	   			try {
	   				br.close();
	   			} catch (IOException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
	   		if (reader != null)
	   			try {
	   				reader.close();
	   			} catch (IOException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
	   	}
		//写入
		String file2=path+"maximum_subgraph_user_name_id.txt";
		FileWriteUtil.WriteDocument(file2, sbUserNameID.toString()); 
	}

	public static void main(String[] args) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获取两个时间的毫秒差异
		System.out.println("开始运行");
		Date nowDate = new Date();
		new Maximum_subgraph_user_name_id().user_name_id_process();
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
