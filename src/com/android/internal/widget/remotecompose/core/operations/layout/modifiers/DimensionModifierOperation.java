package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;

/* loaded from: classes6.dex */
public abstract class DimensionModifierOperation extends Operation implements ModifierOperation, VariableSupport {
    float mOutValue;
    Type mType;
    float mValue;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    public enum Type {
        EXACT,
        FILL,
        WRAP,
        WEIGHT,
        INTRINSIC_MIN,
        INTRINSIC_MAX,
        EXACT_DP;

        static Type fromInt(int i) {
            switch (i) {
                case 0:
                    return EXACT;
                case 1:
                    return FILL;
                case 2:
                    return WRAP;
                case 3:
                    return WEIGHT;
                case 4:
                    return INTRINSIC_MIN;
                case 5:
                    return INTRINSIC_MAX;
                case 6:
                    return EXACT_DP;
                default:
                    return EXACT;
            }
        }
    }

    public DimensionModifierOperation(Type type, float f) {
        Type type2 = Type.EXACT;
        this.mType = type;
        this.mValue = f;
        this.mOutValue = f;
    }

    public DimensionModifierOperation(Type type) {
        this(type, Float.NaN);
    }

    public DimensionModifierOperation(float f) {
        this(Type.EXACT, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        if (this.mType == Type.EXACT) {
            this.mOutValue = Float.isNaN(this.mValue) ? remoteContext.getFloat(Utils.idFromNan(this.mValue)) : this.mValue;
        }
        if (this.mType == Type.EXACT_DP) {
            float f = this.mOutValue;
            float f2 = Float.isNaN(this.mValue) ? remoteContext.getFloat(Utils.idFromNan(this.mValue)) : this.mValue;
            this.mOutValue = f2;
            float density = f2 * remoteContext.getDensity();
            this.mOutValue = density;
            if (f != density) {
                remoteContext.getDocument().getRootLayoutComponent().invalidateMeasure();
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (this.mType == Type.EXACT && Float.isNaN(this.mValue)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue), this);
        }
        if (this.mType == Type.EXACT_DP && Float.isNaN(this.mValue)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue), this);
        }
    }

    public boolean hasWeight() {
        return this.mType == Type.WEIGHT;
    }

    public boolean isWrap() {
        return this.mType == Type.WRAP;
    }

    public boolean isFill() {
        return this.mType == Type.FILL;
    }

    public boolean isIntrinsicMin() {
        return this.mType == Type.INTRINSIC_MIN;
    }

    public boolean isIntrinsicMax() {
        return this.mType == Type.INTRINSIC_MAX;
    }

    public Type getType() {
        return this.mType;
    }

    public float getValue() {
        return this.mOutValue;
    }

    public void setValue(float f) {
        this.mValue = f;
        this.mOutValue = f;
    }

    public String serializedName() {
        return "DIMENSION";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        if (this.mType == Type.EXACT) {
            stringSerializer.append(i, serializedName() + " = " + this.mValue);
        }
        if (this.mType == Type.EXACT_DP) {
            stringSerializer.append(i, serializedName() + " = " + this.mValue + " dp");
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    public String toString() {
        return "DimensionModifierOperation(" + this.mValue + NavigationBarInflaterView.KEY_CODE_END;
    }
}
