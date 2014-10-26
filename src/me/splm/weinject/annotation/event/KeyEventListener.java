package me.splm.weinject.annotation.event;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.splm.weinject.annotation.MethodTypes;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class KeyEventListener extends AbsEventListener implements OnKeyListener {

    private Object obj;

    private String method_name;

    public KeyEventListener(Object object) {

        this.obj = object;

    }

    public KeyEventListener onKeyDown(String method_name) {

        this.method_name = method_name;

        return this;

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        
        return invokeKeyDownMethod(obj, method_name, keyCode,event);
    
    }

    @Override
    public void registListener(Object activity, Field field, String method_name, MethodTypes method) {
        
        if(method_name==null||method_name.trim().length()==0){
            
            return;
            
           }
           
           Object obj;
           try {
            
            obj = field.get(activity);
            
            switch(method){
             
            case OnKeyDown:
             
             if(obj instanceof View){
                
              ((View)obj).setOnKeyListener(new KeyEventListener(activity).onKeyDown(method_name));
              
             }
             
             break;
            
            }
           } catch (IllegalAccessException e) {
            
            e.printStackTrace();
           
           } catch (IllegalArgumentException e) {
            
            e.printStackTrace();
            
           }

    }
    
    public static boolean invokeKeyDownMethod(Object arg_obj,String arg_method,Object...arg_parms){
        
        if(arg_obj==null){
            
            return false;
            
        }
        
        try {
            
            Method method=arg_obj.getClass().getDeclaredMethod(arg_method, View.class,int.class,KeyEvent.class);
            
            if(method!=null){
                
                Object object=method.invoke(arg_obj, arg_parms);
                
                return object==null?false:Boolean.valueOf(object.toString());
                
            }
        
        } catch (NoSuchMethodException e) {
            
            e.printStackTrace();
        
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        
        return false;
        
    }

}
