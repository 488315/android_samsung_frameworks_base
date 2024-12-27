package com.android.server.chimera.umr;

import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ForegroundAppTracker {
    public static final int DELAYED_RESET_APP_LAUNCH_MS =
            SystemProperties.getInt("sys.config.amp_to_app_switch", 4000);
    public static final int DELAYED_RESET_CAMERA_LAUNCH_MS =
            SystemProperties.getInt("sys.config.amp_to_app_switch", 5000);
    public static final boolean IS_DEBUG_ENABLED =
            SystemProperties.getBoolean("debug.chimera.fgtracker", false);
    public static ForegroundMonitor mForegroundMonitor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ForegroundAppMsgHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 2) {
                    ForegroundAppTracker.getForegroundMonitor().setAppLaunch(false);
                } else if (i == 3) {
                    ForegroundAppTracker.getForegroundMonitor().getClass();
                    ForegroundMonitor.setCameraLaunch(false);
                } else if (ForegroundAppTracker.IS_DEBUG_ENABLED) {
                    Slog.d("ForegroundAppTracker", "ForegroundAppMsgHandler: unhandled case");
                }
            } catch (Exception e) {
                Slog.e(
                        "ForegroundAppTracker",
                        "ForegroundAppMsgHandler: failed to handleMessage " + message.what);
                e.printStackTrace();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ForegroundMonitor {
        public static ForegroundAppMsgHandler mMsgHandler;
        public boolean mAppLaunch;

        public static void setCameraLaunch(boolean z) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "cameraLaunch = ", "ForegroundAppTracker", z);
            if (z) {
                ForegroundAppMsgHandler foregroundAppMsgHandler = mMsgHandler;
                int i = ForegroundAppTracker.DELAYED_RESET_CAMERA_LAUNCH_MS;
                if (foregroundAppMsgHandler == null) {
                    return;
                }
                foregroundAppMsgHandler.removeMessages(3);
                Message obtainMessage = foregroundAppMsgHandler.obtainMessage(3);
                if (i > 0) {
                    foregroundAppMsgHandler.sendMessageDelayed(obtainMessage, i);
                } else {
                    foregroundAppMsgHandler.sendMessage(obtainMessage);
                }
            }
        }

        public final void finalize() {
            try {
                ForegroundAppMsgHandler foregroundAppMsgHandler = mMsgHandler;
                if (foregroundAppMsgHandler != null) {
                    foregroundAppMsgHandler.removeCallbacksAndMessages(null);
                    mMsgHandler.getLooper().quit();
                    mMsgHandler = null;
                }
            } finally {
                super.finalize();
            }
        }

        public final void setAppLaunch(boolean z) {
            if (ForegroundAppTracker.IS_DEBUG_ENABLED) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "appLaunch = ", "ForegroundAppTracker", z);
            }
            this.mAppLaunch = z;
            if (z) {
                ForegroundAppMsgHandler foregroundAppMsgHandler = mMsgHandler;
                int i = ForegroundAppTracker.DELAYED_RESET_APP_LAUNCH_MS;
                if (foregroundAppMsgHandler == null) {
                    return;
                }
                foregroundAppMsgHandler.removeMessages(2);
                Message obtainMessage = foregroundAppMsgHandler.obtainMessage(2);
                if (i > 0) {
                    foregroundAppMsgHandler.sendMessageDelayed(obtainMessage, i);
                } else {
                    foregroundAppMsgHandler.sendMessage(obtainMessage);
                }
            }
        }
    }

    public static ForegroundMonitor getForegroundMonitor() {
        if (mForegroundMonitor == null) {
            ForegroundMonitor foregroundMonitor = new ForegroundMonitor();
            if (ForegroundMonitor.mMsgHandler == null) {
                ForegroundMonitor.mMsgHandler =
                        new ForegroundAppMsgHandler(
                                Watchdog$$ExternalSyntheticOutline0.m(
                                                10, "UMR_FOREGROUND_APP_TRACKER", true)
                                        .getLooper(),
                                null,
                                true);
            }
            foregroundMonitor.mAppLaunch = false;
            mForegroundMonitor = foregroundMonitor;
        }
        return mForegroundMonitor;
    }
}
