package com.android.wm.shell.sysui;

import android.content.res.Configuration;
import android.os.Bundle;
import com.android.wm.shell.sysui.ShellController;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShellController.ShellInterfaceImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda2(ShellController.ShellInterfaceImpl shellInterfaceImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = shellInterfaceImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ShellController.ShellInterfaceImpl shellInterfaceImpl = this.f$0;
                ShellController.m3282$$Nest$mhandleDump(ShellController.this, (PrintWriter) this.f$1);
                break;
            case 1:
                ShellController.ShellInterfaceImpl shellInterfaceImpl2 = this.f$0;
                ShellController.this.createExternalInterfaces((Bundle) this.f$1);
                break;
            case 2:
                ShellController.ShellInterfaceImpl shellInterfaceImpl3 = this.f$0;
                ShellController.this.onConfigurationChanged((Configuration) this.f$1);
                break;
            default:
                ShellController.ShellInterfaceImpl shellInterfaceImpl4 = this.f$0;
                ShellController.this.onUserProfilesChanged((List) this.f$1);
                break;
        }
    }
}
