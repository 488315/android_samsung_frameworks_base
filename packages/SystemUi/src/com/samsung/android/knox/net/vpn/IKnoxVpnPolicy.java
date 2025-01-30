package com.samsung.android.knox.net.vpn;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKnoxVpnPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.IKnoxVpnPolicy";

    EnterpriseResponseData activateVpnProfile(KnoxVpnContext knoxVpnContext, String str, boolean z);

    EnterpriseResponseData addAllContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String str);

    EnterpriseResponseData addAllPackagesToVpn(KnoxVpnContext knoxVpnContext, String str);

    EnterpriseResponseData addContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str);

    EnterpriseResponseData addPackagesToVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str);

    void addVpnUidRanges(String str, int i, String str2, String str3, String str4);

    int allowAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str, Bundle bundle);

    int allowNoAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str);

    boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext);

    boolean checkIfCallerIsVpnVendor(int i);

    boolean checkIfLocalProxyPortExists(int i);

    boolean checkIfUidIsExempted(int i);

    boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2);

    EnterpriseResponseData createVpnProfile(KnoxVpnContext knoxVpnContext, String str);

    int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str);

    EnterpriseResponseData getAllContainerPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, int i, String str);

    EnterpriseResponseData getAllPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, String str);

    EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext);

    EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str);

    int getChainingEnabledForProfile(int i);

    List<String> getDomainsByProfileName(String str);

    EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str);

    String getInterfaceNameForUid(int i);

    int getKnoxVpnProfileType(String str);

    int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i);

    int getNotificationDismissibleFlagInternal(int i);

    List<String> getProfilesByDomain(String str);

    String[] getProxyInfoForUid(int i);

    EnterpriseResponseData getState(KnoxVpnContext knoxVpnContext, String str);

    int getUidPidEnabled(int i, String str);

    EnterpriseResponseData getUserCertificate(KnoxVpnContext knoxVpnContext, String str);

    String getVendorNameForProfile(String str);

    EnterpriseResponseData getVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str);

    EnterpriseResponseData getVpnProfile(KnoxVpnContext knoxVpnContext, String str);

    boolean isProxyConfiguredForKnoxVpn(int i);

    int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str);

    EnterpriseResponseData removeAllContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String str);

    EnterpriseResponseData removeAllPackagesFromVpn(KnoxVpnContext knoxVpnContext, String str);

    EnterpriseResponseData removeContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str);

    EnterpriseResponseData removePackagesFromVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str);

    EnterpriseResponseData removeVpnProfile(KnoxVpnContext knoxVpnContext, String str);

    void removeVpnUidRanges(String str);

    EnterpriseResponseData setAutoRetryOnConnectionError(KnoxVpnContext knoxVpnContext, String str, boolean z);

    EnterpriseResponseData setCACertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr);

    void setInitialTimeToTunnelEstablishment(String str, long j);

    int setNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, String str, int i, int i2);

    EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i);

    EnterpriseResponseData setUserCertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2);

    EnterpriseResponseData setVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str, int i);

    void showToastVpnEULA();

    EnterpriseResponseData startConnection(KnoxVpnContext knoxVpnContext, String str);

    EnterpriseResponseData stopConnection(KnoxVpnContext knoxVpnContext, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IKnoxVpnPolicy {
        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData activateVpnProfile(KnoxVpnContext knoxVpnContext, String str, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData addAllContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData addAllPackagesToVpn(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData addContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData addPackagesToVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int allowAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str, Bundle bundle) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int allowNoAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) {
            return 0;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final boolean checkIfCallerIsVpnVendor(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final boolean checkIfLocalProxyPortExists(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final boolean checkIfUidIsExempted(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData createVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getAllContainerPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, int i, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getAllPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int getChainingEnabledForProfile(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final List<String> getDomainsByProfileName(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final String getInterfaceNameForUid(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int getKnoxVpnProfileType(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int getNotificationDismissibleFlagInternal(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final List<String> getProfilesByDomain(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final String[] getProxyInfoForUid(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getState(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int getUidPidEnabled(int i, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getUserCertificate(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final String getVendorNameForProfile(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData getVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final boolean isProxyConfiguredForKnoxVpn(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData removeAllContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData removeAllPackagesFromVpn(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData removeContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData removePackagesFromVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData removeVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData setAutoRetryOnConnectionError(KnoxVpnContext knoxVpnContext, String str, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData setCACertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final int setNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, String str, int i, int i2) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData setUserCertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData setVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData startConnection(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final EnterpriseResponseData stopConnection(KnoxVpnContext knoxVpnContext, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final void showToastVpnEULA() {
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final void removeVpnUidRanges(String str) {
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final void setInitialTimeToTunnelEstablishment(String str, long j) {
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public final void addVpnUidRanges(String str, int i, String str2, String str3, String str4) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IKnoxVpnPolicy {
        public static final int TRANSACTION_activateVpnProfile = 5;
        public static final int TRANSACTION_addAllContainerPackagesToVpn = 24;
        public static final int TRANSACTION_addAllPackagesToVpn = 19;
        public static final int TRANSACTION_addContainerPackagesToVpn = 21;
        public static final int TRANSACTION_addPackagesToVpn = 16;
        public static final int TRANSACTION_addVpnUidRanges = 43;
        public static final int TRANSACTION_allowAuthUsbTetheringOverVpn = 29;
        public static final int TRANSACTION_allowNoAuthUsbTetheringOverVpn = 28;
        public static final int TRANSACTION_bindKnoxVpnInterface = 34;
        public static final int TRANSACTION_checkIfCallerIsVpnVendor = 50;
        public static final int TRANSACTION_checkIfLocalProxyPortExists = 48;
        public static final int TRANSACTION_checkIfUidIsExempted = 46;
        public static final int TRANSACTION_checkIfVendorCreatedKnoxProfile = 40;
        public static final int TRANSACTION_createVpnProfile = 1;
        public static final int TRANSACTION_disallowUsbTetheringOverVpn = 30;
        public static final int TRANSACTION_getAllContainerPackagesInVpnProfile = 23;
        public static final int TRANSACTION_getAllPackagesInVpnProfile = 18;
        public static final int TRANSACTION_getAllVpnProfiles = 4;
        public static final int TRANSACTION_getCACertificate = 9;
        public static final int TRANSACTION_getChainingEnabledForProfile = 36;
        public static final int TRANSACTION_getDomainsByProfileName = 38;
        public static final int TRANSACTION_getErrorString = 13;
        public static final int TRANSACTION_getInterfaceNameForUid = 42;
        public static final int TRANSACTION_getKnoxVpnProfileType = 37;
        public static final int TRANSACTION_getNotificationDismissibleFlag = 33;
        public static final int TRANSACTION_getNotificationDismissibleFlagInternal = 52;
        public static final int TRANSACTION_getProfilesByDomain = 39;
        public static final int TRANSACTION_getProxyInfoForUid = 47;
        public static final int TRANSACTION_getState = 12;
        public static final int TRANSACTION_getUidPidEnabled = 35;
        public static final int TRANSACTION_getUserCertificate = 7;
        public static final int TRANSACTION_getVendorNameForProfile = 41;
        public static final int TRANSACTION_getVpnModeOfOperation = 15;
        public static final int TRANSACTION_getVpnProfile = 2;
        public static final int TRANSACTION_isProxyConfiguredForKnoxVpn = 49;
        public static final int TRANSACTION_isUsbTetheringOverVpnEnabled = 31;
        public static final int TRANSACTION_removeAllContainerPackagesFromVpn = 25;
        public static final int TRANSACTION_removeAllPackagesFromVpn = 20;
        public static final int TRANSACTION_removeContainerPackagesFromVpn = 22;
        public static final int TRANSACTION_removePackagesFromVpn = 17;
        public static final int TRANSACTION_removeVpnProfile = 3;
        public static final int TRANSACTION_removeVpnUidRanges = 44;
        public static final int TRANSACTION_setAutoRetryOnConnectionError = 27;
        public static final int TRANSACTION_setCACertificate = 8;
        public static final int TRANSACTION_setInitialTimeToTunnelEstablishment = 51;
        public static final int TRANSACTION_setNotificationDismissibleFlag = 32;
        public static final int TRANSACTION_setServerCertValidationUserAcceptanceCriteria = 26;
        public static final int TRANSACTION_setUserCertificate = 6;
        public static final int TRANSACTION_setVpnModeOfOperation = 14;
        public static final int TRANSACTION_showToastVpnEULA = 45;
        public static final int TRANSACTION_startConnection = 10;
        public static final int TRANSACTION_stopConnection = 11;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IKnoxVpnPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData activateVpnProfile(KnoxVpnContext knoxVpnContext, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData addAllContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData addAllPackagesToVpn(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData addContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData addPackagesToVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final void addVpnUidRanges(String str, int i, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int allowAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int allowNoAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final boolean checkIfCallerIsVpnVendor(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final boolean checkIfLocalProxyPortExists(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final boolean checkIfUidIsExempted(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData createVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getAllContainerPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getAllPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int getChainingEnabledForProfile(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final List<String> getDomainsByProfileName(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IKnoxVpnPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final String getInterfaceNameForUid(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int getKnoxVpnProfileType(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int getNotificationDismissibleFlagInternal(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final List<String> getProfilesByDomain(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final String[] getProxyInfoForUid(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getState(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int getUidPidEnabled(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getUserCertificate(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final String getVendorNameForProfile(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData getVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final boolean isProxyConfiguredForKnoxVpn(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData removeAllContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData removeAllPackagesFromVpn(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData removeContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData removePackagesFromVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData removeVpnProfile(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final void removeVpnUidRanges(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData setAutoRetryOnConnectionError(KnoxVpnContext knoxVpnContext, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData setCACertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final void setInitialTimeToTunnelEstablishment(String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final int setNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData setUserCertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData setVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final void showToastVpnEULA() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData startConnection(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public final EnterpriseResponseData stopConnection(KnoxVpnContext knoxVpnContext, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(knoxVpnContext, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseResponseData) obtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxVpnPolicy.DESCRIPTOR);
        }

        public static IKnoxVpnPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxVpnPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxVpnPolicy)) ? new Proxy(iBinder) : (IKnoxVpnPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxVpnPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxVpnPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    KnoxVpnContext knoxVpnContext = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData createVpnProfile = createVpnProfile(knoxVpnContext, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createVpnProfile, 1);
                    return true;
                case 2:
                    KnoxVpnContext knoxVpnContext2 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData vpnProfile = getVpnProfile(knoxVpnContext2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnProfile, 1);
                    return true;
                case 3:
                    KnoxVpnContext knoxVpnContext3 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData removeVpnProfile = removeVpnProfile(knoxVpnContext3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removeVpnProfile, 1);
                    return true;
                case 4:
                    KnoxVpnContext knoxVpnContext4 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData allVpnProfiles = getAllVpnProfiles(knoxVpnContext4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allVpnProfiles, 1);
                    return true;
                case 5:
                    KnoxVpnContext knoxVpnContext5 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString4 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData activateVpnProfile = activateVpnProfile(knoxVpnContext5, readString4, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activateVpnProfile, 1);
                    return true;
                case 6:
                    KnoxVpnContext knoxVpnContext6 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString5 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData userCertificate = setUserCertificate(knoxVpnContext6, readString5, createByteArray, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCertificate, 1);
                    return true;
                case 7:
                    KnoxVpnContext knoxVpnContext7 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData userCertificate2 = getUserCertificate(knoxVpnContext7, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCertificate2, 1);
                    return true;
                case 8:
                    KnoxVpnContext knoxVpnContext8 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString8 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData cACertificate = setCACertificate(knoxVpnContext8, readString8, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cACertificate, 1);
                    return true;
                case 9:
                    KnoxVpnContext knoxVpnContext9 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData cACertificate2 = getCACertificate(knoxVpnContext9, readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cACertificate2, 1);
                    return true;
                case 10:
                    KnoxVpnContext knoxVpnContext10 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData startConnection = startConnection(knoxVpnContext10, readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startConnection, 1);
                    return true;
                case 11:
                    KnoxVpnContext knoxVpnContext11 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData stopConnection = stopConnection(knoxVpnContext11, readString11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stopConnection, 1);
                    return true;
                case 12:
                    KnoxVpnContext knoxVpnContext12 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData state = getState(knoxVpnContext12, readString12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 13:
                    KnoxVpnContext knoxVpnContext13 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData errorString = getErrorString(knoxVpnContext13, readString13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(errorString, 1);
                    return true;
                case 14:
                    KnoxVpnContext knoxVpnContext14 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString14 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData vpnModeOfOperation = setVpnModeOfOperation(knoxVpnContext14, readString14, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnModeOfOperation, 1);
                    return true;
                case 15:
                    KnoxVpnContext knoxVpnContext15 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData vpnModeOfOperation2 = getVpnModeOfOperation(knoxVpnContext15, readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnModeOfOperation2, 1);
                    return true;
                case 16:
                    KnoxVpnContext knoxVpnContext16 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData addPackagesToVpn = addPackagesToVpn(knoxVpnContext16, createStringArray, readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addPackagesToVpn, 1);
                    return true;
                case 17:
                    KnoxVpnContext knoxVpnContext17 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String[] createStringArray2 = parcel.createStringArray();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData removePackagesFromVpn = removePackagesFromVpn(knoxVpnContext17, createStringArray2, readString17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removePackagesFromVpn, 1);
                    return true;
                case 18:
                    KnoxVpnContext knoxVpnContext18 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData allPackagesInVpnProfile = getAllPackagesInVpnProfile(knoxVpnContext18, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPackagesInVpnProfile, 1);
                    return true;
                case 19:
                    KnoxVpnContext knoxVpnContext19 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData addAllPackagesToVpn = addAllPackagesToVpn(knoxVpnContext19, readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addAllPackagesToVpn, 1);
                    return true;
                case 20:
                    KnoxVpnContext knoxVpnContext20 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData removeAllPackagesFromVpn = removeAllPackagesFromVpn(knoxVpnContext20, readString20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removeAllPackagesFromVpn, 1);
                    return true;
                case 21:
                    KnoxVpnContext knoxVpnContext21 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int readInt2 = parcel.readInt();
                    String[] createStringArray3 = parcel.createStringArray();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData addContainerPackagesToVpn = addContainerPackagesToVpn(knoxVpnContext21, readInt2, createStringArray3, readString21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addContainerPackagesToVpn, 1);
                    return true;
                case 22:
                    KnoxVpnContext knoxVpnContext22 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int readInt3 = parcel.readInt();
                    String[] createStringArray4 = parcel.createStringArray();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData removeContainerPackagesFromVpn = removeContainerPackagesFromVpn(knoxVpnContext22, readInt3, createStringArray4, readString22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removeContainerPackagesFromVpn, 1);
                    return true;
                case 23:
                    KnoxVpnContext knoxVpnContext23 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int readInt4 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData allContainerPackagesInVpnProfile = getAllContainerPackagesInVpnProfile(knoxVpnContext23, readInt4, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allContainerPackagesInVpnProfile, 1);
                    return true;
                case 24:
                    KnoxVpnContext knoxVpnContext24 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int readInt5 = parcel.readInt();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData addAllContainerPackagesToVpn = addAllContainerPackagesToVpn(knoxVpnContext24, readInt5, readString24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addAllContainerPackagesToVpn, 1);
                    return true;
                case 25:
                    KnoxVpnContext knoxVpnContext25 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int readInt6 = parcel.readInt();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData removeAllContainerPackagesFromVpn = removeAllContainerPackagesFromVpn(knoxVpnContext25, readInt6, readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removeAllContainerPackagesFromVpn, 1);
                    return true;
                case 26:
                    KnoxVpnContext knoxVpnContext26 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString26 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData serverCertValidationUserAcceptanceCriteria = setServerCertValidationUserAcceptanceCriteria(knoxVpnContext26, readString26, readBoolean2, readArrayList, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serverCertValidationUserAcceptanceCriteria, 1);
                    return true;
                case 27:
                    KnoxVpnContext knoxVpnContext27 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString27 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData autoRetryOnConnectionError = setAutoRetryOnConnectionError(knoxVpnContext27, readString27, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(autoRetryOnConnectionError, 1);
                    return true;
                case 28:
                    KnoxVpnContext knoxVpnContext28 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int allowNoAuthUsbTetheringOverVpn = allowNoAuthUsbTetheringOverVpn(knoxVpnContext28, readString28);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowNoAuthUsbTetheringOverVpn);
                    return true;
                case 29:
                    KnoxVpnContext knoxVpnContext29 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString29 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int allowAuthUsbTetheringOverVpn = allowAuthUsbTetheringOverVpn(knoxVpnContext29, readString29, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowAuthUsbTetheringOverVpn);
                    return true;
                case 30:
                    KnoxVpnContext knoxVpnContext30 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int disallowUsbTetheringOverVpn = disallowUsbTetheringOverVpn(knoxVpnContext30, readString30);
                    parcel2.writeNoException();
                    parcel2.writeInt(disallowUsbTetheringOverVpn);
                    return true;
                case 31:
                    KnoxVpnContext knoxVpnContext31 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isUsbTetheringOverVpnEnabled = isUsbTetheringOverVpnEnabled(knoxVpnContext31, readString31);
                    parcel2.writeNoException();
                    parcel2.writeInt(isUsbTetheringOverVpnEnabled);
                    return true;
                case 32:
                    KnoxVpnContext knoxVpnContext32 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String readString32 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notificationDismissibleFlag = setNotificationDismissibleFlag(knoxVpnContext32, readString32, readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationDismissibleFlag);
                    return true;
                case 33:
                    KnoxVpnContext knoxVpnContext33 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notificationDismissibleFlag2 = getNotificationDismissibleFlag(knoxVpnContext33, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationDismissibleFlag2);
                    return true;
                case 34:
                    KnoxVpnContext knoxVpnContext34 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean bindKnoxVpnInterface = bindKnoxVpnInterface(knoxVpnContext34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindKnoxVpnInterface);
                    return true;
                case 35:
                    int readInt11 = parcel.readInt();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidPidEnabled = getUidPidEnabled(readInt11, readString33);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidPidEnabled);
                    return true;
                case 36:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int chainingEnabledForProfile = getChainingEnabledForProfile(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(chainingEnabledForProfile);
                    return true;
                case 37:
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int knoxVpnProfileType = getKnoxVpnProfileType(readString34);
                    parcel2.writeNoException();
                    parcel2.writeInt(knoxVpnProfileType);
                    return true;
                case 38:
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> domainsByProfileName = getDomainsByProfileName(readString35);
                    parcel2.writeNoException();
                    parcel2.writeStringList(domainsByProfileName);
                    return true;
                case 39:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> profilesByDomain = getProfilesByDomain(readString36);
                    parcel2.writeNoException();
                    parcel2.writeStringList(profilesByDomain);
                    return true;
                case 40:
                    String readString37 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkIfVendorCreatedKnoxProfile = checkIfVendorCreatedKnoxProfile(readString37, readInt13, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkIfVendorCreatedKnoxProfile);
                    return true;
                case 41:
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String vendorNameForProfile = getVendorNameForProfile(readString38);
                    parcel2.writeNoException();
                    parcel2.writeString(vendorNameForProfile);
                    return true;
                case 42:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String interfaceNameForUid = getInterfaceNameForUid(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeString(interfaceNameForUid);
                    return true;
                case 43:
                    String readString39 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addVpnUidRanges(readString39, readInt16, readString40, readString41, readString42);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeVpnUidRanges(readString43);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    showToastVpnEULA();
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkIfUidIsExempted = checkIfUidIsExempted(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkIfUidIsExempted);
                    return true;
                case 47:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] proxyInfoForUid = getProxyInfoForUid(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(proxyInfoForUid);
                    return true;
                case 48:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkIfLocalProxyPortExists = checkIfLocalProxyPortExists(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkIfLocalProxyPortExists);
                    return true;
                case 49:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isProxyConfiguredForKnoxVpn = isProxyConfiguredForKnoxVpn(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProxyConfiguredForKnoxVpn);
                    return true;
                case 50:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkIfCallerIsVpnVendor = checkIfCallerIsVpnVendor(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkIfCallerIsVpnVendor);
                    return true;
                case 51:
                    String readString44 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInitialTimeToTunnelEstablishment(readString44, readLong);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notificationDismissibleFlagInternal = getNotificationDismissibleFlagInternal(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationDismissibleFlagInternal);
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
}
