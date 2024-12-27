package android.webkit;

import android.annotation.SystemApi;
import android.os.Handler;

/* loaded from: classes4.dex */
public class HttpAuthHandler extends Handler {
    @SystemApi
    public HttpAuthHandler() {
    }

    public boolean useHttpAuthUsernamePassword() {
        return false;
    }

    public void cancel() {
    }

    public void proceed(String username, String password) {
    }
}
