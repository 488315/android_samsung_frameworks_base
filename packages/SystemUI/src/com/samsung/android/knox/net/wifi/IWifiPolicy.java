package com.samsung.android.knox.net.wifi;

import android.net.wifi.WifiConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IWifiPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.wifi.IWifiPolicy";

    boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z) throws RemoteException;

    int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z) throws RemoteException;

    boolean addWifiSsidToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowWifiScanning(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean clearWifiSsidBlackList(ContextInfo contextInfo) throws RemoteException;

    boolean clearWifiSsidWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<WifiControlInfo> getAllWifiSsidBlackLists(ContextInfo contextInfo) throws RemoteException;

    List<WifiControlInfo> getAllWifiSsidWhiteLists(ContextInfo contextInfo) throws RemoteException;

    boolean getAllowUserPolicyChanges(ContextInfo contextInfo) throws RemoteException;

    boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i) throws RemoteException;

    boolean getAutomaticConnectionToWifi(ContextInfo contextInfo) throws RemoteException;

    List<String> getBlockedNetworks(ContextInfo contextInfo) throws RemoteException;

    int getMinimumRequiredSecurity(ContextInfo contextInfo) throws RemoteException;

    List<String> getNetworkSSIDList(ContextInfo contextInfo) throws RemoteException;

    boolean getPasswordHidden(ContextInfo contextInfo) throws RemoteException;

    boolean getPromptCredentialsEnabled(ContextInfo contextInfo) throws RemoteException;

    WifiConfiguration getWifiApSetting(ContextInfo contextInfo) throws RemoteException;

    WifiAdminProfile getWifiProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isOpenWifiApAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isWifiAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isWifiScanningAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isWifiSsidRestrictionActive(ContextInfo contextInfo) throws RemoteException;

    boolean isWifiStateChangeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean removeBlockedNetwork(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeNetworkConfiguration(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    void resetAutomaticConnectionPolicy(int i) throws RemoteException;

    boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i) throws RemoteException;

    boolean setPasswordHidden(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setWifi(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setWifiAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setWifiApSetting(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException;

    boolean setWifiProfile(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile) throws RemoteException;

    boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IWifiPolicy {
        public static final int TRANSACTION_activateWifiSsidRestriction = 1;
        public static final int TRANSACTION_addNetworkWithRandomizationState = 43;
        public static final int TRANSACTION_addWifiSsidToBlackList = 2;
        public static final int TRANSACTION_addWifiSsidToWhiteList = 3;
        public static final int TRANSACTION_allowOpenWifiAp = 4;
        public static final int TRANSACTION_allowWifiApSettingUserModification = 5;
        public static final int TRANSACTION_allowWifiScanning = 40;
        public static final int TRANSACTION_clearWifiSsidBlackList = 6;
        public static final int TRANSACTION_clearWifiSsidWhiteList = 7;
        public static final int TRANSACTION_getAllWifiSsidBlackLists = 18;
        public static final int TRANSACTION_getAllWifiSsidWhiteLists = 19;
        public static final int TRANSACTION_getAllowUserPolicyChanges = 8;
        public static final int TRANSACTION_getAllowUserProfiles = 9;
        public static final int TRANSACTION_getAutomaticConnectionToWifi = 10;
        public static final int TRANSACTION_getBlockedNetworks = 11;
        public static final int TRANSACTION_getMinimumRequiredSecurity = 12;
        public static final int TRANSACTION_getNetworkSSIDList = 13;
        public static final int TRANSACTION_getPasswordHidden = 14;
        public static final int TRANSACTION_getPromptCredentialsEnabled = 15;
        public static final int TRANSACTION_getWifiApSetting = 16;
        public static final int TRANSACTION_getWifiProfile = 17;
        public static final int TRANSACTION_isOpenWifiApAllowed = 20;
        public static final int TRANSACTION_isWifiAllowed = 21;
        public static final int TRANSACTION_isWifiApSettingUserModificationAllowed = 22;
        public static final int TRANSACTION_isWifiScanningAllowed = 41;
        public static final int TRANSACTION_isWifiSsidRestrictionActive = 23;
        public static final int TRANSACTION_isWifiStateChangeAllowed = 24;
        public static final int TRANSACTION_removeBlockedNetwork = 25;
        public static final int TRANSACTION_removeNetworkConfiguration = 26;
        public static final int TRANSACTION_removeWifiSsidFromBlackList = 27;
        public static final int TRANSACTION_removeWifiSsidFromWhiteList = 28;
        public static final int TRANSACTION_resetAutomaticConnectionPolicy = 42;
        public static final int TRANSACTION_setAllowUserPolicyChanges = 29;
        public static final int TRANSACTION_setAllowUserProfiles = 30;
        public static final int TRANSACTION_setAutomaticConnectionToWifi = 31;
        public static final int TRANSACTION_setMinimumRequiredSecurity = 32;
        public static final int TRANSACTION_setPasswordHidden = 33;
        public static final int TRANSACTION_setPromptCredentialsEnabled = 34;
        public static final int TRANSACTION_setWifi = 35;
        public static final int TRANSACTION_setWifiAllowed = 36;
        public static final int TRANSACTION_setWifiApSetting = 37;
        public static final int TRANSACTION_setWifiProfile = 38;
        public static final int TRANSACTION_setWifiStateChangeAllowed = 39;

        class Proxy implements IWifiPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(wifiConfiguration, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean addWifiSsidToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean allowWifiScanning(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean clearWifiSsidBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean clearWifiSsidWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public List<WifiControlInfo> getAllWifiSsidBlackLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(WifiControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public List<WifiControlInfo> getAllWifiSsidWhiteLists(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(WifiControlInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean getAllowUserPolicyChanges(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean getAutomaticConnectionToWifi(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public List<String> getBlockedNetworks(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IWifiPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public int getMinimumRequiredSecurity(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public List<String> getNetworkSSIDList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean getPasswordHidden(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean getPromptCredentialsEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public WifiConfiguration getWifiApSetting(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WifiConfiguration) parcelObtain2.readTypedObject(WifiConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public WifiAdminProfile getWifiProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WifiAdminProfile) parcelObtain2.readTypedObject(WifiAdminProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean isOpenWifiApAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean isWifiAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean isWifiScanningAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean isWifiSsidRestrictionActive(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean isWifiStateChangeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean removeBlockedNetwork(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean removeNetworkConfiguration(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public void resetAutomaticConnectionPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setPasswordHidden(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setWifi(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setWifiAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setWifiApSetting(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setWifiProfile(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(wifiAdminProfile, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IWifiPolicy.DESCRIPTOR);
        }

        public static IWifiPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWifiPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IWifiPolicy)) ? new Proxy(iBinder) : (IWifiPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "activateWifiSsidRestriction";
                case 2:
                    return "addWifiSsidToBlackList";
                case 3:
                    return "addWifiSsidToWhiteList";
                case 4:
                    return "allowOpenWifiAp";
                case 5:
                    return "allowWifiApSettingUserModification";
                case 6:
                    return "clearWifiSsidBlackList";
                case 7:
                    return "clearWifiSsidWhiteList";
                case 8:
                    return "getAllowUserPolicyChanges";
                case 9:
                    return "getAllowUserProfiles";
                case 10:
                    return "getAutomaticConnectionToWifi";
                case 11:
                    return "getBlockedNetworks";
                case 12:
                    return "getMinimumRequiredSecurity";
                case 13:
                    return "getNetworkSSIDList";
                case 14:
                    return "getPasswordHidden";
                case 15:
                    return "getPromptCredentialsEnabled";
                case 16:
                    return "getWifiApSetting";
                case 17:
                    return "getWifiProfile";
                case 18:
                    return "getAllWifiSsidBlackLists";
                case 19:
                    return "getAllWifiSsidWhiteLists";
                case 20:
                    return "isOpenWifiApAllowed";
                case 21:
                    return "isWifiAllowed";
                case 22:
                    return "isWifiApSettingUserModificationAllowed";
                case 23:
                    return "isWifiSsidRestrictionActive";
                case 24:
                    return "isWifiStateChangeAllowed";
                case 25:
                    return "removeBlockedNetwork";
                case 26:
                    return "removeNetworkConfiguration";
                case 27:
                    return "removeWifiSsidFromBlackList";
                case 28:
                    return "removeWifiSsidFromWhiteList";
                case 29:
                    return "setAllowUserPolicyChanges";
                case 30:
                    return "setAllowUserProfiles";
                case 31:
                    return "setAutomaticConnectionToWifi";
                case 32:
                    return "setMinimumRequiredSecurity";
                case 33:
                    return "setPasswordHidden";
                case 34:
                    return "setPromptCredentialsEnabled";
                case 35:
                    return "setWifi";
                case 36:
                    return "setWifiAllowed";
                case 37:
                    return "setWifiApSetting";
                case 38:
                    return "setWifiProfile";
                case 39:
                    return "setWifiStateChangeAllowed";
                case 40:
                    return "allowWifiScanning";
                case 41:
                    return "isWifiScanningAllowed";
                case 42:
                    return "resetAutomaticConnectionPolicy";
                case 43:
                    return "addNetworkWithRandomizationState";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 42;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWifiPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWifiPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zActivateWifiSsidRestriction = activateWifiSsidRestriction(contextInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivateWifiSsidRestriction);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddWifiSsidToBlackList = addWifiSsidToBlackList(contextInfo2, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddWifiSsidToBlackList);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddWifiSsidToWhiteList = addWifiSsidToWhiteList(contextInfo3, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddWifiSsidToWhiteList);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowOpenWifiAp = allowOpenWifiAp(contextInfo4, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowOpenWifiAp);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowWifiApSettingUserModification = allowWifiApSettingUserModification(contextInfo5, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowWifiApSettingUserModification);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearWifiSsidBlackList = clearWifiSsidBlackList(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearWifiSsidBlackList);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearWifiSsidWhiteList = clearWifiSsidWhiteList(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearWifiSsidWhiteList);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean allowUserPolicyChanges = getAllowUserPolicyChanges(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserPolicyChanges);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean allowUserProfiles = getAllowUserProfiles(contextInfo9, z4, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserProfiles);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean automaticConnectionToWifi = getAutomaticConnectionToWifi(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(automaticConnectionToWifi);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> blockedNetworks = getBlockedNetworks(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(blockedNetworks);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int minimumRequiredSecurity = getMinimumRequiredSecurity(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeInt(minimumRequiredSecurity);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> networkSSIDList = getNetworkSSIDList(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeStringList(networkSSIDList);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean passwordHidden = getPasswordHidden(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(passwordHidden);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean promptCredentialsEnabled = getPromptCredentialsEnabled(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(promptCredentialsEnabled);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    WifiConfiguration wifiApSetting = getWifiApSetting(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiApSetting, 1);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    WifiAdminProfile wifiProfile = getWifiProfile(contextInfo17, string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiProfile, 1);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<WifiControlInfo> allWifiSsidBlackLists = getAllWifiSsidBlackLists(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allWifiSsidBlackLists, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<WifiControlInfo> allWifiSsidWhiteLists = getAllWifiSsidWhiteLists(contextInfo19);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allWifiSsidWhiteLists, 1);
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsOpenWifiApAllowed = isOpenWifiApAllowed(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOpenWifiApAllowed);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiAllowed = isWifiAllowed(contextInfo21, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiAllowed);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiApSettingUserModificationAllowed = isWifiApSettingUserModificationAllowed(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiApSettingUserModificationAllowed);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiSsidRestrictionActive = isWifiSsidRestrictionActive(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiSsidRestrictionActive);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiStateChangeAllowed = isWifiStateChangeAllowed(contextInfo24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiStateChangeAllowed);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveBlockedNetwork = removeBlockedNetwork(contextInfo25, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveBlockedNetwork);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveNetworkConfiguration = removeNetworkConfiguration(contextInfo26, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveNetworkConfiguration);
                    return true;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveWifiSsidFromBlackList = removeWifiSsidFromBlackList(contextInfo27, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveWifiSsidFromBlackList);
                    return true;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveWifiSsidFromWhiteList = removeWifiSsidFromWhiteList(contextInfo28, arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveWifiSsidFromWhiteList);
                    return true;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserPolicyChanges2 = setAllowUserPolicyChanges(contextInfo29, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserPolicyChanges2);
                    return true;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserProfiles2 = setAllowUserProfiles(contextInfo30, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserProfiles2);
                    return true;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean automaticConnectionToWifi2 = setAutomaticConnectionToWifi(contextInfo31, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(automaticConnectionToWifi2);
                    return true;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimumRequiredSecurity2 = setMinimumRequiredSecurity(contextInfo32, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimumRequiredSecurity2);
                    return true;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean passwordHidden2 = setPasswordHidden(contextInfo33, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(passwordHidden2);
                    return true;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean promptCredentialsEnabled2 = setPromptCredentialsEnabled(contextInfo34, z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(promptCredentialsEnabled2);
                    return true;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifi = setWifi(contextInfo35, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifi);
                    return true;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiAllowed = setWifiAllowed(contextInfo36, z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiAllowed);
                    return true;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean wifiApSetting2 = setWifiApSetting(contextInfo37, string4, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiApSetting2);
                    return true;
                case 38:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    WifiAdminProfile wifiAdminProfile = (WifiAdminProfile) parcel.readTypedObject(WifiAdminProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean wifiProfile2 = setWifiProfile(contextInfo38, wifiAdminProfile);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiProfile2);
                    return true;
                case 39:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiStateChangeAllowed = setWifiStateChangeAllowed(contextInfo39, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiStateChangeAllowed);
                    return true;
                case 40:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowWifiScanning = allowWifiScanning(contextInfo40, z14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowWifiScanning);
                    return true;
                case 41:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiScanningAllowed = isWifiScanningAllowed(contextInfo41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiScanningAllowed);
                    return true;
                case 42:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetAutomaticConnectionPolicy(i5);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    WifiConfiguration wifiConfiguration = (WifiConfiguration) parcel.readTypedObject(WifiConfiguration.CREATOR);
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAddNetworkWithRandomizationState = addNetworkWithRandomizationState(wifiConfiguration, z15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddNetworkWithRandomizationState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IWifiPolicy {
        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean addWifiSsidToBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean allowWifiScanning(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean clearWifiSsidBlackList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean clearWifiSsidWhiteList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public List<WifiControlInfo> getAllWifiSsidBlackLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public List<WifiControlInfo> getAllWifiSsidWhiteLists(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean getAllowUserPolicyChanges(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean getAutomaticConnectionToWifi(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public List<String> getBlockedNetworks(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public int getMinimumRequiredSecurity(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public List<String> getNetworkSSIDList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean getPasswordHidden(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean getPromptCredentialsEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public WifiConfiguration getWifiApSetting(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public WifiAdminProfile getWifiProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean isOpenWifiApAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean isWifiAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean isWifiScanningAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean isWifiSsidRestrictionActive(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean isWifiStateChangeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean removeBlockedNetwork(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean removeNetworkConfiguration(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setPasswordHidden(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setWifi(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setWifiAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setWifiApSetting(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setWifiProfile(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public void resetAutomaticConnectionPolicy(int i) throws RemoteException {
        }
    }
}
