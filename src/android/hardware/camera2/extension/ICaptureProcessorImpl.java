package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IProcessResultImpl;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface ICaptureProcessorImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.ICaptureProcessorImpl";

    public static class Default implements ICaptureProcessorImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onImageFormatUpdate(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onOutputSurface(Surface surface, int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onPostviewOutputSurface(Surface surface) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void onResolutionUpdate(Size size, Size size2) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
        public void process(List<CaptureBundle> list, IProcessResultImpl iProcessResultImpl, boolean z) throws RemoteException {
        }
    }

    void onImageFormatUpdate(int i) throws RemoteException;

    void onOutputSurface(Surface surface, int i) throws RemoteException;

    void onPostviewOutputSurface(Surface surface) throws RemoteException;

    void onResolutionUpdate(Size size, Size size2) throws RemoteException;

    void process(List<CaptureBundle> list, IProcessResultImpl iProcessResultImpl, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICaptureProcessorImpl {
        static final int TRANSACTION_onImageFormatUpdate = 4;
        static final int TRANSACTION_onOutputSurface = 1;
        static final int TRANSACTION_onPostviewOutputSurface = 2;
        static final int TRANSACTION_onResolutionUpdate = 3;
        static final int TRANSACTION_process = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ICaptureProcessorImpl.DESCRIPTOR);
        }

        public static ICaptureProcessorImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICaptureProcessorImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICaptureProcessorImpl)) {
                return (ICaptureProcessorImpl) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onOutputSurface";
            }
            if (i == 2) {
                return "onPostviewOutputSurface";
            }
            if (i == 3) {
                return "onResolutionUpdate";
            }
            if (i == 4) {
                return "onImageFormatUpdate";
            }
            if (i != 5) {
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
                parcel.enforceInterface(ICaptureProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICaptureProcessorImpl.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onOutputSurface(surface, readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                parcel.enforceNoDataAvail();
                onPostviewOutputSurface(surface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                Size size = (Size) parcel.readTypedObject(Size.CREATOR);
                Size size2 = (Size) parcel.readTypedObject(Size.CREATOR);
                parcel.enforceNoDataAvail();
                onResolutionUpdate(size, size2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onImageFormatUpdate(readInt2);
                parcel2.writeNoException();
            } else if (i == 5) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(CaptureBundle.CREATOR);
                IProcessResultImpl asInterface = IProcessResultImpl.Stub.asInterface(parcel.readStrongBinder());
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                process(createTypedArrayList, asInterface, readBoolean);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICaptureProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICaptureProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onOutputSurface(Surface surface, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onPostviewOutputSurface(Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onResolutionUpdate(Size size, Size size2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    obtain.writeTypedObject(size2, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void onImageFormatUpdate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
            public void process(List<CaptureBundle> list, IProcessResultImpl iProcessResultImpl, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iProcessResultImpl);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
