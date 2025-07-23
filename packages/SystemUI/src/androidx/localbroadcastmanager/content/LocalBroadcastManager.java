package androidx.localbroadcastmanager.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LocalBroadcastManager {
    public static LocalBroadcastManager mInstance;
    public static final Object mLock = new Object();
    public final Context mAppContext;
    public final AnonymousClass1 mHandler;
    public final HashMap mReceivers = new HashMap();
    public final HashMap mActions = new HashMap();
    public final ArrayList mPendingBroadcasts = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BroadcastRecord {
        public final Intent intent;
        public final ArrayList receivers;

        public BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent;
            this.receivers = arrayList;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReceiverRecord {
        public boolean broadcasting;
        public final IntentFilter filter;
        public final BroadcastReceiver receiver;

        public ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.receiver);
            sb.append(" filter=");
            sb.append(this.filter);
            sb.append("}");
            return sb.toString();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.localbroadcastmanager.content.LocalBroadcastManager$1] */
    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) { // from class: androidx.localbroadcastmanager.content.LocalBroadcastManager.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int size;
                BroadcastRecord[] broadcastRecordArr;
                if (message.what != 1) {
                    super.handleMessage(message);
                    return;
                }
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.this;
                while (true) {
                    synchronized (localBroadcastManager.mReceivers) {
                        try {
                            size = localBroadcastManager.mPendingBroadcasts.size();
                            if (size <= 0) {
                                return;
                            }
                            broadcastRecordArr = new BroadcastRecord[size];
                            localBroadcastManager.mPendingBroadcasts.toArray(broadcastRecordArr);
                            localBroadcastManager.mPendingBroadcasts.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    for (int i = 0; i < size; i++) {
                        BroadcastRecord broadcastRecord = broadcastRecordArr[i];
                        int size2 = broadcastRecord.receivers.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            ReceiverRecord receiverRecord = (ReceiverRecord) broadcastRecord.receivers.get(i2);
                            receiverRecord.getClass();
                            receiverRecord.receiver.onReceive(localBroadcastManager.mAppContext, broadcastRecord.intent);
                        }
                    }
                }
            }
        };
    }

    public static LocalBroadcastManager getInstance(Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            try {
                if (mInstance == null) {
                    mInstance = new LocalBroadcastManager(context.getApplicationContext());
                }
                localBroadcastManager = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return localBroadcastManager;
    }
}
