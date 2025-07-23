package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.quality.ParameterCapability;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.TouchListener;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression;
import com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess;
import com.android.internal.widget.remotecompose.core.operations.utilities.NanMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.touch.VelocityEasing;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class TouchExpression extends Operation implements ComponentData, VariableSupport, TouchListener, Serializable {
    private static final String CLASS_NAME = "TouchExpression";
    public static final int MAX_EXPRESSION_SIZE = 32;
    private static final int OP_CODE = 157;
    public static final int STOP_ABSOLUTE_POS = 6;
    public static final int STOP_ENDS = 2;
    public static final int STOP_GENTLY = 0;
    public static final int STOP_INSTANTLY = 1;
    public static final int STOP_NOTCHES_ABSOLUTE = 5;
    public static final int STOP_NOTCHES_EVEN = 3;
    public static final int STOP_NOTCHES_PERCENTS = 4;
    Component mComponent;
    private float mDefValue;
    float mDownTouchValue;
    private int mId;
    float mMax;
    float mMaxAcceleration;
    float mMaxTime;
    float mMaxVelocity;
    float mMin;
    int mMode;
    float[] mNotches;
    private float mOutDefValue;
    float mOutMax;
    float mOutMin;
    float[] mOutStopSpec;
    private float[] mPreCalcValue;
    float mScrBottom;
    float mScrLeft;
    float mScrRight;
    float mScrTop;
    public float[] mSrcExp;
    int mStopMode;
    float[] mStopSpec;
    int mTouchEffects;
    float mValueAtDown;
    float mVelocityId;
    boolean mWrapMode;
    float mValue = 0.0f;
    boolean mUnmodified = true;
    private float mLastChange = Float.NaN;
    private float mLastCalculatedValue = Float.NaN;
    AnimatedFloatExpression mExp = new AnimatedFloatExpression();
    private VelocityEasing mEasyTouch = new VelocityEasing();
    private boolean mEasingToStop = false;
    private float mTouchUpTime = 0.0f;
    private float mCurrentValue = Float.NaN;
    private boolean mTouchDown = false;
    float mLastValue = 0.0f;

    public static int id() {
        return 157;
    }

    public TouchExpression(int i, float[] fArr, float f, float f2, float f3, int i2, float f4, int i3, float[] fArr2, float[] fArr3) {
        this.mMode = 1;
        this.mMax = 1.0f;
        this.mMin = 1.0f;
        this.mOutMax = 1.0f;
        this.mOutMin = 1.0f;
        this.mMaxTime = 1.0f;
        this.mMaxAcceleration = 5.0f;
        this.mMaxVelocity = 7.0f;
        this.mStopMode = 0;
        this.mWrapMode = false;
        this.mId = i;
        this.mSrcExp = fArr;
        this.mDefValue = f;
        this.mOutDefValue = f;
        this.mMode = 6 == i3 ? 1 : 0;
        this.mMax = f3;
        this.mOutMax = f3;
        if (fArr2 != null) {
            this.mOutStopSpec = Arrays.copyOf(fArr2, fArr2.length);
        }
        this.mTouchEffects = i2;
        this.mVelocityId = f4;
        if (Float.isNaN(f2) && Utils.idFromNan(f2) == 0) {
            this.mWrapMode = true;
        } else {
            this.mMin = f2;
            this.mOutMin = f2;
        }
        this.mStopMode = i3;
        this.mStopSpec = fArr2;
        if (fArr3 == null || fArr3.length < 4 || Float.floatToRawIntBits(fArr3[0]) != 0) {
            return;
        }
        this.mMaxTime = fArr3[1];
        this.mMaxAcceleration = fArr3[2];
        this.mMaxVelocity = fArr3[3];
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        float[] fArr = this.mPreCalcValue;
        if (fArr == null || fArr.length != this.mSrcExp.length) {
            this.mPreCalcValue = new float[this.mSrcExp.length];
        }
        float[] fArr2 = this.mOutStopSpec;
        if (fArr2 == null || fArr2.length != this.mStopSpec.length) {
            this.mOutStopSpec = new float[this.mStopSpec.length];
        }
        if (Float.isNaN(this.mMax)) {
            this.mOutMax = remoteContext.getFloat(Utils.idFromNan(this.mMax));
        }
        if (Float.isNaN(this.mMin)) {
            this.mOutMin = remoteContext.getFloat(Utils.idFromNan(this.mMin));
        }
        if (Float.isNaN(this.mDefValue)) {
            this.mOutDefValue = remoteContext.getFloat(Utils.idFromNan(this.mDefValue));
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            float[] fArr3 = this.mSrcExp;
            if (i2 >= fArr3.length) {
                break;
            }
            float f = fArr3[i2];
            if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                this.mPreCalcValue[i2] = remoteContext.getFloat(Utils.idFromNan(f));
            } else {
                this.mPreCalcValue[i2] = this.mSrcExp[i2];
            }
            i2++;
        }
        while (true) {
            float[] fArr4 = this.mStopSpec;
            if (i >= fArr4.length) {
                return;
            }
            float f2 = fArr4[i];
            if (Float.isNaN(f2)) {
                this.mOutStopSpec[i] = remoteContext.getFloat(Utils.idFromNan(f2));
            } else {
                this.mOutStopSpec[i] = f2;
            }
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mMax)) {
            remoteContext.listensTo(Utils.idFromNan(this.mMax), this);
        }
        if (Float.isNaN(this.mMin)) {
            remoteContext.listensTo(Utils.idFromNan(this.mMin), this);
        }
        if (Float.isNaN(this.mDefValue)) {
            remoteContext.listensTo(Utils.idFromNan(this.mDefValue), this);
        }
        if (this.mComponent == null) {
            remoteContext.addTouchListener(this);
        }
        for (float f : this.mSrcExp) {
            if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                remoteContext.listensTo(Utils.idFromNan(f), this);
            }
        }
        for (float f2 : this.mStopSpec) {
            if (Float.isNaN(f2)) {
                remoteContext.listensTo(Utils.idFromNan(f2), this);
            }
        }
    }

    private float wrap(float f) {
        if (!this.mWrapMode) {
            return f;
        }
        float f2 = this.mOutMax;
        float f3 = f % f2;
        return f3 < 0.0f ? f3 + f2 : f3;
    }

    private float getStopPosition(float f, float f2) {
        float f3;
        float max;
        float f4 = f2 / 2.0f;
        float f5 = f + f4;
        if (this.mWrapMode) {
            max = wrap(f) + f4;
            f3 = max;
        } else {
            f3 = f;
            max = Math.max(Math.min(f5, this.mOutMax), this.mOutMin);
        }
        float[] fArr = this.mStopSpec;
        float[] fArr2 = new float[fArr.length];
        boolean z = this.mWrapMode;
        float f6 = z ? 0.0f : this.mOutMin;
        int i = this.mStopMode;
        if (i == 1) {
            return f3;
        }
        if (i == 2) {
            float f7 = f3 + f2;
            float f8 = this.mOutMax;
            return f7 > (f8 + f6) / 2.0f ? f8 : f6;
        }
        int i2 = 0;
        if (i == 3) {
            float[] fArr3 = this.mOutStopSpec;
            float f9 = ((((fArr3.length > 1 ? fArr3[1] : this.mOutMax) - f6) / ((int) fArr3[0])) * ((int) (((max - this.mOutMin) / r11) + 0.5f))) + f6;
            return !z ? Math.max(Math.min(f9, this.mOutMax), f6) : f9;
        }
        if (i != 4) {
            if (i != 5) {
                return max;
            }
            float f10 = this.mOutMin;
            float abs = Math.abs(f10 - max);
            while (i2 < fArr.length) {
                float abs2 = Math.abs(fArr[i2] - max);
                if (abs > abs2) {
                    f10 = fArr[i2];
                    abs = abs2;
                }
                i2++;
            }
            return f10;
        }
        int length = fArr.length;
        float[] fArr4 = new float[length];
        float abs3 = Math.abs(this.mOutMin - max);
        while (i2 < length) {
            float f11 = this.mOutMin;
            float f12 = f11 + (this.mStopSpec[i2] * (this.mOutMax - f11));
            float abs4 = Math.abs(f12 - max);
            if (abs3 > abs4) {
                f6 = f12;
                abs3 = abs4;
            }
            i2++;
        }
        return f6;
    }

    void haptic(RemoteContext remoteContext) {
        int i = this.mTouchEffects;
        int i2 = i & 255;
        if ((32768 & i) != 0) {
            i2 = remoteContext.getInteger(i & 32767);
        }
        remoteContext.hapticEffect(i2);
    }

    void crossNotchCheck(RemoteContext remoteContext) {
        float f = this.mLastValue;
        float f2 = this.mCurrentValue;
        this.mLastValue = f2;
        float f3 = this.mWrapMode ? 0.0f : this.mOutMin;
        float f4 = this.mOutMax;
        int i = this.mStopMode;
        if (i == 1) {
            haptic(remoteContext);
            return;
        }
        int i2 = 0;
        if (i == 2) {
            if (((f3 - f) * (f4 - f) < 0.0f) ^ ((f3 - f2) * (f4 - f2) < 0.0f)) {
                haptic(remoteContext);
                return;
            }
            return;
        }
        if (i == 3) {
            float f5 = (f4 - f3) / ((int) this.mStopSpec[0]);
            if (((int) ((f - f3) / f5)) != ((int) ((f2 - f3) / f5))) {
                haptic(remoteContext);
                return;
            }
            return;
        }
        if (i == 4) {
            while (true) {
                float[] fArr = this.mStopSpec;
                if (i2 >= fArr.length) {
                    return;
                }
                float f6 = this.mOutMin;
                float f7 = f6 + (fArr[i2] * (this.mOutMax - f6));
                if ((f - f7) * (f2 - f7) < 0.0f) {
                    haptic(remoteContext);
                }
                i2++;
            }
        } else {
            if (i != 5) {
                return;
            }
            while (true) {
                float[] fArr2 = this.mStopSpec;
                if (i2 >= fArr2.length) {
                    return;
                }
                float f8 = fArr2[i2];
                if ((f - f8) * (f2 - f8) < 0.0f) {
                    haptic(remoteContext);
                }
                i2++;
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.TouchListener
    public void setComponent(Component component) {
        this.mComponent = component;
        if (component != null) {
            try {
                component.getRoot().setHasTouchListeners(true);
            } catch (Exception unused) {
            }
        }
    }

    private void updateBounds() {
        Component component = this.mComponent;
        if (component != null) {
            float x = component.getX();
            float y = component.getY();
            float width = component.getWidth();
            float height = component.getHeight();
            for (Component parent = component.getParent(); parent != null; parent = parent.getParent()) {
                x += parent.getX();
                y += parent.getY();
            }
            this.mScrLeft = x;
            this.mScrTop = y;
            this.mScrRight = width + x;
            this.mScrBottom = height + y;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        float min;
        float min2;
        updateBounds();
        if (this.mUnmodified) {
            float f = this.mOutDefValue;
            this.mCurrentValue = f;
            remoteContext.loadFloat(this.mId, wrap(f));
            return;
        }
        if (this.mEasingToStop) {
            float animationTime = remoteContext.getAnimationTime() - this.mTouchUpTime;
            float pos = this.mEasyTouch.getPos(animationTime);
            this.mCurrentValue = pos;
            if (this.mWrapMode) {
                min2 = wrap(pos);
            } else {
                min2 = Math.min(Math.max(pos, this.mOutMin), this.mOutMax);
            }
            remoteContext.loadFloat(this.mId, min2);
            if (this.mEasyTouch.getDuration() < animationTime) {
                this.mEasingToStop = false;
            }
            crossNotchCheck(remoteContext);
            remoteContext.needsRepaint();
            return;
        }
        if (this.mTouchDown) {
            AnimatedFloatExpression animatedFloatExpression = this.mExp;
            CollectionsAccess collectionsAccess = remoteContext.getCollectionsAccess();
            float[] fArr = this.mPreCalcValue;
            float eval = animatedFloatExpression.eval(collectionsAccess, fArr, fArr.length);
            if (this.mMode == 0) {
                eval = (eval - this.mDownTouchValue) + this.mValueAtDown;
            }
            if (this.mWrapMode) {
                min = wrap(eval);
            } else {
                min = Math.min(Math.max(eval, this.mOutMin), this.mOutMax);
            }
            this.mCurrentValue = min;
        }
        crossNotchCheck(remoteContext);
        remoteContext.loadFloat(this.mId, wrap(this.mCurrentValue));
    }

    @Override // com.android.internal.widget.remotecompose.core.TouchListener
    public void touchDown(RemoteContext remoteContext, float f, float f2) {
        if (f < this.mScrLeft || f > this.mScrRight || f2 < this.mScrTop || f2 > this.mScrBottom) {
            Utils.log("NOT IN WINDOW " + f + ", " + f2 + " " + this.mScrLeft + ", " + this.mScrTop);
            return;
        }
        this.mEasingToStop = false;
        this.mTouchDown = true;
        this.mUnmodified = false;
        if (this.mMode == 0) {
            this.mValueAtDown = remoteContext.getFloat(this.mId);
            AnimatedFloatExpression animatedFloatExpression = this.mExp;
            CollectionsAccess collectionsAccess = remoteContext.getCollectionsAccess();
            float[] fArr = this.mPreCalcValue;
            this.mDownTouchValue = animatedFloatExpression.eval(collectionsAccess, fArr, fArr.length);
        }
        remoteContext.needsRepaint();
    }

    @Override // com.android.internal.widget.remotecompose.core.TouchListener
    public void touchUp(RemoteContext remoteContext, float f, float f2, float f3, float f4) {
        if (!this.mTouchDown) {
            return;
        }
        int i = 0;
        this.mTouchDown = false;
        if (this.mStopMode == 1) {
            return;
        }
        AnimatedFloatExpression animatedFloatExpression = this.mExp;
        CollectionsAccess collectionsAccess = remoteContext.getCollectionsAccess();
        float[] fArr = this.mPreCalcValue;
        float eval = animatedFloatExpression.eval(collectionsAccess, fArr, fArr.length);
        while (true) {
            float[] fArr2 = this.mSrcExp;
            if (i < fArr2.length) {
                if (Float.isNaN(fArr2[i])) {
                    int idFromNan = Utils.idFromNan(this.mSrcExp[i]);
                    if (idFromNan == 13) {
                        this.mPreCalcValue[i] = f + (1.0E-4f * f3);
                    } else if (idFromNan == 14) {
                        this.mPreCalcValue[i] = f2 + (1.0E-4f * f4);
                    }
                }
                i++;
            } else {
                AnimatedFloatExpression animatedFloatExpression2 = this.mExp;
                CollectionsAccess collectionsAccess2 = remoteContext.getCollectionsAccess();
                float[] fArr3 = this.mPreCalcValue;
                float eval2 = (animatedFloatExpression2.eval(collectionsAccess2, fArr3, fArr3.length) - eval) / 1.0E-4f;
                float f5 = remoteContext.getFloat(this.mId);
                this.mTouchUpTime = remoteContext.getAnimationTime();
                float stopPosition = getStopPosition(f5, eval2);
                this.mEasyTouch.config(f5, stopPosition, eval2, Math.min(2.0f, (this.mMaxTime * Math.abs(stopPosition - f5)) / (this.mMaxVelocity * 2.0f)), this.mMaxAcceleration, this.mMaxVelocity, null);
                this.mEasingToStop = true;
                remoteContext.needsRepaint();
                return;
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.TouchListener
    public void touchDrag(RemoteContext remoteContext, float f, float f2) {
        if (this.mTouchDown) {
            apply(remoteContext);
            remoteContext.needsRepaint();
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mValue, this.mMin, this.mMax, this.mVelocityId, this.mTouchEffects, this.mSrcExp, this.mStopMode, this.mNotches, null);
    }

    public String toString() {
        String[] strArr = new String[this.mSrcExp.length];
        int i = 0;
        while (true) {
            float[] fArr = this.mSrcExp;
            if (i >= fArr.length) {
                break;
            }
            if (Float.isNaN(fArr[i])) {
                strArr[i] = NavigationBarInflaterView.SIZE_MOD_START + Utils.idStringFromNan(this.mSrcExp[i]) + NavigationBarInflaterView.SIZE_MOD_END;
            }
            i++;
        }
        if (this.mPreCalcValue == null) {
            return "TouchExpression[" + this.mId + "] = (" + AnimatedFloatExpression.toString(this.mSrcExp, strArr) + NavigationBarInflaterView.KEY_CODE_END;
        }
        return "TouchExpression[" + this.mId + "] = (" + AnimatedFloatExpression.toString(this.mPreCalcValue, strArr) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2, float f3, float f4, int i2, float[] fArr, int i3, float[] fArr2, float[] fArr3) {
        wireBuffer.start(157);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(fArr.length);
        for (float f5 : fArr) {
            wireBuffer.writeFloat(f5);
        }
        int length = fArr2 != null ? fArr2.length : 0;
        wireBuffer.writeInt((i3 << 16) | length);
        for (int i4 = 0; i4 < length; i4++) {
            wireBuffer.writeFloat(fArr2[i4]);
        }
        int length2 = fArr3 != null ? fArr3.length : 0;
        wireBuffer.writeInt(length2);
        for (int i5 = 0; i5 < length2; i5++) {
            wireBuffer.writeFloat(fArr3[i5]);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        float readFloat = wireBuffer.readFloat();
        float readFloat2 = wireBuffer.readFloat();
        float readFloat3 = wireBuffer.readFloat();
        float readFloat4 = wireBuffer.readFloat();
        int readInt2 = wireBuffer.readInt();
        int readInt3 = wireBuffer.readInt() & 65535;
        if (readInt3 > 32) {
            throw new RuntimeException("Float expression to long");
        }
        float[] fArr = new float[readInt3];
        for (int i = 0; i < readInt3; i++) {
            fArr[i] = wireBuffer.readFloat();
        }
        int readInt4 = wireBuffer.readInt();
        int i2 = 65535 & readInt4;
        int i3 = readInt4 >> 16;
        float[] fArr2 = new float[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            fArr2[i4] = wireBuffer.readFloat();
        }
        int readInt5 = wireBuffer.readInt();
        float[] fArr3 = new float[readInt5];
        for (int i5 = 0; i5 < readInt5; i5++) {
            fArr3[i5] = wireBuffer.readFloat();
        }
        list.add(new TouchExpression(readInt, fArr, readFloat, readFloat2, readFloat3, readInt2, readFloat4, i3, fArr2, fArr3));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 157, CLASS_NAME).description("A Float expression").field(0, "id", "The id of the Color").field(9, "expression_length", "expression length").field(9, "animation_length", "animation description length").field(10, "expression", "expression_length", "Sequence of Floats representing and expression").field(10, "AnimationSpec", "animation_length", "Sequence of Floats representing animation curve").field(1, "duration", "> time in sec").field(0, "bits", "> WRAP|INITALVALUE | TYPE ").field(10, "spec", "> [SPEC PARAMETERS] ").field(1, "initialValue", "> [Initial value] ").field(1, "wrapValue", "> [Wrap value] ");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("defValue", this.mDefValue, this.mOutDefValue).add(ParameterCapability.CAPABILITY_MIN, this.mMin, this.mOutMin).add("max", this.mMax, this.mOutMax).add("mode", Integer.valueOf(this.mMode)).addFloatExpressionSrc("srcExp", this.mSrcExp);
    }
}
