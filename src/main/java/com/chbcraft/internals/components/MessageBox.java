package com.chbcraft.internals.components;

import java.util.Calendar;

public class MessageBox {
    private volatile static MessageBox logger = null;
    private boolean isOpen = true;
    private final String messageHeader;
    private final Calendar dateMechain;
    private MessageBox(String messageHeader){
        if(messageHeader.equals(""))
            messageHeader = "[Console";
        this.messageHeader = "["+messageHeader;
        dateMechain = Calendar.getInstance();
    }
    public void log(String message){
        System.out.println(split(message));
    }
    public void error(String message){
        System.err.println(split(message));
    }
    public void warn(String reason){
        reason = "Possible error causes:"+"\nCaused by: "+reason;
        System.err.println(split(reason));
    }
    private String split(String message){
        return (messageHeader+" "+ dateMechain.getTime() +"]").replace(" ","-")+message;
    }
    public void broadcastPlugin(String name){
        if(isOpen)
            log("[PluginLoading]["+name+"] has initializing!");
    }
    public void broadcastPlugin(String name,long start){
        if (isOpen)
            log("[PluginLoading]["+name+"] has initialized!"+" Spend "+(System.currentTimeMillis()-start)+"ms");
    }
    public static MessageBox getLogger(){
        synchronized (MessageBox.class){
            if(logger==null){
                if (logger==null){
                    logger = new MessageBox("CONSOLE");
                    logger.isOpen = true;
                }
            }
        }
        return logger;
    }
}
