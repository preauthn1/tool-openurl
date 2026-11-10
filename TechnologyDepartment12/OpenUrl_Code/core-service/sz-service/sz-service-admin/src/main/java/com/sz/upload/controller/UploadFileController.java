package com.sz.upload.controller;

import com.sz.core.common.entity.ResultMsg;
import com.sz.upload.utils.CosUtils;
import com.sz.upload.utils.S3Service;
import com.sz.upload.vo.UploadFileVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 */
@RestController
@RequestMapping("filaAttach")
public class UploadFileController {

    //访问域名
    private final String COS_BASE_URL = "https://ricecolor-1308137330.cos.ap-chongqing.myqcloud.com";
    @Autowired
    private S3Service s3Service;

    /**
     * 文件上传(旧)
     *
     * @return
     */
    @PostMapping(value = "upload")
    public ResultMsg upload(MultipartFile file) {
//        Map<String, Object> result = new HashMap<>();
//        String oName = file.getOriginalFilename();
//        String extName = oName.substring(oName.lastIndexOf(".") + 1);
//
//        //当天日期
//        LocalDateTime time=LocalDateTime.now();
//        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String strDate2 = dtf2.format(time);
//
//        String fileUUIDname= UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
//        String url = "/sdtf/"+strDate2+"/";
//        if (file != null) {
//            String path = url+fileUUIDname;
//            String s1 = CosUtils.uploadFile(file, path+"."+extName);
//            result.put("src", COS_BASE_URL+path+"."+extName);
//            result.put("name", oName);
//            long fileLength = file.getSize();
//            double s = ((double) (fileLength) / 1000);
//            DecimalFormat df = new DecimalFormat("#.00");
//        }
//
//        return ResultMsg.success(result);


        try {
            Map<String, Object> result = new HashMap<>();
            String fileName = s3Service.uploadFile(file);
            result.put("src", fileName);
            result.put("name", file.getOriginalFilename());
            return ResultMsg.success(result);
        } catch (IOException e) {
            return ResultMsg.error(e.getMessage());
        }

    }

    /**
     * 通用下载
     *
     * @param
     * @return
     */
    @PostMapping("downAllFile")
    public void downAllFile(@RequestBody UploadFileVo uploadFileVo, HttpServletResponse response) throws IOException {
        // 指明response的返回对象是文件流
        response.setContentType("application/octet-stream");
        // 设置在下载框默认显示的文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uploadFileVo.getOldName(), "UTF-8"));

        // 读取文件并写入响应流
        try {
            // 文件的地址
            URL url = new URL(uploadFileVo.getFileUrl());
            InputStream inputStream = url.openConnection().getInputStream();
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO 删除文件
    }

}
