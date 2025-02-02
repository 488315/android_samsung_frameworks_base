package com.samsung.android.biometrics.app.setting.prompt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.media.AudioAttributes;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SettingHelper;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsInfo;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;

/* loaded from: classes.dex */
public final class FingerprintPromptGuiHelper extends BiometricPromptGuiHelper {
    private int mBottomHeight;
    private int mDescriptionTextViewHeight;
    private int mDialogHeight;
    private View mDialogLeftMarginView;
    private int mDialogMinimumHeight;
    private Rect mFingerPosition;

    /* renamed from: mH */
    private final Handler f32mH;
    private FingerprintPromptGuiHelper$$ExternalSyntheticLambda0 mRunnableClearMessage;
    private int mSensorIconEdge;
    private int mTitleTextViewHeight;
    private View mTopSpaceView;
    private TextToSpeech mTts;

    public static void $r8$lambda$KQ0mBvPmtpHEq3h3tqEOFgW90ME(FingerprintPromptGuiHelper fingerprintPromptGuiHelper) {
        if (TextUtils.isEmpty(fingerprintPromptGuiHelper.mDescriptionText)) {
            fingerprintPromptGuiHelper.showTemporaryMessage(0, "", false);
        } else {
            fingerprintPromptGuiHelper.showTemporaryMessage(0, fingerprintPromptGuiHelper.mDescriptionText.toString(), false);
        }
        fingerprintPromptGuiHelper.updateIcon(0);
    }

    public static void $r8$lambda$aloSwE1gZhrj01xiaCWVKwMTai0(FingerprintPromptGuiHelper fingerprintPromptGuiHelper, MotionEvent motionEvent) {
        int inDisplayFingerPositionStringId;
        fingerprintPromptGuiHelper.getClass();
        if (motionEvent.getActionMasked() != 9 || fingerprintPromptGuiHelper.mTts == null || (inDisplayFingerPositionStringId = Utils.getInDisplayFingerPositionStringId(fingerprintPromptGuiHelper.mFingerPosition, motionEvent.getX(), motionEvent.getY())) <= 0) {
            return;
        }
        String string = fingerprintPromptGuiHelper.mContext.getString(inDisplayFingerPositionStringId);
        if (fingerprintPromptGuiHelper.mTts == null || TextUtils.isEmpty(string)) {
            return;
        }
        fingerprintPromptGuiHelper.mTts.speak(string, 0, null);
    }

    public FingerprintPromptGuiHelper(Context context, Looper looper, View view, PromptConfig promptConfig, UdfpsInfo udfpsInfo) {
        super(context, view, promptConfig, udfpsInfo);
        this.mTts = null;
        this.TAG += ".P";
        this.f32mH = new Handler(looper);
    }

    private void runFingerPositionTalkBack() {
        if (Utils.isTalkBackEnabled(this.mContext) && Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            TextToSpeech textToSpeech = new TextToSpeech(this.mContext, null);
            this.mTts = textToSpeech;
            textToSpeech.setAudioAttributes(new AudioAttributes.Builder().setUsage(11).build());
            FingerprintManager fingerprintManager = (FingerprintManager) this.mContext.getSystemService("fingerprint");
            if (fingerprintManager == null) {
                Log.w(this.TAG, "onRotationInfoChanged: fingerprintManager is NULL.");
            } else {
                this.mFingerPosition = fingerprintManager.semGetFingerIconRectInDisplay();
                this.mBaseView.setOnHoverListener(new View.OnHoverListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnHoverListener
                    public final boolean onHover(View view, MotionEvent motionEvent) {
                        FingerprintPromptGuiHelper.$r8$lambda$aloSwE1gZhrj01xiaCWVKwMTai0(FingerprintPromptGuiHelper.this, motionEvent);
                        return false;
                    }
                });
            }
        }
    }

    private void setSizeInfo() {
        try {
            Log.d(this.TAG, "setSizeInfo: mScreenLandWidth=" + this.mScreenLandWidth + ", mScreenPortraitWidth=" + this.mScreenPortraitWidth + ", mNavigationBarHeight=" + this.mNavigationBarHeight);
            if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                this.mSensorIconEdge = ((this.mUdfpsInfo.getMarginBottom() + (this.mUdfpsInfo.getAreaHeight() / 2)) + (this.mUdfpsInfo.getImageSize() / 2)) - this.mNavigationBarHeight;
                Log.i(this.TAG, "FingerprintPromptWindow: " + this.mNavigationBarHeight + ", " + getBottomMarginForPortrait() + ", " + this.mSensorIconEdge);
            }
        } catch (Exception e) {
            Log.w(this.TAG, "setSizeInfo: ", e);
        }
    }

    private void showTemporaryMessage(int i, String str, boolean z) {
        Handler handler = this.f32mH;
        handler.removeCallbacks(this.mRunnableClearMessage);
        if (this.mDescriptionTxtView != null) {
            if (this.mPromptConfig.isCheckToEnrollMode()) {
                this.mDialogLayout.setMinimumHeight(this.mDialogMinimumHeight);
                this.mDescriptionTxtView.setMinimumHeight(this.mDescriptionTextViewHeight);
            }
            this.mDescriptionTxtView.setVisibility(8);
            updateIcon(i);
            this.mDescriptionTxtView.setTextSize(0, (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_description_text_size));
            this.mDescriptionTxtView.setText(str);
            this.mDescriptionTxtView.setContentDescription(str);
            this.mDescriptionTxtView.setVisibility(0);
        }
        if (z) {
            handler.postDelayed(this.mRunnableClearMessage, 3000L);
        }
    }

    private void updateIcon(int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconImgView.getLayoutParams();
        if (this.mPromptConfig.isCheckToEnrollMode()) {
            this.mIconImgView.setVisibility(8);
            this.mDescriptionTxtView.setGravity(17);
            return;
        }
        this.mIconImgView.setColorFilter(this.mContext.getResources().getColor(R.color.fingerprint_verification_fingerprint_icon_default_color, null));
        layoutParams.width = (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_icon_width);
        layoutParams.height = (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_icon_height);
        this.mIconImgView.setLayoutParams(layoutParams);
        boolean z = Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
        if (z) {
            this.mIconImgView.setVisibility(0);
        }
        if (this.mPromptConfig.isMultiSensorMode() && i == 0) {
            this.mIconImgView.setVisibility(8);
            return;
        }
        this.mIconImgView.setColorFilter((ColorFilter) null);
        this.mIconImgView.setVisibility(0);
        if (i == -1) {
            this.mIconImgView.setAnimation("fingerprint_error_nomatch.json");
        } else if (i != 0) {
            if (i == 1 || i == 2) {
                this.mIconImgView.setAnimation("fingerprint_error_move.json");
            } else if (i == 3) {
                this.mIconImgView.setAnimation("fingerprint_error_wipe.json");
            } else if (i == 5) {
                this.mIconImgView.setAnimation("fingerprint_error_timeout.json");
            } else if (i != 1003) {
                this.mIconImgView.setImageResource(R.drawable.sem_biometric_prompt_dialog_fingerprint);
            } else {
                this.mIconImgView.setAnimation("fingerprint_error_presslong.json");
            }
        } else if (z) {
            this.mIconImgView.setVisibility(8);
        } else {
            this.mIconImgView.setImageResource(R.drawable.sem_biometric_prompt_dialog_fingerprint);
            this.mIconImgView.setColorFilter(this.mContext.getResources().getColor(R.color.fingerprint_verification_fingerprint_icon_default_color, null));
        }
        this.mIconImgView.playAnimation();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void cleanUpPrompt() {
        this.mPositiveButtonLayout.setVisibility(0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTopSpaceView.getLayoutParams();
        layoutParams.topMargin = 0;
        this.mTopSpaceView.setLayoutParams(layoutParams);
        this.f32mH.removeCallbacks(this.mRunnableClearMessage);
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.mTts.shutdown();
            this.mTts = null;
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getDefaultDescriptionMessage() {
        return this.mContext.getString(R.string.fingerprint_verification_description_default_text);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getToastMessageInDex() {
        return this.mContext.getString(R.string.fingerprint_dex_toast);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationError(int i, int i2) {
        showTemporaryMessage(0, FingerprintManager.getErrorString(this.mContext, i, i2), false);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationFailed() {
        showTemporaryMessage(-1, this.mContext.getString(R.string.fingerprint_biometric_prompt_identify_failure_title), true);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationHelp(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showTemporaryMessage(i, str, true);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper$$ExternalSyntheticLambda0] */
    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    @SuppressLint({"WrongConstant"})
    public final void initPrompt() {
        this.mRunnableClearMessage = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FingerprintPromptGuiHelper.$r8$lambda$KQ0mBvPmtpHEq3h3tqEOFgW90ME(FingerprintPromptGuiHelper.this);
            }
        };
        try {
            this.mDisplayType = this.mContext.getResources().getConfiguration().semDisplayDeviceType;
            this.mLastOrientation = isScreenLandscape() ? 2 : 1;
            this.mDialogLeftMarginView = this.mBaseView.findViewById(R.id.id_prompt_dialog_left_space);
            CharSequence description = this.mPromptConfig.getDescription();
            this.mDescriptionText = description;
            if (TextUtils.isEmpty(description)) {
                this.mDescriptionText = getDefaultDescriptionMessage();
            }
            this.mDescriptionTxtView.setText(this.mDescriptionText);
            this.mPositiveButtonLayout.setVisibility(8);
            View findViewById = this.mBaseView.findViewById(R.id.id_prompt_dialog_vertical_layout_top_margin);
            this.mTopSpaceView = findViewById;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.topMargin = Utils.getStatusBarHeight(this.mContext);
            this.mTopSpaceView.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mBottomButton.getLayoutParams();
            this.mBottomButton.semSetButtonShapeEnabled(true);
            this.mBottomButton.setLayoutParams(layoutParams2);
            setSizeInfo();
            View view = this.mBaseView;
            if (view != null) {
                view.setOnKeyListener(new BiometricPromptGuiHelper.ViewOnKeyListenerC02954());
            }
            LinearLayout linearLayout = this.mDialogLayout;
            if (linearLayout != null) {
                linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper.1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        if (BiometricPromptGuiHelper.DEBUG) {
                            Log.d(FingerprintPromptGuiHelper.this.TAG, "mDialogLayout.onGlobalLayout: called");
                        }
                        try {
                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper = FingerprintPromptGuiHelper.this;
                            fingerprintPromptGuiHelper.mDialogMinimumHeight = fingerprintPromptGuiHelper.mDialogLayout.getHeight();
                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper2 = FingerprintPromptGuiHelper.this;
                            fingerprintPromptGuiHelper2.mDialogHeight = fingerprintPromptGuiHelper2.mDialogLayout.getHeight();
                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper3 = FingerprintPromptGuiHelper.this;
                            fingerprintPromptGuiHelper3.mTitleTextViewHeight = fingerprintPromptGuiHelper3.mTitleTxtView.getHeight();
                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper4 = FingerprintPromptGuiHelper.this;
                            fingerprintPromptGuiHelper4.mBottomHeight = fingerprintPromptGuiHelper4.mBottomButton.getHeight();
                            FingerprintPromptGuiHelper.this.mDescriptionTextViewHeight = (int) ((((((r1.mDialogHeight - FingerprintPromptGuiHelper.this.mTitleTextViewHeight) - FingerprintPromptGuiHelper.this.mBottomHeight) - FingerprintPromptGuiHelper.this.mContext.getResources().getDimension(R.dimen.biometric_prompt_verification_top_margin)) - FingerprintPromptGuiHelper.this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_description_margin)) - FingerprintPromptGuiHelper.this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_bottom_margin)) - FingerprintPromptGuiHelper.this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_middle_margin));
                            Log.i(FingerprintPromptGuiHelper.this.TAG, "DialogHeight : " + FingerprintPromptGuiHelper.this.mDialogHeight + " DescriptionHeight : " + FingerprintPromptGuiHelper.this.mDescriptionTextViewHeight);
                            FingerprintPromptGuiHelper.this.mDialogLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } catch (Exception e) {
                            Log.w(FingerprintPromptGuiHelper.this.TAG, "onGlobalLayout: ", e);
                        }
                    }
                });
            }
            Button button = this.mBottomButton;
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        FingerprintPromptGuiHelper fingerprintPromptGuiHelper = FingerprintPromptGuiHelper.this;
                        if (fingerprintPromptGuiHelper.mPromptConfig.isDeviceCredentialAllowed()) {
                            fingerprintPromptGuiHelper.mPromptConfig.getCallback().onDeviceCredentialPressed();
                        } else {
                            fingerprintPromptGuiHelper.mPromptConfig.getCallback().onNegativeButtonPressed();
                        }
                    }
                });
            }
            if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                UdfpsInfo udfpsInfo = this.mUdfpsInfo;
                if (udfpsInfo != null && udfpsInfo.isNaviBarHide() && !SettingHelper.isNavigationBarHidden(this.mContext)) {
                    this.mBaseView.setSystemUiVisibility(18874368);
                }
                if (Utils.isTalkBackEnabled(this.mContext)) {
                    runFingerPositionTalkBack();
                }
            }
            this.mBaseView.setFocusableInTouchMode(true);
            this.mBaseView.requestFocus();
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("FingerprintPromptWindow: "), this.TAG);
            this.mPromptConfig.getCallback().onUserCancel(-1);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        int i2;
        this.mDescriptionTextViewHeight = (int) ((((((this.mDialogHeight - this.mTitleTextViewHeight) - this.mBottomHeight) - this.mContext.getResources().getDimension(R.dimen.biometric_prompt_verification_top_margin)) - this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_description_margin)) - this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_bottom_margin)) - this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_middle_margin));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        Log.i(this.TAG, "onConfigurationChanged(last=" + this.mLastOrientation + ", orientation=" + configuration.orientation + ", type=" + this.mDisplayType + ", prevWidth=" + this.mDisplayMetrics.widthPixels + ", currWidth=" + displayMetrics.widthPixels);
        int i3 = this.mLastOrientation;
        int i4 = configuration.orientation;
        if (i3 == i4 && this.mDisplayType == configuration.semDisplayDeviceType && i4 != 2 && this.mDisplayMetrics.widthPixels == displayMetrics.widthPixels) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTopSpaceView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mDialogLeftMarginView.getLayoutParams();
        layoutParams2.topMargin = Utils.getStatusBarHeight(this.mContext);
        layoutParams3.width = 0;
        this.mDisplayMetrics = displayMetrics;
        if (configuration.orientation == 2) {
            this.mScreenLandWidth = getScreenLandscapeWidthWithoutNavigationBar();
            layoutParams.bottomMargin = getBottomMarginForLandscape();
            if (Utils.isTablet()) {
                int fraction = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_land_tablet, 1, 1) * this.mDisplayMetrics.widthPixels);
                layoutParams.width = fraction;
                layoutParams3.width = (this.mScreenLandWidth - fraction) / 2;
            } else if (!this.mIsSupportDualDisplay || configuration.semDisplayDeviceType == 5) {
                int fraction2 = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_land, 1, 1) * this.mDisplayMetrics.widthPixels);
                layoutParams.width = fraction2;
                layoutParams3.width = (this.mScreenLandWidth - fraction2) / 2;
                if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                    int rotation = Utils.getRotation(this.mContext);
                    if (rotation == 1) {
                        int i5 = this.mScreenLandWidth;
                        int i6 = layoutParams.width;
                        int i7 = ((i5 - i6) / 2) + i6;
                        int displayHeight = (Utils.getDisplayHeight(this.mContext) - this.mSensorIconEdge) - this.mNavigationBarHeight;
                        if (i7 >= displayHeight) {
                            layoutParams.width -= (i7 - displayHeight) + ((int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_between_icon_prompt_size_land));
                        }
                    } else if (rotation == 3 && (i = (this.mScreenLandWidth - layoutParams.width) / 2) <= (i2 = this.mSensorIconEdge)) {
                        int dimension = (i2 - i) + ((int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_between_icon_prompt_size_land));
                        layoutParams3.width += dimension;
                        layoutParams.width -= dimension;
                    }
                }
            } else {
                int fraction3 = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_land_winner, 1, 1) * this.mDisplayMetrics.widthPixels);
                layoutParams.width = fraction3;
                layoutParams3.width = (this.mScreenLandWidth - fraction3) / 2;
            }
        } else {
            this.mScreenPortraitWidth = Utils.getDisplayWidth(this.mContext);
            if (Utils.isTablet()) {
                layoutParams.width = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_portrait_tablet, 1, 1) * this.mDisplayMetrics.widthPixels);
            } else if (!this.mIsSupportDualDisplay || configuration.semDisplayDeviceType == 5) {
                layoutParams.width = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_portrait, 1, 1) * this.mDisplayMetrics.widthPixels);
            } else {
                layoutParams.width = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_portrait_winner, 1, 1) * this.mDisplayMetrics.widthPixels);
            }
            layoutParams3.width = (this.mScreenPortraitWidth - layoutParams.width) / 2;
            layoutParams.gravity = 1;
            layoutParams.bottomMargin = getBottomMarginForPortrait();
        }
        if (this.mPromptConfig.isCheckToEnrollMode()) {
            this.mDialogLayout.setMinimumHeight(this.mDialogMinimumHeight);
            this.mDescriptionTxtView.setMinimumHeight(this.mDescriptionTextViewHeight);
        }
        setUpKnoxBrandLogoImage();
        customizeSwitch(layoutParams.width, 0);
        this.mDialogLayout.setLayoutParams(layoutParams);
        this.mTopSpaceView.setLayoutParams(layoutParams2);
        this.mDialogLeftMarginView.setLayoutParams(layoutParams3);
        this.mLastOrientation = configuration.orientation;
        this.mDisplayType = configuration.semDisplayDeviceType;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onRotationInfoChanged(int i) {
        Configuration configuration = Resources.getSystem().getConfiguration();
        if (this.mLastOrientation == configuration.orientation) {
            onConfigurationChanged(configuration);
            if (Utils.isTalkBackEnabled(this.mContext) && Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                FingerprintManager fingerprintManager = (FingerprintManager) this.mContext.getSystemService("fingerprint");
                if (fingerprintManager == null) {
                    Log.w(this.TAG, "onRotationInfoChanged: fingerprintManager is NULL.");
                    return;
                }
                this.mFingerPosition = fingerprintManager.semGetFingerIconRectInDisplay();
                if (i == 3) {
                    int navigationBarHeight = Utils.getNavigationBarHeight(this.mContext);
                    Rect rect = this.mFingerPosition;
                    rect.left -= navigationBarHeight;
                    rect.right -= navigationBarHeight;
                }
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void show() {
        int i;
        int i2;
        this.mContext.getDisplay().getRealMetrics(this.mDisplayMetrics);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDialogLeftMarginView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        if (isScreenLandscape()) {
            if (Utils.isTablet()) {
                int fraction = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_land_tablet, 1, 1) * this.mDisplayMetrics.widthPixels);
                layoutParams2.width = fraction;
                layoutParams.width = (this.mScreenLandWidth - fraction) / 2;
            } else if (!this.mIsSupportDualDisplay || this.mDisplayType == 5) {
                int fraction2 = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_land, 1, 1) * this.mDisplayMetrics.widthPixels);
                layoutParams2.width = fraction2;
                layoutParams.width = (this.mScreenLandWidth - fraction2) / 2;
                if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                    int rotation = Utils.getRotation(this.mContext);
                    if (rotation == 1) {
                        int i3 = this.mScreenLandWidth;
                        int i4 = layoutParams2.width;
                        int i5 = ((i3 - i4) / 2) + i4;
                        int displayHeight = (Utils.getDisplayHeight(this.mContext) - this.mSensorIconEdge) - this.mNavigationBarHeight;
                        if (i5 >= displayHeight) {
                            layoutParams2.width -= (i5 - displayHeight) + ((int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_between_icon_prompt_size_land));
                        }
                    } else if (rotation == 3 && (i = (this.mScreenLandWidth - layoutParams2.width) / 2) <= (i2 = this.mSensorIconEdge)) {
                        int dimension = (i2 - i) + ((int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_between_icon_prompt_size_land));
                        layoutParams.width += dimension;
                        layoutParams2.width -= dimension;
                    }
                }
            } else {
                int fraction3 = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_land_winner, 1, 1) * this.mDisplayMetrics.widthPixels);
                layoutParams2.width = fraction3;
                layoutParams.width = (this.mScreenLandWidth - fraction3) / 2;
            }
            layoutParams2.gravity = 1;
        } else {
            if (Utils.isTablet()) {
                layoutParams2.width = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_portrait_tablet, 1, 1) * this.mDisplayMetrics.widthPixels);
            } else if (!this.mIsSupportDualDisplay || this.mDisplayType == 5) {
                layoutParams2.width = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_portrait, 1, 1) * this.mDisplayMetrics.widthPixels);
            } else {
                layoutParams2.width = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_portrait_winner, 1, 1) * this.mDisplayMetrics.widthPixels);
            }
            layoutParams.width = (this.mScreenPortraitWidth - layoutParams2.width) / 2;
            Log.d(this.TAG, "FingerprintPromptWindow: called with: Left Margin=" + layoutParams.width);
            Log.d(this.TAG, "FingerprintPromptWindow: called with: mDialogLayout width=" + this.mDialogLayout.getLayoutParams().width);
        }
        this.mDialogLeftMarginView.setLayoutParams(layoutParams);
        customizeSwitch(layoutParams2.width, 0);
        if (isScreenLandscape()) {
            layoutParams2.bottomMargin = getBottomMarginForLandscape();
        } else {
            layoutParams2.bottomMargin = getBottomMarginForPortrait();
        }
        if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            this.mDescriptionTxtView.setGravity(17);
        }
        this.mDialogLayout.setLayoutParams(layoutParams2);
        if (this.mPromptConfig.isCheckToEnrollMode()) {
            this.mIconImgView.setVisibility(8);
            this.mDescriptionTxtView.setGravity(17);
        } else {
            this.mIconImgView.setVisibility(0);
            updateIcon(0);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void showBiometricName(String str) {
        Handler handler = this.f32mH;
        handler.removeCallbacks(this.mRunnableClearMessage);
        this.mDescriptionTxtView.setTextSize(0, (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_enrolled_fingerprint_text_size));
        this.mDescriptionTxtView.setText(str);
        this.mDescriptionTxtView.setContentDescription(str);
        this.mDescriptionTxtView.setGravity(19);
        if (this.mPromptConfig.isCheckToEnrollMode()) {
            this.mDialogLayout.setMinimumHeight(this.mDialogMinimumHeight);
            this.mDescriptionTxtView.setMinimumHeight(this.mDescriptionTextViewHeight);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconImgView.getLayoutParams();
        this.mIconImgView.setVisibility(0);
        this.mIconImgView.setImageResource(R.drawable.sem_biometric_prompt_dialog_fingerprint);
        this.mIconImgView.setColorFilter(this.mContext.getResources().getColor(R.color.fingerprint_verification_enrolled_fingerprint_icon_color, null));
        layoutParams.width = (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_check_added_fingerprint_icon_width);
        layoutParams.height = (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_check_added_fingerprint_icon_height);
        layoutParams.rightMargin = (int) this.mContext.getResources().getDimension(R.dimen.f36xb05f467d);
        this.mIconImgView.setLayoutParams(layoutParams);
        handler.postDelayed(this.mRunnableClearMessage, 1000L);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationSucceeded() {
    }
}
