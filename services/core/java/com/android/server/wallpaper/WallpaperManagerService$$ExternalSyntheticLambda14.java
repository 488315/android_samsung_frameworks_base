package com.android.server.wallpaper;

import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WallpaperManagerService$$ExternalSyntheticLambda14
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ WallpaperManagerService$$ExternalSyntheticLambda14(
            int i, PrintWriter printWriter) {
        this.$r8$classId = i;
        this.f$0 = printWriter;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        PrintWriter printWriter = this.f$0;
        WallpaperManagerService.DisplayConnector displayConnector =
                (WallpaperManagerService.DisplayConnector) obj;
        switch (i) {
            case 0:
                boolean z = WallpaperManagerService.SHIPPED;
                printWriter.print("     mDisplayId=");
                printWriter.println(displayConnector.mDisplayId);
                printWriter.print("     mToken=");
                printWriter.println(displayConnector.mToken);
                printWriter.print("     mEngine=");
                printWriter.println(displayConnector.mEngine);
                break;
            case 1:
                boolean z2 = WallpaperManagerService.SHIPPED;
                printWriter.print("     mDisplayId=");
                printWriter.println(displayConnector.mDisplayId);
                printWriter.print("     mToken=");
                printWriter.println(displayConnector.mToken);
                printWriter.print("     mEngine=");
                printWriter.println(displayConnector.mEngine);
                break;
            case 2:
                boolean z3 = WallpaperManagerService.SHIPPED;
                printWriter.print("     mDisplayId=");
                printWriter.println(displayConnector.mDisplayId);
                printWriter.print("     mToken=");
                printWriter.println(displayConnector.mToken);
                printWriter.print("     mEngine=");
                printWriter.println(displayConnector.mEngine);
                break;
            case 3:
                boolean z4 = WallpaperManagerService.SHIPPED;
                printWriter.print("     mDisplayId=");
                printWriter.println(displayConnector.mDisplayId);
                printWriter.print("     mToken=");
                printWriter.println(displayConnector.mToken);
                printWriter.print("     mEngine=");
                printWriter.println(displayConnector.mEngine);
                break;
            default:
                printWriter.print("     mDisplayId=");
                printWriter.println(displayConnector.mDisplayId);
                printWriter.print("     mToken=");
                printWriter.println(displayConnector.mToken);
                printWriter.print("     mEngine=");
                printWriter.println(displayConnector.mEngine);
                break;
        }
    }
}
