package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawPath extends PaintOperation implements Serializable {
    private static final String CLASS_NAME = "DrawPath";
    private static final int OP_CODE = 124;
    int mId;
    float mStart = 0.0f;
    float mEnd = 1.0f;

    public static int id() {
        return 124;
    }

    public DrawPath(int i) {
        this.mId = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId);
    }

    public String toString() {
        return "DrawPath [" + this.mId + "], " + this.mStart + ", " + this.mEnd;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawPath(wireBuffer.readInt()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(124);
        wireBuffer.writeInt(i);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", 124, CLASS_NAME).description("Draw a bitmap using integer coordinates").field(0, "id", "id of path");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawPath(this.mId, this.mStart, this.mEnd);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("start", Float.valueOf(this.mStart)).add("end", Float.valueOf(this.mEnd));
    }
}
