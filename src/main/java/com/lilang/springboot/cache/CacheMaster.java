package com.lilang.springboot.cache;

import com.lilang.springboot.cache.annotation.CacheEvit;
import com.lilang.springboot.cache.annotation.CachePut;
import com.lilang.springboot.cache.annotation.Param;
import com.lilang.springboot.cache.annotation.Response;
import com.lilang.springboot.localcache.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/6 ProjectName: springboot-test Version：5.0.0
 */
@Slf4j
@Aspect
@Component
public class CacheMaster {

    @Autowired
    private LocalCache localCache;

    @Around(value = "@annotation(com.lilang.springboot.cache.annotation.CachePut)")
    public Object cachePut(ProceedingJoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        CachePut cacheable = method.getAnnotation(CachePut.class);
        Map context = new HashMap();
        VariableResolverFactory functionFactory = new MapVariableResolverFactory(context);
        buildContext(jp, method, context);
        Serializable compileExpression = MVEL.compileExpression(cacheable.key());
        String key = (String) MVEL.executeExpression(compileExpression, context, functionFactory);
        Object cacheValue = localCache.getIfAbsent(key);
        if (cacheValue != null) {
            return cacheValue;
        }
        Object obj = null;
        try {
            obj = jp.proceed();
            if (obj != null) {
                localCache.put2(key, obj);
            }
        } catch (Throwable throwable) {
            log.error("cache put exception, e:", throwable);
        } finally {
            return obj;
        }

    }


    @AfterReturning(value = "@annotation(com.lilang.springboot.cache.annotation.CacheEvit)", returning = "result")
    public void cacheEvit(JoinPoint jp, Object result) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();

        CacheEvit cacheEvit = method.getAnnotation(CacheEvit.class);
        Response response = method.getAnnotation(Response.class);
        Map context = new HashMap();
        VariableResolverFactory functionFactory = new MapVariableResolverFactory(context);
        buildContext(jp, method, context);
        if (response != null) {
            context.put(response.value(), result);
        }
        Serializable compileExpression = MVEL.compileExpression(cacheEvit.key());
        String key = (String) MVEL.executeExpression(compileExpression, context, functionFactory);
        localCache.evit(key);

    }

    private void buildContext(JoinPoint pj, Method method, Map context) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Param) {
                    context.put(((Param) annotation).value(), pj.getArgs()[i]);
                }
            }
        }
    }

}
