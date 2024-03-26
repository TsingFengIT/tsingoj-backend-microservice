package com.Tsing.tsingojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */
@Slf4j
public class InitRabbitMq {

    public static void doInit() {
        try {

            // 1.建立连接
            ConnectionFactory factory = new ConnectionFactory();
            // 1.1.设置连接参数，分别是：主机名、端口号、vhost、用户名、密码
            factory.setHost("182.92.202.251");
            factory.setPort(5672);
            factory.setVirtualHost("/");
            factory.setUsername("admin");
            factory.setPassword("admin");
            // 1.2.建立连接
            Connection connection = factory.newConnection();

            // 2.创建通道Channel
            Channel channel = connection.createChannel();

            String EXCHANGE_NAME = "code_exchange";
            // 3.创建队列
            String queueName = "code_queue";

//        String queueName = "simple.queue1";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueDeclare(queueName, true, false, false, null);

//        channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "my_routingKey");
            log.info("消息队列启动成功");
        } catch (Exception e) {
            log.error("消息队列启动失败");
        }
    }

    public static void main(String[] args) {
        doInit();
//        System.out.println(test("1011"));
    }


    public static String test(String s) {
        char[] charArray = s.toCharArray();
        int zero = 0;
        for (int i = 0; i < charArray.length; i++) {

            if (charArray[i] == '0') {
                charArray[i] = '1';
                zero += 1;
            }
        }

        for (int i = s.length() - 2; i > s.length() - 2 - 3; --i) {
            charArray[i] = '0';
            System.out.println(i);
        }

        return String.valueOf(charArray);
    }
}
