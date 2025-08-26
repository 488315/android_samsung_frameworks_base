package android.hardware.camera2.extension;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

/* loaded from: classes2.dex */
public interface IRequestUpdateProcessorImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IRequestUpdateProcessorImpl";

    public static class Default implements IRequestUpdateProcessorImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
        public void onImageFormatUpdate(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
        public void onOutputSurface(Surface surface, int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
        public void onResolutionUpdate(Size size) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
        public CaptureStageImpl process(CameraMetadataNative cameraMetadataNative, int i) throws RemoteException {
            return null;
        }
    }

    void onImageFormatUpdate(int i) throws RemoteException;

    void onOutputSurface(Surface surface, int i) throws RemoteException;

    void onResolutionUpdate(Size size) throws RemoteException;

    CaptureStageImpl process(CameraMetadataNative cameraMetadataNative, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IRequestUpdateProcessorImpl {
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
            attachInterface(this, IRequestUpdateProcessorImpl.DESCRIPTOR);
        }

        public static IRequestUpdateProcessorImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRequestUpdateProcessorImpl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRequestUpdateProcessorImpl)) {
                return (IRequestUpdateProcessorImpl) iInterfaceQueryLocalInterface;
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
                parcel.enforceInterface(IRequestUpdateProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRequestUpdateProcessorImpl.DESCRIPTOR);
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
                CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                CaptureStageImpl captureStageImplProcess = process(cameraMetadataNative, i5);
                parcel2.writeNoException();
                parcel2.writeTypedObject(captureStageImplProcess, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRequestUpdateProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestUpdateProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
            public void onOutputSurface(Surface surface, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestUpdateProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
            public void onResolutionUpdate(Size size) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestUpdateProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(size, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
            public void onImageFormatUpdate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestUpdateProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestUpdateProcessorImpl
            public CaptureStageImpl process(CameraMetadataNative cameraMetadataNative, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestUpdateProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CaptureStageImpl) parcelObtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
