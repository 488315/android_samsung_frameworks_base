package com.android.systemui.navigationbar.gestural;

import android.graphics.Region;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda0;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EdgeBackGestureHandler f$0;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda0(EdgeBackGestureHandler edgeBackGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = edgeBackGestureHandler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
        switch (i) {
            case 0:
                edgeBackGestureHandler.getClass();
                edgeBackGestureHandler.mIsInPip = ((Boolean) obj).booleanValue();
                break;
            case 1:
                EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0 = edgeBackGestureHandler.mOnIsInPipStateChangedListener;
                PipController.PipImpl pipImpl = (PipController.PipImpl) ((Pip) obj);
                ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(3, pipImpl, edgeBackGestureHandler$$ExternalSyntheticLambda0));
                break;
            case 2:
                final EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda02 = edgeBackGestureHandler.mDesktopCornersChangedListener;
                final Executor executor = edgeBackGestureHandler.mUiThreadContext.getExecutor();
                final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addDesktopGestureExclusionRegionListener$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                        Consumer consumer = edgeBackGestureHandler$$ExternalSyntheticLambda02;
                        Executor executor2 = executor;
                        final DesktopModeTaskRepository desktopModeTaskRepository = desktopTasksController2.desktopModeTaskRepository;
                        desktopModeTaskRepository.desktopGestureExclusionListener = consumer;
                        desktopModeTaskRepository.desktopGestureExclusionExecutor = executor2;
                        executor2.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$setExclusionRegionListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopModeTaskRepository desktopModeTaskRepository2 = DesktopModeTaskRepository.this;
                                Consumer consumer2 = desktopModeTaskRepository2.desktopGestureExclusionListener;
                                if (consumer2 != null) {
                                    consumer2.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository2));
                                }
                            }
                        });
                    }
                });
                break;
            default:
                edgeBackGestureHandler.mDesktopModeExcludeRegion.set((Region) obj);
                break;
        }
    }
}
