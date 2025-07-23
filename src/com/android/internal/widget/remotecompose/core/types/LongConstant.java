package com.android.internal.widget.remotecompose.core.types;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class LongConstant extends Operation implements Serializable {
    private static final String CLASS_NAME = "LongConstant";
    private static final int OP_CODE = 148;
    public final int mId;
    private long mValue;

    public LongConstant(int i, long j) {
        this.mId = i;
        this.mValue = j;
    }

    public void update(LongConstant longConstant) {
        this.mValue = longConstant.mValue;
    }

    public long getValue() {
        return this.mValue;
    }

    public void setValue(long j) {
        this.mValue = j;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mValue);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.putObject(this.mId, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return toString();
    }

    public String toString() {
        return "LongConstant[" + this.mId + "] = " + this.mValue + "";
    }

    public static void apply(WireBuffer wireBuffer, int i, long j) {
        wireBuffer.start(148);
        wireBuffer.writeInt(i);
        wireBuffer.writeLong(j);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new LongConstant(wireBuffer.readInt(), wireBuffer.readLong()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 148, CLASS_NAME).description("A boolean and its associated id").field(0, "id", "id of Int").field(8, "value", "The long Value");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("value", Long.valueOf(this.mValue));
    }
}
