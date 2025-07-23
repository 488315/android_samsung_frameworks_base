package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class PathCombine extends PaintOperation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "PathCombine";
    private static final int OP_CODE = 175;
    public static final byte OP_DIFFERENCE = 0;
    public static final byte OP_INTERSECT = 1;
    public static final byte OP_REVERSE_DIFFERENCE = 2;
    public static final byte OP_UNION = 3;
    public static final byte OP_XOR = 4;
    private byte mOperation;
    public int mOutId;
    public int mPathId1;
    public int mPathId2;

    public static int id() {
        return 175;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
    }

    public PathCombine(int i, int i2, int i3, byte b) {
        this.mOutId = i;
        this.mPathId1 = i2;
        this.mPathId2 = i3;
        this.mOperation = b;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mOutId, this.mPathId1, this.mPathId2, this.mOperation);
    }

    public String toString() {
        return "PathCombine[" + this.mOutId + "] = [" + this.mPathId1 + " ] + [ " + this.mPathId2 + "], " + ((int) this.mOperation);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, byte b) {
        wireBuffer.start(175);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeByte(b);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new PathCombine(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), (byte) wireBuffer.readByte()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 175, CLASS_NAME).description("Merge two string into one").field(0, "srcPathId1", "id of the path").field(0, "srcPathId1", "x Shift of the path").field(6, "operation", "the operation");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.combinePath(this.mOutId, this.mPathId1, this.mPathId2, this.mOperation);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("outId", Integer.valueOf(this.mOutId)).add("pathId1", Integer.valueOf(this.mPathId1)).add("pathId2", Integer.valueOf(this.mPathId2)).add("operation", Byte.valueOf(this.mOperation));
    }
}
