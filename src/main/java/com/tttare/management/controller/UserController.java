package com.tttare.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tttare.management.common.model.Contant;
import com.tttare.management.common.model.ResponseParam;
import com.tttare.management.common.redis.IRedis;
import com.tttare.management.common.utils.FileViewUtil;
import com.tttare.management.model.FileObject;

import com.tttare.management.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

@Log
@Controller
@RequestMapping(value="/userManage")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource(name = "redisUtil")
    private IRedis redisUtil;

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @RequestMapping("/listPage")
    @ResponseBody
    public ResponseParam userListPage(@RequestBody Map<String,Object> params){
        Page page = userService.findUserPage(params);
        return new ResponseParam(Contant.SUCCESS,page,null);
    }

    @RequestMapping("/viewFile")
    public String viewFile(Model model){
        FileObject fo = new FileObject();
        File file = new File("E:\\书籍\\tttare");
        fo.setId("top");
        FileObject fileObject = FileViewUtil.getSubFile(file, fo,0);
        String str = JSONObject.toJSONString(fileObject);
        model.addAttribute("fileObject",str);
        return "/user/fileList";
    }

    @RequestMapping("/viewFileJson")
    @ResponseBody
    public FileObject viewFileJson(Model model, HttpServletResponse response) throws Exception {
        FileObject fObj = redisUtil.getObject("viewFileObj",FileObject.class);
        if(fObj==null){
            FileObject fo = new FileObject();
            fo.setId("top");
            File file = new File("E:\\书籍\\tttare");
            fObj = FileViewUtil.getSubFile(file, fo,0);
            redisUtil.setObject("viewFileObj",fObj,null);
        }
        return fObj;
    }

    @RequestMapping("/viewVideo")
    public String viewVideo(Model model){

        return "/common/videoPlay";
    }
}
