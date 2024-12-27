package com.android.systemui.statusbar.phone;

import android.os.Build;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEventLogger;
import com.android.systemui.notetask.NoteTaskInfo;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.wm.shell.bubbles.Bubble;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda34 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda34(CentralSurfacesImpl centralSurfacesImpl, boolean z) {
        this.$r8$classId = 1;
        this.f$0 = centralSurfacesImpl;
        this.f$1 = z;
        this.f$2 = "ShellStartingWindow";
    }

    @Override // java.lang.Runnable
    public final void run() {
        NoteTaskInfo noteTaskInfo;
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                boolean z = this.f$1;
                String str = this.f$2;
                centralSurfacesImpl.updateScrimController();
                NoteTaskController noteTaskController = (NoteTaskController) centralSurfacesImpl.mNoteTaskControllerLazy.get();
                if (noteTaskController.isEnabled && (noteTaskInfo = (NoteTaskInfo) noteTaskController.infoReference.getAndSet(null)) != null) {
                    if (Intrinsics.areEqual(str, Bubble.getAppBubbleKeyForApp(noteTaskInfo.packageName, noteTaskInfo.user))) {
                        if (Intrinsics.areEqual(noteTaskInfo.launchMode, NoteTaskLaunchMode.AppBubble.INSTANCE)) {
                            NoteTaskEventLogger noteTaskEventLogger = noteTaskController.eventLogger;
                            if (!z) {
                                int i = DebugLogger.$r8$clinit;
                                boolean z2 = Build.IS_DEBUGGABLE;
                                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                                noteTaskEventLogger.logNoteTaskClosed(noteTaskInfo);
                                break;
                            } else {
                                int i2 = DebugLogger.$r8$clinit;
                                boolean z3 = Build.IS_DEBUGGABLE;
                                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                                noteTaskEventLogger.logNoteTaskOpened(noteTaskInfo);
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                ((NotificationShadeWindowControllerImpl) centralSurfacesImpl2.mNotificationShadeWindowController).setRequestTopUi(this.f$2, this.f$1);
                break;
        }
    }

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda34(CentralSurfacesImpl centralSurfacesImpl, boolean z, String str) {
        this.$r8$classId = 0;
        this.f$0 = centralSurfacesImpl;
        this.f$1 = z;
        this.f$2 = str;
    }
}
