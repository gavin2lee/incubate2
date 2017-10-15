package com.gl.extrade.common.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class ProxyFactory {
	protected ProxyFactory(){}
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<T> cls,final Object invoker){
		return (T)Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class<?>[]{cls}, new InvocationHandler(){

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("proxy:"+proxy.getClass());
				Class<?> cls = proxy.getClass();
				Field [] fs = cls.getDeclaredFields();
				for(Field f : fs){
					System.out.println("field:"+f.getName());
				}
				
				Method [] ms = cls.getDeclaredMethods();
				for(Method m : ms){
					System.out.println("method:"+ m.getName());
				}
				Object result = method.invoke(invoker, args);
				return result;
			}
			
		});
	}
}
