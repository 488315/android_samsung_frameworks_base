package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class DrawBase6 extends PaintOperation implements VariableSupport {
    public static final Companion COMPANION = new Companion(42) { // from class: com.android.internal.widget.remotecompose.core.operations.DrawBase6.1
        public Operation construct(float x1, float y1, float x2, float y2) {
            return null;
        }
    };
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

    public DrawBase6(float v1, float v2, float v3, float v4, float v5, float v6) {
        this.mValue1 = v1;
        this.mValue2 = v2;
        this.mValue3 = v3;
        this.mValue4 = v4;
        this.mValue5 = v5;
        this.mValue6 = v6;
        this.mV1 = v1;
        this.mV2 = v2;
        this.mV3 = v3;
        this.mV4 = v4;
        this.mV5 = v5;
        this.mV6 = v6;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        this.mV1 = Float.isNaN(this.mValue1) ? context.getFloat(Utils.idFromNan(this.mValue1)) : this.mValue1;
        this.mV2 = Float.isNaN(this.mValue2) ? context.getFloat(Utils.idFromNan(this.mValue2)) : this.mValue2;
        this.mV3 = Float.isNaN(this.mValue3) ? context.getFloat(Utils.idFromNan(this.mValue3)) : this.mValue3;
        this.mV4 = Float.isNaN(this.mValue4) ? context.getFloat(Utils.idFromNan(this.mValue4)) : this.mValue4;
        this.mV5 = Float.isNaN(this.mValue5) ? context.getFloat(Utils.idFromNan(this.mValue5)) : this.mValue5;
        this.mV6 = Float.isNaN(this.mValue6) ? context.getFloat(Utils.idFromNan(this.mValue6)) : this.mValue6;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        if (Float.isNaN(this.mValue1)) {
            context.listensTo(Utils.idFromNan(this.mValue1), this);
        }
        if (Float.isNaN(this.mValue2)) {
            context.listensTo(Utils.idFromNan(this.mValue2), this);
        }
        if (Float.isNaN(this.mValue3)) {
            context.listensTo(Utils.idFromNan(this.mValue3), this);
        }
        if (Float.isNaN(this.mValue4)) {
            context.listensTo(Utils.idFromNan(this.mValue4), this);
        }
        if (Float.isNaN(this.mValue5)) {
            context.listensTo(Utils.idFromNan(this.mValue5), this);
        }
        if (Float.isNaN(this.mValue6)) {
            context.listensTo(Utils.idFromNan(this.mValue6), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mV1, this.mV2, this.mV3, this.mV4, this.mV5, this.mV6);
    }

    public String toString() {
        return this.mName + " " + Utils.floatToString(this.mV1) + " " + Utils.floatToString(this.mV2) + " " + Utils.floatToString(this.mV3) + " " + Utils.floatToString(this.mV4);
    }

    public static class Companion implements CompanionOperation {
        public final int OP_CODE;

        protected Companion(int code) {
            this.OP_CODE = code;
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            float sv1 = buffer.readFloat();
            float sv2 = buffer.readFloat();
            float sv3 = buffer.readFloat();
            float sv4 = buffer.readFloat();
            float sv5 = buffer.readFloat();
            float sv6 = buffer.readFloat();
            Operation op = construct(sv1, sv2, sv3, sv4, sv5, sv6);
            operations.add(op);
        }

        public Operation construct(float v1, float v2, float v3, float v4, float v5, float v6) {
            return null;
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "DrawRect";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return this.OP_CODE;
        }

        public void apply(WireBuffer buffer, float v1, float v2, float v3, float v4, float v5, float v6) {
            buffer.start(this.OP_CODE);
            buffer.writeFloat(v1);
            buffer.writeFloat(v2);
            buffer.writeFloat(v3);
            buffer.writeFloat(v4);
            buffer.writeFloat(v5);
            buffer.writeFloat(v6);
        }
    }
}
