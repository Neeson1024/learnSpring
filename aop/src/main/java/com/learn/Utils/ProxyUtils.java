package com.learn.Utils;

import com.learn.handler.ConstomInvocationHandler;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ProxyUtils {

    public static Object newInstance(Class interfaceClass,ConstomInvocationHandler h) {
        Object resultObj = null;

        String line = "\n";
        String tab = "\t";

        String packageContent = "package com.google;" + line;
        String importContent = "import " + interfaceClass.getName() + ";" + line;
        importContent += "import java.lang.reflect.Method;"+ line;
        importContent += "import com.learn.handler.ConstomInvocationHandler;"+ line;
        importContent += "import java.lang.Exception;"+ line;

        String classFirstContent = "public class $Proxy implements " + interfaceClass.getSimpleName() + "{" + line;
        String filedContent = tab + "public " + h.getClass().getInterfaces()[0].getSimpleName() + " h;" + line;
        String constructorContent = tab + "public $Proxy(" + h.getClass().getInterfaces()[0].getSimpleName() + " h){" + line
                                    +tab + tab + "this.h = h;" + line
                                    +"}" + line;

        String methodContent = "";
        Method[] methods = interfaceClass.getMethods();
        for(Method method : methods){
            String returnSimpleName = method.getReturnType().getSimpleName();
            String name = method.getName();

            //方法参数名
            Class<?>[] parameterTypes = method.getParameterTypes();
            int tmp = 0;
            String argsContent = "";
            String methodArgsContent = "";
            for (Class<?> parameterType : parameterTypes) {
                //String
                //String p0,Sting p1,
                argsContent += parameterType.getSimpleName() + " i" + tmp + ",";
                methodArgsContent += "i" + tmp + ",";
                tmp++;
            }

            //截取逗号
            if(argsContent.length() > 0){
                argsContent = argsContent.substring(0,argsContent.lastIndexOf(",") - 1);
                methodArgsContent = methodArgsContent.substring(0,methodArgsContent.lastIndexOf(",") - 1);
            }


            String resultTypeName = method.getReturnType().getSimpleName();
            methodContent += tab + "public " + returnSimpleName + " " + name + "(" + argsContent + ")throws Exception{" + line
                            +tab + tab + "Method method = Class.forName(\""+interfaceClass.getName()+"\").getMethod(\""+method.getName()+"\");"+line;
            if(!resultTypeName.equalsIgnoreCase("void")) {
                methodContent += tab + tab + "return (" + method.getReturnType().getSimpleName() + ")h.invoke(method,new Object[]{"+methodArgsContent+"})" + ";" + line
                        + tab + "}" + line;
            }else{
                methodContent += tab + tab + "h.invoke(method,new Object[]{"+methodArgsContent+"})" + ";" + line
                        + tab + "}" + line;
            }

        }

        String classContent = packageContent + importContent + classFirstContent + filedContent + constructorContent + methodContent + "}";

        try {
            File file = new File("d:\\com\\google\\$Proxy.java");
            if (file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(classContent);
            fileWriter.flush();
            fileWriter.close();


            //引用第三方包进行编译
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);

            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();


            URL[] urls = new URL[]{new URL("file:D:\\\\")};
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<?> clazz = classLoader.loadClass("com.google.$Proxy");

            Constructor<?> constructor = clazz.getConstructor(ConstomInvocationHandler.class);
            resultObj = constructor.newInstance(h);



        }catch (Exception e){
            e.printStackTrace();
        }


        return resultObj;
    }
}
