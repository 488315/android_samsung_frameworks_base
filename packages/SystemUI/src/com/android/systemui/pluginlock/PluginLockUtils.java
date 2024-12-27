package com.android.systemui.pluginlock;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.pluginlock.utils.DumpUtils;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SafeUIState;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PluginLockUtils {
    private static final int LEVEL_SYSUI_SAFEMODE_S = 2;
    private static final int LEVEL_SYSUI_SAFEMODE_T = 5;
    private static final String TAG = "PluginLockUtils";
    private static final int sSafeModeLevel;
    private final Context mContext;
    private final DumpUtils mDumpUtils;
    private final SelectedUserInteractor mSelectedUserInteractor;
    private final KeyguardUpdateMonitor mUpdateMonitor;
    private HandlerExecutor mHandlerExecutor = null;
    private ExecutorService mExecutors = null;

    public class HandlerExecutor {
        private final Handler mHandler;

        public HandlerExecutor() {
            HandlerThread handlerThread = new HandlerThread("PluginLockHandlerThread");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        }

        public void execute(Runnable runnable) {
            if (this.mHandler.post(runnable)) {
                return;
            }
            Log.w(PluginLockUtils.TAG, "HandlerExecutor execute failed");
        }

        public void executeDelayed(Runnable runnable, long j) {
            if (this.mHandler.postDelayed(runnable, j)) {
                return;
            }
            Log.w(PluginLockUtils.TAG, "HandlerExecutor execute failed");
        }
    }

    static {
        sSafeModeLevel = Build.VERSION.SEM_PLATFORM_INT <= 130500 ? 2 : 5;
    }

    public PluginLockUtils(Context context, SelectedUserInteractor selectedUserInteractor, DumpUtils dumpUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mDumpUtils = dumpUtils;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        checkSafeMode();
    }

    private int getRescuePartyLevel() {
        try {
            return Integer.parseInt(SystemProperties.get("persist.sys.rescue_level", "0"));
        } catch (Throwable unused) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDump$0(String str, String str2) {
        synchronized (this) {
            this.mDumpUtils.addEvent(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Thread lambda$getExecutor$1(Runnable runnable) {
        return new Thread(runnable, TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestSafeMode$2() {
        this.mDumpUtils.addEvent(TAG, "call dls_safe_mode, level:" + getRescuePartyLevel());
        callProvider("dls_safe_mode", null, null);
    }

    public void addDump(final String str, final String str2) {
        Log.d(str, str2);
        getExecutor().execute(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PluginLockUtils.this.lambda$addDump$0(str, str2);
            }
        });
    }

    public Bundle callProvider(String str, String str2, Bundle bundle) {
        try {
            Assert.isNotMainThread();
            if (!this.mUpdateMonitor.mUserIsUnlocked.get(0)) {
                Log.w(TAG, "callProvider, user isn't unlocked yet");
                return null;
            }
            Bundle call = this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.dynamiclock.provider"), str, str2, bundle);
            Log.d(TAG, "callProvider, result:" + call);
            return call;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000d, code lost:
    
        if (r5.mUpdateMonitor.mUserIsUnlocked.get(0) != false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void checkSafeMode() {
        /*
            r5 = this;
            boolean r0 = com.android.systemui.LsRune.KEYGUARD_FBE
            if (r0 == 0) goto Lf
            com.android.keyguard.KeyguardUpdateMonitor r0 = r5.mUpdateMonitor
            android.util.SparseBooleanArray r0 = r0.mUserIsUnlocked
            r1 = 0
            boolean r0 = r0.get(r1)
            if (r0 == 0) goto L10
        Lf:
            r1 = 1
        L10:
            boolean r0 = r5.isGoingToRescueParty()
            java.lang.String r2 = "checkSafeMode, userUnlocked="
            java.lang.String r3 = ", safeMode="
            java.lang.String r4 = "PluginLockUtils"
            com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0.m(r2, r3, r4, r1, r0)
            if (r0 == 0) goto L24
            if (r1 == 0) goto L24
            r5.requestSafeMode()
        L24:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.PluginLockUtils.checkSafeMode():void");
    }

    public String getDump() {
        return this.mDumpUtils.getDump();
    }

    public String getDumpLegacy() {
        return this.mDumpUtils.getDumpLegacy();
    }

    public ExecutorService getExecutor() {
        if (this.mExecutors == null) {
            this.mExecutors = Executors.newSingleThreadExecutor(new PluginLockUtils$$ExternalSyntheticLambda2());
        }
        return this.mExecutors;
    }

    public HandlerExecutor getHandlerExecutor() {
        if (this.mHandlerExecutor == null) {
            this.mHandlerExecutor = new HandlerExecutor();
        }
        return this.mHandlerExecutor;
    }

    public boolean isCurrentOwner() {
        return this.mSelectedUserInteractor.getSelectedUserId() == 0;
    }

    public boolean isDesktopMode(Context context) {
        return false;
    }

    public boolean isGoingToRescueParty() {
        return SafeUIState.isSysUiSafeModeEnabled() || getRescuePartyLevel() >= sSafeModeLevel;
    }

    public boolean isLockScreenEnabled() {
        return true;
    }

    public Bundle requestMultiPack(Bundle bundle) {
        return callProvider("user_pack", null, bundle);
    }

    public void requestSafeMode() {
        getExecutor().execute(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PluginLockUtils.this.lambda$requestSafeMode$2();
            }
        });
    }
}
