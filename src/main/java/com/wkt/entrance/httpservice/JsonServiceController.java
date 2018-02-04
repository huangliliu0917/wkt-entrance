package com.wkt.entrance.json.httpservice;

import com.wkt.entrance.common.CommonController;
import com.wkt.entrance.common.CommonManager;
import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.aspect.RestfulAnnotation;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.socket.IsoStrManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author : zmj
 * @description : http json接口服务controller
 * ---------------------------------
 */
@RequestMapping("noRoot")
@RestController
public class JsonServiceController extends CommonController{
    @Autowired
    IsoStrManager isoStrManager;

    @RequestMapping(value = "/json" , method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String json(@RequestBody String json){
        return isoStrManager.deal(json,null);
    }

    @RequestMapping(value = "/json2" , method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String jsonTest(){
        HashMap<String,Object> baseMap = (HashMap<String, Object>) request.getAttribute("baseMap");
        String json1 = baseMap.get("json").toString();
        System.out.println(json1);
        return json1;
    }
}
