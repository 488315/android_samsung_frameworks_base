package com.android.keyguard;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DesktopManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardPluginControllerImpl {
    public final Context mContext;
    public final DesktopManager mDesktopManager;
    public final KeyguardSecurityCallback mKeyguardCallback;
    public final KeyguardTextBuilder mKeyguardTextBuilder;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public AsyncTask mPendingLockCheck;
    public int mPromptReason;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SubScreenManager mSubScreenManager;
    public final ViewMediatorCallback mViewMediatorCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.keyguard.KeyguardPluginControllerImpl$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Factory {
        public final Context mContext;
        public final DesktopManager mDesktopManager;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LatencyTracker mLatencyTracker;
        public final LockPatternUtils mLockPatternUtils;
        public final SelectedUserInteractor mSelectedUserInteractor;
        public final SubScreenManager mSubScreenManager;
        public final ViewMediatorCallback mViewMediatorCallback;

        public Factory(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor) {
            this.mContext = context;
            this.mViewMediatorCallback = viewMediatorCallback;
            this.mDesktopManager = desktopManager;
            this.mSubScreenManager = subScreenManager;
            this.mLatencyTracker = latencyTracker;
            this.mLockPatternUtils = lockPatternUtils;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mSelectedUserInteractor = selectedUserInteractor;
        }
    }

    /* renamed from: -$$Nest$monPasswordChecked, reason: not valid java name */
    public static void m955$$Nest$monPasswordChecked(KeyguardPluginControllerImpl keyguardPluginControllerImpl, int i, boolean z, int i2) {
        keyguardPluginControllerImpl.getClass();
        Log.d("KeyguardPluginController", "onPasswordChecked matched " + z + " timeoutMs " + i2);
        KeyguardSecurityCallback keyguardSecurityCallback = keyguardPluginControllerImpl.mKeyguardCallback;
        if (z) {
            boolean z2 = keyguardPluginControllerImpl.mSelectedUserInteractor.getSelectedUserId() == i;
            keyguardSecurityCallback.reportUnlockAttempt(i, 0, true);
            if (z2) {
                keyguardSecurityCallback.dismiss(true, i, KeyguardSecurityModel.SecurityMode.Invalid);
            }
        } else {
            keyguardSecurityCallback.reportUnlockAttempt(i, i2, false);
            if (i2 > 0) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
                keyguardUpdateMonitor.setLockoutAttemptDeadline(i, i2);
                keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_LOCKOUT_DEADLINE);
            }
        }
        SubScreenManager subScreenManager = keyguardPluginControllerImpl.mSubScreenManager;
        if (subScreenManager.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "onPasswordChecked() no plugin");
            return;
        }
        Log.d("SubScreenManager", "onPasswordChecked() " + z + " " + i2);
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && z && !subScreenManager.mKeyguardUpdateMonitor.isUserUnlocked$1()) {
            subScreenManager.startSubScreenFallback(subScreenManager.mSubDisplay);
        }
        subScreenManager.mSubScreenPlugin.onPasswordChecked(z, i2);
    }

    public /* synthetic */ KeyguardPluginControllerImpl(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor, int i) {
        this(context, viewMediatorCallback, desktopManager, subScreenManager, keyguardSecurityCallback, latencyTracker, lockPatternUtils, keyguardUpdateMonitor, selectedUserInteractor);
    }

    public final void showWipeWarningDialog(String str) {
        if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            return;
        }
        SubScreenManager subScreenManager = this.mSubScreenManager;
        if (subScreenManager.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "showWipeWarningDialog() no plugin");
        } else {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("showWipeWarningDialog() ", str, "SubScreenManager");
            subScreenManager.mSubScreenPlugin.showWipeWarningDialog(str);
        }
    }

    private KeyguardPluginControllerImpl(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor) {
        this.mContext = context;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mDesktopManager = desktopManager;
        this.mSubScreenManager = subScreenManager;
        this.mKeyguardCallback = keyguardSecurityCallback;
        this.mLatencyTracker = latencyTracker;
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardTextBuilder = KeyguardTextBuilder.getInstance(context);
        this.mSelectedUserInteractor = selectedUserInteractor;
    }
}
