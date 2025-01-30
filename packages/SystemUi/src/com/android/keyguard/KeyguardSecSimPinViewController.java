package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.PinResult;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.internal.telephony.ISemTelephony;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecSimPinViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPinViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecSimPinViewController extends KeyguardSimPinViewController {
    public final ConfigurationController mConfigurationController;
    public final C07381 mConfigurationListener;
    public final KeyguardSecESimArea mESimSkipArea;
    public final InputMethodManager mInputMethodManager;
    public Locale mLocale;
    public int mOrientation;
    public final ProgressBar mProgressBar;
    public final SettingsHelper mSettingsHelper;
    public final KeyguardSecSimPinViewController$$ExternalSyntheticLambda0 mSettingsListener;
    public final Uri[] mSettingsValueList;
    public final SystemUITextView mSimCardName;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecSimPinViewController$2 */
    public final class C07392 extends KeyguardUpdateMonitorCallback {
        public C07392() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            Log.i("KeyguardSecSimPinViewController", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("onSimStateChanged(subId=", i, ",state=", i3, ")"));
            KeyguardSecSimPinViewController keyguardSecSimPinViewController = KeyguardSecSimPinViewController.this;
            KeyguardSecurityCallback keyguardSecurityCallback = keyguardSecSimPinViewController.getKeyguardSecurityCallback();
            if (i3 == 0) {
                if (LsRune.SECURITY_ESIM && ((KeyguardSimPinViewController) keyguardSecSimPinViewController).mKeyguardUpdateMonitor.isESimRemoveButtonClicked() && keyguardSecurityCallback != null) {
                    ((ActivityStarter) Dependency.get(ActivityStarter.class)).executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPinViewController$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardSecSimPinViewController keyguardSecSimPinViewController2 = KeyguardSecSimPinViewController.this;
                            if (keyguardSecSimPinViewController2.mESimSkipArea != null) {
                                Log.d("KeyguardSecSimPinViewController", "eraseSubscriptions");
                                keyguardSecSimPinViewController2.mESimSkipArea.eraseSubscriptions();
                            }
                        }
                    }, null, false, false, false);
                    return;
                } else {
                    keyguardSecSimPinViewController.resetState();
                    return;
                }
            }
            if (i3 != 1) {
                if (i3 == 2) {
                    ProgressBar progressBar = keyguardSecSimPinViewController.mProgressBar;
                    if (progressBar == null || !progressBar.isAnimating()) {
                        keyguardSecSimPinViewController.resetState();
                        return;
                    }
                    KeyguardSimPinViewController.CheckSimPin checkSimPin = keyguardSecSimPinViewController.mCheckSimPinThread;
                    if (checkSimPin != null) {
                        checkSimPin.interrupt();
                        keyguardSecSimPinViewController.mCheckSimPinThread = null;
                        keyguardSecSimPinViewController.mOkButton.setVisibility(0);
                        keyguardSecSimPinViewController.mProgressBar.setVisibility(8);
                        keyguardSecSimPinViewController.resetState();
                        keyguardSecSimPinViewController.verifyPasswordAndUnlock();
                        return;
                    }
                    return;
                }
                if (i3 == 4) {
                    if (keyguardSecurityCallback != null) {
                        keyguardSecurityCallback.dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardSecSimPinViewController.mSecurityMode, true);
                        return;
                    } else {
                        keyguardSecSimPinViewController.resetState();
                        return;
                    }
                }
                if (i3 != 5) {
                    keyguardSecSimPinViewController.resetState();
                    return;
                }
            }
            if (i3 == 1) {
                Log.i("KeyguardSecSimPinViewController", "Card Remove during SIM PIN ");
            } else if (i3 == 5) {
                Log.i("KeyguardSecSimPinViewController", "Card READY during SIM PIN ");
            }
            if (keyguardSecurityCallback != null && !((KeyguardSimPinViewController) keyguardSecSimPinViewController).mKeyguardUpdateMonitor.isSimState(2)) {
                Log.i("KeyguardSecSimPinViewController", "Dismiss SIM PIN View");
                keyguardSecurityCallback.dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardSecSimPinViewController.mSecurityMode, true);
            } else if (i3 == 5 && SubscriptionManager.isValidSubscriptionId(i) && keyguardSecSimPinViewController.mSubId != i) {
                Log.d("KeyguardSecSimPinViewController", "READY already came. Skip this");
            } else {
                keyguardSecSimPinViewController.resetState();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecSimPinViewController$3 */
    public final class C07403 extends SecCheckSimPin {
        public final /* synthetic */ KeyguardSecurityCallback val$keyguardSecurityCallback;
        public final /* synthetic */ int val$subId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07403(String str, int i, int i2, KeyguardSecurityCallback keyguardSecurityCallback) {
            super(str, i);
            this.val$subId = i2;
            this.val$keyguardSecurityCallback = keyguardSecurityCallback;
        }

        @Override // com.android.keyguard.KeyguardSimPinViewController.CheckSimPin
        public final void onSimCheckResponse(final PinResult pinResult) {
            KeyguardSecSimPinView keyguardSecSimPinView = (KeyguardSecSimPinView) KeyguardSecSimPinViewController.this.mView;
            final int i = this.val$subId;
            final KeyguardSecurityCallback keyguardSecurityCallback = this.val$keyguardSecurityCallback;
            keyguardSecSimPinView.post(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPinViewController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSecSimPinViewController.C07403 c07403 = KeyguardSecSimPinViewController.C07403.this;
                    PinResult pinResult2 = pinResult;
                    int i2 = i;
                    KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                    ((KeyguardSecSimPinView) KeyguardSecSimPinViewController.this.mView).resetPasswordText(true, pinResult2.getResult() != 0);
                    KeyguardSecSimPinViewController.this.setEnabledKeypad(true);
                    View view = KeyguardSecSimPinViewController.this.mOkButton;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                    KeyguardSecSimPinViewController.this.mProgressBar.setVisibility(8);
                    Log.d("KeyguardSecSimPinViewController", "verifyPasswordAndUnlock  CheckSimPin.onSimCheckResponse: " + pinResult2 + " attemptsRemaining=" + pinResult2.getAttemptsRemaining());
                    if (pinResult2.getResult() == 0) {
                        ((KeyguardSimPinViewController) KeyguardSecSimPinViewController.this).mKeyguardUpdateMonitor.reportSimUnlocked(i2);
                        KeyguardSecSimPinViewController keyguardSecSimPinViewController = KeyguardSecSimPinViewController.this;
                        keyguardSecSimPinViewController.mShowDefaultMessage = true;
                        if (LsRune.SECURITY_SIM_UNLOCK_TOAST) {
                            Toast.makeText(keyguardSecSimPinViewController.getContext(), R.string.kg_sim_lock_verified, 1).show();
                        }
                        KeyguardSecSimPinViewController.this.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecSimPinViewController.this.mSecurityMode, true);
                        if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode()) {
                            Log.d("KeyguardSecSimPinViewController", "ForceHideSoftInput");
                            KeyguardSecSimPinViewController.this.mInputMethodManager.semForceHideSoftInput();
                        }
                    } else {
                        KeyguardSecSimPinViewController.this.mShowDefaultMessage = false;
                        if (pinResult2.getResult() == 1) {
                            Log.i("KeyguardSecSimPinViewController", "verifyPasswordAndUnlock : PIN_RESULT_TYPE_INCORRECT");
                            if (pinResult2.getAttemptsRemaining() == 0) {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.setMessage("", false);
                            } else if (LsRune.SECURITY_LGU_USIM_TEXT && !((KeyguardSimPinViewController) KeyguardSecSimPinViewController.this).mKeyguardUpdateMonitor.isESimEmbedded()) {
                                int attemptsRemaining = 3 - pinResult2.getAttemptsRemaining();
                                KeyguardSecSimPinViewController keyguardSecSimPinViewController2 = KeyguardSecSimPinViewController.this;
                                keyguardSecSimPinViewController2.mMessageAreaController.setMessage(attemptsRemaining == 1 ? keyguardSecSimPinViewController2.getContext().getString(R.string.kg_lgu_sim_puk_1st_attempts) : keyguardSecSimPinViewController2.getContext().getString(R.string.kg_lgu_sim_puk_2nd_attempts), false);
                            } else if (LsRune.SECURITY_KTT_USIM_TEXT) {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.formatMessage(R.string.kg_kor_password_wrong_pin_code, Integer.valueOf(pinResult2.getAttemptsRemaining()));
                            } else if (pinResult2.getAttemptsRemaining() == 1) {
                                if (LsRune.SECURITY_USE_CDMA_CARD_TEXT) {
                                    KeyguardSecSimPinViewController keyguardSecSimPinViewController3 = KeyguardSecSimPinViewController.this;
                                    keyguardSecSimPinViewController3.mMessageAreaController.setMessage(keyguardSecSimPinViewController3.getContext().getString(R.string.kg_ctc_password_wrong_pin_code_one), false);
                                } else {
                                    KeyguardSecSimPinViewController keyguardSecSimPinViewController4 = KeyguardSecSimPinViewController.this;
                                    keyguardSecSimPinViewController4.mMessageAreaController.setMessage(keyguardSecSimPinViewController4.getContext().getString(R.string.kg_password_wrong_pin_code_one), false);
                                }
                            } else if (LsRune.SECURITY_USE_CDMA_CARD_TEXT) {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.formatMessage(R.string.kg_ctc_password_wrong_pin_code_other, Integer.valueOf(pinResult2.getAttemptsRemaining()));
                            } else {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.formatMessage(R.string.kg_password_wrong_pin_code_other, Integer.valueOf(pinResult2.getAttemptsRemaining()));
                            }
                        } else {
                            KeyguardSecSimPinViewController keyguardSecSimPinViewController5 = KeyguardSecSimPinViewController.this;
                            keyguardSecSimPinViewController5.mMessageAreaController.setMessage(keyguardSecSimPinViewController5.getContext().getString(R.string.kg_password_pin_failed), false);
                        }
                    }
                    if (keyguardSecurityCallback2 != null) {
                        keyguardSecurityCallback2.userActivity();
                    }
                    KeyguardSecSimPinViewController.this.mCheckSimPinThread = null;
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SecCheckSimPin extends KeyguardSimPinViewController.CheckSimPin {
        public static final /* synthetic */ int $r8$clinit = 0;

        public SecCheckSimPin(String str, int i) {
            super(str, i);
        }

        @Override // com.android.keyguard.KeyguardSimPinViewController.CheckSimPin, java.lang.Thread, java.lang.Runnable
        public final void run() {
            Log.i("KeyguardSecSimPinViewController", "call supplyPinReportResultForSubscriber(subid=" + this.mSubId + ")");
            TelephonyManager createForSubscriptionId = KeyguardSecSimPinViewController.this.mTelephonyManager.createForSubscriptionId(this.mSubId);
            SystemClock.elapsedRealtime();
            final PinResult supplyIccLockPin = createForSubscriptionId.supplyIccLockPin(this.mPin);
            SystemClock.elapsedRealtime();
            if (supplyIccLockPin == null) {
                Log.e("KeyguardSecSimPinViewController", "Error result for supplyPinReportResult.");
                ((KeyguardSecSimPinView) KeyguardSecSimPinViewController.this.mView).post(new KeyguardSecSimPinViewController$$ExternalSyntheticLambda1(this, 1));
            } else {
                Log.i("KeyguardSecSimPinViewController", "supplyPinReportResult returned: " + supplyIccLockPin.toString());
                ((KeyguardSecSimPinView) KeyguardSecSimPinViewController.this.mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPinViewController$SecCheckSimPin$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecSimPinViewController.SecCheckSimPin.this.onSimCheckResponse(supplyIccLockPin);
                    }
                });
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.KeyguardSecSimPinViewController$1] */
    public KeyguardSecSimPinViewController(KeyguardSecSimPinView keyguardSecSimPinView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController, InputMethodManager inputMethodManager) {
        super(keyguardSecSimPinView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, telephonyManager, falsingCollector, emergencyButtonController, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
        this.mOrientation = 1;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecSimPinViewController.this.updateSimIconImage();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecSimPinViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                boolean z;
                KeyguardSecSimPinViewController keyguardSecSimPinViewController = KeyguardSecSimPinViewController.this;
                int i = keyguardSecSimPinViewController.mOrientation;
                int i2 = configuration.orientation;
                boolean z2 = true;
                if (i != i2) {
                    keyguardSecSimPinViewController.mOrientation = i2;
                    z = true;
                } else {
                    z = false;
                }
                Locale locale = configuration.getLocales().get(0);
                if (locale == null || locale.equals(keyguardSecSimPinViewController.mLocale)) {
                    z2 = z;
                } else {
                    keyguardSecSimPinViewController.mLocale = locale;
                }
                if (z2) {
                    keyguardSecSimPinViewController.resetState();
                }
            }
        };
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor("emergency_mode"), Settings.Global.getUriFor("select_name_1"), Settings.Global.getUriFor("select_name_2")};
        this.mUpdateMonitorCallback = new C07392();
        this.mConfigurationController = configurationController;
        this.mInputMethodManager = inputMethodManager;
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        KeyguardSecESimArea keyguardSecESimArea = (KeyguardSecESimArea) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sec_esim_area);
        this.mESimSkipArea = keyguardSecESimArea;
        if (keyguardSecESimArea != null) {
            keyguardSecESimArea.mCallback = getKeyguardSecurityCallback();
            keyguardSecESimArea.mSecurityMode = this.mSecurityMode;
        }
        ProgressBar progressBar = (ProgressBar) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.progress);
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        updateProgressBarDrawable();
        this.mSimImageView = (ImageView) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sim_icon);
        this.mSimCardName = (SystemUITextView) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sim_name);
        View findViewById = ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sec_sim_info_view_container);
        if (findViewById != null) {
            findViewById.setVisibility(4);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_sim_pin_view;
    }

    @Override // com.android.keyguard.KeyguardSimPinViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        super.onPause();
    }

    @Override // com.android.keyguard.KeyguardSimPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        super.onResume(i);
    }

    @Override // com.android.keyguard.KeyguardSimPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((KeyguardSecSimPinView) this.mView).post(new KeyguardSecSimPinViewController$$ExternalSyntheticLambda1(this, 0));
        ((KeyguardSimPinViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mSettingsHelper.registerCallback(this.mSettingsListener, this.mSettingsValueList);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((KeyguardSimPinViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0189  */
    @Override // com.android.keyguard.KeyguardSimPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resetState() {
        int i;
        String string;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        PasswordTextView passwordTextView;
        ISemTelephony asInterface;
        super.resetState();
        Log.i("KeyguardSecSimPinViewController", "Resetting state");
        int nextSubIdForState = ((KeyguardSimPinViewController) this).mKeyguardUpdateMonitor.getNextSubIdForState(2);
        if (nextSubIdForState != this.mSubId && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
            this.mSubId = nextSubIdForState;
            this.mShowDefaultMessage = true;
        }
        KeyguardSecESimArea keyguardSecESimArea = this.mESimSkipArea;
        if (keyguardSecESimArea != null) {
            keyguardSecESimArea.mSubscriptionId = this.mSubId;
        }
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if (customSdkMonitor != null && customSdkMonitor.mKnoxCustomUnlockSimOnBootState) {
            CustomSdkMonitor customSdkMonitor2 = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
            String str = customSdkMonitor2 == null ? null : customSdkMonitor2.mUnlockSimPin;
            if (str != null) {
                verifyPasswordAndUnlock(str);
            }
        }
        if (this.mShowDefaultMessage) {
            if (!LsRune.SECURITY_SUB_DISPLAY_COVER || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("showDefaultMessage subId="), this.mSubId, "KeyguardSecSimPinViewController");
                if (!SubscriptionManager.isValidSubscriptionId(this.mSubId)) {
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("showDefaultMessage isValidSubscriptionId failed !!!  subid:"), this.mSubId, "KeyguardSecSimPinViewController");
                } else if (SecurityUtils.getSimSlotNum(this.mSubId) == -1) {
                    Log.d("KeyguardSecSimPinViewController", "showDefaultMessage - skip update");
                } else {
                    int i2 = this.mSubId;
                    try {
                        asInterface = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
                    } catch (Exception e) {
                        AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Exception: ", e, "KeyguardSecSimPinViewController");
                    }
                    if (asInterface != null) {
                        i = asInterface.getSimPinRetryForSubscriber(i2);
                        Log.i("KeyguardSecSimPinViewController", "getSimPinLockInfoResult(): num_of_retry is " + i);
                        Resources resources = getResources();
                        if (i != -1) {
                            if (i != 1) {
                                if (i != 3) {
                                    if (LsRune.SECURITY_KOR_USIM_TEXT) {
                                        string = resources.getString(R.string.kg_kor_sim_pin_instructions) + ". " + resources.getString(R.string.kg_sim_pin_remaining_attempts, Integer.valueOf(i));
                                    } else if (LsRune.SECURITY_USE_CDMA_CARD_TEXT) {
                                        string = resources.getString(R.string.kg_ctc_unlock_sim_pin_instructions) + ". " + resources.getString(R.string.kg_sim_pin_remaining_attempts, Integer.valueOf(i));
                                    } else {
                                        string = resources.getString(R.string.kg_unlock_sim_pin_remaining_attempts, Integer.valueOf(i));
                                    }
                                }
                            } else if (LsRune.SECURITY_KOR_USIM_TEXT) {
                                string = resources.getString(R.string.kg_kor_sim_pin_instructions) + ". " + resources.getString(R.string.kg_sim_pin_remaining_1_attempt);
                            } else if (LsRune.SECURITY_USE_CDMA_CARD_TEXT) {
                                string = resources.getString(R.string.kg_ctc_unlock_sim_pin_instructions) + ". " + resources.getString(R.string.kg_sim_pin_remaining_1_attempt);
                            } else {
                                string = resources.getString(R.string.kg_unlock_sim_pin_remaining_1_attempt);
                            }
                            keyguardSecMessageAreaController = this.mMessageAreaController;
                            if (keyguardSecMessageAreaController != null && ((passwordTextView = this.mPasswordEntry) == null || ((SecPasswordTextView) passwordTextView).mText.length() <= 0)) {
                                keyguardSecMessageAreaController.setMessage(string, false);
                                if (LsRune.SECURITY_KOR_USIM_TEXT && !TextUtils.isEmpty("")) {
                                    ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setContentDescription("");
                                }
                            }
                        }
                        string = !LsRune.SECURITY_KOR_USIM_TEXT ? resources.getString(R.string.kg_kor_sim_pin_instructions) : LsRune.SECURITY_USE_CDMA_CARD_TEXT ? resources.getString(R.string.kg_ctc_unlock_sim_pin_instructions) : resources.getString(R.string.kg_unlock_sim_pin_instructions);
                        keyguardSecMessageAreaController = this.mMessageAreaController;
                        if (keyguardSecMessageAreaController != null) {
                            keyguardSecMessageAreaController.setMessage(string, false);
                            if (LsRune.SECURITY_KOR_USIM_TEXT) {
                                ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setContentDescription("");
                            }
                        }
                    }
                    i = 3;
                    Log.i("KeyguardSecSimPinViewController", "getSimPinLockInfoResult(): num_of_retry is " + i);
                    Resources resources2 = getResources();
                    if (i != -1) {
                    }
                    if (!LsRune.SECURITY_KOR_USIM_TEXT) {
                    }
                    keyguardSecMessageAreaController = this.mMessageAreaController;
                    if (keyguardSecMessageAreaController != null) {
                    }
                }
            } else {
                Log.d("KeyguardSecSimPinViewController", "Skip updating showDefaultMessage when folder closed");
            }
            if (this.mSimImageView != null) {
                updateSimIconImage();
            }
        }
        updateESimLayout();
    }

    public final void updateESimLayout() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        KeyguardSecESimArea keyguardSecESimArea = this.mESimSkipArea;
        if (keyguardSecESimArea == null || keyguardSecESimArea.getVisibility() == 8 || (keyguardSecMessageAreaController = this.mMessageAreaController) == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController.getLayoutParams();
        if (this.mOrientation == 1) {
            marginLayoutParams.bottomMargin = (getResources().getDimensionPixelSize(R.dimen.keyguard_hint_text_padding_top) * 2) + this.mESimSkipArea.getHeight();
        } else {
            marginLayoutParams.bottomMargin = this.mESimSkipArea.getHeight();
        }
        keyguardSecMessageAreaController.setLayoutParams(marginLayoutParams);
    }

    public final void updateProgressBarDrawable() {
        this.mProgressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
    }

    public final void updateSimIconImage() {
        if (TelephonyManager.getDefault().getSimCount() <= 1 || !SubscriptionManager.isValidSubscriptionId(this.mSubId)) {
            return;
        }
        int simSlotNum = SecurityUtils.getSimSlotNum(this.mSubId);
        if (simSlotNum == -1) {
            Log.d("KeyguardSecSimPinViewController", "updateSimIconImage - skip update");
            return;
        }
        ImageView imageView = this.mSimImageView;
        if (imageView instanceof SystemUIImageView) {
            SystemUIImageView systemUIImageView = (SystemUIImageView) imageView;
            if (LsRune.SECURITY_ESIM && DeviceState.isESIM(simSlotNum, getContext()) && ((KeyguardSimPinViewController) this).mKeyguardUpdateMonitor.isESimEmbedded()) {
                Log.d("KeyguardSecSimPinViewController", "this is e-SIM");
                KeyguardSecESimArea keyguardSecESimArea = this.mESimSkipArea;
                if (keyguardSecESimArea != null) {
                    keyguardSecESimArea.setVisibility(0);
                }
                if (simSlotNum == 0) {
                    systemUIImageView.setOriginImage("lock_ic_pin_attempt_esim_01");
                    systemUIImageView.setWhiteBgImage("lock_ic_pin_attempt_esim_01_whitebg");
                } else if (simSlotNum == 1) {
                    systemUIImageView.setOriginImage("lock_ic_pin_attempt_esim_02");
                    systemUIImageView.setWhiteBgImage("lock_ic_pin_attempt_esim_02_whitebg");
                }
            } else {
                KeyguardSecESimArea keyguardSecESimArea2 = this.mESimSkipArea;
                if (keyguardSecESimArea2 != null) {
                    keyguardSecESimArea2.setVisibility(8);
                }
                if (simSlotNum == 0) {
                    systemUIImageView.setOriginImage("lock_ic_pin_attempt_sim_01");
                    systemUIImageView.setWhiteBgImage("lock_ic_pin_attempt_sim_01_whitebg");
                } else if (simSlotNum == 1) {
                    systemUIImageView.setOriginImage("lock_ic_pin_attempt_sim_02");
                    systemUIImageView.setWhiteBgImage("lock_ic_pin_attempt_sim_02_whitebg");
                }
            }
            systemUIImageView.updateImage();
        }
        SystemUITextView systemUITextView = this.mSimCardName;
        if (systemUITextView instanceof SystemUITextView) {
            String string = Settings.Global.getString(getContext().getContentResolver(), simSlotNum == 0 ? "select_name_1" : "select_name_2");
            boolean isEmpty = TextUtils.isEmpty(string);
            if (!isEmpty) {
                systemUITextView.setText(string);
            }
            systemUITextView.setVisibility(isEmpty ? 8 : 0);
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        initializeBottomContainerView();
        updateProgressBarDrawable();
    }

    @Override // com.android.keyguard.KeyguardSimPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView == null || !(passwordTextView instanceof SecPasswordTextView)) {
            return;
        }
        verifyPasswordAndUnlock(((SecPasswordTextView) passwordTextView).mText);
    }

    public final void verifyPasswordAndUnlock(String str) {
        KeyguardSecurityCallback keyguardSecurityCallback = getKeyguardSecurityCallback();
        boolean z = LsRune.SECURITY_KOR_USIM_TEXT;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (z && str.length() == 0) {
            keyguardSecMessageAreaController.setMessage(getContext().getString(R.string.kg_empty_sim_perso_hint), false);
            ((KeyguardSecSimPinView) this.mView).resetPasswordText(true, true);
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.userActivity();
                return;
            }
            return;
        }
        if (str.length() < 4) {
            if (z) {
                keyguardSecMessageAreaController.setMessage(getContext().getString(R.string.kg_kor_sim_pin_instructions), false);
            } else {
                keyguardSecMessageAreaController.setMessage(getContext().getString(R.string.kg_invalid_sim_pin_hint, 4, 8), false);
            }
            ((KeyguardSecSimPinView) this.mView).resetPasswordText(true, true);
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.userActivity();
                return;
            }
            return;
        }
        setEnabledKeypad(false);
        View view = this.mOkButton;
        if (view != null) {
            view.setVisibility(8);
        }
        this.mProgressBar.setVisibility(0);
        if (keyguardSecurityCallback != null) {
            keyguardSecurityCallback.userActivity();
        }
        if (this.mCheckSimPinThread == null) {
            C07403 c07403 = new C07403(str, this.mSubId, this.mSubId, keyguardSecurityCallback);
            this.mCheckSimPinThread = c07403;
            c07403.start();
        }
    }
}
