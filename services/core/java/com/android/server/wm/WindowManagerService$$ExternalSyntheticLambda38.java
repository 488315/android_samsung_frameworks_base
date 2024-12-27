package com.android.server.wm;

import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda38 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda38(int i, PrintWriter printWriter) {
        this.$r8$classId = i;
        this.f$0 = printWriter;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        PrintWriter printWriter = this.f$0;
        switch (i) {
            case 0:
                int i2 = WindowManagerService.MY_PID;
                ((TaskDisplayArea) obj).dump(printWriter, "    ", false);
                break;
            case 1:
                int i3 = WindowManagerService.MY_PID;
                printWriter.println((WindowState) obj);
                break;
            case 2:
                DisplayContent displayContent = (DisplayContent) obj;
                int i4 = WindowManagerService.MY_PID;
                int i5 = displayContent.mDisplayId;
                InsetsControlTarget imeTarget = displayContent.getImeTarget(0);
                InputTarget inputTarget = displayContent.mImeInputTarget;
                InsetsControlTarget imeTarget2 = displayContent.getImeTarget(2);
                if (imeTarget != null) {
                    printWriter.print("  imeLayeringTarget in display# ");
                    printWriter.print(i5);
                    printWriter.print(' ');
                    printWriter.println(imeTarget);
                }
                if (inputTarget != null) {
                    printWriter.print("  imeInputTarget in display# ");
                    printWriter.print(i5);
                    printWriter.print(' ');
                    printWriter.println(inputTarget);
                }
                if (imeTarget2 != null) {
                    printWriter.print("  imeControlTarget in display# ");
                    printWriter.print(i5);
                    printWriter.print(' ');
                    printWriter.println(imeTarget2);
                }
                printWriter.print("  Minimum task size of display#");
                printWriter.print(i5);
                printWriter.print(' ');
                printWriter.print(displayContent.mMinSizeOfResizeableTaskDp);
                break;
            default:
                int i6 = WindowManagerService.MY_PID;
                ((DisplayContent) obj).mWallpaperController.dump(printWriter);
                printWriter.println();
                break;
        }
    }
}
