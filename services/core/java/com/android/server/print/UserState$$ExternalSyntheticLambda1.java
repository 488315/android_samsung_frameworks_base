package com.android.server.print;

import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.RemoteException;
import android.print.PrinterId;
import android.util.Log;

import com.android.internal.util.function.pooled.PooledLambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public final /* synthetic */ class UserState$$ExternalSyntheticLambda1 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ UserState$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((UserState) obj).failScheduledPrintJobsForServiceInternal((ComponentName) obj2);
                return;
            case 1:
                UserState userState = (UserState) obj;
                List list = (List) obj2;
                synchronized (userState.mLock) {
                    try {
                        if (userState.mPrintServiceRecommendationsChangeListenerRecords == null) {
                            return;
                        }
                        ArrayList arrayList =
                                new ArrayList(
                                        userState
                                                .mPrintServiceRecommendationsChangeListenerRecords);
                        userState.mPrintServiceRecommendations = list;
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            try {
                                ((UserState.AnonymousClass3) arrayList.get(i))
                                        .listener.onRecommendationsChanged();
                            } catch (RemoteException e) {
                                Log.e(
                                        "UserState",
                                        "Error notifying for print service recommendations change",
                                        e);
                            }
                        }
                        return;
                    } finally {
                    }
                }
            case 2:
                ArrayList arrayList2 = (ArrayList) obj2;
                ((UserState.AnonymousClass1) obj).getClass();
                int size2 = arrayList2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    RemotePrintService remotePrintService = (RemotePrintService) arrayList2.get(i2);
                    remotePrintService.getClass();
                    Handler.getMain()
                            .sendMessage(
                                    PooledLambda.obtainMessage(
                                            new RemotePrintService$$ExternalSyntheticLambda0(5),
                                            remotePrintService));
                }
                return;
            case 3:
                ((UserState.AnonymousClass1) obj).handleDispatchPrintersAdded((ArrayList) obj2);
                return;
            case 4:
                ((UserState.AnonymousClass1) obj).handleDispatchPrintersAdded((List) obj2);
                return;
            case 5:
                RemotePrintService remotePrintService2 = (RemotePrintService) obj;
                remotePrintService2.getClass();
                Handler.getMain()
                        .sendMessage(
                                PooledLambda.obtainMessage(
                                        new RemotePrintService$$ExternalSyntheticLambda1(4),
                                        remotePrintService2,
                                        (List) obj2));
                return;
            case 6:
                RemotePrintService remotePrintService3 = (RemotePrintService) obj;
                remotePrintService3.getClass();
                Handler.getMain()
                        .sendMessage(
                                PooledLambda.obtainMessage(
                                        new RemotePrintService$$ExternalSyntheticLambda1(6),
                                        remotePrintService3,
                                        (PrinterId) obj2));
                return;
            case 7:
                UserState.AnonymousClass1 anonymousClass1 = (UserState.AnonymousClass1) obj;
                ArrayList arrayList3 = (ArrayList) obj2;
                anonymousClass1.getClass();
                int size3 = arrayList3.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    RemotePrintService remotePrintService4 =
                            (RemotePrintService) arrayList3.get(i3);
                    remotePrintService4.getClass();
                    Handler.getMain()
                            .sendMessage(
                                    PooledLambda.obtainMessage(
                                            new RemotePrintService$$ExternalSyntheticLambda0(1),
                                            remotePrintService4));
                }
                UserState.this.mPrinterDiscoverySession = null;
                return;
            case 8:
                ArrayList arrayList4 = (ArrayList) obj2;
                ((UserState.AnonymousClass1) obj).getClass();
                int size4 = arrayList4.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    RemotePrintService remotePrintService5 =
                            (RemotePrintService) arrayList4.get(i4);
                    remotePrintService5.getClass();
                    Handler.getMain()
                            .sendMessage(
                                    PooledLambda.obtainMessage(
                                            new RemotePrintService$$ExternalSyntheticLambda0(4),
                                            remotePrintService5));
                }
                return;
            default:
                UserState.AnonymousClass1 anonymousClass12 = (UserState.AnonymousClass1) obj;
                List list2 = (List) obj2;
                int beginBroadcast = anonymousClass12.mDiscoveryObservers.beginBroadcast();
                int i5 = 0;
                while (true) {
                    UserState$PrinterDiscoverySessionMediator$1
                            userState$PrinterDiscoverySessionMediator$1 =
                                    anonymousClass12.mDiscoveryObservers;
                    if (i5 >= beginBroadcast) {
                        userState$PrinterDiscoverySessionMediator$1.finishBroadcast();
                        return;
                    }
                    try {
                        userState$PrinterDiscoverySessionMediator$1
                                .getBroadcastItem(i5)
                                .onPrintersRemoved(new ParceledListSlice(list2));
                    } catch (RemoteException e2) {
                        Log.e("UserState", "Error sending removed printers", e2);
                    }
                    i5++;
                }
        }
    }
}
