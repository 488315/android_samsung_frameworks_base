package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.CollapsiblePriorityModifierOperation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class CollapsibleRowLayout extends RowLayout {
    public static int id() {
        return 230;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public boolean hasHorizontalIntrinsicDimension() {
        return true;
    }

    public CollapsibleRowLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4, float f5) {
        super(component, i, i2, f, f2, f3, f4, i3, i4, f5);
    }

    public CollapsibleRowLayout(Component component, int i, int i2, int i3, int i4, float f) {
        super(component, i, i2, i3, i4, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout, com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "COLLAPSIBLE_ROW";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, float f) {
        wireBuffer.start(id());
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new CollapsibleRowLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicHeight(RemoteContext remoteContext) {
        float computeModifierDefinedHeight = computeModifierDefinedHeight(remoteContext);
        return !this.mChildrenComponents.isEmpty() ? computeModifierDefinedHeight + this.mChildrenComponents.get(0).minIntrinsicHeight(remoteContext) : computeModifierDefinedHeight;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicWidth(RemoteContext remoteContext) {
        float computeModifierDefinedWidth = computeModifierDefinedWidth(remoteContext);
        return !this.mChildrenComponents.isEmpty() ? computeModifierDefinedWidth + this.mChildrenComponents.get(0).minIntrinsicWidth(remoteContext) : computeModifierDefinedWidth;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        computeVisibleChildren(paintContext, f, f2, z, z2, measurePass, size);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        computeVisibleChildren(paintContext, f2, f4, false, false, measurePass, null);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
        super.internalLayoutMeasure(paintContext, measurePass);
        ComponentMeasure componentMeasure = measurePass.get(this);
        computeVisibleChildren(paintContext, componentMeasure.getW(), componentMeasure.getH(), false, false, measurePass, null);
    }

    private void computeVisibleChildren(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        float f3;
        MeasurePass measurePass2 = measurePass;
        ComponentMeasure componentMeasure = measurePass2.get(this);
        componentMeasure.addVisibilityOverride(32);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        boolean z3 = false;
        float f4 = f;
        int i = 0;
        boolean z4 = false;
        while (it.hasNext()) {
            Component next = it.next();
            if (measurePass2.contains(next.getComponentId())) {
                f3 = f4;
            } else if (next instanceof CollapsibleRowLayout) {
                next.measure(paintContext, 0.0f, f4, 0.0f, f2, measurePass2);
                f3 = f4;
                measurePass2 = measurePass;
            } else {
                f3 = f4;
                measurePass2 = measurePass;
                next.measure(paintContext, 0.0f, Float.MAX_VALUE, 0.0f, f2, measurePass2);
            }
            ComponentMeasure componentMeasure2 = measurePass2.get(next);
            if (componentMeasure2.isGone()) {
                f4 = f3;
            } else {
                if (size != null) {
                    size.setHeight(Math.max(size.getHeight(), componentMeasure2.getH()));
                    size.setWidth(size.getWidth() + componentMeasure2.getW());
                }
                i++;
                f4 = f3 - componentMeasure2.getW();
            }
            if ((next instanceof LayoutComponent) && ((CollapsiblePriorityModifierOperation) ((LayoutComponent) next).selfOrModifier(CollapsiblePriorityModifierOperation.class)) != null) {
                z4 = true;
            }
        }
        if (!this.mChildrenComponents.isEmpty() && size != null) {
            size.setWidth(size.getWidth() + (this.mSpacedBy * (i - 1)));
        }
        ArrayList<Component> arrayList = this.mChildrenComponents;
        if (z4) {
            arrayList = CollapsiblePriority.sortWithPriorities(this.mChildrenComponents, 0);
        }
        Iterator<Component> it2 = arrayList.iterator();
        float f5 = 0.0f;
        float f6 = 0.0f;
        while (it2.hasNext()) {
            ComponentMeasure componentMeasure3 = measurePass2.get(it2.next());
            if (z3 || componentMeasure3.isGone()) {
                componentMeasure3.addVisibilityOverride(16);
            } else {
                float w = componentMeasure3.getW() + f5;
                if (w > f) {
                    componentMeasure3.addVisibilityOverride(16);
                    z3 = true;
                } else {
                    f6 = Math.max(f6, componentMeasure3.getH());
                    i++;
                    f5 = w;
                }
            }
        }
        if (z && size != null) {
            size.setWidth(Math.min(f, f5));
        }
        if (i == 0 || (size != null && size.getWidth() <= 0.0f)) {
            componentMeasure.addVisibilityOverride(16);
        }
    }
}
