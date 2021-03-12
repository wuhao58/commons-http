package com.leo.commons.mian.demo;

import com.leo.commons.http.HttpExecutorManage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: LEO
 * @Date: 2021-03-11 17:03
 * @Description:
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/demo")
public class DemoController {

    private HttpExecutorManage httpExecutorManage;

    @GetMapping(value = "/okhttp")
    public String okhttp() {
        String url = "https://www.baidu.com";
        String result = httpExecutorManage.doGet(url, 1000);
        log.info("result={}", result);

        return "ok";
    }

}
