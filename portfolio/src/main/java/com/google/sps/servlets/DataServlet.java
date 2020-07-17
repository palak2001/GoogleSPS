package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.HashMap;
import java.util.logging.Logger;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet{

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gsonHashMap = new Gson();
        Gson gsonString = new Gson();
        ArrayList<String> comments = new ArrayList<String>();
        Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable())
        { 
            HashMap<String,Object> obj = new HashMap<String,Object>();
            obj.put("comment",entity.getProperty("comment"));    
            obj.put("email",entity.getProperty("userEmail"));
            obj.put("image",entity.getProperty("image"));
            String str=gsonHashMap.toJson(obj);
            comments.add(str);
            
        }
        String jsonComments = gsonString.toJson(comments);
        System.out.println(jsonComments);    
        try 
        {
            System.out.println(jsonComments);
            response.setContentType("application/json;");
            response.getWriter().println(jsonComments);
        }
        catch (IOException e)
        {
            System.err.println("Failed: ");
            e.printStackTrace();
        }
    }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
  {
        Gson gson = new Gson();
        UserService userService = UserServiceFactory.getUserService();
        Entity commentEntity = new Entity("Comment");
        String email = userService.getCurrentUser().getEmail();        
        String comment= getParameter(request,"add-comment","");
        comment= comment.replaceAll("\\\\", "");
        comment= comment.replaceAll("\r\n|\n|\r", "");
        comment= comment.replaceAll("(\\\\r\\\\n|\\\\n)", "");
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler");
        long timestamp = System.currentTimeMillis();
        commentEntity.setProperty("comment", comment);
        commentEntity.setProperty("timestamp", timestamp);
        commentEntity.setProperty("userEmail", email);
        commentEntity.setProperty("image", uploadUrl);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);
        try
        {
            response.sendRedirect("/index.html");
        }
        catch (IOException e)
        {
            System.err.println("Failed: ");
            e.printStackTrace();
        }
  }

  public String getParameter(HttpServletRequest request, String text, String defaultValue)
  {
        String value= request.getParameter(text);
        if(value=="")
        {
            return defaultValue;
        }
        return value;
  }
}
