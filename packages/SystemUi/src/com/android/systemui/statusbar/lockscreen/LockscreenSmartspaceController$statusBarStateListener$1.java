package com.android.systemui.statusbar.lockscreen;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.concurrency.ExecutionImpl;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LockscreenSmartspaceController$statusBarStateListener$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$statusBarStateListener$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozeAmountChanged(float f, float f2) {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
        ((ExecutionImpl) lockscreenSmartspaceController.execution).assertIsMainThread();
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setDozeAmount(f2);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
        ((ExecutionImpl) lockscreenSmartspaceController.execution).assertIsMainThread();
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setDozing(z);
        }
    }
}
