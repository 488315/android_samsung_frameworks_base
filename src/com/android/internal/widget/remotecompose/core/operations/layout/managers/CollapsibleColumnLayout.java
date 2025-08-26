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
public class CollapsibleColumnLayout extends ColumnLayout {
    public static int id() {
        return 233;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public boolean hasVerticalIntrinsicDimension() {
        return true;
    }

    public CollapsibleColumnLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4, float f5) {
        super(component, i, i2, f, f2, f3, f4, i3, i4, f5);
    }

    public CollapsibleColumnLayout(Component component, int i, int i2, int i3, int i4, float f) {
        super(component, i, i2, i3, i4, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout, com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "COLLAPSIBLE_COLUMN";
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
        list.add(new CollapsibleColumnLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicHeight(RemoteContext remoteContext) {
        float fComputeModifierDefinedHeight = computeModifierDefinedHeight(remoteContext);
        return !this.mChildrenComponents.isEmpty() ? fComputeModifierDefinedHeight + this.mChildrenComponents.get(0).minIntrinsicHeight(remoteContext) : fComputeModifierDefinedHeight;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicWidth(RemoteContext remoteContext) {
        float fComputeModifierDefinedWidth = computeModifierDefinedWidth(remoteContext);
        return !this.mChildrenComponents.isEmpty() ? fComputeModifierDefinedWidth + this.mChildrenComponents.get(0).minIntrinsicWidth(remoteContext) : fComputeModifierDefinedWidth;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        computeVisibleChildren(paintContext, f, f2, z, z2, measurePass, size);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        computeVisibleChildren(paintContext, f2, f4, false, false, measurePass, null);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout, com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
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
        float h = f2;
        int i = 0;
        boolean z4 = false;
        while (it.hasNext()) {
            Component next = it.next();
            if (measurePass2.contains(next.getComponentId())) {
                f3 = h;
            } else if (next instanceof CollapsibleColumnLayout) {
                next.measure(paintContext, 0.0f, f, 0.0f, h, measurePass2);
                f3 = h;
                measurePass2 = measurePass;
            } else {
                f3 = h;
                measurePass2 = measurePass;
                next.measure(paintContext, 0.0f, f, 0.0f, Float.MAX_VALUE, measurePass2);
            }
            ComponentMeasure componentMeasure2 = measurePass2.get(next);
            if (componentMeasure2.isGone()) {
                h = f3;
            } else {
                if (size != null) {
                    size.setWidth(Math.max(size.getWidth(), componentMeasure2.getW()));
                    size.setHeight(size.getHeight() + componentMeasure2.getH());
                }
                i++;
                h = f3 - componentMeasure2.getH();
            }
            if ((next instanceof LayoutComponent) && ((CollapsiblePriorityModifierOperation) ((LayoutComponent) next).selfOrModifier(CollapsiblePriorityModifierOperation.class)) != null) {
                z4 = true;
            }
        }
        if (!this.mChildrenComponents.isEmpty() && size != null) {
            size.setHeight(size.getHeight() + (this.mSpacedBy * (i - 1)));
        }
        ArrayList<Component> arrayListSortWithPriorities = this.mChildrenComponents;
        if (z4) {
            arrayListSortWithPriorities = CollapsiblePriority.sortWithPriorities(this.mChildrenComponents, 1);
        }
        Iterator<Component> it2 = arrayListSortWithPriorities.iterator();
        float f4 = 0.0f;
        float fMax = 0.0f;
        while (it2.hasNext()) {
            ComponentMeasure componentMeasure3 = measurePass2.get(it2.next());
            if (z3 || componentMeasure3.isGone()) {
                componentMeasure3.addVisibilityOverride(16);
            } else {
                float h2 = componentMeasure3.getH() + f4;
                if (h2 > f2) {
                    componentMeasure3.addVisibilityOverride(16);
                    z3 = true;
                } else {
                    fMax = Math.max(fMax, componentMeasure3.getW());
                    i++;
                    f4 = h2;
                }
            }
        }
        if (z2 && size != null) {
            size.setHeight(Math.min(f2, f4));
        }
        if (i == 0 || (size != null && size.getHeight() <= 0.0f)) {
            componentMeasure.addVisibilityOverride(16);
        }
    }
}
