package com.android.server.display;

import android.os.Handler;
import android.os.Trace;
import android.util.FloatProperty;
import android.view.Choreographer;

import com.android.server.display.utils.DebugUtils;
import com.android.server.power.Slog;

import java.util.concurrent.Executor;

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

    public DisplayPowerState(
            DisplayBlanker displayBlanker, ColorFade colorFade, int i, int i2, Executor executor) {
        final int i3 = 0;
        this.mScreenUpdateRunnable =
                new Runnable(this) { // from class: com.android.server.display.DisplayPowerState.4
                    public final /* synthetic */ DisplayPowerState this$0;

                    {
                        this.this$0 = this;
                    }

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
