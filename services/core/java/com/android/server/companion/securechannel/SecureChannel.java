package com.android.server.companion.securechannel;

import android.os.Build;
import android.os.Bundle;
import android.security.attestationverification.AttestationProfile;
import android.security.attestationverification.AttestationVerificationManager;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Slog;

import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.companion.transport.SecureTransport;

import libcore.io.IoUtils;
import libcore.io.Streams;

import com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;
import com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1;
import com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext;
import com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecureChannel {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public String mAlias;
    public final Callback mCallback;
    public byte[] mClientInit;
    public D2DConnectionContextV1 mConnectionContext;
    public D2DHandshakeContext mHandshakeContext;
    public volatile boolean mInProgress;
    public final InputStream mInput;
    public final OutputStream mOutput;
    public final byte[] mPreSharedKey;
    public boolean mPskVerified;
    public D2DHandshakeContext.Role mRole;
    public volatile boolean mStopped;
    public int mVerificationResult;
    public final AttestationVerifier mVerifier;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum MessageType {
        HANDSHAKE_INIT("HANDSHAKE_INIT"),
        HANDSHAKE_FINISH("HANDSHAKE_FINISH"),
        PRE_SHARED_KEY("PRE_SHARED_KEY"),
        ATTESTATION("ATTESTATION"),
        AVF_RESULT("AVF_RESULT"),
        SECURE_MESSAGE("SECURE_MESSAGE"),
        UNKNOWN("UNKNOWN");

        private final short mValue;

        MessageType(String str) {
            this.mValue = (short) r2;
        }

        public static MessageType from(short s) {
            for (MessageType messageType : values()) {
                if (s == messageType.mValue) {
                    return messageType;
                }
            }
            return UNKNOWN;
        }
    }

    public SecureChannel(
            InputStream inputStream,
            OutputStream outputStream,
            Callback callback,
            byte[] bArr,
            AttestationVerifier attestationVerifier) {
        this.mInput = inputStream;
        this.mOutput = outputStream;
        this.mCallback = callback;
        this.mPreSharedKey = bArr;
        this.mVerifier = attestationVerifier;
    }

    public static byte[] constructToken(D2DHandshakeContext.Role role, byte[] bArr) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes =
                (role == D2DHandshakeContext.Role.INITIATOR ? "Initiator" : "Responder")
                        .getBytes(StandardCharsets.UTF_8);
        return messageDigest.digest(
                ByteBuffer.allocate(bytes.length + bArr.length).put(bytes).put(bArr).array());
    }

    public final void close() {
        boolean z = DEBUG;
        if (z) {
            Slog.d("CDM_SecureChannel", "Stopping secure channel.");
        }
        this.mStopped = true;
        this.mInProgress = false;
        if (z) {
            Slog.d("CDM_SecureChannel", "Closing secure channel.");
        }
        IoUtils.closeQuietly(this.mInput);
        IoUtils.closeQuietly(this.mOutput);
        String str = this.mAlias;
        try {
            KeyStore loadKeyStore = KeyStoreUtils.loadKeyStore();
            if (loadKeyStore.containsAlias(str)) {
                loadKeyStore.deleteEntry(str);
            }
        } catch (Exception unused) {
        }
    }

    public final void exchangeAuthentication() {
        String str;
        boolean z;
        D2DHandshakeContext.Role role = D2DHandshakeContext.Role.RESPONDER;
        D2DHandshakeContext.Role role2 = D2DHandshakeContext.Role.INITIATOR;
        boolean z2 = DEBUG;
        byte[] bArr = this.mPreSharedKey;
        if (bArr != null) {
            if (z2) {
                Slog.d("CDM_SecureChannel", "Exchanging pre-shared keys.");
            }
            MessageType messageType = MessageType.PRE_SHARED_KEY;
            sendMessage(messageType, constructToken(this.mRole, bArr));
            boolean equals =
                    Arrays.equals(
                            readMessage(messageType),
                            constructToken(this.mRole == role2 ? role : role2, bArr));
            this.mPskVerified = equals;
            if (!equals) {
                throw new SecureChannelException("Failed to verify the hash of pre-shared key.");
            }
            if (z2) {
                Slog.d("CDM_SecureChannel", "The pre-shared key was successfully authenticated.");
            }
        }
        AttestationVerifier attestationVerifier = this.mVerifier;
        if (attestationVerifier != null) {
            if (this.mVerificationResult == 1) {
                Slog.d("CDM_SecureChannel", "Remote attestation was already verified.");
                return;
            }
            if (z2) {
                Slog.d("CDM_SecureChannel", "Exchanging device attestation.");
            }
            if (this.mAlias == null) {
                do {
                    str = "secure-channel-" + UUID.randomUUID();
                    try {
                        z = KeyStoreUtils.loadKeyStore().containsAlias(str);
                    } catch (GeneralSecurityException unused) {
                        z = false;
                    }
                } while (z);
                this.mAlias = str;
            }
            KeyGenParameterSpec build =
                    new KeyGenParameterSpec.Builder(this.mAlias, 12)
                            .setAttestationChallenge(
                                    constructToken(
                                            this.mRole, this.mConnectionContext.getSessionUnique()))
                            .setDigests("SHA-256")
                            .build();
            KeyPairGenerator keyPairGenerator =
                    KeyPairGenerator.getInstance("EC", "AndroidKeyStore");
            keyPairGenerator.initialize(build);
            keyPairGenerator.generateKeyPair();
            Certificate[] certificateChain =
                    KeyStoreUtils.loadKeyStore().getCertificateChain(this.mAlias);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (Certificate certificate : certificateChain) {
                byteArrayOutputStream.writeBytes(certificate.getEncoded());
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            MessageType messageType2 = MessageType.ATTESTATION;
            sendMessage(messageType2, byteArray);
            byte[] readMessage = readMessage(messageType2);
            if (this.mRole != role2) {
                role = role2;
            }
            byte[] constructToken =
                    constructToken(role, this.mConnectionContext.getSessionUnique());
            Bundle bundle = new Bundle();
            bundle.putByteArray("localbinding.challenge", constructToken);
            bundle.putBoolean("android.key_owned_by_system", true);
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ((AttestationVerificationManager)
                            attestationVerifier.mContext.getSystemService(
                                    AttestationVerificationManager.class))
                    .verifyAttestation(
                            new AttestationProfile(3),
                            3,
                            bundle,
                            readMessage,
                            new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                            new BiConsumer() { // from class:
                                               // com.android.server.companion.securechannel.AttestationVerifier$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    AtomicInteger atomicInteger2 = atomicInteger;
                                    CountDownLatch countDownLatch2 = countDownLatch;
                                    atomicInteger2.set(((Integer) obj).intValue());
                                    countDownLatch2.countDown();
                                }
                            });
            try {
                if (!countDownLatch.await(10L, TimeUnit.SECONDS)) {
                    throw new SecureChannelException("Attestation verification timed out.");
                }
                this.mVerificationResult = atomicInteger.get();
                byte[] array = ByteBuffer.allocate(4).putInt(this.mVerificationResult).array();
                MessageType messageType3 = MessageType.AVF_RESULT;
                sendMessage(messageType3, array);
                if (ByteBuffer.wrap(readMessage(messageType3)).getInt() != 1) {
                    throw new SecureChannelException(
                            "Remote device failed to verify local attestation.");
                }
                if (this.mVerificationResult != 1) {
                    throw new SecureChannelException("Failed to verify remote attestation.");
                }
                if (z2) {
                    Slog.d("CDM_SecureChannel", "Remote attestation was successfully verified.");
                }
            } catch (InterruptedException e) {
                throw new SecureChannelException("Attestation verification was interrupted", e);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void exchangeHandshake() {
        if (this.mConnectionContext != null) {
            Slog.d("CDM_SecureChannel", "Ukey2 handshake is already completed.");
            return;
        }
        byte[] readMessage = readMessage(MessageType.HANDSHAKE_INIT);
        this.mInProgress = true;
        ByteBuffer wrap = ByteBuffer.wrap(readMessage);
        int i = 0;
        byte b = wrap.get() == 0;
        int remaining = wrap.remaining();
        byte[] bArr = new byte[remaining];
        wrap.get(bArr);
        if (this.mHandshakeContext != null && b != false) {
            Slog.w(
                    "CDM_SecureChannel",
                    "Detected a Ukey2 handshake role collision. Negotiating a role.");
            byte[] bArr2 = this.mClientInit;
            if (bArr2 != bArr) {
                if (bArr2.length != remaining) {
                    i = bArr2.length - remaining;
                } else {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= bArr2.length) {
                            break;
                        }
                        byte b2 = bArr2[i2];
                        byte b3 = bArr[i2];
                        if (b2 != b3) {
                            i = b2 - b3;
                            break;
                        }
                        i2++;
                    }
                }
            }
            if (i < 0) {
                Slog.d("CDM_SecureChannel", "Assigned: Responder");
                this.mHandshakeContext = null;
            } else {
                Slog.d("CDM_SecureChannel", "Assigned: Initiator; Discarding received Client Init");
                ByteBuffer wrap2 = ByteBuffer.wrap(readMessage(MessageType.HANDSHAKE_INIT));
                if (wrap2.get() == 0) {
                    throw new HandshakeException(
                            "Failed to resolve Ukey2 handshake role collision.");
                }
                bArr = new byte[wrap2.remaining()];
                wrap2.get(bArr);
            }
        }
        if (this.mHandshakeContext == null) {
            D2DHandshakeContext.Role role = D2DHandshakeContext.Role.RESPONDER;
            this.mRole = role;
            this.mHandshakeContext = new D2DHandshakeContext(role);
            boolean z = DEBUG;
            if (z) {
                Slog.d("CDM_SecureChannel", "Receiving Ukey2 Client Init message");
            }
            this.mHandshakeContext.parseHandshakeMessage(bArr);
            if (z) {
                Slog.d("CDM_SecureChannel", "Sending Ukey2 Server Init message");
            }
            MessageType messageType = MessageType.HANDSHAKE_INIT;
            byte[] nextHandshakeMessage = this.mHandshakeContext.getNextHandshakeMessage();
            sendMessage(
                    messageType,
                    ByteBuffer.allocate(nextHandshakeMessage.length + 1)
                            .put(
                                    (byte)
                                            (1
                                                    ^ (D2DHandshakeContext.Role.INITIATOR.equals(
                                                                    this.mRole)
                                                            ? 1
                                                            : 0)))
                            .put(nextHandshakeMessage)
                            .array());
            if (z) {
                Slog.d("CDM_SecureChannel", "Receiving Ukey2 Client Finish message");
            }
            this.mHandshakeContext.parseHandshakeMessage(readMessage(MessageType.HANDSHAKE_FINISH));
        } else {
            boolean z2 = DEBUG;
            if (z2) {
                Slog.d("CDM_SecureChannel", "Receiving Ukey2 Server Init message");
            }
            this.mHandshakeContext.parseHandshakeMessage(bArr);
            if (z2) {
                Slog.d("CDM_SecureChannel", "Sending Ukey2 Client Finish message");
            }
            sendMessage(
                    MessageType.HANDSHAKE_FINISH, this.mHandshakeContext.getNextHandshakeMessage());
        }
        if (!this.mHandshakeContext.isHandshakeComplete()) {
            Slog.e("CDM_SecureChannel", "Failed to complete Ukey2 Handshake");
            throw new IllegalStateException("Ukey2 Handshake did not complete as expected.");
        }
        if (DEBUG) {
            Slog.d("CDM_SecureChannel", "Ukey2 Handshake completed successfully");
        }
        this.mConnectionContext = this.mHandshakeContext.toConnectionContext();
    }

    public final void initiateHandshake() {
        if (this.mConnectionContext != null) {
            Slog.d("CDM_SecureChannel", "Ukey2 handshake is already completed.");
            return;
        }
        D2DHandshakeContext.Role role = D2DHandshakeContext.Role.INITIATOR;
        this.mRole = role;
        D2DHandshakeContext d2DHandshakeContext = new D2DHandshakeContext(role);
        this.mHandshakeContext = d2DHandshakeContext;
        this.mClientInit = d2DHandshakeContext.getNextHandshakeMessage();
        if (DEBUG) {
            Slog.d("CDM_SecureChannel", "Sending Ukey2 Client Init message");
        }
        MessageType messageType = MessageType.HANDSHAKE_INIT;
        byte[] bArr = this.mClientInit;
        sendMessage(
                messageType,
                ByteBuffer.allocate(bArr.length + 1)
                        .put((byte) (!role.equals(this.mRole) ? 1 : 0))
                        .put(bArr)
                        .array());
    }

    public final boolean isSecured() {
        if (this.mConnectionContext == null) {
            return false;
        }
        return this.mPskVerified || this.mVerificationResult == 1;
    }

    public final byte[] readMessage(MessageType messageType) {
        if (DEBUG) {
            if (isSecured()) {
                Slog.d("CDM_SecureChannel", "Waiting to receive next secure message.");
            } else {
                Slog.d("CDM_SecureChannel", "Waiting to receive next " + messageType + " message.");
            }
        }
        synchronized (this.mInput) {
            try {
                byte[] bArr = new byte[6];
                Streams.readFully(this.mInput, bArr);
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                int i = wrap.getInt();
                short s = wrap.getShort();
                if (i != 1) {
                    Streams.skipByReading(this.mInput, Long.MAX_VALUE);
                    throw new SecureChannelException(
                            "Secure channel version mismatch. Currently on version 1. Skipping rest"
                                + " of data.");
                }
                if (s != messageType.mValue) {
                    Streams.skipByReading(this.mInput, Long.MAX_VALUE);
                    throw new SecureChannelException(
                            "Unexpected message type. Expected "
                                    + messageType.name()
                                    + "; Found "
                                    + MessageType.from(s).name()
                                    + ". Skipping rest of data.");
                }
                byte[] bArr2 = new byte[4];
                Streams.readFully(this.mInput, bArr2);
                try {
                    byte[] bArr3 = new byte[ByteBuffer.wrap(bArr2).getInt()];
                    Streams.readFully(this.mInput, bArr3);
                    if (messageType == MessageType.HANDSHAKE_INIT
                            || messageType == MessageType.HANDSHAKE_FINISH) {
                        return bArr3;
                    }
                    return this.mConnectionContext.decodeMessageFromPeer(bArr3, bArr);
                } catch (OutOfMemoryError e) {
                    Streams.skipByReading(this.mInput, Long.MAX_VALUE);
                    throw new SecureChannelException("Payload is too large.", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void receiveSecureMessage() {
        boolean isSecured = isSecured();
        Callback callback = this.mCallback;
        if (!isSecured) {
            Slog.d(
                    "CDM_SecureChannel",
                    "Received a message without a secure connection. Message will be ignored.");
            IllegalStateException illegalStateException =
                    new IllegalStateException("Connection is not secure.");
            SecureTransport secureTransport = (SecureTransport) callback;
            secureTransport.getClass();
            Slog.e(
                    "CDM_CompanionTransport",
                    "Secure transport encountered an error.",
                    illegalStateException);
            if (secureTransport.mSecureChannel.mStopped) {
                secureTransport.close();
                return;
            }
            return;
        }
        try {
            byte[] readMessage = readMessage(MessageType.SECURE_MESSAGE);
            SecureTransport secureTransport2 = (SecureTransport) callback;
            secureTransport2.getClass();
            ByteBuffer wrap = ByteBuffer.wrap(readMessage);
            int i = wrap.getInt();
            int i2 = wrap.getInt();
            byte[] bArr = new byte[wrap.getInt()];
            wrap.get(bArr);
            try {
                secureTransport2.handleMessage(i, i2, bArr);
            } catch (IOException unused) {
            }
        } catch (SecureChannelException e) {
            Slog.w("CDM_SecureChannel", "Ignoring received message.", e);
        }
    }

    public final void sendMessage(MessageType messageType, byte[] bArr) {
        synchronized (this.mOutput) {
            try {
                byte[] array =
                        ByteBuffer.allocate(6).putInt(1).putShort(messageType.mValue).array();
                if (messageType != MessageType.HANDSHAKE_INIT
                        && messageType != MessageType.HANDSHAKE_FINISH) {
                    bArr = this.mConnectionContext.encodeMessageToPeer(bArr, array);
                }
                this.mOutput.write(array);
                this.mOutput.write(ByteBuffer.allocate(4).putInt(bArr.length).array());
                this.mOutput.write(bArr);
                this.mOutput.flush();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendSecureMessage(byte[] bArr) {
        if (!isSecured()) {
            Slog.d("CDM_SecureChannel", "Cannot send a message without a secure connection.");
            throw new IllegalStateException("Channel is not secured yet.");
        }
        try {
            sendMessage(MessageType.SECURE_MESSAGE, bArr);
        } catch (BadHandleException e) {
            throw new SecureChannelException("Failed to encrypt data.", e);
        }
    }
}
