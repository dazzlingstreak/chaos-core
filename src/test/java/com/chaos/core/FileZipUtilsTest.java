package com.chaos.core;

import com.chaos.core.util.FileZipUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangdawei 2020/6/18
 */
@SpringBootTest
public class FileZipUtilsTest {

    @Test
    public void zip() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("Test.zip");

        Map<String,String> urls = new HashMap<>();
        urls.put("带看单1","https://imgapi.apitops.com/TEST/link/20200617/79cfdd87ff9b4df6a88f60b51c8efb47.jpeg");
        urls.put("带看单2","https://imgapi.apitops.com/TEST/link/20200616/5868bfcf3c354cd690a9b68537dc57ce.jpeg");

        FileZipUtils.zip(urls,fileOutputStream);

        fileOutputStream.close();
    }
}
