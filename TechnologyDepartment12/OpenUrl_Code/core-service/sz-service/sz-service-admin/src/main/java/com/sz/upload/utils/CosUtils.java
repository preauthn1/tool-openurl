package com.sz.upload.utils;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 对象存储
 */
public class CosUtils {

    /*
     * secretId
     */
    public static final String ACCESSKEY = "REDACTED";
    /*
     * secretKey
     */
    public static final String SECRETKEY ="REDACTED";
    /*
     * 存储桶
     */
    public static final String BUCKETNAME = "ricecolor-1308137330";
    /*
     * APPID
     */
    public static final String APPID = "1308137330";
    /*
     * 区域
     */
    public static final String REGIONID = "ap-chongqing";

    //访问域名
    private final String COS_BASE_URL = "https://ricecolor-1308137330.cos.ap-chongqing.myqcloud.com";

    /**
     * 初始化CosClient相关配置， appid、accessKey、secretKey、region
     * @return
     */
    public static COSClient getCosClient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(ACCESSKEY, SECRETKEY);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        ClientConfig clientConfig = new ClientConfig(new Region(REGIONID));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        //String bucketName = BUCKETNAME;
        return cosClient;
    }


    /**
     * 上传文件
     * @return
     * //绝对路径和相对路径都OK
     */
    public static String uploadFile(MultipartFile file, String path) {
        String result=null;
        COSClient cosClient = getCosClient();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKETNAME, path, inputStream, metadata);
            // 设置存储类型, 默认是标准(Standard), 低频(standard_ia),一般为标准的
            putObjectRequest.setStorageClass(StorageClass.Standard);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            result= etag;
            System.out.println("文件的etag:"+etag);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        // 关闭客户端
        cosClient.shutdown();
        return result;
    }

    /**
     * 上传文件
     * length为必填项 不确定长度时填小于0的数值
     * @return
     * //绝对路径和相对路径都OK
     */
    public static String uploadFile(InputStream inputStream,String path,int length) {
        String result=null;
        COSClient cc = getCosClient();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKETNAME, path,inputStream, metadata);
            if (length > 0){
                //设置输入流的长度
                metadata.setContentLength(length);
            }
            // 设置存储类型, 默认是标准(Standard), 低频(standard_ia),一般为标准的
            putObjectRequest.setStorageClass(StorageClass.Standard);
            PutObjectResult putObjectResult = cc.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            result= etag;
            System.out.println("文件的etag:"+etag);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        // 关闭客户端
        cc.shutdown();
        return result;
    }

    /**
     * 删除文件
     * @return
     */
    public static void removeFile(String path) {
        COSClient cc = getCosClient();
        try {
            cc.deleteObject(BUCKETNAME,path);
            System.out.println("删除文件:" + path);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        // 关闭客户端
        cc.shutdown();
    }
}
