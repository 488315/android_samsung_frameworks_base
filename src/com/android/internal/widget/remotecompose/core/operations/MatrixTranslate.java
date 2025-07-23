package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase2;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class MatrixTranslate extends DrawBase2 {
    private static final String CLASS_NAME = "MatrixTranslate";
    private static final int OP_CODE = 127;

    public static int id() {
        return 127;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase2.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.MatrixTranslate$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase2.Maker
            public final DrawBase2 create(float f, float f2) {
                return new MatrixTranslate(f, f2);
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
        documentationBuilder.operation("Canvas Operations", 127, CLASS_NAME).description("Preconcat the current matrix with the specified translation").field(1, "dx", "The distance to translate in X").field(1, "dy", "The distance to translate in Y");
    }

    public MatrixTranslate(float f, float f2) {
        super(f, f2);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.matrixTranslate(this.mV1, this.mV2);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2) {
        write(wireBuffer, 127, f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "dx", "dy").addType(CLASS_NAME);
    }
}
