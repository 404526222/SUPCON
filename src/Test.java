/**
 * @author Tom 5 Dec. 2018
 */
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.csvreader.CsvWriter;

import pojo.OutputDTO;
import pojo.InputDTO;

public class Test {
	public static void main(String[] args) throws IOException{
		select();
	}
	
	/**
	 * Function of select
	 */
	public static void select() throws IOException {
		//create the connection
		String resource="mybatis-config.xml";
		InputStream inputStream=Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session=sqlSessionFactory.openSession();
		
		//set the input
		InputDTO input=setInput("2018-12-04 10:00:00","2018-12-04 11:45:00",5,2,200017);
		
		//record result from mysql
		List<OutputDTO> cs=session.selectList("select",input);
		writeCSV(input,cs);
		for(OutputDTO c:cs) {
			System.out.print(c.getStartTime()+" "+c.getAcsId()+" "+c.getEntranceId()+" ");
			if(input.getObject()==2)
				System.out.print(c.getLaneId()+" ");
			System.out.println(c.getPassingVehicleNumber());
		}
	}
	
	/**
	 * Set input using data from website
	 * @param start_time
	 * @param end_time
	 * @param frequency
	 * @param object
	 * @param acs_id
	 */
	public static InputDTO setInput(String start_time,String end_time,int frequency,int object,int acs_id) {
		InputDTO input=new InputDTO();
		input.setStartTime(start_time);
		input.setEndTime(end_time);
		input.setFrequency(frequency);
		input.setObject(object);
		input.setAcsId(acs_id);
		return input;
	}
	
	/**
	 * Output the CSV file
	 * @param object, the type of object
	 * @param cs, datasets of result
	 */
	public static void writeCSV(InputDTO input,List<OutputDTO>cs)throws IOException{
		//set the path of CSV file
		String time=input.getStartTime().replace(":","_")+"-"+input.getEndTime().replace(":", "_");
		String filePath=System.getProperty("user.dir")+"\\路径流量统计表_"+time+".csv";
		ArrayList<String> h=new ArrayList<String>(Arrays.asList("start_time","acs_id","entrance_id","passing_vehicle_number"));
		CsvWriter writer=new CsvWriter(filePath,',',Charset.forName("UTF-8"));
		int bufferSize=0;
		if(input.getObject()==2) {
			h.add(3,"lane_id");
		}
		String[] headers=h.toArray(new String[h.size()]);
		//write header
		writer.writeRecord(headers);
		//write data
		for(OutputDTO c:cs) {
			if(bufferSize<63488) {	//64KB
				if(input.getObject()==1) {
					//record the data
					String[] content=writeObject1(c);
					//sum of the size of buffer
					bufferSize+=calculateSize(content);
					writer.writeRecord(content);
					}
				if(input.getObject()==2) {
					//record the data
					String[] content=writeObject2(c);
					//sum of the size of buffer
					bufferSize+=calculateSize(content);
					writer.writeRecord(content);
				}
			}else {
				writer.flush();
				bufferSize=0;
				if(input.getObject()==1) {
					//record the data
					String[] content=writeObject1(c);
					//sum of the size of buffer
					bufferSize+=calculateSize(content);
					writer.writeRecord(content);
					}
				if(input.getObject()==2) {
					//record the data
					String[] content=writeObject2(c);
					//sum of the size of buffer
					bufferSize+=calculateSize(content);
					writer.writeRecord(content);
				}
			}
		}
		writer.flush();
		writer.close();
	}
	
	/**
	 * Record data when value of object is 1
	 */
	public static String[] writeObject1(OutputDTO c) {
		String[] content= {c.getStartTime(),String.valueOf(c.getAcsId()),String.valueOf(c.getEntranceId()),String.valueOf(c.getLaneId()),String.valueOf(c.getPassingVehicleNumber())};
		return content;
	}
	
	/**
	 * Record data when value of object is 2
	 */
	public static String[] writeObject2(OutputDTO c) {
		String[] content= {c.getStartTime(),String.valueOf(c.getAcsId()),String.valueOf(c.getEntranceId()),String.valueOf(c.getLaneId()),String.valueOf(c.getPassingVehicleNumber())};
		return content;
	}
	
	/**
	 * Calculate the size of recording data
	 */
	public static int calculateSize(String[] content) {
		int sum=0;
		for(int i=0;i<content.length;i++) {
			sum+=content[i].length();
		}
		return sum;
	}
}
