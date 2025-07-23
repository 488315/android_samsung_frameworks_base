package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;

/* loaded from: classes6.dex */
public abstract class DimensionInModifierOperation extends Operation implements ModifierOperation, VariableSupport {
    int mOpCode;
    float mV1;
    float mV2;
    float mValue1;
    float mValue2;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public DimensionInModifierOperation(int i, float f, float f2) {
        this.mOpCode = i;
        this.mValue1 = f;
        this.mValue2 = f2;
        if (!Float.isNaN(f)) {
            this.mV1 = this.mValue1;
        }
        if (Float.isNaN(this.mValue2)) {
            return;
        }
        this.mV2 = this.mValue2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mV1 = Float.isNaN(this.mValue1) ? remoteContext.getFloat(Utils.idFromNan(this.mValue1)) : this.mValue1;
        this.mV2 = Float.isNaN(this.mValue2) ? remoteContext.getFloat(Utils.idFromNan(this.mValue2)) : this.mValue2;
        float f = this.mV1;
        if (f != -1.0f) {
            this.mV1 = f * remoteContext.getDensity();
        }
        float f2 = this.mV2;
        if (f2 != -1.0f) {
            this.mV2 = f2 * remoteContext.getDensity();
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mValue1)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue1), this);
        }
        if (Float.isNaN(this.mValue2)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue2), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    public float getMin() {
        return this.mV1;
    }

    public float getMax() {
        return this.mV2;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "WIDTH_IN = [" + getMin() + ", " + getMax() + NavigationBarInflaterView.SIZE_MOD_END);
    }
}
