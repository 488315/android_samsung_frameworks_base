package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptContentItemBulletedText;
import android.hardware.biometrics.PromptContentItemPlainText;
import android.hardware.biometrics.PromptContentViewWithMoreOptionsButton;
import android.hardware.biometrics.PromptVerticalListContentView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserManager;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;
import com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView;
import com.samsung.android.biometrics.app.setting.prompt.credential.LockoutTimer;
import java.util.ArrayList;
import java.util.List;

public abstract class AuthCredentialView extends LinearLayout implements View.OnKeyListener, View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public int mAlertMode;
    public Button mBtnCancel;
    public Button mBtnContinue;
    public Button mBtnEmergencyCalls;
    public final AuthCredentialView$$ExternalSyntheticLambda0 mClearErrorRunnable;
    public CharSequence mDefaultUnlockGuide;
    public CharSequence mDescription;
    public final DevicePolicyManager mDevicePolicyManager;
    public TextView mErrorView;
    public final Handler mHandler;
    public boolean mIsBiometricInfoModeOnCover;
    public boolean mIsDetachedFromWindow;
    public boolean mIsPasswordShown;
    public LockPatternUtils mLockPatternUtils;
    public LockoutTimer mLockoutTimer;
    public AsyncTask mPendingLockCheck;
    public PromptConfig mPromptConfig;
    public CharSequence mSubTitle;
    public CharSequence mTitle;
    public TextView mTxtViewDescription;
    public TextView mTxtViewSubTitle;
    public TextView mTxtViewTitle;
    public final UserManager mUserManager;

    /* renamed from: com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public AnonymousClass3() {
        }
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
        this.mHandler = new Handler(Looper.myLooper());
        this.mAccessibilityManager = (AccessibilityManager) ((LinearLayout) this).mContext.getSystemService(AccessibilityManager.class);
        this.mUserManager = (UserManager) ((LinearLayout) this).mContext.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) ((LinearLayout) this).mContext.getSystemService(DevicePolicyManager.class);
    }

    public static int getLastAttemptBeforeWipeDeviceMessageRes(int i) {
        return i != 1 ? i != 2 ? R.string.biometric_dialog_last_password_attempt_before_wipe_device : R.string.biometric_dialog_last_pattern_attempt_before_wipe_device : R.string.biometric_dialog_last_pin_attempt_before_wipe_device;
    }

    private int getUserTypeForWipe() {
        UserInfo userInfo = this.mUserManager.getUserInfo(this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(this.mPromptConfig.mUserId));
        if (userInfo == null || userInfo.isPrimary()) {
            return 1;
        }
        return userInfo.isManagedProfile() ? 2 : 3;
    }

    public static void setText(TextView textView, CharSequence charSequence) {
        if (textView == null || charSequence == null) {
            return;
        }
        textView.setText(charSequence);
    }

    public void clearErrorMessage() {
        setText(getErrorTextView(), this.mDefaultUnlockGuide);
    }

    public void disableButton(Button button) {
        if (button != null) {
            button.setVisibility(4);
            button.setEnabled(false);
        }
    }

    public void enableButton(Button button) {
        if (button != null) {
            button.setVisibility(0);
            button.setEnabled(true);
        }
    }

    public void enterAlertMode(int i) {
        int i2;
        Log.i("BSS_AuthCredentialView", "enterAlertMode: " + i);
        this.mAlertMode = i;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mClearErrorRunnable);
        }
        if (i == 1) {
            int userTypeForWipe = getUserTypeForWipe();
            int i3 = this.mPromptConfig.mCredentialType;
            if (userTypeForWipe == 1) {
                i2 = getLastAttemptBeforeWipeDeviceMessageRes(i3);
            } else if (userTypeForWipe == 2) {
                i2 = i3 != 1 ? i3 != 2 ? R.string.biometric_dialog_last_password_attempt_before_wipe_profile : R.string.biometric_dialog_last_pattern_attempt_before_wipe_profile : R.string.biometric_dialog_last_pin_attempt_before_wipe_profile;
            } else {
                if (userTypeForWipe != 3) {
                    throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m(userTypeForWipe, "Unrecognized user type:"));
                }
                i2 = i3 != 1 ? i3 != 2 ? R.string.biometric_dialog_last_password_attempt_before_wipe_user : R.string.biometric_dialog_last_pattern_attempt_before_wipe_user : R.string.biometric_dialog_last_pin_attempt_before_wipe_user;
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
                    throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m(userTypeForWipe2, "Unrecognized user type:"));
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

    public void exitAlertMode() {
        this.mAlertMode = 0;
        setTitleAndDescription();
        setText(this.mErrorView, this.mDefaultUnlockGuide);
    }

    public BiometricPromptCallback getCallback() {
        return this.mPromptConfig.mCallback;
    }

    public CharSequence getDefaultUnlockGuide() {
        int i = this.mPromptConfig.mCredentialType;
        return i == 1 ? ((LinearLayout) this).mContext.getText(R.string.biometric_prompt_unlock_pin) : i == 2 ? ((LinearLayout) this).mContext.getText(R.string.biometric_prompt_unlock_pattern) : ((LinearLayout) this).mContext.getText(R.string.biometric_prompt_unlock_password);
    }

    public int getEffectiveUserId() {
        return this.mPromptConfig.mUserId;
    }

    public TextView getErrorTextView() {
        return this.mErrorView;
    }

    public final double getLengthFromPercent(int i, int i2) {
        ((LinearLayout) this).mContext.getDisplay().getRealSize(new Point());
        ((LinearLayout) this).mContext.getResources().getValue(i, new TypedValue(), true);
        return (i2 == 1 ? r1.x : r1.y) * r0.getFraction(1.0f, 1.0f);
    }

    public String getPassword() {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText == null) {
            return null;
        }
        return editText.getText().toString();
    }

    public ScrollView getScrollView() {
        return (ScrollView) findViewById(R.id.id_credential_prompt_dialog_scroll);
    }

    public int getSelection() {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText == null) {
            return -1;
        }
        return editText.getSelectionEnd();
    }

    public boolean isScreenLandscape() {
        return Utils.isScreenLandscape(((LinearLayout) this).mContext);
    }

    public boolean isTwoPaneLandScape() {
        return this instanceof AuthCredentialPasswordTabletView;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        Drawable drawable;
        super.onAttachedToWindow();
        Log.d("BSS_AuthCredentialView", "onAttachedToWindow");
        this.mTxtViewTitle = (TextView) findViewById(R.id.title);
        this.mTxtViewSubTitle = (TextView) findViewById(R.id.subtitle);
        if (this.mPromptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            this.mTxtViewDescription = (TextView) findViewById(R.id.customized_view_description);
        } else {
            this.mTxtViewDescription = (TextView) findViewById(R.id.description);
        }
        setDescriptionVisibility(0);
        this.mErrorView = (TextView) findViewById(R.id.error);
        this.mBtnContinue = (Button) findViewById(R.id.btn_continue);
        this.mBtnEmergencyCalls = (Button) findViewById(R.id.emergency_call_button);
        Button button = this.mBtnContinue;
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = (Button) findViewById(R.id.btn_cancel);
        this.mBtnCancel = button2;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        Bundle bundle = this.mPromptConfig.mSavedInstanceState;
        if (bundle != null) {
            int i = bundle.getInt("alertMode", 0);
            this.mAlertMode = i;
            if (i != 0) {
                this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 1));
            }
        }
        if (this.mAlertMode == 0) {
            setTitleAndDescription();
            if (TextUtils.isEmpty(this.mDefaultUnlockGuide)) {
                this.mDefaultUnlockGuide = getDefaultUnlockGuide();
            }
            setText(this.mErrorView, this.mDefaultUnlockGuide);
        }
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        if (this.mPromptConfig.mIsManagedProfile) {
            drawable = getResources().getDrawable(R.drawable.auth_dialog_enterprise, ((LinearLayout) this).mContext.getTheme());
            int i2 = this.mPromptConfig.mOrganizationColor;
            if (i2 != 0) {
                drawable.setTint(i2);
            }
        } else {
            drawable = getResources().getDrawable(R.drawable.auth_dialog_lock, ((LinearLayout) this).mContext.getTheme());
        }
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        if (this.mPromptConfig.mPromptInfo.isShowEmergencyCallButton()) {
            this.mBtnEmergencyCalls.setVisibility(0);
            this.mBtnEmergencyCalls.setOnClickListener(this);
        }
        resizeMainLayout();
        setDescriptionMargins();
        if (this.mPromptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.id_prompt_description_layout_list_box);
            this.mTxtViewDescription.setTextAlignment(5);
            PromptContentViewWithMoreOptionsButton contentView = this.mPromptConfig.mPromptInfo.getContentView();
            if (this.mPromptConfig.mPromptInfo.isContentViewMoreOptionsButtonUsed()) {
                showMoreOptionButton();
                String description = contentView.getDescription();
                if (!TextUtils.isEmpty(description)) {
                    setText(this.mTxtViewDescription, description);
                }
                TextView textView = (TextView) findViewById(R.id.customized_view_more_options_button);
                if (textView != null) {
                    if ("android.server.biometrics.cts".contentEquals(this.mPromptConfig.mPackageName) && "More options".contentEquals(textView.getText())) {
                        textView.setText("More Options");
                    }
                    textView.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ((BiometricPromptClient) AuthCredentialView.this.mPromptConfig.mCallback).onDismissed(8, null);
                        }
                    });
                }
            } else if (linearLayout != null) {
                linearLayout.setVisibility(0);
                PromptVerticalListContentView promptVerticalListContentView = (PromptVerticalListContentView) contentView;
                String description2 = promptVerticalListContentView.getDescription();
                if (!TextUtils.isEmpty(description2)) {
                    setText(this.mTxtViewDescription, description2);
                }
                List listItems = promptVerticalListContentView.getListItems();
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < listItems.size(); i3++) {
                    if (listItems.get(i3) instanceof PromptContentItemBulletedText) {
                        arrayList.add(((PromptContentItemBulletedText) listItems.get(i3)).getText());
                    } else if (listItems.get(i3) instanceof PromptContentItemPlainText) {
                        arrayList.add(((PromptContentItemPlainText) listItems.get(i3)).getText());
                    }
                }
                ListView listView = (ListView) findViewById(R.id.prompt_description_list_view);
                listView.setAdapter((ListAdapter) new ArrayAdapter(((LinearLayout) this).mContext, R.layout.biometric_prompt_list_item, arrayList));
                int count = listView.getAdapter().getCount();
                int i4 = 0;
                for (int i5 = 0; i5 < count; i5++) {
                    View view = listView.getAdapter().getView(i5, null, listView);
                    view.measure(View.MeasureSpec.makeMeasureSpec((int) (listView.getResources().getDisplayMetrics().density * 500.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
                    i4 += view.getMeasuredHeight();
                }
                int dividerHeight = (count - 1) * listView.getDividerHeight();
                int paddingBottom = listView.getPaddingBottom() + listView.getPaddingTop();
                ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                layoutParams.height = i4 + dividerHeight + paddingBottom;
                listView.setLayoutParams(layoutParams);
                listView.requestLayout();
            }
        }
        updateScrollViewDivider();
        startLockoutTimer(this.mLockPatternUtils.getLockoutAttemptDeadline(this.mPromptConfig.mUserId));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.btn_cancel) {
            ((BiometricPromptClient) getCallback()).sendEvent(1003, 1);
            ((BiometricPromptClient) getCallback()).onUserCancel(3);
            onCancelButtonClicked();
        } else {
            if (view.getId() == R.id.btn_continue) {
                if (this.mAlertMode != 0) {
                    exitAlertMode();
                    return;
                } else {
                    onContinueButtonClicked();
                    return;
                }
            }
            if (view.getId() == R.id.emergency_call_button) {
                Intent createLaunchEmergencyDialerIntent = ((TelecomManager) ((LinearLayout) this).mContext.getSystemService(TelecomManager.class)).createLaunchEmergencyDialerIntent(null);
                createLaunchEmergencyDialerIntent.setFlags(335544320);
                ((LinearLayout) this).mContext.startActivity(createLaunchEmergencyDialerIntent);
                ((BiometricPromptClient) getCallback()).onUserCancel(3);
                onCancelButtonClicked();
            }
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (isTwoPaneLandScape()) {
            return;
        }
        resizeMainLayout();
    }

    public void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        Log.d("BSS_AuthCredentialView", "onCredentialVerified: " + i);
        if (verifyCredentialResponse.isMatched()) {
            this.mClearErrorRunnable.run();
            this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mPromptConfig.mUserId);
            this.mLockPatternUtils.userPresent(this.mPromptConfig.mUserId);
            long gatekeeperPasswordHandle = verifyCredentialResponse.getGatekeeperPasswordHandle();
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            PromptConfig promptConfig = this.mPromptConfig;
            ((BiometricPromptClient) getCallback()).onDismissed(7, lockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, promptConfig.mOperationId, promptConfig.mUserId).getGatekeeperHAT());
            this.mLockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
            return;
        }
        if (i <= 0) {
            this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.mUserId);
            if (updateErrorMessage()) {
                return;
            }
            showRetryMessage();
            return;
        }
        this.mHandler.removeCallbacks(this.mClearErrorRunnable);
        long lockoutAttemptDeadline = this.mLockPatternUtils.setLockoutAttemptDeadline(this.mPromptConfig.mUserId, i);
        this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.mUserId);
        if (updateErrorMessage()) {
            return;
        }
        startLockoutTimer(lockoutAttemptDeadline);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        LockoutTimer.AnonymousClass1 anonymousClass1;
        super.onDetachedFromWindow();
        Log.d("BSS_AuthCredentialView", "onDetachedFromWindow");
        this.mIsDetachedFromWindow = true;
        LockoutTimer lockoutTimer = this.mLockoutTimer;
        if (lockoutTimer != null && (anonymousClass1 = lockoutTimer.mCountDownTimer) != null) {
            anonymousClass1.cancel();
            lockoutTimer.mCountDownTimer = null;
        }
        if (this.mAlertMode != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("alertMode", this.mAlertMode);
            this.mPromptConfig.mSavedInstanceState = bundle;
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        BiometricPromptCallback biometricPromptCallback;
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1 && (biometricPromptCallback = this.mPromptConfig.mCallback) != null) {
            ((BiometricPromptClient) biometricPromptCallback).sendEvent(1003, 1);
            ((BiometricPromptClient) this.mPromptConfig.mCallback).onUserCancel(2);
        }
        return true;
    }

    public void onLockoutTimeoutFinish() {
        Log.d("BSS_AuthCredentialView", "onLockoutTimeoutFinish");
        this.mLockPatternUtils.getLockoutAttemptDeadline(this.mPromptConfig.mUserId);
        this.mClearErrorRunnable.run();
    }

    public void resizeMainLayout() {
        LinearLayout.LayoutParams layoutParams;
        int displayHeight;
        if (!isTwoPaneLandScape()) {
            Log.d("BSS_AuthCredentialView", "resizeMainLayoutOnePane");
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_view);
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.title_view);
            LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.edit_view);
            if (linearLayout == null || linearLayout2 == null || linearLayout3 == null) {
                return;
            }
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.height = -1;
            if (isScreenLandscape()) {
                if (Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY && ((LinearLayout) this).mContext.getResources().getConfiguration().semDisplayDeviceType == 0) {
                    layoutParams2.height = ((int) getLengthFromPercent(R.fraction.auth_credential_foldable_main_height_percent, 2)) - Utils.getStatusBarHeight(((LinearLayout) this).mContext);
                }
                layoutParams2.width = -1;
                layoutParams2.topMargin = 0;
                linearLayout.setOrientation(0);
                layoutParams = new LinearLayout.LayoutParams(0, -1);
                layoutParams.weight = 1.0f;
            } else {
                if (Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY && ((LinearLayout) this).mContext.getResources().getConfiguration().semDisplayDeviceType == 0) {
                    layoutParams2.width = (int) getLengthFromPercent(R.fraction.auth_credential_foldable_main_width_percent, 1);
                }
                layoutParams2.topMargin = (int) ((LinearLayout) this).mContext.getResources().getDimension(R.dimen.auth_credential_main_view_margin_top);
                linearLayout.setOrientation(1);
                layoutParams = new LinearLayout.LayoutParams(-1, -2);
            }
            linearLayout.setLayoutParams(layoutParams2);
            linearLayout2.setLayoutParams(layoutParams);
            linearLayout3.setLayoutParams(layoutParams);
            return;
        }
        Log.d("BSS_AuthCredentialView", "resizeMainLayoutTwoPane");
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.main_view);
        if (constraintLayout != null) {
            boolean isScreenLandscape = isScreenLandscape();
            int navigationBarHeight = Utils.getNavigationBarHeight(((LinearLayout) this).mContext);
            int statusBarHeight = Utils.getStatusBarHeight(((LinearLayout) this).mContext);
            Context context = ((LinearLayout) this).mContext;
            if (Utils.isScreenLandscape(context)) {
                Display display = context.getDisplay();
                Point point = new Point();
                try {
                    display.getRealSize(point);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                displayHeight = point.x;
            } else {
                displayHeight = Utils.getDisplayHeight(context);
            }
            int displayWidthInPortraitMode = isScreenLandscape ? displayHeight - navigationBarHeight : Utils.getDisplayWidthInPortraitMode(((LinearLayout) this).mContext);
            int dimensionPixelSize = ((int) ((isScreenLandscape ? displayWidthInPortraitMode : displayHeight) * (isScreenLandscape ? 0.18d : 0.25d))) - (((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.auth_credential_main_view_tablet_margin_bottom) + (navigationBarHeight + statusBarHeight));
            int i = (int) (displayWidthInPortraitMode * 0.1d);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) constraintLayout.getLayoutParams();
            layoutParams3.rightMargin = i;
            layoutParams3.leftMargin = i;
            layoutParams3.gravity = 16;
            layoutParams3.height = (int) (displayHeight * (Utils.isScreenLandscape(((LinearLayout) this).mContext) ? 0.35d : 0.55d));
            constraintLayout.setLayoutParams(layoutParams3);
            Log.d("BSS_AuthCredentialView", "displayHeight = " + displayHeight);
            Log.d("BSS_AuthCredentialView", "Top Margin = " + dimensionPixelSize);
        }
    }

    public void setDescriptionMargins() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(Utils.dipToPixel(((LinearLayout) this).mContext, 24.0d), Utils.dipToPixel(((LinearLayout) this).mContext, 16.0d), Utils.dipToPixel(((LinearLayout) this).mContext, 24.0d), this.mPromptConfig.mPromptInfo.semIsDescriptionOptionalUsed() ? 0 : Utils.dipToPixel(((LinearLayout) this).mContext, 50.0d));
        this.mTxtViewDescription.setLayoutParams(layoutParams);
        this.mTxtViewDescription.requestLayout();
    }

    public void setDescriptionVisibility(int i) {
        TextView textView = this.mTxtViewDescription;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    public void setErrorTextVisibility(int i) {
        TextView textView = this.mErrorView;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    public void setLockoutTimer(LockoutTimer lockoutTimer) {
        this.mLockoutTimer = lockoutTimer;
    }

    public void setPassword(String str) {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText != null) {
            editText.setText(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0061, code lost:
    
        if (android.text.TextUtils.equals(r0, r5.mDefaultUnlockGuide) == false) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPromptConfig(com.samsung.android.biometrics.app.setting.prompt.PromptConfig r6) {
        /*
            r5 = this;
            r5.mPromptConfig = r6
            com.android.internal.widget.LockPatternUtils r0 = r6.mLockPatternUtils
            r5.mLockPatternUtils = r0
            java.lang.CharSequence r0 = r5.getDefaultUnlockGuide()
            r5.mDefaultUnlockGuide = r0
            android.hardware.biometrics.PromptInfo r0 = r6.mPromptInfo
            java.lang.CharSequence r1 = r0.getDeviceCredentialTitle()
            if (r1 == 0) goto L15
            goto L19
        L15:
            java.lang.CharSequence r1 = r0.getTitle()
        L19:
            r5.mTitle = r1
            android.hardware.biometrics.PromptInfo r6 = r6.mPromptInfo
            java.lang.CharSequence r0 = r6.getDeviceCredentialSubtitle()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r2 = ""
            if (r1 == 0) goto L5b
            java.lang.CharSequence r0 = r6.getSubtitle()
            boolean r6 = r6.isUseDefaultSubtitle()
            if (r6 == 0) goto L65
            boolean r6 = android.text.TextUtils.isEmpty(r0)
            if (r6 != 0) goto L65
            android.content.Context r6 = r5.mContext
            android.content.res.Resources r6 = r6.getResources()
            java.lang.String r1 = "android"
            java.lang.String r3 = "biometric_dialog_default_subtitle"
            java.lang.String r4 = "string"
            int r6 = r6.getIdentifier(r3, r4, r1)
            if (r6 <= 0) goto L52
            android.content.Context r1 = r5.mContext     // Catch: android.content.res.Resources.NotFoundException -> L65
            java.lang.CharSequence r6 = r1.getText(r6)     // Catch: android.content.res.Resources.NotFoundException -> L65
            goto L53
        L52:
            r6 = r2
        L53:
            boolean r6 = android.text.TextUtils.equals(r0, r6)     // Catch: android.content.res.Resources.NotFoundException -> L65
            if (r6 != 0) goto L64
            r2 = r0
            goto L64
        L5b:
            java.lang.CharSequence r6 = r5.mDefaultUnlockGuide
            boolean r6 = android.text.TextUtils.equals(r0, r6)
            if (r6 != 0) goto L64
            goto L65
        L64:
            r0 = r2
        L65:
            r5.mSubTitle = r0
            com.samsung.android.biometrics.app.setting.prompt.PromptConfig r6 = r5.mPromptConfig
            android.hardware.biometrics.PromptInfo r6 = r6.mPromptInfo
            java.lang.CharSequence r0 = r6.getDeviceCredentialDescription()
            if (r0 == 0) goto L72
            goto L76
        L72:
            java.lang.CharSequence r0 = r6.getDescription()
        L76:
            r5.mDescription = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView.setPromptConfig(com.samsung.android.biometrics.app.setting.prompt.PromptConfig):void");
    }

    public void setSelection(int i) {
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        if (editText == null || i < 0) {
            return;
        }
        editText.setSelection(i);
    }

    public void setSubTitleVisibility(int i) {
        TextView textView = this.mTxtViewSubTitle;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    public void setTextToSubTitleView(CharSequence charSequence) {
        setText(this.mTxtViewSubTitle, charSequence);
    }

    public final void setTitleAndDescription() {
        setText(this.mTxtViewTitle, this.mTitle);
        if (this.mPromptConfig.mPromptInfo.getContentView() instanceof PromptVerticalListContentView) {
            TextView textView = this.mTxtViewSubTitle;
            if (textView != null) {
                textView.setVisibility(8);
            }
        } else {
            TextView textView2 = this.mTxtViewSubTitle;
            CharSequence charSequence = this.mSubTitle;
            if (textView2 != null) {
                if (TextUtils.isEmpty(charSequence)) {
                    textView2.setVisibility(8);
                } else {
                    textView2.setText(charSequence);
                }
                Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
            }
        }
        TextView textView3 = this.mTxtViewDescription;
        CharSequence charSequence2 = this.mDescription;
        if (textView3 == null) {
            return;
        }
        if (TextUtils.isEmpty(charSequence2)) {
            textView3.setVisibility(8);
        } else {
            textView3.setText(charSequence2);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public void setTitleVisibility(int i) {
        TextView textView = this.mTxtViewTitle;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    public final void showError(String str) {
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

    public void showLockoutMessage(String str) {
        setText(getErrorTextView(), str);
    }

    public void showMoreOptionButton() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.id_prompt_description_layout_more_option_box);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
    }

    public void showRetryMessage() {
        showError(getResources().getString(R.string.sec_lockpassword_need_to_unlock_wrong));
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [android.os.CountDownTimer, com.samsung.android.biometrics.app.setting.prompt.credential.LockoutTimer$1] */
    public final void startLockoutTimer(long j) {
        if (j == 0) {
            return;
        }
        final long elapsedRealtime = j - SystemClock.elapsedRealtime();
        if (elapsedRealtime > 0) {
            if (this.mLockoutTimer == null) {
                this.mLockoutTimer = new LockoutTimer(getContext());
            }
            final LockoutTimer lockoutTimer = this.mLockoutTimer;
            lockoutTimer.mListener = new AnonymousClass3();
            LockoutTimer.AnonymousClass1 anonymousClass1 = lockoutTimer.mCountDownTimer;
            if (anonymousClass1 != null) {
                anonymousClass1.cancel();
            }
            ?? r1 = new CountDownTimer(elapsedRealtime) { // from class: com.samsung.android.biometrics.app.setting.prompt.credential.LockoutTimer.1
                @Override // android.os.CountDownTimer
                public final void onFinish() {
                    AuthCredentialView.AnonymousClass3 anonymousClass3 = LockoutTimer.this.mListener;
                    if (anonymousClass3 != null) {
                        AuthCredentialView authCredentialView = AuthCredentialView.this;
                        authCredentialView.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(authCredentialView, 6));
                    }
                }

                @Override // android.os.CountDownTimer
                public final void onTick(long j2) {
                    final String str;
                    LockoutTimer lockoutTimer2 = LockoutTimer.this;
                    int i = (int) (j2 / 1000);
                    int i2 = i / 60;
                    if (i > 60) {
                        int i3 = i2 + 1;
                        str = lockoutTimer2.mContext.getResources().getQuantityString(R.plurals.biometric_dialog_credential_too_many_attempts_min, i3, Integer.valueOf(i3));
                    } else if (i > 0) {
                        str = lockoutTimer2.mContext.getResources().getQuantityString(R.plurals.biometric_dialog_credential_too_many_attempts, i != 1 ? 3 : 1, Integer.valueOf(i));
                    } else {
                        str = "";
                    }
                    final AuthCredentialView.AnonymousClass3 anonymousClass3 = lockoutTimer2.mListener;
                    if (anonymousClass3 != null) {
                        AuthCredentialView.this.mHandler.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView$3$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                AuthCredentialView.AnonymousClass3 anonymousClass32 = AuthCredentialView.AnonymousClass3.this;
                                AuthCredentialView.this.showLockoutMessage(str);
                            }
                        });
                    }
                }
            };
            lockoutTimer.mCountDownTimer = r1;
            r1.start();
            this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 4));
        }
    }

    public final boolean updateErrorMessage() {
        int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mPromptConfig.mUserId);
        int maximumFailedPasswordsForWipe = this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(this.mPromptConfig.mUserId);
        Log.d("BSS_AuthCredentialView", "updateErrorMessage: " + currentFailedPasswordAttempts + ", " + maximumFailedPasswordsForWipe);
        if (maximumFailedPasswordsForWipe <= 0 || currentFailedPasswordAttempts <= 0) {
            return false;
        }
        showError(getResources().getString(R.string.biometric_dialog_credential_attempts_before_wipe, Integer.valueOf(currentFailedPasswordAttempts), Integer.valueOf(maximumFailedPasswordsForWipe)));
        int i = maximumFailedPasswordsForWipe - currentFailedPasswordAttempts;
        if (i == 1) {
            this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 2));
        } else if (i <= 0) {
            this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda0(this, 3));
        }
        return true;
    }

    public final void updateScrollViewDivider() {
        final ScrollView scrollView = getScrollView();
        if (scrollView == null) {
            return;
        }
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                View findViewById = AuthCredentialView.this.findViewById(R.id.id_credential_scroll_top_divider);
                View findViewById2 = AuthCredentialView.this.findViewById(R.id.id_credential_scroll_bottom_divider);
                int height = scrollView.getChildAt(0).getHeight();
                if (scrollView.getHeight() < scrollView.getPaddingBottom() + scrollView.getPaddingTop() + height) {
                    findViewById.setVisibility(0);
                    findViewById2.setVisibility(0);
                } else {
                    findViewById.setVisibility(4);
                    findViewById2.setVisibility(4);
                }
                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void onCancelButtonClicked() {
    }

    public void onContinueButtonClicked() {
    }

    public void onLockoutTimeoutStart() {
    }

    public void setPasswordShown(boolean z) {
    }
}
