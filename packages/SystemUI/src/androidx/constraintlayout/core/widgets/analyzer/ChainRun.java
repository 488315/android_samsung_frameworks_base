package androidx.constraintlayout.core.widgets.analyzer;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ChainRun extends WidgetRun {
    public int mChainStyle;
    public final ArrayList mWidgets;

    public ChainRun(ConstraintWidget constraintWidget, int i) {
        ConstraintWidget constraintWidget2;
        super(constraintWidget);
        this.mWidgets = new ArrayList();
        this.orientation = i;
        ConstraintWidget constraintWidget3 = this.mWidget;
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
        this.mWidget = constraintWidget2;
        ArrayList arrayList = this.mWidgets;
        int i2 = this.orientation;
        arrayList.add(i2 == 0 ? constraintWidget2.mHorizontalRun : i2 == 1 ? constraintWidget2.mVerticalRun : null);
        ConstraintWidget nextChainMember = constraintWidget2.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            ArrayList arrayList2 = this.mWidgets;
            int i3 = this.orientation;
            arrayList2.add(i3 == 0 ? nextChainMember.mHorizontalRun : i3 == 1 ? nextChainMember.mVerticalRun : null);
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        ArrayList arrayList3 = this.mWidgets;
        int size = arrayList3.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList3.get(i4);
            i4++;
            WidgetRun widgetRun = (WidgetRun) obj;
            int i5 = this.orientation;
            if (i5 == 0) {
                widgetRun.mWidget.horizontalChainRun = this;
            } else if (i5 == 1) {
                widgetRun.mWidget.verticalChainRun = this;
            }
        }
        if (this.orientation == 0 && ((ConstraintWidgetContainer) this.mWidget.mParent).mIsRtl && this.mWidgets.size() > 1) {
            this.mWidget = ((WidgetRun) AlertController$$ExternalSyntheticOutline0.m(1, this.mWidgets)).mWidget;
        }
        this.mChainStyle = this.orientation == 0 ? this.mWidget.mHorizontalChainStyle : this.mWidget.mVerticalChainStyle;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        ArrayList arrayList = this.mWidgets;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WidgetRun) obj).apply();
        }
        int size2 = this.mWidgets.size();
        if (size2 < 1) {
            return;
        }
        ConstraintWidget constraintWidget = ((WidgetRun) this.mWidgets.get(0)).mWidget;
        ConstraintWidget constraintWidget2 = ((WidgetRun) this.mWidgets.get(size2 - 1)).mWidget;
        int i2 = this.orientation;
        DependencyNode dependencyNode = this.end;
        DependencyNode dependencyNode2 = this.start;
        if (i2 == 0) {
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
        for (int i = 0; i < this.mWidgets.size(); i++) {
            ((WidgetRun) this.mWidgets.get(i)).applyToWidget();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.mRunGroup = null;
        ArrayList arrayList = this.mWidgets;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((WidgetRun) obj).clear();
        }
    }

    public final ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.mWidgets.size(); i++) {
            ConstraintWidget constraintWidget = ((WidgetRun) this.mWidgets.get(i)).mWidget;
            if (constraintWidget.mVisibility != 8) {
                return constraintWidget;
            }
        }
        return null;
    }

    public final ConstraintWidget getLastVisibleWidget() {
        for (int size = this.mWidgets.size() - 1; size >= 0; size--) {
            ConstraintWidget constraintWidget = ((WidgetRun) this.mWidgets.get(size)).mWidget;
            if (constraintWidget.mVisibility != 8) {
                return constraintWidget;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final long getWrapDimension() {
        int size = this.mWidgets.size();
        long wrapDimension = 0;
        for (int i = 0; i < size; i++) {
            wrapDimension = r4.end.mMargin + ((WidgetRun) this.mWidgets.get(i)).getWrapDimension() + wrapDimension + r4.start.mMargin;
        }
        return wrapDimension;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        int size = this.mWidgets.size();
        for (int i = 0; i < size; i++) {
            if (!((WidgetRun) this.mWidgets.get(i)).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        ArrayList arrayList = this.mWidgets;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            sb.append("<");
            sb.append((WidgetRun) obj);
            sb.append("> ");
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0152  */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(Dependency dependency) {
        int i;
        int i2;
        boolean z;
        float f;
        float f2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f3;
        int i7;
        int i8;
        float f4;
        int i9;
        int i10;
        int i11;
        boolean z2;
        boolean z3;
        int i12;
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            DependencyNode dependencyNode2 = this.end;
            if (dependencyNode2.resolved) {
                ConstraintWidget constraintWidget = this.mWidget.mParent;
                boolean z4 = constraintWidget instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer) constraintWidget).mIsRtl : false;
                int i13 = dependencyNode2.value - dependencyNode.value;
                int size = this.mWidgets.size();
                int i14 = 0;
                while (true) {
                    i = -1;
                    i2 = 8;
                    if (i14 >= size) {
                        i14 = -1;
                        break;
                    } else if (((WidgetRun) this.mWidgets.get(i14)).mWidget.mVisibility != 8) {
                        break;
                    } else {
                        i14++;
                    }
                }
                int i15 = size - 1;
                int i16 = i15;
                while (true) {
                    if (i16 < 0) {
                        break;
                    }
                    if (((WidgetRun) this.mWidgets.get(i16)).mWidget.mVisibility != 8) {
                        i = i16;
                        break;
                    }
                    i16--;
                }
                int i17 = 0;
                while (i17 < 2) {
                    f = 0.0f;
                    i5 = 0;
                    int i18 = 0;
                    int i19 = 0;
                    int i20 = 0;
                    f2 = 0.0f;
                    while (i18 < size) {
                        WidgetRun widgetRun = (WidgetRun) this.mWidgets.get(i18);
                        ConstraintWidget constraintWidget2 = widgetRun.mWidget;
                        if (constraintWidget2.mVisibility == i2) {
                            z2 = z4;
                        } else {
                            i20++;
                            if (i18 > 0 && i18 >= i14) {
                                i5 += widgetRun.start.mMargin;
                            }
                            DimensionDependency dimensionDependency = widgetRun.mDimension;
                            int i21 = dimensionDependency.value;
                            z2 = z4;
                            boolean z5 = widgetRun.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                            if (z5) {
                                int i22 = this.orientation;
                                if (i22 == 0 && !constraintWidget2.mHorizontalRun.mDimension.resolved) {
                                    return;
                                }
                                if (i22 == 1 && !constraintWidget2.mVerticalRun.mDimension.resolved) {
                                    return;
                                } else {
                                    z3 = z5;
                                }
                            } else {
                                z3 = z5;
                                if (widgetRun.matchConstraintsType == 1 && i17 == 0) {
                                    i12 = dimensionDependency.wrapValue;
                                    i19++;
                                } else if (dimensionDependency.resolved) {
                                    i12 = i21;
                                }
                                z3 = true;
                                if (z3) {
                                    i19++;
                                    float f5 = constraintWidget2.mWeight[this.orientation];
                                    if (f5 >= 0.0f) {
                                        f2 += f5;
                                    }
                                } else {
                                    i5 += i12;
                                }
                                if (i18 >= i15 && i18 < i) {
                                    i5 += -widgetRun.end.mMargin;
                                }
                            }
                            i12 = i21;
                            if (z3) {
                            }
                            if (i18 >= i15) {
                            }
                        }
                        i18++;
                        z4 = z2;
                        i2 = 8;
                    }
                    z = z4;
                    if (i5 < i13 || i19 == 0) {
                        i3 = i19;
                        i4 = i20;
                        break;
                    } else {
                        i17++;
                        z4 = z;
                        i2 = 8;
                    }
                }
                z = z4;
                f = 0.0f;
                f2 = 0.0f;
                i3 = 0;
                i4 = 0;
                i5 = 0;
                int i23 = dependencyNode.value;
                if (z) {
                    i23 = dependencyNode2.value;
                }
                float f6 = 0.5f;
                if (i5 > i13) {
                    i23 = z ? i23 + ((int) (((i5 - i13) / 2.0f) + 0.5f)) : i23 - ((int) (((i5 - i13) / 2.0f) + 0.5f));
                }
                if (i3 > 0) {
                    float f7 = i13 - i5;
                    int i24 = (int) ((f7 / i3) + 0.5f);
                    int i25 = 0;
                    int i26 = 0;
                    while (i25 < size) {
                        WidgetRun widgetRun2 = (WidgetRun) this.mWidgets.get(i25);
                        float f8 = f6;
                        ConstraintWidget constraintWidget3 = widgetRun2.mWidget;
                        int i27 = i23;
                        int i28 = i3;
                        if (constraintWidget3.mVisibility != 8 && widgetRun2.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            DimensionDependency dimensionDependency2 = widgetRun2.mDimension;
                            if (dimensionDependency2.resolved) {
                                f4 = f7;
                                i11 = i24;
                            } else {
                                int i29 = f2 > f ? (int) (((constraintWidget3.mWeight[this.orientation] * f7) / f2) + f8) : i24;
                                f4 = f7;
                                if (this.orientation == 0) {
                                    i9 = constraintWidget3.mMatchConstraintMaxWidth;
                                    i10 = constraintWidget3.mMatchConstraintMinWidth;
                                } else {
                                    i9 = constraintWidget3.mMatchConstraintMaxHeight;
                                    i10 = constraintWidget3.mMatchConstraintMinHeight;
                                }
                                i11 = i24;
                                int iMax = Math.max(i10, widgetRun2.matchConstraintsType == 1 ? Math.min(i29, dimensionDependency2.wrapValue) : i29);
                                if (i9 > 0) {
                                    iMax = Math.min(i9, iMax);
                                }
                                if (iMax != i29) {
                                    i26++;
                                    i29 = iMax;
                                }
                                dimensionDependency2.resolve(i29);
                            }
                        }
                        i25++;
                        f6 = f8;
                        i23 = i27;
                        i3 = i28;
                        f7 = f4;
                        i24 = i11;
                    }
                    i6 = i23;
                    f3 = f6;
                    int i30 = i3;
                    if (i26 > 0) {
                        i3 = i30 - i26;
                        i5 = 0;
                        for (int i31 = 0; i31 < size; i31++) {
                            WidgetRun widgetRun3 = (WidgetRun) this.mWidgets.get(i31);
                            if (widgetRun3.mWidget.mVisibility != 8) {
                                if (i31 > 0 && i31 >= i14) {
                                    i5 += widgetRun3.start.mMargin;
                                }
                                i5 += widgetRun3.mDimension.value;
                                if (i31 < i15 && i31 < i) {
                                    i5 += -widgetRun3.end.mMargin;
                                }
                            }
                        }
                    } else {
                        i3 = i30;
                    }
                    i8 = 2;
                    if (this.mChainStyle == 2 && i26 == 0) {
                        i7 = 0;
                        this.mChainStyle = 0;
                    } else {
                        i7 = 0;
                    }
                } else {
                    i6 = i23;
                    f3 = 0.5f;
                    i7 = 0;
                    i8 = 2;
                }
                if (i5 > i13) {
                    this.mChainStyle = i8;
                }
                if (i4 > 0 && i3 == 0 && i14 == i) {
                    this.mChainStyle = i8;
                }
                int i32 = this.mChainStyle;
                if (i32 == 1) {
                    int i33 = i4 > 1 ? (i13 - i5) / (i4 - 1) : i4 == 1 ? (i13 - i5) / 2 : i7;
                    if (i3 > 0) {
                        i33 = i7;
                    }
                    int i34 = i6;
                    for (int i35 = i7; i35 < size; i35++) {
                        WidgetRun widgetRun4 = (WidgetRun) this.mWidgets.get(z ? size - (i35 + 1) : i35);
                        int i36 = widgetRun4.mWidget.mVisibility;
                        DependencyNode dependencyNode3 = widgetRun4.end;
                        DependencyNode dependencyNode4 = widgetRun4.start;
                        if (i36 == 8) {
                            dependencyNode4.resolve(i34);
                            dependencyNode3.resolve(i34);
                        } else {
                            if (i35 > 0) {
                                i34 = z ? i34 - i33 : i34 + i33;
                            }
                            if (i35 > 0 && i35 >= i14) {
                                i34 = z ? i34 - dependencyNode4.mMargin : i34 + dependencyNode4.mMargin;
                            }
                            if (z) {
                                dependencyNode3.resolve(i34);
                            } else {
                                dependencyNode4.resolve(i34);
                            }
                            DimensionDependency dimensionDependency3 = widgetRun4.mDimension;
                            int i37 = dimensionDependency3.value;
                            if (widgetRun4.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun4.matchConstraintsType == 1) {
                                i37 = dimensionDependency3.wrapValue;
                            }
                            i34 = z ? i34 - i37 : i34 + i37;
                            if (z) {
                                dependencyNode4.resolve(i34);
                            } else {
                                dependencyNode3.resolve(i34);
                            }
                            widgetRun4.mResolved = true;
                            if (i35 < i15 && i35 < i) {
                                i34 = z ? i34 - (-dependencyNode3.mMargin) : i34 + (-dependencyNode3.mMargin);
                            }
                        }
                    }
                    return;
                }
                if (i32 == 0) {
                    int i38 = (i13 - i5) / (i4 + 1);
                    if (i3 > 0) {
                        i38 = i7;
                    }
                    int i39 = i6;
                    for (int i40 = i7; i40 < size; i40++) {
                        WidgetRun widgetRun5 = (WidgetRun) this.mWidgets.get(z ? size - (i40 + 1) : i40);
                        int i41 = widgetRun5.mWidget.mVisibility;
                        DependencyNode dependencyNode5 = widgetRun5.end;
                        DependencyNode dependencyNode6 = widgetRun5.start;
                        if (i41 == 8) {
                            dependencyNode6.resolve(i39);
                            dependencyNode5.resolve(i39);
                        } else {
                            int i42 = z ? i39 - i38 : i39 + i38;
                            if (i40 > 0 && i40 >= i14) {
                                i42 = z ? i42 - dependencyNode6.mMargin : i42 + dependencyNode6.mMargin;
                            }
                            if (z) {
                                dependencyNode5.resolve(i42);
                            } else {
                                dependencyNode6.resolve(i42);
                            }
                            DimensionDependency dimensionDependency4 = widgetRun5.mDimension;
                            int iMin = dimensionDependency4.value;
                            if (widgetRun5.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun5.matchConstraintsType == 1) {
                                iMin = Math.min(iMin, dimensionDependency4.wrapValue);
                            }
                            i39 = z ? i42 - iMin : i42 + iMin;
                            if (z) {
                                dependencyNode6.resolve(i39);
                            } else {
                                dependencyNode5.resolve(i39);
                            }
                            if (i40 < i15 && i40 < i) {
                                i39 = z ? i39 - (-dependencyNode5.mMargin) : i39 + (-dependencyNode5.mMargin);
                            }
                        }
                    }
                    return;
                }
                if (i32 == 2) {
                    float f9 = this.orientation == 0 ? this.mWidget.mHorizontalBiasPercent : this.mWidget.mVerticalBiasPercent;
                    if (z) {
                        f9 = 1.0f - f9;
                    }
                    int i43 = (int) (((i13 - i5) * f9) + f3);
                    if (i43 < 0 || i3 > 0) {
                        i43 = i7;
                    }
                    int i44 = z ? i6 - i43 : i6 + i43;
                    for (int i45 = i7; i45 < size; i45++) {
                        WidgetRun widgetRun6 = (WidgetRun) this.mWidgets.get(z ? size - (i45 + 1) : i45);
                        int i46 = widgetRun6.mWidget.mVisibility;
                        DependencyNode dependencyNode7 = widgetRun6.end;
                        DependencyNode dependencyNode8 = widgetRun6.start;
                        if (i46 == 8) {
                            dependencyNode8.resolve(i44);
                            dependencyNode7.resolve(i44);
                        } else {
                            if (i45 > 0 && i45 >= i14) {
                                i44 = z ? i44 - dependencyNode8.mMargin : i44 + dependencyNode8.mMargin;
                            }
                            if (z) {
                                dependencyNode7.resolve(i44);
                            } else {
                                dependencyNode8.resolve(i44);
                            }
                            DimensionDependency dimensionDependency5 = widgetRun6.mDimension;
                            int i47 = dimensionDependency5.value;
                            if (widgetRun6.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun6.matchConstraintsType == 1) {
                                i47 = dimensionDependency5.wrapValue;
                            }
                            i44 = z ? i44 - i47 : i44 + i47;
                            if (z) {
                                dependencyNode8.resolve(i44);
                            } else {
                                dependencyNode7.resolve(i44);
                            }
                            if (i45 < i15 && i45 < i) {
                                i44 = z ? i44 - (-dependencyNode7.mMargin) : i44 + (-dependencyNode7.mMargin);
                            }
                        }
                    }
                }
            }
        }
    }
}
