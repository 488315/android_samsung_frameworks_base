package com.android.systemui.audio.soundcraft.interfaces.connectivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.model.buds.BatteryInfo;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.audio.soundcraft.utils.ToastUtil;
import com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.samsung.android.bluetooth.SmepTag;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class BluetoothDeviceManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean adaptiveRequested;
    public boolean ambientRequested;
    public boolean ancRequested;
    public SoundCraftViewModel$$ExternalSyntheticLambda0 batteryInfoCallback;
    public final Context context;
    public boolean isRegister;
    public SoundCraftViewModel$$ExternalSyntheticLambda0 noiseControlCallback;
    public BatteryInfo currentBatteryInfo = new BatteryInfo(null, null, null, 7, null);
    public Set currentNoiseControlList = new LinkedHashSet();
    public boolean isChanged = true;
    public final BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1 bluetoothMetadataBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) throws Resources.NotFoundException {
            String action = intent.getAction();
            if ("com.samsung.bluetooth.device.action.META_DATA_CHANGED".equals(action)) {
                Log.d("SoundCraft.BluetoothDeviceManager", "action: " + action);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BluetoothDeviceManager bluetoothDeviceManager = this.this$0;
                int i = BluetoothDeviceManager.$r8$clinit;
                if (!Intrinsics.areEqual(bluetoothDevice, bluetoothDeviceManager.getActiveDevice()) || bluetoothDevice == null) {
                    return;
                }
                BluetoothDeviceManager bluetoothDeviceManager2 = this.this$0;
                Set noiseControlList = bluetoothDeviceManager2.getNoiseControlList(bluetoothDevice);
                if (bluetoothDeviceManager2.currentNoiseControlList.isEmpty() || !noiseControlList.equals(bluetoothDeviceManager2.currentNoiseControlList) || bluetoothDeviceManager2.isChanged) {
                    bluetoothDeviceManager2.currentNoiseControlList = noiseControlList;
                    SoundCraftViewModel$$ExternalSyntheticLambda0 soundCraftViewModel$$ExternalSyntheticLambda0 = bluetoothDeviceManager2.noiseControlCallback;
                    if (soundCraftViewModel$$ExternalSyntheticLambda0 != null) {
                        soundCraftViewModel$$ExternalSyntheticLambda0.mo781invoke(noiseControlList);
                    }
                    bluetoothDeviceManager2.isChanged = false;
                }
                BatteryInfo batteryInfo = BluetoothDeviceManager.getBatteryInfo(bluetoothDevice);
                if (Intrinsics.areEqual(bluetoothDeviceManager2.currentBatteryInfo, batteryInfo)) {
                    return;
                }
                bluetoothDeviceManager2.currentBatteryInfo = batteryInfo;
                SoundCraftViewModel$$ExternalSyntheticLambda0 soundCraftViewModel$$ExternalSyntheticLambda02 = bluetoothDeviceManager2.batteryInfoCallback;
                if (soundCraftViewModel$$ExternalSyntheticLambda02 != null) {
                    soundCraftViewModel$$ExternalSyntheticLambda02.mo781invoke(batteryInfo);
                }
            }
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1] */
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

    public static boolean isSupportedSoundCraft(BluetoothDevice bluetoothDevice) {
        boolean z;
        BluetoothIconUtil.INSTANCE.getClass();
        int tag = SmepTag.SUPPORTED_FEATURES.getTag();
        byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (bArrSemGetMetadata == null) {
            bArrSemGetMetadata = null;
        }
        if (bArrSemGetMetadata != null) {
            z = !(bArrSemGetMetadata.length == 0);
        } else {
            z = false;
        }
        return z && BluetoothIconUtil.getDeviceId(bluetoothDevice) >= 313;
    }

    public final BluetoothDevice getActiveDevice() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        Object systemService = context.getSystemService((Class<Object>) BluetoothManager.class);
        systemService.getClass();
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

    public final Set getNoiseControlList(BluetoothDevice bluetoothDevice) throws Resources.NotFoundException {
        boolean state;
        boolean state2;
        boolean state3;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        BluetoothDeviceExtension.INSTANCE.getClass();
        BluetoothStateEnum bluetoothStateEnum = BluetoothStateEnum.ANC;
        if (BluetoothDeviceExtension.isSupported(bluetoothDevice, bluetoothStateEnum)) {
            state = BluetoothDeviceExtension.getState(bluetoothDevice, bluetoothStateEnum);
            linkedHashSet.add(new NoiseControl(getActiveNoiseControlTitle(), state));
            if (this.ancRequested && !state) {
                ToastUtil toastUtil = ToastUtil.INSTANCE;
                Context context = this.context;
                String string = context.getResources().getString(R.string.sound_craft_noise_control_fail_or_one_wearing_warning);
                toastUtil.getClass();
                ToastUtil.makeToast(context, string);
            }
            this.ancRequested = false;
        } else {
            state = false;
        }
        BluetoothStateEnum bluetoothStateEnum2 = BluetoothStateEnum.AMBIENT;
        if (BluetoothDeviceExtension.isSupported(bluetoothDevice, bluetoothStateEnum2)) {
            state2 = BluetoothDeviceExtension.getState(bluetoothDevice, bluetoothStateEnum2);
            linkedHashSet.add(new NoiseControl(getAmbientSoundTitle(), state2));
            if (this.ambientRequested && !state2) {
                ToastUtil toastUtil2 = ToastUtil.INSTANCE;
                Context context2 = this.context;
                String string2 = context2.getResources().getString(R.string.sound_craft_noise_control_fail_or_one_wearing_warning);
                toastUtil2.getClass();
                ToastUtil.makeToast(context2, string2);
            }
            this.ambientRequested = false;
        } else {
            state2 = false;
        }
        BluetoothStateEnum bluetoothStateEnum3 = BluetoothStateEnum.ADAPTIVE;
        if (BluetoothDeviceExtension.isSupported(bluetoothDevice, bluetoothStateEnum3)) {
            state3 = BluetoothDeviceExtension.getState(bluetoothDevice, bluetoothStateEnum3);
            linkedHashSet.add(new NoiseControl(getAdaptiveTitle(), state3));
            if (this.adaptiveRequested && !state3) {
                ToastUtil toastUtil3 = ToastUtil.INSTANCE;
                Context context3 = this.context;
                String string3 = context3.getResources().getString(R.string.sound_craft_noise_control_fail_or_one_wearing_warning);
                toastUtil3.getClass();
                ToastUtil.makeToast(context3, string3);
            }
            this.adaptiveRequested = false;
        } else {
            state3 = false;
        }
        if (!linkedHashSet.isEmpty()) {
            linkedHashSet.add(new NoiseControl(this.context.getResources().getString(R.string.sound_craft_wearable_noise_control_off), (state || state2 || state3) ? false : true));
            this.ancRequested = false;
            this.ambientRequested = false;
            this.adaptiveRequested = false;
        }
        linkedHashSet.add(new NoiseControl("wearing_l", BluetoothDeviceExtension.getState(bluetoothDevice, BluetoothStateEnum.WEARING_L)));
        linkedHashSet.add(new NoiseControl("wearing_r", BluetoothDeviceExtension.getState(bluetoothDevice, BluetoothStateEnum.WEARING_R)));
        return linkedHashSet;
    }
}
