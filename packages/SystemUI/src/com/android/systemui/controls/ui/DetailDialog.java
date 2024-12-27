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
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.wm.shell.taskview.TaskView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

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
        intent.addFlags(524288);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        TaskView.Listener listener = new TaskView.Listener() { // from class: com.android.systemui.controls.ui.DetailDialog$stateCallback$1
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
                makeCustomAnimation.setPendingIntentBackgroundActivityStartMode(1);
                makeCustomAnimation.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
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
        Window window = getWindow();
        if (window != null) {
            window.addFlags(32);
        }
        Window window2 = getWindow();
        if (window2 != null) {
            window2.addPrivateFlags(536870912);
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
                        detailDialog2.broadcastSender.closeSystemDialogs();
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
            throw new IllegalStateException("Required value was null.".toString());
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
        taskView.setListener(controlViewHolder.uiExecutor, listener);
        sALogger.sendScreenView(SALogger.Screen.CustomPanel.INSTANCE);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        if (isShowing()) {
            this.taskView.removeTask();
            Context context = this.activityContext;
            Boolean bool = null;
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
