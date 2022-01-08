//package com.yx.myblog.handler;/*
//    @auther
//    @create ---
//*/
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
///*
//@ControllerAdvice：拦截带有@Controller注解的请求
// */
//@ControllerAdvice
//public class ExceptionHandlerController {
//
//    private final Logger logger= LoggerFactory.getLogger(this.getClass());
//
//    /*
//     @ExceptionHandler:异常处理注解
//     */
//    @ExceptionHandler(Exception.class)
//    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
//
//        logger.error("Request URL:{},Exception :{}",request.getRequestURL(),e);
//
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
//            throw e;
//        }
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("url",request.getRequestURL());
//        mv.addObject("exception",e);
//        mv.setViewName("error/error");
//        return mv;
//    }
//
//}
