package com.android.server.devicestate;

import android.hardware.devicestate.DeviceState;
import android.os.Trace;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.util.Objects;
import java.util.Optional;

public final /* synthetic */ class DeviceStateManagerService$$ExternalSyntheticLambda6
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceStateManagerService f$0;

    public /* synthetic */ DeviceStateManagerService$$ExternalSyntheticLambda6(
            DeviceStateManagerService deviceStateManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceStateManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DeviceStateManagerService.ProcessRecord processRecord;
        int i = this.$r8$classId;
        DeviceStateManagerService deviceStateManagerService = this.f$0;
        switch (i) {
            case 0:
                synchronized (deviceStateManagerService.mLock) {
                    try {
                        DeviceState deviceState =
                                (DeviceState) deviceStateManagerService.mPendingState.get();
                        Slog.d("DeviceStateManagerService", "Committing state: " + deviceState);
                        FrameworkStatsLog.write(
                                350,
                                deviceState.getIdentifier(),
                                !deviceStateManagerService.mCommittedState.isPresent());
                        String str = deviceState.getIdentifier() + ":" + deviceState.getName();
                        Trace.instantForTrack(524288L, "DeviceStateChanged", str);
                        deviceStateManagerService.mSystemPropertySetter
                                .setDebugTracingDeviceStateProperty(str);
                        deviceStateManagerService.mCommittedState = Optional.of(deviceState);
                        deviceStateManagerService.mPendingState = Optional.empty();
                        deviceStateManagerService.updatePendingStateLocked();
                        deviceStateManagerService.notifyDeviceStateInfoChangedAsync();
                        OverrideRequest overrideRequest =
                                (OverrideRequest)
                                        deviceStateManagerService.mActiveOverride.orElse(null);
                        if (overrideRequest != null
                                && overrideRequest.mRequestedState.getIdentifier()
                                        == deviceState.getIdentifier()
                                && (processRecord =
                                                (DeviceStateManagerService.ProcessRecord)
                                                        deviceStateManagerService.mProcessRecords
                                                                .get(overrideRequest.mPid))
                                        != null) {
                            processRecord.notifyRequestActiveAsync(overrideRequest.mToken);
                        }
                        deviceStateManagerService.mHandler.post(
                                new DeviceStateManagerService$$ExternalSyntheticLambda6(
                                        deviceStateManagerService, 2));
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            case 1:
                synchronized (deviceStateManagerService.mLock) {
                    Optional optional = deviceStateManagerService.mActiveOverride;
                    OverrideRequestController overrideRequestController =
                            deviceStateManagerService.mOverrideRequestController;
                    Objects.requireNonNull(overrideRequestController);
                    optional.ifPresent(
                            new DeviceStateManagerService$$ExternalSyntheticLambda3(
                                    overrideRequestController));
                }
                return;
            case 2:
                deviceStateManagerService.notifyPolicyIfNeeded();
                return;
            default:
                DeviceState deviceState2 = DeviceStateManagerService.INVALID_DEVICE_STATE;
                deviceStateManagerService.notifyPolicyIfNeeded();
                return;
        }
    }
}
