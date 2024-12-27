package android.hardware;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class BatteryState {
    public static final int STATUS_CHARGING = 2;
    public static final int STATUS_DISCHARGING = 3;
    public static final int STATUS_FULL = 5;
    public static final int STATUS_NOT_CHARGING = 4;
    public static final int STATUS_UNKNOWN = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BatteryStatus {}

    public abstract float getCapacity();

    public abstract int getStatus();

    public abstract boolean isPresent();
}
