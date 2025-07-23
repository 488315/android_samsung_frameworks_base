package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TouchResponse {
    public final float[] mAnchorDpDt;
    public final int mAutoCompleteMode;
    public final float mDragScale;
    public boolean mDragStarted;
    public final float mDragThreshold;
    public final int mFlags;
    public final boolean mIsRotateMode;
    public float mLastTouchX;
    public float mLastTouchY;
    public final int mLimitBoundsTo;
    public final float mMaxAcceleration;
    public final float mMaxVelocity;
    public final MotionLayout mMotionLayout;
    public final boolean mMoveWhenScrollAtTop;
    public final int mOnTouchUp;
    public final int mRotationCenterId;
    public final int mSpringBoundary;
    public final float mSpringDamping;
    public final float mSpringMass;
    public final float mSpringStiffness;
    public final float mSpringStopThreshold;
    public final int[] mTempLoc;
    public final int mTouchAnchorId;
    public final int mTouchAnchorSide;
    public float mTouchAnchorX;
    public float mTouchAnchorY;
    public float mTouchDirectionX;
    public float mTouchDirectionY;
    public final int mTouchRegionId;
    public final int mTouchSide;
    public static final float[][] TOUCH_SIDES = {new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}, new float[]{0.5f, 1.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}};
    public static final float[][] TOUCH_DIRECTION = {new float[]{0.0f, -1.0f}, new float[]{0.0f, 1.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}};

    public TouchResponse(Context context, MotionLayout motionLayout, XmlPullParser xmlPullParser) {
        this.mTouchAnchorSide = 0;
        this.mTouchSide = 0;
        this.mOnTouchUp = 0;
        this.mTouchAnchorId = -1;
        this.mTouchRegionId = -1;
        this.mLimitBoundsTo = -1;
        this.mTouchAnchorY = 0.5f;
        this.mTouchAnchorX = 0.5f;
        this.mRotationCenterId = -1;
        this.mIsRotateMode = false;
        this.mTouchDirectionX = 0.0f;
        this.mTouchDirectionY = 1.0f;
        this.mDragStarted = false;
        this.mAnchorDpDt = new float[2];
        this.mTempLoc = new int[2];
        this.mMaxVelocity = 4.0f;
        this.mMaxAcceleration = 1.2f;
        this.mMoveWhenScrollAtTop = true;
        this.mDragScale = 1.0f;
        this.mFlags = 0;
        this.mDragThreshold = 10.0f;
        this.mSpringDamping = 10.0f;
        this.mSpringMass = 1.0f;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mSpringBoundary = 0;
        this.mAutoCompleteMode = 0;
        this.mMotionLayout = motionLayout;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.OnSwipe);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.OnSwipe_touchAnchorId) {
                this.mTouchAnchorId = obtainStyledAttributes.getResourceId(index, this.mTouchAnchorId);
            } else if (index == R.styleable.OnSwipe_touchAnchorSide) {
                int i2 = obtainStyledAttributes.getInt(index, this.mTouchAnchorSide);
                this.mTouchAnchorSide = i2;
                float[] fArr = TOUCH_SIDES[i2];
                this.mTouchAnchorX = fArr[0];
                this.mTouchAnchorY = fArr[1];
            } else if (index == R.styleable.OnSwipe_dragDirection) {
                int i3 = obtainStyledAttributes.getInt(index, this.mTouchSide);
                this.mTouchSide = i3;
                if (i3 < 6) {
                    float[] fArr2 = TOUCH_DIRECTION[i3];
                    this.mTouchDirectionX = fArr2[0];
                    this.mTouchDirectionY = fArr2[1];
                } else {
                    this.mTouchDirectionY = Float.NaN;
                    this.mTouchDirectionX = Float.NaN;
                    this.mIsRotateMode = true;
                }
            } else if (index == R.styleable.OnSwipe_maxVelocity) {
                this.mMaxVelocity = obtainStyledAttributes.getFloat(index, this.mMaxVelocity);
            } else if (index == R.styleable.OnSwipe_maxAcceleration) {
                this.mMaxAcceleration = obtainStyledAttributes.getFloat(index, this.mMaxAcceleration);
            } else if (index == R.styleable.OnSwipe_moveWhenScrollAtTop) {
                this.mMoveWhenScrollAtTop = obtainStyledAttributes.getBoolean(index, this.mMoveWhenScrollAtTop);
            } else if (index == R.styleable.OnSwipe_dragScale) {
                this.mDragScale = obtainStyledAttributes.getFloat(index, this.mDragScale);
            } else if (index == R.styleable.OnSwipe_dragThreshold) {
                this.mDragThreshold = obtainStyledAttributes.getFloat(index, this.mDragThreshold);
            } else if (index == R.styleable.OnSwipe_touchRegionId) {
                this.mTouchRegionId = obtainStyledAttributes.getResourceId(index, this.mTouchRegionId);
            } else if (index == R.styleable.OnSwipe_onTouchUp) {
                this.mOnTouchUp = obtainStyledAttributes.getInt(index, this.mOnTouchUp);
            } else if (index == R.styleable.OnSwipe_nestedScrollFlags) {
                this.mFlags = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == R.styleable.OnSwipe_limitBoundsTo) {
                this.mLimitBoundsTo = obtainStyledAttributes.getResourceId(index, 0);
            } else if (index == R.styleable.OnSwipe_rotationCenterId) {
                this.mRotationCenterId = obtainStyledAttributes.getResourceId(index, this.mRotationCenterId);
            } else if (index == R.styleable.OnSwipe_springDamping) {
                this.mSpringDamping = obtainStyledAttributes.getFloat(index, this.mSpringDamping);
            } else if (index == R.styleable.OnSwipe_springMass) {
                this.mSpringMass = obtainStyledAttributes.getFloat(index, this.mSpringMass);
            } else if (index == R.styleable.OnSwipe_springStiffness) {
                this.mSpringStiffness = obtainStyledAttributes.getFloat(index, this.mSpringStiffness);
            } else if (index == R.styleable.OnSwipe_springStopThreshold) {
                this.mSpringStopThreshold = obtainStyledAttributes.getFloat(index, this.mSpringStopThreshold);
            } else if (index == R.styleable.OnSwipe_springBoundary) {
                this.mSpringBoundary = obtainStyledAttributes.getInt(index, this.mSpringBoundary);
            } else if (index == R.styleable.OnSwipe_autoCompleteMode) {
                this.mAutoCompleteMode = obtainStyledAttributes.getInt(index, this.mAutoCompleteMode);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public final RectF getLimitBoundsTo(ViewGroup viewGroup, RectF rectF) {
        View findViewById;
        int i = this.mLimitBoundsTo;
        if (i == -1 || (findViewById = viewGroup.findViewById(i)) == null) {
            return null;
        }
        rectF.set(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
        return rectF;
    }

    public final RectF getTouchRegion(ViewGroup viewGroup, RectF rectF) {
        View findViewById;
        int i = this.mTouchRegionId;
        if (i == -1 || (findViewById = viewGroup.findViewById(i)) == null) {
            return null;
        }
        rectF.set(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
        return rectF;
    }

    public final void setRTL(boolean z) {
        float[][] fArr = TOUCH_SIDES;
        float[][] fArr2 = TOUCH_DIRECTION;
        if (z) {
            fArr2[4] = fArr2[3];
            fArr2[5] = fArr2[2];
            fArr[5] = fArr[2];
            fArr[6] = fArr[1];
        } else {
            fArr2[4] = fArr2[2];
            fArr2[5] = fArr2[3];
            fArr[5] = fArr[1];
            fArr[6] = fArr[2];
        }
        float[] fArr3 = fArr[this.mTouchAnchorSide];
        this.mTouchAnchorX = fArr3[0];
        this.mTouchAnchorY = fArr3[1];
        int i = this.mTouchSide;
        if (i >= 6) {
            return;
        }
        float[] fArr4 = fArr2[i];
        this.mTouchDirectionX = fArr4[0];
        this.mTouchDirectionY = fArr4[1];
    }

    public final String toString() {
        if (Float.isNaN(this.mTouchDirectionX)) {
            return "rotation";
        }
        return this.mTouchDirectionX + " , " + this.mTouchDirectionY;
    }

    public TouchResponse(MotionLayout motionLayout, OnSwipe onSwipe) {
        this.mTouchAnchorSide = 0;
        this.mTouchSide = 0;
        this.mOnTouchUp = 0;
        this.mTouchAnchorId = -1;
        this.mTouchRegionId = -1;
        this.mLimitBoundsTo = -1;
        this.mTouchAnchorY = 0.5f;
        this.mTouchAnchorX = 0.5f;
        this.mRotationCenterId = -1;
        this.mIsRotateMode = false;
        this.mTouchDirectionX = 0.0f;
        this.mTouchDirectionY = 1.0f;
        this.mDragStarted = false;
        this.mAnchorDpDt = new float[2];
        this.mTempLoc = new int[2];
        this.mMaxVelocity = 4.0f;
        this.mMaxAcceleration = 1.2f;
        this.mMoveWhenScrollAtTop = true;
        this.mDragScale = 1.0f;
        this.mFlags = 0;
        this.mDragThreshold = 10.0f;
        this.mSpringDamping = 10.0f;
        this.mSpringMass = 1.0f;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mSpringBoundary = 0;
        this.mAutoCompleteMode = 0;
        this.mMotionLayout = motionLayout;
        onSwipe.getClass();
        this.mTouchAnchorId = -1;
        this.mTouchAnchorSide = 0;
        float[] fArr = TOUCH_SIDES[0];
        this.mTouchAnchorX = fArr[0];
        this.mTouchAnchorY = fArr[1];
        this.mTouchSide = 0;
        float[] fArr2 = TOUCH_DIRECTION[0];
        this.mTouchDirectionX = fArr2[0];
        this.mTouchDirectionY = fArr2[1];
        this.mMaxVelocity = 4.0f;
        this.mMaxAcceleration = 1.2f;
        this.mMoveWhenScrollAtTop = true;
        this.mDragScale = 1.0f;
        this.mDragThreshold = 10.0f;
        this.mTouchRegionId = -1;
        this.mOnTouchUp = 0;
        this.mFlags = 0;
        this.mLimitBoundsTo = -1;
        this.mRotationCenterId = -1;
        this.mSpringBoundary = 0;
        this.mSpringDamping = Float.NaN;
        this.mSpringMass = 1.0f;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mAutoCompleteMode = 0;
    }
}
