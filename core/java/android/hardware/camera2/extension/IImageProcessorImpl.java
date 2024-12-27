package android.hardware.camera2.extension;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IImageProcessorImpl extends IInterface {
    public static final String DESCRIPTOR =
            "android.hardware.camera2.extension.IImageProcessorImpl";

    void onNextImageAvailable(OutputConfigId outputConfigId, ParcelImage parcelImage, String str)
            throws RemoteException;

    public static class Default implements IImageProcessorImpl {
        @Override // android.hardware.camera2.extension.IImageProcessorImpl
        public void onNextImageAvailable(
                OutputConfigId outputConfigId, ParcelImage image, String physicalCameraId)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IImageProcessorImpl {
        static final int TRANSACTION_onNextImageAvailable = 1;

        public Stub() {
            attachInterface(this, IImageProcessorImpl.DESCRIPTOR);
        }

        public static IImageProcessorImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImageProcessorImpl.DESCRIPTOR);
            if (iin != null && (iin instanceof IImageProcessorImpl)) {
                return (IImageProcessorImpl) iin;
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
                    return "onNextImageAvailable";
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
                data.enforceInterface(IImageProcessorImpl.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IImageProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    OutputConfigId _arg0 =
                            (OutputConfigId) data.readTypedObject(OutputConfigId.CREATOR);
                    ParcelImage _arg1 = (ParcelImage) data.readTypedObject(ParcelImage.CREATOR);
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    onNextImageAvailable(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImageProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImageProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IImageProcessorImpl
            public void onNextImageAvailable(
                    OutputConfigId outputConfigId, ParcelImage image, String physicalCameraId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageProcessorImpl.DESCRIPTOR);
                    _data.writeTypedObject(outputConfigId, 0);
                    _data.writeTypedObject(image, 0);
                    _data.writeString(physicalCameraId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
