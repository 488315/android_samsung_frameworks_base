package android.webkit;

import android.annotation.SystemApi;
import java.util.Set;

/* loaded from: classes4.dex */
public class GeolocationPermissions {

    public interface Callback {
        void invoke(String str, boolean z, boolean z2);
    }

    public void allow(String str) {
    }

    public void clear(String str) {
    }

    public void clearAll() {
    }

    public void getAllowed(String str, ValueCallback<Boolean> valueCallback) {
    }

    public void getOrigins(ValueCallback<Set<String>> valueCallback) {
    }

    public static GeolocationPermissions getInstance() {
        return WebViewFactory.getProvider().getGeolocationPermissions();
    }

    @SystemApi
    public GeolocationPermissions() {
    }
}
