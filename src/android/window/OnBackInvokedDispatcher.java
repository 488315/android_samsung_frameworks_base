package android.window;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public interface OnBackInvokedDispatcher {
    public static final boolean DEBUG = false;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_OVERLAY = 1000000;
    public static final int PRIORITY_SYSTEM = -1;
    public static final int PRIORITY_SYSTEM_NAVIGATION_OBSERVER = -2;
    public static final String TAG = "OnBackInvokedDispatcher";

    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    void registerOnBackInvokedCallback(int i, OnBackInvokedCallback onBackInvokedCallback);

    default void registerSystemOnBackInvokedCallback(OnBackInvokedCallback onBackInvokedCallback) {
    }

    default void setImeOnBackInvokedDispatcher(ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
    }

    void unregisterOnBackInvokedCallback(OnBackInvokedCallback onBackInvokedCallback);
}
