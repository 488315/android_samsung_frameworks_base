package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.hardware.biometrics.PromptContentItemBulletedText;
import android.hardware.biometrics.PromptContentItemPlainText;
import android.hardware.biometrics.PromptContentViewWithMoreOptionsButton;
import android.hardware.biometrics.PromptVerticalListContentView;
import android.os.UserHandle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.ResourceManager;
import com.samsung.android.biometrics.app.setting.SettingHelper;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public abstract class BiometricPromptGuiHelper {
    public final View mBaseView;
    public CharSequence mBottomBtnText;
    public Button mBottomButton;
    public Button mConfirmButton;
    public final Context mContext;
    public LinearLayout mDescLayout;
    public CharSequence mDescriptionText;
    public TextView mDescriptionTxtView;
    public final View mDialogBottomAreaView;
    public final LinearLayout mDialogLayout;
    public DisplayMetrics mDisplayMetrics;
    public int mDisplayType;
    public final FingerprintSensorInfo mFingerprintSensorInfo;
    public final LottieAnimationView mIconImgView;
    public int mLastOrientation;
    public final LinearLayout mLogoLayout;
    public final FrameLayout mNegativeButtonLayout;
    public OnModalityChangeListener mOnModalityChangeListener;
    public final FrameLayout mPositiveButtonLayout;
    public final PromptConfig mPromptConfig;
    public Button mReTryButton;
    public int mScreenPortraitWidth;
    public ScrollView mScrollView;
    public final View mScrollViewBottomDividerView;
    public final View mScrollViewTopDividerView;
    public TextView mSubTitleTxtView;
    public Switch mSwitch;
    public AnonymousClass4 mTextWatcher;
    public final LinearLayout mTitleLayout;
    public TextView mTitleTxtView;
    public static final boolean DEBUG = Utils.DEBUG;
    public static final Interpolator DIALOG_EXTEND = new PathInterpolator(0.22f, 0.25f, RecyclerView.DECELERATION_RATE, 1.0f);
    public static final Interpolator DESCRIPTION_CHANGE = new PathInterpolator(RecyclerView.DECELERATION_RATE, RecyclerView.DECELERATION_RATE, 1.0f, 1.0f);
    public String TAG = "BSS_BiometricPromptGuiHelper";
    public final boolean mIsSupportDualDisplay = Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY;
    public final int mNavigationBarHeight = getNavigationBarHeight();
    public int mScreenLandWidth = getScreenLandscapeWidthWithoutNavigationBar();

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper$3, reason: invalid class name */
    public final class AnonymousClass3 implements ViewTreeObserver.OnGlobalLayoutListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BiometricPromptGuiHelper this$0;

        public /* synthetic */ AnonymousClass3(BiometricPromptGuiHelper biometricPromptGuiHelper, int i) {
            this.$r8$classId = i;
            this.this$0 = biometricPromptGuiHelper;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.setUpScrollView();
                    this.this$0.mDescriptionTxtView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    break;
                case 1:
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.this$0.mDialogBottomAreaView.getLayoutParams();
                    if (this.this$0.mPromptConfig.getPrimaryBiometricAuthenticator() == 2) {
                        layoutParams.height = this.this$0.isScreenLandscape() ? 0 : this.this$0.getBottomMarginForPortrait();
                    } else {
                        layoutParams.height = Utils.dipToPixel(this.this$0.mContext, 16.0d);
                    }
                    layoutParams.weight = this.this$0.isScreenLandscape() ? 1.0f : RecyclerView.DECELERATION_RATE;
                    this.this$0.mDialogBottomAreaView.setLayoutParams(layoutParams);
                    this.this$0.mDialogLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    break;
                default:
                    View findViewById = this.this$0.mBaseView.findViewById(R.id.id_scroll_top_divider);
                    View findViewById2 = this.this$0.mBaseView.findViewById(R.id.id_scroll_bottom_divider);
                    if (this.this$0.mScrollView.getHeight() < this.this$0.mScrollView.getPaddingBottom() + this.this$0.mScrollView.getPaddingTop() + this.this$0.mScrollView.getChildAt(0).getHeight()) {
                        findViewById.setVisibility(0);
                        findViewById2.setVisibility(0);
                    } else {
                        findViewById.setVisibility(8);
                        findViewById2.setVisibility(8);
                    }
                    this.this$0.mScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public interface OnModalityChangeListener {
    }

    public BiometricPromptGuiHelper(Context context, View view, PromptConfig promptConfig, FingerprintSensorInfo fingerprintSensorInfo) {
        this.mContext = context;
        this.mBaseView = view;
        this.mPromptConfig = promptConfig;
        this.mFingerprintSensorInfo = fingerprintSensorInfo;
        this.mDisplayMetrics = Utils.getDisplayMetrics(context);
        this.mScreenPortraitWidth = Utils.getDisplayWidthInPortraitMode(context);
        this.mIconImgView = (LottieAnimationView) view.findViewById(R.id.id_prompt_description_image);
        this.mPositiveButtonLayout = (FrameLayout) view.findViewById(R.id.id_prompt_positive_button_container);
        this.mDialogLayout = (LinearLayout) view.findViewById(R.id.id_prompt_dialog_full);
        this.mLogoLayout = (LinearLayout) view.findViewById(R.id.id_prompt_logo_layout);
        this.mTitleLayout = (LinearLayout) view.findViewById(R.id.id_prompt_title_layout);
        this.mNegativeButtonLayout = (FrameLayout) view.findViewById(R.id.id_prompt_negative_button_container);
        this.mDialogBottomAreaView = view.findViewById(R.id.id_prompt_dialog_bottom_area);
        this.mScrollViewTopDividerView = view.findViewById(R.id.id_scroll_top_divider);
        this.mScrollViewBottomDividerView = view.findViewById(R.id.id_scroll_bottom_divider);
        if (promptConfig.isKnoxProfile()) {
            boolean z = promptConfig.mIsKnoxManagedProfile;
            if (z) {
                ImageView imageView = (ImageView) view.findViewById(R.id.sem_biometric_prompt_knox_icon);
                Drawable drawable = new ResourceManager(context, "com.android.settings").getDrawable("knox_basic");
                if (drawable != null) {
                    if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
                        drawable.setTint(-1);
                    } else {
                        drawable.setTint(-16777216);
                    }
                    imageView.setImageDrawable(drawable);
                    imageView.setVisibility(0);
                }
            }
            TextView textView = (TextView) view.findViewById(R.id.title);
            boolean z2 = promptConfig.mIsSecureFolder;
            if (z) {
                textView.setText(context.getString(R.string.biometric_prompt_default_title_work_profile));
            } else if (z2) {
                textView.setText(promptConfig.mExtraInfo.getString("SECURE_FOLDER_NAME"));
            }
            if ((z || z2) && (!promptConfig.mPromptInfo.isUseDefaultTitle() || !context.getString(R.string.biometric_prompt_default_title).contentEquals(promptConfig.mPromptInfo.getTitle()))) {
                TextView textView2 = (TextView) view.findViewById(R.id.id_knox_prompt_title);
                textView2.setVisibility(0);
                textView2.setText(promptConfig.mPromptInfo.getTitle());
                Utils.setMaxTextScaleSize(textView2, R.dimen.biometric_prompt_verification_subtitle_text_size);
            }
            if (z && promptConfig.mExtraInfo.getBoolean("MANAGED_PROFILE_KNOX_TWO_FACTOR", false)) {
                TextView textView3 = (TextView) view.findViewById(R.id.id_knox_prompt_two_step);
                textView3.setVisibility(0);
                Utils.setMaxTextScaleSize(textView3, R.dimen.biometric_prompt_verification_subtitle_text_size);
            }
        }
    }

    public static void setText(TextView textView, CharSequence charSequence) {
        if (textView == null || charSequence == null) {
            return;
        }
        textView.setText(charSequence);
    }

    public static void setVisibilityDelayAnimation(View view, final Runnable runnable) {
        view.setVisibility(0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f);
        alphaAnimation.setStartOffset(100L);
        alphaAnimation.setDuration(200L);
        alphaAnimation.setInterpolator(DESCRIPTION_CHANGE);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper.2
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
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

    public void cleanUpPrompt() {
        AnonymousClass4 anonymousClass4;
        TextView textView = this.mDescriptionTxtView;
        if (textView == null || (anonymousClass4 = this.mTextWatcher) == null) {
            return;
        }
        textView.removeTextChangedListener(anonymousClass4);
    }

    public void customizeSwitch(int i) {
        Switch r0 = this.mSwitch;
        PromptConfig promptConfig = this.mPromptConfig;
        if (r0 == null) {
            Switch r02 = (Switch) this.mBaseView.findViewById(R.id.id_prompt_switch);
            this.mSwitch = r02;
            if (promptConfig.mNumberOfAvailableBiometrics > 1) {
                r02.setVisibility(0);
                this.mSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BiometricPromptGuiHelper biometricPromptGuiHelper = BiometricPromptGuiHelper.this;
                        int i2 = biometricPromptGuiHelper.mSwitch.isChecked() ? 8 : 2;
                        BiometricPromptGuiHelper.OnModalityChangeListener onModalityChangeListener = biometricPromptGuiHelper.mOnModalityChangeListener;
                        if (onModalityChangeListener != null) {
                            ((BiometricPromptWindow) onModalityChangeListener).onModalitySwitched(i2);
                        }
                        Log.i(biometricPromptGuiHelper.TAG, "customizeSwitch: OnClickListener:  modality = " + i2 + ", OnModalityChangeListener = " + biometricPromptGuiHelper.mOnModalityChangeListener);
                    }
                });
            }
        }
        if ((promptConfig.mNumberOfAvailableBiometrics == 1) || i <= 0) {
            this.mSwitch.setVisibility(8);
            return;
        }
        int dimension = (int) (this.mContext.getResources().getDimension(R.dimen.biometric_prompt_verification_horizontal_margin) * 2.0f);
        if (i > dimension) {
            i -= dimension;
        }
        this.mSwitch.setSwitchMinWidth(i);
        int switchMinWidth = this.mSwitch.getSwitchMinWidth();
        int dimension2 = (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_switch_width);
        Drawable drawable = this.mContext.getDrawable(R.drawable.custom_track);
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap createBitmap = Bitmap.createBitmap(switchMinWidth, dimension2, config);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, switchMinWidth, dimension2);
        drawable.draw(canvas);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(this.mContext.getColor(R.color.biometric_prompt_switch_track_text));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(this.mContext.getResources().getDimension(R.dimen.biometric_prompt_switch_text));
        float f = dimension2;
        canvas.drawText(this.mContext.getString(R.string.biometric_prompt_button_fingerprint), switchMinWidth / 4.0f, (f - textPaint.ascent()) / 2.0f, textPaint);
        canvas.drawText(this.mContext.getString(R.string.biometric_prompt_button_face), (switchMinWidth * 3) / 4.0f, (f - textPaint.ascent()) / 2.0f, textPaint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), createBitmap);
        bitmapDrawable.setBounds(0, 0, switchMinWidth, dimension2);
        RippleDrawable rippleDrawable = (RippleDrawable) this.mContext.getDrawable(R.drawable.custom_switch_ripple);
        rippleDrawable.setDrawableByLayerId(R.id.ripple_background, bitmapDrawable);
        this.mSwitch.setTrackDrawable(rippleDrawable);
        int i2 = switchMinWidth / 2;
        Bitmap createBitmap2 = Bitmap.createBitmap(i2, dimension2, config);
        Canvas canvas2 = new Canvas(createBitmap2);
        Drawable drawable2 = this.mContext.getDrawable(R.drawable.custom_thumb);
        drawable2.setBounds(0, 0, i2, dimension2);
        drawable2.draw(canvas2);
        this.mSwitch.setThumbDrawable(new BitmapDrawable(this.mContext.getResources(), createBitmap2));
    }

    public int getBottomMarginForPortrait() {
        FingerprintSensorInfo fingerprintSensorInfo = this.mFingerprintSensorInfo;
        if (fingerprintSensorInfo == null || !fingerprintSensorInfo.mIsAnyUdfps || !this.mPromptConfig.canUseFingerprint()) {
            return ((int) this.mContext.getResources().getDimension(R.dimen.knox_logo_view_height)) + ((int) this.mContext.getResources().getDimension(R.dimen.knox_logo_view_prompt_top_margin));
        }
        return (((fingerprintSensorInfo.mSensorImageSize / 2) + ((fingerprintSensorInfo.mSensorAreaHeight / 2) + fingerprintSensorInfo.mSensorMarginBottom)) + ((int) this.mContext.getResources().getDimension(R.dimen.fingerprint_verification_between_icon_prompt_size))) - this.mNavigationBarHeight;
    }

    public abstract String getDefaultDescriptionMessage();

    public final int getLeftMargin(int i, boolean z) {
        return ((z ? this.mScreenLandWidth : this.mScreenPortraitWidth) - i) / 2;
    }

    public final String getLockoutErrorMessage(int i) {
        String string;
        String string2 = this.mContext.getString(R.string.biometrics_lockout);
        PromptConfig promptConfig = this.mPromptConfig;
        int primaryBiometricAuthenticator = promptConfig.getPrimaryBiometricAuthenticator();
        boolean z = promptConfig.mNumberOfAvailableBiometrics > 1;
        int i2 = promptConfig.mCredentialType;
        if (z) {
            return promptConfig.isDeviceCredentialAllowed() ? i2 != 1 ? i2 != 2 ? i2 != 3 ? string2 : this.mContext.getString(R.string.biometrics_password_lockout) : this.mContext.getString(R.string.biometrics_pattern_lockout) : this.mContext.getString(R.string.biometrics_pin_lockout) : string2;
        }
        if (promptConfig.isDeviceCredentialAllowed()) {
            return i2 != 1 ? i2 != 2 ? i2 != 3 ? string2 : primaryBiometricAuthenticator == 2 ? this.mContext.getString(R.string.fingerprint_password_lockout) : primaryBiometricAuthenticator == 8 ? this.mContext.getString(R.string.face_password_lockout) : string2 : primaryBiometricAuthenticator == 2 ? this.mContext.getString(R.string.fingerprint_pattern_lockout) : primaryBiometricAuthenticator == 8 ? this.mContext.getString(R.string.face_pattern_lockout) : string2 : primaryBiometricAuthenticator == 2 ? this.mContext.getString(R.string.fingerprint_pin_lockout) : primaryBiometricAuthenticator == 8 ? this.mContext.getString(R.string.face_pin_lockout) : string2;
        }
        if (primaryBiometricAuthenticator == 2) {
            string = i == 7 ? this.mContext.getString(R.string.fingerprint_lockout) : this.mContext.getString(R.string.fingerprint_lockout_permanent);
        } else {
            if (primaryBiometricAuthenticator != 8) {
                return string2;
            }
            string = i == 7 ? this.mContext.getString(R.string.face_lockout) : this.mContext.getString(R.string.face_lockout_permanent);
        }
        return string;
    }

    public int getNavigationBarHeight() {
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
            FocusableWindow$$ExternalSyntheticOutline0.m(e, new StringBuilder("BiometricPromptGuiHelper: "), this.TAG);
            return 0;
        }
    }

    public int getOrientation() {
        return isScreenLandscape() ? 2 : 1;
    }

    public int getResourceIdOfPositiveButtonTextSize() {
        return R.dimen.face_verification_positive_text_size;
    }

    public int getScreenLandscapeWidthWithoutNavigationBar() {
        int displayHeight;
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
        return displayHeight - this.mNavigationBarHeight;
    }

    public int getStatusBarHeight() {
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
        Switch r1 = this.mSwitch;
        if (r1 != null) {
            r1.setVisibility(8);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v55, types: [android.text.TextWatcher, com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper$4] */
    public void initPrompt() {
        Drawable userBadgedIcon;
        this.mLastOrientation = getOrientation();
        this.mDisplayType = this.mContext.getResources().getConfiguration().semDisplayDeviceType;
        ImageView imageView = (ImageView) this.mBaseView.findViewById(R.id.id_prompt_background);
        PromptConfig promptConfig = this.mPromptConfig;
        if (!promptConfig.mIsManagedProfile || promptConfig.isKnoxProfile()) {
            imageView.setBackgroundColor(android.R.color.transparent);
        } else {
            Drawable drawable = new ResourceManager(this.mContext, "com.android.settings").getDrawable("work_challenge_background");
            if (drawable != null) {
                drawable.setColorFilter(promptConfig.mOrganizationColor, PorterDuff.Mode.DARKEN);
            }
            imageView.setBackground(drawable);
        }
        ImageView imageView2 = (ImageView) this.mBaseView.findViewById(R.id.id_prompt_knox_image);
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        TextView textView = (TextView) this.mBaseView.findViewById(R.id.title);
        this.mTitleTxtView = textView;
        setText(textView, promptConfig.mPromptInfo.getTitle());
        Utils.setMaxTextScaleSize(this.mTitleTxtView, R.dimen.biometric_prompt_verification_title_text_size);
        this.mSubTitleTxtView = (TextView) this.mBaseView.findViewById(R.id.subtitle);
        if (!TextUtils.isEmpty(promptConfig.mPromptInfo.getSubtitle())) {
            this.mSubTitleTxtView.setVisibility(0);
            this.mSubTitleTxtView.setText(promptConfig.mPromptInfo.getSubtitle());
            Utils.setMaxTextScaleSize(this.mSubTitleTxtView, R.dimen.biometric_prompt_verification_subtitle_text_size);
        }
        Bitmap logo = promptConfig.mPromptInfo.getLogo();
        int i = promptConfig.mUserId;
        String str = promptConfig.mPackageName;
        if (logo != null) {
            userBadgedIcon = new BitmapDrawable(promptConfig.mPromptInfo.getLogo());
        } else {
            Context context = this.mContext;
            ApplicationInfo applicationInfoForLogo = promptConfig.getApplicationInfoForLogo(context, promptConfig.getComponentNameForLogo());
            if (applicationInfoForLogo == null) {
                Log.d("BSS_PromptConfig", "Cannot find app logo for package " + str);
                userBadgedIcon = null;
            } else {
                PackageManager packageManager = context.getPackageManager();
                userBadgedIcon = packageManager.getUserBadgedIcon(packageManager.getApplicationIcon(applicationInfoForLogo), UserHandle.of(i));
            }
        }
        ImageView imageView3 = (ImageView) this.mBaseView.findViewById(R.id.logo);
        if (imageView3 != null) {
            if (userBadgedIcon == null) {
                imageView3.setVisibility(8);
            } else {
                imageView3.setImageDrawable(userBadgedIcon);
            }
        }
        String logoDescription = promptConfig.mPromptInfo.getLogoDescription();
        if (logoDescription == null) {
            Context context2 = this.mContext;
            ApplicationInfo applicationInfoForLogo2 = promptConfig.getApplicationInfoForLogo(context2, promptConfig.getComponentNameForLogo());
            PackageManager packageManager2 = context2.getPackageManager();
            if (applicationInfoForLogo2 == null || TextUtils.isEmpty(packageManager2.getApplicationLabel(applicationInfoForLogo2))) {
                Log.d("BSS_PromptConfig", "Cannot find app logo for package " + str);
                logoDescription = "";
            } else {
                logoDescription = packageManager2.getUserBadgedLabel(packageManager2.getApplicationLabel(applicationInfoForLogo2), UserHandle.of(i)).toString();
            }
        }
        if (!TextUtils.isEmpty(logoDescription)) {
            setText((TextView) this.mBaseView.findViewById(R.id.logo_description), logoDescription);
        }
        this.mDescLayout = (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_description_layout);
        if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            this.mDescriptionTxtView = (TextView) this.mBaseView.findViewById(R.id.customized_view_description);
        } else {
            this.mDescriptionTxtView = (TextView) this.mBaseView.findViewById(R.id.description);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDescriptionTxtView.getLayoutParams();
        layoutParams.setMargins(0, this.mSubTitleTxtView.getVisibility() == 0 ? Utils.dipToPixel(this.mContext, 4.0d) : 0, 0, 0);
        this.mDescriptionTxtView.setLayoutParams(layoutParams);
        this.mDescriptionTxtView.setVisibility(0);
        if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            setUpCustomView();
        } else {
            this.mDescriptionTxtView.setTextAlignment(4);
            CharSequence description = promptConfig.mPromptInfo.getDescription();
            this.mDescriptionText = description;
            if (TextUtils.isEmpty(description)) {
                this.mDescriptionText = getDefaultDescriptionMessage();
            }
        }
        this.mDescriptionTxtView.setText(this.mDescriptionText);
        this.mDescriptionTxtView.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass3(this, 0));
        ?? r0 = new TextWatcher() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper.4
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                TextView textView2 = BiometricPromptGuiHelper.this.mDescriptionTxtView;
                if (textView2 != null) {
                    textView2.announceForAccessibility(textView2.getText());
                    BiometricPromptGuiHelper.setVisibilityDelayAnimation(BiometricPromptGuiHelper.this.mDescriptionTxtView, null);
                    BiometricPromptGuiHelper biometricPromptGuiHelper = BiometricPromptGuiHelper.this;
                    biometricPromptGuiHelper.getClass();
                    ChangeBounds changeBounds = new ChangeBounds();
                    changeBounds.setDuration(450L);
                    changeBounds.setInterpolator(BiometricPromptGuiHelper.DIALOG_EXTEND);
                    changeBounds.excludeTarget((View) biometricPromptGuiHelper.mDescLayout, true);
                    changeBounds.excludeTarget((View) biometricPromptGuiHelper.mPositiveButtonLayout, true);
                    TransitionManager.beginDelayedTransition((ViewGroup) biometricPromptGuiHelper.mBaseView, changeBounds);
                }
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        };
        this.mTextWatcher = r0;
        this.mDescriptionTxtView.addTextChangedListener(r0);
        if (promptConfig.isDeviceCredentialAllowed()) {
            this.mBottomButton = (Button) this.mBaseView.findViewById(R.id.button_use_credential);
            int i2 = promptConfig.mCredentialType;
            if (i2 == 1) {
                this.mBottomBtnText = this.mContext.getString(R.string.biometric_dialog_use_pin);
            } else if (i2 == 2) {
                this.mBottomBtnText = this.mContext.getString(R.string.biometric_dialog_use_pattern);
            } else if (i2 == 3) {
                this.mBottomBtnText = this.mContext.getString(R.string.biometric_dialog_use_password);
            } else if (i2 == 6) {
                this.mBottomBtnText = this.mContext.getString(R.string.ucm_biometric_dialog_use_smart_card);
            }
        } else {
            this.mBottomButton = (Button) this.mBaseView.findViewById(R.id.button_negative);
            this.mBottomBtnText = TextUtils.isEmpty(promptConfig.mPromptInfo.getNegativeButtonText()) ? this.mContext.getString(R.string.biometric_prompt_default_cancel) : promptConfig.mPromptInfo.getNegativeButtonText();
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
        View view = this.mBaseView;
        if (view != null) {
            view.setOnKeyListener(new View.OnKeyListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper.1
                public boolean downPressed = false;

                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view2, int i3, KeyEvent keyEvent) {
                    if (i3 != 4) {
                        return false;
                    }
                    if (keyEvent.getAction() == 0 && !this.downPressed) {
                        this.downPressed = true;
                    } else if (keyEvent.getAction() == 0) {
                        this.downPressed = false;
                    } else if (keyEvent.getAction() == 1 && this.downPressed) {
                        Log.d(BiometricPromptGuiHelper.this.TAG, "Handle Back Key");
                        this.downPressed = false;
                        ((BiometricPromptClient) BiometricPromptGuiHelper.this.mPromptConfig.mCallback).onUserCancel(2);
                    }
                    return true;
                }
            });
        }
        this.mDialogLayout.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass3(this, 1));
    }

    public boolean isScreenLandscape() {
        return Utils.isScreenLandscape(this.mContext);
    }

    public void onConfigurationChanged(Configuration configuration) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        Log.d(this.TAG, "onConfigurationChanged(last=" + this.mLastOrientation + ", orientation=" + configuration.orientation + ", type=" + this.mDisplayType + ", prevWidth=" + this.mDisplayMetrics.widthPixels + ", currWidth=" + displayMetrics.widthPixels);
        int i = this.mLastOrientation;
        int i2 = configuration.orientation;
        if (i == i2 && this.mDisplayType == configuration.semDisplayDeviceType && i2 != 2 && this.mDisplayMetrics.widthPixels == displayMetrics.widthPixels) {
            return;
        }
        this.mDisplayType = configuration.semDisplayDeviceType;
        this.mDisplayMetrics = displayMetrics;
        this.mLastOrientation = i2;
        updateLayoutAsConfigurationChanged(i2);
        boolean z = configuration.orientation == 2;
        Switch r1 = this.mSwitch;
        if (r1 != null && r1.getVisibility() == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mSwitch.getLayoutParams();
            layoutParams.setMargins(0, z ? Utils.dipToPixel(this.mContext, 16.0d) : Utils.dipToPixel(this.mContext, 20.0d), 0, 0);
            this.mSwitch.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout = this.mLogoLayout;
        if (linearLayout != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.setMargins(0, z ? Utils.dipToPixel(this.mContext, 12.0d) : Utils.dipToPixel(this.mContext, 20.0d), 0, 0);
            this.mLogoLayout.setLayoutParams(layoutParams2);
        }
        LinearLayout linearLayout2 = this.mTitleLayout;
        if (linearLayout2 != null) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
            layoutParams3.setMargins(0, z ? Utils.dipToPixel(this.mContext, 8.0d) : Utils.dipToPixel(this.mContext, 12.0d), 0, 0);
            this.mTitleLayout.setLayoutParams(layoutParams3);
        }
        FrameLayout frameLayout = this.mNegativeButtonLayout;
        if (frameLayout != null) {
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
            layoutParams4.setMargins(0, z ? this.mScrollViewBottomDividerView.getVisibility() == 0 ? Utils.dipToPixel(this.mContext, 8.0d) : Utils.dipToPixel(this.mContext, 10.0d) : Utils.dipToPixel(this.mContext, 16.0d), 0, z ? Utils.dipToPixel(this.mContext, 4.0d) : Utils.dipToPixel(this.mContext, 20.0d));
            this.mNegativeButtonLayout.setLayoutParams(layoutParams4);
        }
        View view = this.mScrollViewTopDividerView;
        if (view != null) {
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams5.setMargins(0, z ? Utils.dipToPixel(this.mContext, 8.0d) : Utils.dipToPixel(this.mContext, 16.0d), 0, 0);
            this.mScrollViewTopDividerView.setLayoutParams(layoutParams5);
        }
        View view2 = this.mScrollViewBottomDividerView;
        if (view2 != null) {
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) view2.getLayoutParams();
            layoutParams6.setMargins(0, z ? Utils.dipToPixel(this.mContext, 6.0d) : Utils.dipToPixel(this.mContext, 12.0d), 0, 0);
            this.mScrollViewBottomDividerView.setLayoutParams(layoutParams6);
        }
    }

    public void onRotationInfoChanged(int i) {
        Configuration configuration = Resources.getSystem().getConfiguration();
        if (this.mLastOrientation == configuration.orientation) {
            onConfigurationChanged(configuration);
        }
    }

    public void setUpCustomView() {
        LinearLayout linearLayout = (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_description_layout_list_box);
        LinearLayout linearLayout2 = (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_description_layout_more_option_box);
        this.mDescriptionTxtView.setTextAlignment(5);
        PromptConfig promptConfig = this.mPromptConfig;
        PromptContentViewWithMoreOptionsButton contentView = promptConfig.mPromptInfo.getContentView();
        linearLayout.setVisibility(8);
        linearLayout2.setVisibility(8);
        if (promptConfig.mPromptInfo.isContentViewMoreOptionsButtonUsed()) {
            linearLayout2.setVisibility(0);
            String description = contentView.getDescription();
            this.mDescriptionText = description;
            if (TextUtils.isEmpty(description)) {
                this.mDescriptionText = getDefaultDescriptionMessage();
            }
            TextView textView = (TextView) this.mBaseView.findViewById(R.id.customized_view_more_options_button);
            if ("android.server.biometrics.cts".contentEquals(promptConfig.mPackageName) && "More options".contentEquals(textView.getText())) {
                textView.setText("More Options");
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper.5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ((BiometricPromptClient) BiometricPromptGuiHelper.this.mPromptConfig.mCallback).onDismissed(8, null);
                }
            });
            return;
        }
        linearLayout.setVisibility(0);
        PromptVerticalListContentView promptVerticalListContentView = (PromptVerticalListContentView) contentView;
        String description2 = promptVerticalListContentView.getDescription();
        this.mDescriptionText = description2;
        if (TextUtils.isEmpty(description2)) {
            this.mDescriptionText = getDefaultDescriptionMessage();
        }
        List listItems = promptVerticalListContentView.getListItems();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i) instanceof PromptContentItemBulletedText) {
                arrayList.add(((PromptContentItemBulletedText) listItems.get(i)).getText());
            } else if (listItems.get(i) instanceof PromptContentItemPlainText) {
                arrayList.add(((PromptContentItemPlainText) listItems.get(i)).getText());
            }
        }
        ListView listView = (ListView) this.mBaseView.findViewById(R.id.prompt_description_list_view);
        listView.setAdapter((ListAdapter) new ArrayAdapter(this.mContext, R.layout.biometric_prompt_list_item, arrayList));
        int count = listView.getAdapter().getCount();
        int i2 = 0;
        for (int i3 = 0; i3 < count; i3++) {
            View view = listView.getAdapter().getView(i3, null, listView);
            view.measure(View.MeasureSpec.makeMeasureSpec((int) (listView.getResources().getDisplayMetrics().density * 500.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
            i2 += view.getMeasuredHeight();
        }
        int dividerHeight = (count - 1) * listView.getDividerHeight();
        int paddingBottom = listView.getPaddingBottom() + listView.getPaddingTop();
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = i2 + dividerHeight + paddingBottom;
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }

    public final void setUpKnoxBrandLogoImage() {
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

    public final void setUpLeftMarginViewWidth(int i) {
        View findViewById = this.mBaseView.findViewById(R.id.id_prompt_dialog_left_space);
        if (findViewById == null) {
            return;
        }
        ((LinearLayout.LayoutParams) findViewById.getLayoutParams()).width = i;
    }

    public void setUpScrollView() {
        this.mScrollView = (ScrollView) this.mBaseView.findViewById(R.id.scrollView);
        updateScrollViewHeight();
    }

    public abstract void show();

    public final void updateScrollViewHeight() {
        ScrollView scrollView = this.mScrollView;
        if (scrollView == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();
        int ratioHeight = Utils.getRatioHeight(this.mContext, this.mContext.getResources().getFloat(isScreenLandscape() ? R.dimen.biometric_prompt_scroll_view_landscape_height : R.dimen.biometric_prompt_scroll_view_portrait_height));
        if (this.mPromptConfig.mNumberOfAvailableBiometrics != 1) {
            ratioHeight -= (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_switch_view_height);
        }
        layoutParams.matchConstraintMaxHeight = ratioHeight;
        this.mScrollView.setLayoutParams(layoutParams);
        this.mScrollView.requestLayout();
        ScrollView scrollView2 = this.mScrollView;
        if (scrollView2 == null) {
            return;
        }
        scrollView2.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass3(this, 2));
    }

    public void showBiometricName(String str) {
    }

    public void updateLayoutAsConfigurationChanged(int i) {
    }
}
