package com.samsung.android.biometrics.app.setting.prompt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.ResourceManager;
import com.samsung.android.biometrics.app.setting.SettingHelper;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsInfo;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;

/* loaded from: classes.dex */
public abstract class BiometricPromptGuiHelper {
    protected View mBaseView;
    private CharSequence mBottomBtnText;
    protected Button mBottomButton;
    protected Button mConfirmButton;
    protected Context mContext;
    protected LinearLayout mDescLayout;
    protected CharSequence mDescriptionText;
    protected TextView mDescriptionTxtView;
    protected LinearLayout mDialogLayout;
    protected DisplayMetrics mDisplayMetrics;
    protected int mDisplayType;
    protected LottieAnimationView mIconImgView;

    @Configuration.Orientation
    protected int mLastOrientation;
    protected OnModalityChangeListener mOnModalityChangeListener;
    protected FrameLayout mPositiveButtonLayout;
    protected PromptConfig mPromptConfig;
    protected Button mReTryButton;
    protected int mScreenPortraitWidth;
    protected TextView mSubTitleTxtView;

    @SuppressLint({"UseSwitchCompatOrMaterialCode"})
    protected Switch mSwitch;
    protected TextView mTitleTxtView;
    protected UdfpsInfo mUdfpsInfo;
    protected static final boolean DEBUG = Utils.DEBUG;
    private static final Interpolator DIALOG_EXTEND = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    private static final Interpolator DESCRIPTION_CHANGE = new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f);
    protected String TAG = "BSS_BiometricPromptGuiHelper";
    protected boolean mIsSupportDualDisplay = Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY;
    protected int mNavigationBarHeight = getNavigationBarHeight();
    protected int mScreenLandWidth = getScreenLandscapeWidthWithoutNavigationBar();

    /* renamed from: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper$4 */
    final class ViewOnKeyListenerC02954 implements View.OnKeyListener {
        boolean downPressed = false;

        ViewOnKeyListenerC02954() {
        }

        @Override // android.view.View.OnKeyListener
        public final boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i != 4) {
                return false;
            }
            if (keyEvent.getAction() == 0 && !this.downPressed) {
                this.downPressed = true;
            } else if (keyEvent.getAction() == 0) {
                this.downPressed = false;
            } else if (keyEvent.getAction() == 1 && this.downPressed) {
                Log.d(BiometricPromptGuiHelper.this.TAG, "Handle Back Key");
                this.downPressed = false;
                BiometricPromptGuiHelper.this.mPromptConfig.getCallback().onUserCancel(2);
            }
            return true;
        }
    }

    public interface OnModalityChangeListener {
    }

    public BiometricPromptGuiHelper(Context context, View view, PromptConfig promptConfig, UdfpsInfo udfpsInfo) {
        this.mContext = context;
        this.mBaseView = view;
        this.mPromptConfig = promptConfig;
        this.mUdfpsInfo = udfpsInfo;
        this.mDisplayMetrics = Utils.getDisplayMetrics(context);
        this.mScreenPortraitWidth = Utils.getDisplayWidth(this.mContext);
        ImageView imageView = (ImageView) this.mBaseView.findViewById(R.id.id_prompt_background);
        if (!this.mPromptConfig.isManagedProfile() || this.mPromptConfig.isKnoxProfile()) {
            imageView.setBackgroundColor(android.R.color.transparent);
        } else {
            Drawable drawable = new ResourceManager(this.mContext, "com.android.settings").getDrawable("work_challenge_background");
            if (drawable != null) {
                drawable.setColorFilter(this.mPromptConfig.getOrganizationColor(), PorterDuff.Mode.DARKEN);
            }
            imageView.setBackground(drawable);
        }
        ImageView imageView2 = (ImageView) this.mBaseView.findViewById(R.id.id_prompt_knox_image);
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        TextView textView = (TextView) this.mBaseView.findViewById(R.id.title);
        this.mTitleTxtView = textView;
        setText(textView, this.mPromptConfig.getTitle());
        Utils.setMaxTextScaleSize(this.mTitleTxtView, R.dimen.biometric_prompt_verification_title_text_size);
        this.mSubTitleTxtView = (TextView) this.mBaseView.findViewById(R.id.subtitle);
        if (!TextUtils.isEmpty(this.mPromptConfig.getSubtitle())) {
            this.mSubTitleTxtView.setVisibility(0);
            this.mSubTitleTxtView.setText(this.mPromptConfig.getSubtitle());
            Utils.setMaxTextScaleSize(this.mSubTitleTxtView, R.dimen.biometric_prompt_verification_subtitle_text_size);
        }
        CharSequence description = this.mPromptConfig.getDescription();
        this.mDescriptionText = description;
        if (TextUtils.isEmpty(description)) {
            this.mDescriptionText = getDefaultDescriptionMessage();
        }
        TextView textView2 = (TextView) this.mBaseView.findViewById(R.id.description);
        this.mDescriptionTxtView = textView2;
        textView2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper.2
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                BiometricPromptGuiHelper biometricPromptGuiHelper = BiometricPromptGuiHelper.this;
                TextView textView3 = biometricPromptGuiHelper.mDescriptionTxtView;
                if (textView3 != null) {
                    textView3.setText(biometricPromptGuiHelper.mDescriptionText);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
            }
        });
        this.mDescLayout = (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_description_layout);
        this.mDescriptionTxtView.addTextChangedListener(new TextWatcher() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper.3
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TextView textView3 = BiometricPromptGuiHelper.this.mDescriptionTxtView;
                if (textView3 != null) {
                    textView3.announceForAccessibility(textView3.getText());
                    BiometricPromptGuiHelper.setVisibilityDelayAnimation(BiometricPromptGuiHelper.this.mDescriptionTxtView);
                    BiometricPromptGuiHelper.this.updateUiAnimation();
                }
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        if (this.mPromptConfig.isDeviceCredentialAllowed()) {
            this.mBottomButton = (Button) this.mBaseView.findViewById(R.id.button_use_credential);
            int credentialType = this.mPromptConfig.getCredentialType();
            if (credentialType == 1) {
                this.mBottomBtnText = this.mContext.getString(R.string.biometric_dialog_use_pin);
            } else if (credentialType == 2) {
                this.mBottomBtnText = this.mContext.getString(R.string.biometric_dialog_use_pattern);
            } else if (credentialType == 3) {
                this.mBottomBtnText = this.mContext.getString(R.string.biometric_dialog_use_password);
            } else if (credentialType == 6) {
                this.mBottomBtnText = this.mContext.getString(R.string.ucm_biometric_dialog_use_smart_card);
            }
        } else {
            this.mBottomButton = (Button) this.mBaseView.findViewById(R.id.button_negative);
            this.mBottomBtnText = TextUtils.isEmpty(this.mPromptConfig.getNegativeButtonText()) ? this.mContext.getString(R.string.biometric_prompt_default_cancel) : this.mPromptConfig.getNegativeButtonText();
        }
        this.mBottomButton.semSetButtonShapeEnabled(true);
        this.mBottomButton.setText(this.mBottomBtnText);
        this.mBottomButton.setVisibility(0);
        Utils.setMaxTextScaleSize(this.mBottomButton, R.dimen.biometric_prompt_verification_negative_text_size);
        Button button = (Button) this.mBaseView.findViewById(R.id.button_try_again);
        this.mReTryButton = button;
        button.setVisibility(8);
        this.mReTryButton.setText(this.mContext.getString(R.string.biometric_prompt_postive_retry));
        this.mReTryButton.setEnabled(false);
        Utils.setMaxTextScaleSize(this.mReTryButton, getResourceIdOfPositiveButtonTextSize());
        Button button2 = (Button) this.mBaseView.findViewById(R.id.button_confirm);
        this.mConfirmButton = button2;
        button2.setVisibility(8);
        this.mConfirmButton.setText(this.mContext.getString(R.string.biometric_prompt_positive_confirm));
        this.mConfirmButton.setEnabled(false);
        Utils.setMaxTextScaleSize(this.mConfirmButton, getResourceIdOfPositiveButtonTextSize());
        this.mIconImgView = (LottieAnimationView) this.mBaseView.findViewById(R.id.id_prompt_description_image);
        this.mPositiveButtonLayout = (FrameLayout) this.mBaseView.findViewById(R.id.id_prompt_positive_button_container);
        LinearLayout linearLayout = (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_dialog_full);
        this.mDialogLayout = linearLayout;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.width = (int) (this.mContext.getResources().getFraction(isScreenLandscape() ? R.fraction.fingerprint_verification_width_percent_land : R.fraction.fingerprint_verification_width_percent_portrait, 1, 1) * this.mDisplayMetrics.widthPixels);
        setUpLeftMarginViewWidth((Utils.getDisplayWidth(this.mContext) - layoutParams.width) / 2);
        if (promptConfig.isKnoxProfile()) {
            View view2 = this.mBaseView;
            if (promptConfig.isKnoxManagedProfile()) {
                ImageView imageView3 = (ImageView) view2.findViewById(R.id.sem_biometric_prompt_knox_icon);
                Drawable drawable2 = new ResourceManager(context, "com.android.settings").getDrawable("knox_basic");
                if (drawable2 != null) {
                    if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
                        drawable2.setTint(-1);
                    } else {
                        drawable2.setTint(-16777216);
                    }
                    imageView3.setImageDrawable(drawable2);
                    imageView3.setVisibility(0);
                }
            }
            TextView textView3 = (TextView) view2.findViewById(R.id.title);
            if (promptConfig.isKnoxManagedProfile()) {
                textView3.setText(context.getString(R.string.biometric_prompt_default_title_work_profile));
            } else if (promptConfig.isSecureFolder()) {
                textView3.setText(promptConfig.getExtraInfo().getString("SECURE_FOLDER_NAME"));
            }
            if ((promptConfig.isKnoxManagedProfile() || promptConfig.isSecureFolder()) && (!promptConfig.getPromptInfo().isUseDefaultTitle() || !context.getString(R.string.biometric_prompt_default_title).contentEquals(promptConfig.getTitle()))) {
                TextView textView4 = (TextView) view2.findViewById(R.id.id_knox_prompt_title);
                textView4.setVisibility(0);
                textView4.setText(promptConfig.getTitle());
                Utils.setMaxTextScaleSize(textView4, R.dimen.biometric_prompt_verification_subtitle_text_size);
            }
            if (promptConfig.isKnoxManagedProfile() && promptConfig.getExtraInfo().getBoolean("MANAGED_PROFILE_KNOX_TWO_FACTOR", false)) {
                TextView textView5 = (TextView) view2.findViewById(R.id.id_knox_prompt_two_step);
                textView5.setVisibility(0);
                Utils.setMaxTextScaleSize(textView5, R.dimen.biometric_prompt_verification_subtitle_text_size);
            }
        }
    }

    protected static void setText(TextView textView, CharSequence charSequence) {
        if (textView == null || charSequence == null) {
            return;
        }
        textView.setText(charSequence);
    }

    protected static void setVisibilityDelayAnimation(View view) {
        view.setVisibility(0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(100L);
        alphaAnimation.setDuration(200L);
        alphaAnimation.setInterpolator(DESCRIPTION_CHANGE);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper.1
            final /* synthetic */ Runnable val$runnableAfterAnni = null;

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                Runnable runnable = this.val$runnableAfterAnni;
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        view.startAnimation(alphaAnimation);
    }

    public abstract void cleanUpPrompt();

    protected void customizeSwitch(int i, int i2) {
        int i3;
        int i4 = i;
        if (this.mSwitch == null) {
            this.mSwitch = (Switch) this.mBaseView.findViewById(R.id.id_prompt_switch);
            if (this.mPromptConfig.isMultiSensorMode()) {
                this.mSwitch.setVisibility(0);
                this.mSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BiometricPromptGuiHelper biometricPromptGuiHelper = BiometricPromptGuiHelper.this;
                        int i5 = biometricPromptGuiHelper.mSwitch.isChecked() ? 8 : 2;
                        BiometricPromptGuiHelper.OnModalityChangeListener onModalityChangeListener = biometricPromptGuiHelper.mOnModalityChangeListener;
                        if (onModalityChangeListener != null) {
                            ((BiometricPromptWindow) onModalityChangeListener).onModalitySwitched(i5);
                        }
                        Log.i(biometricPromptGuiHelper.TAG, "customizeSwitch: OnClickListener:  modality = " + i5 + ", OnModalityChangeListener = " + biometricPromptGuiHelper.mOnModalityChangeListener);
                    }
                });
            }
        }
        if (this.mPromptConfig.isSingleSensorMode() || i4 <= 0) {
            this.mSwitch.setVisibility(8);
            return;
        }
        int dimension = (int) (this.mContext.getResources().getDimension(R.dimen.biometric_prompt_verification_horizontal_margin) * 2.0f);
        if (i4 > dimension) {
            i4 -= dimension;
        }
        this.mSwitch.setSwitchMinWidth(i4);
        int switchMinWidth = this.mSwitch.getSwitchMinWidth();
        int dimension2 = (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_switch_width);
        Drawable drawable = this.mContext.getDrawable(R.drawable.custom_track);
        Bitmap createBitmap = Bitmap.createBitmap(switchMinWidth, dimension2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, switchMinWidth, dimension2);
        drawable.draw(canvas);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(this.mContext.getColor(R.color.biometric_prompt_switch_track_text));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(this.mContext.getResources().getDimension(R.dimen.biometric_prompt_switch_text));
        float f = dimension2;
        canvas.drawText(i2 == 2 ? "" : this.mContext.getString(R.string.biometric_prompt_button_fingerprint), switchMinWidth / 4.0f, (f - textPaint.ascent()) / 2.0f, textPaint);
        int i5 = switchMinWidth * 3;
        canvas.drawText(i2 != 8 ? this.mContext.getString(R.string.biometric_prompt_button_face) : "", i5 / 4.0f, (f - textPaint.ascent()) / 2.0f, textPaint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), createBitmap);
        bitmapDrawable.setBounds(0, 0, switchMinWidth, dimension2);
        RippleDrawable rippleDrawable = (RippleDrawable) this.mContext.getDrawable(R.drawable.custom_switch_ripple);
        rippleDrawable.setDrawableByLayerId(R.id.ripple_background, bitmapDrawable);
        this.mSwitch.setTrackDrawable(rippleDrawable);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mBaseView.findViewById(R.id.id_prompt_switch_progress_left);
        LottieAnimationView lottieAnimationView2 = (LottieAnimationView) this.mBaseView.findViewById(R.id.id_prompt_switch_progress_right);
        if (lottieAnimationView == null || lottieAnimationView2 == null) {
            i3 = 2;
        } else {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lottieAnimationView.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) lottieAnimationView2.getLayoutParams();
            i3 = 2;
            layoutParams.topMargin = (dimension2 - lottieAnimationView.getHeight()) / 2;
            layoutParams.leftMargin = (switchMinWidth / 4) - (lottieAnimationView.getWidth() / 2);
            layoutParams2.topMargin = (dimension2 - lottieAnimationView.getHeight()) / 2;
            layoutParams2.leftMargin = (i5 / 4) - (lottieAnimationView.getWidth() / 2);
            lottieAnimationView.setVisibility(4);
            lottieAnimationView2.setVisibility(4);
            if (i2 != 2) {
                lottieAnimationView = i2 == 8 ? lottieAnimationView2 : null;
            }
            if (lottieAnimationView != null) {
                lottieAnimationView.setVisibility(0);
                lottieAnimationView.setRepeatCount(2);
                lottieAnimationView.playAnimation();
            }
        }
        int i6 = switchMinWidth / i3;
        Bitmap createBitmap2 = Bitmap.createBitmap(i6, dimension2, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        Drawable drawable2 = this.mContext.getDrawable(R.drawable.custom_thumb);
        drawable2.setBounds(0, 0, i6, dimension2);
        drawable2.draw(canvas2);
        this.mSwitch.setThumbDrawable(new BitmapDrawable(this.mContext.getResources(), createBitmap2));
    }

    protected final int getBottomMarginForLandscape() {
        return (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_verification_dialog_bottom_margin_land);
    }

    protected int getBottomMarginForPortrait() {
        if (this.mUdfpsInfo == null || !this.mPromptConfig.canUseFingerprint()) {
            return ((int) this.mContext.getResources().getDimension(R.dimen.knox_logo_view_height)) + ((int) this.mContext.getResources().getDimension(R.dimen.knox_logo_view_prompt_top_margin));
        }
        return (((this.mUdfpsInfo.getImageSize() / 2) + ((this.mUdfpsInfo.getAreaHeight() / 2) + this.mUdfpsInfo.getMarginBottom())) + ((int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_between_icon_prompt_size))) - this.mNavigationBarHeight;
    }

    protected abstract String getDefaultDescriptionMessage();

    protected int getNavigationBarHeight() {
        if (SettingHelper.isNavigationBarHidden(this.mContext)) {
            return 0;
        }
        try {
            Resources resources = this.mContext.getResources();
            int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (identifier > 0) {
                return resources.getDimensionPixelSize(identifier);
            }
            Log.e(this.TAG, "BiometricPromptGuiHelper: No resource");
            return 0;
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("BiometricPromptGuiHelper: "), this.TAG);
            return 0;
        }
    }

    protected int getResourceIdOfPositiveButtonTextSize() {
        return R.dimen.face_verification_positive_text_size;
    }

    protected int getScreenLandscapeWidthWithoutNavigationBar() {
        return Utils.getDisplayHeight(this.mContext) - this.mNavigationBarHeight;
    }

    protected int getStatusBarHeight() {
        return Utils.getStatusBarHeight(this.mContext);
    }

    public String getToastMessageInDex() {
        return "";
    }

    public abstract void handleAuthenticationError(int i, int i2);

    public abstract void handleAuthenticationFailed();

    public abstract void handleAuthenticationHelp(int i, String str);

    public abstract void handleAuthenticationSucceeded();

    public void hideSwitch() {
        Switch r0 = this.mSwitch;
        if (r0 == null) {
            return;
        }
        r0.setVisibility(8);
        this.mBaseView.findViewById(R.id.id_prompt_switch_progress_left).setVisibility(8);
    }

    public abstract void initPrompt();

    protected boolean isScreenLandscape() {
        return Utils.isScreenLandscape(this.mContext);
    }

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onRotationInfoChanged(int i);

    protected final void setUpKnoxBrandLogoImage() {
        ImageView imageView = (ImageView) this.mBaseView.findViewById(R.id.id_prompt_knox_image);
        if (imageView == null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        int dimension = (int) this.mContext.getResources().getDimension(R.dimen.knox_logo_end_margin);
        if (Utils.getRotation(this.mContext) == 3) {
            dimension += getStatusBarHeight();
        }
        layoutParams.setMarginEnd(dimension);
        imageView.setLayoutParams(layoutParams);
    }

    protected void setUpLeftMarginViewWidth(int i) {
        View findViewById = this.mBaseView.findViewById(R.id.id_prompt_dialog_left_space);
        if (findViewById == null) {
            return;
        }
        ((LinearLayout.LayoutParams) findViewById.getLayoutParams()).width = i;
    }

    public abstract void show();

    public void showProgressSwitching(int i) {
        Switch r0 = this.mSwitch;
        if (r0 == null) {
            return;
        }
        r0.setChecked(i != 8);
        this.mSwitch.setEnabled(false);
        customizeSwitch(this.mDialogLayout.getWidth(), i);
    }

    public void updateBiometricSwitch(int i) {
        Switch r0 = this.mSwitch;
        if (r0 == null) {
            return;
        }
        r0.setChecked(i == 8);
        this.mSwitch.setEnabled(true);
    }

    protected final void updateUiAnimation() {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(450L);
        changeBounds.setInterpolator(DIALOG_EXTEND);
        changeBounds.excludeTarget((View) this.mDescLayout, true);
        changeBounds.excludeTarget((View) this.mPositiveButtonLayout, true);
        TransitionManager.beginDelayedTransition((ViewGroup) this.mBaseView, changeBounds);
    }

    public void showBiometricName(String str) {
    }
}
