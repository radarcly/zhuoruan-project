package Servlet;
import Entity.User;
import Service.UserService;
import Tool.MailTool;
import Tool.Md5Tool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import static Tool.Tool.isNumeric;

public class UserServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0,methodName.length()-5);
        System.out.println("you invoke " + methodName + " in userServlet");
        try {
            Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getKey(HttpServletRequest request, HttpServletResponse response){
        int key = (int)(Math.random()*9000)+1000;
        request.getSession().setAttribute("key",key);
        try {
            request.setCharacterEncoding("UTF-8");
            String username =  request.getParameter("username");
            String password =  request.getParameter("password");
            String rpassword =  request.getParameter("rpassword");
            String email = request.getParameter("email");
            if(email.equals("")){
                request.setAttribute("message","邮箱地址不能为空");
            }else{
                MailTool.sendMail(email,Integer.toString(key));
                request.setAttribute("message","验证码已发送");
            }
            request.setAttribute("username",username);
            request.setAttribute("password",password);
            request.setAttribute("rpassword",rpassword);
            request.setAttribute("email",email);
            request.getRequestDispatcher("/register.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //登录
    public void login(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            String username =  request.getParameter("username");
            String password =  request.getParameter("password");
            if (username.equals("")||username.equals("")){
                request.setAttribute("message","用户名或密码为空");
                request.setAttribute(username,username);
                request.setAttribute(password,password);
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }else{
                password = Md5Tool.getMD5(password);
                int result  = UserService.login(username,password);
                if(result==-1){
                    request.setAttribute("message","用户名不存在或密码错误");
                    request.setAttribute(username,username);
                    request.setAttribute(password,password);
                    request.getRequestDispatcher("/login.jsp").forward(request,response);
                }else{
                    User user = new User(result,username,password);
                    request.getSession().setAttribute("user",user);
                    request.getRequestDispatcher("/index.jsp").forward(request,response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注册
    public void register(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(request.getSession().getAttribute("key")==null){

            String username =  request.getParameter("username");
            String password =  request.getParameter("password");
            String rpassword =  request.getParameter("rpassword");
            request.setAttribute("message","请先获取验证码");
            request.setAttribute("username",username);
            request.setAttribute("password",password);
            request.setAttribute("rpassword",rpassword);
            try {
                request.getRequestDispatcher("/register.jsp").forward(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            int keyInt =(int)request.getSession().getAttribute("key");
            String key = String.valueOf(keyInt);
            System.out.println("发送的验证码为" + key);
            try {

                String username =  request.getParameter("username");
                String password =  request.getParameter("password");
                String rpassword =  request.getParameter("rpassword");
                String inputKey = request.getParameter("key");
                if(username.equals("")){
                    request.setAttribute("message","用户名为空");
                    request.getRequestDispatcher("/register.jsp").forward(request,response);
                }else if(password.length()<6){
                    request.setAttribute("message","密码长度必须大于等于6");
                    request.setAttribute("username",username);
                    request.getRequestDispatcher("/register.jsp").forward(request,response);
                }else if(isNumeric(password)){
                    request.setAttribute("message","密码不能为纯数字");
                    request.setAttribute("username",username);
                    request.getRequestDispatcher("/register.jsp").forward(request,response);
                }
                else if(!password.equals(rpassword)){
                    request.setAttribute("message","两次密码输入不相等");
                    request.setAttribute("username",username);
                    request.getRequestDispatcher("/register.jsp").forward(request,response);
                }else if(!key.equals(inputKey)){
                    request.setAttribute("message","验证码错误");
                    request.setAttribute("username",username);
                    request.setAttribute("password",password);
                    request.setAttribute("rpassword",rpassword);
                    request.getRequestDispatcher("/register.jsp").forward(request,response);
                } else{
                    password = Md5Tool.getMD5(password);
                    int result  = UserService.register(username,password);
                    if(result==-1) {
                        request.setAttribute("message", "用户名" + username + "已被使用");
                        request.getRequestDispatcher("/register.jsp").forward(request, response);
                    }else{
                        User user = new User(result,username,password);
                        request.getSession().setAttribute("user",user);
                        request.getRequestDispatcher("/index.jsp").forward(request,response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //退出登录
    public void exit(HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("user",null);
        try {
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
