package com.android.systemui.statusbar.phone;

import android.os.Build;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEventLogger;
import com.android.systemui.notetask.NoteTaskInfo;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import com.android.systemui.statusbar.phone.FoldStateListener;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.Bubbles;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda24 implements FoldStateListener.OnFoldStateChangeListener, Bubbles.BubbleExpandListener {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda24(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.BubbleExpandListener
    public void onBubbleExpandChanged(final String str, final boolean z) {
        final CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.f$0;
        centralSurfacesImpl.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                NoteTaskInfo noteTaskInfo;
                CentralSurfacesImpl centralSurfacesImpl2 = centralSurfacesImpl;
                boolean z2 = z;
                String str2 = str;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl2.updateScrimController();
                NoteTaskController noteTaskController = (NoteTaskController) centralSurfacesImpl2.mNoteTaskControllerLazy.get();
                if (noteTaskController.isEnabled && (noteTaskInfo = (NoteTaskInfo) noteTaskController.infoReference.getAndSet(null)) != null) {
                    if (Intrinsics.areEqual(str2, Bubble.getNoteBubbleKeyForApp(noteTaskInfo.packageName, noteTaskInfo.user)) && (noteTaskInfo.launchMode instanceof NoteTaskLaunchMode.AppBubble)) {
                        NoteTaskEventLogger noteTaskEventLogger = noteTaskController.eventLogger;
                        if (z2) {
                            DebugLogger debugLogger = DebugLogger.INSTANCE;
                            boolean z3 = Build.IS_DEBUGGABLE;
                            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                            noteTaskEventLogger.logNoteTaskOpened(noteTaskInfo);
                            return;
                        }
                        DebugLogger debugLogger2 = DebugLogger.INSTANCE;
                        boolean z4 = Build.IS_DEBUGGABLE;
                        Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                        noteTaskEventLogger.logNoteTaskClosed(noteTaskInfo);
                    }
                }
            }
        });
    }

    public void onStatusBarViewUpdated(PhoneStatusBarViewController phoneStatusBarViewController, PhoneStatusBarTransitions phoneStatusBarTransitions) {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.f$0;
        centralSurfacesImpl.mPhoneStatusBarViewController = phoneStatusBarViewController;
        centralSurfacesImpl.mStatusBarTransitions = phoneStatusBarTransitions;
        centralSurfacesImpl.getNotificationShadeWindowViewController().mStatusBarViewController = centralSurfacesImpl.mPhoneStatusBarViewController;
        centralSurfacesImpl.mShadeSurface.updateExpansionAndVisibility();
        centralSurfacesImpl.setBouncerShowingForStatusBarComponents(centralSurfacesImpl.mBouncerShowing);
        centralSurfacesImpl.checkBarModes$1();
    }
}
