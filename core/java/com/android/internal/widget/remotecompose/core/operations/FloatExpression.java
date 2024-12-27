package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes5.dex */
public class FloatExpression implements Operation, VariableSupport {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public FloatAnimation mFloatAnimation;
    public int mId;
    public float[] mPreCalcValue;
    public float[] mSrcAnimation;
    public float[] mSrcValue;
    private float mLastChange = Float.NaN;
    AnimatedFloatExpression mExp = new AnimatedFloatExpression();

    public FloatExpression(int id, float[] value, float[] animation) {
        this.mId = id;
        this.mSrcValue = value;
        this.mSrcAnimation = animation;
        if (this.mSrcAnimation != null) {
            this.mFloatAnimation = new FloatAnimation(this.mSrcAnimation);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        if (this.mPreCalcValue == null || this.mPreCalcValue.length != this.mSrcValue.length) {
            this.mPreCalcValue = new float[this.mSrcValue.length];
        }
        boolean value_changed = false;
        for (int i = 0; i < this.mSrcValue.length; i++) {
            float v = this.mSrcValue[i];
            if (Float.isNaN(v) && !AnimatedFloatExpression.isMathOperator(v)) {
                float newValue = context.getFloat(Utils.idFromNan(v));
                if (this.mFloatAnimation == null) {
                    this.mPreCalcValue[i] = newValue;
                } else if (this.mPreCalcValue[i] != newValue) {
                    this.mLastChange = context.getAnimationTime();
                    value_changed = true;
                    this.mPreCalcValue[i] = newValue;
                }
            } else {
                this.mPreCalcValue[i] = this.mSrcValue[i];
            }
        }
        if (value_changed && this.mFloatAnimation != null) {
            float v2 = this.mExp.eval(Arrays.copyOf(this.mPreCalcValue, this.mPreCalcValue.length), new float[0]);
            if (Float.isNaN(this.mFloatAnimation.getTargetValue())) {
                this.mFloatAnimation.setInitialValue(v2);
            } else {
                this.mFloatAnimation.setInitialValue(this.mFloatAnimation.getTargetValue());
            }
            this.mFloatAnimation.setTargetValue(v2);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        for (int i = 0; i < this.mSrcValue.length; i++) {
            float v = this.mSrcValue[i];
            if (Float.isNaN(v) && !AnimatedFloatExpression.isMathOperator(v)) {
                context.listensTo(Utils.idFromNan(v), this);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        updateVariables(context);
        float t = context.getAnimationTime();
        if (Float.isNaN(this.mLastChange)) {
            this.mLastChange = t;
        }
        if (this.mFloatAnimation != null) {
            float f = this.mFloatAnimation.get(t - this.mLastChange);
            context.loadFloat(this.mId, f);
        } else {
            context.loadFloat(this.mId, this.mExp.eval(Arrays.copyOf(this.mPreCalcValue, this.mPreCalcValue.length), new float[0]));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mId, this.mSrcValue, this.mSrcAnimation);
    }

    public String toString() {
        String[] labels = new String[this.mSrcValue.length];
        for (int i = 0; i < this.mSrcValue.length; i++) {
            if (Float.isNaN(this.mSrcValue[i])) {
                labels[i] = NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(this.mSrcValue[i]) + NavigationBarInflaterView.SIZE_MOD_END;
            }
        }
        return "FloatExpression[" + this.mId + "] = (" + AnimatedFloatExpression.toString(this.mPreCalcValue, labels) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "FloatExpression";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 81;
        }

        public void apply(WireBuffer buffer, int id, float[] value, float[] animation) {
            buffer.start(81);
            buffer.writeInt(id);
            int len = value.length;
            if (animation != null) {
                len |= animation.length << 16;
            }
            buffer.writeInt(len);
            for (float f : value) {
                buffer.writeFloat(f);
            }
            if (animation != null) {
                for (float f2 : animation) {
                    buffer.writeFloat(f2);
                }
            }
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            float[] animation;
            int id = buffer.readInt();
            int len = buffer.readInt();
            int valueLen = len & 65535;
            int animLen = 65535 & (len >> 16);
            float[] values = new float[valueLen];
            for (int i = 0; i < values.length; i++) {
                values[i] = buffer.readFloat();
            }
            if (animLen != 0) {
                animation = new float[animLen];
                for (int i2 = 0; i2 < animation.length; i2++) {
                    animation[i2] = buffer.readFloat();
                }
            } else {
                animation = null;
            }
            operations.add(new FloatExpression(id, values, animation));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
