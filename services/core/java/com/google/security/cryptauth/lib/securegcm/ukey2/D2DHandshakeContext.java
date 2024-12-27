package com.google.security.cryptauth.lib.securegcm.ukey2;

public class D2DHandshakeContext {
    public final long contextPtr;

    public final class NextProtocol {
        public static final /* synthetic */ NextProtocol[] $VALUES;
        public static final NextProtocol AES_256_CBC_HMAC_SHA256;

        NextProtocol EF0;

        static {
            NextProtocol nextProtocol = new NextProtocol("AES_256_GCM_SIV", 0);
            NextProtocol nextProtocol2 = new NextProtocol("AES_256_CBC_HMAC_SHA256", 1);
            AES_256_CBC_HMAC_SHA256 = nextProtocol2;
            $VALUES = new NextProtocol[] {nextProtocol, nextProtocol2};
        }

        public static NextProtocol valueOf(String str) {
            return (NextProtocol) Enum.valueOf(NextProtocol.class, str);
        }

        public static NextProtocol[] values() {
            return (NextProtocol[]) $VALUES.clone();
        }
    }

    public final class Role {
        public static final /* synthetic */ Role[] $VALUES;
        public static final Role INITIATOR;
        public static final Role RESPONDER;

        static {
            Role role = new Role("INITIATOR", 0);
            INITIATOR = role;
            Role role2 = new Role("RESPONDER", 1);
            RESPONDER = role2;
            $VALUES = new Role[] {role, role2};
        }

        public static Role valueOf(String str) {
            return (Role) Enum.valueOf(Role.class, str);
        }

        public static Role[] values() {
            return (Role[]) $VALUES.clone();
        }
    }

    static {
        System.loadLibrary("ukey2_jni");
    }

    public D2DHandshakeContext(Role role) {
        this.contextPtr =
                create_context(
                        role == Role.INITIATOR,
                        new int[] {
                            new NextProtocol[] {NextProtocol.AES_256_CBC_HMAC_SHA256}[0].ordinal()
                        });
    }

    private static native long create_context(boolean z, int[] iArr);

    private static native byte[] get_next_handshake_message(long j) throws BadHandleException;

    private static native boolean is_handshake_complete(long j) throws BadHandleException;

    private static native void parse_handshake_message(long j, byte[] bArr)
            throws AlertException, BadHandleException, HandshakeException;

    private static native long to_connection_context(long j) throws HandshakeException;

    public final byte[] getNextHandshakeMessage() {
        return get_next_handshake_message(this.contextPtr);
    }

    public final boolean isHandshakeComplete() {
        return is_handshake_complete(this.contextPtr);
    }

    public final void parseHandshakeMessage(byte[] bArr) {
        parse_handshake_message(this.contextPtr, bArr);
    }

    public final D2DConnectionContextV1 toConnectionContext() {
        return new D2DConnectionContextV1(to_connection_context(this.contextPtr));
    }
}
