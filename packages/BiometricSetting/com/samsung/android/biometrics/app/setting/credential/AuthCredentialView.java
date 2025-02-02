package com.samsung.android.biometrics.app.setting.credential;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class AuthCredentialView extends LinearLayout implements View.OnKeyListener, View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final AccessibilityManager mAccessibilityManager;
    protected int mAlertMode;
    protected Button mBtnCancel;
    protected Button mBtnContinue;
    private final AuthCredentialView$$ExternalSyntheticLambda0 mClearErrorRunnable;
    private CharSequence mDefaultUnlockGuide;
    private CharSequence mDescription;
    private final DevicePolicyManager mDevicePolicyManager;
    protected TextView mErrorView;
    protected final Handler mHandler;
    protected boolean mIsBiometricInfoModeOnCover;
    private boolean mIsDetachedFromWindow;
    protected boolean mIsPasswordShown;
    protected LockPatternUtils mLockPatternUtils;
    protected ErrorTimer mLockoutTimer;
    protected AsyncTask<?, ?, ?> mPendingLockCheck;
    protected PromptConfig mPromptConfig;
    private CharSequence mSubTitle;
    private CharSequence mTitle;
    private TextView mTxtViewDescription;
    private TextView mTxtViewSubTitle;
    private TextView mTxtViewTitle;
    private final UserManager mUserManager;

    protected static class ErrorTimer extends CountDownTimer {
        private final Context mContext;
        private final Consumer<String> mShowLockoutMessage;

        public ErrorTimer(Context context, long j, AuthCredentialView$$ExternalSyntheticLambda1 authCredentialView$$ExternalSyntheticLambda1) {
            super(j, 1000L);
            this.mContext = context;
            this.mShowLockoutMessage = authCredentialView$$ExternalSyntheticLambda1;
        }

        @Override // android.os.CountDownTimer
        public final void onTick(long j) {
            String str;
            if (this.mShowLockoutMessage == null) {
                return;
            }
            int i = (int) (j / 1000);
            int i2 = i / 60;
            if (i > 60) {
                int i3 = i2 + 1;
                str = this.mContext.getResources().getQuantityString(R.plurals.biometric_dialog_credential_too_many_attempts_min, i3, Integer.valueOf(i3));
            } else if (i > 0) {
                str = this.mContext.getResources().getQuantityString(R.plurals.biometric_dialog_credential_too_many_attempts, i != 1 ? 3 : 1, Integer.valueOf(i));
            } else {
                str = "";
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mShowLockoutMessage.accept(str);
        }
    }

    public static /* synthetic */ void $r8$lambda$yPTQs_IlP1Y3Z_Rni8lKBlSRriA(AuthCredentialView authCredentialView) {
        if (authCredentialView.mIsDetachedFromWindow) {
            return;
        }
        authCredentialView.getCallback().onDismissed(5, null);
    }

    public AuthCredentialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAlertMode = 0;
        this.mDefaultUnlockGuide = "";
        this.mSubTitle = "";
        this.mTitle = "";
        this.mDescription = "";
        this.mClearErrorRunnable = new AuthCredentialView$$ExternalSyntheticLambda0(this, 0);
        Log.d("BSS_AuthCredentialView", "AuthCredentialView");
        this.mHandler = new Handler(context.getMainLooper());
        this.mAccessibilityManager = (AccessibilityManager) ((LinearLayout) this).mContext.getSystemService(AccessibilityManager.class);
        this.mUserManager = (UserManager) ((LinearLayout) this).mContext.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) ((LinearLayout) this).mContext.getSystemService(DevicePolicyManager.class);
    }

    @VisibleForTesting
    static int getLastAttemptBeforeWipeDeviceMessageRes(int i) {
        return i != 1 ? i != 2 ? R.string.biometric_dialog_last_password_attempt_before_wipe_device : R.string.biometric_dialog_last_pattern_attempt_before_wipe_device : R.string.biometric_dialog_last_pin_attempt_before_wipe_device;
    }

    private double getLengthFromPercent(int i, int i2) {
        ((LinearLayout) this).mContext.getDisplay().getRealSize(new Point());
        ((LinearLayout) this).mContext.getResources().getValue(i, new TypedValue(), true);
        return (i2 == 1 ? r1.x : r1.y) * r0.getFraction(1.0f, 1.0f);
    }

    private int getUserTypeForWipe() {
        UserInfo userInfo = this.mUserManager.getUserInfo(this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(this.mPromptConfig.getUserId()));
        if (userInfo == null || userInfo.isPrimary()) {
            return 1;
        }
        return userInfo.isManagedProfile() ? 2 : 3;
    }

    protected static void setText(TextView textView, CharSequence charSequence) {
        if (textView == null || charSequence == null) {
            return;
        }
        textView.setText(charSequence);
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.samsung.android.biometrics.app.setting.credential.AuthCredentialView$$ExternalSyntheticLambda1] */
    private void startLockoutTimer(long j) {
        if (j == 0) {
            return;
        }
        long elapsedRealtime = j - SystemClock.elapsedRealtime();
        if (elapsedRealtime > 0) {
            ErrorTimer errorTimer = this.mLockoutTimer;
            if (errorTimer != null) {
                errorTimer.cancel();
            }
            ErrorTimer errorTimer2 = new ErrorTimer(((LinearLayout) this).mContext, elapsedRealtime, new Consumer() { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialView$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AuthCredentialView.this.showLockoutMessage((String) obj);
                }
            }) { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialView.1
                @Override // android.os.CountDownTimer
                public final void onFinish() {
                    AuthCredentialView.this.onLockoutTimeoutFinish();
                }
            };
            this.mLockoutTimer = errorTimer2;
            errorTimer2.start();
            this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 1));
        }
    }

    private boolean updateErrorMessage() {
        int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mPromptConfig.getUserId());
        int maximumFailedPasswordsForWipe = this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(this.mPromptConfig.getUserId());
        Log.d("BSS_AuthCredentialView", "updateErrorMessage: " + currentFailedPasswordAttempts + ", " + maximumFailedPasswordsForWipe);
        if (maximumFailedPasswordsForWipe <= 0 || currentFailedPasswordAttempts <= 0) {
            return false;
        }
        showError(getResources().getString(R.string.biometric_dialog_credential_attempts_before_wipe, Integer.valueOf(currentFailedPasswordAttempts), Integer.valueOf(maximumFailedPasswordsForWipe)));
        int i = maximumFailedPasswordsForWipe - currentFailedPasswordAttempts;
        if (i == 1) {
            this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 3));
        } else if (i <= 0) {
            this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 4));
        }
        return true;
    }

    protected void applyRotationGui() {
        LinearLayout.LayoutParams layoutParams;
        if (findViewById(R.id.main_view) instanceof ConstraintLayout) {
            Log.d("BSS_AuthCredentialView", "resizeMainLayoutToFitTabletGUI");
            ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.main_view);
            if (constraintLayout != null) {
                int navigationBarHeight = Utils.getNavigationBarHeight(((LinearLayout) this).mContext);
                int statusBarHeight = Utils.getStatusBarHeight(((LinearLayout) this).mContext);
                int displayHeight = Utils.getDisplayHeight(((LinearLayout) this).mContext);
                int displayWidth = isScreenLandscape() ? displayHeight - navigationBarHeight : Utils.getDisplayWidth(((LinearLayout) this).mContext);
                int dimensionPixelSize = ((int) ((Utils.isScreenLandscape(((LinearLayout) this).mContext) ? displayWidth : displayHeight) * (Utils.isScreenLandscape(((LinearLayout) this).mContext) ? 0.18d : 0.25d))) - (((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.auth_credential_main_view_tablet_margin_bottom) + (navigationBarHeight + statusBarHeight));
                int i = (int) (displayWidth * 0.1d);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) constraintLayout.getLayoutParams();
                layoutParams2.rightMargin = i;
                layoutParams2.leftMargin = i;
                layoutParams2.gravity = 16;
                layoutParams2.height = (int) (displayHeight * (Utils.isScreenLandscape(((LinearLayout) this).mContext) ? 0.35d : 0.55d));
                constraintLayout.setLayoutParams(layoutParams2);
                Log.d("BSS_AuthCredentialView", "displayHeight = " + displayHeight);
                Log.d("BSS_AuthCredentialView", "Top Margin = " + dimensionPixelSize);
                return;
            }
            return;
        }
        Log.d("BSS_AuthCredentialView", "applyRotationGui - Phone");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_view);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.title_view);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.edit_view);
        if (linearLayout == null || linearLayout2 == null || linearLayout3 == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams3.height = -1;
        if (isScreenLandscape()) {
            if (Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY && ((LinearLayout) this).mContext.getResources().getConfiguration().semDisplayDeviceType == 0) {
                layoutParams3.height = ((int) getLengthFromPercent(R.fraction.auth_credential_foldable_main_height_percent, 2)) - Utils.getStatusBarHeight(((LinearLayout) this).mContext);
            }
            layoutParams3.width = -1;
            layoutParams3.topMargin = 0;
            linearLayout.setOrientation(0);
            layoutParams = new LinearLayout.LayoutParams(0, -1);
            layoutParams.weight = 1.0f;
        } else {
            if (Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY && ((LinearLayout) this).mContext.getResources().getConfiguration().semDisplayDeviceType == 0) {
                layoutParams3.width = (int) getLengthFromPercent(R.fraction.auth_credential_foldable_main_width_percent, 1);
            }
            layoutParams3.topMargin = (int) ((LinearLayout) this).mContext.getResources().getDimension(R.dimen.auth_credential_main_view_margin_top);
            linearLayout.setOrientation(1);
            layoutParams = new LinearLayout.LayoutParams(-1, -2);
        }
        linearLayout.setLayoutParams(layoutParams3);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout3.setLayoutParams(layoutParams);
    }

    protected void clearErrorMessage() {
        setText(getErrorTextView(), this.mDefaultUnlockGuide);
    }

    protected void disableButton(Button button) {
        if (button != null) {
            button.setVisibility(4);
            button.setEnabled(false);
        }
    }

    protected void enableButton(Button button) {
        if (button != null) {
            button.setVisibility(0);
            button.setEnabled(true);
        }
    }

    protected void enterAlertMode(int i) {
        int i2;
        Log.i("BSS_AuthCredentialView", "enterAlertMode: " + i);
        this.mAlertMode = i;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mClearErrorRunnable);
        }
        if (i == 1) {
            int userTypeForWipe = getUserTypeForWipe();
            int credentialType = this.mPromptConfig.getCredentialType();
            if (userTypeForWipe == 1) {
                i2 = getLastAttemptBeforeWipeDeviceMessageRes(credentialType);
            } else if (userTypeForWipe == 2) {
                i2 = credentialType != 1 ? credentialType != 2 ? R.string.biometric_dialog_last_password_attempt_before_wipe_profile : R.string.biometric_dialog_last_pattern_attempt_before_wipe_profile : R.string.biometric_dialog_last_pin_attempt_before_wipe_profile;
            } else {
                if (userTypeForWipe != 3) {
                    throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Unrecognized user type:", userTypeForWipe));
                }
                i2 = credentialType != 1 ? credentialType != 2 ? R.string.biometric_dialog_last_password_attempt_before_wipe_user : R.string.biometric_dialog_last_pattern_attempt_before_wipe_user : R.string.biometric_dialog_last_pin_attempt_before_wipe_user;
            }
            enableButton(this.mBtnContinue);
            enableButton(this.mBtnCancel);
        } else if (i == 2) {
            int userTypeForWipe2 = getUserTypeForWipe();
            if (userTypeForWipe2 == 1) {
                i2 = R.string.biometric_dialog_failed_attempts_now_wiping_device;
            } else if (userTypeForWipe2 == 2) {
                i2 = R.string.biometric_dialog_failed_attempts_now_wiping_profile;
            } else {
                if (userTypeForWipe2 != 3) {
                    throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Unrecognized user type:", userTypeForWipe2));
                }
                i2 = R.string.biometric_dialog_failed_attempts_now_wiping_user;
            }
            disableButton(this.mBtnCancel);
            disableButton(this.mBtnContinue);
        } else {
            i2 = 0;
        }
        setText(this.mTxtViewTitle, getResources().getString(R.string.biometric_dialog_last_attempt_before_wipe_dialog_title));
        if (i2 != 0) {
            setText(this.mTxtViewDescription, getResources().getString(i2));
        }
        setText(this.mTxtViewSubTitle, "");
        setText(this.mErrorView, "");
    }

    protected void exitAlertMode() {
        this.mAlertMode = 0;
        setText(this.mTxtViewTitle, this.mTitle);
        TextView textView = this.mTxtViewSubTitle;
        CharSequence charSequence = this.mSubTitle;
        if (textView != null) {
            if (TextUtils.isEmpty(charSequence)) {
                textView.setVisibility(8);
            } else {
                textView.setText(charSequence);
            }
            Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        }
        TextView textView2 = this.mTxtViewDescription;
        CharSequence charSequence2 = this.mDescription;
        if (textView2 != null) {
            if (TextUtils.isEmpty(charSequence2)) {
                textView2.setVisibility(8);
            } else {
                textView2.setText(charSequence2);
            }
            Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        }
        setText(this.mErrorView, this.mDefaultUnlockGuide);
    }

    protected BiometricPromptCallback getCallback() {
        return this.mPromptConfig.getCallback();
    }

    protected CharSequence getDefaultUnlockGuide() {
        int credentialType = this.mPromptConfig.getCredentialType();
        return credentialType == 1 ? ((LinearLayout) this).mContext.getText(R.string.biometric_prompt_unlock_pin) : credentialType == 2 ? ((LinearLayout) this).mContext.getText(R.string.biometric_prompt_unlock_pattern) : ((LinearLayout) this).mContext.getText(R.string.biometric_prompt_unlock_password);
    }

    protected int getEffectiveUserId() {
        return this.mPromptConfig.getUserId();
    }

    protected TextView getErrorTextView() {
        return this.mErrorView;
    }

    protected String getPassword() {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText == null) {
            return null;
        }
        return editText.getText().toString();
    }

    protected int getSelection() {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText == null) {
            return -1;
        }
        return editText.getSelectionEnd();
    }

    protected boolean isScreenLandscape() {
        return Utils.isScreenLandscape(((LinearLayout) this).mContext);
    }

    @Override // android.view.ViewGroup, android.view.View
    @SuppressLint({"UseCompatLoadingForDrawables"})
    protected void onAttachedToWindow() {
        Drawable drawable;
        super.onAttachedToWindow();
        Log.d("BSS_AuthCredentialView", "onAttachedToWindow");
        this.mTxtViewTitle = (TextView) findViewById(R.id.title);
        this.mTxtViewSubTitle = (TextView) findViewById(R.id.subtitle);
        this.mTxtViewDescription = (TextView) findViewById(R.id.description);
        this.mErrorView = (TextView) findViewById(R.id.error);
        Button button = (Button) findViewById(R.id.btn_continue);
        this.mBtnContinue = button;
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = (Button) findViewById(R.id.btn_cancel);
        this.mBtnCancel = button2;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        Bundle savedInstanceState = this.mPromptConfig.getSavedInstanceState();
        if (savedInstanceState != null) {
            int i = savedInstanceState.getInt("alertMode", 0);
            this.mAlertMode = i;
            if (i != 0) {
                this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 2));
            }
        }
        if (this.mAlertMode == 0) {
            setText(this.mTxtViewTitle, this.mTitle);
            TextView textView = this.mTxtViewSubTitle;
            CharSequence charSequence = this.mSubTitle;
            if (textView != null) {
                if (TextUtils.isEmpty(charSequence)) {
                    textView.setVisibility(8);
                } else {
                    textView.setText(charSequence);
                }
                Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
            }
            TextView textView2 = this.mTxtViewDescription;
            CharSequence charSequence2 = this.mDescription;
            if (textView2 != null) {
                if (TextUtils.isEmpty(charSequence2)) {
                    textView2.setVisibility(8);
                } else {
                    textView2.setText(charSequence2);
                }
                Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
            }
            if (TextUtils.isEmpty(this.mDefaultUnlockGuide)) {
                this.mDefaultUnlockGuide = getDefaultUnlockGuide();
            }
            setText(this.mErrorView, this.mDefaultUnlockGuide);
        }
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        if (this.mPromptConfig.isManagedProfile()) {
            drawable = getResources().getDrawable(R.drawable.auth_dialog_enterprise, ((LinearLayout) this).mContext.getTheme());
            int organizationColor = this.mPromptConfig.getOrganizationColor();
            if (organizationColor != 0) {
                drawable.setTint(organizationColor);
            }
        } else {
            drawable = getResources().getDrawable(R.drawable.auth_dialog_lock, ((LinearLayout) this).mContext.getTheme());
        }
        imageView.setImageDrawable(drawable);
        applyRotationGui();
        startLockoutTimer(this.mLockPatternUtils.getLockoutAttemptDeadline(this.mPromptConfig.getUserId()));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.btn_cancel) {
            getCallback().onSystemEvent();
            getCallback().onUserCancel(3);
            onCancelButtonClicked();
        } else if (view.getId() == R.id.btn_continue) {
            if (this.mAlertMode != 0) {
                exitAlertMode();
            } else {
                onContinueButtonClicked();
            }
        }
    }

    protected void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        Log.d("BSS_AuthCredentialView", "onCredentialVerified: " + i);
        if (verifyCredentialResponse.isMatched()) {
            this.mClearErrorRunnable.run();
            this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mPromptConfig.getUserId());
            this.mLockPatternUtils.userPresent(this.mPromptConfig.getUserId());
            long gatekeeperPasswordHandle = verifyCredentialResponse.getGatekeeperPasswordHandle();
            getCallback().onDismissed(7, this.mLockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, this.mPromptConfig.getOperationId(), this.mPromptConfig.getUserId()).getGatekeeperHAT());
            this.mLockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
            return;
        }
        if (i <= 0) {
            this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.getUserId());
            if (updateErrorMessage()) {
                return;
            }
            showRetryMessage();
            return;
        }
        this.mHandler.removeCallbacks(this.mClearErrorRunnable);
        long lockoutAttemptDeadline = this.mLockPatternUtils.setLockoutAttemptDeadline(this.mPromptConfig.getUserId(), i);
        this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.getUserId());
        if (updateErrorMessage()) {
            return;
        }
        startLockoutTimer(lockoutAttemptDeadline);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("BSS_AuthCredentialView", "onDetachedFromWindow");
        this.mIsDetachedFromWindow = true;
        ErrorTimer errorTimer = this.mLockoutTimer;
        if (errorTimer != null) {
            errorTimer.cancel();
        }
        if (this.mAlertMode != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("alertMode", this.mAlertMode);
            this.mPromptConfig.onSaveInstanceState(bundle);
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            this.mPromptConfig.getCallback().onSystemEvent();
            this.mPromptConfig.getCallback().onUserCancel(2);
        }
        return true;
    }

    protected void onLockoutTimeoutFinish() {
        Log.d("BSS_AuthCredentialView", "onLockoutTimeoutFinish");
        this.mLockPatternUtils.getLockoutAttemptDeadline(this.mPromptConfig.getUserId());
        this.mClearErrorRunnable.run();
    }

    protected void setDescriptionVisibility(int i) {
        TextView textView = this.mTxtViewDescription;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    protected void setErrorTextVisibility(int i) {
        TextView textView = this.mErrorView;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    protected void setPassword(String str) {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText != null) {
            editText.setText(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x005d, code lost:
    
        if (android.text.TextUtils.equals(r0, r6) == false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0066, code lost:
    
        if (android.text.TextUtils.equals(r0, r5.mDefaultUnlockGuide) == false) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setPromptConfig(PromptConfig promptConfig) {
        CharSequence deviceCredentialDescription;
        CharSequence text;
        this.mPromptConfig = promptConfig;
        this.mLockPatternUtils = promptConfig.getLockPatternUtils();
        this.mDefaultUnlockGuide = getDefaultUnlockGuide();
        PromptInfo promptInfo = promptConfig.getPromptInfo();
        CharSequence deviceCredentialTitle = promptInfo.getDeviceCredentialTitle();
        if (deviceCredentialTitle == null) {
            deviceCredentialTitle = promptInfo.getTitle();
        }
        this.mTitle = deviceCredentialTitle;
        PromptInfo promptInfo2 = promptConfig.getPromptInfo();
        CharSequence deviceCredentialSubtitle = promptInfo2.getDeviceCredentialSubtitle();
        if (TextUtils.isEmpty(deviceCredentialSubtitle)) {
            deviceCredentialSubtitle = promptInfo2.getSubtitle();
            if (promptInfo2.isUseDefaultSubtitle() && !TextUtils.isEmpty(deviceCredentialSubtitle)) {
                int identifier = ((LinearLayout) this).mContext.getResources().getIdentifier("biometric_dialog_default_subtitle", "string", "android");
                if (identifier > 0) {
                    try {
                        text = ((LinearLayout) this).mContext.getText(identifier);
                    } catch (Resources.NotFoundException unused) {
                    }
                } else {
                    text = "";
                }
            }
            this.mSubTitle = deviceCredentialSubtitle;
            PromptInfo promptInfo3 = this.mPromptConfig.getPromptInfo();
            deviceCredentialDescription = promptInfo3.getDeviceCredentialDescription();
            if (deviceCredentialDescription == null) {
                deviceCredentialDescription = promptInfo3.getDescription();
            }
            this.mDescription = deviceCredentialDescription;
        }
        deviceCredentialSubtitle = "";
        this.mSubTitle = deviceCredentialSubtitle;
        PromptInfo promptInfo32 = this.mPromptConfig.getPromptInfo();
        deviceCredentialDescription = promptInfo32.getDeviceCredentialDescription();
        if (deviceCredentialDescription == null) {
        }
        this.mDescription = deviceCredentialDescription;
    }

    protected void setSelection(int i) {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText == null || i < 0) {
            return;
        }
        editText.setSelection(i);
    }

    protected void setSubTitleVisibility(int i) {
        TextView textView = this.mTxtViewSubTitle;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    protected void setTextToSubTitleView(CharSequence charSequence) {
        setText(this.mTxtViewSubTitle, charSequence);
    }

    protected void setTitleVisibility(int i) {
        TextView textView = this.mTxtViewTitle;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    protected final void showError(String str) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mClearErrorRunnable);
            Handler handler2 = this.mHandler;
            AuthCredentialView$$ExternalSyntheticLambda0 authCredentialView$$ExternalSyntheticLambda0 = this.mClearErrorRunnable;
            this.mPromptConfig.getClass();
            handler2.postDelayed(authCredentialView$$ExternalSyntheticLambda0, 3000L);
        }
        setText(getErrorTextView(), str);
    }

    protected void showLockoutMessage(String str) {
        setText(getErrorTextView(), str);
    }

    protected void showRetryMessage() {
        showError(getResources().getString(R.string.sec_lockpassword_need_to_unlock_wrong));
    }

    protected void onCancelButtonClicked() {
    }

    protected void onContinueButtonClicked() {
    }

    protected void onLockoutTimeoutStart() {
    }

    protected void setPasswordShown(boolean z) {
    }
}
