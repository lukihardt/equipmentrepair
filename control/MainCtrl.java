package control;

import java.io.BufferedInputStream;

public class MainCtrl extends HttpServlet
{	public MainCtrl()
	{	super();
	}

	public void destroy()
	{	super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{	this.doPost(request, response);
	}

	public void go(String url, HttpServletRequest request, HttpServletResponse response)
		{	try
			{	request.getRequestDispatcher(url).forward(request, response);
			}catch (ServletException e)
			{	e.printStackTrace();
			}catch (IOException e)
			{	e.printStackTrace();
			}
		}
	public void gor(String url, HttpservletRequest request, HttpServletResponse response)
	{	try
		{	response.sendRedirect(url);
		}catch (IOExcetion e)
		{	// TODD Auto-generated catch block
			e.printStackTrace();
		}
	}

	//代码格式：使用java大括号模式
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throw ServletException, IOException{
		response.setContenType("text/html");
		PrintWriter out = response.getWriter();
		String ac = request.getParameter("ac");
		if(ac == null)ac="";
		CommDAO dao = new CommDAO();
		String date Info.getDateStr();
		String today = date.substring(0,10);
		String tomoth = date.substring(0,7);
	

		if(ac.equals("login")){
			String username = request.getParameter("username");
			String password = request.getParameter("pwd1");
			String utype = request.getParameter("cx");
			String pagerandom = request.getParater("pagerandom")==null?"":request.getParameter("pagerandom);
			String random = (Srting)request.getSession().getAttribute("random");
			if(!pagerandom.equals(random)&&request.getParameter("a")!=null){
				request.setAtrribute("random", "");
				go("/index.jsp", request, response);
			}
			else{
				String sql1="";
				if(utype.equals("RegisterAccount")){
					sql = "select * from yonghuzhuce where yonghuming = '"+username+"' and mima='"+password+"' and issh='是'"; 
				}
				else{
					sql1 = "select * from StaffInfo where StaNum = '"+username+"' and mima = '" + password + "'";
				}
				List<HashMap> userlist1 = dao.select(sql1);
				if(userlist.size()==1){
					if (utype.equals("Register_Account")){
						request.getSession().setAttribute("username", userlist1.get(0).get("UserNum"));
					}
					else{
						request.getSession().setAttribute("username", userlist1.get(0).get("StaNum"));
					}
					request.getSession().setAttribute("cx",utype);
					gor("index.jsp", request, response);
				}
				else{
					request.setAttribute("error", "");
					go("/index.jsp", request, response);
				}
			}
		}
		if(ac.equals("adminlogin")){
			String username = request.getParameter("username");
			String password = request.getParameter("pwd");
			String utype = request.getParameter("cx");
			String pagerandom = request.getParameter("pagerandom")=null ? "" : request.getParameter("pagerandom");
			String random = (String) request.getSession().getAttribute("random");
			if (!pagerandom.equals(random) && request.getParameter("a" != null)){
				request.setAttribute("random", "");
				go("/login.jsp", request, response);
			}else {
				String sql1 = "";
				if (utype.equals("Administrator")){
					sql1 = "select" * from allusers where username='" + username + "' and pwd='" + password + "' ";
				}else if (utype.equals("TcherRegister")){
					sql1 = "select * from TeacherRegister where TcherNum= '" + username + "' and mima ='" + password + "'";
				}else if (utype.equals("StdRegister")){
					sql1 = "select * from StudentRegister where StdNum= '" +username + "' and mima= '" + password + "'";
				}else if (utype.equals("RepWorkerRegister")){
					sql1 = "select * from RepairWorker where RpNum '" + username + "' and mima= '" + password + "'";
				}
				List <HashMap> userlist1 = dao.select(sql1);
				if (userlist1.size()==1){
					if (utype.equals("Administrator")){
						request.getSession().setAttribute("username", userlist1.get(0).get("username"));
						request.getSession().setAttribute("cx", userlist1.get(0),get("cx"));
					}
					if (utype.equals("TecherRegister")){
						request.getSession().setAttribue("username",userlist1.get(0),get("TcherNum"));
						request.getSession().setAttribute("cx", utype);
					}
					if (utype.equals("StdRegister")){
						request.getSession().setAttribute("username", userlist1.get(0),get("StdNum"));
						request.getSession().setAttribute("cx", utype);
					}
					if (utype.equals("RepWorkerRegister")){
						request.getSession().setAttribute("username", userlist1.get(0).get("PpNum"));
						request.getSession().setAttribute("cx", utype);
					}
					gor("main.jsp", request, response);
				} else {
					request.setAttribute("error", "");
					go("/login.jsp", request, response);
				}
				
			}
		}
		if (ac.equals("uppass")){
			String oldUserPass = request.getParameter("ymm");
			String userPass = request.getparameter("xmm1");
			String copyUserPass = request.getParameter("xmm2");
			HashMap m = dao.getmaps("UserName", (String)request.getSession().getAttribute("username"),"UserRegister");
			if (!(((String) m.get("mima")).equals(olduserpass))){
				request.setAttribute("error", "");
				go("mod2.jsp", request, response);
			} else {
				String sql = "update UserRegister set PassWord = '" + userpass  + "'where UserName = '" + (String)request.getSession)(),getAttribute("username") + "'";
				dao.commOper(sql);
				request.setAttribute("suc", "");
				go("mod2.jsp", request, response);
			}
		}
		if (ac.equals("adminuppass")){
			String olduserpass = request.getParameter("ymm");
			String userpass = request.getParameter("xmm1");
			String copyuserpass request.getParamter("xmm2");
			HashMap m dao.getmaps("username", (String)request.getSession().getAttribute("username"), "allusers");
			if (!(((String)m.get("pws")).equals(olduserpass))){
				request.setAttribute("error", "");
				go("mod.jsp", request, response);
			} else {
				String sql = "update allusers set pwd = '" + userpass + "' where username = '" + (String)request.getSession().getAttribute("username") + "'";
				dao.commOper(sql);
				request.setAttribute("suc", "");
				go("mod.jsp", request, response);
			}
		}
		if (ac.equals("uploaddoc")){
			try{
				String filename = "";
				request.setCharacterEncoding("gbk");
				requestContext request = new ServletRequestContext(request);
				if(FileUpload.isMultipartContent(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory;
					factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/"));
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setSizeMax(100 * 1024 * 1024);
					List items = new ArrayList();
					items upload.parseRequest(request);
					FileItems fileItem = (FileItem)items.get(0);
					if (fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() != null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName());
							filename = Info.generalFileName(fullFile.getName());
							File newFile = new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/" + filename);
							try	{
								fileItem.write(newFile);					
							}
							catch (Exception e){
								e.printStackTrace();
							}
							
						} else{
						}
					}
				}
				go("/js/uploaddoc.jsp?docname = " + filename, request, response);
			} catch (Exception e1){
				e1.printStackTrace();
			}
		}
		if (ac.equals("uploaddoc2")){
			try{
				String filename = "";
				request.setCharacterEncoding("gbk");
				RequestContext requestContext = new ServletRequestContext(request);
				if(FileUpload,isMultipartContent(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/"));
					ServletFileUpload upload =  new ServletFileUpload(factory);
					upload.setSizeMax(100 * 1024 * 1024);
					List items = new ArrayList();
					items = upload.parseRequest(request);
					FileItem fileItem = (FileItem)items.get(0);
					if(fileItem.getName() != null && fileItem.getSize() != 0){
						if(fileItem.getName() != null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName());
							filename = Info.generalFileName(fullFile.getName());
							File newFile = new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/" + filename);
							try{
								fileItem.write(newFile);
							}
							catch (Exception e){
								e.printStackTrace();
							}
						}else{
						}
					}
				}
				go("/js/uploaddoc2.jsp?docname =" + filename, request, response);
			}
			catch (Exception e1){
				e1.printStackTrace();
			}
		}
		if (ac.equals("uploaddoc3")){
			try{
				String filename = "";
				request.setCharacterEncoding("gbk");///////////////////////////////////
				RequestContext requestContext = new ServletRequestContext(request);
				if (FileUpload.isMultipartContent(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/"));
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setSizeMax(100 * 1024 * 1024);
					List items = new ArrayList();
					items upload.parseRequest(request);
					FileItem fileItem = (FileItem) items.get(0);
					if (fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() != null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName());
							filename = Info.generalFileName(fullFile.getName());
							File newFile = new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/");
							try{
								fileItem.write(newFile);
							}
							catch (Exception e){
								e.printStackTrace;
							}
						}else {
						}
					}
				}
				go ("/js/uploaddoc3.jsp?docname=" + filename, request, response);
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}
		if (ac.equals("importexcel")){
			String page = request.getParameter("page");
			String whzdstr = reqeust.getParemeter("whzdstr");
			String tablename = request.getParameter("tablename");
			try{
				String filename = "";
				request.setCharacterEncoding("gbk");//////////////////////////////////
				RequestContext requestContext = new ServletRequestContext(reqeust);
				if (FileUpload.isMultipartContext(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/upfile/" + "/")));//////////////////////
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setSizeMax(100 * 1024 * 1024);
					List items = new ArrayList();
					items = upload.parseRequest(request);
					FileItem fileItem = (FileItem)items.get(0);
					if (fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() != null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName());
							filename = Info.generalFileName(fullFile.getName());
							File newFile = new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/" + filename);
							try{
								fileItem.write(newFile);
							}
							catch (Excepion e)
							{	e.printStackTrace();
							}
						}else{
						}
					}

					if (filename.indexOf(".xls") > -1){
						Workbook workbook;
						try{
							workbook = Workbook.getWorkbook(new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/" + filename));
							Sheet sheet = workbook.getSheet(0);
							for (int i =1 ; i < 100 ; i ++ ){
								Cell cell = null;
								try{
									String isql = "insert into " + tablename + "(" ;
									for (String str : whzdstr.split("-")){
										isql += str + ",";
									}
									isql = isql.substring(0, isql.length() - 1);
									isql += ")values";

									int j=0;
									int empty =1;

									for(String str : whzdstr.split("-")){
										cell = sheet.getCell(j,i);
										isql += "'" + cell.getContents0() + "',";
										String content = cell.getContents() == null ? "" : cell.getContents();
										if (!"".equals(content.trim())){
											empty = 0;
										}
										j++;
									}
									if (empty == 1)
										continue;
									isql = isql.substring(0, isql.length() - 1);
									isql += ")";
									dao.commOper(isql);	
								}
								catch (Exception e)
								{	continue;
								}
							}
							workbook.close();
						}
						catch (Exception e){
							e.printStackTrace();
						}
					}
				}
				go("/admin/" + page + "?docname" + filename, request, response);
			}
			catch (Exception e1)
			{	e1.printStackTrace();
			}
		}
		
		if (ac.equals("uploadimg")){
			try{
				String filename = "";
				request.setCharacterEncoding(gbk);  ///////////////////////////////
				RequestContext requestContext = new ServletRequestContext(request);

				if (FileUpload.isMultipartContent(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository(new File(request.getSession.getServletContext().getRealPath("/upfile/") + "/"));
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setSizeMax(100 * 1024 *1024);
					List items = new ArrayList();
					items = upload.parseRequest(request);
					FileItem fileItem = (FileItem) items.get(0);

					if(fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() != null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName());
							filename = Info.generalFileName(fullFile.getName);
							File newFile = new File(request.getSession().getServletContext().getRealPath("/upfile/" + "/" + filename));
							try{
								fileItem.write(newFile);
							}
							catch (Exception e){	
								e.printStackTrace();
							}
						}else{
						}
					}	
				}	
			go("/js/uploadimg.jsp?filename=" + filename, request, response);
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}

		if(ac.equals("uploadimg2")){
			try{
				String filename = "";
				request.setCharacterEncoding("gbk");  //////////////////////////////////
				RequestContext requestContext = new ServletRequestContext(request);
				if (FileUplaod.isMultipartContent(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository(new File(request.getSession.getServletContext().getRealPath("/upfile/") + "/"));
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setSizeMax(100 * 1024 * 1024);
					List items = new ArrayList();
					items upload.parseRequest(request);
					FileItem fileItem = (FileItem) items.get(0);

					if (fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() != null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName());
							filename = Info.generalFileName(fullFile.getName());
							File newFile = new File(request.getSession().getServletContext.getRealPath("/upfile/") + "/" + filename);
							try{
								fileItem.write(newFile);
							}
							catch (Exception e){
								e.printStackTrace();
							}
						}else{
						}
					} 
				}
			}

			go("/js/uploadimg2.jsp?filename=" + filename, request, response);

			catch (Exception e1){
				e1.printStackTrace();
			}
		}

		if (ac.equals("uploadimg3")){
			try{
				String filename = "";
				request.setCharacterEncoding("gbk");///////////////////////
				RequestContext requestContext =  new ServletRequestContext(request);
				if (FileUpload.isMultipartContent(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/"));
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setSizeMax(100 * 1024 * 1024);
					List items = new ArrayList();
					items = upload.paresRequest(request);
					FileItem fileItem = (FileItem) items.get(0);
					if (fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() != null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName);
							filename = Info.generalFileName(fullFile.getName());
							File newFile = new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/" + filename);
							try{
								fileItem.write(newFile);
							}
							catch (Exception e){
								e.printStackTrace();
							}
						} else{
						}
					}
				}

				go("/js/uploadimg3.jsp?filename=" + filename, request, response);

			}
			catch ( Exception e1) {
				e1.printStackTrace();
			}
		}

		if ( ac.equals("uploadimg4")){
			try{
				String filename = "";
				request.setCharacterEncoding(" gbk");///////////////////
				RequestContext requestContext = new ServletRequestContext(request);
				if(FileUpload.isMultipartContent(requestContext)) {
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository( new File( request.getSession().getServletContext().getRealPath("/upfile/") + "/"));
					ServletFileUpload upload = new ServletFileUpload( factory);
					upload.setSizeMax(100 * 1024 * 1024);
					List items = new ArrayList();
					items = upload.parseRequest( request);
					FileItem fileItem = ( FileItem) items.get(0);
					if (fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() != null && fileItem.getSize() !=0){
							File fullFile = new File( fileItem.getName());
							filename = Info.generalFileName( fullFile.getName());
							File newFile = new File( request.getSession().getServletContext().getRealPath("/upfile/") + "/" + filemame);
							try	{
								fileItem.write(newFile);
							}
							catch ( Exception e){
								e.printStackTrace();
							}
						} else{
						}
					}
				}

				go(" /js/uploadimg4.jsp?filename=" + filename, request, response);

			}
			catch (Exception e1){
				e1.printStackTrace();
			}
		}

		if( ac.equals(" uploadimg5")){
			try{
				String filename = "";
				request.setCharacterEncoding(" gbk");
				RequestContext requestContext = new ServletRequestContext(request);
				if ( FileUpload.isMultipartContent(requestContext)){
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setRepository( new File(request.getSession().getServletContext().getRealPath( "/upfile/" + "/")));
					ServletFileUpload upload = new ServeltFileUpload(factory);
					List items = new ArrayList();
					items = upload.parseRequest(request);
					FileItem fileItem = (FileItem) items.get(0);
					if (fileItem.getName() != null && fileItem.getSize() != 0){
						if (fileItem.getName() ！= null && fileItem.getSize() != 0){
							File fullFile = new File(fileItem.getName());
							filename = Info.generalFileName(fullFile.getName());
							File newFile = new File(request.getSession().getServletContext().getRealPath("/upfile/") + "/" + filename);
							try	{
								fileItem.write(newFile);
							}
							catch (Exception e)	{
								e.printStackTrace();
							}
						}else {
						}
					}
				}

				go("/js/uploadimg5.jsp?filename=" + filename, request, response);

			}
			catch (Exception e1){
				e1.printStackTrace();
			}
		}

		dao.close();
		out.flush();
		out.close();
	}
	public void init() throw ServletException{
	}
}