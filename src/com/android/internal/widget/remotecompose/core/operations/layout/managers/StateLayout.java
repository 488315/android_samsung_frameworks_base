package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class StateLayout extends LayoutManager {
    public int MAX_CACHE_ELEMENTS;
    public int[] cacheListElementsId;
    public int currentLayoutIndex;
    public boolean inTransition;
    private int mIndexId;
    public int measuredLayoutIndex;
    public int previousLayoutIndex;
    public Map<Integer, Component[]> statePaintedComponents;

    public StateLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3) {
        super(component, i, i2, f, f2, f3, f4);
        this.measuredLayoutIndex = 0;
        this.currentLayoutIndex = 0;
        this.previousLayoutIndex = 0;
        this.mIndexId = 0;
        this.statePaintedComponents = new HashMap();
        this.MAX_CACHE_ELEMENTS = 16;
        this.cacheListElementsId = new int[16];
        this.inTransition = false;
        this.mIndexId = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void inflate() {
        super.inflate();
        hideLayoutsOtherThan(this.currentLayoutIndex);
    }

    public void findAnimatedComponents() {
        for (int i = 0; i < this.mChildrenComponents.size(); i++) {
            Component component = this.mChildrenComponents.get(i);
            if (component instanceof LayoutComponent) {
                LayoutComponent layoutComponent = (LayoutComponent) component;
                layoutComponent.setX(0.0f);
                layoutComponent.setY(0.0f);
                ArrayList<Component> childrenComponents = layoutComponent.getChildrenComponents();
                for (int i2 = 0; i2 < childrenComponents.size(); i2++) {
                    Component component2 = childrenComponents.get(i2);
                    if (component2.getAnimationId() != -1) {
                        if (!this.statePaintedComponents.containsKey(Integer.valueOf(component2.getAnimationId()))) {
                            this.statePaintedComponents.put(Integer.valueOf(component2.getAnimationId()), new Component[this.mChildrenComponents.size()]);
                        }
                        this.statePaintedComponents.get(Integer.valueOf(component2.getAnimationId()))[i] = component2;
                    }
                }
            }
        }
        collapsePaintedComponents();
    }

    public void collapsePaintedComponents() {
        int i;
        int size = this.mChildrenComponents.size();
        Iterator<Integer> it = this.statePaintedComponents.keySet().iterator();
        while (it.hasNext()) {
            Component[] componentArr = this.statePaintedComponents.get(it.next());
            if (componentArr.length > 1) {
                Component component = componentArr[0];
                if (component != null) {
                    while (true) {
                        if (i < componentArr.length) {
                            Operation operation = componentArr[i];
                            i = (operation != null && component.suitableForTransition(operation)) ? i + 1 : 1;
                        } else {
                            for (int i2 = 0; i2 < size; i2++) {
                                componentArr[i2] = component;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        getLayout(this.currentLayoutIndex).computeSize(paintContext, f, f2, f3, f4, measurePass);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
        getLayout(this.currentLayoutIndex).internalLayoutMeasure(paintContext, measurePass);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        getLayout(this.currentLayoutIndex).computeWrapSize(paintContext, f, f2, z, z2, measurePass, size);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void onClick(RemoteContext remoteContext, CoreDocument coreDocument, float f, float f2) {
        if (contains(f, f2)) {
            getLayout(this.currentLayoutIndex).onClick(remoteContext, coreDocument, f, f2);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public void layout(RemoteContext remoteContext, MeasurePass measurePass) {
        int i;
        ComponentMeasure componentMeasure = measurePass.get(this);
        super.selfLayout(remoteContext, measurePass);
        LayoutManager layout = getLayout(this.currentLayoutIndex);
        measurePass.get(layout.getComponentId()).copyFrom(componentMeasure);
        layout.layout(remoteContext, measurePass);
        if (this.inTransition && (i = this.previousLayoutIndex) != this.currentLayoutIndex) {
            Iterator<Component> it = getLayout(i).getChildrenComponents().iterator();
            while (it.hasNext()) {
                Component next = it.next();
                int componentId = next.getComponentId();
                if (next.getAnimationId() != -1) {
                    componentId = next.getAnimationId();
                    for (Component component : this.statePaintedComponents.get(Integer.valueOf(componentId))) {
                        if (component != null) {
                            component.layout(remoteContext, measurePass);
                        }
                    }
                }
                if (measurePass.contains(componentId)) {
                    next.layout(remoteContext, measurePass);
                }
            }
        }
        this.mFirstLayout = false;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public void measure(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        int i;
        Component[] componentArr;
        if (this.statePaintedComponents.isEmpty()) {
            findAnimatedComponents();
        }
        LayoutManager layout = getLayout(this.currentLayoutIndex);
        if (this.inTransition) {
            int i2 = this.currentLayoutIndex;
            int i3 = this.previousLayoutIndex;
            if (i2 != i3) {
                LayoutManager layout2 = getLayout(i3);
                Iterator<Component> it = layout.getChildrenComponents().iterator();
                while (it.hasNext()) {
                    Component next = it.next();
                    int animationId = next.getAnimationId();
                    if (animationId != -1) {
                        Iterator<Component> it2 = layout2.getChildrenComponents().iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            if (it2.next().getAnimationId() == animationId) {
                                Component component = this.statePaintedComponents.get(Integer.valueOf(next.getAnimationId()))[this.previousLayoutIndex];
                                if (next != component) {
                                    next.measure(paintContext, component.getWidth(), component.getWidth(), component.getHeight(), component.getHeight(), measurePass);
                                    next.layout(paintContext.getContext(), measurePass);
                                    next.setX(component.getX());
                                    next.setY(component.getY());
                                    next.mVisibility = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        layout.measure(paintContext, f, f2, f3, f4, measurePass);
        Iterator<Component> it3 = layout.getChildrenComponents().iterator();
        while (it3.hasNext()) {
            Component next2 = it3.next();
            ComponentMeasure componentMeasure = measurePass.get(next2);
            if (next2.getAnimationId() != -1) {
                ComponentMeasure componentMeasure2 = measurePass.get(next2.getAnimationId());
                componentMeasure2.copyFrom(componentMeasure);
                componentMeasure2.setVisibility(1);
                Component[] componentArr2 = this.statePaintedComponents.get(Integer.valueOf(next2.getAnimationId()));
                int i4 = 0;
                while (i4 < componentArr2.length) {
                    Component[] componentArr3 = componentArr2;
                    Component component2 = componentArr3[i4];
                    if (component2 != null) {
                        ComponentMeasure componentMeasure3 = measurePass.get(component2.getComponentId());
                        if (next2 != component2) {
                            componentMeasure3.copyFrom(componentMeasure);
                        }
                        if (i4 == this.currentLayoutIndex) {
                            componentMeasure3.setVisibility(1);
                        } else if (next2 != component2) {
                            componentMeasure3.setVisibility(0);
                        }
                        if (next2 != component2) {
                            componentArr = componentArr3;
                            i = i4;
                            component2.measure(paintContext, componentMeasure2.getW(), componentMeasure2.getW(), componentMeasure2.getH(), componentMeasure2.getH(), measurePass);
                            i4 = i + 1;
                            componentArr2 = componentArr;
                        }
                    }
                    i = i4;
                    componentArr = componentArr3;
                    i4 = i + 1;
                    componentArr2 = componentArr;
                }
            } else {
                componentMeasure.setVisibility(1);
            }
        }
        int i5 = this.previousLayoutIndex;
        if (i5 != this.currentLayoutIndex) {
            Iterator<Component> it4 = getLayout(i5).getChildrenComponents().iterator();
            while (it4.hasNext()) {
                Component next3 = it4.next();
                int componentId = next3.getComponentId();
                if (next3.getAnimationId() != -1) {
                    componentId = next3.getAnimationId();
                }
                if (!measurePass.contains(componentId)) {
                    ComponentMeasure componentMeasure4 = measurePass.get(next3.getComponentId());
                    componentMeasure4.setX(next3.getX());
                    componentMeasure4.setY(next3.getY());
                    componentMeasure4.setW(next3.getWidth());
                    componentMeasure4.setH(next3.getHeight());
                    componentMeasure4.setVisibility(0);
                }
            }
        }
        measurePass.get(this).copyFrom(measurePass.get(layout));
        this.measuredLayoutIndex = this.currentLayoutIndex;
    }

    public void hideLayoutsOtherThan(int i) {
        Iterator<Component> it = this.mChildrenComponents.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Component next = it.next();
            if (next instanceof LayoutComponent) {
                if (i2 != i) {
                    next.mVisibility = 0;
                } else {
                    next.mVisibility = 1;
                }
                i2++;
            }
        }
    }

    public LayoutManager getLayout(int i) {
        Iterator<Component> it = this.mChildrenComponents.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Component next = it.next();
            if (next instanceof LayoutComponent) {
                if (i2 == i) {
                    return (LayoutManager) next;
                }
                i2++;
            }
        }
        return (LayoutManager) this.mChildrenComponents.get(0);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        Component component;
        int integer;
        int i;
        if (this.mIndexId != 0 && (integer = paintContext.getContext().mRemoteComposeState.getInteger(this.mIndexId)) != (i = this.currentLayoutIndex)) {
            this.previousLayoutIndex = i;
            this.currentLayoutIndex = integer;
            this.inTransition = true;
            invalidateMeasure();
        }
        Iterator<Component> it = this.mChildrenComponents.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Component next = it.next();
            if (next instanceof LayoutComponent) {
                if (i2 != this.currentLayoutIndex && i2 != this.previousLayoutIndex) {
                    next.mVisibility = 0;
                }
                if (i2 == this.currentLayoutIndex && !next.isVisible()) {
                    next.mVisibility = 1;
                }
                i2++;
            }
        }
        LayoutManager layout = getLayout(this.measuredLayoutIndex);
        boolean z = this.inTransition && this.previousLayoutIndex != this.measuredLayoutIndex;
        if (z) {
            LayoutManager layout2 = getLayout(this.previousLayoutIndex);
            int size = layout2.getChildrenComponents().size();
            int i3 = this.MAX_CACHE_ELEMENTS;
            if (size > i3) {
                int i4 = i3 * 2;
                this.MAX_CACHE_ELEMENTS = i4;
                this.cacheListElementsId = new int[i4];
            }
            layout2.applyAnimationAsNeeded(paintContext);
            Iterator<Component> it2 = layout2.getChildrenComponents().iterator();
            int i5 = 0;
            while (it2.hasNext()) {
                this.cacheListElementsId[i5] = it2.next().getPaintId();
                i5++;
            }
            Iterator<Component> it3 = layout.getChildrenComponents().iterator();
            int i6 = i5;
            while (it3.hasNext()) {
                int paintId = it3.next().getPaintId();
                for (int i7 = 0; i7 < i5; i7++) {
                    int[] iArr = this.cacheListElementsId;
                    if (iArr[i7] == paintId) {
                        iArr[i7] = -1;
                        i6--;
                    }
                }
            }
            if (i6 > 0) {
                paintContext.save();
                paintContext.translate(layout2.getX(), layout2.getY());
                Iterator<Component> it4 = layout2.getChildrenComponents().iterator();
                while (it4.hasNext()) {
                    Component next2 = it4.next();
                    int paintId2 = next2.getPaintId();
                    int i8 = 0;
                    while (true) {
                        if (i8 >= i5) {
                            break;
                        }
                        if (this.cacheListElementsId[i8] == paintId2) {
                            next2.paint(paintContext);
                            break;
                        }
                        i8++;
                    }
                }
                paintContext.restore();
            }
            layout.applyAnimationAsNeeded(paintContext);
        }
        paintContext.save();
        paintContext.translate(layout.getX(), layout.getY());
        Iterator<Operation> it5 = layout.getList().iterator();
        while (it5.hasNext()) {
            Operation next3 = it5.next();
            if (next3 instanceof Component) {
                Component component2 = (Component) next3;
                if (component2.getAnimationId() != -1) {
                    Component[] componentArr = this.statePaintedComponents.get(Integer.valueOf(component2.getAnimationId()));
                    Component component3 = componentArr[this.measuredLayoutIndex];
                    if (z && (component = componentArr[this.previousLayoutIndex]) != null && component3 != component) {
                        component.paint(paintContext);
                    }
                    component3.paint(paintContext);
                }
            }
            if (next3 instanceof PaintOperation) {
                ((PaintOperation) next3).paint(paintContext);
            }
        }
        paintContext.restore();
        if (z) {
            checkEndOfTransition();
        }
    }

    public void checkEndOfTransition() {
        LayoutManager layout = getLayout(this.measuredLayoutIndex);
        LayoutManager layout2 = getLayout(this.previousLayoutIndex);
        if (this.inTransition && layout.mAnimateMeasure == null && layout2.mAnimateMeasure == null) {
            this.inTransition = false;
            LayoutManager layout3 = getLayout(this.previousLayoutIndex);
            if (layout3 == layout || layout3.isGone()) {
                return;
            }
            layout3.mVisibility = 0;
            layout3.needsRepaint();
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "STATE_LAYOUT";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, int i5) {
        wireBuffer.start(217);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeInt(i5);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        wireBuffer.readInt();
        wireBuffer.readInt();
        list.add(new StateLayout(null, readInt, readInt2, 0.0f, 0.0f, 100.0f, 100.0f, wireBuffer.readInt()));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.add("indexId", Integer.valueOf(this.mIndexId));
    }
}
