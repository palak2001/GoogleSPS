package com.google.sps.servlets;

import com.google.sps.Comment;
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
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet{

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<Comment> comments = new ArrayList<>();
        Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable())
        {
            Comment comment = new Comment((String)entity.getProperty("comment"),(String)entity.getProperty("userEmail"),(String)entity.getProperty("image"));
            comments.add(comment);
        }
        String jsonComments = gson.toJson(comments);
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
  {     System.out.println("Post is called");
        Gson gson = new Gson();
        UserService userService = UserServiceFactory.getUserService();
        Entity commentEntity = new Entity("Comment");
        String email = userService.getCurrentUser().getEmail();        
        String comment= getParameter(request,"add-comment","");
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler");
        System.out.println(comment);
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
