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
public class DrawOval extends DrawBase4 {
    private static final String CLASS_NAME = "DrawOval";
    private static final int OP_CODE = 56;

    public static int id() {
        return 56;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase4.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.DrawOval$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Maker
            public final DrawBase4 create(float f, float f2, float f3, float f4) {
                return new DrawOval(f, f2, f3, f4);
            }
        }, wireBuffer, list);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 56, CLASS_NAME).description("Draw the specified oval").field(1, "left", "The left side of the oval").field(1, GenerateXML.TOP, "The top of the oval").field(1, "right", "The right side of the oval").field(1, GenerateXML.BOTTOM, "The bottom of the oval");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4
    protected void write(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        apply(wireBuffer, f, f2, f3, f4);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mX1, this.mY1, this.mX2, this.mY2);
    }

    public DrawOval(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawOval(this.mX1, this.mY1, this.mX2, this.mY2);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        write(wireBuffer, 56, f, f2, f3, f4);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "left", GenerateXML.TOP, "right", GenerateXML.BOTTOM).addType(CLASS_NAME);
    }
}
