package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.DisplayCutout;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import com.samsung.android.server.util.FullScreenAppsSupportUtils;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class DisplayCutoutController extends PackagesChangeAsTask {
    public Rect mCutoutInset;
    public final PackageFeatureUserChange.DumpInterface mDumpInterface;
    public final FullScreenAppsSupportUtils mFullScreenUtils;
    public CustomAspectRatioLegacyController mLegacyController;
    public Rect mNonDecorInsetsWithoutCutout;
    public boolean mRemoveCutoutOfConfiguration;
    public Rect mTmpRect;
    public final PackageFeatureUserChange mUserChange;

    public static /* synthetic */ String lambda$new$0(int i, String str, Integer num) {
        return policyToString(num.intValue());
    }

    public DisplayCutoutController(ActivityTaskManagerService activityTaskManagerService) {
        super(activityTaskManagerService);
        PackageFeatureUserChange.DumpInterface dumpInterface = new PackageFeatureUserChange.DumpInterface() { // from class: com.android.server.wm.DisplayCutoutController$$ExternalSyntheticLambda0
            @Override // com.samsung.android.server.packagefeature.PackageFeatureUserChange.DumpInterface
            public final String valueToString(int i, String str, Object obj) {
                String lambda$new$0;
                lambda$new$0 = DisplayCutoutController.lambda$new$0(i, str, (Integer) obj);
                return lambda$new$0;
            }
        };
        this.mDumpInterface = dumpInterface;
        PackageFeatureUserChange packageFeatureUserChange = new PackageFeatureUserChange(128, PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY, "DisplayCutoutPackageMap", dumpInterface);
        this.mUserChange = packageFeatureUserChange;
        this.mFullScreenUtils = FullScreenAppsSupportUtils.get();
        this.mRemoveCutoutOfConfiguration = true;
        this.mNonDecorInsetsWithoutCutout = new Rect();
        this.mTmpRect = new Rect();
        setUserChanges(packageFeatureUserChange);
    }

    public void onConfigurationChanged(DisplayContent displayContent, Configuration configuration) {
        DisplayCutout displayCutout = displayContent.mBaseDisplayCutout;
        DisplayPolicy displayPolicy = displayContent.getDisplayPolicy();
        if (displayCutout == null || displayPolicy == null || displayContent.getDisplayInfo().displayCutout == null) {
            this.mCutoutInset = null;
            this.mNonDecorInsetsWithoutCutout.setEmpty();
            return;
        }
        int i = displayContent.getDisplayInfo().rotation;
        this.mCutoutInset = displayContent.getDisplayInfo().displayCutout.getSafeInsets();
        if (displayPolicy.hasNavigationBar()) {
            int navigationBarPosition = displayPolicy.navigationBarPosition(i);
            if (navigationBarPosition == 4) {
                if (this.mNonDecorInsetsWithoutCutout.bottom > 0) {
                    this.mCutoutInset.bottom = 0;
                }
            } else if (navigationBarPosition == 2) {
                if (this.mNonDecorInsetsWithoutCutout.right > 0) {
                    this.mCutoutInset.right = 0;
                }
            } else {
                if (navigationBarPosition != 1 || this.mNonDecorInsetsWithoutCutout.left <= 0) {
                    return;
                }
                this.mCutoutInset.left = 0;
            }
        }
    }

    public void setPolicy(int i, String str, int i2, boolean z) {
        Integer num = (Integer) this.mUserChange.putValue(i, str, Integer.valueOf(i2));
        if (z) {
            if (num == null || num.intValue() != i2) {
                PackagesChange.removeTaskWithoutRemoveFromRecents(this.mAtmService, str, i, "setCutoutPolicy");
            }
        }
    }

    public int getPolicy(int i, String str) {
        if (!this.mFullScreenUtils.supportsDisplayCutout()) {
            return 0;
        }
        CustomAspectRatioLegacyController customAspectRatioLegacyController = this.mLegacyController;
        if (customAspectRatioLegacyController != null) {
            customAspectRatioLegacyController.migrateIfNeeded();
            this.mLegacyController = null;
        }
        int adjustedSecureFolderUserId = PackagesChange.getAdjustedSecureFolderUserId(i);
        Integer num = (Integer) this.mUserChange.getValue(adjustedSecureFolderUserId, str);
        if (num != null) {
            return num.intValue();
        }
        if (!this.mFullScreenUtils.containsInDefaultFullScreenList(str)) {
            return 0;
        }
        setPolicy(adjustedSecureFolderUserId, str, 1, false);
        return 1;
    }

    public ConcurrentHashMap getChangeValuesAsUser(int i) {
        return this.mUserChange.getChangeValuesAsUser(i);
    }

    public void adjustAppBoundsIfNeeded(ActivityRecord activityRecord, Configuration configuration) {
        Task task;
        Rect appBounds;
        if (!this.mRemoveCutoutOfConfiguration || this.mCutoutInset == null || (task = activityRecord.getTask()) == null || task.mCutoutPolicy != 1 || (appBounds = configuration.windowConfiguration.getAppBounds()) == null || appBounds.isEmpty()) {
            return;
        }
        Configuration resolvedOverrideConfiguration = activityRecord.getResolvedOverrideConfiguration();
        Rect appBounds2 = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
        if (appBounds2 == null || appBounds2.isEmpty()) {
            resolvedOverrideConfiguration.windowConfiguration.setOverlappingWithCutout(true);
            Rect bounds = configuration.windowConfiguration.getBounds();
            this.mTmpRect.set(appBounds);
            Rect rect = this.mCutoutInset;
            if (rect.left > 0) {
                int i = this.mNonDecorInsetsWithoutCutout.left;
                if (i > 0) {
                    this.mTmpRect.left = i;
                } else {
                    this.mTmpRect.left = bounds.left;
                }
            }
            if (rect.top > 0) {
                int i2 = this.mNonDecorInsetsWithoutCutout.top;
                if (i2 > 0) {
                    this.mTmpRect.top = i2;
                } else {
                    this.mTmpRect.top = bounds.top;
                }
            }
            if (rect.right > 0) {
                int i3 = this.mNonDecorInsetsWithoutCutout.right;
                if (i3 > 0) {
                    this.mTmpRect.right = bounds.right - i3;
                } else {
                    this.mTmpRect.right = bounds.right;
                }
            }
            if (rect.bottom > 0) {
                int i4 = this.mNonDecorInsetsWithoutCutout.bottom;
                if (i4 > 0) {
                    this.mTmpRect.bottom = bounds.bottom - i4;
                } else {
                    this.mTmpRect.bottom = bounds.bottom;
                }
            }
            if (appBounds.equals(this.mTmpRect)) {
                return;
            }
            resolvedOverrideConfiguration.windowConfiguration.setAppBounds(this.mTmpRect);
        }
    }

    public int adjustCutoutMode(WindowState windowState, int i) {
        DisplayContent displayContent;
        int i2;
        int i3;
        if (adjustCutoutModeByUser(windowState)) {
            i3 = 2097152;
        } else {
            if (!CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT || (displayContent = windowState.getDisplayContent()) == null || !displayContent.mIsOverlappingWithCutoutAsDefault || ((i2 = windowState.mOriginalLayoutInDisplayCutoutMode) != 0 && i2 != 1)) {
                return i;
            }
            windowState.mAttrs.layoutInDisplayCutoutMode = 3;
            i3 = 4194304;
        }
        return i3 | i;
    }

    public final boolean adjustCutoutModeByUser(WindowState windowState) {
        Task task = windowState.getTask();
        if (task == null) {
            return false;
        }
        int i = task.mCutoutPolicy;
        if (i == 1 && !task.inSplitScreenWindowingMode()) {
            windowState.mAttrs.layoutInDisplayCutoutMode = 3;
            return true;
        }
        if (i != 2) {
            return false;
        }
        windowState.mAttrs.layoutInDisplayCutoutMode = 2;
        return true;
    }

    @Override // com.android.server.wm.PackagesChangeAsTask
    public void onUpdateValueToTask(Task task, String str, boolean z) {
        task.mCutoutPolicy = str != null ? getPolicy(task.mUserId, str) : 0;
    }

    @Override // com.android.server.wm.PackagesChangeAsTask
    public void onDumpInTask(PrintWriter printWriter, String str, Task task) {
        if (task.mCutoutPolicy == 0) {
            return;
        }
        printWriter.print(str);
        printWriter.print("mCutoutPolicy=");
        printWriter.println(policyToString(task.mCutoutPolicy));
    }

    @Override // com.android.server.wm.PackagesChange
    public void dump(PrintWriter printWriter, String str) {
        if (!this.mRemoveCutoutOfConfiguration) {
            printWriter.println(str + "mRemoveCutoutOfConfiguration=false");
        }
        printWriter.println(str + "mCutoutInset=" + this.mCutoutInset);
        printWriter.println(str + "mNonDecorInsetsWithoutCutout=" + this.mNonDecorInsetsWithoutCutout);
    }

    @Override // com.android.server.wm.PackagesChange
    public boolean executeShellCommand(String str, String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.SAFE_DEBUG) {
            return false;
        }
        if ("-setRemoveCutoutOfConfiguration".equals(str)) {
            try {
                boolean booleanValue = Boolean.valueOf(strArr[0]).booleanValue();
                this.mAtmService.mExt.mDisplayCutoutController.mRemoveCutoutOfConfiguration = booleanValue;
                printSuccessful(printWriter, str + ", value=" + booleanValue, null, 0);
            } catch (Exception e) {
                printFail(printWriter, str, e);
                printWriter.println(str + " [packageName] [policy]");
            }
            return true;
        }
        if ("-setCutoutPolicy".equals(str)) {
            try {
                String str2 = strArr[0];
                this.mAtmService.setCutoutPolicy(0, str2, Integer.valueOf(strArr[1]).intValue());
                printSuccessful(printWriter, str, str2, this.mAtmService.getCutoutPolicy(0, strArr[0]));
            } catch (Exception e2) {
                printFail(printWriter, str, e2);
                printWriter.println(str + " [packageName] [policy]");
            }
            return true;
        }
        if ("-getCutoutPolicy".equals(str)) {
            try {
                String str3 = strArr[0];
                printSuccessful(printWriter, str, str3, this.mAtmService.getCutoutPolicy(0, str3));
            } catch (Exception e3) {
                printFail(printWriter, str, e3);
                printWriter.println(str + " [packageName]");
            }
            return true;
        }
        if (!"-resetCutoutPolicy".equals(str)) {
            return false;
        }
        try {
            this.mAtmService.resetUserPackageSettings(0, 128);
            printSuccessful(printWriter, str, null, 0);
        } catch (Exception e4) {
            printFail(printWriter, str, e4);
        }
        return true;
    }

    public final void printSuccessful(PrintWriter printWriter, String str, String str2, int i) {
        printWriter.println("Successful: " + str);
        if (str2 != null) {
            printWriter.println("PackageName=" + str2 + ", Policy=" + i);
        }
    }

    public final void printFail(PrintWriter printWriter, String str, Exception exc) {
        printWriter.println("Fail: " + str);
        printWriter.println(exc.toString());
    }

    public static String policyToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "HideCameraCutout" : "OverlapWithTheCameraCutout" : "AppDefault";
    }
}
