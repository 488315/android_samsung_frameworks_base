package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardCarrierPasswordViewController extends KeyguardSecAbsKeyInputViewController implements TextWatcher, TextView.OnEditorActionListener {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final Context mContext;
    public int mCurrentOrientation;
    public int mFailedAttempts;
    public final InputMethodManager mImm;
    public final LockPatternUtils mLockPatternUtils;
    public final EditText mPasswordEntry;
    public final ViewGroup mPasswordEntryBoxLayout;
    public AsyncTask mPendingLockCheck;
    public final boolean mShowImeAtScreenOn;
    public final AnonymousClass3 mShowImeRunnable;

    /* renamed from: $r8$lambda$-goCXBIlA97VX0btFJtY2f5PTY8, reason: not valid java name */
    public static void m831$r8$lambda$goCXBIlA97VX0btFJtY2f5PTY8(KeyguardCarrierPasswordViewController keyguardCarrierPasswordViewController, boolean z) {
        keyguardCarrierPasswordViewController.mPendingLockCheck = null;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("check result : ", "KeyguardCarrierPasswordView", z);
        if (z) {
            keyguardCarrierPasswordViewController.mImm.hideSoftInputFromWindow(((KeyguardCarrierPasswordView) keyguardCarrierPasswordViewController.mView).getWindowToken(), 0);
            Intent intent = new Intent("com.sec.android.FindingLostPhonePlus.RELEASE");
            intent.addFlags(16777248);
            keyguardCarrierPasswordViewController.mContext.sendBroadcast(intent, "android.permission.MASTER_CLEAR");
            return;
        }
        keyguardCarrierPasswordViewController.mMessageAreaController.setMessage$1(keyguardCarrierPasswordViewController.getResources().getString(R.string.kg_carrier_lock_wrong_password), false);
        keyguardCarrierPasswordViewController.mPasswordEntry.setText("");
        int i = keyguardCarrierPasswordViewController.mFailedAttempts + 1;
        keyguardCarrierPasswordViewController.mFailedAttempts = i;
        if (i > 4) {
            keyguardCarrierPasswordViewController.handleAttemptLockout(keyguardCarrierPasswordViewController.mLockPatternUtils.setCarrierLockoutAttemptDeadline(keyguardCarrierPasswordViewController.mSelectedUserInteractor.getSelectedUserId(false)));
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.keyguard.KeyguardCarrierPasswordViewController$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.keyguard.KeyguardCarrierPasswordViewController$3] */
    public KeyguardCarrierPasswordViewController(KeyguardCarrierPasswordView keyguardCarrierPasswordView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, InputMethodManager inputMethodManager, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardCarrierPasswordView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController, featureFlags, selectedUserInteractor);
        this.mFailedAttempts = 0;
        this.mCurrentOrientation = -1;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardCarrierPasswordViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardCarrierPasswordViewController keyguardCarrierPasswordViewController = KeyguardCarrierPasswordViewController.this;
                if (keyguardCarrierPasswordViewController.mCurrentOrientation != configuration.orientation) {
                    keyguardCarrierPasswordViewController.updateLayout();
                    keyguardCarrierPasswordViewController.mCurrentOrientation = configuration.orientation;
                }
            }
        };
        this.mShowImeRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardCarrierPasswordViewController.3
            @Override // java.lang.Runnable
            public final void run() {
                if (((KeyguardCarrierPasswordView) ((ViewController) KeyguardCarrierPasswordViewController.this).mView).isShown()) {
                    KeyguardCarrierPasswordViewController.this.mPasswordEntry.requestFocus();
                    KeyguardCarrierPasswordViewController keyguardCarrierPasswordViewController = KeyguardCarrierPasswordViewController.this;
                    keyguardCarrierPasswordViewController.mImm.showSoftInput(keyguardCarrierPasswordViewController.mPasswordEntry, 1);
                }
            }
        };
        this.mLockPatternUtils = lockPatternUtils;
        this.mConfigurationController = configurationController;
        this.mImm = inputMethodManager;
        this.mShowImeAtScreenOn = getResources().getBoolean(R.bool.kg_show_ime_at_screen_on);
        this.mContext = getContext();
        this.mPasswordEntry = (EditText) ((KeyguardCarrierPasswordView) this.mView).findViewById(R.id.passwordEntry);
        this.mCurrentOrientation = getResources().getConfiguration().orientation;
        this.mPasswordEntryBoxLayout = (ViewGroup) ((KeyguardCarrierPasswordView) this.mView).findViewById(R.id.password_entry_box);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void handleAttemptLockout(long j) {
        ((KeyguardCarrierPasswordView) this.mView).setPasswordEntryEnabled(false);
        new CountDownTimer(j - System.currentTimeMillis(), 1000L) { // from class: com.android.keyguard.KeyguardCarrierPasswordViewController.4
            @Override // android.os.CountDownTimer
            public final void onFinish() {
                ((KeyguardCarrierPasswordView) ((ViewController) KeyguardCarrierPasswordViewController.this).mView).setPasswordEntryEnabled(true);
                KeyguardCarrierPasswordViewController.this.mMessageAreaController.setMessage$1("", false);
                KeyguardCarrierPasswordViewController.this.mFailedAttempts = 0;
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j2) {
                KeyguardCarrierPasswordViewController keyguardCarrierPasswordViewController = KeyguardCarrierPasswordViewController.this;
                keyguardCarrierPasswordViewController.mMessageAreaController.setMessage$1(keyguardCarrierPasswordViewController.getResources().getString(R.string.kg_carrier_lock_too_many_failed_attempts), false);
            }
        }.start();
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
        if (!z && !z2) {
            return false;
        }
        verifyPasswordAndUnlock();
        return true;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        super.onPause();
        ((KeyguardCarrierPasswordView) this.mView).removeCallbacks(this.mShowImeRunnable);
        this.mImm.hideSoftInputFromWindow(((KeyguardCarrierPasswordView) this.mView).getWindowToken(), 0);
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
            this.mPendingLockCheck = null;
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        super.onResume(i);
        if (this.mPasswordEntry.isEnabled() && (i != 1 || this.mShowImeAtScreenOn)) {
            ((KeyguardCarrierPasswordView) this.mView).postDelayed(this.mShowImeRunnable, 100L);
        }
        reset$1();
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (getKeyguardSecurityCallback() != null) {
            getKeyguardSecurityCallback().userActivity();
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mPasswordEntry.addTextChangedListener(this);
        this.mPasswordEntry.setOnEditorActionListener(this);
        this.mPasswordEntry.setKeyListener(DigitsKeyListener.getInstance());
        this.mPasswordEntry.setInputType(129);
        this.mPasswordEntry.requestFocus();
        this.mPasswordEntry.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyguardCarrierPasswordViewController.this.getKeyguardSecurityCallback().userActivity();
            }
        });
        this.mPasswordEntry.addTextChangedListener(new TextWatcher() { // from class: com.android.keyguard.KeyguardCarrierPasswordViewController.2
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                if (KeyguardCarrierPasswordViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardCarrierPasswordViewController.this.getKeyguardSecurityCallback().userActivity();
                }
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        updateLayout();
        reset$1();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mPasswordEntry.removeTextChangedListener(this);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void reset$1() {
        this.mPasswordEntry.setText("");
        this.mPasswordEntry.requestFocus();
        long carrierLockoutAttemptDeadline = this.mLockPatternUtils.getCarrierLockoutAttemptDeadline(this.mSelectedUserInteractor.getSelectedUserId(false));
        if (carrierLockoutAttemptDeadline != 0) {
            handleAttemptLockout(carrierLockoutAttemptDeadline);
        } else {
            this.mMessageAreaController.setMessage$1("", false);
            ((KeyguardCarrierPasswordView) this.mView).setPasswordEntryEnabled(true);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void updateLayout() {
        if (this.mPasswordEntryBoxLayout != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_side_margin);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mPasswordEntryBoxLayout.getLayoutParams();
            marginLayoutParams.leftMargin = dimensionPixelSize;
            marginLayoutParams.rightMargin = dimensionPixelSize;
            this.mPasswordEntryBoxLayout.setLayoutParams(marginLayoutParams);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        getKeyguardSecurityCallback().userActivity();
        byte[] charSequenceToByteArray = KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(this.mPasswordEntry.getText());
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        this.mPendingLockCheck = LockPatternChecker.checkRemoteLockPassword(this.mLockPatternUtils, 1, charSequenceToByteArray, this.mSelectedUserInteractor.getSelectedUserId(), new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticLambda2
            public final void onChecked(boolean z, int i) {
                KeyguardCarrierPasswordViewController.m831$r8$lambda$goCXBIlA97VX0btFJtY2f5PTY8(KeyguardCarrierPasswordViewController.this, z);
            }
        });
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
