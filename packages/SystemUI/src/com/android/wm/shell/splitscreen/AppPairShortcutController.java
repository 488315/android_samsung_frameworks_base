package com.android.wm.shell.splitscreen;

import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.systemui.R;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.wm.shell.common.split.DockedDividerUtils;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.shared.split.SplitBounds;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AppPairShortcutController {
    public static final String[] sPairComponentNameList = {"component_first", "component_second", "component_third"};
    public static final String[] sPairUserIdList = {"userId_first", "userId_second", "userId_third"};
    public final Context mContext;
    public final H mH;
    public final Rect mSplitAreaBounds = new Rect();
    public final SplitLayout mSplitLayout;
    public final StageCoordinator mStageCoordinator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public static boolean supportAppPairShortCut(Context context, String str) {
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void createAppPairShortcut(int r15) {
        /*
            Method dump skipped, instructions count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.AppPairShortcutController.createAppPairShortcut(int):void");
    }

    public final Intent createAppPairShortcutIntent(String str, ArrayList arrayList, int[] iArr, int i) {
        float calculateSplitRatio;
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
            calculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitLayout.getTopLeftBounds(), this.mSplitAreaBounds, null, dimensionPixelSize);
        } else {
            splitLayout.getDisplayLayout(this.mContext).getDisplayBounds(this.mSplitAreaBounds);
            calculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitLayout.getTopLeftBounds(), this.mSplitAreaBounds, splitLayout.getDisplayLayout(this.mContext).stableInsets(true), dimensionPixelSize);
        }
        intent.putExtra("divider_ratio", calculateSplitRatio);
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

    public final Intent createAppPairShortcutIntentForRecent(String str, ArrayList arrayList, int[] iArr, int i, int i2, boolean z, SplitBounds splitBounds) {
        float calculateSplitRatio;
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
        SplitLayout splitLayout = this.mSplitLayout;
        if (z2 && this.mStageCoordinator.isMultiSplitActive()) {
            intent.putExtra("parallel_multi_split", splitLayout.mParallelMultiSplit);
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY || CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
            splitLayout.getDisplayLayout(this.mContext).getStableBounds(this.mSplitAreaBounds, true);
            calculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitBounds.leftTopBounds, this.mSplitAreaBounds, null, dimensionPixelSize);
        } else {
            splitLayout.getDisplayLayout(this.mContext).getDisplayBounds(this.mSplitAreaBounds);
            calculateSplitRatio = DockedDividerUtils.calculateSplitRatio(splitBounds.leftTopBounds, this.mSplitAreaBounds, splitLayout.getDisplayLayout(this.mContext).stableInsets(true), dimensionPixelSize);
        }
        intent.putExtra("divider_ratio", calculateSplitRatio);
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && size > 2) {
            if (splitBounds.appsStackedVertically) {
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
