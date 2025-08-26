package com.samsung.android.knox.license;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.ILicenseResultCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IEnterpriseLicense extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.license.IEnterpriseLicense";

    void activateKnoxLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException;

    void activateLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException;

    void deActivateKnoxLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException;

    boolean deleteAllApiCallData() throws RemoteException;

    boolean deleteApiCallData(String str, String str2, Error error) throws RemoteException;

    boolean deleteApiCallDataByAdmin(String str) throws RemoteException;

    boolean deleteLicense(String str) throws RemoteException;

    boolean deleteLicenseByAdmin(String str) throws RemoteException;

    List<ActivationInfo> getAllLicenseActivationsInfos() throws RemoteException;

    LicenseInfo[] getAllLicenseInfo() throws RemoteException;

    Bundle getApiCallData(String str) throws RemoteException;

    Bundle getApiCallDataByAdmin(String str) throws RemoteException;

    List<String> getELMPermissions(String str) throws RemoteException;

    String getInstanceId(String str) throws RemoteException;

    ActivationInfo getLicenseActivationInfo(String str) throws RemoteException;

    LicenseInfo getLicenseInfo(String str) throws RemoteException;

    LicenseInfo getLicenseInfoByAdmin(String str) throws RemoteException;

    RightsObject getRightsObject(String str) throws RemoteException;

    RightsObject getRightsObjectByAdmin(String str) throws RemoteException;

    boolean isEulaBypassAllowed(String str) throws RemoteException;

    boolean isServiceAvailable(String str, String str2) throws RemoteException;

    void log(ContextInfo contextInfo, String str, boolean z, boolean z2) throws RemoteException;

    void notifyKlmObservers(String str, LicenseResult licenseResult) throws RemoteException;

    boolean processKnoxLicenseResponse(String str, String str2, String str3, List<String> list, String str4, Error error, int i, String str5, RightsObject rightsObject) throws RemoteException;

    boolean processLicenseActivationResponse(String str, String str2, String str3, List<String> list, String str4, String str5, RightsObject rightsObject, Error error, String str6, String str7) throws RemoteException;

    boolean processLicenseValidationResult(String str, RightsObject rightsObject, Error error, String str2, String str3, String str4, List<String> list) throws RemoteException;

    boolean resetLicense(String str) throws RemoteException;

    boolean resetLicenseByAdmin(String str) throws RemoteException;

    void updateAdminPermissions() throws RemoteException;

    public class Default implements IEnterpriseLicense {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteAllApiCallData() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteApiCallData(String str, String str2, Error error) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteApiCallDataByAdmin(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteLicense(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteLicenseByAdmin(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public List<ActivationInfo> getAllLicenseActivationsInfos() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public LicenseInfo[] getAllLicenseInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public Bundle getApiCallData(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public Bundle getApiCallDataByAdmin(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public List<String> getELMPermissions(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public String getInstanceId(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public ActivationInfo getLicenseActivationInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public LicenseInfo getLicenseInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public LicenseInfo getLicenseInfoByAdmin(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public RightsObject getRightsObject(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public RightsObject getRightsObjectByAdmin(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean isEulaBypassAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean isServiceAvailable(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean processKnoxLicenseResponse(String str, String str2, String str3, List<String> list, String str4, Error error, int i, String str5, RightsObject rightsObject) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean processLicenseActivationResponse(String str, String str2, String str3, List<String> list, String str4, String str5, RightsObject rightsObject, Error error, String str6, String str7) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean processLicenseValidationResult(String str, RightsObject rightsObject, Error error, String str2, String str3, String str4, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean resetLicense(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean resetLicenseByAdmin(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void updateAdminPermissions() throws RemoteException {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void notifyKlmObservers(String str, LicenseResult licenseResult) throws RemoteException {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void activateKnoxLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void activateLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void deActivateKnoxLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void log(ContextInfo contextInfo, String str, boolean z, boolean z2) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IEnterpriseLicense {
        static final int TRANSACTION_activateKnoxLicense = 19;
        static final int TRANSACTION_activateLicense = 10;
        static final int TRANSACTION_deActivateKnoxLicense = 20;
        static final int TRANSACTION_deleteAllApiCallData = 23;
        static final int TRANSACTION_deleteApiCallData = 4;
        static final int TRANSACTION_deleteApiCallDataByAdmin = 5;
        static final int TRANSACTION_deleteLicense = 16;
        static final int TRANSACTION_deleteLicenseByAdmin = 17;
        static final int TRANSACTION_getAllLicenseActivationsInfos = 27;
        static final int TRANSACTION_getAllLicenseInfo = 7;
        static final int TRANSACTION_getApiCallData = 3;
        static final int TRANSACTION_getApiCallDataByAdmin = 6;
        static final int TRANSACTION_getELMPermissions = 21;
        static final int TRANSACTION_getInstanceId = 22;
        static final int TRANSACTION_getLicenseActivationInfo = 26;
        static final int TRANSACTION_getLicenseInfo = 8;
        static final int TRANSACTION_getLicenseInfoByAdmin = 9;
        static final int TRANSACTION_getRightsObject = 1;
        static final int TRANSACTION_getRightsObjectByAdmin = 2;
        static final int TRANSACTION_isEulaBypassAllowed = 28;
        static final int TRANSACTION_isServiceAvailable = 24;
        static final int TRANSACTION_log = 13;
        static final int TRANSACTION_notifyKlmObservers = 25;
        static final int TRANSACTION_processKnoxLicenseResponse = 18;
        static final int TRANSACTION_processLicenseActivationResponse = 11;
        static final int TRANSACTION_processLicenseValidationResult = 12;
        static final int TRANSACTION_resetLicense = 14;
        static final int TRANSACTION_resetLicenseByAdmin = 15;
        static final int TRANSACTION_updateAdminPermissions = 29;

        class Proxy implements IEnterpriseLicense {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void activateKnoxLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void activateLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void deActivateKnoxLicense(String str, String str2, ILicenseResultCallback iLicenseResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteAllApiCallData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteApiCallData(String str, String str2, Error error) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(error, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteApiCallDataByAdmin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteLicense(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteLicenseByAdmin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public List<ActivationInfo> getAllLicenseActivationsInfos() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public LicenseInfo[] getAllLicenseInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LicenseInfo[]) parcelObtain2.createTypedArray(LicenseInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public Bundle getApiCallData(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public Bundle getApiCallDataByAdmin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public List<String> getELMPermissions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public String getInstanceId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEnterpriseLicense.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public ActivationInfo getLicenseActivationInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivationInfo) parcelObtain2.readTypedObject(ActivationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public LicenseInfo getLicenseInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LicenseInfo) parcelObtain2.readTypedObject(LicenseInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public LicenseInfo getLicenseInfoByAdmin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LicenseInfo) parcelObtain2.readTypedObject(LicenseInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public RightsObject getRightsObject(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RightsObject) parcelObtain2.readTypedObject(RightsObject.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public RightsObject getRightsObjectByAdmin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RightsObject) parcelObtain2.readTypedObject(RightsObject.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean isEulaBypassAllowed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean isServiceAvailable(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void log(ContextInfo contextInfo, String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void notifyKlmObservers(String str, LicenseResult licenseResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(licenseResult, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean processKnoxLicenseResponse(String str, String str2, String str3, List<String> list, String str4, Error error, int i, String str5, RightsObject rightsObject) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(error, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeTypedObject(rightsObject, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean processLicenseActivationResponse(String str, String str2, String str3, List<String> list, String str4, String str5, RightsObject rightsObject, Error error, String str6, String str7) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeTypedObject(rightsObject, 0);
                    parcelObtain.writeTypedObject(error, 0);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean processLicenseValidationResult(String str, RightsObject rightsObject, Error error, String str2, String str3, String str4, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(rightsObject, 0);
                    parcelObtain.writeTypedObject(error, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean resetLicense(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean resetLicenseByAdmin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void updateAdminPermissions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEnterpriseLicense.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEnterpriseLicense)) ? new Proxy(iBinder) : (IEnterpriseLicense) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnterpriseLicense.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnterpriseLicense.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RightsObject rightsObject = getRightsObject(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rightsObject, 1);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RightsObject rightsObjectByAdmin = getRightsObjectByAdmin(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rightsObjectByAdmin, 1);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle apiCallData = getApiCallData(string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apiCallData, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    Error error = (Error) parcel.readTypedObject(Error.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDeleteApiCallData = deleteApiCallData(string4, string5, error);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteApiCallData);
                    return true;
                case 5:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteApiCallDataByAdmin = deleteApiCallDataByAdmin(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteApiCallDataByAdmin);
                    return true;
                case 6:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle apiCallDataByAdmin = getApiCallDataByAdmin(string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apiCallDataByAdmin, 1);
                    return true;
                case 7:
                    LicenseInfo[] allLicenseInfo = getAllLicenseInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allLicenseInfo, 1);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LicenseInfo licenseInfo = getLicenseInfo(string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseInfo, 1);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LicenseInfo licenseInfoByAdmin = getLicenseInfoByAdmin(string9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseInfoByAdmin, 1);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    ILicenseResultCallback iLicenseResultCallbackAsInterface = ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    activateLicense(string10, string11, iLicenseResultCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    RightsObject rightsObject2 = (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    Error error2 = (Error) parcel.readTypedObject(Error.CREATOR);
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zProcessLicenseActivationResponse = processLicenseActivationResponse(string12, string13, string14, arrayListCreateStringArrayList, string15, string16, rightsObject2, error2, string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zProcessLicenseActivationResponse);
                    return true;
                case 12:
                    String string19 = parcel.readString();
                    RightsObject rightsObject3 = (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    Error error3 = (Error) parcel.readTypedObject(Error.CREATOR);
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zProcessLicenseValidationResult = processLicenseValidationResult(string19, rightsObject3, error3, string20, string21, string22, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zProcessLicenseValidationResult);
                    return true;
                case 13:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string23 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    log(contextInfo, string23, z, z2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zResetLicense = resetLicense(string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetLicense);
                    return true;
                case 15:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zResetLicenseByAdmin = resetLicenseByAdmin(string25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetLicenseByAdmin);
                    return true;
                case 16:
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteLicense = deleteLicense(string26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteLicense);
                    return true;
                case 17:
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteLicenseByAdmin = deleteLicenseByAdmin(string27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteLicenseByAdmin);
                    return true;
                case 18:
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    String string31 = parcel.readString();
                    Error error4 = (Error) parcel.readTypedObject(Error.CREATOR);
                    int i3 = parcel.readInt();
                    String string32 = parcel.readString();
                    RightsObject rightsObject4 = (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zProcessKnoxLicenseResponse = processKnoxLicenseResponse(string28, string29, string30, arrayListCreateStringArrayList3, string31, error4, i3, string32, rightsObject4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zProcessKnoxLicenseResponse);
                    return true;
                case 19:
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    ILicenseResultCallback iLicenseResultCallbackAsInterface2 = ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    activateKnoxLicense(string33, string34, iLicenseResultCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    ILicenseResultCallback iLicenseResultCallbackAsInterface3 = ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deActivateKnoxLicense(string35, string36, iLicenseResultCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> eLMPermissions = getELMPermissions(string37);
                    parcel2.writeNoException();
                    parcel2.writeStringList(eLMPermissions);
                    return true;
                case 22:
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String instanceId = getInstanceId(string38);
                    parcel2.writeNoException();
                    parcel2.writeString(instanceId);
                    return true;
                case 23:
                    boolean zDeleteAllApiCallData = deleteAllApiCallData();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteAllApiCallData);
                    return true;
                case 24:
                    String string39 = parcel.readString();
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsServiceAvailable = isServiceAvailable(string39, string40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsServiceAvailable);
                    return true;
                case 25:
                    String string41 = parcel.readString();
                    LicenseResult licenseResult = (LicenseResult) parcel.readTypedObject(LicenseResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyKlmObservers(string41, licenseResult);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ActivationInfo licenseActivationInfo = getLicenseActivationInfo(string42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseActivationInfo, 1);
                    return true;
                case 27:
                    List<ActivationInfo> allLicenseActivationsInfos = getAllLicenseActivationsInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allLicenseActivationsInfos, 1);
                    return true;
                case 28:
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsEulaBypassAllowed = isEulaBypassAllowed(string43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEulaBypassAllowed);
                    return true;
                case 29:
                    updateAdminPermissions();
                    parcel2.writeNoException();
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
