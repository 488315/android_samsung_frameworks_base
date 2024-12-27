package com.android.server.wm;

import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
