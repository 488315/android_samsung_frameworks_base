package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.os.Trace;
import android.view.DragEvent;
import android.view.IWindowManager;
import android.window.IGlobalDragListener;
import android.window.IUnhandledDragCallback;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.function.Function;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.random.Random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalDragListener {
    public static final String TAG;
    public GlobalDragListenerCallback callback;
    public final GlobalDragListener$globalDragListener$1 globalDragListener = new IGlobalDragListener.Stub() { // from class: com.android.wm.shell.draganddrop.GlobalDragListener$globalDragListener$1
        public final void onCrossWindowDrop(final ActivityManager.RunningTaskInfo runningTaskInfo) {
            final GlobalDragListener globalDragListener = GlobalDragListener.this;
            globalDragListener.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.GlobalDragListener$globalDragListener$1$onCrossWindowDrop$1
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalDragListener.this.onCrossWindowDrop(runningTaskInfo);
                }
            });
        }

        public final void onUnhandledDrop(final DragEvent dragEvent, final IUnhandledDragCallback iUnhandledDragCallback) {
            final GlobalDragListener globalDragListener = GlobalDragListener.this;
            globalDragListener.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.GlobalDragListener$globalDragListener$1$onUnhandledDrop$1
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalDragListener.this.onUnhandledDrop(dragEvent, iUnhandledDragCallback);
                }
            });
        }
    };
    public final ShellExecutor mainExecutor;
    public final IWindowManager wmService;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface GlobalDragListenerCallback {
    }

    static {
        new Companion(null);
        TAG = "GlobalDragListener";
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.draganddrop.GlobalDragListener$globalDragListener$1] */
    public GlobalDragListener(IWindowManager iWindowManager, ShellExecutor shellExecutor) {
        this.wmService = iWindowManager;
        this.mainExecutor = shellExecutor;
    }

    public final void onCrossWindowDrop(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "onCrossWindowDrop: %s", new Object[]{runningTaskInfo});
        GlobalDragListenerCallback globalDragListenerCallback = this.callback;
        if (globalDragListenerCallback != null) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.reorder(runningTaskInfo.token, true);
            ((DragAndDropController) globalDragListenerCallback).mTransitions.startTransition(3, windowContainerTransaction, null);
        }
    }

    public final void onUnhandledDrop(final DragEvent dragEvent, IUnhandledDragCallback iUnhandledDragCallback) {
        Random.Default.getClass();
        int nextInt = Random.defaultRandom.nextInt();
        Trace.asyncTraceBegin(32L, "GlobalDragListener.onUnhandledDrop", nextInt);
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "onUnhandledDrop: %s", new Object[]{dragEvent});
        GlobalDragListenerCallback globalDragListenerCallback = this.callback;
        if (globalDragListenerCallback == null) {
            iUnhandledDragCallback.notifyUnhandledDropComplete(false);
            Trace.asyncTraceEnd(32L, "GlobalDragListener.onUnhandledDrop", nextInt);
            return;
        }
        final GlobalDragListener$onUnhandledDrop$1 globalDragListener$onUnhandledDrop$1 = new GlobalDragListener$onUnhandledDrop$1(iUnhandledDragCallback, nextInt);
        DragAndDropController dragAndDropController = (DragAndDropController) globalDragListenerCallback;
        final PendingIntent launchIntent = DragUtils.getLaunchIntent(dragEvent.getClipData(), dragEvent.getDragFlags());
        if (launchIntent == null) {
            globalDragListener$onUnhandledDrop$1.accept(Boolean.FALSE);
            return;
        }
        final int identifier = launchIntent.getCreatorUserHandle().getIdentifier();
        if (dragAndDropController.notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                PendingIntent pendingIntent = launchIntent;
                int i = identifier;
                DragEvent dragEvent2 = dragEvent;
                GlobalDragListener$onUnhandledDrop$1 globalDragListener$onUnhandledDrop$12 = globalDragListener$onUnhandledDrop$1;
                int i2 = DragAndDropController.$r8$clinit;
                return Boolean.valueOf(((DragAndDropController.DragAndDropListener) obj).onUnhandledDrag(pendingIntent, i, dragEvent2, globalDragListener$onUnhandledDrop$12));
            }
        })) {
            return;
        }
        globalDragListener$onUnhandledDrop$1.accept(Boolean.FALSE);
    }
}
