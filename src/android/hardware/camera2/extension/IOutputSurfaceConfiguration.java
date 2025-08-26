package android.hardware.camera2.extension;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IOutputSurfaceConfiguration extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IOutputSurfaceConfiguration";

    public static class Default implements IOutputSurfaceConfiguration {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getImageAnalysisOutputSurface() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getImageCaptureOutputSurface() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getPostviewOutputSurface() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public OutputSurface getPreviewOutputSurface() throws RemoteException {
            return null;
        }
    }

    OutputSurface getImageAnalysisOutputSurface() throws RemoteException;

    OutputSurface getImageCaptureOutputSurface() throws RemoteException;

    OutputSurface getPostviewOutputSurface() throws RemoteException;

    OutputSurface getPreviewOutputSurface() throws RemoteException;

    public static abstract class Stub extends Binder implements IOutputSurfaceConfiguration {
        static final int TRANSACTION_getImageAnalysisOutputSurface = 3;
        static final int TRANSACTION_getImageCaptureOutputSurface = 2;
        static final int TRANSACTION_getPostviewOutputSurface = 4;
        static final int TRANSACTION_getPreviewOutputSurface = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IOutputSurfaceConfiguration.DESCRIPTOR);
        }

        public static IOutputSurfaceConfiguration asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IOutputSurfaceConfiguration.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOutputSurfaceConfiguration)) {
                return (IOutputSurfaceConfiguration) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getPreviewOutputSurface";
            }
            if (i == 2) {
                return "getImageCaptureOutputSurface";
            }
            if (i == 3) {
                return "getImageAnalysisOutputSurface";
            }
            if (i != 4) {
                return null;
            }
            return "getPostviewOutputSurface";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOutputSurfaceConfiguration.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOutputSurfaceConfiguration.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                OutputSurface previewOutputSurface = getPreviewOutputSurface();
                parcel2.writeNoException();
                parcel2.writeTypedObject(previewOutputSurface, 1);
            } else if (i == 2) {
                OutputSurface imageCaptureOutputSurface = getImageCaptureOutputSurface();
                parcel2.writeNoException();
                parcel2.writeTypedObject(imageCaptureOutputSurface, 1);
            } else if (i == 3) {
                OutputSurface imageAnalysisOutputSurface = getImageAnalysisOutputSurface();
                parcel2.writeNoException();
                parcel2.writeTypedObject(imageAnalysisOutputSurface, 1);
            } else if (i == 4) {
                OutputSurface postviewOutputSurface = getPostviewOutputSurface();
                parcel2.writeNoException();
                parcel2.writeTypedObject(postviewOutputSurface, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOutputSurfaceConfiguration {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OutputSurface) parcelObtain2.readTypedObject(OutputSurface.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public OutputSurface getImageCaptureOutputSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OutputSurface) parcelObtain2.readTypedObject(OutputSurface.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public OutputSurface getImageAnalysisOutputSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OutputSurface) parcelObtain2.readTypedObject(OutputSurface.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public OutputSurface getPostviewOutputSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OutputSurface) parcelObtain2.readTypedObject(OutputSurface.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
