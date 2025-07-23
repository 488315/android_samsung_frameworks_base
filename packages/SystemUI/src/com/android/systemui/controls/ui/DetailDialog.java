package com.android.systemui.controls.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda1;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.wm.shell.taskview.TaskView;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DetailDialog extends Dialog {
    public final Context activityContext;
    public final ActivityStarter activityStarter;
    public final BroadcastSender broadcastSender;
    public final View controlDetailRoot;
    public final Intent fillInIntent;
    public final KeyguardStateController keyguardStateController;
    public final PendingIntent pendingIntent;
    public final SALogger saLogger;
    public final DetailDialog$stateCallback$1 stateCallback;
    public final TaskView taskView;
    public final View taskViewContainer;
    public final float taskWidthPercentWidth;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.controls.ui.DetailDialog$stateCallback$1, com.android.wm.shell.taskview.TaskView$Listener] */
    public DetailDialog(Context context, BroadcastSender broadcastSender, TaskView taskView, PendingIntent pendingIntent, ControlViewHolder controlViewHolder, KeyguardStateController keyguardStateController, ActivityStarter activityStarter, SALogger sALogger) {
        super(context, R.style.Theme_SystemUI_Sec_Dialog_Control_DetailPanel);
        this.activityContext = context;
        this.broadcastSender = broadcastSender;
        this.taskView = taskView;
        this.pendingIntent = pendingIntent;
        this.keyguardStateController = keyguardStateController;
        this.activityStarter = activityStarter;
        this.saLogger = sALogger;
        this.taskWidthPercentWidth = context.getResources().getFloat(R.dimen.controls_task_view_width_percentage);
        Intent intent = new Intent();
        intent.putExtra("controls.DISPLAY_IN_PANEL", true);
        intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        ?? r2 = new TaskView.Listener() { // from class: com.android.systemui.controls.ui.DetailDialog$stateCallback$1
            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onBackPressedOnTaskRoot(int i) {
                DetailDialog.this.dismiss();
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onInitialized() {
                DetailDialog detailDialog = DetailDialog.this;
                View view = detailDialog.taskViewContainer;
                if (view == null) {
                    view = null;
                }
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = (int) (view.getWidth() * detailDialog.taskWidthPercentWidth);
                view.setLayoutParams(layoutParams);
                ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(detailDialog.activityContext, 0, 0);
                makeCustomAnimation.setPendingIntentBackgroundActivityStartMode(3);
                makeCustomAnimation.setTaskAlwaysOnTop(true);
                TaskView taskView2 = detailDialog.taskView;
                taskView2.startActivity(detailDialog.pendingIntent, detailDialog.fillInIntent, makeCustomAnimation, ConvenienceExtensionsKt.getBoundsOnScreen(taskView2));
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskCreated(int i, ComponentName componentName) {
                ((ViewGroup) DetailDialog.this.requireViewById(R.id.controls_activity_view)).setAlpha(1.0f);
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskRemovalStarted(int i) {
                TaskView taskView2 = DetailDialog.this.taskView;
                taskView2.getHolder().removeCallback(taskView2);
                taskView2.mTaskViewTaskController.performRelease();
            }
        };
        this.stateCallback = r2;
        Window window = getWindow();
        if (window != null) {
            window.addFlags(32);
        }
        Window window2 = getWindow();
        if (window2 != null) {
            window2.addPrivateFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        }
        setContentView(R.layout.sec_controls_detail_dialog);
        this.taskViewContainer = requireViewById(R.id.control_task_view_container);
        View requireViewById = requireViewById(R.id.control_detail_root);
        requireViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.dismiss();
            }
        });
        this.controlDetailRoot = requireViewById;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.controls_activity_view);
        viewGroup.addView(taskView);
        viewGroup.setAlpha(0.0f);
        ((ImageView) requireViewById(R.id.control_detail_close)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$3$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.dismiss();
            }
        });
        ((ImageView) requireViewById(R.id.control_detail_open_in_app)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$4$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.dismiss();
                final DetailDialog detailDialog = DetailDialog.this;
                ActivityStarter.OnDismissAction onDismissAction = new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.DetailDialog$4$1$action$1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        DetailDialog detailDialog2 = DetailDialog.this;
                        BroadcastSender broadcastSender2 = detailDialog2.broadcastSender;
                        broadcastSender2.getClass();
                        broadcastSender2.sendInBackground("closeSystemDialogs", new BroadcastSender$$ExternalSyntheticLambda1(broadcastSender2));
                        detailDialog2.pendingIntent.send(ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                        return false;
                    }
                };
                if (DetailDialog.this.keyguardStateController.isUnlocked()) {
                    onDismissAction.onDismiss();
                } else {
                    DetailDialog.this.activityStarter.dismissKeyguardThenExecute(onDismissAction, null, true);
                }
                DetailDialog.this.saLogger.sendEvent(SALogger.Event.LaunchFullController.INSTANCE);
            }
        });
        Window window3 = getWindow();
        if (window3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        window3.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.controls.ui.DetailDialog.5
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                view.setPadding(insetsIgnoringVisibility.left, insetsIgnoringVisibility.top, insetsIgnoringVisibility.right, insetsIgnoringVisibility.bottom);
                return WindowInsets.CONSUMED;
            }
        });
        if (ScreenDecorationsUtils.supportsRoundedCornersOnWindows(getContext().getResources())) {
            taskView.setCornerRadius(getContext().getResources().getDimensionPixelSize(R.dimen.controls_activity_view_corner_radius));
        }
        taskView.setListener(controlViewHolder.uiExecutor, r2);
        sALogger.sendScreenView(SALogger.Screen.CustomPanel.INSTANCE);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        if (isShowing()) {
            TaskView taskView = this.taskView;
            Boolean bool = null;
            taskView.mTaskViewController.removeTaskView(taskView.mTaskViewTaskController, null);
            Context context = this.activityContext;
            Activity activity = context instanceof Activity ? (Activity) context : null;
            if (activity != null) {
                bool = Boolean.valueOf(activity.isFinishing() || activity.isDestroyed());
            }
            if (Intrinsics.areEqual(bool, Boolean.TRUE)) {
                return;
            }
            super.dismiss();
        }
    }

    public static /* synthetic */ void getStateCallback$annotations() {
    }
}
