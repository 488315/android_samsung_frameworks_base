package com.samsung.android.service.HermesService;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHermesService extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.service.HermesService.IHermesService";

    int getFailureCount(int i) throws RemoteException;

    byte[] hermesCosPatchTest(byte[] bArr) throws RemoteException;

    byte[] hermesGetSeId() throws RemoteException;

    byte[] hermesGetSecureHWInfo() throws RemoteException;

    int hermesProvisioning() throws RemoteException;

    int hermesSecureHwPowerOff() throws RemoteException;

    int hermesSecureHwPowerOn() throws RemoteException;

    byte[] hermesSelftest() throws RemoteException;

    byte[] hermesSelftest2(String str) throws RemoteException;

    byte[] hermesSendApdu(byte[] bArr) throws RemoteException;

    int hermesTerminateService() throws RemoteException;

    byte[] hermesUpdateApplet() throws RemoteException;

    byte[] hermesUpdateCryptoFW() throws RemoteException;

    int hermesVerifyProvisioning() throws RemoteException;

    public static class Default implements IHermesService {
        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesSelftest() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesSelftest2(String cmd) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesProvisioning() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesVerifyProvisioning() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesGetSecureHWInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesTerminateService() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesUpdateCryptoFW() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesUpdateApplet() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesSecureHwPowerOn() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesSecureHwPowerOff() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesSendApdu(byte[] apdu) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesCosPatchTest(byte[] script) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesGetSeId() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int getFailureCount(int slotId) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IHermesService {
        static final int TRANSACTION_getFailureCount = 14;
        static final int TRANSACTION_hermesCosPatchTest = 12;
        static final int TRANSACTION_hermesGetSeId = 13;
        static final int TRANSACTION_hermesGetSecureHWInfo = 5;
        static final int TRANSACTION_hermesProvisioning = 3;
        static final int TRANSACTION_hermesSecureHwPowerOff = 10;
        static final int TRANSACTION_hermesSecureHwPowerOn = 9;
        static final int TRANSACTION_hermesSelftest = 1;
        static final int TRANSACTION_hermesSelftest2 = 2;
        static final int TRANSACTION_hermesSendApdu = 11;
        static final int TRANSACTION_hermesTerminateService = 6;
        static final int TRANSACTION_hermesUpdateApplet = 8;
        static final int TRANSACTION_hermesUpdateCryptoFW = 7;
        static final int TRANSACTION_hermesVerifyProvisioning = 4;

        public Stub() {
            attachInterface(this, IHermesService.DESCRIPTOR);
        }

        public static IHermesService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IHermesService.DESCRIPTOR);
            if (iin != null && (iin instanceof IHermesService)) {
                return (IHermesService) iin;
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
                    return "hermesSelftest";
                case 2:
                    return "hermesSelftest2";
                case 3:
                    return "hermesProvisioning";
                case 4:
                    return "hermesVerifyProvisioning";
                case 5:
                    return "hermesGetSecureHWInfo";
                case 6:
                    return "hermesTerminateService";
                case 7:
                    return "hermesUpdateCryptoFW";
                case 8:
                    return "hermesUpdateApplet";
                case 9:
                    return "hermesSecureHwPowerOn";
                case 10:
                    return "hermesSecureHwPowerOff";
                case 11:
                    return "hermesSendApdu";
                case 12:
                    return "hermesCosPatchTest";
                case 13:
                    return "hermesGetSeId";
                case 14:
                    return "getFailureCount";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IHermesService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IHermesService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    byte[] _result = hermesSelftest();
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    byte[] _result2 = hermesSelftest2(_arg0);
                    reply.writeNoException();
                    reply.writeByteArray(_result2);
                    return true;
                case 3:
                    int _result3 = hermesProvisioning();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    int _result4 = hermesVerifyProvisioning();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    byte[] _result5 = hermesGetSecureHWInfo();
                    reply.writeNoException();
                    reply.writeByteArray(_result5);
                    return true;
                case 6:
                    int _result6 = hermesTerminateService();
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 7:
                    byte[] _result7 = hermesUpdateCryptoFW();
                    reply.writeNoException();
                    reply.writeByteArray(_result7);
                    return true;
                case 8:
                    byte[] _result8 = hermesUpdateApplet();
                    reply.writeNoException();
                    reply.writeByteArray(_result8);
                    return true;
                case 9:
                    int _result9 = hermesSecureHwPowerOn();
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 10:
                    int _result10 = hermesSecureHwPowerOff();
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 11:
                    byte[] _arg02 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result11 = hermesSendApdu(_arg02);
                    reply.writeNoException();
                    reply.writeByteArray(_result11);
                    return true;
                case 12:
                    byte[] _arg03 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result12 = hermesCosPatchTest(_arg03);
                    reply.writeNoException();
                    reply.writeByteArray(_result12);
                    return true;
                case 13:
                    byte[] _result13 = hermesGetSeId();
                    reply.writeNoException();
                    reply.writeByteArray(_result13);
                    return true;
                case 14:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result14 = getFailureCount(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IHermesService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHermesService.DESCRIPTOR;
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesSelftest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesSelftest2(String cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    _data.writeString(cmd);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesProvisioning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesVerifyProvisioning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesGetSecureHWInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesTerminateService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesUpdateCryptoFW() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesUpdateApplet() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesSecureHwPowerOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesSecureHwPowerOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesSendApdu(byte[] apdu) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    _data.writeByteArray(apdu);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesCosPatchTest(byte[] script) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    _data.writeByteArray(script);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesGetSeId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int getFailureCount(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    _data.writeInt(slotId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
