package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawContent extends PaintOperation implements Serializable {
    private static final String CLASS_NAME = "DrawContent";
    private static final int OP_CODE = 139;
    private LayoutComponent mComponent;

    public static int id() {
        return 139;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    public void setComponent(LayoutComponent layoutComponent) {
        this.mComponent = layoutComponent;
    }

    public String toString() {
        return "DrawContent;";
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawContent());
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(139);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 139, CLASS_NAME).description("Draw the component content");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        LayoutComponent layoutComponent = this.mComponent;
        if (layoutComponent != null) {
            layoutComponent.drawContent(paintContext);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME);
    }
}
