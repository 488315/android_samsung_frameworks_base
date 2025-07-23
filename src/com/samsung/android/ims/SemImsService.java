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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(SemImsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof SemImsService)) {
                return (SemImsService) queryLocalInterface;
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
                    SemImsRegiListener asInterface = SemImsRegiListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerImsRegistrationListenerForSlot = registerImsRegistrationListenerForSlot(asInterface, readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(registerImsRegistrationListenerForSlot);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListenerForSlot(readString, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    SemSimMobStatusListener asInterface2 = SemSimMobStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerSimMobilityStatusListener = registerSimMobilityStatusListener(asInterface2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(registerSimMobilityStatusListener);
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSimMobilityStatusListener(readString2, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    SemImsDmConfigListener asInterface3 = SemImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDmValueListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SemImsDmConfigListener asInterface4 = SemImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDmValueListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString3 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemImsRegistration registrationInfoByServiceType = getRegistrationInfoByServiceType(readString3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registrationInfoByServiceType, 1);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemImsRegistration[] registrationInfoByPhoneId = getRegistrationInfoByPhoneId(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfoByPhoneId, 1);
                    return true;
                case 9:
                    String readString4 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isServiceAvailable = isServiceAvailable(readString4, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceAvailable);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNonVerifiedMno = isNonVerifiedMno(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNonVerifiedMno);
                    return true;
                case 11:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String rcsProfileType = getRcsProfileType(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeString(rcsProfileType);
                    return true;
                case 12:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVoLteAvailable = isVoLteAvailable(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoLteAvailable);
                    return true;
                case 13:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSimMobilityActivated = isSimMobilityActivated(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSimMobilityActivated);
                    return true;
                case 14:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRttMode(readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterByPhoneId(readInt15);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableRcsByPhoneId(readBoolean, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRcsEnabled = isRcsEnabled(readBoolean2, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRcsEnabled);
                    return true;
                case 18:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemImsProfile[] currentProfileForSlot = getCurrentProfileForSlot(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentProfileForSlot, 1);
                    return true;
                case 19:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isForbiddenByPhoneId = isForbiddenByPhoneId(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isForbiddenByPhoneId);
                    return true;
                case 20:
                    String[] createStringArray = parcel.createStringArray();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ContentValues configValues = getConfigValues(createStringArray, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configValues, 1);
                    return true;
                case 21:
                    String readString5 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean booleanConfig = getBooleanConfig(readString5, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(booleanConfig);
                    return true;
                case 22:
                    ISemEpdgListener asInterface5 = ISemEpdgListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String registerEpdgListener = registerEpdgListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeString(registerEpdgListener);
                    return true;
                case 23:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unRegisterEpdgListener(readString6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCrossSimCallingRegistered = isCrossSimCallingRegistered(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCrossSimCallingRegistered);
                    return true;
                case 25:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasCrossSimCallingSupport = hasCrossSimCallingSupport(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCrossSimCallingSupport);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(semImsRegiListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerSimMobilityStatusListener(SemSimMobStatusListener semSimMobStatusListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(semSimMobStatusListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterSimMobilityStatusListener(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void registerDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(semImsDmConfigListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterDmValueListener(SemImsDmConfigListener semImsDmConfigListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(semImsDmConfigListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemImsRegistration) obtain2.readTypedObject(SemImsRegistration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemImsRegistration[]) obtain2.createTypedArray(SemImsRegistration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isServiceAvailable(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isNonVerifiedMno(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String getRcsProfileType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isVoLteAvailable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isSimMobilityActivated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void setRttMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendTryRegisterByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void enableRcsByPhoneId(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isRcsEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemImsProfile[]) obtain2.createTypedArray(SemImsProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isForbiddenByPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public ContentValues getConfigValues(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContentValues) obtain2.readTypedObject(ContentValues.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean getBooleanConfig(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerEpdgListener(ISemEpdgListener iSemEpdgListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemEpdgListener);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unRegisterEpdgListener(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isCrossSimCallingRegistered(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean hasCrossSimCallingSupport(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
