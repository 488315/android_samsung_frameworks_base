package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.extension.IImageCaptureExtenderImpl;
import android.hardware.camera2.extension.IInitializeSessionCallback;
import android.hardware.camera2.extension.IPreviewExtenderImpl;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICameraExtensionsProxyService extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.ICameraExtensionsProxyService";

    boolean advancedExtensionsSupported() throws RemoteException;

    IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws RemoteException;

    IImageCaptureExtenderImpl initializeImageExtension(int i) throws RemoteException;

    IPreviewExtenderImpl initializePreviewExtension(int i) throws RemoteException;

    void initializeSession(IInitializeSessionCallback iInitializeSessionCallback) throws RemoteException;

    boolean registerClient(IBinder iBinder) throws RemoteException;

    void releaseSession() throws RemoteException;

    void unregisterClient(IBinder iBinder) throws RemoteException;

    public static class Default implements ICameraExtensionsProxyService {
        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean registerClient(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void unregisterClient(IBinder token) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean advancedExtensionsSupported() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void initializeSession(IInitializeSessionCallback cb) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void releaseSession() throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IPreviewExtenderImpl initializePreviewExtension(int extensionType) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IImageCaptureExtenderImpl initializeImageExtension(int extensionType) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IAdvancedExtenderImpl initializeAdvancedExtension(int extensionType) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICameraExtensionsProxyService {
        static final int TRANSACTION_advancedExtensionsSupported = 3;
        static final int TRANSACTION_initializeAdvancedExtension = 8;
        static final int TRANSACTION_initializeImageExtension = 7;
        static final int TRANSACTION_initializePreviewExtension = 6;
        static final int TRANSACTION_initializeSession = 4;
        static final int TRANSACTION_registerClient = 1;
        static final int TRANSACTION_releaseSession = 5;
        static final int TRANSACTION_unregisterClient = 2;

        public Stub() {
            attachInterface(this, ICameraExtensionsProxyService.DESCRIPTOR);
        }

        public static ICameraExtensionsProxyService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICameraExtensionsProxyService.DESCRIPTOR);
            if (iin != null && (iin instanceof ICameraExtensionsProxyService)) {
                return (ICameraExtensionsProxyService) iin;
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
                    return "registerClient";
                case 2:
                    return "unregisterClient";
                case 3:
                    return "advancedExtensionsSupported";
                case 4:
                    return "initializeSession";
                case 5:
                    return "releaseSession";
                case 6:
                    return "initializePreviewExtension";
                case 7:
                    return "initializeImageExtension";
                case 8:
                    return "initializeAdvancedExtension";
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
                data.enforceInterface(ICameraExtensionsProxyService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICameraExtensionsProxyService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result = registerClient(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unregisterClient(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _result2 = advancedExtensionsSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 4:
                    IInitializeSessionCallback _arg03 = IInitializeSessionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    initializeSession(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    releaseSession();
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    IPreviewExtenderImpl _result3 = initializePreviewExtension(_arg04);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 7:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    IImageCaptureExtenderImpl _result4 = initializeImageExtension(_arg05);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    IAdvancedExtenderImpl _result5 = initializeAdvancedExtension(_arg06);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result5);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICameraExtensionsProxyService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICameraExtensionsProxyService.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public boolean registerClient(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void unregisterClient(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public boolean advancedExtensionsSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void initializeSession(IInitializeSessionCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void releaseSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public IPreviewExtenderImpl initializePreviewExtension(int extensionType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    _data.writeInt(extensionType);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    IPreviewExtenderImpl _result = IPreviewExtenderImpl.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public IImageCaptureExtenderImpl initializeImageExtension(int extensionType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    _data.writeInt(extensionType);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    IImageCaptureExtenderImpl _result = IImageCaptureExtenderImpl.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public IAdvancedExtenderImpl initializeAdvancedExtension(int extensionType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    _data.writeInt(extensionType);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    IAdvancedExtenderImpl _result = IAdvancedExtenderImpl.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
