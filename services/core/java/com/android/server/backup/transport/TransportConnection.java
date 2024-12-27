package com.android.server.backup.transport;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.EventLog;
import com.android.internal.backup.IBackupTransport;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TransportConnection {
    static final String TAG = "TransportConnection";
    public final Intent mBindIntent;
    public final CloseGuard mCloseGuard;
    public final ServiceConnection mConnection;
    public final Context mContext;
    public final String mCreatorLogString;
    public final String mIdentifier;
    public final Handler mListenerHandler;
    public final Map mListeners;
    public final List mLogBuffer;
    public final String mPrefixForLog;
    public int mState;
    public volatile BackupTransportClient mTransport;
    public final ComponentName mTransportComponent;
    public final TransportStats mTransportStats;
    public final int mUserId;
    public final Object mStateLock = new Object();
    public final Object mLogBufferLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class TransportConnectionMonitor implements ServiceConnection {
        public final Context mContext;
        public final WeakReference mTransportClientRef;

        public TransportConnectionMonitor(Context context, TransportConnection transportConnection) {
            this.mContext = context;
            this.mTransportClientRef = new WeakReference(transportConnection);
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            TransportConnection transportConnection = (TransportConnection) this.mTransportClientRef.get();
            if (transportConnection == null) {
                referenceLost("TransportConnection.onBindingDied()");
                return;
            }
            synchronized (transportConnection.mStateLock) {
                try {
                    transportConnection.checkStateIntegrityLocked();
                    transportConnection.log(6, "Binding died: client UNUSABLE");
                    if (transportConnection.mTransport != null) {
                        transportConnection.mTransport.onBecomingUnusable();
                    }
                    int i = transportConnection.mState;
                    if (i == 1) {
                        transportConnection.log(6, "Unexpected state transition IDLE => UNUSABLE");
                        transportConnection.setStateLocked(0, null);
                    } else if (i == 2) {
                        transportConnection.setStateLocked(0, null);
                        transportConnection.mContext.unbindService(transportConnection.mConnection);
                        transportConnection.notifyListenersAndClearLocked(null);
                    } else if (i == 3) {
                        transportConnection.setStateLocked(0, null);
                        transportConnection.mContext.unbindService(transportConnection.mConnection);
                    }
                } finally {
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TransportConnection transportConnection = (TransportConnection) this.mTransportClientRef.get();
            if (transportConnection == null) {
                referenceLost("TransportConnection.onServiceConnected()");
                return;
            }
            Binder.allowBlocking(iBinder);
            BackupTransportClient backupTransportClient = new BackupTransportClient(IBackupTransport.Stub.asInterface(iBinder));
            synchronized (transportConnection.mStateLock) {
                try {
                    transportConnection.checkStateIntegrityLocked();
                    if (transportConnection.mState != 0) {
                        transportConnection.log(3, "Transport connected");
                        transportConnection.setStateLocked(3, backupTransportClient);
                        transportConnection.notifyListenersAndClearLocked(backupTransportClient);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            TransportConnection transportConnection = (TransportConnection) this.mTransportClientRef.get();
            if (transportConnection == null) {
                referenceLost("TransportConnection.onServiceDisconnected()");
                return;
            }
            synchronized (transportConnection.mStateLock) {
                try {
                    transportConnection.log(6, "Service disconnected: client UNUSABLE");
                    if (transportConnection.mTransport != null) {
                        transportConnection.mTransport.onBecomingUnusable();
                    }
                    transportConnection.setStateLocked(0, null);
                    try {
                        transportConnection.mContext.unbindService(transportConnection.mConnection);
                    } catch (IllegalArgumentException e) {
                        transportConnection.log(5, "Exception trying to unbind onServiceDisconnected(): " + e.getMessage());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void referenceLost(String str) {
            try {
                this.mContext.unbindService(this);
                TransportUtils.log(4, TransportConnection.TAG, str.concat(" called but TransportClient reference has been GC'ed"));
            } catch (IllegalArgumentException e) {
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " called but unbindService failed: ");
                m.append(e.getMessage());
                TransportUtils.log(5, TransportConnection.TAG, m.toString());
            }
        }
    }

    public TransportConnection(int i, Context context, TransportStats transportStats, Intent intent, ComponentName componentName, String str, String str2, Handler handler) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mLogBuffer = new LinkedList();
        this.mListeners = new ArrayMap();
        this.mState = 1;
        this.mUserId = i;
        this.mContext = context;
        this.mTransportStats = transportStats;
        this.mTransportComponent = componentName;
        this.mBindIntent = intent;
        this.mIdentifier = str;
        this.mCreatorLogString = str2;
        this.mListenerHandler = handler;
        this.mConnection = new TransportConnectionMonitor(context, this);
        this.mPrefixForLog = componentName.getShortClassName().replaceFirst(".*\\.", "") + "#" + str + ":";
        closeGuard.open("markAsDisposed");
    }

    public static String stateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "<UNKNOWN = ", ">") : "CONNECTED" : "BOUND_AND_CONNECTING" : "IDLE" : "UNUSABLE";
    }

    public final void checkState(boolean z, String str) {
        if (z) {
            return;
        }
        log(6, str);
    }

    public final void checkStateIntegrityLocked() {
        int i = this.mState;
        if (i == 0) {
            checkState(((ArrayMap) this.mListeners).isEmpty(), "Unexpected listeners when state = UNUSABLE");
            checkState(this.mTransport == null, "Transport expected to be null when state = UNUSABLE");
        } else if (i != 1) {
            if (i == 2) {
                checkState(this.mTransport == null, "Transport expected to be null when state = BOUND_AND_CONNECTING");
                return;
            }
            if (i == 3) {
                checkState(((ArrayMap) this.mListeners).isEmpty(), "Unexpected listeners when state = CONNECTED");
                checkState(this.mTransport != null, "Transport expected to be non-null when state = CONNECTED");
                return;
            } else {
                checkState(false, "Unexpected state = " + stateToString(this.mState));
                return;
            }
        }
        checkState(((ArrayMap) this.mListeners).isEmpty(), "Unexpected listeners when state = IDLE");
        checkState(this.mTransport == null, "Transport expected to be null when state = IDLE");
    }

    public final BackupTransportClient connect(String str) {
        Preconditions.checkState(!Looper.getMainLooper().isCurrentThread(), "Can't call connect() on main thread");
        BackupTransportClient backupTransportClient = this.mTransport;
        if (backupTransportClient != null) {
            log(3, str, "Sync connect: reusing transport");
            return backupTransportClient;
        }
        synchronized (this.mStateLock) {
            try {
                if (this.mState == 0) {
                    log(5, str, "Sync connect: UNUSABLE client");
                    return null;
                }
                CompletableFuture completableFuture = new CompletableFuture();
                TransportConnection$$ExternalSyntheticLambda1 transportConnection$$ExternalSyntheticLambda1 = new TransportConnection$$ExternalSyntheticLambda1(completableFuture);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                log(3, str, "Sync connect: calling async");
                synchronized (this.mStateLock) {
                    try {
                        checkStateIntegrityLocked();
                        int i = this.mState;
                        if (i == 0) {
                            log(5, str, "Async connect: UNUSABLE client");
                            notifyListener(transportConnection$$ExternalSyntheticLambda1, null, str);
                        } else if (i != 1) {
                            if (i == 2) {
                                log(3, str, "Async connect: already connecting, adding listener");
                                ((ArrayMap) this.mListeners).put(transportConnection$$ExternalSyntheticLambda1, str);
                            } else if (i == 3) {
                                log(3, str, "Async connect: reusing transport");
                                notifyListener(transportConnection$$ExternalSyntheticLambda1, this.mTransport, str);
                            }
                        } else if (this.mContext.bindServiceAsUser(this.mBindIntent, this.mConnection, 1, UserHandle.of(this.mUserId))) {
                            log(3, str, "Async connect: service bound, connecting");
                            setStateLocked(2, null);
                            ((ArrayMap) this.mListeners).put(transportConnection$$ExternalSyntheticLambda1, str);
                        } else {
                            log(6, "Async connect: bindService returned false");
                            this.mContext.unbindService(this.mConnection);
                            notifyListener(transportConnection$$ExternalSyntheticLambda1, null, str);
                        }
                    } finally {
                    }
                }
                try {
                    BackupTransportClient backupTransportClient2 = (BackupTransportClient) completableFuture.get();
                    long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
                    this.mTransportStats.registerConnectionTime(this.mTransportComponent, elapsedRealtime2);
                    Locale locale = Locale.US;
                    log(3, str, "Connect took " + elapsedRealtime2 + " ms");
                    return backupTransportClient2;
                } catch (InterruptedException | ExecutionException e) {
                    StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(e.getClass().getSimpleName(), " while waiting for transport: ");
                    m.append(e.getMessage());
                    log(6, str, m.toString());
                    return null;
                }
            } finally {
            }
        }
    }

    public final BackupTransportClient connectOrThrow(String str) {
        BackupTransportClient connect = connect(str);
        if (connect != null) {
            return connect;
        }
        log(6, str, "Transport connection failed");
        throw new TransportNotAvailableException();
    }

    public final void finalize() {
        synchronized (this.mStateLock) {
            this.mCloseGuard.warnIfOpen();
            if (this.mState >= 2) {
                log(6, "TransportClient.finalize()", "Dangling TransportClient created in [" + this.mCreatorLogString + "] being GC'ed. Left bound, unbinding...");
                try {
                    unbind("TransportClient.finalize()");
                } catch (IllegalStateException unused) {
                }
            }
        }
    }

    public final BackupTransportClient getConnectedTransport() {
        BackupTransportClient backupTransportClient = this.mTransport;
        if (backupTransportClient != null) {
            return backupTransportClient;
        }
        log(6, "PFTBT.handleCancel()", "Transport not connected");
        throw new TransportNotAvailableException();
    }

    public final void log(int i, String str) {
        TransportUtils.log(i, TAG, TransportUtils.formatMessage(this.mPrefixForLog, null, str));
        saveLogEntry(TransportUtils.formatMessage(null, null, str));
    }

    public final void log(int i, String str, String str2) {
        TransportUtils.log(i, TAG, TransportUtils.formatMessage(this.mPrefixForLog, str, str2));
        saveLogEntry(TransportUtils.formatMessage(null, str, str2));
    }

    public final void notifyListener(final TransportConnection$$ExternalSyntheticLambda1 transportConnection$$ExternalSyntheticLambda1, final BackupTransportClient backupTransportClient, String str) {
        log(4, BootReceiver$$ExternalSyntheticOutline0.m("Notifying [", str, "] transport = ", backupTransportClient != null ? "BackupTransportClient" : "null"));
        this.mListenerHandler.post(new Runnable() { // from class: com.android.server.backup.transport.TransportConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TransportConnection transportConnection = TransportConnection.this;
                TransportConnection$$ExternalSyntheticLambda1 transportConnection$$ExternalSyntheticLambda12 = transportConnection$$ExternalSyntheticLambda1;
                BackupTransportClient backupTransportClient2 = backupTransportClient;
                transportConnection.getClass();
                transportConnection$$ExternalSyntheticLambda12.f$0.complete(backupTransportClient2);
            }
        });
    }

    public final void notifyListenersAndClearLocked(BackupTransportClient backupTransportClient) {
        for (Map.Entry entry : ((ArrayMap) this.mListeners).entrySet()) {
            notifyListener((TransportConnection$$ExternalSyntheticLambda1) entry.getKey(), backupTransportClient, (String) entry.getValue());
        }
        ((ArrayMap) this.mListeners).clear();
    }

    public final void saveLogEntry(String str) {
        String str2 = ((Object) DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis())) + " " + str;
        synchronized (this.mLogBufferLock) {
            try {
                if (((LinkedList) this.mLogBuffer).size() == 5) {
                    ((LinkedList) this.mLogBuffer).remove(((LinkedList) r1).size() - 1);
                }
                ((LinkedList) this.mLogBuffer).add(0, str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setStateLocked(int i, BackupTransportClient backupTransportClient) {
        log(2, "State: " + stateToString(this.mState) + " => " + stateToString(i));
        int i2 = this.mState;
        String flattenToShortString = this.mTransportComponent.flattenToShortString();
        char c = 65535;
        boolean z = (i2 >= 2 || 2 > i) ? (i2 < 2 || 2 <= i) ? false : -1 : true;
        if (i2 < 3 && 3 <= i) {
            c = 1;
        } else if (i2 < 3 || 3 <= i) {
            c = 0;
        }
        if (z) {
            EventLog.writeEvent(2850, flattenToShortString, Integer.valueOf(z ? 1 : 0));
        }
        if (c != 0) {
            EventLog.writeEvent(2851, flattenToShortString, Integer.valueOf(c == 1 ? 1 : 0));
        }
        this.mState = i;
        this.mTransport = backupTransportClient;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TransportClient{");
        sb.append(this.mTransportComponent.flattenToShortString());
        sb.append("#");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mIdentifier, "}");
    }

    public final void unbind(String str) {
        synchronized (this.mStateLock) {
            try {
                checkStateIntegrityLocked();
                log(3, str, "Unbind requested (was " + stateToString(this.mState) + ")");
                int i = this.mState;
                if (i == 2) {
                    setStateLocked(1, null);
                    this.mContext.unbindService(this.mConnection);
                    notifyListenersAndClearLocked(null);
                } else if (i == 3) {
                    setStateLocked(1, null);
                    this.mContext.unbindService(this.mConnection);
                }
            } finally {
            }
        }
    }
}
