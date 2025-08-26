package android.sec.enterprise;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.net.Uri;
import android.os.ParcelUuid;
import android.sec.enterprise.BluetoothPolicy;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes3.dex */
public class BluetoothUtils {
    public static final int NO_PROFILE = -1;
    private static final String TAG = "BluetoothUtils";
    static final int TYPE_L2CAP = 3;
    static final int TYPE_RFCOMM = 1;
    static final int TYPE_SCO = 2;

    private static boolean getBluetoothProfileEnabled(ParcelUuid parcelUuid) {
        int i;
        if (BluetoothUuid.SAP.equals(parcelUuid)) {
            i = 256;
        } else if (parcelUuid.equals(BluetoothUuid.A2DP_SOURCE) || parcelUuid.equals(BluetoothUuid.ADV_AUDIO_DIST)) {
            i = 8;
        } else if (BluetoothUuid.HSP.equals(parcelUuid) || BluetoothUuid.HSP_AG.equals(parcelUuid)) {
            i = 1;
        } else {
            i = (parcelUuid.equals(BluetoothUuid.HFP) || parcelUuid.equals(BluetoothUuid.HFP_AG)) ? 2 : -1;
        }
        if (i != -1) {
            return EnterpriseDeviceManager.getInstance().getBluetoothPolicy().isProfileEnabled(i);
        }
        return true;
    }

    public static boolean isSvcRfComPortNumberBlockedBySecurityPolicy(int i) {
        BluetoothPolicy bluetoothPolicy;
        try {
            bluetoothPolicy = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
        } catch (Exception e) {
            Log.w("BluetoothUtils", e.toString());
        }
        if (!bluetoothPolicy.isProfileEnabled(128)) {
            Log.d("BluetoothUtils", "MDM - SPP Profile is disabled");
            return false;
        }
        String[][] strArr = {new String[]{AudioParameter.VALUE_VM_CSD_500_WARNING, BluetoothPolicy.BluetoothUUID.OBEXOBJECTPUSH_UUID}, new String[]{"19", BluetoothPolicy.BluetoothUUID.PBAP_UUID}};
        for (int i2 = 0; i2 < 2; i2++) {
            String[] strArr2 = strArr[i2];
            String str = strArr2[0];
            String str2 = strArr2[1];
            if (Integer.parseInt(str) == i && !bluetoothPolicy.isBluetoothUUIDAllowed(str2)) {
                Log.d("BluetoothUtils", "MDM: Profile UUID = " + str2 + " Blocked");
                return true;
            }
        }
        return false;
    }

    public static boolean isHeadsetAllowedBySecurityPolicy(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return true;
        }
        BluetoothPolicy bluetoothPolicy = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
        if (!bluetoothPolicy.isProfileEnabled(128)) {
            Log.d("BluetoothUtils", "MDM - SPP Profile is disabled");
            return false;
        }
        if (!bluetoothPolicy.isProfileEnabled(1)) {
            Log.d("BluetoothUtils", "MDM: HSP profile  is disabled");
            return false;
        }
        if (!bluetoothPolicy.isProfileEnabled(2)) {
            Log.d("BluetoothUtils", "MDM: HFP profile is disabled");
            return false;
        }
        if (bluetoothPolicy.isBluetoothDeviceAllowed(bluetoothDevice.getAddress())) {
            return true;
        }
        Log.d("BluetoothUtils", "MDM: Remote Device Blocked");
        return false;
    }

    public static boolean isPairingAllowedbySecurityPolicy(String str) {
        BluetoothPolicy bluetoothPolicy = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
        if (!bluetoothPolicy.isPairingEnabled()) {
            Log.d("BluetoothUtils", "MDM: Pairing Blocked");
            return false;
        }
        if (bluetoothPolicy.isBluetoothDeviceAllowed(str)) {
            return true;
        }
        Log.d("BluetoothUtils", "MDM: Remote Device Blocked");
        return false;
    }

    public static boolean isProfileAuthorizedBySecurityPolicy(ParcelUuid parcelUuid) {
        return isProfileAuthorizedBySecurityPolicy(parcelUuid, 1);
    }

    public static boolean isProfileAuthorizedBySecurityPolicy(ParcelUuid parcelUuid, int i) {
        BluetoothPolicy bluetoothPolicy = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
        if (2 == i && !bluetoothPolicy.isOutgoingCallsAllowed()) {
            Log.d("BluetoothUtils", "MDM: Outgoing Call is Disabled");
            return false;
        }
        if ((parcelUuid.equals(BluetoothUuid.A2DP_SOURCE) || parcelUuid.equals(BluetoothUuid.ADV_AUDIO_DIST)) && (!bluetoothPolicy.isProfileEnabled(8) || !bluetoothPolicy.isProfileEnabled(128))) {
            Log.d("BluetoothUtils", "MDM: SPP or A2DP profile is disabled");
            AuditLog.logEvent(78, new Object[0]);
            return false;
        }
        if ((parcelUuid.equals(BluetoothUuid.AVRCP_TARGET) || parcelUuid.equals(BluetoothUuid.AVRCP_CONTROLLER)) && (!bluetoothPolicy.isProfileEnabled(16) || !bluetoothPolicy.isProfileEnabled(128))) {
            Log.d("BluetoothUtils", "MDM: AVRCP profile is disabled");
            return false;
        }
        if (parcelUuid.equals(BluetoothUuid.OBEX_OBJECT_PUSH) && (!bluetoothPolicy.getAllowBluetoothDataTransfer(true) || !bluetoothPolicy.isProfileEnabled(128))) {
            Log.d("BluetoothUtils", "MDM: OPP profile is disabled");
            AuditLog.logEvent(73, new Object[0]);
            return false;
        }
        if (parcelUuid.equals(BluetoothUuid.MAP) && !bluetoothPolicy.isProfileEnabled(128)) {
            Log.d("BluetoothUtils", "MDM: MAP profile is disabled");
            return false;
        }
        if (BluetoothUuid.SAP.equals(parcelUuid) && (!bluetoothPolicy.isProfileEnabled(256) || !bluetoothPolicy.isProfileEnabled(128))) {
            Log.d("BluetoothUtils", "MDM: SAP profile is disabled");
            return false;
        }
        if (!bluetoothPolicy.isBluetoothUUIDAllowed(parcelUuid.toString()) || !getBluetoothProfileEnabled(parcelUuid)) {
            Log.d("BluetoothUtils", "MDM: profile UUID = " + parcelUuid.toString() + " is disabled");
            return false;
        }
        if (parcelUuid.equals(BluetoothUuid.OBEX_OBJECT_PUSH)) {
            AuditLog.logEvent(74, new Object[0]);
        }
        return true;
    }

    public static void bluetoothLog(String str, String str2) {
        try {
            EnterpriseDeviceManager.getInstance().getBluetoothPolicy().bluetoothLog(str, str2);
        } catch (Exception unused) {
            Log.e("BluetoothUtils", "Exception on blutoothLog");
        }
    }

    public static void bluetoothSocketLog(String str, BluetoothDevice bluetoothDevice, int i, int i2) {
        if (bluetoothDevice == null) {
            return;
        }
        try {
            if (i2 == 1) {
                bluetoothLog("RFCOMM " + str, bluetoothDevice.getName(), bluetoothDevice.getAddress());
            } else if (i2 == 2) {
                bluetoothLog("SCO " + str, bluetoothDevice.getName(), bluetoothDevice.getAddress());
            } else {
                if (i2 != 3) {
                    return;
                }
                bluetoothLog("L2CAP " + str, bluetoothDevice.getName(), bluetoothDevice.getAddress());
            }
        } catch (Exception unused) {
            Log.e("BluetoothUtils", "Exception on bluetoothLogSocket");
        }
    }

    public static void bluetoothLog(String str, String str2, String str3) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        StringBuilder sb = new StringBuilder();
        if (defaultAdapter != null) {
            sb.append("Local Name: ");
            sb.append(defaultAdapter.getName());
            sb.append("\nLocal Address: ");
            sb.append(defaultAdapter.getAddress());
            sb.append('\n');
        }
        if (str2 != null && str2.length() > 0) {
            sb.append("Remote Name: ");
            sb.append(str2);
            sb.append('\n');
        }
        if (str3 != null && str3.length() > 0) {
            sb.append("Remote Address: ");
            sb.append(str3);
            sb.append('\n');
        }
        bluetoothLog(str, sb.toString());
    }

    public static boolean isBluetoothLogEnabled() {
        try {
            return EnterpriseDeviceManager.getInstance().getBluetoothPolicy().isBluetoothLogEnabled();
        } catch (Exception unused) {
            Log.e("BluetoothUtils", "Exception on isBluetoothLogEnabled");
            return false;
        }
    }

    public static void bluetoothLog(String str, int i, BluetoothDevice bluetoothDevice) {
        String address;
        String name;
        String address2;
        String name2;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            address = "";
            name = address;
        } else {
            name = defaultAdapter.getName();
            address = defaultAdapter.getAddress();
        }
        if (bluetoothDevice == null) {
            address2 = "";
            name2 = address2;
        } else {
            name2 = bluetoothDevice.getName();
            address2 = bluetoothDevice.getAddress();
        }
        StringBuilder sb = new StringBuilder("");
        if (i != -1) {
            sb.append(convertBluetoothProfile(i));
        }
        if (address2 != null && address2.length() > 0) {
            sb.append("Remote Address: ");
            sb.append(address2);
            sb.append('\n');
        }
        if (address2 != null && address2.length() > 0) {
            sb.append("Remote Name: ");
            sb.append(name2);
            sb.append('\n');
        }
        if (address != null && address.length() > 0) {
            sb.append("Local Address: ");
            sb.append(address);
            sb.append('\n');
        }
        if (address != null && address.length() > 0) {
            sb.append("Local Name: ");
            sb.append(name);
            sb.append('\n');
        }
        bluetoothLog(str, sb.toString());
    }

    public static void bluetoothLog(String str, String str2, String str3, String str4, Uri uri, String str5) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        StringBuilder sb = new StringBuilder();
        if (str4.length() > 0) {
            sb.append("Profile: " + str4);
            sb.append('\n');
        }
        if (uri != null && uri.toString().length() > 0) {
            sb.append("URI: " + uri);
            sb.append('\n');
        }
        if (str5 != null && str5.length() > 0) {
            sb.append("Filename: " + str5);
            sb.append('\n');
        }
        if (defaultAdapter != null) {
            sb.append("Local Name: ");
            sb.append(defaultAdapter.getName());
            sb.append("\nLocal Address: ");
            sb.append(defaultAdapter.getAddress());
            sb.append('\n');
        }
        if (str2 != null && str2.length() > 0) {
            sb.append("Remote Name: ");
            sb.append(str2);
            sb.append('\n');
        }
        if (str3 != null && str3.length() > 0) {
            sb.append("Remote Address: ");
            sb.append(str3);
            sb.append('\n');
        }
        bluetoothLog(str, sb.toString());
    }

    private static String convertBluetoothProfile(int i) {
        if (i != 9) {
            switch (i) {
                case 1:
                    return "Profile: Headset and Handsfree\n";
                case 2:
                    return "Profile: A2DP\n";
                case 3:
                    return "Profile: HEALTH\n";
                case 4:
                    return "Profile: INPUT DEVICE\n";
                case 5:
                    return "Profile: PAN\n";
                case 6:
                    return "Profile: PBAP\n";
                default:
                    return "";
            }
        }
        return "Profile: MAP\n";
    }
}
