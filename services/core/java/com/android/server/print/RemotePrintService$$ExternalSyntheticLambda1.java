package com.android.server.print;

import android.print.PrintJobInfo;
import android.print.PrinterId;

import java.util.List;
import java.util.function.BiConsumer;

public final /* synthetic */ class RemotePrintService$$ExternalSyntheticLambda1
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        RemotePrintService remotePrintService = (RemotePrintService) obj;
        switch (this.$r8$classId) {
            case 0:
                remotePrintService.handleRequestCustomPrinterIcon((PrinterId) obj2);
                break;
            case 1:
                remotePrintService.handleValidatePrinters((List) obj2);
                break;
            case 2:
                remotePrintService.handleOnPrintJobQueued((PrintJobInfo) obj2);
                break;
            case 3:
                remotePrintService.handleRequestCancelPrintJob((PrintJobInfo) obj2);
                break;
            case 4:
                remotePrintService.handleStartPrinterDiscovery((List) obj2);
                break;
            case 5:
                remotePrintService.handleStopPrinterStateTracking((PrinterId) obj2);
                break;
            default:
                remotePrintService.handleStartPrinterStateTracking((PrinterId) obj2);
                break;
        }
    }
}
