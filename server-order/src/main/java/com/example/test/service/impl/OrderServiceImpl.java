package com.example.test.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fescar.core.context.RootContext;
import com.example.test.service.OrderService;
import com.example.test.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author 侯存路
 * @date 2019/1/11
 * @company codingApi
 * @description
 */
@Service(interfaceClass = OrderService.class)
@Component
public class OrderServiceImpl implements OrderService {




    @Reference
    PayService  payService;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrder(String userName) {
        System.out.println("----->"+RootContext.getXID());
        aa(userName);

        payService.addPay(userName);

        if(StringUtils.isEmpty(userName)){
            throw  new NullPointerException("pay 异常");
        }
        return true;
    }


    private  void aa(String userName){
        jdbcTemplate.update("INSERT INTO `t_order` (  `user_name`) VALUES ( ? ) ; " , userName );

    }



}
