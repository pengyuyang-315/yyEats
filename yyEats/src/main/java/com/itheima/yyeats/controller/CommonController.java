package com.itheima.yyeats.controller;

import com.itheima.yyeats.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * file upload and download
 * @param
 * @return
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${yyeats.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
//        get original fileName
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        using UUID to make name unique
        String fileName = UUID.randomUUID().toString()+suffix;

//        check whether the filepath exists
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        try{
            file.transferTo(new File(basePath+fileName));

        }catch (IOException e){
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
