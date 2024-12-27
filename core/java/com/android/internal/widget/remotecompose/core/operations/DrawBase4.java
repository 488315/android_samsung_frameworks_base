package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class DrawBase4 extends PaintOperation implements VariableSupport {
    public static final Companion COMPANION = new Companion(42) { // from class: com.android.internal.widget.remotecompose.core.operations.DrawBase4.1
        @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Companion
        public Operation construct(float x1, float y1, float x2, float y2) {
            return null;
        }
    };
    protected String mName = "DrawRectBase";
    float mX1;
    float mX1Value;
    float mX2;
    float mX2Value;
    float mY1;
    float mY1Value;
    float mY2;
    float mY2Value;

    public DrawBase4(float x1, float y1, float x2, float y2) {
        this.mX1Value = x1;
        this.mY1Value = y1;
        this.mX2Value = x2;
        this.mY2Value = y2;
        this.mX1 = x1;
        this.mY1 = y1;
        this.mX2 = x2;
        this.mY2 = y2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        this.mX1 = Float.isNaN(this.mX1Value) ? context.getFloat(Utils.idFromNan(this.mX1Value)) : this.mX1Value;
        this.mY1 = Float.isNaN(this.mY1Value) ? context.getFloat(Utils.idFromNan(this.mY1Value)) : this.mY1Value;
        this.mX2 = Float.isNaN(this.mX2Value) ? context.getFloat(Utils.idFromNan(this.mX2Value)) : this.mX2Value;
        this.mY2 = Float.isNaN(this.mY2Value) ? context.getFloat(Utils.idFromNan(this.mY2Value)) : this.mY2Value;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        if (Float.isNaN(this.mX1Value)) {
            context.listensTo(Utils.idFromNan(this.mX1Value), this);
        }
        if (Float.isNaN(this.mY1Value)) {
            context.listensTo(Utils.idFromNan(this.mY1Value), this);
        }
        if (Float.isNaN(this.mX2Value)) {
            context.listensTo(Utils.idFromNan(this.mX2Value), this);
        }
        if (Float.isNaN(this.mY2Value)) {
            context.listensTo(Utils.idFromNan(this.mY2Value), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mX1, this.mY1, this.mX2, this.mY2);
    }

    public String toString() {
        return this.mName + " " + Utils.floatToString(this.mX1Value, this.mX1) + " " + Utils.floatToString(this.mY1Value, this.mY1) + " " + Utils.floatToString(this.mX2Value, this.mX2) + " " + Utils.floatToString(this.mY2Value, this.mY2);
    }

    public static class Companion implements CompanionOperation {
        public final int OP_CODE;

        protected Companion(int code) {
            this.OP_CODE = code;
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            float sLeft = buffer.readFloat();
            float srcTop = buffer.readFloat();
            float srcRight = buffer.readFloat();
            float srcBottom = buffer.readFloat();
            Operation op = construct(sLeft, srcTop, srcRight, srcBottom);
            operations.add(op);
        }

        public Operation construct(float x1, float y1, float x2, float y2) {
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

        public void apply(WireBuffer buffer, float x1, float y1, float x2, float y2) {
            buffer.start(this.OP_CODE);
            buffer.writeFloat(x1);
            buffer.writeFloat(y1);
            buffer.writeFloat(x2);
            buffer.writeFloat(y2);
        }
    }
}
