package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import java.util.List;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Redirects the user to the Google logout page, which then redirects to the homepage.
*/
@WebServlet("/messagechart")
public class ChatServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    // The following line should match however you manipulated getMessages() in Step 1
    List<Message> msgList = datastore.getMessages(null);
    Gson gson = new Gson();
    String json = gson.toJson(msgList);
    response.getWriter().println(json);
  }
}
