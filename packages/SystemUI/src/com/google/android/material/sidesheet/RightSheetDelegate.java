package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RightSheetDelegate extends SheetDelegate {
    public final SideSheetBehavior sheetBehavior;

    public RightSheetDelegate(SideSheetBehavior<? extends View> sideSheetBehavior) {
        this.sheetBehavior = sideSheetBehavior;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int calculateInnerMargin(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.rightMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final float calculateSlideOffset(int i) {
        float f = this.sheetBehavior.parentWidth;
        return (f - i) / (f - getExpandedOffset());
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getCoplanarSiblingAdjacentMargin(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.rightMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getExpandedOffset() {
        SideSheetBehavior sideSheetBehavior = this.sheetBehavior;
        return Math.max(0, (sideSheetBehavior.parentWidth - sideSheetBehavior.childWidth) - sideSheetBehavior.innerMargin);
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getHiddenOffset() {
        return this.sheetBehavior.parentWidth;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getMaxViewPositionHorizontal() {
        return this.sheetBehavior.parentWidth;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getMinViewPositionHorizontal() {
        return getExpandedOffset();
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getOuterEdge(View view) {
        return view.getLeft() - this.sheetBehavior.innerMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getParentInnerEdge(CoordinatorLayout coordinatorLayout) {
        return coordinatorLayout.getRight();
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getSheetEdge() {
        return 0;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isExpandingOutwards(float f) {
        return f < 0.0f;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isReleasedCloseToInnerEdge(View view) {
        return view.getLeft() > (getExpandedOffset() + this.sheetBehavior.parentWidth) / 2;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isSwipeSignificant(float f, float f2) {
        if (Math.abs(f) <= Math.abs(f2)) {
            return false;
        }
        float abs = Math.abs(f);
        this.sheetBehavior.getClass();
        return abs > ((float) 500);
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean shouldHide(float f, View view) {
        float right = view.getRight();
        SideSheetBehavior sideSheetBehavior = this.sheetBehavior;
        float abs = Math.abs((f * sideSheetBehavior.hideFriction) + right);
        sideSheetBehavior.getClass();
        return abs > 0.5f;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void updateCoplanarSiblingAdjacentMargin(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        marginLayoutParams.rightMargin = i;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void updateCoplanarSiblingLayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2) {
        int i3 = this.sheetBehavior.parentWidth;
        if (i <= i3) {
            marginLayoutParams.rightMargin = i3 - i;
        }
    }
}
