package com.samsung.android.biometrics.app.setting.fingerprint.vi;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardSensorWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VisualEffectContainer extends FrameLayout {
    static final String ASSET_NAME_OPTICAL_GREEN =
            "indisplay_fingerprint_touch_effect_green_circle.json";
    static final String ASSET_NAME_OPTICAL_WHITE =
            "indisplay_fingerprint_touch_effect_white_circle.json";
    static final String ASSET_NAME_ULTRASONIC = "indisplay_fingerprint_touch_effect_ripple.json";
    public long mAnimationDuration;
    public Callback mCallback;
    public final Context mContext;
    LottieAnimationView mEffectAnimationView;
    public final List mEffectViews;
    public final Handler mHandler;
    public boolean mIsReadTouchMap;
    public Thread mReadTouchMapThread;
    public FingerprintSensorInfo mSensorInfo;
    public final VisualEffectContainer$$ExternalSyntheticLambda0 mStartCallback;
    public final VisualEffectContainer$$ExternalSyntheticLambda0 mStopCallback;
    public final SemVisualEffectTouchMap mTouchMap;
    public long mTouchMapStartTime;

    public interface Callback {}

    public final class SemVisualEffectTouchMap {
        public int[][] mCheckArray;
        public Path mEffectPath;
        public final TouchMapReader mTouchMapReader;
        public int mWidth = 0;
        public int mHeight = 0;
        public final int[] EDGE_X = {0, 1, 1, 0};
        public final int[] EDGE_Y = {0, 0, 1, 1};
        public final int[] SEARCH_X = {0, 1, 0, -1};
        public final int[] SEARCH_Y = {-1, 0, 1, 0};

        public SemVisualEffectTouchMap(Context context) {
            this.mTouchMapReader = new TouchMapReader(context);
        }

        public static Path calculateClipOutPath(ArrayList arrayList) {
            Path path = new Path();
            if (arrayList.size() >= 3) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    float[] fArr = (float[]) arrayList.get(i % size);
                    int i2 = i + 1;
                    float[] fArr2 = (float[]) arrayList.get(i2 % size);
                    float[] fArr3 = (float[]) arrayList.get((i + 2) % size);
                    if (i == 0) {
                        path.moveTo((fArr[0] + fArr2[0]) / 2.0f, (fArr[1] + fArr2[1]) / 2.0f);
                    }
                    float f = fArr[0];
                    float f2 = fArr2[0];
                    float f3 = (f + f2) / 2.0f;
                    float f4 = fArr[1];
                    float f5 = fArr2[1];
                    path.cubicTo(
                            f3,
                            (f4 + f5) / 2.0f,
                            f2,
                            f5,
                            (fArr3[0] + f2) / 2.0f,
                            (fArr3[1] + f5) / 2.0f);
                    i = i2;
                }
                path.close();
            }
            return path;
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x00c9, code lost:

           if (r5 < 0) goto L41;
        */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00cc, code lost:

           if (r4 >= r20.length) goto L41;
        */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00d1, code lost:

           if (r5 < r20[0].length) goto L40;
        */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00d4, code lost:

           r3 = 5;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.ArrayList getOuterLine(int[][] r20) {
            /*
                Method dump skipped, instructions count: 461
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer.SemVisualEffectTouchMap.getOuterLine(int[][]):java.util.ArrayList");
        }

        public final void searchNeighborBlock(
                ArrayList arrayList, int[][] iArr, int i, int i2, int i3) {
            int[] iArr2 = this.mCheckArray[i2];
            if (iArr2[i] == 1) {
                return;
            }
            iArr2[i] = 1;
            for (int i4 = 0; i4 < 4; i4++) {
                int i5 = (i3 + i4) % 4;
                int i6 = i2 + this.EDGE_Y[i5];
                int i7 = i + this.EDGE_X[i5];
                int i8 = i + this.SEARCH_X[i5];
                int i9 = i2 + this.SEARCH_Y[i5];
                if (iArr[i2][i] == 1
                        && (i9 < 0
                                || i8 < 0
                                || i9 >= iArr.length
                                || i8 >= iArr[0].length
                                || iArr[i9][i8] == 0)) {
                    if (i9 >= 0 && i8 > 0 && i9 < iArr.length && i8 < iArr[0].length) {
                        this.mCheckArray[i9][i8] = 1;
                    }
                    arrayList.add(new Point(i7, i6));
                } else if (this.mCheckArray[i9][i8] != 1) {
                    searchNeighborBlock(arrayList, iArr, i8, i9, (i5 + 3) % 4);
                }
            }
        }
    }

    public VisualEffectContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchMapStartTime = 0L;
        this.mAnimationDuration = 1000L;
        this.mStartCallback = new VisualEffectContainer$$ExternalSyntheticLambda0(this, 0);
        this.mStopCallback = new VisualEffectContainer$$ExternalSyntheticLambda0(this, 1);
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        this.mTouchMap = new SemVisualEffectTouchMap(context);
        this.mEffectViews = new ArrayList();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchConfigurationChanged(Configuration configuration) {
        super.dispatchConfigurationChanged(configuration);
        updateLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void init(
            com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo r7,
            com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer.Callback
                    r8) {
        /*
            Method dump skipped, instructions count: 397
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer.init(com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo,"
                    + " com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer$Callback):void");
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        FingerprintSensorInfo fingerprintSensorInfo = this.mSensorInfo;
        if (fingerprintSensorInfo != null) {
            int i = fingerprintSensorInfo.mSensorAreaHeight;
            int i2 = fingerprintSensorInfo.mSensorMarginBottom;
            float width = (getWidth() / 2.0f) + fingerprintSensorInfo.mSensorMarginLeft;
            float height = getHeight() - ((i / 2.0f) + i2);
            Iterator it = ((ArrayList) this.mEffectViews).iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                ((View) pair.first).setTranslationX(((int) width) - (r4.getWidth() / 2.0f));
                ((View) pair.first).setTranslationY(((int) height) - (r2.getHeight() / 2.0f));
            }
        }
        if (this.mIsReadTouchMap) {
            SemVisualEffectTouchMap semVisualEffectTouchMap = this.mTouchMap;
            if (canvas == null) {
                semVisualEffectTouchMap.getClass();
                return;
            }
            Path path = semVisualEffectTouchMap.mEffectPath;
            if (path == null || path.isEmpty()) {
                return;
            }
            canvas.clipOutPath(semVisualEffectTouchMap.mEffectPath);
        }
    }

    public final void stop() {
        this.mCallback = null;
        this.mHandler.removeCallbacks(this.mStartCallback);
        this.mHandler.removeCallbacks(this.mStopCallback);
        stopVI();
        removeAllViews();
        ((ArrayList) this.mEffectViews).clear();
    }

    public final void stopVI() {
        UdfpsWindowCallback udfpsWindowCallback;
        Log.i("BSS_VisualEffectContainer", "stop()");
        this.mIsReadTouchMap = false;
        LottieAnimationView lottieAnimationView = this.mEffectAnimationView;
        if (lottieAnimationView != null) {
            lottieAnimationView.setRenderMode(RenderMode.SOFTWARE);
        }
        setVisibility(4);
        this.mIsReadTouchMap = false;
        invalidate();
        Callback callback = this.mCallback;
        if (callback == null
                || (udfpsWindowCallback = ((UdfpsKeyguardSensorWindow) callback).mCallback)
                        == null) {
            return;
        }
        udfpsWindowCallback.onVisualEffectFinished();
    }

    public final void updateLayout() {
        FingerprintSensorInfo fingerprintSensorInfo = this.mSensorInfo;
        if (fingerprintSensorInfo == null) {
            return;
        }
        int i = fingerprintSensorInfo.mSensorAreaHeight;
        int i2 = fingerprintSensorInfo.mSensorMarginBottom;
        int i3 = Utils.getDisplaySize(this.mContext).x;
        int i4 = (int) ((i / 2.0f) + (i3 / 2.0f) + i2);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.width = i3;
        layoutParams.height = i4;
        layoutParams.gravity = 81;
        setLayoutParams(layoutParams);
        requestLayout();
        int applyDimension =
                (int) TypedValue.applyDimension(1, 400.0f, Utils.getDisplayMetrics(this.mContext));
        Iterator it = ((ArrayList) this.mEffectViews).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            ViewGroup.LayoutParams layoutParams2 = ((View) pair.first).getLayoutParams();
            layoutParams2.height = applyDimension;
            layoutParams2.width = applyDimension;
            ((View) pair.first).setLayoutParams(layoutParams2);
        }
    }
}
