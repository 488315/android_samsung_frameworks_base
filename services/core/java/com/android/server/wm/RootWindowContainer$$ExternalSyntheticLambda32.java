package com.android.server.wm;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda32
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda32(
            int i, PrintWriter printWriter) {
        this.$r8$classId = i;
        this.f$0 = printWriter;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PrintWriter printWriter = this.f$0;
        switch (i) {
            case 0:
                printWriter.println("  Activities waiting to finish:");
                break;
            case 1:
                printWriter.println("  Activities waiting to stop:");
                break;
            default:
                printWriter.println(
                        "  Resumed activities in task display areas (from top to bottom):");
                break;
        }
    }
}
