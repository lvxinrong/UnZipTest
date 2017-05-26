package com.lv.unZip;

import java.io.File;

public class Main {
	
	public static void main(String... args) {
		if(args.length != 3) 
			throw new IllegalArgumentException("�����쳣");
		String method = args[0];
		if(method.equals("1")) {
			//ѹ��
			String fileZipName = args[1];
			String zipSource = args[2];
			try {
				MyZipUtil.zip(fileZipName, new File(zipSource));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(method.equals("2")) {
			String fileZipName = args[1];
			String destName = args[2];
			try {
				MyZipUtil.unZip(fileZipName, destName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			throw new IllegalArgumentException("���ܲ�������������[1,2]");
		}
	}

}
