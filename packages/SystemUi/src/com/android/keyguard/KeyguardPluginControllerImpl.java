package com.android.keyguard;

import android.R;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPluginControllerImpl {
    public final Context mContext;
    public final DesktopManager mDesktopManager;
    public final KeyguardSecurityCallback mKeyguardCallback;
    public final KeyguardTextBuilder mKeyguardTextBuilder;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public AsyncTask mPendingLockCheck;
    public int mPromptReason;
    public String mStrongAuthPopupMessage;
    public final SubScreenManager mSubScreenManager;
    public final ViewMediatorCallback mViewMediatorCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardPluginControllerImpl$2 */
    public abstract /* synthetic */ class AbstractC07102 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f206xdc0e830a;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f206xdc0e830a = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f206xdc0e830a[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f206xdc0e830a[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final Context mContext;
        public final DesktopManager mDesktopManager;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LatencyTracker mLatencyTracker;
        public final LockPatternUtils mLockPatternUtils;
        public final SubScreenManager mSubScreenManager;
        public final ViewMediatorCallback mViewMediatorCallback;

        public Factory(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
            this.mContext = context;
            this.mViewMediatorCallback = viewMediatorCallback;
            this.mDesktopManager = desktopManager;
            this.mSubScreenManager = subScreenManager;
            this.mLatencyTracker = latencyTracker;
            this.mLockPatternUtils = lockPatternUtils;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00af, code lost:
    
        if (r12 != 4) goto L40;
     */
    /* renamed from: -$$Nest$monPasswordChecked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m355$$Nest$monPasswordChecked(KeyguardPluginControllerImpl keyguardPluginControllerImpl, int i, boolean z, int i2) {
        keyguardPluginControllerImpl.getClass();
        Log.d("KeyguardPluginController", "onPasswordChecked matched " + z + " timeoutMs " + i2);
        int i3 = 0;
        KeyguardSecurityCallback keyguardSecurityCallback = keyguardPluginControllerImpl.mKeyguardCallback;
        if (z) {
            boolean z2 = KeyguardUpdateMonitor.getCurrentUser() == i;
            keyguardSecurityCallback.reportUnlockAttempt(i, 0, true);
            if (z2) {
                keyguardSecurityCallback.dismiss(i, KeyguardSecurityModel.SecurityMode.Invalid, true);
            }
        } else {
            keyguardSecurityCallback.reportUnlockAttempt(i, i2, false);
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) keyguardPluginControllerImpl.mKnoxStateMonitor;
            boolean isDeviceDisabledForMaxFailedAttempt = knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt();
            DesktopManager desktopManager = keyguardPluginControllerImpl.mDesktopManager;
            Context context = keyguardPluginControllerImpl.mContext;
            if (isDeviceDisabledForMaxFailedAttempt) {
                EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
                String str = edmMonitor == null ? null : edmMonitor.mPkgNameForMaxAttemptDisable;
                StringBuilder sb = new StringBuilder();
                sb.append(context.getString(R.string.font_family_menu_material));
                sb.append(TextUtils.isEmpty(str) ? "" : PathParser$$ExternalSyntheticOutline0.m29m("(", str, ")"));
                String sb2 = sb.toString();
                if (((DesktopManagerImpl) desktopManager).isDesktopMode()) {
                    ((DesktopManagerImpl) desktopManager).notifyUpdateBouncerMessage(1, sb2, keyguardPluginControllerImpl.mStrongAuthPopupMessage);
                }
            } else {
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
                if (i2 > 0) {
                    keyguardUpdateMonitor.setLockoutAttemptDeadline(i, i2);
                    keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_LOCKOUT_DEADLINE);
                }
                if (i2 == 0) {
                    int remainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(2);
                    int credentialTypeForUser = keyguardUpdateMonitor.getCredentialTypeForUser(i);
                    if (credentialTypeForUser != 1) {
                        if (credentialTypeForUser != 2) {
                            if (credentialTypeForUser == 3) {
                                i3 = com.android.systemui.R.string.kg_incorrect_pin;
                            }
                        }
                        i3 = com.android.systemui.R.string.kg_incorrect_password;
                    } else {
                        i3 = com.android.systemui.R.string.kg_incorrect_pattern;
                    }
                    String string = context.getString(i3);
                    if (remainingAttempt > 0) {
                        string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(AbstractC0000x2c234b15.m2m(string, " ("), context.getResources().getQuantityString(com.android.systemui.R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                    }
                    if (((DesktopManagerImpl) desktopManager).isDesktopMode()) {
                        ((DesktopManagerImpl) desktopManager).notifyUpdateBouncerMessage(1, string, keyguardPluginControllerImpl.mStrongAuthPopupMessage);
                    }
                }
            }
        }
        SubScreenManager subScreenManager = keyguardPluginControllerImpl.mSubScreenManager;
        if (subScreenManager.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "onPasswordChecked() no plugin");
            return;
        }
        Log.d("SubScreenManager", "onPasswordChecked() " + z + " " + i2);
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && z && !subScreenManager.mKeyguardUpdateMonitor.isUserUnlocked()) {
            subScreenManager.startSubScreenFallback(subScreenManager.mSubDisplay);
        }
        subScreenManager.mSubScreenPlugin.onPasswordChecked(z, i2);
    }

    public /* synthetic */ KeyguardPluginControllerImpl(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        this(context, viewMediatorCallback, desktopManager, subScreenManager, keyguardSecurityCallback, latencyTracker, lockPatternUtils, keyguardUpdateMonitor);
    }

    public final void showWipeWarningDialog(String str) {
        DesktopManager desktopManager = this.mDesktopManager;
        if (((DesktopManagerImpl) desktopManager).isDesktopMode()) {
            ((DesktopManagerImpl) desktopManager).notifyUpdateBouncerMessage(2, "", str);
        }
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            SubScreenManager subScreenManager = this.mSubScreenManager;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "showWipeWarningDialog() no plugin");
            } else {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("showWipeWarningDialog() ", str, "SubScreenManager");
                subScreenManager.mSubScreenPlugin.showWipeWarningDialog(str);
            }
        }
    }

    private KeyguardPluginControllerImpl(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mDesktopManager = desktopManager;
        this.mSubScreenManager = subScreenManager;
        this.mKeyguardCallback = keyguardSecurityCallback;
        this.mLatencyTracker = latencyTracker;
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardTextBuilder = KeyguardTextBuilder.getInstance(context);
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
    }
}
