package com.android.systemui.navigationbar.gestural;

import com.android.internal.view.AppearanceRegion;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda10 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EdgeBackGestureHandler f$0;
    public final /* synthetic */ Executor f$1;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda10(EdgeBackGestureHandler edgeBackGestureHandler, Executor executor, int i) {
        this.$r8$classId = i;
        this.f$0 = edgeBackGestureHandler;
        this.f$1 = executor;
    }

    public void customizeStatusBarAppearance(AppearanceRegion appearanceRegion) {
        Executor executor = this.f$1;
        int i = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
        EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
        edgeBackGestureHandler.getClass();
        executor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda6(edgeBackGestureHandler, appearanceRegion, 2));
    }

    public void requestTopUi(final boolean z) {
        final EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
        int i = this.$r8$classId;
        Executor executor = this.f$1;
        switch (i) {
            case 1:
                int i2 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.getClass();
                final int i3 = 1;
                executor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                EdgeBackGestureHandler edgeBackGestureHandler2 = edgeBackGestureHandler;
                                ((NotificationShadeWindowControllerImpl) edgeBackGestureHandler2.mNotificationShadeWindowController).setRequestTopUi("ShellBackPreview", z);
                                break;
                            default:
                                EdgeBackGestureHandler edgeBackGestureHandler3 = edgeBackGestureHandler;
                                ((NotificationShadeWindowControllerImpl) edgeBackGestureHandler3.mNotificationShadeWindowController).setRequestTopUi("ShellBackPreview", z);
                                break;
                        }
                    }
                });
                break;
            default:
                int i4 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.getClass();
                final int i5 = 0;
                executor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i5) {
                            case 0:
                                EdgeBackGestureHandler edgeBackGestureHandler2 = edgeBackGestureHandler;
                                ((NotificationShadeWindowControllerImpl) edgeBackGestureHandler2.mNotificationShadeWindowController).setRequestTopUi("ShellBackPreview", z);
                                break;
                            default:
                                EdgeBackGestureHandler edgeBackGestureHandler3 = edgeBackGestureHandler;
                                ((NotificationShadeWindowControllerImpl) edgeBackGestureHandler3.mNotificationShadeWindowController).setRequestTopUi("ShellBackPreview", z);
                                break;
                        }
                    }
                });
                break;
        }
    }
}
