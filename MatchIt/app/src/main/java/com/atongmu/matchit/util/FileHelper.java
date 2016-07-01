package com.atongmu.matchit.util;

import java.io.File;

/**
 * Created by mfg on 16/07/01.
 */
public class FileHelper {
    public static void renameFile(String path, String oldname, String newname){
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(path+"/"+oldname);
            File newfile=new File(path+"/"+newname);
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname+"已经存在！");
            else{
                oldfile.renameTo(newfile);
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    /**
     * 获取目录下所有文件名
     * @param path
     * @return
     */
    public static String[] getFileNames(String path){
        File file = new File(path);
        if(file.isDirectory()){
            return file.list();
        }
        return null;
    }

    public static void renameFileToLowerCase(String path, String prefix){
        File file = new File(path);
        String[] filenames = null;
        if(file.isDirectory()){
            filenames= file.list();
        }

        for(String filename:filenames){
            File oldfile=new File(path+"/"+filename);
            File newfile=new File(path+"/"+prefix+filename.toLowerCase());
            oldfile.renameTo(newfile);
        }
    }

//    public static void main(String []args){
//        String path = "E:\\Code\\git\\MatchIt\\MatchIt\\app\\src\\main\\res\\drawable\\resources\\pics\\tools";
//        FileHelper.renameFileToLowerCase(path, "tool_");
//    }
}
