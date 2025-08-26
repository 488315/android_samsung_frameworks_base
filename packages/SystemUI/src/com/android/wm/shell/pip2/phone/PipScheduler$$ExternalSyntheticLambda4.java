package com.android.wm.shell.pip2.phone;

import android.app.WindowConfiguration;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipScheduler$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ PipScheduler f$0;

    public /* synthetic */ PipScheduler$$ExternalSyntheticLambda4(PipScheduler pipScheduler) {
        this.f$0 = pipScheduler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WindowContainerTransaction windowContainerTransaction;
        Configuration configuration;
        WindowConfiguration windowConfiguration;
        final PipScheduler pipScheduler = this.f$0;
        PipTransitionState pipTransitionState = pipScheduler.mPipTransitionState;
        if (pipTransitionState.isInPip()) {
            WindowContainerToken pipTaskToken = pipTransitionState.getPipTaskToken();
            if (pipTaskToken == null) {
                windowContainerTransaction = null;
            } else {
                windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(pipTaskToken, (Rect) null);
                PipDesktopState pipDesktopState = pipScheduler.mPipDesktopState;
                int i = 0;
                if (pipDesktopState.isPipInDesktopMode()) {
                    DisplayAreaInfo displayAreaInfo = pipDesktopState.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(pipDesktopState.pipDisplayLayoutState.mDisplayId);
                    if (displayAreaInfo == null || (configuration = displayAreaInfo.configuration) == null || (windowConfiguration = configuration.windowConfiguration) == null || windowConfiguration.getWindowingMode() != 5) {
                        i = 5;
                    }
                }
                windowContainerTransaction.setWindowingMode(pipTaskToken, i);
            }
            if (windowContainerTransaction == null) {
                return;
            }
            final WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            pipScheduler.mSplitScreenControllerOptional.ifPresent(new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipScheduler$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PipScheduler pipScheduler2 = pipScheduler;
                    WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction2;
                    SplitScreenController splitScreenController = (SplitScreenController) obj;
                    if (splitScreenController.isTaskInSplitScreen$1(pipScheduler2.mPipTransitionState.mPipTaskInfo.lastParentTaskIdBeforePip)) {
                        splitScreenController.prepareEnterSplitScreen(windowContainerTransaction3);
                    }
                }
            });
            boolean zIsEmpty = windowContainerTransaction2.isEmpty();
            windowContainerTransaction2.merge(windowContainerTransaction, true);
            PipTransition pipTransition = pipScheduler.mPipTransitionController;
            pipTransition.mPipTransitionState.setState(7, null);
            pipTransition.mExitViaExpandTransition = pipTransition.mTransitions.startTransition(!zIsEmpty ? 1002 : 1001, windowContainerTransaction2, pipTransition);
        }
    }
}
