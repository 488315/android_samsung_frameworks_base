package com.android.server.pm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PerPackageReadTimeouts {
    public final String packageName;
    public final byte[] sha256certificate;
    public final Timeouts timeouts;
    public final VersionCodes versionCodes;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Timeouts {
        public static final Timeouts DEFAULT = new Timeouts(3600000000L, 3600000000L, 3600000000L);
        public final long maxPendingTimeUs;
        public final long minPendingTimeUs;
        public final long minTimeUs;

        public Timeouts(long j, long j2, long j3) {
            this.minTimeUs = j;
            this.minPendingTimeUs = j2;
            this.maxPendingTimeUs = j3;
        }

        public static Timeouts parse(String str) {
            long j;
            long j2;
            String[] split = str.split(":", 3);
            int length = split.length;
            Timeouts timeouts = DEFAULT;
            if (length != 3) {
                return timeouts;
            }
            long j3 = 3600000000L;
            try {
                j = Long.parseLong(split[0]);
            } catch (NumberFormatException unused) {
                j = 3600000000L;
            }
            try {
                j2 = Long.parseLong(split[1]);
            } catch (NumberFormatException unused2) {
                j2 = 3600000000L;
            }
            try {
                j3 = Long.parseLong(split[2]);
            } catch (NumberFormatException unused3) {
            }
            long j4 = j3;
            return (0 > j || j > j2 || j2 > j4) ? timeouts : new Timeouts(j, j2, j4);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VersionCodes {
        public static final VersionCodes ALL_VERSION_CODES = new VersionCodes(Long.MIN_VALUE, Long.MAX_VALUE);
        public final long maxVersionCode;
        public final long minVersionCode;

        public VersionCodes(long j, long j2) {
            this.minVersionCode = j;
            this.maxVersionCode = j2;
        }
    }

    public PerPackageReadTimeouts(String str, byte[] bArr, VersionCodes versionCodes, Timeouts timeouts) {
        this.packageName = str;
        this.sha256certificate = bArr;
        this.versionCodes = versionCodes;
        this.timeouts = timeouts;
    }
}
