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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.CapturedBlurContainer;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.SecPanelBackground;
import com.android.systemui.statusbar.phone.SecPanelBackgroundController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecQpBlurController implements ShadeExpansionListener, StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener, SettingsHelper.OnChangedCallback, PanelScreenShotLogger.LogProvider, DisplayLifecycle.Observer {
    public final int backgroundColorId;
    public float mAnimatedFraction;
    public SecPanelBackgroundController mBackgroundController;
    public ValueAnimator mBlurAnimator;
    public final C11322 mBlurUtils;
    public final PathInterpolator mCaptureInterpolator;
    public CapturedBlurContainerController mCapturedBlurController;
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
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final ConfigurationState mLastConfigurationState;
    public final Lazy mLazyUnlockedScreenOffAnimationController;
    public boolean mNeedToUpdateByConfig;
    public final ConfigurationState mPanelCollapseConfig;
    public float mPanelExpandedFraction;
    public boolean mQsExpanded;
    public NotificationShadeWindowView mRoot;
    public final SettingsHelper mSettingsHelper;
    public final ShadeControllerImpl mShadeControllerImpl;
    public boolean mShouldUseBlurFilter;
    public final StatusBarStateController mStatusBarStateController;
    public final SecQpBlurController$$ExternalSyntheticLambda0 mUpdateBlurCallback;
    public float mWallpaperBlurRadius;
    public float mWindowBlurRadius;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.blur.SecQpBlurController$2 */
    public final class C11322 {
        public C11322() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:7:0x003e, code lost:
        
            if (((r1.getResources().getConfiguration().uiMode & 32) != 0) != false) goto L12;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean hasCustomColorForPanelBG() {
            boolean z;
            SecQpBlurController secQpBlurController = SecQpBlurController.this;
            int i = secQpBlurController.backgroundColorId;
            Context context = secQpBlurController.mContext;
            boolean z2 = !Integer.toHexString(context.getColor(i)).equals("ff5d5d5d");
            boolean isUltraPowerSavingMode = secQpBlurController.mSettingsHelper.isUltraPowerSavingMode();
            boolean z3 = !context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on);
            if (!isUltraPowerSavingMode) {
                if (z3) {
                }
                z = false;
                return (z2 || z) ? false : true;
            }
            z = true;
            if (z2) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r5v14, types: [com.android.systemui.blur.SecQpBlurController$$ExternalSyntheticLambda0] */
    public SecQpBlurController(Context context, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController, Choreographer choreographer, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardWallpaper keyguardWallpaper, SettingsHelper settingsHelper, ConfigurationController configurationController, ShadeControllerImpl shadeControllerImpl, Lazy lazy, WakefulnessLifecycle wakefulnessLifecycle) {
        ConfigurationState.ConfigurationField configurationField = ConfigurationState.ConfigurationField.THEME_SEQ;
        ConfigurationState.ConfigurationField configurationField2 = ConfigurationState.ConfigurationField.ASSET_SEQ;
        ConfigurationState.ConfigurationField configurationField3 = ConfigurationState.ConfigurationField.UI_MODE;
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(configurationField, configurationField2, configurationField3));
        this.mPanelCollapseConfig = new ConfigurationState(Arrays.asList(configurationField3, ConfigurationState.ConfigurationField.ORIENTATION));
        this.backgroundColorId = R.color.open_theme_qp_bg_color;
        this.mInterpolator = new PathInterpolator(0.63f, 0.0f, 0.63f, 0.83f);
        this.mCaptureInterpolator = new PathInterpolator(0.29f, 0.08f, 0.69f, 0.98f);
        this.mAnimatedFraction = 0.0f;
        this.mIsMirrorVisible = false;
        this.mIsBouncerShowing = false;
        this.mIsWakingUp = false;
        this.mContext = context;
        this.mStatusBarStateController = statusBarStateController;
        this.mChoreographer = choreographer;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mSettingsHelper = settingsHelper;
        this.mColorCurve = new QSColorCurve(context);
        this.mBlurUtils = new C11322();
        this.mUpdateBlurCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.blur.SecQpBlurController$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                SecQpBlurController.this.doFrame();
            }
        };
        settingsHelper.registerCallback(this, Settings.System.getUriFor("accessibility_reduce_transparency"), Settings.System.getUriFor("minimal_battery_use"), Settings.System.getUriFor("ultra_powersaving_mode"));
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
        this.mIsBlurReduced = Settings.System.getInt(context.getContentResolver(), "accessibility_reduce_transparency", 0) != 0;
    }

    public final void doCaptureContainerAlpha(float f, CapturedBlurContainerController.BlurType blurType) {
        CapturedBlurContainerController.BlurType blurType2;
        CapturedBlurContainerController capturedBlurContainerController = this.mCapturedBlurController;
        if (capturedBlurContainerController == null) {
            Log.w("SecQpBlurController", "doCapturedBlur: mCapturedBlurController is null");
            return;
        }
        boolean z = true;
        if (((CapturedBlurContainer) capturedBlurContainerController.mView).getVisibility() != 0) {
            return;
        }
        if (f == 0.0f) {
            ((CapturedBlurContainer) capturedBlurContainerController.mView).setBackground(null);
        }
        if (f > 0.0f) {
            if (((CapturedBlurContainer) capturedBlurContainerController.mView).getBackground() != null && (blurType2 = capturedBlurContainerController.mLastBlurType) != null && blurType2 == blurType) {
                z = false;
            }
            if (z) {
                capturedBlurContainerController.captureAndSetBackground(blurType);
            }
        }
        ((CapturedBlurContainer) capturedBlurContainerController.mView).setAlpha(f);
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doFrame() {
        float customBlurPercentage;
        SemBlurInfo.Builder builder;
        String str;
        boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
        float interpolation = (this.mIsMirrorVisible || this.mIsBouncerShowing) ? this.mAnimatedFraction : (z ? this.mInterpolator : this.mCaptureInterpolator).getInterpolation(this.mPanelExpandedFraction);
        QSColorCurve qSColorCurve = this.mColorCurve;
        qSColorCurve.setFraction(interpolation);
        if (((UnlockedScreenOffAnimationController) this.mLazyUnlockedScreenOffAnimationController.get()).isAnimationPlaying()) {
            if (interpolation > 0.0f) {
                Log.d("SecQpBlurController", "ScreenOff animation is running. & fraction: 0");
            }
            interpolation = 0.0f;
            customBlurPercentage = 0.0f;
        } else {
            customBlurPercentage = getCustomBlurPercentage() * qSColorCurve.radius;
        }
        if (z) {
            if (shouldUseBlurFilter()) {
                KeyguardWallpaper keyguardWallpaper = this.mKeyguardWallpaper;
                if (keyguardWallpaper == null) {
                    Log.w("SecQpBlurController", "doWallpaperBlur: mKeyguardWallpaper is null");
                } else if (this.mWallpaperBlurRadius != customBlurPercentage || this.mNeedToUpdateByConfig) {
                    int i = (int) (customBlurPercentage / 2.0f);
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("doWallPaperBlur: ", i, "SecQpBlurController");
                    ((KeyguardWallpaperController) keyguardWallpaper).applyBlur(i);
                    this.mWallpaperBlurRadius = i;
                    this.mNeedToUpdateByConfig = false;
                }
            } else {
                int i2 = (int) customBlurPercentage;
                if (this.mRoot == null) {
                    Log.w("SecQpBlurController", "doWindowBlur: mRoot is null");
                } else {
                    float f = i2;
                    if (this.mWindowBlurRadius != f || this.mNeedToUpdateByConfig) {
                        SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
                        boolean z2 = (secPanelBackgroundController != null && secPanelBackgroundController.mMaxAlpha == 1.0f) || this.mIsBlurReduced;
                        SemBlurInfo.Builder builder2 = new SemBlurInfo.Builder(0);
                        SecPanelBackgroundController secPanelBackgroundController2 = this.mBackgroundController;
                        if (secPanelBackgroundController2 != null) {
                            if (!(((SecPanelBackground) secPanelBackgroundController2.mView).getVisibility() == 0)) {
                                builder = builder2;
                                builder2.setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY);
                                builder.setRadius(!z2 ? 0 : i2);
                                if (z2) {
                                    f = 0.0f;
                                }
                                this.mWindowBlurRadius = f;
                                this.mNeedToUpdateByConfig = false;
                                String str2 = "";
                                if (this.mBackgroundController == null) {
                                    StringBuilder sb = new StringBuilder(" isBackgroundVisible = ");
                                    sb.append(((SecPanelBackground) this.mBackgroundController.mView).getVisibility() == 0);
                                    str = sb.toString();
                                } else {
                                    str = "";
                                }
                                if (this.mBackgroundController != null) {
                                    str2 = " getMaxAlpha = " + this.mBackgroundController.mMaxAlpha;
                                }
                                StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("Window Blur: ", i2, " shouldBlockBlur: ", z2, " mIsBlurReduced = ");
                                m76m.append(this.mIsBlurReduced);
                                m76m.append(str2);
                                m76m.append(str);
                                Log.d("SecQpBlurController", m76m.toString());
                                this.mRoot.semSetBlurInfo(builder.build());
                            }
                        }
                        builder = builder2;
                        builder.setRadius(!z2 ? 0 : i2);
                        if (z2) {
                        }
                        this.mWindowBlurRadius = f;
                        this.mNeedToUpdateByConfig = false;
                        String str22 = "";
                        if (this.mBackgroundController == null) {
                        }
                        if (this.mBackgroundController != null) {
                        }
                        StringBuilder m76m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("Window Blur: ", i2, " shouldBlockBlur: ", z2, " mIsBlurReduced = ");
                        m76m2.append(this.mIsBlurReduced);
                        m76m2.append(str22);
                        m76m2.append(str);
                        Log.d("SecQpBlurController", m76m2.toString());
                        this.mRoot.semSetBlurInfo(builder.build());
                    }
                }
            }
        } else if (QpRune.QUICK_PANEL_BLUR_MASSIVE && !this.mIsBouncerShowing) {
            doCaptureContainerAlpha(interpolation, CapturedBlurContainerController.BlurType.QUICK_PANEL);
        }
        SecPanelBackgroundController secPanelBackgroundController3 = this.mBackgroundController;
        if (secPanelBackgroundController3 != null) {
            if (((SecPanelBackground) secPanelBackgroundController3.mView).getVisibility() == 0) {
                SecPanelBackgroundController secPanelBackgroundController4 = this.mBackgroundController;
                if (((UnlockedScreenOffAnimationController) SecQpBlurController.this.mLazyUnlockedScreenOffAnimationController.get()).isAnimationPlaying()) {
                    return;
                }
                ((SecPanelBackground) secPanelBackgroundController4.mView).setAlpha(interpolation * secPanelBackgroundController4.mMaxAlpha);
            }
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("SecQpBlurController =================================================================================== ");
        arrayList.add("  radius = " + this.mColorCurve.radius + " custom_blur_level = " + getCustomBlurPercentage() + " mWindowBlurRadius = " + this.mWindowBlurRadius + " mWallpaperBlurRadius = " + this.mWallpaperBlurRadius);
        arrayList.add("  mIsMirrorVisible = " + this.mIsMirrorVisible + " mIsBouncerShowing = " + this.mIsBouncerShowing + " shouldUseBlurFilter = " + shouldUseBlurFilter() + " bgColor = " + Integer.toHexString(this.mContext.getColor(this.backgroundColorId)));
        arrayList.add("======================================================================================================= ");
        return arrayList;
    }

    public final float getCustomBlurPercentage() {
        return this.mContext.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level) / 100.0f;
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

    public final void notifyWallpaper(boolean z) {
        boolean z2 = this.mStatusBarStateController.getState() == 1;
        boolean isVideoWallpaper = WallpaperUtils.isVideoWallpaper();
        if (z2 && isVideoWallpaper && !this.mIsBouncerShowing) {
            Log.d("SecQpBlurController", "notifyWallpaper(" + z + ")");
            SystemUIWallpaperBase systemUIWallpaperBase = ((KeyguardWallpaperController) this.mKeyguardWallpaper).mWallpaperView;
            if (systemUIWallpaperBase != null) {
                systemUIWallpaperBase.updateDrawState(!z);
            }
        }
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (uri == null) {
            return;
        }
        boolean equals = Settings.System.getUriFor("accessibility_reduce_transparency").equals(uri);
        Context context = this.mContext;
        if (!equals) {
            if (Settings.System.getUriFor("minimal_battery_use").equals(uri) || Settings.System.getUriFor("ultra_powersaving_mode").equals(uri)) {
                Log.d("SecQpBlurController", "onChanged: minimal_battery_use || ultra_powersaving_mode");
                if ((context.getResources().getConfiguration().uiMode & 32) != 0) {
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
        this.mIsBlurReduced = Settings.System.getInt(context.getContentResolver(), "accessibility_reduce_transparency", 0) != 0;
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("onChanged: accessibility_reduce_transparency: "), this.mIsBlurReduced, "SecQpBlurController");
        SecPanelBackgroundController secPanelBackgroundController2 = this.mBackgroundController;
        if (secPanelBackgroundController2 != null) {
            secPanelBackgroundController2.mMaxAlpha = 0.3f;
            C11322 c11322 = secPanelBackgroundController2.mBlurUtils;
            if (c11322 != null) {
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
            doFrame();
            configurationState.update(configuration);
        }
        ConfigurationState configurationState2 = this.mPanelCollapseConfig;
        if (configurationState2.needToUpdate(configuration)) {
            if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                int state = this.mStatusBarStateController.getState();
                ShadeControllerImpl shadeControllerImpl = this.mShadeControllerImpl;
                if (state == 1) {
                    shadeControllerImpl.mNotificationPanelViewController.animateCollapseQs(true);
                } else if (this.mPanelExpandedFraction > 0.0f) {
                    shadeControllerImpl.instantCollapseShade();
                }
            }
            configurationState2.update(configuration);
        }
    }

    @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
    public final void onFolderStateChanged(boolean z) {
        SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
        if (secPanelBackgroundController != null) {
            secPanelBackgroundController.updatePanel();
        }
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (statusBarStateController.getState() == 1) {
            return;
        }
        float screenHeight = DeviceState.getScreenHeight(this.mContext);
        boolean z = QpRune.QUICK_PANEL_BLUR_MASSIVE;
        float max = Math.max(Math.min(1.0f, shadeExpansionChangeEvent.dragDownPxAmount / (screenHeight * (z ? 0.35f : 0.6f))), shadeExpansionChangeEvent.fraction);
        if (this.mPanelExpandedFraction != max) {
            this.mPanelExpandedFraction = max;
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("onPanelExpansionChanged mPanelExpandedFraction: "), this.mPanelExpandedFraction, "SecQpBlurController");
            float customBlurPercentage = getCustomBlurPercentage() * (this.mColorCurve.isCoverDisplay() ? 348.0f : z ? 70.0f : QpRune.QUICK_TABLET ? 200.0f : 400.0f);
            if (statusBarStateController.getState() == 0) {
                float f = this.mPanelExpandedFraction;
                if ((f == 1.0f && this.mWindowBlurRadius != customBlurPercentage) || (f == 0.0f && this.mWindowBlurRadius != 0.0f)) {
                    doFrame();
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
        }
        this.mChoreographer.postFrameCallback(this.mUpdateBlurCallback);
    }

    public final void setBrightnessMirrorVisible(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setBrightnessMirrorVisible: ", z, "SecQpBlurController");
        this.mIsMirrorVisible = z;
        if (z) {
            makeAnimationAndRun(1.0f, 0.0f, 150);
        } else {
            makeAnimationAndRun(0.0f, 1.0f, 200);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean shouldUseBlurFilter() {
        if ((this.mStatusBarStateController.getState() == 0) == true) {
            return false;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (!keyguardStateControllerImpl.mOccluded && this.mSettingsHelper.isUltraPowerSavingMode()) {
            return true;
        }
        int i = this.mContext.getResources().getConfiguration().semDisplayDeviceType;
        boolean z = (keyguardStateControllerImpl.mOccluded || !this.mKeyguardUpdateMonitor.hasLockscreenWallpaper() || WallpaperUtils.isVideoWallpaper() || (WallpaperUtils.sWallpaperType[WallpaperUtils.isSubDisplay() ? 1 : 0] == 7) == true) ? false : true;
        if (z != this.mShouldUseBlurFilter) {
            this.mShouldUseBlurFilter = z;
            SecPanelBackgroundController secPanelBackgroundController = this.mBackgroundController;
            if (secPanelBackgroundController != null) {
                secPanelBackgroundController.updatePanel();
            }
        }
        return z;
    }
}
