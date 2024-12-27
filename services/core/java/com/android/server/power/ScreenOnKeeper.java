package com.android.server.power;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.SystemClock;
import android.provider.Settings;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;

import java.io.PrintWriter;
import java.util.function.IntSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ScreenOnKeeper {
    public final VcnManagementService$$ExternalSyntheticLambda10 mClock;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsScreenOnKeeperEnabled;
    public final long mKeepScreenOnDuration;
    public long mLastScreenTouchTime;
    public final Object mLock;
    public SettingsObserver mSettingsObserver;
    public final SystemPropertiesWrapper mSystemProperties;
    public final long mTouchOutCheckDuration;
    public final PowerManager.WakeLock mWakeLock;
    public final IntSupplier mWakeLockSummarySupplier;
    public int mWakefulness;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenOnKeeperHandlerCallback implements Handler.Callback {
        public ScreenOnKeeperHandlerCallback() {}

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ScreenOnKeeper screenOnKeeper = ScreenOnKeeper.this;
                synchronized (screenOnKeeper.mLock) {
                    try {
                        screenOnKeeper.mClock.getClass();
                        long uptimeMillis = SystemClock.uptimeMillis();
                        long j =
                                screenOnKeeper.mLastScreenTouchTime
                                        + screenOnKeeper.mTouchOutCheckDuration;
                        if (j <= uptimeMillis) {
                            screenOnKeeper.notifyKeepScreenOnExpiredLocked();
                        } else {
                            Slog.d(
                                    "ScreenOnKeeper",
                                    "checkKeepScreenOnDuration: wait next validation time");
                            screenOnKeeper.mHandler.sendMessageAtTime(
                                    screenOnKeeper.mHandler.obtainMessage(1), j);
                        }
                    } finally {
                    }
                }
            } else if (i == 2) {
                ScreenOnKeeper screenOnKeeper2 = ScreenOnKeeper.this;
                synchronized (screenOnKeeper2.mLock) {
                    Slog.d("ScreenOnKeeper", "handleKeepScreenOnTimeout()");
                    screenOnKeeper2.disableScreenOnKeeper();
                }
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            Slog.d("ScreenOnKeeper", "[api] SettingsObserver: onChange: " + uri);
            synchronized (ScreenOnKeeper.this.mLock) {
                ScreenOnKeeper.m825$$Nest$mhandleSettingsChangedLocked(ScreenOnKeeper.this);
            }
        }
    }

    /* renamed from: -$$Nest$mhandleSettingsChangedLocked, reason: not valid java name */
    public static void m825$$Nest$mhandleSettingsChangedLocked(ScreenOnKeeper screenOnKeeper) {
        int i;
        screenOnKeeper.getClass();
        try {
            i =
                    Settings.Global.getInt(
                            screenOnKeeper.mContext.getContentResolver(), "screen_on_keeper");
        } catch (Settings.SettingNotFoundException e) {
            int i2 = Slog.$r8$clinit;
            if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
                Slog.addPMSLogList(
                        "D ScreenOnKeeper: handleSettingsChangedLocked: SettingNotFoundException=");
            }
            android.util.Slog.d(
                    "ScreenOnKeeper", "handleSettingsChangedLocked: SettingNotFoundException=", e);
            i = 0;
        }
        Slog.d("ScreenOnKeeper", "handleSettingsChangedLocked: screenOnKeeperSettingValue=" + i);
        boolean z = i > 0;
        screenOnKeeper.mIsScreenOnKeeperEnabled = z;
        if (z && !PowerManagerInternal.isInteractive(screenOnKeeper.mWakefulness)) {
            Slog.d(
                    "ScreenOnKeeper",
                    "handleSettingsChangedLocked: disable keep screen on mWakefulness="
                            + PowerManagerInternal.wakefulnessToString(
                                    screenOnKeeper.mWakefulness));
            screenOnKeeper.disableScreenOnKeeper();
            screenOnKeeper.notifyScreenOnKeeperDisabledLocked(0);
            return;
        }
        Handler handler = screenOnKeeper.mHandler;
        handler.removeMessages(2);
        if (!screenOnKeeper.mIsScreenOnKeeperEnabled) {
            Slog.d("ScreenOnKeeper", "handleSettingsChangedLocked: disable keep screen on");
            if (screenOnKeeper.mWakeLock.isHeld()) {
                screenOnKeeper.mWakeLock.release();
            }
            handler.removeMessages(1);
            return;
        }
        Slog.d("ScreenOnKeeper", "handleSettingsChangedLocked: enable/extend keep screen on");
        if (!screenOnKeeper.mWakeLock.isHeld()) {
            screenOnKeeper.mWakeLock.acquire();
        }
        Message obtainMessage = handler.obtainMessage(1);
        screenOnKeeper.mClock.getClass();
        handler.sendMessageAtTime(
                obtainMessage, SystemClock.uptimeMillis() + screenOnKeeper.mKeepScreenOnDuration);
    }

    public ScreenOnKeeper(
            Context context,
            Object obj,
            Looper looper,
            int i,
            PowerManagerService$$ExternalSyntheticLambda16
                    powerManagerService$$ExternalSyntheticLambda16,
            SystemPropertiesWrapper systemPropertiesWrapper) {
        VcnManagementService$$ExternalSyntheticLambda10
                vcnManagementService$$ExternalSyntheticLambda10 =
                        new VcnManagementService$$ExternalSyntheticLambda10();
        this.mContext = context;
        this.mLock = obj;
        this.mHandler = new Handler(looper, new ScreenOnKeeperHandlerCallback());
        this.mClock = vcnManagementService$$ExternalSyntheticLambda10;
        this.mWakefulness = i;
        this.mWakeLockSummarySupplier = powerManagerService$$ExternalSyntheticLambda16;
        this.mSystemProperties = systemPropertiesWrapper;
        this.mWakeLock =
                ((PowerManager) context.getSystemService("power"))
                        .newWakeLock(10, "ScreenOnKeeper");
        try {
            long parseInt =
                    Integer.parseInt(
                            systemPropertiesWrapper.get(
                                    "persist.debug.power.keep_screen_on_duration", "-1"));
            long parseInt2 =
                    Integer.parseInt(
                            systemPropertiesWrapper.get(
                                    "persist.debug.power.touch_out_duration", "-1"));
            if (parseInt < 0) {
                parseInt = 1800000;
            }
            this.mKeepScreenOnDuration = parseInt;
            if (parseInt2 < 0) {
                parseInt2 = 600000;
            }
            this.mTouchOutCheckDuration = parseInt2;
        } catch (NumberFormatException e) {
            Slog.w("ScreenOnKeeper", "NumberFormatException: check SystemProperties " + e);
            this.mKeepScreenOnDuration = 1800000L;
            this.mTouchOutCheckDuration = 600000L;
        }
        disableScreenOnKeeper();
        Handler handler = this.mHandler;
        ScreenOnKeeper$$ExternalSyntheticLambda1 screenOnKeeper$$ExternalSyntheticLambda1 =
                new ScreenOnKeeper$$ExternalSyntheticLambda1(this, 1);
        getClass();
        handler.postAtTime(screenOnKeeper$$ExternalSyntheticLambda1, SystemClock.uptimeMillis());
    }

    public final void disableScreenOnKeeper() {
        Settings.Global.putInt(this.mContext.getContentResolver(), "screen_on_keeper", 0);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Screen On Keeper");
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("  mKeepScreenOnDuration="),
                                                this.mKeepScreenOnDuration,
                                                printWriter,
                                                "  mTouchOutCheckDuration="),
                                        this.mTouchOutCheckDuration,
                                        printWriter,
                                        "  mIsScreenOnKeeperEnabled="),
                                this.mIsScreenOnKeeperEnabled,
                                printWriter,
                                "  mLastScreenTouchTime="),
                        this.mLastScreenTouchTime,
                        printWriter,
                        "  mWakeLock.isHeld()=");
        m.append(this.mWakeLock.isHeld());
        printWriter.println(m.toString());
        printWriter.println();
    }

    public final void notifyKeepScreenOnExpiredLocked() {
        if ((this.mWakeLockSummarySupplier.getAsInt() & 2) == 0) {
            Slog.d(
                    "ScreenOnKeeper",
                    "notifyKeepScreenOnExpiredLocked: disable directly, wakelock exist");
            disableScreenOnKeeper();
            notifyScreenOnKeeperDisabledLocked(3);
            return;
        }
        Slog.d("ScreenOnKeeper", "notifyKeepScreenOnExpiredLocked: send expired intent");
        Intent intent = new Intent("android.intent.action.KEEP_SCREEN_ON_EXPIRED");
        intent.setPackage("com.samsung.android.displayassistant");
        Handler handler = this.mHandler;
        ScreenOnKeeper$$ExternalSyntheticLambda0 screenOnKeeper$$ExternalSyntheticLambda0 =
                new ScreenOnKeeper$$ExternalSyntheticLambda0(this, intent, 1);
        VcnManagementService$$ExternalSyntheticLambda10
                vcnManagementService$$ExternalSyntheticLambda10 = this.mClock;
        vcnManagementService$$ExternalSyntheticLambda10.getClass();
        handler.postAtTime(screenOnKeeper$$ExternalSyntheticLambda0, SystemClock.uptimeMillis());
        Message obtainMessage = handler.obtainMessage(2);
        vcnManagementService$$ExternalSyntheticLambda10.getClass();
        handler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis() + 90000);
    }

    public final void notifyScreenOnKeeperDisabledLocked(int i) {
        Intent intent = new Intent("android.intent.action.SCREEN_ON_KEEPER_DISABLED");
        intent.setPackage("com.samsung.android.displayassistant");
        intent.putExtra("reason", i);
        Handler handler = this.mHandler;
        ScreenOnKeeper$$ExternalSyntheticLambda0 screenOnKeeper$$ExternalSyntheticLambda0 =
                new ScreenOnKeeper$$ExternalSyntheticLambda0(this, intent, 0);
        getClass();
        handler.postAtTime(screenOnKeeper$$ExternalSyntheticLambda0, SystemClock.uptimeMillis());
    }
}
