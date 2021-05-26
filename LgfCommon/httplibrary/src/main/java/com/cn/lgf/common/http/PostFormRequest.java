package com.cn.lgf.common.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cn.lgf.common.http.base.Request;
import com.cn.lgf.common.http.base.RequestCacheType;
import com.cn.lgf.common.http.base.RequestType;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PostFormRequest extends Request {
    public List<FileInfo> mListOfFileInfo;

    private PostFormRequest(
            @NonNull List<FileInfo> listOfFileInfo,
            @NonNull String url,
            @Nullable Map<String, String> headers) {
        super(url, RequestType.POST, null, RequestCacheType.DEFAULT, headers, null);
        mListOfFileInfo = new ArrayList<>();
        mListOfFileInfo.addAll(listOfFileInfo);
    }

    public List<FileInfo> getFiles() {
        return Collections.unmodifiableList(mListOfFileInfo);
    }

    private PostFormRequest(@NonNull FileInfo fileInfo,
                            @Nullable String url,
                            @Nullable Map<String, String> headers) {
        super(url, RequestType.POST, null, RequestCacheType.DEFAULT, headers, null);
        mListOfFileInfo = new ArrayList<>();
        mListOfFileInfo.add(fileInfo);
    }


    public static PostFormRequest upload(@NonNull FileInfo fileInfo, @NonNull String url, @Nullable Map<String, String> headers) {
        return new PostFormRequest(fileInfo, url, headers);
    }


    public static PostFormRequest upload(@NonNull FileInfo fileInfo, @Nullable String url) {
        return upload(fileInfo, url, null);
    }


    public static PostFormRequest upload(@NonNull List<FileInfo> fileInfos, @NonNull String url, @Nullable Map<String, String> headers) {
        return new PostFormRequest(fileInfos, url, headers);
    }


    public static PostFormRequest upload(@NonNull List<FileInfo> fileInfos, @NonNull String url) {
        return upload(fileInfos, url, null);
    }


    /**
     * 每个文件上传的最小单元
     */
    public static class FileInfo {
        //类别表单中<input type="file" name="mFile"/>的name属性。
        @NonNull
        public String name;
        @NonNull
        public String fileName;
        @NonNull
        public File file;
    }

    public static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
