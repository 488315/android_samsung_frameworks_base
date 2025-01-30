package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.graphics.Rect;
import com.android.wm.shell.common.split.CellUtil;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.util.SplitBounds;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda4(StageCoordinator stageCoordinator, int i) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00fc, code lost:
    
        if (r4.anyMatch(new com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda13(r5)) != false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0180, code lost:
    
        if (r0.hasSameRatioInGroupedTasks(r4, false) == false) goto L78;
     */
    @Override // java.util.function.Consumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void accept(Object obj) {
        Rect rect;
        int i;
        int i2;
        int i3;
        int i4;
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean z;
        ActivityManager.RunningTaskInfo topRunningTaskInfo;
        ActivityManager.RunningTaskInfo topRunningTaskInfo2;
        ActivityManager.RunningTaskInfo topRunningTaskInfo3;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onFoldedStateChanged(((Boolean) obj).booleanValue());
                break;
            case 1:
                StageCoordinator stageCoordinator = this.f$0;
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                stageCoordinator.getClass();
                stageCoordinator.mMainStage.doForAllChildTasks(new StageCoordinator$$ExternalSyntheticLambda14(recentTasksController, 0));
                stageCoordinator.mSideStage.doForAllChildTasks(new StageCoordinator$$ExternalSyntheticLambda14(recentTasksController, 1));
                break;
            default:
                StageCoordinator stageCoordinator2 = this.f$0;
                RecentTasksController recentTasksController2 = (RecentTasksController) obj;
                Rect bounds1 = stageCoordinator2.mSplitLayout.getBounds1();
                Rect bounds2 = stageCoordinator2.mSplitLayout.getBounds2();
                MainStage mainStage = stageCoordinator2.mMainStage;
                int topVisibleChildTaskId = mainStage.getTopVisibleChildTaskId();
                SideStage sideStage = stageCoordinator2.mSideStage;
                int topVisibleChildTaskId2 = sideStage.getTopVisibleChildTaskId();
                boolean z2 = stageCoordinator2.mSideStagePosition == 0;
                boolean z3 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
                CellStage cellStage = stageCoordinator2.mCellStage;
                if (z3 && cellStage.mIsActive) {
                    int topVisibleChildTaskId3 = cellStage.getTopVisibleChildTaskId();
                    SplitLayout splitLayout = stageCoordinator2.mSplitLayout;
                    splitLayout.getClass();
                    Rect rect2 = new Rect(splitLayout.mBounds3);
                    int i5 = stageCoordinator2.mCellStageWindowConfigPosition;
                    if (CellUtil.isCellInLeftOrTopBounds(i5, stageCoordinator2.mSplitLayout.isVerticalDivision())) {
                        bounds1.set(stageCoordinator2.mSplitLayout.getHostBounds());
                    } else {
                        bounds2.set(stageCoordinator2.mSplitLayout.getHostBounds());
                    }
                    i2 = i5;
                    rect = rect2;
                    i = topVisibleChildTaskId3;
                } else {
                    rect = null;
                    i = -1;
                    i2 = 0;
                }
                if (z2) {
                    i4 = topVisibleChildTaskId;
                    i3 = topVisibleChildTaskId2;
                } else {
                    i3 = topVisibleChildTaskId;
                    i4 = topVisibleChildTaskId2;
                }
                boolean z4 = CoreRune.MW_SA_LOGGING;
                ArrayList arrayList3 = stageCoordinator2.mCurrentPackageNameList;
                arrayList3.clear();
                if (topVisibleChildTaskId != -1 && (topRunningTaskInfo3 = mainStage.getTopRunningTaskInfo()) != null) {
                    arrayList3.add(topRunningTaskInfo3.baseActivity.getPackageName());
                }
                if (topVisibleChildTaskId2 != -1 && (topRunningTaskInfo2 = sideStage.getTopRunningTaskInfo()) != null) {
                    arrayList3.add(topRunningTaskInfo2.baseActivity.getPackageName());
                }
                if (i != -1 && (topRunningTaskInfo = cellStage.getTopRunningTaskInfo()) != null) {
                    arrayList3.add(topRunningTaskInfo.baseActivity.getPackageName());
                }
                int size = arrayList3.size();
                ArrayList arrayList4 = stageCoordinator2.mLastPackageNameList;
                if (size >= 2) {
                    Stream stream = arrayList3.stream();
                    final List list = stageCoordinator2.mExcludeLoggingPackages;
                    Objects.requireNonNull(list);
                    break;
                }
                arrayList3.clear();
                arrayList4.clear();
                if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && stageCoordinator2.isMultiSplitActive()) {
                    int i6 = i;
                    SplitBounds splitBounds = new SplitBounds(bounds1, bounds2, rect, i3, i4, i, i2, stageCoordinator2.mSplitDivision);
                    if (topVisibleChildTaskId == -1 || topVisibleChildTaskId2 == -1 || i6 == -1) {
                        arrayList = arrayList3;
                        arrayList2 = arrayList4;
                    } else {
                        recentTasksController2.addSplitPair(topVisibleChildTaskId, topVisibleChildTaskId2, i6, splitBounds);
                        if (CoreRune.MW_SA_LOGGING) {
                            stageCoordinator2.sendPairLoggingLocked();
                        }
                        arrayList = arrayList3;
                        arrayList2 = arrayList4;
                        if ((!arrayList2.equals(arrayList)) || (stageCoordinator2.mLastSplitStateInfo != null && !stageCoordinator2.hasSameRatioInGroupedTasks(splitBounds, true))) {
                            stageCoordinator2.mSplitLayout.mSplitWindowManager.sendSplitStateChangedInfo(false);
                            stageCoordinator2.mLastSplitStateInfo = splitBounds;
                        }
                    }
                } else {
                    arrayList = arrayList3;
                    arrayList2 = arrayList4;
                    SplitBounds splitBounds2 = new SplitBounds(bounds1, bounds2, i3, i4);
                    if (topVisibleChildTaskId != -1 && topVisibleChildTaskId2 != -1) {
                        recentTasksController2.addSplitPair(topVisibleChildTaskId, topVisibleChildTaskId2, -1, splitBounds2);
                    }
                    if (CoreRune.MW_SA_LOGGING) {
                        stageCoordinator2.sendPairLoggingLocked();
                    }
                    if (!(!arrayList2.equals(arrayList))) {
                        if (stageCoordinator2.mLastSplitStateInfo != null) {
                            z = false;
                            break;
                        }
                    } else {
                        z = false;
                    }
                    stageCoordinator2.mSplitLayout.mSplitWindowManager.sendSplitStateChangedInfo(z);
                    stageCoordinator2.mLastSplitStateInfo = splitBounds2;
                }
                boolean z5 = CoreRune.MW_SA_LOGGING;
                arrayList2.clear();
                arrayList2.addAll(arrayList);
                break;
        }
    }
}
