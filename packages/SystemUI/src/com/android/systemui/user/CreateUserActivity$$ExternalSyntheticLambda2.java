package com.android.systemui.user;

import android.util.Log;

public final /* synthetic */ class CreateUserActivity$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CreateUserActivity f$0;

    public /* synthetic */ CreateUserActivity$$ExternalSyntheticLambda2(CreateUserActivity createUserActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = createUserActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        CreateUserActivity createUserActivity = this.f$0;
        switch (i) {
            case 0:
                createUserActivity.finish();
                break;
            default:
                int i2 = CreateUserActivity.$r8$clinit;
                createUserActivity.getClass();
                Log.e("CreateUserActivity", "Unable to create user");
                if (!createUserActivity.isFinishing() && !createUserActivity.isDestroyed()) {
                    createUserActivity.finish();
                    break;
                }
                break;
        }
    }
}
