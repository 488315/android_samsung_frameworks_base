package com.android.systemui.audio.soundcraft.interfaces.connectivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.model.buds.BatteryInfo;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.volume.util.BluetoothIconUtil;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class BluetoothDeviceManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Function1 batteryInfoCallback;
    public final Context context;
    public boolean isRegister;
    public Function1 noiseControlCallback;
    public BatteryInfo currentBatteryInfo = new BatteryInfo(null, null, null, 7, null);
    public Set currentNoiseControlList = new LinkedHashSet();
    public boolean isChanged = true;
    public final BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1 bluetoothMetadataBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.samsung.bluetooth.device.action.META_DATA_CHANGED".equals(action)) {
                Log.d("SoundCraft.BluetoothDeviceManager", "action: " + action);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BluetoothDeviceManager bluetoothDeviceManager = BluetoothDeviceManager.this;
                int i = BluetoothDeviceManager.$r8$clinit;
                if (!Intrinsics.areEqual(bluetoothDevice, bluetoothDeviceManager.getActiveDevice()) || bluetoothDevice == null) {
                    return;
                }
                BluetoothDeviceManager bluetoothDeviceManager2 = BluetoothDeviceManager.this;
                Set noiseControlList = bluetoothDeviceManager2.getNoiseControlList(bluetoothDevice);
                if (bluetoothDeviceManager2.currentNoiseControlList.isEmpty() || !noiseControlList.equals(bluetoothDeviceManager2.currentNoiseControlList) || bluetoothDeviceManager2.isChanged) {
                    bluetoothDeviceManager2.currentNoiseControlList = noiseControlList;
                    Function1 function1 = bluetoothDeviceManager2.noiseControlCallback;
                    if (function1 != null) {
                        function1.invoke(noiseControlList);
                    }
                    bluetoothDeviceManager2.isChanged = false;
                }
                BatteryInfo batteryInfo = BluetoothDeviceManager.getBatteryInfo(bluetoothDevice);
                if (!Intrinsics.areEqual(bluetoothDeviceManager2.currentBatteryInfo, batteryInfo)) {
                    bluetoothDeviceManager2.currentBatteryInfo = batteryInfo;
                    Function1 function12 = bluetoothDeviceManager2.batteryInfoCallback;
                    if (function12 != null) {
                        function12.invoke(batteryInfo);
                    }
                }
            }
        }
    };

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BluetoothDeviceManager(Context context) {
        this.context = context;
    }

    public static BatteryInfo getBatteryInfo(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return new BatteryInfo(null, null, null, 7, null);
        }
        BluetoothDeviceExtension bluetoothDeviceExtension = BluetoothDeviceExtension.INSTANCE;
        BluetoothStateEnum bluetoothStateEnum = BluetoothStateEnum.BATTERY_LEFT;
        bluetoothDeviceExtension.getClass();
        return new BatteryInfo(BluetoothDeviceExtension.getBattery(bluetoothDevice, bluetoothStateEnum), BluetoothDeviceExtension.getBattery(bluetoothDevice, BluetoothStateEnum.BATTERY_RIGHT), BluetoothDeviceExtension.getBattery(bluetoothDevice, BluetoothStateEnum.BATTERY_CRADLE));
    }

    public static boolean isBuds3OrNextModel(BluetoothDevice bluetoothDevice) {
        BluetoothIconUtil.INSTANCE.getClass();
        return BluetoothIconUtil.isBudsSeries(bluetoothDevice) && BluetoothIconUtil.getDeviceId(bluetoothDevice) >= 333;
    }

    public final BluetoothDevice getActiveDevice() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        Object systemService = context.getSystemService((Class<Object>) BluetoothManager.class);
        Intrinsics.checkNotNull(systemService);
        return (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(((BluetoothManager) systemService).getAdapter().getActiveDevices(2));
    }

    public final String getActiveNoiseControlTitle() {
        return this.context.getResources().getString(R.string.sound_craft_noise_cancelling);
    }

    public final String getAdaptiveTitle() {
        return this.context.getResources().getString(R.string.sound_craft_adaptive);
    }

    public final String getAmbientSoundTitle() {
        return this.context.getResources().getString(R.string.sound_craft_ambient_sound);
    }

    public final Set getNoiseControlList(BluetoothDevice bluetoothDevice) {
        boolean z;
        boolean z2;
        boolean z3;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        BluetoothDeviceExtension.INSTANCE.getClass();
        BluetoothStateEnum bluetoothStateEnum = BluetoothStateEnum.ANC;
        boolean z4 = false;
        if (BluetoothDeviceExtension.isSupported(bluetoothDevice, bluetoothStateEnum)) {
            z = BluetoothDeviceExtension.getState(bluetoothDevice, bluetoothStateEnum);
            linkedHashSet.add(new NoiseControl(getActiveNoiseControlTitle(), z));
        } else {
            z = false;
        }
        BluetoothStateEnum bluetoothStateEnum2 = BluetoothStateEnum.AMBIENT;
        if (BluetoothDeviceExtension.isSupported(bluetoothDevice, bluetoothStateEnum2)) {
            z2 = BluetoothDeviceExtension.getState(bluetoothDevice, bluetoothStateEnum2);
            linkedHashSet.add(new NoiseControl(getAmbientSoundTitle(), z2));
        } else {
            z2 = false;
        }
        BluetoothStateEnum bluetoothStateEnum3 = BluetoothStateEnum.ADAPTIVE;
        if (BluetoothDeviceExtension.isSupported(bluetoothDevice, bluetoothStateEnum3)) {
            z3 = BluetoothDeviceExtension.getState(bluetoothDevice, bluetoothStateEnum3);
            linkedHashSet.add(new NoiseControl(getAdaptiveTitle(), z3));
        } else {
            z3 = false;
        }
        if (!linkedHashSet.isEmpty()) {
            String noiseControlOffTitle = getNoiseControlOffTitle();
            if (!z && !z2 && !z3) {
                z4 = true;
            }
            linkedHashSet.add(new NoiseControl(noiseControlOffTitle, z4));
        }
        linkedHashSet.add(new NoiseControl("wearing_l", BluetoothDeviceExtension.getState(bluetoothDevice, BluetoothStateEnum.WEARING_L)));
        linkedHashSet.add(new NoiseControl("wearing_r", BluetoothDeviceExtension.getState(bluetoothDevice, BluetoothStateEnum.WEARING_R)));
        return linkedHashSet;
    }

    public final String getNoiseControlOffTitle() {
        return this.context.getResources().getString(R.string.sound_craft_wearable_noise_control_off);
    }

    public final void updateNoiseControlList(NoiseControl noiseControl) {
        BluetoothDevice activeDevice = getActiveDevice();
        if (activeDevice != null) {
            String name = noiseControl.getName();
            if (Intrinsics.areEqual(name, getActiveNoiseControlTitle())) {
                BluetoothDeviceExtension.INSTANCE.getClass();
                BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ANC, true);
            } else if (Intrinsics.areEqual(name, getAmbientSoundTitle())) {
                BluetoothDeviceExtension.INSTANCE.getClass();
                BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.AMBIENT, true);
            } else if (Intrinsics.areEqual(name, getAdaptiveTitle())) {
                BluetoothDeviceExtension.INSTANCE.getClass();
                BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ADAPTIVE, true);
            } else if (Intrinsics.areEqual(name, getNoiseControlOffTitle())) {
                BluetoothDeviceExtension.INSTANCE.getClass();
                BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ANC, false);
                BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.AMBIENT, false);
                BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ADAPTIVE, false);
            }
            this.isChanged = true;
        }
    }
}
