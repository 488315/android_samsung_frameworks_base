package com.android.internal.widget.remotecompose.core.operations;

import android.app.jank.AppJankStats;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression;
import com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess;
import com.android.internal.widget.remotecompose.core.operations.utilities.NanMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.SpringStopEngine;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class FloatExpression extends Operation implements ComponentData, VariableSupport, Serializable {
    private static final String CLASS_NAME = "FloatExpression";
    public static final int MAX_EXPRESSION_SIZE = 32;
    private static final int OP_CODE = 81;
    public FloatAnimation mFloatAnimation;
    public int mId;
    public float[] mPreCalcValue;
    private SpringStopEngine mSpring;
    public float[] mSrcAnimation;
    public float[] mSrcValue;
    private float mLastChange = Float.NaN;
    private float mLastCalculatedValue = Float.NaN;
    AnimatedFloatExpression mExp = new AnimatedFloatExpression();
    float mLastAnimatedValue = Float.NaN;

    public static int id() {
        return 81;
    }

    public FloatExpression(int i, float[] fArr, float[] fArr2) {
        this.mId = i;
        this.mSrcValue = fArr;
        this.mSrcAnimation = fArr2;
        if (fArr2 != null) {
            if (fArr2.length > 4 && fArr2[0] == 0.0f) {
                this.mSpring = new SpringStopEngine(this.mSrcAnimation);
            } else {
                this.mFloatAnimation = new FloatAnimation(this.mSrcAnimation);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        SpringStopEngine springStopEngine;
        FloatAnimation floatAnimation;
        float[] fArr = this.mPreCalcValue;
        if (fArr == null || fArr.length != this.mSrcValue.length) {
            this.mPreCalcValue = new float[this.mSrcValue.length];
        }
        boolean z = false;
        int i = 0;
        boolean z2 = false;
        while (true) {
            float[] fArr2 = this.mSrcValue;
            if (i >= fArr2.length) {
                break;
            }
            float f = fArr2[i];
            if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                int iIdFromNan = Utils.idFromNan(f);
                float f2 = remoteContext.getFloat(Utils.idFromNan(f));
                if (iIdFromNan == 27 && f2 == 0.0f) {
                    f2 = 1.0f;
                }
                if (this.mFloatAnimation != null) {
                    float[] fArr3 = this.mPreCalcValue;
                    if (fArr3[i] != f2) {
                        fArr3[i] = f2;
                        z2 = true;
                    }
                } else if (this.mSpring != null) {
                    float[] fArr4 = this.mPreCalcValue;
                    if (fArr4[i] != f2) {
                        fArr4[i] = f2;
                        z2 = true;
                    }
                } else {
                    this.mPreCalcValue[i] = f2;
                }
            } else {
                this.mPreCalcValue[i] = this.mSrcValue[i];
            }
            i++;
        }
        float fEval = this.mLastCalculatedValue;
        if (z2) {
            AnimatedFloatExpression animatedFloatExpression = this.mExp;
            float[] fArr5 = this.mPreCalcValue;
            fEval = animatedFloatExpression.eval(fArr5, fArr5.length, new float[0]);
            if (fEval != this.mLastCalculatedValue) {
                this.mLastChange = remoteContext.getAnimationTime();
                this.mLastCalculatedValue = fEval;
                z = z2;
            }
        } else {
            z = z2;
        }
        if (!z || (floatAnimation = this.mFloatAnimation) == null) {
            if (!z || (springStopEngine = this.mSpring) == null) {
                return;
            }
            springStopEngine.setTargetValue(fEval);
            return;
        }
        if (Float.isNaN(floatAnimation.getTargetValue())) {
            this.mFloatAnimation.setInitialValue(fEval);
        } else {
            FloatAnimation floatAnimation2 = this.mFloatAnimation;
            floatAnimation2.setInitialValue(floatAnimation2.getTargetValue());
        }
        this.mFloatAnimation.setTargetValue(fEval);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        for (float f : this.mSrcValue) {
            if (Float.isNaN(f) && !AnimatedFloatExpression.isMathOperator(f) && !NanMap.isDataVariable(f)) {
                remoteContext.listensTo(Utils.idFromNan(f), this);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        float animationTime = remoteContext.getAnimationTime();
        if (Float.isNaN(this.mLastChange)) {
            this.mLastChange = animationTime;
        }
        if (this.mFloatAnimation != null) {
            if (Float.isNaN(this.mLastCalculatedValue)) {
                try {
                    AnimatedFloatExpression animatedFloatExpression = this.mExp;
                    CollectionsAccess collectionsAccess = remoteContext.getCollectionsAccess();
                    float[] fArr = this.mPreCalcValue;
                    float fEval = animatedFloatExpression.eval(collectionsAccess, fArr, fArr.length);
                    this.mLastCalculatedValue = fEval;
                    this.mFloatAnimation.setTargetValue(fEval);
                    if (Float.isNaN(this.mFloatAnimation.getInitialValue())) {
                        this.mFloatAnimation.setInitialValue(this.mLastCalculatedValue);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(toString() + " len = " + this.mPreCalcValue.length, e);
                }
            }
            float f = this.mFloatAnimation.get(animationTime - this.mLastChange);
            if (f != this.mLastAnimatedValue) {
                this.mLastAnimatedValue = f;
                remoteContext.loadFloat(this.mId, f);
                remoteContext.needsRepaint();
                markDirty();
                return;
            }
            return;
        }
        SpringStopEngine springStopEngine = this.mSpring;
        if (springStopEngine != null) {
            float f2 = springStopEngine.get(animationTime - this.mLastChange);
            if (f2 != this.mLastAnimatedValue) {
                this.mLastAnimatedValue = f2;
                remoteContext.loadFloat(this.mId, f2);
                remoteContext.needsRepaint();
                return;
            }
            return;
        }
        try {
            AnimatedFloatExpression animatedFloatExpression2 = this.mExp;
            CollectionsAccess collectionsAccess2 = remoteContext.getCollectionsAccess();
            float[] fArr2 = this.mPreCalcValue;
            remoteContext.loadFloat(this.mId, animatedFloatExpression2.eval(collectionsAccess2, fArr2, fArr2.length));
        } catch (Exception e2) {
            throw new RuntimeException(toString() + " len = " + this.mPreCalcValue.length, e2);
        }
    }

    public float evaluate(RemoteContext remoteContext) {
        updateVariables(remoteContext);
        float animationTime = remoteContext.getAnimationTime();
        if (Float.isNaN(this.mLastChange)) {
            this.mLastChange = animationTime;
        }
        AnimatedFloatExpression animatedFloatExpression = this.mExp;
        CollectionsAccess collectionsAccess = remoteContext.getCollectionsAccess();
        float[] fArr = this.mPreCalcValue;
        return animatedFloatExpression.eval(collectionsAccess, fArr, fArr.length);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mSrcValue, this.mSrcAnimation);
    }

    public String toString() {
        String[] strArr = new String[this.mSrcValue.length];
        int i = 0;
        while (true) {
            float[] fArr = this.mSrcValue;
            if (i >= fArr.length) {
                break;
            }
            if (Float.isNaN(fArr[i])) {
                strArr[i] = NavigationBarInflaterView.SIZE_MOD_START + Utils.idStringFromNan(this.mSrcValue[i]) + NavigationBarInflaterView.SIZE_MOD_END;
            }
            i++;
        }
        if (this.mPreCalcValue == null) {
            return "FloatExpression[" + this.mId + "] = (" + AnimatedFloatExpression.toString(this.mSrcValue, strArr) + NavigationBarInflaterView.KEY_CODE_END;
        }
        return "FloatExpression[" + this.mId + "] = (" + AnimatedFloatExpression.toString(this.mPreCalcValue, strArr) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float[] fArr, float[] fArr2) {
        wireBuffer.start(81);
        wireBuffer.writeInt(i);
        int length = fArr.length;
        if (length > 32) {
            throw new RuntimeException(AnimatedFloatExpression.toString(fArr, (String[]) null) + " to long");
        }
        if (fArr2 != null) {
            length |= fArr2.length << 16;
        }
        wireBuffer.writeInt(length);
        for (float f : fArr) {
            wireBuffer.writeFloat(f);
        }
        if (fArr2 != null) {
            for (float f2 : fArr2) {
                wireBuffer.writeFloat(f2);
            }
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        float[] fArr;
        int i = wireBuffer.readInt();
        int i2 = wireBuffer.readInt();
        int i3 = i2 & 65535;
        if (i3 > 32) {
            throw new RuntimeException("Float expression too long");
        }
        int i4 = (i2 >> 16) & 65535;
        float[] fArr2 = new float[i3];
        for (int i5 = 0; i5 < i3; i5++) {
            fArr2[i5] = wireBuffer.readFloat();
        }
        if (i4 != 0) {
            fArr = new float[i4];
            for (int i6 = 0; i6 < i4; i6++) {
                fArr[i6] = wireBuffer.readFloat();
            }
        } else {
            fArr = null;
        }
        list.add(new FloatExpression(i, fArr2, fArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 81, CLASS_NAME).description("A Float expression").field(0, "id", "The id of the Color").field(9, "expression_length", "expression length").field(9, "animation_length", "animation description length").field(10, "expression", "expression_length", "Sequence of Floats representing and expression").field(10, "AnimationSpec", "animation_length", "Sequence of Floats representing animation curve").field(1, "duration", "> time in sec").field(0, "bits", "> WRAP|INITALVALUE | TYPE ").field(10, "spec", "> [SPEC PARAMETERS] ").field(1, "initialValue", "> [Initial value] ").field(1, "wrapValue", "> [Wrap value] ");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.EXPRESSION).addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).addFloatExpressionSrc("srcValues", this.mSrcValue).add(AppJankStats.WIDGET_CATEGORY_ANIMATION, this.mFloatAnimation);
    }
}
