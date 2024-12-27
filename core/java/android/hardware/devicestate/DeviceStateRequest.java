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

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    public static Builder newBuilder(int requestedState) {
        return new Builder(requestedState);
    }

    public static final class Builder {
        private int mFlags;
        private final int mRequestedState;

        private Builder(int requestedState) {
            this.mRequestedState = requestedState;
        }

        public Builder setFlags(int flags) {
            this.mFlags |= flags;
            return this;
        }

        public DeviceStateRequest build() {
            return new DeviceStateRequest(this.mRequestedState, this.mFlags);
        }
    }

    public interface Callback {
        default void onRequestActivated(DeviceStateRequest request) {
        }

        default void onRequestSuspended(DeviceStateRequest request) {
        }

        default void onRequestCanceled(DeviceStateRequest request) {
        }
    }

    private DeviceStateRequest(int requestedState, int flags) {
        this.mRequestedState = requestedState;
        this.mFlags = flags;
    }

    public int getState() {
        return this.mRequestedState;
    }

    public int getFlags() {
        return this.mFlags;
    }
}
