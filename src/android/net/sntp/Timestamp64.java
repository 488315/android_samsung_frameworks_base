package android.net.sntp;

import android.text.TextUtils;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;

/* loaded from: classes3.dex */
public final class Timestamp64 {
    static final long MAX_SECONDS_IN_ERA = 4294967295L;
    static final int NANOS_PER_SECOND = 1000000000;
    static final long OFFSET_1900_TO_1970 = 2208988800L;
    static final long SECONDS_IN_ERA = 4294967296L;
    static final int SUB_MILLIS_BITS_TO_RANDOMIZE = 22;
    public static final Timestamp64 ZERO = fromComponents(0, 0);
    private final long mEraSeconds;
    private final int mFractionBits;

    static int fractionBitsToNanos(int i) {
        return (int) (((i & 4294967295L) * 1000000000) >>> 32);
    }

    public static Timestamp64 fromComponents(long j, int i) {
        return new Timestamp64(j, i);
    }

    public static Timestamp64 fromString(String str) {
        if (str.length() != 17 || str.charAt(8) != '.') {
            throw new IllegalArgumentException(str);
        }
        String substring = str.substring(0, 8);
        String substring2 = str.substring(9);
        long parseLong = Long.parseLong(substring, 16);
        long parseLong2 = Long.parseLong(substring2, 16);
        if (parseLong2 < 0 || parseLong2 > 4294967295L) {
            throw new IllegalArgumentException("Invalid fractionBits:" + substring2);
        }
        return new Timestamp64(parseLong, (int) parseLong2);
    }

    public static Timestamp64 fromInstant(Instant instant) {
        long epochSecond = instant.getEpochSecond() + OFFSET_1900_TO_1970;
        if (epochSecond < 0) {
            epochSecond = 4294967296L - ((-epochSecond) % 4294967296L);
        }
        return new Timestamp64(epochSecond % 4294967296L, nanosToFractionBits(instant.getNano()));
    }

    private Timestamp64(long j, int i) {
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("Invalid parameters. seconds=" + j + ", fraction=" + i);
        }
        this.mEraSeconds = j;
        this.mFractionBits = i;
    }

    public long getEraSeconds() {
        return this.mEraSeconds;
    }

    public int getFractionBits() {
        return this.mFractionBits;
    }

    public String toString() {
        return TextUtils.formatSimple("%08x.%08x", Long.valueOf(this.mEraSeconds), Integer.valueOf(this.mFractionBits));
    }

    public Instant toInstant(int i) {
        return Instant.ofEpochSecond((this.mEraSeconds - OFFSET_1900_TO_1970) + (i * 4294967296L), fractionBitsToNanos(this.mFractionBits));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Timestamp64 timestamp64 = (Timestamp64) obj;
            if (this.mEraSeconds == timestamp64.mEraSeconds && this.mFractionBits == timestamp64.mFractionBits) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mEraSeconds), Integer.valueOf(this.mFractionBits));
    }

    static int nanosToFractionBits(long j) {
        if (j > 1000000000) {
            throw new IllegalArgumentException();
        }
        return (int) ((j << 32) / 1000000000);
    }

    public Timestamp64 randomizeSubMillis(Random random) {
        return new Timestamp64(this.mEraSeconds, randomizeLowestBits(random, this.mFractionBits, 22));
    }

    public static int randomizeLowestBits(Random random, int i, int i2) {
        if (i2 < 1 || i2 >= 32) {
            throw new IllegalArgumentException(Integer.toString(i2));
        }
        int i3 = (-1) << i2;
        return (random.nextInt() & (~i3)) | (i & i3);
    }
}
