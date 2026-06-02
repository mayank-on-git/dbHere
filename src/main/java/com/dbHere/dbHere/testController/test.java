package com.dbHere.dbHere.testController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @GetMapping("/hello")
        public String test(){
            return"welcome to dbHere";
        }



    @GetMapping("/change")
    public String change(){
        return"welcome to change";
    }

    @GetMapping("/step2")
    public String step2(){
        return"welcome to step2";
    }

    @GetMapping("/step1")
    public String step1(){
        return"welcome to step1";
    }
    }

