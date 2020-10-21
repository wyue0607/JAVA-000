import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author mundo.wang on 2020/10/21
 */
public class HelloClassLoader extends ClassLoader {

    private static final String FILE_NAME = "./Week_01/src/Hello.xlass";

    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassLoader().findClass("Hello");
            Method helloMethod = clazz.getMethod("hello");
            helloMethod.invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] content = getContent();

        for (int i = 0; i < content.length; i++) {
            content[i] = (byte) (255 - content[i]);
        }
        return defineClass(name, content, 0, content.length);
    }

    private byte[] getContent() {
        File classFile = new File(FILE_NAME);
        long length = classFile.length();
        byte[] bytes = new byte[(int) length];
        try (InputStream inputStream = new FileInputStream(classFile)) {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}