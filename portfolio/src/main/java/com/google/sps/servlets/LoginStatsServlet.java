package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.logging.Logger;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/loginStats")
public class LoginStatsServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        response.setContentType("application/json;");
        Gson gson = new Gson();
        if(userService.isUserLoggedIn())
        {
            response.getWriter().println(gson.toJson("1"));
        }
        else
        {    
            String urlToRedirectToAfterUserLogsIn = "/";
            String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

            response.getWriter().println(gson.toJson("<p>Hello stranger.</p>"));
            response.getWriter().println(gson.toJson("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>"));
        }
  }
}
