package com.vse.http;

import com.vse.core.*;

/**
class: HttpServerProperties
Purpose: holds server properties for HTTP requests.
Notes:
Author: Tim Lane
Date: 24/03/2014

**/

import java.io.*;

public class HttpServerProperties {
  
  private String serverIP;
  private int ServerPort;
  private int ServerBacklog;
  
  boolean testFlag = false;
  
  public void setServerIP(String serverIP){
    this.serverIP = serverIP;
  }
  
  public String getServerIP(){
    return serverIP;
  }
  
  public void setServerPort(String ServerPort){
    this.ServerPort = Integer.parseInt(ServerPort);
  }

  public void setServerBacklog(String ServerBacklog){
    this.ServerBacklog = Integer.parseInt(ServerBacklog);
  }


  
  public int getServerPort(){
    return ServerPort;
    
  }
  
  public int getServerBacklog(){
    return ServerBacklog;
  }

  public boolean setTestFlag(String testFlagStr){
    if (testFlagStr.toUpperCase().matches("TRUE")){
      return this.testFlag = true;
    } else {
      return this.testFlag = false;
    }
  
  }

  public boolean getTestFlag(){
    return testFlag;
  }
 
}
