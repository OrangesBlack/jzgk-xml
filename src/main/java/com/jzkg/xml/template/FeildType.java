package com.jzkg.xml.template;

/**
 * enum - 参数帅选枚举
 * 
 * @author LouYue
 *
 */
public enum FeildType {

	/**
	 * 参数类型枚举
	 */
	INT("Integer", 1), CHAR("Char", 2), SHORT("short", 3), LONG("Long", 4), BOOLEAN("Boolean", 5), DOUBLE("double", 6),
	FLOAT("float", 7), BTYE("byte", 8), STRING("String", 9), INTEGER("Integer", 10), DATE("Date", 11), VOID("void", 12);

	private String name;
	private int index;

	private FeildType(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 筛选类变量类型
	 * 
	 * @param typeName
	 * @return
	 */
	public static String chooseTypeName(String typeName) {
		switch (typeName) {
		case "java.lang.Integer":
			return INT.getName();
		case "java.lang.Character":
			return CHAR.getName();
		case "java.lang.Short":
			return SHORT.getName();
		case "java.lang.Long":
			return LONG.getName();
		case "java.lang.Boolean":
			return BOOLEAN.getName();
		case "java.lang.Double":
			return DOUBLE.getName();
		case "java.lang.Float":
			return FLOAT.getName();
		case "java.lang.Byte":
			return BTYE.getName();
		case "java.lang.String":
			return STRING.getName();
		case "java.util.Date":
			return DATE.getName();
		default:
			return VOID.getName();
		}
	}
}
