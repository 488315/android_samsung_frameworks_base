package android.app.time;

import android.annotation.SystemApi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class Capabilities {
    public static final int CAPABILITY_NOT_ALLOWED = 20;
    public static final int CAPABILITY_NOT_APPLICABLE = 30;
    public static final int CAPABILITY_NOT_SUPPORTED = 10;
    public static final int CAPABILITY_POSSESSED = 40;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CapabilityState {}

    private Capabilities() {}
}
