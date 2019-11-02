package com.neuedu.controller.backend;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.vo.ImageVo;
import com.neuedu.common.ServerResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(value = "/manage/")
public class UploadController {
    @Value("${Demo.imageHost}")
    private String imageHost;



    @GetMapping("upload")
    public  String upload(){
        return "upload";
    }

    @PostMapping("upload")
    @ResponseBody
    public ServerResponse upload(@Param("multipartFile")MultipartFile multipartFile){
        if (multipartFile==null||multipartFile.getOriginalFilename().equals("")){

            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"图片必须上传");
        }
        //获取上传的图片名称
        String oldFileName = multipartFile.getOriginalFilename();
        //获取文件扩展名
        String extendName=  oldFileName.substring(oldFileName.lastIndexOf("."));
        //生成新的文件名
        String newFilename= UUID.randomUUID().toString()+extendName;
        File mkdir=new File("F:/upload");
        if(!mkdir.exists()){
            mkdir.mkdirs();
        }
        File newFile=new File(mkdir,newFilename);
        try{
            multipartFile.transferTo(newFile);

            ImageVo imageVo =new ImageVo(newFilename,imageHost+newFilename);

            return ServerResponse.serverResponseBySuccess(imageVo);
        }catch (IOException e){
            e.printStackTrace();
        }

        return ServerResponse.serverResponseByError();
    }
}

