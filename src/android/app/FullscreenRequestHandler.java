package android.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.OutcomeReceiver;
import android.window.DesktopModeFlags;

/* loaded from: classes.dex */
public class FullscreenRequestHandler {
    public static final String REMOTE_CALLBACK_RESULT_KEY = "result";
    public static final int RESULT_APPROVED = 0;
    public static final int RESULT_FAILED_ALREADY_FULLY_EXPANDED = 3;
    public static final int RESULT_FAILED_NOT_IN_FULLSCREEN_WITH_HISTORY = 1;
    public static final int RESULT_FAILED_NOT_TOP_FOCUSED = 2;

    public @interface RequestResult {
    }

    static void requestFullscreenMode(int i, final OutcomeReceiver<Void, Throwable> outcomeReceiver, Configuration configuration, IBinder iBinder) {
        int iEarlyCheckRequestMatchesWindowingMode = earlyCheckRequestMatchesWindowingMode(i, configuration.windowConfiguration.getWindowingMode());
        if (iEarlyCheckRequestMatchesWindowingMode != 0) {
            if (outcomeReceiver != null) {
                notifyFullscreenRequestResult(outcomeReceiver, iEarlyCheckRequestMatchesWindowingMode);
                return;
            }
            return;
        }
        try {
            if (outcomeReceiver != null) {
                ActivityClient.getInstance().requestMultiwindowFullscreen(iBinder, i, new IRemoteCallback.Stub() { // from class: android.app.FullscreenRequestHandler.1
                    @Override // android.os.IRemoteCallback
                    public void sendResult(Bundle bundle) {
                        FullscreenRequestHandler.notifyFullscreenRequestResult(outcomeReceiver, bundle.getInt("result"));
                    }
                });
            } else {
                ActivityClient.getInstance().requestMultiwindowFullscreen(iBinder, i, null);
            }
        } catch (Throwable th) {
            if (outcomeReceiver != null) {
                outcomeReceiver.onError(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyFullscreenRequestResult(OutcomeReceiver<Void, Throwable> outcomeReceiver, int i) {
        IllegalStateException illegalStateException;
        if (i == 1) {
            illegalStateException = new IllegalStateException("The window is not in fullscreen by calling the requestFullscreenMode API before, such that cannot be restored.");
        } else if (i == 2) {
            illegalStateException = new IllegalStateException("The window is not the top focused window.");
        } else if (i == 3) {
            illegalStateException = new IllegalStateException("The window is already fully expanded.");
        } else {
            illegalStateException = null;
            outcomeReceiver.onResult(null);
        }
        if (illegalStateException != null) {
            outcomeReceiver.onError(illegalStateException);
        }
    }

    private static int earlyCheckRequestMatchesWindowingMode(int i, int i2) {
        return i == 0 ? i2 != 1 ? 1 : 0 : (DesktopModeFlags.ENABLE_REQUEST_FULLSCREEN_BUGFIX.isTrue() && (i2 == 1 || i2 == 6)) ? 3 : 0;
    }
}
