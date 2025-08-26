package android.net;

import android.net.sntp.Duration64;
import android.net.sntp.Timestamp64;
import android.os.RemoteException;
import android.os.SystemClock;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.TrafficStatsConstants;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x006f A[Catch: UnknownHostException -> 0x007c, TRY_LEAVE, TryCatch #2 {UnknownHostException -> 0x007c, blocks: (B:22:0x0067, B:23:0x006c, B:25:0x006f), top: B:38:0x0067 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0037 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean requestTime(String str, int i, int i2, Network network) throws UnknownHostException {
        String ntpServer;
        RemoteException e;
        Network privateDnsBypassingCopy;
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null && service.shallForceNtpMdmValues()) {
                ntpServer = service.getNtpServer();
                int ntpTimeout = (int) service.getNtpTimeout();
                if (!TextUtils.isEmpty(ntpServer)) {
                    try {
                        Log.d(TAG, "host set by MDM: " + ntpServer);
                        str = ntpServer;
                        if (ntpTimeout != 0) {
                            try {
                                Log.d(TAG, "timeout set by MDM: " + ntpTimeout);
                                i2 = ntpTimeout;
                            } catch (RemoteException e2) {
                                ntpServer = str;
                                e = e2;
                                i2 = ntpTimeout;
                                Log.d(TAG, "Remote Exception: " + e);
                                str = ntpServer;
                                privateDnsBypassingCopy = network.getPrivateDnsBypassingCopy();
                                while (i < r2.length) {
                                }
                                Log.d(TAG, "request time failed");
                                return false;
                            }
                        }
                    } catch (RemoteException e3) {
                        e = e3;
                        Log.d(TAG, "Remote Exception: " + e);
                        str = ntpServer;
                        privateDnsBypassingCopy = network.getPrivateDnsBypassingCopy();
                        while (i < r2.length) {
                        }
                        Log.d(TAG, "request time failed");
                        return false;
                    }
                } else if (ntpTimeout != 0) {
                }
            }
        } catch (RemoteException e4) {
            ntpServer = str;
            e = e4;
        }
        privateDnsBypassingCopy = network.getPrivateDnsBypassingCopy();
        try {
            for (InetAddress inetAddress : privateDnsBypassingCopy.getAllByName(str)) {
                if (requestTime(inetAddress, i, i2, privateDnsBypassingCopy)) {
                    return true;
                }
            }
        } catch (UnknownHostException e5) {
            Log.w(TAG, "Unknown host: " + str);
            EventLogTags.writeNtpFailure(str, e5.toString());
        }
        Log.d(TAG, "request time failed");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0151  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean requestTime(InetAddress inetAddress, int i, int i2, Network network) throws Throwable {
        int i3;
        String str;
        boolean z;
        DatagramSocket datagramSocket;
        DatagramSocket datagramSocket2;
        byte[] bArr;
        Instant instant;
        Timestamp64 timestamp64FromInstant;
        Timestamp64 timestamp64RandomizeSubMillis;
        long jElapsedRealtime;
        long j;
        int andSetThreadStatsTag = TrafficStats.getAndSetThreadStatsTag(TrafficStatsConstants.TAG_SYSTEM_NTP);
        DatagramSocket datagramSocket3 = null;
        try {
            datagramSocket = new DatagramSocket();
            try {
                try {
                    network.bindSocket(datagramSocket);
                    datagramSocket.setSoTimeout(i2);
                    bArr = new byte[48];
                    DatagramPacket datagramPacket = new DatagramPacket(bArr, 48, inetAddress, i);
                    bArr[0] = 27;
                    instant = this.mSystemTimeSupplier.get();
                    timestamp64FromInstant = Timestamp64.fromInstant(instant);
                    timestamp64RandomizeSubMillis = timestamp64FromInstant.randomizeSubMillis(this.mRandom);
                    long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                    z = false;
                    try {
                        writeTimeStamp(bArr, 40, timestamp64RandomizeSubMillis);
                        datagramSocket.send(datagramPacket);
                        datagramSocket.receive(new DatagramPacket(bArr, 48));
                        jElapsedRealtime = SystemClock.elapsedRealtime();
                        str = "request time failed: ";
                        i3 = andSetThreadStatsTag;
                        j = jElapsedRealtime - jElapsedRealtime2;
                    } catch (Exception e) {
                        e = e;
                        str = "request time failed: ";
                        i3 = andSetThreadStatsTag;
                    }
                } catch (Throwable th) {
                    th = th;
                    i3 = andSetThreadStatsTag;
                }
            } catch (Exception e2) {
                e = e2;
                str = "request time failed: ";
                i3 = andSetThreadStatsTag;
                z = false;
            }
        } catch (Exception e3) {
            e = e3;
            str = "request time failed: ";
            i3 = andSetThreadStatsTag;
            z = false;
        } catch (Throwable th2) {
            th = th2;
            i3 = andSetThreadStatsTag;
        }
        try {
            Instant instantPlusMillis = instant.plusMillis(j);
            Timestamp64 timestamp64FromInstant2 = Timestamp64.fromInstant(instantPlusMillis);
            byte b = bArr[0];
            int i4 = bArr[1] & 255;
            Timestamp64 timeStamp = readTimeStamp(bArr, 16);
            Timestamp64 timeStamp2 = readTimeStamp(bArr, 24);
            Timestamp64 timeStamp3 = readTimeStamp(bArr, 32);
            Timestamp64 timeStamp4 = readTimeStamp(bArr, 40);
            checkValidServerReply((byte) ((b >> 6) & 3), (byte) (b & 7), i4, timeStamp4, timeStamp, timestamp64RandomizeSubMillis, timeStamp2);
            long millis = j - Duration64.between(timeStamp3, timeStamp4).toDuration().toMillis();
            Duration durationCalculateClockOffset = calculateClockOffset(timestamp64FromInstant, timeStamp3, timeStamp4, timestamp64FromInstant2);
            datagramSocket2 = datagramSocket;
            try {
                long millis2 = durationCalculateClockOffset.toMillis();
                EventLogTags.writeNtpSuccess(inetAddress.toString(), millis, millis2);
                Log.d(TAG, "round trip: " + millis + "ms, clock offset: " + millis2 + "ms");
                this.mClockOffset = millis2;
                this.mNtpTime = instantPlusMillis.plus((TemporalAmount) durationCalculateClockOffset).toEpochMilli();
                this.mNtpTimeReference = jElapsedRealtime;
                this.mRoundTripTime = millis;
                this.mServerSocketAddress = new InetSocketAddress(inetAddress, i);
                datagramSocket2.close();
                TrafficStats.setThreadStatsTag(i3);
                return true;
            } catch (Exception e4) {
                e = e4;
                datagramSocket3 = datagramSocket2;
                try {
                    EventLogTags.writeNtpFailure(inetAddress.toString(), e.toString());
                    Log.d(TAG, str + e);
                    if (datagramSocket3 != null) {
                        datagramSocket3.close();
                    }
                    TrafficStats.setThreadStatsTag(i3);
                    return z;
                } catch (Throwable th3) {
                    th = th3;
                    if (datagramSocket3 != null) {
                        datagramSocket3.close();
                    }
                    TrafficStats.setThreadStatsTag(i3);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                datagramSocket3 = datagramSocket2;
                if (datagramSocket3 != null) {
                }
                TrafficStats.setThreadStatsTag(i3);
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            datagramSocket2 = datagramSocket;
            datagramSocket3 = datagramSocket2;
            EventLogTags.writeNtpFailure(inetAddress.toString(), e.toString());
            Log.d(TAG, str + e);
            if (datagramSocket3 != null) {
            }
            TrafficStats.setThreadStatsTag(i3);
            return z;
        } catch (Throwable th5) {
            th = th5;
            datagramSocket2 = datagramSocket;
            datagramSocket3 = datagramSocket2;
            if (datagramSocket3 != null) {
            }
            TrafficStats.setThreadStatsTag(i3);
            throw th;
        }
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
