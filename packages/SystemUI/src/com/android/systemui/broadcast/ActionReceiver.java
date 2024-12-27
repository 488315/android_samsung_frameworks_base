package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ActionReceiver extends BroadcastReceiver implements Dumpable {
    public static final AtomicInteger index;
    public final String action;
    public final BroadcastDispatcherLogger logger;
    public final Function2 registerAction;
    public boolean registered;
    public final Function2 testPendingRemovalAction;
    public final Function1 unregisterAction;
    public final int userId;
    public final Executor workerExecutor;
    public final ArraySet receiverDatas = new ArraySet();
    public final ArraySet activeCategories = new ArraySet();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        index = new AtomicInteger(0);
    }

    public ActionReceiver(String str, int i, Function2 function2, Function1 function1, Executor executor, BroadcastDispatcherLogger broadcastDispatcherLogger, Function2 function22) {
        this.action = str;
        this.userId = i;
        this.registerAction = function2;
        this.unregisterAction = function1;
        this.workerExecutor = executor;
        this.logger = broadcastDispatcherLogger;
        this.testPendingRemovalAction = function22;
    }

    public final IntentFilter createFilter() {
        IntentFilter intentFilter = new IntentFilter(this.action);
        Iterator it = this.activeCategories.iterator();
        while (it.hasNext()) {
            intentFilter.addCategory((String) it.next());
        }
        if (this.action.startsWith("android.intent.action.PACKAGE_")) {
            intentFilter.addDataScheme("package");
        }
        return intentFilter;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        boolean z = printWriter instanceof IndentingPrintWriter;
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        printWriter.println("Registered: " + this.registered);
        printWriter.println("Receivers:");
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        Iterator it = this.receiverDatas.iterator();
        while (it.hasNext()) {
            printWriter.println(((ReceiverData) it.next()).receiver);
        }
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
        printWriter.println("Categories: ".concat(CollectionsKt___CollectionsKt.joinToString$default(this.activeCategories, ", ", null, null, null, 62)));
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, final Intent intent) {
        if (!Intrinsics.areEqual(intent.getAction(), this.action)) {
            throw new IllegalStateException(MotionLayout$$ExternalSyntheticOutline0.m("Received intent for ", intent.getAction(), " in receiver for ", this.action, "}"));
        }
        final int andIncrement = index.getAndIncrement();
        this.logger.logBroadcastReceived(andIncrement, this.userId, intent);
        this.workerExecutor.execute(new Runnable() { // from class: com.android.systemui.broadcast.ActionReceiver$onReceive$1
            @Override // java.lang.Runnable
            public final void run() {
                final ActionReceiver actionReceiver = ActionReceiver.this;
                ArraySet<ReceiverData> arraySet = actionReceiver.receiverDatas;
                final Intent intent2 = intent;
                final Context context2 = context;
                final int i = andIncrement;
                for (final ReceiverData receiverData : arraySet) {
                    if (receiverData.filter.matchCategories(intent2.getCategories()) == null && !((Boolean) actionReceiver.testPendingRemovalAction.invoke(receiverData.receiver, Integer.valueOf(actionReceiver.userId))).booleanValue()) {
                        receiverData.executor.execute(new Runnable() { // from class: com.android.systemui.broadcast.ActionReceiver$onReceive$1$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ReceiverData.this.receiver.setPendingResult(actionReceiver.getPendingResult());
                                ReceiverData.this.receiver.onReceive(context2, intent2);
                                ActionReceiver actionReceiver2 = actionReceiver;
                                actionReceiver2.logger.logBroadcastDispatched(i, actionReceiver2.action, ReceiverData.this.receiver);
                            }
                        });
                    }
                }
            }
        });
    }
}
