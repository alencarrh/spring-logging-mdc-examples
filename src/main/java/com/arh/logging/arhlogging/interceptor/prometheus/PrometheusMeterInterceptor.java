package com.arh.logging.arhlogging.interceptor.prometheus;

import com.arh.logging.arhlogging.annotation.PrometheusMeter;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * https://stackoverflow.com/questions/12249721/spring-mvc-3-how-to-get-path-variable-in-an-interceptor
 */

@Slf4j
@Aspect
@Component
public class PrometheusMeterInterceptor {

  @Pointcut("@annotation(com.arh.logging.arhlogging.annotation.PrometheusMeter)")
  public void withAnnotationPrometheusMeter() {}

  @Pointcut("execution(* com.arh.logging.arhlogging.web..*Controller.*(..))")
  public void anyControllerMethod() {}

  @Around("anyControllerMethod() && withAnnotationPrometheusMeter()")
  public Object intercept(final ProceedingJoinPoint joinPoint) throws Throwable {
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    PrometheusMeter prometheusMeter = method.getAnnotation(PrometheusMeter.class);
    GetMapping getMapping = method.getAnnotation(GetMapping.class);

    try {
      return joinPoint.proceed();
    } finally {
      System.out.println(Arrays.toString(getMapping.value()));
      increment(1);
    }
  }

  public void increment(int count) {
    System.out.println("qawqweqwe");
  }
}
