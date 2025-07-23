package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase3;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawCircle extends DrawBase3 {
    private static final String CLASS_NAME = "DrawCircle";
    private static final int OP_CODE = 46;

    public static int id() {
        return 46;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase3.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.DrawCircle$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase3.Maker
            public final DrawBase3 create(float f, float f2, float f3) {
                return new DrawCircle(f, f2, f3);
            }
        }, wireBuffer, list);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 46, CLASS_NAME).description("Draw a Circle").field(1, "centerX", "The x-coordinate of the center of the circle to be drawn").field(1, "centerY", "The y-coordinate of the center of the circle to be drawn").field(1, "radius", "The radius of the circle to be drawn");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase3
    protected void write(WireBuffer wireBuffer, float f, float f2, float f3) {
        apply(wireBuffer, f, f2, f3);
    }

    public DrawCircle(float f, float f2, float f3) {
        super(f, f2, f3);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawCircle(this.mV1, this.mV2, this.mV3);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3) {
        wireBuffer.start(46);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "cx", "cy", "radius").addType(CLASS_NAME);
    }
}
