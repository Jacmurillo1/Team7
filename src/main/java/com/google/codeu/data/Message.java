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

import java.util.Objects;
import java.util.UUID;

/** A single message posted by a user. */
// added a recipient to the class message
public class Message {
  private String recipient;
  private UUID id;
  private String user;
  private String text;
  private long timestamp;
  private String imageUrl;

  /**
   * Constructs a new {@link Message} posted by {@code user} with {@code text} content. Generates a
   * random ID and uses the current system time for the creation time.
   */
  public Message(String user, String text, String recipient) {
    this(UUID.randomUUID(), user, text, System.currentTimeMillis(), recipient);
  }

  public Message(UUID id, String user, String text, long timestamp, String recipient) {
    this.id = id;
    this.user = user;
    this.text = text;
    this.timestamp = timestamp;
    this.recipient = recipient;
  }

  //constructor with imageUrl parameter
  public Message(UUID id, String user, String text, long timestamp, String recipient, String imageUrl) {
    this.id = id;
    this.user = user;
    this.text = text;
    this.timestamp = timestamp;
    this.recipient = recipient;
    this.imageUrl = imageUrl;
  }

  public UUID getId() {
    return id;
  }

  public String getUser() {
    return user;
  }

  public String getText() {
    return text;
  }

  public long getTimestamp() {
    return timestamp;
  }
  // new addition added a recipient
  public String getRecipient(){
    return recipient;
  }

  public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl;}

  public String getImageUrl() { return imageUrl;}

  @Override
  public boolean equals(Object message) {
    Message m = (Message)message;
    return id.equals(m.id) && Objects.equals(user, m.user) && Objects.equals(text, m.text)
            && Objects.equals(timestamp, m.timestamp) && Objects.equals(recipient, m.recipient)
            && Objects.equals(imageUrl, m.imageUrl);
  }
}
