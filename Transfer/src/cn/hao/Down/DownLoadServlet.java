package cn.hao.Down;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/DownLoadServlet")
public class DownLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求行里的文件名
        String filename = request.getParameter("filename");
        //获取文件在服务器内存里的真实路径
        ServletContext context = this.getServletContext();
        String realPath = context.getRealPath("/img/" + filename);
       // System.out.println(realPath);
        //连接流
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(realPath));
        //获取文件MIME类型
        String mimeType = context.getMimeType(filename);
        //设置响应头文件
        response.setHeader("context-type",mimeType);
        //设置文件打开方式
        String header = request.getHeader("user-agent");
        String fileName = DownLoadUtils.getFileName(header, filename);
        System.out.println(fileName);
        response.setHeader("content-disposition","attachment;filename="+fileName);
        //响应输出流
        byte[] bys=new byte[1024*8];
        while(bis.read(bys)!=-1){
            response.getOutputStream().write(bys);
        }
        bis.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
