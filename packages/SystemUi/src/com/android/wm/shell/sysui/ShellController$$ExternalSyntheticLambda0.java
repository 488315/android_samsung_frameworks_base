package com.android.wm.shell.sysui;

import android.util.ArrayMap;
import android.view.SurfaceControlRegistry;
import com.android.wm.shell.sysui.ShellController;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShellController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShellController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final ShellController shellController = (ShellController) this.f$0;
                shellController.getClass();
                shellController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.sysui.ShellController$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ShellController shellController2 = ShellController.this;
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        shellController2.getClass();
                        String str2 = str + "  ";
                        printWriter.println(str + "ShellController");
                        printWriter.println(str2 + "mConfigChangeListeners=" + shellController2.mConfigChangeListeners.size());
                        printWriter.println(str2 + "mLastConfiguration=" + shellController2.mLastConfiguration);
                        printWriter.println(str2 + "mKeyguardChangeListeners=" + shellController2.mKeyguardChangeListeners.size());
                        printWriter.println(str2 + "mUserChangeListeners=" + shellController2.mUserChangeListeners.size());
                        ArrayMap arrayMap = shellController2.mExternalInterfaces;
                        if (arrayMap.isEmpty()) {
                            return;
                        }
                        printWriter.println(str2 + "mExternalInterfaces={");
                        for (String str3 : arrayMap.keySet()) {
                            printWriter.println(str2 + "\t" + str3 + ": " + arrayMap.get(str3));
                        }
                        printWriter.println(str2 + "}");
                    }
                }, shellController);
                break;
            case 1:
                ShellController shellController2 = ((ShellController.ShellInterfaceImpl) this.f$0).this$0;
                SurfaceControlRegistry.createProcessInstance(shellController2.mContext);
                shellController2.mShellInit.init();
                break;
            default:
                ((ShellController.ShellInterfaceImpl) this.f$0).this$0.onKeyguardDismissAnimationFinished();
                break;
        }
    }
}
