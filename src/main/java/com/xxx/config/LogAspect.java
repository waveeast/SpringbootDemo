package com.xxx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Aspect
@Configuration
public class LogAspect {

    public static Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMappingPointCut() {}
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMappingPointCut() {}
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMappingPointCut() {}

    @Around("postMappingPointCut() || getMappingPointCut() || requestMappingPointCut()")
    public Object logControllerInAndOut(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        String signatureStr = signature.toString();
        Object[] args = pjp.getArgs();
        List<Object> bizArgs = new LinkedList<>();
        for (Object obj: args) {
            if (!(obj instanceof HttpServletRequest) && !(obj instanceof HttpServletResponse)) {
                bizArgs.add(obj);
            }
        }
        Object result = null;
        try {
            LOGGER.info("method={}||param={}", signatureStr, OBJECT_MAPPER.writeValueAsString(bizArgs));
            result = pjp.proceed();
            LOGGER.info("method={}||result={}", signatureStr, OBJECT_MAPPER.writeValueAsString(result));
        } catch (Exception e) {
            LOGGER.error("method={}||error={}", signatureStr, e.getMessage());
            throw e;
        }

        return result;
    }

    @Around("execution(* com.xxx.service.*.*(..))")
    public Object logServiceInAndOut(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        String signatureStr = signature.toString();
        Object[] args = pjp.getArgs();
        Object result = null;

        try {
            LOGGER.info("method={}||param={}", signatureStr, OBJECT_MAPPER.writeValueAsString(args));
            result = pjp.proceed();
            LOGGER.info("method={}||result={}", signatureStr, OBJECT_MAPPER.writeValueAsString(result));
        } catch (Exception e) {
            LOGGER.error("method={}||error={}", signatureStr, e.getMessage());
            throw e;
        }
        return result;
    }
}
