package android.hardware.devicestate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class DeviceStateRequest {
    public static final int FLAG_CANCEL_WHEN_BASE_CHANGES = 1;
    public static final int FLAG_CANCEL_WHEN_FULL_OPEN = 8;
    public static final int FLAG_NO_CANCEL_WHEN_REQUESTER_NOT_ON_TOP = 16;
    public static final int FLAG_NO_CANCEL_WHEN_SCREEN_OFF = 2;
    public static final int FLAG_NO_USE_NOTIFY = 4;
    private final int mFlags;
    private final int mRequestedState;

    public interface Callback {
        default void onRequestActivated(DeviceStateRequest deviceStateRequest) {
        }

        default void onRequestCanceled(DeviceStateRequest deviceStateRequest) {
        }

        default void onRequestSuspended(DeviceStateRequest deviceStateRequest) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    public static Builder newBuilder(int i) {
        return new Builder(i);
    }

    public static final class Builder {
        private int mFlags;
        private final int mRequestedState;

        private Builder(int i) {
            this.mRequestedState = i;
        }

        public Builder setFlags(int i) {
            this.mFlags = i | this.mFlags;
            return this;
        }

        public DeviceStateRequest build() {
            return new DeviceStateRequest(this.mRequestedState, this.mFlags);
        }
    }

    private DeviceStateRequest(int i, int i2) {
        this.mRequestedState = i;
        this.mFlags = i2;
    }

    public int getState() {
        return this.mRequestedState;
    }

    public int getFlags() {
        return this.mFlags;
    }
}
