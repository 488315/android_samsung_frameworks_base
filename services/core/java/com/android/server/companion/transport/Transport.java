package com.android.server.companion.transport;

import android.companion.IOnMessageReceivedListener;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import libcore.util.EmptyArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Transport {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public final int mAssociationId;
    public CompanionTransportManager$$ExternalSyntheticLambda2 mOnTransportClosed;
    public final InputStream mRemoteIn;
    public final OutputStream mRemoteOut;
    public final SparseArray mPendingRequests = new SparseArray();
    public final AtomicInteger mNextSequence = new AtomicInteger();
    public final Map mListeners = new HashMap();

    public Transport(int i, ParcelFileDescriptor parcelFileDescriptor) {
        this.mAssociationId = i;
        this.mRemoteIn = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        this.mRemoteOut = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
    }

    public final void callback(int i, byte[] bArr) {
        int i2 = this.mAssociationId;
        if (((HashMap) this.mListeners).containsKey(Integer.valueOf(i))) {
            try {
                ((IOnMessageReceivedListener) ((HashMap) this.mListeners).get(Integer.valueOf(i)))
                        .onMessageReceived(i2, bArr);
                Slog.d(
                        "CDM_CompanionTransport",
                        "Message 0x"
                                + Integer.toHexString(i)
                                + " is received from associationId "
                                + i2
                                + ", sending data length "
                                + bArr.length
                                + " to the listener.");
            } catch (RemoteException unused) {
            }
        }
    }

    public final void handleMessage(int i, int i2, byte[] bArr) {
        CompletableFuture completableFuture;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("Received message 0x");
            sb.append(Integer.toHexString(i));
            sb.append(" sequence ");
            sb.append(i2);
            sb.append(" length ");
            sb.append(bArr.length);
            sb.append(" from association ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    sb, this.mAssociationId, "CDM_CompanionTransport");
        }
        int i3 = (-16777216) & i;
        if (i3 == 1124073472) {
            if (i == 1131446919 || i == 1132491640 || i == 1132755335) {
                callback(i, bArr);
                return;
            }
            Slog.w(
                    "CDM_CompanionTransport",
                    "Ignoring unknown message 0x" + Integer.toHexString(i));
            return;
        }
        if (i3 == 1660944384) {
            try {
                processRequest(i, i2, bArr);
                return;
            } catch (IOException e) {
                Slog.w(
                        "CDM_CompanionTransport",
                        "Failed to respond to 0x" + Integer.toHexString(i),
                        e);
                return;
            }
        }
        if (i3 != 855638016) {
            Slog.w("CDM_CompanionTransport", "Unknown message 0x" + Integer.toHexString(i));
            return;
        }
        synchronized (this.mPendingRequests) {
            completableFuture = (CompletableFuture) this.mPendingRequests.removeReturnOld(i2);
        }
        if (completableFuture == null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "Ignoring unknown sequence ", "CDM_CompanionTransport");
            return;
        }
        if (i == 863004019) {
            completableFuture.completeExceptionally(new RuntimeException("Remote failure"));
        } else {
            if (i == 864257383) {
                completableFuture.complete(bArr);
                return;
            }
            Slog.w(
                    "CDM_CompanionTransport",
                    "Ignoring unknown response 0x" + Integer.toHexString(i));
        }
    }

    public final void processRequest(int i, int i2, byte[] bArr) {
        switch (i) {
            case 1667729539:
            case 1669494629:
                callback(i, bArr);
                sendMessage(864257383, i2, EmptyArray.BYTE);
                break;
            case 1669362552:
                sendMessage(864257383, i2, bArr);
                break;
            case 1669491075:
                try {
                    callback(i, bArr);
                    sendMessage(864257383, i2, EmptyArray.BYTE);
                    break;
                } catch (Exception unused) {
                    Slog.w("CDM_CompanionTransport", "Failed to restore permissions");
                    sendMessage(863004019, i2, EmptyArray.BYTE);
                    return;
                }
            default:
                Slog.w("CDM_CompanionTransport", "Unknown request 0x" + Integer.toHexString(i));
                sendMessage(863004019, i2, EmptyArray.BYTE);
                break;
        }
    }

    public final Future sendMessage(int i, byte[] bArr) {
        CompletableFuture completableFuture = new CompletableFuture();
        int i2 = (-16777216) & i;
        if (i2 == 1124073472) {
            if (DEBUG) {
                Slog.d("CDM_CompanionTransport", "Sending a one-way message");
            }
            CompletableFuture completableFuture2 = new CompletableFuture();
            try {
                sendMessage(i, -1, bArr);
                completableFuture2.complete(null);
            } catch (IOException e) {
                completableFuture2.completeExceptionally(e);
            }
            return completableFuture2;
        }
        if (i2 != 1660944384) {
            Slog.w("CDM_CompanionTransport", "Failed to send message 0x" + Integer.toHexString(i));
            completableFuture.completeExceptionally(
                    new IllegalArgumentException(
                            "The message being sent must be either a one-way or a request."));
            return completableFuture;
        }
        if (DEBUG) {
            Slog.d("CDM_CompanionTransport", "Requesting for response");
        }
        int incrementAndGet = this.mNextSequence.incrementAndGet();
        CompletableFuture completableFuture3 = new CompletableFuture();
        synchronized (this.mPendingRequests) {
            this.mPendingRequests.put(incrementAndGet, completableFuture3);
        }
        try {
            sendMessage(i, incrementAndGet, bArr);
        } catch (IOException e2) {
            synchronized (this.mPendingRequests) {
                this.mPendingRequests.remove(incrementAndGet);
                completableFuture3.completeExceptionally(e2);
            }
        }
        return completableFuture3;
    }

    public abstract void sendMessage(int i, int i2, byte[] bArr);

    public abstract void start();

    public abstract void stop();
}
