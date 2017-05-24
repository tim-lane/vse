package com.vse.http;

import com.vse.core.*;
/**
class: HttpsStub
Purpose: main method for HTTPS stubbing
Notes: httpS only
Author: Tim Lane
Date: 24/03/2014
Version: 
0.1 24/03/2014 lanet - initial write
**/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.FileAppender;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.security.KeyStore;
import java.security.Security;

import java.util.ArrayList;
import java.util.List;


public class httpsStub {
  
  private HttpProperties httpProperties;
  private ServerSocket serverSocket;
  private HttpBaseLineMessage httpBaseLineMessage;
  private LogFileProperties logFileProperties;

  
  List<String> receiverEventsCntr = new ArrayList<String>();
  
  private static String httpVersion = "1.1";
  // Create an HTTPS Stub for a particular TCP port
  public httpsStub(HttpProperties httpProperties, LogFileProperties logFileProperties)
  {
    this.httpProperties = httpProperties;
    this.logFileProperties = logFileProperties;
  }
    
  static Logger logger = Logger.getLogger(httpsStub.class);
  public static void main(String[] args) {
    
    /*
     * get config file, need command line option
     */
    String configFileName = null;
    String configMessage = null;
    /*
     * get xml file from the command line
     */
    if (args.length > 0) {
      configFileName = args[0];
    } else { // default for testing purposes.
      configFileName = "c:\\_git\\vse\\xml\\vsehttp.xml";
      System.out.println("tcpStub: using default config file: " + configFileName);
    } 
    configMessage = "XML config file: " + configFileName;
    
    try {
      /*
       * open XML config file and from xml config file read HTTP properties
       */
      XMLExtractor extractor = new XMLExtractor(new FileInputStream(new File(configFileName)));
      HttpProperties httpProperties = new HttpProperties(extractor.getElement("HTTPServer"));
      httpProperties.setConfigFileName(configFileName);
     
     /*
     * setup logging
     * TRACE < DEBUG < INFO < WARN < ERROR < FATAL
     */
      LogFileProperties logFileProperties = new LogFileProperties(extractor.getElement("Header")) ;
      PropertyConfigurator.configure(logFileProperties.getLogFileName());
      logger.setLevel(Level.INFO); // for INFO for header information
      logger.info("httpsStub: version " + httpVersion);
      logger.info(configMessage);
      logger.info("log4j config file : " + logFileProperties.getLogFileName());
      httpsStub httpsStub = new httpsStub(httpProperties, logFileProperties);
      httpsStub.RunIsolator();
    } catch (Exception e) {
      logger.error("httpsStub: error extracting XML file " + configFileName + ". " + e);
      e.printStackTrace();
      System.exit(1);
    }
   
  }
    
ServerSocket getSslServerSocket() throws Exception
    {
        logger.info("Preparing SSL Server socket on Host " + httpProperties.getServerIP() + " Port " + httpProperties.getServerPort());
        // Make sure that JSSE is available
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        // A keystore is where keys and certificates are kept
        // Both the keystore and individual private keys should be password protected
        KeyStore keystore = KeyStore.getInstance("JKS");
        
        logger.info(" Opening SSL Key Store " + httpProperties.getSslKeyStore() 
                      + " with password: " + httpProperties.getSslKeyPswd());
        keystore.load(new FileInputStream(httpProperties.getSslKeyStore()), httpProperties.getSslKeyPswd().toCharArray());
       
        // A KeyManagerFactory is used to create key managers
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        // Initialize the KeyManagerFactory to work with our keystore
        logger.info(" Accessing KeyManageFactory with password " + httpProperties.getSslKeyPswd());
        kmf.init(keystore, httpProperties.getSslKeyPswd().toCharArray());
        // An SSLContext is an environment for implementing JSSE
        // It is used to create a ServerSocketFactory
        SSLContext sslc = SSLContext.getInstance("SSLv3");
        // Initialize the SSLContext to work with our key managers
        sslc.init(kmf.getKeyManagers(), null, null);
        // Create a ServerSocketFactory from the SSLContext
        ServerSocketFactory ssf = sslc.getServerSocketFactory();
        // Socket to me
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(httpProperties.getServerPort());
        // Authenticate the client?
        logger.info(" Client Authentication is " + httpProperties.getSslClientAuth());
        serverSocket.setNeedClientAuth(httpProperties.getSslClientAuth());
        // Return a ServerSocket on the desired port
        return serverSocket;
    }
    
  public void RunIsolator() {
    
    CoreProperties coreProperties = new CoreProperties(httpProperties.getConfigFileName(),
                                                       logger);
    /*
     * display stub information to log file
     */
    logger.info("Author : " + coreProperties.getAuthor()
                  + " - Name : " + coreProperties.getName()
                  + " - Description : " + coreProperties.getDescription()
                  + " - Date : " + coreProperties.getDate());

  
    /*
     * load the variable configurations
     */
    for (int i = 0; i < coreProperties.getVariables().size(); i++) {
      Variable variable =  (Variable) coreProperties.getVariables().get(i);
    }
    /*
     * load the baseline response message templates
     */
    for (int i = 0; i < coreProperties.getBaselineMessages().size(); i++) {
      BaseLineMessage baseLineMessage =  (BaseLineMessage) coreProperties.getBaselineMessages().get(i);
    }
    /*
     * load the receiver events and the associated messages
     */
    for (int i = 0; i < coreProperties.getReceiverEvents().size(); i++) {
      ReceiverEvent receiverEvent =  (ReceiverEvent) coreProperties.getReceiverEvents().get(i);
      int numberOfMessages = receiverEvent.getMessages().size();
      for (int c = 0; c < numberOfMessages;  c++ ) {
        EventMessage message = (EventMessage) receiverEvent.getMessages().get(c);
      }
    }
    /*
     * setup thread pool
     */
    logger.info("setting up threadpool of size : " + httpProperties.getThreadCount()); 
    ExecutorService executor = Executors.newFixedThreadPool(httpProperties.getThreadCount());
    /*
     * now we've written header detail, set log level to that in the xml
     */
    logger.info("logging set to : " + logFileProperties.getLogLevel().toUpperCase()); 
    if (logFileProperties.getLogLevel().toUpperCase().equals("INFO")) {
      logger.setLevel(Level.INFO);
    } else if (logFileProperties.getLogLevel().toUpperCase().equals("DEBUG")) {
      logger.setLevel(Level.DEBUG);
    } else if (logFileProperties.getLogLevel().toUpperCase().equals("WARN")) {
      logger.setLevel(Level.WARN);
    } else if (logFileProperties.getLogLevel().toUpperCase().equals("ERROR")) {
      logger.setLevel(Level.ERROR);
    } else if (logFileProperties.getLogLevel().toUpperCase().equals("FATAL")) {
      logger.setLevel(Level.FATAL);
    } else if (logFileProperties.getLogLevel().toUpperCase().equals("TRACE")) {
      logger.setLevel(Level.TRACE);
    }
    boolean socketLoop = true;
    boolean connectionLoop = true;
    int connectionLoopCntr = 0;
    while (socketLoop) {
    
      serverSocket = null;
      try {
        /*
         * open the socket
         */
        serverSocket = getSslServerSocket();
        serverSocket.setSoTimeout(5 * 1000);
          
      } catch (Exception e) {
        logger.error("httpsStub: Unable to listen on " + httpProperties.getServerIP() + ":" 
                       + httpProperties.getServerPort()
                       + ". " + e);
        e.printStackTrace();
        // exit on fail to bind port id.
        System.exit(1);
      }
      /*
       * listen for connections...
       */
      while (connectionLoop){
        try {
          /*
           * accept connections a connection on a new socket
           */
          Socket clientConnection = null;
          clientConnection = serverSocket.accept();
          clientConnection.setSoTimeout(5 * 1000);
          /*
           * Handle the connection with a separate thread                     
           */
          if (clientConnection != null) {
            Runnable httpStubWorker = new HttpStubWorker(clientConnection, 
                                                         httpProperties,
                                                         coreProperties,
                                                         logger,
                                                         receiverEventsCntr);
            //Thread thread = new Thread(httpStubWorker);
            //thread.start();
            
            executor.execute(httpStubWorker);

            //clientConnection.close();
          }
     
                    
        } catch (SocketTimeoutException e) {
          /*
          * DO NOTHING - The timeout just allows the checking of the restart
          * request and will only close the socket server if a restart request
          * has been issued
          */           
        } catch (Exception e) {
          logger.error("httpsStub: socket exception. " + e );
          e.printStackTrace();
        } 
            
      }
      /* 
       * shutdown threads
       */
      executor.shutdown();
      while (!executor.isTerminated()) {
      }
                  
    }
    
    
  }
  
  

  
}

