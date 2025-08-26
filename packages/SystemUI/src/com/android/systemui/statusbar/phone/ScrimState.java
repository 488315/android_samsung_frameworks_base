package com.android.systemui.statusbar.phone;

import android.graphics.Color;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.dock.DockManager;
import com.android.systemui.scrim.ScrimView;
import java.util.function.Supplier;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public class ScrimState {
    public static final /* synthetic */ ScrimState[] $VALUES;
    public static final ScrimState AOD;
    public static final ScrimState BOUNCER;
    public static final ScrimState BOUNCER_SCRIMMED;
    public static final ScrimState BRIGHTNESS_MIRROR;
    public static final ScrimState DREAMING;
    public static final ScrimState GLANCEABLE_HUB;
    public static final ScrimState GLANCEABLE_HUB_OVER_DREAM;
    public static final ScrimState KEYGUARD;
    public static final ScrimState OFF;
    public static final ScrimState PULSING;
    public static final ScrimState SHADE_LOCKED;
    public static final ScrimState UNINITIALIZED;
    public static final ScrimState UNLOCKED;
    AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    boolean mAnimateChange;
    long mAnimationDuration;
    float mAodFrontScrimAlpha;
    int mBackgroundColor;
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
    protected Supplier<Boolean> mIsBlurSupported;
    boolean mKeyguardFadingAway;
    long mKeyguardFadingAwayDuration;
    boolean mLaunchingAffordanceWithPreview;
    float mNotifAlpha;
    protected float mNotifBlurRadius;
    int mNotifTint;
    boolean mOccludeAnimationPlaying;
    ScrimView mScrimBehind;
    float mScrimBehindAlphaKeyguard;
    ScrimView mScrimInFront;
    int mSurfaceColor;
    boolean mWakeLockScreenSensorActive;

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$1, reason: invalid class name */
    enum AnonymousClass1 extends ScrimState {
        public /* synthetic */ AnonymousClass1() {
            this("OFF", 1);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            int i = this.mBackgroundColor;
            this.mFrontTint = i;
            this.mBehindTint = i;
            this.mFrontAlpha = 1.0f;
            this.mBehindAlpha = 1.0f;
            if (scrimState == ScrimState.AOD) {
                this.mAnimateChange = false;
            } else {
                this.mAnimationDuration = 1000L;
            }
        }

        private AnonymousClass1(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$10, reason: invalid class name */
    enum AnonymousClass10 extends ScrimState {
        public /* synthetic */ AnonymousClass10() {
            this("DREAMING", 10);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mFrontTint = 0;
            int i = this.mBackgroundColor;
            this.mBehindTint = i;
            boolean z = this.mClipQsScrim;
            this.mNotifTint = z ? i : 0;
            this.mFrontAlpha = 0.0f;
            this.mBehindAlpha = z ? 1.0f : 0.0f;
            this.mNotifAlpha = 0.0f;
            this.mBlankScreen = false;
            if (z) {
                updateScrimColor(this.mScrimBehind, i);
            }
        }

        private AnonymousClass10(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$11, reason: invalid class name */
    enum AnonymousClass11 extends ScrimState {
        public /* synthetic */ AnonymousClass11() {
            this("GLANCEABLE_HUB", 11);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBehindAlpha = 0.0f;
            this.mNotifAlpha = 0.0f;
            this.mFrontAlpha = 0.0f;
            this.mFrontTint = 0;
            int i = this.mBackgroundColor;
            this.mBehindTint = i;
            this.mNotifTint = this.mClipQsScrim ? i : 0;
        }

        private AnonymousClass11(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$12, reason: invalid class name */
    enum AnonymousClass12 extends ScrimState {
        public /* synthetic */ AnonymousClass12() {
            this("GLANCEABLE_HUB_OVER_DREAM", 12);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBehindAlpha = 0.0f;
            this.mNotifAlpha = 0.0f;
            this.mFrontAlpha = 0.0f;
            this.mFrontTint = 0;
            int i = this.mBackgroundColor;
            this.mBehindTint = i;
            this.mNotifTint = this.mClipQsScrim ? i : 0;
        }

        private AnonymousClass12(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$2, reason: invalid class name */
    enum AnonymousClass2 extends ScrimState {
        public /* synthetic */ AnonymousClass2() {
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
            int i = this.mBackgroundColor;
            this.mFrontTint = i;
            this.mBehindTint = i;
            boolean z = this.mClipQsScrim;
            this.mNotifTint = z ? i : 0;
            this.mFrontAlpha = 0.0f;
            this.mBehindAlpha = z ? 1.0f : this.mScrimBehindAlphaKeyguard;
            this.mNotifAlpha = z ? this.mScrimBehindAlphaKeyguard : 0.0f;
            if (z) {
                updateScrimColor(this.mScrimBehind, i);
            }
        }

        private AnonymousClass2(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$3, reason: invalid class name */
    enum AnonymousClass3 extends ScrimState {
        public /* synthetic */ AnonymousClass3() {
            this("BOUNCER", 3);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            boolean z = this.mClipQsScrim;
            this.mBehindAlpha = z ? 1.0f : this.mDefaultScrimAlpha;
            this.mBehindTint = z ? this.mBackgroundColor : this.mSurfaceColor;
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

        private AnonymousClass3(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$4, reason: invalid class name */
    enum AnonymousClass4 extends ScrimState {
        public /* synthetic */ AnonymousClass4() {
            this("BOUNCER_SCRIMMED", 4);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBehindAlpha = 0.0f;
            this.mFrontAlpha = this.mDefaultScrimAlpha;
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final boolean shouldBlendWithMainColor() {
            return true;
        }

        private AnonymousClass4(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$5, reason: invalid class name */
    enum AnonymousClass5 extends ScrimState {
        public /* synthetic */ AnonymousClass5() {
            this("SHADE_LOCKED", 5);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            boolean z = this.mClipQsScrim;
            this.mBehindAlpha = z ? 1.0f : this.mDefaultScrimAlpha;
            this.mNotifAlpha = 1.0f;
            this.mFrontAlpha = 0.0f;
            this.mBehindTint = z ? 0 : this.mBackgroundColor;
            if (z) {
                updateScrimColor(this.mScrimBehind, this.mBackgroundColor);
            }
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void setDefaultScrimAlpha() {
            this.mDefaultScrimAlpha = 1.0f;
        }

        private AnonymousClass5(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$6, reason: invalid class name */
    enum AnonymousClass6 extends ScrimState {
        public /* synthetic */ AnonymousClass6() {
            this("BRIGHTNESS_MIRROR", 6);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mBehindAlpha = 0.0f;
            this.mFrontAlpha = 0.0f;
        }

        private AnonymousClass6(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$7, reason: invalid class name */
    enum AnonymousClass7 extends ScrimState {
        public /* synthetic */ AnonymousClass7() {
            this("AOD", 7);
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
            int i = this.mBackgroundColor;
            this.mFrontTint = i;
            this.mFrontAlpha = (alwaysOn || z) ? this.mAodFrontScrimAlpha : 1.0f;
            this.mBehindTint = i;
            this.mBehindAlpha = 0.0f;
            this.mAnimationDuration = 1000L;
            boolean z2 = false;
            if (scrimState == ScrimState.OFF) {
                this.mAnimateChange = false;
                return;
            }
            DozeParameters dozeParameters = this.mDozeParameters;
            if (dozeParameters.mControlScreenOffAnimation && !dozeParameters.shouldShowLightRevealScrim()) {
                z2 = true;
            }
            this.mAnimateChange = z2;
        }

        private AnonymousClass7(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$8, reason: invalid class name */
    enum AnonymousClass8 extends ScrimState {
        public /* synthetic */ AnonymousClass8() {
            this("PULSING", 8);
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final void prepare(ScrimState scrimState) {
            this.mFrontAlpha = this.mAodFrontScrimAlpha;
            int i = this.mBackgroundColor;
            this.mBehindTint = i;
            this.mFrontTint = i;
            this.mBlankScreen = this.mDisplayRequiresBlanking;
            this.mAnimationDuration = this.mWakeLockScreenSensorActive ? 1000L : 220L;
        }

        private AnonymousClass8(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ScrimState$9, reason: invalid class name */
    enum AnonymousClass9 extends ScrimState {
        public /* synthetic */ AnonymousClass9() {
            this("UNLOCKED", 9);
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
            int i = this.mBackgroundColor;
            this.mBehindTint = i;
            this.mBlankScreen = false;
            if (this.mDisplayRequiresBlanking && scrimState == scrimState2) {
                updateScrimColor(this.mScrimInFront, i);
                updateScrimColor(this.mScrimBehind, this.mBackgroundColor);
                int i2 = this.mBackgroundColor;
                this.mFrontTint = i2;
                this.mBehindTint = i2;
                this.mBlankScreen = true;
            }
            if (this.mClipQsScrim) {
                updateScrimColor(this.mScrimBehind, this.mBackgroundColor);
            }
        }

        @Override // com.android.systemui.statusbar.phone.ScrimState
        public final boolean shouldBlendWithMainColor() {
            return false;
        }

        private AnonymousClass9(String str, int i) {
            super(str, i, 0);
        }
    }

    static {
        ScrimState scrimState = new ScrimState("UNINITIALIZED", 0);
        UNINITIALIZED = scrimState;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        OFF = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        KEYGUARD = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        BOUNCER = anonymousClass3;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        BOUNCER_SCRIMMED = anonymousClass4;
        AnonymousClass5 anonymousClass5 = new AnonymousClass5();
        SHADE_LOCKED = anonymousClass5;
        AnonymousClass6 anonymousClass6 = new AnonymousClass6();
        BRIGHTNESS_MIRROR = anonymousClass6;
        AnonymousClass7 anonymousClass7 = new AnonymousClass7();
        AOD = anonymousClass7;
        AnonymousClass8 anonymousClass8 = new AnonymousClass8();
        PULSING = anonymousClass8;
        AnonymousClass9 anonymousClass9 = new AnonymousClass9();
        UNLOCKED = anonymousClass9;
        AnonymousClass10 anonymousClass10 = new AnonymousClass10();
        DREAMING = anonymousClass10;
        AnonymousClass11 anonymousClass11 = new AnonymousClass11();
        GLANCEABLE_HUB = anonymousClass11;
        AnonymousClass12 anonymousClass12 = new AnonymousClass12();
        GLANCEABLE_HUB_OVER_DREAM = anonymousClass12;
        $VALUES = new ScrimState[]{scrimState, anonymousClass1, anonymousClass2, anonymousClass3, anonymousClass4, anonymousClass5, anonymousClass6, anonymousClass7, anonymousClass8, anonymousClass9, anonymousClass10, anonymousClass11, anonymousClass12};
    }

    public /* synthetic */ ScrimState(String str, int i, int i2) {
        this(str, i);
    }

    public static ScrimState valueOf(String str) {
        return (ScrimState) Enum.valueOf(ScrimState.class, str);
    }

    public static ScrimState[] values() {
        return (ScrimState[]) $VALUES.clone();
    }

    public boolean isLowPowerState() {
        return this instanceof AnonymousClass1;
    }

    public void setDefaultScrimAlpha() {
        this.mDefaultScrimAlpha = 1.0f;
    }

    public void setSurfaceColor(int i) {
        this.mSurfaceColor = i;
    }

    public boolean shouldBlendWithMainColor() {
        return !(this instanceof AnonymousClass7);
    }

    public final void updateScrimColor(ScrimView scrimView, int i) {
        TrackTracer.instantForGroup((int) 255.0f, "scrim", scrimView == this.mScrimInFront ? "front_scrim_alpha" : "back_scrim_alpha");
        TrackTracer.instantForGroup(Color.alpha(i), "scrim", scrimView == this.mScrimInFront ? "front_scrim_tint" : "back_scrim_tint");
        scrimView.setTint(i);
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
        this.mNotifBlurRadius = 0.0f;
    }

    public void prepare(ScrimState scrimState) {
    }
}
