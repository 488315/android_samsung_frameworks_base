package android.os;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/* loaded from: classes3.dex */
public abstract class SimpleClock extends Clock {
    private final ZoneId zone;

    @Override // java.time.Clock, java.time.InstantSource
    public abstract long millis();

    public SimpleClock(ZoneId zoneId) {
        this.zone = zoneId;
    }

    @Override // java.time.Clock
    public ZoneId getZone() {
        return this.zone;
    }

    @Override // java.time.Clock, java.time.InstantSource
    public Clock withZone(ZoneId zoneId) {
        return new SimpleClock(zoneId) { // from class: android.os.SimpleClock.1
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return SimpleClock.this.millis();
            }
        };
    }

    @Override // java.time.Clock, java.time.InstantSource
    public Instant instant() {
        return Instant.ofEpochMilli(millis());
    }
}
