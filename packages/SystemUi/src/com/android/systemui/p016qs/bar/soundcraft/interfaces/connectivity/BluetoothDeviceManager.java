package com.android.systemui.p016qs.bar.soundcraft.interfaces.connectivity;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.media.MediaBluetoothHelper;
import com.android.systemui.p016qs.bar.soundcraft.model.NoiseControl;
import com.android.systemui.volume.util.BluetoothA2dpUtil;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BluetoothDeviceManager {
    public static final byte[] OFF;

    /* renamed from: ON */
    public static final byte[] f332ON;
    public final BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1 bluetoothMetadataBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            BluetoothDeviceManager bluetoothDeviceManager;
            Function1 function1;
            String action = intent.getAction();
            if (Intrinsics.areEqual("com.samsung.bluetooth.device.action.META_DATA_CHANGED", action)) {
                Log.d("SoundCraft.BluetoothDeviceManager", "action: " + action);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice == null || (function1 = (bluetoothDeviceManager = BluetoothDeviceManager.this).callback) == null) {
                    return;
                }
                function1.invoke(bluetoothDeviceManager.getNoiseControlList(bluetoothDevice));
            }
        }
    };
    public Function1 callback;
    public final Context context;
    public boolean isSupportANC;
    public boolean isSupportAdaptive;
    public boolean isSupportAmbient;
    public final MediaBluetoothHelper mediaBluetoothHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SmepTag.values().length];
            try {
                iArr[SmepTag.FEATURE_ANC_LEVEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SmepTag.FEATURE_AMBIENT_LEVEL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SmepTag.FEATURE_RESPONSIVE_HEARING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        OFF = new byte[]{0};
        f332ON = new byte[]{1};
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager$bluetoothMetadataBroadcastReceiver$1] */
    public BluetoothDeviceManager(Context context, MediaBluetoothHelper mediaBluetoothHelper) {
        this.context = context;
        this.mediaBluetoothHelper = mediaBluetoothHelper;
    }

    public static byte[] intToBytes(int i) {
        return new byte[]{(byte) i, (byte) (i >> 8)};
    }

    public static byte[] makeTlv(int i, byte[] bArr) {
        if (!SmepTag.isValidConstantKey(i) || bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(intToBytes(i));
            byteArrayOutputStream.write((byte) bArr.length);
            byteArrayOutputStream.write(bArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
    
        if (r1 == null) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final BluetoothDevice getActiveDevice() {
        List list;
        BluetoothA2dp bluetoothA2dp = this.mediaBluetoothHelper.a2dp;
        if (bluetoothA2dp != null) {
            BluetoothA2dpUtil.INSTANCE.getClass();
            list = BluetoothA2dpUtil.getOrderConnectedDevices(bluetoothA2dp);
            if (list != null) {
                if (!(!list.isEmpty())) {
                    list = null;
                }
            }
        }
        list = EmptyList.INSTANCE;
        return (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(list);
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
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(intToBytes(SmepTag.SUPPORTED_FEATURES.getTag()));
        if (semGetMetadata == null || semGetMetadata.length < 5) {
            Log.e("SoundCraft.BluetoothDeviceManager", "parseSupportedFeatures :: DataPacket is too short.");
        } else if ((((semGetMetadata[0] & 255) | ((semGetMetadata[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL) == SmepTag.SUPPORTED_FEATURES.getTag()) {
            int i = 2;
            while (i < semGetMetadata.length) {
                int i2 = ((semGetMetadata[i] & 255) | ((semGetMetadata[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
                int i3 = semGetMetadata[i + 2] & 255;
                byte[] bArr = new byte[i3];
                System.arraycopy(semGetMetadata, i + 3, bArr, 0, i3);
                i += i3 + 3;
                SmepTag smepKey = SmepTag.getSmepKey(i2);
                int i4 = smepKey == null ? -1 : WhenMappings.$EnumSwitchMapping$0[smepKey.ordinal()];
                if (i4 == 1) {
                    this.isSupportANC = bArr[0] == 1;
                } else if (i4 == 2) {
                    this.isSupportAmbient = bArr[0] == 1;
                } else if (i4 == 3) {
                    this.isSupportAdaptive = bArr[0] == 1;
                }
            }
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("getNoiseControlList isSupportANC: ", this.isSupportANC, ", isSupportAmbient: ", this.isSupportAmbient, ", isSupportAdaptive: "), this.isSupportAdaptive, "SoundCraft.BluetoothDeviceManager");
        if (this.isSupportANC) {
            byte[] semGetMetadata2 = bluetoothDevice.semGetMetadata(intToBytes(SmepTag.STATE_ANC.getTag()));
            z = semGetMetadata2 != null && semGetMetadata2.length > 3 && semGetMetadata2[3] == 1;
            linkedHashSet.add(new NoiseControl(getActiveNoiseControlTitle(), z));
        } else {
            z = false;
        }
        if (this.isSupportAmbient) {
            byte[] semGetMetadata3 = bluetoothDevice.semGetMetadata(intToBytes(SmepTag.STATE_AMBIENT.getTag()));
            z2 = semGetMetadata3 != null && semGetMetadata3.length > 3 && semGetMetadata3[3] == 1;
            linkedHashSet.add(new NoiseControl(getAmbientSoundTitle(), z2));
        } else {
            z2 = false;
        }
        if (this.isSupportAdaptive) {
            byte[] semGetMetadata4 = bluetoothDevice.semGetMetadata(intToBytes(SmepTag.STATE_RESPONSIVE_HEARING.getTag()));
            z3 = semGetMetadata4 != null && semGetMetadata4.length > 3 && semGetMetadata4[3] == 1;
            linkedHashSet.add(new NoiseControl(getAdaptiveTitle(), z3));
        } else {
            z3 = false;
        }
        linkedHashSet.add(new NoiseControl(getNoiseControlOffTitle(), (z || z2 || z3) ? false : true));
        return linkedHashSet;
    }

    public final String getNoiseControlOffTitle() {
        return this.context.getResources().getString(R.string.sound_craft_wearable_noise_control_off);
    }

    public final void updateNoiseControlList(NoiseControl noiseControl) {
        BluetoothDevice activeDevice = getActiveDevice();
        if (activeDevice != null) {
            String name = noiseControl.getName();
            boolean areEqual = Intrinsics.areEqual(name, getActiveNoiseControlTitle());
            byte[] bArr = f332ON;
            if (areEqual) {
                Log.d("SoundCraft.BluetoothDeviceManager", "setAncState");
                activeDevice.semSetMetadata(makeTlv(SmepTag.STATE_ANC.getTag(), bArr));
                return;
            }
            if (Intrinsics.areEqual(name, getAmbientSoundTitle())) {
                Log.d("SoundCraft.BluetoothDeviceManager", "setAmbientState");
                activeDevice.semSetMetadata(makeTlv(SmepTag.STATE_AMBIENT.getTag(), bArr));
                return;
            }
            if (!Intrinsics.areEqual(name, getNoiseControlOffTitle())) {
                if (Intrinsics.areEqual(name, getAdaptiveTitle())) {
                    Log.d("SoundCraft.BluetoothDeviceManager", "setAdaptiveState");
                    activeDevice.semSetMetadata(makeTlv(SmepTag.STATE_RESPONSIVE_HEARING.getTag(), bArr));
                    return;
                }
                return;
            }
            Log.d("SoundCraft.BluetoothDeviceManager", "setAncState");
            int tag = SmepTag.STATE_ANC.getTag();
            byte[] bArr2 = OFF;
            activeDevice.semSetMetadata(makeTlv(tag, bArr2));
            Log.d("SoundCraft.BluetoothDeviceManager", "setAmbientState");
            activeDevice.semSetMetadata(makeTlv(SmepTag.STATE_AMBIENT.getTag(), bArr2));
            Log.d("SoundCraft.BluetoothDeviceManager", "setAdaptiveState");
            activeDevice.semSetMetadata(makeTlv(SmepTag.STATE_RESPONSIVE_HEARING.getTag(), bArr2));
        }
    }
}
