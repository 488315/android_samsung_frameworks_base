package com.android.systemui.user;

import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CreateUserActivity$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CreateUserActivity f$0;

    public /* synthetic */ CreateUserActivity$$ExternalSyntheticLambda1(CreateUserActivity createUserActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = createUserActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.finish();
                break;
            default:
                CreateUserActivity createUserActivity = this.f$0;
                int i = CreateUserActivity.$r8$clinit;
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
