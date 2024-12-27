package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IProcessResultImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

/* loaded from: classes2.dex */
public interface IPreviewImageProcessorImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IPreviewImageProcessorImpl";

    void onImageFormatUpdate(int i) throws RemoteException;

    void onOutputSurface(Surface surface, int i) throws RemoteException;

    void onResolutionUpdate(Size size) throws RemoteException;

    void process(ParcelImage parcelImage, CameraMetadataNative cameraMetadataNative, int i, IProcessResultImpl iProcessResultImpl) throws RemoteException;

    public static class Default implements IPreviewImageProcessorImpl {
        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onOutputSurface(Surface surface, int imageFormat) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onResolutionUpdate(Size size) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onImageFormatUpdate(int imageFormat) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void process(ParcelImage image, CameraMetadataNative result, int sequenceId, IProcessResultImpl resultCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPreviewImageProcessorImpl {
        static final int TRANSACTION_onImageFormatUpdate = 3;
        static final int TRANSACTION_onOutputSurface = 1;
        static final int TRANSACTION_onResolutionUpdate = 2;
        static final int TRANSACTION_process = 4;

        public Stub() {
            attachInterface(this, IPreviewImageProcessorImpl.DESCRIPTOR);
        }

        public static IPreviewImageProcessorImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPreviewImageProcessorImpl.DESCRIPTOR);
            if (iin != null && (iin instanceof IPreviewImageProcessorImpl)) {
                return (IPreviewImageProcessorImpl) iin;
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
                    return "onOutputSurface";
                case 2:
                    return "onResolutionUpdate";
                case 3:
                    return "onImageFormatUpdate";
                case 4:
                    return "process";
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
                data.enforceInterface(IPreviewImageProcessorImpl.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPreviewImageProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Surface _arg0 = (Surface) data.readTypedObject(Surface.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onOutputSurface(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    Size _arg02 = (Size) data.readTypedObject(Size.CREATOR);
                    data.enforceNoDataAvail();
                    onResolutionUpdate(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onImageFormatUpdate(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    ParcelImage _arg04 = (ParcelImage) data.readTypedObject(ParcelImage.CREATOR);
                    CameraMetadataNative _arg12 = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
                    int _arg2 = data.readInt();
                    IProcessResultImpl _arg3 = IProcessResultImpl.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    process(_arg04, _arg12, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPreviewImageProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPreviewImageProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onOutputSurface(Surface surface, int imageFormat) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(surface, 0);
                    _data.writeInt(imageFormat);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onResolutionUpdate(Size size) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(size, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onImageFormatUpdate(int imageFormat) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    _data.writeInt(imageFormat);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void process(ParcelImage image, CameraMetadataNative result, int sequenceId, IProcessResultImpl resultCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(image, 0);
                    _data.writeTypedObject(result, 0);
                    _data.writeInt(sequenceId);
                    _data.writeStrongInterface(resultCallback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
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
