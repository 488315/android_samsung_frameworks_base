package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IVpnManager extends IInterface {
    public static final String DESCRIPTOR = "android.net.IVpnManager";

    public static class Default implements IVpnManager {
        @Override // android.net.IVpnManager
        public boolean addVpnAddress(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void applyBlockingRulesToUidRange(String str, int i, boolean z, String str2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean checkIfLocalProxyPortExists(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean checkIfUidIsExempted(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void createEnterpriseVpnInstance(String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void deleteVpnProfile(String str) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public boolean disconnectKnoxVpn(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public void factoryReset() throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public String getActiveDefaultInterface() throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public Network getActiveDefaultNetwork() throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public String getAlwaysOnVpnPackage(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public List<String> getAppExclusionList(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean getChainingEnabledForProfile(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public String[] getDnsServerListForInterface(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public byte[] getFromVpnProfileStore(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.net.IVpnManager
        public int[] getKnoxVpnZtnaProxyInfoForUid(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public LegacyVpnInfo getLegacyVpnInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public List<String> getLockdownVpnAllowList(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public int getNetIdforActiveDefaultInterface() throws RemoteException {
            return 0;
        }

        @Override // android.net.IVpnManager
        public VpnProfileState getProvisionedVpnProfileState(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public String[] getProxyInfoForUid(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public int getVpnClientUid(int i) throws RemoteException {
            return 0;
        }

        @Override // android.net.IVpnManager
        public VpnConfig getVpnConfig(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public VpnConfig getVpnConfigForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public List<String> getVpnLockdownAllowlist(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public int getVpnNetId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.net.IVpnManager
        public boolean isAlwaysOnVpnPackageSupported(int i, String str) throws RemoteException {
            return false;
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
        public boolean isDoEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean isLockdownVpnEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean isVpnConfigured(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean isVpnLockdownEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public int knoxVpnProfileType(String str) throws RemoteException {
            return 0;
        }

        @Override // android.net.IVpnManager
        public String[] listFromVpnProfileStore(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean prepareEnterpriseVpnExt(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean prepareVpn(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean provisionVpnProfile(VpnProfile vpnProfile, String str) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean putIntoVpnProfileStore(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void registerSystemDefaultNetworkCallback() throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void removeEnterpriseVpnInstance(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public boolean removeFromVpnProfileStore(String str) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean removeVpnAddress(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void resetUidListInNetworkCapabilities(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean setAppExclusionList(int i, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean setUnderlyingNetworksForVpn(Network[] networkArr) throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void setVpnPackageAuthorization(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public String startVpnProfile(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public void stopVpnProfile(String str) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void unregisterSystemDefaultNetworkCallback() throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void updateEnterpriseVpn(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void updateLocalProxyInfo(String str, int i, String str2, ProxyInfo proxyInfo) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public boolean updateLockdownVpn() throws RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void updateNotificationIcon(int i) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void updateUidRangesToPerAppVpn(String str, int i, boolean z, int[] iArr, String str2) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void updateUidRangesToUserVpn(String str, int i, boolean z, int i2, String str2) throws RemoteException {
        }

        @Override // android.net.IVpnManager
        public void updateUidRangesToUserVpnWithBlackList(String str, int i, int i2, int[] iArr, String str2) throws RemoteException {
        }
    }

    boolean addVpnAddress(String str, int i) throws RemoteException;

    void applyBlockingRulesToUidRange(String str, int i, boolean z, String str2) throws RemoteException;

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

    byte[] getFromVpnProfileStore(String str) throws RemoteException;

    int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException;

    int[] getKnoxVpnZtnaProxyInfoForUid(int i, String str) throws RemoteException;

    LegacyVpnInfo getLegacyVpnInfo(int i) throws RemoteException;

    List<String> getLockdownVpnAllowList(int i) throws RemoteException;

    int getNetIdforActiveDefaultInterface() throws RemoteException;

    VpnProfileState getProvisionedVpnProfileState(String str) throws RemoteException;

    String[] getProxyInfoForUid(int i) throws RemoteException;

    int getVpnClientUid(int i) throws RemoteException;

    VpnConfig getVpnConfig(int i) throws RemoteException;

    VpnConfig getVpnConfigForUser(int i) throws RemoteException;

    List<String> getVpnLockdownAllowlist(int i) throws RemoteException;

    int getVpnNetId(int i) throws RemoteException;

    boolean isAlwaysOnVpnPackageSupported(int i, String str) throws RemoteException;

    boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException;

    boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException;

    boolean isDoEnabled(int i) throws RemoteException;

    boolean isLockdownVpnEnabled(int i) throws RemoteException;

    boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException;

    boolean isVpnConfigured(int i) throws RemoteException;

    boolean isVpnLockdownEnabled(int i) throws RemoteException;

    int knoxVpnProfileType(String str) throws RemoteException;

    String[] listFromVpnProfileStore(String str) throws RemoteException;

    boolean prepareEnterpriseVpnExt(String str, boolean z) throws RemoteException;

    boolean prepareVpn(String str, String str2, int i) throws RemoteException;

    boolean provisionVpnProfile(VpnProfile vpnProfile, String str) throws RemoteException;

    boolean putIntoVpnProfileStore(String str, byte[] bArr) throws RemoteException;

    void registerSystemDefaultNetworkCallback() throws RemoteException;

    void removeEnterpriseVpnInstance(String str, String str2, int i) throws RemoteException;

    boolean removeFromVpnProfileStore(String str) throws RemoteException;

    boolean removeVpnAddress(String str, int i) throws RemoteException;

    void resetUidListInNetworkCapabilities(String str, int i, String str2) throws RemoteException;

    boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List<String> list) throws RemoteException;

    boolean setAppExclusionList(int i, String str, List<String> list) throws RemoteException;

    boolean setUnderlyingNetworksForVpn(Network[] networkArr) throws RemoteException;

    void setVpnPackageAuthorization(String str, int i, int i2) throws RemoteException;

    void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException;

    String startVpnProfile(String str) throws RemoteException;

    void stopVpnProfile(String str) throws RemoteException;

    void unregisterSystemDefaultNetworkCallback() throws RemoteException;

    void updateEnterpriseVpn(String str, int i, boolean z) throws RemoteException;

    void updateLocalProxyInfo(String str, int i, String str2, ProxyInfo proxyInfo) throws RemoteException;

    boolean updateLockdownVpn() throws RemoteException;

    void updateNotificationIcon(int i) throws RemoteException;

    void updateUidRangesToPerAppVpn(String str, int i, boolean z, int[] iArr, String str2) throws RemoteException;

    void updateUidRangesToUserVpn(String str, int i, boolean z, int i2, String str2) throws RemoteException;

    void updateUidRangesToUserVpnWithBlackList(String str, int i, int i2, int[] iArr, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IVpnManager {
        static final int TRANSACTION_addVpnAddress = 4;
        static final int TRANSACTION_applyBlockingRulesToUidRange = 40;
        static final int TRANSACTION_checkIfLocalProxyPortExists = 34;
        static final int TRANSACTION_checkIfUidIsExempted = 35;
        static final int TRANSACTION_createEnterpriseVpnInstance = 45;
        static final int TRANSACTION_deleteVpnProfile = 8;
        static final int TRANSACTION_disconnectKnoxVpn = 42;
        static final int TRANSACTION_establishVpn = 3;
        static final int TRANSACTION_factoryReset = 29;
        static final int TRANSACTION_getActiveDefaultInterface = 54;
        static final int TRANSACTION_getActiveDefaultNetwork = 53;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 16;
        static final int TRANSACTION_getAppExclusionList = 13;
        static final int TRANSACTION_getChainingEnabledForProfile = 30;
        static final int TRANSACTION_getDnsServerListForInterface = 36;
        static final int TRANSACTION_getFromVpnProfileStore = 24;
        static final int TRANSACTION_getKnoxNwFilterHttpProxyPort = 55;
        static final int TRANSACTION_getKnoxVpnZtnaProxyInfoForUid = 32;
        static final int TRANSACTION_getLegacyVpnInfo = 22;
        static final int TRANSACTION_getLockdownVpnAllowList = 61;
        static final int TRANSACTION_getNetIdforActiveDefaultInterface = 52;
        static final int TRANSACTION_getProvisionedVpnProfileState = 11;
        static final int TRANSACTION_getProxyInfoForUid = 33;
        static final int TRANSACTION_getVpnClientUid = 59;
        static final int TRANSACTION_getVpnConfig = 28;
        static final int TRANSACTION_getVpnConfigForUser = 62;
        static final int TRANSACTION_getVpnLockdownAllowlist = 18;
        static final int TRANSACTION_getVpnNetId = 58;
        static final int TRANSACTION_isAlwaysOnVpnPackageSupported = 14;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnApp = 19;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnLockdownApp = 20;
        static final int TRANSACTION_isDoEnabled = 57;
        static final int TRANSACTION_isLockdownVpnEnabled = 60;
        static final int TRANSACTION_isProxyConfiguredForKnoxVpn = 37;
        static final int TRANSACTION_isVpnConfigured = 56;
        static final int TRANSACTION_isVpnLockdownEnabled = 17;
        static final int TRANSACTION_knoxVpnProfileType = 31;
        static final int TRANSACTION_listFromVpnProfileStore = 27;
        static final int TRANSACTION_prepareEnterpriseVpnExt = 41;
        static final int TRANSACTION_prepareVpn = 1;
        static final int TRANSACTION_provisionVpnProfile = 7;
        static final int TRANSACTION_putIntoVpnProfileStore = 25;
        static final int TRANSACTION_registerSystemDefaultNetworkCallback = 50;
        static final int TRANSACTION_removeEnterpriseVpnInstance = 46;
        static final int TRANSACTION_removeFromVpnProfileStore = 26;
        static final int TRANSACTION_removeVpnAddress = 5;
        static final int TRANSACTION_resetUidListInNetworkCapabilities = 38;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 15;
        static final int TRANSACTION_setAppExclusionList = 12;
        static final int TRANSACTION_setUnderlyingNetworksForVpn = 6;
        static final int TRANSACTION_setVpnPackageAuthorization = 2;
        static final int TRANSACTION_startLegacyVpn = 21;
        static final int TRANSACTION_startVpnProfile = 9;
        static final int TRANSACTION_stopVpnProfile = 10;
        static final int TRANSACTION_unregisterSystemDefaultNetworkCallback = 51;
        static final int TRANSACTION_updateEnterpriseVpn = 43;
        static final int TRANSACTION_updateLocalProxyInfo = 39;
        static final int TRANSACTION_updateLockdownVpn = 23;
        static final int TRANSACTION_updateNotificationIcon = 44;
        static final int TRANSACTION_updateUidRangesToPerAppVpn = 47;
        static final int TRANSACTION_updateUidRangesToUserVpn = 48;
        static final int TRANSACTION_updateUidRangesToUserVpnWithBlackList = 49;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 61;
        }

        public Stub() {
            attachInterface(this, IVpnManager.DESCRIPTOR);
        }

        public static IVpnManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVpnManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVpnManager)) {
                return (IVpnManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "getFromVpnProfileStore";
                case 25:
                    return "putIntoVpnProfileStore";
                case 26:
                    return "removeFromVpnProfileStore";
                case 27:
                    return "listFromVpnProfileStore";
                case 28:
                    return "getVpnConfig";
                case 29:
                    return "factoryReset";
                case 30:
                    return "getChainingEnabledForProfile";
                case 31:
                    return "knoxVpnProfileType";
                case 32:
                    return "getKnoxVpnZtnaProxyInfoForUid";
                case 33:
                    return "getProxyInfoForUid";
                case 34:
                    return "checkIfLocalProxyPortExists";
                case 35:
                    return "checkIfUidIsExempted";
                case 36:
                    return "getDnsServerListForInterface";
                case 37:
                    return "isProxyConfiguredForKnoxVpn";
                case 38:
                    return "resetUidListInNetworkCapabilities";
                case 39:
                    return "updateLocalProxyInfo";
                case 40:
                    return "applyBlockingRulesToUidRange";
                case 41:
                    return "prepareEnterpriseVpnExt";
                case 42:
                    return "disconnectKnoxVpn";
                case 43:
                    return "updateEnterpriseVpn";
                case 44:
                    return "updateNotificationIcon";
                case 45:
                    return "createEnterpriseVpnInstance";
                case 46:
                    return "removeEnterpriseVpnInstance";
                case 47:
                    return "updateUidRangesToPerAppVpn";
                case 48:
                    return "updateUidRangesToUserVpn";
                case 49:
                    return "updateUidRangesToUserVpnWithBlackList";
                case 50:
                    return "registerSystemDefaultNetworkCallback";
                case 51:
                    return "unregisterSystemDefaultNetworkCallback";
                case 52:
                    return "getNetIdforActiveDefaultInterface";
                case 53:
                    return "getActiveDefaultNetwork";
                case 54:
                    return "getActiveDefaultInterface";
                case 55:
                    return "getKnoxNwFilterHttpProxyPort";
                case 56:
                    return "isVpnConfigured";
                case 57:
                    return "isDoEnabled";
                case 58:
                    return "getVpnNetId";
                case 59:
                    return "getVpnClientUid";
                case 60:
                    return "isLockdownVpnEnabled";
                case 61:
                    return "getLockdownVpnAllowList";
                case 62:
                    return "getVpnConfigForUser";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVpnManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVpnManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean prepareVpn = prepareVpn(readString, readString2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(prepareVpn);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVpnPackageAuthorization(readString3, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    VpnConfig vpnConfig = (VpnConfig) parcel.readTypedObject(VpnConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor establishVpn = establishVpn(vpnConfig);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(establishVpn, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addVpnAddress = addVpnAddress(readString4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addVpnAddress);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeVpnAddress = removeVpnAddress(readString5, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeVpnAddress);
                    return true;
                case 6:
                    Network[] networkArr = (Network[]) parcel.createTypedArray(Network.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean underlyingNetworksForVpn = setUnderlyingNetworksForVpn(networkArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(underlyingNetworksForVpn);
                    return true;
                case 7:
                    VpnProfile vpnProfile = (VpnProfile) parcel.readTypedObject(VpnProfile.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean provisionVpnProfile = provisionVpnProfile(vpnProfile, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(provisionVpnProfile);
                    return true;
                case 8:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteVpnProfile(readString7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String startVpnProfile = startVpnProfile(readString8);
                    parcel2.writeNoException();
                    parcel2.writeString(startVpnProfile);
                    return true;
                case 10:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopVpnProfile(readString9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    VpnProfileState provisionedVpnProfileState = getProvisionedVpnProfileState(readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(provisionedVpnProfileState, 1);
                    return true;
                case 12:
                    int readInt6 = parcel.readInt();
                    String readString11 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean appExclusionList = setAppExclusionList(readInt6, readString11, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appExclusionList);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> appExclusionList2 = getAppExclusionList(readInt7, readString12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(appExclusionList2);
                    return true;
                case 14:
                    int readInt8 = parcel.readInt();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAlwaysOnVpnPackageSupported = isAlwaysOnVpnPackageSupported(readInt8, readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlwaysOnVpnPackageSupported);
                    return true;
                case 15:
                    int readInt9 = parcel.readInt();
                    String readString14 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnVpnPackage = setAlwaysOnVpnPackage(readInt9, readString14, readBoolean, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysOnVpnPackage);
                    return true;
                case 16:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String alwaysOnVpnPackage2 = getAlwaysOnVpnPackage(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackage2);
                    return true;
                case 17:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVpnLockdownEnabled = isVpnLockdownEnabled(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVpnLockdownEnabled);
                    return true;
                case 18:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> vpnLockdownAllowlist = getVpnLockdownAllowlist(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(vpnLockdownAllowlist);
                    return true;
                case 19:
                    boolean isCallerCurrentAlwaysOnVpnApp = isCallerCurrentAlwaysOnVpnApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerCurrentAlwaysOnVpnApp);
                    return true;
                case 20:
                    boolean isCallerCurrentAlwaysOnVpnLockdownApp = isCallerCurrentAlwaysOnVpnLockdownApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerCurrentAlwaysOnVpnLockdownApp);
                    return true;
                case 21:
                    VpnProfile vpnProfile2 = (VpnProfile) parcel.readTypedObject(VpnProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    startLegacyVpn(vpnProfile2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LegacyVpnInfo legacyVpnInfo = getLegacyVpnInfo(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(legacyVpnInfo, 1);
                    return true;
                case 23:
                    boolean updateLockdownVpn = updateLockdownVpn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateLockdownVpn);
                    return true;
                case 24:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] fromVpnProfileStore = getFromVpnProfileStore(readString15);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(fromVpnProfileStore);
                    return true;
                case 25:
                    String readString16 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean putIntoVpnProfileStore = putIntoVpnProfileStore(readString16, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(putIntoVpnProfileStore);
                    return true;
                case 26:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeFromVpnProfileStore = removeFromVpnProfileStore(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeFromVpnProfileStore);
                    return true;
                case 27:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] listFromVpnProfileStore = listFromVpnProfileStore(readString18);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listFromVpnProfileStore);
                    return true;
                case 28:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VpnConfig vpnConfig2 = getVpnConfig(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnConfig2, 1);
                    return true;
                case 29:
                    factoryReset();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean chainingEnabledForProfile = getChainingEnabledForProfile(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(chainingEnabledForProfile);
                    return true;
                case 31:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int knoxVpnProfileType = knoxVpnProfileType(readString19);
                    parcel2.writeNoException();
                    parcel2.writeInt(knoxVpnProfileType);
                    return true;
                case 32:
                    int readInt16 = parcel.readInt();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] knoxVpnZtnaProxyInfoForUid = getKnoxVpnZtnaProxyInfoForUid(readInt16, readString20);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(knoxVpnZtnaProxyInfoForUid);
                    return true;
                case 33:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] proxyInfoForUid = getProxyInfoForUid(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(proxyInfoForUid);
                    return true;
                case 34:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkIfLocalProxyPortExists = checkIfLocalProxyPortExists(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkIfLocalProxyPortExists);
                    return true;
                case 35:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkIfUidIsExempted = checkIfUidIsExempted(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkIfUidIsExempted);
                    return true;
                case 36:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] dnsServerListForInterface = getDnsServerListForInterface(readString21);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(dnsServerListForInterface);
                    return true;
                case 37:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isProxyConfiguredForKnoxVpn = isProxyConfiguredForKnoxVpn(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProxyConfiguredForKnoxVpn);
                    return true;
                case 38:
                    String readString22 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetUidListInNetworkCapabilities(readString22, readInt21, readString23);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String readString24 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    String readString25 = parcel.readString();
                    ProxyInfo proxyInfo = (ProxyInfo) parcel.readTypedObject(ProxyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateLocalProxyInfo(readString24, readInt22, readString25, proxyInfo);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    String readString26 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    applyBlockingRulesToUidRange(readString26, readInt23, readBoolean2, readString27);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String readString28 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean prepareEnterpriseVpnExt = prepareEnterpriseVpnExt(readString28, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(prepareEnterpriseVpnExt);
                    return true;
                case 42:
                    String readString29 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disconnectKnoxVpn = disconnectKnoxVpn(readString29, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disconnectKnoxVpn);
                    return true;
                case 43:
                    String readString30 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateEnterpriseVpn(readString30, readInt25, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateNotificationIcon(readInt26);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createEnterpriseVpnInstance(readString31, readString32, readInt27, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEnterpriseVpnInstance(readString33, readString34, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String readString35 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    int[] createIntArray = parcel.createIntArray();
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateUidRangesToPerAppVpn(readString35, readInt30, readBoolean5, createIntArray, readString36);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String readString37 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt32 = parcel.readInt();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateUidRangesToUserVpn(readString37, readInt31, readBoolean6, readInt32, readString38);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String readString39 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateUidRangesToUserVpnWithBlackList(readString39, readInt33, readInt34, createIntArray2, readString40);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    registerSystemDefaultNetworkCallback();
                    parcel2.writeNoException();
                    return true;
                case 51:
                    unregisterSystemDefaultNetworkCallback();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int netIdforActiveDefaultInterface = getNetIdforActiveDefaultInterface();
                    parcel2.writeNoException();
                    parcel2.writeInt(netIdforActiveDefaultInterface);
                    return true;
                case 53:
                    Network activeDefaultNetwork = getActiveDefaultNetwork();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeDefaultNetwork, 1);
                    return true;
                case 54:
                    String activeDefaultInterface = getActiveDefaultInterface();
                    parcel2.writeNoException();
                    parcel2.writeString(activeDefaultInterface);
                    return true;
                case 55:
                    int readInt35 = parcel.readInt();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int knoxNwFilterHttpProxyPort = getKnoxNwFilterHttpProxyPort(readInt35, readString41);
                    parcel2.writeNoException();
                    parcel2.writeInt(knoxNwFilterHttpProxyPort);
                    return true;
                case 56:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVpnConfigured = isVpnConfigured(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVpnConfigured);
                    return true;
                case 57:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDoEnabled = isDoEnabled(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDoEnabled);
                    return true;
                case 58:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vpnNetId = getVpnNetId(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnNetId);
                    return true;
                case 59:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vpnClientUid = getVpnClientUid(readInt39);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnClientUid);
                    return true;
                case 60:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isLockdownVpnEnabled = isLockdownVpnEnabled(readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLockdownVpnEnabled);
                    return true;
                case 61:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> lockdownVpnAllowList = getLockdownVpnAllowList(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeStringList(lockdownVpnAllowList);
                    return true;
                case 62:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VpnConfig vpnConfigForUser = getVpnConfigForUser(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnConfigForUser, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVpnManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVpnManager.DESCRIPTOR;
            }

            @Override // android.net.IVpnManager
            public boolean prepareVpn(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void setVpnPackageAuthorization(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeTypedObject(vpnConfig, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean addVpnAddress(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean removeVpnAddress(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setUnderlyingNetworksForVpn(Network[] networkArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeTypedArray(networkArr, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean provisionVpnProfile(VpnProfile vpnProfile, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeTypedObject(vpnProfile, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void deleteVpnProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String startVpnProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void stopVpnProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public VpnProfileState getProvisionedVpnProfileState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VpnProfileState) obtain2.readTypedObject(VpnProfileState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setAppExclusionList(int i, String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public List<String> getAppExclusionList(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isAlwaysOnVpnPackageSupported(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String getAlwaysOnVpnPackage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isVpnLockdownEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public List<String> getVpnLockdownAllowlist(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeTypedObject(vpnProfile, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public LegacyVpnInfo getLegacyVpnInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LegacyVpnInfo) obtain2.readTypedObject(LegacyVpnInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean updateLockdownVpn() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public byte[] getFromVpnProfileStore(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean putIntoVpnProfileStore(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean removeFromVpnProfileStore(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String[] listFromVpnProfileStore(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public VpnConfig getVpnConfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VpnConfig) obtain2.readTypedObject(VpnConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void factoryReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean getChainingEnabledForProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int knoxVpnProfileType(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int[] getKnoxVpnZtnaProxyInfoForUid(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String[] getProxyInfoForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean checkIfLocalProxyPortExists(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean checkIfUidIsExempted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String[] getDnsServerListForInterface(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void resetUidListInNetworkCapabilities(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateLocalProxyInfo(String str, int i, String str2, ProxyInfo proxyInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void applyBlockingRulesToUidRange(String str, int i, boolean z, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean prepareEnterpriseVpnExt(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean disconnectKnoxVpn(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateEnterpriseVpn(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateNotificationIcon(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void createEnterpriseVpnInstance(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void removeEnterpriseVpnInstance(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateUidRangesToPerAppVpn(String str, int i, boolean z, int[] iArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateUidRangesToUserVpn(String str, int i, boolean z, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateUidRangesToUserVpnWithBlackList(String str, int i, int i2, int[] iArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void registerSystemDefaultNetworkCallback() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void unregisterSystemDefaultNetworkCallback() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getNetIdforActiveDefaultInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public Network getActiveDefaultNetwork() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Network) obtain2.readTypedObject(Network.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String getActiveDefaultInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isVpnConfigured(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isDoEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getVpnNetId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getVpnClientUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isLockdownVpnEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public List<String> getLockdownVpnAllowList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public VpnConfig getVpnConfigForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VpnConfig) obtain2.readTypedObject(VpnConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
