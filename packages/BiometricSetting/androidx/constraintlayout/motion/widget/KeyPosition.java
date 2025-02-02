package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class KeyPosition extends KeyPositionBase {
    String mTransitionEasing = null;
    int mPathMotionArc = -1;
    int mDrawPath = 0;
    float mPercentWidth = Float.NaN;
    float mPercentHeight = Float.NaN;
    float mPercentX = Float.NaN;
    float mPercentY = Float.NaN;
    float mAltPercentX = Float.NaN;
    float mAltPercentY = Float.NaN;
    int mPositionType = 0;

    private static class Loader {
        private static SparseIntArray sAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sAttrMap = sparseIntArray;
            sparseIntArray.append(4, 1);
            sAttrMap.append(2, 2);
            sAttrMap.append(11, 3);
            sAttrMap.append(0, 4);
            sAttrMap.append(1, 5);
            sAttrMap.append(8, 6);
            sAttrMap.append(9, 7);
            sAttrMap.append(3, 9);
            sAttrMap.append(10, 8);
            sAttrMap.append(7, 11);
            sAttrMap.append(6, 12);
            sAttrMap.append(5, 10);
        }

        static void access$000(KeyPosition keyPosition, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (sAttrMap.get(index)) {
                    case 1:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = typedArray.getResourceId(index, keyPosition.mTargetId);
                            keyPosition.mTargetId = resourceId;
                            if (resourceId == -1) {
                                keyPosition.mTargetString = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyPosition.mTargetString = typedArray.getString(index);
                            break;
                        } else {
                            keyPosition.mTargetId = typedArray.getResourceId(index, keyPosition.mTargetId);
                            break;
                        }
                    case 2:
                        keyPosition.mFramePosition = typedArray.getInt(index, keyPosition.mFramePosition);
                        break;
                    case 3:
                        if (typedArray.peekValue(index).type == 3) {
                            keyPosition.mTransitionEasing = typedArray.getString(index);
                            break;
                        } else {
                            keyPosition.mTransitionEasing = Easing.NAMED_EASING[typedArray.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        keyPosition.mCurveFit = typedArray.getInteger(index, keyPosition.mCurveFit);
                        break;
                    case 5:
                        keyPosition.mDrawPath = typedArray.getInt(index, keyPosition.mDrawPath);
                        break;
                    case 6:
                        keyPosition.mPercentX = typedArray.getFloat(index, keyPosition.mPercentX);
                        break;
                    case 7:
                        keyPosition.mPercentY = typedArray.getFloat(index, keyPosition.mPercentY);
                        break;
                    case 8:
                        float f = typedArray.getFloat(index, keyPosition.mPercentHeight);
                        keyPosition.mPercentWidth = f;
                        keyPosition.mPercentHeight = f;
                        break;
                    case 9:
                        keyPosition.mPositionType = typedArray.getInt(index, keyPosition.mPositionType);
                        break;
                    case 10:
                        keyPosition.mPathMotionArc = typedArray.getInt(index, keyPosition.mPathMotionArc);
                        break;
                    case 11:
                        keyPosition.mPercentWidth = typedArray.getFloat(index, keyPosition.mPercentWidth);
                        break;
                    case 12:
                        keyPosition.mPercentHeight = typedArray.getFloat(index, keyPosition.mPercentHeight);
                        break;
                    default:
                        Log.e("KeyPosition", "unused attribute 0x" + Integer.toHexString(index) + "   " + sAttrMap.get(index));
                        break;
                }
            }
            if (keyPosition.mFramePosition == -1) {
                Log.e("KeyPosition", "no frame position");
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        Loader.access$000(this, context.obtainStyledAttributes(attributeSet, R$styleable.KeyPosition));
    }

    public final void setType() {
        this.mPositionType = 0;
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
    public final Key mo15clone() {
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
    public final void addValues(HashMap<String, ViewSpline> hashMap) {
    }
}
