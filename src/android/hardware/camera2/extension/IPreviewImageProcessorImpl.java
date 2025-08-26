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

    public static class Default implements IPreviewImageProcessorImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onImageFormatUpdate(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onOutputSurface(Surface surface, int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onResolutionUpdate(Size size) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void process(ParcelImage parcelImage, CameraMetadataNative cameraMetadataNative, int i, IProcessResultImpl iProcessResultImpl) throws RemoteException {
        }
    }

    void onImageFormatUpdate(int i) throws RemoteException;

    void onOutputSurface(Surface surface, int i) throws RemoteException;

    void onResolutionUpdate(Size size) throws RemoteException;

    void process(ParcelImage parcelImage, CameraMetadataNative cameraMetadataNative, int i, IProcessResultImpl iProcessResultImpl) throws RemoteException;

    public static abstract class Stub extends Binder implements IPreviewImageProcessorImpl {
        static final int TRANSACTION_onImageFormatUpdate = 3;
        static final int TRANSACTION_onOutputSurface = 1;
        static final int TRANSACTION_onResolutionUpdate = 2;
        static final int TRANSACTION_process = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IPreviewImageProcessorImpl.DESCRIPTOR);
        }

        public static IPreviewImageProcessorImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPreviewImageProcessorImpl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPreviewImageProcessorImpl)) {
                return (IPreviewImageProcessorImpl) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onOutputSurface";
            }
            if (i == 2) {
                return "onResolutionUpdate";
            }
            if (i == 3) {
                return "onImageFormatUpdate";
            }
            if (i != 4) {
                return null;
            }
            return "process";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPreviewImageProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPreviewImageProcessorImpl.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onOutputSurface(surface, i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                Size size = (Size) parcel.readTypedObject(Size.CREATOR);
                parcel.enforceNoDataAvail();
                onResolutionUpdate(size);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onImageFormatUpdate(i4);
                parcel2.writeNoException();
            } else if (i == 4) {
                ParcelImage parcelImage = (ParcelImage) parcel.readTypedObject(ParcelImage.CREATOR);
                CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                int i5 = parcel.readInt();
                IProcessResultImpl iProcessResultImplAsInterface = IProcessResultImpl.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                process(parcelImage, cameraMetadataNative, i5, iProcessResultImplAsInterface);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPreviewImageProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPreviewImageProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onOutputSurface(Surface surface, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onResolutionUpdate(Size size) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(size, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onImageFormatUpdate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void process(ParcelImage parcelImage, CameraMetadataNative cameraMetadataNative, int i, IProcessResultImpl iProcessResultImpl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewImageProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelImage, 0);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iProcessResultImpl);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
