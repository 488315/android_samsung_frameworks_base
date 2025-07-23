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

    public static class Default implements ICameraExtensionsProxyService {
        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean advancedExtensionsSupported() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IImageCaptureExtenderImpl initializeImageExtension(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IPreviewExtenderImpl initializePreviewExtension(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void initializeSession(IInitializeSessionCallback iInitializeSessionCallback) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean registerClient(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void releaseSession() throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void unregisterClient(IBinder iBinder) throws RemoteException {
        }
    }

    boolean advancedExtensionsSupported() throws RemoteException;

    IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws RemoteException;

    IImageCaptureExtenderImpl initializeImageExtension(int i) throws RemoteException;

    IPreviewExtenderImpl initializePreviewExtension(int i) throws RemoteException;

    void initializeSession(IInitializeSessionCallback iInitializeSessionCallback) throws RemoteException;

    boolean registerClient(IBinder iBinder) throws RemoteException;

    void releaseSession() throws RemoteException;

    void unregisterClient(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements ICameraExtensionsProxyService {
        static final int TRANSACTION_advancedExtensionsSupported = 3;
        static final int TRANSACTION_initializeAdvancedExtension = 8;
        static final int TRANSACTION_initializeImageExtension = 7;
        static final int TRANSACTION_initializePreviewExtension = 6;
        static final int TRANSACTION_initializeSession = 4;
        static final int TRANSACTION_registerClient = 1;
        static final int TRANSACTION_releaseSession = 5;
        static final int TRANSACTION_unregisterClient = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, ICameraExtensionsProxyService.DESCRIPTOR);
        }

        public static ICameraExtensionsProxyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICameraExtensionsProxyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICameraExtensionsProxyService)) {
                return (ICameraExtensionsProxyService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICameraExtensionsProxyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICameraExtensionsProxyService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean registerClient = registerClient(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerClient);
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterClient(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean advancedExtensionsSupported = advancedExtensionsSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(advancedExtensionsSupported);
                    return true;
                case 4:
                    IInitializeSessionCallback asInterface = IInitializeSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    initializeSession(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    releaseSession();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IPreviewExtenderImpl initializePreviewExtension = initializePreviewExtension(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(initializePreviewExtension);
                    return true;
                case 7:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImageCaptureExtenderImpl initializeImageExtension = initializeImageExtension(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(initializeImageExtension);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(initializeAdvancedExtension);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICameraExtensionsProxyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICameraExtensionsProxyService.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public boolean registerClient(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void unregisterClient(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public boolean advancedExtensionsSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void initializeSession(IInitializeSessionCallback iInitializeSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInitializeSessionCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void releaseSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public IPreviewExtenderImpl initializePreviewExtension(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return IPreviewExtenderImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public IImageCaptureExtenderImpl initializeImageExtension(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImageCaptureExtenderImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return IAdvancedExtenderImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
