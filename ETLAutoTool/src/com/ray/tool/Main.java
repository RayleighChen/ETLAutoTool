package com.ray.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//工具初始化
		Tool tool = new Tool();
		
		//根据变更总控，获取所有变更号，和修改的SDM
		HashMap<String, ArrayList<String>> versions = (HashMap<String, ArrayList<String>>) tool.getChangeVersion();
		
//*******************
		//初始化变更号
		//1. 初始化某个变更号
//		tool.createFileTree("20140924001", versions.get("20140924001"));
		tool.updateFileTree("20140924001", versions.get("20140924001"));		
	}
}