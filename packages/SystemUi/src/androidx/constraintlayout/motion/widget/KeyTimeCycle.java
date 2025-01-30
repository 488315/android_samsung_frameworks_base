package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyTimeCycle extends Key {
    public int mCurveFit = -1;
    public float mAlpha = Float.NaN;
    public float mElevation = Float.NaN;
    public float mRotation = Float.NaN;
    public float mRotationX = Float.NaN;
    public float mRotationY = Float.NaN;
    public float mTransitionPathRotate = Float.NaN;
    public float mScaleX = Float.NaN;
    public float mScaleY = Float.NaN;
    public float mTranslationX = Float.NaN;
    public float mTranslationY = Float.NaN;
    public float mTranslationZ = Float.NaN;
    public float mProgress = Float.NaN;
    public int mWaveShape = 0;
    public float mWavePeriod = Float.NaN;
    public float mWaveOffset = 0.0f;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Loader {
        public static final SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(0, 1);
            sparseIntArray.append(9, 2);
            sparseIntArray.append(5, 4);
            sparseIntArray.append(6, 5);
            sparseIntArray.append(7, 6);
            sparseIntArray.append(3, 7);
            sparseIntArray.append(15, 8);
            sparseIntArray.append(14, 9);
            sparseIntArray.append(13, 10);
            sparseIntArray.append(11, 12);
            sparseIntArray.append(10, 13);
            sparseIntArray.append(4, 14);
            sparseIntArray.append(1, 15);
            sparseIntArray.append(2, 16);
            sparseIntArray.append(8, 17);
            sparseIntArray.append(12, 18);
            sparseIntArray.append(18, 20);
            sparseIntArray.append(17, 21);
            sparseIntArray.append(20, 19);
        }

        private Loader() {
        }
    }

    public KeyTimeCycle() {
        this.mCustomConstraints = new HashMap();
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void addValues(HashMap hashMap) {
        throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void getAttributeNames(HashSet hashSet) {
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
            Iterator it = this.mCustomConstraints.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + ((String) it.next()));
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyTimeCycle);
        SparseIntArray sparseIntArray = Loader.mAttrMap;
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray2 = Loader.mAttrMap;
            switch (sparseIntArray2.get(index)) {
                case 1:
                    this.mAlpha = obtainStyledAttributes.getFloat(index, this.mAlpha);
                    break;
                case 2:
                    this.mElevation = obtainStyledAttributes.getDimension(index, this.mElevation);
                    break;
                case 3:
                case 11:
                default:
                    Log.e("KeyTimeCycle", "unused attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray2.get(index));
                    break;
                case 4:
                    this.mRotation = obtainStyledAttributes.getFloat(index, this.mRotation);
                    break;
                case 5:
                    this.mRotationX = obtainStyledAttributes.getFloat(index, this.mRotationX);
                    break;
                case 6:
                    this.mRotationY = obtainStyledAttributes.getFloat(index, this.mRotationY);
                    break;
                case 7:
                    this.mScaleX = obtainStyledAttributes.getFloat(index, this.mScaleX);
                    break;
                case 8:
                    this.mTransitionPathRotate = obtainStyledAttributes.getFloat(index, this.mTransitionPathRotate);
                    break;
                case 9:
                    obtainStyledAttributes.getString(index);
                    break;
                case 10:
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
                case 12:
                    this.mFramePosition = obtainStyledAttributes.getInt(index, this.mFramePosition);
                    break;
                case 13:
                    this.mCurveFit = obtainStyledAttributes.getInteger(index, this.mCurveFit);
                    break;
                case 14:
                    this.mScaleY = obtainStyledAttributes.getFloat(index, this.mScaleY);
                    break;
                case 15:
                    this.mTranslationX = obtainStyledAttributes.getDimension(index, this.mTranslationX);
                    break;
                case 16:
                    this.mTranslationY = obtainStyledAttributes.getDimension(index, this.mTranslationY);
                    break;
                case 17:
                    this.mTranslationZ = obtainStyledAttributes.getDimension(index, this.mTranslationZ);
                    break;
                case 18:
                    this.mProgress = obtainStyledAttributes.getFloat(index, this.mProgress);
                    break;
                case 19:
                    if (obtainStyledAttributes.peekValue(index).type == 3) {
                        obtainStyledAttributes.getString(index);
                        this.mWaveShape = 7;
                        break;
                    } else {
                        this.mWaveShape = obtainStyledAttributes.getInt(index, this.mWaveShape);
                        break;
                    }
                case 20:
                    this.mWavePeriod = obtainStyledAttributes.getFloat(index, this.mWavePeriod);
                    break;
                case 21:
                    if (obtainStyledAttributes.peekValue(index).type == 5) {
                        this.mWaveOffset = obtainStyledAttributes.getDimension(index, this.mWaveOffset);
                        break;
                    } else {
                        this.mWaveOffset = obtainStyledAttributes.getFloat(index, this.mWaveOffset);
                        break;
                    }
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void setInterpolation(HashMap hashMap) {
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
        if (!Float.isNaN(this.mScaleX)) {
            hashMap.put("scaleY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mProgress)) {
            hashMap.put("progress", Integer.valueOf(this.mCurveFit));
        }
        if (this.mCustomConstraints.size() > 0) {
            Iterator it = this.mCustomConstraints.keySet().iterator();
            while (it.hasNext()) {
                hashMap.put(KeyAttributes$$ExternalSyntheticOutline0.m21m("CUSTOM,", (String) it.next()), Integer.valueOf(this.mCurveFit));
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo303clone() {
        KeyTimeCycle keyTimeCycle = new KeyTimeCycle();
        super.copy(this);
        keyTimeCycle.mCurveFit = this.mCurveFit;
        keyTimeCycle.mWaveShape = this.mWaveShape;
        keyTimeCycle.mWavePeriod = this.mWavePeriod;
        keyTimeCycle.mWaveOffset = this.mWaveOffset;
        keyTimeCycle.mProgress = this.mProgress;
        keyTimeCycle.mAlpha = this.mAlpha;
        keyTimeCycle.mElevation = this.mElevation;
        keyTimeCycle.mRotation = this.mRotation;
        keyTimeCycle.mTransitionPathRotate = this.mTransitionPathRotate;
        keyTimeCycle.mRotationX = this.mRotationX;
        keyTimeCycle.mRotationY = this.mRotationY;
        keyTimeCycle.mScaleX = this.mScaleX;
        keyTimeCycle.mScaleY = this.mScaleY;
        keyTimeCycle.mTranslationX = this.mTranslationX;
        keyTimeCycle.mTranslationY = this.mTranslationY;
        keyTimeCycle.mTranslationZ = this.mTranslationZ;
        return keyTimeCycle;
    }
}
