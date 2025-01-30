package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyPosition extends KeyPositionBase {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Loader {
        public static final SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(4, 1);
            sparseIntArray.append(2, 2);
            sparseIntArray.append(11, 3);
            sparseIntArray.append(0, 4);
            sparseIntArray.append(1, 5);
            sparseIntArray.append(8, 6);
            sparseIntArray.append(9, 7);
            sparseIntArray.append(3, 9);
            sparseIntArray.append(10, 8);
            sparseIntArray.append(7, 11);
            sparseIntArray.append(6, 12);
            sparseIntArray.append(5, 10);
        }

        private Loader() {
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyPosition);
        SparseIntArray sparseIntArray = Loader.mAttrMap;
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray2 = Loader.mAttrMap;
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

    public final void setValue(Object obj, String str) {
        switch (str) {
            case "transitionEasing":
                this.mTransitionEasing = obj.toString();
                break;
            case "percentWidth":
                this.mPercentWidth = Key.toFloat(obj);
                break;
            case "percentHeight":
                this.mPercentHeight = Key.toFloat(obj);
                break;
            case "drawPath":
                this.mDrawPath = obj instanceof Integer ? ((Integer) obj).intValue() : Integer.parseInt(obj.toString());
                break;
            case "sizePercent":
                float f = Key.toFloat(obj);
                this.mPercentWidth = f;
                this.mPercentHeight = f;
                break;
            case "percentX":
                this.mPercentX = Key.toFloat(obj);
                break;
            case "percentY":
                this.mPercentY = Key.toFloat(obj);
                break;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo303clone() {
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

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void addValues(HashMap hashMap) {
    }
}
