package com.android.systemui.pluginlock;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.utils.DumpUtils;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SafeUIState;
import com.sec.ims.configuration.DATA;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockUtils {
    public static final int sSafeModeLevel;
    public final Context mContext;
    public final DumpUtils mDumpUtils;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public HandlerExecutor mHandlerExecutor = null;
    public ExecutorService mExecutors = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HandlerExecutor {
        public final Handler mHandler;

        public HandlerExecutor() {
            HandlerThread handlerThread = new HandlerThread("PluginLockHandlerThread");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        }
    }

    static {
        sSafeModeLevel = Build.VERSION.SEM_PLATFORM_INT <= 130500 ? 2 : 5;
    }

    public PluginLockUtils(Context context, DumpUtils dumpUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mDumpUtils = dumpUtils;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        checkSafeMode();
    }

    public static boolean isCurrentOwner() {
        return KeyguardUpdateMonitor.getCurrentUser() == 0;
    }

    public static boolean isGoingToRescueParty() {
        int i;
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            try {
                i = Integer.parseInt(SystemProperties.get("persist.sys.rescue_level", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
            } catch (Throwable unused) {
                i = 0;
            }
            if (i < sSafeModeLevel) {
                return false;
            }
        }
        return true;
    }

    public final void addDump(final String str, final String str2) {
        Log.d(str, str2);
        if (this.mExecutors == null) {
            this.mExecutors = Executors.newSingleThreadExecutor(new PluginLockUtils$$ExternalSyntheticLambda2());
        }
        this.mExecutors.execute(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PluginLockUtils pluginLockUtils = PluginLockUtils.this;
                String str3 = str2;
                synchronized (pluginLockUtils) {
                    pluginLockUtils.mDumpUtils.addEvent(str3);
                }
            }
        });
    }

    public final Bundle callProvider(String str, Bundle bundle) {
        try {
            Assert.isNotMainThread();
            if (!this.mUpdateMonitor.isUserUnlocked()) {
                Log.w("PluginLockUtils", "callProvider, user isn't unlocked yet");
                return null;
            }
            Bundle call = this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.dynamiclock.provider"), str, (String) null, bundle);
            Log.d("PluginLockUtils", "callProvider, result:" + call);
            return call;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public final void checkSafeMode() {
        boolean z = !LsRune.KEYGUARD_FBE || this.mUpdateMonitor.isUserUnlocked();
        boolean isGoingToRescueParty = isGoingToRescueParty();
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("checkSafeMode, userUnlocked=", z, ", safeMode=", isGoingToRescueParty, "PluginLockUtils");
        if (isGoingToRescueParty && z) {
            if (this.mExecutors == null) {
                this.mExecutors = Executors.newSingleThreadExecutor(new PluginLockUtils$$ExternalSyntheticLambda2());
            }
            this.mExecutors.execute(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    PluginLockUtils pluginLockUtils = PluginLockUtils.this;
                    StringBuilder sb = new StringBuilder("call dls_safe_mode, level:");
                    pluginLockUtils.getClass();
                    try {
                        i = Integer.parseInt(SystemProperties.get("persist.sys.rescue_level", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
                    } catch (Throwable unused) {
                        i = 0;
                    }
                    sb.append(i);
                    pluginLockUtils.mDumpUtils.addEvent(sb.toString());
                    pluginLockUtils.callProvider("dls_safe_mode", null);
                }
            });
        }
    }
}
