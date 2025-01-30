package com.android.p038wm.shell.splitscreen;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Slog;
import android.window.WindowContainerToken;
import com.android.internal.policy.DockedDividerUtils;
import com.android.p038wm.shell.common.split.SplitLayout;
import com.android.systemui.R;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppPairShortcutController {
    public static final String[] sPairComponentNameList = {"component_first", "component_second", "component_third"};
    public static final String[] sPairUserIdList = {"userId_first", "userId_second", "userId_third"};
    public final Context mContext;

    /* renamed from: mH */
    public final HandlerC4091H f456mH;
    public final Rect mSplitAreaBounds = new Rect();
    public final SplitLayout mSplitLayout;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.AppPairShortcutController$H */
    public final class HandlerC4091H extends Handler {
        public HandlerC4091H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 6) {
                AppPairShortcutController.this.mContext.sendBroadcastAsUser((Intent) message.obj, UserHandle.CURRENT, "com.samsung.android.permission.ADD_PAIR_APP_SHORTCUT");
            } else {
                if (i != 7) {
                    return;
                }
                AppPairShortcutController.this.mContext.sendBroadcastAsUser((Intent) message.obj, UserHandle.CURRENT, "com.samsung.android.permission.SEND_SPLIT_STATE_CHANGED");
            }
        }
    }

    public AppPairShortcutController(Context context, SplitLayout splitLayout) {
        this.mContext = context;
        this.f456mH = new HandlerC4091H(context.getMainLooper());
        this.mSplitLayout = splitLayout;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0134 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void createAppPairShortcut(int i) {
        String flattenToShortString;
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout.mWinToken1 == null || splitLayout.mWinToken2 == null) {
            Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity there is null");
            return;
        }
        ArrayList arrayList = new ArrayList();
        Intent intent = null;
        List childTasks = splitLayout.mTaskOrganizer.getChildTasks(splitLayout.mWinToken1, (int[]) null);
        List childTasks2 = splitLayout.mTaskOrganizer.getChildTasks(splitLayout.mWinToken2, (int[]) null);
        if (childTasks.isEmpty() || childTasks2.isEmpty()) {
            Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity Or there is no child tasks.");
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && splitLayout.mStageCoordinator.isMultiSplitActive()) {
            WindowContainerToken windowContainerToken = splitLayout.mWinToken3;
            if (windowContainerToken == null) {
                Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity there is null for cell");
                return;
            }
            List childTasks3 = splitLayout.mTaskOrganizer.getChildTasks(windowContainerToken, (int[]) null);
            if (childTasks3.isEmpty()) {
                Slog.e("AppPairShortcutController", "createAppPairShortcut: Can't find topActivity there is no child tasks for cell");
                return;
            }
            int splitCreateMode = splitLayout.mStageCoordinator.getSplitCreateMode();
            int cellSide = splitLayout.getCellSide();
            if (splitCreateMode == 2 || splitCreateMode == 3) {
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
        ArrayList arrayList2 = new ArrayList();
        int[] iArr = new int[3];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) arrayList.get(i2);
            ComponentName componentName = runningTaskInfo.realActivity;
            if (componentName == null) {
                Slog.e("AppPairShortcutController", "getLaunchActivityForTask, can't get ComponentName from Task=" + runningTaskInfo);
            } else {
                Intent launchIntentForPackageAsUser = MultiWindowUtils.getLaunchIntentForPackageAsUser(componentName.getPackageName(), runningTaskInfo.userId);
                if (launchIntentForPackageAsUser != null && launchIntentForPackageAsUser.getComponent() != null) {
                    flattenToShortString = launchIntentForPackageAsUser.getComponent().flattenToShortString();
                    if (flattenToShortString == null) {
                        arrayList2.add(flattenToShortString);
                        iArr[i2] = runningTaskInfo.userId;
                    }
                }
            }
            flattenToShortString = null;
            if (flattenToShortString == null) {
            }
        }
        if (arrayList.size() != arrayList2.size()) {
            return;
        }
        if (i == 0 || i == 1) {
            intent = createAppPairShortcutIntent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_LAUNCHER", arrayList2, iArr, i);
        } else if (i == 2) {
            intent = createAppPairShortcutIntent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_EDGEPANEL", arrayList2, iArr, i);
        } else if (i == 3) {
            intent = createAppPairShortcutIntent("com.samsung.android.multiwindow.SEND_SPLIT_STATE_CHANGED", arrayList2, iArr, i);
        }
        if (intent != null) {
            HandlerC4091H handlerC4091H = this.f456mH;
            if (i == 3) {
                handlerC4091H.sendMessage(handlerC4091H.obtainMessage(7, intent));
                return;
            }
            handlerC4091H.sendMessage(handlerC4091H.obtainMessage(6, intent));
            if (CoreRune.MW_SPLIT_APP_PAIR_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1050", i == 0 ? "Taskbar" : i == 1 ? "Home screen" : "Apps edge");
            }
        }
    }

    public final Intent createAppPairShortcutIntent(String str, ArrayList arrayList, int[] iArr, int i) {
        float calculateSplitRatio;
        Intent intent = new Intent();
        intent.setAction(str);
        intent.addFlags(285212672);
        if (i == 3) {
            intent.setPackage("com.samsung.android.smartsuggestions");
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
        SplitLayout splitLayout = this.mSplitLayout;
        if (z) {
            if (splitLayout.mStageCoordinator.isMultiSplitActive()) {
                intent.putExtra("pair_orientation", splitLayout.mStageCoordinator.getSplitCreateMode());
            } else {
                intent.putExtra("pair_orientation", splitLayout.isVerticalDivision() ? 2 : 3);
            }
        }
        Context context = this.mContext;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
        boolean z2 = CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY;
        Rect rect = this.mSplitAreaBounds;
        if (z2 || CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
            splitLayout.getDisplayLayout(context).getStableBounds(rect, true);
            calculateSplitRatio = DockedDividerUtils.calculateSplitRatio(dimensionPixelSize, splitLayout.getBounds1(), rect, (Rect) null);
        } else {
            splitLayout.getDisplayLayout(context).getDisplayBounds(rect);
            calculateSplitRatio = DockedDividerUtils.calculateSplitRatio(dimensionPixelSize, splitLayout.getBounds1(), rect, splitLayout.getDisplayLayout(context).stableInsets(true));
        }
        intent.putExtra("divider_ratio", calculateSplitRatio);
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && size > 2) {
            splitLayout.getClass();
            Rect rect2 = new Rect(new Rect(splitLayout.mBounds3));
            rect2.union(splitLayout.getHostBounds());
            intent.putExtra("cell_divider_ratio", DockedDividerUtils.calculateSplitRatio(dimensionPixelSize, new Rect(splitLayout.mBounds3), rect2, (Rect) null));
        }
        if (i == 3) {
            Slog.d("AppPairShortcutController", "send split state, Split activities = " + arrayList);
        } else {
            Slog.d("AppPairShortcutController", "createAppPairShortcutLocked() Split activities = " + arrayList + ", userIds = " + Arrays.toString(iArr));
        }
        return intent;
    }
}
