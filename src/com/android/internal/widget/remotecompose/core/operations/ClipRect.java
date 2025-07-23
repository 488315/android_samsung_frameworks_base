package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase4;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.List;

/* loaded from: classes6.dex */
public class ClipRect extends DrawBase4 {
    private static final String CLASS_NAME = "ClipRect";
    private static final int OP_CODE = 39;

    public static int id() {
        return 39;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase4.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.ClipRect$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Maker
            public final DrawBase4 create(float f, float f2, float f3, float f4) {
                return new ClipRect(f, f2, f3, f4);
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
        documentationBuilder.operation("Expressions Operations", 39, CLASS_NAME).description("Intersect the current clip with rectangle").field(1, "left", "The left side of the rectangle to intersect with the current clip").field(1, GenerateXML.TOP, "The top of the rectangle to intersect with the current clip").field(1, "right", "The right side of the rectangle to intersect with the current clip").field(1, GenerateXML.BOTTOM, "The bottom of the rectangle to intersect with the current clip");
    }

    public ClipRect(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.clipRect(this.mX1, this.mY1, this.mX2, this.mY2);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        write(wireBuffer, 39, f, f2, f3, f4);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "left", GenerateXML.TOP, "right", GenerateXML.BOTTOM).addType(CLASS_NAME);
    }
}
