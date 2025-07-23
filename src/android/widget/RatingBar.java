package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.PluralsMessageFormatter;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class RatingBar extends AbsSeekBar {
    public static final String PLURALS_MAX = "max";
    public static final String PLURALS_RATING = "rating";
    private int mNumStars;
    private OnRatingBarChangeListener mOnRatingBarChangeListener;
    private int mProgressOnStartTracking;

    public interface OnRatingBarChangeListener {
        void onRatingChanged(RatingBar ratingBar, float f, boolean z);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<RatingBar> {
        private int mIsIndicatorId;
        private int mNumStarsId;
        private boolean mPropertiesMapped = false;
        private int mRatingId;
        private int mStepSizeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mIsIndicatorId = propertyMapper.mapBoolean("isIndicator", 16843079);
            this.mNumStarsId = propertyMapper.mapInt("numStars", 16843076);
            this.mRatingId = propertyMapper.mapFloat(RatingBar.PLURALS_RATING, 16843077);
            this.mStepSizeId = propertyMapper.mapFloat("stepSize", 16843078);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(RatingBar ratingBar, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIsIndicatorId, ratingBar.isIndicator());
            propertyReader.readInt(this.mNumStarsId, ratingBar.getNumStars());
            propertyReader.readFloat(this.mRatingId, ratingBar.getRating());
            propertyReader.readFloat(this.mStepSizeId, ratingBar.getStepSize());
        }
    }

    public RatingBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RatingBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNumStars = 5;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RatingBar, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.RatingBar, attributeSet, obtainStyledAttributes, i, i2);
        int i3 = obtainStyledAttributes.getInt(0, this.mNumStars);
        setIsIndicator(obtainStyledAttributes.getBoolean(3, !this.mIsUserSeekable));
        float f = obtainStyledAttributes.getFloat(1, -1.0f);
        float f2 = obtainStyledAttributes.getFloat(2, -1.0f);
        obtainStyledAttributes.recycle();
        if (i3 > 0 && i3 != this.mNumStars) {
            setNumStars(i3);
        }
        if (f2 >= 0.0f) {
            setStepSize(f2);
        } else {
            setStepSize(0.5f);
        }
        if (f >= 0.0f) {
            setRating(f);
        }
        this.mTouchProgressOffset = 0.6f;
    }

    public RatingBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842876);
    }

    public RatingBar(Context context) {
        this(context, null);
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingBarChangeListener) {
        this.mOnRatingBarChangeListener = onRatingBarChangeListener;
    }

    public OnRatingBarChangeListener getOnRatingBarChangeListener() {
        return this.mOnRatingBarChangeListener;
    }

    public void setIsIndicator(boolean z) {
        this.mIsUserSeekable = !z;
        if (z) {
            setFocusable(16);
        } else {
            setFocusable(1);
        }
    }

    public boolean isIndicator() {
        return !this.mIsUserSeekable;
    }

    public void setNumStars(int i) {
        if (i <= 0) {
            return;
        }
        this.mNumStars = i;
        requestLayout();
    }

    public int getNumStars() {
        return this.mNumStars;
    }

    public void setRating(float f) {
        setProgress(Math.round(f * getProgressPerStar()));
    }

    public float getRating() {
        return getProgress() / getProgressPerStar();
    }

    public void setStepSize(float f) {
        if (f <= 0.0f) {
            return;
        }
        float f2 = this.mNumStars / f;
        setMax((int) f2);
        setProgress((int) ((f2 / getMax()) * getProgress()));
    }

    public float getStepSize() {
        return getNumStars() / getMax();
    }

    private float getProgressPerStar() {
        if (this.mNumStars > 0) {
            return (getMax() * 1.0f) / this.mNumStars;
        }
        return 1.0f;
    }

    @Override // android.widget.ProgressBar
    Shape getDrawableShape() {
        return new RectShape();
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar
    void onProgressRefresh(float f, boolean z, int i) {
        super.onProgressRefresh(f, z, i);
        updateSecondaryProgress(i);
        if (z) {
            return;
        }
        dispatchRatingChange(false);
    }

    private void updateSecondaryProgress(int i) {
        float progressPerStar = getProgressPerStar();
        if (progressPerStar > 0.0f) {
            setSecondaryProgress((int) (Math.ceil(i / progressPerStar) * progressPerStar));
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mSampleWidth > 0) {
            setMeasuredDimension(resolveSizeAndState(this.mSampleWidth * this.mNumStars, i, 0), getMeasuredHeight());
        }
    }

    @Override // android.widget.AbsSeekBar
    void onStartTrackingTouch() {
        this.mProgressOnStartTracking = getProgress();
        super.onStartTrackingTouch();
    }

    @Override // android.widget.AbsSeekBar
    void onStopTrackingTouch() {
        super.onStopTrackingTouch();
        if (getProgress() != this.mProgressOnStartTracking) {
            dispatchRatingChange(true);
        }
    }

    @Override // android.widget.AbsSeekBar
    void onKeyChange() {
        super.onKeyChange();
        dispatchRatingChange(true);
    }

    void dispatchRatingChange(boolean z) {
        OnRatingBarChangeListener onRatingBarChangeListener = this.mOnRatingBarChangeListener;
        if (onRatingBarChangeListener != null) {
            onRatingBarChangeListener.onRatingChanged(this, getRating(), z);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar
    public synchronized void setMax(int i) {
        if (i <= 0) {
            return;
        }
        super.setMax(i);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public CharSequence getAccessibilityClassName() {
        return RatingBar.class.getName();
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (canUserSetProgress()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        }
        float max = getMax() * getStepSize();
        HashMap hashMap = new HashMap();
        hashMap.put(PLURALS_RATING, Float.valueOf(getRating()));
        hashMap.put("max", Float.valueOf(max));
        accessibilityNodeInfo.setStateDescription(PluralsMessageFormatter.format(getContext().getResources(), hashMap, R.string.rating_label));
    }

    @Override // android.widget.AbsSeekBar
    boolean canUserSetProgress() {
        return super.canUserSetProgress() && !isIndicator();
    }
}
