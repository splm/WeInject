package me.splm.weinject.annotation.event;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.splm.weinject.annotation.MethodTypes;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * 
 * @ClassName: EventListener   
 * @Description 注解事件类
 * @author splm   
 * @date 2014-9-22 下午1:22:45
 * 
 * 扩展说明：
 * 1.声明String类型的方法名变量；
 * 2.创建返回值为EventListener的方法，方法名称最好为功能名称，
 * 3.继承对应的事件监听器，在对应实现方法中使用invoke执行方法即可；
 * 
 */
public class ClickEventListener extends AbsEventListener implements OnClickListener,OnLongClickListener,OnItemClickListener,OnItemLongClickListener {
	
	private static final String TAG="EventListener";
	
	private Object obj;
	
	private String clickMethod;
	
	private String longClickMethod;
	
	private String itemLongClickMethod;
	
	private String itemClickMethod;
	
	public ClickEventListener(Object object){
		
		this.obj=object;
		
	}
	
	public ClickEventListener click(String method_name){
		
		this.clickMethod=method_name;
		
		return this;
		
	}
	
	public ClickEventListener longClick(String method_name){
		
		this.longClickMethod=method_name;
		
		return this;
		
	}
	
	public ClickEventListener itemLongClick(String method_name){
		
		this.itemLongClickMethod=method_name;
		
		return this;
		
	}
	
	public ClickEventListener itemClick(String method_name){
		
		this.itemClickMethod=method_name;
		
		return this;
		
	}
	
	@Override
	public boolean onLongClick(View v) {
		
		return invokeLongClickMethod(obj, longClickMethod, v);
	
	}

	@Override
	public void onClick(View v) {
		
		invokeClickMethod(obj, clickMethod, v);
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
		
		return invokeItemLongClickMethod(obj, itemLongClickMethod, parent,view,position,id);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		
		invokeItemClick(obj, itemClickMethod, parent,view,position,id);
		
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
			
			case Click:
				
				if(obj instanceof View){
					
					((View)obj).setOnClickListener(new ClickEventListener(activity).click(method_name));
					
				}
				
				break;
				
			case LongClick:
				
				if(obj instanceof View){
					
					((View)obj).setOnLongClickListener(new ClickEventListener(activity).longClick(method_name));
					
				}
				
			case ItemClick:
				
				if(obj.getClass().getSuperclass().equals(AbsListView.class)){
					
					((AbsListView)obj).setOnItemClickListener(new ClickEventListener(activity).itemClick(method_name));
					
				}
				
				break;
				
			case ItemLongClick:
				
				if(obj.getClass().getSuperclass().equals(AbsListView.class)){
					
					((AbsListView)obj).setOnItemLongClickListener(new ClickEventListener(activity).itemLongClick(method_name));
					
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
	
	/**
	 * 
	* @Title: invokeClickMethod 
	  
	* @Description 注册单击事件
	
	* @caution  无
	  
	* @param object 当前的Activity
	* @param method_name 在Activity中创建的方法
	* @param parms 
	  
	* @return Object 
	  
	* @throws 
	
	* @author splm
	
	* @contact cd@zhaot.com
	
	* @version v1.0
	
	* @date 2014-10-19-下午2:53:42
	
	* @copyright [吉林找它信息有限公司]
	 */
	private static Object invokeClickMethod(Object object,String method_name,Object...parms){
		
		if(object==null){
			
			return null;
			
		}
		
		try {
			
			Method method=object.getClass().getMethod(method_name, View.class);
			
			if(method!=null){
				
				method.invoke(object, parms);
				
			}
			
			else{
				
				Log.e(TAG, "EventListener中的单击事件为空");
				
			}
		
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		
		}
		
		return null;
		
	} 
	
	private static boolean invokeLongClickMethod(Object object,String method_name,Object...parms){
		
		if(object==null){
			
			return false;
			
		}
		
		try {
			
			//public boolean onLongClick(View v)
			Method method=object.getClass().getDeclaredMethod(method_name, View.class);
			
			if(method!=null){
				
				Object obj=method.invoke(object, parms);
				
				return obj==null?false:Boolean.valueOf(obj.toString());
				
			}
		
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		
		}
		
		return false;
		
	}
	
	/**
	 * 
	* @Title: invokeItemLongClickMethod 
	  
	* @Description 执行listview元素长按事件
	
	* @caution 无
	  
	* @param arg_object
	* @param arg_method_name
	* @param arg_parms
	  
	* @return boolean 与listview onItemLongClick方法返回值相同
	  
	* @throws 
	
	* @author splm
	
	* @contact cd@zhaot.com
	
	* @version v1.0
	
	* @date 2014-10-19-下午5:13:55
	
	* @copyright [吉林找它信息有限公司]
	 */
	private static boolean invokeItemLongClickMethod(Object arg_object,String arg_method_name,Object...arg_parms){
		
		if(arg_object==null){
			
			return false;
			
		}
		//public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id)
		try {
			
			Method method=arg_object.getClass().getDeclaredMethod(arg_method_name, AdapterView.class,View.class,int.class,long.class);
			
			if(method!=null){
				
				Object object=method.invoke(arg_object, arg_parms);
				
				return object==null?false:Boolean.valueOf(object.toString());
				
			}
			else{
				
				Log.e(TAG, "EventListener中的列表元素长按事件为空");
				
			}
		
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		
		}
		
		return false;
		
	}
	
	/**
	 * 
	* @Title: invokeItemClick 
	  
	* @Description 
	
	* @caution 无
	  
	* @param @param arg_object
	* @param @param arg_method_name
	* @param @param arg_parms
	* @param @return    设定参数 
	  
	* @return Object    返回类型 
	  
	* @throws 
	
	* @author splm
	
	* @contact cd@zhaot.com
	
	* @version v1.0
	
	* @date 2014-10-19-下午5:19:37
	
	* @copyright [吉林找它信息有限公司]
	 */
	private static Object invokeItemClick(Object arg_object,String arg_method_name,Object...arg_parms){
		
		if(arg_object==null){
			
			return null;
			
		}
		//public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		try {
			
			Method method=arg_object.getClass().getDeclaredMethod(arg_method_name, AdapterView.class,View.class,int.class,long.class);
			
			if(method!=null){
				
				method.invoke(arg_object, arg_parms);
				
			}
			else{
				
				Log.e(TAG, "EventListener中的列表元素单击事件为空");
				
			}
		
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		
		}
		
		return null;
		
	}

}
