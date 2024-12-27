package com.android.server.input.debug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RotaryInputGraphView extends View {
    public static final long MAX_GESTURE_TIME;
    public static final long MAX_SHOWN_TIME_INTERVAL;
    public final Locale mDefaultLocale;
    public final DisplayMetrics mDm;
    public float mFrameCenterPosition;
    public final float mFrameCenterToBorderDistance;
    public final Paint mFramePaint;
    public final Paint mFrameTextPaint;
    public final Paint mGraphLinePaint;
    public final Paint mGraphPointPaint;
    public final CyclicBuffer mGraphValues;
    public final float mScaledVerticalScrollFactor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CyclicBuffer {
        public int mIteratorCount;
        public int mIteratorIndex;
        public int mSize = 0;
        public int mLastIndex = 0;
        public final AnonymousClass1 mReverseIterator = new AnonymousClass1();
        public final GraphValue[] mValues = new GraphValue[400];

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.input.debug.RotaryInputGraphView$CyclicBuffer$1, reason: invalid class name */
        public final class AnonymousClass1 implements Iterator {
            public AnonymousClass1() {
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                CyclicBuffer cyclicBuffer = CyclicBuffer.this;
                return cyclicBuffer.mIteratorCount <= cyclicBuffer.mSize;
            }

            @Override // java.util.Iterator
            public final Object next() {
                CyclicBuffer cyclicBuffer = CyclicBuffer.this;
                cyclicBuffer.mIteratorCount++;
                int i = cyclicBuffer.mIteratorIndex;
                cyclicBuffer.mIteratorIndex = i - 1;
                cyclicBuffer.getClass();
                return cyclicBuffer.mValues[(i + 400) % 400];
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GraphValue {
        public float mPos;
        public long mTime;
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        MAX_SHOWN_TIME_INTERVAL = timeUnit.toMillis(5L);
        MAX_GESTURE_TIME = timeUnit.toMillis(1L);
    }

    public RotaryInputGraphView(Context context) {
        super(context);
        this.mDefaultLocale = Locale.getDefault();
        Paint paint = new Paint();
        this.mFramePaint = paint;
        Paint paint2 = new Paint();
        this.mFrameTextPaint = paint2;
        Paint paint3 = new Paint();
        this.mGraphLinePaint = paint3;
        Paint paint4 = new Paint();
        this.mGraphPointPaint = paint4;
        this.mGraphValues = new CyclicBuffer();
        this.mFrameCenterPosition = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mDm = ((View) this).mContext.getResources().getDisplayMetrics();
        this.mFrameCenterToBorderDistance = r4.heightPixels;
        this.mScaledVerticalScrollFactor = ViewConfiguration.get(context).getScaledVerticalScrollFactor();
        paint.setColor(-1082909881);
        paint.setStrokeWidth(applyDimensionSp(2, r4));
        paint2.setColor(-65281);
        paint2.setTextSize(applyDimensionSp(10, r4));
        paint3.setColor(-65281);
        paint3.setStrokeWidth(applyDimensionSp(1, r4));
        Paint.Cap cap = Paint.Cap.ROUND;
        paint3.setStrokeCap(cap);
        Paint.Join join = Paint.Join.ROUND;
        paint3.setStrokeJoin(join);
        paint4.setColor(-65281);
        paint4.setStrokeWidth(applyDimensionSp(4, r4));
        paint4.setStrokeCap(cap);
        paint4.setStrokeJoin(join);
    }

    public static int applyDimensionSp(int i, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(2, i, displayMetrics);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float f;
        int i;
        super.onDraw(canvas);
        int applyDimensionSp = applyDimensionSp(10, this.mDm);
        int height = getHeight() - applyDimensionSp;
        int i2 = (applyDimensionSp + height) / 2;
        float f2 = applyDimensionSp;
        float width = getWidth();
        canvas.drawLine(FullScreenMagnificationGestureHandler.MAX_SCALE, f2, width, f2, this.mFramePaint);
        float f3 = i2;
        canvas.drawLine(FullScreenMagnificationGestureHandler.MAX_SCALE, f3, width, f3, this.mFramePaint);
        float f4 = height;
        canvas.drawLine(FullScreenMagnificationGestureHandler.MAX_SCALE, f4, width, f4, this.mFramePaint);
        int applyDimensionSp2 = applyDimensionSp(2, this.mDm);
        canvas.drawText(String.format(this.mDefaultLocale, "%.1f", Float.valueOf(this.mFrameCenterPosition + this.mFrameCenterToBorderDistance)), FullScreenMagnificationGestureHandler.MAX_SCALE, applyDimensionSp - applyDimensionSp2, this.mFrameTextPaint);
        canvas.drawText(String.format(this.mDefaultLocale, "%.1f", Float.valueOf(this.mFrameCenterPosition)), FullScreenMagnificationGestureHandler.MAX_SCALE, i2 - applyDimensionSp2, this.mFrameTextPaint);
        canvas.drawText(String.format(this.mDefaultLocale, "%.1f", Float.valueOf(this.mFrameCenterPosition - this.mFrameCenterToBorderDistance)), FullScreenMagnificationGestureHandler.MAX_SCALE, height - applyDimensionSp2, this.mFrameTextPaint);
        CyclicBuffer cyclicBuffer = this.mGraphValues;
        if (cyclicBuffer.mSize == 0) {
            return;
        }
        int i3 = cyclicBuffer.mLastIndex;
        long j = cyclicBuffer.mValues[i3].mTime;
        cyclicBuffer.mIteratorIndex = i3;
        cyclicBuffer.mIteratorCount = 1;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (true) {
            CyclicBuffer.AnonymousClass1 anonymousClass1 = cyclicBuffer.mReverseIterator;
            if (!anonymousClass1.hasNext()) {
                return;
            }
            GraphValue graphValue = (GraphValue) anonymousClass1.next();
            int i4 = (int) (j - graphValue.mTime);
            float f8 = graphValue.mPos;
            int i5 = i2;
            long j2 = j;
            float f9 = (((r10 - i4) / MAX_SHOWN_TIME_INTERVAL) * width) + FullScreenMagnificationGestureHandler.MAX_SCALE;
            float f10 = f3 - (((f8 - this.mFrameCenterPosition) / this.mFrameCenterToBorderDistance) * (i5 - applyDimensionSp));
            canvas.drawPoint(f9, f10, this.mGraphPointPaint);
            if (i4 != 0) {
                f = f9;
                if (i4 - f5 <= MAX_GESTURE_TIME) {
                    i = i4;
                    canvas.drawLine(f6, f7, f, f10, this.mGraphLinePaint);
                    f5 = i;
                    f7 = f10;
                    f6 = f;
                    i2 = i5;
                    j = j2;
                }
            } else {
                f = f9;
            }
            i = i4;
            f5 = i;
            f7 = f10;
            f6 = f;
            i2 = i5;
            j = j2;
        }
    }
}
