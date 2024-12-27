package com.android.server.wm;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda51
        implements Consumer {
    public final /* synthetic */ ArrayList f$0;
    public final /* synthetic */ PrintWriter f$1;
    public final /* synthetic */ int[] f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda51(
            ArrayList arrayList, PrintWriter printWriter, int[] iArr, boolean z) {
        this.f$0 = arrayList;
        this.f$1 = printWriter;
        this.f$2 = iArr;
        this.f$3 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ArrayList arrayList = this.f$0;
        PrintWriter printWriter = this.f$1;
        int[] iArr = this.f$2;
        boolean z = this.f$3;
        WindowState windowState = (WindowState) obj;
        if (arrayList == null || arrayList.contains(windowState)) {
            printWriter.println("  Window #" + iArr[0] + " " + windowState + ":");
            windowState.dump(printWriter, "    ", z || arrayList != null);
            iArr[0] = iArr[0] + 1;
        }
    }
}
