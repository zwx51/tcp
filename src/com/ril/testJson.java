package com.ril;

import com.alibaba.fastjson.*;
import com.ril.bean.Machine;
public class testJson {
	public static void main(String[] args) throws Exception {
		String userString = "{\"machid\":1}";
		JSONObject userJson = JSONObject.parseObject(userString);
		Machine user = JSON.toJavaObject(userJson,Machine.class);
		System.out.println(user);
	}
}
