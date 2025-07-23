package com.android.systemui.navigationbar.gestural;

import android.graphics.Region;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.pip.Pip;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
                int i2 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.getClass();
                edgeBackGestureHandler.mIsInPip = ((Boolean) obj).booleanValue();
                break;
            case 1:
                ((Pip) obj).removeOnIsInPipStateChangedListener(edgeBackGestureHandler.mOnIsInPipStateChangedListener);
                break;
            case 2:
                ((Pip) obj).addOnIsInPipStateChangedListener(edgeBackGestureHandler.mOnIsInPipStateChangedListener);
                break;
            case 3:
                final EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0 = edgeBackGestureHandler.mDesktopCornersChangedListener;
                final Executor executor = edgeBackGestureHandler.mUiThreadContext.getExecutor();
                final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                desktopTasksController.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addDesktopGestureExclusionRegionListener$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                        Consumer consumer = edgeBackGestureHandler$$ExternalSyntheticLambda0;
                        Executor executor2 = executor;
                        final DesktopRepository desktopRepository = desktopTasksController2.taskRepository;
                        desktopRepository.desktopGestureExclusionListener = consumer;
                        desktopRepository.desktopGestureExclusionExecutor = executor2;
                        executor2.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$setExclusionRegionListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopRepository desktopRepository2 = DesktopRepository.this;
                                Consumer consumer2 = desktopRepository2.desktopGestureExclusionListener;
                                if (consumer2 != null) {
                                    consumer2.accept(DesktopRepository.access$calculateDesktopExclusionRegion(desktopRepository2));
                                }
                            }
                        });
                    }
                });
                break;
            case 4:
                edgeBackGestureHandler.mDesktopModeExcludeRegion.set((Region) obj);
                break;
            default:
                edgeBackGestureHandler.mGestureBlockingActivityRunning.set(((Boolean) obj).booleanValue());
                break;
        }
    }
}
