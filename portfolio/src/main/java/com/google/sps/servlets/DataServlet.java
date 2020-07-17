package com.google.sps.servlets;

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
        ArrayList<String> comments = new ArrayList<String>();
        Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable())
        { 
            HashMap<String,String> obj = new HashMap<String,String>();
            obj.put("comment",(String) entity.getProperty("comment"));    
            obj.put("userEmail",(String) entity.getProperty("userEmail"));
            comments.add(convertHashMapToJson(obj));
        }
        String jsonComments = convertStringToJson(comments);        
        try 
        {
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
        UserService userService = UserServiceFactory.getUserService();
        Entity commentEntity = new Entity("Comment");
        String email = userService.getCurrentUser().getEmail();        
        String comment= getParameter(request,"add-comment","");
        long timestamp = System.currentTimeMillis();
        commentEntity.setProperty("comment", comment);
        commentEntity.setProperty("timestamp", timestamp);
        commentEntity.setProperty("userEmail", email);
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

  private String convertStringToJson(ArrayList<String> comments)
  {
      Gson gson = new Gson();
      return gson.toJson(comments);
  }
  private String convertHashMapToJson(HashMap<String,String> comments)
  {
      Gson gson = new Gson();
      return gson.toJson(comments);
  }
}
