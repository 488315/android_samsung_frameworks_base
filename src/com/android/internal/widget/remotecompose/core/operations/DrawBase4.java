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
public abstract class DrawBase4 extends PaintOperation implements VariableSupport, Serializable {
    protected String mName = "DrawRectBase";
    protected float mX1;
    float mX1Value;
    protected float mX2;
    float mX2Value;
    protected float mY1;
    float mY1Value;
    protected float mY2;
    float mY2Value;

    /* JADX INFO: Access modifiers changed from: protected */
    public interface Maker {
        DrawBase4 create(float f, float f2, float f3, float f4);
    }

    public Operation construct(float f, float f2, float f3, float f4) {
        return null;
    }

    protected abstract void write(WireBuffer wireBuffer, float f, float f2, float f3, float f4);

    public DrawBase4(float f, float f2, float f3, float f4) {
        this.mX1Value = f;
        this.mY1Value = f2;
        this.mX2Value = f3;
        this.mY2Value = f4;
        this.mX1 = f;
        this.mY1 = f2;
        this.mX2 = f3;
        this.mY2 = f4;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mX1 = Float.isNaN(this.mX1Value) ? remoteContext.getFloat(Utils.idFromNan(this.mX1Value)) : this.mX1Value;
        this.mY1 = Float.isNaN(this.mY1Value) ? remoteContext.getFloat(Utils.idFromNan(this.mY1Value)) : this.mY1Value;
        this.mX2 = Float.isNaN(this.mX2Value) ? remoteContext.getFloat(Utils.idFromNan(this.mX2Value)) : this.mX2Value;
        this.mY2 = Float.isNaN(this.mY2Value) ? remoteContext.getFloat(Utils.idFromNan(this.mY2Value)) : this.mY2Value;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mX1Value)) {
            remoteContext.listensTo(Utils.idFromNan(this.mX1Value), this);
        }
        if (Float.isNaN(this.mY1Value)) {
            remoteContext.listensTo(Utils.idFromNan(this.mY1Value), this);
        }
        if (Float.isNaN(this.mX2Value)) {
            remoteContext.listensTo(Utils.idFromNan(this.mX2Value), this);
        }
        if (Float.isNaN(this.mY2Value)) {
            remoteContext.listensTo(Utils.idFromNan(this.mY2Value), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        write(wireBuffer, this.mX1, this.mY1, this.mX2, this.mY2);
    }

    public String toString() {
        return this.mName + " " + Utils.floatToString(this.mX1Value, this.mX1) + " " + Utils.floatToString(this.mY1Value, this.mY1) + " " + Utils.floatToString(this.mX2Value, this.mX2) + " " + Utils.floatToString(this.mY2Value, this.mY2);
    }

    public static void read(Maker maker, WireBuffer wireBuffer, List<Operation> list) {
        list.add(maker.create(wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    protected static void write(WireBuffer wireBuffer, int i, float f, float f2, float f3, float f4) {
        wireBuffer.start(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
    }

    protected MapSerializer serialize(MapSerializer mapSerializer, String str, String str2, String str3, String str4) {
        return mapSerializer.add(str, this.mX1Value, this.mX1).add(str2, this.mY1Value, this.mY1).add(str3, this.mX2Value, this.mX2).add(str4, this.mY2Value, this.mY2);
    }
}
