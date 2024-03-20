package img;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@SuppressWarnings("serial")
@WebServlet("/AddImage")
public class AddImage extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	   Part file=req.getPart("image");
	   String imageFileName=file.getSubmittedFileName();
	   System.out.println("selected Image Name  "+imageFileName);
	   String uploadPath="C:/Users/HP/eclipse-workspace/ImageHandling/src/main/webapp/image/"+imageFileName;
	 
	   try {
		
	
	  FileOutputStream fos =new FileOutputStream(uploadPath);
	  InputStream is=file.getInputStream();
	  byte[] data=new byte[is.available()];
	  is.read(data);
	  fos.write(data);
	  fos.close();
	   } catch (Exception e) {
			e.printStackTrace();
		}
	  
	   Connection conn=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		   conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","12345");
		String sql="insert into image(imageFileName) values(?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, imageFileName);
		int i=ps.executeUpdate();
		
		if (i==1) {
			System.out.println("successfully upload");
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	
}
