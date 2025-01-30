package com.android.systemui.wmshell;

import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.wmshell.BubblesManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubblesManager$4$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubblesManager.C37394 f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ BubblesManager$4$$ExternalSyntheticLambda1(BubblesManager.C37394 c37394, SysUiState sysUiState, boolean z) {
        this.$r8$classId = 1;
        this.f$0 = c37394;
        this.f$2 = sysUiState;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubblesManager.C37394 c37394 = this.f$0;
                boolean z = this.f$1;
                ((NotificationShadeWindowControllerImpl) BubblesManager.this.mNotificationShadeWindowController).setRequestTopUi((String) this.f$2, z);
                break;
            default:
                BubblesManager.C37394 c373942 = this.f$0;
                SysUiState sysUiState = (SysUiState) this.f$2;
                boolean z2 = this.f$1;
                c373942.getClass();
                sysUiState.setFlag(16384L, z2);
                BubblesManager bubblesManager = BubblesManager.this;
                sysUiState.commitUpdate(bubblesManager.mContext.getDisplayId());
                if (!z2) {
                    sysUiState.setFlag(8388608L, false);
                    sysUiState.commitUpdate(bubblesManager.mContext.getDisplayId());
                    break;
                }
                break;
        }
    }

    public /* synthetic */ BubblesManager$4$$ExternalSyntheticLambda1(BubblesManager.C37394 c37394, boolean z) {
        this.$r8$classId = 0;
        this.f$0 = c37394;
        this.f$1 = z;
        this.f$2 = "Bubbles";
    }
}
