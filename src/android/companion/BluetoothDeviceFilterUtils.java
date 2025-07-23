package android.companion;

import android.bluetooth.BluetoothDevice;
import android.net.wifi.ScanResult;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class BluetoothDeviceFilterUtils {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "CDM_BluetoothDeviceFilterUtils";

    private BluetoothDeviceFilterUtils() {
    }

    static String patternToString(Pattern pattern) {
        if (pattern == null) {
            return null;
        }
        return pattern.pattern();
    }

    static Pattern patternFromString(String str) {
        if (str == null) {
            return null;
        }
        return Pattern.compile(str);
    }

    static boolean matchesAddress(String str, BluetoothDevice bluetoothDevice) {
        if (str != null) {
            return bluetoothDevice != null && str.equals(bluetoothDevice.getAddress());
        }
        return true;
    }

    static boolean matchesServiceUuids(List<ParcelUuid> list, List<ParcelUuid> list2, BluetoothDevice bluetoothDevice) {
        for (int i = 0; i < list.size(); i++) {
            if (!matchesServiceUuid(list.get(i), list2.get(i), bluetoothDevice)) {
                return false;
            }
        }
        return true;
    }

    static boolean matchesServiceUuid(ParcelUuid parcelUuid, ParcelUuid parcelUuid2, BluetoothDevice bluetoothDevice) {
        List asList = bluetoothDevice.getUuids() == null ? Collections.EMPTY_LIST : Arrays.asList(bluetoothDevice.getUuids());
        if (parcelUuid == null) {
            return true;
        }
        Iterator it = asList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (uuidsMaskedEquals(((ParcelUuid) it.next()).getUuid(), parcelUuid.getUuid(), parcelUuid2 == null ? null : parcelUuid2.getUuid())) {
                z = true;
            }
        }
        return z;
    }

    static boolean matchesName(Pattern pattern, BluetoothDevice bluetoothDevice) {
        String name;
        if (pattern == null) {
            return true;
        }
        return (bluetoothDevice == null || (name = bluetoothDevice.getName()) == null || !pattern.matcher(name).find()) ? false : true;
    }

    static boolean matchesName(Pattern pattern, ScanResult scanResult) {
        String str;
        if (pattern == null) {
            return true;
        }
        return (scanResult == null || (str = scanResult.SSID) == null || !pattern.matcher(str).find()) ? false : true;
    }

    private static void debugLogMatchResult(boolean z, BluetoothDevice bluetoothDevice, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDeviceDisplayNameInternal(bluetoothDevice));
        sb.append(z ? " ~ " : " !~ ");
        sb.append(obj);
        Log.i(LOG_TAG, sb.toString());
    }

    private static void debugLogMatchResult(boolean z, ScanResult scanResult, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDeviceDisplayNameInternal(scanResult));
        sb.append(z ? " ~ " : " !~ ");
        sb.append(obj);
        Log.i(LOG_TAG, sb.toString());
    }

    public static String getDeviceDisplayNameInternal(BluetoothDevice bluetoothDevice) {
        return TextUtils.firstNotEmpty(bluetoothDevice.getAlias(), bluetoothDevice.getAddress());
    }

    public static String getDeviceDisplayNameInternal(ScanResult scanResult) {
        return TextUtils.firstNotEmpty(scanResult.SSID, scanResult.BSSID);
    }

    public static String getDeviceMacAddress(Parcelable parcelable) {
        if (parcelable instanceof BluetoothDevice) {
            return ((BluetoothDevice) parcelable).getAddress();
        }
        if (parcelable instanceof ScanResult) {
            return ((ScanResult) parcelable).BSSID;
        }
        if (parcelable instanceof android.bluetooth.le.ScanResult) {
            return getDeviceMacAddress(((android.bluetooth.le.ScanResult) parcelable).getDevice());
        }
        throw new IllegalArgumentException("Unknown device type: " + parcelable);
    }

    public static boolean uuidsMaskedEquals(UUID uuid, UUID uuid2, UUID uuid3) {
        if (uuid3 == null) {
            return Objects.equals(uuid, uuid2);
        }
        return (uuid.getLeastSignificantBits() & uuid3.getLeastSignificantBits()) == (uuid2.getLeastSignificantBits() & uuid3.getLeastSignificantBits()) && (uuid.getMostSignificantBits() & uuid3.getMostSignificantBits()) == (uuid2.getMostSignificantBits() & uuid3.getMostSignificantBits());
    }
}
