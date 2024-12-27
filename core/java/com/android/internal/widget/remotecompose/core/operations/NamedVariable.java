package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class NamedVariable implements Operation {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public int mVarId;
    public String mVarName;
    public int mVarType;

    public NamedVariable(int varId, int varType, String name) {
        this.mVarId = varId;
        this.mVarType = varType;
        this.mVarName = name;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mVarId, this.mVarType, this.mVarName);
    }

    public String toString() {
        return "VariableName[" + this.mVarId + "] = \"" + Utils.trimString(this.mVarName, 10) + "\" type=" + this.mVarType;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "TextData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 102;
        }

        public void apply(WireBuffer buffer, int varId, int varType, String text) {
            buffer.start(102);
            buffer.writeInt(varId);
            buffer.writeInt(varType);
            buffer.writeUTF8(text);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int varId = buffer.readInt();
            int varType = buffer.readInt();
            String text = buffer.readUTF8(4000);
            operations.add(new NamedVariable(varId, varType, text));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.loadVariableName(this.mVarName, this.mVarId, this.mVarType);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
