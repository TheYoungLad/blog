package cn.xinglongfei.blog.aspect;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.Log;
import cn.xinglongfei.blog.po.User;
import cn.xinglongfei.blog.service.LogService;
import cn.xinglongfei.blog.util.RequestUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;


/**
 * 系统日志：切面处理类
 * Created by Phoenix on 2020/11/28
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 操作数据库
     */
    @Autowired
    private LogService sysLogService;



    /**
     * 我这里是使用log4j2把一些信息打印在控制台上面，可以不写
     */
    private static final Logger log = LogManager.getLogger(LogAspect.class);

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(cn.xinglongfei.blog.log.MyLog)")
    public void logPoinCut() {
    }


    //切面 配置通知
    @Before("logPoinCut()")         //AfterReturning
    public void saveOperation(JoinPoint joinPoint) {
        log.info("---------------接口日志记录---------------");
        //用于保存日志
        Log sysLog = new Log();

        // 客户端ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = RequestUtil.getIpAddr(request);
        sysLog.setIp(ip);

        //获取用户的浏览器版本以及操作系统版本
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String browser = userAgent.getBrowser().getName();
        String os = userAgent.getOperatingSystem().getName();
        String osTemp = os.toUpperCase();
        sysLog.setBrowser(browser);
        sysLog.setSystem(os);
        log.info("Browser=" + browser, "OperatingSystem=" + os);

        //只记录真实用户的IP，过滤爬虫（Robot）的日志记录，爬虫的系统为Unkown，虽然工具类有部分系统不能识别，但是不能识别的部分很少很少
        if (!osTemp.equals("UNKNOWN")) {

            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();


            //获取操作--方法上的Log的值
            MyLog myLog = method.getAnnotation(MyLog.class);
            if (myLog != null) {
                //保存操作事件
                String operation = myLog.operation();
                sysLog.setOperation(operation);

                //保存日志类型
                String type = myLog.type();
                sysLog.setType(type);

                log.info("operation=" + operation + ",type=" + type);
            }

            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();
            sysLog.setMethod(className + "." + methodName);
            log.info("methodName=" + methodName, "ip=" + ip);

            //请求的参数
            Object[] argsTemp = joinPoint.getArgs();
            sysLog.setArgs(Arrays.toString(argsTemp));


            // 操作人账号、姓名（需要提前将用户信息存到session）
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                Long userId = user.getId();
                String userName = user.getUsername();
                sysLog.setUserId(userId);
                sysLog.setUsername(userName);
                log.info("userId=" + userId, "userName=" + userName);
            }
            //设置时间
            sysLog.setOperaTime(new Date());

            //调用service保存SysLog实体类到数据库
            sysLogService.saveLog(sysLog);

        }


    }

}
