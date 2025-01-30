package com.android.systemui.controls.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.wm.shell.taskview.TaskView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DetailDialog extends Dialog {
    public final Context activityContext;
    public final ActivityStarter activityStarter;
    public final BroadcastSender broadcastSender;
    public int detailTaskId;
    public final Intent fillInIntent;
    public final KeyguardStateController keyguardStateController;
    public final PendingIntent pendingIntent;
    public final SALogger saLogger;
    public final DetailDialog$stateCallback$1 stateCallback;
    public final TaskView taskView;
    public final View taskViewContainer;
    public final float taskWidthPercentWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.controls.ui.DetailDialog$stateCallback$1, com.android.wm.shell.taskview.TaskView$Listener] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DetailDialog(Context context, BroadcastSender broadcastSender, TaskView taskView, PendingIntent pendingIntent, ControlViewHolder controlViewHolder, KeyguardStateController keyguardStateController, ActivityStarter activityStarter, SALogger sALogger) {
        super(context, r0 ? 2132018403 : 2132018530);
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        this.activityContext = context;
        this.broadcastSender = broadcastSender;
        this.taskView = taskView;
        this.pendingIntent = pendingIntent;
        this.keyguardStateController = keyguardStateController;
        this.activityStarter = activityStarter;
        this.saLogger = sALogger;
        this.detailTaskId = -1;
        this.taskWidthPercentWidth = context.getResources().getFloat(R.dimen.controls_task_view_width_percentage);
        Intent intent = new Intent();
        intent.putExtra("controls.DISPLAY_IN_PANEL", true);
        intent.addFlags(524288);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        ?? r3 = new TaskView.Listener() { // from class: com.android.systemui.controls.ui.DetailDialog$stateCallback$1
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
                ActivityOptions pendingIntentBackgroundActivityStartMode = ActivityOptions.makeCustomAnimation(detailDialog.activityContext, 0, 0).setPendingIntentBackgroundActivityStartMode(1);
                pendingIntentBackgroundActivityStartMode.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
                TaskView taskView2 = detailDialog.taskView;
                PendingIntent pendingIntent2 = detailDialog.pendingIntent;
                Intent intent2 = detailDialog.fillInIntent;
                WindowMetrics currentWindowMetrics = ((WindowManager) detailDialog.getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics();
                Rect bounds = currentWindowMetrics.getBounds();
                Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                taskView2.startActivity(pendingIntent2, intent2, pendingIntentBackgroundActivityStartMode, new Rect(bounds.left - insetsIgnoringVisibility.left, bounds.top + insetsIgnoringVisibility.top + detailDialog.getContext().getResources().getDimensionPixelSize(R.dimen.controls_detail_dialog_header_height), bounds.right - insetsIgnoringVisibility.right, bounds.bottom - insetsIgnoringVisibility.bottom));
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onReleased() {
                DetailDialog detailDialog = DetailDialog.this;
                if (detailDialog.detailTaskId == -1) {
                    return;
                }
                ActivityTaskManager.getInstance().removeTask(detailDialog.detailTaskId);
                detailDialog.detailTaskId = -1;
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskCreated(int i) {
                DetailDialog detailDialog = DetailDialog.this;
                detailDialog.detailTaskId = i;
                ((ViewGroup) detailDialog.requireViewById(R.id.controls_activity_view)).setAlpha(1.0f);
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskRemovalStarted(int i) {
                DetailDialog detailDialog = DetailDialog.this;
                detailDialog.detailTaskId = -1;
                detailDialog.dismiss();
            }
        };
        this.stateCallback = r3;
        getWindow().addFlags(32);
        getWindow().addPrivateFlags(QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
        setContentView(z ? R.layout.controls_custom_detail_dialog : R.layout.controls_detail_dialog);
        this.taskViewContainer = requireViewById(R.id.control_task_view_container);
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.controls_activity_view);
        viewGroup.addView(taskView);
        viewGroup.setAlpha(0.0f);
        ((ImageView) requireViewById(R.id.control_detail_close)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$2$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.dismiss();
            }
        });
        requireViewById(R.id.control_detail_root).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$3$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.dismiss();
            }
        });
        ((ImageView) requireViewById(R.id.control_detail_open_in_app)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$4$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog detailDialog = DetailDialog.this;
                if (detailDialog.detailTaskId != -1) {
                    ActivityTaskManager.getInstance().removeTask(detailDialog.detailTaskId);
                    detailDialog.detailTaskId = -1;
                }
                DetailDialog.this.dismiss();
                final DetailDialog detailDialog2 = DetailDialog.this;
                ActivityStarter.OnDismissAction onDismissAction = new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.DetailDialog$4$1$action$1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        DetailDialog detailDialog3 = DetailDialog.this;
                        detailDialog3.broadcastSender.closeSystemDialogs();
                        detailDialog3.pendingIntent.send(ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                        return false;
                    }
                };
                if (DetailDialog.this.keyguardStateController.isUnlocked()) {
                    onDismissAction.onDismiss();
                } else {
                    DetailDialog.this.activityStarter.dismissKeyguardThenExecute(onDismissAction, null, true);
                }
                if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                    DetailDialog.this.saLogger.sendEvent(SALogger.Event.LaunchFullController.INSTANCE);
                }
            }
        });
        getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.controls.ui.DetailDialog.5
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                    Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                    view.setPadding(insetsIgnoringVisibility.left, insetsIgnoringVisibility.top, insetsIgnoringVisibility.right, insetsIgnoringVisibility.bottom);
                } else {
                    int paddingLeft = view.getPaddingLeft();
                    int paddingRight = view.getPaddingRight();
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                    view.setPadding(paddingLeft, insets.top, paddingRight, insets.bottom);
                }
                return WindowInsets.CONSUMED;
            }
        });
        if (ScreenDecorationsUtils.supportsRoundedCornersOnWindows(getContext().getResources())) {
            taskView.setCornerRadius(getContext().getResources().getDimensionPixelSize(R.dimen.controls_activity_view_corner_radius));
        }
        taskView.setListener(controlViewHolder.uiExecutor, r3);
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            sALogger.sendScreenView(SALogger.Screen.CustomPanel.INSTANCE);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        if (isShowing()) {
            this.taskView.release();
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
}
