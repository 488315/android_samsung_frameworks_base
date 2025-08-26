package com.android.systemui.audio.soundcraft.interfaces.connectivity;

import android.bluetooth.BluetoothDevice;
import android.util.Log;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public final class BluetoothDeviceExtension {
    public static final BluetoothDeviceExtension INSTANCE = new BluetoothDeviceExtension();
    public static final byte[] OFF = {0};
    public static final byte[] ON = {1};

    private BluetoothDeviceExtension() {
    }

    public static String getBattery(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum) {
        int tag = bluetoothStateEnum.getTag();
        byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (bArrSemGetMetadata != null) {
            String strValueOf = bArrSemGetMetadata.length > 3 ? String.valueOf((int) bArrSemGetMetadata[3]) : "";
            if (strValueOf != null) {
                return strValueOf;
            }
        }
        return "";
    }

    public static boolean getState(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum) {
        int tag = bluetoothStateEnum.getTag();
        byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        return bArrSemGetMetadata != null && bArrSemGetMetadata.length > 3 && bArrSemGetMetadata[3] == 1;
    }

    public static boolean isSupported(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum) {
        SmepTag smepTag = SmepTag.SUPPORTED_FEATURES;
        int tag = smepTag.getTag();
        byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (bArrSemGetMetadata != null && bArrSemGetMetadata.length >= 5) {
            INSTANCE.getClass();
            if (bArrSemGetMetadata.length < 5) {
                Log.e("SoundCraft.BluetoothDeviceExtension", "parseSupportedFeatures :: DataPacket is too short.");
                return false;
            }
            if ((((bArrSemGetMetadata[0] & 255) | ((bArrSemGetMetadata[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL) == smepTag.getTag()) {
                int i = 2;
                while (true) {
                    if (i >= bArrSemGetMetadata.length) {
                        break;
                    }
                    int i2 = ((bArrSemGetMetadata[i] & 255) | ((bArrSemGetMetadata[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
                    int i3 = bArrSemGetMetadata[i + 2] & 255;
                    byte[] bArr = new byte[i3];
                    System.arraycopy(bArrSemGetMetadata, i + 3, bArr, 0, i3);
                    i += i3 + 3;
                    if (SmepTag.getSmepKey(i2) == bluetoothStateEnum.getSupportedTag()) {
                        if (bArr[0] == 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void setState(BluetoothDevice bluetoothDevice, BluetoothStateEnum bluetoothStateEnum, boolean z) {
        byte[] byteArray;
        Log.d("SoundCraft.BluetoothDeviceExtension", bluetoothStateEnum.getTitle() + " set " + z);
        int tag = bluetoothStateEnum.getTag();
        byte[] bArr = z ? ON : OFF;
        if (!SmepTag.isValidConstantKey(tag) || bArr == null || bArr.length == 0) {
            byteArray = null;
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayOutputStream.write(new byte[]{(byte) tag, (byte) (tag >> 8)});
                byteArrayOutputStream.write((byte) bArr.length);
                byteArrayOutputStream.write(bArr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArray = byteArrayOutputStream.toByteArray();
        }
        bluetoothDevice.semSetMetadata(byteArray);
    }
}
