package com.android.server.print;

import android.print.PrintJobInfo;
import android.print.PrinterId;
import java.util.List;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemotePrintService$$ExternalSyntheticLambda1 implements BiConsumer {
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
