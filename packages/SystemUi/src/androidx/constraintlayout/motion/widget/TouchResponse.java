package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R$styleable;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TouchResponse {
    public final float[] mAnchorDpDt;
    public int mAutoCompleteMode;
    public float mDragScale;
    public boolean mDragStarted;
    public float mDragThreshold;
    public int mFlags;
    public boolean mIsRotateMode;
    public float mLastTouchX;
    public float mLastTouchY;
    public int mLimitBoundsTo;
    public float mMaxAcceleration;
    public float mMaxVelocity;
    public final MotionLayout mMotionLayout;
    public boolean mMoveWhenScrollAtTop;
    public int mOnTouchUp;
    public final float mRotateCenterX;
    public final float mRotateCenterY;
    public int mRotationCenterId;
    public int mSpringBoundary;
    public float mSpringDamping;
    public float mSpringMass;
    public float mSpringStiffness;
    public float mSpringStopThreshold;
    public final int[] mTempLoc;
    public int mTouchAnchorId;
    public int mTouchAnchorSide;
    public float mTouchAnchorX;
    public float mTouchAnchorY;
    public float mTouchDirectionX;
    public float mTouchDirectionY;
    public int mTouchRegionId;
    public int mTouchSide;
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
        this.mRotateCenterX = 0.5f;
        this.mRotateCenterY = 0.5f;
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.OnSwipe);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 16) {
                this.mTouchAnchorId = obtainStyledAttributes.getResourceId(index, this.mTouchAnchorId);
            } else if (index == 17) {
                int i2 = obtainStyledAttributes.getInt(index, this.mTouchAnchorSide);
                this.mTouchAnchorSide = i2;
                float[] fArr = TOUCH_SIDES[i2];
                this.mTouchAnchorX = fArr[0];
                this.mTouchAnchorY = fArr[1];
            } else if (index == 1) {
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
            } else if (index == 6) {
                this.mMaxVelocity = obtainStyledAttributes.getFloat(index, this.mMaxVelocity);
            } else if (index == 5) {
                this.mMaxAcceleration = obtainStyledAttributes.getFloat(index, this.mMaxAcceleration);
            } else if (index == 7) {
                this.mMoveWhenScrollAtTop = obtainStyledAttributes.getBoolean(index, this.mMoveWhenScrollAtTop);
            } else if (index == 2) {
                this.mDragScale = obtainStyledAttributes.getFloat(index, this.mDragScale);
            } else if (index == 3) {
                this.mDragThreshold = obtainStyledAttributes.getFloat(index, this.mDragThreshold);
            } else if (index == 18) {
                this.mTouchRegionId = obtainStyledAttributes.getResourceId(index, this.mTouchRegionId);
            } else if (index == 9) {
                this.mOnTouchUp = obtainStyledAttributes.getInt(index, this.mOnTouchUp);
            } else if (index == 8) {
                this.mFlags = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == 4) {
                this.mLimitBoundsTo = obtainStyledAttributes.getResourceId(index, 0);
            } else if (index == 10) {
                this.mRotationCenterId = obtainStyledAttributes.getResourceId(index, this.mRotationCenterId);
            } else if (index == 12) {
                this.mSpringDamping = obtainStyledAttributes.getFloat(index, this.mSpringDamping);
            } else if (index == 13) {
                this.mSpringMass = obtainStyledAttributes.getFloat(index, this.mSpringMass);
            } else if (index == 14) {
                this.mSpringStiffness = obtainStyledAttributes.getFloat(index, this.mSpringStiffness);
            } else if (index == 15) {
                this.mSpringStopThreshold = obtainStyledAttributes.getFloat(index, this.mSpringStopThreshold);
            } else if (index == 11) {
                this.mSpringBoundary = obtainStyledAttributes.getInt(index, this.mSpringBoundary);
            } else if (index == 0) {
                this.mAutoCompleteMode = obtainStyledAttributes.getInt(index, this.mAutoCompleteMode);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public final RectF getLimitBoundsTo(MotionLayout motionLayout, RectF rectF) {
        View findViewById;
        int i = this.mLimitBoundsTo;
        if (i == -1 || (findViewById = motionLayout.findViewById(i)) == null) {
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
        this.mRotateCenterX = 0.5f;
        this.mRotateCenterY = 0.5f;
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
        this.mTouchAnchorId = onSwipe.mTouchAnchorId;
        this.mTouchAnchorSide = 0;
        float[] fArr = TOUCH_SIDES[0];
        this.mTouchAnchorX = fArr[0];
        this.mTouchAnchorY = fArr[1];
        this.mTouchSide = 0;
        float[] fArr2 = TOUCH_DIRECTION[0];
        this.mTouchDirectionX = fArr2[0];
        this.mTouchDirectionY = fArr2[1];
        this.mMaxVelocity = onSwipe.mMaxVelocity;
        this.mMaxAcceleration = onSwipe.mMaxAcceleration;
        this.mMoveWhenScrollAtTop = onSwipe.mMoveWhenScrollAtTop;
        this.mDragScale = onSwipe.mDragScale;
        this.mDragThreshold = onSwipe.mDragThreshold;
        this.mTouchRegionId = onSwipe.mTouchRegionId;
        this.mOnTouchUp = 0;
        this.mFlags = 0;
        this.mLimitBoundsTo = onSwipe.mLimitBoundsTo;
        this.mRotationCenterId = onSwipe.mRotationCenterId;
        this.mSpringBoundary = 0;
        this.mSpringDamping = onSwipe.mSpringDamping;
        this.mSpringMass = onSwipe.mSpringMass;
        this.mSpringStiffness = onSwipe.mSpringStiffness;
        this.mSpringStopThreshold = onSwipe.mSpringStopThreshold;
        this.mAutoCompleteMode = 0;
    }
}
