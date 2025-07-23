package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase6;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawArc extends DrawBase6 {
    private static final String CLASS_NAME = "DrawArc";
    private static final int OP_CODE = 152;

    public static int id() {
        return 152;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase6.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.DrawArc$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase6.Maker
            public final DrawBase6 create(float f, float f2, float f3, float f4, float f5, float f6) {
                return new DrawArc(f, f2, f3, f4, f5, f6);
            }
        }, wireBuffer, list);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4, float f5, float f6) {
        wireBuffer.start(152);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeFloat(f5);
        wireBuffer.writeFloat(f6);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase6
    protected void write(WireBuffer wireBuffer, float f, float f2, float f3, float f4, float f5, float f6) {
        apply(wireBuffer, f, f2, f3, f4, f5, f6);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 152, CLASS_NAME).description("Draw the specified arcwhich will be scaled to fit inside the specified oval").field(1, "left", "The left side of the Oval").field(1, GenerateXML.TOP, "The top of the Oval").field(1, "right", "The right side of the Oval").field(1, GenerateXML.BOTTOM, "The bottom of the Oval").field(1, "startAngle", "Starting angle (in degrees) where the arc begins").field(1, "sweepAngle", "Sweep angle (in degrees) measured clockwise");
    }

    public DrawArc(float f, float f2, float f3, float f4, float f5, float f6) {
        super(f, f2, f3, f4, f5, f6);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawArc(this.mV1, this.mV2, this.mV3, this.mV4, this.mV5, this.mV6);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "left", GenerateXML.TOP, "right", GenerateXML.BOTTOM, "startAngle", "sweepAngle").addType(CLASS_NAME);
    }
}
