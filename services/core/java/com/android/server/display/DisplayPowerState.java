package com.android.server.display;

import android.os.Handler;
import android.os.Trace;
import android.util.FloatProperty;
import android.view.Choreographer;

import com.android.server.display.utils.DebugUtils;
import com.android.server.power.Slog;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayPowerState {
    public static final AnonymousClass1 COLOR_FADE_LEVEL;
    public static final AnonymousClass1 SCREEN_BRIGHTNESS_FLOAT;
    public static final AnonymousClass1 SCREEN_SDR_BRIGHTNESS_FLOAT;
    public final Executor mAsyncDestroyExecutor;
    public final DisplayBlanker mBlanker;
    public Runnable mCleanListener;
    public final ColorFade mColorFade;
    public boolean mColorFadeDrawPending;
    final Runnable mColorFadeDrawRunnable;
    public float mColorFadeLevel;
    public boolean mColorFadePrepared;
    public boolean mColorFadeReady;
    public final int mDisplayId;
    public final PhotonicModulator mPhotonicModulator;
    public float mScreenBrightness;
    public boolean mScreenReady;
    public int mScreenState;
    public boolean mScreenUpdatePending;
    public final AnonymousClass4 mScreenUpdateRunnable;
    public float mSdrScreenBrightness;
    public volatile boolean mStopped;
    public static final boolean DEBUG = DebugUtils.isDebuggable("DisplayPowerState");
    public static final String COUNTER_COLOR_FADE = "ColorFadeLevel";
    public final Handler mHandler = new Handler(true);
    public final Choreographer mChoreographer = Choreographer.getInstance();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhotonicModulator extends Thread {
        public float mActualBacklight;
        public float mActualSdrBacklight;
        public int mActualState;
        public boolean mBacklightChangeInProgress;
        public final Object mLock;
        public float mPendingBacklight;
        public float mPendingSdrBacklight;
        public int mPendingState;
        public boolean mStateChangeInProgress;

        public PhotonicModulator() {
            super("PhotonicModulator");
            this.mLock = new Object();
            this.mPendingState = 0;
            this.mPendingBacklight = Float.NaN;
            this.mPendingSdrBacklight = Float.NaN;
            this.mActualState = 0;
            this.mActualBacklight = Float.NaN;
            this.mActualSdrBacklight = Float.NaN;
        }

        /* JADX WARN: Can't wrap try/catch for region: R(20:4|5|(1:7)(1:122)|8|(14:13|(1:15)|(1:17)|(1:120)(1:21)|(2:25|(7:29|30|31|32|33|34|35)(8:45|46|47|48|(1:50)|51|9b|96))|119|(1:28)(1:118)|29|30|31|32|33|34|35)|121|(0)|(0)|(1:19)|120|(9:25|(0)(0)|29|30|31|32|33|34|35)|119|(0)(0)|29|30|31|32|33|34|35) */
        /* JADX WARN: Code restructure failed: missing block: B:112:0x00fe, code lost:

           r0 = move-exception;
        */
        /* JADX WARN: Code restructure failed: missing block: B:115:0x014c, code lost:

           throw r0;
        */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x015e, code lost:

           if (r16.this$0.mStopped != false) goto L122;
        */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0161, code lost:

           return;
        */
        /* JADX WARN: Removed duplicated region for block: B:118:0x0052 A[ADDED_TO_REGION, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0029 A[Catch: all -> 0x0023, TryCatch #0 {all -> 0x0023, blocks: (B:5:0x0005, B:8:0x0010, B:10:0x001a, B:15:0x0029, B:17:0x0039, B:19:0x003d, B:46:0x0055, B:47:0x005b, B:30:0x0150, B:32:0x0154, B:33:0x0162, B:38:0x015a, B:41:0x0160), top: B:4:0x0005, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0039 A[Catch: all -> 0x0023, TryCatch #0 {all -> 0x0023, blocks: (B:5:0x0005, B:8:0x0010, B:10:0x001a, B:15:0x0029, B:17:0x0039, B:19:0x003d, B:46:0x0055, B:47:0x005b, B:30:0x0150, B:32:0x0154, B:33:0x0162, B:38:0x015a, B:41:0x0160), top: B:4:0x0005, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0050 A[ADDED_TO_REGION] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 359
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.DisplayPowerState.PhotonicModulator.run():void");
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.display.DisplayPowerState$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.display.DisplayPowerState$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.display.DisplayPowerState$1] */
    static {
        final int i = 0;
        COLOR_FADE_LEVEL =
                new FloatProperty(
                        "electronBeamLevel") { // from class:
                                               // com.android.server.display.DisplayPowerState.1
                    @Override // android.util.Property
                    public final Float get(Object obj) {
                        switch (i) {
                            case 0:
                                return Float.valueOf(((DisplayPowerState) obj).mColorFadeLevel);
                            case 1:
                                return Float.valueOf(((DisplayPowerState) obj).mScreenBrightness);
                            default:
                                return Float.valueOf(
                                        ((DisplayPowerState) obj).mSdrScreenBrightness);
                        }
                    }

                    @Override // android.util.FloatProperty
                    public final void setValue(Object obj, float f) {
                        switch (i) {
                            case 0:
                                ((DisplayPowerState) obj).setColorFadeLevel(f);
                                break;
                            case 1:
                                DisplayPowerState displayPowerState = (DisplayPowerState) obj;
                                if (displayPowerState.mScreenBrightness != f) {
                                    if (DisplayPowerState.DEBUG) {
                                        Slog.d(
                                                "DisplayPowerState",
                                                "setScreenBrightness: brightness=" + f);
                                    }
                                    displayPowerState.mScreenBrightness = f;
                                    if (displayPowerState.mScreenState != 1) {
                                        displayPowerState.mScreenReady = false;
                                        displayPowerState.scheduleScreenUpdate();
                                        break;
                                    }
                                }
                                break;
                            default:
                                DisplayPowerState displayPowerState2 = (DisplayPowerState) obj;
                                if (displayPowerState2.mSdrScreenBrightness != f) {
                                    if (DisplayPowerState.DEBUG) {
                                        Slog.d(
                                                "DisplayPowerState",
                                                "setSdrScreenBrightness: brightness=" + f);
                                    }
                                    displayPowerState2.mSdrScreenBrightness = f;
                                    if (displayPowerState2.mScreenState != 1) {
                                        displayPowerState2.mScreenReady = false;
                                        displayPowerState2.scheduleScreenUpdate();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        SCREEN_BRIGHTNESS_FLOAT =
                new FloatProperty(
                        "screenBrightnessFloat") { // from class:
                                                   // com.android.server.display.DisplayPowerState.1
                    @Override // android.util.Property
                    public final Float get(Object obj) {
                        switch (i2) {
                            case 0:
                                return Float.valueOf(((DisplayPowerState) obj).mColorFadeLevel);
                            case 1:
                                return Float.valueOf(((DisplayPowerState) obj).mScreenBrightness);
                            default:
                                return Float.valueOf(
                                        ((DisplayPowerState) obj).mSdrScreenBrightness);
                        }
                    }

                    @Override // android.util.FloatProperty
                    public final void setValue(Object obj, float f) {
                        switch (i2) {
                            case 0:
                                ((DisplayPowerState) obj).setColorFadeLevel(f);
                                break;
                            case 1:
                                DisplayPowerState displayPowerState = (DisplayPowerState) obj;
                                if (displayPowerState.mScreenBrightness != f) {
                                    if (DisplayPowerState.DEBUG) {
                                        Slog.d(
                                                "DisplayPowerState",
                                                "setScreenBrightness: brightness=" + f);
                                    }
                                    displayPowerState.mScreenBrightness = f;
                                    if (displayPowerState.mScreenState != 1) {
                                        displayPowerState.mScreenReady = false;
                                        displayPowerState.scheduleScreenUpdate();
                                        break;
                                    }
                                }
                                break;
                            default:
                                DisplayPowerState displayPowerState2 = (DisplayPowerState) obj;
                                if (displayPowerState2.mSdrScreenBrightness != f) {
                                    if (DisplayPowerState.DEBUG) {
                                        Slog.d(
                                                "DisplayPowerState",
                                                "setSdrScreenBrightness: brightness=" + f);
                                    }
                                    displayPowerState2.mSdrScreenBrightness = f;
                                    if (displayPowerState2.mScreenState != 1) {
                                        displayPowerState2.mScreenReady = false;
                                        displayPowerState2.scheduleScreenUpdate();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i3 = 2;
        SCREEN_SDR_BRIGHTNESS_FLOAT =
                new FloatProperty(
                        "sdrScreenBrightnessFloat") { // from class:
                                                      // com.android.server.display.DisplayPowerState.1
                    @Override // android.util.Property
                    public final Float get(Object obj) {
                        switch (i3) {
                            case 0:
                                return Float.valueOf(((DisplayPowerState) obj).mColorFadeLevel);
                            case 1:
                                return Float.valueOf(((DisplayPowerState) obj).mScreenBrightness);
                            default:
                                return Float.valueOf(
                                        ((DisplayPowerState) obj).mSdrScreenBrightness);
                        }
                    }

                    @Override // android.util.FloatProperty
                    public final void setValue(Object obj, float f) {
                        switch (i3) {
                            case 0:
                                ((DisplayPowerState) obj).setColorFadeLevel(f);
                                break;
                            case 1:
                                DisplayPowerState displayPowerState = (DisplayPowerState) obj;
                                if (displayPowerState.mScreenBrightness != f) {
                                    if (DisplayPowerState.DEBUG) {
                                        Slog.d(
                                                "DisplayPowerState",
                                                "setScreenBrightness: brightness=" + f);
                                    }
                                    displayPowerState.mScreenBrightness = f;
                                    if (displayPowerState.mScreenState != 1) {
                                        displayPowerState.mScreenReady = false;
                                        displayPowerState.scheduleScreenUpdate();
                                        break;
                                    }
                                }
                                break;
                            default:
                                DisplayPowerState displayPowerState2 = (DisplayPowerState) obj;
                                if (displayPowerState2.mSdrScreenBrightness != f) {
                                    if (DisplayPowerState.DEBUG) {
                                        Slog.d(
                                                "DisplayPowerState",
                                                "setSdrScreenBrightness: brightness=" + f);
                                    }
                                    displayPowerState2.mSdrScreenBrightness = f;
                                    if (displayPowerState2.mScreenState != 1) {
                                        displayPowerState2.mScreenReady = false;
                                        displayPowerState2.scheduleScreenUpdate();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.display.DisplayPowerState$4] */
    public DisplayPowerState(
            DisplayBlanker displayBlanker, ColorFade colorFade, int i, int i2, Executor executor) {
        final int i3 = 0;
        this.mScreenUpdateRunnable =
                new Runnable(this) { // from class: com.android.server.display.DisplayPowerState.4
                    public final /* synthetic */ DisplayPowerState this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:60:0x0086 A[Catch: all -> 0x007a, TryCatch #0 {all -> 0x007a, blocks: (B:27:0x0064, B:30:0x006b, B:32:0x0071, B:38:0x00cf, B:39:0x00d2, B:58:0x0082, B:60:0x0086, B:61:0x00a3, B:63:0x00ad, B:71:0x00bd, B:73:0x00c1, B:75:0x00c6, B:77:0x00ca), top: B:26:0x0064 }] */
                    /* JADX WARN: Removed duplicated region for block: B:68:0x00b7 A[ADDED_TO_REGION] */
                    /* JADX WARN: Removed duplicated region for block: B:73:0x00c1 A[Catch: all -> 0x007a, TryCatch #0 {all -> 0x007a, blocks: (B:27:0x0064, B:30:0x006b, B:32:0x0071, B:38:0x00cf, B:39:0x00d2, B:58:0x0082, B:60:0x0086, B:61:0x00a3, B:63:0x00ad, B:71:0x00bd, B:73:0x00c1, B:75:0x00c6, B:77:0x00ca), top: B:26:0x0064 }] */
                    /* JADX WARN: Removed duplicated region for block: B:77:0x00ca A[Catch: all -> 0x007a, TryCatch #0 {all -> 0x007a, blocks: (B:27:0x0064, B:30:0x006b, B:32:0x0071, B:38:0x00cf, B:39:0x00d2, B:58:0x0082, B:60:0x0086, B:61:0x00a3, B:63:0x00ad, B:71:0x00bd, B:73:0x00c1, B:75:0x00c6, B:77:0x00ca), top: B:26:0x0064 }] */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            Method dump skipped, instructions count: 264
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.display.DisplayPowerState.AnonymousClass4.run():void");
                    }
                };
        final int i4 = 1;
        this.mColorFadeDrawRunnable =
                new Runnable(this) { // from class: com.android.server.display.DisplayPowerState.4
                    public final /* synthetic */ DisplayPowerState this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        /*
                            Method dump skipped, instructions count: 264
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.display.DisplayPowerState.AnonymousClass4.run():void");
                    }
                };
        this.mBlanker = displayBlanker;
        this.mColorFade = colorFade;
        PhotonicModulator photonicModulator = new PhotonicModulator();
        this.mPhotonicModulator = photonicModulator;
        photonicModulator.start();
        this.mDisplayId = i;
        this.mAsyncDestroyExecutor = executor;
        this.mScreenState = i2;
        float f = i2 != 1 ? 1.0f : -1.0f;
        this.mScreenBrightness = f;
        this.mSdrScreenBrightness = f;
        scheduleScreenUpdate();
        this.mColorFadePrepared = false;
        this.mColorFadeLevel = 1.0f;
        this.mColorFadeReady = true;
    }

    public final void dismissColorFade() {
        Trace.traceCounter(131072L, COUNTER_COLOR_FADE, 100);
        ColorFade colorFade = this.mColorFade;
        if (colorFade != null) {
            colorFade.dismiss();
        }
        this.mColorFadePrepared = false;
        this.mColorFadeReady = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x03d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean prepareColorFade(android.content.Context r35, int r36) {
        /*
            Method dump skipped, instructions count: 1064
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayPowerState.prepareColorFade(android.content.Context,"
                    + " int):boolean");
    }

    public final void scheduleScreenUpdate() {
        if (this.mScreenUpdatePending) {
            return;
        }
        this.mScreenUpdatePending = true;
        Handler handler = this.mHandler;
        AnonymousClass4 anonymousClass4 = this.mScreenUpdateRunnable;
        handler.removeCallbacks(anonymousClass4);
        handler.post(anonymousClass4);
    }

    public final void setColorFadeLevel(float f) {
        if (this.mColorFadeLevel != f) {
            if (DEBUG) {
                Slog.d("DisplayPowerState", "setColorFadeLevel: level=" + f);
            }
            double d = this.mColorFadeLevel;
            int i = this.mDisplayId;
            if (d == 0.0d) {
                Slog.wk("DisplayPowerState", "ColorFade exit displayId=" + i);
            } else if (f == 0.0d) {
                Slog.wk("DisplayPowerState", "ColorFade entry displayId=" + i);
            }
            this.mColorFadeLevel = f;
            if (this.mScreenState != 1) {
                this.mScreenReady = false;
                scheduleScreenUpdate();
            }
            if (this.mColorFadePrepared) {
                this.mColorFadeReady = false;
                if (this.mColorFadeDrawPending) {
                    return;
                }
                this.mColorFadeDrawPending = true;
                this.mChoreographer.postCallback(3, this.mColorFadeDrawRunnable, null);
            }
        }
    }

    public final boolean waitUntilClean(Runnable runnable) {
        if (this.mScreenReady && this.mColorFadeReady) {
            this.mCleanListener = null;
            return true;
        }
        this.mCleanListener = runnable;
        return false;
    }
}
