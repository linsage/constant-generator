package xyz.linsage;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import xyz.linsage.common.ConfigLoader;
import xyz.linsage.model.Constant;


import java.awt.*;
import java.net.URI;
import java.util.Properties;

/**
 * 启动类
 *
 * @author linsage
 * @create 2017-01-30  下午4:41
 */
public class WebConfig extends JFinalConfig {
    private static final int PORT = 8085;
    private static final String BASE_URL = "http://localhost:" + PORT;
    private static final String WEB_APP = "src/main/webapp";
    private static final String CONTEXT_PATH = "/";

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        WebAppContext context = new WebAppContext(WEB_APP, CONTEXT_PATH);
        server.setHandler(context);
        server.start();
        //打开浏览器
        System.out.println(BASE_URL);
        Desktop.getDesktop().browse(new URI(BASE_URL));
        server.join();
    }

    /**
     * 配置常量
     *
     * @param constants
     */
    @Override
    public void configConstant(Constants constants) {
        // 加载少量必要配置，随后可用getProperty(...)获取值
        Properties properties = loadPropertyFile("config.properties");
        constants.setDevMode(getPropertyToBoolean("devMode", true));

        ConfigLoader configLoader = new ConfigLoader();
        configLoader.load(Constant.class,properties);

        System.out.println("ok");
        //全局变量
//        Constant.qiniu.accessKey = getProperty("qiuniu.accessKey");
//        Constant.qiniu.secretKey = getProperty("qiuniu.secretKey");
//        Constant.qiniu.separator = getProperty("qiuniu.separator");
//        Constant.qiniu.bucket = getProperty("qiuniu.bucket");
//        Constant.qiniu.domain = getProperty("qiuniu.domain");
//        Constant.qiniu.ins.bucket = getProperty("qiuniu.ins.bucket");
//        Constant.qiniu.ins.domain = getProperty("qiuniu.ins.domain");
//        Constant.httpProxy.ip=getProperty("httpProxy.ip");
//        Constant.httpProxy.port=getPropertyToInt("httpProxy.port");
    }

    /**
     * 配置路由
     *
     * @param routes
     */
    public void configRoute(Routes routes) {

    }

    @Override
    public void configEngine(Engine me) {

    }

    /**
     * 配置插件
     *
     * @param plugins
     */
    @Override
    public void configPlugin(Plugins plugins) {

    }

    /**
     * 配置全局拦截器
     *
     * @param interceptors
     */
    @Override
    public void configInterceptor(Interceptors interceptors) {
    }

    /**
     * 配置处理器
     *
     * @param handlers
     */
    @Override
    public void configHandler(Handlers handlers) {
        DruidStatViewHandler dvh = new DruidStatViewHandler(getProperty("druid.visitPath"));
        handlers.add(dvh);
    }
}
