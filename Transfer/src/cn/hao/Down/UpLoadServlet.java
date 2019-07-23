package cn.hao.Down;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@WebServlet("/UpLoadServlet")
@MultipartConfig
public class UpLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取文件名称
        request.setCharacterEncoding("utf-8");
        ServletContext context = this.getServletContext();
        //获取服务器存储位置的真实路径
        String upload = context.getRealPath("upload");
        if(!new File(upload).exists()){
            new File(upload).mkdirs();
        }
        //获取part对象
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            //通过part对象得到文件名
            String submittedFileName = part.getSubmittedFileName();
            Collection<String> headerNames = part.getHeaderNames();
            //将文件名进行拼接
            File file=new File(upload,submittedFileName);
            //通过part对象获取上传的数据流
            InputStream is =part.getInputStream();
            //将上传的数据存储到服务器
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
            byte[] bys=new byte[1024*8];
            int len ;
            while((len=is.read(bys))!=-1){
                bos.write(bys,0,len);
            }
            bos.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
