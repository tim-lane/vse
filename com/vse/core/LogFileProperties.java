package com.vse.core;

/**
class: LogFileProperties
Purpose: holds log file properties for HTTP requests.
Notes:
Author: Tim Lane
Date: 24/03/2014

**/

import java.io.*;
import org.w3c.dom.Element;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogFileProperties {
  
  private String logFileName;
  private String logLevel;
  public static final String LOGFILENAME_TAG = "LogFileName";
  public static final String HEADER_TAG = "Header";
  
  public LogFileProperties(Element logFilePropertiesElement) {
    setLogFileName(logFilePropertiesElement.getAttribute("LogFileName"));
    setLogLevel(logFilePropertiesElement.getAttribute("LogLevel"));
  }
  
      public void setLogFileName(String logFileName) {
      this.logFileName = logFileName;
    }
        
    public String getLogFileName(){
      return this.logFileName;
    }
    
    public void setLogLevel(String logLevel) {
      this.logLevel = logLevel;
    }
        
    public String getLogLevel(){
      return this.logLevel;
    }
}
