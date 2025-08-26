package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ScrollModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.WidthInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.utils.DebugLog;
import com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class RowLayout extends LayoutManager {
    public static final int BOTTOM = 5;
    public static final int CENTER = 2;
    public static final int END = 3;
    public static final int SPACE_AROUND = 8;
    public static final int SPACE_BETWEEN = 6;
    public static final int SPACE_EVENLY = 7;
    public static final int START = 1;
    public static final int TOP = 4;
    int mHorizontalPositioning;
    float mSpacedBy;
    int mVerticalPositioning;

    public static int id() {
        return 203;
    }

    public RowLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4, float f5) {
        super(component, i, i2, f, f2, f3, f4);
        this.mHorizontalPositioning = i3;
        this.mVerticalPositioning = i4;
        this.mSpacedBy = f5;
    }

    public RowLayout(Component component, int i, int i2, int i3, int i4, float f) {
        this(component, i, i2, 0.0f, 0.0f, 0.0f, 0.0f, i3, i4, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return getSerializedName() + " [" + this.mComponentId + ":" + this.mAnimationId + "] (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + this.mVisibility;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "ROW";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public boolean isInHorizontalFill() {
        return super.isInHorizontalFill() || childrenHaveHorizontalWeights();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$computeWrapSize$0() {
        return "COMPUTE WRAP SIZE in " + this + " (" + this.mComponentId + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        DebugLog.s(new StringValueSupplier() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier
            public final String getString() {
                return this.f$0.lambda$computeWrapSize$0();
            }
        });
        Iterator<Component> it = this.mChildrenComponents.iterator();
        int i = 0;
        float w = f;
        while (it.hasNext()) {
            Component next = it.next();
            PaintContext paintContext2 = paintContext;
            float f3 = f2;
            MeasurePass measurePass2 = measurePass;
            next.measure(paintContext2, 0.0f, w, 0.0f, f3, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            if (!componentMeasure.isGone()) {
                size.setWidth(size.getWidth() + componentMeasure.getW());
                size.setHeight(Math.max(size.getHeight(), componentMeasure.getH()));
                i++;
                w -= componentMeasure.getW();
            }
            paintContext = paintContext2;
            f2 = f3;
            measurePass = measurePass2;
        }
        if (!this.mChildrenComponents.isEmpty()) {
            size.setWidth(size.getWidth() + (this.mSpacedBy * (i - 1)));
        }
        DebugLog.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$computeSize$1() {
        return "COMPUTE SIZE in " + this + " (" + this.mComponentId + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        DebugLog.s(new StringValueSupplier() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout$$ExternalSyntheticLambda2
            @Override // com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier
            public final String getString() {
                return this.f$0.lambda$computeSize$1();
            }
        });
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float w = f2;
        while (it.hasNext()) {
            Component next = it.next();
            PaintContext paintContext2 = paintContext;
            float f5 = f;
            float f6 = f3;
            float f7 = f4;
            MeasurePass measurePass2 = measurePass;
            next.measure(paintContext2, f5, w, f6, f7, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            if (!componentMeasure.isGone()) {
                w -= componentMeasure.getW();
            }
            paintContext = paintContext2;
            f = f5;
            f3 = f6;
            f4 = f7;
            measurePass = measurePass2;
        }
        DebugLog.e();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicWidth(RemoteContext remoteContext) {
        float fComputeModifierDefinedWidth = computeModifierDefinedWidth(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float fMinIntrinsicWidth = 0.0f;
        while (it.hasNext()) {
            fMinIntrinsicWidth += it.next().minIntrinsicWidth(remoteContext);
        }
        return Math.max(fComputeModifierDefinedWidth, fMinIntrinsicWidth);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicHeight(RemoteContext remoteContext) {
        float fComputeModifierDefinedHeight = computeModifierDefinedHeight(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float fMax = 0.0f;
        while (it.hasNext()) {
            fMax = Math.max(fMax, it.next().minIntrinsicHeight(remoteContext));
        }
        return Math.max(fComputeModifierDefinedHeight, fMax);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
        int i;
        float f;
        float f2;
        final ComponentMeasure componentMeasure = measurePass.get(this);
        DebugLog.s(new StringValueSupplier() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier
            public final String getString() {
                return this.f$0.lambda$internalLayoutMeasure$2(componentMeasure);
            }
        });
        if (this.mChildrenComponents.isEmpty()) {
            DebugLog.e();
            return;
        }
        float w = (componentMeasure.getW() - this.mPaddingLeft) - this.mPaddingRight;
        float h = (componentMeasure.getH() - this.mPaddingTop) - this.mPaddingBottom;
        if (this.mComponentModifiers.hasHorizontalScroll()) {
            w = (this.mComponentModifiers.getHorizontalScrollDimension() - this.mPaddingLeft) - this.mPaddingRight;
        }
        float f3 = w;
        if (this.mComponentModifiers.hasVerticalScroll()) {
            h = (this.mComponentModifiers.getVerticalScrollDimension() - this.mPaddingTop) - this.mPaddingBottom;
        }
        float f4 = h;
        loop0: while (true) {
            boolean z = true;
            while (true) {
                i = 0;
                if (!z) {
                    break loop0;
                }
                Iterator<Component> it = this.mChildrenComponents.iterator();
                boolean z2 = false;
                float w2 = 0.0f;
                float value = 0.0f;
                while (it.hasNext()) {
                    Component next = it.next();
                    ComponentMeasure componentMeasure2 = measurePass.get(next);
                    if (!componentMeasure2.isGone()) {
                        if (next instanceof LayoutComponent) {
                            LayoutComponent layoutComponent = (LayoutComponent) next;
                            if (layoutComponent.getWidthModifier().hasWeight()) {
                                value += layoutComponent.getWidthModifier().getValue();
                                z2 = true;
                            }
                        }
                        w2 += componentMeasure2.getW();
                    }
                }
                if (z2) {
                    float f5 = f3 - w2;
                    Iterator<Component> it2 = this.mChildrenComponents.iterator();
                    while (it2.hasNext()) {
                        Component next2 = it2.next();
                        if (next2 instanceof LayoutComponent) {
                            LayoutComponent layoutComponent2 = (LayoutComponent) next2;
                            if (layoutComponent2.getWidthModifier().hasWeight()) {
                                ComponentMeasure componentMeasure3 = measurePass.get(next2);
                                if (!componentMeasure3.isGone()) {
                                    float value2 = (layoutComponent2.getWidthModifier().getValue() * f5) / value;
                                    WidthInModifierOperation widthIn = layoutComponent2.getWidthModifier().getWidthIn();
                                    if (widthIn != null) {
                                        float min = widthIn.getMin();
                                        float max = widthIn.getMax();
                                        if (min != -1.0f) {
                                            value2 = Math.max(min, value2);
                                        }
                                        if (max != -1.0f) {
                                            value2 = Math.min(max, value2);
                                        }
                                    }
                                    componentMeasure3.setW(value2);
                                    next2.measure(paintContext, componentMeasure3.getW(), componentMeasure3.getW(), componentMeasure3.getH(), componentMeasure3.getH(), measurePass);
                                }
                            }
                        }
                    }
                }
                if (!applyVisibility(f3, f4, measurePass) || !z2) {
                    z = false;
                }
            }
        }
        Iterator<Component> it3 = this.mChildrenComponents.iterator();
        float w3 = 0.0f;
        float fMax = 0.0f;
        while (it3.hasNext()) {
            ComponentMeasure componentMeasure4 = measurePass.get(it3.next());
            if (!componentMeasure4.isGone()) {
                w3 += componentMeasure4.getW();
                fMax = Math.max(fMax, componentMeasure4.getH());
                i++;
            }
        }
        float f6 = i - 1;
        float f7 = w3 + (this.mSpacedBy * f6);
        int i2 = this.mHorizontalPositioning;
        if (i2 == 1) {
            f = 0.0f;
            f2 = 0.0f;
        } else if (i2 == 2) {
            f = (f3 - f7) / 2.0f;
            f2 = 0.0f;
        } else if (i2 == 3) {
            f = f3 - f7;
            f2 = 0.0f;
        } else if (i2 == 6) {
            Iterator<Component> it4 = this.mChildrenComponents.iterator();
            float w4 = 0.0f;
            while (it4.hasNext()) {
                ComponentMeasure componentMeasure5 = measurePass.get(it4.next());
                if (!componentMeasure5.isGone()) {
                    w4 += componentMeasure5.getW();
                }
            }
            if (i > 1) {
                f2 = (f3 - w4) / f6;
                f = 0.0f;
            }
            f = (f3 - f7) / 2.0f;
            f2 = 0.0f;
        } else if (i2 != 7) {
            if (i2 == 8) {
                Iterator<Component> it5 = this.mChildrenComponents.iterator();
                float w5 = 0.0f;
                while (it5.hasNext()) {
                    ComponentMeasure componentMeasure6 = measurePass.get(it5.next());
                    if (!componentMeasure6.isGone()) {
                        w5 += componentMeasure6.getW();
                    }
                }
                f2 = (f3 - w5) / i;
                f = f2 / 2.0f;
            }
            f = 0.0f;
            f2 = 0.0f;
        } else {
            Iterator<Component> it6 = this.mChildrenComponents.iterator();
            float w6 = 0.0f;
            while (it6.hasNext()) {
                ComponentMeasure componentMeasure7 = measurePass.get(it6.next());
                if (!componentMeasure7.isGone()) {
                    w6 += componentMeasure7.getW();
                }
            }
            f2 = (f3 - w6) / (i + 1);
            f = f2;
        }
        Iterator<Component> it7 = this.mChildrenComponents.iterator();
        float h2 = 0.0f;
        while (it7.hasNext()) {
            ComponentMeasure componentMeasure8 = measurePass.get(it7.next());
            int i3 = this.mVerticalPositioning;
            if (i3 == 2) {
                h2 = (f4 - componentMeasure8.getH()) / 2.0f;
            } else if (i3 == 4) {
                h2 = 0.0f;
            } else if (i3 == 5) {
                h2 = f4 - componentMeasure8.getH();
            }
            componentMeasure8.setX(f);
            componentMeasure8.setY(h2);
            if (!componentMeasure8.isGone()) {
                float w7 = f + componentMeasure8.getW();
                int i4 = this.mHorizontalPositioning;
                if (i4 == 6 || i4 == 8 || i4 == 7) {
                    w7 += f2;
                }
                f = w7 + this.mSpacedBy;
            }
        }
        DebugLog.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$internalLayoutMeasure$2(ComponentMeasure componentMeasure) {
        return "INTERNAL LAYOUT " + this + " (" + this.mComponentId + ") children: " + this.mChildrenComponents.size() + " size (" + componentMeasure.getW() + " x " + componentMeasure.getH() + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void getLocationInWindow(float[] fArr, boolean z) {
        super.getLocationInWindow(fArr, z);
        if (z || !(this.mHorizontalScrollDelegate instanceof ScrollModifierOperation)) {
            return;
        }
        fArr[0] = fArr[0] + ((ScrollModifierOperation) this.mHorizontalScrollDelegate).getScrollX();
    }

    public static String name() {
        return "RowLayout";
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
        list.add(new RowLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("Row layout implementation, positioning components one after the other horizontally.\n\nIt supports weight and horizontal/vertical positioning.").examplesDimension(400, 100).exampleImage("Start", "layout-RowLayout-start-top.png").exampleImage("Center", "layout-RowLayout-center-top.png").exampleImage("End", "layout-RowLayout-end-top.png").exampleImage("SpaceEvenly", "layout-RowLayout-space-evenly-top.png").exampleImage("SpaceAround", "layout-RowLayout-space-around-top.png").exampleImage("SpaceBetween", "layout-RowLayout-space-between-top.png").field(0, "COMPONENT_ID", "unique id for this component").field(0, "ANIMATION_ID", "id used to match components, for animation purposes").field(0, "HORIZONTAL_POSITIONING", "horizontal positioning value").possibleValues("START", 1).possibleValues("CENTER", 2).possibleValues("END", 3).possibleValues("SPACE_BETWEEN", 6).possibleValues("SPACE_EVENLY", 7).possibleValues("SPACE_AROUND", 8).field(0, "VERTICAL_POSITIONING", "vertical positioning value").possibleValues("TOP", 4).possibleValues("CENTER", 2).possibleValues("BOTTOM", 5).field(1, "SPACED_BY", "Horizontal spacing between components");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mComponentId, this.mAnimationId, this.mHorizontalPositioning, this.mVerticalPositioning, this.mSpacedBy);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.add("verticalPositioning", getPositioningString(this.mVerticalPositioning));
        mapSerializer.add("horizontalPositioning", getPositioningString(this.mHorizontalPositioning));
        mapSerializer.add("spacedBy", Float.valueOf(this.mSpacedBy));
    }

    private String getPositioningString(int i) {
        switch (i) {
            case 1:
                return "START";
            case 2:
                return "CENTER";
            case 3:
                return "END";
            case 4:
                return "TOP";
            case 5:
                return "BOTTOM";
            case 6:
                return "SPACE_BETWEEN";
            case 7:
                return "SPACE_EVENLY";
            case 8:
                return "SPACE_AROUND";
            default:
                return KeyProperties.DIGEST_NONE;
        }
    }
}
