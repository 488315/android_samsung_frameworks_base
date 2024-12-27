package com.android.server.locksettings;

import android.app.ActivityManagerNative;
import android.os.RemoteException;
import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.knox.dar.DarManagerService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LockSettingsService$Lifecycle$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LockSettingsService$Lifecycle$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DarManagerService darManagerService = (DarManagerService) obj;
                darManagerService.getClass();
                try {
                    ActivityManagerNative.getDefault().registerUserSwitchObserver(darManagerService.mUserSwitchObserver, "DarManagerService");
                    return;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            case 1:
                ((DarManagerService) obj).getClass();
                return;
            default:
                DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                throw null;
        }
    }
}
