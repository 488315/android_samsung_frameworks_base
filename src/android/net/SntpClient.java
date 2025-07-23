package android.net;

import android.net.sntp.Duration64;
import android.net.sntp.Timestamp64;
import android.util.Log;
import android.util.Slog;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SntpClient {
    private static final boolean DBG = true;
    private static final int NTP_LEAP_NOSYNC = 3;
    private static final int NTP_MODE_BROADCAST = 5;
    private static final int NTP_MODE_CLIENT = 3;
    private static final int NTP_MODE_SERVER = 4;
    private static final int NTP_PACKET_SIZE = 48;
    private static final int NTP_STRATUM_DEATH = 0;
    private static final int NTP_STRATUM_MAX = 15;
    private static final int NTP_VERSION = 3;
    private static final int ORIGINATE_TIME_OFFSET = 24;
    private static final int RECEIVE_TIME_OFFSET = 32;
    private static final int REFERENCE_TIME_OFFSET = 16;
    public static final int STANDARD_NTP_PORT = 123;
    private static final String TAG = "SntpClient";
    private static final int TRANSMIT_TIME_OFFSET = 40;
    private long mClockOffset;
    private long mNtpTime;
    private long mNtpTimeReference;
    private final Random mRandom;
    private long mRoundTripTime;
    private InetSocketAddress mServerSocketAddress;
    private final Supplier<Instant> mSystemTimeSupplier;

    private static class InvalidServerReplyException extends Exception {
        public InvalidServerReplyException(String str) {
            super(str);
        }
    }

    public SntpClient() {
        this(new Supplier() { // from class: android.net.SntpClient$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Instant.now();
            }
        }, defaultRandom());
    }

    public SntpClient(Supplier<Instant> supplier, Random random) {
        this.mSystemTimeSupplier = (Supplier) Objects.requireNonNull(supplier);
        this.mRandom = (Random) Objects.requireNonNull(random);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x006f A[Catch: UnknownHostException -> 0x007c, TRY_LEAVE, TryCatch #2 {UnknownHostException -> 0x007c, blocks: (B:25:0x0067, B:26:0x006c, B:28:0x006f), top: B:24:0x0067 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean requestTime(java.lang.String r8, int r9, int r10, android.net.Network r11) {
        /*
            r7 = this;
            java.lang.String r0 = "SntpClient"
            java.lang.String r1 = "timeout set by MDM: "
            java.lang.String r2 = "host set by MDM: "
            android.sec.enterprise.IEDMProxy r3 = android.sec.enterprise.EnterpriseDeviceManager.EDMProxyServiceHelper.getService()     // Catch: android.os.RemoteException -> L4d
            if (r3 == 0) goto L62
            boolean r4 = r3.shallForceNtpMdmValues()     // Catch: android.os.RemoteException -> L4d
            if (r4 == 0) goto L62
            java.lang.String r4 = r3.getNtpServer()     // Catch: android.os.RemoteException -> L4d
            long r5 = r3.getNtpTimeout()     // Catch: android.os.RemoteException -> L4d
            int r3 = (int) r5     // Catch: android.os.RemoteException -> L4d
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch: android.os.RemoteException -> L4d
            if (r5 != 0) goto L35
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L33
            r8.<init>(r2)     // Catch: android.os.RemoteException -> L33
            r8.append(r4)     // Catch: android.os.RemoteException -> L33
            java.lang.String r8 = r8.toString()     // Catch: android.os.RemoteException -> L33
            android.util.Log.d(r0, r8)     // Catch: android.os.RemoteException -> L33
            r8 = r4
            goto L35
        L33:
            r8 = move-exception
            goto L50
        L35:
            if (r3 == 0) goto L62
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L48
            r10.<init>(r1)     // Catch: android.os.RemoteException -> L48
            r10.append(r3)     // Catch: android.os.RemoteException -> L48
            java.lang.String r10 = r10.toString()     // Catch: android.os.RemoteException -> L48
            android.util.Log.d(r0, r10)     // Catch: android.os.RemoteException -> L48
            r10 = r3
            goto L62
        L48:
            r10 = move-exception
            r4 = r8
            r8 = r10
            r10 = r3
            goto L50
        L4d:
            r1 = move-exception
            r4 = r8
            r8 = r1
        L50:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Remote Exception: "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r8 = r1.toString()
            android.util.Log.d(r0, r8)
            r8 = r4
        L62:
            android.net.Network r11 = r11.getPrivateDnsBypassingCopy()
            r1 = 0
            java.net.InetAddress[] r2 = r11.getAllByName(r8)     // Catch: java.net.UnknownHostException -> L7c
            r3 = r1
        L6c:
            int r4 = r2.length     // Catch: java.net.UnknownHostException -> L7c
            if (r3 >= r4) goto L95
            r4 = r2[r3]     // Catch: java.net.UnknownHostException -> L7c
            boolean r4 = r7.requestTime(r4, r9, r10, r11)     // Catch: java.net.UnknownHostException -> L7c
            if (r4 == 0) goto L79
            r7 = 1
            return r7
        L79:
            int r3 = r3 + 1
            goto L6c
        L7c:
            r7 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Unknown host: "
            r9.<init>(r10)
            r9.append(r8)
            java.lang.String r9 = r9.toString()
            android.util.Log.w(r0, r9)
            java.lang.String r7 = r7.toString()
            android.net.EventLogTags.writeNtpFailure(r8, r7)
        L95:
            java.lang.String r7 = "request time failed"
            android.util.Log.d(r0, r7)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.SntpClient.requestTime(java.lang.String, int, int, android.net.Network):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0151  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean requestTime(java.net.InetAddress r28, int r29, int r30, android.net.Network r31) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.SntpClient.requestTime(java.net.InetAddress, int, int, android.net.Network):boolean");
    }

    public static Duration calculateClockOffset(Timestamp64 timestamp64, Timestamp64 timestamp642, Timestamp64 timestamp643, Timestamp64 timestamp644) {
        return Duration64.between(timestamp64, timestamp642).plus(Duration64.between(timestamp644, timestamp643)).dividedBy(2L);
    }

    @Deprecated
    public boolean requestTime(String str, int i) {
        Log.w(TAG, "Shame on you for calling the hidden API requestTime()!");
        return false;
    }

    public long getClockOffset() {
        return this.mClockOffset;
    }

    public long getNtpTime() {
        return this.mNtpTime;
    }

    public long getNtpTimeReference() {
        return this.mNtpTimeReference;
    }

    public long getRoundTripTime() {
        return this.mRoundTripTime;
    }

    public InetSocketAddress getServerSocketAddress() {
        return this.mServerSocketAddress;
    }

    private static void checkValidServerReply(byte b, byte b2, int i, Timestamp64 timestamp64, Timestamp64 timestamp642, Timestamp64 timestamp643, Timestamp64 timestamp644) throws InvalidServerReplyException {
        if (b == 3) {
            throw new InvalidServerReplyException("unsynchronized server");
        }
        if (b2 != 4 && b2 != 5) {
            throw new InvalidServerReplyException("untrusted mode: " + ((int) b2));
        }
        if (i == 0 || i > 15) {
            throw new InvalidServerReplyException("untrusted stratum: " + i);
        }
        if (!timestamp643.equals(timestamp644)) {
            throw new InvalidServerReplyException("originateTimestamp != randomizedRequestTimestamp");
        }
        if (timestamp64.equals(Timestamp64.ZERO)) {
            throw new InvalidServerReplyException("zero transmitTimestamp");
        }
        if (timestamp642.equals(Timestamp64.ZERO)) {
            throw new InvalidServerReplyException("zero referenceTimestamp");
        }
    }

    private long readUnsigned32(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        int i3 = bArr[i + 1] & 255;
        int i4 = bArr[i + 2] & 255;
        return ((i3 << 16) | (i2 << 24) | (i4 << 8) | (bArr[i + 3] & 255)) & 4294967295L;
    }

    private Timestamp64 readTimeStamp(byte[] bArr, int i) {
        return Timestamp64.fromComponents(readUnsigned32(bArr, i), (int) readUnsigned32(bArr, i + 4));
    }

    private void writeTimeStamp(byte[] bArr, int i, Timestamp64 timestamp64) {
        long eraSeconds = timestamp64.getEraSeconds();
        bArr[i] = (byte) (eraSeconds >>> 24);
        bArr[i + 1] = (byte) (eraSeconds >>> 16);
        bArr[i + 2] = (byte) (eraSeconds >>> 8);
        bArr[i + 3] = (byte) eraSeconds;
        int fractionBits = timestamp64.getFractionBits();
        bArr[i + 4] = (byte) (fractionBits >>> 24);
        bArr[i + 5] = (byte) (fractionBits >>> 16);
        bArr[i + 6] = (byte) (fractionBits >>> 8);
        bArr[i + 7] = (byte) fractionBits;
    }

    private static Random defaultRandom() {
        try {
            return SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            Slog.wtf(TAG, "Unable to access SecureRandom", e);
            return new Random(System.currentTimeMillis());
        }
    }
}
