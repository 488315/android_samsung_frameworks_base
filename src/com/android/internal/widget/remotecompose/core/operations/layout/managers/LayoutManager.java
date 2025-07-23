package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import java.util.Iterator;

/* loaded from: classes6.dex */
public abstract class LayoutManager extends LayoutComponent implements Measurable {
    Size mCachedWrapSize;

    public boolean applyVisibility(float f, float f2, MeasurePass measurePass) {
        return false;
    }

    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
    }

    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
    }

    public void internalLayoutMeasure(PaintContext paintContext, MeasurePass measurePass) {
    }

    public LayoutManager(Component component, int i, int i2, float f, float f2, float f3, float f4) {
        super(component, i, i2, f, f2, f3, f4);
        this.mCachedWrapSize = new Size(0.0f, 0.0f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicHeight(RemoteContext remoteContext) {
        float computeModifierDefinedHeight = computeModifierDefinedHeight(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            computeModifierDefinedHeight = Math.max(it.next().minIntrinsicHeight(remoteContext), computeModifierDefinedHeight);
        }
        return computeModifierDefinedHeight;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicWidth(RemoteContext remoteContext) {
        float computeModifierDefinedWidth = computeModifierDefinedWidth(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            computeModifierDefinedWidth = Math.max(it.next().minIntrinsicWidth(remoteContext), computeModifierDefinedWidth);
        }
        return computeModifierDefinedWidth;
    }

    protected boolean childrenHaveHorizontalWeights() {
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            Component next = it.next();
            if (next instanceof LayoutManager) {
                LayoutManager layoutManager = (LayoutManager) next;
                if (layoutManager.getWidthModifier() != null && layoutManager.getWidthModifier().hasWeight()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean childrenHaveVerticalWeights() {
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            Component next = it.next();
            if (next instanceof LayoutManager) {
                LayoutManager layoutManager = (LayoutManager) next;
                if (layoutManager.getHeightModifier() != null && layoutManager.getHeightModifier().hasWeight()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInHorizontalFill() {
        return this.mWidthModifier.isFill();
    }

    public boolean isInVerticalFill() {
        return this.mHeightModifier.isFill();
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x01e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void measure_v0_4_0(com.android.internal.widget.remotecompose.core.PaintContext r17, float r18, float r19, float r20, float r21, com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass r22) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager.measure_v0_4_0(com.android.internal.widget.remotecompose.core.PaintContext, float, float, float, float, com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x011f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void measure_v0_4_1(com.android.internal.widget.remotecompose.core.PaintContext r17, float r18, float r19, float r20, float r21, com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass r22) {
        /*
            Method dump skipped, instructions count: 617
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager.measure_v0_4_1(com.android.internal.widget.remotecompose.core.PaintContext, float, float, float, float, com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass):void");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public void measure(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        if (paintContext.supportsVersion(0, 4, 1)) {
            measure_v0_4_1(paintContext, f, f2, f3, f4, measurePass);
        } else {
            measure_v0_4_0(paintContext, f, f2, f3, f4, measurePass);
        }
    }

    private boolean hasHorizontalScroll() {
        return this.mComponentModifiers.hasHorizontalScroll();
    }

    protected boolean hasHorizontalIntrinsicDimension() {
        return hasHorizontalScroll();
    }

    protected boolean hasVerticalIntrinsicDimension() {
        return hasVerticalScroll();
    }

    private boolean hasVerticalScroll() {
        return this.mComponentModifiers.hasVerticalScroll();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public void layout(RemoteContext remoteContext, MeasurePass measurePass) {
        super.layout(remoteContext, measurePass);
        ComponentMeasure componentMeasure = measurePass.get(this);
        this.mComponentModifiers.layout(remoteContext, this, componentMeasure.getW(), componentMeasure.getH());
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            it.next().layout(remoteContext, measurePass);
        }
        this.mNeedsMeasure = false;
    }

    public void selfLayout(RemoteContext remoteContext, MeasurePass measurePass) {
        super.layout(remoteContext, measurePass);
        ComponentMeasure componentMeasure = measurePass.get(this);
        this.mComponentModifiers.layout(remoteContext, this, componentMeasure.getW(), componentMeasure.getH());
        this.mNeedsMeasure = false;
    }
}
