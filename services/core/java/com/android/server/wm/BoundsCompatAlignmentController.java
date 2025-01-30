package com.android.server.wm;

import android.graphics.Rect;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class BoundsCompatAlignmentController {
    public static final String TAG = "BoundsCompatAlignmentController";
    public boolean mAnimationEnabled;
    public ActivityTaskManagerService mAtmService;
    public final BoundsCompatAlignment mGlobalAlignment;
    public final Runnable mRecomputeConfiguration;

    public abstract class LazyHolder {
        public static final BoundsCompatAlignmentController sController = new BoundsCompatAlignmentController();
    }

    public static BoundsCompatAlignmentController getController() {
        return LazyHolder.sController;
    }

    public static void setAtmService(ActivityTaskManagerService activityTaskManagerService) {
        getController().mAtmService = activityTaskManagerService;
    }

    public static void setAlignmentLocked(int i) {
        BoundsCompatAlignmentController controller = getController();
        if (i == controller.mGlobalAlignment.getAlignment()) {
            return;
        }
        controller.mGlobalAlignment.setAlignment(i);
        scheduleRecomputeConfigurationLocked();
    }

    public static void scheduleRecomputeConfigurationLocked() {
        BoundsCompatAlignmentController controller = getController();
        ActivityTaskManagerService activityTaskManagerService = controller.mAtmService;
        if (activityTaskManagerService == null || activityTaskManagerService.f1734mH.hasCallbacks(controller.mRecomputeConfiguration)) {
            return;
        }
        controller.mAtmService.f1734mH.post(controller.mRecomputeConfiguration);
    }

    public static int getAlignmentLocked() {
        return getGlobalBoundsCompatAlignmentLocked().getAlignment();
    }

    public static BoundsCompatAlignment getGlobalBoundsCompatAlignmentLocked() {
        return getController().mGlobalAlignment;
    }

    public static void dumpLocked(PrintWriter printWriter, String str) {
        BoundsCompatAlignmentController controller = getController();
        printWriter.print(str);
        printWriter.print(TAG);
        printWriter.print(":[ mLast");
        printWriter.print(controller.mGlobalAlignment);
        printWriter.print("]");
        printWriter.print(", FeatureEnabled=");
        printWriter.print(CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_CONTROL);
        printWriter.print(", mAnimationEnabled=");
        printWriter.print(controller.mAnimationEnabled);
        printWriter.println();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0042 A[Catch: all -> 0x004f, TRY_LEAVE, TryCatch #0 {all -> 0x004f, blocks: (B:6:0x0006, B:15:0x0032, B:18:0x0042, B:20:0x0016, B:23:0x0020), top: B:5:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean executeShellCommandLocked(String str, String[] strArr, PrintWriter printWriter) {
        int hashCode;
        char c;
        if (!CoreRune.SAFE_DEBUG) {
            return false;
        }
        try {
            hashCode = str.hashCode();
        } catch (Throwable th) {
            printWriter.println("Exception=" + th.toString());
        }
        if (hashCode != -502148531) {
            if (hashCode == 37703415 && str.equals("-setBoundsCompatAlignment")) {
                c = 0;
                if (c != 0) {
                    setAlignmentLocked(Integer.parseInt(strArr[0]));
                    dumpLocked(printWriter, "");
                    return true;
                }
                if (c == 1) {
                    getController().mAnimationEnabled = Boolean.parseBoolean(strArr[0]);
                    dumpLocked(printWriter, "");
                    return true;
                }
                return false;
            }
            c = 65535;
            if (c != 0) {
            }
        } else {
            if (str.equals("-setBoundsCompatAlignmentAnimation")) {
                c = 1;
                if (c != 0) {
                }
            }
            c = 65535;
            if (c != 0) {
            }
        }
    }

    public static boolean shouldPlayMoveAnimation(WindowState windowState) {
        ActivityRecord activityRecord;
        return getController().mAnimationEnabled && (activityRecord = windowState.mActivityRecord) != null && activityRecord.mCompatRecord.mShouldPlayMoveAnimation;
    }

    public BoundsCompatAlignmentController() {
        this.mGlobalAlignment = BoundsCompatUtils.get().getDefaultDisplayAlignment();
        this.mRecomputeConfiguration = new Runnable() { // from class: com.android.server.wm.BoundsCompatAlignmentController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BoundsCompatAlignmentController.this.recomputeConfiguration();
            }
        };
        this.mAnimationEnabled = CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION;
    }

    public final boolean supportsDisplay(DisplayContent displayContent) {
        return displayContent.isDefaultDisplay;
    }

    public final void recomputeConfiguration() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                final ArrayList<ActivityRecord> arrayList = new ArrayList();
                final Rect rect = new Rect();
                this.mAtmService.mRootWindowContainer.forAllDisplays(new Consumer() { // from class: com.android.server.wm.BoundsCompatAlignmentController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        BoundsCompatAlignmentController.this.lambda$recomputeConfiguration$1(rect, arrayList, (DisplayContent) obj);
                    }
                });
                if (arrayList.isEmpty()) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mAtmService.mWindowManager.mWindowPlacerLocked.performSurfacePlacement();
                if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION) {
                    for (ActivityRecord activityRecord : arrayList) {
                        activityRecord.mCompatRecord.mShouldPlayMoveAnimation = false;
                        Task task = activityRecord.getTask();
                        if (task != null) {
                            task.dispatchTaskInfoChangedIfNeeded(false);
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$recomputeConfiguration$1(final Rect rect, final List list, DisplayContent displayContent) {
        if (supportsDisplay(displayContent)) {
            displayContent.forAllActivities(new Consumer() { // from class: com.android.server.wm.BoundsCompatAlignmentController$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BoundsCompatAlignmentController.lambda$recomputeConfiguration$0(rect, list, (ActivityRecord) obj);
                }
            }, false);
        }
    }

    public static /* synthetic */ void lambda$recomputeConfiguration$0(Rect rect, List list, ActivityRecord activityRecord) {
        if (activityRecord.mCompatRecord.isCompatModeEnabled()) {
            Rect bounds = activityRecord.getConfiguration().windowConfiguration.getBounds();
            rect.set(bounds);
            activityRecord.recomputeConfiguration();
            if (rect.equals(bounds)) {
                return;
            }
            if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION) {
                activityRecord.mCompatRecord.mShouldPlayMoveAnimation = true;
            }
            list.add(activityRecord);
        }
    }
}
