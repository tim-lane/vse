package com.vse.tcp;

import com.vse.core.*;
/**
class: TcpInputStream
Purpose: reads message from an input stream.
Notes:
Author: Tim Lane
Date: 24/03/2014

**/
import java.io.FilterInputStream;
import java.io.InputStream;

public class TcpInputStream extends FilterInputStream{
  
    public TcpInputStream(InputStream in) {
      super(in);
    }
      
    /*
     * get a whole line by passing the number of characters on a line
     */
    public String readLine(int lineLength) throws Exception {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < lineLength; i++)
        {
            int ch = read();
            result.append((char) ch);
        }
        return result.toString();
    }

    /*
     * Get a line by reading all characters
     */
    public String readLine() throws Exception
    {
        StringBuffer result=new StringBuffer();
        boolean finished = false;
        boolean cr = false;
        do
        {
            int ch = -1;
            ch = read();
//            if(ch==-1)
//                return result.toString();

            result.append((char) ch);
            if(ch==10){ // assumes linefeed for the moment, should make this an property
                result.setLength(result.length()-2);
                finished = true;
            }

        } while (!finished);
        return result.toString();
    }
    
  

  
}
