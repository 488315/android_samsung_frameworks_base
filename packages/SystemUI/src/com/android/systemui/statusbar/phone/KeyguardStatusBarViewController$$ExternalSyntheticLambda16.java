package com.android.systemui.statusbar.phone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardStatusBarViewController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda16(KeyguardStatusBarViewController keyguardStatusBarViewController, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardStatusBarViewController;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardStatusBarViewController.$r8$lambda$PyURNFylWesnFtBP3URsrY4f6G0(this.f$0, this.f$1);
                break;
            default:
                ((KeyguardStatusBarView) this.f$0.mView).mIsUserSwitcherEnabled = this.f$1;
                break;
        }
    }
}
