package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.UserBroadcastDispatcher;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$$ExternalSyntheticLambda0;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes.dex */
public class BroadcastDispatcher implements Dumpable {
    public final Executor broadcastExecutor;
    public final Looper broadcastLooper;
    public final Context context;
    public final DumpManager dumpManager;
    public final BroadcastDispatcher$handler$1 handler;
    public final BroadcastDispatcherLogger logger;
    public final Executor mainExecutor;
    public final SparseArray receiversByUser = new SparseArray(20);
    public final PendingRemovalStore removalPendingStore;
    public final UserTracker userTracker;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1, reason: invalid class name */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ IntentFilter $filter;
        final /* synthetic */ int $flags;
        final /* synthetic */ Function2 $map;
        final /* synthetic */ String $permission;
        final /* synthetic */ UserHandle $user;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(IntentFilter intentFilter, UserHandle userHandle, int i, String str, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$filter = intentFilter;
            this.$user = userHandle;
            this.$flags = i;
            this.$permission = str;
            this.$map = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = BroadcastDispatcher.this.new AnonymousClass1(this.$filter, this.$user, this.$flags, this.$permission, this.$map, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1$receiver$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final Function2 function2 = this.$map;
                final ?? r4 = new BroadcastReceiver() { // from class: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1$receiver$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, function2.invoke(intent, this), "BroadcastDispatcher");
                    }
                };
                BroadcastDispatcher broadcastDispatcher = BroadcastDispatcher.this;
                broadcastDispatcher.registerReceiver(r4, this.$filter, broadcastDispatcher.broadcastExecutor, this.$user, this.$flags, this.$permission);
                final BroadcastDispatcher broadcastDispatcher2 = BroadcastDispatcher.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        broadcastDispatcher2.unregisterReceiver(r4);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.broadcast.BroadcastDispatcher$handler$1] */
    public BroadcastDispatcher(Context context, Executor executor, final Looper looper, Executor executor2, DumpManager dumpManager, BroadcastDispatcherLogger broadcastDispatcherLogger, UserTracker userTracker, PendingRemovalStore pendingRemovalStore) {
        this.context = context;
        this.mainExecutor = executor;
        this.broadcastLooper = looper;
        this.broadcastExecutor = executor2;
        this.dumpManager = dumpManager;
        this.logger = broadcastDispatcherLogger;
        this.userTracker = userTracker;
        this.removalPendingStore = pendingRemovalStore;
        this.handler = new Handler(looper) { // from class: com.android.systemui.broadcast.BroadcastDispatcher$handler$1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                BroadcastDispatcher broadcastDispatcher = this.this$0;
                if (i != 0) {
                    if (i == 1) {
                        int size = broadcastDispatcher.receiversByUser.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            ((UserBroadcastDispatcher) broadcastDispatcher.receiversByUser.valueAt(i2)).unregisterReceiver((BroadcastReceiver) message.obj);
                        }
                        broadcastDispatcher.removalPendingStore.clearPendingRemoval((BroadcastReceiver) message.obj, -1);
                        return;
                    }
                    if (i != 2) {
                        super.handleMessage(message);
                        return;
                    }
                    int userId = message.arg1;
                    if (userId == -2) {
                        userId = ((UserTrackerImpl) broadcastDispatcher.userTracker).getUserId();
                    }
                    UserBroadcastDispatcher userBroadcastDispatcher = (UserBroadcastDispatcher) broadcastDispatcher.receiversByUser.get(userId);
                    if (userBroadcastDispatcher != null) {
                        userBroadcastDispatcher.unregisterReceiver((BroadcastReceiver) message.obj);
                    }
                    broadcastDispatcher.removalPendingStore.clearPendingRemoval((BroadcastReceiver) message.obj, userId);
                    return;
                }
                ReceiverData receiverData = (ReceiverData) message.obj;
                int i3 = message.arg1;
                int userId2 = receiverData.user.getIdentifier() == -2 ? ((UserTrackerImpl) broadcastDispatcher.userTracker).getUserId() : receiverData.user.getIdentifier();
                if (userId2 < -1) {
                    throw new IllegalStateException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(userId2, "Attempting to register receiver for invalid user {", "}"));
                }
                UserBroadcastDispatcher userBroadcastDispatcher2 = (UserBroadcastDispatcher) broadcastDispatcher.receiversByUser.get(userId2, broadcastDispatcher.createUBRForUser(userId2));
                broadcastDispatcher.receiversByUser.put(userId2, userBroadcastDispatcher2);
                Preconditions.checkState(userBroadcastDispatcher2.workerLooper.isCurrentThread(), "This method should only be called from the worker thread (which is expected to be the BroadcastRunning thread)");
                ArrayMap arrayMap = userBroadcastDispatcher2.receiverToActions;
                BroadcastReceiver broadcastReceiver = receiverData.receiver;
                Object arraySet = arrayMap.get(broadcastReceiver);
                if (arraySet == null) {
                    arraySet = new ArraySet();
                    arrayMap.put(broadcastReceiver, arraySet);
                }
                Collection collection = (Collection) arraySet;
                Iterator<String> itActionsIterator = receiverData.filter.actionsIterator();
                CollectionsKt__MutableCollectionsKt.addAll(collection, itActionsIterator != null ? SequencesKt__SequencesKt.asSequence(itActionsIterator) : EmptySequence.INSTANCE);
                Iterator<String> itActionsIterator2 = receiverData.filter.actionsIterator();
                while (itActionsIterator2.hasNext()) {
                    String next = itActionsIterator2.next();
                    ArrayMap arrayMap2 = userBroadcastDispatcher2.actionsToActionsReceivers;
                    next.getClass();
                    String str = receiverData.permission;
                    UserBroadcastDispatcher.ReceiverProperties receiverProperties = new UserBroadcastDispatcher.ReceiverProperties(next, i3, str);
                    Object objCreateActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core = arrayMap2.get(receiverProperties);
                    if (objCreateActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core == null) {
                        objCreateActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core = userBroadcastDispatcher2.createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core(next, str, i3);
                        arrayMap2.put(receiverProperties, objCreateActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core);
                    }
                    ActionReceiver actionReceiver = (ActionReceiver) objCreateActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core;
                    actionReceiver.getClass();
                    if (!receiverData.filter.hasAction(actionReceiver.action)) {
                        throw new IllegalArgumentException("Trying to attach to " + actionReceiver.action + " without correct action,receiver: " + receiverData.receiver);
                    }
                    ArraySet arraySet2 = actionReceiver.activeCategories;
                    Iterator<String> itCategoriesIterator = receiverData.filter.categoriesIterator();
                    boolean zAddAll = CollectionsKt__MutableCollectionsKt.addAll(arraySet2, itCategoriesIterator != null ? SequencesKt__SequencesKt.asSequence(itCategoriesIterator) : EmptySequence.INSTANCE);
                    if (actionReceiver.receiverDatas.add(receiverData) && actionReceiver.receiverDatas.size() == 1) {
                        actionReceiver.registerAction.invoke(actionReceiver, actionReceiver.createFilter());
                        actionReceiver.registered = true;
                    } else if (zAddAll) {
                        actionReceiver.unregisterAction.mo781invoke(actionReceiver);
                        actionReceiver.registerAction.invoke(actionReceiver, actionReceiver.createFilter());
                    }
                }
                BroadcastReceiver broadcastReceiver2 = receiverData.receiver;
                BroadcastDispatcherLogger broadcastDispatcherLogger2 = userBroadcastDispatcher2.logger;
                broadcastDispatcherLogger2.getClass();
                String string = broadcastReceiver2.toString();
                BroadcastDispatcherLogger.Companion.getClass();
                String strFlagToString = BroadcastDispatcherLogger.Companion.flagToString(i3);
                LogLevel logLevel = LogLevel.INFO;
                BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = broadcastDispatcherLogger2.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.int1 = userBroadcastDispatcher2.userId;
                logMessageImpl.str1 = string;
                logMessageImpl.str2 = strFlagToString;
                logBuffer.commit(logMessageObtain);
            }
        };
    }

    public static Flow broadcastFlow$default(BroadcastDispatcher broadcastDispatcher, IntentFilter intentFilter, UserHandle userHandle, Function2 function2, int i) {
        if ((i & 2) != 0) {
            userHandle = null;
        }
        broadcastDispatcher.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(broadcastDispatcher.new AnonymousClass1(intentFilter, userHandle, 2, null, function2, null));
    }

    public static /* synthetic */ void registerReceiver$default(BroadcastDispatcher broadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, int i, String str, int i2) {
        if ((i2 & 4) != 0) {
            executor = null;
        }
        if ((i2 & 8) != 0) {
            userHandle = null;
        }
        if ((i2 & 16) != 0) {
            i = 2;
        }
        if ((i2 & 32) != 0) {
            str = null;
        }
        broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, executor, userHandle, i, str);
    }

    public static void registerReceiverWithHandler$default(BroadcastDispatcher broadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler, UserHandle userHandle, String str, int i) {
        if ((i & 8) != 0) {
            userHandle = broadcastDispatcher.context.getUser();
        }
        UserHandle userHandle2 = userHandle;
        if ((i & 32) != 0) {
            str = null;
        }
        broadcastDispatcher.getClass();
        broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, new HandlerExecutor(handler), userHandle2, 2, str);
    }

    public final Flow broadcastFlow(IntentFilter intentFilter, UserHandle userHandle, int i, String str) {
        return FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(intentFilter, userHandle, i, str, new BroadcastDispatcher$$ExternalSyntheticLambda1(), null));
    }

    public UserBroadcastDispatcher createUBRForUser(int i) {
        return new UserBroadcastDispatcher(this.context, i, this.broadcastLooper, this.broadcastExecutor, this.logger, this.removalPendingStore);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Broadcast dispatcher:");
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        int size = this.receiversByUser.size();
        for (int i = 0; i < size; i++) {
            indentingPrintWriter.println("User " + this.receiversByUser.keyAt(i));
            ((UserBroadcastDispatcher) this.receiversByUser.valueAt(i)).dump(indentingPrintWriter, strArr);
        }
        indentingPrintWriter.println("Pending removal:");
        this.removalPendingStore.dump(indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle) {
        registerReceiver$default(this, broadcastReceiver, intentFilter, executor, userHandle, 0, null, 48);
    }

    public final void registerReceiverWithHandler(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler) {
        registerReceiverWithHandler$default(this, broadcastReceiver, intentFilter, handler, null, null, 56);
    }

    public final void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        PendingRemovalStore pendingRemovalStore = this.removalPendingStore;
        BroadcastDispatcherLogger broadcastDispatcherLogger = pendingRemovalStore.logger;
        broadcastDispatcherLogger.getClass();
        String string = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = broadcastDispatcherLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = -1;
        logMessageImpl.str1 = string;
        logBuffer.commit(logMessageObtain);
        synchronized (pendingRemovalStore.pendingRemoval) {
            pendingRemovalStore.pendingRemoval.add(-1, broadcastReceiver);
        }
        obtainMessage(1, broadcastReceiver).sendToTarget();
    }

    public final void registerReceiver(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
        registerReceiver$default(this, broadcastReceiver, intentFilter, null, null, 0, null, 60);
    }

    public final void registerReceiverWithHandler(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler, UserHandle userHandle) {
        registerReceiverWithHandler$default(this, broadcastReceiver, intentFilter, handler, userHandle, null, 48);
    }

    public static Flow broadcastFlow$default(BroadcastDispatcher broadcastDispatcher, IntentFilter intentFilter, UserHandle userHandle, int i) {
        UserHandle userHandle2 = (i & 2) != 0 ? null : userHandle;
        int i2 = (i & 4) != 0 ? 2 : 4;
        String str = (i & 8) == 0 ? "com.android.systemui.permission.SELF" : null;
        broadcastDispatcher.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(broadcastDispatcher.new AnonymousClass1(intentFilter, userHandle2, i2, str, new BroadcastDispatcher$$ExternalSyntheticLambda1(), null));
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, int i, String str) {
        StringBuilder sb = new StringBuilder();
        if (intentFilter.countActions() == 0) {
            sb.append("Filter must contain at least one action. ");
        }
        if (intentFilter.countDataAuthorities() != 0) {
            sb.append("Filter cannot contain DataAuthorities. ");
        }
        if (intentFilter.countDataPaths() != 0) {
            sb.append("Filter cannot contain DataPaths. ");
        }
        int iCountDataSchemes = intentFilter.countDataSchemes();
        if (iCountDataSchemes != 0) {
            Iterator<String> itActionsIterator = intentFilter.actionsIterator();
            Integer numValueOf = itActionsIterator != null ? Integer.valueOf(SequencesKt___SequencesKt.count(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(itActionsIterator), new BroadcastDispatcher$$ExternalSyntheticLambda0()))) : null;
            if (numValueOf != null && numValueOf.intValue() == 0) {
                sb.append("Filter cannot contain DataSchemes without android.intent.action.PACKAGE_* action");
            } else if (!intentFilter.hasDataScheme("package") || iCountDataSchemes != 1) {
                sb.append("Filter needs only \"package\" data scheme");
            }
        }
        if (intentFilter.countDataTypes() != 0) {
            sb.append("Filter cannot contain DataTypes. ");
        }
        if (intentFilter.getPriority() != 0) {
            sb.append("Filter cannot modify priority. ");
        }
        if (TextUtils.isEmpty(sb)) {
            if (executor == null) {
                executor = this.mainExecutor;
            }
            Executor executor2 = executor;
            if (userHandle == null) {
                userHandle = this.context.getUser();
            }
            obtainMessage(0, i, 0, new ReceiverData(broadcastReceiver, intentFilter, executor2, userHandle, str)).sendToTarget();
            return;
        }
        throw new IllegalArgumentException(sb.toString());
    }
}
