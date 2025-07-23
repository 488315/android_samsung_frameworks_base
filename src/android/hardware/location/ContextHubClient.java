package android.hardware.location;

import android.annotation.SystemApi;
import android.hardware.location.ContextHubTransaction;
import android.os.RemoteException;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@SystemApi
/* loaded from: classes2.dex */
public class ContextHubClient implements Closeable {
    private static final String TAG = "ContextHubClient";
    private final ContextHubInfo mAttachedHub;
    private final CloseGuard mCloseGuard;
    private final boolean mPersistent;
    private IContextHubClient mClientProxy = null;
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private Integer mId = null;

    ContextHubClient(ContextHubInfo contextHubInfo, boolean z) {
        this.mAttachedHub = contextHubInfo;
        this.mPersistent = z;
        if (z) {
            this.mCloseGuard = null;
            return;
        }
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        closeGuard.open("ContextHubClient.close");
    }

    synchronized void setClientProxy(IContextHubClient iContextHubClient) {
        Objects.requireNonNull(iContextHubClient, "IContextHubClient cannot be null");
        if (this.mClientProxy != null) {
            throw new IllegalStateException("Cannot change client proxy multiple times");
        }
        this.mClientProxy = iContextHubClient;
        try {
            this.mId = Integer.valueOf(iContextHubClient.getId());
            notifyAll();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubInfo getAttachedHub() {
        return this.mAttachedHub;
    }

    public int getId() {
        Integer num = this.mId;
        if (num == null) {
            throw new IllegalStateException("ID was not set");
        }
        return num.intValue() & 65535;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.mIsClosed.getAndSet(true)) {
            return;
        }
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            closeGuard.close();
        }
        try {
            this.mClientProxy.close();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) {
        return doSendMessageToNanoApp(nanoAppMessage, null);
    }

    public ContextHubTransaction<Void> sendReliableMessageToNanoApp(NanoAppMessage nanoAppMessage) {
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<>(5);
        if (!this.mAttachedHub.supportsReliableMessages() || nanoAppMessage.isBroadcastMessage()) {
            contextHubTransaction.setResponse(new ContextHubTransaction.Response<>(9, null));
            return contextHubTransaction;
        }
        int doSendMessageToNanoApp = doSendMessageToNanoApp(nanoAppMessage, ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction));
        if (doSendMessageToNanoApp != 0) {
            contextHubTransaction.setResponse(new ContextHubTransaction.Response<>(doSendMessageToNanoApp, null));
        }
        return contextHubTransaction;
    }

    private int doSendMessageToNanoApp(NanoAppMessage nanoAppMessage, IContextHubTransactionCallback iContextHubTransactionCallback) {
        Objects.requireNonNull(nanoAppMessage, "NanoAppMessage cannot be null");
        int maxPacketLengthBytes = this.mAttachedHub.getMaxPacketLengthBytes();
        byte[] messageBody = nanoAppMessage.getMessageBody();
        if (messageBody != null && messageBody.length > maxPacketLengthBytes) {
            Log.e(TAG, "Message (%d bytes) exceeds max payload length (%d bytes)".formatted(Integer.valueOf(messageBody.length), Integer.valueOf(maxPacketLengthBytes)));
            return 2;
        }
        try {
            if (iContextHubTransactionCallback == null) {
                return this.mClientProxy.sendMessageToNanoApp(nanoAppMessage);
            }
            return this.mClientProxy.sendReliableMessageToNanoApp(nanoAppMessage, iContextHubTransactionCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
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

    public synchronized void reliableMessageCallbackFinished(int i, byte b) {
        try {
            waitForClientProxy();
            this.mClientProxy.reliableMessageCallbackFinished(i, b);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void waitForClientProxy() {
        while (this.mClientProxy == null) {
            try {
                wait();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
