package com.android.server.pm;

import android.content.Intent;
import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.android.internal.util.ArrayUtils;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageMonitorCallbackHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ PackageMonitorCallbackHelper f$0;
    public final /* synthetic */ RemoteCallbackList f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int[] f$3;
    public final /* synthetic */ Intent f$4;
    public final /* synthetic */ BiFunction f$5;

    public /* synthetic */ PackageMonitorCallbackHelper$$ExternalSyntheticLambda0(
            PackageMonitorCallbackHelper packageMonitorCallbackHelper,
            RemoteCallbackList remoteCallbackList,
            int i,
            int[] iArr,
            Intent intent,
            BroadcastHelper$$ExternalSyntheticLambda6 broadcastHelper$$ExternalSyntheticLambda6) {
        this.f$0 = packageMonitorCallbackHelper;
        this.f$1 = remoteCallbackList;
        this.f$2 = i;
        this.f$3 = iArr;
        this.f$4 = intent;
        this.f$5 = broadcastHelper$$ExternalSyntheticLambda6;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final PackageMonitorCallbackHelper packageMonitorCallbackHelper = this.f$0;
        RemoteCallbackList remoteCallbackList = this.f$1;
        final int i = this.f$2;
        final int[] iArr = this.f$3;
        final Intent intent = this.f$4;
        BiFunction biFunction = this.f$5;
        packageMonitorCallbackHelper.getClass();
        final BroadcastHelper$$ExternalSyntheticLambda6 broadcastHelper$$ExternalSyntheticLambda6 =
                (BroadcastHelper$$ExternalSyntheticLambda6) biFunction;
        remoteCallbackList.broadcast(
                new BiConsumer() { // from class:
                    // com.android.server.pm.PackageMonitorCallbackHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        Bundle extras;
                        PackageMonitorCallbackHelper packageMonitorCallbackHelper2 =
                                PackageMonitorCallbackHelper.this;
                        int i2 = i;
                        int[] iArr2 = iArr;
                        Intent intent2 = intent;
                        BiFunction biFunction2 = broadcastHelper$$ExternalSyntheticLambda6;
                        IRemoteCallback iRemoteCallback = (IRemoteCallback) obj;
                        packageMonitorCallbackHelper2.getClass();
                        PackageMonitorCallbackHelper.RegisterUser registerUser =
                                (PackageMonitorCallbackHelper.RegisterUser) obj2;
                        int i3 = registerUser.mUserId;
                        if (i3 == -1 || i3 == i2) {
                            int i4 = registerUser.mUid;
                            if (iArr2 == null || i4 == 1000 || ArrayUtils.contains(iArr2, i4)) {
                                if (biFunction2 != null && (extras = intent2.getExtras()) != null) {
                                    Bundle bundle =
                                            (Bundle) biFunction2.apply(Integer.valueOf(i4), extras);
                                    if (bundle == null) {
                                        return;
                                    }
                                    Intent intent3 = new Intent(intent2);
                                    intent3.replaceExtras(bundle);
                                    intent2 = intent3;
                                }
                                try {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable(
                                            "android.content.pm.extra.EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT",
                                            intent2);
                                    iRemoteCallback.sendResult(bundle2);
                                } catch (RemoteException unused) {
                                }
                            }
                        }
                    }
                });
    }
}
