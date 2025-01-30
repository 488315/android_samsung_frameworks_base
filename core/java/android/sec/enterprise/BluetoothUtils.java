package android.sec.enterprise;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.net.Uri;
import android.os.Binder;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditEvents;
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

  public static boolean isSocketAllowedBySecurityPolicy(
      BluetoothDevice device, int aPortNum, int aPortType, ParcelUuid uuid) {
    long token;
    BluetoothPolicy service = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
    if (1 == aPortType
        && (!service.getAllowBluetoothDataTransfer(true) || !service.isProfileEnabled(128))) {
      Log.m102w(
          "BluetoothUtils",
          "isSocketAllowedBySecurityPolicy : device requesting for spp, block it");
      token = Binder.clearCallingIdentity();
      try {
        AuditLog.logAsUser(
            5,
            5,
            false,
            Process.myPid(),
            "BluetoothUtils",
            AuditEvents.AUDIT_EXCHANGING_DATA_VIA_BLUETOOTH_FAILED_RESTRICTED,
            UserHandle.getUserId(Binder.getCallingUid()));
        return false;
      } finally {
      }
    }
    if (uuid != null
        && (!service.isBluetoothUUIDAllowed(uuid.toString())
            || !getBluetoothProfileEnabled(uuid))) {
      Log.m94d("BluetoothUtils", "MDM: profile UUID = " + uuid.toString() + " is disabled");
      return false;
    }
    if (device == null || aPortNum < 0) {
      Log.m94d("BluetoothUtils", "isSocketAllowedBySecurityPolicy start : device null");
      return true;
    }
    ParcelUuid[] ids = device.getUuids();
    if (ids == null) {
      return true;
    }
    for (int i = 0; i < ids.length; i++) {}
    if (1 == aPortType) {
      token = Binder.clearCallingIdentity();
      try {
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "BluetoothUtils",
            AuditEvents.AUDIT_EXCHANGING_DATA_VIA_BLUETOOTH_SUCCEEDED,
            UserHandle.getUserId(Binder.getCallingUid()));
      } finally {
      }
    }
    return true;
  }

  private static boolean getBluetoothProfileEnabled(ParcelUuid uuid) {
    int profile = -1;
    if (BluetoothUuid.SAP.equals(uuid)) {
      profile = 256;
    } else if (uuid.equals(BluetoothUuid.A2DP_SOURCE)
        || uuid.equals(BluetoothUuid.ADV_AUDIO_DIST)) {
      profile = 8;
    } else if (BluetoothUuid.HSP.equals(uuid) || BluetoothUuid.HSP_AG.equals(uuid)) {
      profile = 1;
    } else if (uuid.equals(BluetoothUuid.HFP) || uuid.equals(BluetoothUuid.HFP_AG)) {
      profile = 2;
    }
    if (profile != -1) {
      return EnterpriseDeviceManager.getInstance().getBluetoothPolicy().isProfileEnabled(profile);
    }
    return true;
  }

  public static boolean isSvcRfComPortNumberBlockedBySecurityPolicy(int aPortNum) {
    BluetoothPolicy service;
    try {
      service = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
    } catch (Exception e) {
      Log.m102w("BluetoothUtils", e.toString());
    }
    if (!service.isProfileEnabled(128)) {
      Log.m94d("BluetoothUtils", "MDM - SPP Profile is disabled");
      return false;
    }
    String[][] RESERVED_RFCOMM_CHANNELS = {
      new String[] {
        AudioParameter.VALUE_VM_CSD_500_WARNING, BluetoothPolicy.BluetoothUUID.OBEXOBJECTPUSH_UUID
      },
      new String[] {"19", BluetoothPolicy.BluetoothUUID.PBAP_UUID}
    };
    for (int i = 0; i < RESERVED_RFCOMM_CHANNELS.length; i++) {
      String port = RESERVED_RFCOMM_CHANNELS[i][0];
      String uuid = RESERVED_RFCOMM_CHANNELS[i][1];
      if (Integer.parseInt(port) == aPortNum && !service.isBluetoothUUIDAllowed(uuid)) {
        Log.m94d("BluetoothUtils", "MDM: Profile UUID = " + uuid + " Blocked");
        return true;
      }
    }
    return false;
  }

  public static boolean isHeadsetAllowedBySecurityPolicy(BluetoothDevice device) {
    if (device == null) {
      return true;
    }
    BluetoothPolicy service = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
    if (!service.isProfileEnabled(128)) {
      Log.m94d("BluetoothUtils", "MDM - SPP Profile is disabled");
      return false;
    }
    if (!service.isProfileEnabled(1)) {
      Log.m94d("BluetoothUtils", "MDM: HSP profile  is disabled");
      return false;
    }
    if (!service.isProfileEnabled(2)) {
      Log.m94d("BluetoothUtils", "MDM: HFP profile is disabled");
      return false;
    }
    if (service.isBluetoothDeviceAllowed(device.getAddress())) {
      return true;
    }
    Log.m94d("BluetoothUtils", "MDM: Remote Device Blocked");
    return false;
  }

  public static boolean isPairingAllowedbySecurityPolicy(String address) {
    BluetoothPolicy service = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
    if (!service.isPairingEnabled()) {
      Log.m94d("BluetoothUtils", "MDM: Pairing Blocked");
      return false;
    }
    if (!service.isBluetoothDeviceAllowed(address)) {
      Log.m94d("BluetoothUtils", "MDM: Remote Device Blocked");
      return false;
    }
    return true;
  }

  public static boolean isProfileAuthorizedBySecurityPolicy(ParcelUuid uuid) {
    return isProfileAuthorizedBySecurityPolicy(uuid, 1);
  }

  public static boolean isProfileAuthorizedBySecurityPolicy(ParcelUuid uuid, int portType) {
    long token;
    BluetoothPolicy service = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
    if (2 == portType && !service.isOutgoingCallsAllowed()) {
      Log.m94d("BluetoothUtils", "MDM: Outgoing Call is Disabled");
      return false;
    }
    if ((uuid.equals(BluetoothUuid.A2DP_SOURCE) || uuid.equals(BluetoothUuid.ADV_AUDIO_DIST))
        && (!service.isProfileEnabled(8) || !service.isProfileEnabled(128))) {
      Log.m94d("BluetoothUtils", "MDM: SPP or A2DP profile is disabled");
      return false;
    }
    if ((uuid.equals(BluetoothUuid.AVRCP_TARGET) || uuid.equals(BluetoothUuid.AVRCP_CONTROLLER))
        && (!service.isProfileEnabled(16) || !service.isProfileEnabled(128))) {
      Log.m94d("BluetoothUtils", "MDM: AVRCP profile is disabled");
      return false;
    }
    if (uuid.equals(BluetoothUuid.OBEX_OBJECT_PUSH)
        && (!service.getAllowBluetoothDataTransfer(true) || !service.isProfileEnabled(128))) {
      Log.m94d("BluetoothUtils", "MDM: OPP profile is disabled");
      token = Binder.clearCallingIdentity();
      try {
        AuditLog.logAsUser(
            5,
            5,
            false,
            Process.myPid(),
            "BluetoothUtils",
            AuditEvents.AUDIT_EXCHANGING_DATA_VIA_BLUETOOTH_FAILED_RESTRICTED,
            UserHandle.getUserId(Binder.getCallingUid()));
        return false;
      } finally {
      }
    }
    if (uuid.equals(BluetoothUuid.MAP) && !service.isProfileEnabled(128)) {
      Log.m94d("BluetoothUtils", "MDM: MAP profile is disabled");
      return false;
    }
    if (BluetoothUuid.SAP.equals(uuid)
        && (!service.isProfileEnabled(256) || !service.isProfileEnabled(128))) {
      Log.m94d("BluetoothUtils", "MDM: SAP profile is disabled");
      return false;
    }
    if (!service.isBluetoothUUIDAllowed(uuid.toString()) || !getBluetoothProfileEnabled(uuid)) {
      Log.m94d("BluetoothUtils", "MDM: profile UUID = " + uuid.toString() + " is disabled");
      return false;
    }
    if (uuid.equals(BluetoothUuid.OBEX_OBJECT_PUSH)) {
      token = Binder.clearCallingIdentity();
      try {
        AuditLog.logAsUser(
            5,
            5,
            true,
            Process.myPid(),
            "BluetoothUtils",
            AuditEvents.AUDIT_EXCHANGING_DATA_VIA_BLUETOOTH_SUCCEEDED,
            UserHandle.getUserId(Binder.getCallingUid()));
      } finally {
      }
    }
    return true;
  }

  public static void bluetoothLog(String tag, String msg) {
    try {
      BluetoothPolicy service = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
      service.bluetoothLog(tag, msg);
    } catch (Exception e) {
      Log.m96e("BluetoothUtils", "Exception on blutoothLog");
    }
  }

  public static void bluetoothSocketLog(
      String tag, BluetoothDevice device, int aPortNum, int aPortType) {
    if (device == null) {
      return;
    }
    try {
      switch (aPortType) {
        case 1:
          bluetoothLog("RFCOMM " + tag, device.getName(), device.getAddress());
          break;
        case 2:
          bluetoothLog("SCO " + tag, device.getName(), device.getAddress());
          break;
        case 3:
          bluetoothLog("L2CAP " + tag, device.getName(), device.getAddress());
          break;
        default:
          return;
      }
    } catch (Exception e) {
      Log.m96e("BluetoothUtils", "Exception on bluetoothLogSocket");
    }
  }

  public static void bluetoothLog(String tag, String remoteName, String remoteAddress) {
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    StringBuilder logMsg = new StringBuilder();
    if (adapter != null) {
      logMsg.append("Local Name: ");
      logMsg.append(adapter.getName());
      logMsg.append('\n');
      logMsg.append("Local Address: ");
      logMsg.append(adapter.getAddress());
      logMsg.append('\n');
    }
    if (remoteName != null && remoteName.length() > 0) {
      logMsg.append("Remote Name: ");
      logMsg.append(remoteName);
      logMsg.append('\n');
    }
    if (remoteAddress != null && remoteAddress.length() > 0) {
      logMsg.append("Remote Address: ");
      logMsg.append(remoteAddress);
      logMsg.append('\n');
    }
    bluetoothLog(tag, logMsg.toString());
  }

  public static boolean isBluetoothLogEnabled() {
    try {
      BluetoothPolicy service = EnterpriseDeviceManager.getInstance().getBluetoothPolicy();
      return service.isBluetoothLogEnabled();
    } catch (Exception e) {
      Log.m96e("BluetoothUtils", "Exception on isBluetoothLogEnabled");
      return false;
    }
  }

  public static void bluetoothLog(String tag, int profile, BluetoothDevice device) {
    String localName = "";
    String localAddress = "";
    String remoteName = "";
    String remoteAddress = "";
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    if (adapter != null) {
      localName = adapter.getName();
      localAddress = adapter.getAddress();
    }
    if (device != null) {
      remoteName = device.getName();
      remoteAddress = device.getAddress();
    }
    StringBuilder logMsg = new StringBuilder("");
    if (profile != -1) {
      logMsg.append(convertBluetoothProfile(profile));
    }
    if (remoteAddress != null && remoteAddress.length() > 0) {
      logMsg.append("Remote Address: ");
      logMsg.append(remoteAddress);
      logMsg.append('\n');
    }
    if (remoteAddress != null && remoteAddress.length() > 0) {
      logMsg.append("Remote Name: ");
      logMsg.append(remoteName);
      logMsg.append('\n');
    }
    if (localAddress != null && localAddress.length() > 0) {
      logMsg.append("Local Address: ");
      logMsg.append(localAddress);
      logMsg.append('\n');
    }
    if (localAddress != null && localAddress.length() > 0) {
      logMsg.append("Local Name: ");
      logMsg.append(localName);
      logMsg.append('\n');
    }
    bluetoothLog(tag, logMsg.toString());
  }

  public static void bluetoothLog(
      String tag,
      String remoteName,
      String remoteAddress,
      String profile,
      Uri filePath,
      String fileName) {
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    StringBuilder logMsg = new StringBuilder();
    if (profile.length() > 0) {
      logMsg.append("Profile: " + profile);
      logMsg.append('\n');
    }
    if (filePath != null && filePath.toString().length() > 0) {
      logMsg.append("URI: " + filePath);
      logMsg.append('\n');
    }
    if (fileName != null && fileName.length() > 0) {
      logMsg.append("Filename: " + fileName);
      logMsg.append('\n');
    }
    if (adapter != null) {
      logMsg.append("Local Name: ");
      logMsg.append(adapter.getName());
      logMsg.append('\n');
      logMsg.append("Local Address: ");
      logMsg.append(adapter.getAddress());
      logMsg.append('\n');
    }
    if (remoteName != null && remoteName.length() > 0) {
      logMsg.append("Remote Name: ");
      logMsg.append(remoteName);
      logMsg.append('\n');
    }
    if (remoteAddress != null && remoteAddress.length() > 0) {
      logMsg.append("Remote Address: ");
      logMsg.append(remoteAddress);
      logMsg.append('\n');
    }
    bluetoothLog(tag, logMsg.toString());
  }

  private static String convertBluetoothProfile(int profile) {
    switch (profile) {
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
      case 7:
      case 8:
      default:
        return "";
      case 9:
        return "Profile: MAP\n";
    }
  }
}
