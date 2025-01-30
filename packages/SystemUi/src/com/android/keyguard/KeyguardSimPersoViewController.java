package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.ProgressBar;
import com.android.internal.telephony.ISemTelephony;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPersoViewController;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSimPersoViewController extends KeyguardSecPinBasedInputViewController {
    public final CarrierText mCarrierLabel;
    public CheckSimPerso mCheckSimPersoThread;
    public final ConfigurationController mConfigurationController;
    public final C07711 mConfigurationListener;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public int mOrientation;
    public final ProgressBar mProgressBar;
    public volatile boolean mSimCheckInProgress;
    public int mSubId;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public static final String SIM_TYPE = SystemProperties.get("ril.simtype");
    public static final String DOMESTIC_OTA_START = SystemProperties.get("ril.domesticOtaStart");
    public static int mNumRetry = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSimPersoViewController$3 */
    public final class C07733 extends CheckSimPerso {
        public final /* synthetic */ KeyguardSecurityCallback val$keyguardSecurityCallback;
        public final /* synthetic */ int val$subId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07733(String str, int i, KeyguardSecurityCallback keyguardSecurityCallback) {
            super(str);
            this.val$subId = i;
            this.val$keyguardSecurityCallback = keyguardSecurityCallback;
        }

        @Override // com.android.keyguard.KeyguardSimPersoViewController.CheckSimPerso
        public final void onSimCheckResponse(final boolean z) {
            KeyguardSimPersoViewController keyguardSimPersoViewController = KeyguardSimPersoViewController.this;
            String str = KeyguardSimPersoViewController.SIM_TYPE;
            KeyguardSimPersoView keyguardSimPersoView = (KeyguardSimPersoView) keyguardSimPersoViewController.mView;
            final int i = this.val$subId;
            final KeyguardSecurityCallback keyguardSecurityCallback = this.val$keyguardSecurityCallback;
            keyguardSimPersoView.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPersoViewController$3$$ExternalSyntheticLambda0
                /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:
                
                    if (r3.equals("true") != false) goto L17;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    KeyguardSimPersoViewController keyguardSimPersoViewController2;
                    KeyguardSimPersoViewController.C07733 c07733 = KeyguardSimPersoViewController.C07733.this;
                    boolean z2 = z;
                    int i2 = i;
                    KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                    KeyguardSimPersoViewController keyguardSimPersoViewController3 = KeyguardSimPersoViewController.this;
                    String str2 = KeyguardSimPersoViewController.SIM_TYPE;
                    ((KeyguardSimPersoView) keyguardSimPersoViewController3.mView).resetPasswordText(true, true);
                    KeyguardSimPersoViewController.this.setEnabledKeypad(true);
                    KeyguardSimPersoViewController.this.mOkButton.setVisibility(0);
                    KeyguardSimPersoViewController.this.mProgressBar.setVisibility(8);
                    if (z2) {
                        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                        if (LsRune.SECURITY_KTT_USIM_TEXT) {
                            KeyguardSimPersoViewController.mNumRetry = 0;
                        }
                        String str3 = KeyguardSimPersoViewController.SIM_TYPE;
                        if (str3.equals("") || (Integer.parseInt(str3) == 19 && Integer.parseInt(str3) == 20)) {
                            String str4 = KeyguardSimPersoViewController.DOMESTIC_OTA_START;
                            if (!str4.equals("")) {
                            }
                            keyguardSimPersoViewController2 = KeyguardSimPersoViewController.this;
                            if (!keyguardSimPersoViewController2.mPaused && keyguardSimPersoViewController2.getKeyguardSecurityCallback() != null) {
                                KeyguardSimPersoViewController.this.getKeyguardSecurityCallback().dismiss(currentUser, KeyguardSimPersoViewController.this.mSecurityMode, true);
                            }
                        }
                        KeyguardSimPersoViewController.this.mKeyguardUpdateMonitor.reportSimUnlocked(i2);
                        keyguardSimPersoViewController2 = KeyguardSimPersoViewController.this;
                        if (!keyguardSimPersoViewController2.mPaused) {
                            KeyguardSimPersoViewController.this.getKeyguardSecurityCallback().dismiss(currentUser, KeyguardSimPersoViewController.this.mSecurityMode, true);
                        }
                    } else {
                        if (LsRune.SECURITY_KTT_USIM_TEXT) {
                            KeyguardSimPersoViewController.mNumRetry++;
                        }
                        if (LsRune.SECURITY_SKT_USIM_TEXT) {
                            KeyguardSimPersoViewController keyguardSimPersoViewController4 = KeyguardSimPersoViewController.this;
                            keyguardSimPersoViewController4.mMessageAreaController.setMessage(keyguardSimPersoViewController4.getContext().getString(R.string.kg_password_perso_failed), false);
                        } else if (KeyguardSimPersoViewController.mNumRetry < 5) {
                            KeyguardSimPersoViewController keyguardSimPersoViewController5 = KeyguardSimPersoViewController.this;
                            keyguardSimPersoViewController5.mMessageAreaController.setMessage(keyguardSimPersoViewController5.getContext().getString(R.string.kg_password_perso_failed), false);
                        } else {
                            KeyguardSimPersoViewController keyguardSimPersoViewController6 = KeyguardSimPersoViewController.this;
                            keyguardSimPersoViewController6.mMessageAreaController.setMessage(keyguardSimPersoViewController6.getContext().getString(R.string.kg_password_perso_max_failed), false);
                        }
                    }
                    keyguardSecurityCallback2.userActivity();
                    KeyguardSimPersoViewController.this.mSimCheckInProgress = false;
                    KeyguardSimPersoViewController.this.mCheckSimPersoThread = null;
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CheckSimPerso extends Thread {
        public final String mPin;

        public CheckSimPerso(String str) {
            this.mPin = str;
        }

        public abstract void onSimCheckResponse(boolean z);

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                ISemTelephony asInterface = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
                if (asInterface != null) {
                    final boolean supplyPersoForSubId = asInterface.supplyPersoForSubId(KeyguardSimPersoViewController.this.mSubId, this.mPin);
                    KeyguardSimPersoViewController keyguardSimPersoViewController = KeyguardSimPersoViewController.this;
                    int i = keyguardSimPersoViewController.mSubId;
                    ((KeyguardSimPersoView) keyguardSimPersoViewController.mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSimPersoViewController$CheckSimPerso$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardSimPersoViewController.CheckSimPerso.this.onSimCheckResponse(supplyPersoForSubId);
                        }
                    }, 50L);
                }
            } catch (RemoteException e) {
                Log.e("KeyguardSimPersoView", "RemoteException for supplyPerso:", e);
                KeyguardSimPersoViewController keyguardSimPersoViewController2 = KeyguardSimPersoViewController.this;
                String str = KeyguardSimPersoViewController.SIM_TYPE;
                ((KeyguardSimPersoView) keyguardSimPersoViewController2.mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSimPersoViewController$CheckSimPerso$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSimPersoViewController.CheckSimPerso.this.onSimCheckResponse(false);
                    }
                }, 50L);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.KeyguardSimPersoViewController$1] */
    public KeyguardSimPersoViewController(KeyguardSimPersoView keyguardSimPersoView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController) {
        super(keyguardSimPersoView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
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
                KeyguardSimPersoViewController keyguardSimPersoViewController = KeyguardSimPersoViewController.this;
                if (i3 != 1 && i3 != 5) {
                    keyguardSimPersoViewController.resetState();
                    return;
                }
                if (!keyguardSimPersoViewController.mPaused && keyguardSimPersoViewController.getKeyguardSecurityCallback() != null && !keyguardSimPersoViewController.mKeyguardUpdateMonitor.isSimState(2)) {
                    keyguardSimPersoViewController.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardSimPersoViewController.mSecurityMode, true);
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
        updateProgressBarDrawable();
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
        int nextSubIdForState = this.mKeyguardUpdateMonitor.getNextSubIdForState(12);
        if (nextSubIdForState != this.mSubId && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
            this.mSubId = nextSubIdForState;
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setMessage(getContext().getString(R.string.kg_ktt_sim_perso_instructions), false);
        }
    }

    public final void updateProgressBarDrawable() {
        this.mProgressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        updateProgressBarDrawable();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        if (this.mPasswordEntry instanceof SecPasswordTextView) {
            KeyguardSecurityCallback keyguardSecurityCallback = getKeyguardSecurityCallback();
            String str = ((SecPasswordTextView) this.mPasswordEntry).mText;
            if (LsRune.SECURITY_KTT_USIM_TEXT && mNumRetry >= 5) {
                ((KeyguardSimPersoView) this.mView).resetPasswordText(true, true);
                this.mMessageAreaController.setMessage(getContext().getString(R.string.kg_password_perso_max_failed), false);
                keyguardSecurityCallback.userActivity();
                return;
            }
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
                this.mCheckSimPersoThread = new C07733(str, this.mSubId, keyguardSecurityCallback);
                if (this.mSimCheckInProgress) {
                    return;
                }
                this.mSimCheckInProgress = true;
                this.mCheckSimPersoThread.start();
            }
        }
    }
}
