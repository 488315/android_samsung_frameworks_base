package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public abstract class DrawBase2 extends PaintOperation implements VariableSupport {
    public static final Companion COMPANION =
            new Companion(
                    46) { // from class:
                          // com.android.internal.widget.remotecompose.core.operations.DrawBase2.1
                @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase2.Companion
                public Operation construct(float x1, float y1) {
                    return null;
                }
            };
    protected String mName = "DrawRectBase";
    float mV1;
    float mV2;
    float mValue1;
    float mValue2;

    public DrawBase2(float v1, float v2) {
        this.mValue1 = v1;
        this.mValue2 = v2;
        this.mV1 = v1;
        this.mV2 = v2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        this.mV1 =
                Float.isNaN(this.mValue1)
                        ? context.getFloat(Utils.idFromNan(this.mValue1))
                        : this.mValue1;
        this.mV2 =
                Float.isNaN(this.mValue2)
                        ? context.getFloat(Utils.idFromNan(this.mValue2))
                        : this.mValue2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        if (Float.isNaN(this.mValue1)) {
            context.listensTo(Utils.idFromNan(this.mValue1), this);
        }
        if (Float.isNaN(this.mValue2)) {
            context.listensTo(Utils.idFromNan(this.mValue2), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mV1, this.mV2);
    }

    public String toString() {
        return this.mName
                + " "
                + Utils.floatToString(this.mV1)
                + " "
                + Utils.floatToString(this.mV2);
    }

    public static class Companion implements CompanionOperation {
        public final int OP_CODE;

        protected Companion(int code) {
            this.OP_CODE = code;
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            float v1 = buffer.readFloat();
            float v2 = buffer.readFloat();
            Operation op = construct(v1, v2);
            operations.add(op);
        }

        public Operation construct(float x1, float y1) {
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

        public void apply(WireBuffer buffer, float x1, float y1) {
            buffer.start(this.OP_CODE);
            buffer.writeFloat(x1);
            buffer.writeFloat(y1);
        }
    }
}
