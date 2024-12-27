package com.android.server.knox;

import android.content.Context;
import android.widget.Toast;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SeamLessSwitchHandler$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ SeamLessSwitchHandler f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SeamLessSwitchHandler$$ExternalSyntheticLambda0(
            SeamLessSwitchHandler seamLessSwitchHandler, int i) {
        this.f$0 = seamLessSwitchHandler;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SeamLessSwitchHandler seamLessSwitchHandler = this.f$0;
        int i = this.f$1;
        seamLessSwitchHandler.getClass();
        if (!SemPersonaManager.isSecureFolderId(i)
                || seamLessSwitchHandler.mPersonaManagerInternal.shouldConfirmCredentials(i)) {
            return;
        }
        Context context = seamLessSwitchHandler.c;
        Toast.makeText(
                        context,
                        String.format(
                                context.getText(17043007).toString(),
                                seamLessSwitchHandler.personaManagerService.getContainerName(i)),
                        0)
                .show();
    }
}
