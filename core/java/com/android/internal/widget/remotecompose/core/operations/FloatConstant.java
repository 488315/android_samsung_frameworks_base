package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class FloatConstant implements Operation {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public int mTextId;
    public float mValue;

    public FloatConstant(int textId, float value) {
        this.mTextId = textId;
        this.mValue = value;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mTextId, this.mValue);
    }

    public String toString() {
        return "FloatConstant[" + this.mTextId + "] = " + this.mValue + "";
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "FloatExpression";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 80;
        }

        public void apply(WireBuffer buffer, int textId, float value) {
            buffer.start(80);
            buffer.writeInt(textId);
            buffer.writeFloat(value);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int textId = buffer.readInt();
            float value = buffer.readFloat();
            operations.add(new FloatConstant(textId, value));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.loadFloat(this.mTextId, this.mValue);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
