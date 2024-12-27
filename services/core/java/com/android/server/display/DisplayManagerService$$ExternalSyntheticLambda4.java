package com.android.server.display;

import android.util.IndentingPrintWriter;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda4
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda4(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Runnable updateDisplayStateLocked;
        switch (this.$r8$classId) {
            case 0:
                DisplayManagerService displayManagerService = (DisplayManagerService) this.f$0;
                List list = (List) this.f$1;
                DisplayDevice displayDevice = (DisplayDevice) obj;
                displayManagerService.getClass();
                if (displayDevice.getDisplayDeviceInfoLocked().type == 1
                        && (updateDisplayStateLocked =
                                        displayManagerService.updateDisplayStateLocked(
                                                displayDevice))
                                != null) {
                    list.add(updateDisplayStateLocked);
                    break;
                }
                break;
            default:
                PrintWriter printWriter = (PrintWriter) this.f$0;
                PrintWriter printWriter2 = (IndentingPrintWriter) this.f$1;
                DisplayDevice displayDevice2 = (DisplayDevice) obj;
                printWriter.println("  " + displayDevice2.getDisplayDeviceInfoLocked());
                displayDevice2.dumpLocked(printWriter2);
                break;
        }
    }
}
