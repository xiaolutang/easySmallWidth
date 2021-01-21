package com.txl.plugin.xmlutils;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class FileUtil {
	public static String HOST="";//"/meilihelan";
	public static String SOBEY  ="";// SDCARD +HOST+ "/";


	public static String USER    ="";// SOBEY + "user/";
	public static String CONTENT ="";// SOBEY + "content/";
	public static String BUFFER  = "";//SOBEY + "buffer/";
	public static String FILEAPK = "";//SOBEY + "apk/";
	public static String CACHE   = "";//SOBEY + "cache/";
	public static String LOG     = "";//SOBEY + "log/";
	public static String PHOTO_APP     = "";//SOBEY + "photo/";

	public static String TEMP="";

	/**
	 * ��ҳ����Ŀ¼
	 * */
	public static String HOME_CACHE_DIR ="";//CACHE+"home/";

	public static final String TXT     = ".txt";
	public static final String TMP     = "tmp";
	public static final String JPG     = "jpg";
	public static final String PNG     = "png";
	public static final String MP3     = ".mp3";
	public static final String APK     = ".apk";

	public static final String TAG = FileUtil.class.getSimpleName();


	/**
	 * �����ļ�·��
	 * 
	 * @param dir
	 * @param filename
	 * @param suf
	 * @return
	 */
	public static String createFilePath(String dir, String filename, String suf) {
		String filepath = null;
		try {
			if (!StringUtils.isEmpty( filename )) {
				File file = new FileX( dir + filename + suf );
				File parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				filepath = file.getPath();
			}
		} catch (Exception ex) {
			filepath = null;
		}
		return filepath;
	}

	public static long fileLength(String path) {
		try {
			File file = new FileX( path );
			if (file.exists()) {
				return file.length();
			}
		} catch (Exception ex) {

		}
		return 0;
	}

	/**
	 * �½��ļ���
	 * 
	 * @param path
	 * @return
	 */
	public static File createDirectory(String path) {
		File file;
		try {
			file = new FileX( path );
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			file = null;
		}
		return file;
	}

	/**
	 * �½��ļ�
	 * 
	 * @param path
	 * @return
	 */
	public static File createFile(String path) {
		File file;
		try {
			file = new FileX( path );
			if (!file.exists()) {
				File parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				file.createNewFile();
			}
			else
			{
				file.delete();
				file.createNewFile();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			file = null;
		}
		return file;
	}

	/**
	 * �½���ʱ�ļ�
	 * 
	 * @param prefix
	 *            �ļ���
	 * @param suffix
	 *            �ļ����� ".txt"
	 * @param directory
	 *            ��Ŀ¼
	 * @return
	 */
	public static File createTempFile(String prefix, String suffix, File directory) {
		File file;
		try {
			if (!directory.exists()) {
				directory.mkdirs();
			}
			file = File.createTempFile( prefix, suffix, directory );
		} catch (IOException ex) {
			ex.printStackTrace();
			file = null;
		}
		return file;
	}

	/**
	 * ����޸�ʱ��
	 * 
	 * @param dir
	 * @param filename
	 * @param suf
	 * @return
	 */
	public static long lastModified(String dir, String filename, String suf) {
		return lastModified( createFilePath( dir, filename, suf ) );
	}

	/**
	 * ����޸�ʱ��
	 * 
	 * @param path
	 * @return
	 */
	public static long lastModified(String path) {
		try {
			if (!StringUtils.isEmpty( path )) {
				File file = new FileX( path );
				if (file.exists()) {
					return file.lastModified();
				}
			}
		} catch (Exception ex) {
		}
		return 0;
	}

	/**
	 * �ļ��Ƿ����
	 * 
	 * @param path
	 * @return
	 */
	public static boolean hasFile(String path) {
		boolean value = false;
		try {
			if (!StringUtils.isEmpty( path )) {
				File file = new FileX( path );
				if (file.exists() && file.isFile()) {
					value = true;
				}
			}
		} catch (Exception ex) {
		}
		return value;
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path) {
		if (null == path || path.length() == 0)
			return true;
		try
		{
			File file = new FileX( path );
			if (file.exists() && file.isFile()) {
				return file.delete();
			}
		}
		catch (Exception e)
		{
		}
		return false;
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param file
	 * @return
	 */
	public static boolean delFile(File file) {
		if (file.exists() && file.isFile()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * ɾ���ļ���
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delDirectory(String path) {
		File directory = new FileX( path );
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (File file : files) {
					if (!delDirectory( file ))
						delFile( file );
				}
			}
			return directory.delete();
		}
		return false;
	}

	/**
	 * ɾ���ļ���
	 * 
	 * @param directory
	 * @return
	 */
	public static boolean delDirectory(File directory) {
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (File file : files) {
					if (!delDirectory( file ))
						delFile( file );
				}
			}
			return directory.delete();
		}
		return false;
	}

	/**
	 * ����ļ���
	 * 
	 * @param path
	 * @return
	 */
	public static boolean clearDirectory(String path) {
		File directory = new FileX( path );

		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();

			if (null != files) {
				for (File file : files) {
					if (!delDirectory( file ))
						delFile( file );
				}
			}
		}

		return true;
	}

	/*
	 * �ļ�����
	 */
	public static void renameFile(String strDest, String strSrc) {
		File fileDest = new FileX( strDest ), fileSrc = new FileX( strSrc );

		if (fileDest.exists() && fileDest.isFile()) {
			fileDest.delete();
		}

		if (fileSrc.exists() && fileSrc.isFile()) {
			fileSrc.renameTo( fileDest );
		}

		if (fileSrc.exists() && fileSrc.isFile()) {
			fileSrc.delete();
		}
	}

	/**
	 * 
	 * @param strPath
	 * @param strText
	 * @return
	 */
	public static boolean saveTextFile(String strPath, String strText) {
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		boolean bRet = true;
		File file = null;

		try {
			createFile(strPath );

			file = new FileX( strPath );

			outputStream = new FileOutputStream( file );
			outputStreamWriter = new OutputStreamWriter( outputStream ,"UTF-8");

			outputStreamWriter.write( strText );
			outputStreamWriter.close();
		} catch (Exception e) {
			bRet = false;
		}

		return bRet;
	}

	/**
	 * ��ȡ�ı�����
	 * @param strPath ���ļ������ļ���ȫ·��
	 * @return
	 */
	public static String readTextFile(String strPath) {
		InputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		String strText;
		StringBuffer stringBuffer = null;
		File file = null;

		try {
			file = new FileX( strPath );
			if (file.exists() && ( file.isFile() )) {
				stringBuffer = new StringBuffer();

				inputStream  = new FileInputStream( file );
				bufferedInputStream = new BufferedInputStream( inputStream );
				inputStreamReader = new InputStreamReader( bufferedInputStream, "UTF-8" );
				bufferedReader = new BufferedReader( inputStreamReader );

				while ( ( strText = bufferedReader.readLine() ) != null) {
					stringBuffer.append( strText+"\n" );
				}

				strText = stringBuffer.toString();
			} else {
				strText = null;
			}
		} catch (Exception e) {
			strText = null;
		} finally {
			try {
				if (null != bufferedReader) {
					bufferedReader.close();
					bufferedReader = null;
				}
				if (null != inputStreamReader) {
					inputStreamReader.close();
					inputStreamReader = null;
				}
				if (null != bufferedInputStream) {
					bufferedInputStream.close();
					bufferedInputStream = null;
				}
				if (null != inputStream) {
					inputStream.close();
					inputStream = null;
				}
			} catch (Exception ex) {
			}
		}

		return strText;
	}


	/**
	 * ��ȡ�ļ���
	 * 
	 * @param ins
	 * @return
	 */
	public static String readTextInputStream(InputStream ins) {
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		String strText=null;
		StringBuffer stringBuffer = null;

		try {
			stringBuffer = new StringBuffer();
			inputStreamReader = new InputStreamReader( ins, "UTF-8" );
			bufferedReader = new BufferedReader( inputStreamReader );

			while((strText = bufferedReader.readLine())!=null)
			{
				if(!StringUtils.isEmpty(strText))
					stringBuffer.append( strText+"\n" );
			}
			strText = stringBuffer.toString();
		} catch (Exception e) {
			strText = null;
		} finally {
			try {
				if (null != bufferedReader) {
					bufferedReader.close();
					bufferedReader = null;
				}
				if (null != inputStreamReader) {
					inputStreamReader.close();
					inputStreamReader = null;
				}
			} catch (Exception e) {
			}
		}

		return strText;
	}

	/**
	 * �����ļ�
	 * 
	 * @param ins
	 * @param ops
	 * @return
	 */
	public static boolean copyFile(InputStream ins, OutputStream ops) {
		boolean result = false;
		try {
			byte[] byBuffer = new byte[1024];
			int readLen = 0;
			while ( ( readLen = ins.read( byBuffer ) ) > 0) {
				ops.write( byBuffer, 0, readLen );
			}
			ops.flush();

			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			try {
				if (ops != null) {
					ops.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * �����ļ�
	 * 
	 * @param newPath
	 * @param oldPath
	 * @return
	 */
	public static boolean copyFile(String newPath, String oldPath) {
		boolean result = false;

		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream( oldPath );
			outputStream = new FileOutputStream( newPath, false );

			byte byBuffer[] = new byte[1024];
			int nRead;
			while ( ( nRead = inputStream.read( byBuffer ) ) > 0) 
			{
				outputStream.write( byBuffer, 0, nRead );
			}
			outputStream.flush();

			result = true;
		} catch (Exception e) {
			result = false;
			System.out.println(e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}

				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}



	public static boolean isFileOutofLength(String path,double targetLength){
		try {
			File file = new FileX( path );
			if (file.exists()) {
				return file.length() > targetLength;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean isFileEnable(String path){
		try {
			File file = new FileX( path );
			if(file.exists())
			{
				if(file.isDirectory())
					return true;
				else if(file.length()>0)
					return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean isFileEnable(File file){
		try {
			if (file.exists() && file.length() > 0) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ʱ���������Ƭ����
	 * 
	 * @return
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	public static File saveData(String data,String fileName)
	{
		return saveData(data.getBytes(), fileName);
	}
	public static File saveData(byte[] data,String fileName)
	{
		FileOutputStream outSteam=null;
		File file=null;
		try
		{
			file=new FileX(CACHE+fileName);
			outSteam=new FileOutputStream(file);
			outSteam.write(data);
			outSteam.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return file;
	}
	public static File saveData(String data,String filePath,String fileName)
	{
		return saveData(data.getBytes(),filePath,fileName);
	}
	public static File saveData(byte[] data,String filePath,String fileName)
	{
		FileOutputStream outSteam=null;
		File file=null;
		try
		{
			file=new FileX(filePath+fileName);
			if(!file.exists())
				createFile(file.getAbsolutePath());
			outSteam=new FileOutputStream(file);
			outSteam.write(data);
			outSteam.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return file;
	}

	public static String getFileNameNoSuffix(String filePath)
	{
		try {
			int endIndex=filePath.lastIndexOf(".");
			if(endIndex==-1)
				endIndex=filePath.length();
			if(filePath.lastIndexOf(File.separator)!=-1)
				return filePath.substring(filePath.lastIndexOf(File.separator)+1, endIndex);
			else if(filePath.lastIndexOf("\\")!=-1)
				return filePath.substring(filePath.lastIndexOf("\\")+1, endIndex);
			else 
				return filePath.substring(0, endIndex);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "";
	}

	public static String getFileName(String filePath)
	{
		try {
			if(filePath.lastIndexOf(File.separator)!=-1)
				return filePath.substring(filePath.lastIndexOf(File.separator)+1);
			else
				return filePath;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "";
	}

	public static List<File> getSimilarNameFiles(String similarFileName,String dirPath)
	{
		ArrayList<File> files=new ArrayList<File>();
		File[] dirFiles=getDirFiles(dirPath);
		for(int i=0;dirFiles!=null&&i<dirFiles.length;i++)
		{
			File itemFile=dirFiles[i];
			if(itemFile.getName().contains(similarFileName))
			{
				files.add(itemFile);
			}
		}
		return files;
	}

	public static long getFileSize(File file)
	{
		if(file.isDirectory())
		{
			long size=0;
			File[] fileList=file.listFiles();
			for(int i=0;i<fileList.length;i++)
			{
				File item=fileList[i];
				if(item.isDirectory())
					size+=getFileSize(item);
				else
					size+=item.length();
			}
			return size;
		}
		else 
			return file.length();
	}

	public static File[] getDirFiles(String dirPath)
	{
		try 
		{
			return new FileX(dirPath).listFiles();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<File> sortFileBySize(List<File> fileList,final boolean bigFirst)
	{
		fileList.sort(new Comparator<File>() 
		{
			@Override
			public int compare(File file1, File file2)
			{
				long sizeFile1=getFileSize(file1);
				long sizeFile2=getFileSize(file2);
				if(sizeFile1>sizeFile2)
				{
					if(!bigFirst)
						return 1;
					else 
						return -1;
				}
				else if(sizeFile1<sizeFile2)
				{
					if(!bigFirst)
						return -1;
					else 
						return 1;
				}
				else
				{
					return 0;
				}
			}
		});
		return fileList;
	}


	public static void copyFolder(String oldPath, String newPath) 
	{
		try { 
			(new FileX(newPath)).mkdirs(); //����ļ��в����� �������ļ��� 
			File a=new FileX(oldPath); 
			String[] file=a.list(); 
			File temp=null; 
			for (int i = 0; i < file.length; i++) 
			{ 
				if(oldPath.endsWith(File.separator))
				{ 
					temp=new FileX(oldPath+file[i]); 
				} 
				else{ 
					temp=new FileX(oldPath+File.separator+file[i]); 
				}

				if(temp.isFile())
				{
					copyFile((newPath + "/" + (temp.getName()).toString()),temp.getAbsolutePath());
				} 
				if(temp.isDirectory())
				{//��������ļ��� 
					copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
				} 
			} 
		} 
		catch (Exception e)
		{ 
			System.out.println("���������ļ������ݲ�������:\n"+e); 
			e.printStackTrace();

		}

	}

	public static void cutFolder(String oldPath, String newPath) 
	{
		copyFolder(oldPath, newPath);
		delDirectory(oldPath);
	}

	public static boolean containsFileWithSimpleName(String simpleNameWithSufix,File[] files)
	{
		for(File file:files)
		{
			if(file.isDirectory())
			{
				boolean value=containsFileWithSimpleName(simpleNameWithSufix,file.listFiles());
				if(value)
					return true;
			}
			else
			{
				String fileName=FileUtil.getFileName(file.getAbsolutePath());
				if(simpleNameWithSufix.equals(fileName))
					return true;
			}
		}
		return false;
	}

	public static ArrayList<String> readStringLineArray(String path)
	{
		ArrayList<String> arrayList=new ArrayList<String>();

		InputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		String strText="";
		File file = null;

		try {
			file = new FileX( path );
			if (file.exists() && ( file.isFile() )) {

				inputStream  = new FileInputStream( file );
				bufferedInputStream = new BufferedInputStream( inputStream );
				inputStreamReader = new InputStreamReader( bufferedInputStream, "UTF-8" );
				bufferedReader = new BufferedReader( inputStreamReader );

				while ( ( strText = bufferedReader.readLine() ) != null) {
					if(!StringUtils.isEmpty(strText))
					{
						arrayList.add(strText.trim());
					}
				}
			}
		} catch (Exception e) {
			strText = null;
		} finally {
			try {
				if (null != bufferedReader) {
					bufferedReader.close();
					bufferedReader = null;
				}
				if (null != inputStreamReader) {
					inputStreamReader.close();
					inputStreamReader = null;
				}
				if (null != bufferedInputStream) {
					bufferedInputStream.close();
					bufferedInputStream = null;
				}
				if (null != inputStream) {
					inputStream.close();
					inputStream = null;
				}
			} catch (Exception ex) {
			}
		}
		return arrayList;
	}

	public static final String JPEG="jpeg",GIF="gif",BMP="bmp";
	public static boolean isImageSuffix(String filePath)
	{
		if(filePath.lastIndexOf(".")!=-1)
		{
			String suffix=filePath.substring(filePath.lastIndexOf("."));
			suffix=suffix.toLowerCase();
			return suffix.contains(JPEG)||suffix.contains(JPG)||suffix.contains(GIF)||suffix.contains(BMP)||suffix.contains(PNG);
		}
		return false;
	}

	public static String getImageSuffix(String filePath)
	{
		if(filePath.lastIndexOf(".")!=-1)
		{
			String suffix=filePath.substring(filePath.lastIndexOf(".")+1);
			suffix=suffix.toLowerCase();
			return suffix;
		}
		return "";
	}
}