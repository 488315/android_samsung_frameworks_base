package com.android.server.wm;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowRelayoutResult;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.window.flags.Flags;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import com.samsung.android.server.util.FullScreenAppsSupportUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class DisplayCutoutController extends PackagesChange {
    public Rect mCutoutInset;
    public final DisplayCutoutController$$ExternalSyntheticLambda0 mDumpInterface;
    public final FullScreenAppsSupportUtils mFullScreenUtils;
    public final Rect mNonDecorInsetsWithoutCutout;
    public final boolean mRemoveCutoutOfConfiguration;
    public final Rect mTmpRect;
    public final PackageFeatureUserChange mUserChange;

    public DisplayCutoutController(ActivityTaskManagerService activityTaskManagerService) {
        super(activityTaskManagerService);
        ((ArrayList) PackagesChange.sAllPackagesChangeAsTask).add(this);
        PackageFeatureUserChange packageFeatureUserChange =
                new PackageFeatureUserChange(
                        128,
                        PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY,
                        "DisplayCutoutPackageMap",
                        new DisplayCutoutController$$ExternalSyntheticLambda0());
        this.mUserChange = packageFeatureUserChange;
        this.mFullScreenUtils = FullScreenAppsSupportUtils.LazyHolder.sUtils;
        this.mRemoveCutoutOfConfiguration = true;
        this.mNonDecorInsetsWithoutCutout = new Rect();
        this.mTmpRect = new Rect();
        this.mUserChanges = new PackageFeatureUserChange[] {packageFeatureUserChange};
    }

    public static int adjustCutoutMode(
            WindowState windowState,
            int i,
            Bundle bundle,
            WindowRelayoutResult windowRelayoutResult) {
        DisplayContent displayContent;
        int i2;
        int i3;
        Task task = windowState.getTask();
        if (task != null) {
            int i4 = task.mCutoutPolicy;
            if (i4 == 1 && !task.inSplitScreenWindowingMode()) {
                if (Flags.windowSessionRelayoutInfo() && windowRelayoutResult != null) {
                    windowRelayoutResult.cutoutPolicy = 1;
                } else if (bundle != null) {
                    bundle.putInt("cutoutpolicy", 1);
                }
                windowState.mAttrs.layoutInDisplayCutoutMode = 3;
            } else if (i4 == 2) {
                if (Flags.windowSessionRelayoutInfo() && windowRelayoutResult != null) {
                    windowRelayoutResult.cutoutPolicy = 2;
                } else if (bundle != null) {
                    bundle.putInt("cutoutpolicy", 2);
                }
                windowState.mAttrs.layoutInDisplayCutoutMode = 2;
            }
            i3 = 2097152;
            return i3 | i;
        }
        if (!CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT
                || (displayContent = windowState.getDisplayContent()) == null
                || !displayContent.mIsOverlappingWithCutoutAsDefault
                || ((i2 = windowState.mOriginalLayoutInDisplayCutoutMode) != 0 && i2 != 1)) {
            return i;
        }
        windowState.mAttrs.layoutInDisplayCutoutMode = 3;
        i3 = 4194304;
        return i3 | i;
    }

    @Override // com.android.server.wm.PackagesChange
    public final void dump(PrintWriter printWriter, String str) {
        if (!this.mRemoveCutoutOfConfiguration) {
            printWriter.println(str + "mRemoveCutoutOfConfiguration=false");
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mCutoutInset=");
        m.append(this.mCutoutInset);
        printWriter.println(m.toString());
        printWriter.println(
                str + "mNonDecorInsetsWithoutCutout=" + this.mNonDecorInsetsWithoutCutout);
    }

    public final int getPolicy(int i, String str) {
        int adjustedUserId = MultiTaskingAppCompatUtils.getAdjustedUserId(i, 2, null);
        Integer num = (Integer) this.mUserChange.getValue(adjustedUserId, str);
        if (num != null) {
            return num.intValue();
        }
        FullScreenAppsSupportUtils fullScreenAppsSupportUtils = this.mFullScreenUtils;
        if ((2 & fullScreenAppsSupportUtils.getFullScreenAppsSupportMode()) == 0
                || TextUtils.isEmpty(str)
                || !fullScreenAppsSupportUtils.mDefaultFullScreenList.contains(str)) {
            return 0;
        }
        setPolicy(adjustedUserId, 1, str, false);
        return 1;
    }

    public final void setPolicy(int i, int i2, String str, boolean z) {
        Integer num = (Integer) this.mUserChange.putValue(str, i, Integer.valueOf(i2));
        if (z) {
            if (num == null || num.intValue() != i2) {
                MultiTaskingAppCompatController multiTaskingAppCompatController =
                        this.mAtmService.mMultiTaskingAppCompatController;
                multiTaskingAppCompatController.getClass();
                multiTaskingAppCompatController.removeTaskWithoutRemoveFromRecents(
                        i, "setCutoutPolicy", false, List.of(str));
            }
        }
    }
}
