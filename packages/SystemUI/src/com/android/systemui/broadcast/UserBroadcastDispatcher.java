package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class UserBroadcastDispatcher implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final BroadcastDispatcherLogger logger;
    public final PendingRemovalStore removalPendingStore;
    public final int userId;
    public final Executor workerExecutor;
    public final Handler workerHandler;
    public final Looper workerLooper;
    public final ArrayMap actionsToActionsReceivers = new ArrayMap();
    public final ArrayMap receiverToActions = new ArrayMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReceiverProperties {
        public final String action;
        public final int flags;
        public final String permission;

        public ReceiverProperties(String str, int i, String str2) {
            this.action = str;
            this.flags = i;
            this.permission = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ReceiverProperties)) {
                return false;
            }
            ReceiverProperties receiverProperties = (ReceiverProperties) obj;
            return Intrinsics.areEqual(this.action, receiverProperties.action) && this.flags == receiverProperties.flags && Intrinsics.areEqual(this.permission, receiverProperties.permission);
        }

        public final int hashCode() {
            int m = ReorderTile$$ExternalSyntheticOutline0.m(this.flags, this.action.hashCode() * 31, 31);
            String str = this.permission;
            return m + (str == null ? 0 : str.hashCode());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ReceiverProperties(action=");
            sb.append(this.action);
            sb.append(", flags=");
            sb.append(this.flags);
            sb.append(", permission=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.permission, ")");
        }
    }

    static {
        new Companion(null);
        new AtomicInteger(0);
    }

    public UserBroadcastDispatcher(Context context, int i, Looper looper, Executor executor, BroadcastDispatcherLogger broadcastDispatcherLogger, PendingRemovalStore pendingRemovalStore) {
        this.context = context;
        this.userId = i;
        this.workerLooper = looper;
        this.workerExecutor = executor;
        this.logger = broadcastDispatcherLogger;
        this.removalPendingStore = pendingRemovalStore;
        this.workerHandler = new Handler(looper);
    }

    public ActionReceiver createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final String str, final String str2, final int i) {
        return new ActionReceiver(str, this.userId, new Function2() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) obj;
                IntentFilter intentFilter = (IntentFilter) obj2;
                int i2 = UserBroadcastDispatcher.$r8$clinit;
                boolean isEnabled = Trace.isEnabled();
                UserBroadcastDispatcher userBroadcastDispatcher = this;
                int i3 = userBroadcastDispatcher.userId;
                if (isEnabled) {
                    Trace.traceBegin(4096L, "registerReceiver act=" + str + " user=" + i3);
                }
                Context context = userBroadcastDispatcher.context;
                UserHandle of = UserHandle.of(i3);
                Handler handler = userBroadcastDispatcher.workerHandler;
                String str3 = str2;
                int i4 = i;
                context.registerReceiverAsUser(broadcastReceiver, of, intentFilter, str3, handler, i4);
                Trace.endSection();
                BroadcastDispatcherLogger broadcastDispatcherLogger = userBroadcastDispatcher.logger;
                broadcastDispatcherLogger.getClass();
                String joinToString$default = SequencesKt___SequencesKt.joinToString$default(SequencesKt__SequencesKt.asSequence(intentFilter.actionsIterator()), ",", "Actions(", 56);
                String joinToString$default2 = intentFilter.countCategories() != 0 ? SequencesKt___SequencesKt.joinToString$default(SequencesKt__SequencesKt.asSequence(intentFilter.categoriesIterator()), ",", "Categories(", 56) : "";
                LogLevel logLevel = LogLevel.INFO;
                BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(6);
                LogBuffer logBuffer = broadcastDispatcherLogger.buffer;
                LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.int1 = i3;
                if (!Intrinsics.areEqual(joinToString$default2, "")) {
                    joinToString$default = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(joinToString$default, "\n", joinToString$default2);
                }
                logMessageImpl.str1 = joinToString$default;
                BroadcastDispatcherLogger.Companion.getClass();
                logMessageImpl.str2 = BroadcastDispatcherLogger.Companion.flagToString(i4);
                logBuffer.commit(obtain);
                return Unit.INSTANCE;
            }
        }, new Function1() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String str3 = str;
                UserBroadcastDispatcher userBroadcastDispatcher = this;
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) obj;
                int i2 = UserBroadcastDispatcher.$r8$clinit;
                try {
                    if (Trace.isEnabled()) {
                        Trace.traceBegin(4096L, "unregisterReceiver act=" + str3 + " user=" + userBroadcastDispatcher.userId);
                    }
                    userBroadcastDispatcher.context.unregisterReceiver(broadcastReceiver);
                    Trace.endSection();
                    BroadcastDispatcherLogger broadcastDispatcherLogger = userBroadcastDispatcher.logger;
                    int i3 = userBroadcastDispatcher.userId;
                    broadcastDispatcherLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(7);
                    LogBuffer logBuffer = broadcastDispatcherLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) obtain).int1 = i3;
                    ((LogMessageImpl) obtain).str1 = str3;
                    logBuffer.commit(obtain);
                } catch (IllegalArgumentException e) {
                    Log.e("UserBroadcastDispatcher", BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(userBroadcastDispatcher.userId, "Trying to unregister unregistered receiver for user ", ", action ", str3), new IllegalStateException(e));
                }
                return Unit.INSTANCE;
            }
        }, this.workerExecutor, this.logger, new UserBroadcastDispatcher$createActionReceiver$3(this.removalPendingStore));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        boolean z = printWriter instanceof IndentingPrintWriter;
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        for (Map.Entry entry : this.actionsToActionsReceivers.entrySet()) {
            ReceiverProperties receiverProperties = (ReceiverProperties) entry.getKey();
            ActionReceiver actionReceiver = (ActionReceiver) entry.getValue();
            String str = receiverProperties.action;
            BroadcastDispatcherLogger.Companion.getClass();
            String flagToString = BroadcastDispatcherLogger.Companion.flagToString(receiverProperties.flags);
            String str2 = "):";
            String str3 = receiverProperties.permission;
            if (str3 != null) {
                str2 = ContentInViewNode$Request$$ExternalSyntheticOutline0.m(":", str3, "):");
            }
            printWriter.println(MotionLayout$$ExternalSyntheticOutline0.m("(", str, ": ", flagToString, str2));
            actionReceiver.dump(printWriter, strArr);
        }
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    public final boolean isReceiverReferenceHeld$frameworks__base__packages__SystemUI__android_common__SystemUI_core(BroadcastReceiver broadcastReceiver) {
        Collection values = this.actionsToActionsReceivers.values();
        if (!values.isEmpty()) {
            Iterator it = values.iterator();
            while (it.hasNext()) {
                ArraySet arraySet = ((ActionReceiver) it.next()).receiverDatas;
                if (arraySet == null || !arraySet.isEmpty()) {
                    Iterator it2 = arraySet.iterator();
                    while (it2.hasNext()) {
                        if (Intrinsics.areEqual(((ReceiverData) it2.next()).receiver, broadcastReceiver)) {
                            return true;
                        }
                    }
                }
            }
        }
        return this.receiverToActions.containsKey(broadcastReceiver);
    }

    public final void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        Preconditions.checkState(this.workerLooper.isCurrentThread(), "This method should only be called from the worker thread (which is expected to be the BroadcastRunning thread)");
        for (String str : (Iterable) this.receiverToActions.getOrDefault(broadcastReceiver, new LinkedHashSet())) {
            for (Map.Entry entry : this.actionsToActionsReceivers.entrySet()) {
                ReceiverProperties receiverProperties = (ReceiverProperties) entry.getKey();
                ActionReceiver actionReceiver = (ActionReceiver) entry.getValue();
                if (Intrinsics.areEqual(receiverProperties.action, str)) {
                    Iterator it = actionReceiver.receiverDatas.iterator();
                    boolean z = false;
                    while (it.hasNext()) {
                        ReceiverData receiverData = (ReceiverData) it.next();
                        AtomicInteger atomicInteger = ActionReceiver.index;
                        if (Intrinsics.areEqual(receiverData.receiver, broadcastReceiver)) {
                            it.remove();
                            z = true;
                        }
                    }
                    if (z && actionReceiver.receiverDatas.isEmpty() && actionReceiver.registered) {
                        actionReceiver.unregisterAction.mo779invoke(actionReceiver);
                        actionReceiver.registered = false;
                        actionReceiver.activeCategories.clear();
                    }
                }
            }
        }
        this.receiverToActions.remove(broadcastReceiver);
        BroadcastDispatcherLogger broadcastDispatcherLogger = this.logger;
        broadcastDispatcherLogger.getClass();
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = broadcastDispatcherLogger.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = this.userId;
        logMessageImpl.str1 = broadcastReceiver2;
        logBuffer.commit(obtain);
    }

    public static /* synthetic */ void getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
