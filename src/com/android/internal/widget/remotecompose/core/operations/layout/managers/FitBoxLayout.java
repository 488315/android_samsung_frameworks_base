package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import android.security.keystore.KeyProperties;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HeightInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.WidthInModifierOperation;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class FitBoxLayout extends LayoutManager {
    public static final int BOTTOM = 5;
    public static final int CENTER = 2;
    public static final int END = 3;
    public static final int START = 1;
    public static final int TOP = 4;
    int mHorizontalPositioning;
    int mVerticalPositioning;

    public static int id() {
        return 176;
    }

    public FitBoxLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4) {
        super(component, i, i2, f, f2, f3, f4);
        this.mHorizontalPositioning = i3;
        this.mVerticalPositioning = i4;
    }

    public FitBoxLayout(Component component, int i, int i2, int i3, int i4) {
        this(component, i, i2, 0.0f, 0.0f, 0.0f, 0.0f, i3, i4);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "BOX [" + this.mComponentId + ":" + this.mAnimationId + "] (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + this.mVisibility;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "FITBOX";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        float f3;
        float f4;
        HeightInModifierOperation heightIn;
        WidthInModifierOperation widthIn;
        ComponentMeasure componentMeasure = measurePass.get(this);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            Component next = it.next();
            float min = 0.0f;
            if (next instanceof LayoutComponent) {
                LayoutComponent layoutComponent = (LayoutComponent) next;
                float min2 = (layoutComponent.getWidthModifier() == null || (widthIn = layoutComponent.getWidthModifier().getWidthIn()) == null) ? 0.0f : widthIn.getMin();
                if (layoutComponent.getHeightModifier() != null && (heightIn = layoutComponent.getHeightModifier().getHeightIn()) != null) {
                    min = heightIn.getMin();
                }
                f4 = min;
                f3 = min2;
            } else {
                f3 = 0.0f;
                f4 = 0.0f;
            }
            next.measure(paintContext, 0.0f, f, 0.0f, f2, measurePass);
            ComponentMeasure componentMeasure2 = measurePass.get(next);
            if (!z3 && f3 <= f && f4 <= f2) {
                componentMeasure2.addVisibilityOverride(32);
                size.setWidth(componentMeasure2.getW());
                size.setHeight(componentMeasure2.getH());
                z3 = true;
            } else {
                componentMeasure2.addVisibilityOverride(16);
            }
        }
        if (!z3) {
            componentMeasure.setVisibility(0);
        } else {
            componentMeasure.setVisibility(1);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        float f5;
        float f6;
        float f7;
        float f8;
        MeasurePass measurePass2;
        float f9;
        PaintContext paintContext2;
        float f10;
        HeightInModifierOperation heightIn;
        WidthInModifierOperation widthIn;
        measurePass.get(this);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        boolean z = false;
        while (it.hasNext()) {
            Component next = it.next();
            float min = 0.0f;
            if (next instanceof LayoutComponent) {
                LayoutComponent layoutComponent = (LayoutComponent) next;
                float min2 = (layoutComponent.getWidthModifier() == null || (widthIn = layoutComponent.getWidthModifier().getWidthIn()) == null) ? 0.0f : widthIn.getMin();
                if (layoutComponent.getHeightModifier() != null && (heightIn = layoutComponent.getHeightModifier().getHeightIn()) != null) {
                    min = heightIn.getMin();
                }
                float f11 = min;
                paintContext2 = paintContext;
                f10 = min2;
                f5 = f;
                f9 = f11;
                f6 = f2;
                f7 = f3;
                f8 = f4;
                measurePass2 = measurePass;
            } else {
                f5 = f;
                f6 = f2;
                f7 = f3;
                f8 = f4;
                measurePass2 = measurePass;
                f9 = 0.0f;
                paintContext2 = paintContext;
                f10 = 0.0f;
            }
            next.measure(paintContext2, f5, f6, f7, f8, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            componentMeasure.clearVisibilityOverride();
            if (!z && f10 <= f6 && f9 <= f8) {
                componentMeasure.addVisibilityOverride(32);
                z = true;
            } else {
                componentMeasure.addVisibilityOverride(16);
            }
            paintContext = paintContext2;
            f = f5;
            f2 = f6;
            f3 = f7;
            f4 = f8;
            measurePass = measurePass2;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
        float h;
        ComponentMeasure componentMeasure = measurePass.get(this);
        float w = (componentMeasure.getW() - this.mPaddingLeft) - this.mPaddingRight;
        float h2 = (componentMeasure.getH() - this.mPaddingTop) - this.mPaddingBottom;
        applyVisibility(w, h2, measurePass);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            ComponentMeasure componentMeasure2 = measurePass.get(it.next());
            int i = this.mVerticalPositioning;
            float w2 = 0.0f;
            if (i == 2) {
                h = (h2 - componentMeasure2.getH()) / 2.0f;
            } else {
                h = (i == 4 || i != 5) ? 0.0f : h2 - componentMeasure2.getH();
            }
            int i2 = this.mHorizontalPositioning;
            if (i2 != 1) {
                if (i2 == 2) {
                    w2 = (w - componentMeasure2.getW()) / 2.0f;
                } else if (i2 == 3) {
                    w2 = w - componentMeasure2.getW();
                }
            }
            componentMeasure2.setX(w2);
            componentMeasure2.setY(h);
        }
    }

    public static String name() {
        return "BoxLayout";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4) {
        wireBuffer.start(id());
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new FitBoxLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("FitBox layout implementation.\n\nOnly display the first child component that fits in the available space").examplesDimension(150, 100).exampleImage("Top", "layout-BoxLayout-start-top.png").exampleImage("Center", "layout-BoxLayout-center-center.png").exampleImage("Bottom", "layout-BoxLayout-end-bottom.png").field(0, "COMPONENT_ID", "unique id for this component").field(0, "ANIMATION_ID", "id used to match components, for animation purposes").field(0, "HORIZONTAL_POSITIONING", "horizontal positioning value").possibleValues("START", 1).possibleValues("CENTER", 2).possibleValues("END", 3).field(0, "VERTICAL_POSITIONING", "vertical positioning value").possibleValues("TOP", 4).possibleValues("CENTER", 2).possibleValues("BOTTOM", 5);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mComponentId, this.mAnimationId, this.mHorizontalPositioning, this.mVerticalPositioning);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.add("verticalPositioning", getPositioningString(this.mVerticalPositioning));
        mapSerializer.add("horizontalPositioning", getPositioningString(this.mHorizontalPositioning));
    }

    private String getPositioningString(int i) {
        if (i == 1) {
            return "START";
        }
        if (i == 2) {
            return "CENTER";
        }
        if (i == 3) {
            return "END";
        }
        if (i == 4) {
            return "TOP";
        }
        if (i == 5) {
            return "BOTTOM";
        }
        return KeyProperties.DIGEST_NONE;
    }
}
