package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.common.split.SplitState;
import com.android.wm.shell.draganddrop.SplitDragPolicy;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MultiSplitDropTargetProvider extends SplitDropTargetProvider {
    public final int mParallelMultiSplitEdgeWidthPx;

    public MultiSplitDropTargetProvider(SplitDragPolicy splitDragPolicy, Context context) {
        super(splitDragPolicy, context);
        this.mParallelMultiSplitEdgeWidthPx = (int) (context.getResources().getDisplayMetrics().density * 180.0f);
    }

    public static SplitDragPolicy.Target createTarget(int i, Insets insets, Rect rect) {
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
                        return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right + insets.right, rect.bottom), new Rect(rect), -1);
                }
            }
            return new SplitDragPolicy.Target(i, new Rect(rect.left, rect.top, rect.right + insets.right, rect.bottom), new Rect(rect), -1);
        }
        return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right, rect.bottom), new Rect(rect), -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0216 A[EDGE_INSN: B:109:0x0216->B:110:0x0217 BREAK  A[LOOP:0: B:98:0x01f4->B:126:0x01f4]] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0061  */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v3 */
    @Override // com.android.wm.shell.draganddrop.SplitDropTargetProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addSplitTargets(Rect rect, boolean z, boolean z2, float f, ArrayList arrayList) {
        boolean z3;
        ?? r13;
        char c;
        SplitDragPolicy splitDragPolicy = this.mPolicy;
        Insets insetsOf = Insets.of(splitDragPolicy.mSession.displayLayout.stableInsets(true));
        SplitScreenController splitScreenController = this.mSplitScreen;
        if (splitScreenController.isMultiSplitScreenVisible() && z2) {
            Rect rect2 = new Rect();
            Rect rect3 = new Rect();
            Rect rect4 = new Rect();
            boolean zIsVerticalDivision = splitScreenController.isVerticalDivision();
            int cellStageWindowConfigPosition = splitScreenController.getCellStageWindowConfigPosition();
            splitScreenController.getAllStageBounds(rect2, rect4, rect3);
            if (zIsVerticalDivision) {
                c = (cellStageWindowConfigPosition & 8) != 0 ? '\b' : (cellStageWindowConfigPosition & 32) != 0 ? ' ' : (char) 0;
            } else if ((cellStageWindowConfigPosition & 16) != 0) {
                c = 16;
            } else if ((cellStageWindowConfigPosition & 64) != 0) {
                c = '@';
            }
            if (c == '\b') {
                int i = cellStageWindowConfigPosition & 16;
                Rect rect5 = i != 0 ? rect3 : rect2;
                if (i == 0) {
                    rect2 = rect3;
                }
                arrayList.add(createTarget(6, insetsOf, rect5));
                arrayList.add(createTarget(7, insetsOf, rect2));
                arrayList.add(createTarget(3, insetsOf, rect4));
                return;
            }
            if (c == 16) {
                int i2 = cellStageWindowConfigPosition & 8;
                Rect rect6 = i2 != 0 ? rect3 : rect2;
                if (i2 == 0) {
                    rect2 = rect3;
                }
                arrayList.add(createTarget(6, insetsOf, rect6));
                arrayList.add(createTarget(8, insetsOf, rect2));
                arrayList.add(createTarget(4, insetsOf, rect4));
                return;
            }
            if (c == ' ') {
                int i3 = cellStageWindowConfigPosition & 16;
                Rect rect7 = i3 != 0 ? rect3 : rect2;
                if (i3 == 0) {
                    rect2 = rect3;
                }
                arrayList.add(createTarget(8, insetsOf, rect7));
                arrayList.add(createTarget(9, insetsOf, rect2));
                arrayList.add(createTarget(1, insetsOf, rect4));
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
            arrayList.add(createTarget(7, insetsOf, rect8));
            arrayList.add(createTarget(9, insetsOf, rect2));
            arrayList.add(createTarget(2, insetsOf, rect4));
            return;
        }
        if (!z2) {
            int i5 = rect.right;
            int i6 = rect.bottom;
            if (Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_TASK_BAR, 1) == 1) {
                DragSession dragSession = splitDragPolicy.mSession;
                if (dragSession.runningTaskActType == 2) {
                    z3 = false;
                    break;
                }
                if (dragSession.isDragFromRecent) {
                    for (ActivityManager.RunningTaskInfo runningTaskInfo : ActivityTaskManager.getInstance().getTasks(Integer.MAX_VALUE)) {
                        if (runningTaskInfo.getDisplayId() == 0) {
                            if (runningTaskInfo.getActivityType() != 2) {
                                if (runningTaskInfo.getWindowingMode() == 1) {
                                    break;
                                }
                            } else {
                                z3 = false;
                                break;
                            }
                        }
                    }
                }
                z3 = true;
            }
            int i7 = i5 - insetsOf.right;
            int i8 = insetsOf.left;
            int iM = AbsActionBarView$$ExternalSyntheticOutline0.m(i7, i8, 2, i8);
            int i9 = z3 ? i6 - insetsOf.bottom : i6;
            if (splitScreenController.isSplitScreenFeasible(true)) {
                int i10 = i6 / 2;
                arrayList.add(new SplitDragPolicy.Target(2, null, new Rect(insetsOf.left, 0, i5 - insetsOf.right, i10), -1, true, getPolygonTouchRegion(2, new Rect(0, 0, i5, i10))));
                r13 = 0;
                arrayList.add(new SplitDragPolicy.Target(4, null, new Rect(insetsOf.left, i10, i5 - insetsOf.right, i9), -1, true, getPolygonTouchRegion(4, new Rect(0, i10, i5, i9))));
            } else {
                r13 = 0;
            }
            if (splitScreenController.isSplitScreenFeasible(r13)) {
                int i11 = i5 / 2;
                arrayList.add(new SplitDragPolicy.Target(3, null, new Rect(iM, r13, i5 - insetsOf.right, i9), -1, true, getPolygonTouchRegion(3, new Rect(i11, r13, i5, i9))));
                arrayList.add(new SplitDragPolicy.Target(1, null, new Rect(insetsOf.left, r13, iM, i9), -1, true, getPolygonTouchRegion(1, new Rect(r13, r13, i11, i9))));
                return;
            }
            return;
        }
        boolean zIsVerticalDivision2 = splitScreenController.isVerticalDivision();
        Rect rect9 = new Rect();
        Rect rect10 = new Rect();
        splitScreenController.getStageBounds(rect9, rect10);
        SplitState splitState = splitScreenController.mSplitState;
        if (zIsVerticalDivision2) {
            if (splitState.isSplitStashed()) {
                if (splitState.mState == 14) {
                    arrayList.add(createTarget(3, insetsOf, rect10));
                    return;
                } else {
                    arrayList.add(createTarget(1, insetsOf, rect9));
                    return;
                }
            }
            arrayList.add(createTarget(1, insetsOf, rect9));
            arrayList.add(createTarget(3, insetsOf, rect10));
            arrayList.add(createMultiSplitTarget(6, rect9, true, insetsOf));
            arrayList.add(createMultiSplitTarget(7, rect9, true, insetsOf));
            arrayList.add(createMultiSplitTarget(8, rect10, true, insetsOf));
            arrayList.add(createMultiSplitTarget(9, rect10, true, insetsOf));
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT && z) {
                arrayList.add(createMultiSplitTarget(10, rect9, true, insetsOf));
                arrayList.add(createMultiSplitTarget(12, rect10, true, insetsOf));
                return;
            }
            return;
        }
        if (splitState.isSplitStashed()) {
            if (splitState.mState == 14) {
                arrayList.add(createTarget(4, insetsOf, rect10));
                return;
            } else {
                arrayList.add(createTarget(2, insetsOf, rect9));
                return;
            }
        }
        arrayList.add(createTarget(2, insetsOf, rect9));
        arrayList.add(createTarget(4, insetsOf, rect10));
        arrayList.add(createMultiSplitTarget(6, rect9, false, insetsOf));
        arrayList.add(createMultiSplitTarget(8, rect9, false, insetsOf));
        arrayList.add(createMultiSplitTarget(7, rect10, false, insetsOf));
        arrayList.add(createMultiSplitTarget(9, rect10, false, insetsOf));
        if (!CoreRune.MW_PARALLEL_MULTI_SPLIT || z) {
            return;
        }
        arrayList.add(createMultiSplitTarget(11, rect9, false, insetsOf));
        arrayList.add(createMultiSplitTarget(13, rect10, false, insetsOf));
    }

    public final SplitDragPolicy.Target createMultiSplitTarget(int i, Rect rect, boolean z, Insets insets) {
        int i2 = this.mParallelMultiSplitEdgeWidthPx;
        if (!z) {
            int i3 = (rect.right + insets.left) / 2;
            switch (i) {
                case 6:
                case 7:
                    return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right / 3, rect.bottom), new Rect(rect.left, rect.top, i3, rect.bottom), -1);
                case 8:
                case 9:
                    int i4 = rect.right;
                    return new SplitDragPolicy.Target(i, new Rect((i4 * 2) / 3, rect.top, i4 + insets.right, rect.bottom), new Rect(i3, rect.top, rect.right, rect.bottom), -1);
                case 10:
                case 12:
                default:
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong DropTarget type: #"));
                case 11:
                    int i5 = rect.left;
                    int i6 = rect.top;
                    Rect rect2 = new Rect(i5, i6, rect.right, i6 + i2);
                    int i7 = rect.left;
                    int i8 = rect.top;
                    return new SplitDragPolicy.Target(i, rect2, new Rect(i7, i8, rect.right, i2 + i8), -1);
                case 13:
                    int i9 = rect.left;
                    int i10 = rect.bottom;
                    Rect rect3 = new Rect(i9, i10 - i2, rect.right, i10);
                    int i11 = rect.left;
                    int i12 = rect.bottom;
                    return new SplitDragPolicy.Target(i, rect3, new Rect(i11, i12 - i2, rect.right, i12), -1);
            }
        }
        int i13 = (rect.bottom + insets.top) / 2;
        switch (i) {
            case 6:
                return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right, rect.bottom / 3), new Rect(rect.left, rect.top, rect.right, i13), -1);
            case 7:
                int i14 = rect.left - insets.left;
                int i15 = rect.bottom;
                return new SplitDragPolicy.Target(i, new Rect(i14, (i15 * 2) / 3, rect.right, i15), new Rect(rect.left, i13, rect.right, rect.bottom), -1);
            case 8:
                return new SplitDragPolicy.Target(i, new Rect(rect.left, rect.top, rect.right + insets.right, rect.bottom / 3), new Rect(rect.left, rect.top, rect.right, i13), -1);
            case 9:
                int i16 = rect.left;
                int i17 = rect.bottom;
                return new SplitDragPolicy.Target(i, new Rect(i16, (i17 * 2) / 3, rect.right + insets.right, i17), new Rect(rect.left, i13, rect.right, rect.bottom), -1);
            case 10:
                int i18 = rect.left;
                Rect rect4 = new Rect(i18, rect.top, i18 + i2, rect.bottom);
                int i19 = rect.left;
                return new SplitDragPolicy.Target(i, rect4, new Rect(i19, rect.top, i2 + i19, rect.bottom), -1);
            case 11:
            default:
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong DropTarget type: #"));
            case 12:
                int i20 = rect.right;
                Rect rect5 = new Rect(i20 - i2, rect.top, i20, rect.bottom);
                int i21 = rect.right;
                return new SplitDragPolicy.Target(i, rect5, new Rect(i21 - i2, rect.top, i21, rect.bottom), -1);
        }
    }

    public final List getPolygonTouchRegion(int i, Rect rect) {
        SplitDragPolicy splitDragPolicy = this.mPolicy;
        Rect centerFreeformBounds = splitDragPolicy.getCenterFreeformBounds();
        int i2 = -splitDragPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
        centerFreeformBounds.inset(i2, i2);
        ArrayList arrayList = new ArrayList();
        if (i == 2) {
            arrayList.add(new PointF(rect.left, rect.top));
            arrayList.add(new PointF(rect.right, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
            return arrayList;
        }
        if (i == 3) {
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
            arrayList.add(new PointF(rect.right, rect.top));
            arrayList.add(new PointF(rect.right, rect.bottom));
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.bottom));
            return arrayList;
        }
        if (i == 1) {
            arrayList.add(new PointF(rect.left, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.bottom));
            arrayList.add(new PointF(rect.left, rect.bottom));
            return arrayList;
        }
        if (i == 4) {
            arrayList.add(new PointF(centerFreeformBounds.left, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.right, rect.top));
            arrayList.add(new PointF(rect.right, rect.bottom));
            arrayList.add(new PointF(rect.left, rect.bottom));
        }
        return arrayList;
    }
}
