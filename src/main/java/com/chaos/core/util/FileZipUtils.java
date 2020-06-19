package com.chaos.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author huangdawei 2020/6/18
 */
public class FileZipUtils {

    private final static int INITIAL_CAPACITY = 1024 * 64;

    /**
     * 将文件打包
     *
     * @param urls         文件信息，key值为文件名，value为oss path
     * @param outputStream 输出流
     * @throws IOException
     */
    public static void zip(Map<String, String> urls, OutputStream outputStream) throws IOException {

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            Map<String, String> syncUrls = Collections.synchronizedMap(urls);

            for (Map.Entry<String, String> entry : syncUrls.entrySet()) {
                String zipEntryName = entry.getKey();
                String fileUrlPath = entry.getValue();

                try (InputStream inputStream = FileUtils.getFileStream(fileUrlPath)) {
                    zip(inputStream, zipOutputStream, zipEntryName);
                }
            }
        }
    }

    /**
     * 将文件写入zip流
     *
     * @param inputStream     文件流
     * @param zipOutputStream 压缩文件流
     * @param zipEntryName    指定的文件名
     * @throws IOException
     */
    private static void zip(InputStream inputStream, ZipOutputStream zipOutputStream, String zipEntryName) throws IOException {
        //每次读取64KB大小
        byte[] bytes = FileUtils.allocateBytes(INITIAL_CAPACITY);
        ZipEntry zipEntry = new ZipEntry(zipEntryName);
        zipOutputStream.putNextEntry(zipEntry);

        int readCount;
        while ((readCount = inputStream.read(bytes, 0, bytes.length)) != -1) {
            zipOutputStream.write(bytes, 0, readCount);
        }

    }
}
