package com.android.server.accessibility.magnification;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;

import com.android.server.accessibility.Flags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PanningScalingHandler extends GestureDetector.SimpleOnGestureListener
        implements ScaleGestureDetector.OnScaleGestureListener {
    public static final boolean DEBUG = Log.isLoggable("PanningScalingHandler", 3);
    public final int mDisplayId;
    public boolean mEnable;
    public final MagnificationDelegate mMagnificationDelegate;
    public final float mMaxScale;
    public final ScaleGestureDetector mScaleGestureDetector;
    public boolean mScaling;
    public final float mScalingThreshold;
    public final GestureDetector mScrollGestureDetector;
    public float mInitialScaleFactor = -1.0f;
    public final float mMinScale = 1.0f;
    public final boolean mBlockScroll = true;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface MagnificationDelegate {
        float getScale(int i);

        void processScroll(int i, float f, float f2);

        void setScale(float f, int i);
    }

    public PanningScalingHandler(
            Context context,
            float f,
            WindowMagnificationGestureHandler.AnonymousClass1 anonymousClass1) {
        this.mDisplayId = context.getDisplayId();
        this.mMaxScale = f;
        Flags.pinchZoomZeroMinSpan();
        ScaleGestureDetector scaleGestureDetector =
                new ScaleGestureDetector(
                        context,
                        ViewConfiguration.get(context).getScaledTouchSlop() * 2,
                        0,
                        Handler.getMain(),
                        this);
        this.mScaleGestureDetector = scaleGestureDetector;
        this.mScrollGestureDetector = new GestureDetector(context, this, Handler.getMain());
        scaleGestureDetector.setQuickScaleEnabled(false);
        this.mMagnificationDelegate = anonymousClass1;
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(R.dimen.conversation_content_start, typedValue, false);
        this.mScalingThreshold = typedValue.getFloat();
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:

       if (r7 < r2) goto L21;
    */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005c  */
    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onScale(android.view.ScaleGestureDetector r7) {
        /*
            r6 = this;
            boolean r0 = com.android.server.accessibility.magnification.PanningScalingHandler.DEBUG
            java.lang.String r1 = "PanningScalingHandler"
            if (r0 == 0) goto Lc
            java.lang.String r2 = "onScale: triggered "
            android.util.Slog.i(r1, r2)
        Lc:
            boolean r2 = r6.mScaling
            r3 = 1
            if (r2 != 0) goto L36
            float r0 = r6.mInitialScaleFactor
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r1 = 0
            if (r0 >= 0) goto L20
            float r7 = r7.getScaleFactor()
            r6.mInitialScaleFactor = r7
            return r1
        L20:
            float r7 = r7.getScaleFactor()
            float r0 = r6.mInitialScaleFactor
            float r7 = r7 - r0
            float r7 = java.lang.Math.abs(r7)
            float r0 = r6.mScalingThreshold
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 <= 0) goto L32
            goto L33
        L32:
            r3 = r1
        L33:
            r6.mScaling = r3
            return r3
        L36:
            com.android.server.accessibility.magnification.PanningScalingHandler$MagnificationDelegate r2 = r6.mMagnificationDelegate
            int r4 = r6.mDisplayId
            float r2 = r2.getScale(r4)
            float r7 = r7.getScaleFactor()
            float r7 = r7 * r2
            float r4 = r6.mMaxScale
            int r5 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r5 <= 0) goto L4f
            int r5 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r5 <= 0) goto L4f
        L4d:
            r7 = r4
            goto L5a
        L4f:
            float r4 = r6.mMinScale
            int r5 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r5 >= 0) goto L5a
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 >= 0) goto L5a
            goto L4d
        L5a:
            if (r0 == 0) goto L73
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Scaled content to: "
            r0.<init>(r2)
            r0.append(r7)
            java.lang.String r2 = "x"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Slog.i(r1, r0)
        L73:
            com.android.server.accessibility.magnification.PanningScalingHandler$MagnificationDelegate r0 = r6.mMagnificationDelegate
            int r6 = r6.mDisplayId
            r0.setScale(r7, r6)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.magnification.PanningScalingHandler.onScale(android.view.ScaleGestureDetector):boolean");
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public final boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return this.mEnable;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public final void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        this.mInitialScaleFactor = -1.0f;
        this.mScaling = false;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnGestureListener
    public final boolean onScroll(
            MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mEnable && (!this.mBlockScroll || !this.mScaling)) {
            this.mMagnificationDelegate.processScroll(this.mDisplayId, f, f2);
        }
        return true;
    }

    public final String toString() {
        return "PanningScalingHandler{mInitialScaleFactor="
                + this.mInitialScaleFactor
                + ", mScaling="
                + this.mScaling
                + '}';
    }
}
