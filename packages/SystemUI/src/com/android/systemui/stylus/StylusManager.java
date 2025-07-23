package com.android.systemui.stylus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.hardware.BatteryState;
import android.hardware.input.InputManager;
import android.hardware.input.InputSettings;
import android.os.Build;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.InputDevice;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.stylus.StylusManager;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Charsets;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StylusManager implements InputManager.InputDeviceListener, InputManager.InputDeviceBatteryListener, BluetoothAdapter.OnMetadataChangedListener {
    public static final String TAG;
    public final BluetoothAdapter bluetoothAdapter;
    public final Context context;
    public final Executor executor;
    public final FeatureFlags featureFlags;
    public final Handler handler;
    public boolean hasStarted;
    public final InputManager inputManager;
    public final UiEventLogger uiEventLogger;
    public InstanceId usiSessionId;
    public final CopyOnWriteArrayList stylusCallbacks = new CopyOnWriteArrayList();
    public final Map inputDeviceAddressMap = new ArrayMap();
    public final Map inputDeviceBtSessionIdMap = new ArrayMap();
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(8192);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
        if (simpleName == null) {
            simpleName = "";
        }
        TAG = simpleName;
    }

    public StylusManager(Context context, InputManager inputManager, BluetoothAdapter bluetoothAdapter, Handler handler, Executor executor, FeatureFlags featureFlags, UiEventLogger uiEventLogger) {
        this.context = context;
        this.inputManager = inputManager;
        this.bluetoothAdapter = bluetoothAdapter;
        this.handler = handler;
        this.executor = executor;
        this.featureFlags = featureFlags;
        this.uiEventLogger = uiEventLogger;
    }

    public final void onBatteryStateChanged(final int i, final long j, final BatteryState batteryState) {
        this.handler.post(new Runnable(batteryState, i, j) { // from class: com.android.systemui.stylus.StylusManager$onBatteryStateChanged$1
            public final /* synthetic */ BatteryState $batteryState;
            public final /* synthetic */ int $deviceId;

            @Override // java.lang.Runnable
            public final void run() {
                if (StylusManager.this.hasStarted) {
                    DebugLogger debugLogger = DebugLogger.INSTANCE;
                    boolean z = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                    StylusManager stylusManager = StylusManager.this;
                    BatteryState batteryState2 = this.$batteryState;
                    stylusManager.getClass();
                    boolean z2 = batteryState2.isPresent() && batteryState2.getCapacity() > 0.0f;
                    StylusManager stylusManager2 = StylusManager.this;
                    int i2 = !((ArrayMap) stylusManager2.inputDeviceBtSessionIdMap).isEmpty() ? 1 : 0;
                    if (z2 && stylusManager2.usiSessionId == null) {
                        Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                        InstanceId newInstanceId = stylusManager2.instanceIdSequence.newInstanceId();
                        stylusManager2.usiSessionId = newInstanceId;
                        stylusManager2.uiEventLogger.logWithInstanceIdAndPosition(StylusUiEvent.USI_STYLUS_BATTERY_PRESENCE_FIRST_DETECTED, 0, (String) null, newInstanceId, i2);
                    } else if (!z2 && stylusManager2.usiSessionId != null) {
                        Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                        stylusManager2.uiEventLogger.logWithInstanceIdAndPosition(StylusUiEvent.USI_STYLUS_BATTERY_PRESENCE_REMOVED, 0, (String) null, stylusManager2.usiSessionId, i2);
                        stylusManager2.usiSessionId = null;
                    }
                    if (z2) {
                        StylusManager.this.onStylusUsed();
                    }
                    StylusManager stylusManager3 = StylusManager.this;
                    int i3 = this.$deviceId;
                    BatteryState batteryState3 = this.$batteryState;
                    Iterator it = stylusManager3.stylusCallbacks.iterator();
                    while (it.hasNext()) {
                        ((StylusManager.StylusCallback) it.next()).onStylusUsiBatteryStateChanged(i3, batteryState3);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        });
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        InputDevice inputDevice;
        if (this.hasStarted && (inputDevice = this.inputManager.getInputDevice(i)) != null && inputDevice.supportsSource(16386)) {
            DebugLogger debugLogger = DebugLogger.INSTANCE;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
            if (!inputDevice.isExternal()) {
                registerBatteryListener(i);
            }
            String bluetoothAddress = inputDevice.getBluetoothAddress();
            ((ArrayMap) this.inputDeviceAddressMap).put(Integer.valueOf(i), bluetoothAddress);
            Iterator it = this.stylusCallbacks.iterator();
            while (it.hasNext()) {
                ((StylusCallback) it.next()).onStylusAdded(i);
                Unit unit = Unit.INSTANCE;
            }
            if (bluetoothAddress != null) {
                onStylusUsed();
                onStylusBluetoothConnected(i, bluetoothAddress);
                Iterator it2 = this.stylusCallbacks.iterator();
                while (it2.hasNext()) {
                    ((StylusCallback) it2.next()).getClass();
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        InputDevice inputDevice;
        if (this.hasStarted && (inputDevice = this.inputManager.getInputDevice(i)) != null && inputDevice.supportsSource(16386)) {
            DebugLogger debugLogger = DebugLogger.INSTANCE;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
            String bluetoothAddress = inputDevice.getBluetoothAddress();
            String str = (String) ((ArrayMap) this.inputDeviceAddressMap).get(Integer.valueOf(i));
            ((ArrayMap) this.inputDeviceAddressMap).put(Integer.valueOf(i), bluetoothAddress);
            if (str == null && bluetoothAddress != null) {
                onStylusBluetoothConnected(i, bluetoothAddress);
                Iterator it = this.stylusCallbacks.iterator();
                while (it.hasNext()) {
                    ((StylusCallback) it.next()).getClass();
                    Unit unit = Unit.INSTANCE;
                }
            }
            if (str == null || bluetoothAddress != null) {
                return;
            }
            onStylusBluetoothDisconnected(i, str);
            Iterator it2 = this.stylusCallbacks.iterator();
            while (it2.hasNext()) {
                ((StylusCallback) it2.next()).getClass();
                Unit unit2 = Unit.INSTANCE;
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        if (this.hasStarted) {
            if (((ArrayMap) this.inputDeviceAddressMap).containsKey(Integer.valueOf(i))) {
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                boolean z = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                try {
                    this.inputManager.removeInputDeviceBatteryListener(i, this);
                } catch (SecurityException e) {
                    Log.e(TAG, e + ": Failed to remove registered battery listener for " + i + ".");
                }
                String str = (String) ((ArrayMap) this.inputDeviceAddressMap).get(Integer.valueOf(i));
                ((ArrayMap) this.inputDeviceAddressMap).remove(Integer.valueOf(i));
                if (str != null) {
                    onStylusBluetoothDisconnected(i, str);
                    Iterator it = this.stylusCallbacks.iterator();
                    while (it.hasNext()) {
                        ((StylusCallback) it.next()).getClass();
                        Unit unit = Unit.INSTANCE;
                    }
                }
                Iterator it2 = this.stylusCallbacks.iterator();
                while (it2.hasNext()) {
                    ((StylusCallback) it2.next()).getClass();
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        }
    }

    public final void onMetadataChanged(final BluetoothDevice bluetoothDevice, final int i, final byte[] bArr) {
        this.handler.post(new Runnable() { // from class: com.android.systemui.stylus.StylusManager$onMetadataChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                StylusManager stylusManager = StylusManager.this;
                if (stylusManager.hasStarted && i == 19 && bArr != null) {
                    Map map = stylusManager.inputDeviceAddressMap;
                    BluetoothDevice bluetoothDevice2 = bluetoothDevice;
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Map.Entry entry : ((ArrayMap) map).entrySet()) {
                        if (Intrinsics.areEqual((String) entry.getValue(), bluetoothDevice2.getAddress())) {
                            linkedHashMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    if (((Integer) CollectionsKt___CollectionsKt.firstOrNull(linkedHashMap.keySet())) != null) {
                        new String(bArr, Charsets.UTF_8).equals("true");
                        DebugLogger debugLogger = DebugLogger.INSTANCE;
                        StylusManager stylusManager2 = StylusManager.this;
                        boolean z = Build.IS_DEBUGGABLE;
                        Reflection.getOrCreateKotlinClass(stylusManager2.getClass()).getSimpleName();
                        Iterator it = StylusManager.this.stylusCallbacks.iterator();
                        while (it.hasNext()) {
                            ((StylusManager.StylusCallback) it.next()).getClass();
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                }
            }
        });
    }

    public final void onStylusBluetoothConnected(int i, String str) {
        BluetoothDevice remoteDevice;
        trackAndLogBluetoothSession(i, true);
        BluetoothAdapter bluetoothAdapter = this.bluetoothAdapter;
        if (bluetoothAdapter == null || (remoteDevice = bluetoothAdapter.getRemoteDevice(str)) == null) {
            return;
        }
        try {
            this.bluetoothAdapter.addOnMetadataChangedListener(remoteDevice, this.executor, this);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e + ": Metadata listener already registered for device. Ignoring.");
        }
    }

    public final void onStylusBluetoothDisconnected(int i, String str) {
        BluetoothDevice remoteDevice;
        trackAndLogBluetoothSession(i, false);
        BluetoothAdapter bluetoothAdapter = this.bluetoothAdapter;
        if (bluetoothAdapter == null || (remoteDevice = bluetoothAdapter.getRemoteDevice(str)) == null) {
            return;
        }
        try {
            this.bluetoothAdapter.removeOnMetadataChangedListener(remoteDevice, this);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e + ": Metadata listener does not exist for device. Ignoring.");
        }
    }

    public final void onStylusUsed() {
        if (((FeatureFlagsClassicRelease) this.featureFlags).isEnabled(Flags.TRACK_STYLUS_EVER_USED) && !InputSettings.isStylusEverUsed(this.context)) {
            DebugLogger debugLogger = DebugLogger.INSTANCE;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
            InputSettings.setStylusEverUsed(this.context, true);
            Iterator it = this.stylusCallbacks.iterator();
            while (it.hasNext()) {
                ((StylusCallback) it.next()).onStylusFirstUsed();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void registerBatteryListener(int i) {
        try {
            this.inputManager.addInputDeviceBatteryListener(i, this.executor, this);
        } catch (SecurityException e) {
            Log.e(TAG, e + ": Failed to register battery listener for " + i + ".");
        }
    }

    public final void trackAndLogBluetoothSession(int i, boolean z) {
        DebugLogger debugLogger = DebugLogger.INSTANCE;
        boolean z2 = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
        if (!z) {
            this.uiEventLogger.logWithInstanceId(StylusUiEvent.BLUETOOTH_STYLUS_DISCONNECTED, 0, (String) null, (InstanceId) ((ArrayMap) this.inputDeviceBtSessionIdMap).get(Integer.valueOf(i)));
            ((ArrayMap) this.inputDeviceBtSessionIdMap).remove(Integer.valueOf(i));
            return;
        }
        Integer valueOf = Integer.valueOf(i);
        ((ArrayMap) this.inputDeviceBtSessionIdMap).put(valueOf, this.instanceIdSequence.newInstanceId());
        this.uiEventLogger.logWithInstanceId(StylusUiEvent.BLUETOOTH_STYLUS_CONNECTED, 0, (String) null, (InstanceId) ((ArrayMap) this.inputDeviceBtSessionIdMap).get(Integer.valueOf(i)));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface StylusCallback {
        default void onStylusAdded(int i) {
        }

        default void onStylusFirstUsed() {
        }

        default void onStylusUsiBatteryStateChanged(int i, BatteryState batteryState) {
        }
    }

    public static /* synthetic */ void getInstanceIdSequence$annotations() {
    }
}
