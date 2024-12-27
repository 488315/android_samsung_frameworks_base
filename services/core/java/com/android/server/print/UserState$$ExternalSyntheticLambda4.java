package com.android.server.print;

import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.RemoteException;
import android.print.IPrinterDiscoveryObserver;
import android.print.PrintJobId;
import android.print.PrinterId;
import android.util.Log;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledSupplier;
import com.android.server.print.UserState;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserState$$ExternalSyntheticLambda4 implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ UserState$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        switch (this.$r8$classId) {
            case 0:
                UserState userState = (UserState) obj;
                PrintJobId printJobId = (PrintJobId) obj2;
                userState.getClass();
                int asInt = ((PooledSupplier.OfInt) obj3).getAsInt();
                synchronized (userState.mLock) {
                    try {
                        if (userState.mPrintJobStateChangeListenerRecords == null) {
                            return;
                        }
                        ArrayList arrayList = new ArrayList(userState.mPrintJobStateChangeListenerRecords);
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            UserState.AnonymousClass2 anonymousClass2 = (UserState.AnonymousClass2) arrayList.get(i);
                            int i2 = anonymousClass2.appId;
                            if (i2 == -2 || i2 == asInt) {
                                try {
                                    anonymousClass2.listener.onPrintJobStateChanged(printJobId);
                                } catch (RemoteException e) {
                                    Log.e("UserState", "Error notifying for print job state change", e);
                                }
                            }
                        }
                        return;
                    } finally {
                    }
                }
            case 1:
                RemotePrintService remotePrintService = (RemotePrintService) obj2;
                ((UserState.AnonymousClass1) obj).getClass();
                remotePrintService.getClass();
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda1(1), remotePrintService, (List) obj3));
                return;
            case 2:
                RemotePrintService remotePrintService2 = (RemotePrintService) obj2;
                ((UserState.AnonymousClass1) obj).getClass();
                remotePrintService2.getClass();
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda1(5), remotePrintService2, (PrinterId) obj3));
                return;
            case 3:
                ArrayList arrayList2 = (ArrayList) obj2;
                List list = (List) obj3;
                ((UserState.AnonymousClass1) obj).getClass();
                int size2 = arrayList2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    RemotePrintService remotePrintService3 = (RemotePrintService) arrayList2.get(i3);
                    remotePrintService3.getClass();
                    Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda1(4), remotePrintService3, list));
                }
                return;
            case 4:
                IPrinterDiscoveryObserver iPrinterDiscoveryObserver = (IPrinterDiscoveryObserver) obj2;
                ArrayList arrayList3 = (ArrayList) obj3;
                ((UserState.AnonymousClass1) obj).getClass();
                try {
                    iPrinterDiscoveryObserver.onPrintersAdded(new ParceledListSlice(arrayList3));
                    return;
                } catch (RemoteException e2) {
                    Log.e("UserState", "Error sending added printers", e2);
                    return;
                }
            default:
                RemotePrintService remotePrintService4 = (RemotePrintService) obj2;
                ((UserState.AnonymousClass1) obj).getClass();
                remotePrintService4.getClass();
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda1(6), remotePrintService4, (PrinterId) obj3));
                return;
        }
    }
}
