package com.android.internal.widget.remotecompose.core.types;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class BooleanConstant extends Operation implements Serializable {
    private static final String CLASS_NAME = "BooleanConstant";
    private static final int OP_CODE = 143;
    private int mId;
    private boolean mValue;

    public static int id() {
        return 143;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    public BooleanConstant(int i, boolean z) {
        this.mId = i;
        this.mValue = z;
    }

    public boolean getValue() {
        return this.mValue;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mValue);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return toString();
    }

    public String toString() {
        return "BooleanConstant[" + this.mId + "] = " + this.mValue + "";
    }

    public static String name() {
        return "OrigamiBoolean";
    }

    public static void apply(WireBuffer wireBuffer, int i, boolean z) {
        wireBuffer.start(143);
        wireBuffer.writeInt(i);
        wireBuffer.writeBoolean(z);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new BooleanConstant(wireBuffer.readInt(), wireBuffer.readBoolean()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 143, CLASS_NAME).description("A boolean and its associated id").field(0, "id", "id of Int").field(6, "value", "8-bit 0 or 1");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("value", Boolean.valueOf(this.mValue));
    }
}
