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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVpnManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVpnManager)) {
                return (IVpnManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zPrepareVpn = prepareVpn(string, string2, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPrepareVpn);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVpnPackageAuthorization(string3, i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    VpnConfig vpnConfig = (VpnConfig) parcel.readTypedObject(VpnConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorEstablishVpn = establishVpn(vpnConfig);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorEstablishVpn, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAddVpnAddress = addVpnAddress(string4, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddVpnAddress);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveVpnAddress = removeVpnAddress(string5, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveVpnAddress);
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
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zProvisionVpnProfile = provisionVpnProfile(vpnProfile, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zProvisionVpnProfile);
                    return true;
                case 8:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteVpnProfile(string7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strStartVpnProfile = startVpnProfile(string8);
                    parcel2.writeNoException();
                    parcel2.writeString(strStartVpnProfile);
                    return true;
                case 10:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopVpnProfile(string9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    VpnProfileState provisionedVpnProfileState = getProvisionedVpnProfileState(string10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(provisionedVpnProfileState, 1);
                    return true;
                case 12:
                    int i8 = parcel.readInt();
                    String string11 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean appExclusionList = setAppExclusionList(i8, string11, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appExclusionList);
                    return true;
                case 13:
                    int i9 = parcel.readInt();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> appExclusionList2 = getAppExclusionList(i9, string12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(appExclusionList2);
                    return true;
                case 14:
                    int i10 = parcel.readInt();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAlwaysOnVpnPackageSupported = isAlwaysOnVpnPackageSupported(i10, string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAlwaysOnVpnPackageSupported);
                    return true;
                case 15:
                    int i11 = parcel.readInt();
                    String string14 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnVpnPackage = setAlwaysOnVpnPackage(i11, string14, z, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysOnVpnPackage);
                    return true;
                case 16:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String alwaysOnVpnPackage2 = getAlwaysOnVpnPackage(i12);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackage2);
                    return true;
                case 17:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVpnLockdownEnabled = isVpnLockdownEnabled(i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVpnLockdownEnabled);
                    return true;
                case 18:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> vpnLockdownAllowlist = getVpnLockdownAllowlist(i14);
                    parcel2.writeNoException();
                    parcel2.writeStringList(vpnLockdownAllowlist);
                    return true;
                case 19:
                    boolean zIsCallerCurrentAlwaysOnVpnApp = isCallerCurrentAlwaysOnVpnApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallerCurrentAlwaysOnVpnApp);
                    return true;
                case 20:
                    boolean zIsCallerCurrentAlwaysOnVpnLockdownApp = isCallerCurrentAlwaysOnVpnLockdownApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallerCurrentAlwaysOnVpnLockdownApp);
                    return true;
                case 21:
                    VpnProfile vpnProfile2 = (VpnProfile) parcel.readTypedObject(VpnProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    startLegacyVpn(vpnProfile2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LegacyVpnInfo legacyVpnInfo = getLegacyVpnInfo(i15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(legacyVpnInfo, 1);
                    return true;
                case 23:
                    boolean zUpdateLockdownVpn = updateLockdownVpn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateLockdownVpn);
                    return true;
                case 24:
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] fromVpnProfileStore = getFromVpnProfileStore(string15);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(fromVpnProfileStore);
                    return true;
                case 25:
                    String string16 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zPutIntoVpnProfileStore = putIntoVpnProfileStore(string16, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPutIntoVpnProfileStore);
                    return true;
                case 26:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveFromVpnProfileStore = removeFromVpnProfileStore(string17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveFromVpnProfileStore);
                    return true;
                case 27:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] strArrListFromVpnProfileStore = listFromVpnProfileStore(string18);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrListFromVpnProfileStore);
                    return true;
                case 28:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VpnConfig vpnConfig2 = getVpnConfig(i16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnConfig2, 1);
                    return true;
                case 29:
                    factoryReset();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean chainingEnabledForProfile = getChainingEnabledForProfile(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(chainingEnabledForProfile);
                    return true;
                case 31:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iKnoxVpnProfileType = knoxVpnProfileType(string19);
                    parcel2.writeNoException();
                    parcel2.writeInt(iKnoxVpnProfileType);
                    return true;
                case 32:
                    int i18 = parcel.readInt();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] knoxVpnZtnaProxyInfoForUid = getKnoxVpnZtnaProxyInfoForUid(i18, string20);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(knoxVpnZtnaProxyInfoForUid);
                    return true;
                case 33:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] proxyInfoForUid = getProxyInfoForUid(i19);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(proxyInfoForUid);
                    return true;
                case 34:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckIfLocalProxyPortExists = checkIfLocalProxyPortExists(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckIfLocalProxyPortExists);
                    return true;
                case 35:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckIfUidIsExempted = checkIfUidIsExempted(i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckIfUidIsExempted);
                    return true;
                case 36:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] dnsServerListForInterface = getDnsServerListForInterface(string21);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(dnsServerListForInterface);
                    return true;
                case 37:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsProxyConfiguredForKnoxVpn = isProxyConfiguredForKnoxVpn(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProxyConfiguredForKnoxVpn);
                    return true;
                case 38:
                    String string22 = parcel.readString();
                    int i23 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetUidListInNetworkCapabilities(string22, i23, string23);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String string24 = parcel.readString();
                    int i24 = parcel.readInt();
                    String string25 = parcel.readString();
                    ProxyInfo proxyInfo = (ProxyInfo) parcel.readTypedObject(ProxyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateLocalProxyInfo(string24, i24, string25, proxyInfo);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    String string26 = parcel.readString();
                    int i25 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    applyBlockingRulesToUidRange(string26, i25, z2, string27);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String string28 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zPrepareEnterpriseVpnExt = prepareEnterpriseVpnExt(string28, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPrepareEnterpriseVpnExt);
                    return true;
                case 42:
                    String string29 = parcel.readString();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDisconnectKnoxVpn = disconnectKnoxVpn(string29, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisconnectKnoxVpn);
                    return true;
                case 43:
                    String string30 = parcel.readString();
                    int i27 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateEnterpriseVpn(string30, i27, z4);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateNotificationIcon(i28);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createEnterpriseVpnInstance(string31, string32, i29, i30);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEnterpriseVpnInstance(string33, string34, i31);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String string35 = parcel.readString();
                    int i32 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateUidRangesToPerAppVpn(string35, i32, z5, iArrCreateIntArray, string36);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String string37 = parcel.readString();
                    int i33 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    int i34 = parcel.readInt();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateUidRangesToUserVpn(string37, i33, z6, i34, string38);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String string39 = parcel.readString();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateUidRangesToUserVpnWithBlackList(string39, i35, i36, iArrCreateIntArray2, string40);
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
                    int i37 = parcel.readInt();
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int knoxNwFilterHttpProxyPort = getKnoxNwFilterHttpProxyPort(i37, string41);
                    parcel2.writeNoException();
                    parcel2.writeInt(knoxNwFilterHttpProxyPort);
                    return true;
                case 56:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVpnConfigured = isVpnConfigured(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVpnConfigured);
                    return true;
                case 57:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDoEnabled = isDoEnabled(i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDoEnabled);
                    return true;
                case 58:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vpnNetId = getVpnNetId(i40);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnNetId);
                    return true;
                case 59:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vpnClientUid = getVpnClientUid(i41);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnClientUid);
                    return true;
                case 60:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsLockdownVpnEnabled = isLockdownVpnEnabled(i42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLockdownVpnEnabled);
                    return true;
                case 61:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> lockdownVpnAllowList = getLockdownVpnAllowList(i43);
                    parcel2.writeNoException();
                    parcel2.writeStringList(lockdownVpnAllowList);
                    return true;
                case 62:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VpnConfig vpnConfigForUser = getVpnConfigForUser(i44);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void setVpnPackageAuthorization(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(vpnConfig, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean addVpnAddress(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean removeVpnAddress(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setUnderlyingNetworksForVpn(Network[] networkArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeTypedArray(networkArr, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean provisionVpnProfile(VpnProfile vpnProfile, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(vpnProfile, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void deleteVpnProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String startVpnProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void stopVpnProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public VpnProfileState getProvisionedVpnProfileState(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VpnProfileState) parcelObtain2.readTypedObject(VpnProfileState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setAppExclusionList(int i, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public List<String> getAppExclusionList(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isAlwaysOnVpnPackageSupported(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String getAlwaysOnVpnPackage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isVpnLockdownEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public List<String> getVpnLockdownAllowlist(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(vpnProfile, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public LegacyVpnInfo getLegacyVpnInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LegacyVpnInfo) parcelObtain2.readTypedObject(LegacyVpnInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean updateLockdownVpn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public byte[] getFromVpnProfileStore(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean putIntoVpnProfileStore(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean removeFromVpnProfileStore(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String[] listFromVpnProfileStore(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public VpnConfig getVpnConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VpnConfig) parcelObtain2.readTypedObject(VpnConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void factoryReset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean getChainingEnabledForProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int knoxVpnProfileType(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int[] getKnoxVpnZtnaProxyInfoForUid(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String[] getProxyInfoForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean checkIfLocalProxyPortExists(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean checkIfUidIsExempted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String[] getDnsServerListForInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void resetUidListInNetworkCapabilities(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateLocalProxyInfo(String str, int i, String str2, ProxyInfo proxyInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void applyBlockingRulesToUidRange(String str, int i, boolean z, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean prepareEnterpriseVpnExt(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean disconnectKnoxVpn(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateEnterpriseVpn(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateNotificationIcon(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void createEnterpriseVpnInstance(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void removeEnterpriseVpnInstance(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateUidRangesToPerAppVpn(String str, int i, boolean z, int[] iArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateUidRangesToUserVpn(String str, int i, boolean z, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void updateUidRangesToUserVpnWithBlackList(String str, int i, int i2, int[] iArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void registerSystemDefaultNetworkCallback() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void unregisterSystemDefaultNetworkCallback() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getNetIdforActiveDefaultInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public Network getActiveDefaultNetwork() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Network) parcelObtain2.readTypedObject(Network.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public String getActiveDefaultInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isVpnConfigured(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isDoEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getVpnNetId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public int getVpnClientUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isLockdownVpnEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public List<String> getLockdownVpnAllowList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public VpnConfig getVpnConfigForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVpnManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VpnConfig) parcelObtain2.readTypedObject(VpnConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
