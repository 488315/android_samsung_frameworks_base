package com.android.systemui.controls.ui;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.wm.shell.taskview.TaskView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PanelTaskViewController {
    public final Context activityContext;
    public final Intent fillInIntent;
    public final Function0 hide;
    public final PendingIntent pendingIntent;
    public final PanelTaskViewController$stateCallback$1 stateCallback;
    public final TaskView taskView;
    public final Executor uiExecutor;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1] */
    public PanelTaskViewController(Context context, Executor executor, PendingIntent pendingIntent, TaskView taskView, Function0 function0) {
        this.activityContext = context;
        this.uiExecutor = executor;
        this.pendingIntent = pendingIntent;
        this.taskView = taskView;
        this.hide = function0;
        taskView.setAlpha(0.0f);
        Intent intent = new Intent();
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        this.stateCallback = new TaskView.Listener() { // from class: com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1
            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onBackPressedOnTaskRoot(int i) {
                PanelTaskViewController.this.hide.invoke();
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onInitialized() {
                final PanelTaskViewController panelTaskViewController = PanelTaskViewController.this;
                final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(panelTaskViewController.activityContext, 0, 0);
                makeCustomAnimation.setTaskAlwaysOnTop(true);
                panelTaskViewController.taskView.post(new Runnable() { // from class: com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1$onInitialized$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int dimensionPixelSize = PanelTaskViewController.this.activityContext.getResources().getDimensionPixelSize(R.dimen.controls_panel_corner_radius);
                        float[] fArr = new float[8];
                        for (int i = 0; i < 8; i++) {
                            fArr[i] = dimensionPixelSize;
                        }
                        TaskView taskView2 = PanelTaskViewController.this.taskView;
                        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
                        shapeDrawable.setTint(0);
                        taskView2.setBackground(shapeDrawable);
                        PanelTaskViewController.this.taskView.setZOrderedOnTop(true, true);
                        PanelTaskViewController.this.taskView.setClipToOutline(true);
                        PanelTaskViewController panelTaskViewController2 = PanelTaskViewController.this;
                        TaskView taskView3 = panelTaskViewController2.taskView;
                        taskView3.startActivity(panelTaskViewController2.pendingIntent, panelTaskViewController2.fillInIntent, makeCustomAnimation, ConvenienceExtensionsKt.getBoundsOnScreen(taskView3));
                        Log.d("PanelTaskViewController", "onInitialized - taskView.startActivity");
                        Trace.instant(4096L, "PanelTaskViewController - startActivity");
                    }
                });
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskCreated(int i, ComponentName componentName) {
                PanelTaskViewController.this.taskView.setAlpha(1.0f);
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskRemovalStarted(int i) {
                PanelTaskViewController.this.release();
            }
        };
    }

    public final void release() {
        TaskView taskView = this.taskView;
        taskView.getHolder().removeCallback(taskView);
        taskView.mTaskViewTaskController.performRelease();
    }

    public /* synthetic */ PanelTaskViewController(Context context, Executor executor, PendingIntent pendingIntent, TaskView taskView, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, executor, pendingIntent, taskView, (i & 16) != 0 ? new Function0() { // from class: com.android.systemui.controls.ui.PanelTaskViewController.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        } : function0);
    }
}
