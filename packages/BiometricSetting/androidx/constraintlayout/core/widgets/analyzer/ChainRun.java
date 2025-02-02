package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ChainRun extends WidgetRun {
    private int mChainStyle;
    ArrayList<WidgetRun> mWidgets;

    public ChainRun(int i, ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.mWidgets = new ArrayList<>();
        this.orientation = i;
        ConstraintWidget constraintWidget2 = this.mWidget;
        ConstraintWidget previousChainMember = constraintWidget2.getPreviousChainMember(i);
        while (previousChainMember != null) {
            constraintWidget2 = previousChainMember;
            previousChainMember = previousChainMember.getPreviousChainMember(this.orientation);
        }
        this.mWidget = constraintWidget2;
        ArrayList<WidgetRun> arrayList = this.mWidgets;
        int i2 = this.orientation;
        arrayList.add(i2 == 0 ? constraintWidget2.mHorizontalRun : i2 == 1 ? constraintWidget2.mVerticalRun : null);
        ConstraintWidget nextChainMember = constraintWidget2.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            ArrayList<WidgetRun> arrayList2 = this.mWidgets;
            int i3 = this.orientation;
            arrayList2.add(i3 == 0 ? nextChainMember.mHorizontalRun : i3 == 1 ? nextChainMember.mVerticalRun : null);
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            WidgetRun next = it.next();
            int i4 = this.orientation;
            if (i4 == 0) {
                next.mWidget.horizontalChainRun = this;
            } else if (i4 == 1) {
                next.mWidget.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.mWidget.mParent).isRtl()) && this.mWidgets.size() > 1) {
            ArrayList<WidgetRun> arrayList3 = this.mWidgets;
            this.mWidget = arrayList3.get(arrayList3.size() - 1).mWidget;
        }
        this.mChainStyle = this.orientation == 0 ? this.mWidget.getHorizontalChainStyle() : this.mWidget.getVerticalChainStyle();
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.mWidgets.size(); i++) {
            WidgetRun widgetRun = this.mWidgets.get(i);
            if (widgetRun.mWidget.getVisibility() != 8) {
                return widgetRun.mWidget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int size = this.mWidgets.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = this.mWidgets.get(size);
            if (widgetRun.mWidget.getVisibility() != 8) {
                return widgetRun.mWidget;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final void apply() {
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int size = this.mWidgets.size();
        if (size < 1) {
            return;
        }
        ConstraintWidget constraintWidget = this.mWidgets.get(0).mWidget;
        ConstraintWidget constraintWidget2 = this.mWidgets.get(size - 1).mWidget;
        if (this.orientation == 0) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
            DependencyNode target = WidgetRun.getTarget(constraintAnchor, 0);
            int margin = constraintAnchor.getMargin();
            ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
            if (firstVisibleWidget != null) {
                margin = firstVisibleWidget.mLeft.getMargin();
            }
            if (target != null) {
                WidgetRun.addTarget(this.start, target, margin);
            }
            DependencyNode target2 = WidgetRun.getTarget(constraintAnchor2, 0);
            int margin2 = constraintAnchor2.getMargin();
            ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
            if (lastVisibleWidget != null) {
                margin2 = lastVisibleWidget.mRight.getMargin();
            }
            if (target2 != null) {
                WidgetRun.addTarget(this.end, target2, -margin2);
            }
        } else {
            ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
            ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
            DependencyNode target3 = WidgetRun.getTarget(constraintAnchor3, 1);
            int margin3 = constraintAnchor3.getMargin();
            ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
            if (firstVisibleWidget2 != null) {
                margin3 = firstVisibleWidget2.mTop.getMargin();
            }
            if (target3 != null) {
                WidgetRun.addTarget(this.start, target3, margin3);
            }
            DependencyNode target4 = WidgetRun.getTarget(constraintAnchor4, 1);
            int margin4 = constraintAnchor4.getMargin();
            ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
            if (lastVisibleWidget2 != null) {
                margin4 = lastVisibleWidget2.mBottom.getMargin();
            }
            if (target4 != null) {
                WidgetRun.addTarget(this.end, target4, -margin4);
            }
        }
        this.start.updateDelegate = this;
        this.end.updateDelegate = this;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        for (int i = 0; i < this.mWidgets.size(); i++) {
            this.mWidgets.get(i).applyToWidget();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final void clear() {
        this.mRunGroup = null;
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final long getWrapDimension() {
        int size = this.mWidgets.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            j = r4.end.mMargin + this.mWidgets.get(i).getWrapDimension() + j + r4.start.mMargin;
        }
        return j;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final boolean supportsWrapComputation() {
        int size = this.mWidgets.size();
        for (int i = 0; i < size; i++) {
            if (!this.mWidgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            WidgetRun next = it.next();
            sb.append("<");
            sb.append(next);
            sb.append("> ");
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:287:0x03fd, code lost:
    
        r9 = r9 - r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00e8  */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(Dependency dependency) {
        int i;
        int i2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        int i3;
        int i4;
        int i5;
        float f;
        boolean z;
        int i6;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        int i7;
        int i8;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3;
        boolean z2;
        int i9;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4;
        float f2;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        if (this.start.resolved && this.end.resolved) {
            ConstraintWidget constraintWidget = this.mWidget.mParent;
            boolean isRtl = constraintWidget instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer) constraintWidget).isRtl() : false;
            int i17 = this.end.value - this.start.value;
            int size = this.mWidgets.size();
            int i18 = 0;
            while (true) {
                i = -1;
                i2 = 8;
                if (i18 >= size) {
                    i18 = -1;
                    break;
                } else if (this.mWidgets.get(i18).mWidget.getVisibility() != 8) {
                    break;
                } else {
                    i18++;
                }
            }
            int i19 = size - 1;
            int i20 = i19;
            while (true) {
                if (i20 < 0) {
                    break;
                }
                if (this.mWidgets.get(i20).mWidget.getVisibility() != 8) {
                    i = i20;
                    break;
                }
                i20--;
            }
            int i21 = 0;
            while (true) {
                dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (i21 >= 2) {
                    i3 = 0;
                    i4 = 0;
                    i5 = 0;
                    f = 0.0f;
                    break;
                }
                int i22 = 0;
                i5 = 0;
                i13 = 0;
                i14 = 0;
                f = 0.0f;
                while (i22 < size) {
                    WidgetRun widgetRun = this.mWidgets.get(i22);
                    if (widgetRun.mWidget.getVisibility() != i2) {
                        i14++;
                        if (i22 > 0 && i22 >= i18) {
                            i5 += widgetRun.start.mMargin;
                        }
                        DimensionDependency dimensionDependency = widgetRun.mDimension;
                        int i23 = dimensionDependency.value;
                        boolean z3 = widgetRun.mDimensionBehavior != dimensionBehaviour;
                        if (z3) {
                            int i24 = this.orientation;
                            if (i24 == 0 && !widgetRun.mWidget.mHorizontalRun.mDimension.resolved) {
                                return;
                            }
                            if (i24 == 1 && !widgetRun.mWidget.mVerticalRun.mDimension.resolved) {
                                return;
                            } else {
                                i15 = i23;
                            }
                        } else {
                            i15 = i23;
                            if (widgetRun.matchConstraintsType == 1 && i21 == 0) {
                                i16 = dimensionDependency.wrapValue;
                                i13++;
                            } else if (dimensionDependency.resolved) {
                                i16 = i15;
                            }
                            z3 = true;
                            if (z3) {
                                i13++;
                                float f3 = widgetRun.mWidget.mWeight[this.orientation];
                                if (f3 >= 0.0f) {
                                    f += f3;
                                }
                            } else {
                                i5 += i16;
                            }
                            if (i22 < i19 && i22 < i) {
                                i5 += -widgetRun.end.mMargin;
                            }
                        }
                        i16 = i15;
                        if (z3) {
                        }
                        if (i22 < i19) {
                            i5 += -widgetRun.end.mMargin;
                        }
                    }
                    i22++;
                    i2 = 8;
                }
                if (i5 < i17 || i13 == 0) {
                    break;
                }
                i21++;
                i2 = 8;
            }
            i3 = i13;
            i4 = i14;
            int i25 = this.start.value;
            if (isRtl) {
                i25 = this.end.value;
            }
            if (i5 > i17) {
                i25 = isRtl ? i25 + ((int) (((i5 - i17) / 2.0f) + 0.5f)) : i25 - ((int) (((i5 - i17) / 2.0f) + 0.5f));
            }
            if (i3 > 0) {
                float f4 = i17 - i5;
                int i26 = (int) ((f4 / i3) + 0.5f);
                int i27 = 0;
                int i28 = 0;
                while (i27 < size) {
                    WidgetRun widgetRun2 = this.mWidgets.get(i27);
                    int i29 = i26;
                    int i30 = i5;
                    if (widgetRun2.mWidget.getVisibility() != 8 && widgetRun2.mDimensionBehavior == dimensionBehaviour) {
                        DimensionDependency dimensionDependency2 = widgetRun2.mDimension;
                        if (!dimensionDependency2.resolved) {
                            if (f > 0.0f) {
                                i9 = i25;
                                i10 = (int) (((widgetRun2.mWidget.mWeight[this.orientation] * f4) / f) + 0.5f);
                            } else {
                                i9 = i25;
                                i10 = i29;
                            }
                            if (this.orientation == 0) {
                                ConstraintWidget constraintWidget2 = widgetRun2.mWidget;
                                f2 = f4;
                                i12 = constraintWidget2.mMatchConstraintMaxWidth;
                                i11 = constraintWidget2.mMatchConstraintMinWidth;
                                dimensionBehaviour4 = dimensionBehaviour;
                            } else {
                                f2 = f4;
                                ConstraintWidget constraintWidget3 = widgetRun2.mWidget;
                                int i31 = constraintWidget3.mMatchConstraintMaxHeight;
                                dimensionBehaviour4 = dimensionBehaviour;
                                i11 = constraintWidget3.mMatchConstraintMinHeight;
                                i12 = i31;
                            }
                            z2 = isRtl;
                            int max = Math.max(i11, widgetRun2.matchConstraintsType == 1 ? Math.min(i10, dimensionDependency2.wrapValue) : i10);
                            if (i12 > 0) {
                                max = Math.min(i12, max);
                            }
                            if (max != i10) {
                                i28++;
                                i10 = max;
                            }
                            widgetRun2.mDimension.resolve(i10);
                            i27++;
                            i26 = i29;
                            i5 = i30;
                            i25 = i9;
                            f4 = f2;
                            dimensionBehaviour = dimensionBehaviour4;
                            isRtl = z2;
                        }
                    }
                    z2 = isRtl;
                    i9 = i25;
                    dimensionBehaviour4 = dimensionBehaviour;
                    f2 = f4;
                    i27++;
                    i26 = i29;
                    i5 = i30;
                    i25 = i9;
                    f4 = f2;
                    dimensionBehaviour = dimensionBehaviour4;
                    isRtl = z2;
                }
                z = isRtl;
                i6 = i25;
                dimensionBehaviour2 = dimensionBehaviour;
                int i32 = i5;
                if (i28 > 0) {
                    i3 -= i28;
                    int i33 = 0;
                    for (int i34 = 0; i34 < size; i34++) {
                        WidgetRun widgetRun3 = this.mWidgets.get(i34);
                        if (widgetRun3.mWidget.getVisibility() != 8) {
                            if (i34 > 0 && i34 >= i18) {
                                i33 += widgetRun3.start.mMargin;
                            }
                            i33 += widgetRun3.mDimension.value;
                            if (i34 < i19 && i34 < i) {
                                i33 += -widgetRun3.end.mMargin;
                            }
                        }
                    }
                    i5 = i33;
                } else {
                    i5 = i32;
                }
                i8 = 2;
                if (this.mChainStyle == 2 && i28 == 0) {
                    i7 = 0;
                    this.mChainStyle = 0;
                } else {
                    i7 = 0;
                }
            } else {
                z = isRtl;
                i6 = i25;
                dimensionBehaviour2 = dimensionBehaviour;
                i7 = 0;
                i8 = 2;
            }
            if (i5 > i17) {
                this.mChainStyle = i8;
            }
            if (i4 > 0 && i3 == 0 && i18 == i) {
                this.mChainStyle = i8;
            }
            int i35 = this.mChainStyle;
            if (i35 == 1) {
                int i36 = i4 > 1 ? (i17 - i5) / (i4 - 1) : i4 == 1 ? (i17 - i5) / 2 : i7;
                if (i3 > 0) {
                    i36 = i7;
                }
                int i37 = i7;
                int i38 = i6;
                while (i37 < size) {
                    WidgetRun widgetRun4 = this.mWidgets.get(z ? size - (i37 + 1) : i37);
                    if (widgetRun4.mWidget.getVisibility() == 8) {
                        widgetRun4.start.resolve(i38);
                        widgetRun4.end.resolve(i38);
                        dimensionBehaviour3 = dimensionBehaviour2;
                    } else {
                        if (i37 > 0) {
                            i38 = z ? i38 - i36 : i38 + i36;
                        }
                        if (i37 > 0 && i37 >= i18) {
                            i38 = z ? i38 - widgetRun4.start.mMargin : i38 + widgetRun4.start.mMargin;
                        }
                        if (z) {
                            widgetRun4.end.resolve(i38);
                        } else {
                            widgetRun4.start.resolve(i38);
                        }
                        DimensionDependency dimensionDependency3 = widgetRun4.mDimension;
                        int i39 = dimensionDependency3.value;
                        dimensionBehaviour3 = dimensionBehaviour2;
                        if (widgetRun4.mDimensionBehavior == dimensionBehaviour3 && widgetRun4.matchConstraintsType == 1) {
                            i39 = dimensionDependency3.wrapValue;
                        }
                        i38 = z ? i38 - i39 : i38 + i39;
                        if (z) {
                            widgetRun4.start.resolve(i38);
                        } else {
                            widgetRun4.end.resolve(i38);
                        }
                        widgetRun4.mResolved = true;
                        if (i37 < i19 && i37 < i) {
                            i38 = z ? i38 - (-widgetRun4.end.mMargin) : i38 + (-widgetRun4.end.mMargin);
                        }
                    }
                    i37++;
                    dimensionBehaviour2 = dimensionBehaviour3;
                }
                return;
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = dimensionBehaviour2;
            if (i35 == 0) {
                int i40 = (i17 - i5) / (i4 + 1);
                if (i3 > 0) {
                    i40 = i7;
                }
                int i41 = i6;
                for (int i42 = i7; i42 < size; i42++) {
                    WidgetRun widgetRun5 = this.mWidgets.get(z ? size - (i42 + 1) : i42);
                    if (widgetRun5.mWidget.getVisibility() == 8) {
                        widgetRun5.start.resolve(i41);
                        widgetRun5.end.resolve(i41);
                    } else {
                        int i43 = z ? i41 - i40 : i41 + i40;
                        if (i42 > 0 && i42 >= i18) {
                            i43 = z ? i43 - widgetRun5.start.mMargin : i43 + widgetRun5.start.mMargin;
                        }
                        if (z) {
                            widgetRun5.end.resolve(i43);
                        } else {
                            widgetRun5.start.resolve(i43);
                        }
                        DimensionDependency dimensionDependency4 = widgetRun5.mDimension;
                        int i44 = dimensionDependency4.value;
                        if (widgetRun5.mDimensionBehavior == dimensionBehaviour5 && widgetRun5.matchConstraintsType == 1) {
                            i44 = Math.min(i44, dimensionDependency4.wrapValue);
                        }
                        i41 = z ? i43 - i44 : i43 + i44;
                        if (z) {
                            widgetRun5.start.resolve(i41);
                        } else {
                            widgetRun5.end.resolve(i41);
                        }
                        if (i42 < i19 && i42 < i) {
                            i41 = z ? i41 - (-widgetRun5.end.mMargin) : i41 + (-widgetRun5.end.mMargin);
                        }
                    }
                }
                return;
            }
            if (i35 == 2) {
                float horizontalBiasPercent = this.orientation == 0 ? this.mWidget.getHorizontalBiasPercent() : this.mWidget.getVerticalBiasPercent();
                if (z) {
                    horizontalBiasPercent = 1.0f - horizontalBiasPercent;
                }
                int i45 = (int) (((i17 - i5) * horizontalBiasPercent) + 0.5f);
                if (i45 < 0 || i3 > 0) {
                    i45 = i7;
                }
                int i46 = z ? i6 - i45 : i6 + i45;
                for (int i47 = i7; i47 < size; i47++) {
                    WidgetRun widgetRun6 = this.mWidgets.get(z ? size - (i47 + 1) : i47);
                    if (widgetRun6.mWidget.getVisibility() == 8) {
                        widgetRun6.start.resolve(i46);
                        widgetRun6.end.resolve(i46);
                    } else {
                        if (i47 > 0 && i47 >= i18) {
                            i46 = z ? i46 - widgetRun6.start.mMargin : i46 + widgetRun6.start.mMargin;
                        }
                        if (z) {
                            widgetRun6.end.resolve(i46);
                        } else {
                            widgetRun6.start.resolve(i46);
                        }
                        DimensionDependency dimensionDependency5 = widgetRun6.mDimension;
                        int i48 = dimensionDependency5.value;
                        if (widgetRun6.mDimensionBehavior == dimensionBehaviour5 && widgetRun6.matchConstraintsType == 1) {
                            i48 = dimensionDependency5.wrapValue;
                        }
                        i46 += i48;
                        if (z) {
                            widgetRun6.start.resolve(i46);
                        } else {
                            widgetRun6.end.resolve(i46);
                        }
                        if (i47 < i19 && i47 < i) {
                            i46 = z ? i46 - (-widgetRun6.end.mMargin) : i46 + (-widgetRun6.end.mMargin);
                        }
                    }
                }
            }
        }
    }
}
