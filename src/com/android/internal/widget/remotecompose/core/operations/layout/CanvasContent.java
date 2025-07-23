package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import java.util.List;

/* loaded from: classes6.dex */
public class CanvasContent extends Component {
    public static int id() {
        return 207;
    }

    public CanvasContent(int i, float f, float f2, float f3, float f4, Component component, int i2) {
        super(component, i, i2, f, f2, f3, f4);
    }

    public static String name() {
        return "CanvasContent";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "CANVAS_CONTENT";
    }

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(207);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new CanvasContent(wireBuffer.readInt(), 0.0f, 0.0f, 0.0f, 0.0f, null, -1));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).field(0, "COMPONENT_ID", "unique id for this component").description("Container for canvas commands.");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mComponentId);
    }
}
