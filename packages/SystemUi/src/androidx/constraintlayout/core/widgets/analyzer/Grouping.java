package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Grouping {
    public static WidgetGroup findDependents(ConstraintWidget constraintWidget, int i, ArrayList arrayList, WidgetGroup widgetGroup) {
        boolean z;
        int i2;
        int i3 = i == 0 ? constraintWidget.horizontalGroup : constraintWidget.verticalGroup;
        if (i3 != -1 && (widgetGroup == null || i3 != widgetGroup.f22id)) {
            int i4 = 0;
            while (true) {
                if (i4 >= arrayList.size()) {
                    break;
                }
                WidgetGroup widgetGroup2 = (WidgetGroup) arrayList.get(i4);
                if (widgetGroup2.f22id == i3) {
                    if (widgetGroup != null) {
                        widgetGroup.moveTo(i, widgetGroup2);
                        arrayList.remove(widgetGroup);
                    }
                    widgetGroup = widgetGroup2;
                } else {
                    i4++;
                }
            }
        } else if (i3 != -1) {
            return widgetGroup;
        }
        if (widgetGroup == null) {
            if (constraintWidget instanceof HelperWidget) {
                HelperWidget helperWidget = (HelperWidget) constraintWidget;
                int i5 = 0;
                while (true) {
                    if (i5 >= helperWidget.mWidgetsCount) {
                        i2 = -1;
                        break;
                    }
                    ConstraintWidget constraintWidget2 = helperWidget.mWidgets[i5];
                    if ((i == 0 && (i2 = constraintWidget2.horizontalGroup) != -1) || (i == 1 && (i2 = constraintWidget2.verticalGroup) != -1)) {
                        break;
                    }
                    i5++;
                }
                if (i2 != -1) {
                    int i6 = 0;
                    while (true) {
                        if (i6 >= arrayList.size()) {
                            break;
                        }
                        WidgetGroup widgetGroup3 = (WidgetGroup) arrayList.get(i6);
                        if (widgetGroup3.f22id == i2) {
                            widgetGroup = widgetGroup3;
                            break;
                        }
                        i6++;
                    }
                }
            }
            if (widgetGroup == null) {
                widgetGroup = new WidgetGroup(i);
            }
            arrayList.add(widgetGroup);
        }
        ArrayList arrayList2 = widgetGroup.widgets;
        if (arrayList2.contains(constraintWidget)) {
            z = false;
        } else {
            arrayList2.add(constraintWidget);
            z = true;
        }
        if (z) {
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                guideline.mAnchor.findDependents(guideline.mOrientation == 0 ? 1 : 0, widgetGroup, arrayList);
            }
            int i7 = widgetGroup.f22id;
            if (i == 0) {
                constraintWidget.horizontalGroup = i7;
                constraintWidget.mLeft.findDependents(i, widgetGroup, arrayList);
                constraintWidget.mRight.findDependents(i, widgetGroup, arrayList);
            } else {
                constraintWidget.verticalGroup = i7;
                constraintWidget.mTop.findDependents(i, widgetGroup, arrayList);
                constraintWidget.mBaseline.findDependents(i, widgetGroup, arrayList);
                constraintWidget.mBottom.findDependents(i, widgetGroup, arrayList);
            }
            constraintWidget.mCenter.findDependents(i, widgetGroup, arrayList);
        }
        return widgetGroup;
    }

    public static boolean validInGroup(ConstraintWidget.DimensionBehaviour dimensionBehaviour, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, ConstraintWidget.DimensionBehaviour dimensionBehaviour3, ConstraintWidget.DimensionBehaviour dimensionBehaviour4) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour6;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = ConstraintWidget.DimensionBehaviour.FIXED;
        return (dimensionBehaviour3 == dimensionBehaviour7 || dimensionBehaviour3 == (dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && dimensionBehaviour != dimensionBehaviour6)) || (dimensionBehaviour4 == dimensionBehaviour7 || dimensionBehaviour4 == (dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && dimensionBehaviour2 != dimensionBehaviour5));
    }
}
