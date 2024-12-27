package com.android.systemui.wmshell;

import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.wmshell.BubblesManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubblesManager.AnonymousClass5 f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda2(BubblesManager.AnonymousClass5 anonymousClass5, SysUiState sysUiState, boolean z) {
        this.$r8$classId = 0;
        this.f$0 = anonymousClass5;
        this.f$1 = sysUiState;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubblesManager.AnonymousClass5 anonymousClass5 = this.f$0;
                SysUiState sysUiState = (SysUiState) this.f$1;
                boolean z = this.f$2;
                anonymousClass5.getClass();
                sysUiState.setFlag(16384L, z);
                BubblesManager bubblesManager = BubblesManager.this;
                sysUiState.commitUpdate(bubblesManager.mContext.getDisplayId());
                if (!z) {
                    sysUiState.setFlag(8388608L, false);
                    sysUiState.commitUpdate(bubblesManager.mContext.getDisplayId());
                    break;
                }
                break;
            default:
                BubblesManager.AnonymousClass5 anonymousClass52 = this.f$0;
                boolean z2 = this.f$2;
                ((NotificationShadeWindowControllerImpl) BubblesManager.this.mNotificationShadeWindowController).setRequestTopUi((String) this.f$1, z2);
                break;
        }
    }

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda2(BubblesManager.AnonymousClass5 anonymousClass5, boolean z) {
        this.$r8$classId = 1;
        this.f$0 = anonymousClass5;
        this.f$2 = z;
        this.f$1 = "Bubbles";
    }
}
