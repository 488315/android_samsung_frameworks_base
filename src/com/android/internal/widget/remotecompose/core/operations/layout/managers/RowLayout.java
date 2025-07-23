package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ScrollModifierOperation;
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
                String lambda$computeWrapSize$0;
                lambda$computeWrapSize$0 = RowLayout.this.lambda$computeWrapSize$0();
                return lambda$computeWrapSize$0;
            }
        });
        Iterator<Component> it = this.mChildrenComponents.iterator();
        int i = 0;
        float f3 = f;
        while (it.hasNext()) {
            Component next = it.next();
            PaintContext paintContext2 = paintContext;
            float f4 = f2;
            MeasurePass measurePass2 = measurePass;
            next.measure(paintContext2, 0.0f, f3, 0.0f, f4, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            if (!componentMeasure.isGone()) {
                size.setWidth(size.getWidth() + componentMeasure.getW());
                size.setHeight(Math.max(size.getHeight(), componentMeasure.getH()));
                i++;
                f3 -= componentMeasure.getW();
            }
            paintContext = paintContext2;
            f2 = f4;
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
                String lambda$computeSize$1;
                lambda$computeSize$1 = RowLayout.this.lambda$computeSize$1();
                return lambda$computeSize$1;
            }
        });
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float f5 = f2;
        while (it.hasNext()) {
            Component next = it.next();
            PaintContext paintContext2 = paintContext;
            float f6 = f;
            float f7 = f3;
            float f8 = f4;
            MeasurePass measurePass2 = measurePass;
            next.measure(paintContext2, f6, f5, f7, f8, measurePass2);
            ComponentMeasure componentMeasure = measurePass2.get(next);
            if (!componentMeasure.isGone()) {
                f5 -= componentMeasure.getW();
            }
            paintContext = paintContext2;
            f = f6;
            f3 = f7;
            f4 = f8;
            measurePass = measurePass2;
        }
        DebugLog.e();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicWidth(RemoteContext remoteContext) {
        float computeModifierDefinedWidth = computeModifierDefinedWidth(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float f = 0.0f;
        while (it.hasNext()) {
            f += it.next().minIntrinsicWidth(remoteContext);
        }
        return Math.max(computeModifierDefinedWidth, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicHeight(RemoteContext remoteContext) {
        float computeModifierDefinedHeight = computeModifierDefinedHeight(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        float f = 0.0f;
        while (it.hasNext()) {
            f = Math.max(f, it.next().minIntrinsicHeight(remoteContext));
        }
        return Math.max(computeModifierDefinedHeight, f);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x020d  */
    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void internalLayoutMeasure(com.android.internal.widget.remotecompose.core.PaintContext r18, com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass r19) {
        /*
            Method dump skipped, instructions count: 597
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout.internalLayoutMeasure(com.android.internal.widget.remotecompose.core.PaintContext, com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass):void");
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
