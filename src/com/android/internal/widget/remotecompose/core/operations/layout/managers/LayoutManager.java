package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HeightInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.WidthInModifierOperation;
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
        float fComputeModifierDefinedHeight = computeModifierDefinedHeight(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            fComputeModifierDefinedHeight = Math.max(it.next().minIntrinsicHeight(remoteContext), fComputeModifierDefinedHeight);
        }
        return fComputeModifierDefinedHeight;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicWidth(RemoteContext remoteContext) {
        float fComputeModifierDefinedWidth = computeModifierDefinedWidth(remoteContext);
        Iterator<Component> it = this.mChildrenComponents.iterator();
        while (it.hasNext()) {
            fComputeModifierDefinedWidth = Math.max(it.next().minIntrinsicWidth(remoteContext), fComputeModifierDefinedWidth);
        }
        return fComputeModifierDefinedWidth;
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

    private void measure_v0_4_0(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        MeasurePass measurePass2;
        float f5;
        float f6;
        float height;
        boolean z;
        float fMin;
        float fMin2;
        float f7;
        float fMinIntrinsicWidth = f2;
        float fMin3 = Math.min(fMinIntrinsicWidth, computeModifierDefinedWidth(paintContext.getContext()));
        float fMin4 = Math.min(f4, computeModifierDefinedHeight(paintContext.getContext()));
        if (this.mWidthModifier.isIntrinsicMin()) {
            fMinIntrinsicWidth = minIntrinsicWidth(paintContext.getContext()) + this.mPaddingLeft + this.mPaddingRight;
        }
        float f8 = fMinIntrinsicWidth;
        float fMinIntrinsicHeight = this.mHeightModifier.isIntrinsicMin() ? minIntrinsicHeight(paintContext.getContext()) + this.mPaddingTop + this.mPaddingBottom : f4;
        float f9 = (f8 - this.mPaddingLeft) - this.mPaddingRight;
        float f10 = (fMinIntrinsicHeight - this.mPaddingTop) - this.mPaddingBottom;
        boolean zIsWrap = this.mWidthModifier.isWrap();
        boolean zIsWrap2 = this.mHeightModifier.isWrap();
        if (zIsWrap || zIsWrap2) {
            this.mCachedWrapSize.setWidth(0.0f);
            this.mCachedWrapSize.setHeight(0.0f);
            measurePass2 = measurePass;
            f5 = f10;
            computeWrapSize(paintContext, f9, f5, this.mWidthModifier.isWrap(), this.mHeightModifier.isWrap(), measurePass2, this.mCachedWrapSize);
            f6 = f9;
            int visibility = measurePass2.get(this).getVisibility();
            if (Component.Visibility.hasOverride(visibility) && this.mScheduledVisibility != visibility) {
                this.mScheduledVisibility = visibility;
            }
            fMin3 = this.mCachedWrapSize.getWidth();
            if (zIsWrap) {
                fMin3 += this.mPaddingLeft + this.mPaddingRight;
            }
            height = this.mCachedWrapSize.getHeight();
            if (zIsWrap2) {
                height += this.mPaddingTop + this.mPaddingBottom;
            }
            z = true;
        } else {
            z = false;
            measurePass2 = measurePass;
            f6 = f9;
            height = fMin4;
            f5 = f10;
        }
        if (isInHorizontalFill()) {
            fMin = f8;
        } else if (this.mWidthModifier.hasWeight()) {
            fMin = Math.max(fMin3, computeModifierDefinedWidth(paintContext.getContext()));
        } else {
            fMin = Math.min(Math.max(fMin3, f), f8);
        }
        if (isInVerticalFill()) {
            fMin2 = fMinIntrinsicHeight;
        } else if (this.mHeightModifier.hasWeight()) {
            fMin2 = Math.max(height, computeModifierDefinedHeight(paintContext.getContext()));
        } else {
            fMin2 = Math.min(Math.max(height, f3), fMinIntrinsicHeight);
        }
        float f11 = f == f8 ? f8 : fMin;
        float f12 = f3 == fMinIntrinsicHeight ? fMinIntrinsicHeight : fMin2;
        if (z) {
            f7 = f11;
        } else if (hasHorizontalIntrinsicDimension()) {
            this.mCachedWrapSize.setWidth(0.0f);
            this.mCachedWrapSize.setHeight(0.0f);
            float f13 = fMinIntrinsicHeight;
            float f14 = f5;
            computeWrapSize(paintContext, Float.MAX_VALUE, f13, false, false, measurePass2, this.mCachedWrapSize);
            float width = this.mCachedWrapSize.getWidth();
            if (hasHorizontalScroll()) {
                computeSize(paintContext, 0.0f, width, 0.0f, f12, measurePass);
                this.mComponentModifiers.setHorizontalScrollDimension(f11, width);
            } else {
                computeSize(paintContext, 0.0f, Math.min(f11, f6), 0.0f, Math.min(f12, f14), measurePass);
            }
            measurePass2 = measurePass;
            f7 = f11;
        } else {
            float f15 = f5;
            if (hasVerticalIntrinsicDimension()) {
                this.mCachedWrapSize.setWidth(0.0f);
                this.mCachedWrapSize.setHeight(0.0f);
                computeWrapSize(paintContext, f8, Float.MAX_VALUE, false, false, measurePass, this.mCachedWrapSize);
                float height2 = this.mCachedWrapSize.getHeight();
                if (hasVerticalScroll()) {
                    float f16 = f11;
                    computeSize(paintContext, 0.0f, f16, 0.0f, height2, measurePass);
                    f7 = f16;
                    this.mComponentModifiers.setVerticalScrollDimension(f12, height2);
                } else {
                    f7 = f11;
                    computeSize(paintContext, 0.0f, Math.min(f7, f6), 0.0f, Math.min(f12, f15), measurePass);
                }
                measurePass2 = measurePass;
            } else {
                f7 = f11;
                measurePass2 = measurePass;
                computeSize(paintContext, 0.0f, (f7 - this.mPaddingLeft) - this.mPaddingRight, 0.0f, (f12 - this.mPaddingTop) - this.mPaddingBottom, measurePass2);
            }
        }
        if (this.mContent != null) {
            ComponentMeasure componentMeasure = measurePass2.get(this.mContent);
            componentMeasure.setX(0.0f);
            componentMeasure.setY(0.0f);
            componentMeasure.setW(f7);
            componentMeasure.setH(f12);
        }
        ComponentMeasure componentMeasure2 = measurePass2.get(this);
        componentMeasure2.setW(f7);
        componentMeasure2.setH(f12);
        componentMeasure2.setVisibility(this.mScheduledVisibility);
        internalLayoutMeasure(paintContext, measurePass2);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x011f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x023a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void measure_v0_4_1(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        float f5;
        float f6;
        float fMin;
        boolean z;
        float fMin2;
        boolean z2;
        MeasurePass measurePass2;
        float f7;
        int visibility;
        float f8;
        float fMin3 = f2;
        float fMin4 = Math.min(fMin3, computeModifierDefinedWidth(paintContext.getContext()));
        float fMin5 = f4;
        float fMin6 = Math.min(fMin5, computeModifierDefinedHeight(paintContext.getContext()));
        if (this.mWidthModifier.isIntrinsicMin()) {
            fMin3 = minIntrinsicWidth(paintContext.getContext()) + this.mPaddingLeft + this.mPaddingRight;
        }
        if (this.mHeightModifier.isIntrinsicMin()) {
            fMin5 = minIntrinsicHeight(paintContext.getContext()) + this.mPaddingTop + this.mPaddingBottom;
        }
        WidthInModifierOperation widthIn = this.mWidthModifier.getWidthIn();
        if (widthIn != null) {
            float fMax = Math.max(f, widthIn.getMin());
            fMin3 = Math.min(fMin3, widthIn.getMax());
            f5 = fMax;
        } else {
            f5 = f;
        }
        HeightInModifierOperation heightIn = this.mHeightModifier.getHeightIn();
        if (heightIn != null) {
            float fMax2 = Math.max(f3, heightIn.getMin());
            fMin5 = Math.min(fMin5, heightIn.getMax());
            f6 = fMax2;
        } else {
            f6 = f3;
        }
        float f9 = (fMin3 - this.mPaddingLeft) - this.mPaddingRight;
        float f10 = (fMin5 - this.mPaddingTop) - this.mPaddingBottom;
        boolean z3 = true;
        if (isInHorizontalFill()) {
            fMin = fMin3;
        } else if (this.mWidthModifier.hasWeight()) {
            fMin = Math.max(fMin4, computeModifierDefinedWidth(paintContext.getContext()));
        } else {
            fMin = Math.min(Math.max(fMin4, f5), fMin3);
            z = this.mWidthModifier.isWrap() || this.mWidthModifier.isIntrinsicMin();
            if (!isInVerticalFill()) {
                fMin2 = fMin5;
            } else if (this.mHeightModifier.hasWeight()) {
                fMin2 = Math.max(fMin6, computeModifierDefinedHeight(paintContext.getContext()));
            } else {
                fMin2 = Math.min(Math.max(fMin6, f6), fMin5);
                if (!this.mHeightModifier.isWrap() && !this.mHeightModifier.isIntrinsicMin()) {
                    z3 = false;
                }
                z2 = z3;
                float fMax3 = f5 == fMin3 ? fMin3 : fMin;
                float fMax4 = f6 == fMin5 ? fMin5 : fMin2;
                if (z || z2) {
                    this.mCachedWrapSize.setWidth(0.0f);
                    this.mCachedWrapSize.setHeight(0.0f);
                    measurePass2 = measurePass;
                    f7 = 0.0f;
                    computeWrapSize(paintContext, f9, f10, this.mWidthModifier.isWrap(), this.mHeightModifier.isWrap(), measurePass2, this.mCachedWrapSize);
                    visibility = measurePass2.get(this).getVisibility();
                    if (Component.Visibility.hasOverride(visibility) && this.mScheduledVisibility != visibility) {
                        this.mScheduledVisibility = visibility;
                    }
                    if (z) {
                        fMax3 = Math.max(this.mCachedWrapSize.getWidth() + this.mPaddingLeft + this.mPaddingRight, f5);
                    }
                    if (z2) {
                        fMax4 = Math.max(this.mCachedWrapSize.getHeight() + this.mPaddingTop + this.mPaddingBottom, f6);
                    }
                } else {
                    if (hasHorizontalIntrinsicDimension()) {
                        this.mCachedWrapSize.setWidth(0.0f);
                        this.mCachedWrapSize.setHeight(0.0f);
                        f8 = 0.0f;
                        computeWrapSize(paintContext, Float.MAX_VALUE, fMin5, false, false, measurePass, this.mCachedWrapSize);
                        float width = this.mCachedWrapSize.getWidth();
                        if (hasHorizontalScroll()) {
                            computeSize(paintContext, 0.0f, width, 0.0f, fMax4, measurePass);
                            this.mComponentModifiers.setHorizontalScrollDimension(fMax3, width);
                        } else {
                            computeSize(paintContext, 0.0f, Math.min(fMax3, f9), 0.0f, Math.min(fMax4, f10), measurePass);
                        }
                    } else {
                        f8 = 0.0f;
                        if (hasVerticalIntrinsicDimension()) {
                            this.mCachedWrapSize.setWidth(0.0f);
                            this.mCachedWrapSize.setHeight(0.0f);
                            computeWrapSize(paintContext, fMin3, Float.MAX_VALUE, false, false, measurePass, this.mCachedWrapSize);
                            float height = this.mCachedWrapSize.getHeight();
                            if (hasVerticalScroll()) {
                                computeSize(paintContext, 0.0f, fMax3, 0.0f, height, measurePass);
                                this.mComponentModifiers.setVerticalScrollDimension(fMax4, height);
                            } else {
                                computeSize(paintContext, 0.0f, Math.min(fMax3, f9), 0.0f, Math.min(fMax4, f10), measurePass);
                            }
                        } else {
                            computeSize(paintContext, 0.0f, (fMax3 - this.mPaddingLeft) - this.mPaddingRight, 0.0f, (fMax4 - this.mPaddingTop) - this.mPaddingBottom, measurePass);
                        }
                    }
                    measurePass2 = measurePass;
                    f7 = f8;
                }
                if (this.mContent != null) {
                    ComponentMeasure componentMeasure = measurePass2.get(this.mContent);
                    componentMeasure.setX(f7);
                    componentMeasure.setY(f7);
                    componentMeasure.setW(fMax3);
                    componentMeasure.setH(fMax4);
                }
                float fMax5 = Math.max(fMax3, f5);
                float fMax6 = Math.max(fMax4, f6);
                ComponentMeasure componentMeasure2 = measurePass2.get(this);
                componentMeasure2.setW(fMax5);
                componentMeasure2.setH(fMax6);
                componentMeasure2.setVisibility(this.mScheduledVisibility);
                internalLayoutMeasure(paintContext, measurePass2);
            }
            z2 = false;
            if (f5 == fMin3) {
            }
            if (f6 == fMin5) {
            }
            if (z) {
                this.mCachedWrapSize.setWidth(0.0f);
                this.mCachedWrapSize.setHeight(0.0f);
                measurePass2 = measurePass;
                f7 = 0.0f;
                computeWrapSize(paintContext, f9, f10, this.mWidthModifier.isWrap(), this.mHeightModifier.isWrap(), measurePass2, this.mCachedWrapSize);
                visibility = measurePass2.get(this).getVisibility();
                if (Component.Visibility.hasOverride(visibility)) {
                    this.mScheduledVisibility = visibility;
                }
                if (z) {
                }
                if (z2) {
                }
            }
            if (this.mContent != null) {
            }
            float fMax52 = Math.max(fMax3, f5);
            float fMax62 = Math.max(fMax4, f6);
            ComponentMeasure componentMeasure22 = measurePass2.get(this);
            componentMeasure22.setW(fMax52);
            componentMeasure22.setH(fMax62);
            componentMeasure22.setVisibility(this.mScheduledVisibility);
            internalLayoutMeasure(paintContext, measurePass2);
        }
        z = false;
        if (!isInVerticalFill()) {
        }
        z2 = false;
        if (f5 == fMin3) {
        }
        if (f6 == fMin5) {
        }
        if (z) {
        }
        if (this.mContent != null) {
        }
        float fMax522 = Math.max(fMax3, f5);
        float fMax622 = Math.max(fMax4, f6);
        ComponentMeasure componentMeasure222 = measurePass2.get(this);
        componentMeasure222.setW(fMax522);
        componentMeasure222.setH(fMax622);
        componentMeasure222.setVisibility(this.mScheduledVisibility);
        internalLayoutMeasure(paintContext, measurePass2);
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
