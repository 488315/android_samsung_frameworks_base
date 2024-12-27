package com.android.systemui.audio.soundcraft.interfaces.connectivity;

import android.bluetooth.BluetoothDevice;
import android.util.Log;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class BluetoothDeviceExtension {
    public static final BluetoothDeviceExtension INSTANCE = new BluetoothDeviceExtension();
    public static final byte[] OFF = {0};
    public static final byte[] ON = {1};

    private BluetoothDeviceExtension() {
    }

    public static String getBattery(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum) {
        int tag = bluetoothStateEnum.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (semGetMetadata == null) {
            return "";
        }
        String valueOf = semGetMetadata.length > 3 ? String.valueOf((int) semGetMetadata[3]) : "";
        return valueOf == null ? "" : valueOf;
    }

    public static boolean getState(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum) {
        int tag = bluetoothStateEnum.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        return semGetMetadata != null && semGetMetadata.length > 3 && semGetMetadata[3] == 1;
    }

    public static boolean isSupported(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum) {
        SmepTag smepTag = SmepTag.SUPPORTED_FEATURES;
        int tag = smepTag.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (semGetMetadata == null || semGetMetadata.length < 5) {
            return false;
        }
        INSTANCE.getClass();
        if (semGetMetadata.length < 5) {
            Log.e("SoundCraft.BluetoothDeviceExtension", "parseSupportedFeatures :: DataPacket is too short.");
            return false;
        }
        if ((((semGetMetadata[0] & 255) | ((semGetMetadata[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL) != smepTag.getTag()) {
            return false;
        }
        int i = 2;
        while (i < semGetMetadata.length) {
            int i2 = ((semGetMetadata[i] & 255) | ((semGetMetadata[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
            int i3 = semGetMetadata[i + 2] & 255;
            byte[] bArr = new byte[i3];
            System.arraycopy(semGetMetadata, i + 3, bArr, 0, i3);
            i += i3 + 3;
            if (SmepTag.getSmepKey(i2) == bluetoothStateEnum.getSupportedTag()) {
                return bArr[0] == 1;
            }
        }
        return false;
    }

    public static void setState(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum, boolean z) {
        byte[] bArr;
        Log.d("SoundCraft.BluetoothDeviceExtension", bluetoothStateEnum.getTitle() + " set " + z);
        int tag = bluetoothStateEnum.getTag();
        byte[] bArr2 = z ? ON : OFF;
        if (!SmepTag.isValidConstantKey(tag) || bArr2 == null || bArr2.length == 0) {
            bArr = null;
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayOutputStream.write(new byte[]{(byte) tag, (byte) (tag >> 8)});
                byteArrayOutputStream.write((byte) bArr2.length);
                byteArrayOutputStream.write(bArr2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bArr = byteArrayOutputStream.toByteArray();
        }
        bluetoothDevice.semSetMetadata(bArr);
    }
}
