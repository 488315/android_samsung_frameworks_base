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
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HeightInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ScrollModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.utils.DebugLog;
import com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ColumnLayout extends LayoutManager {
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
        return 204;
    }

    public ColumnLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4, float f5) {
        super(component, i, i2, f, f2, f3, f4);
        this.mHorizontalPositioning = i3;
        this.mVerticalPositioning = i4;
        this.mSpacedBy = f5;
    }

    public ColumnLayout(Component component, int i, int i2, int i3, int i4, float f) {
        this(component, i, i2, 0.0f, 0.0f, 0.0f, 0.0f, i3, i4, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return getSerializedName() + " [" + this.mComponentId + ":" + this.mAnimationId + "] (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + this.mVisibility;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "COLUMN";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public boolean isInVerticalFill() {
        return super.isInVerticalFill() || childrenHaveVerticalWeights();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$computeWrapSize$0() {
        return "COMPUTE WRAP SIZE in " + this + " (" + this.mComponentId + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        DebugLog.s(new StringValueSupplier() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier
            public final String getString() {
                String lambda$computeWrapSize$0;
                lambda$computeWrapSize$0 = ColumnLayout.this.lambda$computeWrapSize$0();
                return lambda$computeWrapSize$0;
            }
        });
        Iterator<Component> it = this.mChildrenComponents.iterator();
        int i = 0;
        float f3 = f2;
        while (it.hasNext()) {
            Component next = it.next();
            PaintContext paintContext2 = paintContext;
            float f4 = f;
            MeasurePass measurePass2 = measurePass;
            next.measure(paintContext2, 0.0f, f4, 0.0f, f3, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            if (!componentMeasure.isGone()) {
                size.setWidth(Math.max(size.getWidth(), componentMeasure.getW()));
                size.setHeight(size.getHeight() + componentMeasure.getH());
                i++;
                f3 -= componentMeasure.getH();
            }
            paintContext = paintContext2;
            f = f4;
            measurePass = measurePass2;
        }
        if (!this.mChildrenComponents.isEmpty()) {
            size.setHeight(size.getHeight() + (this.mSpacedBy * (i - 1)));
        }
        DebugLog.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$computeSize$1() {
        return "COMPUTE SIZE in " + this + " (" + this.mComponentId + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        DebugLog.s(new StringValueSupplier() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout$$ExternalSyntheticLambda2
            @Override // com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier
            public final String getString() {
                String lambda$computeSize$1;
                lambda$computeSize$1 = ColumnLayout.this.lambda$computeSize$1();
                return lambda$computeSize$1;
            }
        });
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float f5 = f4;
        while (it.hasNext()) {
            Component next = it.next();
            PaintContext paintContext2 = paintContext;
            float f6 = f;
            float f7 = f2;
            float f8 = f3;
            MeasurePass measurePass2 = measurePass;
            next.measure(paintContext2, f6, f7, f8, f5, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            if (!componentMeasure.isGone()) {
                f5 -= componentMeasure.getH();
            }
            paintContext = paintContext2;
            f = f6;
            f2 = f7;
            f3 = f8;
            measurePass = measurePass2;
        }
        DebugLog.e();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicHeight(RemoteContext remoteContext) {
        float computeModifierDefinedHeight = computeModifierDefinedHeight(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float f = 0.0f;
        while (it.hasNext()) {
            f += it.next().minIntrinsicHeight(remoteContext);
        }
        return Math.max(computeModifierDefinedHeight, f);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
        int i;
        float f;
        float f2;
        final ComponentMeasure componentMeasure = measurePass.get(this);
        DebugLog.s(new StringValueSupplier() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.operations.layout.utils.StringValueSupplier
            public final String getString() {
                String lambda$internalLayoutMeasure$2;
                lambda$internalLayoutMeasure$2 = ColumnLayout.this.lambda$internalLayoutMeasure$2(componentMeasure);
                return lambda$internalLayoutMeasure$2;
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
                float f5 = 0.0f;
                if (!z) {
                    break loop0;
                }
                Iterator<Component> it = this.mChildrenComponents.iterator();
                float f6 = 0.0f;
                boolean z2 = false;
                while (it.hasNext()) {
                    Component next = it.next();
                    ComponentMeasure componentMeasure2 = measurePass.get(next);
                    if (!componentMeasure2.isGone()) {
                        if (next instanceof LayoutComponent) {
                            LayoutComponent layoutComponent = (LayoutComponent) next;
                            if (layoutComponent.getHeightModifier().hasWeight()) {
                                f6 += layoutComponent.getHeightModifier().getValue();
                                z2 = true;
                            }
                        }
                        f5 += componentMeasure2.getH();
                    }
                }
                if (z2) {
                    float f7 = f4 - f5;
                    Iterator<Component> it2 = this.mChildrenComponents.iterator();
                    while (it2.hasNext()) {
                        Component next2 = it2.next();
                        if (next2 instanceof LayoutComponent) {
                            LayoutComponent layoutComponent2 = (LayoutComponent) next2;
                            if (layoutComponent2.getHeightModifier().hasWeight()) {
                                ComponentMeasure componentMeasure3 = measurePass.get(next2);
                                if (!componentMeasure3.isGone()) {
                                    float value = (layoutComponent2.getHeightModifier().getValue() * f7) / f6;
                                    HeightInModifierOperation heightIn = layoutComponent2.getHeightModifier().getHeightIn();
                                    if (heightIn != null) {
                                        float min = heightIn.getMin();
                                        float max = heightIn.getMax();
                                        if (min != -1.0f) {
                                            value = Math.max(min, value);
                                        }
                                        if (max != -1.0f) {
                                            value = Math.min(max, value);
                                        }
                                    }
                                    componentMeasure3.setH(value);
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
        float f8 = 0.0f;
        float f9 = 0.0f;
        while (it3.hasNext()) {
            ComponentMeasure componentMeasure4 = measurePass.get(it3.next());
            if (!componentMeasure4.isGone()) {
                f9 = Math.max(f9, componentMeasure4.getW());
                f8 += componentMeasure4.getH();
                i++;
            }
        }
        float f10 = i - 1;
        float f11 = f8 + (this.mSpacedBy * f10);
        switch (this.mVerticalPositioning) {
            case 2:
                f = (f4 - f11) / 2.0f;
                f2 = 0.0f;
                break;
            case 3:
            case 4:
            default:
                f = 0.0f;
                f2 = 0.0f;
                break;
            case 5:
                f = f4 - f11;
                f2 = 0.0f;
                break;
            case 6:
                Iterator<Component> it4 = this.mChildrenComponents.iterator();
                float f12 = 0.0f;
                while (it4.hasNext()) {
                    ComponentMeasure componentMeasure5 = measurePass.get(it4.next());
                    if (!componentMeasure5.isGone()) {
                        f12 += componentMeasure5.getH();
                    }
                }
                if (i > 1) {
                    f2 = (f4 - f12) / f10;
                    f = 0.0f;
                    break;
                }
                f = (f4 - f11) / 2.0f;
                f2 = 0.0f;
                break;
            case 7:
                Iterator<Component> it5 = this.mChildrenComponents.iterator();
                float f13 = 0.0f;
                while (it5.hasNext()) {
                    ComponentMeasure componentMeasure6 = measurePass.get(it5.next());
                    if (!componentMeasure6.isGone()) {
                        f13 += componentMeasure6.getH();
                    }
                }
                f2 = (f4 - f13) / (i + 1);
                f = f2;
                break;
            case 8:
                Iterator<Component> it6 = this.mChildrenComponents.iterator();
                float f14 = 0.0f;
                while (it6.hasNext()) {
                    ComponentMeasure componentMeasure7 = measurePass.get(it6.next());
                    if (!componentMeasure7.isGone()) {
                        f14 += componentMeasure7.getH();
                    }
                }
                f2 = (f4 - f14) / i;
                f = f2 / 2.0f;
                break;
        }
        Iterator<Component> it7 = this.mChildrenComponents.iterator();
        float f15 = 0.0f;
        while (it7.hasNext()) {
            ComponentMeasure componentMeasure8 = measurePass.get(it7.next());
            int i2 = this.mHorizontalPositioning;
            if (i2 == 1) {
                f15 = 0.0f;
            } else if (i2 == 2) {
                f15 = (f3 - componentMeasure8.getW()) / 2.0f;
            } else if (i2 == 3) {
                f15 = f3 - componentMeasure8.getW();
            }
            componentMeasure8.setX(f15);
            componentMeasure8.setY(f);
            if (!componentMeasure8.isGone()) {
                float h2 = f + componentMeasure8.getH();
                int i3 = this.mVerticalPositioning;
                if (i3 == 6 || i3 == 8 || i3 == 7) {
                    h2 += f2;
                }
                f = h2 + this.mSpacedBy;
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
        if (z || !(this.mVerticalScrollDelegate instanceof ScrollModifierOperation)) {
            return;
        }
        fArr[1] = fArr[1] + ((ScrollModifierOperation) this.mVerticalScrollDelegate).getScrollY();
    }

    public static String name() {
        return "ColumnLayout";
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
        list.add(new ColumnLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("Column layout implementation, positioning components one after the other vertically.\n\nIt supports weight and horizontal/vertical positioning.").examplesDimension(100, 400).exampleImage("Top", "layout-ColumnLayout-start-top.png").exampleImage("Center", "layout-ColumnLayout-start-center.png").exampleImage("Bottom", "layout-ColumnLayout-start-bottom.png").exampleImage("SpaceEvenly", "layout-ColumnLayout-start-space-evenly.png").exampleImage("SpaceAround", "layout-ColumnLayout-start-space-around.png").exampleImage("SpaceBetween", "layout-ColumnLayout-start-space-between.png").field(0, "COMPONENT_ID", "unique id for this component").field(0, "ANIMATION_ID", "id used to match components, for animation purposes").field(0, "HORIZONTAL_POSITIONING", "horizontal positioning value").possibleValues("START", 1).possibleValues("CENTER", 2).possibleValues("END", 3).field(0, "VERTICAL_POSITIONING", "vertical positioning value").possibleValues("TOP", 4).possibleValues("CENTER", 2).possibleValues("BOTTOM", 5).possibleValues("SPACE_BETWEEN", 6).possibleValues("SPACE_EVENLY", 7).possibleValues("SPACE_AROUND", 8).field(1, "SPACED_BY", "Horizontal spacing between components");
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
