package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Flow;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class Grouping {
    public static WidgetGroup findDependents(ConstraintWidget constraintWidget, int i, ArrayList<WidgetGroup> arrayList, WidgetGroup widgetGroup) {
        int i2;
        int i3 = i == 0 ? constraintWidget.horizontalGroup : constraintWidget.verticalGroup;
        if (i3 != -1 && (widgetGroup == null || i3 != widgetGroup.mId)) {
            int i4 = 0;
            while (true) {
                if (i4 >= arrayList.size()) {
                    break;
                }
                WidgetGroup widgetGroup2 = arrayList.get(i4);
                if (widgetGroup2.mId == i3) {
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
                        WidgetGroup widgetGroup3 = arrayList.get(i6);
                        if (widgetGroup3.mId == i2) {
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
        if (widgetGroup.add(constraintWidget)) {
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                guideline.getAnchor().findDependents(guideline.getOrientation() == 0 ? 1 : 0, widgetGroup, arrayList);
            }
            if (i == 0) {
                constraintWidget.horizontalGroup = widgetGroup.mId;
                constraintWidget.mLeft.findDependents(i, widgetGroup, arrayList);
                constraintWidget.mRight.findDependents(i, widgetGroup, arrayList);
            } else {
                constraintWidget.verticalGroup = widgetGroup.mId;
                constraintWidget.mTop.findDependents(i, widgetGroup, arrayList);
                constraintWidget.mBaseline.findDependents(i, widgetGroup, arrayList);
                constraintWidget.mBottom.findDependents(i, widgetGroup, arrayList);
            }
            constraintWidget.mCenter.findDependents(i, widgetGroup, arrayList);
        }
        return widgetGroup;
    }

    /* JADX WARN: Removed duplicated region for block: B:231:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x038c A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean simpleSolvingPass(ConstraintWidgetContainer constraintWidgetContainer, BasicMeasure.Measurer measurer) {
        WidgetGroup widgetGroup;
        WidgetGroup widgetGroup2;
        int measureWrap;
        int measureWrap2;
        WidgetGroup widgetGroup3;
        WidgetGroup widgetGroup4;
        ArrayList<ConstraintWidget> arrayList = constraintWidgetContainer.mChildren;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = arrayList.get(i);
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidgetContainer.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidget.mListDimensionBehaviors;
            if (!validInGroup(dimensionBehaviour, dimensionBehaviour2, dimensionBehaviourArr2[0], dimensionBehaviourArr2[1]) || (constraintWidget instanceof Flow)) {
                return false;
            }
        }
        int i2 = 0;
        ArrayList arrayList2 = null;
        ArrayList arrayList3 = null;
        ArrayList arrayList4 = null;
        ArrayList arrayList5 = null;
        ArrayList arrayList6 = null;
        ArrayList arrayList7 = null;
        while (i2 < size) {
            ConstraintWidget constraintWidget2 = arrayList.get(i2);
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr3 = constraintWidgetContainer.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr3[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr3[1];
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr4 = constraintWidget2.mListDimensionBehaviors;
            ArrayList<ConstraintWidget> arrayList8 = arrayList;
            if (!validInGroup(dimensionBehaviour3, dimensionBehaviour4, dimensionBehaviourArr4[0], dimensionBehaviourArr4[1])) {
                ConstraintWidgetContainer.measure(constraintWidget2, measurer, constraintWidgetContainer.mMeasure);
            }
            boolean z = constraintWidget2 instanceof Guideline;
            if (z) {
                Guideline guideline = (Guideline) constraintWidget2;
                if (guideline.getOrientation() == 0) {
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList();
                    }
                    arrayList4.add(guideline);
                }
                if (guideline.getOrientation() == 1) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add(guideline);
                }
            }
            if (constraintWidget2 instanceof HelperWidget) {
                if (constraintWidget2 instanceof Barrier) {
                    Barrier barrier = (Barrier) constraintWidget2;
                    if (barrier.getOrientation() == 0) {
                        if (arrayList3 == null) {
                            arrayList3 = new ArrayList();
                        }
                        arrayList3.add(barrier);
                    }
                    if (barrier.getOrientation() == 1) {
                        if (arrayList5 == null) {
                            arrayList5 = new ArrayList();
                        }
                        arrayList5.add(barrier);
                    }
                } else {
                    HelperWidget helperWidget = (HelperWidget) constraintWidget2;
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList();
                    }
                    arrayList3.add(helperWidget);
                    if (arrayList5 == null) {
                        arrayList5 = new ArrayList();
                    }
                    arrayList5.add(helperWidget);
                }
            }
            if (constraintWidget2.mLeft.mTarget == null && constraintWidget2.mRight.mTarget == null && !z && !(constraintWidget2 instanceof Barrier)) {
                if (arrayList6 == null) {
                    arrayList6 = new ArrayList();
                }
                arrayList6.add(constraintWidget2);
            }
            if (constraintWidget2.mTop.mTarget == null && constraintWidget2.mBottom.mTarget == null && constraintWidget2.mBaseline.mTarget == null && !z && !(constraintWidget2 instanceof Barrier)) {
                if (arrayList7 == null) {
                    arrayList7 = new ArrayList();
                }
                arrayList7.add(constraintWidget2);
            }
            i2++;
            arrayList = arrayList8;
        }
        ArrayList<ConstraintWidget> arrayList9 = arrayList;
        ArrayList<WidgetGroup> arrayList10 = new ArrayList<>();
        if (arrayList2 != null) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                findDependents((Guideline) it.next(), 0, arrayList10, null);
            }
        }
        WidgetGroup widgetGroup5 = null;
        if (arrayList3 != null) {
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                HelperWidget helperWidget2 = (HelperWidget) it2.next();
                WidgetGroup findDependents = findDependents(helperWidget2, 0, arrayList10, widgetGroup5);
                helperWidget2.addDependents(0, findDependents, arrayList10);
                findDependents.cleanup(arrayList10);
                widgetGroup5 = null;
            }
        }
        ConstraintAnchor anchor = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.LEFT);
        if (anchor.getDependents() != null) {
            Iterator<ConstraintAnchor> it3 = anchor.getDependents().iterator();
            while (it3.hasNext()) {
                findDependents(it3.next().mOwner, 0, arrayList10, null);
            }
        }
        ConstraintAnchor anchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.RIGHT);
        if (anchor2.getDependents() != null) {
            Iterator<ConstraintAnchor> it4 = anchor2.getDependents().iterator();
            while (it4.hasNext()) {
                findDependents(it4.next().mOwner, 0, arrayList10, null);
            }
        }
        ConstraintAnchor.Type type = ConstraintAnchor.Type.CENTER;
        ConstraintAnchor anchor3 = constraintWidgetContainer.getAnchor(type);
        if (anchor3.getDependents() != null) {
            Iterator<ConstraintAnchor> it5 = anchor3.getDependents().iterator();
            while (it5.hasNext()) {
                findDependents(it5.next().mOwner, 0, arrayList10, null);
            }
        }
        WidgetGroup widgetGroup6 = null;
        if (arrayList6 != null) {
            Iterator it6 = arrayList6.iterator();
            while (it6.hasNext()) {
                findDependents((ConstraintWidget) it6.next(), 0, arrayList10, null);
            }
        }
        if (arrayList4 != null) {
            Iterator it7 = arrayList4.iterator();
            while (it7.hasNext()) {
                findDependents((Guideline) it7.next(), 1, arrayList10, null);
            }
        }
        if (arrayList5 != null) {
            Iterator it8 = arrayList5.iterator();
            while (it8.hasNext()) {
                HelperWidget helperWidget3 = (HelperWidget) it8.next();
                WidgetGroup findDependents2 = findDependents(helperWidget3, 1, arrayList10, widgetGroup6);
                helperWidget3.addDependents(1, findDependents2, arrayList10);
                findDependents2.cleanup(arrayList10);
                widgetGroup6 = null;
            }
        }
        ConstraintAnchor anchor4 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.TOP);
        if (anchor4.getDependents() != null) {
            Iterator<ConstraintAnchor> it9 = anchor4.getDependents().iterator();
            while (it9.hasNext()) {
                findDependents(it9.next().mOwner, 1, arrayList10, null);
            }
        }
        ConstraintAnchor anchor5 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BASELINE);
        if (anchor5.getDependents() != null) {
            Iterator<ConstraintAnchor> it10 = anchor5.getDependents().iterator();
            while (it10.hasNext()) {
                findDependents(it10.next().mOwner, 1, arrayList10, null);
            }
        }
        ConstraintAnchor anchor6 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BOTTOM);
        if (anchor6.getDependents() != null) {
            Iterator<ConstraintAnchor> it11 = anchor6.getDependents().iterator();
            while (it11.hasNext()) {
                findDependents(it11.next().mOwner, 1, arrayList10, null);
            }
        }
        ConstraintAnchor anchor7 = constraintWidgetContainer.getAnchor(type);
        if (anchor7.getDependents() != null) {
            Iterator<ConstraintAnchor> it12 = anchor7.getDependents().iterator();
            while (it12.hasNext()) {
                findDependents(it12.next().mOwner, 1, arrayList10, null);
            }
        }
        if (arrayList7 != null) {
            Iterator it13 = arrayList7.iterator();
            while (it13.hasNext()) {
                findDependents((ConstraintWidget) it13.next(), 1, arrayList10, null);
            }
        }
        int i3 = 0;
        while (i3 < size) {
            ArrayList<ConstraintWidget> arrayList11 = arrayList9;
            ConstraintWidget constraintWidget3 = arrayList11.get(i3);
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr5 = constraintWidget3.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = dimensionBehaviourArr5[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            if (dimensionBehaviour5 == dimensionBehaviour6 && dimensionBehaviourArr5[1] == dimensionBehaviour6) {
                int i4 = constraintWidget3.horizontalGroup;
                int size2 = arrayList10.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size2) {
                        widgetGroup3 = null;
                        break;
                    }
                    widgetGroup3 = arrayList10.get(i5);
                    if (i4 == widgetGroup3.mId) {
                        break;
                    }
                    i5++;
                }
                int i6 = constraintWidget3.verticalGroup;
                int size3 = arrayList10.size();
                int i7 = 0;
                while (true) {
                    if (i7 >= size3) {
                        widgetGroup4 = null;
                        break;
                    }
                    widgetGroup4 = arrayList10.get(i7);
                    if (i6 == widgetGroup4.mId) {
                        break;
                    }
                    i7++;
                }
                if (widgetGroup3 != null && widgetGroup4 != null) {
                    widgetGroup3.moveTo(0, widgetGroup4);
                    widgetGroup4.mOrientation = 2;
                    arrayList10.remove(widgetGroup3);
                }
            }
            i3++;
            arrayList9 = arrayList11;
        }
        if (arrayList10.size() <= 1) {
            return false;
        }
        ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = constraintWidgetContainer.mListDimensionBehaviors[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = ConstraintWidget.DimensionBehaviour.FIXED;
        if (dimensionBehaviour7 == dimensionBehaviour8) {
            Iterator<WidgetGroup> it14 = arrayList10.iterator();
            int i8 = 0;
            widgetGroup = null;
            while (it14.hasNext()) {
                WidgetGroup next = it14.next();
                if (next.mOrientation != 1 && (measureWrap2 = next.measureWrap(constraintWidgetContainer.getSystem(), 0)) > i8) {
                    widgetGroup = next;
                    i8 = measureWrap2;
                }
            }
            if (widgetGroup != null) {
                constraintWidgetContainer.setHorizontalDimensionBehaviour(dimensionBehaviour9);
                constraintWidgetContainer.setWidth(i8);
                if (constraintWidgetContainer.mListDimensionBehaviors[1] == dimensionBehaviour8) {
                    Iterator<WidgetGroup> it15 = arrayList10.iterator();
                    int i9 = 0;
                    WidgetGroup widgetGroup7 = null;
                    while (it15.hasNext()) {
                        WidgetGroup next2 = it15.next();
                        if (next2.mOrientation != 0 && (measureWrap = next2.measureWrap(constraintWidgetContainer.getSystem(), 1)) > i9) {
                            widgetGroup7 = next2;
                            i9 = measureWrap;
                        }
                    }
                    if (widgetGroup7 != null) {
                        constraintWidgetContainer.setVerticalDimensionBehaviour(dimensionBehaviour9);
                        constraintWidgetContainer.setHeight(i9);
                        widgetGroup2 = widgetGroup7;
                        return widgetGroup == null || widgetGroup2 != null;
                    }
                }
                widgetGroup2 = null;
                if (widgetGroup == null) {
                }
            }
        }
        widgetGroup = null;
        if (constraintWidgetContainer.mListDimensionBehaviors[1] == dimensionBehaviour8) {
        }
        widgetGroup2 = null;
        if (widgetGroup == null) {
        }
    }

    public static boolean validInGroup(ConstraintWidget.DimensionBehaviour dimensionBehaviour, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, ConstraintWidget.DimensionBehaviour dimensionBehaviour3, ConstraintWidget.DimensionBehaviour dimensionBehaviour4) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        return (dimensionBehaviour3 == dimensionBehaviour5 || dimensionBehaviour3 == dimensionBehaviour7 || (dimensionBehaviour3 == dimensionBehaviour6 && dimensionBehaviour != dimensionBehaviour7)) || (dimensionBehaviour4 == dimensionBehaviour5 || dimensionBehaviour4 == dimensionBehaviour7 || (dimensionBehaviour4 == dimensionBehaviour6 && dimensionBehaviour2 != dimensionBehaviour7));
    }
}
