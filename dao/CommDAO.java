package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Info;

public class CommDAO{
	public static String dbname = "";
	public static String dbtype = "";
	public static Connection conn = null;

	public CommDAO(){
		conn = this.getConn();
	}

	//给字段做加减法
	public void jiajian(String tablename, String colname, String id, String num){
		HashMap map = this.getmap(id, tablename);
		String value = map.get(colname).toString();
		if (value.equals("")){
			value = "0";
			
		}
		
		int i = Integer.parseInt(value);
		int j = Integer.parseInt(num);
		commOper("update " + tablename + " set" + colname + " = " + ( i + j) + "where id =" + id);
	}

	public String DynamicImage( String categoryid, int cut, int width, int height){
		StringBuffer imgStr = new StringBuffer();
		StringBuffer thePics1 = new StringBuffer();
		StringBuffer theLinks1 = new StringBuffer();
		StringBuffer theTexts1 = new StringBuffer();

		imgStr.append( "<div id = picViwer1 style = 'background-color: #ffffff' align = center></div><SCRIPT src = 'js/dynamicImage.js' type = text/javascipt></SCRIPT>\n<script language = JavaScript>\n");
		thePics1.append("var thePics1=\n");
		theLinks1.append("var theLinks1 = ' ");
		theText1.append("var theTexts1 = ' ");

		List<HashMap> co = this.select ("select * from xinwentongzhi where shouyetupian<>'' and shouyetupian<> 'null' and shouyetupian like '%.jpg' order by id desc",1, 6);
		int i = co.size();

		int j = 0;
		for (HashMap b:co){
			j++;
			int id = Integer.parseInt(b.get("id").toString());
			String title = Info.subStr(b.get("biaoti").toString(), 21);
			String url = "" + b.get("shouyetupian");

			String purl = "gg_detail.jsp?id = " + b.get( "id");

			if (j != i){
				thePics1.append(url.replaceAll("\n", "") + "|");
				theLinks1.append(purl + "|");
				theTexts1.append(title + "|");
			}

			if ( j == i){
				thePics1.append(url.replaceAll( "\n", ""));
				theLinks1.append( "gg_detail.jsp?id= " + b.get("id"));
				theTexts1.append(title);
			}

		}

		thePics1.append(" '; ");
		theLinks1.append(" '; ");
		theText1.append( " '; ");
		imgStr.append(thePics1 + "\n");
		imgStr.append(theLinks1 + "\n");
		imgStr.append(theTexts1 + "\n");
		imgStr.append("\n setPic(thePics1, theLinks1, theText1," + width + "," + height + ",'picViwer1');</script>");

		return imgStr.toString();
	}

	public HashMap getmap(String id, String table){
		List<HashMap> list = new ArrayList();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery( "select * from " + table + "where id =" + id);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next){
				HashMap map = new HashMap();
				int i = rsmd.getColumnCount();
				for (int j = 1; j <= i ; j++ ){
					if (!rsmd.getColumnName(j).equals("ID")){
						String str = rs.getString(j) == null ? "" : rs.getString(j);
						if (str.equals("null")){
							str ="";
						}
						map.put( rsmd.getColumnName(j), str);
					}else
						map.put("id", rs.getString(j));
				}
				list.add(map);
			}

			rs.close();
			st.close();
		}
		catch ( SQLException e){
			e.printStackTrace();
		}

		return list.get(0);
	}

	public HashMap getmaps (String nzd, String zdz, String table){
		List<HashMap> list = new ArrayList();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery( "select * from" + table + " where" + nzd + " ='" + zdz + " '");
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next){
				HashMap map = new HashMap();
				int i = rsmd.getColumnCount();
				for (int j = 1; j<=i ; j++ ){
					if (!rsmd.getColumnName(j).equals("ID")){
						String str = rs.getString(j) == null ? "" : rs.getString(j);
						if (str.equals("null")){
							str = "";
							map.put(rsmd.getColumnName(j), str);
						}
					}else
						map.put("id", rs.getString(j));
				}

				list.add(map);
			}

			rs.close();
			st.close();
		}
		catch ( SQLException e){
			e.printStackTrace();
		}

		return list.get(0);
	}

	public String insert( HttpServletRequest request, HttpServletResponse response, String tablename, HashMap extmap, boolean alert, boolean reflush, String tzurl){
		extmap.put( "addtime", Info.getDateStr());
		if (request.getParameter( "f") != null){
			HashMap typemap = new HashMap();
			ArrayList<String> collist = new ArrayList();
			String sql = "insert into" + tablename + "(";

			Connection conn = this.getConn();

			try{
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery( "select * from " + tablename);
				ResultSetMetaData rsmd = rs.getMetaData();
				int i = rsmd.getColumnCount();

				for (int j = 1; j<=i; j++){
					if(rsmd.getColumnName(j).equals("id"))
						continue;
					if (rsmd.getColumnName(j).equals("ID"))
						continue;
					if (rsmd.getColumnName(j).equals( "iD"))
						continue;
					if( rsmd.getColumnName(j).equals( "Id"))
						continue;

					typemap.put(rsmd.getColumnName(j) + "---", rsmd.getColumnTypeName(j));
					collist.add(rsmd.getColumnName( j));
					sql += rsmd.getColumnName(j) + ",";
				}

				sql = sql.substring (0, sql.length() - 1);
				sql += ") values(";

				rs.close();
				st.colse();
			}
			catch ( SQLException e)	{
				e.printStackTrace();
			}

			Enumeration enumeration = request.getParameterNames();
			String name = " ,";
			while (enumration.hasMoreElements()){
				names += enumeration.nextElement().toString() + ",";
			}

			try	{
				Statement st conn.createStatement();
				for ( String str : collist){
					if (names.indexOf( "," + str + ",") > -1){
						String[] values = request.getParameterValues(str);
						String value = "";
						for ( String vstr : values){
							if(vstr == null)
								vstr = "";
							if(vstr.equals(" null"))
								vstr = "";
							if( vstr.trim().equals(""))
								continue;
							if (request.getParameter(vstr) != null && !"".equals(request.getParameter(vstr)) && request.getParameter( "dk-" + str + "-value" != null)){

								String dkv = request.getParameter(vstr);
								String dknamevalue = request.getParameter("dk-" + str + "-value");
								vstr += " - " + dknamevalue + ":" + dkv;
							}

							value +=vstr +" ~ "
						}

						if(value == null)
							value = "";
						if( value.equals( "null"))
							value = "";
						if( value.length() > 0)
							value = value.substring(0, value.length() - 3);
						if( typemap.get(str + "---").equals( "int")){
							sql += (value.equals("") ? -10 : value ) + ",";
						} else{
							sql += "'" + v
						}
					}
				}
			}
			catch ()
			{
			}
		}
	}
}