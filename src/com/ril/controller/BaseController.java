package com.ril.controller;

import com.alibaba.fastjson.JSONObject;

public class BaseController {
	
	public JSONObject success(String msg) {
        JSONObject object = new JSONObject();
        object.put("status", "SUCCESS");
        object.put("msg", msg);
        return object;
    }
	public JSONObject fail(String err) {
        JSONObject object = new JSONObject();
        object.put("status", "FAIL");
        object.put("msg", err);
        return object;
    }
}
