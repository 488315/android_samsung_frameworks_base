package com.android.server.print;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemotePrintService$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        RemotePrintService remotePrintService = (RemotePrintService) obj;
        switch (this.$r8$classId) {
            case 0:
                remotePrintService.handleBinderDied();
                break;
            case 1:
                remotePrintService.handleDestroyPrinterDiscoverySession();
                break;
            case 2:
                remotePrintService.handleOnAllPrintJobsHandled();
                break;
            case 3:
                remotePrintService.stopTrackingAllPrinters();
                if (remotePrintService.mDiscoveryPriorityList != null) {
                    remotePrintService.handleStopPrinterDiscovery();
                }
                if (remotePrintService.mHasPrinterDiscoverySession) {
                    remotePrintService.handleDestroyPrinterDiscoverySession();
                }
                remotePrintService.ensureUnbound();
                remotePrintService.mDestroyed = true;
                break;
            case 4:
                remotePrintService.handleStopPrinterDiscovery();
                break;
            default:
                remotePrintService.handleCreatePrinterDiscoverySession();
                break;
        }
    }
}
