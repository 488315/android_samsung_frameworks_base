package android.hardware.camera2.extension;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOutputSurfaceConfiguration extends IInterface {
    public static final String DESCRIPTOR =
            "android.hardware.camera2.extension.IOutputSurfaceConfiguration";

    OutputSurface getImageAnalysisOutputSurface() throws RemoteException;

    OutputSurface getImageCaptureOutputSurface() throws RemoteException;

    OutputSurface getPostviewOutputSurface() throws RemoteException;

    OutputSurface getPreviewOutputSurface() throws RemoteException;

    public static class Default implements IOutputSurfaceConfiguration {
        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getPreviewOutputSurface() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getImageCaptureOutputSurface() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getImageAnalysisOutputSurface() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getPostviewOutputSurface() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOutputSurfaceConfiguration {
        static final int TRANSACTION_getImageAnalysisOutputSurface = 3;
        static final int TRANSACTION_getImageCaptureOutputSurface = 2;
        static final int TRANSACTION_getPostviewOutputSurface = 4;
        static final int TRANSACTION_getPreviewOutputSurface = 1;

        public Stub() {
            attachInterface(this, IOutputSurfaceConfiguration.DESCRIPTOR);
        }

        public static IOutputSurfaceConfiguration asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOutputSurfaceConfiguration.DESCRIPTOR);
            if (iin != null && (iin instanceof IOutputSurfaceConfiguration)) {
                return (IOutputSurfaceConfiguration) iin;
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
                    return "getPreviewOutputSurface";
                case 2:
                    return "getImageCaptureOutputSurface";
                case 3:
                    return "getImageAnalysisOutputSurface";
                case 4:
                    return "getPostviewOutputSurface";
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
                data.enforceInterface(IOutputSurfaceConfiguration.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOutputSurfaceConfiguration.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    OutputSurface _result = getPreviewOutputSurface();
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    OutputSurface _result2 = getImageCaptureOutputSurface();
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    OutputSurface _result3 = getImageAnalysisOutputSurface();
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    OutputSurface _result4 = getPostviewOutputSurface();
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOutputSurfaceConfiguration {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOutputSurfaceConfiguration.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public OutputSurface getPreviewOutputSurface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    OutputSurface _result =
                            (OutputSurface) _reply.readTypedObject(OutputSurface.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public OutputSurface getImageCaptureOutputSurface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    OutputSurface _result =
                            (OutputSurface) _reply.readTypedObject(OutputSurface.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public OutputSurface getImageAnalysisOutputSurface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    OutputSurface _result =
                            (OutputSurface) _reply.readTypedObject(OutputSurface.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public OutputSurface getPostviewOutputSurface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    OutputSurface _result =
                            (OutputSurface) _reply.readTypedObject(OutputSurface.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
