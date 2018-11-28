package com.jzkg.xml.util;

import java.io.IOException;

import org.dom4j.DocumentException;

import com.jzkg.xml.pojo.TestPojo;


/**
 * 测试
 * 
 * @author LouYue
 *
 */
public class Test {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException, DocumentException {
		Dom4jUtils dom4jUtils = new Dom4jUtils();

		// 1.自动化生成实体类
		System.out.println(dom4jUtils.createClassPojo("StorageXML/code.xml"));

		// 2.反向生成XML
		Class[] classes = new Class[] { TestPojo.class };
		System.out.println(dom4jUtils.createDocment("StorageXML/test.xml", classes));
	}
}
