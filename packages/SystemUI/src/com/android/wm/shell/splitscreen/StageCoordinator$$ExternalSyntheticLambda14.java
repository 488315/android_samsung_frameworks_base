package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.Slog;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.split.CellUtil;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.split.SplitBounds;
import com.samsung.android.rune.CoreRune;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda14 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda14(int i, StageCoordinator stageCoordinator) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00ef  */
    @Override // java.util.function.Consumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void accept(Object obj) {
        Rect bounds3;
        int topVisibleChildTaskId;
        int i;
        boolean z;
        StageTaskListener stageTaskListener;
        int i2;
        int i3;
        int i4;
        ActivityManager.RunningTaskInfo topRunningTaskInfo;
        ActivityManager.RunningTaskInfo topRunningTaskInfo2;
        ActivityManager.RunningTaskInfo topRunningTaskInfo3;
        int i5 = this.$r8$classId;
        StageCoordinator stageCoordinator = this.f$0;
        switch (i5) {
            case 0:
                stageCoordinator.onFoldedStateChanged(((Boolean) obj).booleanValue());
                break;
            case 1:
                final RecentTasksController recentTasksController = (RecentTasksController) obj;
                stageCoordinator.getClass();
                final int i6 = 0;
                stageCoordinator.mMainStage.doForAllChildTasks(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda29
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        int i7 = i6;
                        RecentTasksController recentTasksController2 = recentTasksController;
                        Integer num = (Integer) obj2;
                        switch (i7) {
                            case 0:
                                recentTasksController2.removeSplitPair(num.intValue());
                                break;
                            default:
                                recentTasksController2.removeSplitPair(num.intValue());
                                break;
                        }
                    }
                });
                final int i7 = 1;
                stageCoordinator.mSideStage.doForAllChildTasks(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda29
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        int i72 = i7;
                        RecentTasksController recentTasksController2 = recentTasksController;
                        Integer num = (Integer) obj2;
                        switch (i72) {
                            case 0:
                                recentTasksController2.removeSplitPair(num.intValue());
                                break;
                            default:
                                recentTasksController2.removeSplitPair(num.intValue());
                                break;
                        }
                    }
                });
                break;
            case 2:
                RecentTasksController recentTasksController2 = (RecentTasksController) obj;
                int topVisibleChildTaskId2 = stageCoordinator.mMainStage.getTopVisibleChildTaskId();
                if (topVisibleChildTaskId2 != -1 && -1 == recentTasksController2.mSplitTasks.get(topVisibleChildTaskId2, -1)) {
                    Slog.d("StageCoordinator", "update pair by onSplitPairUpdateRequested");
                    stageCoordinator.mShouldUpdateRecents = true;
                    stageCoordinator.updateRecentTasksSplitPair();
                    break;
                }
                break;
            default:
                RecentTasksController recentTasksController3 = (RecentTasksController) obj;
                stageCoordinator.getClass();
                Rect rect = new Rect();
                rect.set(stageCoordinator.mSplitLayout.getTopLeftBounds());
                Rect rect2 = new Rect();
                rect2.set(stageCoordinator.mSplitLayout.getBottomRightBounds());
                StageTaskListener stageTaskListener2 = stageCoordinator.mMainStage;
                int topVisibleChildTaskId3 = stageTaskListener2.getTopVisibleChildTaskId();
                StageTaskListener stageTaskListener3 = stageCoordinator.mSideStage;
                int topVisibleChildTaskId4 = stageTaskListener3.getTopVisibleChildTaskId();
                boolean z2 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
                StageTaskListener stageTaskListener4 = stageCoordinator.mCellStage;
                if (z2 && stageTaskListener4.mIsActive) {
                    topVisibleChildTaskId = stageTaskListener4.getTopVisibleChildTaskId();
                    bounds3 = stageCoordinator.mSplitLayout.getBounds3();
                    i = stageCoordinator.mCellStageWindowConfigPosition;
                    if (CellUtil.isCellInLeftOrTopBounds(i, stageCoordinator.mSplitLayout.isVerticalDivision())) {
                        rect.set(stageCoordinator.mSplitLayout.getHostBounds());
                    } else {
                        rect2.set(stageCoordinator.mSplitLayout.getHostBounds());
                    }
                } else {
                    bounds3 = null;
                    topVisibleChildTaskId = -1;
                    i = 0;
                }
                if (stageCoordinator.mSideStagePosition == 0) {
                    z = z2;
                    stageTaskListener = stageTaskListener4;
                    i3 = topVisibleChildTaskId3;
                    i2 = topVisibleChildTaskId4;
                } else {
                    z = z2;
                    stageTaskListener = stageTaskListener4;
                    i2 = topVisibleChildTaskId3;
                    i3 = topVisibleChildTaskId4;
                }
                if (topVisibleChildTaskId3 != -1 && topVisibleChildTaskId4 != -1) {
                    int iCalculateCurrentSnapPosition = stageCoordinator.mSplitLayout.calculateCurrentSnapPosition();
                    boolean z3 = CoreRune.MW_SA_LOGGING;
                    stageCoordinator.mCurrentPackageNameList.clear();
                    if (topVisibleChildTaskId3 == -1 || (topRunningTaskInfo3 = stageTaskListener2.getTopRunningTaskInfo()) == null) {
                        i4 = -1;
                    } else {
                        stageCoordinator.mCurrentPackageNameList.add(topRunningTaskInfo3.baseActivity.getPackageName());
                        i4 = -1;
                    }
                    if (topVisibleChildTaskId4 != i4 && (topRunningTaskInfo2 = stageTaskListener3.getTopRunningTaskInfo()) != null) {
                        stageCoordinator.mCurrentPackageNameList.add(topRunningTaskInfo2.baseActivity.getPackageName());
                    }
                    if (topVisibleChildTaskId != i4 && (topRunningTaskInfo = stageTaskListener.getTopRunningTaskInfo()) != null) {
                        stageCoordinator.mCurrentPackageNameList.add(topRunningTaskInfo.baseActivity.getPackageName());
                    }
                    if (stageCoordinator.mCurrentPackageNameList.size() >= 2) {
                        Stream stream = stageCoordinator.mCurrentPackageNameList.stream();
                        final List list = stageCoordinator.mExcludeLoggingPackages;
                        Objects.requireNonNull(list);
                        if (stream.anyMatch(new Predicate() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda34
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj2) {
                                return list.contains((String) obj2);
                            }
                        })) {
                            stageCoordinator.mCurrentPackageNameList.clear();
                            stageCoordinator.mLastPackageNameList.clear();
                        }
                    }
                    boolean zEquals = stageCoordinator.mLastPackageNameList.equals(stageCoordinator.mCurrentPackageNameList);
                    stageCoordinator.mLastPackageNameList.clear();
                    stageCoordinator.mLastPackageNameList.addAll(stageCoordinator.mCurrentPackageNameList);
                    if (!z || !stageCoordinator.isMultiSplitActive()) {
                        int i8 = i2;
                        int i9 = i3;
                        rect.left = Math.max(rect.left, 0);
                        rect.top = Math.max(rect.top, 0);
                        rect2.right = Math.min(rect2.right, stageCoordinator.mSplitLayout.mRootBounds.width());
                        rect2.bottom = Math.min(rect2.bottom, stageCoordinator.mSplitLayout.mRootBounds.height());
                        SplitBounds splitBounds = new SplitBounds(rect, rect2, i8, i9, iCalculateCurrentSnapPosition);
                        if (topVisibleChildTaskId3 != -1 && topVisibleChildTaskId4 != -1) {
                            if (recentTasksController3.addSplitPair(topVisibleChildTaskId3, topVisibleChildTaskId4, -1, splitBounds) && ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5970869964711314248L, 5, Long.valueOf(i8), Long.valueOf(i9));
                            }
                            if (z3 && !zEquals) {
                                stageCoordinator.sendPairLoggingLocked();
                            }
                        }
                        if (!zEquals || (stageCoordinator.mLastSplitStateInfo != null && !stageCoordinator.hasSameRatioInGroupedTasks(splitBounds, false))) {
                            stageCoordinator.mSplitLayout.mSplitWindowManager.sendSplitStateChangedInfo(false);
                            stageCoordinator.mLastSplitStateInfo = splitBounds;
                            break;
                        }
                    } else {
                        SplitBounds splitBounds2 = new SplitBounds(rect, rect2, bounds3, i2, i3, stageCoordinator.mSplitLayout.calculateCurrentSnapPosition(), topVisibleChildTaskId, i, stageCoordinator.mSplitDivision, stageCoordinator.mSplitLayout.mParallelMultiSplit);
                        if (topVisibleChildTaskId != -1) {
                            recentTasksController3.addSplitPair(topVisibleChildTaskId3, topVisibleChildTaskId4, topVisibleChildTaskId, splitBounds2);
                            if (z3 && !zEquals) {
                                stageCoordinator.sendPairLoggingLocked();
                            }
                            if (!zEquals || (stageCoordinator.mLastSplitStateInfo != null && !stageCoordinator.hasSameRatioInGroupedTasks(splitBounds2, true))) {
                                stageCoordinator.mSplitLayout.mSplitWindowManager.sendSplitStateChangedInfo(false);
                                stageCoordinator.mLastSplitStateInfo = splitBounds2;
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }
}
