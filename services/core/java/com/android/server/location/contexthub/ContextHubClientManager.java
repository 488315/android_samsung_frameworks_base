package com.android.server.location.contexthub;

import android.app.PendingIntent;
import android.chre.flags.Flags;
import android.content.Context;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.IContextHubClient;
import android.hardware.location.IContextHubClientCallback;
import android.hardware.location.NanoAppMessage;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.location.contexthub.ContextHubClientBroker;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContextHubClientManager {
    public final Context mContext;
    public final IContextHubWrapper mContextHubProxy;
    public final ConcurrentHashMap mHostEndPointIdToClientMap = new ConcurrentHashMap();
    public int mNextHostEndPointId = 0;
    public final ConcurrentLinkedEvictingDeque mRegistrationRecordDeque = new ConcurrentLinkedEvictingDeque();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RegistrationRecord {
        public final int mAction;
        public final String mBroker;
        public final long mTimestamp = System.currentTimeMillis();

        public RegistrationRecord(String str, int i) {
            this.mBroker = str;
            this.mAction = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ContextHubServiceUtil.formatDateFromTimestamp(this.mTimestamp));
            sb.append(" ");
            int i = this.mAction;
            sb.append(i == 0 ? "+ " : "- ");
            sb.append(this.mBroker);
            if (i == 2) {
                sb.append(" (cancelled)");
            }
            return sb.toString();
        }
    }

    public ContextHubClientManager(Context context, IContextHubWrapper iContextHubWrapper) {
        this.mContext = context;
        this.mContextHubProxy = iContextHubWrapper;
    }

    public final void forEachClientOfHub(int i, Consumer consumer) {
        for (ContextHubClientBroker contextHubClientBroker : this.mHostEndPointIdToClientMap.values()) {
            if (contextHubClientBroker.mAttachedContextHubInfo.getId() == i) {
                consumer.accept(contextHubClientBroker);
            }
        }
    }

    public final ContextHubClientBroker getClientBroker(int i, PendingIntent pendingIntent, long j) {
        PendingIntent pendingIntent2;
        long j2;
        for (ContextHubClientBroker contextHubClientBroker : this.mHostEndPointIdToClientMap.values()) {
            synchronized (contextHubClientBroker) {
                ContextHubClientBroker.PendingIntentRequest pendingIntentRequest = contextHubClientBroker.mPendingIntentRequest;
                pendingIntent2 = pendingIntentRequest.mPendingIntent;
                j2 = pendingIntentRequest.mNanoAppId;
            }
            if (pendingIntent2 != null && pendingIntent2.equals(pendingIntent) && j2 == j && contextHubClientBroker.mAttachedContextHubInfo.getId() == i) {
                return contextHubClientBroker;
            }
        }
        return null;
    }

    public final short getHostEndPointId() {
        if (this.mHostEndPointIdToClientMap.size() == 32768) {
            throw new IllegalStateException("Could not register client - max limit exceeded");
        }
        int i = this.mNextHostEndPointId;
        int i2 = 0;
        while (true) {
            if (i2 > 32767) {
                break;
            }
            if (this.mHostEndPointIdToClientMap.containsKey(Short.valueOf((short) i))) {
                i = i == 32767 ? 0 : i + 1;
                i2++;
            } else {
                this.mNextHostEndPointId = i != 32767 ? i + 1 : 0;
            }
        }
        return (short) i;
    }

    public final byte onMessageFromNanoApp(int i, short s, final NanoAppMessage nanoAppMessage, final List list, final List list2) {
        if (!nanoAppMessage.isBroadcastMessage()) {
            ContextHubClientBroker contextHubClientBroker = (ContextHubClientBroker) this.mHostEndPointIdToClientMap.get(Short.valueOf(s));
            if (contextHubClientBroker != null) {
                ContextHubEventLogger.getInstance().logMessageFromNanoapp(i, nanoAppMessage, true);
                return contextHubClientBroker.sendMessageToClient(nanoAppMessage, list, list2);
            }
            ContextHubEventLogger.getInstance().logMessageFromNanoapp(i, nanoAppMessage, false);
            Log.e("ContextHubClientManager", "Cannot send message to unregistered client (host endpoint ID = " + ((int) s) + ")");
            return (byte) 4;
        }
        if (Flags.reliableMessageImplementation() && nanoAppMessage.isReliable()) {
            Log.e("ContextHubClientManager", "Received reliable broadcast message from " + nanoAppMessage.getNanoAppId());
            return (byte) 2;
        }
        if (list2.isEmpty()) {
            ContextHubEventLogger.getInstance().logMessageFromNanoapp(i, nanoAppMessage, true);
            forEachClientOfHub(i, new Consumer() { // from class: com.android.server.location.contexthub.ContextHubClientManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((ContextHubClientBroker) obj).sendMessageToClient(nanoAppMessage, list, list2);
                }
            });
            return (byte) 0;
        }
        Log.e("ContextHubClientManager", "Received broadcast message with permissions from " + nanoAppMessage.getNanoAppId());
        return (byte) 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0, types: [android.os.IBinder, android.os.IBinder$DeathRecipient, com.android.server.location.contexthub.ContextHubClientBroker, java.lang.Object] */
    public final IContextHubClient registerClient(ContextHubInfo contextHubInfo, IContextHubClientCallback iContextHubClientCallback, String str, ContextHubTransactionManager contextHubTransactionManager, String str2) {
        ?? contextHubClientBroker;
        synchronized (this) {
            short hostEndPointId = getHostEndPointId();
            contextHubClientBroker = new ContextHubClientBroker(this.mContext, this.mContextHubProxy, this, contextHubInfo, hostEndPointId, iContextHubClientCallback, str, contextHubTransactionManager, null, 0L, str2);
            this.mHostEndPointIdToClientMap.put(Short.valueOf(hostEndPointId), contextHubClientBroker);
            this.mRegistrationRecordDeque.add(new RegistrationRecord(contextHubClientBroker.toString(), 0));
        }
        try {
            IContextHubClientCallback iContextHubClientCallback2 = contextHubClientBroker.mContextHubClientCallback;
            if (iContextHubClientCallback2 != null) {
                iContextHubClientCallback2.asBinder().linkToDeath(contextHubClientBroker, 0);
            }
            Log.d("ContextHubClientManager", "Registered client with host endpoint ID " + ((int) contextHubClientBroker.mHostEndPointId));
            return IContextHubClient.Stub.asInterface((IBinder) contextHubClientBroker);
        } catch (RemoteException unused) {
            Log.e("ContextHubClientManager", "Failed to attach death recipient to client");
            contextHubClientBroker.close();
            return null;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.mHostEndPointIdToClientMap.values().iterator();
        while (it.hasNext()) {
            sb.append((ContextHubClientBroker) it.next());
            sb.append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        sb.append("Registration History:");
        sb.append(System.lineSeparator());
        Iterator descendingIterator = this.mRegistrationRecordDeque.descendingIterator();
        while (descendingIterator.hasNext()) {
            sb.append(descendingIterator.next());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public final void unregisterClient(short s) {
        ContextHubClientBroker contextHubClientBroker = (ContextHubClientBroker) this.mHostEndPointIdToClientMap.get(Short.valueOf(s));
        if (contextHubClientBroker != null) {
            this.mRegistrationRecordDeque.add(new RegistrationRecord(contextHubClientBroker.toString(), contextHubClientBroker.mIsPendingIntentCancelled.get() ? 2 : 1));
        }
        if (this.mHostEndPointIdToClientMap.remove(Short.valueOf(s)) != null) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(s, "Unregistered client with host endpoint ID ", "ContextHubClientManager");
        } else {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(s, "Cannot unregister non-existing client with host endpoint ID ", "ContextHubClientManager");
        }
    }
}
