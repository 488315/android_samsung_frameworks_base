package com.android.systemui.controls.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewFactoryController;
import com.android.wm.shell.taskview.TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
public final class SecControlsUiControllerImpl$controlsPanelCallback$1 {
    public final /* synthetic */ SecControlsUiControllerImpl this$0;

    public SecControlsUiControllerImpl$controlsPanelCallback$1(SecControlsUiControllerImpl secControlsUiControllerImpl) {
        this.this$0 = secControlsUiControllerImpl;
    }

    public final void onPanelUpdated(final FrameLayout frameLayout, final PendingIntent pendingIntent) {
        final SecControlsUiControllerImpl secControlsUiControllerImpl = this.this$0;
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("onPanelUpdated isAddedTaskView= ", "SecControlsUiControllerImpl", secControlsUiControllerImpl.isAddedTaskView);
        if (secControlsUiControllerImpl.isAddedTaskView) {
            return;
        }
        secControlsUiControllerImpl.isAddedTaskView = true;
        secControlsUiControllerImpl.panelView = frameLayout;
        frameLayout.post(new Runnable() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$controlsPanelCallback$1$onPanelUpdated$1
            @Override // java.lang.Runnable
            public final void run() {
                frameLayout.removeAllViews();
                SecControlsUiControllerImpl secControlsUiControllerImpl2 = secControlsUiControllerImpl;
                if (secControlsUiControllerImpl2.activityContext == null || secControlsUiControllerImpl2.onDismiss == null) {
                    frameLayout.setVisibility(8);
                    Log.i("SecControlsUiControllerImpl", "onPanelUpdated activityContext or onDismiss is null");
                    return;
                }
                TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl = (TaskViewFactoryController.TaskViewFactoryImpl) secControlsUiControllerImpl2.taskViewFactory.get();
                final SecControlsUiControllerImpl secControlsUiControllerImpl3 = secControlsUiControllerImpl;
                Context context = secControlsUiControllerImpl3.activityContext;
                final FrameLayout frameLayout2 = frameLayout;
                final PendingIntent pendingIntent2 = pendingIntent;
                TaskViewFactoryController.this.mShellExecutor.execute(new TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0(taskViewFactoryImpl, context, secControlsUiControllerImpl3.uiExecutor, new Consumer() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$controlsPanelCallback$1$onPanelUpdated$1.1

                    /* renamed from: com.android.systemui.controls.ui.SecControlsUiControllerImpl$controlsPanelCallback$1$onPanelUpdated$1$1$1, reason: invalid class name and collision with other inner class name */
                    final /* synthetic */ class C01761 extends FunctionReferenceImpl implements Function0 {
                        public C01761(Object obj) {
                            super(0, obj, Runnable.class, "run", "run()V", 0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            ((Runnable) this.receiver).run();
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        TaskView taskView = (TaskView) obj;
                        SecControlsUiControllerImpl secControlsUiControllerImpl4 = secControlsUiControllerImpl3;
                        if (secControlsUiControllerImpl4.activityContext == null || secControlsUiControllerImpl4.onDismiss == null) {
                            frameLayout2.setVisibility(8);
                            Log.i("SecControlsUiControllerImpl", "onPanelUpdated taskViewFactory.create activityContext or onDismiss is null");
                            return;
                        }
                        frameLayout2.setVisibility(0);
                        SecControlsUiControllerImpl secControlsUiControllerImpl5 = secControlsUiControllerImpl3;
                        Context context2 = secControlsUiControllerImpl5.activityContext;
                        context2.getClass();
                        DelayableExecutor delayableExecutor = secControlsUiControllerImpl3.uiExecutor;
                        PendingIntent pendingIntent3 = pendingIntent2;
                        taskView.getClass();
                        SecControlsActivity$onStart$1$1 secControlsActivity$onStart$1$1 = secControlsUiControllerImpl3.onDismiss;
                        secControlsActivity$onStart$1$1.getClass();
                        PanelTaskViewController panelTaskViewController = new PanelTaskViewController(context2, delayableExecutor, pendingIntent3, taskView, new C01761(secControlsActivity$onStart$1$1));
                        frameLayout2.addView(taskView);
                        panelTaskViewController.taskView.setListener(panelTaskViewController.uiExecutor, panelTaskViewController.stateCallback);
                        secControlsUiControllerImpl5.taskViewController = panelTaskViewController;
                    }
                }));
            }
        });
    }
}
