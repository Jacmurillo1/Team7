/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.data;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Provides access to the data stored in Datastore. */
public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
  

  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty("user", message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());
    //new addition all it does is storing the recipient in our data
    messageEntity.setProperty("recipient", message.getRecipient());

    datastore.put(messageEntity);
  }

  /**
   * Gets all the messages or message from a certian user.
   *
   * @return a list of all messages posted if singleUser is true, if false returns all messages,
   *    returns empty is there are no messages. List is sorted by time descending.
   */
   //changes the behavior so the function returns the messages where the user is the recipient instead of the author
  public List<Message> getMessageOrMessages(String recipient, boolean singleUser) {
    List<Message> messages = new ArrayList<>();

    Query query;

    //singleUser is true if you want to filter messages from one person
    if(singleUser){
      query = new Query("Message")
      .setFilter(new Query.FilterPredicate("recipient", FilterOperator.EQUAL, recipient)) // change made so it is the recipient
      .addSort("timestamp", SortDirection.DESCENDING);

    }else{
      query = new Query("Message")
      .addSort("timestamp", SortDirection.DESCENDING);
      
    }
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String user = "";
        //if returns all messages
        if(!singleUser){
          //adds user to be the one who posts and not the one to recieve
          user = (String) entity.getProperty("user");
        }
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");

        // now adds recipient to the constructor
        Message message = new Message(id, user, text, timestamp, recipient);

        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return messages;
  }

  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<Message> getMessages(String user) {
    return getMessageOrMessages(user, true);
  }

  /**
   * Gets all the messages.
   *
   * @return a list of all messages posted, or empty list if
   *    there are no messages. List is sorted by time descending.
   */
  public List<Message> getAllMessages() {
    return getMessageOrMessages(null,false);
  }


    /**
     * Returns the total number of messages for all users.
     */
    public int getTotalMessageCount() {
        Query query = new Query("Message");
        PreparedQuery results = datastore.prepare(query);
        return results.countEntities(FetchOptions.Builder.withLimit(1000));
    }
}
