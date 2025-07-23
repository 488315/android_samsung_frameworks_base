package com.android.wm.shell.shared.startingsurface;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.wm.shell.shared.TransactionPool;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplashScreenExitAnimationUtils {
    public static final Interpolator ICON_INTERPOLATOR = new PathInterpolator(0.15f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator MASK_RADIUS_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.4f, 1.0f);
    public static final Interpolator SHIFT_UP_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RadialVanishAnimation extends View {
        public final Point mCircleCenter;
        public int mFinishRadius;
        public final Matrix mVanishMatrix;
        public final Paint mVanishPaint;
        public final ViewGroup mView;

        public RadialVanishAnimation(ViewGroup viewGroup) {
            super(viewGroup.getContext());
            this.mCircleCenter = new Point();
            this.mVanishMatrix = new Matrix();
            Paint paint = new Paint(1);
            this.mVanishPaint = paint;
            this.mView = viewGroup;
            viewGroup.addView(this);
            if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) getLayoutParams()).setMargins(0, 0, 0, 0);
            }
            paint.setAlpha(0);
        }

        @Override // android.view.View
        public final void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawRect(0.0f, 0.0f, this.mView.getWidth(), this.mView.getHeight(), this.mVanishPaint);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShiftUpAnimation {
        public final SyncRtSurfaceTransactionApplier mApplier;
        public final Rect mFirstWindowFrame;
        public final SurfaceControl mFirstWindowSurface;
        public final float mFromYDelta;
        public final int mMainWindowShiftLength;
        public final View mOccludeHoleView;
        public final ViewGroup mSplashScreenView;
        public final Matrix mTmpTransform = new Matrix();
        public final float mToYDelta;
        public final TransactionPool mTransactionPool;

        public ShiftUpAnimation(float f, float f2, View view, SurfaceControl surfaceControl, ViewGroup viewGroup, TransactionPool transactionPool, Rect rect, int i, float f3) {
            int i2 = rect.top;
            this.mFromYDelta = i2 - Math.max(i2, f3);
            this.mToYDelta = f2;
            this.mOccludeHoleView = view;
            this.mApplier = new SyncRtSurfaceTransactionApplier(view);
            this.mFirstWindowSurface = surfaceControl;
            this.mSplashScreenView = viewGroup;
            this.mTransactionPool = transactionPool;
            this.mFirstWindowFrame = rect;
            this.mMainWindowShiftLength = i;
        }
    }

    public static float getProgress(long j, long j2, int i, float f) {
        return MathUtils.constrain(((f * i) - j) / j2, 0.0f, 1.0f);
    }
}
