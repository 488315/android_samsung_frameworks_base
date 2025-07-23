package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase3;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class MatrixRotate extends DrawBase3 {
    private static final String CLASS_NAME = "MatrixRotate";
    private static final int OP_CODE = 129;

    public static int id() {
        return 129;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase3.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.MatrixRotate.1
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase3.Maker
            public DrawBase3 create(float f, float f2, float f3) {
                return new MatrixRotate(f, f2, f3);
            }
        }, wireBuffer, list);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 129, CLASS_NAME).description("apply rotation to matrix").field(1, "rotate", "Angle to rotate").field(1, "pivotX", "X Pivot point").field(1, "pivotY", "Y Pivot point");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase3
    protected void write(WireBuffer wireBuffer, float f, float f2, float f3) {
        apply(wireBuffer, f, f2, f3);
    }

    public MatrixRotate(float f, float f2, float f3) {
        super(f, f2, f3);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.matrixRotate(this.mV1, this.mV2, this.mV3);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3) {
        wireBuffer.start(129);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "rotate", "pivotX", "pivotY").addType(CLASS_NAME);
    }
}
