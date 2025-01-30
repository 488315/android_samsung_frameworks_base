package com.android.keyguard;

import android.app.AlertDialog;
import android.app.SemWallpaperColors;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.ServiceManager;
import android.provider.Settings;
import android.telephony.PinResult;
import android.telephony.SubscriptionInfo;
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
import android.widget.Toast;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.internal.telephony.ISemTelephony;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecSimPukViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPukViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
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
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecSimPukViewController extends KeyguardSimPukViewController {
    public AlertDialog mCarrierDialog;
    public final ConfigurationController mConfigurationController;
    public final C07411 mConfigurationListener;
    public final KeyguardSecESimArea mESimSkipArea;
    public final InputMethodManager mInputMethodManager;
    public Locale mLocale;
    public int mOrientation;
    public final ProgressBar mProgressBar;
    public final SettingsHelper mSettingsHelper;
    public final KeyguardSecSimPukViewController$$ExternalSyntheticLambda1 mSettingsListener;
    public final Uri[] mSettingsValueList;
    public final SystemUITextView mSimCardName;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecSimPukViewController$2 */
    public final class C07422 extends KeyguardUpdateMonitorCallback {
        public C07422() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            Log.i("KeyguardSecSimPukViewController", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("onSimStateChanged(subId=", i, ",state=", i3, ")"));
            KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
            KeyguardSecurityCallback keyguardSecurityCallback = keyguardSecSimPukViewController.getKeyguardSecurityCallback();
            if (i3 == 0) {
                if (LsRune.SECURITY_ESIM && ((KeyguardSimPukViewController) keyguardSecSimPukViewController).mKeyguardUpdateMonitor.isESimRemoveButtonClicked() && keyguardSecurityCallback != null) {
                    ((ActivityStarter) Dependency.get(ActivityStarter.class)).executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPukViewController$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardSecSimPukViewController keyguardSecSimPukViewController2 = KeyguardSecSimPukViewController.this;
                            if (keyguardSecSimPukViewController2.mESimSkipArea != null) {
                                Log.d("KeyguardSecSimPukViewController", "eraseSubscriptions");
                                keyguardSecSimPukViewController2.mESimSkipArea.eraseSubscriptions();
                            }
                        }
                    }, null, false, false, false);
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
                        keyguardSecurityCallback.dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardSecSimPukViewController.mSecurityMode, true);
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
                keyguardSecurityCallback.dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardSecSimPukViewController.mSecurityMode, true);
            } else if (i3 == 5 && SubscriptionManager.isValidSubscriptionId(i) && keyguardSecSimPukViewController.mSubId != i) {
                Log.d("KeyguardSecSimPukViewController", "READY already came. Skip this");
            } else {
                keyguardSecSimPukViewController.resetState();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecSimPukViewController$3 */
    public final class C07433 extends KeyguardSimPukViewController.CheckSimPuk {
        public C07433(String str, String str2, int i) {
            super(str, str2, i);
        }

        @Override // com.android.keyguard.KeyguardSimPukViewController.CheckSimPuk
        public final void onSimLockChangedResponse(final PinResult pinResult) {
            ((KeyguardSecSimPukView) KeyguardSecSimPukViewController.this.mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPukViewController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSecSimPukViewController.C07433 c07433 = KeyguardSecSimPukViewController.C07433.this;
                    PinResult pinResult2 = pinResult;
                    ((KeyguardSecSimPukView) KeyguardSecSimPukViewController.this.mView).resetPasswordText(true, pinResult2.getResult() != 0);
                    KeyguardSecSimPukViewController.this.setEnabledKeypad(true);
                    KeyguardSecSimPukViewController.this.mOkButton.setVisibility(0);
                    KeyguardSecSimPukViewController.this.mProgressBar.setVisibility(8);
                    if (pinResult2.getResult() == 0) {
                        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).reportSimUnlocked(c07433.mSubId);
                        KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
                        keyguardSecSimPukViewController.mRemainingAttempts = -1;
                        keyguardSecSimPukViewController.mShowDefaultMessage = true;
                        if (LsRune.SECURITY_SIM_UNLOCK_TOAST) {
                            Toast.makeText(keyguardSecSimPukViewController.getContext(), R.string.kg_sim_lock_accepted, 1).show();
                        } else if (LsRune.SECURITY_KOR_USIM_TEXT) {
                            String string = keyguardSecSimPukViewController.getContext().getString(R.string.kg_kor_success_pin_message);
                            AlertDialog alertDialog = keyguardSecSimPukViewController.mCarrierDialog;
                            if (alertDialog == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(keyguardSecSimPukViewController.getContext(), 2132018526);
                                builder.setMessage(string);
                                builder.setCancelable(false);
                                builder.setNeutralButton(R.string.f789ok, (DialogInterface.OnClickListener) null);
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
                            if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode()) {
                                Log.d("KeyguardSecSimPukViewController", "ForceHideSoftInput");
                                KeyguardSecSimPukViewController.this.mInputMethodManager.semForceHideSoftInput();
                            }
                            KeyguardSecSimPukViewController.this.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecSimPukViewController.this.mSecurityMode, true);
                        }
                    } else {
                        KeyguardSecSimPukViewController.this.mShowDefaultMessage = false;
                        if (pinResult2.getResult() == 1) {
                            KeyguardSecSimPukViewController keyguardSecSimPukViewController2 = KeyguardSecSimPukViewController.this;
                            KeyguardSecMessageAreaController keyguardSecMessageAreaController = keyguardSecSimPukViewController2.mMessageAreaController;
                            KeyguardSecSimPukView keyguardSecSimPukView = (KeyguardSecSimPukView) keyguardSecSimPukViewController2.mView;
                            int attemptsRemaining = pinResult2.getAttemptsRemaining();
                            keyguardSecSimPukView.getClass();
                            boolean isESimEmbedded = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isESimEmbedded();
                            String string2 = attemptsRemaining == 0 ? keyguardSecSimPukView.getContext().getString(R.string.kg_password_wrong_puk_code_dead) : attemptsRemaining > 1 ? (!LsRune.SECURITY_LGU_USIM_TEXT || isESimEmbedded) ? LsRune.SECURITY_KOR_USIM_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_kor_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), Integer.valueOf(attemptsRemaining)) : LsRune.SECURITY_USE_CDMA_CARD_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_ctc_sim_puk_remaining_attempts, Integer.valueOf(attemptsRemaining)) : keyguardSecSimPukView.getContext().getString(R.string.kg_sim_puk_remaining_attempts, Integer.valueOf(attemptsRemaining)) : keyguardSecSimPukView.getContext().getString(R.string.kg_lgu_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), 10) : attemptsRemaining == 1 ? (!LsRune.SECURITY_LGU_USIM_TEXT || isESimEmbedded) ? LsRune.SECURITY_KOR_USIM_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_kor_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), Integer.valueOf(attemptsRemaining)) : LsRune.SECURITY_USE_CDMA_CARD_TEXT ? keyguardSecSimPukView.getContext().getString(R.string.kg_ctc_sim_puk_remaining_1_attempt) : keyguardSecSimPukView.getContext().getString(R.string.kg_sim_puk_remaining_1_attempt) : keyguardSecSimPukView.getContext().getString(R.string.kg_lgu_sim_puk_remaining_attempts, Integer.valueOf(10 - attemptsRemaining), 10) : keyguardSecSimPukView.getContext().getString(R.string.kg_password_puk_failed);
                            Log.d("KeyguardSimPukView", "getPukPasswordErrorMessage: attemptsRemaining=" + attemptsRemaining + " displayMessage=" + string2);
                            keyguardSecMessageAreaController.setMessage(string2, false);
                        } else {
                            KeyguardSecSimPukViewController keyguardSecSimPukViewController3 = KeyguardSecSimPukViewController.this;
                            keyguardSecSimPukViewController3.mMessageAreaController.setMessage(keyguardSecSimPukViewController3.getContext().getString(R.string.kg_password_puk_failed), false);
                        }
                        Log.d("KeyguardSecSimPukViewController", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + pinResult2.getAttemptsRemaining());
                        KeyguardSecSimPukViewController.this.mStateMachine.reset();
                    }
                    KeyguardSecSimPukViewController.this.mCheckSimPukThread = null;
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                        if (!LsRune.SECURITY_KOR_USIM_TEXT) {
                            i = R.string.kg_invalid_confirm_pin_hint;
                        } else if (keyguardSecSimPukViewController.getPasswordText() != null && keyguardSecSimPukViewController.getPasswordText().length != 0) {
                            i = R.string.kg_kor_invalid_confirm_pin_hint;
                        }
                    }
                } else if (keyguardSecSimPukViewController.checkPin()) {
                    this.mState = 2;
                    i = LsRune.SECURITY_KOR_USIM_TEXT ? R.string.kg_kor_enter_confirm_pin_hint : LsRune.SECURITY_USE_CDMA_CARD_TEXT ? R.string.kg_ctc_enter_confirm_pin_hint : R.string.kg_sec_puk_enter_confirm_pin_hint;
                } else if (!LsRune.SECURITY_KOR_USIM_TEXT) {
                    i = R.string.kg_invalid_sim_pin_hint;
                } else if (keyguardSecSimPukViewController.getPasswordText() != null && keyguardSecSimPukViewController.getPasswordText().length != 0) {
                    i = R.string.kg_kor_sim_pin_instructions;
                }
            } else if (keyguardSecSimPukViewController.checkPuk()) {
                this.mState = 1;
                i = LsRune.SECURITY_KOR_USIM_TEXT ? R.string.kg_kor_puk_enter_pin_hint : LsRune.SECURITY_USE_CDMA_CARD_TEXT ? R.string.kg_ctc_puk_enter_pin_hint : R.string.kg_sec_puk_enter_pin_hint;
            } else {
                i = LsRune.SECURITY_KOR_USIM_TEXT ? (keyguardSecSimPukViewController.getPasswordText() == null || keyguardSecSimPukViewController.getPasswordText().length == 0) ? R.string.kg_kor_empty_sim_puk_hint : R.string.kg_kor_invalid_sim_puk_hint : R.string.kg_sec_invalid_sim_puk_hint;
            }
            ((KeyguardSecSimPukView) keyguardSecSimPukViewController.mView).resetPasswordText(true, true);
            if (i != 0) {
                if (i != R.string.kg_invalid_sim_pin_hint) {
                    keyguardSecSimPukViewController.mMessageAreaController.setMessage(keyguardSecSimPukViewController.getContext().getString(i), false);
                } else {
                    keyguardSecSimPukViewController.mMessageAreaController.setMessage(keyguardSecSimPukViewController.getContext().getString(R.string.kg_invalid_sim_pin_hint, 4, 8), false);
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

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.keyguard.KeyguardSecSimPukViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.KeyguardSecSimPukViewController$1] */
    public KeyguardSecSimPukViewController(KeyguardSecSimPukView keyguardSecSimPukView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController, InputMethodManager inputMethodManager) {
        super(keyguardSecSimPukView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, telephonyManager, falsingCollector, emergencyButtonController, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
        this.mOrientation = 1;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecSimPukViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecSimPukViewController.this.updateSimIconImage();
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
        };
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor("emergency_mode"), Settings.Global.getUriFor("select_name_1"), Settings.Global.getUriFor("select_name_2")};
        this.mUpdateMonitorCallback = new C07422();
        this.mConfigurationController = configurationController;
        this.mInputMethodManager = inputMethodManager;
        this.mStateMachine = new SecStateMachine(this, 0);
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        KeyguardSecESimArea keyguardSecESimArea = (KeyguardSecESimArea) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sec_esim_area);
        this.mESimSkipArea = keyguardSecESimArea;
        if (keyguardSecESimArea != null) {
            keyguardSecESimArea.mCallback = getKeyguardSecurityCallback();
            keyguardSecESimArea.mSecurityMode = this.mSecurityMode;
        }
        ProgressBar progressBar = (ProgressBar) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.progress);
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        updateProgressBarDrawable();
        this.mSimImageView = (ImageView) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sim_icon);
        this.mSimCardName = (SystemUITextView) ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sim_name);
        View findViewById = ((KeyguardSecSimPukView) this.mView).findViewById(R.id.keyguard_sec_sim_info_view_container);
        if (findViewById != null) {
            findViewById.setVisibility(4);
        }
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

    @Override // com.android.keyguard.KeyguardSimPukViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((KeyguardSecSimPukView) this.mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSecSimPukViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecSimPukViewController keyguardSecSimPukViewController = KeyguardSecSimPukViewController.this;
                View findViewById = ((KeyguardSecSimPukView) keyguardSecSimPukViewController.mView).findViewById(R.id.keyguard_sec_sim_info_view_container);
                if (findViewById != null) {
                    findViewById.setVisibility(0);
                }
                keyguardSecSimPukViewController.updateESimLayout();
            }
        });
        ((KeyguardSimPukViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mSettingsHelper.registerCallback(this.mSettingsListener, this.mSettingsValueList);
    }

    @Override // com.android.keyguard.KeyguardSimPukViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
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
            updateSimIconImage();
        }
        updateESimLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0112  */
    @Override // com.android.keyguard.KeyguardSimPukViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showDefaultMessage() {
        int i;
        String string;
        String iccId;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        ISemTelephony asInterface;
        if (LsRune.SECURITY_SUB_DISPLAY_COVER && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            Log.d("KeyguardSecSimPukViewController", "Skip updating showDefaultMessage when folder closed");
            return;
        }
        TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("showDefaultMessage subId="), this.mSubId, "KeyguardSecSimPukViewController");
        boolean isValidSubscriptionId = SubscriptionManager.isValidSubscriptionId(this.mSubId);
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (!isValidSubscriptionId) {
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("showDefaultMessage isValidSubscriptionId failed !!!  subid:"), this.mSubId, "KeyguardSecSimPukViewController");
        } else {
            if (SecurityUtils.getSimSlotNum(this.mSubId) == -1) {
                Log.d("KeyguardSecSimPukViewController", "showDefaultMessage - skip update");
                return;
            }
            int i2 = this.mSubId;
            try {
                asInterface = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
            } catch (Exception e) {
                AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Exception: ", e, "KeyguardSecSimPukViewController");
            }
            if (asInterface != null) {
                i = asInterface.getSimPukRetryForSubscriber(i2);
                Log.i("KeyguardSecSimPukViewController", "getSimPukLockInfoResult(): num_of_retry is " + i);
                Resources resources = getResources();
                if (i != -1) {
                    if (i != 1) {
                        if (i != 10) {
                            if (LsRune.SECURITY_KOR_USIM_TEXT) {
                                string = resources.getString(R.string.kg_kor_sim_puk_instructions) + " " + resources.getString(R.string.kg_sim_pin_remaining_attempts, Integer.valueOf(i));
                            } else {
                                string = LsRune.SECURITY_USE_CDMA_CARD_TEXT ? resources.getString(R.string.kg_ctc_sim_puk_remaining_attempts, Integer.valueOf(i)) : resources.getString(R.string.kg_sim_puk_remaining_attempts, Integer.valueOf(i));
                            }
                        }
                    } else if (LsRune.SECURITY_KOR_USIM_TEXT) {
                        string = resources.getString(R.string.kg_kor_sim_puk_instructions) + " " + resources.getString(R.string.kg_sim_pin_remaining_1_attempt);
                    } else {
                        string = LsRune.SECURITY_USE_CDMA_CARD_TEXT ? resources.getString(R.string.kg_ctc_sim_puk_remaining_1_attempt) : resources.getString(R.string.kg_sim_puk_remaining_1_attempt);
                    }
                    keyguardSecMessageAreaController = this.mMessageAreaController;
                    if (keyguardSecMessageAreaController != null) {
                        if (passwordTextView != null && ((SecPasswordTextView) passwordTextView).mText.length() > 0) {
                            return;
                        } else {
                            keyguardSecMessageAreaController.setMessage(string, false);
                        }
                    }
                }
                if (!LsRune.SECURITY_DCM_USIM_TEXT) {
                    string = resources.getString(R.string.kg_dcm_sim_puk_instructions);
                } else if (LsRune.SECURITY_KTT_USIM_TEXT) {
                    string = resources.getString(R.string.kg_kt_sim_puk_instructions);
                } else if (LsRune.SECURITY_KOR_USIM_TEXT) {
                    string = resources.getString(R.string.kg_kor_sim_puk_instructions);
                } else if (LsRune.SECURITY_USE_CDMA_CARD_TEXT) {
                    string = resources.getString(R.string.kg_ctc_sim_puk_instructions);
                } else {
                    SubscriptionInfo subscriptionInfoForSubId = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getSubscriptionInfoForSubId(this.mSubId);
                    string = (subscriptionInfoForSubId == null || (iccId = subscriptionInfoForSubId.getIccId()) == null || !iccId.startsWith("894101")) ? false : true ? resources.getString(R.string.kg_swisscom_sim_puk_instructions) : resources.getString(R.string.kg_sim_puk_instructions);
                }
                keyguardSecMessageAreaController = this.mMessageAreaController;
                if (keyguardSecMessageAreaController != null) {
                }
            }
            i = 0;
            Log.i("KeyguardSecSimPukViewController", "getSimPukLockInfoResult(): num_of_retry is " + i);
            Resources resources2 = getResources();
            if (i != -1) {
            }
            if (!LsRune.SECURITY_DCM_USIM_TEXT) {
            }
            keyguardSecMessageAreaController = this.mMessageAreaController;
            if (keyguardSecMessageAreaController != null) {
            }
        }
        if (passwordTextView != null) {
            passwordTextView.requestFocus();
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

    public final void updateProgressBarDrawable() {
        this.mProgressBar.setIndeterminateDrawable(getContext().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_progress_material_whitebg : R.drawable.keyguard_progress_material));
    }

    @Override // com.android.keyguard.KeyguardSimPukViewController
    public final void updateSim() {
        if (this.mCheckSimPukThread == null) {
            C07433 c07433 = new C07433(this.mPukText, this.mPinText, this.mSubId);
            this.mCheckSimPukThread = c07433;
            c07433.start();
        }
    }

    public final void updateSimIconImage() {
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
            if (LsRune.SECURITY_ESIM && DeviceState.isESIM(simSlotNum, getContext()) && ((KeyguardSimPukViewController) this).mKeyguardUpdateMonitor.isESimEmbedded()) {
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

    @Override // com.android.keyguard.KeyguardSimPukViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        Log.d("KeyguardSecSimPukViewController", "verifyPasswordAndUnlock next");
        super.verifyPasswordAndUnlock();
    }
}
