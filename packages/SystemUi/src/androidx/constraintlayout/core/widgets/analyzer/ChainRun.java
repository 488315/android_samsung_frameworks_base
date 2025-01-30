package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ChainRun extends WidgetRun {
    public int chainStyle;
    public final ArrayList widgets;

    public ChainRun(ConstraintWidget constraintWidget, int i) {
        super(constraintWidget);
        ConstraintWidget constraintWidget2;
        this.widgets = new ArrayList();
        this.orientation = i;
        ConstraintWidget constraintWidget3 = this.widget;
        ConstraintWidget previousChainMember = constraintWidget3.getPreviousChainMember(i);
        while (true) {
            ConstraintWidget constraintWidget4 = previousChainMember;
            constraintWidget2 = constraintWidget3;
            constraintWidget3 = constraintWidget4;
            if (constraintWidget3 == null) {
                break;
            } else {
                previousChainMember = constraintWidget3.getPreviousChainMember(this.orientation);
            }
        }
        this.widget = constraintWidget2;
        int i2 = this.orientation;
        WidgetRun widgetRun = i2 == 0 ? constraintWidget2.horizontalRun : i2 == 1 ? constraintWidget2.verticalRun : null;
        ArrayList arrayList = this.widgets;
        arrayList.add(widgetRun);
        ConstraintWidget nextChainMember = constraintWidget2.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            int i3 = this.orientation;
            arrayList.add(i3 == 0 ? nextChainMember.horizontalRun : i3 == 1 ? nextChainMember.verticalRun : null);
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            WidgetRun widgetRun2 = (WidgetRun) it.next();
            int i4 = this.orientation;
            if (i4 == 0) {
                widgetRun2.widget.horizontalChainRun = this;
            } else if (i4 == 1) {
                widgetRun2.widget.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.widget.mParent).mIsRtl) && arrayList.size() > 1) {
            this.widget = ((WidgetRun) arrayList.get(arrayList.size() - 1)).widget;
        }
        this.chainStyle = this.orientation == 0 ? this.widget.mHorizontalChainStyle : this.widget.mVerticalChainStyle;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        ArrayList arrayList = this.widgets;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((WidgetRun) it.next()).apply();
        }
        int size = arrayList.size();
        if (size < 1) {
            return;
        }
        ConstraintWidget constraintWidget = ((WidgetRun) arrayList.get(0)).widget;
        ConstraintWidget constraintWidget2 = ((WidgetRun) arrayList.get(size - 1)).widget;
        int i = this.orientation;
        DependencyNode dependencyNode = this.end;
        DependencyNode dependencyNode2 = this.start;
        if (i == 0) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
            DependencyNode target = WidgetRun.getTarget(constraintAnchor, 0);
            int margin = constraintAnchor.getMargin();
            ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
            if (firstVisibleWidget != null) {
                margin = firstVisibleWidget.mLeft.getMargin();
            }
            if (target != null) {
                WidgetRun.addTarget(dependencyNode2, target, margin);
            }
            DependencyNode target2 = WidgetRun.getTarget(constraintAnchor2, 0);
            int margin2 = constraintAnchor2.getMargin();
            ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
            if (lastVisibleWidget != null) {
                margin2 = lastVisibleWidget.mRight.getMargin();
            }
            if (target2 != null) {
                WidgetRun.addTarget(dependencyNode, target2, -margin2);
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
                WidgetRun.addTarget(dependencyNode2, target3, margin3);
            }
            DependencyNode target4 = WidgetRun.getTarget(constraintAnchor4, 1);
            int margin4 = constraintAnchor4.getMargin();
            ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
            if (lastVisibleWidget2 != null) {
                margin4 = lastVisibleWidget2.mBottom.getMargin();
            }
            if (target4 != null) {
                WidgetRun.addTarget(dependencyNode, target4, -margin4);
            }
        }
        dependencyNode2.updateDelegate = this;
        dependencyNode.updateDelegate = this;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.widgets;
            if (i >= arrayList.size()) {
                return;
            }
            ((WidgetRun) arrayList.get(i)).applyToWidget();
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.runGroup = null;
        Iterator it = this.widgets.iterator();
        while (it.hasNext()) {
            ((WidgetRun) it.next()).clear();
        }
    }

    public final ConstraintWidget getFirstVisibleWidget() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.widgets;
            if (i >= arrayList.size()) {
                return null;
            }
            ConstraintWidget constraintWidget = ((WidgetRun) arrayList.get(i)).widget;
            if (constraintWidget.mVisibility != 8) {
                return constraintWidget;
            }
            i++;
        }
    }

    public final ConstraintWidget getLastVisibleWidget() {
        ArrayList arrayList = this.widgets;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ConstraintWidget constraintWidget = ((WidgetRun) arrayList.get(size)).widget;
            if (constraintWidget.mVisibility != 8) {
                return constraintWidget;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final long getWrapDimension() {
        ArrayList arrayList = this.widgets;
        int size = arrayList.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            j = r4.end.margin + ((WidgetRun) arrayList.get(i)).getWrapDimension() + j + r4.start.margin;
        }
        return j;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        ArrayList arrayList = this.widgets;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (!((WidgetRun) arrayList.get(i)).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        Iterator it = this.widgets.iterator();
        while (it.hasNext()) {
            WidgetRun widgetRun = (WidgetRun) it.next();
            sb.append("<");
            sb.append(widgetRun);
            sb.append("> ");
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:290:0x03af, code lost:
    
        r0 = r0 - r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00dd  */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(Dependency dependency) {
        int i;
        ArrayList arrayList;
        int i2;
        int i3;
        int i4;
        int i5;
        float f;
        int i6;
        boolean z;
        ArrayList arrayList2;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z2;
        float f2;
        int i11;
        int i12;
        int i13;
        int i14;
        boolean z3;
        int i15;
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            DependencyNode dependencyNode2 = this.end;
            if (dependencyNode2.resolved) {
                ConstraintWidget constraintWidget = this.widget.mParent;
                boolean z4 = constraintWidget instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer) constraintWidget).mIsRtl : false;
                int i16 = dependencyNode2.value - dependencyNode.value;
                ArrayList arrayList3 = this.widgets;
                int size = arrayList3.size();
                int i17 = 0;
                while (true) {
                    i = -1;
                    if (i17 >= size) {
                        i17 = -1;
                        break;
                    } else if (((WidgetRun) arrayList3.get(i17)).widget.mVisibility != 8) {
                        break;
                    } else {
                        i17++;
                    }
                }
                int i18 = size - 1;
                int i19 = i18;
                while (true) {
                    if (i19 < 0) {
                        break;
                    }
                    if (((WidgetRun) arrayList3.get(i19)).widget.mVisibility != 8) {
                        i = i19;
                        break;
                    }
                    i19--;
                }
                int i20 = 0;
                while (i20 < 2) {
                    int i21 = 0;
                    i5 = 0;
                    int i22 = 0;
                    int i23 = 0;
                    f = 0.0f;
                    while (i21 < size) {
                        WidgetRun widgetRun = (WidgetRun) arrayList3.get(i21);
                        ConstraintWidget constraintWidget2 = widgetRun.widget;
                        ArrayList arrayList4 = arrayList3;
                        if (constraintWidget2.mVisibility == 8) {
                            i14 = i17;
                        } else {
                            i23++;
                            if (i21 > 0 && i21 >= i17) {
                                i5 += widgetRun.start.margin;
                            }
                            DimensionDependency dimensionDependency = widgetRun.dimension;
                            int i24 = dimensionDependency.value;
                            i14 = i17;
                            boolean z5 = widgetRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                            if (z5) {
                                int i25 = this.orientation;
                                if (i25 == 0 && !constraintWidget2.horizontalRun.dimension.resolved) {
                                    return;
                                }
                                if (i25 == 1 && !constraintWidget2.verticalRun.dimension.resolved) {
                                    return;
                                } else {
                                    z3 = z5;
                                }
                            } else {
                                z3 = z5;
                                if (widgetRun.matchConstraintsType == 1 && i20 == 0) {
                                    i15 = dimensionDependency.wrapValue;
                                    i22++;
                                } else if (dimensionDependency.resolved) {
                                    i15 = i24;
                                }
                                z3 = true;
                                if (z3) {
                                    i22++;
                                    float f3 = constraintWidget2.mWeight[this.orientation];
                                    if (f3 >= 0.0f) {
                                        f += f3;
                                    }
                                } else {
                                    i5 += i15;
                                }
                                if (i21 < i18 && i21 < i) {
                                    i5 += -widgetRun.end.margin;
                                }
                            }
                            i15 = i24;
                            if (z3) {
                            }
                            if (i21 < i18) {
                                i5 += -widgetRun.end.margin;
                            }
                        }
                        i21++;
                        arrayList3 = arrayList4;
                        i17 = i14;
                    }
                    arrayList = arrayList3;
                    i2 = i17;
                    if (i5 < i16 || i22 == 0) {
                        i3 = i22;
                        i4 = i23;
                        break;
                    } else {
                        i20++;
                        arrayList3 = arrayList;
                        i17 = i2;
                    }
                }
                arrayList = arrayList3;
                i2 = i17;
                i3 = 0;
                i4 = 0;
                i5 = 0;
                f = 0.0f;
                int i26 = dependencyNode.value;
                if (z4) {
                    i26 = dependencyNode2.value;
                }
                if (i5 > i16) {
                    i26 = z4 ? i26 + ((int) (((i5 - i16) / 2.0f) + 0.5f)) : i26 - ((int) (((i5 - i16) / 2.0f) + 0.5f));
                }
                if (i3 > 0) {
                    float f4 = i16 - i5;
                    int i27 = (int) ((f4 / i3) + 0.5f);
                    int i28 = 0;
                    int i29 = 0;
                    while (i28 < size) {
                        ArrayList arrayList5 = arrayList;
                        WidgetRun widgetRun2 = (WidgetRun) arrayList5.get(i28);
                        int i30 = i27;
                        ConstraintWidget constraintWidget3 = widgetRun2.widget;
                        int i31 = i5;
                        int i32 = i26;
                        if (constraintWidget3.mVisibility != 8 && widgetRun2.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            DimensionDependency dimensionDependency2 = widgetRun2.dimension;
                            if (!dimensionDependency2.resolved) {
                                if (f > 0.0f) {
                                    z2 = z4;
                                    i11 = (int) (((constraintWidget3.mWeight[this.orientation] * f4) / f) + 0.5f);
                                } else {
                                    z2 = z4;
                                    i11 = i30;
                                }
                                if (this.orientation == 0) {
                                    i12 = constraintWidget3.mMatchConstraintMaxWidth;
                                    i13 = constraintWidget3.mMatchConstraintMinWidth;
                                } else {
                                    i12 = constraintWidget3.mMatchConstraintMaxHeight;
                                    i13 = constraintWidget3.mMatchConstraintMinHeight;
                                }
                                f2 = f4;
                                int max = Math.max(i13, widgetRun2.matchConstraintsType == 1 ? Math.min(i11, dimensionDependency2.wrapValue) : i11);
                                if (i12 > 0) {
                                    max = Math.min(i12, max);
                                }
                                if (max != i11) {
                                    i29++;
                                    i11 = max;
                                }
                                dimensionDependency2.resolve(i11);
                                i28++;
                                i27 = i30;
                                i5 = i31;
                                i26 = i32;
                                z4 = z2;
                                f4 = f2;
                                arrayList = arrayList5;
                            }
                        }
                        z2 = z4;
                        f2 = f4;
                        i28++;
                        i27 = i30;
                        i5 = i31;
                        i26 = i32;
                        z4 = z2;
                        f4 = f2;
                        arrayList = arrayList5;
                    }
                    i6 = i26;
                    z = z4;
                    arrayList2 = arrayList;
                    int i33 = i5;
                    if (i29 > 0) {
                        i3 -= i29;
                        int i34 = 0;
                        int i35 = 0;
                        while (i34 < size) {
                            WidgetRun widgetRun3 = (WidgetRun) arrayList2.get(i34);
                            if (widgetRun3.widget.mVisibility == 8) {
                                i10 = i2;
                            } else {
                                i10 = i2;
                                if (i34 > 0 && i34 >= i10) {
                                    i35 += widgetRun3.start.margin;
                                }
                                i35 += widgetRun3.dimension.value;
                                if (i34 < i18 && i34 < i) {
                                    i35 += -widgetRun3.end.margin;
                                }
                            }
                            i34++;
                            i2 = i10;
                        }
                        i7 = i2;
                        i5 = i35;
                    } else {
                        i7 = i2;
                        i5 = i33;
                    }
                    i9 = 2;
                    if (this.chainStyle == 2 && i29 == 0) {
                        i8 = 0;
                        this.chainStyle = 0;
                    } else {
                        i8 = 0;
                    }
                } else {
                    i6 = i26;
                    z = z4;
                    arrayList2 = arrayList;
                    i7 = i2;
                    i8 = 0;
                    i9 = 2;
                }
                if (i5 > i16) {
                    this.chainStyle = i9;
                }
                if (i4 > 0 && i3 == 0 && i7 == i) {
                    this.chainStyle = i9;
                }
                int i36 = this.chainStyle;
                if (i36 == 1) {
                    int i37 = i4 > 1 ? (i16 - i5) / (i4 - 1) : i4 == 1 ? (i16 - i5) / 2 : i8;
                    if (i3 > 0) {
                        i37 = i8;
                    }
                    int i38 = i6;
                    for (int i39 = i8; i39 < size; i39++) {
                        WidgetRun widgetRun4 = (WidgetRun) arrayList2.get(z ? size - (i39 + 1) : i39);
                        int i40 = widgetRun4.widget.mVisibility;
                        DependencyNode dependencyNode3 = widgetRun4.end;
                        DependencyNode dependencyNode4 = widgetRun4.start;
                        if (i40 == 8) {
                            dependencyNode4.resolve(i38);
                            dependencyNode3.resolve(i38);
                        } else {
                            if (i39 > 0) {
                                i38 = z ? i38 - i37 : i38 + i37;
                            }
                            if (i39 > 0 && i39 >= i7) {
                                i38 = z ? i38 - dependencyNode4.margin : i38 + dependencyNode4.margin;
                            }
                            if (z) {
                                dependencyNode3.resolve(i38);
                            } else {
                                dependencyNode4.resolve(i38);
                            }
                            DimensionDependency dimensionDependency3 = widgetRun4.dimension;
                            int i41 = dimensionDependency3.value;
                            if (widgetRun4.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun4.matchConstraintsType == 1) {
                                i41 = dimensionDependency3.wrapValue;
                            }
                            i38 = z ? i38 - i41 : i38 + i41;
                            if (z) {
                                dependencyNode4.resolve(i38);
                            } else {
                                dependencyNode3.resolve(i38);
                            }
                            widgetRun4.resolved = true;
                            if (i39 < i18 && i39 < i) {
                                i38 = z ? i38 - (-dependencyNode3.margin) : i38 + (-dependencyNode3.margin);
                            }
                        }
                    }
                    return;
                }
                if (i36 == 0) {
                    int i42 = (i16 - i5) / (i4 + 1);
                    if (i3 > 0) {
                        i42 = i8;
                    }
                    int i43 = i6;
                    for (int i44 = i8; i44 < size; i44++) {
                        WidgetRun widgetRun5 = (WidgetRun) arrayList2.get(z ? size - (i44 + 1) : i44);
                        int i45 = widgetRun5.widget.mVisibility;
                        DependencyNode dependencyNode5 = widgetRun5.end;
                        DependencyNode dependencyNode6 = widgetRun5.start;
                        if (i45 == 8) {
                            dependencyNode6.resolve(i43);
                            dependencyNode5.resolve(i43);
                        } else {
                            int i46 = z ? i43 - i42 : i43 + i42;
                            if (i44 > 0 && i44 >= i7) {
                                i46 = z ? i46 - dependencyNode6.margin : i46 + dependencyNode6.margin;
                            }
                            if (z) {
                                dependencyNode5.resolve(i46);
                            } else {
                                dependencyNode6.resolve(i46);
                            }
                            DimensionDependency dimensionDependency4 = widgetRun5.dimension;
                            int i47 = dimensionDependency4.value;
                            if (widgetRun5.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun5.matchConstraintsType == 1) {
                                i47 = Math.min(i47, dimensionDependency4.wrapValue);
                            }
                            i43 = z ? i46 - i47 : i46 + i47;
                            if (z) {
                                dependencyNode6.resolve(i43);
                            } else {
                                dependencyNode5.resolve(i43);
                            }
                            if (i44 < i18 && i44 < i) {
                                i43 = z ? i43 - (-dependencyNode5.margin) : i43 + (-dependencyNode5.margin);
                            }
                        }
                    }
                    return;
                }
                if (i36 == 2) {
                    int i48 = this.orientation;
                    ConstraintWidget constraintWidget4 = this.widget;
                    float f5 = i48 == 0 ? constraintWidget4.mHorizontalBiasPercent : constraintWidget4.mVerticalBiasPercent;
                    if (z) {
                        f5 = 1.0f - f5;
                    }
                    int i49 = (int) (((i16 - i5) * f5) + 0.5f);
                    if (i49 < 0 || i3 > 0) {
                        i49 = i8;
                    }
                    int i50 = z ? i6 - i49 : i6 + i49;
                    for (int i51 = i8; i51 < size; i51++) {
                        WidgetRun widgetRun6 = (WidgetRun) arrayList2.get(z ? size - (i51 + 1) : i51);
                        int i52 = widgetRun6.widget.mVisibility;
                        DependencyNode dependencyNode7 = widgetRun6.end;
                        DependencyNode dependencyNode8 = widgetRun6.start;
                        if (i52 == 8) {
                            dependencyNode8.resolve(i50);
                            dependencyNode7.resolve(i50);
                        } else {
                            if (i51 > 0 && i51 >= i7) {
                                i50 = z ? i50 - dependencyNode8.margin : i50 + dependencyNode8.margin;
                            }
                            if (z) {
                                dependencyNode7.resolve(i50);
                            } else {
                                dependencyNode8.resolve(i50);
                            }
                            DimensionDependency dimensionDependency5 = widgetRun6.dimension;
                            int i53 = dimensionDependency5.value;
                            if (widgetRun6.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun6.matchConstraintsType == 1) {
                                i53 = dimensionDependency5.wrapValue;
                            }
                            i50 += i53;
                            if (z) {
                                dependencyNode8.resolve(i50);
                            } else {
                                dependencyNode7.resolve(i50);
                            }
                            if (i51 < i18 && i51 < i) {
                                i50 = z ? i50 - (-dependencyNode7.margin) : i50 + (-dependencyNode7.margin);
                            }
                        }
                    }
                }
            }
        }
    }
}
