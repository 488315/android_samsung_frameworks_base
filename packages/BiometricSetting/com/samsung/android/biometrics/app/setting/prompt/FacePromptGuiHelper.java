package com.samsung.android.biometrics.app.setting.prompt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.hardware.SensorPrivacyManager;
import android.hardware.face.FaceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.face.PunchHoleVIHelper;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsInfo;

/* loaded from: classes.dex */
public final class FacePromptGuiHelper extends BiometricPromptGuiHelper {
    protected TextView mFeedbackTextView;
    private boolean mIsCameraBlocked;
    private boolean mIsSupportNegativeButton;
    private LottieAnimationView mPunchHoleIcon;
    private PunchHoleVIHelper mPunchHoleVIHelper;
    private SensorPrivacyManager mSensorPrivacyManager;
    private long mTimePreviousHelpMessage;
    private Rect mViViewLocation;

    /* renamed from: $r8$lambda$XVWCAZ-yaFvMQMB1NvyAo6PYhuo, reason: not valid java name */
    public static /* synthetic */ void m242$r8$lambda$XVWCAZyaFvMQMB1NvyAo6PYhuo(FacePromptGuiHelper facePromptGuiHelper) {
        SensorPrivacyManager sensorPrivacyManager;
        if (facePromptGuiHelper.mIsCameraBlocked && (sensorPrivacyManager = facePromptGuiHelper.mSensorPrivacyManager) != null) {
            sensorPrivacyManager.setSensorPrivacy(2, 2, false);
            facePromptGuiHelper.mReTryButton.setText(facePromptGuiHelper.mContext.getString(R.string.biometric_prompt_postive_retry));
            facePromptGuiHelper.mIsCameraBlocked = false;
        }
        facePromptGuiHelper.mReTryButton.setVisibility(8);
        facePromptGuiHelper.mReTryButton.setEnabled(false);
        facePromptGuiHelper.updateIcon(0);
        facePromptGuiHelper.mDescriptionTxtView.setText(facePromptGuiHelper.mDescriptionText);
        facePromptGuiHelper.playPunchHoleAnimation();
        facePromptGuiHelper.mPromptConfig.getCallback().onTryAgainPressed();
    }

    /* renamed from: $r8$lambda$g1DGZXYJ2AeaZlPrRO_J7m-k5Kg, reason: not valid java name */
    public static /* synthetic */ void m243$r8$lambda$g1DGZXYJ2AeaZlPrRO_J7mk5Kg(FacePromptGuiHelper facePromptGuiHelper) {
        if (facePromptGuiHelper.mPromptConfig.isDeviceCredentialAllowed() && facePromptGuiHelper.mConfirmButton.getVisibility() != 0) {
            facePromptGuiHelper.mPromptConfig.getCallback().onDeviceCredentialPressed();
        } else if (facePromptGuiHelper.mIsSupportNegativeButton) {
            facePromptGuiHelper.mPromptConfig.getCallback().onNegativeButtonPressed();
        } else {
            facePromptGuiHelper.mPromptConfig.getCallback().onUserCancel(2);
        }
    }

    public FacePromptGuiHelper(Context context, View view, PromptConfig promptConfig, UdfpsInfo udfpsInfo) {
        super(context, view, promptConfig, udfpsInfo);
        this.mTimePreviousHelpMessage = 0L;
        this.mPunchHoleIcon = null;
        this.mPunchHoleVIHelper = null;
        this.mViViewLocation = null;
        this.mSensorPrivacyManager = null;
        this.TAG += ".Fa";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pausePunchHoleAnimation() {
        LottieAnimationView lottieAnimationView = this.mPunchHoleIcon;
        if (lottieAnimationView != null && lottieAnimationView.isAnimating()) {
            this.mPunchHoleIcon.setVisibility(8);
            this.mPunchHoleIcon.pauseAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0015, code lost:
    
        if (android.provider.Settings.System.getInt(r0.getContentResolver(), "any_screen_running", 0) == 1) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void playPunchHoleAnimation() {
        boolean z;
        if (this.mPunchHoleIcon == null) {
            return;
        }
        Context context = this.mContext;
        if (context != null) {
            z = true;
        } else {
            Log.w("BSS_Utils", "isOneHandedModeActive : context = null");
        }
        z = false;
        if (z) {
            pausePunchHoleAnimation();
        } else {
            if (this.mPunchHoleIcon.isAnimating()) {
                return;
            }
            this.mPunchHoleIcon.setVisibility(0);
            this.mPunchHoleIcon.setAnimation(this.mPunchHoleVIHelper.getAnimationName(false));
            this.mPunchHoleIcon.playAnimation();
        }
    }

    private void setPunchHoleRotation(int i) {
        if (this.mPunchHoleIcon == null) {
            return;
        }
        this.mViViewLocation = this.mPunchHoleVIHelper.getPunchHoleVIRect();
        this.mPunchHoleIcon.setTranslationX(r0.left);
        this.mPunchHoleIcon.setTranslationY(this.mViViewLocation.top);
        if (i == 1) {
            this.mPunchHoleIcon.setRotation(-90.0f);
        } else {
            if (i != 3) {
                this.mPunchHoleIcon.setRotation(0.0f);
                return;
            }
            this.mPunchHoleIcon.setTranslationX(this.mViViewLocation.left - Utils.getNavigationBarHeight(this.mContext));
            this.mPunchHoleIcon.setTranslationY(this.mViViewLocation.top);
            this.mPunchHoleIcon.setRotation(90.0f);
        }
    }

    private void setTopMarginOfFeedbackLayout() {
        LinearLayout linearLayout = (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_feedback_layout);
        if (linearLayout == null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        linearLayout.setLayoutParams(layoutParams);
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    private void updateIcon(int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconImgView.getLayoutParams();
        this.mIconImgView.setImageDrawable(this.mContext.getDrawable(R.drawable.face_default));
        layoutParams.width = (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_icon_width);
        layoutParams.height = (int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_icon_height);
        this.mIconImgView.setLayoutParams(layoutParams);
        this.mIconImgView.setColorFilter((ColorFilter) null);
        if (!this.mPromptConfig.isSingleSensorMode() && i == 0) {
            this.mIconImgView.setVisibility(8);
            return;
        }
        this.mIconImgView.setVisibility(0);
        if (i == 1) {
            this.mIconImgView.setAnimation("face_error_nomatch.json");
        }
        this.mIconImgView.playAnimation();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void cleanUpPrompt() {
        pausePunchHoleAnimation();
        TextView textView = this.mFeedbackTextView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        this.mReTryButton.setVisibility(8);
        this.mConfirmButton.setVisibility(8);
        this.mIconImgView.setVisibility(8);
        updateIcon(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final int getBottomMarginForPortrait() {
        return (Utils.isTablet() || (this.mIsSupportDualDisplay && this.mDisplayType != 5)) ? (int) this.mContext.getResources().getDimension(R.dimen.f35x7dfaaec6) : super.getBottomMarginForPortrait();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getDefaultDescriptionMessage() {
        return this.mContext.getString(R.string.face_verification_description_default_text);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getToastMessageInDex() {
        return this.mContext.getString(R.string.face_dex_toast);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationError(int i, int i2) {
        if (i2 == 100003) {
            String string = this.mContext.getString(R.string.camera_access_turn_on_message);
            BiometricPromptGuiHelper.setText(this.mFeedbackTextView, "");
            BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, string);
            this.mReTryButton.setText(this.mContext.getString(R.string.camera_access_turn_on_button));
        } else {
            String errorString = FaceManager.getErrorString(this.mContext, i, i2);
            BiometricPromptGuiHelper.setText(this.mFeedbackTextView, "");
            BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, errorString);
        }
        updateIcon(1);
        pausePunchHoleAnimation();
        if (i == 7 || i == 9) {
            return;
        }
        BiometricPromptGuiHelper.setVisibilityDelayAnimation(this.mReTryButton);
        this.mReTryButton.setEnabled(true);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationFailed() {
        if (this.mBaseView == null) {
            Log.e(this.TAG, "mBaseView is null");
            return;
        }
        updateIcon(1);
        String string = this.mContext.getString(R.string.face_biometric_prompt_identify_failure_title);
        BiometricPromptGuiHelper.setText(this.mFeedbackTextView, "");
        BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, string);
        pausePunchHoleAnimation();
        BiometricPromptGuiHelper.setVisibilityDelayAnimation(this.mReTryButton);
        this.mReTryButton.setEnabled(true);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationHelp(int i, String str) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mTimePreviousHelpMessage < 800) {
            return;
        }
        updateIcon(0);
        this.mTimePreviousHelpMessage = currentTimeMillis;
        BiometricPromptGuiHelper.setText(this.mFeedbackTextView, str);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationSucceeded() {
        BiometricPromptGuiHelper.setText(this.mFeedbackTextView, "");
        if (this.mPromptConfig.isConfirmationRequested()) {
            pausePunchHoleAnimation();
            this.mReTryButton.setVisibility(8);
            this.mReTryButton.setEnabled(false);
            BiometricPromptGuiHelper.setVisibilityDelayAnimation(this.mConfirmButton);
            this.mConfirmButton.setEnabled(true);
            this.mDescriptionTxtView.setText(R.string.biometric_prompt_confirmed_description_text);
            updateIcon(0);
            if (this.mPromptConfig.isDeviceCredentialAllowed()) {
                CharSequence negativeButtonText = this.mPromptConfig.getNegativeButtonText();
                if (TextUtils.isEmpty(negativeButtonText)) {
                    negativeButtonText = this.mContext.getString(R.string.biometric_prompt_default_cancel);
                    this.mIsSupportNegativeButton = false;
                }
                this.mBottomButton.setText(negativeButtonText);
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void initPrompt() {
        SensorPrivacyManager sensorPrivacyManager = (SensorPrivacyManager) this.mContext.getSystemService("sensor_privacy");
        this.mSensorPrivacyManager = sensorPrivacyManager;
        final int i = 2;
        final int i2 = 0;
        final int i3 = 1;
        this.mIsCameraBlocked = sensorPrivacyManager != null && sensorPrivacyManager.isSensorPrivacyEnabled(2);
        this.mDisplayType = this.mContext.getResources().getConfiguration().semDisplayDeviceType;
        this.mLastOrientation = isScreenLandscape() ? 2 : 1;
        CharSequence description = this.mPromptConfig.getDescription();
        this.mDescriptionText = description;
        if (TextUtils.isEmpty(description)) {
            this.mDescriptionText = getDefaultDescriptionMessage();
        }
        this.mDescriptionTxtView.setText(this.mDescriptionText);
        if (!this.mPromptConfig.isDeviceCredentialAllowed()) {
            this.mIsSupportNegativeButton = true;
        }
        setTopMarginOfFeedbackLayout();
        PunchHoleVIHelper punchHoleVIHelper = new PunchHoleVIHelper(this.mContext);
        this.mPunchHoleVIHelper = punchHoleVIHelper;
        if (punchHoleVIHelper.initialize()) {
            LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mBaseView.findViewById(R.id.id_prompt_punch_hole_lottie_view);
            this.mPunchHoleIcon = lottieAnimationView;
            lottieAnimationView.setVisibility(0);
            this.mPunchHoleIcon.loop();
            this.mPunchHoleIcon.setRenderMode(RenderMode.HARDWARE);
            this.mViViewLocation = this.mPunchHoleVIHelper.getPunchHoleVIRect();
            this.mPunchHoleIcon.setTranslationX(r0.left);
            this.mPunchHoleIcon.setTranslationY(this.mViViewLocation.top);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mPunchHoleIcon.getLayoutParams();
            layoutParams.width = this.mViViewLocation.width();
            layoutParams.height = this.mViViewLocation.height();
            this.mPunchHoleIcon.setLayoutParams(layoutParams);
            setPunchHoleRotation(Utils.getRotation(this.mContext));
        }
        TextView textView = (TextView) this.mBaseView.findViewById(R.id.id_prompt_feedback_text);
        this.mFeedbackTextView = textView;
        textView.setVisibility(0);
        updateIcon(0);
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
        View view = this.mBaseView;
        if (view != null) {
            view.setOnKeyListener(new View.OnKeyListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper.1
                boolean downPressed = false;

                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view2, int i4, KeyEvent keyEvent) {
                    if (i4 != 4) {
                        return false;
                    }
                    if (keyEvent.getAction() == 0 && !this.downPressed) {
                        this.downPressed = true;
                    } else if (keyEvent.getAction() == 0) {
                        this.downPressed = false;
                    } else if (keyEvent.getAction() == 1 && this.downPressed) {
                        Log.d(FacePromptGuiHelper.this.TAG, "Handle Back Key");
                        this.downPressed = false;
                        FacePromptGuiHelper.this.mPromptConfig.getCallback().onUserCancel(2);
                    }
                    return true;
                }
            });
            final RelativeLayout relativeLayout = (RelativeLayout) this.mBaseView.findViewById(R.id.id_prompt_main_layout);
            relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    WindowInsets rootWindowInsets = FacePromptGuiHelper.this.mBaseView.getRootWindowInsets();
                    if (rootWindowInsets != null) {
                        if (rootWindowInsets.getDisplayCutout() != null) {
                            Log.d(FacePromptGuiHelper.this.TAG, "Notch support, setPunchHoleUI");
                            FacePromptGuiHelper.this.playPunchHoleAnimation();
                        } else {
                            Log.d(FacePromptGuiHelper.this.TAG, "Notch not support");
                            FacePromptGuiHelper.this.pausePunchHoleAnimation();
                        }
                        relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
        this.mReTryButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ FacePromptGuiHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i2) {
                    case 0:
                        FacePromptGuiHelper.m242$r8$lambda$XVWCAZyaFvMQMB1NvyAo6PYhuo(this.f$0);
                        break;
                    case 1:
                        this.f$0.mPromptConfig.getCallback().onConfirmPressed();
                        break;
                    default:
                        FacePromptGuiHelper.m243$r8$lambda$g1DGZXYJ2AeaZlPrRO_J7mk5Kg(this.f$0);
                        break;
                }
            }
        });
        this.mConfirmButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ FacePromptGuiHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i3) {
                    case 0:
                        FacePromptGuiHelper.m242$r8$lambda$XVWCAZyaFvMQMB1NvyAo6PYhuo(this.f$0);
                        break;
                    case 1:
                        this.f$0.mPromptConfig.getCallback().onConfirmPressed();
                        break;
                    default:
                        FacePromptGuiHelper.m243$r8$lambda$g1DGZXYJ2AeaZlPrRO_J7mk5Kg(this.f$0);
                        break;
                }
            }
        });
        Button button = this.mBottomButton;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener(this) { // from class: com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper$$ExternalSyntheticLambda0
                public final /* synthetic */ FacePromptGuiHelper f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    switch (i) {
                        case 0:
                            FacePromptGuiHelper.m242$r8$lambda$XVWCAZyaFvMQMB1NvyAo6PYhuo(this.f$0);
                            break;
                        case 1:
                            this.f$0.mPromptConfig.getCallback().onConfirmPressed();
                            break;
                        default:
                            FacePromptGuiHelper.m243$r8$lambda$g1DGZXYJ2AeaZlPrRO_J7mk5Kg(this.f$0);
                            break;
                    }
                }
            });
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mDisplayMetrics = displayMetrics;
        setTopMarginOfFeedbackLayout();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        int i2 = this.mLastOrientation;
        int i3 = configuration.orientation;
        if (i2 == i3 && this.mDisplayType == configuration.semDisplayDeviceType && i3 != 2) {
            return;
        }
        if (i3 == 2) {
            Log.d(this.TAG, "onConfigurationChanged:  LAND MODE");
            layoutParams.bottomMargin = getBottomMarginForLandscape();
            int fraction = (int) (this.mContext.getResources().getFraction(R.fraction.face_verification_width_percent_land, 1, 1) * this.mDisplayMetrics.widthPixels);
            layoutParams.width = fraction;
            i = (this.mScreenLandWidth - fraction) / 2;
        } else {
            this.mScreenPortraitWidth = Utils.getDisplayWidth(this.mContext);
            layoutParams.width = (int) (this.mContext.getResources().getFraction(R.fraction.face_verification_width_percent_portrait, 1, 1) * this.mDisplayMetrics.widthPixels);
            layoutParams.bottomMargin = getBottomMarginForPortrait();
            i = (this.mScreenPortraitWidth - layoutParams.width) / 2;
        }
        layoutParams.gravity = 1;
        this.mDialogLayout.setLayoutParams(layoutParams);
        setUpLeftMarginViewWidth(i);
        this.mLastOrientation = configuration.orientation;
        this.mDisplayType = configuration.semDisplayDeviceType;
        customizeSwitch(layoutParams.width, 0);
        setPunchHoleRotation(Utils.getRotation(this.mContext));
        setUpKnoxBrandLogoImage();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onRotationInfoChanged(int i) {
        setPunchHoleRotation(i);
        Configuration configuration = Resources.getSystem().getConfiguration();
        if (this.mLastOrientation == configuration.orientation) {
            onConfigurationChanged(configuration);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void show() {
        int i;
        this.mDisplayMetrics = Utils.getDisplayMetrics(this.mContext);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        if (isScreenLandscape()) {
            layoutParams.width = (int) (this.mContext.getResources().getFraction(R.fraction.face_verification_width_percent_land, 1, 1) * this.mDisplayMetrics.widthPixels);
            layoutParams.bottomMargin = getBottomMarginForLandscape();
            i = (this.mScreenLandWidth - layoutParams.width) / 2;
        } else {
            layoutParams.width = (int) (this.mContext.getResources().getFraction(R.fraction.face_verification_width_percent_portrait, 1, 1) * this.mDisplayMetrics.widthPixels);
            layoutParams.bottomMargin = getBottomMarginForPortrait();
            i = (this.mScreenPortraitWidth - layoutParams.width) / 2;
        }
        layoutParams.gravity = 1;
        setUpLeftMarginViewWidth(i);
        this.mDialogLayout.setLayoutParams(layoutParams);
        customizeSwitch(layoutParams.width, 0);
        if (Utils.isShowDexPrompt(this.mContext, this.mPromptConfig.getDisplayId())) {
            Toast.makeText(this.mContext, getToastMessageInDex(), 0).show();
        }
        this.mBaseView.setVisibility(0);
        this.mTimePreviousHelpMessage = 0L;
    }
}
