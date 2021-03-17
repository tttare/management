package com.tttare.management.common.utils;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * ClassName: FileUtil <br/>
 * Description: file operate util<br/>
 * date: 2020/2/5 21:29<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
public class FileUtil {

     /**
     * 复制单个文件
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名
     * @return
     */
     public void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[10485760];
                while((byteread = inStream.read(buffer)) != -1){
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                    }
                inStream.close();
            }
        }catch (Exception e) {
            //文件操作失败

        }
     }


    /**
     * 图片转base64字符串
     * @param imgFile 图片File对象
     * @return base64字符串
     */
    public static String GetImageStr(File imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {

            in = new FileInputStream(imgFile);

            data = new byte[in.available()];

            in.read(data);

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * base64字符串转化成图片
     * @param base64 base64字符串
     * @param  filePath 文件夹路径(不是文件路径)
     * @param  fileName 文件名称
     * @return
     */
    public static File base64ToFile(String base64, String filePath,String fileName) {
        if(base64.contains("data:image")){
            base64 = base64.substring(base64.indexOf(",")+1);
        }
        base64 = base64.toString().replace("\r\n", "");
        File file = null;
        //创建文件目录
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        OutputStream out = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64);

            file = new File(filePath + File.separator + fileName);
            out = new FileOutputStream(filePath + File.separator + fileName);
            out.write(bytes);
        }catch(Exception e){

        }
        finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * base64字符串转化成图片
     * @param file 文件
     * @param  encoding 字符集
     * @return String
     */
    public static String readFile(File file,String encoding) {
        if(StringUtils.isEmpty(encoding)){
            encoding = "utf-8";
        }
        String str=null;
        try {
            FileInputStream in=new FileInputStream(file);
            // size 为字串的长度 ，这里一次性读完
            int size=in.available();
            byte[] buffer=new byte[size];
            in.read(buffer);
            in.close();
            str=new String(buffer,encoding);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
