package com.android.systemui.controls.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewFactoryController;
import com.android.wm.shell.taskview.TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlActionCoordinatorImpl$showDetail$1 implements Runnable {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ PendingIntent $pendingIntent;
    public final /* synthetic */ boolean $showFullScreen;
    public final /* synthetic */ ControlActionCoordinatorImpl this$0;

    public ControlActionCoordinatorImpl$showDetail$1(ControlActionCoordinatorImpl controlActionCoordinatorImpl, PendingIntent pendingIntent, boolean z, ControlViewHolder controlViewHolder) {
        this.this$0 = controlActionCoordinatorImpl;
        this.$pendingIntent = pendingIntent;
        this.$showFullScreen = z;
        this.$cvh = controlViewHolder;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final List<ResolveInfo> queryIntentActivities = this.this$0.context.getPackageManager().queryIntentActivities(this.$pendingIntent.getIntent(), 65536);
        final ControlActionCoordinatorImpl controlActionCoordinatorImpl = this.this$0;
        DelayableExecutor delayableExecutor = controlActionCoordinatorImpl.uiExecutor;
        final boolean z = this.$showFullScreen;
        final PendingIntent pendingIntent = this.$pendingIntent;
        final ControlViewHolder controlViewHolder = this.$cvh;
        delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1.1
            @Override // java.lang.Runnable
            public final void run() {
                if (!(!queryIntentActivities.isEmpty()) || !controlActionCoordinatorImpl.taskViewFactory.isPresent()) {
                    controlViewHolder.setErrorStatus();
                    return;
                }
                if (z) {
                    PendingIntent pendingIntent2 = pendingIntent;
                    Intent intent = pendingIntent2.getIntent();
                    if ((intent.getFlags() & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) == 0) {
                        Log.w("ControlsUiController", "makeValid: " + intent);
                        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    }
                    pendingIntent2.send();
                    return;
                }
                ControlActionCoordinatorImpl controlActionCoordinatorImpl2 = controlActionCoordinatorImpl;
                if (controlActionCoordinatorImpl2.dialog == null) {
                    TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl = (TaskViewFactoryController.TaskViewFactoryImpl) controlActionCoordinatorImpl2.taskViewFactory.get();
                    final ControlActionCoordinatorImpl controlActionCoordinatorImpl3 = controlActionCoordinatorImpl;
                    Context context = controlActionCoordinatorImpl3.context;
                    final PendingIntent pendingIntent3 = pendingIntent;
                    final ControlViewHolder controlViewHolder2 = controlViewHolder;
                    ((HandlerExecutor) TaskViewFactoryController.this.mShellExecutor).execute(new TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0(taskViewFactoryImpl, context, controlActionCoordinatorImpl3.uiExecutor, new Consumer() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.showDetail.1.1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            TaskView taskView = (TaskView) obj;
                            ControlActionCoordinatorImpl controlActionCoordinatorImpl4 = ControlActionCoordinatorImpl.this;
                            Context context2 = ControlActionCoordinatorImpl.this.activityContext;
                            Intrinsics.checkNotNull(context2);
                            BroadcastSender broadcastSender = ControlActionCoordinatorImpl.this.broadcastSender;
                            Intrinsics.checkNotNull(taskView);
                            PendingIntent pendingIntent4 = pendingIntent3;
                            ControlViewHolder controlViewHolder3 = controlViewHolder2;
                            ControlActionCoordinatorImpl controlActionCoordinatorImpl5 = ControlActionCoordinatorImpl.this;
                            DetailDialog detailDialog = new DetailDialog(context2, broadcastSender, taskView, pendingIntent4, controlViewHolder3, controlActionCoordinatorImpl5.keyguardStateController, controlActionCoordinatorImpl5.activityStarter, controlActionCoordinatorImpl5.saLogger);
                            final ControlActionCoordinatorImpl controlActionCoordinatorImpl6 = ControlActionCoordinatorImpl.this;
                            detailDialog.setTitle("DetailDialog");
                            detailDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1$1$1$1$1
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    ControlActionCoordinatorImpl.this.dialog = null;
                                }
                            });
                            detailDialog.show();
                            controlActionCoordinatorImpl4.dialog = detailDialog;
                        }
                    }));
                    Unit unit = Unit.INSTANCE;
                }
            }
        });
    }
}
