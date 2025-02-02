package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class KeyCycle extends Key {
    private int mCurveFit = 0;
    private int mWaveShape = -1;
    private String mCustomWaveShape = null;
    private float mWavePeriod = Float.NaN;
    private float mWaveOffset = 0.0f;
    private float mWavePhase = 0.0f;
    private float mProgress = Float.NaN;
    private int mWaveVariesBy = -1;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;

    private static class Loader {
        private static SparseIntArray sAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sAttrMap = sparseIntArray;
            sparseIntArray.append(13, 1);
            sAttrMap.append(11, 2);
            sAttrMap.append(14, 3);
            sAttrMap.append(10, 4);
            sAttrMap.append(19, 5);
            sAttrMap.append(17, 6);
            sAttrMap.append(16, 7);
            sAttrMap.append(20, 8);
            sAttrMap.append(0, 9);
            sAttrMap.append(9, 10);
            sAttrMap.append(5, 11);
            sAttrMap.append(6, 12);
            sAttrMap.append(7, 13);
            sAttrMap.append(15, 14);
            sAttrMap.append(3, 15);
            sAttrMap.append(4, 16);
            sAttrMap.append(1, 17);
            sAttrMap.append(2, 18);
            sAttrMap.append(8, 19);
            sAttrMap.append(12, 20);
            sAttrMap.append(18, 21);
        }

        static void access$000(KeyCycle keyCycle, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (sAttrMap.get(index)) {
                    case 1:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = typedArray.getResourceId(index, keyCycle.mTargetId);
                            keyCycle.mTargetId = resourceId;
                            if (resourceId == -1) {
                                keyCycle.mTargetString = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyCycle.mTargetString = typedArray.getString(index);
                            break;
                        } else {
                            keyCycle.mTargetId = typedArray.getResourceId(index, keyCycle.mTargetId);
                            break;
                        }
                    case 2:
                        keyCycle.mFramePosition = typedArray.getInt(index, keyCycle.mFramePosition);
                        break;
                    case 3:
                        typedArray.getString(index);
                        keyCycle.getClass();
                        break;
                    case 4:
                        keyCycle.mCurveFit = typedArray.getInteger(index, keyCycle.mCurveFit);
                        break;
                    case 5:
                        if (typedArray.peekValue(index).type == 3) {
                            keyCycle.mCustomWaveShape = typedArray.getString(index);
                            keyCycle.mWaveShape = 7;
                            break;
                        } else {
                            keyCycle.mWaveShape = typedArray.getInt(index, keyCycle.mWaveShape);
                            break;
                        }
                    case 6:
                        keyCycle.mWavePeriod = typedArray.getFloat(index, keyCycle.mWavePeriod);
                        break;
                    case 7:
                        if (typedArray.peekValue(index).type == 5) {
                            keyCycle.mWaveOffset = typedArray.getDimension(index, keyCycle.mWaveOffset);
                            break;
                        } else {
                            keyCycle.mWaveOffset = typedArray.getFloat(index, keyCycle.mWaveOffset);
                            break;
                        }
                    case 8:
                        keyCycle.mWaveVariesBy = typedArray.getInt(index, keyCycle.mWaveVariesBy);
                        break;
                    case 9:
                        keyCycle.mAlpha = typedArray.getFloat(index, keyCycle.mAlpha);
                        break;
                    case 10:
                        keyCycle.mElevation = typedArray.getDimension(index, keyCycle.mElevation);
                        break;
                    case 11:
                        keyCycle.mRotation = typedArray.getFloat(index, keyCycle.mRotation);
                        break;
                    case 12:
                        keyCycle.mRotationX = typedArray.getFloat(index, keyCycle.mRotationX);
                        break;
                    case 13:
                        keyCycle.mRotationY = typedArray.getFloat(index, keyCycle.mRotationY);
                        break;
                    case 14:
                        keyCycle.mTransitionPathRotate = typedArray.getFloat(index, keyCycle.mTransitionPathRotate);
                        break;
                    case 15:
                        keyCycle.mScaleX = typedArray.getFloat(index, keyCycle.mScaleX);
                        break;
                    case 16:
                        keyCycle.mScaleY = typedArray.getFloat(index, keyCycle.mScaleY);
                        break;
                    case 17:
                        keyCycle.mTranslationX = typedArray.getDimension(index, keyCycle.mTranslationX);
                        break;
                    case 18:
                        keyCycle.mTranslationY = typedArray.getDimension(index, keyCycle.mTranslationY);
                        break;
                    case 19:
                        keyCycle.mTranslationZ = typedArray.getDimension(index, keyCycle.mTranslationZ);
                        break;
                    case 20:
                        keyCycle.mProgress = typedArray.getFloat(index, keyCycle.mProgress);
                        break;
                    case 21:
                        keyCycle.mWavePhase = typedArray.getFloat(index, keyCycle.mWavePhase) / 360.0f;
                        break;
                    default:
                        Log.e("KeyCycle", "unused attribute 0x" + Integer.toHexString(index) + "   " + sAttrMap.get(index));
                        break;
                }
            }
        }
    }

    public KeyCycle() {
        this.mCustomConstraints = new HashMap<>();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b3, code lost:
    
        if (r1.equals("scaleY") == false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addCycleValues(HashMap<String, ViewOscillator> hashMap) {
        ViewOscillator viewOscillator;
        float f;
        ViewOscillator viewOscillator2;
        Iterator<String> it = hashMap.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            char c = 7;
            if (next.startsWith("CUSTOM")) {
                ConstraintAttribute constraintAttribute = this.mCustomConstraints.get(next.substring(7));
                if (constraintAttribute != null && constraintAttribute.getType() == ConstraintAttribute.AttributeType.FLOAT_TYPE && (viewOscillator = hashMap.get(next)) != null) {
                    viewOscillator.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, constraintAttribute.getValueToInterpolate(), constraintAttribute);
                }
            } else {
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
                    case -40300674:
                        if (next.equals("rotation")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -4379043:
                        if (next.equals("elevation")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 37232917:
                        if (next.equals("transitionPathRotate")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 92909918:
                        if (next.equals("alpha")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 156108012:
                        if (next.equals("waveOffset")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1530034690:
                        if (next.equals("wavePhase")) {
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
                        f = this.mRotationX;
                        break;
                    case 1:
                        f = this.mRotationY;
                        break;
                    case 2:
                        f = this.mTranslationX;
                        break;
                    case 3:
                        f = this.mTranslationY;
                        break;
                    case 4:
                        f = this.mTranslationZ;
                        break;
                    case 5:
                        f = this.mProgress;
                        break;
                    case 6:
                        f = this.mScaleX;
                        break;
                    case 7:
                        f = this.mScaleY;
                        break;
                    case '\b':
                        f = this.mRotation;
                        break;
                    case '\t':
                        f = this.mElevation;
                        break;
                    case '\n':
                        f = this.mTransitionPathRotate;
                        break;
                    case 11:
                        f = this.mAlpha;
                        break;
                    case '\f':
                        f = this.mWaveOffset;
                        break;
                    case '\r':
                        f = this.mWavePhase;
                        break;
                    default:
                        if (!next.startsWith("CUSTOM")) {
                            Log.v("WARNING! KeyCycle", "  UNKNOWN  ".concat(next));
                        }
                        f = Float.NaN;
                        break;
                }
                float f2 = f;
                if (!Float.isNaN(f2) && (viewOscillator2 = hashMap.get(next)) != null) {
                    viewOscillator2.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, f2);
                }
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void addValues(HashMap<String, ViewSpline> hashMap) {
        String str = "add " + hashMap.size() + " values";
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int min = Math.min(2, stackTrace.length - 1);
        String str2 = " ";
        for (int i = 1; i <= min; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            String str3 = ".(" + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber() + ") " + stackTrace[i].getMethodName();
            str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str2, " ");
            Log.v("KeyCycle", str + str2 + str3 + str2);
        }
        for (String str4 : hashMap.keySet()) {
            ViewSpline viewSpline = hashMap.get(str4);
            if (viewSpline != null) {
                str4.getClass();
                str4.hashCode();
                switch (str4) {
                    case "rotationX":
                        viewSpline.setPoint(this.mFramePosition, this.mRotationX);
                        break;
                    case "rotationY":
                        viewSpline.setPoint(this.mFramePosition, this.mRotationY);
                        break;
                    case "translationX":
                        viewSpline.setPoint(this.mFramePosition, this.mTranslationX);
                        break;
                    case "translationY":
                        viewSpline.setPoint(this.mFramePosition, this.mTranslationY);
                        break;
                    case "translationZ":
                        viewSpline.setPoint(this.mFramePosition, this.mTranslationZ);
                        break;
                    case "progress":
                        viewSpline.setPoint(this.mFramePosition, this.mProgress);
                        break;
                    case "scaleX":
                        viewSpline.setPoint(this.mFramePosition, this.mScaleX);
                        break;
                    case "scaleY":
                        viewSpline.setPoint(this.mFramePosition, this.mScaleY);
                        break;
                    case "rotation":
                        viewSpline.setPoint(this.mFramePosition, this.mRotation);
                        break;
                    case "elevation":
                        viewSpline.setPoint(this.mFramePosition, this.mElevation);
                        break;
                    case "transitionPathRotate":
                        viewSpline.setPoint(this.mFramePosition, this.mTransitionPathRotate);
                        break;
                    case "alpha":
                        viewSpline.setPoint(this.mFramePosition, this.mAlpha);
                        break;
                    case "waveOffset":
                        viewSpline.setPoint(this.mFramePosition, this.mWaveOffset);
                        break;
                    case "wavePhase":
                        viewSpline.setPoint(this.mFramePosition, this.mWavePhase);
                        break;
                    default:
                        if (str4.startsWith("CUSTOM")) {
                            break;
                        } else {
                            Log.v("WARNING KeyCycle", "  UNKNOWN  ".concat(str4));
                            break;
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
            Iterator<String> it = this.mCustomConstraints.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + it.next());
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        Loader.access$000(this, context.obtainStyledAttributes(attributeSet, R$styleable.KeyCycle));
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo15clone() {
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
