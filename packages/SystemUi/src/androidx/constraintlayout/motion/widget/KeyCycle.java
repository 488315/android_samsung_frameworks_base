package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyCycle extends Key {
    public int mCurveFit = 0;
    public int mWaveShape = -1;
    public String mCustomWaveShape = null;
    public float mWavePeriod = Float.NaN;
    public float mWaveOffset = 0.0f;
    public float mWavePhase = 0.0f;
    public float mProgress = Float.NaN;
    public int mWaveVariesBy = -1;
    public float mAlpha = Float.NaN;
    public float mElevation = Float.NaN;
    public float mRotation = Float.NaN;
    public float mTransitionPathRotate = Float.NaN;
    public float mRotationX = Float.NaN;
    public float mRotationY = Float.NaN;
    public float mScaleX = Float.NaN;
    public float mScaleY = Float.NaN;
    public float mTranslationX = Float.NaN;
    public float mTranslationY = Float.NaN;
    public float mTranslationZ = Float.NaN;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Loader {
        public static final SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(13, 1);
            sparseIntArray.append(11, 2);
            sparseIntArray.append(14, 3);
            sparseIntArray.append(10, 4);
            sparseIntArray.append(19, 5);
            sparseIntArray.append(17, 6);
            sparseIntArray.append(16, 7);
            sparseIntArray.append(20, 8);
            sparseIntArray.append(0, 9);
            sparseIntArray.append(9, 10);
            sparseIntArray.append(5, 11);
            sparseIntArray.append(6, 12);
            sparseIntArray.append(7, 13);
            sparseIntArray.append(15, 14);
            sparseIntArray.append(3, 15);
            sparseIntArray.append(4, 16);
            sparseIntArray.append(1, 17);
            sparseIntArray.append(2, 18);
            sparseIntArray.append(8, 19);
            sparseIntArray.append(12, 20);
            sparseIntArray.append(18, 21);
        }

        private Loader() {
        }
    }

    public KeyCycle() {
        this.mCustomConstraints = new HashMap();
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void addValues(HashMap hashMap) {
        hashMap.size();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int min = Math.min(2, stackTrace.length - 1);
        String str = " ";
        for (int i = 1; i <= min; i++) {
            stackTrace[i].getFileName();
            stackTrace[i].getLineNumber();
            stackTrace[i].getMethodName();
            str = str + " ";
        }
        for (String str2 : hashMap.keySet()) {
            SplineSet splineSet = (SplineSet) hashMap.get(str2);
            if (splineSet != null) {
                str2.getClass();
                switch (str2) {
                    case "rotationX":
                        splineSet.setPoint(this.mRotationX, this.mFramePosition);
                        break;
                    case "rotationY":
                        splineSet.setPoint(this.mRotationY, this.mFramePosition);
                        break;
                    case "translationX":
                        splineSet.setPoint(this.mTranslationX, this.mFramePosition);
                        break;
                    case "translationY":
                        splineSet.setPoint(this.mTranslationY, this.mFramePosition);
                        break;
                    case "translationZ":
                        splineSet.setPoint(this.mTranslationZ, this.mFramePosition);
                        break;
                    case "progress":
                        splineSet.setPoint(this.mProgress, this.mFramePosition);
                        break;
                    case "scaleX":
                        splineSet.setPoint(this.mScaleX, this.mFramePosition);
                        break;
                    case "scaleY":
                        splineSet.setPoint(this.mScaleY, this.mFramePosition);
                        break;
                    case "rotation":
                        splineSet.setPoint(this.mRotation, this.mFramePosition);
                        break;
                    case "elevation":
                        splineSet.setPoint(this.mElevation, this.mFramePosition);
                        break;
                    case "transitionPathRotate":
                        splineSet.setPoint(this.mTransitionPathRotate, this.mFramePosition);
                        break;
                    case "alpha":
                        splineSet.setPoint(this.mAlpha, this.mFramePosition);
                        break;
                    case "waveOffset":
                        splineSet.setPoint(this.mWaveOffset, this.mFramePosition);
                        break;
                    case "wavePhase":
                        splineSet.setPoint(this.mWavePhase, this.mFramePosition);
                        break;
                    default:
                        str2.startsWith("CUSTOM");
                        break;
                }
            }
        }
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
        if (!Float.isNaN(this.mScaleX)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashSet.add("transitionPathRotate");
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
        if (this.mCustomConstraints.size() > 0) {
            Iterator it = this.mCustomConstraints.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + ((String) it.next()));
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyCycle);
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
                    obtainStyledAttributes.getString(index);
                    break;
                case 4:
                    this.mCurveFit = obtainStyledAttributes.getInteger(index, this.mCurveFit);
                    break;
                case 5:
                    if (obtainStyledAttributes.peekValue(index).type == 3) {
                        this.mCustomWaveShape = obtainStyledAttributes.getString(index);
                        this.mWaveShape = 7;
                        break;
                    } else {
                        this.mWaveShape = obtainStyledAttributes.getInt(index, this.mWaveShape);
                        break;
                    }
                case 6:
                    this.mWavePeriod = obtainStyledAttributes.getFloat(index, this.mWavePeriod);
                    break;
                case 7:
                    if (obtainStyledAttributes.peekValue(index).type == 5) {
                        this.mWaveOffset = obtainStyledAttributes.getDimension(index, this.mWaveOffset);
                        break;
                    } else {
                        this.mWaveOffset = obtainStyledAttributes.getFloat(index, this.mWaveOffset);
                        break;
                    }
                case 8:
                    this.mWaveVariesBy = obtainStyledAttributes.getInt(index, this.mWaveVariesBy);
                    break;
                case 9:
                    this.mAlpha = obtainStyledAttributes.getFloat(index, this.mAlpha);
                    break;
                case 10:
                    this.mElevation = obtainStyledAttributes.getDimension(index, this.mElevation);
                    break;
                case 11:
                    this.mRotation = obtainStyledAttributes.getFloat(index, this.mRotation);
                    break;
                case 12:
                    this.mRotationX = obtainStyledAttributes.getFloat(index, this.mRotationX);
                    break;
                case 13:
                    this.mRotationY = obtainStyledAttributes.getFloat(index, this.mRotationY);
                    break;
                case 14:
                    this.mTransitionPathRotate = obtainStyledAttributes.getFloat(index, this.mTransitionPathRotate);
                    break;
                case 15:
                    this.mScaleX = obtainStyledAttributes.getFloat(index, this.mScaleX);
                    break;
                case 16:
                    this.mScaleY = obtainStyledAttributes.getFloat(index, this.mScaleY);
                    break;
                case 17:
                    this.mTranslationX = obtainStyledAttributes.getDimension(index, this.mTranslationX);
                    break;
                case 18:
                    this.mTranslationY = obtainStyledAttributes.getDimension(index, this.mTranslationY);
                    break;
                case 19:
                    this.mTranslationZ = obtainStyledAttributes.getDimension(index, this.mTranslationZ);
                    break;
                case 20:
                    this.mProgress = obtainStyledAttributes.getFloat(index, this.mProgress);
                    break;
                case 21:
                    this.mWavePhase = obtainStyledAttributes.getFloat(index, this.mWavePhase) / 360.0f;
                    break;
                default:
                    Log.e("KeyCycle", "unused attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray2.get(index));
                    break;
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo303clone() {
        KeyCycle keyCycle = new KeyCycle();
        super.copy(this);
        keyCycle.mCurveFit = this.mCurveFit;
        keyCycle.mWaveShape = this.mWaveShape;
        keyCycle.mCustomWaveShape = this.mCustomWaveShape;
        keyCycle.mWavePeriod = this.mWavePeriod;
        keyCycle.mWaveOffset = this.mWaveOffset;
        keyCycle.mWavePhase = this.mWavePhase;
        keyCycle.mProgress = this.mProgress;
        keyCycle.mWaveVariesBy = this.mWaveVariesBy;
        keyCycle.mAlpha = this.mAlpha;
        keyCycle.mElevation = this.mElevation;
        keyCycle.mRotation = this.mRotation;
        keyCycle.mTransitionPathRotate = this.mTransitionPathRotate;
        keyCycle.mRotationX = this.mRotationX;
        keyCycle.mRotationY = this.mRotationY;
        keyCycle.mScaleX = this.mScaleX;
        keyCycle.mScaleY = this.mScaleY;
        keyCycle.mTranslationX = this.mTranslationX;
        keyCycle.mTranslationY = this.mTranslationY;
        keyCycle.mTranslationZ = this.mTranslationZ;
        return keyCycle;
    }
}
