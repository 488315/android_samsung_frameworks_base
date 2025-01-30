package com.samsung.android.knox.license;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.ILicenseResultCallback;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IEnterpriseLicense extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.license.IEnterpriseLicense";

    void activateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback);

    void activateKnoxLicenseForUMC(ContextInfo contextInfo, String str, String str2);

    void activateLicense(ContextInfo contextInfo, String str, String str2, String str3, ILicenseResultCallback iLicenseResultCallback);

    void activateLicenseForUMC(ContextInfo contextInfo, String str, String str2, String str3);

    void deActivateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback);

    boolean deleteAllApiCallData();

    boolean deleteApiCallData(String str, String str2, Error error);

    boolean deleteApiCallDataByAdmin(String str);

    boolean deleteLicense(String str);

    boolean deleteLicenseByAdmin(String str);

    List<ActivationInfo> getAllLicenseActivationsInfos();

    LicenseInfo[] getAllLicenseInfo();

    Bundle getApiCallData(String str);

    Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str);

    List<String> getELMPermissions(String str);

    String getInstanceId(String str);

    ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str);

    LicenseInfo getLicenseInfo(String str);

    LicenseInfo getLicenseInfoByAdmin(String str);

    RightsObject getRightsObject(String str);

    RightsObject getRightsObjectByAdmin(String str);

    boolean isEulaBypassAllowed(String str);

    boolean isServiceAvailable(String str, String str2);

    void log(ContextInfo contextInfo, String str, boolean z, boolean z2);

    void notifyKlmObservers(String str, LicenseResult licenseResult);

    boolean processKnoxLicenseResponse(String str, String str2, String str3, Error error, int i, int i2, String str4, RightsObject rightsObject, int i3);

    boolean processLicenseActivationResponse(String str, String str2, String str3, String str4, RightsObject rightsObject, Error error, String str5, String str6, int i);

    boolean processLicenseValidationResult(String str, String str2, RightsObject rightsObject, Error error, String str3, String str4, String str5, String str6);

    boolean resetLicense(String str);

    boolean resetLicenseByAdmin(String str);

    void updateAdminPermissions();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IEnterpriseLicense {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean deleteAllApiCallData() {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean deleteApiCallData(String str, String str2, Error error) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean deleteApiCallDataByAdmin(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean deleteLicense(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean deleteLicenseByAdmin(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final List<ActivationInfo> getAllLicenseActivationsInfos() {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final LicenseInfo[] getAllLicenseInfo() {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final Bundle getApiCallData(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final List<String> getELMPermissions(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final String getInstanceId(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final LicenseInfo getLicenseInfo(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final LicenseInfo getLicenseInfoByAdmin(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final RightsObject getRightsObject(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final RightsObject getRightsObjectByAdmin(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean isEulaBypassAllowed(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean isServiceAvailable(String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean processKnoxLicenseResponse(String str, String str2, String str3, Error error, int i, int i2, String str4, RightsObject rightsObject, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean processLicenseActivationResponse(String str, String str2, String str3, String str4, RightsObject rightsObject, Error error, String str5, String str6, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean processLicenseValidationResult(String str, String str2, RightsObject rightsObject, Error error, String str3, String str4, String str5, String str6) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean resetLicense(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final boolean resetLicenseByAdmin(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void updateAdminPermissions() {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void notifyKlmObservers(String str, LicenseResult licenseResult) {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void activateKnoxLicenseForUMC(ContextInfo contextInfo, String str, String str2) {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void activateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void activateLicenseForUMC(ContextInfo contextInfo, String str, String str2, String str3) {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void deActivateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void log(ContextInfo contextInfo, String str, boolean z, boolean z2) {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public final void activateLicense(ContextInfo contextInfo, String str, String str2, String str3, ILicenseResultCallback iLicenseResultCallback) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IEnterpriseLicense {
        public static final int TRANSACTION_activateKnoxLicense = 20;
        public static final int TRANSACTION_activateKnoxLicenseForUMC = 21;
        public static final int TRANSACTION_activateLicense = 10;
        public static final int TRANSACTION_activateLicenseForUMC = 13;
        public static final int TRANSACTION_deActivateKnoxLicense = 22;
        public static final int TRANSACTION_deleteAllApiCallData = 25;
        public static final int TRANSACTION_deleteApiCallData = 4;
        public static final int TRANSACTION_deleteApiCallDataByAdmin = 5;
        public static final int TRANSACTION_deleteLicense = 17;
        public static final int TRANSACTION_deleteLicenseByAdmin = 18;
        public static final int TRANSACTION_getAllLicenseActivationsInfos = 30;
        public static final int TRANSACTION_getAllLicenseInfo = 7;
        public static final int TRANSACTION_getApiCallData = 3;
        public static final int TRANSACTION_getApiCallDataByAdmin = 6;
        public static final int TRANSACTION_getELMPermissions = 23;
        public static final int TRANSACTION_getInstanceId = 24;
        public static final int TRANSACTION_getLicenseActivationInfo = 29;
        public static final int TRANSACTION_getLicenseInfo = 8;
        public static final int TRANSACTION_getLicenseInfoByAdmin = 9;
        public static final int TRANSACTION_getRightsObject = 1;
        public static final int TRANSACTION_getRightsObjectByAdmin = 2;
        public static final int TRANSACTION_isEulaBypassAllowed = 31;
        public static final int TRANSACTION_isServiceAvailable = 26;
        public static final int TRANSACTION_log = 14;
        public static final int TRANSACTION_notifyKlmObservers = 28;
        public static final int TRANSACTION_processKnoxLicenseResponse = 19;
        public static final int TRANSACTION_processLicenseActivationResponse = 11;
        public static final int TRANSACTION_processLicenseValidationResult = 12;
        public static final int TRANSACTION_resetLicense = 15;
        public static final int TRANSACTION_resetLicenseByAdmin = 16;
        public static final int TRANSACTION_updateAdminPermissions = 27;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IEnterpriseLicense {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void activateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void activateKnoxLicenseForUMC(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void activateLicense(ContextInfo contextInfo, String str, String str2, String str3, ILicenseResultCallback iLicenseResultCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void activateLicenseForUMC(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void deActivateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean deleteAllApiCallData() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean deleteApiCallData(String str, String str2, Error error) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(error, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean deleteApiCallDataByAdmin(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean deleteLicense(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean deleteLicenseByAdmin(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final List<ActivationInfo> getAllLicenseActivationsInfos() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final LicenseInfo[] getAllLicenseInfo() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LicenseInfo[]) obtain2.createTypedArray(LicenseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final Bundle getApiCallData(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final List<String> getELMPermissions(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final String getInstanceId(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IEnterpriseLicense.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivationInfo) obtain2.readTypedObject(ActivationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final LicenseInfo getLicenseInfo(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LicenseInfo) obtain2.readTypedObject(LicenseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final LicenseInfo getLicenseInfoByAdmin(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LicenseInfo) obtain2.readTypedObject(LicenseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final RightsObject getRightsObject(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RightsObject) obtain2.readTypedObject(RightsObject.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final RightsObject getRightsObjectByAdmin(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RightsObject) obtain2.readTypedObject(RightsObject.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean isEulaBypassAllowed(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean isServiceAvailable(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void log(ContextInfo contextInfo, String str, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void notifyKlmObservers(String str, LicenseResult licenseResult) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(licenseResult, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean processKnoxLicenseResponse(String str, String str2, String str3, Error error, int i, int i2, String str4, RightsObject rightsObject, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(error, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(rightsObject, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean processLicenseActivationResponse(String str, String str2, String str3, String str4, RightsObject rightsObject, Error error, String str5, String str6, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(rightsObject, 0);
                    obtain.writeTypedObject(error, 0);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean processLicenseValidationResult(String str, String str2, RightsObject rightsObject, Error error, String str3, String str4, String str5, String str6) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(rightsObject, 0);
                    obtain.writeTypedObject(error, 0);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean resetLicense(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final boolean resetLicenseByAdmin(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public final void updateAdminPermissions() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEnterpriseLicense.DESCRIPTOR);
        }

        public static IEnterpriseLicense asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEnterpriseLicense.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEnterpriseLicense)) ? new Proxy(iBinder) : (IEnterpriseLicense) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnterpriseLicense.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnterpriseLicense.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RightsObject rightsObject = getRightsObject(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rightsObject, 1);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RightsObject rightsObjectByAdmin = getRightsObjectByAdmin(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rightsObjectByAdmin, 1);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle apiCallData = getApiCallData(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apiCallData, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    Error error = (Error) parcel.readTypedObject(Error.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean deleteApiCallData = deleteApiCallData(readString4, readString5, error);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteApiCallData);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteApiCallDataByAdmin = deleteApiCallDataByAdmin(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteApiCallDataByAdmin);
                    return true;
                case 6:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle apiCallDataByAdmin = getApiCallDataByAdmin(contextInfo, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apiCallDataByAdmin, 1);
                    return true;
                case 7:
                    LicenseInfo[] allLicenseInfo = getAllLicenseInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allLicenseInfo, 1);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LicenseInfo licenseInfo = getLicenseInfo(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseInfo, 1);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LicenseInfo licenseInfoByAdmin = getLicenseInfoByAdmin(readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseInfoByAdmin, 1);
                    return true;
                case 10:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    ILicenseResultCallback asInterface = ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    activateLicense(contextInfo2, readString10, readString11, readString12, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    RightsObject rightsObject2 = (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    Error error2 = (Error) parcel.readTypedObject(Error.CREATOR);
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean processLicenseActivationResponse = processLicenseActivationResponse(readString13, readString14, readString15, readString16, rightsObject2, error2, readString17, readString18, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processLicenseActivationResponse);
                    return true;
                case 12:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    RightsObject rightsObject3 = (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    Error error3 = (Error) parcel.readTypedObject(Error.CREATOR);
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean processLicenseValidationResult = processLicenseValidationResult(readString19, readString20, rightsObject3, error3, readString21, readString22, readString23, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processLicenseValidationResult);
                    return true;
                case 13:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    activateLicenseForUMC(contextInfo3, readString25, readString26, readString27);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString28 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    log(contextInfo4, readString28, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean resetLicense = resetLicense(readString29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetLicense);
                    return true;
                case 16:
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean resetLicenseByAdmin = resetLicenseByAdmin(readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetLicenseByAdmin);
                    return true;
                case 17:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteLicense = deleteLicense(readString31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteLicense);
                    return true;
                case 18:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteLicenseByAdmin = deleteLicenseByAdmin(readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteLicenseByAdmin);
                    return true;
                case 19:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    Error error4 = (Error) parcel.readTypedObject(Error.CREATOR);
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    String readString36 = parcel.readString();
                    RightsObject rightsObject4 = (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean processKnoxLicenseResponse = processKnoxLicenseResponse(readString33, readString34, readString35, error4, readInt2, readInt3, readString36, rightsObject4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processKnoxLicenseResponse);
                    return true;
                case 20:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    ILicenseResultCallback asInterface2 = ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    activateKnoxLicense(contextInfo5, readString37, readString38, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    activateKnoxLicenseForUMC(contextInfo6, readString39, readString40);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    ILicenseResultCallback asInterface3 = ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deActivateKnoxLicense(contextInfo7, readString41, readString42, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> eLMPermissions = getELMPermissions(readString43);
                    parcel2.writeNoException();
                    parcel2.writeStringList(eLMPermissions);
                    return true;
                case 24:
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String instanceId = getInstanceId(readString44);
                    parcel2.writeNoException();
                    parcel2.writeString(instanceId);
                    return true;
                case 25:
                    boolean deleteAllApiCallData = deleteAllApiCallData();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteAllApiCallData);
                    return true;
                case 26:
                    String readString45 = parcel.readString();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isServiceAvailable = isServiceAvailable(readString45, readString46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceAvailable);
                    return true;
                case 27:
                    updateAdminPermissions();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString47 = parcel.readString();
                    LicenseResult licenseResult = (LicenseResult) parcel.readTypedObject(LicenseResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyKlmObservers(readString47, licenseResult);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ActivationInfo licenseActivationInfo = getLicenseActivationInfo(contextInfo8, readString48);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseActivationInfo, 1);
                    return true;
                case 30:
                    List<ActivationInfo> allLicenseActivationsInfos = getAllLicenseActivationsInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allLicenseActivationsInfos, 1);
                    return true;
                case 31:
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEulaBypassAllowed = isEulaBypassAllowed(readString49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEulaBypassAllowed);
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
