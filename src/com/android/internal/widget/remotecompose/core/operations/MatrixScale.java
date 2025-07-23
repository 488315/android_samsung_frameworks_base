package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase4;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class MatrixScale extends DrawBase4 {
    private static final String CLASS_NAME = "MatrixScale";
    private static final int OP_CODE = 126;

    public static int id() {
        return 126;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase4.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.MatrixScale$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Maker
            public final DrawBase4 create(float f, float f2, float f3, float f4) {
                return new MatrixScale(f, f2, f3, f4);
            }
        }, wireBuffer, list);
    }

    public static String name() {
        return CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4
    protected void write(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        apply(wireBuffer, f, f2, f3, f4);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 126, CLASS_NAME).description("Scale the following draw commands").field(1, "scaleX", "The amount to scale in X").field(1, "scaleY", "The amount to scale in Y").field(1, "pivotX", "The x-coordinate for the pivot point").field(1, "pivotY", "The y-coordinate for the pivot point");
    }

    public MatrixScale(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.matrixScale(this.mX1, this.mY1, this.mX2, this.mY2);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        write(wireBuffer, 126, f, f2, f3, f4);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "scaleX", "scaleY", "pivotX", "pivotY").addType(CLASS_NAME);
    }
}
