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
import com.android.wm.shell.draganddrop.GlobalDragListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.random.Random;

/* loaded from: classes3.dex */
public final class GlobalDragListener {
    public static final String TAG;
    public GlobalDragListenerCallback callback;
    public final GlobalDragListener$globalDragListener$1 globalDragListener = new IGlobalDragListener.Stub() { // from class: com.android.wm.shell.draganddrop.GlobalDragListener$globalDragListener$1
        public final void onCrossWindowDrop(final ActivityManager.RunningTaskInfo runningTaskInfo) {
            final GlobalDragListener globalDragListener = this.this$0;
            globalDragListener.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.GlobalDragListener$globalDragListener$1$onCrossWindowDrop$1
                @Override // java.lang.Runnable
                public final void run() {
                    globalDragListener.onCrossWindowDrop(runningTaskInfo);
                }
            });
        }

        public final void onUnhandledDrop(final DragEvent dragEvent, final IUnhandledDragCallback iUnhandledDragCallback) {
            final GlobalDragListener globalDragListener = this.this$0;
            globalDragListener.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.GlobalDragListener$globalDragListener$1$onUnhandledDrop$1
                @Override // java.lang.Runnable
                public final void run() {
                    globalDragListener.onUnhandledDrop(dragEvent, iUnhandledDragCallback);
                }
            });
        }
    };
    public final ShellExecutor mainExecutor;
    public final IWindowManager wmService;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface GlobalDragListenerCallback {
    }

    /* renamed from: com.android.wm.shell.draganddrop.GlobalDragListener$onUnhandledDrop$1, reason: invalid class name */
    public final class AnonymousClass1 implements Consumer {
        public final /* synthetic */ int $traceCookie;
        public final /* synthetic */ IUnhandledDragCallback $wmCallback;

        public AnonymousClass1(IUnhandledDragCallback iUnhandledDragCallback, int i) {
            this.$wmCallback = iUnhandledDragCallback;
            this.$traceCookie = i;
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Boolean bool = (Boolean) obj;
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Notifying onUnhandledDrop complete: %b", new Object[]{bool});
            this.$wmCallback.notifyUnhandledDropComplete(bool.booleanValue());
            Trace.asyncTraceEnd(32L, "GlobalDragListener.onUnhandledDrop", this.$traceCookie);
        }
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
        int iNextInt = Random.defaultRandom.nextInt();
        Trace.asyncTraceBegin(32L, "GlobalDragListener.onUnhandledDrop", iNextInt);
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "onUnhandledDrop: %s", new Object[]{dragEvent});
        GlobalDragListenerCallback globalDragListenerCallback = this.callback;
        if (globalDragListenerCallback == null) {
            iUnhandledDragCallback.notifyUnhandledDropComplete(false);
            Trace.asyncTraceEnd(32L, "GlobalDragListener.onUnhandledDrop", iNextInt);
            return;
        }
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(iUnhandledDragCallback, iNextInt);
        DragAndDropController dragAndDropController = (DragAndDropController) globalDragListenerCallback;
        final PendingIntent launchIntent = DragUtils.getLaunchIntent(dragEvent.getClipData(), dragEvent.getDragFlags());
        if (launchIntent == null) {
            anonymousClass1.accept(Boolean.FALSE);
            return;
        }
        final int identifier = launchIntent.getCreatorUserHandle().getIdentifier();
        if (dragAndDropController.notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                PendingIntent pendingIntent = launchIntent;
                int i = identifier;
                DragEvent dragEvent2 = dragEvent;
                GlobalDragListener.AnonymousClass1 anonymousClass12 = anonymousClass1;
                int i2 = DragAndDropController.$r8$clinit;
                return Boolean.valueOf(((DragAndDropController.DragAndDropListener) obj).onUnhandledDrag(pendingIntent, i, dragEvent2, anonymousClass12));
            }
        })) {
            return;
        }
        anonymousClass1.accept(Boolean.FALSE);
    }
}
