package com.android.internal.widget;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public final class NotificationProgressDrawable extends Drawable {
    private static final String TAG = "NotifProgressDrawable";
    private int mAlpha;
    private BoundsChangeListener mBoundsChangeListener;
    private int mEndDotColor;
    private final Paint mFillPaint;
    private boolean mMutated;
    private final ArrayList<DrawablePart> mParts;
    private final RectF mPointRectF;
    private final RectF mSegRectF;
    private State mState;

    public interface BoundsChangeListener {
        void onDrawableBoundsChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float scaleFromDensity(float f, int i, int i2) {
        return (f * i2) / i;
    }

    private void updateLocalState() {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public NotificationProgressDrawable() {
        this(new State(), null);
    }

    public float getPointRadius() {
        return this.mState.mPointRadius;
    }

    public void setParts(List<DrawablePart> list) {
        this.mParts.clear();
        this.mParts.addAll(list);
        invalidateSelf();
    }

    public void setParts(DrawablePart... drawablePartArr) {
        setParts(Arrays.asList(drawablePartArr));
    }

    public void updateEndDotColor(int i) {
        if (this.mEndDotColor != i) {
            this.mEndDotColor = i;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float f = this.mState.mPointRadius;
        float f2 = getBounds().left;
        float fCenterY = getBounds().centerY();
        int size = this.mParts.size();
        float fRound = Math.round(fCenterY - f);
        float fRound2 = Math.round(f + fCenterY);
        for (int i = 0; i < size; i++) {
            DrawablePart drawablePart = this.mParts.get(i);
            float f3 = drawablePart.mStart + f2;
            float f4 = drawablePart.mEnd + f2;
            if (drawablePart instanceof DrawableSegment) {
                DrawableSegment drawableSegment = (DrawableSegment) drawablePart;
                if (f3 <= f4) {
                    float f5 = (drawableSegment.mFaded ? this.mState.mFadedSegmentHeight : this.mState.mSegmentHeight) / 2.0f;
                    float f6 = this.mState.mSegmentCornerRadius;
                    this.mFillPaint.setColor(drawableSegment.mColor);
                    this.mSegRectF.set(Math.round(f3), Math.round(fCenterY - f5), Math.round(f4), Math.round(f5 + fCenterY));
                    canvas.drawRoundRect(this.mSegRectF, f6, f6, this.mFillPaint);
                }
            } else if (drawablePart instanceof DrawablePoint) {
                this.mPointRectF.set(Math.round(f3), fRound, Math.round(f4), fRound2);
                float f7 = this.mState.mPointRectInset;
                float f8 = this.mState.mPointRectCornerRadius;
                this.mPointRectF.inset(f7, f7);
                this.mFillPaint.setColor(((DrawablePoint) drawablePart).mColor);
                canvas.drawRoundRect(this.mPointRectF, f8, f8, this.mFillPaint);
            }
        }
        if (this.mEndDotColor != 0) {
            float f9 = getBounds().right;
            float f10 = this.mState.mFadedSegmentHeight / 2.0f;
            this.mFillPaint.setColor(this.mEndDotColor);
            this.mSegRectF.set(Math.round(f9 - this.mState.mFadedSegmentHeight), Math.round(fCenterY - f10), Math.round(f9), Math.round(fCenterY + f10));
            canvas.drawRoundRect(this.mSegRectF, this.mState.mSegmentCornerRadius, this.mState.mSegmentCornerRadius, this.mFillPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mAlpha != i) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    public void setBoundsChangeListener(BoundsChangeListener boundsChangeListener) {
        this.mBoundsChangeListener = boundsChangeListener;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        BoundsChangeListener boundsChangeListener = this.mBoundsChangeListener;
        if (boundsChangeListener != null) {
            boundsChangeListener.onDrawableBoundsChanged();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mState.setDensity(resolveDensity(resources, 0));
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        updateLocalState();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        State state = this.mState;
        if (state == null) {
            return;
        }
        state.setDensity(resolveDensity(theme.getResources(), 0));
        applyThemeChildElements(theme);
        updateLocalState();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mState.canApplyTheme() || super.canApplyTheme();
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            int depth2 = xmlPullParser.getDepth();
            if (depth2 < depth && next == 3) {
                return;
            }
            if (next == 2 && depth2 <= depth) {
                String name = xmlPullParser.getName();
                if (name.equals("segments")) {
                    TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.NotificationProgressDrawableSegments);
                    updateSegmentsFromTypedArray(typedArrayObtainAttributes);
                    typedArrayObtainAttributes.recycle();
                } else if (name.equals("points")) {
                    TypedArray typedArrayObtainAttributes2 = obtainAttributes(resources, theme, attributeSet, R.styleable.NotificationProgressDrawablePoints);
                    updatePointsFromTypedArray(typedArrayObtainAttributes2);
                    typedArrayObtainAttributes2.recycle();
                } else {
                    Log.w(TAG, "Bad element under NotificationProgressDrawable: " + name);
                }
            }
        }
    }

    private void applyThemeChildElements(Resources.Theme theme) {
        State state = this.mState;
        if (state.mThemeAttrsSegments != null) {
            TypedArray typedArrayResolveAttributes = theme.resolveAttributes(state.mThemeAttrsSegments, R.styleable.NotificationProgressDrawableSegments);
            updateSegmentsFromTypedArray(typedArrayResolveAttributes);
            typedArrayResolveAttributes.recycle();
        }
        if (state.mThemeAttrsPoints != null) {
            TypedArray typedArrayResolveAttributes2 = theme.resolveAttributes(state.mThemeAttrsPoints, R.styleable.NotificationProgressDrawablePoints);
            updatePointsFromTypedArray(typedArrayResolveAttributes2);
            typedArrayResolveAttributes2.recycle();
        }
    }

    private void updateSegmentsFromTypedArray(TypedArray typedArray) {
        State state = this.mState;
        state.mChangingConfigurations |= typedArray.getChangingConfigurations();
        state.mThemeAttrsSegments = typedArray.extractThemeAttrs();
        state.mSegmentHeight = typedArray.getDimension(0, state.mSegmentHeight);
        state.mFadedSegmentHeight = typedArray.getDimension(2, state.mFadedSegmentHeight);
        state.mSegmentCornerRadius = typedArray.getDimension(1, state.mSegmentCornerRadius);
    }

    private void updatePointsFromTypedArray(TypedArray typedArray) {
        State state = this.mState;
        state.mChangingConfigurations |= typedArray.getChangingConfigurations();
        state.mThemeAttrsPoints = typedArray.extractThemeAttrs();
        state.mPointRadius = typedArray.getDimension(0, state.mPointRadius);
        state.mPointRectInset = typedArray.getDimension(1, state.mPointRectInset);
        state.mPointRectCornerRadius = typedArray.getDimension(2, state.mPointRectCornerRadius);
    }

    static int resolveDensity(Resources resources, int i) {
        if (resources != null) {
            i = resources.getDisplayMetrics().densityDpi;
        }
        if (i == 0) {
            return 160;
        }
        return i;
    }

    private static int scaleFromDensity(int i, int i2, int i3, boolean z) {
        if (i == 0 || i2 == i3) {
            return i;
        }
        float f = (i3 * i) / i2;
        if (!z) {
            return (int) f;
        }
        int iRound = Math.round(f);
        return iRound != 0 ? iRound : i > 0 ? 1 : -1;
    }

    public static abstract class DrawablePart {
        protected final int mColor;
        protected float mEnd;
        protected float mStart;

        protected DrawablePart(float f, float f2, int i) {
            this.mStart = f;
            this.mEnd = f2;
            this.mColor = i;
        }

        public float getStart() {
            return this.mStart;
        }

        public void setStart(float f) {
            this.mStart = f;
        }

        public float getEnd() {
            return this.mEnd;
        }

        public void setEnd(float f) {
            this.mEnd = f;
        }

        public float getWidth() {
            return this.mEnd - this.mStart;
        }

        public int getColor() {
            return this.mColor;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                DrawablePart drawablePart = (DrawablePart) obj;
                if (Float.compare(this.mStart, drawablePart.mStart) == 0 && Float.compare(this.mEnd, drawablePart.mEnd) == 0 && this.mColor == drawablePart.mColor) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mStart), Float.valueOf(this.mEnd), Integer.valueOf(this.mColor));
        }
    }

    public static final class DrawableSegment extends DrawablePart {
        private final boolean mFaded;

        public DrawableSegment(float f, float f2, int i) {
            this(f, f2, i, false);
        }

        public DrawableSegment(float f, float f2, int i, boolean z) {
            super(f, f2, i);
            this.mFaded = z;
        }

        public String toString() {
            return "Segment(start=" + this.mStart + ", end=" + this.mEnd + ", color=" + this.mColor + ", faded=" + this.mFaded + ')';
        }

        @Override // com.android.internal.widget.NotificationProgressDrawable.DrawablePart
        public boolean equals(Object obj) {
            return super.equals(obj) && this.mFaded == ((DrawableSegment) obj).mFaded;
        }

        @Override // com.android.internal.widget.NotificationProgressDrawable.DrawablePart
        public int hashCode() {
            return Objects.hash(Integer.valueOf(super.hashCode()), Boolean.valueOf(this.mFaded));
        }
    }

    public static final class DrawablePoint extends DrawablePart {
        public DrawablePoint(float f, float f2, int i) {
            super(f, f2, i);
        }

        public String toString() {
            return "Point(start=" + this.mStart + ", end=" + this.mEnd + ", color=" + this.mColor + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new State(this.mState, null);
            updateLocalState();
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static final class State extends Drawable.ConstantState {
        int mChangingConfigurations;
        int mDensity;
        float mFadedSegmentHeight;
        float mPointRadius;
        float mPointRectCornerRadius;
        float mPointRectInset;
        float mSegmentCornerRadius;
        float mSegmentHeight;
        int[] mThemeAttrs;
        int[] mThemeAttrsPoints;
        int[] mThemeAttrsSegments;

        State() {
            this.mDensity = 160;
        }

        State(State state, Resources resources) {
            this.mDensity = 160;
            this.mChangingConfigurations = state.mChangingConfigurations;
            this.mSegmentHeight = state.mSegmentHeight;
            this.mFadedSegmentHeight = state.mFadedSegmentHeight;
            this.mSegmentCornerRadius = state.mSegmentCornerRadius;
            this.mPointRadius = state.mPointRadius;
            this.mPointRectInset = state.mPointRectInset;
            this.mPointRectCornerRadius = state.mPointRectCornerRadius;
            this.mThemeAttrs = state.mThemeAttrs;
            this.mThemeAttrsSegments = state.mThemeAttrsSegments;
            this.mThemeAttrsPoints = state.mThemeAttrsPoints;
            int iResolveDensity = NotificationProgressDrawable.resolveDensity(resources, state.mDensity);
            this.mDensity = iResolveDensity;
            int i = state.mDensity;
            if (i != iResolveDensity) {
                applyDensityScaling(i, iResolveDensity);
            }
        }

        private void applyDensityScaling(int i, int i2) {
            float f = this.mSegmentHeight;
            if (f > 0.0f) {
                this.mSegmentHeight = NotificationProgressDrawable.scaleFromDensity(f, i, i2);
            }
            float f2 = this.mFadedSegmentHeight;
            if (f2 > 0.0f) {
                this.mFadedSegmentHeight = NotificationProgressDrawable.scaleFromDensity(f2, i, i2);
            }
            float f3 = this.mSegmentCornerRadius;
            if (f3 > 0.0f) {
                this.mSegmentCornerRadius = NotificationProgressDrawable.scaleFromDensity(f3, i, i2);
            }
            float f4 = this.mPointRadius;
            if (f4 > 0.0f) {
                this.mPointRadius = NotificationProgressDrawable.scaleFromDensity(f4, i, i2);
            }
            float f5 = this.mPointRectInset;
            if (f5 > 0.0f) {
                this.mPointRectInset = NotificationProgressDrawable.scaleFromDensity(f5, i, i2);
            }
            float f6 = this.mPointRectCornerRadius;
            if (f6 > 0.0f) {
                this.mPointRectCornerRadius = NotificationProgressDrawable.scaleFromDensity(f6, i, i2);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new NotificationProgressDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            if (NotificationProgressDrawable.resolveDensity(resources, this.mDensity) != this.mDensity) {
                this = new State(this, resources);
            }
            return new NotificationProgressDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return (this.mThemeAttrs == null && this.mThemeAttrsSegments == null && this.mThemeAttrsPoints == null && !super.canApplyTheme()) ? false : true;
        }

        public void setDensity(int i) {
            int i2 = this.mDensity;
            if (i2 != i) {
                this.mDensity = i;
                applyDensityScaling(i2, i);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    private NotificationProgressDrawable(State state, Resources resources) {
        this.mBoundsChangeListener = null;
        this.mParts = new ArrayList<>();
        this.mSegRectF = new RectF();
        this.mPointRectF = new RectF();
        Paint paint = new Paint();
        this.mFillPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mEndDotColor = 0;
        this.mState = state;
        updateLocalState();
    }
}
