package com.android.server.companion.transport;

import android.companion.AssociationInfo;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.companion.securechannel.AttestationVerifier;
import com.android.server.companion.securechannel.SecureChannel;
import com.android.server.companion.securechannel.SecureChannelException;

import com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecureTransport extends Transport implements SecureChannel.Callback {
    public final BlockingQueue mRequestQueue;
    public final SecureChannel mSecureChannel;
    public volatile boolean mShouldProcessRequests;

    public SecureTransport(int i, ParcelFileDescriptor parcelFileDescriptor, Context context) {
        super(i, parcelFileDescriptor);
        this.mShouldProcessRequests = false;
        this.mRequestQueue = new ArrayBlockingQueue(500);
        this.mSecureChannel =
                new SecureChannel(
                        this.mRemoteIn,
                        this.mRemoteOut,
                        this,
                        null,
                        new AttestationVerifier(context));
    }

    public SecureTransport(int i, ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        super(i, parcelFileDescriptor);
        this.mShouldProcessRequests = false;
        this.mRequestQueue = new ArrayBlockingQueue(500);
        this.mSecureChannel = new SecureChannel(this.mRemoteIn, this.mRemoteOut, this, bArr, null);
    }

    public final void close() {
        this.mSecureChannel.close();
        this.mShouldProcessRequests = false;
        CompanionTransportManager$$ExternalSyntheticLambda2
                companionTransportManager$$ExternalSyntheticLambda2 = this.mOnTransportClosed;
        if (companionTransportManager$$ExternalSyntheticLambda2 != null) {
            CompanionTransportManager companionTransportManager =
                    companionTransportManager$$ExternalSyntheticLambda2.f$0;
            companionTransportManager.getClass();
            AssociationInfo associationById =
                    companionTransportManager.mAssociationStore.getAssociationById(
                            this.mAssociationId);
            if (associationById != null) {
                companionTransportManager.detachSystemDataTransport(associationById.getId());
            }
        }
    }

    @Override // com.android.server.companion.transport.Transport
    public final void sendMessage(int i, int i2, byte[] bArr) {
        if (!this.mShouldProcessRequests) {
            Slog.d("CDM_CompanionTransport", "Establishing secure connection.");
            try {
                SecureChannel secureChannel = this.mSecureChannel;
                if (secureChannel.isSecured()) {
                    Slog.d("CDM_SecureChannel", "Channel is already secure.");
                } else if (secureChannel.mInProgress) {
                    Slog.w(
                            "CDM_SecureChannel",
                            "Channel has already started establishing secure connection.");
                } else {
                    try {
                        secureChannel.mInProgress = true;
                        secureChannel.initiateHandshake();
                    } catch (BadHandleException e) {
                        throw new SecureChannelException(
                                "Failed to initiate handshake protocol.", e);
                    }
                }
            } catch (Exception e2) {
                Slog.e(
                        "CDM_CompanionTransport",
                        "Failed to initiate secure channel handshake.",
                        e2);
                close();
            }
        }
        if (Transport.DEBUG) {
            StringBuilder sb = new StringBuilder("Queueing message 0x");
            sb.append(Integer.toHexString(i));
            sb.append(" sequence ");
            sb.append(i2);
            sb.append(" length ");
            sb.append(bArr.length);
            sb.append(" to association ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    sb, this.mAssociationId, "CDM_CompanionTransport");
        }
        try {
            ((ArrayBlockingQueue) this.mRequestQueue)
                    .add(
                            ByteBuffer.allocate(bArr.length + 12)
                                    .putInt(i)
                                    .putInt(i2)
                                    .putInt(bArr.length)
                                    .put(bArr)
                                    .array());
        } catch (IllegalStateException e3) {
            Slog.w(
                    "CDM_CompanionTransport",
                    "Failed to queue message 0x"
                            + Integer.toHexString(i)
                            + " . Request buffer is full; detaching transport.",
                    e3);
            close();
        }
    }

    @Override // com.android.server.companion.transport.Transport
    public final void start() {
        final SecureChannel secureChannel = this.mSecureChannel;
        if (SecureChannel.DEBUG) {
            secureChannel.getClass();
            Slog.d("CDM_SecureChannel", "Starting secure channel.");
        }
        secureChannel.mStopped = false;
        new Thread(
                        new Runnable() { // from class:
                            // com.android.server.companion.securechannel.SecureChannel$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecureChannel secureChannel2 = SecureChannel.this;
                                secureChannel2.getClass();
                                try {
                                    secureChannel2.exchangeHandshake();
                                    secureChannel2.exchangeAuthentication();
                                    secureChannel2.mInProgress = false;
                                    final SecureTransport secureTransport =
                                            (SecureTransport) secureChannel2.mCallback;
                                    secureTransport.mShouldProcessRequests = true;
                                    Slog.d(
                                            "CDM_CompanionTransport",
                                            "Secure connection established.");
                                    new Thread(
                                                    new Runnable() { // from class:
                                                        // com.android.server.companion.transport.SecureTransport$$ExternalSyntheticLambda0
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            SecureTransport secureTransport2 =
                                                                    SecureTransport.this;
                                                            while (secureTransport2
                                                                    .mShouldProcessRequests) {
                                                                try {
                                                                    secureTransport2.mSecureChannel
                                                                            .sendSecureMessage(
                                                                                    (byte[])
                                                                                            ((ArrayBlockingQueue)
                                                                                                            secureTransport2
                                                                                                                    .mRequestQueue)
                                                                                                    .take());
                                                                } catch (Exception e) {
                                                                    Slog.e(
                                                                            "CDM_CompanionTransport",
                                                                            "Failed to send secure"
                                                                                + " message.",
                                                                            e);
                                                                    secureTransport2.close();
                                                                }
                                                            }
                                                        }
                                                    })
                                            .start();
                                    while (!secureChannel2.mStopped) {
                                        secureChannel2.receiveSecureMessage();
                                    }
                                } catch (Exception e) {
                                    if (secureChannel2.mStopped) {
                                        return;
                                    }
                                    Slog.e(
                                            "CDM_SecureChannel",
                                            "Secure channel encountered an error.",
                                            e);
                                    secureChannel2.close();
                                    SecureTransport secureTransport2 =
                                            (SecureTransport) secureChannel2.mCallback;
                                    secureTransport2.getClass();
                                    Slog.e(
                                            "CDM_CompanionTransport",
                                            "Secure transport encountered an error.",
                                            e);
                                    if (secureTransport2.mSecureChannel.mStopped) {
                                        secureTransport2.close();
                                    }
                                }
                            }
                        })
                .start();
    }

    @Override // com.android.server.companion.transport.Transport
    public final void stop() {
        SecureChannel secureChannel = this.mSecureChannel;
        if (SecureChannel.DEBUG) {
            secureChannel.getClass();
            Slog.d("CDM_SecureChannel", "Stopping secure channel.");
        }
        secureChannel.mStopped = true;
        secureChannel.mInProgress = false;
        this.mShouldProcessRequests = false;
    }

    public final String toString() {
        return "SecureTransport{mAssociationId="
                + this.mAssociationId
                + ", mSecureChannel="
                + this.mSecureChannel
                + '}';
    }
}
