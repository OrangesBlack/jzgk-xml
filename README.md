
## 结构：
1. com.xfqb.base.xml  
> CodeTemplate：写入模版  
   Dom4jUtils：主要功能实现  
   FeildType：定义的变量返回类型筛选  
   PojoReflexUtils：获取类信息（变量，方法等） 
   Test：main测试方法
  
2. com.xfqb.base.pojo  
>生成与解析测试包  
3. com.xfqb.base  
>SpringBoot启动类
	

## Code.xml
功能：
类似于mybatis generator插件，通过code.xml指定的标签自动生成基于lombok注解的POJO实体类。
方便于开发者不需要一步一步手动去设计实体类，只需要通过改变标签的值，就可以自动生成。
	
准备工作：
1. 需要自己创建文件夹 + XML文件。
2. 自己创建的xml文件结构必须和code.xml模版结构相同。
3. 指定xml上的bean包，需要在项目中自己添加。
	
步骤：
1. 导入类 Dom4jUtils
2. 类调用 createClassPojo方法，参数为xml路径 + 名称，生成成功返回true，失败返回false。

注意事项：
1. 生成的实体类基于lombok，需要引入jar包
2. 生成的实体类需要自行格式化排版。
	
## 多类集成生成XML
功能：
多个实体类组合成Class数组转换成xml文件
	
步骤：
1. 导入类 Dom4jUtils
2. 类调用 createDocment方法，参数所要生成到的路径 + xml名称，所转换的类数组，生成成功返回XML配置信息，错误返回错误信息。
	
注意事项：
1. 转换类需要自己定义
2. 所生成到的文件夹需要自己定义
	
	
	
