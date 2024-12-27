package com.android.server.wm;

import android.R;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class DeviceStateController {
    public final int mConcurrentDisplayDeviceState;
    public int mCurrentState;
    public final int[] mFoldedDeviceStates;
    public final int[] mHalfFoldedDeviceStates;
    public final boolean mMatchBuiltInDisplayOrientationToDefaultDisplay;
    public final int[] mOpenDeviceStates;
    public final int[] mRearDisplayDeviceStates;
    public final int[] mReverseRotationAroundZAxisStates;
    public final WindowManagerGlobalLock mWmLock;
    final Map mDeviceStateCallbacks = new ArrayMap();
    public DeviceState mCurrentDeviceState = DeviceState.UNKNOWN;

    public final class DeviceState {
        public static final /* synthetic */ DeviceState[] $VALUES;
        public static final DeviceState CONCURRENT;
        public static final DeviceState FOLDED;
        public static final DeviceState HALF_FOLDED;
        public static final DeviceState OPEN;
        public static final DeviceState REAR;
        public static final DeviceState UNKNOWN;

        static {
            DeviceState deviceState = new DeviceState("UNKNOWN", 0);
            UNKNOWN = deviceState;
            DeviceState deviceState2 = new DeviceState("OPEN", 1);
            OPEN = deviceState2;
            DeviceState deviceState3 = new DeviceState("FOLDED", 2);
            FOLDED = deviceState3;
            DeviceState deviceState4 = new DeviceState("HALF_FOLDED", 3);
            HALF_FOLDED = deviceState4;
            DeviceState deviceState5 = new DeviceState("REAR", 4);
            REAR = deviceState5;
            DeviceState deviceState6 = new DeviceState("CONCURRENT", 5);
            CONCURRENT = deviceState6;
            $VALUES =
                    new DeviceState[] {
                        deviceState,
                        deviceState2,
                        deviceState3,
                        deviceState4,
                        deviceState5,
                        deviceState6
                    };
        }

        public static DeviceState valueOf(String str) {
            return (DeviceState) Enum.valueOf(DeviceState.class, str);
        }

        public static DeviceState[] values() {
            return (DeviceState[]) $VALUES.clone();
        }
    }

    public DeviceStateController(Context context, WindowManagerGlobalLock windowManagerGlobalLock) {
        this.mWmLock = windowManagerGlobalLock;
        this.mOpenDeviceStates = context.getResources().getIntArray(17236280);
        this.mHalfFoldedDeviceStates = context.getResources().getIntArray(R.array.sim_colors);
        this.mFoldedDeviceStates =
                context.getResources()
                        .getIntArray(R.array.preloaded_freeform_multi_window_drawables);
        this.mRearDisplayDeviceStates = context.getResources().getIntArray(17236286);
        this.mConcurrentDisplayDeviceState =
                context.getResources()
                        .getInteger(R.integer.config_dynamicPowerSavingsDefaultDisableThreshold);
        this.mReverseRotationAroundZAxisStates =
                context.getResources().getIntArray(R.array.config_telephonyHardware);
        this.mMatchBuiltInDisplayOrientationToDefaultDisplay =
                context.getResources().getBoolean(R.bool.config_nightDisplayAvailable);
    }

    public List copyDeviceStateCallbacks() {
        final ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mWmLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDeviceStateCallbacks.forEach(
                        new BiConsumer() { // from class:
                                           // com.android.server.wm.DeviceStateController$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                arrayList.add(new Pair((Consumer) obj, (Executor) obj2));
                            }
                        });
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return arrayList;
    }
}
