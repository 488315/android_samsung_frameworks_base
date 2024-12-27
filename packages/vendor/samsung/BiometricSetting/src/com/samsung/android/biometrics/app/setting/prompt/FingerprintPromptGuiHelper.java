package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.media.AudioAttributes;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SettingHelper;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class FingerprintPromptGuiHelper extends BiometricPromptGuiHelper {
    public int mBottomHeight;
    public int mDescriptionTextViewHeight;
    public int mDialogHeight;
    public int mDialogMinimumHeight;
    public final Handler mH;
    public FingerprintPromptGuiHelper$$ExternalSyntheticLambda0 mRunnableClearMessage;
    public int mSensorIconEdge;
    public int mTitleTextViewHeight;
    public TextToSpeech mTts;

    public FingerprintPromptGuiHelper(
            Context context,
            Looper looper,
            View view,
            PromptConfig promptConfig,
            FingerprintSensorInfo fingerprintSensorInfo) {
        super(context, view, promptConfig, fingerprintSensorInfo);
        this.mTts = null;
        this.TAG += ".P";
        this.mH = new Handler(looper);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void cleanUpPrompt() {
        super.cleanUpPrompt();
        this.mPositiveButtonLayout.setVisibility(0);
        this.mH.removeCallbacks(this.mRunnableClearMessage);
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
        showTemporaryMessage(
                0,
                (i == 7 || i == 9)
                        ? getLockoutErrorMessage(i)
                        : FingerprintManager.getErrorString(this.mContext, i, i2),
                false);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationFailed() {
        showTemporaryMessage(
                -1,
                this.mContext.getString(
                        R.string.fingerprint_biometric_prompt_identify_failure_title),
                true);
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
    public final void initPrompt() {
        super.initPrompt();
        this.mRunnableClearMessage =
                new Runnable() { // from class:
                                 // com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintPromptGuiHelper fingerprintPromptGuiHelper =
                                FingerprintPromptGuiHelper.this;
                        PromptConfig promptConfig = fingerprintPromptGuiHelper.mPromptConfig;
                        if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
                            if (TextUtils.isEmpty(promptConfig.mPromptInfo.getSubtitle())) {
                                fingerprintPromptGuiHelper.showTemporaryMessage(0, "", false);
                            } else {
                                fingerprintPromptGuiHelper.showTemporaryMessage(
                                        0,
                                        promptConfig.mPromptInfo.getSubtitle().toString(),
                                        false);
                            }
                        } else if (TextUtils.isEmpty(fingerprintPromptGuiHelper.mDescriptionText)) {
                            fingerprintPromptGuiHelper.showTemporaryMessage(0, "", false);
                        } else {
                            fingerprintPromptGuiHelper.showTemporaryMessage(
                                    0,
                                    fingerprintPromptGuiHelper.mDescriptionText.toString(),
                                    false);
                        }
                        if (promptConfig.isCheckToEnrollMode()) {
                            fingerprintPromptGuiHelper.mDescriptionTxtView.setMinimumHeight(
                                    fingerprintPromptGuiHelper.mDescriptionTextViewHeight);
                            fingerprintPromptGuiHelper.mDescriptionTxtView.setPadding(0, 0, 0, 0);
                        }
                        fingerprintPromptGuiHelper.updateIcon$1(0);
                    }
                };
        try {
            this.mPositiveButtonLayout.setVisibility(8);
            LinearLayout linearLayout =
                    (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_feedback_layout);
            if (linearLayout != null) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.topMargin = 0;
                linearLayout.setLayoutParams(layoutParams);
            }
            FrameLayout.LayoutParams layoutParams2 =
                    (FrameLayout.LayoutParams) this.mBottomButton.getLayoutParams();
            this.mBottomButton.semSetButtonShapeEnabled(true);
            this.mBottomButton.setLayoutParams(layoutParams2);
            setSizeInfo();
            LinearLayout linearLayout2 = this.mDialogLayout;
            if (linearLayout2 != null) {
                linearLayout2
                        .getViewTreeObserver()
                        .addOnGlobalLayoutListener(
                                new ViewTreeObserver
                                        .OnGlobalLayoutListener() { // from class:
                                                                    // com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper.1
                                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                                    public final void onGlobalLayout() {
                                        if (BiometricPromptGuiHelper.DEBUG) {
                                            Log.d(
                                                    FingerprintPromptGuiHelper.this.TAG,
                                                    "mDialogLayout.onGlobalLayout: called");
                                        }
                                        try {
                                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper =
                                                    FingerprintPromptGuiHelper.this;
                                            fingerprintPromptGuiHelper.mDialogMinimumHeight =
                                                    fingerprintPromptGuiHelper.mDialogLayout
                                                            .getHeight();
                                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper2 =
                                                    FingerprintPromptGuiHelper.this;
                                            fingerprintPromptGuiHelper2.mDialogHeight =
                                                    fingerprintPromptGuiHelper2.mDialogLayout
                                                            .getHeight();
                                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper3 =
                                                    FingerprintPromptGuiHelper.this;
                                            fingerprintPromptGuiHelper3.mTitleTextViewHeight =
                                                    fingerprintPromptGuiHelper3.mTitleTxtView
                                                            .getHeight();
                                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper4 =
                                                    FingerprintPromptGuiHelper.this;
                                            fingerprintPromptGuiHelper4.mBottomHeight =
                                                    fingerprintPromptGuiHelper4.mBottomButton
                                                            .getHeight();
                                            FingerprintPromptGuiHelper fingerprintPromptGuiHelper5 =
                                                    FingerprintPromptGuiHelper.this;
                                            fingerprintPromptGuiHelper5.mDescriptionTextViewHeight =
                                                    (int)
                                                            ((((((fingerprintPromptGuiHelper5
                                                                                                                    .mDialogHeight
                                                                                                            - fingerprintPromptGuiHelper5
                                                                                                                    .mTitleTextViewHeight)
                                                                                                    - fingerprintPromptGuiHelper5
                                                                                                            .mBottomHeight)
                                                                                            - fingerprintPromptGuiHelper5
                                                                                                    .mContext
                                                                                                    .getResources()
                                                                                                    .getDimension(
                                                                                                            R
                                                                                                                    .dimen
                                                                                                                    .biometric_prompt_verification_top_margin))
                                                                                    - FingerprintPromptGuiHelper
                                                                                            .this
                                                                                            .mContext
                                                                                            .getResources()
                                                                                            .getDimension(
                                                                                                    R
                                                                                                            .dimen
                                                                                                            .fingerprint_verification_description_margin))
                                                                            - FingerprintPromptGuiHelper
                                                                                    .this
                                                                                    .mContext
                                                                                    .getResources()
                                                                                    .getDimension(
                                                                                            R.dimen
                                                                                                    .fingerprint_verification_bottom_margin))
                                                                    - FingerprintPromptGuiHelper
                                                                            .this
                                                                            .mContext
                                                                            .getResources()
                                                                            .getDimension(
                                                                                    R.dimen
                                                                                            .fingerprint_verification_middle_margin));
                                            Log.i(
                                                    FingerprintPromptGuiHelper.this.TAG,
                                                    "DialogHeight : "
                                                            + FingerprintPromptGuiHelper.this
                                                                    .mDialogHeight
                                                            + " DescriptionHeight : "
                                                            + FingerprintPromptGuiHelper.this
                                                                    .mDescriptionTextViewHeight);
                                            if (FingerprintPromptGuiHelper.this.mPromptConfig
                                                    .isCheckToEnrollMode()) {
                                                FingerprintPromptGuiHelper
                                                        fingerprintPromptGuiHelper6 =
                                                                FingerprintPromptGuiHelper.this;
                                                fingerprintPromptGuiHelper6.mDialogLayout
                                                        .setMinimumHeight(
                                                                fingerprintPromptGuiHelper6
                                                                        .mDialogMinimumHeight);
                                                FingerprintPromptGuiHelper
                                                        fingerprintPromptGuiHelper7 =
                                                                FingerprintPromptGuiHelper.this;
                                                fingerprintPromptGuiHelper7.mDescriptionTxtView
                                                        .setMinimumHeight(
                                                                fingerprintPromptGuiHelper7
                                                                        .mDescriptionTextViewHeight);
                                            }
                                            FingerprintPromptGuiHelper.this
                                                    .mDialogLayout
                                                    .getViewTreeObserver()
                                                    .removeOnGlobalLayoutListener(this);
                                        } catch (Exception e) {
                                            Log.w(
                                                    FingerprintPromptGuiHelper.this.TAG,
                                                    "onGlobalLayout: ",
                                                    e);
                                        }
                                    }
                                });
            }
            Button button = this.mBottomButton;
            if (button != null) {
                button.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper$$ExternalSyntheticLambda2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                PromptConfig promptConfig =
                                        FingerprintPromptGuiHelper.this.mPromptConfig;
                                if (promptConfig.isDeviceCredentialAllowed()) {
                                    promptConfig.mCallback.onDeviceCredentialPressed();
                                    return;
                                }
                                BiometricPromptClient biometricPromptClient =
                                        (BiometricPromptClient) promptConfig.mCallback;
                                biometricPromptClient.mDismissedReason = 2;
                                biometricPromptClient.stop();
                            }
                        });
            }
            if (isAnyUdfps()) {
                FingerprintSensorInfo fingerprintSensorInfo = this.mFingerprintSensorInfo;
                if (fingerprintSensorInfo != null
                        && fingerprintSensorInfo.mIsNaviHide
                        && !SettingHelper.isNavigationBarHidden(this.mContext)) {
                    this.mBaseView.setSystemUiVisibility(18874368);
                }
                if (Utils.isTalkBackEnabled(this.mContext)) {
                    runFingerPositionTalkBack();
                }
            }
            this.mBaseView.setFocusableInTouchMode(true);
            this.mBaseView.requestFocus();
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("FingerprintPromptWindow: "), this.TAG);
            ((BiometricPromptClient) this.mPromptConfig.mCallback).onUserCancel(-1);
        }
    }

    public final boolean isAnyUdfps() {
        FingerprintSensorInfo fingerprintSensorInfo = this.mFingerprintSensorInfo;
        return fingerprintSensorInfo != null && fingerprintSensorInfo.mIsAnyUdfps;
    }

    public final void runFingerPositionTalkBack() {
        if (Utils.isTalkBackEnabled(this.mContext) && isAnyUdfps()) {
            TextToSpeech textToSpeech = new TextToSpeech(this.mContext, null);
            this.mTts = textToSpeech;
            textToSpeech.setAudioAttributes(new AudioAttributes.Builder().setUsage(11).build());
            FingerprintManager fingerprintManager =
                    (FingerprintManager) this.mContext.getSystemService("fingerprint");
            if (fingerprintManager == null) {
                Log.w(this.TAG, "onRotationInfoChanged: fingerprintManager is NULL.");
            } else {
                final Rect semGetFingerIconRectInDisplay =
                        fingerprintManager.semGetFingerIconRectInDisplay();
                this.mBaseView.setOnHoverListener(
                        new View
                                .OnHoverListener() { // from class:
                                                     // com.samsung.android.biometrics.app.setting.prompt.FingerprintPromptGuiHelper$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnHoverListener
                            public final boolean onHover(View view, MotionEvent motionEvent) {
                                int inDisplayFingerPositionStringId;
                                FingerprintPromptGuiHelper fingerprintPromptGuiHelper =
                                        FingerprintPromptGuiHelper.this;
                                Rect rect = semGetFingerIconRectInDisplay;
                                fingerprintPromptGuiHelper.getClass();
                                if (motionEvent.getActionMasked() == 9
                                        && fingerprintPromptGuiHelper.mTts != null
                                        && (inDisplayFingerPositionStringId =
                                                        Utils.getInDisplayFingerPositionStringId(
                                                                rect,
                                                                motionEvent.getX(),
                                                                motionEvent.getY()))
                                                > 0) {
                                    String string =
                                            fingerprintPromptGuiHelper.mContext.getString(
                                                    inDisplayFingerPositionStringId);
                                    if (fingerprintPromptGuiHelper.mTts != null
                                            && !TextUtils.isEmpty(string)) {
                                        fingerprintPromptGuiHelper.mTts.speak(string, 0, null);
                                    }
                                }
                                return false;
                            }
                        });
            }
        }
    }

    public final void setPromptLayout(boolean z) {
        int i;
        int i2;
        int displayHeight;
        Log.d(this.TAG, "setPromptLayout: " + z + ", " + this.mDisplayMetrics);
        boolean isAnyUdfps = isAnyUdfps();
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        int dipToPixel = Utils.dipToPixel(this.mContext, 340.0d);
        int leftMargin = getLeftMargin(dipToPixel, z);
        layoutParams.width = dipToPixel;
        if (z && isAnyUdfps) {
            int rotation = Utils.getRotation(this.mContext);
            int i3 = 0;
            if (isAnyUdfps()) {
                if (rotation == 1) {
                    int i4 = ((this.mScreenLandWidth - dipToPixel) / 2) + dipToPixel;
                    Context context = this.mContext;
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
                    int i5 = (displayHeight - this.mSensorIconEdge) - this.mNavigationBarHeight;
                    if (i4 >= i5) {
                        i3 =
                                (i4 - i5)
                                        + ((int)
                                                this.mContext
                                                        .getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .fingerprint_verification_between_icon_prompt_size_land));
                    }
                } else if (rotation == 3
                        && (i = (this.mScreenLandWidth - dipToPixel) / 2)
                                <= (i2 = this.mSensorIconEdge)) {
                    i3 =
                            (i2 - i)
                                    + ((int)
                                            this.mContext
                                                    .getResources()
                                                    .getDimension(
                                                            R.dimen
                                                                    .fingerprint_verification_between_icon_prompt_size_land));
                }
            }
            layoutParams.width -= i3;
            if (rotation == 3) {
                leftMargin += i3;
            }
        }
        layoutParams.gravity = 1;
        setUpLeftMarginViewWidth(leftMargin);
        customizeSwitch(layoutParams.width);
        this.mDialogLayout.setLayoutParams(layoutParams);
    }

    public final void setSizeInfo() {
        int i = this.mNavigationBarHeight;
        try {
            Log.d(
                    this.TAG,
                    "setSizeInfo: mScreenLandWidth="
                            + this.mScreenLandWidth
                            + ", mScreenPortraitWidth="
                            + this.mScreenPortraitWidth
                            + ", mNavigationBarHeight="
                            + i);
            if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                FingerprintSensorInfo fingerprintSensorInfo = this.mFingerprintSensorInfo;
                this.mSensorIconEdge =
                        ((fingerprintSensorInfo.mSensorMarginBottom
                                                + (fingerprintSensorInfo.mSensorAreaHeight / 2))
                                        + (fingerprintSensorInfo.mSensorImageSize / 2))
                                - i;
                Log.i(
                        this.TAG,
                        "FingerprintPromptWindow: "
                                + i
                                + ", "
                                + getBottomMarginForPortrait()
                                + ", "
                                + this.mSensorIconEdge);
            }
        } catch (Exception e) {
            Log.w(this.TAG, "setSizeInfo: ", e);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void show() {
        this.mContext.getDisplay().getRealMetrics(this.mDisplayMetrics);
        setPromptLayout(Utils.isScreenLandscape(this.mContext));
        if (isAnyUdfps()) {
            this.mDescriptionTxtView.setGravity(17);
        }
        boolean isCheckToEnrollMode = this.mPromptConfig.isCheckToEnrollMode();
        LottieAnimationView lottieAnimationView = this.mIconImgView;
        if (isCheckToEnrollMode) {
            lottieAnimationView.setVisibility(8);
            this.mDescriptionTxtView.setGravity(17);
        } else {
            lottieAnimationView.setVisibility(0);
            updateIcon$1(0);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void showBiometricName(String str) {
        Handler handler = this.mH;
        handler.removeCallbacks(this.mRunnableClearMessage);
        this.mDescriptionTxtView.setMinimumHeight(0);
        this.mDescriptionTxtView.setPadding(
                0,
                0,
                0,
                (int)
                        TypedValue.applyDimension(
                                2, 16.0f, this.mContext.getResources().getDisplayMetrics()));
        this.mDescriptionTxtView.setTextSize(
                0,
                (int)
                        this.mContext
                                .getResources()
                                .getDimension(
                                        R.dimen
                                                .fingerprint_verification_enrolled_fingerprint_text_size));
        this.mDescriptionTxtView.setText(str);
        this.mDescriptionTxtView.setContentDescription(str);
        LottieAnimationView lottieAnimationView = this.mIconImgView;
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) lottieAnimationView.getLayoutParams();
        lottieAnimationView.setVisibility(0);
        lottieAnimationView.setImageResource(R.drawable.sem_fingerprint_icon_bg_white);
        lottieAnimationView.setColorFilter(
                this.mContext
                        .getResources()
                        .getColor(
                                R.color.fingerprint_verification_enrolled_fingerprint_icon_color,
                                null));
        layoutParams.width =
                (int)
                        TypedValue.applyDimension(
                                2, 40.0f, this.mContext.getResources().getDisplayMetrics());
        layoutParams.height =
                (int)
                        TypedValue.applyDimension(
                                2, 40.0f, this.mContext.getResources().getDisplayMetrics());
        lottieAnimationView.setLayoutParams(layoutParams);
        handler.postDelayed(this.mRunnableClearMessage, 1000L);
    }

    public final void showTemporaryMessage(int i, String str, boolean z) {
        Handler handler = this.mH;
        handler.removeCallbacks(this.mRunnableClearMessage);
        PromptConfig promptConfig = this.mPromptConfig;
        if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            this.mSubTitleTxtView.setText(str);
        } else if (this.mDescriptionTxtView != null) {
            if (promptConfig.isCheckToEnrollMode()) {
                this.mDialogLayout.setMinimumHeight(this.mDialogMinimumHeight);
                this.mDescriptionTxtView.setMinimumHeight(this.mDescriptionTextViewHeight);
            }
            this.mDescriptionTxtView.setVisibility(8);
            this.mDescriptionTxtView.setTextSize(
                    0,
                    (int)
                            this.mContext
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .fingerprint_verification_description_text_size));
            this.mDescriptionTxtView.setText(str);
            this.mDescriptionTxtView.setContentDescription(str);
            this.mDescriptionTxtView.setVisibility(0);
        }
        updateIcon$1(i);
        if (z) {
            handler.postDelayed(this.mRunnableClearMessage, 3000L);
        }
    }

    public final void updateIcon$1(int i) {
        LottieAnimationView lottieAnimationView = this.mIconImgView;
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) lottieAnimationView.getLayoutParams();
        PromptConfig promptConfig = this.mPromptConfig;
        if (promptConfig.isCheckToEnrollMode()) {
            lottieAnimationView.setVisibility(8);
            this.mDescriptionTxtView.setGravity(17);
            return;
        }
        lottieAnimationView.setColorFilter(
                this.mContext
                        .getResources()
                        .getColor(
                                R.color.fingerprint_verification_fingerprint_icon_default_color,
                                null));
        layoutParams.width =
                (int)
                        TypedValue.applyDimension(
                                2, 40.0f, this.mContext.getResources().getDisplayMetrics());
        layoutParams.height =
                (int)
                        TypedValue.applyDimension(
                                2, 40.0f, this.mContext.getResources().getDisplayMetrics());
        lottieAnimationView.setLayoutParams(layoutParams);
        boolean z = Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
        if (z) {
            lottieAnimationView.setVisibility(0);
        }
        if (promptConfig.mNumberOfAvailableBiometrics > 1 && i == 0) {
            lottieAnimationView.setVisibility(8);
            return;
        }
        lottieAnimationView.setColorFilter((ColorFilter) null);
        lottieAnimationView.setVisibility(0);
        if (i == -1) {
            lottieAnimationView.setAnimation("fingerprint_error_nomatch.json");
        } else if (i != 0) {
            if (i == 1 || i == 2) {
                lottieAnimationView.setAnimation("fingerprint_error_move.json");
            } else if (i == 3) {
                lottieAnimationView.setAnimation("fingerprint_error_wipe.json");
            } else if (i == 5) {
                lottieAnimationView.setAnimation("fingerprint_error_timeout.json");
            } else if (i != 1003) {
                lottieAnimationView.setImageResource(R.drawable.sem_fingerprint_icon_bg_white);
            } else {
                lottieAnimationView.setAnimation("fingerprint_error_presslong.json");
            }
        } else if (z) {
            lottieAnimationView.setVisibility(8);
        } else {
            lottieAnimationView.setImageResource(R.drawable.sem_fingerprint_icon_bg_white);
            lottieAnimationView.setColorFilter(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.fingerprint_verification_fingerprint_icon_default_color,
                                    null));
        }
        lottieAnimationView.playAnimation();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void updateLayoutAsConfigurationChanged(int i) {
        this.mScreenLandWidth = getScreenLandscapeWidthWithoutNavigationBar();
        this.mScreenPortraitWidth = Utils.getDisplayWidthInPortraitMode(this.mContext);
        setPromptLayout(i == 2);
        setUpKnoxBrandLogoImage();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationSucceeded() {}
}
