package com.example.demo.controller;

import com.example.demo.exception.LineBillException;
import com.example.demo.service.DateService;
import com.example.demo.service.LineBillService;
import com.example.demo.vo.LineBill;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@Getter
@RequestMapping("/linebill")
@RestController
public class LineBillController {
    @Autowired
    LineBillService lineBillService;
    @Autowired
    DateService dateService;


    @GetMapping("/new")
    public LineBill createNewLineBill(@RequestParam Float amount,
                                      @RequestParam (required = false) Float tvaPercent,
                                      @RequestParam (required = false) Float tva,
                                      @RequestParam String date,
                                      @RequestParam String country) throws LineBillException {

        LineBill l = this.lineBillService.saveLineBill(LineBill.builder()
                .amount(amount)
                .tvaPercent(tvaPercent)
                .tva(tva)
                .date(dateService.parseDate(date))
                .country(country)
                .build());
        return l;
    }

    @GetMapping("/delete")
    public HttpStatus deleteLineBill(int id) throws LineBillException {

        return this.lineBillService.deleteLineBill(id);
    }


}
