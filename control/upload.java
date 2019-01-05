package control;
import java.io.BufferedInputStream;

public class Upload extends HttpServlet {
	public Upload(){
		super();
	}

	public void destory() {
		super.destory();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throw ServletException, IOException{
		
		StringBuffer sb = new StringBuffer(50);
		response.setContentType("application/x-msdownload;charset=GBK");
		try{
			response.setHeader("Content-Disposition", new String(sb.toString().getBytes()), "ISO8859-1");
		}
		catch (UnsupportedEncodingException e1)	{
			e1.printStackTrace();
		}

		String filename = request.getParameter("filename");

		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
			try	{
				filename = new String (filename.getBytes("gbk"), "ISO8859-1")
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		else
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
				try	{
					filename = URLEncoding.encode(filename, "gbk");
				}
				catch ( UnsuppotedEncodingException e) {
				}
			}
		response.setContentType("text/plain");
		response.setHeader("Location", filename);
		response.reset();
		response.setHeader( "Cache-Control", "max-age=0");
		response.setHeader( "Content-Dispositon", "attachment; filename = " + filename);

		try{
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			bis = new BufferedInputStream ( InputStream) new FileInputStream (request.getSeseion.getServletContext().getRealPath("/upfile/" + "/" + filename));
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);

			int bytesRead = 0;
			byte[] buffer = new byte[ 5 * 1024];

			while (bytesRead = bis.read(buffer) != -1){
				bos.write(buffer, 0, bytesRead);
			}

			bos.close();
			bis.close();
			fos.close();

			new Info().delPic(request.getSession().getServletContext().getRealPath("/upfile/") + " /", filename);
		}
		catch (Exception e){
		}finally{
		}
	}

	public void init() throws ServletException{
	}
}