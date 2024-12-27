package com.android.systemui.statusbar.phone;

import android.app.SemWallpaperColors;
import android.content.res.Resources;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SecLsScrimControlHelper implements KeyguardStateController.Callback, SystemUIWidgetCallback {
    public static final boolean DEBUG = Log.isLoggable("ScrimController", 3);
    public final AODAmbientWallpaperHelper mAodAmbientWallpaperHelper;
    public final ColorExtractor.GradientColors mBouncerColors;
    public final Lazy mCoverHostLazy;
    public final Lazy mDozeParametersLazy;
    public final Handler mHandler;
    public boolean mIsDLSOverlayView;
    public boolean mIsFingerprintOptionEnabled;
    public boolean mIsFoldOpened;
    public boolean mIsReduceTransparency;
    public final KeyguardFastBioUnlockController mKeyguardFastBioUnlockController;
    public final KeyguardStateController mKeyguardStateController;
    public final Lazy mKeyguardStateControllerLazy;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardVisibilityMonitor mKeyguardVisibilityMonitor;
    public final Lazy mPluginAODManagerLazy;
    public final PowerInteractor mPowerInteractor;
    public ScrimState mPreviousState;
    public SecLsScrimControlProvider mProvider;
    public boolean mQsExpandedOnNightMode;
    public final Resources mResources;
    public ScrimView mScrimBehind;
    public float mScrimBouncerAlpha;
    public int mScrimBouncerColor;
    public ScrimView mScrimInFront;
    private final SettingsHelper mSettingsHelper;
    public ScrimState mState;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.SecLsScrimControlHelper$4, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$statusbar$phone$ScrimState;

        static {
            int[] iArr = new int[ScrimState.values().length];
            $SwitchMap$com$android$systemui$statusbar$phone$ScrimState = iArr;
            try {
                iArr[ScrimState.AOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$phone$ScrimState[ScrimState.KEYGUARD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$phone$ScrimState[ScrimState.UNLOCKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$phone$ScrimState[ScrimState.DREAMING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public SecLsScrimControlHelper(Lazy lazy, Lazy lazy2, Lazy lazy3, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, KeyguardFoldController keyguardFoldController, Resources resources, Lazy lazy4, KeyguardFastBioUnlockController keyguardFastBioUnlockController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, Handler handler, PowerInteractor powerInteractor) {
        ScrimState scrimState = ScrimState.UNINITIALIZED;
        this.mPreviousState = scrimState;
        this.mState = scrimState;
        this.mIsFoldOpened = false;
        this.mDozeParametersLazy = lazy;
        this.mKeyguardStateControllerLazy = lazy2;
        this.mPluginAODManagerLazy = lazy3;
        this.mCoverHostLazy = lazy4;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardFastBioUnlockController = keyguardFastBioUnlockController;
        this.mKeyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.mAodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.mResources = resources;
        this.mScrimBouncerColor = resources.getColor(R.color.scrim_bouncer_color);
        ColorExtractor.GradientColors gradientColors = new ColorExtractor.GradientColors();
        this.mBouncerColors = gradientColors;
        gradientColors.setMainColor(this.mScrimBouncerColor);
        gradientColors.setSecondaryColor(this.mScrimBouncerColor);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.statusbar.phone.SecLsScrimControlHelper$$ExternalSyntheticLambda0
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    SecLsScrimControlHelper secLsScrimControlHelper = SecLsScrimControlHelper.this;
                    secLsScrimControlHelper.mIsFoldOpened = z;
                    if (z && secLsScrimControlHelper.mState == ScrimState.BOUNCER_SCRIMMED && LsRune.SECURITY_SUB_DISPLAY_LOCK && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isBouncerOnFoldOpened()) {
                        boolean whiteWallpaperState$1 = SecLsScrimControlHelper.getWhiteWallpaperState$1(Boolean.valueOf(secLsScrimControlHelper.mIsFoldOpened));
                        float f = whiteWallpaperState$1 ? 0.2f : 0.3f;
                        if (LsRune.SECURITY_COLOR_CURVE_BLUR) {
                            f = 0.0f;
                        }
                        secLsScrimControlHelper.mScrimBouncerColor = secLsScrimControlHelper.mResources.getColor(whiteWallpaperState$1 ? R.color.scrim_bouncer_color_whitebg : R.color.scrim_bouncer_color);
                        String str = "onFolderStateChanged isWhiteWallpaper() = " + whiteWallpaperState$1 + " bouncerScrimAlpha = " + f + " mScrimBouncerColor = #" + Integer.toHexString(secLsScrimControlHelper.mScrimBouncerColor);
                        if (secLsScrimControlHelper.mScrimBouncerAlpha != f) {
                            secLsScrimControlHelper.mScrimBouncerAlpha = f;
                            SecurityLog.d("ScrimController", str);
                        }
                        secLsScrimControlHelper.mBouncerColors.setMainColor(secLsScrimControlHelper.mScrimBouncerColor);
                        secLsScrimControlHelper.mBouncerColors.setSecondaryColor(secLsScrimControlHelper.mScrimBouncerColor);
                        secLsScrimControlHelper.mScrimInFront.setColors(secLsScrimControlHelper.mBouncerColors, false);
                        secLsScrimControlHelper.mScrimInFront.setTint(secLsScrimControlHelper.mScrimBouncerColor);
                        secLsScrimControlHelper.mScrimInFront.setViewAlpha(f);
                    }
                }
            }, 2, false);
        }
        if (LsRune.COVER_SUPPORTED) {
            this.mWakefulnessLifecycle = wakefulnessLifecycle;
        }
        this.mSettingsHelper = settingsHelper;
        this.mHandler = handler;
        this.mPowerInteractor = powerInteractor;
    }

    public static boolean getWhiteWallpaperState$1(Boolean bool) {
        SemWallpaperColors cachedSemWallpaperColors = bool != null ? WallpaperUtils.getCachedSemWallpaperColors(!bool.booleanValue()) : WallpaperUtils.getCachedSemWallpaperColors(WallpaperUtils.isSubDisplay());
        return cachedSemWallpaperColors != null && cachedSemWallpaperColors.get(512L).getFontColor() == 1;
    }

    public final boolean needUpdateScrimColor() {
        ScrimState scrimState;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        return keyguardStateControllerImpl.mPrimaryBouncerShowing && keyguardStateControllerImpl.mShowing && !((Boolean) this.mProvider.mKeyguardOccludedSupplier.get()).booleanValue() && ((scrimState = this.mState) == ScrimState.BOUNCER_SCRIMMED || scrimState == ScrimState.UNLOCKED);
    }

    public final void setFrontScrimToBlack(boolean z) {
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("setFrontScrimToBlack direct=", " isAODShown=", z);
        m.append(this.mSettingsHelper.isAODShown());
        Log.i("ScrimController", m.toString());
        if (!z) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.SecLsScrimControlHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SecLsScrimControlHelper secLsScrimControlHelper = SecLsScrimControlHelper.this;
                    secLsScrimControlHelper.getClass();
                    Log.i("ScrimController", "setFrontScrimToBlack in handler");
                    secLsScrimControlHelper.mScrimInFront.setViewAlpha(1.0f);
                    secLsScrimControlHelper.mScrimInFront.setTint(-16777216);
                }
            });
        } else {
            if (this.mSettingsHelper.isAODShown()) {
                return;
            }
            this.mScrimInFront.setViewAlpha(1.0f);
            this.mScrimInFront.setTint(-16777216);
        }
    }

    public final void setQsExpandedOnNightMode(boolean z) {
        if (this.mQsExpandedOnNightMode != z) {
            Log.d("ScrimController", "setQsExpandedOnNightMode(" + this.mQsExpandedOnNightMode + " -> " + z + ")");
            this.mQsExpandedOnNightMode = z;
            this.mProvider.mUpdateScrimsRunnable.run();
            setScrimAlphaForKeyguard(true);
        }
    }

    public final void setScrimAlphaForKeyguard(boolean z) {
        boolean isReduceTransparencyEnabled = this.mSettingsHelper.isReduceTransparencyEnabled();
        if (z || this.mIsReduceTransparency != isReduceTransparencyEnabled) {
            boolean whiteWallpaperState$1 = LsRune.SECURITY_SUB_DISPLAY_LOCK ? getWhiteWallpaperState$1(null) : WallpaperUtils.isWhiteKeyguardWallpaper("background");
            this.mIsReduceTransparency = isReduceTransparencyEnabled;
            boolean z2 = false;
            boolean z3 = LsRune.SECURITY_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed();
            boolean z4 = (LsRune.SECURITY_BLUR || z3) && !this.mIsReduceTransparency;
            if (z3 && this.mKeyguardUpdateMonitor.isFingerprintOptionEnabled() && DeviceType.isOpticalFingerprintSupported()) {
                z2 = true;
            }
            float f = (!z4 || z2 || ((Boolean) this.mProvider.mKeyguardOccludedSupplier.get()).booleanValue() || this.mQsExpandedOnNightMode || this.mIsDLSOverlayView) ? 0.75f : whiteWallpaperState$1 ? 0.2f : 0.3f;
            this.mScrimBouncerColor = this.mResources.getColor(whiteWallpaperState$1 ? R.color.scrim_bouncer_color_whitebg : R.color.scrim_bouncer_color);
            String str = "setScrimAlphaForKeyguard isWhiteWallpaper() = " + whiteWallpaperState$1 + " bouncerScrimAlpha = " + f + " mScrimBouncerColor = #" + Integer.toHexString(this.mScrimBouncerColor) + " callers = " + Debug.getCallers(1);
            if (this.mScrimBouncerAlpha != f) {
                this.mScrimBouncerAlpha = f;
                SecurityLog.d("ScrimController", str);
            }
            ScrimState.BOUNCER_SCRIMMED.mDefaultScrimAlpha = f;
            this.mBouncerColors.setMainColor(this.mScrimBouncerColor);
            this.mBouncerColors.setSecondaryColor(this.mScrimBouncerColor);
            ScrimState scrimState = this.mState;
            if (scrimState == ScrimState.AOD || scrimState == ScrimState.PULSING) {
                return;
            }
            this.mProvider.mUpdateScrimsRunnable.run();
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        setScrimAlphaForKeyguard(true);
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardDismissAmountChanged() {
    }
}
