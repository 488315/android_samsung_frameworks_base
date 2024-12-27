package com.android.server.wm;

import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.displayhash.IDisplayHashingService;
import android.util.Slog;

import java.util.function.BiConsumer;

public final /* synthetic */ class DisplayHashController$$ExternalSyntheticLambda1
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        IDisplayHashingService iDisplayHashingService = (IDisplayHashingService) obj;
        RemoteCallback remoteCallback = (RemoteCallback) obj2;
        switch (this.$r8$classId) {
            case 0:
                try {
                    iDisplayHashingService.getIntervalBetweenRequestsMillis(remoteCallback);
                    break;
                } catch (RemoteException e) {
                    Slog.e("WindowManager", "Failed to invoke getDisplayHashAlgorithms command", e);
                    return;
                }
            default:
                try {
                    iDisplayHashingService.getDisplayHashAlgorithms(remoteCallback);
                    break;
                } catch (RemoteException e2) {
                    Slog.e(
                            "WindowManager",
                            "Failed to invoke getDisplayHashAlgorithms command",
                            e2);
                }
        }
    }
}
