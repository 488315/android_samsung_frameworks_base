package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class KeyAttributes extends Key {
    private int mCurveFit = -1;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private float mProgress = Float.NaN;

    private static class Loader {
        private static SparseIntArray sAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sAttrMap = sparseIntArray;
            sparseIntArray.append(0, 1);
            sAttrMap.append(11, 2);
            sAttrMap.append(7, 4);
            sAttrMap.append(8, 5);
            sAttrMap.append(9, 6);
            sAttrMap.append(1, 19);
            sAttrMap.append(2, 20);
            sAttrMap.append(5, 7);
            sAttrMap.append(18, 8);
            sAttrMap.append(17, 9);
            sAttrMap.append(15, 10);
            sAttrMap.append(13, 12);
            sAttrMap.append(12, 13);
            sAttrMap.append(6, 14);
            sAttrMap.append(3, 15);
            sAttrMap.append(4, 16);
            sAttrMap.append(10, 17);
            sAttrMap.append(14, 18);
        }

        public static void read(KeyAttributes keyAttributes, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (sAttrMap.get(index)) {
                    case 1:
                        keyAttributes.mAlpha = typedArray.getFloat(index, keyAttributes.mAlpha);
                        break;
                    case 2:
                        keyAttributes.mElevation = typedArray.getDimension(index, keyAttributes.mElevation);
                        break;
                    case 3:
                    case 11:
                    default:
                        Log.e("KeyAttribute", "unused attribute 0x" + Integer.toHexString(index) + "   " + sAttrMap.get(index));
                        break;
                    case 4:
                        keyAttributes.mRotation = typedArray.getFloat(index, keyAttributes.mRotation);
                        break;
                    case 5:
                        keyAttributes.mRotationX = typedArray.getFloat(index, keyAttributes.mRotationX);
                        break;
                    case 6:
                        keyAttributes.mRotationY = typedArray.getFloat(index, keyAttributes.mRotationY);
                        break;
                    case 7:
                        keyAttributes.mScaleX = typedArray.getFloat(index, keyAttributes.mScaleX);
                        break;
                    case 8:
                        keyAttributes.mTransitionPathRotate = typedArray.getFloat(index, keyAttributes.mTransitionPathRotate);
                        break;
                    case 9:
                        typedArray.getString(index);
                        keyAttributes.getClass();
                        break;
                    case 10:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = typedArray.getResourceId(index, keyAttributes.mTargetId);
                            keyAttributes.mTargetId = resourceId;
                            if (resourceId == -1) {
                                keyAttributes.mTargetString = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyAttributes.mTargetString = typedArray.getString(index);
                            break;
                        } else {
                            keyAttributes.mTargetId = typedArray.getResourceId(index, keyAttributes.mTargetId);
                            break;
                        }
                    case 12:
                        keyAttributes.mFramePosition = typedArray.getInt(index, keyAttributes.mFramePosition);
                        break;
                    case 13:
                        keyAttributes.mCurveFit = typedArray.getInteger(index, keyAttributes.mCurveFit);
                        break;
                    case 14:
                        keyAttributes.mScaleY = typedArray.getFloat(index, keyAttributes.mScaleY);
                        break;
                    case 15:
                        keyAttributes.mTranslationX = typedArray.getDimension(index, keyAttributes.mTranslationX);
                        break;
                    case 16:
                        keyAttributes.mTranslationY = typedArray.getDimension(index, keyAttributes.mTranslationY);
                        break;
                    case 17:
                        keyAttributes.mTranslationZ = typedArray.getDimension(index, keyAttributes.mTranslationZ);
                        break;
                    case 18:
                        keyAttributes.mProgress = typedArray.getFloat(index, keyAttributes.mProgress);
                        break;
                    case 19:
                        keyAttributes.mPivotX = typedArray.getDimension(index, keyAttributes.mPivotX);
                        break;
                    case 20:
                        keyAttributes.mPivotY = typedArray.getDimension(index, keyAttributes.mPivotY);
                        break;
                }
            }
        }
    }

    public KeyAttributes() {
        this.mCustomConstraints = new HashMap<>();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x009a, code lost:
    
        if (r1.equals("scaleY") == false) goto L15;
     */
    @Override // androidx.constraintlayout.motion.widget.Key
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addValues(HashMap<String, ViewSpline> hashMap) {
        Iterator<String> it = hashMap.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            ViewSpline viewSpline = hashMap.get(next);
            if (viewSpline != null) {
                char c = 7;
                if (!next.startsWith("CUSTOM")) {
                    switch (next.hashCode()) {
                        case -1249320806:
                            if (next.equals("rotationX")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1249320805:
                            if (next.equals("rotationY")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1225497657:
                            if (next.equals("translationX")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1225497656:
                            if (next.equals("translationY")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1225497655:
                            if (next.equals("translationZ")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1001078227:
                            if (next.equals("progress")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case -908189618:
                            if (next.equals("scaleX")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case -908189617:
                            break;
                        case -760884510:
                            if (next.equals("transformPivotX")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case -760884509:
                            if (next.equals("transformPivotY")) {
                                c = '\t';
                                break;
                            }
                            c = 65535;
                            break;
                        case -40300674:
                            if (next.equals("rotation")) {
                                c = '\n';
                                break;
                            }
                            c = 65535;
                            break;
                        case -4379043:
                            if (next.equals("elevation")) {
                                c = 11;
                                break;
                            }
                            c = 65535;
                            break;
                        case 37232917:
                            if (next.equals("transitionPathRotate")) {
                                c = '\f';
                                break;
                            }
                            c = 65535;
                            break;
                        case 92909918:
                            if (next.equals("alpha")) {
                                c = '\r';
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            if (!Float.isNaN(this.mRotationX)) {
                                viewSpline.setPoint(this.mFramePosition, this.mRotationX);
                                break;
                            } else {
                                break;
                            }
                        case 1:
                            if (!Float.isNaN(this.mRotationY)) {
                                viewSpline.setPoint(this.mFramePosition, this.mRotationY);
                                break;
                            } else {
                                break;
                            }
                        case 2:
                            if (!Float.isNaN(this.mTranslationX)) {
                                viewSpline.setPoint(this.mFramePosition, this.mTranslationX);
                                break;
                            } else {
                                break;
                            }
                        case 3:
                            if (!Float.isNaN(this.mTranslationY)) {
                                viewSpline.setPoint(this.mFramePosition, this.mTranslationY);
                                break;
                            } else {
                                break;
                            }
                        case 4:
                            if (!Float.isNaN(this.mTranslationZ)) {
                                viewSpline.setPoint(this.mFramePosition, this.mTranslationZ);
                                break;
                            } else {
                                break;
                            }
                        case 5:
                            if (!Float.isNaN(this.mProgress)) {
                                viewSpline.setPoint(this.mFramePosition, this.mProgress);
                                break;
                            } else {
                                break;
                            }
                        case 6:
                            if (!Float.isNaN(this.mScaleX)) {
                                viewSpline.setPoint(this.mFramePosition, this.mScaleX);
                                break;
                            } else {
                                break;
                            }
                        case 7:
                            if (!Float.isNaN(this.mScaleY)) {
                                viewSpline.setPoint(this.mFramePosition, this.mScaleY);
                                break;
                            } else {
                                break;
                            }
                        case '\b':
                            if (!Float.isNaN(this.mRotationX)) {
                                viewSpline.setPoint(this.mFramePosition, this.mPivotX);
                                break;
                            } else {
                                break;
                            }
                        case '\t':
                            if (!Float.isNaN(this.mRotationY)) {
                                viewSpline.setPoint(this.mFramePosition, this.mPivotY);
                                break;
                            } else {
                                break;
                            }
                        case '\n':
                            if (!Float.isNaN(this.mRotation)) {
                                viewSpline.setPoint(this.mFramePosition, this.mRotation);
                                break;
                            } else {
                                break;
                            }
                        case 11:
                            if (!Float.isNaN(this.mElevation)) {
                                viewSpline.setPoint(this.mFramePosition, this.mElevation);
                                break;
                            } else {
                                break;
                            }
                        case '\f':
                            if (!Float.isNaN(this.mTransitionPathRotate)) {
                                viewSpline.setPoint(this.mFramePosition, this.mTransitionPathRotate);
                                break;
                            } else {
                                break;
                            }
                        case '\r':
                            if (!Float.isNaN(this.mAlpha)) {
                                viewSpline.setPoint(this.mFramePosition, this.mAlpha);
                                break;
                            } else {
                                break;
                            }
                    }
                } else {
                    ConstraintAttribute constraintAttribute = this.mCustomConstraints.get(next.substring(7));
                    if (constraintAttribute != null) {
                        ((ViewSpline.CustomSet) viewSpline).setPoint(this.mFramePosition, constraintAttribute);
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.mAlpha)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.mRotationX)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.mPivotX)) {
            hashSet.add("transformPivotX");
        }
        if (!Float.isNaN(this.mPivotY)) {
            hashSet.add("transformPivotY");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.mProgress)) {
            hashSet.add("progress");
        }
        if (this.mCustomConstraints.size() > 0) {
            Iterator<String> it = this.mCustomConstraints.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + it.next());
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R$styleable.KeyAttribute));
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void setInterpolation(HashMap<String, Integer> hashMap) {
        if (this.mCurveFit == -1) {
            return;
        }
        if (!Float.isNaN(this.mAlpha)) {
            hashMap.put("alpha", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mElevation)) {
            hashMap.put("elevation", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotation)) {
            hashMap.put("rotation", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationX)) {
            hashMap.put("rotationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationY)) {
            hashMap.put("rotationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotX)) {
            hashMap.put("transformPivotX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotY)) {
            hashMap.put("transformPivotY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationX)) {
            hashMap.put("translationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationY)) {
            hashMap.put("translationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            hashMap.put("translationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashMap.put("transitionPathRotate", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashMap.put("scaleX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashMap.put("scaleY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mProgress)) {
            hashMap.put("progress", Integer.valueOf(this.mCurveFit));
        }
        if (this.mCustomConstraints.size() > 0) {
            Iterator<String> it = this.mCustomConstraints.keySet().iterator();
            while (it.hasNext()) {
                hashMap.put("CUSTOM," + it.next(), Integer.valueOf(this.mCurveFit));
            }
        }
    }

    public final void setValue(Object obj, String str) {
        switch (str) {
            case "motionProgress":
                this.mProgress = Key.toFloat(obj);
                break;
            case "transitionEasing":
                obj.toString();
                break;
            case "rotationX":
                this.mRotationX = Key.toFloat(obj);
                break;
            case "rotationY":
                this.mRotationY = Key.toFloat(obj);
                break;
            case "translationX":
                this.mTranslationX = Key.toFloat(obj);
                break;
            case "translationY":
                this.mTranslationY = Key.toFloat(obj);
                break;
            case "translationZ":
                this.mTranslationZ = Key.toFloat(obj);
                break;
            case "scaleX":
                this.mScaleX = Key.toFloat(obj);
                break;
            case "scaleY":
                this.mScaleY = Key.toFloat(obj);
                break;
            case "transformPivotX":
                this.mPivotX = Key.toFloat(obj);
                break;
            case "transformPivotY":
                this.mPivotY = Key.toFloat(obj);
                break;
            case "rotation":
                this.mRotation = Key.toFloat(obj);
                break;
            case "elevation":
                this.mElevation = Key.toFloat(obj);
                break;
            case "transitionPathRotate":
                this.mTransitionPathRotate = Key.toFloat(obj);
                break;
            case "alpha":
                this.mAlpha = Key.toFloat(obj);
                break;
            case "curveFit":
                this.mCurveFit = obj instanceof Integer ? ((Integer) obj).intValue() : Integer.parseInt(obj.toString());
                break;
            case "visibility":
                if (!(obj instanceof Boolean)) {
                    Boolean.parseBoolean(obj.toString());
                    break;
                } else {
                    ((Boolean) obj).booleanValue();
                    break;
                }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo15clone() {
        KeyAttributes keyAttributes = new KeyAttributes();
        super.copy(this);
        keyAttributes.mCurveFit = this.mCurveFit;
        keyAttributes.mAlpha = this.mAlpha;
        keyAttributes.mElevation = this.mElevation;
        keyAttributes.mRotation = this.mRotation;
        keyAttributes.mRotationX = this.mRotationX;
        keyAttributes.mRotationY = this.mRotationY;
        keyAttributes.mPivotX = this.mPivotX;
        keyAttributes.mPivotY = this.mPivotY;
        keyAttributes.mTransitionPathRotate = this.mTransitionPathRotate;
        keyAttributes.mScaleX = this.mScaleX;
        keyAttributes.mScaleY = this.mScaleY;
        keyAttributes.mTranslationX = this.mTranslationX;
        keyAttributes.mTranslationY = this.mTranslationY;
        keyAttributes.mTranslationZ = this.mTranslationZ;
        keyAttributes.mProgress = this.mProgress;
        return keyAttributes;
    }
}
