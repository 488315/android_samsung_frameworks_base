package com.android.systemui.controls.ui;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.wm.shell.taskview.TaskView;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
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
        intent.addFlags(268435456);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        this.stateCallback = new TaskView.Listener() { // from class: com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1
            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onBackPressedOnTaskRoot(int i) {
                this.this$0.hide.invoke();
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onInitialized() {
                final PanelTaskViewController panelTaskViewController = this.this$0;
                final ActivityOptions activityOptionsMakeCustomAnimation = ActivityOptions.makeCustomAnimation(panelTaskViewController.activityContext, 0, 0);
                activityOptionsMakeCustomAnimation.setTaskAlwaysOnTop(true);
                panelTaskViewController.taskView.post(new Runnable() { // from class: com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1$onInitialized$1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        int dimensionPixelSize = panelTaskViewController.activityContext.getResources().getDimensionPixelSize(R.dimen.controls_panel_corner_radius);
                        float[] fArr = new float[8];
                        for (int i = 0; i < 8; i++) {
                            fArr[i] = dimensionPixelSize;
                        }
                        TaskView taskView2 = panelTaskViewController.taskView;
                        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
                        shapeDrawable.setTint(0);
                        taskView2.setBackground(shapeDrawable);
                        panelTaskViewController.taskView.setZOrderedOnTop(true, true);
                        panelTaskViewController.taskView.setClipToOutline(true);
                        PanelTaskViewController panelTaskViewController2 = panelTaskViewController;
                        TaskView taskView3 = panelTaskViewController2.taskView;
                        taskView3.startActivity(panelTaskViewController2.pendingIntent, panelTaskViewController2.fillInIntent, activityOptionsMakeCustomAnimation, ConvenienceExtensionsKt.getBoundsOnScreen(taskView3));
                        Log.d("PanelTaskViewController", "onInitialized - taskView.startActivity");
                        Trace.instant(4096L, "PanelTaskViewController - startActivity");
                    }
                });
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskCreated(int i, ComponentName componentName) {
                this.this$0.taskView.setAlpha(1.0f);
            }

            @Override // com.android.wm.shell.taskview.TaskView.Listener
            public final void onTaskRemovalStarted(int i) {
                this.this$0.release();
            }
        };
    }

    public final void release() {
        TaskView taskView = this.taskView;
        taskView.getHolder().removeCallback(taskView);
        taskView.mTaskViewTaskController.performRelease();
    }

    public /* synthetic */ PanelTaskViewController(Context context, Executor executor, PendingIntent pendingIntent, TaskView taskView, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, executor, pendingIntent, taskView, (i & 16) != 0 ? new PanelTaskViewController$$ExternalSyntheticLambda0() : function0);
    }
}
