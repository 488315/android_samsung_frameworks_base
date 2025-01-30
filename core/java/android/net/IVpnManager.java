package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.net.KnoxVpnProfile;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import java.util.List;

/* loaded from: classes2.dex */
public interface IVpnManager extends IInterface {
  public static final String DESCRIPTOR = "android.net.IVpnManager";

  boolean addVpnAddress(String str, int i) throws RemoteException;

  void applyBlockingRulesToUidRange(String str, int i, boolean z, String str2)
      throws RemoteException;

  boolean checkIfLocalProxyPortExists(int i) throws RemoteException;

  boolean checkIfUidIsExempted(int i) throws RemoteException;

  void createEnterpriseVpnInstance(String str, String str2, int i, int i2) throws RemoteException;

  void deleteVpnProfile(String str) throws RemoteException;

  boolean disconnectKnoxVpn(String str, int i) throws RemoteException;

  ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) throws RemoteException;

  void factoryReset() throws RemoteException;

  String getActiveDefaultInterface() throws RemoteException;

  Network getActiveDefaultNetwork() throws RemoteException;

  String getAlwaysOnVpnPackage(int i) throws RemoteException;

  List<String> getAppExclusionList(int i, String str) throws RemoteException;

  boolean getChainingEnabledForProfile(int i) throws RemoteException;

  String[] getDnsServerListForInterface(String str) throws RemoteException;

  int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException;

  int[] getKnoxVpnZtnaProxyInfoForUid(int i, String str) throws RemoteException;

  LegacyVpnInfo getLegacyKnoxVpnInfo(int i) throws RemoteException;

  LegacyVpnInfo getLegacyVpnInfo(int i) throws RemoteException;

  int getNetIdforActiveDefaultInterface() throws RemoteException;

  VpnProfileState getProvisionedVpnProfileState(String str) throws RemoteException;

  String[] getProxyInfoForUid(int i) throws RemoteException;

  VpnConfig getVpnConfig(int i) throws RemoteException;

  List<String> getVpnLockdownAllowlist(int i) throws RemoteException;

  boolean isAlwaysOnVpnPackageSupported(int i, String str) throws RemoteException;

  boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException;

  boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException;

  boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException;

  boolean isVpnConfigured(int i) throws RemoteException;

  boolean isVpnLockdownEnabled(int i) throws RemoteException;

  int knoxVpnProfileType(String str) throws RemoteException;

  boolean prepareEnterpriseVpnExt(String str, boolean z) throws RemoteException;

  boolean prepareVpn(String str, String str2, int i) throws RemoteException;

  boolean provisionVpnProfile(VpnProfile vpnProfile, String str) throws RemoteException;

  void registerSystemDefaultNetworkCallback() throws RemoteException;

  void removeEnterpriseVpnInstance(String str, String str2, int i) throws RemoteException;

  boolean removeVpnAddress(String str, int i) throws RemoteException;

  void resetUidListInNetworkCapabilities(String str, int i, String str2) throws RemoteException;

  boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List<String> list)
      throws RemoteException;

  boolean setAppExclusionList(int i, String str, List<String> list) throws RemoteException;

  void setNotificationDismissibleFlag(String str, int i, int i2) throws RemoteException;

  boolean setUnderlyingNetworksForVpn(Network[] networkArr) throws RemoteException;

  void setVpnPackageAuthorization(String str, int i, int i2) throws RemoteException;

  void startLegacyKnoxVpn(int i, KnoxVpnProfile knoxVpnProfile) throws RemoteException;

  void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException;

  String startVpnProfile(String str) throws RemoteException;

  void stopLegacyKnoxVpn(int i, String str, String str2) throws RemoteException;

  void stopVpnProfile(String str) throws RemoteException;

  void unregisterSystemDefaultNetworkCallback() throws RemoteException;

  void updateEnterpriseVpn(String str, int i, boolean z) throws RemoteException;

  void updateLocalProxyInfo(String str, int i, String str2, ProxyInfo proxyInfo)
      throws RemoteException;

  boolean updateLockdownVpn() throws RemoteException;

  void updateUidRangesToPerAppVpn(String str, int i, boolean z, int[] iArr, String str2)
      throws RemoteException;

  void updateUidRangesToUserVpn(String str, int i, boolean z, int i2, String str2)
      throws RemoteException;

  void updateUidRangesToUserVpnWithBlackList(String str, int i, int i2, int[] iArr, String str2)
      throws RemoteException;

  public static class Default implements IVpnManager {
    @Override // android.net.IVpnManager
    public boolean prepareVpn(String oldPackage, String newPackage, int userId)
        throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public void setVpnPackageAuthorization(String packageName, int userId, int vpnType)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public ParcelFileDescriptor establishVpn(VpnConfig config) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean addVpnAddress(String address, int prefixLength) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public boolean removeVpnAddress(String address, int prefixLength) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public boolean setUnderlyingNetworksForVpn(Network[] networks) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public boolean provisionVpnProfile(VpnProfile profile, String packageName)
        throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public void deleteVpnProfile(String packageName) throws RemoteException {}

    @Override // android.net.IVpnManager
    public String startVpnProfile(String packageName) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public void stopVpnProfile(String packageName) throws RemoteException {}

    @Override // android.net.IVpnManager
    public VpnProfileState getProvisionedVpnProfileState(String packageName)
        throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean setAppExclusionList(int userId, String vpnPackage, List<String> excludedApps)
        throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public List<String> getAppExclusionList(int userId, String vpnPackage) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean isAlwaysOnVpnPackageSupported(int userId, String packageName)
        throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public boolean setAlwaysOnVpnPackage(
        int userId, String packageName, boolean lockdown, List<String> lockdownAllowlist)
        throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public String getAlwaysOnVpnPackage(int userId) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean isVpnLockdownEnabled(int userId) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public List<String> getVpnLockdownAllowlist(int userId) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public void startLegacyVpn(VpnProfile profile) throws RemoteException {}

    @Override // android.net.IVpnManager
    public LegacyVpnInfo getLegacyVpnInfo(int userId) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean updateLockdownVpn() throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public VpnConfig getVpnConfig(int userId) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public void factoryReset() throws RemoteException {}

    @Override // android.net.IVpnManager
    public boolean getChainingEnabledForProfile(int callingUid) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public int knoxVpnProfileType(String profileName) throws RemoteException {
      return 0;
    }

    @Override // android.net.IVpnManager
    public int[] getKnoxVpnZtnaProxyInfoForUid(int uid, String packageName) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public String[] getProxyInfoForUid(int uid) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean checkIfLocalProxyPortExists(int port) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public boolean checkIfUidIsExempted(int uid) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public String[] getDnsServerListForInterface(String interfaceName) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public boolean isProxyConfiguredForKnoxVpn(int uid) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public void resetUidListInNetworkCapabilities(
        String profileName, int vpnClientLoc, String packageName) throws RemoteException {}

    @Override // android.net.IVpnManager
    public void updateLocalProxyInfo(
        String profileName, int vpnClientLoc, String packageName, ProxyInfo proxyInfo)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public void applyBlockingRulesToUidRange(
        String profileName, int vpnClientLoc, boolean block, String packageName)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public boolean prepareEnterpriseVpnExt(String profileName, boolean isMetaEnabled)
        throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public boolean disconnectKnoxVpn(String profileName, int callingUid) throws RemoteException {
      return false;
    }

    @Override // android.net.IVpnManager
    public void updateEnterpriseVpn(String profileName, int domain, boolean flag)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public void setNotificationDismissibleFlag(String profileName, int userId, int flag)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public void startLegacyKnoxVpn(int userId, KnoxVpnProfile profile) throws RemoteException {}

    @Override // android.net.IVpnManager
    public void stopLegacyKnoxVpn(int userId, String oldPackage, String newPackage)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public LegacyVpnInfo getLegacyKnoxVpnInfo(int userId) throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public void createEnterpriseVpnInstance(
        String packageName, String profileName, int vpnClientLoc, int chainingValue)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public void removeEnterpriseVpnInstance(
        String packageName, String profileName, int vpnClientLoc) throws RemoteException {}

    @Override // android.net.IVpnManager
    public void updateUidRangesToPerAppVpn(
        String profileName, int vpnClientLoc, boolean insert, int[] uidList, String packageName)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public void updateUidRangesToUserVpn(
        String profileName, int vpnClientLoc, boolean insert, int userId, String packageName)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public void updateUidRangesToUserVpnWithBlackList(
        String profileName, int vpnClientLoc, int userId, int[] uidList, String packageName)
        throws RemoteException {}

    @Override // android.net.IVpnManager
    public void registerSystemDefaultNetworkCallback() throws RemoteException {}

    @Override // android.net.IVpnManager
    public void unregisterSystemDefaultNetworkCallback() throws RemoteException {}

    @Override // android.net.IVpnManager
    public int getNetIdforActiveDefaultInterface() throws RemoteException {
      return 0;
    }

    @Override // android.net.IVpnManager
    public Network getActiveDefaultNetwork() throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public String getActiveDefaultInterface() throws RemoteException {
      return null;
    }

    @Override // android.net.IVpnManager
    public int getKnoxNwFilterHttpProxyPort(int userId, String packageName) throws RemoteException {
      return 0;
    }

    @Override // android.net.IVpnManager
    public boolean isVpnConfigured(int netId) throws RemoteException {
      return false;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IVpnManager {
    static final int TRANSACTION_addVpnAddress = 4;
    static final int TRANSACTION_applyBlockingRulesToUidRange = 36;
    static final int TRANSACTION_checkIfLocalProxyPortExists = 30;
    static final int TRANSACTION_checkIfUidIsExempted = 31;
    static final int TRANSACTION_createEnterpriseVpnInstance = 44;
    static final int TRANSACTION_deleteVpnProfile = 8;
    static final int TRANSACTION_disconnectKnoxVpn = 38;
    static final int TRANSACTION_establishVpn = 3;
    static final int TRANSACTION_factoryReset = 25;
    static final int TRANSACTION_getActiveDefaultInterface = 53;
    static final int TRANSACTION_getActiveDefaultNetwork = 52;
    static final int TRANSACTION_getAlwaysOnVpnPackage = 16;
    static final int TRANSACTION_getAppExclusionList = 13;
    static final int TRANSACTION_getChainingEnabledForProfile = 26;
    static final int TRANSACTION_getDnsServerListForInterface = 32;
    static final int TRANSACTION_getKnoxNwFilterHttpProxyPort = 54;
    static final int TRANSACTION_getKnoxVpnZtnaProxyInfoForUid = 28;
    static final int TRANSACTION_getLegacyKnoxVpnInfo = 43;
    static final int TRANSACTION_getLegacyVpnInfo = 22;
    static final int TRANSACTION_getNetIdforActiveDefaultInterface = 51;
    static final int TRANSACTION_getProvisionedVpnProfileState = 11;
    static final int TRANSACTION_getProxyInfoForUid = 29;
    static final int TRANSACTION_getVpnConfig = 24;
    static final int TRANSACTION_getVpnLockdownAllowlist = 18;
    static final int TRANSACTION_isAlwaysOnVpnPackageSupported = 14;
    static final int TRANSACTION_isCallerCurrentAlwaysOnVpnApp = 19;
    static final int TRANSACTION_isCallerCurrentAlwaysOnVpnLockdownApp = 20;
    static final int TRANSACTION_isProxyConfiguredForKnoxVpn = 33;
    static final int TRANSACTION_isVpnConfigured = 55;
    static final int TRANSACTION_isVpnLockdownEnabled = 17;
    static final int TRANSACTION_knoxVpnProfileType = 27;
    static final int TRANSACTION_prepareEnterpriseVpnExt = 37;
    static final int TRANSACTION_prepareVpn = 1;
    static final int TRANSACTION_provisionVpnProfile = 7;
    static final int TRANSACTION_registerSystemDefaultNetworkCallback = 49;
    static final int TRANSACTION_removeEnterpriseVpnInstance = 45;
    static final int TRANSACTION_removeVpnAddress = 5;
    static final int TRANSACTION_resetUidListInNetworkCapabilities = 34;
    static final int TRANSACTION_setAlwaysOnVpnPackage = 15;
    static final int TRANSACTION_setAppExclusionList = 12;
    static final int TRANSACTION_setNotificationDismissibleFlag = 40;
    static final int TRANSACTION_setUnderlyingNetworksForVpn = 6;
    static final int TRANSACTION_setVpnPackageAuthorization = 2;
    static final int TRANSACTION_startLegacyKnoxVpn = 41;
    static final int TRANSACTION_startLegacyVpn = 21;
    static final int TRANSACTION_startVpnProfile = 9;
    static final int TRANSACTION_stopLegacyKnoxVpn = 42;
    static final int TRANSACTION_stopVpnProfile = 10;
    static final int TRANSACTION_unregisterSystemDefaultNetworkCallback = 50;
    static final int TRANSACTION_updateEnterpriseVpn = 39;
    static final int TRANSACTION_updateLocalProxyInfo = 35;
    static final int TRANSACTION_updateLockdownVpn = 23;
    static final int TRANSACTION_updateUidRangesToPerAppVpn = 46;
    static final int TRANSACTION_updateUidRangesToUserVpn = 47;
    static final int TRANSACTION_updateUidRangesToUserVpnWithBlackList = 48;

    public Stub() {
      attachInterface(this, IVpnManager.DESCRIPTOR);
    }

    public static IVpnManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IVpnManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IVpnManager)) {
        return (IVpnManager) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "prepareVpn";
        case 2:
          return "setVpnPackageAuthorization";
        case 3:
          return "establishVpn";
        case 4:
          return "addVpnAddress";
        case 5:
          return "removeVpnAddress";
        case 6:
          return "setUnderlyingNetworksForVpn";
        case 7:
          return "provisionVpnProfile";
        case 8:
          return "deleteVpnProfile";
        case 9:
          return "startVpnProfile";
        case 10:
          return "stopVpnProfile";
        case 11:
          return "getProvisionedVpnProfileState";
        case 12:
          return "setAppExclusionList";
        case 13:
          return "getAppExclusionList";
        case 14:
          return "isAlwaysOnVpnPackageSupported";
        case 15:
          return "setAlwaysOnVpnPackage";
        case 16:
          return "getAlwaysOnVpnPackage";
        case 17:
          return "isVpnLockdownEnabled";
        case 18:
          return "getVpnLockdownAllowlist";
        case 19:
          return "isCallerCurrentAlwaysOnVpnApp";
        case 20:
          return "isCallerCurrentAlwaysOnVpnLockdownApp";
        case 21:
          return "startLegacyVpn";
        case 22:
          return "getLegacyVpnInfo";
        case 23:
          return "updateLockdownVpn";
        case 24:
          return "getVpnConfig";
        case 25:
          return "factoryReset";
        case 26:
          return "getChainingEnabledForProfile";
        case 27:
          return "knoxVpnProfileType";
        case 28:
          return "getKnoxVpnZtnaProxyInfoForUid";
        case 29:
          return "getProxyInfoForUid";
        case 30:
          return "checkIfLocalProxyPortExists";
        case 31:
          return "checkIfUidIsExempted";
        case 32:
          return "getDnsServerListForInterface";
        case 33:
          return "isProxyConfiguredForKnoxVpn";
        case 34:
          return "resetUidListInNetworkCapabilities";
        case 35:
          return "updateLocalProxyInfo";
        case 36:
          return "applyBlockingRulesToUidRange";
        case 37:
          return "prepareEnterpriseVpnExt";
        case 38:
          return "disconnectKnoxVpn";
        case 39:
          return "updateEnterpriseVpn";
        case 40:
          return "setNotificationDismissibleFlag";
        case 41:
          return "startLegacyKnoxVpn";
        case 42:
          return "stopLegacyKnoxVpn";
        case 43:
          return "getLegacyKnoxVpnInfo";
        case 44:
          return "createEnterpriseVpnInstance";
        case 45:
          return "removeEnterpriseVpnInstance";
        case 46:
          return "updateUidRangesToPerAppVpn";
        case 47:
          return "updateUidRangesToUserVpn";
        case 48:
          return "updateUidRangesToUserVpnWithBlackList";
        case 49:
          return "registerSystemDefaultNetworkCallback";
        case 50:
          return "unregisterSystemDefaultNetworkCallback";
        case 51:
          return "getNetIdforActiveDefaultInterface";
        case 52:
          return "getActiveDefaultNetwork";
        case 53:
          return "getActiveDefaultInterface";
        case 54:
          return "getKnoxNwFilterHttpProxyPort";
        case 55:
          return "isVpnConfigured";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IVpnManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IVpnManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              String _arg1 = data.readString();
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = prepareVpn(_arg0, _arg1, _arg2);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              String _arg02 = data.readString();
              int _arg12 = data.readInt();
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              setVpnPackageAuthorization(_arg02, _arg12, _arg22);
              reply.writeNoException();
              return true;
            case 3:
              VpnConfig _arg03 = (VpnConfig) data.readTypedObject(VpnConfig.CREATOR);
              data.enforceNoDataAvail();
              ParcelFileDescriptor _result2 = establishVpn(_arg03);
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            case 4:
              String _arg04 = data.readString();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = addVpnAddress(_arg04, _arg13);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 5:
              String _arg05 = data.readString();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result4 = removeVpnAddress(_arg05, _arg14);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 6:
              Network[] _arg06 = (Network[]) data.createTypedArray(Network.CREATOR);
              data.enforceNoDataAvail();
              boolean _result5 = setUnderlyingNetworksForVpn(_arg06);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 7:
              VpnProfile _arg07 = (VpnProfile) data.readTypedObject(VpnProfile.CREATOR);
              String _arg15 = data.readString();
              data.enforceNoDataAvail();
              boolean _result6 = provisionVpnProfile(_arg07, _arg15);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 8:
              String _arg08 = data.readString();
              data.enforceNoDataAvail();
              deleteVpnProfile(_arg08);
              reply.writeNoException();
              return true;
            case 9:
              String _arg09 = data.readString();
              data.enforceNoDataAvail();
              String _result7 = startVpnProfile(_arg09);
              reply.writeNoException();
              reply.writeString(_result7);
              return true;
            case 10:
              String _arg010 = data.readString();
              data.enforceNoDataAvail();
              stopVpnProfile(_arg010);
              reply.writeNoException();
              return true;
            case 11:
              String _arg011 = data.readString();
              data.enforceNoDataAvail();
              VpnProfileState _result8 = getProvisionedVpnProfileState(_arg011);
              reply.writeNoException();
              reply.writeTypedObject(_result8, 1);
              return true;
            case 12:
              int _arg012 = data.readInt();
              String _arg16 = data.readString();
              List<String> _arg23 = data.createStringArrayList();
              data.enforceNoDataAvail();
              boolean _result9 = setAppExclusionList(_arg012, _arg16, _arg23);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 13:
              int _arg013 = data.readInt();
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              List<String> _result10 = getAppExclusionList(_arg013, _arg17);
              reply.writeNoException();
              reply.writeStringList(_result10);
              return true;
            case 14:
              int _arg014 = data.readInt();
              String _arg18 = data.readString();
              data.enforceNoDataAvail();
              boolean _result11 = isAlwaysOnVpnPackageSupported(_arg014, _arg18);
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 15:
              int _arg015 = data.readInt();
              String _arg19 = data.readString();
              boolean _arg24 = data.readBoolean();
              List<String> _arg3 = data.createStringArrayList();
              data.enforceNoDataAvail();
              boolean _result12 = setAlwaysOnVpnPackage(_arg015, _arg19, _arg24, _arg3);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 16:
              int _arg016 = data.readInt();
              data.enforceNoDataAvail();
              String _result13 = getAlwaysOnVpnPackage(_arg016);
              reply.writeNoException();
              reply.writeString(_result13);
              return true;
            case 17:
              int _arg017 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result14 = isVpnLockdownEnabled(_arg017);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 18:
              int _arg018 = data.readInt();
              data.enforceNoDataAvail();
              List<String> _result15 = getVpnLockdownAllowlist(_arg018);
              reply.writeNoException();
              reply.writeStringList(_result15);
              return true;
            case 19:
              boolean _result16 = isCallerCurrentAlwaysOnVpnApp();
              reply.writeNoException();
              reply.writeBoolean(_result16);
              return true;
            case 20:
              boolean _result17 = isCallerCurrentAlwaysOnVpnLockdownApp();
              reply.writeNoException();
              reply.writeBoolean(_result17);
              return true;
            case 21:
              VpnProfile _arg019 = (VpnProfile) data.readTypedObject(VpnProfile.CREATOR);
              data.enforceNoDataAvail();
              startLegacyVpn(_arg019);
              reply.writeNoException();
              return true;
            case 22:
              int _arg020 = data.readInt();
              data.enforceNoDataAvail();
              LegacyVpnInfo _result18 = getLegacyVpnInfo(_arg020);
              reply.writeNoException();
              reply.writeTypedObject(_result18, 1);
              return true;
            case 23:
              boolean _result19 = updateLockdownVpn();
              reply.writeNoException();
              reply.writeBoolean(_result19);
              return true;
            case 24:
              int _arg021 = data.readInt();
              data.enforceNoDataAvail();
              VpnConfig _result20 = getVpnConfig(_arg021);
              reply.writeNoException();
              reply.writeTypedObject(_result20, 1);
              return true;
            case 25:
              factoryReset();
              reply.writeNoException();
              return true;
            case 26:
              int _arg022 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result21 = getChainingEnabledForProfile(_arg022);
              reply.writeNoException();
              reply.writeBoolean(_result21);
              return true;
            case 27:
              String _arg023 = data.readString();
              data.enforceNoDataAvail();
              int _result22 = knoxVpnProfileType(_arg023);
              reply.writeNoException();
              reply.writeInt(_result22);
              return true;
            case 28:
              int _arg024 = data.readInt();
              String _arg110 = data.readString();
              data.enforceNoDataAvail();
              int[] _result23 = getKnoxVpnZtnaProxyInfoForUid(_arg024, _arg110);
              reply.writeNoException();
              reply.writeIntArray(_result23);
              return true;
            case 29:
              int _arg025 = data.readInt();
              data.enforceNoDataAvail();
              String[] _result24 = getProxyInfoForUid(_arg025);
              reply.writeNoException();
              reply.writeStringArray(_result24);
              return true;
            case 30:
              int _arg026 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result25 = checkIfLocalProxyPortExists(_arg026);
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 31:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result26 = checkIfUidIsExempted(_arg027);
              reply.writeNoException();
              reply.writeBoolean(_result26);
              return true;
            case 32:
              String _arg028 = data.readString();
              data.enforceNoDataAvail();
              String[] _result27 = getDnsServerListForInterface(_arg028);
              reply.writeNoException();
              reply.writeStringArray(_result27);
              return true;
            case 33:
              int _arg029 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result28 = isProxyConfiguredForKnoxVpn(_arg029);
              reply.writeNoException();
              reply.writeBoolean(_result28);
              return true;
            case 34:
              String _arg030 = data.readString();
              int _arg111 = data.readInt();
              String _arg25 = data.readString();
              data.enforceNoDataAvail();
              resetUidListInNetworkCapabilities(_arg030, _arg111, _arg25);
              reply.writeNoException();
              return true;
            case 35:
              String _arg031 = data.readString();
              int _arg112 = data.readInt();
              String _arg26 = data.readString();
              ProxyInfo _arg32 = (ProxyInfo) data.readTypedObject(ProxyInfo.CREATOR);
              data.enforceNoDataAvail();
              updateLocalProxyInfo(_arg031, _arg112, _arg26, _arg32);
              reply.writeNoException();
              return true;
            case 36:
              String _arg032 = data.readString();
              int _arg113 = data.readInt();
              boolean _arg27 = data.readBoolean();
              String _arg33 = data.readString();
              data.enforceNoDataAvail();
              applyBlockingRulesToUidRange(_arg032, _arg113, _arg27, _arg33);
              reply.writeNoException();
              return true;
            case 37:
              String _arg033 = data.readString();
              boolean _arg114 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result29 = prepareEnterpriseVpnExt(_arg033, _arg114);
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 38:
              String _arg034 = data.readString();
              int _arg115 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result30 = disconnectKnoxVpn(_arg034, _arg115);
              reply.writeNoException();
              reply.writeBoolean(_result30);
              return true;
            case 39:
              String _arg035 = data.readString();
              int _arg116 = data.readInt();
              boolean _arg28 = data.readBoolean();
              data.enforceNoDataAvail();
              updateEnterpriseVpn(_arg035, _arg116, _arg28);
              reply.writeNoException();
              return true;
            case 40:
              String _arg036 = data.readString();
              int _arg117 = data.readInt();
              int _arg29 = data.readInt();
              data.enforceNoDataAvail();
              setNotificationDismissibleFlag(_arg036, _arg117, _arg29);
              reply.writeNoException();
              return true;
            case 41:
              int _arg037 = data.readInt();
              KnoxVpnProfile _arg118 =
                  (KnoxVpnProfile) data.readTypedObject(KnoxVpnProfile.CREATOR);
              data.enforceNoDataAvail();
              startLegacyKnoxVpn(_arg037, _arg118);
              reply.writeNoException();
              return true;
            case 42:
              int _arg038 = data.readInt();
              String _arg119 = data.readString();
              String _arg210 = data.readString();
              data.enforceNoDataAvail();
              stopLegacyKnoxVpn(_arg038, _arg119, _arg210);
              reply.writeNoException();
              return true;
            case 43:
              int _arg039 = data.readInt();
              data.enforceNoDataAvail();
              LegacyVpnInfo _result31 = getLegacyKnoxVpnInfo(_arg039);
              reply.writeNoException();
              reply.writeTypedObject(_result31, 1);
              return true;
            case 44:
              String _arg040 = data.readString();
              String _arg120 = data.readString();
              int _arg211 = data.readInt();
              int _arg34 = data.readInt();
              data.enforceNoDataAvail();
              createEnterpriseVpnInstance(_arg040, _arg120, _arg211, _arg34);
              reply.writeNoException();
              return true;
            case 45:
              String _arg041 = data.readString();
              String _arg121 = data.readString();
              int _arg212 = data.readInt();
              data.enforceNoDataAvail();
              removeEnterpriseVpnInstance(_arg041, _arg121, _arg212);
              reply.writeNoException();
              return true;
            case 46:
              String _arg042 = data.readString();
              int _arg122 = data.readInt();
              boolean _arg213 = data.readBoolean();
              int[] _arg35 = data.createIntArray();
              String _arg4 = data.readString();
              data.enforceNoDataAvail();
              updateUidRangesToPerAppVpn(_arg042, _arg122, _arg213, _arg35, _arg4);
              reply.writeNoException();
              return true;
            case 47:
              String _arg043 = data.readString();
              int _arg123 = data.readInt();
              boolean _arg214 = data.readBoolean();
              int _arg36 = data.readInt();
              String _arg42 = data.readString();
              data.enforceNoDataAvail();
              updateUidRangesToUserVpn(_arg043, _arg123, _arg214, _arg36, _arg42);
              reply.writeNoException();
              return true;
            case 48:
              String _arg044 = data.readString();
              int _arg124 = data.readInt();
              int _arg215 = data.readInt();
              int[] _arg37 = data.createIntArray();
              String _arg43 = data.readString();
              data.enforceNoDataAvail();
              updateUidRangesToUserVpnWithBlackList(_arg044, _arg124, _arg215, _arg37, _arg43);
              reply.writeNoException();
              return true;
            case 49:
              registerSystemDefaultNetworkCallback();
              reply.writeNoException();
              return true;
            case 50:
              unregisterSystemDefaultNetworkCallback();
              reply.writeNoException();
              return true;
            case 51:
              int _result32 = getNetIdforActiveDefaultInterface();
              reply.writeNoException();
              reply.writeInt(_result32);
              return true;
            case 52:
              Network _result33 = getActiveDefaultNetwork();
              reply.writeNoException();
              reply.writeTypedObject(_result33, 1);
              return true;
            case 53:
              String _result34 = getActiveDefaultInterface();
              reply.writeNoException();
              reply.writeString(_result34);
              return true;
            case 54:
              int _arg045 = data.readInt();
              String _arg125 = data.readString();
              data.enforceNoDataAvail();
              int _result35 = getKnoxNwFilterHttpProxyPort(_arg045, _arg125);
              reply.writeNoException();
              reply.writeInt(_result35);
              return true;
            case 55:
              int _arg046 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result36 = isVpnConfigured(_arg046);
              reply.writeNoException();
              reply.writeBoolean(_result36);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IVpnManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IVpnManager.DESCRIPTOR;
      }

      @Override // android.net.IVpnManager
      public boolean prepareVpn(String oldPackage, String newPackage, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(oldPackage);
          _data.writeString(newPackage);
          _data.writeInt(userId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void setVpnPackageAuthorization(String packageName, int userId, int vpnType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          _data.writeInt(vpnType);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public ParcelFileDescriptor establishVpn(VpnConfig config) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeTypedObject(config, 0);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          ParcelFileDescriptor _result =
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean addVpnAddress(String address, int prefixLength) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(address);
          _data.writeInt(prefixLength);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean removeVpnAddress(String address, int prefixLength) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(address);
          _data.writeInt(prefixLength);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean setUnderlyingNetworksForVpn(Network[] networks) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeTypedArray(networks, 0);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean provisionVpnProfile(VpnProfile profile, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeTypedObject(profile, 0);
          _data.writeString(packageName);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void deleteVpnProfile(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public String startVpnProfile(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void stopVpnProfile(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public VpnProfileState getProvisionedVpnProfileState(String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          VpnProfileState _result =
              (VpnProfileState) _reply.readTypedObject(VpnProfileState.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean setAppExclusionList(int userId, String vpnPackage, List<String> excludedApps)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(vpnPackage);
          _data.writeStringList(excludedApps);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public List<String> getAppExclusionList(int userId, String vpnPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(vpnPackage);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean isAlwaysOnVpnPackageSupported(int userId, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean setAlwaysOnVpnPackage(
          int userId, String packageName, boolean lockdown, List<String> lockdownAllowlist)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          _data.writeBoolean(lockdown);
          _data.writeStringList(lockdownAllowlist);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public String getAlwaysOnVpnPackage(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean isVpnLockdownEnabled(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public List<String> getVpnLockdownAllowlist(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void startLegacyVpn(VpnProfile profile) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeTypedObject(profile, 0);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public LegacyVpnInfo getLegacyVpnInfo(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          LegacyVpnInfo _result = (LegacyVpnInfo) _reply.readTypedObject(LegacyVpnInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean updateLockdownVpn() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public VpnConfig getVpnConfig(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          VpnConfig _result = (VpnConfig) _reply.readTypedObject(VpnConfig.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void factoryReset() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean getChainingEnabledForProfile(int callingUid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(callingUid);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public int knoxVpnProfileType(String profileName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public int[] getKnoxVpnZtnaProxyInfoForUid(int uid, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeString(packageName);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public String[] getProxyInfoForUid(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean checkIfLocalProxyPortExists(int port) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(port);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean checkIfUidIsExempted(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public String[] getDnsServerListForInterface(String interfaceName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(interfaceName);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean isProxyConfiguredForKnoxVpn(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void resetUidListInNetworkCapabilities(
          String profileName, int vpnClientLoc, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          _data.writeString(packageName);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void updateLocalProxyInfo(
          String profileName, int vpnClientLoc, String packageName, ProxyInfo proxyInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          _data.writeString(packageName);
          _data.writeTypedObject(proxyInfo, 0);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void applyBlockingRulesToUidRange(
          String profileName, int vpnClientLoc, boolean block, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          _data.writeBoolean(block);
          _data.writeString(packageName);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean prepareEnterpriseVpnExt(String profileName, boolean isMetaEnabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeBoolean(isMetaEnabled);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean disconnectKnoxVpn(String profileName, int callingUid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(callingUid);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void updateEnterpriseVpn(String profileName, int domain, boolean flag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(domain);
          _data.writeBoolean(flag);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void setNotificationDismissibleFlag(String profileName, int userId, int flag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(userId);
          _data.writeInt(flag);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void startLegacyKnoxVpn(int userId, KnoxVpnProfile profile) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeTypedObject(profile, 0);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void stopLegacyKnoxVpn(int userId, String oldPackage, String newPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(oldPackage);
          _data.writeString(newPackage);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public LegacyVpnInfo getLegacyKnoxVpnInfo(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          LegacyVpnInfo _result = (LegacyVpnInfo) _reply.readTypedObject(LegacyVpnInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void createEnterpriseVpnInstance(
          String packageName, String profileName, int vpnClientLoc, int chainingValue)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          _data.writeInt(chainingValue);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void removeEnterpriseVpnInstance(
          String packageName, String profileName, int vpnClientLoc) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void updateUidRangesToPerAppVpn(
          String profileName, int vpnClientLoc, boolean insert, int[] uidList, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          _data.writeBoolean(insert);
          _data.writeIntArray(uidList);
          _data.writeString(packageName);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void updateUidRangesToUserVpn(
          String profileName, int vpnClientLoc, boolean insert, int userId, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          _data.writeBoolean(insert);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void updateUidRangesToUserVpnWithBlackList(
          String profileName, int vpnClientLoc, int userId, int[] uidList, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeString(profileName);
          _data.writeInt(vpnClientLoc);
          _data.writeInt(userId);
          _data.writeIntArray(uidList);
          _data.writeString(packageName);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void registerSystemDefaultNetworkCallback() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public void unregisterSystemDefaultNetworkCallback() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public int getNetIdforActiveDefaultInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public Network getActiveDefaultNetwork() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
          Network _result = (Network) _reply.readTypedObject(Network.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public String getActiveDefaultInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public int getKnoxNwFilterHttpProxyPort(int userId, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.IVpnManager
      public boolean isVpnConfigured(int netId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVpnManager.DESCRIPTOR);
          _data.writeInt(netId);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 54;
    }
  }
}
