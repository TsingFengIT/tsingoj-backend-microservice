package com.Tsing.tsingojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.Tsing.tsingojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.Tsing")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.Tsing.tsingojbackendserviceclient.service"})
public class TsingojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsingojBackendUserServiceApplication.class, args);
    }

}
