package com.itheima.yyeats.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @param
 * @return
 */

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert...common part");
        log.info(metaObject.toString());
        if (metaObject.hasSetter("createTime")) {
            metaObject.setValue("createTime", Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        }

//        metaObject.setValue("createTime", Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//        metaObject.setValue("updateTime",Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        }
        if(metaObject.hasSetter("createUser")){
            metaObject.setValue("createUser", BaseContext.getCurrentId());

        }
//        metaObject.setValue("createUser", BaseContext.getCurrentId());
        if(metaObject.hasSetter("updateUser")){
            metaObject.setValue("updateUser", BaseContext.getCurrentId());
        }
//        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("update...common part");
        log.info(metaObject.toString());


        long id = Thread.currentThread().getId();
        log.info("thread id:{}",id);
        metaObject.setValue("updateTime",Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
