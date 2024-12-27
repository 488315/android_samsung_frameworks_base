package com.android.systemui.blur;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.Choreographer;
import android.view.SemBlurInfo;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.SecPanelBackgroundController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecQpBlurController implements ShadeExpansionListener, StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener, SettingsHelper.OnChangedCallback, PanelScreenShotLogger.LogProvider, LockscreenShadeTransitionController.Callback {
    public final int backgroundColorId;
    public float mAnimatedFraction;
    public final SecPanelBackgroundController mBackgroundController;
    public ValueAnimator mBlurAnimator;
    public final PathInterpolator mCaptureInterpolator;
    public final CapturedBlurContainerController mCapturedBlurController;
    public final Choreographer mChoreographer;
    public final QSColorCurve mColorCurve;
    public final Context mContext;
    public final PathInterpolator mInterpolator;
    public boolean mIsBlurReduced;
    public boolean mIsBouncerShowing;
    public boolean mIsMirrorVisible;
    public boolean mIsWakingUp;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final ConfigurationState mLastConfigurationState;
    public final Lazy mLazyUnlockedScreenOffAnimationController;
    public boolean mNeedToUpdateByConfig;
    public final ConfigurationState mPanelCollapseConfig;
    public float mPanelExpandedFraction;
    public NotificationShadeWindowView mRoot;
    private final SettingsHelper mSettingsHelper;
    public final ShadeControllerImpl mShadeControllerImpl;
    public final StatusBarStateController mStatusBarStateController;
    public final SecQpBlurController$$ExternalSyntheticLambda0 mUpdateBlurCallback;
    public float mWindowBlurRadius;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.blur.SecQpBlurController$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* renamed from: -$$Nest$mhasCustomColorForBackground, reason: not valid java name */
    public static boolean m930$$Nest$mhasCustomColorForBackground(SecQpBlurController secQpBlurController) {
        return (Integer.toHexString(secQpBlurController.mContext.getColor(secQpBlurController.backgroundColorId)).equals("ff5d5d5d") ^ true) && !(secQpBlurController.mSettingsHelper.isUltraPowerSavingMode() || ((secQpBlurController.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ^ true) && (secQpBlurController.mContext.getResources().getConfiguration().uiMode & 32) != 0));
    }

    /* JADX WARN: Type inference failed for: r8v3, types: [com.android.systemui.blur.SecQpBlurController$$ExternalSyntheticLambda0] */
    public SecQpBlurController(Context context, SecPanelBackgroundController secPanelBackgroundController, CapturedBlurContainerController capturedBlurContainerController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController, Choreographer choreographer, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardWallpaper keyguardWallpaper, SettingsHelper settingsHelper, KeyguardStateController keyguardStateController, ConfigurationController configurationController, ShadeControllerImpl shadeControllerImpl, Lazy lazy, WakefulnessLifecycle wakefulnessLifecycle, LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        ConfigurationState.ConfigurationField configurationField = ConfigurationState.ConfigurationField.THEME_SEQ;
        ConfigurationState.ConfigurationField configurationField2 = ConfigurationState.ConfigurationField.ASSET_SEQ;
        ConfigurationState.ConfigurationField configurationField3 = ConfigurationState.ConfigurationField.UI_MODE;
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(configurationField, configurationField2, configurationField3));
        this.mPanelCollapseConfig = new ConfigurationState(Arrays.asList(configurationField3, ConfigurationState.ConfigurationField.ORIENTATION));
        this.backgroundColorId = R.color.open_theme_qp_bg_color;
        this.mInterpolator = new PathInterpolator(0.42f, 0.22f, 0.18f, 1.0f);
        this.mCaptureInterpolator = new PathInterpolator(0.29f, 0.08f, 0.69f, 0.98f);
        this.mAnimatedFraction = 0.0f;
        this.mIsMirrorVisible = false;
        this.mIsBouncerShowing = false;
        this.mIsBlurReduced = false;
        this.mIsWakingUp = false;
        this.mContext = context;
        this.mStatusBarStateController = statusBarStateController;
        this.mChoreographer = choreographer;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardStateController = keyguardStateController;
        this.mColorCurve = new QSColorCurve(context);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mUpdateBlurCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.blur.SecQpBlurController$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                SecQpBlurController.this.doFrame$1();
            }
        };
        settingsHelper.registerCallback(this, Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY), Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE), Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE));
        shadeExpansionStateManager.addExpansionListener(this);
        statusBarStateController.addCallback(this);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mShadeControllerImpl = shadeControllerImpl;
        PanelScreenShotLogger.INSTANCE.addLogProvider("SecQpBlurController", this);
        this.mLazyUnlockedScreenOffAnimationController = lazy;
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.blur.SecQpBlurController.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                SecQpBlurController.this.mIsWakingUp = false;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                SecQpBlurController.this.mIsWakingUp = true;
            }
        });
        lockscreenShadeTransitionController.addCallback(this);
        this.mIsBlurReduced = Settings.System.getInt(context.getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) != 0;
        this.mBackgroundController = secPanelBackgroundController;
        secPanelBackgroundController.mBlurUtils = anonymousClass2;
        this.mCapturedBlurController = capturedBlurContainerController;
        capturedBlurContainerController.mBlurUtils = anonymousClass2;
    }

    public final void doCaptureContainerAlpha(float f, CapturedBlurContainerController.BlurType blurType) {
        CapturedBlurContainerController capturedBlurContainerController = this.mCapturedBlurController;
        if (capturedBlurContainerController == null) {
            Log.w("SecQpBlurController", "doCapturedBlur: mCapturedBlurController is null");
        } else {
            capturedBlurContainerController.setAlpha(f, blurType);
        }
    }

    public final void doFrame$1() {
        float integer;
        boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
        float interpolation = (this.mIsMirrorVisible || this.mIsBouncerShowing) ? this.mAnimatedFraction : (z ? this.mInterpolator : this.mCaptureInterpolator).getInterpolation(this.mPanelExpandedFraction);
        QSColorCurve qSColorCurve = this.mColorCurve;
        qSColorCurve.setFraction(interpolation);
        if (((UnlockedScreenOffAnimationController) this.mLazyUnlockedScreenOffAnimationController.get()).isAnimationPlaying()) {
            if (interpolation > 0.0f) {
                Log.d("SecQpBlurController", "ScreenOff animation is running. & fraction: 0");
            }
            interpolation = 0.0f;
            integer = 0.0f;
        } else {
            integer = (this.mContext.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level) / 100.0f) * qSColorCurve.radius;
        }
        if (z) {
            if (this.mStatusBarStateController.getState() == 0 || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mOccluded || !this.mSettingsHelper.isUltraPowerSavingMode() || integer == 0.0f) {
                doWindowBlur((int) integer);
            }
        } else if (QpRune.QUICK_PANEL_BLUR_MASSIVE && !this.mIsBouncerShowing) {
            doCaptureContainerAlpha(interpolation, CapturedBlurContainerController.BlurType.QUICK_PANEL);
        }
        SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
        if (secPanelBackgroundController == null || secPanelBackgroundController.mView.getVisibility() != 0) {
            return;
        }
        SecPanelBackgroundController secPanelBackgroundController2 = this.mBackgroundController;
        if (((UnlockedScreenOffAnimationController) SecQpBlurController.this.mLazyUnlockedScreenOffAnimationController.get()).isAnimationPlaying()) {
            return;
        }
        secPanelBackgroundController2.mView.setAlpha(interpolation * secPanelBackgroundController2.mMaxAlpha);
    }

    public final void doWindowBlur(int i) {
        String str;
        if (this.mRoot == null) {
            Log.w("SecQpBlurController", "doWindowBlur: mRoot is null");
            return;
        }
        float f = i;
        if (this.mWindowBlurRadius != f || this.mNeedToUpdateByConfig) {
            SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
            boolean z = (secPanelBackgroundController != null && secPanelBackgroundController.mMaxAlpha == 1.0f) || this.mIsBlurReduced;
            SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
            SecPanelBackgroundController secPanelBackgroundController2 = this.mBackgroundController;
            if (secPanelBackgroundController2 != null && secPanelBackgroundController2.mView.getVisibility() != 0) {
                QSColorCurve qSColorCurve = this.mColorCurve;
                builder.setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY);
            }
            builder.setRadius(z ? 0 : i);
            if (z) {
                f = 0.0f;
            }
            this.mWindowBlurRadius = f;
            this.mNeedToUpdateByConfig = false;
            String str2 = "";
            if (this.mBackgroundController != null) {
                StringBuilder sb = new StringBuilder(" isBackgroundVisible = ");
                sb.append(this.mBackgroundController.mView.getVisibility() == 0);
                str = sb.toString();
            } else {
                str = "";
            }
            if (this.mBackgroundController != null) {
                str2 = " getMaxAlpha = " + this.mBackgroundController.mMaxAlpha;
            }
            StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("Window Blur: ", i, " shouldBlockBlur: ", z, " mIsBlurReduced = ");
            m.append(this.mIsBlurReduced);
            m.append(str2);
            m.append(str);
            Log.d("SecQpBlurController", m.toString());
            this.mRoot.semSetBlurInfo(builder.build());
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SecQpBlurController =================================================================================== ");
        arrayList.add("  radius = " + this.mColorCurve.radius + " custom_blur_level = " + (this.mContext.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level) / 100.0f) + " mWindowBlurRadius = " + this.mWindowBlurRadius);
        arrayList.add("  mIsMirrorVisible = " + this.mIsMirrorVisible + " mIsBouncerShowing = " + this.mIsBouncerShowing + " bgColor = " + Integer.toHexString(this.mContext.getColor(this.backgroundColorId)));
        arrayList.add("======================================================================================================= ");
        return arrayList;
    }

    public final void makeAnimationAndRun(float f, float f2, int i) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        ofFloat.setDuration(i);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.blur.SecQpBlurController$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SecQpBlurController secQpBlurController = SecQpBlurController.this;
                secQpBlurController.getClass();
                secQpBlurController.mAnimatedFraction = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                secQpBlurController.mChoreographer.postFrameCallback(secQpBlurController.mUpdateBlurCallback);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.blur.SecQpBlurController.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SecQpBlurController.this.mBlurAnimator = null;
            }
        });
        ValueAnimator valueAnimator = this.mBlurAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ofFloat.start();
        this.mBlurAnimator = ofFloat;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (uri == null) {
            return;
        }
        if (!Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY).equals(uri)) {
            if (Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE).equals(uri) || Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE).equals(uri)) {
                Log.d("SecQpBlurController", "onChanged: minimal_battery_use || ultra_powersaving_mode");
                if ((this.mContext.getResources().getConfiguration().uiMode & 32) != 0) {
                    SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
                    if (secPanelBackgroundController != null) {
                        secPanelBackgroundController.updatePanel();
                    }
                    CapturedBlurContainerController capturedBlurContainerController = this.mCapturedBlurController;
                    if (capturedBlurContainerController != null) {
                        capturedBlurContainerController.updateContainerVisibility();
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        this.mIsBlurReduced = Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) != 0;
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged: accessibility_reduce_transparency: "), this.mIsBlurReduced, "SecQpBlurController");
        SecPanelBackgroundController secPanelBackgroundController2 = this.mBackgroundController;
        if (secPanelBackgroundController2 != null) {
            secPanelBackgroundController2.mMaxAlpha = 0.3f;
            AnonymousClass2 anonymousClass2 = secPanelBackgroundController2.mBlurUtils;
            if (anonymousClass2 != null) {
                SecQpBlurController secQpBlurController = SecQpBlurController.this;
                if (secQpBlurController.mIsBlurReduced) {
                    secPanelBackgroundController2.mMaxAlpha = 1.0f;
                } else if (secQpBlurController.mSettingsHelper.isUltraPowerSavingMode()) {
                    secPanelBackgroundController2.mMaxAlpha = 1.0f;
                }
            }
            secPanelBackgroundController2.updatePanel();
        }
        CapturedBlurContainerController capturedBlurContainerController2 = this.mCapturedBlurController;
        if (capturedBlurContainerController2 != null) {
            capturedBlurContainerController2.updateContainerVisibility();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration)) {
            this.mNeedToUpdateByConfig = true;
            SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
            if (secPanelBackgroundController != null) {
                secPanelBackgroundController.updatePanel();
            }
            CapturedBlurContainerController capturedBlurContainerController = this.mCapturedBlurController;
            if (capturedBlurContainerController != null) {
                capturedBlurContainerController.updateContainerVisibility();
            }
            doFrame$1();
            configurationState.update(configuration);
        }
        ConfigurationState configurationState2 = this.mPanelCollapseConfig;
        if (configurationState2.needToUpdate(configuration)) {
            if (QpRune.QUICK_PANEL_BLUR_MASSIVE && this.mPanelExpandedFraction > 0.0f) {
                int state = this.mStatusBarStateController.getState();
                ShadeControllerImpl shadeControllerImpl = this.mShadeControllerImpl;
                if (state == 1) {
                    shadeControllerImpl.getNpvc().animateCollapseQs(true);
                } else {
                    shadeControllerImpl.instantCollapseShade();
                }
            }
            configurationState2.update(configuration);
        }
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (statusBarStateController.getState() == 1) {
            return;
        }
        float f = this.mPanelExpandedFraction;
        float f2 = shadeExpansionChangeEvent.fraction;
        if (f != f2) {
            this.mPanelExpandedFraction = f2;
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(new StringBuilder("onPanelExpansionChanged mPanelExpandedFraction: "), this.mPanelExpandedFraction, "SecQpBlurController");
            this.mColorCurve.getClass();
            float integer = (this.mContext.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level) / 100.0f) * 250.0f;
            if (statusBarStateController.getState() == 0) {
                float f3 = this.mPanelExpandedFraction;
                if ((f3 == 1.0f && this.mWindowBlurRadius != integer) || (f3 == 0.0f && this.mWindowBlurRadius != 0.0f)) {
                    doFrame$1();
                }
            }
        }
        this.mChoreographer.postFrameCallback(this.mUpdateBlurCallback);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
        if (secPanelBackgroundController != null && secPanelBackgroundController.mStatusBarState != i) {
            secPanelBackgroundController.mStatusBarState = i;
            secPanelBackgroundController.updatePanel();
        }
        if (i == 2) {
            this.mPanelExpandedFraction = 1.0f;
        } else if (i == 1) {
            this.mPanelExpandedFraction = 0.0f;
        }
        this.mChoreographer.postFrameCallback(this.mUpdateBlurCallback);
    }

    public final void setBrightnessMirrorVisible(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setBrightnessMirrorVisible: ", "SecQpBlurController", z);
        this.mIsMirrorVisible = z;
        if (z) {
            makeAnimationAndRun(1.0f, 0.0f, 150);
        } else {
            makeAnimationAndRun(0.0f, 1.0f, 200);
        }
    }

    @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
    public final void setTransitionToFullShadeAmount(float f) {
        if (this.mStatusBarStateController.getState() == 1 && this.mPanelExpandedFraction != f) {
            this.mPanelExpandedFraction = f;
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(new StringBuilder("setTransitionToFullShadeAmount mPanelExpandedFraction: "), this.mPanelExpandedFraction, "SecQpBlurController");
            this.mChoreographer.postFrameCallback(this.mUpdateBlurCallback);
        }
    }
}
