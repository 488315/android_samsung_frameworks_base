package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.window.WindowContainerToken;
import com.android.systemui.R;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.split.CellUtil;
import com.android.wm.shell.common.split.DockedDividerUtils;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.shared.split.SplitBounds;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class AppPairShortcutController {
    public static final String[] sPairComponentNameList = {"component_first", "component_second", "component_third"};
    public static final String[] sPairUserIdList = {"userId_first", "userId_second", "userId_third"};
    public final Context mContext;
    public final H mH;
    public final Rect mSplitAreaBounds = new Rect();
    public final SplitLayout mSplitLayout;
    public final StageCoordinator mStageCoordinator;

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            AppPairShortcutController appPairShortcutController = AppPairShortcutController.this;
            if (i == 6) {
                appPairShortcutController.mContext.sendBroadcastAsUser((Intent) message.obj, UserHandle.CURRENT, "com.samsung.android.permission.ADD_PAIR_APP_SHORTCUT");
            } else {
                if (i != 7) {
                    return;
                }
                appPairShortcutController.mContext.sendBroadcastAsUser((Intent) message.obj, UserHandle.CURRENT, "com.samsung.android.permission.SEND_SPLIT_STATE_CHANGED");
            }
        }
    }

    public AppPairShortcutController(Context context, SplitLayout splitLayout) {
        this.mContext = context;
        this.mH = new H(context.getMainLooper());
        this.mSplitLayout = splitLayout;
        this.mStageCoordinator = splitLayout.mStageCoordinator;
    }

    public static String getLaunchActivityForTask(TaskInfo taskInfo) {
        ComponentName componentName = taskInfo.realActivity;
        if (componentName == null) {
            Slog.e("AppPairShortcutController", "getLaunchActivityForTask, can't get ComponentName from Task=" + taskInfo);
            return null;
        }
        Intent launchIntentForPackageAsUser = MultiWindowUtils.getLaunchIntentForPackageAsUser(componentName.getPackageName(), taskInfo.userId);
        if (launchIntentForPackageAsUser == null || launchIntentForPackageAsUser.getComponent() == null) {
            return null;
        }
        return launchIntentForPackageAsUser.getComponent().flattenToShortString();
    }

    public static boolean supportAppPairShortCut(Context context, String str) throws PackageManager.NameNotFoundException {
        Bundle bundle;
        if (MultiWindowUtils.isSingleInstancePerTask(context, str)) {
            return true;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                return bundle.getBoolean("com.samsung.android.multiwindow.support.pair.shortcut", false);
            }
            Log.w("AppPairShortcutController", "[Divider AppPair] appInfo or appInfo.metaData is null=".concat(str));
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("AppPairShortcutController", "[Divider AppPair] No such package=".concat(str));
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x015f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void createAppPairShortcut(int i) throws Resources.NotFoundException {
        SplitLayout splitLayout = this.mSplitLayout;
        WindowContainerToken windowContainerToken = splitLayout.mWinToken1;
        if (windowContainerToken == null || splitLayout.mWinToken2 == null) {
            Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity there is null");
            return;
        }
        ShellTaskOrganizer shellTaskOrganizer = splitLayout.mTaskOrganizer;
        Intent intentCreateAppPairShortcutIntent = null;
        List childTasks = shellTaskOrganizer.getChildTasks(windowContainerToken, (int[]) null);
        List childTasks2 = shellTaskOrganizer.getChildTasks(splitLayout.mWinToken2, (int[]) null);
        if (childTasks == null || childTasks2 == null || childTasks.isEmpty() || childTasks2.isEmpty()) {
            Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity Or there is no child tasks.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR) {
            StageCoordinator stageCoordinator = this.mStageCoordinator;
            if (stageCoordinator.isMultiSplitActive()) {
                WindowContainerToken windowContainerToken2 = splitLayout.mWinToken3;
                if (windowContainerToken2 == null) {
                    Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity there is null for cell");
                    return;
                }
                List childTasks3 = shellTaskOrganizer.getChildTasks(windowContainerToken2, (int[]) null);
                if (childTasks3.isEmpty()) {
                    Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity there is no child tasks for cell");
                    return;
                }
                int splitCreateMode = stageCoordinator.getSplitCreateMode();
                int cellSide = CellUtil.getCellSide(splitLayout.mCellStageWindowConfigPosition, splitLayout.isVerticalDivision(), splitLayout.mParallelMultiSplit);
                if (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitLayout.mParallelMultiSplit) {
                    if (splitCreateMode == 2) {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                    } else if (splitCreateMode == 3) {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                    } else if (splitCreateMode == 4) {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                    } else if (splitCreateMode == 5) {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                    }
                } else if (splitCreateMode == 2 || splitCreateMode == 3) {
                    if (cellSide == 2 || cellSide == 3) {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                    } else {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                    }
                    arrayList.add(1, (ActivityManager.RunningTaskInfo) childTasks2.get(0));
                } else {
                    if (cellSide == 2 || cellSide == 3) {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                    } else {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                    }
                    arrayList.add(1, (ActivityManager.RunningTaskInfo) childTasks.get(0));
                }
            } else {
                arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int[] iArr = new int[3];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) arrayList.get(i2);
            String launchActivityForTask = getLaunchActivityForTask(runningTaskInfo);
            if (launchActivityForTask != null) {
                ComponentName componentName = runningTaskInfo.baseActivity;
                if (componentName == null || !componentName.getClassName().equals("com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity")) {
                    arrayList2.add(launchActivityForTask);
                } else {
                    arrayList2.add("com.google.android.apps.bard/.shellapp.BardEntryPointActivity");
                }
                iArr[i2] = runningTaskInfo.userId;
            }
        }
        if (arrayList.size() != arrayList2.size()) {
            return;
        }
        if (i == 0 || i == 1) {
            intentCreateAppPairShortcutIntent = createAppPairShortcutIntent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_LAUNCHER", arrayList2, iArr, i);
        } else if (i == 2) {
            intentCreateAppPairShortcutIntent = createAppPairShortcutIntent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_EDGEPANEL", arrayList2, iArr, i);
        } else if (i == 3) {
            intentCreateAppPairShortcutIntent = createAppPairShortcutIntent("com.samsung.android.multiwindow.SEND_SPLIT_STATE_CHANGED", arrayList2, iArr, i);
        }
        if (intentCreateAppPairShortcutIntent != null) {
            H h = this.mH;
            if (i == 3) {
                h.sendMessage(h.obtainMessage(7, intentCreateAppPairShortcutIntent));
                return;
            }
            h.sendMessage(h.obtainMessage(6, intentCreateAppPairShortcutIntent));
            if (CoreRune.MW_SPLIT_APP_PAIR_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1050", i == 0 ? "Taskbar" : i == 1 ? "Home screen" : "Apps edge");
            }
        }
    }

    public final Intent createAppPairShortcutIntent(String str, ArrayList arrayList, int[] iArr, int i) throws Resources.NotFoundException {
        float fCalculateSplitRatio;
        Intent intent = new Intent();
        intent.setAction(str);
        intent.addFlags(285212672);
        if (i == 3) {
            intent.setPackage(BriefNowBarController.SUGGESTION_PACKAGE);
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            intent.putExtra(sPairComponentNameList[i2], (String) arrayList.get(i2));
            intent.putExtra(sPairUserIdList[i2], iArr[i2]);
        }
        if (i != 3) {
            intent.putExtra("add_app_pair_to", i);
        }
        boolean z = CoreRune.MW_MULTI_SPLIT_FREE_POSITION;
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        SplitLayout splitLayout = this.mSplitLayout;
        if (z) {
            if (stageCoordinator.isMultiSplitActive()) {
                intent.putExtra("pair_orientation", stageCoordinator.getSplitCreateMode());
            } else {
                intent.putExtra("pair_orientation", splitLayout.isVerticalDivision() ? 2 : 3);
            }
        }
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && stageCoordinator.isMultiSplitActive()) {
            intent.putExtra("parallel_multi_split", splitLayout.mParallelMultiSplit);
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY || CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
            splitLayout.getDisplayLayout(this.mContext).getStableBounds(this.mSplitAreaBounds, true);
            fCalculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitLayout.getTopLeftBounds(), this.mSplitAreaBounds, null, dimensionPixelSize);
        } else {
            splitLayout.getDisplayLayout(this.mContext).getDisplayBounds(this.mSplitAreaBounds);
            fCalculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitLayout.getTopLeftBounds(), this.mSplitAreaBounds, splitLayout.getDisplayLayout(this.mContext).stableInsets(true), dimensionPixelSize);
        }
        intent.putExtra("divider_ratio", fCalculateSplitRatio);
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && size > 2) {
            Rect rect = new Rect(splitLayout.getBounds3());
            rect.union(splitLayout.getHostBounds());
            intent.putExtra("cell_divider_ratio", DockedDividerUtils.calculateSplitRatio(splitLayout.getBounds3(), rect, null, dimensionPixelSize));
        }
        if (i == 3) {
            Slog.d("AppPairShortcutController", "send split state, Split activities = " + arrayList);
            return intent;
        }
        Slog.d("AppPairShortcutController", "createAppPairShortcutLocked() Split activities = " + arrayList + ", userIds = " + Arrays.toString(iArr));
        return intent;
    }

    public final Intent createAppPairShortcutIntentForRecent(String str, ArrayList arrayList, int[] iArr, int i, int i2, boolean z, SplitBounds splitBounds) throws Resources.NotFoundException {
        float fCalculateSplitRatio;
        float f;
        float f2;
        Intent intent = new Intent();
        intent.setAction(str);
        intent.addFlags(285212672);
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            intent.putExtra(sPairComponentNameList[i3], (String) arrayList.get(i3));
            intent.putExtra(sPairUserIdList[i3], iArr[i3]);
        }
        intent.putExtra("add_app_pair_to", i);
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            if (z) {
                intent.putExtra("pair_orientation", i2);
            } else {
                intent.putExtra("pair_orientation", splitBounds.appsStackedVertically ? 3 : 2);
            }
        }
        boolean z2 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
        if (z2 && z) {
            intent.putExtra("parallel_multi_split", splitBounds.parallelMultiSplit);
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
        boolean z3 = CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY;
        SplitLayout splitLayout = this.mSplitLayout;
        if (z3 || CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
            splitLayout.getDisplayLayout(this.mContext).getStableBounds(this.mSplitAreaBounds, true);
            if (z2 && splitBounds.parallelMultiSplit) {
                Rect rect = new Rect(splitBounds.leftTopBounds);
                int i4 = splitBounds.cellPosition;
                if ((i4 & 8) != 0 || (i4 & 16) != 0) {
                    rect.union(splitBounds.cellTaskBounds);
                }
                fCalculateSplitRatio = DockedDividerUtils.calculateSplitRatio(rect, this.mSplitAreaBounds, null, dimensionPixelSize);
            } else {
                fCalculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitBounds.leftTopBounds, this.mSplitAreaBounds, null, dimensionPixelSize);
            }
        } else {
            splitLayout.getDisplayLayout(this.mContext).getDisplayBounds(this.mSplitAreaBounds);
            fCalculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitBounds.leftTopBounds, this.mSplitAreaBounds, splitLayout.getDisplayLayout(this.mContext).stableInsets(true), dimensionPixelSize);
        }
        intent.putExtra("divider_ratio", fCalculateSplitRatio);
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && size > 2) {
            if (z2 && splitBounds.parallelMultiSplit) {
                if (splitBounds.appsStackedVertically) {
                    if ((splitBounds.cellPosition & 64) != 0) {
                        f2 = splitBounds.cellTopTaskPercent;
                        f = 1.0f - f2;
                        intent.putExtra("cell_divider_ratio", f);
                    } else {
                        f = splitBounds.cellTopTaskPercent;
                        intent.putExtra("cell_divider_ratio", f);
                    }
                } else if ((splitBounds.cellPosition & 32) != 0) {
                    f2 = splitBounds.cellLeftTaskPercent;
                    f = 1.0f - f2;
                    intent.putExtra("cell_divider_ratio", f);
                } else {
                    f = splitBounds.cellLeftTaskPercent;
                    intent.putExtra("cell_divider_ratio", f);
                }
            } else if (splitBounds.appsStackedVertically) {
                if ((splitBounds.cellPosition & 32) != 0) {
                    f2 = splitBounds.cellLeftTaskPercent;
                    f = 1.0f - f2;
                    intent.putExtra("cell_divider_ratio", f);
                } else {
                    f = splitBounds.cellLeftTaskPercent;
                    intent.putExtra("cell_divider_ratio", f);
                }
            } else if ((splitBounds.cellPosition & 64) != 0) {
                f2 = splitBounds.cellTopTaskPercent;
                f = 1.0f - f2;
                intent.putExtra("cell_divider_ratio", f);
            } else {
                f = splitBounds.cellTopTaskPercent;
                intent.putExtra("cell_divider_ratio", f);
            }
        }
        Slog.d("AppPairShortcutController", "createAppPairShortcut for Recent, activities = " + arrayList);
        return intent;
    }
}
