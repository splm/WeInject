package me.splm.weinject.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.splm.weinject.annotation.event.ClickEventListener;
import me.splm.weinject.annotation.event.KeyEventListener;
import me.splm.weinject.annotation.event.TouchEventListener;
import android.app.Activity;
import android.content.Context;
import android.view.View;

public final class InitViewject {

    private Context context;

    private static InitViewject instance;

    public static InitViewject getInstance() {

        if (instance == null) {

            return new InitViewject();

        }

        return instance;

    }

    public void init(Context context) {

        this.context = context;
        
        initContentViewInject(context);

        initViewInject(context, ((Activity) context).getWindow().getDecorView());

    }
    
    private void initContentViewInject(Object arg_obj){
        
        if(arg_obj==null){
            
            return;
            
        }
        
        Class<?> handler_type=arg_obj.getClass();
        
        ContentViewInject contentViewInject=handler_type.getAnnotation(ContentViewInject.class);
        
        if(contentViewInject!=null){
            
            try {
                
                Method method=handler_type.getMethod("setContentView", int.class);
                
                if(method!=null){
                    
                    method.invoke(arg_obj, contentViewInject.value());
                    
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
            
        }
        
    }

    private void initViewInject(Object obj, View sourceView) {

        Field[] fields = obj.getClass().getDeclaredFields();

        if (fields != null && fields.length > 0) {

            for (Field field : fields) {

                field.setAccessible(true);// 可以访问私有变量

                try {

                    if (field.get(obj) != null) {

                        continue;

                    }

                    ViewInject viewInject = field.getAnnotation(ViewInject.class);

                    if (viewInject != null) {

                        int view_id = viewInject.id();

                        if (view_id == 0) {// 如果view_id等于0，也就是说注解失败或没有写入注解时

                            // 根据资源属性为view_id设置id
                            view_id = context.getResources().getIdentifier(field.getName(), "id",context.getPackageName());

                        }

                        field.set(obj, sourceView.findViewById(view_id));

                        new ClickEventListener(obj).registListener(obj, field, viewInject.click(),MethodTypes.Click);

                        new ClickEventListener(obj).registListener(obj, field,viewInject.longClick(), MethodTypes.LongClick);

                        new ClickEventListener(obj).registListener(obj, field,viewInject.itemClick(), MethodTypes.ItemClick);

                        new ClickEventListener(obj).registListener(obj, field,viewInject.itemLongClick(), MethodTypes.ItemLongClick);

                        new TouchEventListener(obj).registListener(obj, field, viewInject.touch(),MethodTypes.Touch);

                        new KeyEventListener(obj).registListener(obj, field,
                                viewInject.onKeyDown(), MethodTypes.OnKeyDown);

                    }

                } catch (IllegalAccessException e) {

                    e.printStackTrace();

                } catch (IllegalArgumentException e) {

                    e.printStackTrace();

                }

            }

        }

    }

}
