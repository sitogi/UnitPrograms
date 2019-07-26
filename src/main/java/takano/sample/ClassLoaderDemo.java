package takano.sample;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.concurrent.TimeUnit;

public class ClassLoaderDemo {

    public static void main(String[] args) throws Exception {
        異なるクラスローダを使用_同じFQCNのクラスでも別物のクラスとして扱える();
        同じクラスローダを使用_最初のforNameの時しかstaticイニシャライザは動かない();
        インスタンス化なしでも一緒();
    }

    private static void 異なるクラスローダを使用_同じFQCNのクラスでも別物のクラスとして扱える() throws Exception {
        Driver driver;
        Driver driver2;

        {
            final URL[] urls = { new File("./libs/my_jdbc_driver.jar").toURI().toURL() };
            final URLClassLoader loader = new URLClassLoader(urls);
            driver = (Driver) Class.forName("takano.jdbc.DummyJDBCDriver", false, loader).newInstance();
        }

        TimeUnit.SECONDS.sleep(2);

        {
            final URL[] urls = { new File("./libs/my_jdbc_driver.jar").toURI().toURL() };
            final URLClassLoader loader = new URLClassLoader(urls);
            driver2 = (Driver) Class.forName("takano.jdbc.DummyJDBCDriver", false, loader).newInstance();
        }

        // ローダーが異なると、同じクラスに見えるけど実は違うクラスを参照できる
        System.out.println(driver.getMajorVersion());
        System.out.println(driver2.getMajorVersion());
    }

    private static void 同じクラスローダを使用_最初のforNameの時しかstaticイニシャライザは動かない() throws Exception {
        Driver driver;
        Driver driver2;
        final URL[] urls = { new File("./libs/my_jdbc_driver.jar").toURI().toURL() };
        final URLClassLoader loader = new URLClassLoader(urls);

        {
            driver = (Driver) Class.forName("takano.jdbc.DummyJDBCDriver", true, loader).newInstance();
        }

        TimeUnit.SECONDS.sleep(2);

        {
            driver2 = (Driver) Class.forName("takano.jdbc.DummyJDBCDriver", true, loader).newInstance();
        }

        System.out.println(driver.getMajorVersion());
        System.out.println(driver2.getMajorVersion());
    }

    private static void インスタンス化なしでも一緒() throws Exception {
        final URL[] urls = { new File("./libs/my_jdbc_driver.jar").toURI().toURL() };
        final URLClassLoader loader = new URLClassLoader(urls);

        {
            Class.forName("takano.jdbc.DummyJDBCDriver", true, loader);
        }

        TimeUnit.SECONDS.sleep(2);

        {
            Class.forName("takano.jdbc.DummyJDBCDriver", true, loader); // これだと動かない！！！
        }
    }

}
