package com.android.server.pm;

import android.os.Bundle;
import android.os.IUserRestrictionsListener;
import android.os.RemoteException;
import android.util.Slog;

public final /* synthetic */ class UserManagerService$$ExternalSyntheticLambda1
        implements ResilientAtomicFile.ReadEventLogger,
                UserManagerInternal.UserRestrictionsListener {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UserManagerService$$ExternalSyntheticLambda1(
            IUserRestrictionsListener iUserRestrictionsListener) {
        this.f$0 = iUserRestrictionsListener;
    }

    public /* synthetic */ UserManagerService$$ExternalSyntheticLambda1(
            UserManagerService userManagerService) {
        this.f$0 = userManagerService;
    }

    @Override // com.android.server.pm.ResilientAtomicFile.ReadEventLogger
    public void logEvent(int i, String str) {
        UserManagerService userManagerService = (UserManagerService) this.f$0;
        userManagerService.getClass();
        Slog.e("UserManagerService", str);
        if (userManagerService.mHandler.hasMessages(2)) {
            return;
        }
        userManagerService.mHandler.sendMessageDelayed(
                userManagerService.mHandler.obtainMessage(2), 2000L);
    }

    @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
    public void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) {
        try {
            ((IUserRestrictionsListener) this.f$0).onUserRestrictionsChanged(i, bundle, bundle2);
        } catch (RemoteException e) {
            Slog.e("IUserRestrictionsListener", "Unable to invoke listener: " + e.getMessage());
        }
    }
}
