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

    public static class Default implements SemImsService {
        @Override // com.samsung.android.ims.SemImsService
        public String registerImsRegistrationListenerForSlot(SemImsRegiListener listener, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterImsRegistrationListenerForSlot(String token, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerSimMobilityStatusListener(SemSimMobStatusListener listener, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterSimMobilityStatusListener(String token, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void registerDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unregisterDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsRegistration getRegistrationInfoByServiceType(String serviceType, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsRegistration[] getRegistrationInfoByPhoneId(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isServiceAvailable(String service, int rat, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isNonVerifiedMno(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public String getRcsProfileType(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isVoLteAvailable(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isSimMobilityActivated(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void setRttMode(int phoneId, int mode) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void sendTryRegisterByPhoneId(int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public void enableRcsByPhoneId(boolean enable, int phoneId) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isRcsEnabled(boolean needAutoConfigCheck, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public SemImsProfile[] getCurrentProfileForSlot(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isForbiddenByPhoneId(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public ContentValues getConfigValues(String[] fields, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean getBooleanConfig(String key, int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public String registerEpdgListener(ISemEpdgListener listener) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsService
        public void unRegisterEpdgListener(String token) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean isCrossSimCallingRegistered(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.ims.SemImsService
        public boolean hasCrossSimCallingSupport(int phoneId) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, SemImsService.DESCRIPTOR);
        }

        public static SemImsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(SemImsService.DESCRIPTOR);
            if (iin != null && (iin instanceof SemImsService)) {
                return (SemImsService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(SemImsService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(SemImsService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SemImsRegiListener _arg0 = SemImsRegiListener.Stub.asInterface(data.readStrongBinder());
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result = registerImsRegistrationListenerForSlot(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterImsRegistrationListenerForSlot(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    SemSimMobStatusListener _arg03 = SemSimMobStatusListener.Stub.asInterface(data.readStrongBinder());
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result2 = registerSimMobilityStatusListener(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterSimMobilityStatusListener(_arg04, _arg14);
                    reply.writeNoException();
                    return true;
                case 5:
                    SemImsDmConfigListener _arg05 = SemImsDmConfigListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerDmValueListener(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    SemImsDmConfigListener _arg06 = SemImsDmConfigListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterDmValueListener(_arg06);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    SemImsRegistration _result3 = getRegistrationInfoByServiceType(_arg07, _arg15);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    SemImsRegistration[] _result4 = getRegistrationInfoByPhoneId(_arg08);
                    reply.writeNoException();
                    reply.writeTypedArray(_result4, 1);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    int _arg16 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = isServiceAvailable(_arg09, _arg16, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = isNonVerifiedMno(_arg010);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result7 = getRcsProfileType(_arg011);
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = isVoLteAvailable(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = isSimMobilityActivated(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    setRttMode(_arg014, _arg17);
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTryRegisterByPhoneId(_arg015);
                    reply.writeNoException();
                    return true;
                case 16:
                    boolean _arg016 = data.readBoolean();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    enableRcsByPhoneId(_arg016, _arg18);
                    reply.writeNoException();
                    return true;
                case 17:
                    boolean _arg017 = data.readBoolean();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = isRcsEnabled(_arg017, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    SemImsProfile[] _result11 = getCurrentProfileForSlot(_arg018);
                    reply.writeNoException();
                    reply.writeTypedArray(_result11, 1);
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result12 = isForbiddenByPhoneId(_arg019);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 20:
                    String[] _arg020 = data.createStringArray();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    ContentValues _result13 = getConfigValues(_arg020, _arg110);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 21:
                    String _arg021 = data.readString();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result14 = getBooleanConfig(_arg021, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 22:
                    ISemEpdgListener _arg022 = ISemEpdgListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    String _result15 = registerEpdgListener(_arg022);
                    reply.writeNoException();
                    reply.writeString(_result15);
                    return true;
                case 23:
                    String _arg023 = data.readString();
                    data.enforceNoDataAvail();
                    unRegisterEpdgListener(_arg023);
                    reply.writeNoException();
                    return true;
                case 24:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result16 = isCrossSimCallingRegistered(_arg024);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 25:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result17 = hasCrossSimCallingSupport(_arg025);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements SemImsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerImsRegistrationListenerForSlot(SemImsRegiListener listener, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterImsRegistrationListenerForSlot(String token, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerSimMobilityStatusListener(SemSimMobStatusListener listener, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterSimMobilityStatusListener(String token, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void registerDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unregisterDmValueListener(SemImsDmConfigListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration getRegistrationInfoByServiceType(String serviceType, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(serviceType);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    SemImsRegistration _result = (SemImsRegistration) _reply.readTypedObject(SemImsRegistration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsRegistration[] getRegistrationInfoByPhoneId(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    SemImsRegistration[] _result = (SemImsRegistration[]) _reply.createTypedArray(SemImsRegistration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isServiceAvailable(String service, int rat, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(service);
                    _data.writeInt(rat);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isNonVerifiedMno(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String getRcsProfileType(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isVoLteAvailable(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isSimMobilityActivated(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void setRttMode(int phoneId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(mode);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void sendTryRegisterByPhoneId(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void enableRcsByPhoneId(boolean enable, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isRcsEnabled(boolean needAutoConfigCheck, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeBoolean(needAutoConfigCheck);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public SemImsProfile[] getCurrentProfileForSlot(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    SemImsProfile[] _result = (SemImsProfile[]) _reply.createTypedArray(SemImsProfile.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isForbiddenByPhoneId(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public ContentValues getConfigValues(String[] fields, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStringArray(fields);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    ContentValues _result = (ContentValues) _reply.readTypedObject(ContentValues.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean getBooleanConfig(String key, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public String registerEpdgListener(ISemEpdgListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public void unRegisterEpdgListener(String token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeString(token);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean isCrossSimCallingRegistered(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsService
            public boolean hasCrossSimCallingSupport(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }
    }
}
