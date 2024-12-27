package android.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.OutcomeReceiver;

public class FullscreenRequestHandler {
    public static final String REMOTE_CALLBACK_RESULT_KEY = "result";
    public static final int RESULT_APPROVED = 0;
    public static final int RESULT_FAILED_NOT_IN_FULLSCREEN_WITH_HISTORY = 1;
    public static final int RESULT_FAILED_NOT_TOP_FOCUSED = 2;

    public @interface RequestResult {}

    static void requestFullscreenMode(
            int request,
            final OutcomeReceiver<Void, Throwable> approvalCallback,
            Configuration config,
            IBinder token) {
        int earlyCheck =
                earlyCheckRequestMatchesWindowingMode(
                        request, config.windowConfiguration.getWindowingMode());
        if (earlyCheck != 0) {
            if (approvalCallback != null) {
                notifyFullscreenRequestResult(approvalCallback, earlyCheck);
                return;
            }
            return;
        }
        try {
            if (approvalCallback != null) {
                ActivityClient.getInstance()
                        .requestMultiwindowFullscreen(
                                token,
                                request,
                                new IRemoteCallback.Stub() { // from class:
                                    // android.app.FullscreenRequestHandler.1
                                    @Override // android.os.IRemoteCallback
                                    public void sendResult(Bundle res) {
                                        FullscreenRequestHandler.notifyFullscreenRequestResult(
                                                OutcomeReceiver.this, res.getInt("result"));
                                    }
                                });
            } else {
                ActivityClient.getInstance().requestMultiwindowFullscreen(token, request, null);
            }
        } catch (Throwable e) {
            if (approvalCallback != null) {
                approvalCallback.onError(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyFullscreenRequestResult(
            OutcomeReceiver<Void, Throwable> callback, int result) {
        Throwable e = null;
        switch (result) {
            case 1:
                e =
                        new IllegalStateException(
                                "The window is not in fullscreen by calling the"
                                        + " requestFullscreenMode API before, such that cannot be"
                                        + " restored.");
                break;
            case 2:
                e = new IllegalStateException("The window is not the top focused window.");
                break;
            default:
                callback.onResult(null);
                break;
        }
        if (e != null) {
            callback.onError(e);
        }
    }

    private static int earlyCheckRequestMatchesWindowingMode(int request, int windowingMode) {
        if (request == 0 && windowingMode != 1) {
            return 1;
        }
        return 0;
    }
}
