package com.android.systemui.shortcut;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.android.internal.policy.IShortcutService;
import com.android.systemui.R;
import com.android.wm.shell.common.split.DividerSnapAlgorithm;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ShortcutKeyServiceProxy extends IShortcutService.Stub {
    public final Callbacks mCallbacks;
    public final Object mLock = new Object();
    public final H mHandler = new H(this, 0);

    public interface Callbacks {
    }

    public final class H extends Handler {
        public /* synthetic */ H(ShortcutKeyServiceProxy shortcutKeyServiceProxy, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            Callbacks callbacks = ShortcutKeyServiceProxy.this.mCallbacks;
            final long jLongValue = ((Long) message.obj).longValue();
            ShortcutKeyDispatcher shortcutKeyDispatcher = (ShortcutKeyDispatcher) callbacks;
            int i = shortcutKeyDispatcher.mContext.getResources().getConfiguration().orientation;
            if ((jLongValue == 281474976710727L || jLongValue == 281474976710728L) && i == 2) {
                shortcutKeyDispatcher.mSplitScreenOptional.ifPresent(new Consumer() { // from class: com.android.systemui.shortcut.ShortcutKeyDispatcher$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        final long j = jLongValue;
                        final SplitScreenController splitScreenController = (SplitScreenController) obj;
                        int i2 = ShortcutKeyDispatcher.$r8$clinit;
                        splitScreenController.getClass();
                        splitScreenController.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda11
                            @Override // java.lang.Runnable
                            public final void run() {
                                List tasks;
                                SplitScreenController splitScreenController2 = splitScreenController;
                                long j2 = j;
                                if (!splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                    DesktopStateImpl.Companion.getClass();
                                    if (DesktopStateImpl.Companion.inDesktopWindowing(0) || (tasks = ActivityTaskManager.getInstance().getTasks(1)) == null || tasks.isEmpty()) {
                                        return;
                                    }
                                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) tasks.get(0);
                                    if (runningTaskInfo.displayId != 0) {
                                        return;
                                    }
                                    if (runningTaskInfo.supportsMultiWindow) {
                                        splitScreenController2.toggleSplitScreen(1);
                                        return;
                                    } else {
                                        Toast.makeText(splitScreenController2.mContext, R.string.dock_non_resizeble_failed_to_dock_text, 0).show();
                                        return;
                                    }
                                }
                                StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                DividerSnapAlgorithm dividerSnapAlgorithm = stageCoordinator.mSplitLayout.getDividerSnapAlgorithm();
                                int i3 = stageCoordinator.mSplitLayout.mDividerPosition;
                                DividerSnapAlgorithm.SnapTarget snapTargetCalculateNonDismissingSnapTarget = dividerSnapAlgorithm.calculateNonDismissingSnapTarget(i3);
                                if (j2 == 281474976710727L) {
                                    int iIndexOf = dividerSnapAlgorithm.mTargets.indexOf(snapTargetCalculateNonDismissingSnapTarget);
                                    if (iIndexOf != -1 && iIndexOf > 0) {
                                        snapTargetCalculateNonDismissingSnapTarget = (DividerSnapAlgorithm.SnapTarget) dividerSnapAlgorithm.mTargets.get(iIndexOf - 1);
                                    }
                                } else {
                                    int iIndexOf2 = dividerSnapAlgorithm.mTargets.indexOf(snapTargetCalculateNonDismissingSnapTarget);
                                    if (iIndexOf2 != -1 && iIndexOf2 < dividerSnapAlgorithm.mTargets.size() - 1) {
                                        snapTargetCalculateNonDismissingSnapTarget = (DividerSnapAlgorithm.SnapTarget) dividerSnapAlgorithm.mTargets.get(iIndexOf2 + 1);
                                    }
                                }
                                DividerSnapAlgorithm.SnapTarget snapTarget = snapTargetCalculateNonDismissingSnapTarget;
                                SplitLayout splitLayout = stageCoordinator.mSplitLayout;
                                splitLayout.getClass();
                                splitLayout.snapToTarget(i3, snapTarget, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, false);
                            }
                        });
                    }
                });
            }
        }

        private H() {
        }
    }

    public ShortcutKeyServiceProxy(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public final void notifyShortcutKeyPressed(long j) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1, Long.valueOf(j)).sendToTarget();
        }
    }
}
