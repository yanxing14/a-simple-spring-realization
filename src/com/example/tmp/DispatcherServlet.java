package com.example.tmp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DispatcherServlet {
    // 3个List，代表url路径、方法、Controller对象，具有相同的长度
    // 同一个下标对应的3个元素，形成 路径-方法-对象 映射关系
    private List<String> pathList = new ArrayList<>();
    private List<Method> methodList = new ArrayList<>();
    private List<Object> controllerList = new ArrayList<>();

    private static List<String> getAllClassNameFromDirectory(String directoryPath, String classNamePrefix) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(directoryPath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getAllClassNameFromDirectory(childFile.getPath(), classNamePrefix + "." + childFile.getName()));
            } else {
                String childFileName = childFile.getName();
                childFileName = childFileName.substring(0, childFileName.lastIndexOf("."));
                myClassName.add(classNamePrefix + "." + childFileName);
            }
        }
        return myClassName;
    }

    public void initialize(String scanPackage) {
        // 1. 扫描，获取controller文件夹下所有类的全名
        String scanPackagePath = getClass().getClassLoader().getSystemResource("").getPath() + scanPackage.replace(".", "\\");
        List<String> allClassNames = getAllClassNameFromDirectory(scanPackagePath, scanPackage);

        // 2. 处理每个类，构建 路径-方法-对象 映射关系
        for (String className : allClassNames) {
            RequestMappingInfo rmInfo;
            try {
                rmInfo = RequestMappingUtil.getRequestMappingInfo(Class.forName(className));
            } catch (ClassNotFoundException e) {
                rmInfo = null;
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (rmInfo == null)
                continue;
            //keySet返回key，get（path）得到对应方法
            for (String path : rmInfo.getMap().keySet()) {
                pathList.add(path);
                methodList.add(rmInfo.getMap().get(path));
                controllerList.add(rmInfo.getControllerObject());
            }
        }
    }

    public String doGet(String urlPath) throws InvocationTargetException, IllegalAccessException, ParserConfigurationException, IOException, SAXException {
        // 1. 从pathList中找出匹配路径，获得其下标，
        // 进而获取methodList和controllerList中对应的方法、controller对象
        // 于是调用方法，获得ModelAndView返回值对象
        int index=-1;
        int count=-1;
        for (Iterator<String> iterator=pathList.iterator();iterator.hasNext();){
            count++;
            if(urlPath.equals(iterator.next())){
                index=count;
            }
        }
        if(index==-1){
            return "404 not found!";
        }
        Method method=methodList.get(index);
        Object controller=controllerList.get(index);
        ModelAndView mav=(ModelAndView)method.invoke(controller);
        // 2. 根据ModelAndView返回值对象，读取view文件，用返回值对象的数据进行替换填充
        File f = new File("D:\\Daima\\JFL3\\resources\\"+mav.getViewName());
        FileReader in=new FileReader(f);
        BufferedReader bufIn=new BufferedReader(in);
        String line=null,result="";
        //srcStrs：jsp里要改动的字符串集合
        Set<String> srcStrs=mav.getModel().keySet();
        Object[] os=srcStrs.toArray();
        String[] srcs=new String[srcStrs.size()];
        for(int i=0;i<srcStrs.size();i++){
            srcs[i]=(String)os[i];  //放到字符串数组里
        }
        while((line=bufIn.readLine())!=null){
            for (int i=0;i<srcs.length;i++){
                if(line.contains(srcs[i]))
                    //替换字符串
                    line=line.replaceAll("\\$\\{"+srcs[i]+"}",mav.getModel().get(srcs[i]));
            }
            result+=line;
            result+="\n";
        }
        bufIn.close();
        return result;
    }
}
