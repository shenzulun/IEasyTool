/**
 * File Name: FileTools.java
 * Date: 2019-08-15 16:50:11
 */
package me.belucky.easytool.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 文件操作工具类
 * @author shenzulun
 * @date 2017-06-07
 * @version 1.0
 */
public class FileTools {
	protected static Logger log = LoggerFactory.getLogger(FileTools.class);
	
	/**
	 * 获取目录
	 * @param path
	 * @return
	 */
	public static File getDirectory(String path){
		if(path == null) {
			log.warn("path can't be null");
			return null;
		}
		File file = new File(path);
		if(!file.exists()){
			log.warn("path [{}] not found", path);
			return null;
		}
		if(file.isFile()){
			log.warn("give me directory path not the file path [{}]", file.getPath());
			return null;
		}
		return file;	
	}
	
	/**
	 * 获取文件
	 * @param path
	 * @return
	 */
	public static File getFile(String path){
		if(path == null) {
			log.warn("path can't be null");
			return null;
		}
		File file = new File(path);
		if(!file.exists()){
			log.info("path [{}] not found", path);
			return null;
		}
		return file;	
	}
	
	/**
	 * 获取当前目录下的所有文件
	 * @param path
	 * @return
	 */
	public static File[] getFileArray(String folder){
		if(folder == null) {
			log.warn("folder can't be null");
			return null;
		}
		File file = new File(folder);
		if(!file.exists()){
			log.info("folder [{}] not found", folder);
			return null;
		}
		return file.listFiles();
	}
	
	/**
	 * 获取当前目录下的指定命名的所有文件
	 * @param folder
	 * @param regex
	 * @return
	 */
	public static File[] getFileArray(String folder,final String regex){
		File file = new File(folder);
		if(!file.exists()){
			log.info("folder [{}] not found", folder);
			return null;
		}
		File[] files = file.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(name);
				return matcher.find();
			}
			
		});
		return files;
	}
	
	/**
	 * 获取当前目录下的指定命名的所有文件
	 * @param folder
	 * @param regex
	 * @return
	 */
	public static String[] getFileNameArray(String folder,final String regex){
		File file = new File(folder);
		if(!file.exists()){
			log.info("folder [{}] not found", folder);
			return null;
		}
		String[] files = file.list(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(name);
				return matcher.find();
			}
			
		});
		return files;
	}
	
	/**
	 * 文件拷贝
	 * @param source	原文件
	 * @param target	新文件
	 * @return
	 */
	public static boolean nioCopy(File source,File target){
		boolean isSuccess = false;
		//check
		if(!source.exists()){
			log.info("source [{}] not exists", source);
			return false;
		}
		if(!target.exists()){
			if(!createFile(target))
				return false;
		}
		if(!source.isFile()){
			log.info("source is not a file");
			return false;
		}
		if(!target.isFile()){
			log.info("target is not a file");
			return false;
		}
		
		FileChannel in = null;   
		FileChannel out = null;
		RandomAccessFile fin = null;
		RandomAccessFile fout = null;
		
		try {
			fin = new RandomAccessFile(source,"rw");
			fout = new RandomAccessFile(target,"rw");
			in = fin.getChannel();
			out = fout.getChannel();			
			in.transferTo(0, in.size(), out);
			isSuccess = true;
		} catch (FileNotFoundException e) {
			log.error("",e);
		} catch (IOException e) {
			log.error("",e);
		} catch(Exception e){
			log.error("",e);
		} finally{
			if(isSuccess){
				log.info("copy succeed!");
			}else{
				log.info("copy failed!");
			}
			try {
				out.close();
				in.close();
				fout.close();
				fin.close();
			} catch (IOException e) {
				log.error("",e);
			}
		}	
		return isSuccess;
	}
	
	/**
	 * 文件拷贝
	 * @param sourcePath
	 * @param targetPath
	 * @return
	 */
	public static boolean nioCopy(String sourcePath, String targetPath){
		return nioCopy(new File(sourcePath),new File(targetPath));
	}
	
	/**
	 * 创建新文件
	 * @param path
	 * @return
	 */
	public static boolean createFile(String path){
		return createFile(new File(path));
	}
	
	/**
	 * 创建新文件
	 * @param file
	 * @return
	 */
	public static boolean createFile(File file){
		boolean isSuccess = false;
		try {
			file.createNewFile();
			isSuccess = true;
		} catch (IOException e) {		
			log.error("",e);
		} finally{
			if(isSuccess){
				log.info("create new File [{}] succeed", file.getName());
			}else{
				log.info("create new File [{}] failed", file.getName());
			}
		}
		return isSuccess;
	}
	/**
	 * 创建新文件,自动适配建立文件路径
	 * @param path
	 * @return
	 */
	public static boolean createFileAuto(String path){
		boolean isSuccess = false;
		int ind1 = path.lastIndexOf("/");
		if(ind1 > 0){
			String p = path.substring(0, ind1);
			File dir = new File(p);
			if(!dir.exists()){
				//create
				dir.mkdirs();
			}
		}
		File file = new File(path);
		try {
			file.createNewFile();
			isSuccess = true;
		} catch (IOException e) {
			log.error("",e);
		}finally{
			if(isSuccess){
				log.info("create new File [{}] succeed", file.getName());
			}else{
				log.info("create new File [{}] failed", file.getName());
			}
		}
		return isSuccess;
	}
	
	/**
	 * 获取文件内容,默认相对路径
	 * @param templateName
	 * @return
	 */
	public static String getContent(String fileName){
		return getContent(fileName, false);
	}
	
	/**
	 * 获取文件内容
	 * @param file
	 * @return
	 */
	public static String getContent(File file){
		return getContent(file.getAbsolutePath(),true);
	}
	
	/**
	 * 获取文件内容
	 * @param file
	 * @param charsetName
	 * @return
	 */
	public static String getContent(File file, String charsetName){
		return getContent(file.getAbsolutePath(),true, charsetName);
	}
	
	/**
	 * 获取文件
	 * @param fileName
	 * @param isAbsolute  true: 绝对路径   false: 相对路径
	 * @return
	 */
	public static String getContent(String fileName, boolean isAbsolute){
		return getContent(fileName, isAbsolute, null);
	}
	
	/**
	 * 获取文件
	 * @param fileName
	 * @param isAbsolute  true: 绝对路径   false: 相对路径
	 * @param charsetName 字符集
	 * @return
	 */
	public static String getContent(String fileName, boolean isAbsolute, String charsetName){
		StringBuffer buffer = new StringBuffer();
		InputStream is = null;
		String lineSeparator = System.getProperty("line.separator","\n");
		try {
			if(isAbsolute){
				is = new FileInputStream(fileName);
			}else{
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			}
			InputStreamReader isr = null;
			if(charsetName != null){
				isr = new InputStreamReader(is, charsetName);
			}else{
				isr = new InputStreamReader(is);
			}
			BufferedReader br = new BufferedReader(isr);
			while(br.ready()){
				buffer.append(br.readLine()).append(lineSeparator);
			}
			br.close();
		} catch (IOException e) {
			log.error("",e);
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					log.error("",e);
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 获取文件内容,默认相对路径
	 * @param templateName
	 * @return
	 */
	public static List<String> getContentList(String fileName){
		return getContentList(fileName, false);
	}
	
	/**
	 * 获取文件内容
	 * @param file
	 * @return
	 */
	public static List<String> getContentList(File file){
		return getContentList(file.getAbsolutePath(), true);
	}
	
	/**
	 * 获取文件内容
	 * @param file
	 * @return
	 */
	public static List<String> getContentList(File file, String charsetName){
		return getContentList(file.getAbsolutePath(), true, charsetName);
	}
	
	/**
	 *  获取文件内容
	 * @param fileName
	 * @param isAbsolute  true: 绝对路径   false: 相对路径
	 * @return
	 */
	public static List<String> getContentList(String fileName, boolean isAbsolute){
		return getContentList(fileName, isAbsolute, null);
	}
	
	/**
	 *  获取文件内容
	 * @param fileName
	 * @param isAbsolute  true: 绝对路径   false: 相对路径
	 * @return
	 */
	public static List<String> getContentList(String fileName, boolean isAbsolute, String charsetName){
		List<String> contents = new ArrayList<String>();
		InputStream is = null;
		try {
			if(isAbsolute){
				File file = new File(fileName);
				if(!file.exists()){
					return contents;
				}
				is = new FileInputStream(fileName);
			}else{
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			}
			InputStreamReader isr = null;
			if(charsetName != null){
				isr = new InputStreamReader(is, charsetName);
			}else{
				isr = new InputStreamReader(is);
			}
			BufferedReader br = new BufferedReader(isr);
			while(br.ready()){
				contents.add(br.readLine());
			}
			br.close();
		} catch (IOException e) {
			log.error("",e);
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					log.error("",e);
				}
			}
		}
		return contents;
	}
	
	/**
	 * 导出文件,默认UTF-8格式
	 * @param filePath
	 * @param contents
	 */
	public static void exportFile(String filePath, String contents){
		exportFile(filePath, contents, "UTF-8");
	}
	
	/**
	 * 导出文件
	 * @param filePath
	 * @param contents
	 */
	public static void exportFile(String filePath, String contents, String charsetName){
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		createFileAuto(filePath);
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			OutputStreamWriter out = new OutputStreamWriter(fos,charsetName);	
			BufferedWriter writer = new BufferedWriter(out);
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			log.error("",e);
		}
	}
	
	/**
	 * 导出文件
	 * @param path
	 * @param contents
	 */
	public static void exportFile(String filePath, List<String> contentList, String charsetName){
		String contents = "";
		if(contentList != null){
			StringBuffer buff = new StringBuffer();
			String lineSeparator = System.getProperty("line.separator","\n");
			for(String str : contentList){
				buff.append(str).append(lineSeparator);
			}
			contents = buff.toString();
		}
		exportFile(filePath,contents,charsetName);
	}
	
	/**
	 * 导出文件
	 * @param filePath
	 * @param contentList
	 */
	public static void exportFile(String filePath, List<String> contentList){
		exportFile(filePath,contentList,"UTF-8");
	}
	
	/**
	 * 删除文件
	 * @param file
	 */
	public static void removeFile(File file){
		if(file.exists()){
			file.delete();
		}
	}
	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static void removeFile(String filePath){
		removeFile(new File(filePath));
	}
	/**
	 * 清空当前目录,不循环删除目录下的子目录
	 * @param folderPath	目录
	 */
	public static void clearFolder(String folderPath){
		File file = new File(folderPath);
		clearFiles(file.listFiles());
	}
	/**
	 * 清空当前目录,不循环删除目录下的子目录
	 * @param folderPath	目录
	 * @param fileType		匹配的文件类型
	 */
	public static void clearFolder(String folderPath, final String fileType){
		File file = new File(folderPath);
		File[] files = file.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(fileType)){
					return true;
				}
				return false;
			}
		});
		clearFiles(files);
	}
	/**
	 * 批量删除文件
	 * @param files
	 */
	public static void clearFiles(File[] files){
		for(File file : files){
			if(file.exists() && file.isFile()){
				removeFile(file);
			}
		}
	}
	/**
	 * 获取目录下的指定格式文件
	 * @param rootFolderPath
	 * @param fileType
	 * @return
	 */
	public List<String> getFileNames(String rootFolderPath, final String fileType){
		File file = new File(rootFolderPath);
		String[] files = file.list(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(fileType)){
					return true;
				}
				return false;
			}
			
		});
		return Arrays.asList(files);
	}
	
	/**
	 * 获取目录下的指定格式文件
	 * @param rootFolderPath
	 * @param fileType
	 * @return
	 */
	public List<File> getFiles(String rootFolderPath, final String fileType){
		File file = new File(rootFolderPath);
		File[] files = file.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(fileType)){
					return true;
				}
				return false;
			}
		});
		return Arrays.asList(files);
	}
	
	/**
	 * 文件压缩成.gz格式
	 * @param inFileName
	 * @throws IOException
	 */
	public static String compressFileByGZ(String inFileName) throws IOException {   
		if(!StringUtils.isNotNull(inFileName)) {
			log.info("输入的文件名不符合要求");
			return null;
		}
		GZIPOutputStream out = null;   
		FileInputStream in = null;   
        try {   
            String outFileName = inFileName + ".gz";   
            out = new GZIPOutputStream(new FileOutputStream(outFileName));   
            in = new FileInputStream(inFileName);   
  
            byte[] buf = new byte[1024];   
            int len;   
            while((len = in.read(buf)) > 0) {   
                out.write(buf, 0, len);   
            }   
            out.finish();
            log.info("文件[{}]压缩成功->{}", inFileName, outFileName);   
            return outFileName;
        } catch (IOException e) {   
           log.error("文件[{}]压缩失败", inFileName);
           throw e;
        } finally {
        	if(out != null) {
        		out.close();
        	}
        	if(in != null) {
        		in.close();
        	}
        }
    }
	
	/**
	 * 解压缩.gz格式的文件
	 * @param inFileName
	 * @throws IOException 
	 */
	public static String uncompressFileByGZ(String inFileName) throws IOException {   
		if(!StringUtils.isNotNull(inFileName) || !inFileName.toUpperCase().endsWith(".GZ")) {
			log.info("输入的文件名不符合要求");
			return null;
		}
		GZIPInputStream in = null;  
		FileOutputStream out = null;   
        try {   
        	String outFileName = inFileName.substring(0, inFileName.length() - 3);
            in = new GZIPInputStream(new FileInputStream(inFileName));   
            out = new FileOutputStream(outFileName);   
  
            byte[] buf = new byte[1024];   
            int len;   
            while((len = in.read(buf)) > 0) {   
                out.write(buf, 0, len);   
            }    
            log.info("文件[{}]解压成功->{}", inFileName, outFileName);   
            return outFileName;
        } catch (IOException e) {   
        	log.error("文件[{}]解压失败", inFileName);
            throw e;
        } finally {
        	if(out != null) {
        		out.close();
        	}
        	if(in != null) {
        		in.close();
        	}
        }   
    }
	
	/**
	 * 计算文件的MD5
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String calcFileMD5(String fileName) throws IOException {
		if(!StringUtils.isNotNull(fileName)) {
			log.info("文件名不能为空");
			return null;
		}
		String md5 = DigestUtils.md5Hex(new FileInputStream(fileName));
		log.info("文件[{}]MD5计算完成:{}", fileName, md5);
		return md5;
	}
	
	/**
	 * 获取文件的字节大小
	 * @param fileName
	 * @return
	 */
	public static long getFileSize(String fileName) {
		if(!StringUtils.isNotNull(fileName)) {
			log.info("文件名不能为空");
			return -1;
		}
		File file = new File(fileName);
		if(!file.exists()) {
			log.info("文件[{}]不存在", fileName);
			return -1;
		}
		return file.length();
	}

}
