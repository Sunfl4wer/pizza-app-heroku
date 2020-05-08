package com.pycogroup.pizza.product.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class ActionAspect {

  private static Logger logger = LoggerFactory.getLogger(ActionAspect.class);

  private String getParameterNames(ProceedingJoinPoint joinPoint) {
    CodeSignature codeSig = (CodeSignature)joinPoint.getSignature();
    return String.join(", ", codeSig.getParameterNames());
  }

  @Around("@annotation(LogExecutionStatus)")
  public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    
    String methodName = joinPoint.getSignature().getName();
    Object proceed = null;
    try {
      logger.info("Start method '{}' with arguments {}",methodName, this.getParameterNames(joinPoint));
      proceed = joinPoint.proceed();
      logger.info("End method '{}'", methodName);
    } catch (Throwable ex) {
      logger.error("Fail to execute '{}' {}", methodName, ex);
    }
    return proceed;
  }
}
