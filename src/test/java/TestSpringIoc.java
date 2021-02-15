import com.Jessie.OnlineAlbum.service.impl.userServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringIoc
{

    @Test
    public void run1()
    {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        userServiceImpl as = (userServiceImpl) ac.getBean("userService");
        as.findAllAccount();
        //Spring 测试成功！
    }
}
