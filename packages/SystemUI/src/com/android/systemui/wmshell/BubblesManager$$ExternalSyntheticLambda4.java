package com.android.systemui.wmshell;

import android.os.RemoteException;
import android.util.Log;
import com.android.internal.protolog.common.ProtoLog;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda10;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ BubblesManager f$0;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda4(BubblesManager bubblesManager) {
        this.f$0 = bubblesManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        BubblesManager bubblesManager = this.f$0;
        bubblesManager.mKeyguardShowing = ((KeyguardStateControllerImpl) bubblesManager.mKeyguardStateController).mShowing;
        boolean z2 = false;
        try {
            z = bubblesManager.mDreamManager.isDreamingOrInPreview();
        } catch (RemoteException e) {
            Log.e("Bubbles", "Failed to query dream manager.", e);
            z = false;
        }
        bubblesManager.mDreamingOrInPreview = z;
        if (!bubblesManager.mKeyguardShowing && !z) {
            z2 = true;
        }
        ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, "handleKeyguardOrDreamChange isUnlockedShade=%b keyguardShowing=%b dreamingOrInPreview=%b", new Object[]{Boolean.valueOf(z2), Boolean.valueOf(bubblesManager.mKeyguardShowing), Boolean.valueOf(bubblesManager.mDreamingOrInPreview)});
        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl, z2, 3));
    }
}
