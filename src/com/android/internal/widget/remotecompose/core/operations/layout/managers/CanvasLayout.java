package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class CanvasLayout extends BoxLayout {
    public static int id() {
        return 205;
    }

    public CanvasLayout(Component component, int i, int i2, float f, float f2, float f3, float f4) {
        super(component, i, i2, f, f2, f3, f4, 0, 0);
    }

    public CanvasLayout(Component component, int i, int i2) {
        this(component, i, i2, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.BoxLayout, com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "CANVAS [" + this.mComponentId + ":" + this.mAnimationId + "] (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + this.mVisibility;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.BoxLayout, com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "CANVAS";
    }

    public static String name() {
        return "CanvasLayout";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(205);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new CanvasLayout(null, wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("Canvas implementation. Encapsulate draw operations.\n\n").field(0, "COMPONENT_ID", "unique id for this component").field(0, "ANIMATION_ID", "id used to match components, for animation purposes");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.BoxLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
        ComponentMeasure componentMeasure = measurePass.get(this);
        float w = (componentMeasure.getW() - this.mPaddingLeft) - this.mPaddingRight;
        float h = (componentMeasure.getH() - this.mPaddingTop) - this.mPaddingBottom;
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            ComponentMeasure componentMeasure2 = measurePass.get(it.next());
            componentMeasure2.setX(0.0f);
            componentMeasure2.setY(0.0f);
            componentMeasure2.setW(w);
            componentMeasure2.setH(h);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.BoxLayout, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mComponentId, this.mAnimationId);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.BoxLayout, com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.addType(getSerializedName());
    }
}
