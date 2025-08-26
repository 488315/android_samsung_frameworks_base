package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Rect;
import com.android.wm.shell.common.split.SplitState;
import com.android.wm.shell.draganddrop.SplitDragPolicy;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class AospSplitDropTargetProvider extends SplitDropTargetProvider {
    public AospSplitDropTargetProvider(SplitDragPolicy splitDragPolicy, Context context) {
        super(splitDragPolicy, context);
    }

    @Override // com.android.wm.shell.draganddrop.SplitDropTargetProvider
    public final void addSplitTargets(Rect rect, boolean z, boolean z2, float f, ArrayList arrayList) {
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        SplitScreenController splitScreenController = this.mSplitScreen;
        splitScreenController.getStageBounds(rect2, rect3);
        rect2.intersect(rect);
        rect3.intersect(rect);
        SplitState splitState = splitScreenController.mSplitState;
        if (z) {
            Rect rect4 = new Rect();
            Rect rect5 = new Rect();
            if (z2) {
                rect4.set(rect2);
                float f2 = f / 2.0f;
                rect4.right = (int) (rect4.right + f2);
                rect5.set(rect3);
                rect5.left = (int) (rect5.left - f2);
            } else {
                rect.splitVertically(new Rect[]{rect4, rect5});
            }
            if (!splitState.isSplitStashed()) {
                arrayList.add(new SplitDragPolicy.Target(1, rect4, rect2, -1));
                arrayList.add(new SplitDragPolicy.Target(3, rect5, rect3, -1));
                return;
            } else if (splitState.mState == 14) {
                arrayList.add(new SplitDragPolicy.Target(3, rect5, rect3, -1));
                return;
            } else {
                arrayList.add(new SplitDragPolicy.Target(1, rect4, rect2, -1));
                return;
            }
        }
        Rect rect6 = new Rect();
        Rect rect7 = new Rect();
        if (z2) {
            rect6.set(rect2);
            float f3 = f / 2.0f;
            rect6.bottom = (int) (rect6.bottom + f3);
            rect7.set(rect3);
            rect7.top = (int) (rect7.top - f3);
        } else {
            rect.splitHorizontally(new Rect[]{rect6, rect7});
        }
        if (!splitState.isSplitStashed()) {
            arrayList.add(new SplitDragPolicy.Target(2, rect6, rect2, -1));
            arrayList.add(new SplitDragPolicy.Target(4, rect7, rect3, -1));
        } else if (splitState.mState == 14) {
            arrayList.add(new SplitDragPolicy.Target(4, rect7, rect3, -1));
        } else {
            arrayList.add(new SplitDragPolicy.Target(2, rect6, rect2, -1));
        }
    }
}
