package com.android.server.wm;

import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class AppTransition$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        IRemoteCallback iRemoteCallback = (IRemoteCallback) obj;
        switch (this.$r8$classId) {
            case 0:
                try {
                    iRemoteCallback.sendResult((Bundle) null);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            default:
                ArrayList arrayList = AppTransition.sFlagToString;
                try {
                    iRemoteCallback.sendResult((Bundle) null);
                    break;
                } catch (RemoteException unused2) {
                    return;
                }
        }
    }
}
