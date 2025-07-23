package android.net.sntp;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.time.Duration;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class Duration64 {
    public static final Duration64 ZERO = new Duration64(0);
    private final long mBits;

    private Duration64(long j) {
        this.mBits = j;
    }

    public static Duration64 between(Timestamp64 timestamp64, Timestamp64 timestamp642) {
        return new Duration64(((timestamp642.getFractionBits() & 4294967295L) | (timestamp642.getEraSeconds() << 32)) - ((timestamp64.getEraSeconds() << 32) | (timestamp64.getFractionBits() & 4294967295L)));
    }

    public Duration plus(Duration64 duration64) {
        return toDuration().plus(duration64.toDuration());
    }

    public static Duration64 fromDuration(Duration duration) {
        long seconds = duration.getSeconds();
        if (seconds < -2147483648L || seconds > 2147483647L) {
            throw new IllegalArgumentException();
        }
        return new Duration64((seconds << 32) | (Timestamp64.nanosToFractionBits(duration.getNano()) & 4294967295L));
    }

    public Duration toDuration() {
        return Duration.ofSeconds(getSeconds(), getNanos());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mBits == ((Duration64) obj).mBits;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mBits));
    }

    public String toString() {
        Duration duration = toDuration();
        return Long.toHexString(this.mBits) + NavigationBarInflaterView.KEY_CODE_START + duration.getSeconds() + "s " + duration.getNano() + "ns)";
    }

    public int getSeconds() {
        return (int) (this.mBits >> 32);
    }

    public int getNanos() {
        return Timestamp64.fractionBitsToNanos((int) (this.mBits & 4294967295L));
    }
}
