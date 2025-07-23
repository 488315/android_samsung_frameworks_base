package com.android.wm.shell.common.split;

import android.app.ActivityManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.wm.shell.shared.split.SplitScreenConstants;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplitScreenUtils {
    public static boolean isLeftRightSplit(boolean z, Configuration configuration, int i) {
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        return isLeftRightSplit(z, configuration.smallestScreenWidthDp >= 600, maxBounds.width() >= maxBounds.height(), CoreRune.MW_MULTI_SPLIT_FREE_POSITION && configuration.semDisplayDeviceType == 5, i);
    }

    public static boolean isValidToSplit(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo != null && runningTaskInfo.supportsMultiWindow && ArrayUtils.contains(SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, runningTaskInfo.getActivityType()) && ArrayUtils.contains(SplitScreenConstants.CONTROLLED_WINDOWING_MODES, runningTaskInfo.getWindowingMode());
    }

    public static int reverseSplitPosition(int i) {
        if (i != 0) {
            return i != 1 ? -1 : 0;
        }
        return 1;
    }

    public static String splitFailureMessage(String str, String str2) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("(", str, ") Splitscreen aborted: ", str2);
    }

    public static boolean isLeftRightSplit(boolean z, boolean z2, boolean z3, boolean z4, int i) {
        return (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || z4) ? (z && z2) ? !z3 : z3 : i == 0;
    }
}
