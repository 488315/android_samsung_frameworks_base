package com.android.server.companion.transport;

import android.companion.IOnMessageReceivedListener;
import android.content.Context;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.util.EmptyArray;

/* loaded from: classes.dex */
public abstract class Transport {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public final int mAssociationId;
    public final Context mContext;
    public final ParcelFileDescriptor mFd;
    public OnTransportClosedListener mOnTransportClosed;
    public final InputStream mRemoteIn;
    public final OutputStream mRemoteOut;
    public final SparseArray mPendingRequests = new SparseArray();
    public final AtomicInteger mNextSequence = new AtomicInteger();
    public final Map mListeners = new HashMap();

    public interface OnTransportClosedListener {
        void onClosed(Transport transport);
    }

    public static boolean isRequest(int i) {
        return (i & (-16777216)) == 1660944384;
    }

    public static boolean isResponse(int i) {
        return (i & (-16777216)) == 855638016;
    }

    public abstract void sendMessage(int i, int i2, byte[] bArr);

    public abstract void start();

    public abstract void stop();

    public Transport(int i, ParcelFileDescriptor parcelFileDescriptor, Context context) {
        this.mAssociationId = i;
        this.mFd = parcelFileDescriptor;
        this.mRemoteIn = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        this.mRemoteOut = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        this.mContext = context;
    }

    public void addListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
        this.mListeners.put(Integer.valueOf(i), iOnMessageReceivedListener);
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    public void close() {
        OnTransportClosedListener onTransportClosedListener = this.mOnTransportClosed;
        if (onTransportClosedListener != null) {
            onTransportClosedListener.onClosed(this);
        }
    }

    public Future requestForResponse(int i, byte[] bArr) {
        if (DEBUG) {
            Slog.d("CDM_CompanionTransport", "Requesting for response");
        }
        int incrementAndGet = this.mNextSequence.incrementAndGet();
        CompletableFuture completableFuture = new CompletableFuture();
        synchronized (this.mPendingRequests) {
            this.mPendingRequests.put(incrementAndGet, completableFuture);
        }
        try {
            sendMessage(i, incrementAndGet, bArr);
        } catch (IOException e) {
            synchronized (this.mPendingRequests) {
                this.mPendingRequests.remove(incrementAndGet);
                completableFuture.completeExceptionally(e);
            }
        }
        return completableFuture;
    }

    public final void handleMessage(int i, int i2, byte[] bArr) {
        if (DEBUG) {
            Slog.d("CDM_CompanionTransport", "Received message 0x" + Integer.toHexString(i) + " sequence " + i2 + " length " + bArr.length + " from association " + this.mAssociationId);
        }
        if (isRequest(i)) {
            try {
                processRequest(i, i2, bArr);
                return;
            } catch (IOException e) {
                Slog.w("CDM_CompanionTransport", "Failed to respond to 0x" + Integer.toHexString(i), e);
                return;
            }
        }
        if (isResponse(i)) {
            processResponse(i, i2, bArr);
            return;
        }
        Slog.w("CDM_CompanionTransport", "Unknown message 0x" + Integer.toHexString(i));
    }

    public final void processRequest(int i, int i2, byte[] bArr) {
        if (i == 1667729539) {
            callback(i, bArr);
            sendMessage(864257383, i2, EmptyArray.BYTE);
            return;
        }
        if (i == 1669362552) {
            sendMessage(864257383, i2, bArr);
            return;
        }
        if (i == 1669491075) {
            try {
                callback(i, bArr);
                sendMessage(864257383, i2, EmptyArray.BYTE);
                return;
            } catch (Exception unused) {
                Slog.w("CDM_CompanionTransport", "Failed to restore permissions");
                sendMessage(863004019, i2, EmptyArray.BYTE);
                return;
            }
        }
        Slog.w("CDM_CompanionTransport", "Unknown request 0x" + Integer.toHexString(i));
        sendMessage(863004019, i2, EmptyArray.BYTE);
    }

    public final void callback(int i, byte[] bArr) {
        if (this.mListeners.containsKey(Integer.valueOf(i))) {
            try {
                ((IOnMessageReceivedListener) this.mListeners.get(Integer.valueOf(i))).onMessageReceived(getAssociationId(), bArr);
                Slog.i("CDM_CompanionTransport", "Message 0x" + Integer.toHexString(i) + " is received from associationId " + this.mAssociationId + ", sending data length " + bArr.length + " to the listener.");
            } catch (RemoteException unused) {
            }
        }
    }

    public final void processResponse(int i, int i2, byte[] bArr) {
        CompletableFuture completableFuture;
        synchronized (this.mPendingRequests) {
            completableFuture = (CompletableFuture) this.mPendingRequests.removeReturnOld(i2);
        }
        if (completableFuture == null) {
            Slog.w("CDM_CompanionTransport", "Ignoring unknown sequence " + i2);
            return;
        }
        if (i == 863004019) {
            completableFuture.completeExceptionally(new RuntimeException("Remote failure"));
            return;
        }
        if (i == 864257383) {
            completableFuture.complete(bArr);
            return;
        }
        Slog.w("CDM_CompanionTransport", "Ignoring unknown response 0x" + Integer.toHexString(i));
    }

    public void setOnTransportClosedListener(OnTransportClosedListener onTransportClosedListener) {
        this.mOnTransportClosed = onTransportClosedListener;
    }
}
