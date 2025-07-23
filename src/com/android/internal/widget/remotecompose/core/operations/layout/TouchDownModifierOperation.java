package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class TouchDownModifierOperation extends ListActionsOperation implements TouchHandler {
    private static final int OP_CODE = 219;

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchCancel(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDrag(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchUp(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2, float f3, float f4) {
    }

    public TouchDownModifierOperation() {
        super("TOUCH_DOWN_MODIFIER");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation
    public String toString() {
        return "TouchDownModifier";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        RootLayoutComponent rootLayoutComponent = remoteContext.getDocument().getRootLayoutComponent();
        if (rootLayoutComponent != null) {
            rootLayoutComponent.setHasTouchListeners(true);
        }
        super.apply(remoteContext);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDown(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        if (applyActions(remoteContext, coreDocument, component, f, f2, false)) {
            coreDocument.appliedTouchOperation(component);
        }
    }

    public static String name() {
        return "TouchModifier";
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(219);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TouchDownModifierOperation());
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 219, name()).description("Touch down modifier. This operation contains a list of action executed on Touch down");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.addType("TouchDownModifierOperation");
    }
}
