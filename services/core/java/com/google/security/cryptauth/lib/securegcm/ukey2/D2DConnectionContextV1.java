package com.google.security.cryptauth.lib.securegcm.ukey2;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class D2DConnectionContextV1 {
    public final long contextPtr;

    static {
        String property = System.getProperty("java.library.path");
        if (property == null) {
            throw new RuntimeException("Path isn't set.");
        }
        List of = List.of((Object[]) property.split(";"));
        final PrintStream printStream = System.out;
        Objects.requireNonNull(printStream);
        of.forEach(
                new Consumer() { // from class:
                                 // com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        printStream.println((String) obj);
                    }
                });
        System.loadLibrary("ukey2_jni");
    }

    public D2DConnectionContextV1(long j) {
        this.contextPtr = j;
    }

    private static native byte[] decode_message_from_peer(long j, byte[] bArr, byte[] bArr2)
            throws CryptoException;

    private static native byte[] encode_message_to_peer(long j, byte[] bArr, byte[] bArr2)
            throws BadHandleException;

    private static native byte[] get_session_unique(long j) throws BadHandleException;

    public final byte[] decodeMessageFromPeer(byte[] bArr, byte[] bArr2) {
        return decode_message_from_peer(this.contextPtr, bArr, bArr2);
    }

    public final byte[] encodeMessageToPeer(byte[] bArr, byte[] bArr2) {
        return encode_message_to_peer(this.contextPtr, bArr, bArr2);
    }

    public final byte[] getSessionUnique() {
        return get_session_unique(this.contextPtr);
    }
}
