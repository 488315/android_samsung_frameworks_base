package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                BroadcastDispatcher broadcastDispatcher = BroadcastDispatcher.this;
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
                    int i3 = message.arg1;
                    if (i3 == -2) {
                        i3 = ((UserTrackerImpl) broadcastDispatcher.userTracker).getUserId();
                    }
                    UserBroadcastDispatcher userBroadcastDispatcher = (UserBroadcastDispatcher) broadcastDispatcher.receiversByUser.get(i3);
                    if (userBroadcastDispatcher != null) {
                        userBroadcastDispatcher.unregisterReceiver((BroadcastReceiver) message.obj);
                    }
                    broadcastDispatcher.removalPendingStore.clearPendingRemoval((BroadcastReceiver) message.obj, i3);
                    return;
                }
                ReceiverData receiverData = (ReceiverData) message.obj;
                int i4 = message.arg1;
                int userId = receiverData.user.getIdentifier() == -2 ? ((UserTrackerImpl) broadcastDispatcher.userTracker).getUserId() : receiverData.user.getIdentifier();
                if (userId < -1) {
                    throw new IllegalStateException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(userId, "Attempting to register receiver for invalid user {", "}"));
                }
                UserBroadcastDispatcher userBroadcastDispatcher2 = (UserBroadcastDispatcher) broadcastDispatcher.receiversByUser.get(userId, broadcastDispatcher.createUBRForUser(userId));
                broadcastDispatcher.receiversByUser.put(userId, userBroadcastDispatcher2);
                Preconditions.checkState(userBroadcastDispatcher2.workerLooper.isCurrentThread(), "This method should only be called from the worker thread (which is expected to be the BroadcastRunning thread)");
                ArrayMap arrayMap = userBroadcastDispatcher2.receiverToActions;
                BroadcastReceiver broadcastReceiver = receiverData.receiver;
                Object obj = arrayMap.get(broadcastReceiver);
                if (obj == null) {
                    obj = new ArraySet();
                    arrayMap.put(broadcastReceiver, obj);
                }
                Collection collection = (Collection) obj;
                Iterator<String> actionsIterator = receiverData.filter.actionsIterator();
                CollectionsKt__MutableCollectionsKt.addAll(collection, actionsIterator != null ? SequencesKt__SequencesKt.asSequence(actionsIterator) : EmptySequence.INSTANCE);
                Iterator<String> actionsIterator2 = receiverData.filter.actionsIterator();
                while (actionsIterator2.hasNext()) {
                    String next = actionsIterator2.next();
                    ArrayMap arrayMap2 = userBroadcastDispatcher2.actionsToActionsReceivers;
                    next.getClass();
                    String str = receiverData.permission;
                    UserBroadcastDispatcher.ReceiverProperties receiverProperties = new UserBroadcastDispatcher.ReceiverProperties(next, i4, str);
                    Object obj2 = arrayMap2.get(receiverProperties);
                    if (obj2 == null) {
                        obj2 = userBroadcastDispatcher2.createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core(next, str, i4);
                        arrayMap2.put(receiverProperties, obj2);
                    }
                    ActionReceiver actionReceiver = (ActionReceiver) obj2;
                    actionReceiver.getClass();
                    if (!receiverData.filter.hasAction(actionReceiver.action)) {
                        throw new IllegalArgumentException("Trying to attach to " + actionReceiver.action + " without correct action,receiver: " + receiverData.receiver);
                    }
                    ArraySet arraySet = actionReceiver.activeCategories;
                    Iterator<String> categoriesIterator = receiverData.filter.categoriesIterator();
                    boolean addAll = CollectionsKt__MutableCollectionsKt.addAll(arraySet, categoriesIterator != null ? SequencesKt__SequencesKt.asSequence(categoriesIterator) : EmptySequence.INSTANCE);
                    if (actionReceiver.receiverDatas.add(receiverData) && actionReceiver.receiverDatas.size() == 1) {
                        actionReceiver.registerAction.invoke(actionReceiver, actionReceiver.createFilter());
                        actionReceiver.registered = true;
                    } else if (addAll) {
                        actionReceiver.unregisterAction.mo779invoke(actionReceiver);
                        actionReceiver.registerAction.invoke(actionReceiver, actionReceiver.createFilter());
                    }
                }
                BroadcastReceiver broadcastReceiver2 = receiverData.receiver;
                BroadcastDispatcherLogger broadcastDispatcherLogger2 = userBroadcastDispatcher2.logger;
                broadcastDispatcherLogger2.getClass();
                String broadcastReceiver3 = broadcastReceiver2.toString();
                BroadcastDispatcherLogger.Companion.getClass();
                String flagToString = BroadcastDispatcherLogger.Companion.flagToString(i4);
                LogLevel logLevel = LogLevel.INFO;
                BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = broadcastDispatcherLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.int1 = userBroadcastDispatcher2.userId;
                logMessageImpl.str1 = broadcastReceiver3;
                logMessageImpl.str2 = flagToString;
                logBuffer.commit(obtain);
            }
        };
    }

    public static Flow broadcastFlow$default(BroadcastDispatcher broadcastDispatcher, IntentFilter intentFilter, UserHandle userHandle, Function2 function2, int i) {
        if ((i & 2) != 0) {
            userHandle = null;
        }
        broadcastDispatcher.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(new BroadcastDispatcher$broadcastFlow$1(broadcastDispatcher, intentFilter, userHandle, 2, null, function2, null));
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
        return FlowConflatedKt.conflatedCallbackFlow(new BroadcastDispatcher$broadcastFlow$1(this, intentFilter, userHandle, i, str, new BroadcastDispatcher$$ExternalSyntheticLambda1(), null));
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
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = broadcastDispatcherLogger.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = -1;
        logMessageImpl.str1 = broadcastReceiver2;
        logBuffer.commit(obtain);
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
        return FlowConflatedKt.conflatedCallbackFlow(new BroadcastDispatcher$broadcastFlow$1(broadcastDispatcher, intentFilter, userHandle2, i2, str, new BroadcastDispatcher$$ExternalSyntheticLambda1(), null));
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
        int countDataSchemes = intentFilter.countDataSchemes();
        if (countDataSchemes != 0) {
            Iterator<String> actionsIterator = intentFilter.actionsIterator();
            Integer valueOf = actionsIterator != null ? Integer.valueOf(SequencesKt___SequencesKt.count(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(actionsIterator), new BroadcastDispatcher$$ExternalSyntheticLambda0()))) : null;
            if (valueOf != null && valueOf.intValue() == 0) {
                sb.append("Filter cannot contain DataSchemes without android.intent.action.PACKAGE_* action");
            } else if (!intentFilter.hasDataScheme("package") || countDataSchemes != 1) {
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
