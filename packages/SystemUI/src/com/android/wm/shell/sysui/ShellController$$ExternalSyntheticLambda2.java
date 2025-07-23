package com.android.wm.shell.sysui;

import android.graphics.Rect;
import android.view.SurfaceControlRegistry;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.wm.shell.sysui.ShellController;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShellController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShellController$$ExternalSyntheticLambda2(ShellController shellController, Rect rect) {
        this.$r8$classId = 1;
        this.f$0 = shellController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                final ShellController shellController = (ShellController) obj;
                ShellController.AnonymousClass2 anonymousClass2 = shellController.mDumpCommandHandler;
                ShellCommandHandler shellCommandHandler = shellController.mShellCommandHandler;
                shellCommandHandler.addCommandCallback("dump", anonymousClass2, shellController);
                shellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.sysui.ShellController$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        ShellController shellController2 = ShellController.this;
                        PrintWriter printWriter = (PrintWriter) obj2;
                        String str = (String) obj3;
                        shellController2.getClass();
                        String str2 = str + "  ";
                        printWriter.println(str + "ShellController");
                        printWriter.println(str2 + "mConfigChangeListeners=" + shellController2.mConfigChangeListeners.size());
                        printWriter.println(str2 + "mLastConfiguration=" + shellController2.mLastConfiguration);
                        printWriter.println(str2 + "mKeyguardChangeListeners=" + shellController2.mKeyguardChangeListeners.size());
                        printWriter.println(str2 + "mUserChangeListeners=" + shellController2.mUserChangeListeners.size());
                        if (shellController2.mExternalInterfaces.isEmpty()) {
                            return;
                        }
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str2, "mExternalInterfaces={");
                        for (String str3 : shellController2.mExternalInterfaces.keySet()) {
                            printWriter.println(str2 + "\t" + str3 + ": " + shellController2.mExternalInterfaces.get(str3));
                        }
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str2, "}");
                    }
                }, shellController);
                shellController.mDisplayInsetsController.addInsetsChangedListener(shellController.mContext.getDisplayId(), shellController.mInsetsChangeListener);
                return;
            case 1:
                ((ShellController) obj).mContext.getDisplayId();
                throw null;
            case 2:
                ((ShellController) obj).mContext.getDisplayId();
                throw null;
            case 3:
                ShellController shellController2 = (ShellController) obj;
                SurfaceControlRegistry.createProcessInstance(shellController2.mContext);
                shellController2.mShellInit.init();
                return;
            default:
                ShellController.this.onKeyguardDismissAnimationFinished();
                return;
        }
    }

    public /* synthetic */ ShellController$$ExternalSyntheticLambda2(ShellController shellController, boolean z) {
        this.$r8$classId = 2;
        this.f$0 = shellController;
    }

    public /* synthetic */ ShellController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }
}
