package com.samsung.android.knox.net.vpn;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IKnoxVpnPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.IKnoxVpnPolicy";

    EnterpriseResponseData activateVpnProfile(KnoxVpnContext knoxVpnContext, String str, boolean z) throws RemoteException;

    EnterpriseResponseData addAllContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException;

    EnterpriseResponseData addAllPackagesToVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    EnterpriseResponseData addContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) throws RemoteException;

    EnterpriseResponseData addPackagesToVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) throws RemoteException;

    void addVpnUidRanges(String str, int i, String str2, String str3, String str4) throws RemoteException;

    int allowAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str, Bundle bundle) throws RemoteException;

    int allowNoAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext) throws RemoteException;

    boolean checkIfCallerIsVpnVendor(int i) throws RemoteException;

    boolean checkIfLocalProxyPortExists(int i) throws RemoteException;

    boolean checkIfUidIsExempted(int i) throws RemoteException;

    boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2) throws RemoteException;

    EnterpriseResponseData createVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    EnterpriseResponseData getAllContainerPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException;

    EnterpriseResponseData getAllPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext) throws RemoteException;

    EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    int getChainingEnabledForProfile(int i) throws RemoteException;

    List<String> getDomainsByProfileName(String str) throws RemoteException;

    EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    String getInterfaceNameForUid(int i) throws RemoteException;

    int getKnoxVpnProfileType(String str) throws RemoteException;

    int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i) throws RemoteException;

    int getNotificationDismissibleFlagInternal(int i) throws RemoteException;

    List<String> getProfilesByDomain(String str) throws RemoteException;

    String[] getProxyInfoForUid(int i) throws RemoteException;

    EnterpriseResponseData getState(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    int getUidPidEnabled(int i, String str) throws RemoteException;

    EnterpriseResponseData getUserCertificate(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    String getVendorNameForProfile(String str) throws RemoteException;

    EnterpriseResponseData getVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    EnterpriseResponseData getVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException;

    int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    EnterpriseResponseData removeAllContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException;

    EnterpriseResponseData removeAllPackagesFromVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    EnterpriseResponseData removeContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) throws RemoteException;

    EnterpriseResponseData removePackagesFromVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) throws RemoteException;

    EnterpriseResponseData removeVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    void removeVpnUidRanges(String str) throws RemoteException;

    EnterpriseResponseData setAutoRetryOnConnectionError(KnoxVpnContext knoxVpnContext, String str, boolean z) throws RemoteException;

    EnterpriseResponseData setCACertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr) throws RemoteException;

    int setNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, String str, int i, int i2) throws RemoteException;

    EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i) throws RemoteException;

    EnterpriseResponseData setUserCertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2) throws RemoteException;

    EnterpriseResponseData setVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str, int i) throws RemoteException;

    void showToastVpnEULA() throws RemoteException;

    EnterpriseResponseData startConnection(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    EnterpriseResponseData stopConnection(KnoxVpnContext knoxVpnContext, String str) throws RemoteException;

    public class Default implements IKnoxVpnPolicy {
        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData activateVpnProfile(KnoxVpnContext knoxVpnContext, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData addAllContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData addAllPackagesToVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData addContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData addPackagesToVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int allowAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int allowNoAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public boolean checkIfCallerIsVpnVendor(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public boolean checkIfLocalProxyPortExists(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public boolean checkIfUidIsExempted(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData createVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getAllContainerPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getAllPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int getChainingEnabledForProfile(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public List<String> getDomainsByProfileName(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public String getInterfaceNameForUid(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int getKnoxVpnProfileType(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int getNotificationDismissibleFlagInternal(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public List<String> getProfilesByDomain(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public String[] getProxyInfoForUid(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getState(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int getUidPidEnabled(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getUserCertificate(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public String getVendorNameForProfile(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData getVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData removeAllContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData removeAllPackagesFromVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData removeContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData removePackagesFromVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData removeVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData setAutoRetryOnConnectionError(KnoxVpnContext knoxVpnContext, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData setCACertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public int setNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData setUserCertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData setVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData startConnection(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public EnterpriseResponseData stopConnection(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public void showToastVpnEULA() throws RemoteException {
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public void removeVpnUidRanges(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
        public void addVpnUidRanges(String str, int i, String str2, String str3, String str4) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IKnoxVpnPolicy {
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
        public static final int TRANSACTION_getNotificationDismissibleFlagInternal = 51;
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
        public static final int TRANSACTION_setNotificationDismissibleFlag = 32;
        public static final int TRANSACTION_setServerCertValidationUserAcceptanceCriteria = 26;
        public static final int TRANSACTION_setUserCertificate = 6;
        public static final int TRANSACTION_setVpnModeOfOperation = 14;
        public static final int TRANSACTION_showToastVpnEULA = 45;
        public static final int TRANSACTION_startConnection = 10;
        public static final int TRANSACTION_stopConnection = 11;

        class Proxy implements IKnoxVpnPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData activateVpnProfile(KnoxVpnContext knoxVpnContext, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData addAllContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData addAllPackagesToVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData addContainerPackagesToVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData addPackagesToVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public void addVpnUidRanges(String str, int i, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int allowAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int allowNoAuthUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public boolean bindKnoxVpnInterface(KnoxVpnContext knoxVpnContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public boolean checkIfCallerIsVpnVendor(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public boolean checkIfLocalProxyPortExists(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public boolean checkIfUidIsExempted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public boolean checkIfVendorCreatedKnoxProfile(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData createVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int disallowUsbTetheringOverVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getAllContainerPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getAllPackagesInVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getAllVpnProfiles(KnoxVpnContext knoxVpnContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getCACertificate(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int getChainingEnabledForProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public List<String> getDomainsByProfileName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getErrorString(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxVpnPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public String getInterfaceNameForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int getKnoxVpnProfileType(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int getNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int getNotificationDismissibleFlagInternal(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public List<String> getProfilesByDomain(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public String[] getProxyInfoForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getState(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int getUidPidEnabled(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getUserCertificate(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public String getVendorNameForProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData getVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public boolean isProxyConfiguredForKnoxVpn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int isUsbTetheringOverVpnEnabled(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData removeAllContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData removeAllPackagesFromVpn(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData removeContainerPackagesFromVpn(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData removePackagesFromVpn(KnoxVpnContext knoxVpnContext, String[] strArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData removeVpnProfile(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public void removeVpnUidRanges(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData setAutoRetryOnConnectionError(KnoxVpnContext knoxVpnContext, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData setCACertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public int setNotificationDismissibleFlag(KnoxVpnContext knoxVpnContext, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData setServerCertValidationUserAcceptanceCriteria(KnoxVpnContext knoxVpnContext, String str, boolean z, List list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData setUserCertificate(KnoxVpnContext knoxVpnContext, String str, byte[] bArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData setVpnModeOfOperation(KnoxVpnContext knoxVpnContext, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public void showToastVpnEULA() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData startConnection(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.IKnoxVpnPolicy
            public EnterpriseResponseData stopConnection(KnoxVpnContext knoxVpnContext, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxVpnContext, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseResponseData) parcelObtain2.readTypedObject(EnterpriseResponseData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxVpnPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxVpnPolicy)) ? new Proxy(iBinder) : (IKnoxVpnPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataCreateVpnProfile = createVpnProfile(knoxVpnContext, string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataCreateVpnProfile, 1);
                    return true;
                case 2:
                    KnoxVpnContext knoxVpnContext2 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData vpnProfile = getVpnProfile(knoxVpnContext2, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnProfile, 1);
                    return true;
                case 3:
                    KnoxVpnContext knoxVpnContext3 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataRemoveVpnProfile = removeVpnProfile(knoxVpnContext3, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataRemoveVpnProfile, 1);
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
                    String string4 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataActivateVpnProfile = activateVpnProfile(knoxVpnContext5, string4, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataActivateVpnProfile, 1);
                    return true;
                case 6:
                    KnoxVpnContext knoxVpnContext6 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string5 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData userCertificate = setUserCertificate(knoxVpnContext6, string5, bArrCreateByteArray, string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCertificate, 1);
                    return true;
                case 7:
                    KnoxVpnContext knoxVpnContext7 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData userCertificate2 = getUserCertificate(knoxVpnContext7, string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCertificate2, 1);
                    return true;
                case 8:
                    KnoxVpnContext knoxVpnContext8 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string8 = parcel.readString();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData cACertificate = setCACertificate(knoxVpnContext8, string8, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cACertificate, 1);
                    return true;
                case 9:
                    KnoxVpnContext knoxVpnContext9 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData cACertificate2 = getCACertificate(knoxVpnContext9, string9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cACertificate2, 1);
                    return true;
                case 10:
                    KnoxVpnContext knoxVpnContext10 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataStartConnection = startConnection(knoxVpnContext10, string10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataStartConnection, 1);
                    return true;
                case 11:
                    KnoxVpnContext knoxVpnContext11 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataStopConnection = stopConnection(knoxVpnContext11, string11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataStopConnection, 1);
                    return true;
                case 12:
                    KnoxVpnContext knoxVpnContext12 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData state = getState(knoxVpnContext12, string12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 13:
                    KnoxVpnContext knoxVpnContext13 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData errorString = getErrorString(knoxVpnContext13, string13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(errorString, 1);
                    return true;
                case 14:
                    KnoxVpnContext knoxVpnContext14 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string14 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData vpnModeOfOperation = setVpnModeOfOperation(knoxVpnContext14, string14, i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnModeOfOperation, 1);
                    return true;
                case 15:
                    KnoxVpnContext knoxVpnContext15 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData vpnModeOfOperation2 = getVpnModeOfOperation(knoxVpnContext15, string15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnModeOfOperation2, 1);
                    return true;
                case 16:
                    KnoxVpnContext knoxVpnContext16 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataAddPackagesToVpn = addPackagesToVpn(knoxVpnContext16, strArrCreateStringArray, string16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataAddPackagesToVpn, 1);
                    return true;
                case 17:
                    KnoxVpnContext knoxVpnContext17 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataRemovePackagesFromVpn = removePackagesFromVpn(knoxVpnContext17, strArrCreateStringArray2, string17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataRemovePackagesFromVpn, 1);
                    return true;
                case 18:
                    KnoxVpnContext knoxVpnContext18 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData allPackagesInVpnProfile = getAllPackagesInVpnProfile(knoxVpnContext18, string18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPackagesInVpnProfile, 1);
                    return true;
                case 19:
                    KnoxVpnContext knoxVpnContext19 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataAddAllPackagesToVpn = addAllPackagesToVpn(knoxVpnContext19, string19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataAddAllPackagesToVpn, 1);
                    return true;
                case 20:
                    KnoxVpnContext knoxVpnContext20 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataRemoveAllPackagesFromVpn = removeAllPackagesFromVpn(knoxVpnContext20, string20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataRemoveAllPackagesFromVpn, 1);
                    return true;
                case 21:
                    KnoxVpnContext knoxVpnContext21 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int i4 = parcel.readInt();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataAddContainerPackagesToVpn = addContainerPackagesToVpn(knoxVpnContext21, i4, strArrCreateStringArray3, string21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataAddContainerPackagesToVpn, 1);
                    return true;
                case 22:
                    KnoxVpnContext knoxVpnContext22 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int i5 = parcel.readInt();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataRemoveContainerPackagesFromVpn = removeContainerPackagesFromVpn(knoxVpnContext22, i5, strArrCreateStringArray4, string22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataRemoveContainerPackagesFromVpn, 1);
                    return true;
                case 23:
                    KnoxVpnContext knoxVpnContext23 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int i6 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData allContainerPackagesInVpnProfile = getAllContainerPackagesInVpnProfile(knoxVpnContext23, i6, string23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allContainerPackagesInVpnProfile, 1);
                    return true;
                case 24:
                    KnoxVpnContext knoxVpnContext24 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int i7 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataAddAllContainerPackagesToVpn = addAllContainerPackagesToVpn(knoxVpnContext24, i7, string24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataAddAllContainerPackagesToVpn, 1);
                    return true;
                case 25:
                    KnoxVpnContext knoxVpnContext25 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int i8 = parcel.readInt();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData enterpriseResponseDataRemoveAllContainerPackagesFromVpn = removeAllContainerPackagesFromVpn(knoxVpnContext25, i8, string25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enterpriseResponseDataRemoveAllContainerPackagesFromVpn, 1);
                    return true;
                case 26:
                    KnoxVpnContext knoxVpnContext26 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string26 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData serverCertValidationUserAcceptanceCriteria = setServerCertValidationUserAcceptanceCriteria(knoxVpnContext26, string26, z2, arrayList, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serverCertValidationUserAcceptanceCriteria, 1);
                    return true;
                case 27:
                    KnoxVpnContext knoxVpnContext27 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string27 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    EnterpriseResponseData autoRetryOnConnectionError = setAutoRetryOnConnectionError(knoxVpnContext27, string27, z3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(autoRetryOnConnectionError, 1);
                    return true;
                case 28:
                    KnoxVpnContext knoxVpnContext28 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAllowNoAuthUsbTetheringOverVpn = allowNoAuthUsbTetheringOverVpn(knoxVpnContext28, string28);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowNoAuthUsbTetheringOverVpn);
                    return true;
                case 29:
                    KnoxVpnContext knoxVpnContext29 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string29 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAllowAuthUsbTetheringOverVpn = allowAuthUsbTetheringOverVpn(knoxVpnContext29, string29, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowAuthUsbTetheringOverVpn);
                    return true;
                case 30:
                    KnoxVpnContext knoxVpnContext30 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iDisallowUsbTetheringOverVpn = disallowUsbTetheringOverVpn(knoxVpnContext30, string30);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisallowUsbTetheringOverVpn);
                    return true;
                case 31:
                    KnoxVpnContext knoxVpnContext31 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iIsUsbTetheringOverVpnEnabled = isUsbTetheringOverVpnEnabled(knoxVpnContext31, string31);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsUsbTetheringOverVpnEnabled);
                    return true;
                case 32:
                    KnoxVpnContext knoxVpnContext32 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    String string32 = parcel.readString();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notificationDismissibleFlag = setNotificationDismissibleFlag(knoxVpnContext32, string32, i10, i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationDismissibleFlag);
                    return true;
                case 33:
                    KnoxVpnContext knoxVpnContext33 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notificationDismissibleFlag2 = getNotificationDismissibleFlag(knoxVpnContext33, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationDismissibleFlag2);
                    return true;
                case 34:
                    KnoxVpnContext knoxVpnContext34 = (KnoxVpnContext) parcel.readTypedObject(KnoxVpnContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zBindKnoxVpnInterface = bindKnoxVpnInterface(knoxVpnContext34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBindKnoxVpnInterface);
                    return true;
                case 35:
                    int i13 = parcel.readInt();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidPidEnabled = getUidPidEnabled(i13, string33);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidPidEnabled);
                    return true;
                case 36:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int chainingEnabledForProfile = getChainingEnabledForProfile(i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(chainingEnabledForProfile);
                    return true;
                case 37:
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int knoxVpnProfileType = getKnoxVpnProfileType(string34);
                    parcel2.writeNoException();
                    parcel2.writeInt(knoxVpnProfileType);
                    return true;
                case 38:
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> domainsByProfileName = getDomainsByProfileName(string35);
                    parcel2.writeNoException();
                    parcel2.writeStringList(domainsByProfileName);
                    return true;
                case 39:
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> profilesByDomain = getProfilesByDomain(string36);
                    parcel2.writeNoException();
                    parcel2.writeStringList(profilesByDomain);
                    return true;
                case 40:
                    String string37 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckIfVendorCreatedKnoxProfile = checkIfVendorCreatedKnoxProfile(string37, i15, i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckIfVendorCreatedKnoxProfile);
                    return true;
                case 41:
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String vendorNameForProfile = getVendorNameForProfile(string38);
                    parcel2.writeNoException();
                    parcel2.writeString(vendorNameForProfile);
                    return true;
                case 42:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String interfaceNameForUid = getInterfaceNameForUid(i17);
                    parcel2.writeNoException();
                    parcel2.writeString(interfaceNameForUid);
                    return true;
                case 43:
                    String string39 = parcel.readString();
                    int i18 = parcel.readInt();
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addVpnUidRanges(string39, i18, string40, string41, string42);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeVpnUidRanges(string43);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    showToastVpnEULA();
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckIfUidIsExempted = checkIfUidIsExempted(i19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckIfUidIsExempted);
                    return true;
                case 47:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] proxyInfoForUid = getProxyInfoForUid(i20);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(proxyInfoForUid);
                    return true;
                case 48:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckIfLocalProxyPortExists = checkIfLocalProxyPortExists(i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckIfLocalProxyPortExists);
                    return true;
                case 49:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsProxyConfiguredForKnoxVpn = isProxyConfiguredForKnoxVpn(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProxyConfiguredForKnoxVpn);
                    return true;
                case 50:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckIfCallerIsVpnVendor = checkIfCallerIsVpnVendor(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckIfCallerIsVpnVendor);
                    return true;
                case 51:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notificationDismissibleFlagInternal = getNotificationDismissibleFlagInternal(i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationDismissibleFlagInternal);
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
}
