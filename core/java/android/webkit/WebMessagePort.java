package android.webkit;

import android.annotation.SystemApi;
import android.os.Handler;

/* loaded from: classes4.dex */
public abstract class WebMessagePort {
    public abstract void close();

    public abstract void postMessage(WebMessage webMessage);

    public abstract void setWebMessageCallback(WebMessageCallback webMessageCallback);

    public abstract void setWebMessageCallback(
            WebMessageCallback webMessageCallback, Handler handler);

    public abstract static class WebMessageCallback {
        public void onMessage(WebMessagePort port, WebMessage message) {}
    }

    @SystemApi
    public WebMessagePort() {}
}
