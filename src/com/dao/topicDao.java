package com.dao;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;

import com.bean.Manager;
import com.bean.Topic;
import com.util.dbAccess;
public class topicDao {
	//����Ա�鿴��Ŀ��Ϣ
	static public ArrayList<Topic> getAlltopic1() throws SQLException, ClassNotFoundException{
		ArrayList<Topic> topics=new ArrayList<Topic>();
		dbAccess db=new dbAccess();
		String sql="select *from TopicInfo";
		ResultSet rs=db.Query(sql);
		while(rs.next()){
			Topic topic=new Topic();
			topic.setTeacher(rs.getString(1));
		    topic.setName(rs.getString(2));
		    topic.setType(rs.getString(3));
		    topic.setSource(rs.getString(4));
		    topic.setTools(rs.getString(5));
		    topic.setContent(rs.getString(6));
		    topic.setState(rs.getString(7));
		    topic.setSno(rs.getString(8));
		    topics.add(topic);
		}
		return topics;
	}
	//ѧ���鿴��Ŀ��Ϣ
	static public ArrayList<Topic> getAlltopic2() throws SQLException, ClassNotFoundException{
		ArrayList<Topic> topics=new ArrayList<Topic>();
		dbAccess db=new dbAccess();
		//ѧ��ֻ��ѡ��û�б�����ѡ��Ŀ���
		String sql="select Teacher,Name,Type,Source,Tools,Content from TopicInfo where Sno is null";
		ResultSet rs=db.Query(sql);
		while(rs.next()){
			Topic topic=new Topic();
			topic.setTeacher(rs.getString(1));
		    topic.setName(rs.getString(2));
		    topic.setType(rs.getString(3));
		    topic.setSource(rs.getString(4));
		    topic.setTools(rs.getString(5));
		    topic.setContent(rs.getString(6));
		    topics.add(topic);
		}
		return topics;
	}
	//��ʦ�鿴�Լ�����Ŀ��Ϣ
	static public ArrayList<Topic> getAlltopic3(String Tno) throws SQLException, ClassNotFoundException{
		ArrayList<Topic> topics=new ArrayList<Topic>();
		dbAccess db=new dbAccess();
		String sql="select Name,Type,Source,Tools,Content from TopicInfo where Teacher='"+Tno+"'";
		ResultSet rs=db.Query(sql);
		while(rs.next()){
			Topic topic=new Topic();
		    topic.setName(rs.getString(1));
		    topic.setType(rs.getString(2));
		    topic.setSource(rs.getString(3));
		    topic.setTools(rs.getString(4));
		    topic.setContent(rs.getString(5));
		    topics.add(topic);
		}
		return topics;
	}
	//ÿλ��ʦ��Ҫ��˵Ŀ���
	static public ResultSet getUncompletetopic(String Tno) throws SQLException, ClassNotFoundException{
		dbAccess db=new dbAccess();
		String sql="select Name,Type,Content,Sname,Smajor,GPA,State from TopicInfo as t join StudentInfo as s"
				+ " on t.Sno=s.Sno where State='�ܾ�' and Teacher='"+Tno+"' and t.Sno is not null";
		ResultSet rs=db.Query(sql);
		return rs;
	}
	//ÿλ��ʦ�Ѿ���˵Ŀ���
	static public ResultSet getCompletetopic(String Tno) throws SQLException, ClassNotFoundException{
		dbAccess db=new dbAccess();
		String sql="select Name,Type,Content,Sname,Smajor,GPA,State from TopicInfo as t join StudentInfo as s"
				+ " on t.Sno=s.Sno where State='ͨ��' and Teacher='"+Tno+"' and t.Sno is not null";
		ResultSet rs=db.Query(sql);
		return rs;
	}
	//�ж�ÿλ��ʦָ��ѧ�������Ƿ�ﵽ����
	static public int judgeStunum(String number) throws ClassNotFoundException, SQLException{
		int flag=0;
		int num1=0,num2=1;
		String sql1="select count(*) from TopicInfo where Teacher='"+number+"' and State='ͨ��'";
		dbAccess db1=new dbAccess();
		ResultSet rs1=db1.Query(sql1);
		if(rs1!=null){
			rs1.next();
			num1=rs1.getInt(1);
		}
		String sql2="select Maxnum from MgrInfo where IsPublished='�ѷ���'";
		dbAccess db2=new dbAccess();
		ResultSet rs2=db2.Query(sql2);
		if(rs2!=null){
			rs2.next();
			num2=rs2.getInt(1);
		}
		System.out.println(num1+"  "+num2);
		if(num1>=num2){
			flag=1;
		}
		return flag;
	}
	//�ж�ÿλ��ʦָ������ѧ��ռ��
	static public int judgeStuGPA(String number,String GPA) throws ClassNotFoundException, SQLException{
		int flag=0;
		int num1=0,num2=1;
		float gpa=Float.valueOf(GPA);
		String sql1="select count(*) from TopicInfo as t join StudentInfo  as s on "
				+ "t.Sno=s.Sno where State='ͨ��' and Teacher='"+number+"' and GPA>3.5";
		dbAccess db1=new dbAccess();
		ResultSet rs1=db1.Query(sql1);
		if(rs1!=null){
			rs1.next();
			num1=rs1.getInt(1);
		}
		String sql2="select radio from MgrInfo where IsPublished='�ѷ���'";
		dbAccess db2=new dbAccess();
		ResultSet rs2=db2.Query(sql2);
		if(rs2!=null){
	        rs2.next();
	        num2=rs2.getInt(1);
		}
		System.out.println(num1+"  "+num2);
	    if(num1>=num2){
			flag=1;
		}
	    if(gpa<3.5){
	    	flag=0;
	    }
		return flag;
	}
	//��Ŀ�������
	static public int completeTopicCount() throws ClassNotFoundException, SQLException{
		ResultSet rs;
		int count=0;
		String sql="select count(*) from TopicInfo where State='ͨ��'";
		dbAccess db=new dbAccess();
		rs=db.Query(sql);
		rs.next();
		count=rs.getInt(1);
		return count;
	}
	//��Ŀδ�����
	static public int uncompleteTopicCount() throws ClassNotFoundException, SQLException{
		ResultSet rs;
		int count=0;
		String sql="select count(*) from TopicInfo where State='�ܾ�'";
		dbAccess db=new dbAccess();
		rs=db.Query(sql);
		rs.next();
		count=rs.getInt(1);
		return count;
	}
	//�����ѡ�����Ŀ
	static public ArrayList<Topic> completeTopic() throws ClassNotFoundException, SQLException{
		ArrayList<Topic> topics=new ArrayList<Topic>();
		dbAccess db=new dbAccess();
		String sql="select *from TopicInfo where State='ͨ��'";
		ResultSet rs=db.Query(sql);
		while(rs.next()){
			Topic topic=new Topic();
			topic.setTeacher(rs.getString(1));
		    topic.setName(rs.getString(2));
		    topic.setType(rs.getString(3));
		    topic.setSource(rs.getString(4));
		    topic.setTools(rs.getString(5));
		    topic.setContent(rs.getString(6));
		    topic.setState(rs.getString(7));
		    topic.setSno(rs.getString(8));
		    topics.add(topic);
		}
		return topics;
	}
	//δ���ѡ�����Ŀ
	static public ArrayList<Topic> uncompleteTopic() throws ClassNotFoundException, SQLException{
		ArrayList<Topic> topics=new ArrayList<Topic>();
		dbAccess db=new dbAccess();
		String sql="select *from TopicInfo where State='�ܾ�'";
		ResultSet rs=db.Query(sql);
		while(rs.next()){
			Topic topic=new Topic();
			topic.setTeacher(rs.getString(1));
		    topic.setName(rs.getString(2));
		    topic.setType(rs.getString(3));
		    topic.setSource(rs.getString(4));
		    topic.setTools(rs.getString(5));
		    topic.setContent(rs.getString(6));
		    topic.setState(rs.getString(7));
		    topic.setSno(rs.getString(8));
		    topics.add(topic);
		}
		return topics;
	}
	//�õ���Ŀ����
	static public int getTopicCount() throws ClassNotFoundException, SQLException{
		ResultSet rs;
		int count=0;
		String sql="select count(*) from TopicInfo";
		dbAccess db=new dbAccess();
		rs=db.Query(sql);
		rs.next();
		count=rs.getInt(1);
		return count;
	}
	//��ʦ�����Ŀ
	static public int checkTopic(String State,String name) throws ClassNotFoundException, SQLException{
		int rtn=0;
		String sql="update TopicInfo set State='"+State+"' where Name='"+name+"'";
		dbAccess db=new dbAccess();
		rtn=db.Update(sql);
		return rtn;
	}
	//����Աͳ��ÿ����ʦָ���˶��ٸ�ѧ��
	static public ArrayList<Topic> tongjiStu() throws ClassNotFoundException, SQLException{
		ArrayList<Topic> array=new ArrayList<Topic>();
		String sql="select Teacher,count(*) from TopicInfo group by Teacher,State having State='ͨ��'";
		dbAccess db=new dbAccess();
		ResultSet rs=db.Query(sql);
		while(rs.next()){
			Topic a=new Topic();
			a.setTeacher(rs.getString(1));
			a.setCount(rs.getString(2));
			array.add(a);
		}
		return array;
	}
	//����Աͳ��ÿ����Ŀ�ĸ���
	static public ArrayList<Topic> tongjiTopic() throws ClassNotFoundException, SQLException{
		ArrayList<Topic> array=new ArrayList<Topic>();
		String sql="select Type,count(*) from TopicInfo group by Type";
		dbAccess db=new dbAccess();
		ResultSet rs=db.Query(sql);
		while(rs.next()){
			Topic a=new Topic();
			a.setName(rs.getString(1));
			a.setCount(rs.getString(2));
			array.add(a);
		}
		return array;
	}
	static public int insertTopic(String Teacher,String Name,String Type,String Source,String Tools
	,String Content,String State) throws ClassNotFoundException,SQLException{
		int rtn=0;
		String sql="insert into TopicInfo(Teacher,Name,Type,Source,Tools,Content,State) values('"+Teacher
			+"','"+Name+"','"+Type+"','"+Source+"','"+Tools+"','"+Content+"','"+State+"')";
		dbAccess db=new dbAccess();
		rtn=db.Update(sql);
		return rtn;
	}
	static public int updateTopic(String Name,String Type,String Source,String Tools,
		String Content,String name) throws ClassNotFoundException,SQLException{
		int rtn=0;
		String sql="update TopicInfo set Name='"+Name+"',Type='"+Type
				+"',Source='"+Source+"',Tools='"+Tools+"',Content='"+Content+"' where Name='"+name+"'";
		dbAccess db=new dbAccess();
		rtn=db.Update(sql);
		return rtn;
	}
	static public int deleteTopic(String name) throws ClassNotFoundException, SQLException{
		int rtn=0;
		String sql="delete from TopicInfo where Name='"+name+"'";
		dbAccess db=new dbAccess();
		rtn=db.Update(sql);
		return rtn;
	}
	//ѧ��ѡ���ȡ��ѡ��
	static public int StucheckTopic(String State,String name,String Sno) throws ClassNotFoundException, SQLException{
		int rtn=0;
		String sql="update Topicinfo set State='"+State+ "',Sno='"+Sno+"' where Name='"+name+"'";
		dbAccess db=new dbAccess();
		rtn=db.Update(sql);
		return rtn;
	}
	//���ݵ�ǰѧ�����꼶��ѯ����Ա�Ƿ񷢲���ǰ�꼶��ѡ��
		static public Manager findByGrade(String Sgrade)throws Exception{
			Manager manager = new Manager();
			String sql = "select * from MgrInfo where GradYear='"+Sgrade+"'";
			dbAccess db = new dbAccess();
			ResultSet rs = db.Query(sql);
			if (rs.next()){
				manager.setID(rs.getInt("ID"));
				manager.setGradYear(rs.getString("GradYear"));
				manager.setStuNum(rs.getString("StuNum"));
				manager.setTimeStart(rs.getString("TimeStart"));
				manager.setTimeEnd(rs.getString("TimeEnd"));
				manager.setIsPublished(rs.getString("IsPublished"));
			}
			return manager;
		}
		//ͨ��ѧ�Ų����ҵ�ѡ����Ϣ
		static public ArrayList<Topic> findByNo(String id)throws Exception{
			ArrayList<Topic> list = new ArrayList<Topic>();
			String sql = "select * from TopicInfo where Sno='"+id+"'";
			dbAccess db = new dbAccess();
			ResultSet rs = db.Query(sql);
			while (rs.next()){
				Topic topic = new Topic();
				topic.setTeacher(rs.getString(1));
				topic.setName(rs.getString(2));
				topic.setType(rs.getString(3));
				topic.setSource(rs.getString(4));
				topic.setTools(rs.getString(5));
				topic.setContent(rs.getString(6));
				topic.setState(rs.getString(7));
				topic.setSno(rs.getString(8));
				list.add(topic);
			}
			return list;
		}
}
