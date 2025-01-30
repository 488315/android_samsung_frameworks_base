package androidx.constraintlayout.core.widgets.analyzer;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Chain;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WidgetGroup {
    public static int count;

    /* renamed from: id */
    public final int f22id;
    public int orientation;
    public final ArrayList widgets = new ArrayList();
    public ArrayList results = null;
    public int moveTo = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MeasureResult {
        public MeasureResult(WidgetGroup widgetGroup, ConstraintWidget constraintWidget, LinearSystem linearSystem, int i) {
            new WeakReference(constraintWidget);
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            linearSystem.getClass();
            LinearSystem.getObjectVariableValue(constraintAnchor);
            LinearSystem.getObjectVariableValue(constraintWidget.mTop);
            LinearSystem.getObjectVariableValue(constraintWidget.mRight);
            LinearSystem.getObjectVariableValue(constraintWidget.mBottom);
            LinearSystem.getObjectVariableValue(constraintWidget.mBaseline);
        }
    }

    public WidgetGroup(int i) {
        this.f22id = -1;
        int i2 = count;
        count = i2 + 1;
        this.f22id = i2;
        this.orientation = i;
    }

    public final void cleanup(ArrayList arrayList) {
        int size = this.widgets.size();
        if (this.moveTo != -1 && size > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                WidgetGroup widgetGroup = (WidgetGroup) arrayList.get(i);
                if (this.moveTo == widgetGroup.f22id) {
                    moveTo(this.orientation, widgetGroup);
                }
            }
        }
        if (size == 0) {
            arrayList.remove(this);
        }
    }

    public final int measureWrap(LinearSystem linearSystem, int i) {
        int objectVariableValue;
        int objectVariableValue2;
        ArrayList arrayList = this.widgets;
        if (arrayList.size() == 0) {
            return 0;
        }
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) ((ConstraintWidget) arrayList.get(0)).mParent;
        linearSystem.reset();
        constraintWidgetContainer.addToSolver(linearSystem, false);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ((ConstraintWidget) arrayList.get(i2)).addToSolver(linearSystem, false);
        }
        if (i == 0 && constraintWidgetContainer.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem, arrayList, 0);
        }
        if (i == 1 && constraintWidgetContainer.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem, arrayList, 1);
        }
        try {
            linearSystem.minimize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.results = new ArrayList();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.results.add(new MeasureResult(this, (ConstraintWidget) arrayList.get(i3), linearSystem, i));
        }
        if (i == 0) {
            objectVariableValue = LinearSystem.getObjectVariableValue(constraintWidgetContainer.mLeft);
            objectVariableValue2 = LinearSystem.getObjectVariableValue(constraintWidgetContainer.mRight);
            linearSystem.reset();
        } else {
            objectVariableValue = LinearSystem.getObjectVariableValue(constraintWidgetContainer.mTop);
            objectVariableValue2 = LinearSystem.getObjectVariableValue(constraintWidgetContainer.mBottom);
            linearSystem.reset();
        }
        return objectVariableValue2 - objectVariableValue;
    }

    public final void moveTo(int i, WidgetGroup widgetGroup) {
        Iterator it = this.widgets.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            int i2 = widgetGroup.f22id;
            if (!hasNext) {
                this.moveTo = i2;
                return;
            }
            ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
            ArrayList arrayList = widgetGroup.widgets;
            if (!arrayList.contains(constraintWidget)) {
                arrayList.add(constraintWidget);
            }
            if (i == 0) {
                constraintWidget.horizontalGroup = i2;
            } else {
                constraintWidget.verticalGroup = i2;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.orientation;
        sb.append(i == 0 ? "Horizontal" : i == 1 ? "Vertical" : i == 2 ? "Both" : "Unknown");
        sb.append(" [");
        String m19m = ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.f22id, "] <");
        Iterator it = this.widgets.iterator();
        while (it.hasNext()) {
            ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(m19m, " ");
            m2m.append(constraintWidget.mDebugName);
            m19m = m2m.toString();
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m19m, " >");
    }
}
