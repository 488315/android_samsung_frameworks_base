package com.android.server.display;

import android.os.IBinder;
import android.util.EventLog;
import android.view.SurfaceControl;

import com.android.internal.util.function.TriConsumer;
import com.android.server.power.Slog;

import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda0
        implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        final LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice =
                (LocalDisplayAdapter.LocalDisplayDevice) obj;
        final IBinder iBinder = (IBinder) obj2;
        switch (this.$r8$classId) {
            case 0:
                final SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs =
                        (SurfaceControl.DesiredDisplayModeSpecs) obj3;
                localDisplayDevice.getClass();
                if (CoreRune.FW_VRR_PERFORMANCE) {
                    synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                        for (int i = 0; i < LocalDisplayAdapter.this.mDevices.size(); i++) {
                            try {
                                LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice2 =
                                        (LocalDisplayAdapter.LocalDisplayDevice)
                                                LocalDisplayAdapter.this.mDevices.valueAt(i);
                                int i2 = localDisplayDevice2.mStateOverride;
                                if (i2 != 0) {
                                    if (i2 != localDisplayDevice2.mCommittedState) {
                                        localDisplayDevice2.mStateChangeCallbacks.add(
                                                new Runnable() { // from class:
                                                    // com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda4
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        LocalDisplayAdapter.LocalDisplayDevice
                                                                localDisplayDevice3 =
                                                                        LocalDisplayAdapter
                                                                                .LocalDisplayDevice
                                                                                .this;
                                                        SurfaceControl.DesiredDisplayModeSpecs
                                                                desiredDisplayModeSpecs2 =
                                                                        desiredDisplayModeSpecs;
                                                        IBinder iBinder2 = iBinder;
                                                        localDisplayDevice3.getClass();
                                                        EventLog.writeEvent(
                                                                1290000,
                                                                desiredDisplayModeSpecs2
                                                                        .toString());
                                                        Slog.d(
                                                                "LocalDisplayAdapter",
                                                                "Run! setDesiredDisplayModeSpecsAsync("
                                                                        + localDisplayDevice3
                                                                                .mPhysicalDisplayId
                                                                        + ") : "
                                                                        + desiredDisplayModeSpecs2);
                                                        LocalDisplayAdapter.this
                                                                .mSurfaceControlProxy.getClass();
                                                        SurfaceControl.setDesiredDisplayModeSpecs(
                                                                iBinder2, desiredDisplayModeSpecs2);
                                                    }
                                                });
                                        return;
                                    }
                                } else {
                                    if (localDisplayDevice2.mState
                                            != localDisplayDevice2.mCommittedState) {
                                        localDisplayDevice2.mStateChangeCallbacks.add(
                                                new Runnable() { // from class:
                                                    // com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda4
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        LocalDisplayAdapter.LocalDisplayDevice
                                                                localDisplayDevice3 =
                                                                        LocalDisplayAdapter
                                                                                .LocalDisplayDevice
                                                                                .this;
                                                        SurfaceControl.DesiredDisplayModeSpecs
                                                                desiredDisplayModeSpecs2 =
                                                                        desiredDisplayModeSpecs;
                                                        IBinder iBinder2 = iBinder;
                                                        localDisplayDevice3.getClass();
                                                        EventLog.writeEvent(
                                                                1290000,
                                                                desiredDisplayModeSpecs2
                                                                        .toString());
                                                        Slog.d(
                                                                "LocalDisplayAdapter",
                                                                "Run! setDesiredDisplayModeSpecsAsync("
                                                                        + localDisplayDevice3
                                                                                .mPhysicalDisplayId
                                                                        + ") : "
                                                                        + desiredDisplayModeSpecs2);
                                                        LocalDisplayAdapter.this
                                                                .mSurfaceControlProxy.getClass();
                                                        SurfaceControl.setDesiredDisplayModeSpecs(
                                                                iBinder2, desiredDisplayModeSpecs2);
                                                    }
                                                });
                                        return;
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                }
                if (CoreRune.FW_VRR_POLICY) {
                    EventLog.writeEvent(1290000, desiredDisplayModeSpecs.toString());
                    Slog.d(
                            "LocalDisplayAdapter",
                            "setDesiredDisplayModeSpecsAsync("
                                    + localDisplayDevice.mPhysicalDisplayId
                                    + ") : "
                                    + desiredDisplayModeSpecs);
                }
                LocalDisplayAdapter.this.mSurfaceControlProxy.getClass();
                SurfaceControl.setDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
                return;
            default:
                int intValue = ((Integer) obj3).intValue();
                LocalDisplayAdapter.this.mSurfaceControlProxy.getClass();
                SurfaceControl.setActiveColorMode(iBinder, intValue);
                synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                    localDisplayDevice.updateDeviceInfoLocked();
                }
                return;
        }
    }
}
