package me.splm.weinject.annotation.event;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.splm.weinject.annotation.MethodTypes;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
   
* @ClassName: TouchEventListener   

* @Description 注解触摸事件

* @author splm  

* @contact cd@zhaot.com 

* @date 2014-10-19 下午5:26:32      

* @version v1.0

* @copyright [吉林找它信息有限公司]
 */
public class TouchEventListener extends AbsEventListener implements OnTouchListener {
	
	private Object object;
	
	private String touchMethod;
	
	public TouchEventListener(Object object){
		
		this.object=object;
		
	}
	
	public TouchEventListener touch(String method_name){
		
		this.touchMethod=method_name;
		
		return this;
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return invokeTouchMethod(object, touchMethod, v,event);
	
	}
	
	private static boolean invokeTouchMethod(Object arg_obj,String arg_method_name,Object...parms){
		
		if(arg_obj==null){
			
			return false;
			
		}
		
		try {
			
			//public boolean onTouch(View v, MotionEvent event)
			Method method=arg_obj.getClass().getDeclaredMethod(arg_method_name, View.class,MotionEvent.class);
			
			if(method!=null){
				
				Object obj=method.invoke(arg_obj, parms);
				
				return obj==null?false:Boolean.valueOf(obj.toString());
				
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

	@Override
	public void registListener(Object activity, Field field,String method_name, MethodTypes method) {
		
		if(method_name==null||method_name.trim().length()==0){
			
			return;
			
		}
		
		Object obj;
		try {
			
			obj = field.get(activity);
			
			switch(method){
				
			case Touch:
				
				if(obj instanceof View){
							
					((View)obj).setOnTouchListener(new TouchEventListener(activity).touch(method_name));
					
				}
				
				break;
			
			default:
			
				break;
			
			}
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
