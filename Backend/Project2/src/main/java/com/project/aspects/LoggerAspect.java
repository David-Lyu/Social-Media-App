//package com.project.aspects;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component("loggerAspect")
//@Aspect

//@EnableAspectJAutoProxy(proxyTargetClass=true)
//
//public class LoggerAspect 
//{
	
//	@AfterThrowing(pointcut="execution(* * (..))", throwing="exc")
//	public void afterSqlException(JoinPoint jp, SQLException exc) 
//	{
//		System.out.println("PANIC! A SQL EXCEPTION!");
//	}
//	
//	@AfterThrowing(pointcut="execution(* * (..))", throwing="exc")
//	public void afterValidationException(JoinPoint jp, SQLException exc) 
//	{
//		System.out.println("PANIC! A SQL EXCEPTION!");
//	}
//	
	//@AfterThrowing(pointcut="bean(*postDao*)", throwing="exc")
//	@AfterThrowing(pointcut = "bean(*)", throwing="exc")
//	public void afterAnyException(JoinPoint jp, Throwable exc) throws Throwable
//	{
//		System.out.println("PANIC! AN EXCEPTION! At Joinpoint: " + jp);
//		System.out.println(jp.getSignature());
//		System.out.println(exc.getMessage());
//		System.out.println(exc);
//	}
//
//	
//	@Before("execution(* *addPost* (..))")
//	public void testAspect(JoinPoint jp)
//	{
//		System.out.println();
//		System.out.println("Test Aspect before.");
//		System.out.println(jp.getClass());
//		System.out.println(jp.getSignature());
//		System.out.println(jp);
//		
//		System.out.println("---End--");
//	}
	
//	@Before("bean(*postServ*) || bean(*postDao*)")
//	public void testAspect(JoinPoint jp)
//	{
//		Logger loggy = Logger.getLogger(LoggerAspect.class);
//		
//		System.out.println();
//		System.out.println("Test Aspect before.");
//		System.out.println(jp.getClass());
//		System.out.println(jp.getSignature());
//		System.out.println(jp);
//		
//		loggy.info("-" + jp.getSignature()+ "-" + " Testing logger aspect!!!!!!!");
//		
//		System.out.println("---End--");
//	}
//}
