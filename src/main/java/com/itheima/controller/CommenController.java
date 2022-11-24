package com.itheima.controller;

import com.itheima.Common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 这个类主要处理文件的上传与下载
 */
@RestController
@RequestMapping("/commen")
@Slf4j
public class CommenController {
    @Value("${reggie.path}")
    private String path;
    /*sping框架在spring-web包中对文件上传进行了封装，简化了服务端代码，我们只需要在控制层的方法中声明一个
       MultipartFile类型的参数即可接收上传的文件
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){//此处的参数名必须与前端表单name中的属性值相同，不能随便取
        String uuid = UUID.randomUUID().toString();
//        //获取文件原始的文件名(不推荐，若用户上传的文件名重复容易出现文件覆盖问题)
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //当前这个file用户上传之后是一个临时文件，需要作出处理，转存到指定位置，否则，请求完成后，该文件自动删除消失
        //文件转存操作
        String fileName = uuid+suffix;

        //创建一个目录对象
        File dir = new File(path);
        //判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(path+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }
}
