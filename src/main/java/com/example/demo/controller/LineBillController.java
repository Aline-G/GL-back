package com.example.demo.controller;

import com.example.demo.service.LineBillService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequestMapping("/lineBill")
@RestController
public class LineBillController {
    @Autowired
    LineBillService lineBillService;
}
