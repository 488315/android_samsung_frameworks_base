package android.webkit;

import android.annotation.SystemApi;
import android.os.Handler;

public class HttpAuthHandler extends Handler {
    @SystemApi
    public HttpAuthHandler() {}

    public boolean useHttpAuthUsernamePassword() {
        return false;
    }

    public void cancel() {}

    public void proceed(String username, String password) {}
}
