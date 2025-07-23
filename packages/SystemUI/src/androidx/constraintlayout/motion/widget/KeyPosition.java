package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyPosition extends KeyPositionBase {
    public String mTransitionEasing = null;
    public int mPathMotionArc = -1;
    public int mDrawPath = 0;
    public float mPercentWidth = Float.NaN;
    public float mPercentHeight = Float.NaN;
    public float mPercentX = Float.NaN;
    public float mPercentY = Float.NaN;
    public float mAltPercentX = Float.NaN;
    public float mAltPercentY = Float.NaN;
    public int mPositionType = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Loader {
        public static final SparseIntArray sAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
            sparseIntArray.append(R.styleable.KeyPosition_framePosition, 2);
            sparseIntArray.append(R.styleable.KeyPosition_transitionEasing, 3);
            sparseIntArray.append(R.styleable.KeyPosition_curveFit, 4);
            sparseIntArray.append(R.styleable.KeyPosition_drawPath, 5);
            sparseIntArray.append(R.styleable.KeyPosition_percentX, 6);
            sparseIntArray.append(R.styleable.KeyPosition_percentY, 7);
            sparseIntArray.append(R.styleable.KeyPosition_keyPositionType, 9);
            sparseIntArray.append(R.styleable.KeyPosition_sizePercent, 8);
            sparseIntArray.append(R.styleable.KeyPosition_percentWidth, 11);
            sparseIntArray.append(R.styleable.KeyPosition_percentHeight, 12);
            sparseIntArray.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        private Loader() {
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.KeyPosition);
        SparseIntArray sparseIntArray = Loader.sAttrMap;
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray2 = Loader.sAttrMap;
            switch (sparseIntArray2.get(index)) {
                case 1:
                    if (MotionLayout.IS_IN_EDIT_MODE) {
                        int resourceId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                        this.mTargetId = resourceId;
                        if (resourceId == -1) {
                            this.mTargetString = obtainStyledAttributes.getString(index);
                            break;
                        } else {
                            break;
                        }
                    } else if (obtainStyledAttributes.peekValue(index).type == 3) {
                        this.mTargetString = obtainStyledAttributes.getString(index);
                        break;
                    } else {
                        this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                        break;
                    }
                case 2:
                    this.mFramePosition = obtainStyledAttributes.getInt(index, this.mFramePosition);
                    break;
                case 3:
                    if (obtainStyledAttributes.peekValue(index).type == 3) {
                        this.mTransitionEasing = obtainStyledAttributes.getString(index);
                        break;
                    } else {
                        this.mTransitionEasing = Easing.NAMED_EASING[obtainStyledAttributes.getInteger(index, 0)];
                        break;
                    }
                case 4:
                    this.mCurveFit = obtainStyledAttributes.getInteger(index, this.mCurveFit);
                    break;
                case 5:
                    this.mDrawPath = obtainStyledAttributes.getInt(index, this.mDrawPath);
                    break;
                case 6:
                    this.mPercentX = obtainStyledAttributes.getFloat(index, this.mPercentX);
                    break;
                case 7:
                    this.mPercentY = obtainStyledAttributes.getFloat(index, this.mPercentY);
                    break;
                case 8:
                    float f = obtainStyledAttributes.getFloat(index, this.mPercentHeight);
                    this.mPercentWidth = f;
                    this.mPercentHeight = f;
                    break;
                case 9:
                    this.mPositionType = obtainStyledAttributes.getInt(index, this.mPositionType);
                    break;
                case 10:
                    this.mPathMotionArc = obtainStyledAttributes.getInt(index, this.mPathMotionArc);
                    break;
                case 11:
                    this.mPercentWidth = obtainStyledAttributes.getFloat(index, this.mPercentWidth);
                    break;
                case 12:
                    this.mPercentHeight = obtainStyledAttributes.getFloat(index, this.mPercentHeight);
                    break;
                default:
                    Log.e("KeyPosition", "unused attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray2.get(index));
                    break;
            }
        }
        if (this.mFramePosition == -1) {
            Log.e("KeyPosition", "no frame position");
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo886clone() {
        KeyPosition keyPosition = new KeyPosition();
        super.copy(this);
        keyPosition.mTransitionEasing = this.mTransitionEasing;
        keyPosition.mPathMotionArc = this.mPathMotionArc;
        keyPosition.mDrawPath = this.mDrawPath;
        keyPosition.mPercentWidth = this.mPercentWidth;
        keyPosition.mPercentHeight = Float.NaN;
        keyPosition.mPercentX = this.mPercentX;
        keyPosition.mPercentY = this.mPercentY;
        keyPosition.mAltPercentX = this.mAltPercentX;
        keyPosition.mAltPercentY = this.mAltPercentY;
        return keyPosition;
    }
}
