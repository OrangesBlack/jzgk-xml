package com.jzkg.xml.template;

import org.dom4j.tree.DefaultElement;

/**
 * POJO文件生成器
 * 
 * @author LouYue
 *
 */
public class CodeTemplate {

	/**
	 * CLASS-写入文件
	 * 
	 * @param defaultElement
	 * @return
	 */
	public static String createPojoName(DefaultElement defaultElement) {
		String classPathName = "src/main/java/" + defaultElement.attributeValue("package");
		String formatName = classPathName.replace(".", "/") + "/" + defaultElement.attributeValue("name") + ".java";
		return formatName;
	}

	/**
	 * CLASS-引入包
	 * 
	 * @param packageName
	 * @return
	 */
	public static String createPackage(String packageName) {
		String packageStr = "package " + packageName + ";";
		return packageStr;
	}

	/**
	 * CLASS-生成导入包
	 * 
	 * @return
	 */
	public static String[] createImport() {
		String[] lombokImports = new String[5];
		lombokImports[0] = "import lombok.AllArgsConstructor;";
		lombokImports[1] = "import lombok.Data;";
		lombokImports[2] = "import lombok.EqualsAndHashCode;";
		lombokImports[3] = "import lombok.NoArgsConstructor;";
		lombokImports[4] = "import lombok.ToString;";
		return lombokImports;
	}

	/**
	 * CLASS-生成注解
	 * 
	 * @return
	 */
	public static String[] createAnnotation() {
		String[] lombokAnnotations = new String[5];
		lombokAnnotations[0] = "@Data";
		lombokAnnotations[1] = "@NoArgsConstructor";
		lombokAnnotations[2] = "@AllArgsConstructor";
		lombokAnnotations[3] = "@ToString";
		lombokAnnotations[4] = "@EqualsAndHashCode";
		return lombokAnnotations;
	}

	/**
	 * CLASS-生成类名
	 * 
	 * @param className
	 * @return
	 */
	public static String createmain(String className) {
		StringBuilder str = new StringBuilder();

		str.append("public").append(" class ").append(className).append("{");
		return str.toString();
	}

	/**
	 * CLASS-生成变量
	 * 
	 * @param feildName
	 * @param typeName
	 * @return
	 */
	public static String createFeilds(String feildName, String typeName) {
		StringBuilder str = new StringBuilder();
		String type = FeildType.chooseTypeName(typeName);

		str.append("private ").append(type).append(" ").append(feildName).append(";");
		return str.toString();
	}

	/**
	 * CLASS-生成自定义方法
	 * 
	 * @return
	 */
	public static String createCustomMethods(String methodName, String typeName, boolean flag) {
		StringBuilder str = new StringBuilder();
		String type = FeildType.chooseTypeName(typeName);

		str.append("public ");
		if (!flag) {
			str.append("static ");
		}
		str.append(type).append(" ").append(methodName).append(" () ").append("{").append("return null;").append("}");
		return str.toString();
	}

	/**
	 * CLASS-生成底部
	 * 
	 * @return
	 */
	public static String createFooder() {
		return "}";
	}
}
