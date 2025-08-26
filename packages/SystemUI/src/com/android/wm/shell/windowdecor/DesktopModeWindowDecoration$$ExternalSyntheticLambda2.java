package com.android.wm.shell.windowdecor;

import android.graphics.Bitmap;
import android.window.WindowContainerTransaction;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecoration$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecoration f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DesktopModeWindowDecoration$$ExternalSyntheticLambda2(DesktopModeWindowDecoration desktopModeWindowDecoration, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecoration;
        this.f$1 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda19, java.lang.Runnable] */
    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecoration desktopModeWindowDecoration = this.f$0;
                desktopModeWindowDecoration.mTaskOrganizer.applyTransaction((WindowContainerTransaction) this.f$1);
                break;
            case 1:
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.f$0;
                desktopModeWindowDecoration2.mTaskOrganizer.applyTransaction((WindowContainerTransaction) this.f$1);
                break;
            default:
                DesktopModeWindowDecoration desktopModeWindowDecoration3 = this.f$0;
                final BiConsumer biConsumer = (BiConsumer) this.f$1;
                final CharSequence name = desktopModeWindowDecoration3.mTaskResourceLoader.getName(desktopModeWindowDecoration3.mTaskInfo);
                final Bitmap headerIcon = desktopModeWindowDecoration3.mTaskResourceLoader.getHeaderIcon(desktopModeWindowDecoration3.mTaskInfo);
                ?? r3 = new Runnable() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        biConsumer.accept(name, headerIcon);
                    }
                };
                desktopModeWindowDecoration3.mSetAppInfoRunnable = r3;
                desktopModeWindowDecoration3.mMainExecutor.execute(r3);
                break;
        }
    }
}
