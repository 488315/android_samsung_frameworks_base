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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Charsets;

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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    public final void executeStylusCallbacks(Function1 function1) {
        Iterator it = this.stylusCallbacks.iterator();
        while (it.hasNext()) {
            function1.invoke(it.next());
        }
    }

    public final void onBatteryStateChanged(final int i, final long j, final BatteryState batteryState) {
        this.handler.post(new Runnable() { // from class: com.android.systemui.stylus.StylusManager$onBatteryStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                if (StylusManager.this.hasStarted) {
                    int i2 = DebugLogger.$r8$clinit;
                    boolean z = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                    StylusManager stylusManager = StylusManager.this;
                    BatteryState batteryState2 = batteryState;
                    stylusManager.getClass();
                    boolean z2 = batteryState2.isPresent() && batteryState2.getCapacity() > 0.0f;
                    StylusManager stylusManager2 = StylusManager.this;
                    int i3 = !((ArrayMap) stylusManager2.inputDeviceBtSessionIdMap).isEmpty() ? 1 : 0;
                    if (z2 && stylusManager2.usiSessionId == null) {
                        Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                        InstanceId newInstanceId = stylusManager2.instanceIdSequence.newInstanceId();
                        stylusManager2.usiSessionId = newInstanceId;
                        stylusManager2.uiEventLogger.logWithInstanceIdAndPosition(StylusUiEvent.USI_STYLUS_BATTERY_PRESENCE_FIRST_DETECTED, 0, (String) null, newInstanceId, i3);
                    } else if (!z2 && stylusManager2.usiSessionId != null) {
                        Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                        stylusManager2.uiEventLogger.logWithInstanceIdAndPosition(StylusUiEvent.USI_STYLUS_BATTERY_PRESENCE_REMOVED, 0, (String) null, stylusManager2.usiSessionId, i3);
                        stylusManager2.usiSessionId = null;
                    }
                    if (z2) {
                        StylusManager.this.onStylusUsed();
                    }
                    StylusManager stylusManager3 = StylusManager.this;
                    final int i4 = i;
                    final long j2 = j;
                    final BatteryState batteryState3 = batteryState;
                    stylusManager3.executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onBatteryStateChanged$1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            ((StylusManager.StylusCallback) obj).onStylusUsiBatteryStateChanged(i4, batteryState3);
                            return Unit.INSTANCE;
                        }
                    });
                }
            }
        });
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(final int i) {
        InputDevice inputDevice;
        if (this.hasStarted && (inputDevice = this.inputManager.getInputDevice(i)) != null && inputDevice.supportsSource(16386)) {
            int i2 = DebugLogger.$r8$clinit;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
            if (!inputDevice.isExternal()) {
                registerBatteryListener(i);
            }
            final String bluetoothAddress = inputDevice.getBluetoothAddress();
            ((ArrayMap) this.inputDeviceAddressMap).put(Integer.valueOf(i), bluetoothAddress);
            executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onInputDeviceAdded$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ((StylusManager.StylusCallback) obj).onStylusAdded(i);
                    return Unit.INSTANCE;
                }
            });
            if (bluetoothAddress != null) {
                onStylusUsed();
                onStylusBluetoothConnected(i, bluetoothAddress);
                executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onInputDeviceAdded$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return Unit.INSTANCE;
                    }
                });
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(final int i) {
        InputDevice inputDevice;
        if (this.hasStarted && (inputDevice = this.inputManager.getInputDevice(i)) != null && inputDevice.supportsSource(16386)) {
            int i2 = DebugLogger.$r8$clinit;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
            final String bluetoothAddress = inputDevice.getBluetoothAddress();
            final String str = (String) ((ArrayMap) this.inputDeviceAddressMap).get(Integer.valueOf(i));
            ((ArrayMap) this.inputDeviceAddressMap).put(Integer.valueOf(i), bluetoothAddress);
            if (str == null && bluetoothAddress != null) {
                onStylusBluetoothConnected(i, bluetoothAddress);
                executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onInputDeviceChanged$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return Unit.INSTANCE;
                    }
                });
            }
            if (str == null || bluetoothAddress != null) {
                return;
            }
            onStylusBluetoothDisconnected(i, str);
            executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onInputDeviceChanged$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return Unit.INSTANCE;
                }
            });
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(final int i) {
        if (this.hasStarted) {
            if (((ArrayMap) this.inputDeviceAddressMap).containsKey(Integer.valueOf(i))) {
                int i2 = DebugLogger.$r8$clinit;
                boolean z = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                try {
                    this.inputManager.removeInputDeviceBatteryListener(i, this);
                } catch (SecurityException e) {
                    Log.e(TAG, e + ": Failed to remove registered battery listener for " + i + ".");
                }
                final String str = (String) ((ArrayMap) this.inputDeviceAddressMap).get(Integer.valueOf(i));
                ((ArrayMap) this.inputDeviceAddressMap).remove(Integer.valueOf(i));
                if (str != null) {
                    onStylusBluetoothDisconnected(i, str);
                    executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onInputDeviceRemoved$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            return Unit.INSTANCE;
                        }
                    });
                }
                executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onInputDeviceRemoved$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return Unit.INSTANCE;
                    }
                });
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
                    Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull(linkedHashMap.keySet());
                    if (num != null) {
                        final int intValue = num.intValue();
                        final boolean equals = new String(bArr, Charsets.UTF_8).equals("true");
                        int i2 = DebugLogger.$r8$clinit;
                        StylusManager stylusManager2 = StylusManager.this;
                        boolean z = Build.IS_DEBUGGABLE;
                        Reflection.getOrCreateKotlinClass(stylusManager2.getClass()).getSimpleName();
                        StylusManager stylusManager3 = StylusManager.this;
                        final BluetoothDevice bluetoothDevice3 = bluetoothDevice;
                        stylusManager3.executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onMetadataChanged$1.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return Unit.INSTANCE;
                            }
                        });
                    }
                }
            }
        });
    }

    public final void onStylusBluetoothConnected(int i, String str) {
        trackAndLogBluetoothSession(i, true);
        BluetoothAdapter bluetoothAdapter = this.bluetoothAdapter;
        BluetoothDevice remoteDevice = bluetoothAdapter != null ? bluetoothAdapter.getRemoteDevice(str) : null;
        if (remoteDevice == null) {
            return;
        }
        try {
            this.bluetoothAdapter.addOnMetadataChangedListener(remoteDevice, this.executor, this);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e + ": Metadata listener already registered for device. Ignoring.");
        }
    }

    public final void onStylusBluetoothDisconnected(int i, String str) {
        trackAndLogBluetoothSession(i, false);
        BluetoothAdapter bluetoothAdapter = this.bluetoothAdapter;
        BluetoothDevice remoteDevice = bluetoothAdapter != null ? bluetoothAdapter.getRemoteDevice(str) : null;
        if (remoteDevice == null) {
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
            int i = DebugLogger.$r8$clinit;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
            InputSettings.setStylusEverUsed(this.context, true);
            executeStylusCallbacks(new Function1() { // from class: com.android.systemui.stylus.StylusManager$onStylusUsed$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ((StylusManager.StylusCallback) obj).onStylusFirstUsed();
                    return Unit.INSTANCE;
                }
            });
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
        int i2 = DebugLogger.$r8$clinit;
        boolean z2 = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
        if (z) {
            ((ArrayMap) this.inputDeviceBtSessionIdMap).put(Integer.valueOf(i), this.instanceIdSequence.newInstanceId());
            this.uiEventLogger.logWithInstanceId(StylusUiEvent.BLUETOOTH_STYLUS_CONNECTED, 0, (String) null, (InstanceId) ((ArrayMap) this.inputDeviceBtSessionIdMap).get(Integer.valueOf(i)));
            return;
        }
        this.uiEventLogger.logWithInstanceId(StylusUiEvent.BLUETOOTH_STYLUS_DISCONNECTED, 0, (String) null, (InstanceId) ((ArrayMap) this.inputDeviceBtSessionIdMap).get(Integer.valueOf(i)));
        ((ArrayMap) this.inputDeviceBtSessionIdMap).remove(Integer.valueOf(i));
    }

    public interface StylusCallback {
        default void onStylusFirstUsed() {
        }

        default void onStylusAdded(int i) {
        }

        default void onStylusUsiBatteryStateChanged(int i, BatteryState batteryState) {
        }
    }

    public static /* synthetic */ void getInstanceIdSequence$annotations() {
    }
}
