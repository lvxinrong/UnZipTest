package com.lv.unZip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class MyZipUtil {
	
	private static int count = 1; // ����ݹ��������  

	public static void zip(String zipFileName, File inputFile) throws Exception {
		System.out.println("ѹ����");
		ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFileName));
		BufferedOutputStream bo = new BufferedOutputStream(zip);
		zipFile(zip, inputFile, inputFile.getName(), bo);
		bo.close();  
		zip.close(); // ������ر�  
        System.out.println("ѹ�����");
	}

	private static void zipFile(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws Exception {
		if (f.isDirectory()) {
             File[] fl = f.listFiles();
             if(fl.length == 0) {
            	 out.putNextEntry(new ZipEntry(base + "/"));
            	 System.out.println(base + "/");
             }
             for(int i = 0; i < fl.length; i++) {
            	 zipFile(out, fl[i], base + "/" + fl[i].getName(), bo); // �ݹ�������ļ��� 
             }
             count++;
             System.out.println("��" + count + "�ݹ�");
		} else {
			out.putNextEntry(new ZipEntry(base)); 
            System.out.println(base);  
            FileInputStream in = new FileInputStream(f);  
            BufferedInputStream bi = new BufferedInputStream(in);  
            int b;  
            while ((b = bi.read()) != -1) {  
                bo.write(b);
            } 
            bo.flush();
            bi.close();  
            in.close();
		}
	}
	
	public static void unZip(String zipFileName,String destName) throws Exception {
		File file = new File(zipFileName);
        if (!file.exists()) {
            throw new RuntimeException(zipFileName + "��ָ�ļ�������");
        }
        @SuppressWarnings("resource")
		ZipFile zf = new ZipFile(file);
        Enumeration<? extends ZipEntry> entrys = zf.entries();
        ZipEntry entry = null;
        while(entrys.hasMoreElements()) {
        	entry = entrys.nextElement();
        	System.out.println("��ѹ" + entry.getName());
        	if(entry.isDirectory()) {
        		String dirPath = destName + File.separator + entry.getName();
        		File newFile = new File(dirPath);
        		newFile.mkdirs();
        	}else {
        		File f = new File(destName + File.separator + entry.getName());
        		if(!f.exists()) {
        			String dirs = f.getParentFile().getPath();
        			File parentDir = new File(dirs);
        			parentDir.mkdirs();
        		}
        		f.createNewFile();
        		InputStream in = zf.getInputStream(entry);
        		FileOutputStream out = new FileOutputStream(f);
        		int count = 0;
        		byte[] buf = new byte[2048];
        		while((count = in.read()) != -1) {
        			out.write(buf, 0, count);
        		}
        		out.close();
        		in.close();
        	}
        }
	}
}
