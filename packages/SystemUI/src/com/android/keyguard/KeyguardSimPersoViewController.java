package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.hardware.input.InputManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.ProgressBar;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.internal.telephony.ISemTelephony;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPersoViewController;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.R;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;

/* loaded from: classes.dex */
public class KeyguardSimPersoViewController extends KeyguardSecPinBasedInputViewController {
    public final CarrierText mCarrierLabel;
    public AnonymousClass3 mCheckSimPersoThread;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public int mOrientation;
    public final ProgressBar mProgressBar;
    public volatile boolean mSimCheckInProgress;
    public int mSubId;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public static final String SIM_TYPE = SystemProperties.get("ril.simtype");
    public static final String DOMESTIC_OTA_START = SystemProperties.get("ril.domesticOtaStart");

    /* renamed from: com.android.keyguard.KeyguardSimPersoViewController$3, reason: invalid class name */
    public class AnonymousClass3 extends CheckSimPerso {
        public final /* synthetic */ KeyguardSecurityCallback val$keyguardSecurityCallback;
        public final /* synthetic */ int val$subId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(String str, int i, KeyguardSecurityCallback keyguardSecurityCallback) {
            super(str);
            this.val$subId = i;
            this.val$keyguardSecurityCallback = keyguardSecurityCallback;
        }

        @Override // com.android.keyguard.KeyguardSimPersoViewController.CheckSimPerso
        public final void onSimCheckResponse(final boolean z) {
            KeyguardSimPersoView keyguardSimPersoView = (KeyguardSimPersoView) ((ViewController) KeyguardSimPersoViewController.this).mView;
            final int i = this.val$subId;
            final KeyguardSecurityCallback keyguardSecurityCallback = this.val$keyguardSecurityCallback;
            keyguardSimPersoView.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPersoViewController$3$$ExternalSyntheticLambda0
                /* JADX WARN: Removed duplicated region for block: B:14:0x0067  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    KeyguardSimPersoViewController.AnonymousClass3 anonymousClass3 = this.f$0;
                    boolean z2 = z;
                    int i2 = i;
                    KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                    ((KeyguardSimPersoView) ((ViewController) KeyguardSimPersoViewController.this).mView).resetPasswordText(true, true);
                    KeyguardSimPersoViewController.this.setEnabledKeypad(true);
                    KeyguardSimPersoViewController.this.mOkButton.setVisibility(0);
                    KeyguardSimPersoViewController.this.mProgressBar.setVisibility(8);
                    if (z2) {
                        int selectedUserId = KeyguardSimPersoViewController.this.mSelectedUserInteractor.getSelectedUserId();
                        Log.i("KeyguardSimPersoView", "verifyPasswordAndUnlock onSimCheckResponse verifySucceed");
                        String str = KeyguardSimPersoViewController.SIM_TYPE;
                        if (str.equals("") || (Integer.parseInt(str) == 19 && Integer.parseInt(str) == 20)) {
                            String str2 = KeyguardSimPersoViewController.DOMESTIC_OTA_START;
                            if (!str2.equals("") && str2.equals("true")) {
                                KeyguardSimPersoViewController.this.mKeyguardUpdateMonitor.reportSimUnlocked(i2);
                            }
                            KeyguardSimPersoViewController keyguardSimPersoViewController = KeyguardSimPersoViewController.this;
                            if (!keyguardSimPersoViewController.mPaused && keyguardSimPersoViewController.getKeyguardSecurityCallback() != null) {
                                KeyguardSimPersoViewController.this.getKeyguardSecurityCallback().dismiss(true, selectedUserId, KeyguardSimPersoViewController.this.mSecurityMode);
                            }
                        }
                    } else {
                        Log.i("KeyguardSimPersoView", "verifyPasswordAndUnlock onSimCheckResponse verifyFail");
                        KeyguardSimPersoViewController keyguardSimPersoViewController2 = KeyguardSimPersoViewController.this;
                        keyguardSimPersoViewController2.mMessageAreaController.setMessage(keyguardSimPersoViewController2.getContext().getString(R.string.kg_password_perso_failed), false);
                    }
                    keyguardSecurityCallback2.userActivity();
                    KeyguardSimPersoViewController.this.mSimCheckInProgress = false;
                    KeyguardSimPersoViewController.this.mCheckSimPersoThread = null;
                }
            });
        }
    }

    public abstract class CheckSimPerso extends Thread {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final String mPin;

        public CheckSimPerso(String str) {
            this.mPin = str;
        }

        public abstract void onSimCheckResponse(boolean z);

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                ISemTelephony iSemTelephonyAsInterface = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
                if (iSemTelephonyAsInterface != null) {
                    final boolean zSupplyPersoForSubId = iSemTelephonyAsInterface.supplyPersoForSubId(KeyguardSimPersoViewController.this.mSubId, this.mPin);
                    Log.i("KeyguardSimPersoView", "CheckSimPerso supplyPersoForSubId(subId=" + KeyguardSimPersoViewController.this.mSubId + ") returned : " + zSupplyPersoForSubId);
                    ((KeyguardSimPersoView) ((ViewController) KeyguardSimPersoViewController.this).mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSimPersoViewController$CheckSimPerso$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardSimPersoViewController.CheckSimPerso checkSimPerso = this.f$0;
                            boolean z = zSupplyPersoForSubId;
                            int i = KeyguardSimPersoViewController.CheckSimPerso.$r8$clinit;
                            checkSimPerso.onSimCheckResponse(z);
                        }
                    }, 50L);
                }
            } catch (RemoteException e) {
                Log.e("KeyguardSimPersoView", "RemoteException for supplyPerso:", e);
                ((KeyguardSimPersoView) ((ViewController) KeyguardSimPersoViewController.this).mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSimPersoViewController$CheckSimPerso$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSimPersoViewController.CheckSimPerso checkSimPerso = this.f$0;
                        int i = KeyguardSimPersoViewController.CheckSimPerso.$r8$clinit;
                        checkSimPerso.onSimCheckResponse(false);
                    }
                }, 50L);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.KeyguardSimPersoViewController$1] */
    public KeyguardSimPersoViewController(KeyguardSimPersoView keyguardSimPersoView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor, BouncerHapticPlayer bouncerHapticPlayer, UserActivityNotifier userActivityNotifier, InputManager inputManager) {
        super(keyguardSimPersoView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, emergencyButtonController, falsingCollector, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor, bouncerHapticPlayer, userActivityNotifier, inputManager);
        this.mSimCheckInProgress = false;
        this.mOrientation = 1;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSimPersoViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardSimPersoViewController keyguardSimPersoViewController = KeyguardSimPersoViewController.this;
                int i = keyguardSimPersoViewController.mOrientation;
                int i2 = configuration.orientation;
                if (i != i2) {
                    keyguardSimPersoViewController.mOrientation = i2;
                    if (DeviceState.isCenterDisplayCutOut(keyguardSimPersoViewController.getContext())) {
                        if (configuration.orientation == 1) {
                            keyguardSimPersoViewController.mCarrierLabel.setMaxWidth(keyguardSimPersoViewController.getResources().getDimensionPixelSize(R.dimen.carrier_label_portrait_max_width));
                        } else {
                            keyguardSimPersoViewController.mCarrierLabel.setMaxWidth(Integer.MAX_VALUE);
                        }
                    }
                }
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPersoViewController.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                Log.i("KeyguardSimPersoView", MutableVectorKt$$ExternalSyntheticOutline0.m(i, i3, "onSimStateChanged(subId=", ",state=", ")"));
                KeyguardSimPersoViewController keyguardSimPersoViewController = KeyguardSimPersoViewController.this;
                if (i3 != 1 && i3 != 5) {
                    keyguardSimPersoViewController.resetState();
                    return;
                }
                if (i3 == 1) {
                    Log.i("KeyguardSimPersoView", "Card Remove during SIM perso");
                } else if (i3 == 5) {
                    Log.i("KeyguardSimPersoView", "Card READY during SIM perso ");
                }
                if (!keyguardSimPersoViewController.mPaused && keyguardSimPersoViewController.getKeyguardSecurityCallback() != null && !keyguardSimPersoViewController.mKeyguardUpdateMonitor.isSimState(12)) {
                    Log.i("KeyguardSimPersoView", "Dismiss SIM perso View");
                    keyguardSimPersoViewController.getKeyguardSecurityCallback().dismiss(true, keyguardSimPersoViewController.mSelectedUserInteractor.getSelectedUserId(), keyguardSimPersoViewController.mSecurityMode);
                } else if (i3 == 5 && SubscriptionManager.isValidSubscriptionId(i) && keyguardSimPersoViewController.mSubId != i) {
                    Log.d("KeyguardSimPersoView", "READY already came. Skip this");
                } else {
                    keyguardSimPersoViewController.resetState();
                }
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView != null) {
            ((SecPasswordTextView) passwordTextView).mMaxLength = 8;
        }
        CarrierText carrierText = (CarrierText) ((KeyguardSimPersoView) this.mView).findViewById(R.id.carrier_text);
        this.mCarrierLabel = carrierText;
        if (DeviceState.isCenterDisplayCutOut(getContext())) {
            if (this.mOrientation == 1) {
                carrierText.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.carrier_label_portrait_max_width));
            } else {
                carrierText.setMaxWidth(Integer.MAX_VALUE);
            }
        }
        ProgressBar progressBar = (ProgressBar) ((KeyguardSimPersoView) this.mView).findViewById(R.id.progress);
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        progressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY) ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_sec_sim_perso_view;
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        super.resetState();
        Log.i("KeyguardSimPersoView", "Resetting state");
        int nextSubIdForState = this.mKeyguardUpdateMonitor.getNextSubIdForState(12);
        if (nextSubIdForState != this.mSubId && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
            this.mSubId = nextSubIdForState;
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setMessage(getContext().getString(R.string.kg_ktt_sim_perso_instructions), false);
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        this.mProgressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY) ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        if (this.mPasswordEntry instanceof SecPasswordTextView) {
            KeyguardSecurityCallback keyguardSecurityCallback = getKeyguardSecurityCallback();
            String str = ((SecPasswordTextView) this.mPasswordEntry).mText;
            if (str.length() == 0) {
                this.mMessageAreaController.setMessage(getContext().getString(R.string.kg_empty_sim_perso_hint), false);
                ((KeyguardSimPersoView) this.mView).resetPasswordText(true, true);
                keyguardSecurityCallback.userActivity();
                return;
            }
            if (str.length() < 4) {
                this.mMessageAreaController.setMessage(getContext().getString(R.string.kg_ktt_sim_perso_instructions), false);
                ((KeyguardSimPersoView) this.mView).resetPasswordText(true, true);
                keyguardSecurityCallback.userActivity();
                return;
            }
            setEnabledKeypad(false);
            this.mOkButton.setVisibility(8);
            this.mProgressBar.setVisibility(0);
            if (this.mCheckSimPersoThread == null) {
                this.mCheckSimPersoThread = new AnonymousClass3(str, this.mSubId, keyguardSecurityCallback);
                if (this.mSimCheckInProgress) {
                    return;
                }
                this.mSimCheckInProgress = true;
                this.mCheckSimPersoThread.start();
            }
        }
    }
}
