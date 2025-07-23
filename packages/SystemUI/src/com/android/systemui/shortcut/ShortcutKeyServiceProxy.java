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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ShortcutKeyServiceProxy extends IShortcutService.Stub {
    public final Callbacks mCallbacks;
    public final Object mLock = new Object();
    public final H mHandler = new H(this, 0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callbacks {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        int i2 = ShortcutKeyDispatcher.$r8$clinit;
                        splitScreenController.getClass();
                        splitScreenController.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda11
                            @Override // java.lang.Runnable
                            public final void run() {
                                List tasks;
                                SplitScreenController splitScreenController2 = SplitScreenController.this;
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
                                DividerSnapAlgorithm.SnapTarget calculateNonDismissingSnapTarget = dividerSnapAlgorithm.calculateNonDismissingSnapTarget(i3);
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
                                DividerSnapAlgorithm.SnapTarget snapTarget = calculateNonDismissingSnapTarget;
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
