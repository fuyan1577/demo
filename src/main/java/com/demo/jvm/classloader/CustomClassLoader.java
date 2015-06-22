package com.demo.jvm.classloader;

import java.io.*;

/**
 * Created by ivan on 2015/6/22.
 */
public class CustomClassLoader extends ClassLoader {
    private String basePath;

    public CustomClassLoader(String basePath) {
        this.basePath = basePath;
    }

    public synchronized Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        Class result;
        byte classData[];

        result = findLoadedClass(className);
        if (result != null) {
            return result;
        }

        //if (className.compareTo("") != 0) {
        try {
            result = super.findSystemClass(className);
            return result;
        } catch (ClassNotFoundException e) {
        }
        //}

        classData = getTypeFromBasePath(className);
        if (classData == null) {
            System.out.println("Could not load class: " + className);
            throw new ClassNotFoundException();
        }
        result = defineClass(className, classData, 0, classData.length);
        if (result == null) {
            System.out.println("Class format error: " + className);
            throw new ClassFormatError();
        }

        if (resolveIt) {
            resolveClass(result);
        }

        return result;
    }

    private byte[] getTypeFromBasePath(String typeName) {
        FileInputStream fis;
        String fileName = basePath + File.separatorChar + typeName.replace('.', File.separatorChar) + ".class";
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            return null;
        }

        BufferedInputStream bis = new BufferedInputStream(fis);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int c = bis.read();
            while (c != -1) {
                out.write(c);
                c = bis.read();
            }
        } catch (IOException e) {
            return null;
        }

        return out.toByteArray();
    }
}
