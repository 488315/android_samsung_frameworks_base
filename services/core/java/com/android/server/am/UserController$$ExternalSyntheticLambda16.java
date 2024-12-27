package com.android.server.am;


public final /* synthetic */ class UserController$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ UserController$$ExternalSyntheticLambda16(int i) {
        this.$r8$classId = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                throw new RuntimeException("Keyguard is not shown in 20000 ms.");
            default:
                boolean z = MARsPolicyManager.MARs_ENABLE;
                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                return;
        }
    }
}
