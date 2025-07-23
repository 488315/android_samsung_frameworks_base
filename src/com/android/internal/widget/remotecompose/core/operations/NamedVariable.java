package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class NamedVariable extends Operation implements Serializable {
    private static final String CLASS_NAME = "NamedVariable";
    public static final int COLOR_TYPE = 2;
    public static final int FLOAT_TYPE = 1;
    public static final int IMAGE_TYPE = 3;
    public static final int INT_TYPE = 4;
    public static final int LONG_TYPE = 5;
    public static final int MAX_STRING_SIZE = 4000;
    private static final int OP_CODE = 137;
    public static final int STRING_TYPE = 0;
    public final int mVarId;
    public final String mVarName;
    public final int mVarType;

    public static int id() {
        return 137;
    }

    public NamedVariable(int i, int i2, String str) {
        this.mVarId = i;
        this.mVarType = i2;
        this.mVarName = str;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mVarId, this.mVarType, this.mVarName);
    }

    public String toString() {
        return "VariableName[" + this.mVarId + "] = \"" + Utils.trimString(this.mVarName, 10) + "\" type=" + this.mVarType;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, String str) {
        wireBuffer.start(137);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeUTF8(str);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new NamedVariable(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readUTF8(4000)));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 137, CLASS_NAME).description("Add a string name for an ID").field(0, "varId", "id to label").field(0, "varType", "The type of variable").field(5, "name", "String");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadVariableName(this.mVarName, this.mVarId, this.mVarType);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("varId", Integer.valueOf(this.mVarId)).add("varName", this.mVarName).add("varType", typeToString());
    }

    private String typeToString() {
        int i = this.mVarType;
        if (i == 0) {
            return "STRING_TYPE";
        }
        if (i == 1) {
            return "FLOAT_TYPE";
        }
        if (i == 2) {
            return "COLOR_TYPE";
        }
        if (i == 3) {
            return "IMAGE_TYPE";
        }
        if (i == 4) {
            return "INT_TYPE";
        }
        return "INVALID_TYPE";
    }
}
