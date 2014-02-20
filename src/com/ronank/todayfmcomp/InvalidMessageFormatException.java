package com.ronank.todayfmcomp;

/**
 * InvalidMessageFormatException class
 * 
 * @author rkelly
 *
 */
public class InvalidMessageFormatException extends Exception
{
    private static final long serialVersionUID = 2803476767375486750L;
    
    public InvalidMessageFormatException() 
    {
        
    }

    public InvalidMessageFormatException(String message)
    {
       super(message);
    }
}
