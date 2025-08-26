package androidx.constraintlayout.core.widgets.analyzer;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Chain;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class WidgetGroup {
    public static int sCount;
    public final int mId;
    public int mOrientation;
    public final ArrayList mWidgets = new ArrayList();
    public ArrayList mResults = null;
    public int mMoveTo = -1;

    public class MeasureResult {
        public MeasureResult(ConstraintWidget constraintWidget, LinearSystem linearSystem, int i) {
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
        int i2 = sCount;
        sCount = i2 + 1;
        this.mId = i2;
        this.mOrientation = i;
    }

    public final void cleanup(ArrayList arrayList) {
        int size = this.mWidgets.size();
        if (this.mMoveTo != -1 && size > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                WidgetGroup widgetGroup = (WidgetGroup) arrayList.get(i);
                if (this.mMoveTo == widgetGroup.mId) {
                    moveTo(this.mOrientation, widgetGroup);
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
        if (this.mWidgets.size() == 0) {
            return 0;
        }
        ArrayList arrayList = this.mWidgets;
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
            System.err.println(e.toString() + "\n" + Arrays.toString(e.getStackTrace()).replace("[", "   at ").replace(",", "\n   at").replace("]", ""));
        }
        this.mResults = new ArrayList();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.mResults.add(new MeasureResult((ConstraintWidget) arrayList.get(i3), linearSystem, i));
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
        ArrayList arrayList = this.mWidgets;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ConstraintWidget constraintWidget = (ConstraintWidget) obj;
            if (!widgetGroup.mWidgets.contains(constraintWidget)) {
                widgetGroup.mWidgets.add(constraintWidget);
            }
            int i3 = widgetGroup.mId;
            if (i == 0) {
                constraintWidget.horizontalGroup = i3;
            } else {
                constraintWidget.verticalGroup = i3;
            }
        }
        this.mMoveTo = widgetGroup.mId;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.mOrientation;
        sb.append(i == 0 ? "Horizontal" : i == 1 ? "Vertical" : i == 2 ? "Both" : C2paManifestList.UNKNOWN_VALUE);
        sb.append(" [");
        String strM = ReorderTile$$ExternalSyntheticOutline0.m(this.mId, "] <", sb);
        ArrayList arrayList = this.mWidgets;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM, " ");
            sbM.append(((ConstraintWidget) obj).mDebugName);
            strM = sbM.toString();
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, " >");
    }
}
