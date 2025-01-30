package com.android.systemui.statusbar.phone;

import android.app.SemWallpaperColors;
import android.content.res.Resources;
import android.os.Debug;
import android.util.Log;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecLsScrimControlHelper implements KeyguardStateController.Callback, SystemUIWidgetCallback, PanelScreenShotLogger.LogProvider {
    public static final boolean DEBUG = Log.isLoggable("ScrimController", 3);
    public final AODAmbientWallpaperHelper mAodAmbientWallpaperHelper;
    public final ColorExtractor.GradientColors mBouncerColors;
    public final Lazy mCoverHostLazy;
    public final Lazy mDozeParametersLazy;
    public boolean mIsDLSOverlayView;
    public boolean mIsFingerprintOptionEnabled;
    public boolean mIsFoldOpened;
    public boolean mIsReduceTransparency;
    public final KeyguardFastBioUnlockController mKeyguardFastBioUnlockController;
    public final KeyguardStateController mKeyguardStateController;
    public final Lazy mKeyguardStateControllerLazy;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public ScrimView mNotificationsScrim;
    public final Lazy mPluginAODManagerLazy;
    public ScrimState mPreviousState;
    public SecLsScrimControlProvider mProvider;
    public boolean mQsExpandedOnNightMode;
    public final Resources mResources;
    public ScrimView mScrimBehind;
    public float mScrimBouncerAlpha;
    public int mScrimBouncerColor;
    public ScrimView mScrimInFront;
    public ScrimState mState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.SecLsScrimControlHelper$4 */
    public abstract /* synthetic */ class AbstractC31224 {
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

    public SecLsScrimControlHelper(Lazy lazy, Lazy lazy2, Lazy lazy3, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, KeyguardFoldController keyguardFoldController, Resources resources, Lazy lazy4, KeyguardFastBioUnlockController keyguardFastBioUnlockController) {
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
                    if (z && secLsScrimControlHelper.mState == ScrimState.BOUNCER_SCRIMMED && LsRune.SECURITY_SUB_DISPLAY_LOCK && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isBouncerOnFoldOpened()) {
                        boolean whiteWallpaperState = secLsScrimControlHelper.getWhiteWallpaperState(Boolean.valueOf(secLsScrimControlHelper.mIsFoldOpened));
                        float f = whiteWallpaperState ? 0.2f : 0.3f;
                        if (LsRune.SECURITY_COLOR_CURVE_BLUR) {
                            f = 0.0f;
                        }
                        secLsScrimControlHelper.mScrimBouncerColor = secLsScrimControlHelper.mResources.getColor(whiteWallpaperState ? R.color.scrim_bouncer_color_whitebg : R.color.scrim_bouncer_color);
                        String str = "onFolderStateChanged isWhiteWallpaper() = " + whiteWallpaperState + " bouncerScrimAlpha = " + f + " mScrimBouncerColor = #" + Integer.toHexString(secLsScrimControlHelper.mScrimBouncerColor);
                        if (secLsScrimControlHelper.mScrimBouncerAlpha != f) {
                            secLsScrimControlHelper.mScrimBouncerAlpha = f;
                            SecurityLog.m143d("ScrimController", str);
                        }
                        ColorExtractor.GradientColors gradientColors2 = secLsScrimControlHelper.mBouncerColors;
                        gradientColors2.setMainColor(secLsScrimControlHelper.mScrimBouncerColor);
                        gradientColors2.setSecondaryColor(secLsScrimControlHelper.mScrimBouncerColor);
                        secLsScrimControlHelper.mScrimInFront.setColors(gradientColors2, false);
                        ScrimView scrimView = secLsScrimControlHelper.mScrimInFront;
                        int i = secLsScrimControlHelper.mScrimBouncerColor;
                        scrimView.getClass();
                        scrimView.executeOnExecutor(new ScrimView$$ExternalSyntheticLambda4(scrimView, i));
                        secLsScrimControlHelper.mScrimInFront.setViewAlpha(f);
                    }
                }
            }, 2);
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        HashMap hashMap = new HashMap();
        ScrimView scrimView = this.mNotificationsScrim;
        if (scrimView != null) {
            hashMap.put("Notification Scrim", scrimView);
        }
        ScrimView scrimView2 = this.mScrimInFront;
        if (scrimView2 != null) {
            hashMap.put("Front Scrim", scrimView2);
        }
        ScrimView scrimView3 = this.mScrimBehind;
        if (scrimView3 != null) {
            hashMap.put("Behind Scrim", scrimView3);
        }
        final ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("ScrimController", arrayList);
        hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.statusbar.phone.SecLsScrimControlHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ArrayList arrayList2 = arrayList;
                ScrimView scrimView4 = (ScrimView) obj2;
                Objects.requireNonNull(arrayList2);
                arrayList2.add(((String) obj) + ": view alpha= " + scrimView4.mViewAlpha + ", alpha= " + scrimView4.getAlpha() + ", visibility= " + scrimView4.getVisibility() + ", tint= 0x" + Integer.toHexString(scrimView4.mTintColor));
            }
        });
        return arrayList;
    }

    public final boolean getWhiteWallpaperState(Boolean bool) {
        SemWallpaperColors cachedSemWallpaperColors = bool != null ? WallpaperUtils.getCachedSemWallpaperColors(!bool.booleanValue()) : WallpaperUtils.getCachedSemWallpaperColors(WallpaperUtils.isSubDisplay());
        return cachedSemWallpaperColors != null && cachedSemWallpaperColors.get(512L).getFontColor() == 1;
    }

    public final boolean needUpdateScrimColor() {
        ScrimState scrimState;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        return keyguardStateControllerImpl.mPrimaryBouncerShowing && keyguardStateControllerImpl.mShowing && !((Boolean) this.mProvider.mKeyguardOccludedSupplier.get()).booleanValue() && ((scrimState = this.mState) == ScrimState.BOUNCER_SCRIMMED || scrimState == ScrimState.UNLOCKED);
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
        boolean isReduceTransparencyEnabled = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled();
        if (z || this.mIsReduceTransparency != isReduceTransparencyEnabled) {
            boolean whiteWallpaperState = LsRune.SECURITY_SUB_DISPLAY_LOCK ? getWhiteWallpaperState(null) : WallpaperUtils.isWhiteKeyguardWallpaper("background");
            this.mIsReduceTransparency = isReduceTransparencyEnabled;
            boolean z2 = false;
            boolean z3 = LsRune.SECURITY_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed();
            boolean z4 = (LsRune.SECURITY_BLUR || z3) && !this.mIsReduceTransparency;
            if (z3 && this.mKeyguardUpdateMonitor.isFingerprintOptionEnabled()) {
                if (DeviceType.supportOpticalFingerprint == -1) {
                    DeviceType.supportOpticalFingerprint = 1;
                }
                if (DeviceType.supportOpticalFingerprint == 1) {
                    z2 = true;
                }
            }
            float f = (!z4 || z2 || ((Boolean) this.mProvider.mKeyguardOccludedSupplier.get()).booleanValue() || this.mQsExpandedOnNightMode || this.mIsDLSOverlayView) ? 0.75f : whiteWallpaperState ? 0.2f : 0.3f;
            this.mScrimBouncerColor = this.mResources.getColor(whiteWallpaperState ? R.color.scrim_bouncer_color_whitebg : R.color.scrim_bouncer_color);
            String str = "setScrimAlphaForKeyguard isWhiteWallpaper() = " + whiteWallpaperState + " bouncerScrimAlpha = " + f + " mScrimBouncerColor = #" + Integer.toHexString(this.mScrimBouncerColor) + " callers = " + Debug.getCallers(1);
            if (this.mScrimBouncerAlpha != f) {
                this.mScrimBouncerAlpha = f;
                SecurityLog.m143d("ScrimController", str);
            }
            ScrimState.BOUNCER_SCRIMMED.mDefaultScrimAlpha = f;
            ColorExtractor.GradientColors gradientColors = this.mBouncerColors;
            gradientColors.setMainColor(this.mScrimBouncerColor);
            gradientColors.setSecondaryColor(this.mScrimBouncerColor);
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
