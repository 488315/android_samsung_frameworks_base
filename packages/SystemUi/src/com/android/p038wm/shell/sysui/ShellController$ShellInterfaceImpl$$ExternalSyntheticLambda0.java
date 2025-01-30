package com.android.p038wm.shell.sysui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceControlRegistry;
import com.android.p038wm.shell.sysui.ShellController;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShellController.ShellInterfaceImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda0(ShellController.ShellInterfaceImpl shellInterfaceImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = shellInterfaceImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ShellController.ShellInterfaceImpl shellInterfaceImpl = this.f$0;
                shellInterfaceImpl.this$0.onConfigurationChanged((Configuration) this.f$1);
                break;
            case 1:
                ShellController.ShellInterfaceImpl shellInterfaceImpl2 = this.f$0;
                shellInterfaceImpl2.this$0.createExternalInterfaces((Bundle) this.f$1);
                break;
            case 2:
                ShellController.ShellInterfaceImpl shellInterfaceImpl3 = this.f$0;
                shellInterfaceImpl3.this$0.onUserProfilesChanged((List) this.f$1);
                break;
            default:
                ShellController.ShellInterfaceImpl shellInterfaceImpl4 = this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                TreeMap treeMap = shellInterfaceImpl4.this$0.mShellCommandHandler.mDumpables;
                Iterator it = treeMap.keySet().iterator();
                while (it.hasNext()) {
                    ((BiConsumer) treeMap.get((String) it.next())).accept(printWriter, "");
                    printWriter.println();
                }
                SurfaceControlRegistry.dump(100, false, printWriter);
                break;
        }
    }
}
