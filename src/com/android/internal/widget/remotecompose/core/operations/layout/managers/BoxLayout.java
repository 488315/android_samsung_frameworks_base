package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import android.security.keystore.KeyProperties;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class BoxLayout extends LayoutManager {
    public static final int BOTTOM = 5;
    public static final int CENTER = 2;
    public static final int END = 3;
    public static final int START = 1;
    public static final int TOP = 4;
    int mHorizontalPositioning;
    int mVerticalPositioning;

    public static int id() {
        return 202;
    }

    public BoxLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4) {
        super(component, i, i2, f, f2, f3, f4);
        this.mHorizontalPositioning = i3;
        this.mVerticalPositioning = i4;
    }

    public BoxLayout(Component component, int i, int i2, int i3, int i4) {
        this(component, i, i2, 0.0f, 0.0f, 0.0f, 0.0f, i3, i4);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "BOX [" + this.mComponentId + ":" + this.mAnimationId + "] (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + this.mVisibility;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "BOX";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            Component next = it.next();
            PaintContext paintContext2 = paintContext;
            float f3 = f;
            float f4 = f2;
            MeasurePass measurePass2 = measurePass;
            next.measure(paintContext2, 0.0f, f3, 0.0f, f4, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            if (!componentMeasure.isGone()) {
                size.setWidth(Math.max(size.getWidth(), componentMeasure.getW()));
                size.setHeight(Math.max(size.getHeight(), componentMeasure.getH()));
            }
            paintContext = paintContext2;
            f = f3;
            f2 = f4;
            measurePass = measurePass2;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            it.next().measure(paintContext, f, f2, f3, f4, measurePass);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
        float h;
        ComponentMeasure componentMeasure = measurePass.get(this);
        float w = (componentMeasure.getW() - this.mPaddingLeft) - this.mPaddingRight;
        float h2 = (componentMeasure.getH() - this.mPaddingTop) - this.mPaddingBottom;
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
        wireBuffer.start(202);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new BoxLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("Box layout implementation.\n\nChild components are laid out independently from one another,\n and painted in their hierarchy order (first children drawnbefore the latter). Horizontal and Vertical positioningare supported.").examplesDimension(150, 100).exampleImage("Top", "layout-BoxLayout-start-top.png").exampleImage("Center", "layout-BoxLayout-center-center.png").exampleImage("Bottom", "layout-BoxLayout-end-bottom.png").field(0, "COMPONENT_ID", "unique id for this component").field(0, "ANIMATION_ID", "id used to match components, for animation purposes").field(0, "HORIZONTAL_POSITIONING", "horizontal positioning value").possibleValues("START", 1).possibleValues("CENTER", 2).possibleValues("END", 3).field(0, "VERTICAL_POSITIONING", "vertical positioning value").possibleValues("TOP", 4).possibleValues("CENTER", 2).possibleValues("BOTTOM", 5);
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
