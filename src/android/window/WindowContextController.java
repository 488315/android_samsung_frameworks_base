package android.window;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class WindowContextController {
    private static final boolean DEBUG_ATTACH = false;
    private static final String TAG = "WindowContextController";
    public int mAttachedToDisplayArea = 0;
    private final WindowTokenClient mToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttachStatus {
        public static final int STATUS_ATTACHED = 1;
        public static final int STATUS_DETACHED = 2;
        public static final int STATUS_FAILED = 3;
        public static final int STATUS_INITIALIZED = 0;
    }

    public WindowContextController(WindowTokenClient windowTokenClient) {
        this.mToken = windowTokenClient;
    }

    public void attachToDisplayArea(int i, int i2, Bundle bundle) {
        if (this.mAttachedToDisplayArea == 1) {
            throw new IllegalStateException("A Window Context can be only attached to a DisplayArea once.");
        }
        int i3 = getWindowTokenClientController().attachToDisplayArea(this.mToken, i, i2, bundle) ? 1 : 3;
        this.mAttachedToDisplayArea = i3;
        if (i3 == 3) {
            Log.w(TAG, "attachToDisplayArea fail, type:" + i + ", displayId:" + i2);
        }
    }

    public void attachToWindowToken(IBinder iBinder) {
        if (this.mAttachedToDisplayArea != 1) {
            throw new IllegalStateException("The Window Context should have been attached to a DisplayArea. AttachToDisplayArea:" + this.mAttachedToDisplayArea);
        }
        if (getWindowTokenClientController().attachToWindowToken(this.mToken, iBinder)) {
            return;
        }
        Log.e(TAG, "attachToWindowToken fail");
    }

    public void detachIfNeeded() {
        if (this.mAttachedToDisplayArea == 1) {
            getWindowTokenClientController().detachIfNeeded(this.mToken);
            this.mAttachedToDisplayArea = 2;
        }
    }

    public void reparentToDisplayArea(int i, int i2, Bundle bundle) {
        if (this.mAttachedToDisplayArea != 1) {
            attachToDisplayArea(i, i2, bundle);
        } else {
            getWindowTokenClientController().reparentToDisplayArea(this.mToken, i2);
        }
    }

    public WindowTokenClientController getWindowTokenClientController() {
        return WindowTokenClientController.getInstance();
    }
}
