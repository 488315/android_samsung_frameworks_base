package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Rect;
import com.android.wm.shell.draganddrop.DragAndDropPolicy;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.multiwindow.MultiWindowUtils;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AospSplitDropTargetProvider extends SplitDropTargetProvider {
    public AospSplitDropTargetProvider(DragAndDropPolicy dragAndDropPolicy, Context context) {
        super(dragAndDropPolicy, context);
    }

    @Override // com.android.wm.shell.draganddrop.SplitDropTargetProvider
    public final void addSplitTargets(Rect rect, boolean z, boolean z2, float f, ArrayList arrayList) {
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        SplitScreenController splitScreenController = this.mSplitScreen;
        splitScreenController.getStageBounds(rect2, rect3);
        rect2.intersect(rect);
        rect3.intersect(rect);
        DragAndDropPolicy dragAndDropPolicy = this.mPolicy;
        if (dragAndDropPolicy.isInSubDisplay() && !z2) {
            rect2.top = 0;
            rect3.bottom = rect.bottom;
        }
        Context context = dragAndDropPolicy.mContext;
        if (MultiWindowUtils.isFlexPanelEnabled(context) || (!z2 ? z : splitScreenController.isVerticalDivision())) {
            Rect rect4 = new Rect();
            Rect rect5 = new Rect();
            if (z2) {
                rect4.set(rect2);
                float f2 = f / 2.0f;
                rect4.bottom = (int) (rect4.bottom + f2);
                rect5.set(rect3);
                rect5.top = (int) (rect5.top - f2);
            } else if (MultiWindowUtils.isFlexPanelEnabled(context)) {
                rect4.set(splitScreenController.getStageBounds(0));
                rect2.set(splitScreenController.getStageBounds(0));
                rect5.set(splitScreenController.getStageBounds(1));
                rect3.set(splitScreenController.getStageBounds(1));
            } else {
                rect.splitHorizontally(new Rect[]{rect4, rect5});
            }
            arrayList.add(new DragAndDropPolicy.Target(2, rect4, rect2));
            arrayList.add(new DragAndDropPolicy.Target(4, rect5, rect3));
            return;
        }
        if (dragAndDropPolicy.isInSubDisplay() && !z2) {
            rect2.left = 0;
            rect2.bottom = rect.bottom;
            rect3.top = 0;
            rect3.right = rect.right;
        }
        Rect rect6 = new Rect();
        Rect rect7 = new Rect();
        if (z2) {
            rect6.set(rect2);
            float f3 = f / 2.0f;
            rect6.right = (int) (rect6.right + f3);
            rect7.set(rect3);
            rect7.left = (int) (rect7.left - f3);
        } else {
            rect.splitVertically(new Rect[]{rect6, rect7});
        }
        arrayList.add(new DragAndDropPolicy.Target(1, rect6, rect2));
        arrayList.add(new DragAndDropPolicy.Target(3, rect7, rect3));
    }
}
