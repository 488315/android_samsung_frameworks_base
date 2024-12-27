package com.android.server.power;

import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;

import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ScreenCurtainController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenCurtainController f$0;

    public /* synthetic */ ScreenCurtainController$$ExternalSyntheticLambda0(
            ScreenCurtainController screenCurtainController, int i) {
        this.$r8$classId = i;
        this.f$0 = screenCurtainController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ScreenCurtainController screenCurtainController = this.f$0;
        screenCurtainController.getClass();
        switch (i) {
            case 0:
                screenCurtainController.mContext.sendBroadcastAsUser(
                        new Intent("android.intent.action.ACTION_DISPLAY_ASSISTANT_READY"),
                        UserHandle.CURRENT);
                break;
            default:
                SemWindowManager.getInstance()
                        .registerFoldStateListener(
                                screenCurtainController.mFoldStateListener, (Handler) null);
                break;
        }
    }
}
