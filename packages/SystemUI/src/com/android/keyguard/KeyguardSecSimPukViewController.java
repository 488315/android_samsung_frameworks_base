package com.android.keyguard;

import android.app.AlertDialog;
import android.app.SemWallpaperColors;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.PinResult;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecSimPukViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPukViewController;
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
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSecSimPukViewController extends KeyguardSimPukViewController {
    public AlertDialog mCarrierDialog;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSecSimPukViewController$2, reason: invalid class name */
    class AnonymousClass2 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass2() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            Log.i("KeyguardSecSimPukViewController", HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i, i3, "onSimStateChanged(subId=", ",state=", ")"));
            KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
            KeyguardSecurityCallback keyguardSecurityCallback = keyguardSecSimPukViewController.getKeyguardSecurityCallback();
            if (i3 == 0) {
                if (LsRune.SECURITY_ESIM && ((KeyguardSimPukViewController) keyguardSecSimPukViewController).mKeyguardUpdateMonitor.isESimRemoveButtonClicked() && keyguardSecurityCallback != null) {
                    ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).executeRunnableDismissingKeyguard(new KeyguardSecSimPukViewController$$ExternalSyntheticLambda0(this, 1), null, false, false, false);
                    return;
                } else {
                    keyguardSecSimPukViewController.resetState();
                    return;
                }
            }
            if (i3 != 1) {
                if (i3 == 3) {
                    ProgressBar progressBar = keyguardSecSimPukViewController.mProgressBar;
                    if (progressBar == null || !progressBar.isAnimating()) {
                        keyguardSecSimPukViewController.resetState();
                        return;
                    }
                    KeyguardSimPukViewController.CheckSimPuk checkSimPuk = keyguardSecSimPukViewController.mCheckSimPukThread;
                    if (checkSimPuk != null) {
                        checkSimPuk.interrupt();
                        keyguardSecSimPukViewController.mCheckSimPukThread = null;
                        keyguardSecSimPukViewController.mOkButton.setVisibility(0);
                        keyguardSecSimPukViewController.mProgressBar.setVisibility(8);
                        keyguardSecSimPukViewController.resetState();
                        keyguardSecSimPukViewController.verifyPasswordAndUnlock();
                        return;
                    }
                    return;
                }
                if (i3 == 4) {
                    if (keyguardSecurityCallback != null) {
                        keyguardSecurityCallback.dismiss(true, keyguardSecSimPukViewController.mSelectedUserInteractor.getSelectedUserId(), keyguardSecSimPukViewController.mSecurityMode);
                        return;
                    } else {
                        keyguardSecSimPukViewController.resetState();
                        return;
                    }
                }
                if (i3 != 5) {
                    keyguardSecSimPukViewController.resetState();
                    return;
                }
            }
            if (i3 == 1) {
                Log.i("KeyguardSecSimPukViewController", "Card Remove during SIM PUK ");
            } else if (i3 == 5) {
                Log.i("KeyguardSecSimPukViewController", "Card READY during SIM PUK ");
            }
            if (keyguardSecurityCallback != null && !((KeyguardSimPukViewController) keyguardSecSimPukViewController).mKeyguardUpdateMonitor.isSimState(3)) {
                Log.i("KeyguardSecSimPukViewController", "Dismiss SIM PUK View");
                keyguardSecurityCallback.dismiss(true, keyguardSecSimPukViewController.mSelectedUserInteractor.getSelectedUserId(), keyguardSecSimPukViewController.mSecurityMode);
            } else if (i3 == 5 && SubscriptionManager.isValidSubscriptionId(i) && keyguardSecSimPukViewController.mSubId != i) {
                Log.d("KeyguardSecSimPukViewController", "READY already came. Skip this");
            } else {
                keyguardSecSimPukViewController.resetState();
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSecSimPukViewController$3, reason: invalid class name */
    public final class AnonymousClass3 extends KeyguardSimPukViewController.CheckSimPuk {
        public AnonymousClass3(String str, String str2, int i) {
            super(str, str2, i);
        }

        @Override // com.android.keyguard.KeyguardSimPukViewController.CheckSimPuk
        public final void onSimLockChangedResponse(final PinResult pinResult) {
            ((KeyguardSecSimPukView) ((ViewController) KeyguardSecSimPukViewController.this).mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPukViewController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    View view;
                    View view2;
                    KeyguardSecSimPukViewController.AnonymousClass3 anonymousClass3 = KeyguardSecSimPukViewController.AnonymousClass3.this;
                    PinResult pinResult2 = pinResult;
                    view = ((ViewController) KeyguardSecSimPukViewController.this).mView;
                    ((KeyguardSecSimPukView) view).resetPasswordText(true, pinResult2.getResult() != 0);
                    KeyguardSecSimPukViewController.this.setEnabledKeypad(true);
                    KeyguardSecSimPukViewController.this.mOkButton.setVisibility(0);
                    KeyguardSecSimPukViewController.this.mProgressBar.setVisibility(8);
                    if (pinResult2.getResult() == 0) {
                        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).reportSimUnlocked(anonymousClass3.mSubId);
                        KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
                        keyguardSecSimPukViewController.mRemainingAttempts = -1;
                        keyguardSecSimPukViewController.mShowDefaultMessage = true;
                        if (CscRune.SECURITY_KOR_USIM_TEXT) {
                            String string = keyguardSecSimPukViewController.getContext().getString(R.string.kg_kor_success_pin_message);
                            AlertDialog alertDialog = keyguardSecSimPukViewController.mCarrierDialog;
                            if (alertDialog == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(keyguardSecSimPukViewController.getContext(), R.style.Theme_SystemUI_DayNightDialog);
                                builder.setMessage(string);
                                builder.setCancelable(false);
                                builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
                                AlertDialog create = builder.create();
                                keyguardSecSimPukViewController.mCarrierDialog = create;
                                Window window = create.getWindow();
                                Objects.requireNonNull(window);
                                window.setType(2009);
                            } else {
                                alertDialog.setMessage(string);
                            }
                            keyguardSecSimPukViewController.mCarrierDialog.show();
                        }
                        if (KeyguardSecSimPukViewController.this.getKeyguardSecurityCallback() != null) {
                            if (((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isDesktopMode()) {
                                Log.d("KeyguardSecSimPukViewController", "ForceHideSoftInput");
                                KeyguardSecSimPukViewController.this.mInputMethodManager.semForceHideSoftInput();
                            }
                            KeyguardSecSimPukViewController.this.getKeyguardSecurityCallback().dismiss(true, KeyguardSecSimPukViewController.this.mSelectedUserInteractor.getSelectedUserId(false), KeyguardSecSimPukViewController.this.mSecurityMode);
                        }
                    } else {
                        KeyguardSecSimPukViewController.this.mShowDefaultMessage = false;
                        if (pinResult2.getResult() == 1) {
                            KeyguardSecSimPukViewController keyguardSecSimPukViewController2 = KeyguardSecSimPukViewController.this;
                            KeyguardSecMessageAreaController keyguardSecMessageAreaController = keyguardSecSimPukViewController2.mMessageAreaController;
                            view2 = ((ViewController) keyguardSecSimPukViewController2).mView;
                            KeyguardSecSimPukView keyguardSecSimPukView = (KeyguardSecSimPukView) view2;
                            int attemptsRemaining = pinResult2.getAttemptsRemaining();
                            keyguardSecSimPukView.getClass();
                            boolean isESimEmbedded = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isESimEmbedded();
                            String string2 = attemptsRemaining == 0 ? keyguardSecSimPukView.getContext().getString(R.string.kg_password_wrong_puk_code_dead) : attemptsRemaining > 1 ? (!CscRune.SECURITY_LGU_USIM_TEXT || isESimEmbedded) ? CscRune.SECURITY_KOR_USIM_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_kor_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), Integer.valueOf(attemptsRemaining)) : CscRune.SECURITY_USE_CDMA_CARD_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_ctc_sim_puk_remaining_attempts, Integer.valueOf(attemptsRemaining)) : keyguardSecSimPukView.getContext().getString(R.string.kg_sim_puk_remaining_attempts, Integer.valueOf(attemptsRemaining)) : keyguardSecSimPukView.getContext().getString(R.string.kg_lgu_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), 10) : attemptsRemaining == 1 ? (!CscRune.SECURITY_LGU_USIM_TEXT || isESimEmbedded) ? CscRune.SECURITY_KOR_USIM_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_kor_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), Integer.valueOf(attemptsRemaining)) : CscRune.SECURITY_USE_CDMA_CARD_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_ctc_sim_puk_remaining_1_attempt) : keyguardSecSimPukView.getContext().getString(R.string.kg_sim_puk_remaining_1_attempt) : keyguardSecSimPukView.getContext().getString(R.string.kg_lgu_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), 10) : keyguardSecSimPukView.getContext().getString(R.string.kg_password_puk_failed);
                            Log.d("KeyguardSimPukView", "getPukPasswordErrorMessage: attemptsRemaining=" + attemptsRemaining + " displayMessage=" + string2);
                            keyguardSecMessageAreaController.setMessage$1(string2, false);
                        } else {
                            KeyguardSecSimPukViewController keyguardSecSimPukViewController3 = KeyguardSecSimPukViewController.this;
                            keyguardSecSimPukViewController3.mMessageAreaController.setMessage$1(keyguardSecSimPukViewController3.getContext().getString(R.string.kg_password_puk_failed), false);
                        }
                        Log.d("KeyguardSecSimPukViewController", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + pinResult2.getAttemptsRemaining());
                        KeyguardSecSimPukViewController.this.mStateMachine.reset();
                    }
                    KeyguardSecSimPukViewController.this.mCheckSimPukThread = null;
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SecStateMachine extends KeyguardSimPukViewController.StateMachine {
        public /* synthetic */ SecStateMachine(KeyguardSecSimPukViewController keyguardSecSimPukViewController, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardSimPukViewController.StateMachine
        public final void next() {
            int i;
            int i2 = this.mState;
            KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
            if (i2 != 0) {
                i = R.string.kg_empty_sim_perso_hint;
                if (i2 != 1) {
                    if (i2 != 2) {
                        i = 0;
                    } else if (keyguardSecSimPukViewController.confirmPin()) {
                        this.mState = 3;
                        keyguardSecSimPukViewController.setEnabledKeypad(false);
                        keyguardSecSimPukViewController.mOkButton.setVisibility(8);
                        keyguardSecSimPukViewController.mProgressBar.setVisibility(0);
                        keyguardSecSimPukViewController.updateSim();
                        i = R.string.keyguard_sim_unlock_progress_dialog_message;
                    } else {
                        this.mState = 1;
                        if (!CscRune.SECURITY_KOR_USIM_TEXT) {
                            i = R.string.kg_invalid_confirm_pin_hint;
                        } else if (keyguardSecSimPukViewController.getPasswordText() != null && keyguardSecSimPukViewController.getPasswordText().length != 0) {
                            i = R.string.kg_kor_invalid_confirm_pin_hint;
                        }
                    }
                } else if (keyguardSecSimPukViewController.checkPin()) {
                    this.mState = 2;
                    i = CscRune.SECURITY_KOR_USIM_TEXT ? R.string.kg_kor_enter_confirm_pin_hint : CscRune.SECURITY_USE_CDMA_CARD_TEXT ? R.string.kg_ctc_enter_confirm_pin_hint : R.string.kg_sec_puk_enter_confirm_pin_hint;
                } else if (!CscRune.SECURITY_KOR_USIM_TEXT) {
                    i = R.string.kg_invalid_sim_pin_hint;
                } else if (keyguardSecSimPukViewController.getPasswordText() != null && keyguardSecSimPukViewController.getPasswordText().length != 0) {
                    i = R.string.kg_kor_sim_pin_instructions;
                }
            } else if (keyguardSecSimPukViewController.checkPuk()) {
                this.mState = 1;
                i = CscRune.SECURITY_KOR_USIM_TEXT ? R.string.kg_kor_puk_enter_pin_hint : CscRune.SECURITY_USE_CDMA_CARD_TEXT ? R.string.kg_ctc_puk_enter_pin_hint : R.string.kg_sec_puk_enter_pin_hint;
            } else {
                i = CscRune.SECURITY_KOR_USIM_TEXT ? (keyguardSecSimPukViewController.getPasswordText() == null || keyguardSecSimPukViewController.getPasswordText().length == 0) ? R.string.kg_kor_empty_sim_puk_hint : R.string.kg_kor_invalid_sim_puk_hint : R.string.kg_sec_invalid_sim_puk_hint;
            }
            ((KeyguardSecSimPukView) ((ViewController) keyguardSecSimPukViewController).mView).resetPasswordText(true, true);
            if (i != 0) {
                if (i != R.string.kg_invalid_sim_pin_hint) {
                    keyguardSecSimPukViewController.mMessageAreaController.setMessage$1(keyguardSecSimPukViewController.getContext().getString(i), false);
                } else {
                    keyguardSecSimPukViewController.mMessageAreaController.setMessage$1(keyguardSecSimPukViewController.getContext().getString(R.string.kg_invalid_sim_pin_hint, 4, 8), false);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardSimPukViewController.StateMachine
        public final void reset() {
            Log.d("KeyguardSecSimPukViewController", "reset()");
            KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
            keyguardSecSimPukViewController.mPinText = "";
            keyguardSecSimPukViewController.mPukText = "";
            this.mState = 0;
            int nextSubIdForState = ((KeyguardSimPukViewController) keyguardSecSimPukViewController).mKeyguardUpdateMonitor.getNextSubIdForState(3);
            if (nextSubIdForState != keyguardSecSimPukViewController.mSubId && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
                keyguardSecSimPukViewController.mSubId = nextSubIdForState;
                keyguardSecSimPukViewController.mShowDefaultMessage = true;
                keyguardSecSimPukViewController.mRemainingAttempts = -1;
            }
            KeyguardSecESimArea keyguardSecESimArea = keyguardSecSimPukViewController.mESimSkipArea;
            if (keyguardSecESimArea != null) {
                keyguardSecESimArea.mSubscriptionId = keyguardSecSimPukViewController.mSubId;
            }
            PasswordTextView passwordTextView = keyguardSecSimPukViewController.mPasswordEntry;
            if (passwordTextView != null) {
                passwordTextView.requestFocus();
            }
        }

        private SecStateMachine() {
            super();
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.KeyguardSecSimPukViewController$1] */
    public KeyguardSecSimPukViewController(KeyguardSecSimPukView keyguardSecSimPukView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor, InputMethodManager inputMethodManager) {
        super(keyguardSecSimPukView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, telephonyManager, falsingCollector, emergencyButtonController, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor);
        this.mOrientation = 1;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecSimPukViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecSimPukViewController.this.updateSimIconImage$1();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecSimPukViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                boolean z;
                KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
                int i = keyguardSecSimPukViewController.mOrientation;
                int i2 = configuration.orientation;
                boolean z2 = true;
                if (i != i2) {
                    keyguardSecSimPukViewController.mOrientation = i2;
                    z = true;
                } else {
                    z = false;
                }
                Locale locale = configuration.getLocales().get(0);
                if (locale == null || locale.equals(keyguardSecSimPukViewController.mLocale)) {
                    z2 = z;
                } else {
                    keyguardSecSimPukViewController.mLocale = locale;
                }
                if (z2) {
                    keyguardSecSimPukViewController.resetState();
                }
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                FontSizeUtils.updateFontSize(KeyguardSecSimPukViewController.this.mSimCardName, R.dimen.kg_sim_pin_name_font_size, 0.8f, 1.0f);
            }
        };
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE), Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_1), Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_2)};
        this.mUpdateMonitorCallback = new AnonymousClass2();
        this.mConfigurationController = configurationController;
        this.mInputMethodManager = inputMethodManager;
        this.mStateMachine = new SecStateMachine(this, 0);
        this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        KeyguardSecESimArea keyguardSecESimArea = (KeyguardSecESimArea) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sec_esim_area);
        this.mESimSkipArea = keyguardSecESimArea;
        if (keyguardSecESimArea != null) {
            keyguardSecESimArea.mCallback = getKeyguardSecurityCallback();
            keyguardSecESimArea.mSecurityMode = this.mSecurityMode;
            keyguardSecESimArea.mSelectedUserInteractor = this.mSelectedUserInteractor;
        }
        ProgressBar progressBar = (ProgressBar) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.progress);
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        progressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
        this.mSimImageView = (ImageView) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sim_icon);
        this.mSimCardName = (SystemUITextView) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sim_name);
        setSimInfoViewVisibility$1(4);
    }

    @Override // com.android.keyguard.KeyguardSimPukViewController
    public final boolean checkPin() {
        int length;
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView == null || !(passwordTextView instanceof SecPasswordTextView) || (length = ((SecPasswordTextView) passwordTextView).mText.length()) < 4 || length > 8) {
            return false;
        }
        this.mPinText = ((SecPasswordTextView) passwordTextView).mText;
        return true;
    }

    @Override // com.android.keyguard.KeyguardSimPukViewController
    public final boolean checkPuk() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView == null || !(passwordTextView instanceof SecPasswordTextView) || ((SecPasswordTextView) passwordTextView).mText.length() != 8) {
            return false;
        }
        this.mPukText = ((SecPasswordTextView) passwordTextView).mText;
        return true;
    }

    @Override // com.android.keyguard.KeyguardSimPukViewController
    public final boolean confirmPin() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView == null) {
            return false;
        }
        return passwordTextView instanceof SecPasswordTextView ? this.mPinText.equals(((SecPasswordTextView) passwordTextView).mText) : this.mPinText.equals(passwordTextView.getText().toString());
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_sim_puk_view;
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((KeyguardSecSimPukView) this.mView).post(new KeyguardSecSimPukViewController$$ExternalSyntheticLambda0(this, 0));
        ((KeyguardSimPukViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mSettingsHelper.registerCallback(this.mSettingsListener, this.mSettingsValueList);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((KeyguardSimPukViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
    }

    @Override // com.android.keyguard.KeyguardSimPukViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        super.resetState();
        showDefaultMessage();
        if (this.mSimImageView != null) {
            updateSimIconImage$1();
        }
        updateESimLayout$1();
    }

    public final void setSimInfoViewVisibility$1(int i) {
        View findViewById = ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sec_sim_info_view_container);
        if (findViewById != null) {
            findViewById.setVisibility(i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00fa  */
    @Override // com.android.keyguard.KeyguardSimPukViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showDefaultMessage() {
        /*
            Method dump skipped, instructions count: 359
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecSimPukViewController.showDefaultMessage():void");
    }

    public final void updateESimLayout$1() {
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

    @Override // com.android.keyguard.KeyguardSimPukViewController
    public final void updateSim() {
        if (this.mCheckSimPukThread == null) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.mPukText, this.mPinText, this.mSubId);
            this.mCheckSimPukThread = anonymousClass3;
            anonymousClass3.start();
        }
    }

    public final void updateSimIconImage$1() {
        if (TelephonyManager.getDefault().getSimCount() <= 1 || !SubscriptionManager.isValidSubscriptionId(this.mSubId)) {
            return;
        }
        int simSlotNum = SecurityUtils.getSimSlotNum(this.mSubId);
        if (simSlotNum == -1) {
            Log.d("KeyguardSecSimPukViewController", "updateSimIconImage - skip update");
            return;
        }
        ImageView imageView = this.mSimImageView;
        if (imageView instanceof SystemUIImageView) {
            SystemUIImageView systemUIImageView = (SystemUIImageView) imageView;
            if (LsRune.SECURITY_ESIM && DeviceState.isESIM(getContext(), simSlotNum) && ((KeyguardSimPukViewController) this).mKeyguardUpdateMonitor.isESimEmbedded()) {
                Log.d("KeyguardSecSimPukViewController", "this is e-SIM");
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

    @Override // com.android.keyguard.KeyguardSimPukViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        Log.d("KeyguardSecSimPukViewController", "verifyPasswordAndUnlock next");
        super.verifyPasswordAndUnlock();
    }
}
