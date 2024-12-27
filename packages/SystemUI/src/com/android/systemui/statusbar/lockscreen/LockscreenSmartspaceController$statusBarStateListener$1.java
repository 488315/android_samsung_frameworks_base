package com.android.systemui.statusbar.lockscreen;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenSmartspaceController$statusBarStateListener$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$statusBarStateListener$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozeAmountChanged(float f, float f2) {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
        lockscreenSmartspaceController.execution.assertIsMainThread();
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setDozeAmount(f2);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
        lockscreenSmartspaceController.execution.assertIsMainThread();
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setDozing(z);
        }
    }
}
