package com.android.systemui.statusbar.phone;

import android.graphics.Color;
import android.os.Build;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.dock.DockManager;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda4;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ScrimState {
    public static final /* synthetic */ ScrimState[] $VALUES;
    public static final ScrimState AOD;
    public static final ScrimState AUTH_SCRIMMED;
    public static final ScrimState AUTH_SCRIMMED_SHADE;
    public static final ScrimState BOUNCER;
    public static final ScrimState BOUNCER_SCRIMMED;
    public static final ScrimState BRIGHTNESS_MIRROR;
    public static final ScrimState DREAMING;
    public static final ScrimState KEYGUARD;
    public static final ScrimState OFF;
    public static final ScrimState PULSING;
    public static final ScrimState SHADE_LOCKED;
    public static final String TAG;
    public static final ScrimState UNINITIALIZED;
    public static final ScrimState UNLOCKED;
    AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    boolean mAnimateChange;
    long mAnimationDuration;
    float mAodFrontScrimAlpha;
    float mBehindAlpha;
    int mBehindTint;
    boolean mBlankScreen;
    boolean mClipQsScrim;
    float mDefaultScrimAlpha;
    boolean mDisplayRequiresBlanking;
    DockManager mDockManager;
    DozeParameters mDozeParameters;
    float mFrontAlpha;
    int mFrontTint;
    boolean mHasBackdrop;
    boolean mKeyguardFadingAway;
    long mKeyguardFadingAwayDuration;
    boolean mLaunchingAffordanceWithPreview;
    float mNotifAlpha;
    int mNotifTint;
    boolean mOccludeAnimationPlaying;
    ScrimView mScrimBehind;
    float mScrimBehindAlphaKeyguard;
    ScrimView mScrimInFront;
    int mSurfaceColor;
    boolean mWakeLockScreenSensorActive;
    boolean mWallpaperSupportsAmbientMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$1 */
    public enum C31081 extends ScrimState {
        public /* synthetic */ C31081() {
            this("OFF", 1);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
            this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
            this.mFrontAlpha = 1.0f;
            this.mBehindAlpha = 1.0f;
            this.mAnimationDuration = 1000L;
        }

        private C31081(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$10 */
    public enum C310910 extends ScrimState {
        public /* synthetic */ C310910() {
            this("PULSING", 10);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final float getMaxLightRevealScrimAlpha() {
            if (this.mWakeLockScreenSensorActive) {
                return 0.6f;
            }
            return ScrimState.AOD.getMaxLightRevealScrimAlpha();
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mFrontAlpha = this.mAodFrontScrimAlpha;
            this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
            this.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
            this.mBlankScreen = this.mDisplayRequiresBlanking;
            this.mAnimationDuration = this.mWakeLockScreenSensorActive ? 1000L : 220L;
        }

        private C310910(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$11 */
    public enum C311011 extends ScrimState {
        public /* synthetic */ C311011() {
            this("UNLOCKED", 11);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBehindAlpha = this.mClipQsScrim ? 1.0f : 0.0f;
            this.mNotifAlpha = 0.0f;
            this.mFrontAlpha = 0.0f;
            this.mAnimationDuration = this.mKeyguardFadingAway ? this.mKeyguardFadingAwayDuration : 300L;
            ScrimState scrimState2 = ScrimState.AOD;
            this.mAnimateChange = (this.mLaunchingAffordanceWithPreview || this.mOccludeAnimationPlaying || (scrimState == scrimState2 || scrimState == ScrimState.PULSING)) ? false : true;
            this.mFrontTint = 0;
            this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
            this.mBlankScreen = false;
            if (this.mDisplayRequiresBlanking && scrimState == scrimState2) {
                updateScrimColor(this.mScrimInFront);
                updateScrimColor(this.mScrimBehind);
                this.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
                this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
                this.mBlankScreen = true;
            }
            if (this.mClipQsScrim) {
                updateScrimColor(this.mScrimBehind);
            }
        }

        private C311011(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$12 */
    public enum C311112 extends ScrimState {
        public /* synthetic */ C311112() {
            this("DREAMING", 12);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mFrontTint = 0;
            int i = EmergencyPhoneWidget.BG_COLOR;
            this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
            boolean z = this.mClipQsScrim;
            if (!z) {
                i = 0;
            }
            this.mNotifTint = i;
            this.mFrontAlpha = 0.0f;
            this.mBehindAlpha = z ? 1.0f : 0.0f;
            this.mNotifAlpha = 0.0f;
            this.mBlankScreen = false;
            if (z) {
                updateScrimColor(this.mScrimBehind);
            }
        }

        private C311112(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$2 */
    public enum C31122 extends ScrimState {
        public /* synthetic */ C31122() {
            this("KEYGUARD", 2);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBlankScreen = false;
            if (scrimState == ScrimState.AOD) {
                this.mAnimationDuration = 667L;
                if (this.mDisplayRequiresBlanking) {
                    this.mBlankScreen = true;
                }
            } else if (scrimState == ScrimState.KEYGUARD) {
                this.mAnimationDuration = 667L;
            } else {
                this.mAnimationDuration = 220L;
            }
            this.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
            this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
            boolean z = this.mClipQsScrim;
            this.mNotifTint = z ? -16777216 : 0;
            this.mFrontAlpha = 0.0f;
            this.mBehindAlpha = z ? 1.0f : this.mScrimBehindAlphaKeyguard;
            this.mNotifAlpha = z ? this.mScrimBehindAlphaKeyguard : 0.0f;
            if (z) {
                updateScrimColor(this.mScrimBehind);
            }
        }

        private C31122(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$3 */
    public enum C31133 extends ScrimState {
        public /* synthetic */ C31133() {
            this("AUTH_SCRIMMED_SHADE", 3);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
            this.mFrontAlpha = 0.66f;
            this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
            this.mBehindAlpha = 1.0f;
        }

        private C31133(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$4 */
    public enum C31144 extends ScrimState {
        public /* synthetic */ C31144() {
            this("AUTH_SCRIMMED", 4);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mNotifTint = scrimState.mNotifTint;
            this.mNotifAlpha = scrimState.mNotifAlpha;
            this.mBehindTint = scrimState.mBehindTint;
            this.mBehindAlpha = scrimState.mBehindAlpha;
            this.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
            this.mFrontAlpha = 0.66f;
        }

        private C31144(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$5 */
    public enum C31155 extends ScrimState {
        public /* synthetic */ C31155() {
            this("BOUNCER", 5);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            boolean z = this.mClipQsScrim;
            this.mBehindAlpha = z ? 1.0f : this.mDefaultScrimAlpha;
            this.mBehindTint = z ? EmergencyPhoneWidget.BG_COLOR : this.mSurfaceColor;
            this.mNotifAlpha = z ? this.mDefaultScrimAlpha : 0.0f;
            this.mNotifTint = 0;
            this.mFrontAlpha = 0.0f;
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void setSurfaceColor(int i) {
            this.mSurfaceColor = i;
            if (this.mClipQsScrim) {
                return;
            }
            this.mBehindTint = i;
        }

        private C31155(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$6 */
    public enum C31166 extends ScrimState {
        public /* synthetic */ C31166() {
            this("BOUNCER_SCRIMMED", 6);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBehindAlpha = 0.0f;
            this.mFrontAlpha = this.mDefaultScrimAlpha;
        }

        private C31166(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$7 */
    public enum C31177 extends ScrimState {
        public /* synthetic */ C31177() {
            this("SHADE_LOCKED", 7);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            boolean z = this.mClipQsScrim;
            this.mBehindAlpha = z ? 1.0f : this.mDefaultScrimAlpha;
            this.mNotifAlpha = 1.0f;
            this.mFrontAlpha = 0.0f;
            this.mBehindTint = z ? 0 : EmergencyPhoneWidget.BG_COLOR;
            if (z) {
                updateScrimColor(this.mScrimBehind);
            }
        }

        private C31177(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$8 */
    public enum C31188 extends ScrimState {
        public /* synthetic */ C31188() {
            this("BRIGHTNESS_MIRROR", 8);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBehindAlpha = 0.0f;
            this.mFrontAlpha = 0.0f;
        }

        private C31188(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$9 */
    public enum C31199 extends ScrimState {
        public /* synthetic */ C31199() {
            this("AOD", 9);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final float getMaxLightRevealScrimAlpha() {
            return (LsRune.AOD_FULLSCREEN && this.mAODAmbientWallpaperHelper.isAODFullScreenMode()) ? ScrimState.getAlpha() : (!this.mWallpaperSupportsAmbientMode || this.mHasBackdrop) ? 1.0f : 0.0f;
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final boolean isLowPowerState() {
            return true;
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            boolean alwaysOn = this.mDozeParameters.getAlwaysOn();
            boolean z = this.mDozeParameters.mIsQuickPickupEnabled;
            this.mDockManager.getClass();
            this.mBlankScreen = this.mDisplayRequiresBlanking;
            this.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
            this.mFrontAlpha = (alwaysOn || z) ? this.mAodFrontScrimAlpha : 1.0f;
            this.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
            this.mBehindAlpha = 0.0f;
            this.mAnimationDuration = 1000L;
            DozeParameters dozeParameters = this.mDozeParameters;
            this.mAnimateChange = dozeParameters.mControlScreenOffAnimation && !dozeParameters.shouldShowLightRevealScrim();
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final boolean shouldBlendWithMainColor() {
            return false;
        }

        private C31199(String str, int i) {
            super(str, i, 0);
        }
    }

    static {
        ScrimState scrimState = new ScrimState("UNINITIALIZED", 0);
        UNINITIALIZED = scrimState;
        C31081 c31081 = new C31081();
        OFF = c31081;
        C31122 c31122 = new C31122();
        KEYGUARD = c31122;
        C31133 c31133 = new C31133();
        AUTH_SCRIMMED_SHADE = c31133;
        C31144 c31144 = new C31144();
        AUTH_SCRIMMED = c31144;
        C31155 c31155 = new C31155();
        BOUNCER = c31155;
        C31166 c31166 = new C31166();
        BOUNCER_SCRIMMED = c31166;
        C31177 c31177 = new C31177();
        SHADE_LOCKED = c31177;
        C31188 c31188 = new C31188();
        BRIGHTNESS_MIRROR = c31188;
        C31199 c31199 = new C31199();
        AOD = c31199;
        C310910 c310910 = new C310910();
        PULSING = c310910;
        C311011 c311011 = new C311011();
        UNLOCKED = c311011;
        C311112 c311112 = new C311112();
        DREAMING = c311112;
        $VALUES = new ScrimState[]{scrimState, c31081, c31122, c31133, c31144, c31155, c31166, c31177, c31188, c31199, c310910, c311011, c311112};
        TAG = "ScrimState";
    }

    public /* synthetic */ ScrimState(String str, int i, int i2) {
        this(str, i);
    }

    public static float getAlpha() {
        String str = TAG;
        String str2 = Build.TYPE;
        float f = 0.6f;
        if (!"eng".equals(str2) && !"userdebug".equals(str2)) {
            return 0.6f;
        }
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        settingsHelper.getClass();
        String stringValue = LsRune.AOD_FULLSCREEN ? settingsHelper.mItemLists.get("aod_light_reveal_alpha").getStringValue() : null;
        if (stringValue == null) {
            return 0.6f;
        }
        try {
            f = Float.parseFloat(stringValue);
            Log.d(str, "alpha:" + f);
            return f;
        } catch (NumberFormatException e) {
            Log.e(str, "cannot convert alpha to float: " + e);
            return f;
        }
    }

    public static ScrimState valueOf(String str) {
        return (ScrimState) Enum.valueOf(ScrimState.class, str);
    }

    public static ScrimState[] values() {
        return (ScrimState[]) $VALUES.clone();
    }

    public float getMaxLightRevealScrimAlpha() {
        if (LsRune.AOD_FULLSCREEN && this.mAODAmbientWallpaperHelper.isAODFullScreenMode()) {
            return getAlpha();
        }
        return 1.0f;
    }

    public boolean isLowPowerState() {
        return this instanceof C31081;
    }

    public void setSurfaceColor(int i) {
        this.mSurfaceColor = i;
    }

    public boolean shouldBlendWithMainColor() {
        return !(this instanceof C311011);
    }

    public final void updateScrimColor(ScrimView scrimView) {
        Trace.traceCounter(4096L, scrimView == this.mScrimInFront ? "front_scrim_alpha" : "back_scrim_alpha", (int) 255.0f);
        String str = scrimView == this.mScrimInFront ? "front_scrim_tint" : "back_scrim_tint";
        int i = EmergencyPhoneWidget.BG_COLOR;
        Trace.traceCounter(4096L, str, Color.alpha(EmergencyPhoneWidget.BG_COLOR));
        scrimView.getClass();
        scrimView.executeOnExecutor(new ScrimView$$ExternalSyntheticLambda4(scrimView, i));
        scrimView.setViewAlpha(1.0f);
    }

    private ScrimState(String str, int i) {
        this.mBlankScreen = false;
        this.mAnimationDuration = 220L;
        this.mFrontTint = 0;
        this.mBehindTint = 0;
        this.mNotifTint = 0;
        this.mSurfaceColor = 0;
        this.mAnimateChange = true;
    }

    public void prepare(ScrimState scrimState) {
    }
}
