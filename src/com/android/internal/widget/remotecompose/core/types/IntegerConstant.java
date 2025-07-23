package com.android.internal.widget.remotecompose.core.types;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class IntegerConstant extends Operation implements Serializable {
    private static final String CLASS_NAME = "IntegerConstant";
    public final int mId;
    private int mValue;

    public static int id() {
        return 140;
    }

    IntegerConstant(int i, int i2) {
        this.mId = i;
        this.mValue = i2;
    }

    public void update(IntegerConstant integerConstant) {
        this.mValue = integerConstant.mValue;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mValue);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadInteger(this.mId, this.mValue);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return toString();
    }

    public String toString() {
        return "IntegerConstant[" + this.mId + "] = " + this.mValue + "";
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(140);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new IntegerConstant(wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", id(), CLASS_NAME).description("A integer and its associated id").field(0, "id", "id of Int").field(0, "value", "32-bit int value");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("value", Integer.valueOf(this.mValue));
    }
}
