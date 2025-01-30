package com.android.p038wm.shell.draganddrop;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.draganddrop.DragAndDropPolicy;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiSplitDropTargetProvider extends SplitDropTargetProvider {
    public MultiSplitDropTargetProvider(DragAndDropPolicy dragAndDropPolicy, Context context) {
        super(dragAndDropPolicy, context);
    }

    public static DragAndDropPolicy.Target createMultiSplitTarget(int i, Rect rect, boolean z, Insets insets) {
        if (!z) {
            int i2 = (rect.right + insets.left) / 2;
            switch (i) {
                case 6:
                case 7:
                    return new DragAndDropPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right / 3, rect.bottom), new Rect(rect.left, rect.top, i2, rect.bottom));
                case 8:
                case 9:
                    int i3 = rect.right;
                    return new DragAndDropPolicy.Target(i, new Rect((i3 * 2) / 3, rect.top, i3 + insets.right, rect.bottom), new Rect(i2, rect.top, rect.right, rect.bottom));
                default:
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Wrong DropTarget type: #", i));
            }
        }
        int i4 = (rect.bottom + insets.top) / 2;
        switch (i) {
            case 6:
                return new DragAndDropPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right, rect.bottom / 3), new Rect(rect.left, rect.top, rect.right, i4));
            case 7:
                int i5 = rect.left - insets.left;
                int i6 = rect.bottom;
                return new DragAndDropPolicy.Target(i, new Rect(i5, (i6 * 2) / 3, rect.right, i6), new Rect(rect.left, i4, rect.right, rect.bottom));
            case 8:
                return new DragAndDropPolicy.Target(i, new Rect(rect.left, rect.top, rect.right + insets.right, rect.bottom / 3), new Rect(rect.left, rect.top, rect.right, i4));
            case 9:
                int i7 = rect.left;
                int i8 = rect.bottom;
                return new DragAndDropPolicy.Target(i, new Rect(i7, (i8 * 2) / 3, rect.right + insets.right, i8), new Rect(rect.left, i4, rect.right, rect.bottom));
            default:
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Wrong DropTarget type: #", i));
        }
    }

    public static DragAndDropPolicy.Target createTarget(int i, Insets insets, Rect rect) {
        if (i != 1) {
            if (i != 3) {
                switch (i) {
                    case 6:
                    case 7:
                        break;
                    case 8:
                    case 9:
                        break;
                    default:
                        return new DragAndDropPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right + insets.right, rect.bottom), new Rect(rect));
                }
            }
            return new DragAndDropPolicy.Target(i, new Rect(rect.left, rect.top, rect.right + insets.right, rect.bottom), new Rect(rect));
        }
        return new DragAndDropPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right, rect.bottom), new Rect(rect));
    }

    /* JADX WARN: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0227  */
    @Override // com.android.p038wm.shell.draganddrop.SplitDropTargetProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addSplitTargets(Rect rect, boolean z, boolean z2, float f, ArrayList arrayList) {
        boolean z3;
        boolean z4;
        boolean z5;
        char c;
        DragAndDropPolicy dragAndDropPolicy = this.mPolicy;
        Insets of = Insets.of(dragAndDropPolicy.mSession.displayLayout.stableInsets(true));
        SplitScreenController splitScreenController = this.mSplitScreen;
        if (splitScreenController.isMultiSplitScreenVisible()) {
            Rect rect2 = new Rect();
            Rect rect3 = new Rect();
            Rect rect4 = new Rect();
            boolean isVerticalDivision = splitScreenController.isVerticalDivision();
            int cellStageWindowConfigPosition = splitScreenController.getCellStageWindowConfigPosition();
            splitScreenController.getAllStageBounds(rect2, rect3, rect4);
            if (isVerticalDivision) {
                if ((cellStageWindowConfigPosition & 8) != 0) {
                    c = '\b';
                } else {
                    if ((cellStageWindowConfigPosition & 32) != 0) {
                        c = ' ';
                    }
                    c = 0;
                }
            } else if ((cellStageWindowConfigPosition & 16) != 0) {
                c = 16;
            } else {
                if ((cellStageWindowConfigPosition & 64) != 0) {
                    c = '@';
                }
                c = 0;
            }
            if (c == '\b') {
                int i = cellStageWindowConfigPosition & 16;
                Rect rect5 = i != 0 ? rect3 : rect2;
                if (i == 0) {
                    rect2 = rect3;
                }
                arrayList.add(createTarget(6, of, rect5));
                arrayList.add(createTarget(7, of, rect2));
                arrayList.add(createTarget(3, of, rect4));
                return;
            }
            if (c == 16) {
                int i2 = cellStageWindowConfigPosition & 8;
                Rect rect6 = i2 != 0 ? rect3 : rect2;
                if (i2 == 0) {
                    rect2 = rect3;
                }
                arrayList.add(createTarget(6, of, rect6));
                arrayList.add(createTarget(8, of, rect2));
                arrayList.add(createTarget(4, of, rect4));
                return;
            }
            if (c == ' ') {
                int i3 = cellStageWindowConfigPosition & 16;
                Rect rect7 = i3 != 0 ? rect3 : rect2;
                if (i3 == 0) {
                    rect2 = rect3;
                }
                arrayList.add(createTarget(8, of, rect7));
                arrayList.add(createTarget(9, of, rect2));
                arrayList.add(createTarget(1, of, rect4));
                return;
            }
            if (c != '@') {
                return;
            }
            int i4 = cellStageWindowConfigPosition & 8;
            Rect rect8 = i4 != 0 ? rect3 : rect2;
            if (i4 == 0) {
                rect2 = rect3;
            }
            arrayList.add(createTarget(7, of, rect8));
            arrayList.add(createTarget(9, of, rect2));
            arrayList.add(createTarget(2, of, rect4));
            return;
        }
        if (z2) {
            boolean isVerticalDivision2 = splitScreenController.isVerticalDivision();
            Rect rect9 = new Rect();
            Rect rect10 = new Rect();
            splitScreenController.getStageBounds(rect9, rect10);
            if (isVerticalDivision2) {
                arrayList.add(createTarget(1, of, rect9));
                arrayList.add(createTarget(3, of, rect10));
                arrayList.add(createMultiSplitTarget(6, rect9, true, of));
                arrayList.add(createMultiSplitTarget(7, rect9, true, of));
                arrayList.add(createMultiSplitTarget(8, rect10, true, of));
                arrayList.add(createMultiSplitTarget(9, rect10, true, of));
                return;
            }
            arrayList.add(createTarget(2, of, rect9));
            arrayList.add(createTarget(4, of, rect10));
            arrayList.add(createMultiSplitTarget(6, rect9, false, of));
            arrayList.add(createMultiSplitTarget(8, rect9, false, of));
            arrayList.add(createMultiSplitTarget(7, rect10, false, of));
            arrayList.add(createMultiSplitTarget(9, rect10, false, of));
            return;
        }
        int i5 = rect.right;
        int i6 = rect.bottom;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "task_bar", 1) == 1) {
            DragAndDropPolicy.DragSession dragSession = dragAndDropPolicy.mSession;
            if (dragSession.runningTaskActType != 2) {
                if (dragSession.isDragFromRecent) {
                    for (ActivityManager.RunningTaskInfo runningTaskInfo : dragAndDropPolicy.mActivityTaskManager.getTasks(Integer.MAX_VALUE)) {
                        if (runningTaskInfo.getDisplayId() == 0) {
                            if (runningTaskInfo.getActivityType() != 2) {
                                if (runningTaskInfo.getWindowingMode() == 1) {
                                    break;
                                }
                            } else {
                                z5 = true;
                                break;
                            }
                        }
                    }
                }
                z5 = false;
                if (!z5) {
                    z4 = false;
                    if (!z4) {
                        z3 = true;
                        int i7 = i5 - of.right;
                        int i8 = of.left;
                        int m8m = AbsActionBarView$$ExternalSyntheticOutline0.m8m(i7, i8, 2, i8);
                        int i9 = !z3 ? i6 - of.bottom : i6;
                        if (splitScreenController.isSplitScreenFeasible(true)) {
                            int i10 = i6 / 2;
                            arrayList.add(new DragAndDropPolicy.Target(2, null, new Rect(of.left, 0, i5 - of.right, i10), true, getPolygonTouchRegion(2, new Rect(0, 0, i5, i10))));
                            arrayList.add(new DragAndDropPolicy.Target(4, null, new Rect(of.left, i10, i5 - of.right, i9), true, getPolygonTouchRegion(4, new Rect(0, i10, i5, i9))));
                        }
                        if (splitScreenController.isSplitScreenFeasible(false)) {
                            return;
                        }
                        int i11 = i5 / 2;
                        arrayList.add(new DragAndDropPolicy.Target(3, null, new Rect(m8m, 0, i5 - of.right, i9), true, getPolygonTouchRegion(3, new Rect(i11, 0, i5, i9))));
                        arrayList.add(new DragAndDropPolicy.Target(1, null, new Rect(of.left, 0, m8m, i9), true, getPolygonTouchRegion(1, new Rect(0, 0, i11, i9))));
                        return;
                    }
                }
            }
            z4 = true;
            if (!z4) {
            }
        }
        z3 = false;
        int i72 = i5 - of.right;
        int i82 = of.left;
        int m8m2 = AbsActionBarView$$ExternalSyntheticOutline0.m8m(i72, i82, 2, i82);
        if (!z3) {
        }
        if (splitScreenController.isSplitScreenFeasible(true)) {
        }
        if (splitScreenController.isSplitScreenFeasible(false)) {
        }
    }

    public final List getPolygonTouchRegion(int i, Rect rect) {
        DragAndDropPolicy dragAndDropPolicy = this.mPolicy;
        Rect centerFreeformBounds = dragAndDropPolicy.getCenterFreeformBounds();
        int i2 = -dragAndDropPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
        centerFreeformBounds.inset(i2, i2);
        ArrayList arrayList = new ArrayList();
        if (i == 2) {
            arrayList.add(new PointF(rect.left, rect.top));
            arrayList.add(new PointF(rect.right, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
        } else if (i == 3) {
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
            arrayList.add(new PointF(rect.right, rect.top));
            arrayList.add(new PointF(rect.right, rect.bottom));
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.bottom));
        } else if (i == 1) {
            arrayList.add(new PointF(rect.left, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.bottom));
            arrayList.add(new PointF(rect.left, rect.bottom));
        } else if (i == 4) {
            arrayList.add(new PointF(centerFreeformBounds.left, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.right, rect.top));
            arrayList.add(new PointF(rect.right, rect.bottom));
            arrayList.add(new PointF(rect.left, rect.bottom));
        }
        return arrayList;
    }
}
