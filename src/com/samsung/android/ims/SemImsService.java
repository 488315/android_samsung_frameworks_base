package com.samsung.android.ims;

import android.content.ContentValues;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.enterprise.content.SecContentProviderURI;
import com.samsung.android.ims.ISemEpdgListener;
import com.samsung.android.ims.SemImsDmConfigListener;
import com.samsung.android.ims.SemImsRegiListener;
import com.samsung.android.ims.SemSimMobStatusListener;
import com.samsung.android.ims.settings.SemImsProfile;

/* loaded from: classes6.dex */
public interface SemImsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.SemImsService";

    public static class Default implements SemImsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void enableRcsByPhoneId(boolean z, int i) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean getBooleanConfig(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public ContentValues getConfigValues(String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public String getRcsProfileType(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean hasCrossSimCallingSupport(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isCrossSimCallingRegistered(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isForbiddenByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isNonVerifiedMno(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isRcsEnabled(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isServiceAvailable(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isSimMobilityActivated(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isVoLteAvailable(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void registerDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerEpdgListener(ISemEpdgListener iSemEpdgListener) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerImsRegistrationListenerForSlot(SemImsRegiListener semImsRegiListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerSimMobilityStatusListener(SemSimMobStatusListener semSimMobStatusListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void sendTryRegisterByPhoneId(int i) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void setRttMode(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unRegisterEpdgListener(String str) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterSimMobilityStatusListener(String str, int i) throws RemoteException {
        }
    }

    void enableRcsByPhoneId(boolean z, int i) throws RemoteException;

    boolean getBooleanConfig(String str, int i) throws RemoteException;

    ContentValues getConfigValues(String[] strArr, int i) throws RemoteException;

    SemImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException;

    String getRcsProfileType(int i) throws RemoteException;

    SemImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException;

    SemImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException;

    boolean hasCrossSimCallingSupport(int i) throws RemoteException;

    boolean isCrossSimCallingRegistered(int i) throws RemoteException;

    boolean isForbiddenByPhoneId(int i) throws RemoteException;

    boolean isNonVerifiedMno(int i) throws RemoteException;

    boolean isRcsEnabled(boolean z, int i) throws RemoteException;

    boolean isServiceAvailable(String str, int i, int i2) throws RemoteException;

    boolean isSimMobilityActivated(int i) throws RemoteException;

    boolean isVoLteAvailable(int i) throws RemoteException;

    void registerDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException;

    String registerEpdgListener(ISemEpdgListener iSemEpdgListener) throws RemoteException;

    String registerImsRegistrationListenerForSlot(SemImsRegiListener semImsRegiListener, int i) throws RemoteException;

    String registerSimMobilityStatusListener(SemSimMobStatusListener semSimMobStatusListener, int i) throws RemoteException;

    void sendTryRegisterByPhoneId(int i) throws RemoteException;

    void setRttMode(int i, int i2) throws RemoteException;

    void unRegisterEpdgListener(String str) throws RemoteException;

    void unregisterDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException;

    void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException;

    void unregisterSimMobilityStatusListener(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements SemImsService {
        static final int TRANSACTION_enableRcsByPhoneId = 16;
        static final int TRANSACTION_getBooleanConfig = 21;
        static final int TRANSACTION_getConfigValues = 20;
        static final int TRANSACTION_getCurrentProfileForSlot = 18;
        static final int TRANSACTION_getRcsProfileType = 11;
        static final int TRANSACTION_getRegistrationInfoByPhoneId = 8;
        static final int TRANSACTION_getRegistrationInfoByServiceType = 7;
        static final int TRANSACTION_hasCrossSimCallingSupport = 25;
        static final int TRANSACTION_isCrossSimCallingRegistered = 24;
        static final int TRANSACTION_isForbiddenByPhoneId = 19;
        static final int TRANSACTION_isNonVerifiedMno = 10;
        static final int TRANSACTION_isRcsEnabled = 17;
        static final int TRANSACTION_isServiceAvailable = 9;
        static final int TRANSACTION_isSimMobilityActivated = 13;
        static final int TRANSACTION_isVoLteAvailable = 12;
        static final int TRANSACTION_registerDmValueListener = 5;
        static final int TRANSACTION_registerEpdgListener = 22;
        static final int TRANSACTION_registerImsRegistrationListenerForSlot = 1;
        static final int TRANSACTION_registerSimMobilityStatusListener = 3;
        static final int TRANSACTION_sendTryRegisterByPhoneId = 15;
        static final int TRANSACTION_setRttMode = 14;
        static final int TRANSACTION_unRegisterEpdgListener = 23;
        static final int TRANSACTION_unregisterDmValueListener = 6;
        static final int TRANSACTION_unregisterImsRegistrationListenerForSlot = 2;
        static final int TRANSACTION_unregisterSimMobilityStatusListener = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }

        public Stub() {
            attachInterface(this, SemImsService.DESCRIPTOR);
        }

        public static SemImsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(SemImsService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof SemImsService)) {
                return (SemImsService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerImsRegistrationListenerForSlot";
                case 2:
                    return "unregisterImsRegistrationListenerForSlot";
                case 3:
                    return "registerSimMobilityStatusListener";
                case 4:
                    return "unregisterSimMobilityStatusListener";
                case 5:
                    return "registerDmValueListener";
                case 6:
                    return "unregisterDmValueListener";
                case 7:
                    return "getRegistrationInfoByServiceType";
                case 8:
                    return "getRegistrationInfoByPhoneId";
                case 9:
                    return SecContentProviderURI.ENTERPRISELICENSEPOLICY_ISSERVICEAVAILABLE_METHOD;
                case 10:
                    return "isNonVerifiedMno";
                case 11:
                    return "getRcsProfileType";
                case 12:
                    return "isVoLteAvailable";
                case 13:
                    return "isSimMobilityActivated";
                case 14:
                    return "setRttMode";
                case 15:
                    return "sendTryRegisterByPhoneId";
                case 16:
                    return "enableRcsByPhoneId";
                case 17:
                    return "isRcsEnabled";
                case 18:
                    return "getCurrentProfileForSlot";
                case 19:
                    return "isForbiddenByPhoneId";
                case 20:
                    return "getConfigValues";
                case 21:
                    return "getBooleanConfig";
                case 22:
                    return "registerEpdgListener";
                case 23:
                    return "unRegisterEpdgListener";
                case 24:
                    return "isCrossSimCallingRegistered";
                case 25:
                    return "hasCrossSimCallingSupport";
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
                parcel.enforceInterface(SemImsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemImsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SemImsRegiListener semImsRegiListenerAsInterface = SemImsRegiListener.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterImsRegistrationListenerForSlot = registerImsRegistrationListenerForSlot(semImsRegiListenerAsInterface, i3);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterImsRegistrationListenerForSlot);
                    return true;
                case 2:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListenerForSlot(string, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    SemSimMobStatusListener semSimMobStatusListenerAsInterface = SemSimMobStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterSimMobilityStatusListener = registerSimMobilityStatusListener(semSimMobStatusListenerAsInterface, i5);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterSimMobilityStatusListener);
                    return true;
                case 4:
                    String string2 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSimMobilityStatusListener(string2, i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    SemImsDmConfigListener semImsDmConfigListenerAsInterface = SemImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDmValueListener(semImsDmConfigListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SemImsDmConfigListener semImsDmConfigListenerAsInterface2 = SemImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDmValueListener(semImsDmConfigListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string3 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemImsRegistration registrationInfoByServiceType = getRegistrationInfoByServiceType(string3, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registrationInfoByServiceType, 1);
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemImsRegistration[] registrationInfoByPhoneId = getRegistrationInfoByPhoneId(i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfoByPhoneId, 1);
                    return true;
                case 9:
                    String string4 = parcel.readString();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsServiceAvailable = isServiceAvailable(string4, i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsServiceAvailable);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNonVerifiedMno = isNonVerifiedMno(i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNonVerifiedMno);
                    return true;
                case 11:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String rcsProfileType = getRcsProfileType(i12);
                    parcel2.writeNoException();
                    parcel2.writeString(rcsProfileType);
                    return true;
                case 12:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVoLteAvailable = isVoLteAvailable(i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoLteAvailable);
                    return true;
                case 13:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSimMobilityActivated = isSimMobilityActivated(i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSimMobilityActivated);
                    return true;
                case 14:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRttMode(i15, i16);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterByPhoneId(i17);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z = parcel.readBoolean();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableRcsByPhoneId(z, i18);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean z2 = parcel.readBoolean();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRcsEnabled = isRcsEnabled(z2, i19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRcsEnabled);
                    return true;
                case 18:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemImsProfile[] currentProfileForSlot = getCurrentProfileForSlot(i20);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentProfileForSlot, 1);
                    return true;
                case 19:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsForbiddenByPhoneId = isForbiddenByPhoneId(i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsForbiddenByPhoneId);
                    return true;
                case 20:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ContentValues configValues = getConfigValues(strArrCreateStringArray, i22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configValues, 1);
                    return true;
                case 21:
                    String string5 = parcel.readString();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean booleanConfig = getBooleanConfig(string5, i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(booleanConfig);
                    return true;
                case 22:
                    ISemEpdgListener iSemEpdgListenerAsInterface = ISemEpdgListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterEpdgListener = registerEpdgListener(iSemEpdgListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterEpdgListener);
                    return true;
                case 23:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unRegisterEpdgListener(string6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCrossSimCallingRegistered = isCrossSimCallingRegistered(i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCrossSimCallingRegistered);
                    return true;
                case 25:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasCrossSimCallingSupport = hasCrossSimCallingSupport(i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCrossSimCallingSupport);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements SemImsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerImsRegistrationListenerForSlot(SemImsRegiListener semImsRegiListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(semImsRegiListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerSimMobilityStatusListener(SemSimMobStatusListener semSimMobStatusListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(semSimMobStatusListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterSimMobilityStatusListener(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void registerDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(semImsDmConfigListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(semImsDmConfigListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemImsRegistration) parcelObtain2.readTypedObject(SemImsRegistration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemImsRegistration[]) parcelObtain2.createTypedArray(SemImsRegistration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isServiceAvailable(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isNonVerifiedMno(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String getRcsProfileType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isVoLteAvailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isSimMobilityActivated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void setRttMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendTryRegisterByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void enableRcsByPhoneId(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isRcsEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemImsProfile[]) parcelObtain2.createTypedArray(SemImsProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isForbiddenByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public ContentValues getConfigValues(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContentValues) parcelObtain2.readTypedObject(ContentValues.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean getBooleanConfig(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerEpdgListener(ISemEpdgListener iSemEpdgListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemEpdgListener);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unRegisterEpdgListener(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isCrossSimCallingRegistered(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean hasCrossSimCallingSupport(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
