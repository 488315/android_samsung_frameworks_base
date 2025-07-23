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
public class MatrixSave extends PaintOperation implements Serializable {
    private static final String CLASS_NAME = "MatrixSave";
    private static final int OP_CODE = 130;

    public static int id() {
        return 130;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    public String toString() {
        return "MatrixSave;";
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new MatrixSave());
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(130);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 130, CLASS_NAME).description("Save the matrix and clip to a stack");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.matrixSave();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME);
    }
}
