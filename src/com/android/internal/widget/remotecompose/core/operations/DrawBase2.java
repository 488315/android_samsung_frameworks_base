package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class DrawBase2 extends PaintOperation implements VariableSupport, Serializable {
    protected String mName = "DrawRectBase";
    float mV1;
    float mV2;
    float mValue1;
    float mValue2;

    protected interface Maker {
        DrawBase2 create(float f, float f2);
    }

    public Operation construct(float f, float f2) {
        return null;
    }

    protected abstract void write(WireBuffer wireBuffer, float f, float f2);

    public DrawBase2(float f, float f2) {
        this.mValue1 = f;
        this.mValue2 = f2;
        this.mV1 = f;
        this.mV2 = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mV1 = Float.isNaN(this.mValue1) ? remoteContext.getFloat(Utils.idFromNan(this.mValue1)) : this.mValue1;
        this.mV2 = Float.isNaN(this.mValue2) ? remoteContext.getFloat(Utils.idFromNan(this.mValue2)) : this.mValue2;
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
    public void write(WireBuffer wireBuffer) {
        write(wireBuffer, this.mV1, this.mV2);
    }

    public String toString() {
        return this.mName + " " + Utils.floatToString(this.mV1) + " " + Utils.floatToString(this.mV2);
    }

    public static void read(Maker maker, WireBuffer wireBuffer, List<Operation> list) {
        list.add(maker.create(wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    protected static void write(WireBuffer wireBuffer, int i, float f, float f2) {
        wireBuffer.start(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    protected MapSerializer serialize(MapSerializer mapSerializer, String str, String str2) {
        return mapSerializer.add(str, this.mValue1, this.mV1).add(str2, this.mValue2, this.mV2);
    }
}
