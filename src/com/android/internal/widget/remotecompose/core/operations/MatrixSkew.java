package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase2;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class MatrixSkew extends DrawBase2 {
    private static final String CLASS_NAME = "MatrixSkew";
    private static final int OP_CODE = 128;

    public static int id() {
        return 128;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase2.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.MatrixSkew$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase2.Maker
            public final DrawBase2 create(float f, float f2) {
                return new MatrixSkew(f, f2);
            }
        }, wireBuffer, list);
    }

    public static String name() {
        return CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase2
    protected void write(WireBuffer wireBuffer, float f, float f2) {
        apply(wireBuffer, f, f2);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 128, CLASS_NAME).description("Current matrix with the specified skew.").field(1, "skewX", "The amount to skew in X").field(1, "skewY", "The amount to skew in Y");
    }

    public MatrixSkew(float f, float f2) {
        super(f, f2);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.matrixSkew(this.mV1, this.mV2);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2) {
        write(wireBuffer, 128, f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "skewX", "skewY").addType(CLASS_NAME);
    }
}
