package com.jzkg.xml.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Java类解析
 * 
 * @author LouYue
 *
 */
public class PojoReflexUtils {

	/**
	 * Java反射 - 实验方法
	 * 
	 * @param pojo
	 */
	@SuppressWarnings("rawtypes")
	public static void queryMemberVar(Object pojo) {
		Class base = (Class) pojo;

		System.out.println("--------------------------------");
		// 1.包名 + 文件名
		String className = base.getName();
		System.out.println(className);
		System.out.println("--------------------------------");
		// 2.获得类的所有成员变量
		Field[] allFields = base.getDeclaredFields();
		for (Field field : allFields) {
			System.out.println(field.getName());
		}
		System.out.println("--------------------------------");
		// 3.获得类的所有方法。
		Method[] allMethods = base.getDeclaredMethods();
		for (Method method : allMethods) {
			System.out.println(method.getName());
		}
		System.out.println("--------------------------------");
		// 4.
		Constructor[] allConstructor = base.getConstructors();
		for (Constructor constructor : allConstructor) {
			System.out.println(constructor);
		}
	}

	/**
	 * Class - 获取类中素有的成员变量
	 * 
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] gainFields(Class pojo) {
		Field[] allFields = pojo.getDeclaredFields();

		return allFields;
	}

	/**
	 * Class - 获取类的自定义方法
	 * 
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Method[] gainMethod(Class pojo) {
		Method[] allMethods = pojo.getDeclaredMethods();

		return allMethods;
	}

	/**
	 * Class - 获取类名
	 * 
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String gainClassName(Class pojo) {
		String className = pojo.getSimpleName();

		return className;
	}

	/**
	 * Class - 获取包名
	 * 
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String gainClassPackage(Class pojo) {
		String packageName = pojo.getPackage().getName();

		return packageName;
	}
}
