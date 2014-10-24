package me.splm.weinject.annotation.event;

import java.lang.reflect.Field;

import me.splm.weinject.annotation.MethodTypes;

public abstract class AbsEventListener {
	
	public abstract void registListener(Object activity,Field field,String method_name,MethodTypes method);

}
