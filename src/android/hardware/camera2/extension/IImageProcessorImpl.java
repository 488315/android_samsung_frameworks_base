package android.hardware.camera2.extension;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IImageProcessorImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IImageProcessorImpl";

    public static class Default implements IImageProcessorImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageProcessorImpl
        public void onNextImageAvailable(OutputConfigId outputConfigId, ParcelImage parcelImage, String str) throws RemoteException {
        }
    }

    void onNextImageAvailable(OutputConfigId outputConfigId, ParcelImage parcelImage, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IImageProcessorImpl {
        static final int TRANSACTION_onNextImageAvailable = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IImageProcessorImpl.DESCRIPTOR);
        }

        public static IImageProcessorImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImageProcessorImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImageProcessorImpl)) {
                return (IImageProcessorImpl) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onNextImageAvailable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImageProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImageProcessorImpl.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                OutputConfigId outputConfigId = (OutputConfigId) parcel.readTypedObject(OutputConfigId.CREATOR);
                ParcelImage parcelImage = (ParcelImage) parcel.readTypedObject(ParcelImage.CREATOR);
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onNextImageAvailable(outputConfigId, parcelImage, readString);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IImageProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImageProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IImageProcessorImpl
            public void onNextImageAvailable(OutputConfigId outputConfigId, ParcelImage parcelImage, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(outputConfigId, 0);
                    obtain.writeTypedObject(parcelImage, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
