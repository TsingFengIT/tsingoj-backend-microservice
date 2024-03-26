package com.Tsing.tsingojbackendgateway.filter;

import com.Tsing.tsingojbackendcommon.constant.JwtClaimsConstant;
import com.Tsing.tsingojbackendcommon.context.BaseContext;
import com.Tsing.tsingojbackendcommon.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String path = serverHttpRequest.getURI().getPath();
        //未登录可以查看题目
        if(antPathMatcher.match("/**/api/question/list/page/vo/**", path)){
            return chain.filter(exchange);
        }

        if(antPathMatcher.match("/**/login/**", path) || antPathMatcher.match("/**/logout/**", path) || antPathMatcher.match("/**/register/**", path) ){
            return chain.filter(exchange);
        }

        // 判断路径中是否包含 inner，只允许内部调用
        if (antPathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }
        //  统一权限校验，通过 JWT 获取登录用户信息
        //1、从请求头中获取令牌
        HttpHeaders headers = serverHttpRequest.getHeaders();
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);

// 处理 token 值
        if (token != null && token.startsWith("Bearer ")) {
            // 提取出实际的 token 值
            token = token.substring(7);
        }
//
//        //2、校验令牌
        ServerHttpResponse response = exchange.getResponse();
//        if (antPathMatcher.match("/**/login/**", path)) {


        try {

            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT("tsingoj", token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("当前管理员id：{} 访问Path：{}", empId,path);

            BaseContext.setCurrentId(empId);

            //3、通过，放行
        } catch (Exception ex) {
            //4、不通过，
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("请先登录".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }
//        }
        return chain.filter(exchange);
////
    }

    /**
     * 优先级提到最高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    // 提取出 token 值的方法

    //    headers.getFirst(HttpHeaders.AUTHORIZATION);
    //        List<String> cookies = headers.get(HttpHeaders.COOKIE);
//        String token = "";
//        if(cookies != null){
//            for (String cookie : cookies) {
//
//                if (cookie.contains("token")) {
//                    // 提取出 token 值
//                    token = extractTokenFromCookie(cookie);
//                    // 现在你可以使用 token 进行后续操作
//                    break;
//                }
//            }
//        }
    private String extractTokenFromCookie(String cookie) {
        // 假设 token 在 Cookie 中的格式是 "token=<token-value>;"
        int startIndex = cookie.indexOf("token=");
        if (startIndex != -1) {
            startIndex += 6; // 跳过 "token="
            int endIndex = cookie.indexOf(";", startIndex);
            if (endIndex == -1) {
                endIndex = cookie.length();
            }
            return cookie.substring(startIndex, endIndex);
        }
        return null;
    }

}
