package com.android.server.display.brightness.clamper;

import android.os.Handler;
import android.os.IBinder;
import android.view.SurfaceControlHdrLayerInfoListener;

import com.android.server.display.config.HdrBrightnessData;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HdrClamper {
    public final BrightnessClamperController.ClamperChangeListener mClamperChangeListener;
    public final Handler mHandler;
    public final HdrLayerInfoListener mHdrListener;
    public HdrBrightnessData mHdrBrightnessData = null;
    public IBinder mRegisteredDisplayToken = null;
    public float mAmbientLux = Float.MAX_VALUE;
    public boolean mHdrVisible = false;
    public float mMaxBrightness = 1.0f;
    public float mDesiredMaxBrightness = 1.0f;
    public float mTransitionRate = -1.0f;
    public float mDesiredTransitionRate = -1.0f;
    public boolean mAutoBrightnessEnabled = false;
    public boolean mUseSlowTransition = false;
    public final HdrClamper$$ExternalSyntheticLambda0 mDebouncer =
            new Runnable() { // from class:
                             // com.android.server.display.brightness.clamper.HdrClamper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HdrClamper hdrClamper = HdrClamper.this;
                    hdrClamper.mTransitionRate = hdrClamper.mDesiredTransitionRate;
                    hdrClamper.mMaxBrightness = hdrClamper.mDesiredMaxBrightness;
                    hdrClamper.mUseSlowTransition = true;
                    hdrClamper.mClamperChangeListener.onChanged();
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HdrLayerInfoListener extends SurfaceControlHdrLayerInfoListener {
        public final Handler mHandler;
        public final HdrClamper$$ExternalSyntheticLambda1 mHdrListener;
        public float mHdrMinPixels = Float.MAX_VALUE;

        public HdrLayerInfoListener(
                HdrClamper$$ExternalSyntheticLambda1 hdrClamper$$ExternalSyntheticLambda1,
                Handler handler) {
            this.mHdrListener = hdrClamper$$ExternalSyntheticLambda1;
            this.mHandler = handler;
        }

        public final void onHdrInfoChanged(
                IBinder iBinder, final int i, final int i2, final int i3, int i4, float f) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.display.brightness.clamper.HdrClamper$HdrLayerInfoListener$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            HdrClamper.HdrLayerInfoListener hdrLayerInfoListener =
                                    HdrClamper.HdrLayerInfoListener.this;
                            int i5 = i;
                            int i6 = i2;
                            int i7 = i3;
                            HdrClamper$$ExternalSyntheticLambda1
                                    hdrClamper$$ExternalSyntheticLambda1 =
                                            hdrLayerInfoListener.mHdrListener;
                            boolean z =
                                    i5 > 0
                                            && ((float) (i6 * i7))
                                                    >= hdrLayerInfoListener.mHdrMinPixels;
                            HdrClamper hdrClamper = hdrClamper$$ExternalSyntheticLambda1.f$0;
                            hdrClamper.mHdrVisible = z;
                            hdrClamper.recalculateBrightnessCap(
                                    hdrClamper.mHdrBrightnessData, hdrClamper.mAmbientLux, z);
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.server.display.brightness.clamper.HdrClamper$$ExternalSyntheticLambda0] */
    public HdrClamper(
            BrightnessClamperController.ClamperChangeListener clamperChangeListener,
            Handler handler,
            Injector injector) {
        this.mClamperChangeListener = clamperChangeListener;
        this.mHandler = handler;
        HdrClamper$$ExternalSyntheticLambda1 hdrClamper$$ExternalSyntheticLambda1 =
                new HdrClamper$$ExternalSyntheticLambda1(this);
        injector.getClass();
        this.mHdrListener = new HdrLayerInfoListener(hdrClamper$$ExternalSyntheticLambda1, handler);
    }

    public float getMaxBrightness() {
        return this.mMaxBrightness;
    }

    public final void recalculateBrightnessCap(
            HdrBrightnessData hdrBrightnessData, float f, boolean z) {
        long j;
        HdrClamper$$ExternalSyntheticLambda0 hdrClamper$$ExternalSyntheticLambda0 = this.mDebouncer;
        float f2 = 1.0f;
        Handler handler = this.mHandler;
        if (hdrBrightnessData == null || !z || !this.mAutoBrightnessEnabled) {
            if (this.mMaxBrightness == 1.0f
                    && this.mDesiredMaxBrightness == 1.0f
                    && this.mTransitionRate == -1.0f
                    && this.mDesiredTransitionRate == -1.0f) {
                return;
            }
            handler.removeCallbacks(hdrClamper$$ExternalSyntheticLambda0);
            this.mMaxBrightness = 1.0f;
            this.mDesiredMaxBrightness = 1.0f;
            this.mDesiredTransitionRate = -1.0f;
            this.mTransitionRate = -1.0f;
            this.mUseSlowTransition = false;
            this.mClamperChangeListener.onChanged();
            return;
        }
        float f3 = Float.MAX_VALUE;
        for (Map.Entry entry : hdrBrightnessData.mMaxBrightnessLimits.entrySet()) {
            float floatValue = ((Float) entry.getKey()).floatValue();
            if (floatValue > f && floatValue < f3) {
                f2 = ((Float) entry.getValue()).floatValue();
                f3 = floatValue;
            }
        }
        float f4 = this.mMaxBrightness;
        if (f4 == f2) {
            this.mDesiredMaxBrightness = f4;
            this.mDesiredTransitionRate = -1.0f;
            this.mTransitionRate = -1.0f;
            handler.removeCallbacks(hdrClamper$$ExternalSyntheticLambda0);
            return;
        }
        if (this.mDesiredMaxBrightness != f2) {
            this.mDesiredMaxBrightness = f2;
            if (f2 > f4) {
                HdrBrightnessData hdrBrightnessData2 = this.mHdrBrightnessData;
                j = hdrBrightnessData2.mBrightnessIncreaseDebounceMillis;
                this.mDesiredTransitionRate = hdrBrightnessData2.mScreenBrightnessRampIncrease;
            } else {
                HdrBrightnessData hdrBrightnessData3 = this.mHdrBrightnessData;
                j = hdrBrightnessData3.mBrightnessDecreaseDebounceMillis;
                this.mDesiredTransitionRate = hdrBrightnessData3.mScreenBrightnessRampDecrease;
            }
            handler.removeCallbacks(hdrClamper$$ExternalSyntheticLambda0);
            handler.postDelayed(hdrClamper$$ExternalSyntheticLambda0, j);
        }
    }
}
