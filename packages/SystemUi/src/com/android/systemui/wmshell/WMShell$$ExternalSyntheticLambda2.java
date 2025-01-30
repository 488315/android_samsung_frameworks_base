package com.android.systemui.wmshell;

import com.android.p038wm.shell.desktopmode.DesktopMode;
import com.android.p038wm.shell.onehanded.OneHanded;
import com.android.p038wm.shell.pip.Pip;
import com.android.p038wm.shell.recents.RecentTasks;
import com.android.p038wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.p038wm.shell.splitscreen.SplitScreen;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.wmshell.WMShell.C374814;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WMShell$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((WMShell) this.f$0).initPip((Pip) obj);
                break;
            case 1:
                ((WMShell) this.f$0).initSplitScreen((SplitScreen) obj);
                break;
            case 2:
                ((WMShell) this.f$0).initOneHanded((OneHanded) obj);
                break;
            case 3:
                WMShell wMShell = (WMShell) this.f$0;
                wMShell.getClass();
                ((DesktopMode) obj).addVisibleTasksListener(wMShell.new C374814(), wMShell.mSysUiMainExecutor);
                break;
            case 4:
                ((WMShell) this.f$0).initRecentTasks((RecentTasks) obj);
                break;
            case 5:
                WMShell wMShell2 = (WMShell) this.f$0;
                wMShell2.getClass();
                wMShell2.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(wMShell2, (EnterSplitGestureHandler) obj, 1));
                break;
            default:
                ((CommandQueue) this.f$0).onRecentsAnimationStateChanged(((Boolean) obj).booleanValue());
                break;
        }
    }
}
