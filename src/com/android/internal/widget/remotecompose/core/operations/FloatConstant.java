package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class FloatConstant extends Operation implements Serializable {
    private static final String CLASS_NAME = "FloatConstant";
    private static final int OP_CODE = 80;
    public int mId;
    public float mValue;

    public static int id() {
        return 80;
    }

    public FloatConstant(int i, float f) {
        this.mId = i;
        this.mValue = f;
    }

    public void update(FloatConstant floatConstant) {
        this.mValue = floatConstant.mValue;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mValue);
    }

    public String toString() {
        return "FloatConstant[" + this.mId + "] = " + this.mValue;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f) {
        wireBuffer.start(80);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new FloatConstant(wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 80, CLASS_NAME).description("A float and its associated id").field(0, "id", "id of float").field(1, "value", "32-bit float value");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadFloat(this.mId, this.mValue);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("value", Float.valueOf(this.mValue));
    }
}
