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
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class UserBroadcastDispatcher implements Dumpable {
    public final Context context;
    public final BroadcastDispatcherLogger logger;
    public final PendingRemovalStore removalPendingStore;
    public final int userId;
    public final Executor workerExecutor;
    public final Handler workerHandler;
    public final Looper workerLooper;
    public final String wrongThreadErrorMsg = "This method should only be called from the worker thread (which is expected to be the BroadcastRunning thread)";
    public final ArrayMap actionsToActionsReceivers = new ArrayMap();
    public final ArrayMap receiverToActions = new ArrayMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.flags, this.action.hashCode() * 31, 31);
            String str = this.permission;
            return m + (str == null ? 0 : str.hashCode());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ReceiverProperties(action=");
            sb.append(this.action);
            sb.append(", flags=");
            sb.append(this.flags);
            sb.append(", permission=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.permission, ")");
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
        return new ActionReceiver(str, this.userId, new Function2() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$createActionReceiver$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) obj;
                IntentFilter intentFilter = (IntentFilter) obj2;
                if (Trace.isEnabled()) {
                    Trace.traceBegin(4096L, TraceData$$ExternalSyntheticOutline0.m(this.userId, "registerReceiver act=", str, " user="));
                }
                UserBroadcastDispatcher userBroadcastDispatcher = this;
                userBroadcastDispatcher.context.registerReceiverAsUser(broadcastReceiver, UserHandle.of(userBroadcastDispatcher.userId), intentFilter, str2, this.workerHandler, i);
                Trace.endSection();
                UserBroadcastDispatcher userBroadcastDispatcher2 = this;
                userBroadcastDispatcher2.logger.logContextReceiverRegistered(userBroadcastDispatcher2.userId, i, intentFilter);
                return Unit.INSTANCE;
            }
        }, new Function1() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$createActionReceiver$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) obj;
                try {
                    if (Trace.isEnabled()) {
                        Trace.traceBegin(4096L, "unregisterReceiver act=" + str + " user=" + this.userId);
                    }
                    this.context.unregisterReceiver(broadcastReceiver);
                    Trace.endSection();
                    UserBroadcastDispatcher userBroadcastDispatcher = this;
                    userBroadcastDispatcher.logger.logContextReceiverUnregistered(userBroadcastDispatcher.userId, str);
                } catch (IllegalArgumentException e) {
                    Log.e("UserBroadcastDispatcher", BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(this.userId, "Trying to unregister unregistered receiver for user ", ", action ", str), new IllegalStateException(e));
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
            loop0: while (it.hasNext()) {
                ArraySet arraySet = ((ActionReceiver) it.next()).receiverDatas;
                if (!(arraySet instanceof Collection) || !arraySet.isEmpty()) {
                    Iterator it2 = arraySet.iterator();
                    while (it2.hasNext()) {
                        if (Intrinsics.areEqual(((ReceiverData) it2.next()).receiver, broadcastReceiver)) {
                            break loop0;
                        }
                    }
                }
            }
        }
        return this.receiverToActions.containsKey(broadcastReceiver);
    }

    public final void unregisterReceiver(final BroadcastReceiver broadcastReceiver) {
        Preconditions.checkState(this.workerLooper.isCurrentThread(), this.wrongThreadErrorMsg);
        for (String str : (Iterable) this.receiverToActions.getOrDefault(broadcastReceiver, new LinkedHashSet())) {
            for (Map.Entry entry : this.actionsToActionsReceivers.entrySet()) {
                ReceiverProperties receiverProperties = (ReceiverProperties) entry.getKey();
                ActionReceiver actionReceiver = (ActionReceiver) entry.getValue();
                if (Intrinsics.areEqual(receiverProperties.action, str) && CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(actionReceiver.receiverDatas, new Function1() { // from class: com.android.systemui.broadcast.ActionReceiver$removeReceiver$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(Intrinsics.areEqual(((ReceiverData) obj).receiver, broadcastReceiver));
                    }
                }, true) && actionReceiver.receiverDatas.isEmpty() && actionReceiver.registered) {
                    actionReceiver.unregisterAction.invoke(actionReceiver);
                    actionReceiver.registered = false;
                    actionReceiver.activeCategories.clear();
                }
            }
        }
        this.receiverToActions.remove(broadcastReceiver);
        this.logger.logReceiverUnregistered(this.userId, broadcastReceiver);
    }

    public static /* synthetic */ void getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
