package com.vse.http;

import com.vse.core.*;
/**
class: HttpServerProperties
Purpose: holds server properties for HTTP requests.
Notes:
Author: Tim Lane
Date: 24/03/2014

**/

import org.w3c.dom.Element;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.net.InetAddress;


  
public class HttpProperties {
  
  private String serverIP;
  private int ServerPort;
  private int ServerBacklog;
  private String configFileName;
  private int threadCount;
  private String sslKeyStore;
  private String sslKeyPswd;
  private String sslKeyStorePswd;
  private String sslCertStore;
  private boolean sslClientAuth;
  private int activeThreadCount = 0;
  
  public static final String HOST_TAG = "Host";
  public static final String PORT_TAG = "Port";
  public static final String THREAD_COUNT_TAG = "ThreadCount";
  public static final String SSL_KEY_STORE_TAG = "SSLKeyStore";
  public static final String SSL_KEY_PASSWORD_TAG = "SSLKeyPswd";
  public static final String SSL_KEY_STORE_PASSWORD_TAG = "SSLKeyStorePswd";
  public static final String SSL_CERT_STORE_TAG = "SSLCertStore";
  public static final String SSL_CLIENT_AUTH_TAG = "SSLClientAuth";

  boolean testFlag = false;
  
  public HttpProperties(Element httpReceiverElement) {
    
    setServerIP(httpReceiverElement.getAttribute(HOST_TAG));
    setServerPort(httpReceiverElement.getAttribute(PORT_TAG));
    setThreadCount(httpReceiverElement.getAttribute(THREAD_COUNT_TAG));
    setSslKeyStore(httpReceiverElement.getAttribute(SSL_KEY_STORE_TAG));
    setSslKeyPswd(httpReceiverElement.getAttribute(SSL_KEY_PASSWORD_TAG));
    setSslKeyStorePswd(httpReceiverElement.getAttribute(SSL_KEY_STORE_PASSWORD_TAG));
    setSslCertStore(httpReceiverElement.getAttribute(SSL_CERT_STORE_TAG));
    setSslClientAuth(httpReceiverElement.getAttribute(SSL_CLIENT_AUTH_TAG));
    
  }
  
  
  public void setActiveThreadCount(){
    this.activeThreadCount++ ;
  }
  
  public int getActiveThreadCount(){
    return activeThreadCount;
  }
  
  public void setThreadCount(String threadCount){
    this.threadCount = Integer.parseInt(threadCount);
  }
  
  public int getThreadCount(){
    return threadCount;
  }
     
  public void setServerIP(String serverIP){
// 2.3     
    if (serverIP.toLowerCase().equals("localhost")){
      try {
        serverIP = InetAddress.getLocalHost().getHostAddress();
      } catch (Exception e) {
        System.out.println("httpproperties: error extracting localhosts. " + e);
        e.printStackTrace();
        System.exit(1);
      }
    }
    this.serverIP = serverIP;
    
  }
  
  public String getServerIP(){
    return serverIP;
  }
  
  // setSslKeyStore
  
  public void setSslKeyStore(String sslKeyStore){
    this.sslKeyStore = sslKeyStore;
  }
  
  public String getSslKeyStore(){
    return sslKeyStore;
  }
  
  // sslKeyPswd
  
  public void setSslKeyPswd(String sslKeyPswd){
    this.sslKeyPswd = sslKeyPswd;
  }
  
  public String getSslKeyPswd(){
    return sslKeyPswd;
  }
  
  // sslKeyStorePswd
    
  
  public void setSslKeyStorePswd(String sslKeyStorePswd){
    this.sslKeyStorePswd = sslKeyStorePswd;
  }
  
  public String getSslKeyStorePswd(){
    return sslKeyStorePswd;
  }
  
  // sslCertStore
  
  public void setSslCertStore(String sslCertStore){
    this.sslCertStore = sslCertStore;
  }
  
  public String getSslCertStore(){
    return sslCertStore;
  }
  
  // sslClientAuth
  
  
  public boolean setSslClientAuth(String sslClientAuth){
    if (sslClientAuth.toUpperCase().matches("TRUE")){
      return this.sslClientAuth = true;
    } else {
      return this.sslClientAuth = false;
    }
  
  }

  public boolean getSslClientAuth(){
    return sslClientAuth;
  }
  
   public void setConfigFileName(String configFileName){
    this.configFileName = configFileName;
  }
  
  public String getConfigFileName(){
    return configFileName;
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
