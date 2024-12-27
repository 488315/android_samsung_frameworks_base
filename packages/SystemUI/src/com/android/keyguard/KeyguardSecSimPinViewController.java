package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.net.Uri;
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
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecSimPinViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPinViewController;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import java.util.Locale;

public final class KeyguardSecSimPinViewController extends KeyguardSimPinViewController {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final KeyguardSecESimArea mESimSkipArea;
    public final InputMethodManager mInputMethodManager;
    public Locale mLocale;
    public int mOrientation;
    public final ProgressBar mProgressBar;
    private final SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final Uri[] mSettingsValueList;
    public final SystemUITextView mSimCardName;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* renamed from: com.android.keyguard.KeyguardSecSimPinViewController$2, reason: invalid class name */
    class AnonymousClass2 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass2() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            Log.i("KeyguardSecSimPinViewController", HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i, i3, "onSimStateChanged(subId=", ",state=", ")"));
            KeyguardSecSimPinViewController keyguardSecSimPinViewController = KeyguardSecSimPinViewController.this;
            KeyguardSecurityCallback keyguardSecurityCallback = keyguardSecSimPinViewController.getKeyguardSecurityCallback();
            if (i3 == 0) {
                if (LsRune.SECURITY_ESIM && ((KeyguardSimPinViewController) keyguardSecSimPinViewController).mKeyguardUpdateMonitor.isESimRemoveButtonClicked() && keyguardSecurityCallback != null) {
                    ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).executeRunnableDismissingKeyguard(new KeyguardSecSimPinViewController$$ExternalSyntheticLambda1(this, 1), null, false, false, false);
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
                        keyguardSecurityCallback.dismiss(true, keyguardSecSimPinViewController.mSelectedUserInteractor.getSelectedUserId(), keyguardSecSimPinViewController.mSecurityMode);
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
                keyguardSecurityCallback.dismiss(true, keyguardSecSimPinViewController.mSelectedUserInteractor.getSelectedUserId(), keyguardSecSimPinViewController.mSecurityMode);
            } else if (i3 == 5 && SubscriptionManager.isValidSubscriptionId(i) && keyguardSecSimPinViewController.mSubId != i) {
                Log.d("KeyguardSecSimPinViewController", "READY already came. Skip this");
            } else {
                keyguardSecSimPinViewController.resetState();
            }
        }
    }

    /* renamed from: com.android.keyguard.KeyguardSecSimPinViewController$3, reason: invalid class name */
    public final class AnonymousClass3 extends SecCheckSimPin {
        public final /* synthetic */ KeyguardSecurityCallback val$keyguardSecurityCallback;
        public final /* synthetic */ int val$subId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(String str, int i, int i2, KeyguardSecurityCallback keyguardSecurityCallback) {
            super(str, i);
            this.val$subId = i2;
            this.val$keyguardSecurityCallback = keyguardSecurityCallback;
        }

        @Override // com.android.keyguard.KeyguardSimPinViewController.CheckSimPin
        public final void onSimCheckResponse(final PinResult pinResult) {
            KeyguardSecSimPinView keyguardSecSimPinView = (KeyguardSecSimPinView) ((ViewController) KeyguardSecSimPinViewController.this).mView;
            final int i = this.val$subId;
            final KeyguardSecurityCallback keyguardSecurityCallback = this.val$keyguardSecurityCallback;
            keyguardSecSimPinView.post(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPinViewController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    View view;
                    KeyguardSecSimPinViewController.AnonymousClass3 anonymousClass3 = KeyguardSecSimPinViewController.AnonymousClass3.this;
                    PinResult pinResult2 = pinResult;
                    int i2 = i;
                    KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                    view = ((ViewController) KeyguardSecSimPinViewController.this).mView;
                    ((KeyguardSecSimPinView) view).resetPasswordText(true, pinResult2.getResult() != 0);
                    KeyguardSecSimPinViewController.this.setEnabledKeypad(true);
                    View view2 = KeyguardSecSimPinViewController.this.mOkButton;
                    if (view2 != null) {
                        view2.setVisibility(0);
                    }
                    KeyguardSecSimPinViewController.this.mProgressBar.setVisibility(8);
                    Log.d("KeyguardSecSimPinViewController", "verifyPasswordAndUnlock  CheckSimPin.onSimCheckResponse: " + pinResult2 + " attemptsRemaining=" + pinResult2.getAttemptsRemaining());
                    if (pinResult2.getResult() == 0) {
                        ((KeyguardSimPinViewController) KeyguardSecSimPinViewController.this).mKeyguardUpdateMonitor.reportSimUnlocked(i2);
                        KeyguardSecSimPinViewController keyguardSecSimPinViewController = KeyguardSecSimPinViewController.this;
                        keyguardSecSimPinViewController.mShowDefaultMessage = true;
                        keyguardSecSimPinViewController.getKeyguardSecurityCallback().dismiss(true, KeyguardSecSimPinViewController.this.mSelectedUserInteractor.getSelectedUserId(false), KeyguardSecSimPinViewController.this.mSecurityMode);
                        if (((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isDesktopMode()) {
                            Log.d("KeyguardSecSimPinViewController", "ForceHideSoftInput");
                            KeyguardSecSimPinViewController.this.mInputMethodManager.semForceHideSoftInput();
                        }
                    } else {
                        KeyguardSecSimPinViewController.this.mShowDefaultMessage = false;
                        if (pinResult2.getResult() == 1) {
                            Log.i("KeyguardSecSimPinViewController", "verifyPasswordAndUnlock : PIN_RESULT_TYPE_INCORRECT");
                            if (pinResult2.getAttemptsRemaining() == 0) {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.setMessage$1("", false);
                            } else if (CscRune.SECURITY_LGU_USIM_TEXT && !((KeyguardSimPinViewController) KeyguardSecSimPinViewController.this).mKeyguardUpdateMonitor.isESimEmbedded()) {
                                int attemptsRemaining = 3 - pinResult2.getAttemptsRemaining();
                                KeyguardSecSimPinViewController keyguardSecSimPinViewController2 = KeyguardSecSimPinViewController.this;
                                keyguardSecSimPinViewController2.mMessageAreaController.setMessage$1(attemptsRemaining == 1 ? keyguardSecSimPinViewController2.getContext().getString(R.string.kg_lgu_sim_puk_1st_attempts) : keyguardSecSimPinViewController2.getContext().getString(R.string.kg_lgu_sim_puk_2nd_attempts), false);
                            } else if (CscRune.SECURITY_KTT_USIM_TEXT) {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.formatMessage(R.string.kg_kor_password_wrong_pin_code, Integer.valueOf(pinResult2.getAttemptsRemaining()));
                            } else if (pinResult2.getAttemptsRemaining() == 1) {
                                if (CscRune.SECURITY_USE_CDMA_CARD_TEXT) {
                                    KeyguardSecSimPinViewController keyguardSecSimPinViewController3 = KeyguardSecSimPinViewController.this;
                                    keyguardSecSimPinViewController3.mMessageAreaController.setMessage$1(keyguardSecSimPinViewController3.getContext().getString(R.string.kg_ctc_password_wrong_pin_code_one), false);
                                } else {
                                    KeyguardSecSimPinViewController keyguardSecSimPinViewController4 = KeyguardSecSimPinViewController.this;
                                    keyguardSecSimPinViewController4.mMessageAreaController.setMessage$1(keyguardSecSimPinViewController4.getContext().getString(R.string.kg_password_wrong_pin_code_one), false);
                                }
                            } else if (CscRune.SECURITY_USE_CDMA_CARD_TEXT) {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.formatMessage(R.string.kg_ctc_password_wrong_pin_code_other, Integer.valueOf(pinResult2.getAttemptsRemaining()));
                            } else {
                                KeyguardSecSimPinViewController.this.mMessageAreaController.formatMessage(R.string.kg_password_wrong_pin_code_other, Integer.valueOf(pinResult2.getAttemptsRemaining()));
                            }
                        } else {
                            KeyguardSecSimPinViewController keyguardSecSimPinViewController5 = KeyguardSecSimPinViewController.this;
                            keyguardSecSimPinViewController5.mMessageAreaController.setMessage$1(keyguardSecSimPinViewController5.getContext().getString(R.string.kg_password_pin_failed), false);
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

    public abstract class SecCheckSimPin extends KeyguardSimPinViewController.CheckSimPin {
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
                ((KeyguardSecSimPinView) ((ViewController) KeyguardSecSimPinViewController.this).mView).post(new KeyguardSecSimPinViewController$$ExternalSyntheticLambda1(this, 2));
            } else {
                Log.i("KeyguardSecSimPinViewController", "supplyPinReportResult returned: " + supplyIccLockPin.toString());
                ((KeyguardSecSimPinView) ((ViewController) KeyguardSecSimPinViewController.this).mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPinViewController$SecCheckSimPin$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecSimPinViewController.SecCheckSimPin.this.onSimCheckResponse(supplyIccLockPin);
                    }
                });
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.KeyguardSecSimPinViewController$1] */
    public KeyguardSecSimPinViewController(KeyguardSecSimPinView keyguardSecSimPinView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor, InputMethodManager inputMethodManager) {
        super(keyguardSecSimPinView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, telephonyManager, falsingCollector, emergencyButtonController, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor);
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

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                FontSizeUtils.updateFontSize(KeyguardSecSimPinViewController.this.mSimCardName, R.dimen.kg_sim_pin_name_font_size, 0.8f, 1.0f);
            }
        };
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE), Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_1), Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_2)};
        this.mUpdateMonitorCallback = new AnonymousClass2();
        this.mConfigurationController = configurationController;
        this.mInputMethodManager = inputMethodManager;
        this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        KeyguardSecESimArea keyguardSecESimArea = (KeyguardSecESimArea) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sec_esim_area);
        this.mESimSkipArea = keyguardSecESimArea;
        if (keyguardSecESimArea != null) {
            keyguardSecESimArea.mCallback = getKeyguardSecurityCallback();
            keyguardSecESimArea.mSecurityMode = this.mSecurityMode;
            keyguardSecESimArea.mSelectedUserInteractor = this.mSelectedUserInteractor;
        }
        ProgressBar progressBar = (ProgressBar) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.progress);
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        progressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
        this.mSimImageView = (ImageView) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sim_icon);
        this.mSimCardName = (SystemUITextView) ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sim_name);
        setSimInfoViewVisibility(4);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_sim_pin_view;
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
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

    /* JADX WARN: Removed duplicated region for block: B:36:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0170  */
    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resetState() {
        /*
            Method dump skipped, instructions count: 447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecSimPinViewController.resetState():void");
    }

    public final void setSimInfoViewVisibility(int i) {
        View findViewById = ((KeyguardSecSimPinView) this.mView).findViewById(R.id.keyguard_sec_sim_info_view_container);
        if (findViewById != null) {
            findViewById.setVisibility(i);
        }
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
            if (LsRune.SECURITY_ESIM && DeviceState.isESIM(getContext(), simSlotNum) && ((KeyguardSimPinViewController) this).mKeyguardUpdateMonitor.isESimEmbedded()) {
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
            String string = Settings.Global.getString(getContext().getContentResolver(), simSlotNum == 0 ? SettingsHelper.INDEX_SIM_SELECT_NAME_1 : SettingsHelper.INDEX_SIM_SELECT_NAME_2);
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
        this.mProgressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
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
        boolean z = CscRune.SECURITY_KOR_USIM_TEXT;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (z && str.length() == 0) {
            keyguardSecMessageAreaController.setMessage$1(getContext().getString(R.string.kg_empty_sim_perso_hint), false);
            ((KeyguardSecSimPinView) this.mView).resetPasswordText(true, true);
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.userActivity();
                return;
            }
            return;
        }
        if (str.length() < 4) {
            if (CscRune.SECURITY_KOR_USIM_TEXT) {
                keyguardSecMessageAreaController.setMessage$1(getContext().getString(R.string.kg_kor_sim_pin_instructions), false);
            } else {
                keyguardSecMessageAreaController.setMessage$1(getContext().getString(R.string.kg_invalid_sim_pin_hint, 4, 8), false);
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
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(str, this.mSubId, this.mSubId, keyguardSecurityCallback);
            this.mCheckSimPinThread = anonymousClass3;
            anonymousClass3.start();
        }
    }
}
