package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase4;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawLine extends DrawBase4 implements SerializableToString {
    private static final String CLASS_NAME = "DrawLine";
    private static final int OP_CODE = 47;

    public static int id() {
        return 47;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase4.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.DrawLine$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Maker
            public final DrawBase4 create(float f, float f2, float f3, float f4) {
                return new DrawLine(f, f2, f3, f4);
            }
        }, wireBuffer, list);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 47, CLASS_NAME).description("Draw a line segment").field(1, "startX", "The x-coordinate of the start point of the line").field(1, "startY", "The y-coordinate of the start point of the line").field(1, "endX", "The x-coordinate of the end point of the line").field(1, "endY", "The y-coordinate of the end point of the line");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4
    protected void write(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        apply(wireBuffer, f, f2, f3, f4);
    }

    public DrawLine(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawLine(this.mX1, this.mY1, this.mX2, this.mY2);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        write(wireBuffer, 47, f, f2, f3, f4);
    }

    @Override // com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        String str = "" + this.mX1;
        if (Float.isNaN(this.mX1Value)) {
            str = NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(this.mX1Value) + " = " + this.mX1 + NavigationBarInflaterView.SIZE_MOD_END;
        }
        String str2 = "" + this.mY1;
        if (Float.isNaN(this.mY1Value)) {
            str2 = NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(this.mY1Value) + " = " + this.mY1 + NavigationBarInflaterView.SIZE_MOD_END;
        }
        String str3 = "" + this.mX2;
        if (Float.isNaN(this.mX2Value)) {
            str3 = NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(this.mX2Value) + " = " + this.mX2 + NavigationBarInflaterView.SIZE_MOD_END;
        }
        String str4 = "" + this.mY2;
        if (Float.isNaN(this.mY2Value)) {
            str4 = NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(this.mY2Value) + " = " + this.mY2 + NavigationBarInflaterView.SIZE_MOD_END;
        }
        stringSerializer.append(i, "DrawLine(" + str + ", " + str2 + ", " + str3 + ", " + str4 + NavigationBarInflaterView.KEY_CODE_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "startX", "startY", "endX", "endY").addType(CLASS_NAME);
    }
}
