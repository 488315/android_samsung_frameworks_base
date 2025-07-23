package android.hardware.devicestate;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateRequest;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlags;
import com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlagsImpl;
import com.android.internal.util.ArrayUtils;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes2.dex */
public final class DeviceStateManager {
    public static final String ACTION_SHOW_REAR_DISPLAY_OVERLAY = "com.android.intent.action.SHOW_REAR_DISPLAY_OVERLAY";
    public static final String EXTRA_ORIGINAL_DEVICE_BASE_STATE = "original_device_base_state";
    public static final DeviceState INVALID_DEVICE_STATE = new DeviceState(new DeviceState.Configuration.Builder(-1, "INVALID").build());
    public static final int INVALID_DEVICE_STATE_IDENTIFIER = -1;
    public static final int MAXIMUM_DEVICE_STATE_IDENTIFIER = 10000;
    public static final int MINIMUM_DEVICE_STATE_IDENTIFIER = 0;
    private final DeviceStateManagerGlobal mGlobal;

    public interface DeviceStateCallback {
        void onDeviceStateChanged(DeviceState deviceState);

        default void onSupportedStatesChanged(List<DeviceState> list) {
        }
    }

    public DeviceStateManager() {
        DeviceStateManagerGlobal deviceStateManagerGlobal = DeviceStateManagerGlobal.getInstance();
        if (deviceStateManagerGlobal == null) {
            throw new IllegalStateException("Failed to get instance of global device state manager.");
        }
        this.mGlobal = deviceStateManagerGlobal;
    }

    public List<DeviceState> getSupportedDeviceStates() {
        return this.mGlobal.getSupportedDeviceStates();
    }

    public void requestState(DeviceStateRequest deviceStateRequest, Executor executor, DeviceStateRequest.Callback callback) {
        this.mGlobal.requestState(deviceStateRequest, executor, callback);
    }

    public void cancelStateRequest() {
        this.mGlobal.cancelStateRequest();
    }

    public void requestBaseStateOverride(DeviceStateRequest deviceStateRequest, Executor executor, DeviceStateRequest.Callback callback) {
        this.mGlobal.requestBaseStateOverride(deviceStateRequest, executor, callback);
    }

    public void cancelBaseStateOverride() {
        this.mGlobal.cancelBaseStateOverride();
    }

    public void registerCallback(Executor executor, DeviceStateCallback deviceStateCallback) {
        this.mGlobal.registerDeviceStateCallback(deviceStateCallback, executor);
    }

    public void unregisterCallback(DeviceStateCallback deviceStateCallback) {
        this.mGlobal.unregisterDeviceStateCallback(deviceStateCallback);
    }

    public static class FoldStateListener implements DeviceStateCallback {
        private Boolean lastResult;
        private final Consumer<Boolean> mDelegate;
        private final FeatureFlags mFeatureFlags;
        private final int[] mFoldedDeviceStates;

        static /* synthetic */ void lambda$new$0(Boolean bool) {
        }

        public FoldStateListener(Context context) {
            this(context, new Consumer() { // from class: android.hardware.devicestate.DeviceStateManager$FoldStateListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DeviceStateManager.FoldStateListener.lambda$new$0((Boolean) obj);
                }
            });
        }

        public FoldStateListener(Context context, Consumer<Boolean> consumer) {
            this.mFoldedDeviceStates = context.getResources().getIntArray(R.array.config_foldedDeviceStates);
            this.mDelegate = consumer;
            this.mFeatureFlags = new FeatureFlagsImpl();
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public final void onDeviceStateChanged(DeviceState deviceState) {
            boolean contains;
            if (this.mFeatureFlags.deviceStatePropertyApi()) {
                contains = deviceState.hasProperty(11) || ArrayUtils.contains(this.mFoldedDeviceStates, deviceState.getIdentifier());
            } else {
                contains = ArrayUtils.contains(this.mFoldedDeviceStates, deviceState.getIdentifier());
            }
            Boolean bool = this.lastResult;
            if (bool == null || !bool.equals(Boolean.valueOf(contains))) {
                this.lastResult = Boolean.valueOf(contains);
                this.mDelegate.accept(Boolean.valueOf(contains));
            }
        }

        public Boolean getFolded() {
            return this.lastResult;
        }
    }
}
