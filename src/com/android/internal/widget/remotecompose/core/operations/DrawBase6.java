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
public abstract class DrawBase6 extends PaintOperation implements VariableSupport, Serializable {
    protected String mName = "DrawRectBase";
    float mV1;
    float mV2;
    float mV3;
    float mV4;
    float mV5;
    float mV6;
    float mValue1;
    float mValue2;
    float mValue3;
    float mValue4;
    float mValue5;
    float mValue6;

    interface Maker {
        DrawBase6 create(float f, float f2, float f3, float f4, float f5, float f6);
    }

    public Operation construct(float f, float f2, float f3, float f4, float f5, float f6) {
        return null;
    }

    protected abstract void write(WireBuffer wireBuffer, float f, float f2, float f3, float f4, float f5, float f6);

    public DrawBase6(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mValue1 = f;
        this.mValue2 = f2;
        this.mValue3 = f3;
        this.mValue4 = f4;
        this.mValue5 = f5;
        this.mValue6 = f6;
        this.mV1 = f;
        this.mV2 = f2;
        this.mV3 = f3;
        this.mV4 = f4;
        this.mV5 = f5;
        this.mV6 = f6;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mV1 = Float.isNaN(this.mValue1) ? remoteContext.getFloat(Utils.idFromNan(this.mValue1)) : this.mValue1;
        this.mV2 = Float.isNaN(this.mValue2) ? remoteContext.getFloat(Utils.idFromNan(this.mValue2)) : this.mValue2;
        this.mV3 = Float.isNaN(this.mValue3) ? remoteContext.getFloat(Utils.idFromNan(this.mValue3)) : this.mValue3;
        this.mV4 = Float.isNaN(this.mValue4) ? remoteContext.getFloat(Utils.idFromNan(this.mValue4)) : this.mValue4;
        this.mV5 = Float.isNaN(this.mValue5) ? remoteContext.getFloat(Utils.idFromNan(this.mValue5)) : this.mValue5;
        this.mV6 = Float.isNaN(this.mValue6) ? remoteContext.getFloat(Utils.idFromNan(this.mValue6)) : this.mValue6;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mValue1)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue1), this);
        }
        if (Float.isNaN(this.mValue2)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue2), this);
        }
        if (Float.isNaN(this.mValue3)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue3), this);
        }
        if (Float.isNaN(this.mValue4)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue4), this);
        }
        if (Float.isNaN(this.mValue5)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue5), this);
        }
        if (Float.isNaN(this.mValue6)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue6), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        write(wireBuffer, this.mV1, this.mV2, this.mV3, this.mV4, this.mV5, this.mV6);
    }

    public String toString() {
        return this.mName + " " + Utils.floatToString(this.mV1) + " " + Utils.floatToString(this.mV2) + " " + Utils.floatToString(this.mV3) + " " + Utils.floatToString(this.mV4);
    }

    public static void read(Maker maker, WireBuffer wireBuffer, List<Operation> list) {
        list.add(maker.create(wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static String name() {
        return "DrawBase6";
    }

    protected MapSerializer serialize(MapSerializer mapSerializer, String str, String str2, String str3, String str4, String str5, String str6) {
        return mapSerializer.add(str, this.mValue1, this.mV1).add(str2, this.mValue2, this.mV2).add(str3, this.mValue3, this.mV3).add(str4, this.mValue4, this.mV4).add(str5, this.mValue5, this.mV5).add(str6, this.mValue6, this.mV6);
    }
}
