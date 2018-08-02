package Servlet;

import Dao.ResourceDao;
import Entity.Course;
import Service.CourseService;
import Service.OtherService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

public class OtherServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0,methodName.length()-6);
        System.out.println("you invoke " + methodName + " in userServlet");
        try {
            Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download(HttpServletRequest req, HttpServletResponse resp){
        try {
            req.setCharacterEncoding("UTF-8");
            String name = req.getParameter("name");//获取要下载的文件名
            //第一步：设置响应类型
            resp.setContentType("application/force-download");//应用程序强制下载
            //第二读取文件
            String path = getServletContext().getRealPath("/other/"+name);
            System.out.println(path);
            InputStream in = new FileInputStream(path);
            //设置响应头，对文件进行url编码
            name = URLEncoder.encode(name, "UTF-8");
            resp.setHeader("Content-Disposition", "attachment;filename="+name);
            resp.setContentLength(in.available());


            OutputStream out = resp.getOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while((len = in.read(b))!=-1){
                out.write(b, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //上传资源
    public void upload(HttpServletRequest request,HttpServletResponse response) throws Exception {
        System.setProperty("sun.jnu.encoding","utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        upload.setHeaderEncoding("UTF-8");
        HashMap<String,String> map = new HashMap<>();
        for(FileItem item:items){
            if(item.isFormField()){
                String name = item.getFieldName();
                String value = item.getString("UTF-8");
                System.out.println(name+"："+value);
                map.put(name,value);
            }else{
                int courseID = Integer.parseInt(map.get("courseID"));
                String otherPath =  item.getName();
                OtherService.addOther(courseID,otherPath);

                String filePath = "D:\\cly\\project\\out\\artifacts\\project_war_exploded\\other\\";
                String fileName = otherPath;
                String file = filePath + fileName;
                File storeFile = new File(file);
                item.write(storeFile);

                String filePath2 = "D:\\cly\\project\\web\\other\\";
                String file2 = filePath2 + fileName;
                File srcFile = new File(file);
                File targetFile = new File(file2);
                InputStream in = new FileInputStream(srcFile);
                OutputStream out = new FileOutputStream(targetFile);
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                in.close();
                out.close();

                Course course = CourseService.showCourseDetail(courseID);
                request.setAttribute("course",course);
                request.setAttribute("isTeacher",true);
                request.setAttribute("isTeacherOrStudent",true);
                request.setAttribute("others", OtherService.getOthers(courseID));
                request.getRequestDispatcher("/detail.jsp").forward(request,response);

            }

        }
    }

}

