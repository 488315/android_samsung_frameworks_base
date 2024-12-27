package com.android.systemui.shortcut;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.android.internal.policy.IShortcutService;
import com.android.systemui.R;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.split.DividerSnapAlgorithm;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShortcutKeyServiceProxy extends IShortcutService.Stub {
    public final Callbacks mCallbacks;
    public final Object mLock = new Object();
    public final H mHandler = new H(this, 0);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callbacks {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            final long longValue = ((Long) message.obj).longValue();
            ShortcutKeyDispatcher shortcutKeyDispatcher = (ShortcutKeyDispatcher) callbacks;
            int i = shortcutKeyDispatcher.mContext.getResources().getConfiguration().orientation;
            if ((longValue == 281474976710727L || longValue == 281474976710728L) && i == 2) {
                shortcutKeyDispatcher.mSplitScreenOptional.ifPresent(new Consumer() { // from class: com.android.systemui.shortcut.ShortcutKeyDispatcher$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        final long j = longValue;
                        final SplitScreenController splitScreenController = (SplitScreenController) obj;
                        splitScreenController.getClass();
                        ((HandlerExecutor) splitScreenController.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                List tasks;
                                SplitScreenController splitScreenController2 = SplitScreenController.this;
                                long j2 = j;
                                if (!splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                    if (splitScreenController2.mContext.getResources().getConfiguration().isDexMode() || (tasks = ActivityTaskManager.getInstance().getTasks(1)) == null || tasks.isEmpty()) {
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
                                int i2 = stageCoordinator.mSplitLayout.mDividerPosition;
                                DividerSnapAlgorithm.SnapTarget calculateNonDismissingSnapTarget = dividerSnapAlgorithm.calculateNonDismissingSnapTarget(i2);
                                if (j2 == 281474976710727L) {
                                    int indexOf = dividerSnapAlgorithm.mTargets.indexOf(calculateNonDismissingSnapTarget);
                                    if (indexOf != -1 && indexOf > 0) {
                                        calculateNonDismissingSnapTarget = (DividerSnapAlgorithm.SnapTarget) dividerSnapAlgorithm.mTargets.get(indexOf - 1);
                                    }
                                } else {
                                    int indexOf2 = dividerSnapAlgorithm.mTargets.indexOf(calculateNonDismissingSnapTarget);
                                    if (indexOf2 != -1 && indexOf2 < dividerSnapAlgorithm.mTargets.size() - 1) {
                                        calculateNonDismissingSnapTarget = (DividerSnapAlgorithm.SnapTarget) dividerSnapAlgorithm.mTargets.get(indexOf2 + 1);
                                    }
                                }
                                stageCoordinator.mSplitLayout.snapToTarget(i2, calculateNonDismissingSnapTarget);
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
