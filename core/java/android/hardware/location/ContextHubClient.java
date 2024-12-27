package android.hardware.location;

import android.annotation.SystemApi;
import android.chre.flags.Flags;
import android.os.RemoteException;
import android.util.Log;

import dalvik.system.CloseGuard;

import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@SystemApi
public class ContextHubClient implements Closeable {
    private static final String TAG = "ContextHubClient";
    private final ContextHubInfo mAttachedHub;
    private final CloseGuard mCloseGuard;
    private final boolean mPersistent;
    private IContextHubClient mClientProxy = null;
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private Integer mId = null;

    ContextHubClient(ContextHubInfo hubInfo, boolean persistent) {
        this.mAttachedHub = hubInfo;
        this.mPersistent = persistent;
        if (this.mPersistent) {
            this.mCloseGuard = null;
        } else {
            this.mCloseGuard = CloseGuard.get();
            this.mCloseGuard.open("ContextHubClient.close");
        }
    }

    synchronized void setClientProxy(IContextHubClient clientProxy) {
        Objects.requireNonNull(clientProxy, "IContextHubClient cannot be null");
        if (this.mClientProxy != null) {
            throw new IllegalStateException("Cannot change client proxy multiple times");
        }
        this.mClientProxy = clientProxy;
        try {
            this.mId = Integer.valueOf(this.mClientProxy.getId());
            notifyAll();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubInfo getAttachedHub() {
        return this.mAttachedHub;
    }

    public int getId() {
        if (this.mId == null) {
            throw new IllegalStateException("ID was not set");
        }
        return this.mId.intValue() & 65535;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (!this.mIsClosed.getAndSet(true)) {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.close();
            }
            try {
                this.mClientProxy.close();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int sendMessageToNanoApp(NanoAppMessage message) {
        return doSendMessageToNanoApp(message, null);
    }

    public ContextHubTransaction<Void> sendReliableMessageToNanoApp(NanoAppMessage message) {
        ContextHubTransaction<Void> transaction = new ContextHubTransaction<>(5);
        if (!Flags.reliableMessageImplementation()
                || !this.mAttachedHub.supportsReliableMessages()
                || message.isBroadcastMessage()) {
            transaction.setResponse(new ContextHubTransaction.Response<>(9, null));
            return transaction;
        }
        IContextHubTransactionCallback callback =
                ContextHubTransactionHelper.createTransactionCallback(transaction);
        int result = doSendMessageToNanoApp(message, callback);
        if (result != 0) {
            transaction.setResponse(new ContextHubTransaction.Response<>(result, null));
        }
        return transaction;
    }

    private int doSendMessageToNanoApp(
            NanoAppMessage message, IContextHubTransactionCallback transactionCallback) {
        Objects.requireNonNull(message, "NanoAppMessage cannot be null");
        int maxPayloadBytes = this.mAttachedHub.getMaxPacketLengthBytes();
        byte[] payload = message.getMessageBody();
        if (payload != null && payload.length > maxPayloadBytes) {
            Log.e(
                    TAG,
                    "Message (%d bytes) exceeds max payload length (%d bytes)"
                            .formatted(
                                    Integer.valueOf(payload.length),
                                    Integer.valueOf(maxPayloadBytes)));
            return 2;
        }
        try {
            if (transactionCallback == null) {
                return this.mClientProxy.sendMessageToNanoApp(message);
            }
            return this.mClientProxy.sendReliableMessageToNanoApp(message, transactionCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mPersistent) {
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public synchronized void callbackFinished() {
        try {
            waitForClientProxy();
            this.mClientProxy.callbackFinished();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public synchronized void reliableMessageCallbackFinished(
            int messageSequenceNumber, byte errorCode) {
        try {
            waitForClientProxy();
            this.mClientProxy.reliableMessageCallbackFinished(messageSequenceNumber, errorCode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void waitForClientProxy() {
        while (this.mClientProxy == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
