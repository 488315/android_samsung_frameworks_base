package android.net;

import android.net.sntp.Duration64;
import android.net.sntp.Timestamp64;
import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
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
        public InvalidServerReplyException(String message) {
            super(message);
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

    public SntpClient(Supplier<Instant> systemTimeSupplier, Random random) {
        this.mSystemTimeSupplier = (Supplier) Objects.requireNonNull(systemTimeSupplier);
        this.mRandom = (Random) Objects.requireNonNull(random);
    }

    public boolean requestTime(String host, int port, int timeout, Network network) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null && lService.shallForceNtpMdmValues()) {
                String newHost = lService.getNtpServer();
                int newTimeout = (int) lService.getNtpTimeout();
                if (!TextUtils.isEmpty(newHost)) {
                    host = newHost;
                    Log.d(TAG, "host set by MDM: " + host);
                }
                if (newTimeout != 0) {
                    timeout = newTimeout;
                    Log.d(TAG, "timeout set by MDM: " + timeout);
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "Remote Exception: " + e);
        }
        Network networkForResolv = network.getPrivateDnsBypassingCopy();
        try {
            InetAddress[] addresses = networkForResolv.getAllByName(host);
            for (InetAddress inetAddress : addresses) {
                if (requestTime(inetAddress, port, timeout, networkForResolv)) {
                    return true;
                }
            }
        } catch (UnknownHostException e2) {
            Log.w(TAG, "Unknown host: " + host);
            EventLogTags.writeNtpFailure(host, e2.toString());
        }
        Log.d(TAG, "request time failed");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0189  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean requestTime(java.net.InetAddress r40, int r41, int r42, android.net.Network r43) {
        /*
            Method dump skipped, instructions count: 400
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.SntpClient.requestTime(java.net.InetAddress, int, int, android.net.Network):boolean");
    }

    public static Duration calculateClockOffset(Timestamp64 clientRequestTimestamp, Timestamp64 serverReceiveTimestamp, Timestamp64 serverTransmitTimestamp, Timestamp64 clientResponseTimestamp) {
        return Duration64.between(clientRequestTimestamp, serverReceiveTimestamp).plus(Duration64.between(clientResponseTimestamp, serverTransmitTimestamp)).dividedBy(2L);
    }

    @Deprecated
    public boolean requestTime(String host, int timeout) {
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

    private static void checkValidServerReply(byte leap, byte mode, int stratum, Timestamp64 transmitTimestamp, Timestamp64 referenceTimestamp, Timestamp64 randomizedRequestTimestamp, Timestamp64 originateTimestamp) throws InvalidServerReplyException {
        if (leap == 3) {
            throw new InvalidServerReplyException("unsynchronized server");
        }
        if (mode != 4 && mode != 5) {
            throw new InvalidServerReplyException("untrusted mode: " + ((int) mode));
        }
        if (stratum == 0 || stratum > 15) {
            throw new InvalidServerReplyException("untrusted stratum: " + stratum);
        }
        if (!randomizedRequestTimestamp.equals(originateTimestamp)) {
            throw new InvalidServerReplyException("originateTimestamp != randomizedRequestTimestamp");
        }
        if (transmitTimestamp.equals(Timestamp64.ZERO)) {
            throw new InvalidServerReplyException("zero transmitTimestamp");
        }
        if (referenceTimestamp.equals(Timestamp64.ZERO)) {
            throw new InvalidServerReplyException("zero referenceTimestamp");
        }
    }

    private long readUnsigned32(byte[] buffer, int offset) {
        int offset2 = offset + 1;
        int i0 = buffer[offset] & 255;
        int offset3 = offset2 + 1;
        int i1 = buffer[offset2] & 255;
        int offset4 = offset3 + 1;
        int i2 = buffer[offset3] & 255;
        int i3 = buffer[offset4] & 255;
        int bits = (i0 << 24) | (i1 << 16) | (i2 << 8) | i3;
        return bits & 4294967295L;
    }

    private Timestamp64 readTimeStamp(byte[] buffer, int offset) {
        long seconds = readUnsigned32(buffer, offset);
        int fractionBits = (int) readUnsigned32(buffer, offset + 4);
        return Timestamp64.fromComponents(seconds, fractionBits);
    }

    private void writeTimeStamp(byte[] buffer, int offset, Timestamp64 timestamp) {
        long seconds = timestamp.getEraSeconds();
        int offset2 = offset + 1;
        buffer[offset] = (byte) (seconds >>> 24);
        int offset3 = offset2 + 1;
        buffer[offset2] = (byte) (seconds >>> 16);
        int offset4 = offset3 + 1;
        buffer[offset3] = (byte) (seconds >>> 8);
        int offset5 = offset4 + 1;
        buffer[offset4] = (byte) seconds;
        int fractionBits = timestamp.getFractionBits();
        int offset6 = offset5 + 1;
        buffer[offset5] = (byte) (fractionBits >>> 24);
        int offset7 = offset6 + 1;
        buffer[offset6] = (byte) (fractionBits >>> 16);
        buffer[offset7] = (byte) (fractionBits >>> 8);
        buffer[offset7 + 1] = (byte) fractionBits;
    }

    private static Random defaultRandom() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            return random;
        } catch (NoSuchAlgorithmException e) {
            Slog.wtf(TAG, "Unable to access SecureRandom", e);
            Random random2 = new Random(System.currentTimeMillis());
            return random2;
        }
    }
}
