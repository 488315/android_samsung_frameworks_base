package com.samsung.android.knox.net.wifi;

import android.net.wifi.WifiConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IWifiPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.wifi.IWifiPolicy";

    boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z);

    int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z);

    boolean addWifiSsidToBlackList(ContextInfo contextInfo, List<String> list);

    boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List<String> list);

    boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z);

    boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z);

    boolean allowWifiScanning(ContextInfo contextInfo, boolean z);

    boolean clearWifiSsidBlackList(ContextInfo contextInfo);

    boolean clearWifiSsidWhiteList(ContextInfo contextInfo);

    List<WifiControlInfo> getAllWifiSsidBlackLists(ContextInfo contextInfo);

    List<WifiControlInfo> getAllWifiSsidWhiteLists(ContextInfo contextInfo);

    boolean getAllowUserPolicyChanges(ContextInfo contextInfo);

    boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i);

    boolean getAutomaticConnectionToWifi(ContextInfo contextInfo);

    List<String> getBlockedNetworks(ContextInfo contextInfo);

    int getMinimumRequiredSecurity(ContextInfo contextInfo);

    List<String> getNetworkSSIDList(ContextInfo contextInfo);

    boolean getPasswordHidden(ContextInfo contextInfo);

    boolean getPromptCredentialsEnabled(ContextInfo contextInfo);

    WifiConfiguration getWifiApSetting(ContextInfo contextInfo);

    WifiAdminProfile getWifiProfile(ContextInfo contextInfo, String str);

    boolean isOpenWifiApAllowed(ContextInfo contextInfo);

    boolean isWifiAllowed(ContextInfo contextInfo, boolean z);

    boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo);

    boolean isWifiScanningAllowed(ContextInfo contextInfo);

    boolean isWifiSsidRestrictionActive(ContextInfo contextInfo);

    boolean isWifiStateChangeAllowed(ContextInfo contextInfo);

    boolean removeBlockedNetwork(ContextInfo contextInfo, String str);

    boolean removeNetworkConfiguration(ContextInfo contextInfo, String str);

    boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List<String> list);

    boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List<String> list);

    void resetAutomaticConnectionPolicy(int i);

    boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z);

    boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z);

    boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z);

    boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i);

    boolean setPasswordHidden(ContextInfo contextInfo, boolean z);

    boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z);

    boolean setWifi(ContextInfo contextInfo, boolean z);

    boolean setWifiAllowed(ContextInfo contextInfo, boolean z);

    boolean setWifiApSetting(ContextInfo contextInfo, String str, String str2, String str3);

    boolean setWifiProfile(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile);

    boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IWifiPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IWifiPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(wifiConfiguration, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean addWifiSsidToBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean allowWifiScanning(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean clearWifiSsidBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean clearWifiSsidWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final List<WifiControlInfo> getAllWifiSsidBlackLists(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(WifiControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final List<WifiControlInfo> getAllWifiSsidWhiteLists(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(WifiControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean getAllowUserPolicyChanges(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean getAutomaticConnectionToWifi(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final List<String> getBlockedNetworks(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IWifiPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final int getMinimumRequiredSecurity(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final List<String> getNetworkSSIDList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean getPasswordHidden(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean getPromptCredentialsEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final WifiConfiguration getWifiApSetting(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WifiConfiguration) obtain2.readTypedObject(WifiConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final WifiAdminProfile getWifiProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WifiAdminProfile) obtain2.readTypedObject(WifiAdminProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean isOpenWifiApAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean isWifiAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean isWifiScanningAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean isWifiSsidRestrictionActive(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean isWifiStateChangeAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean removeBlockedNetwork(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean removeNetworkConfiguration(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final void resetAutomaticConnectionPolicy(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setPasswordHidden(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setWifi(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setWifiAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setWifiApSetting(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setWifiProfile(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(wifiAdminProfile, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
            public final boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWifiPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IWifiPolicy)) ? new Proxy(iBinder) : (IWifiPolicy) queryLocalInterface;
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

        public final int getMaxTransactionId() {
            return 42;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean activateWifiSsidRestriction = activateWifiSsidRestriction(contextInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activateWifiSsidRestriction);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addWifiSsidToBlackList = addWifiSsidToBlackList(contextInfo2, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addWifiSsidToBlackList);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addWifiSsidToWhiteList = addWifiSsidToWhiteList(contextInfo3, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addWifiSsidToWhiteList);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowOpenWifiAp = allowOpenWifiAp(contextInfo4, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowOpenWifiAp);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowWifiApSettingUserModification = allowWifiApSettingUserModification(contextInfo5, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowWifiApSettingUserModification);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearWifiSsidBlackList = clearWifiSsidBlackList(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearWifiSsidBlackList);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearWifiSsidWhiteList = clearWifiSsidWhiteList(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearWifiSsidWhiteList);
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
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean allowUserProfiles = getAllowUserProfiles(contextInfo9, readBoolean4, readInt);
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    WifiAdminProfile wifiProfile = getWifiProfile(contextInfo17, readString);
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
                    boolean isOpenWifiApAllowed = isOpenWifiApAllowed(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOpenWifiApAllowed);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isWifiAllowed = isWifiAllowed(contextInfo21, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiAllowed);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isWifiApSettingUserModificationAllowed = isWifiApSettingUserModificationAllowed(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApSettingUserModificationAllowed);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isWifiSsidRestrictionActive = isWifiSsidRestrictionActive(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiSsidRestrictionActive);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isWifiStateChangeAllowed = isWifiStateChangeAllowed(contextInfo24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiStateChangeAllowed);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeBlockedNetwork = removeBlockedNetwork(contextInfo25, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeBlockedNetwork);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeNetworkConfiguration = removeNetworkConfiguration(contextInfo26, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeNetworkConfiguration);
                    return true;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeWifiSsidFromBlackList = removeWifiSsidFromBlackList(contextInfo27, createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeWifiSsidFromBlackList);
                    return true;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean removeWifiSsidFromWhiteList = removeWifiSsidFromWhiteList(contextInfo28, createStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeWifiSsidFromWhiteList);
                    return true;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserPolicyChanges2 = setAllowUserPolicyChanges(contextInfo29, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserPolicyChanges2);
                    return true;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserProfiles2 = setAllowUserProfiles(contextInfo30, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserProfiles2);
                    return true;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean automaticConnectionToWifi2 = setAutomaticConnectionToWifi(contextInfo31, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(automaticConnectionToWifi2);
                    return true;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimumRequiredSecurity2 = setMinimumRequiredSecurity(contextInfo32, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimumRequiredSecurity2);
                    return true;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean passwordHidden2 = setPasswordHidden(contextInfo33, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(passwordHidden2);
                    return true;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean promptCredentialsEnabled2 = setPromptCredentialsEnabled(contextInfo34, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(promptCredentialsEnabled2);
                    return true;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifi = setWifi(contextInfo35, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifi);
                    return true;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiAllowed = setWifiAllowed(contextInfo36, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiAllowed);
                    return true;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean wifiApSetting2 = setWifiApSetting(contextInfo37, readString4, readString5, readString6);
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
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiStateChangeAllowed = setWifiStateChangeAllowed(contextInfo39, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiStateChangeAllowed);
                    return true;
                case 40:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowWifiScanning = allowWifiScanning(contextInfo40, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowWifiScanning);
                    return true;
                case 41:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isWifiScanningAllowed = isWifiScanningAllowed(contextInfo41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiScanningAllowed);
                    return true;
                case 42:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetAutomaticConnectionPolicy(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    WifiConfiguration wifiConfiguration = (WifiConfiguration) parcel.readTypedObject(WifiConfiguration.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int addNetworkWithRandomizationState = addNetworkWithRandomizationState(wifiConfiguration, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeInt(addNetworkWithRandomizationState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IWifiPolicy {
        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean addWifiSsidToBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean allowWifiScanning(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean clearWifiSsidBlackList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean clearWifiSsidWhiteList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final List<WifiControlInfo> getAllWifiSsidBlackLists(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final List<WifiControlInfo> getAllWifiSsidWhiteLists(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean getAllowUserPolicyChanges(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean getAutomaticConnectionToWifi(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final List<String> getBlockedNetworks(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final int getMinimumRequiredSecurity(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final List<String> getNetworkSSIDList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean getPasswordHidden(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean getPromptCredentialsEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final WifiConfiguration getWifiApSetting(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final WifiAdminProfile getWifiProfile(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean isOpenWifiApAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean isWifiAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean isWifiScanningAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean isWifiSsidRestrictionActive(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean isWifiStateChangeAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean removeBlockedNetwork(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean removeNetworkConfiguration(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setPasswordHidden(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setWifi(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setWifiAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setWifiApSetting(ContextInfo contextInfo, String str, String str2, String str3) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setWifiProfile(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.wifi.IWifiPolicy
        public final void resetAutomaticConnectionPolicy(int i) {
        }
    }
}
