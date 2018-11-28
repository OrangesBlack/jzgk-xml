package com.jzkg.xml.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import com.jzkg.xml.template.CodeTemplate;

/**
 * Dom4j XML工具类
 * 
 * @author LouYue
 *
 */
public class Dom4jUtils {

	private final String compact = "compact";
	private final String pretty = "pretty";

	private String[] formatFeilds;
	private String[] methodsFeilds;
	private int count = 0;

	/*-------------------------------------------- 主要功能 ---------------------------------------------*/
	/**
	 * Pojo类生成XML文件
	 * 
	 * @param pathName
	 * @param pojos
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Document createDocment(String pathName, Class... pojos) {
		Document document = DocumentHelper.createDocument();
		// 1.插入 - 根节点
		Element root = document.addElement("code");
		// 2.插入 - 字节点Bean
		Class[] classes = pojos;
		for (Class pojo : classes) {
			String pojoName = PojoReflexUtils.gainClassName(pojo);
			String packageName = PojoReflexUtils.gainClassPackage(pojo);
			Field[] fields = PojoReflexUtils.gainFields(pojo);
			Method[] methods = PojoReflexUtils.gainMethod(pojo);

			// 3.插入 - code下的子节点
			Element bean = createElementBean("bean", root, pojoName, packageName);
			// 4.插入 - Bean下的子节点
			creaeteElementFields("feilds", bean, fields);
			// 5.插入 - Bean下的子节点
			createElementMethods("methods", bean, methods);
		}
		// 6.文件写入
		try {
			writeDomXml(document, pathName, pojos);
		} catch (IOException e) {
			e.printStackTrace();
			document = null;
		}
		return document;
	}

	/**
	 * XML结构生成对应实体类
	 * 
	 * @return
	 * 
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public boolean createClassPojo(String pathName) throws DocumentException {
		SAXReader sax = new SAXReader();
		try {
			Document document = sax.read(pathName);
			List<DefaultElement> beans = document.selectNodes("//code/bean");
			for (DefaultElement one : beans) {
				// 1. 获取文件名，包名
				String pojoName = one.attributeValue("name");
				String packageName = one.attributeValue("package");
				// 2.建立写入类结构
				String formatPojoName = CodeTemplate.createPojoName(one);
				String formatPackageName = CodeTemplate.createPackage(packageName);
				String formatMain = CodeTemplate.createmain(pojoName);
				
				setWriteFeilds(one);
				setWriteMethods(one);
				// 3.开始写入生成实体类
				boolean sucFlag = createJavaPojo(formatPojoName, formatPackageName, formatMain, formatFeilds,methodsFeilds);
				if (!sucFlag) {
					return false;
				}
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*-------------------------------------------- 可使用功能 ---------------------------------------------*/

	/**
	 * XML - 写入实验测试
	 * 
	 * @param document
	 * @throws IOException
	 */
	public void write(Document document) throws IOException {

		// lets write to a file
		FileWriter fileWiter = new FileWriter("output.xml");
		XMLWriter writer = new XMLWriter(fileWiter);
		writer.write(document);
		writer.close();

		// Compact format to System.out
		OutputFormat format = sortsPrint(compact);
		writer = new XMLWriter(System.out, format);
		writer.write(document);
		writer.close();
	}

	/**
	 * Dom -转换- 成字符串
	 * 
	 * @param document
	 * @return
	 */
	public String formatSrtTest(Document document) {
		return document.asXML();
	}

	/**
	 * 字符串 -转换- Dom
	 * 
	 * @param domStr
	 * @return
	 * @throws DocumentException
	 */
	public Document convertDom(String domStr) throws DocumentException {
		return DocumentHelper.parseText(domStr);
	}

	/**
	 * XML文档打印规格
	 * 
	 * @param format    输出策略
	 * @param printName 打印方式 ( pretty,compact)
	 * @return
	 */
	public OutputFormat sortsPrint(String printName) {
		OutputFormat format = new OutputFormat();
		if (pretty.equals(printName.toLowerCase())) {
			format = OutputFormat.createPrettyPrint();
		} else if (compact.equals(printName.toLowerCase())) {
			format = OutputFormat.createCompactFormat();
		}
		return format;
	}

	/*-------------------------------------------- 封装方法 ---------------------------------------------*/
	/**
	 * DOM - 创建子节点
	 * 
	 * @param elemName
	 * @param element
	 * @param param
	 * @return
	 */
	private Element createElementBean(String elemName, Element element, String... param) {
		Element bean = element.addElement(elemName).addAttribute("name", param[0]).addAttribute("package", param[1]);
		return bean;
	}

	/**
	 * DOM - 创建子节点
	 * 
	 * @param elemName
	 * @param element
	 * @param fields
	 * @return
	 */
	private Element creaeteElementFields(String elemName, Element element, Field[] fields) {
		Element fieldElem = element.addElement(elemName);
		for (Field field : fields) {
			fieldElem.addElement("field").addAttribute("name", field.getName()).addAttribute("type",
					field.getType().getName());
		}
		return fieldElem;
	}

	/**
	 * DOM - 创建子节点
	 * 
	 * @param elemName
	 * @param element
	 * @param methods
	 * @return
	 */
	private Element createElementMethods(String elemName, Element element, Method[] methods) {
		Element methodElem = element.addElement(elemName);
		for (Method method : methods) {
			methodElem.addElement("method").addAttribute("name", method.getName()).addAttribute("type",
					method.getAnnotatedReturnType().getType().getTypeName());
		}
		return methodElem;
	}

	/**
	 * XML -写入
	 * 
	 * @param pathName
	 * @throws IOException
	 */
	private void writeDomXml(Document document, String pathName, Object[] pojos) throws IOException {
		// write xml
		OutputFormat format = sortsPrint("pretty");
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new FileOutputStream(new File(pathName)), format);
		writer.write(document);

		// Compact | pretty format to System.out
		writer = new XMLWriter(System.err, format);
		writer.write(document);
		writer.close();
	}

	/**
	 * 生成写入实体类的参数
	 * 
	 * @param defaultElement
	 */
	@SuppressWarnings("unchecked")
	private void setWriteFeilds(DefaultElement defaultElement) {
		List<DefaultElement> feilds = defaultElement.selectNodes("//feilds/field");
		formatFeilds = new String[feilds.size()];
		for (DefaultElement two : feilds) {
			String feildName = two.attributeValue("name");
			String typeName = two.attributeValue("type");
			String feild = CodeTemplate.createFeilds(feildName, typeName);
			formatFeilds[count] = feild;
			count++;

		}
		count = 0;
	}

	/**
	 * 生成写入实体类的自定义方法
	 * 
	 * @param defaultElement
	 */
	@SuppressWarnings("unchecked")
	private void setWriteMethods(DefaultElement defaultElement) {
		List<DefaultElement> methods = defaultElement.selectNodes("//methods/method");
		methodsFeilds = new String[methods.size()];
		for (DefaultElement three : methods) {
			String methodName = three.attributeValue("name");
			String typeName = three.attributeValue("type");
			Boolean staticFlag = Boolean.valueOf(three.attributeValue("default"));

			String feild = CodeTemplate.createCustomMethods(methodName, typeName, staticFlag);
			methodsFeilds[count] = feild;
			count++;
		}
		count = 0;
	}

	/**
	 * Java类创建
	 * 
	 * @param classPathName
	 * @param packageName
	 * @param mainName
	 * @param feilds
	 * @param customMethods
	 * @throws IOException
	 */
	private boolean createJavaPojo(String classPathName, String packageName, String mainName, String[] feilds,
			String[] customMethods) throws IOException {
		File file = new File(classPathName);
		OutputStream out = null;

		String[] importName = null;
		String[] annotationName = null;
		String fooder = null;

		try {
			importName = CodeTemplate.createImport();
			annotationName = CodeTemplate.createAnnotation();
			fooder = CodeTemplate.createFooder();

			out = new FileOutputStream(file);

			// 写入引包
			out.write(packageName.getBytes());
			out.write("\r\n".getBytes());

			// 写入导包
			for (int i = 0; i < importName.length; i++) {
				out.write(importName[i].getBytes());
				out.write("\r\n".getBytes());
			}

			// 写入注解
			for (int i = 0; i < annotationName.length; i++) {
				out.write(annotationName[i].getBytes());
				out.write("\r\n".getBytes());
			}

			// 写入类头
			out.write(mainName.getBytes());
			out.write("\r\n\r\n".getBytes());

			// 写入参数
			for (int i = 0; i < feilds.length; i++) {
				out.write(feilds[i].getBytes());
				out.write("\r\n".getBytes());
			}

			// 写入方法
			for (int i = 0; i < customMethods.length; i++) {
				out.write(customMethods[i].getBytes());
				out.write("\r\n".getBytes());
			}

			out.write(fooder.getBytes());
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭输出流
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
