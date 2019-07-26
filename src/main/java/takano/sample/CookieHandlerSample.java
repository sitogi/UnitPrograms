package takano.sample;

import java.net.CookieHandler;

public class CookieHandlerSample {

    public static void main(String[] args) {
        final CookieHandler dch = CookieHandler.getDefault();

        System.out.println(dch);
    }

}
