import com.Jessie.OnlineAlbum.dao.UserDAO;
import com.Jessie.OnlineAlbum.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMybatis
{

    @Test
    public void run2() throws IOException
    {
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建sqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = factory.openSession();
        UserDAO ad = session.getMapper(UserDAO.class);
        List<User> list = ad.findAll();
        for (User x : list)
        {
            System.out.println(x);
            //哇塞居然真的只要写个interface就好了？？？？
            //Mybatis yyds!！！！
        }
        //释放资源
        session.close();
        in.close();
    }
    @Test
    public void run3() throws IOException
    {
        User newUser = new User();
        newUser.setUsername("Lin");
        newUser.setPassword("123456");
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建sqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = factory.openSession();
        UserDAO ad = session.getMapper(UserDAO.class);
        ad.save(newUser);
        session.commit();//除了查询剩下操作都要提交更改
        session.close();
        in.close();
    }

    @Test
    public void run4()
    {

    }
}
