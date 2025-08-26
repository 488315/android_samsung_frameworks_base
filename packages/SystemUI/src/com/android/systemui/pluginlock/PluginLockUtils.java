package com.android.systemui.pluginlock;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.Log;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.utils.DumpUtils;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SafeUIState;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
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
                this.f$0.lambda$addDump$0(str, str2);
            }
        });
    }

    public Bundle callProvider(String str, String str2, Bundle bundle) {
        try {
            Assert.isNotMainThread();
            if (!this.mUpdateMonitor.mUserManager.isUserUnlocked(0)) {
                Log.w(TAG, "callProvider, user isn't unlocked yet");
                return null;
            }
            Bundle bundleCall = this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.dynamiclock.provider"), str, str2, bundle);
            Log.d(TAG, "callProvider, result:" + bundleCall);
            return bundleCall;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void checkSafeMode() {
        boolean z;
        if (LsRune.KEYGUARD_FBE) {
            z = this.mUpdateMonitor.mUserManager.isUserUnlocked(0);
        }
        boolean zIsGoingToRescueParty = isGoingToRescueParty();
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("checkSafeMode, userUnlocked=", ", safeMode=", TAG, z, zIsGoingToRescueParty);
        if (zIsGoingToRescueParty && z) {
            requestSafeMode();
        }
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
                this.f$0.lambda$requestSafeMode$2();
            }
        });
    }
}
