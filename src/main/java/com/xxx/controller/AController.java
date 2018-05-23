package com.xxx.controller;

import com.xxx.controller.response.BaseResponse;
import com.xxx.service.AService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.awt.*;

/**
 * Created by fu.dongbo on 2018/5/23.
 * Description:
 */

@RestController
@RequestMapping("/xxx/xxx")
public class AController {
    @Resource
    AService aService;

    @RequestMapping(value = "/XXX/XXX", method = RequestMethod.POST, produces = PageAttributes.MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> calYield(){

    }

}
