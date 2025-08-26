package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IImageProcessorImpl;
import android.hardware.camera2.extension.IRequestCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IRequestProcessorImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IRequestProcessorImpl";

    public static class Default implements IRequestProcessorImpl {
        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void abortCaptures() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void setImageProcessor(OutputConfigId outputConfigId, IImageProcessorImpl iImageProcessorImpl) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int setRepeating(Request request, IRequestCallback iRequestCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void stopRepeating() throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submit(Request request, IRequestCallback iRequestCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submitBurst(List<Request> list, IRequestCallback iRequestCallback) throws RemoteException {
            return 0;
        }
    }

    void abortCaptures() throws RemoteException;

    void setImageProcessor(OutputConfigId outputConfigId, IImageProcessorImpl iImageProcessorImpl) throws RemoteException;

    int setRepeating(Request request, IRequestCallback iRequestCallback) throws RemoteException;

    void stopRepeating() throws RemoteException;

    int submit(Request request, IRequestCallback iRequestCallback) throws RemoteException;

    int submitBurst(List<Request> list, IRequestCallback iRequestCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IRequestProcessorImpl {
        static final int TRANSACTION_abortCaptures = 5;
        static final int TRANSACTION_setImageProcessor = 1;
        static final int TRANSACTION_setRepeating = 4;
        static final int TRANSACTION_stopRepeating = 6;
        static final int TRANSACTION_submit = 2;
        static final int TRANSACTION_submitBurst = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IRequestProcessorImpl.DESCRIPTOR);
        }

        public static IRequestProcessorImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRequestProcessorImpl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRequestProcessorImpl)) {
                return (IRequestProcessorImpl) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setImageProcessor";
                case 2:
                    return "submit";
                case 3:
                    return "submitBurst";
                case 4:
                    return "setRepeating";
                case 5:
                    return "abortCaptures";
                case 6:
                    return "stopRepeating";
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
                parcel.enforceInterface(IRequestProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRequestProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    OutputConfigId outputConfigId = (OutputConfigId) parcel.readTypedObject(OutputConfigId.CREATOR);
                    IImageProcessorImpl iImageProcessorImplAsInterface = IImageProcessorImpl.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setImageProcessor(outputConfigId, iImageProcessorImplAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    Request request = (Request) parcel.readTypedObject(Request.CREATOR);
                    IRequestCallback iRequestCallbackAsInterface = IRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iSubmit = submit(request, iRequestCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSubmit);
                    return true;
                case 3:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Request.CREATOR);
                    IRequestCallback iRequestCallbackAsInterface2 = IRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iSubmitBurst = submitBurst(arrayListCreateTypedArrayList, iRequestCallbackAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSubmitBurst);
                    return true;
                case 4:
                    Request request2 = (Request) parcel.readTypedObject(Request.CREATOR);
                    IRequestCallback iRequestCallbackAsInterface3 = IRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int repeating = setRepeating(request2, iRequestCallbackAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(repeating);
                    return true;
                case 5:
                    abortCaptures();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopRepeating();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRequestProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void setImageProcessor(OutputConfigId outputConfigId, IImageProcessorImpl iImageProcessorImpl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(outputConfigId, 0);
                    parcelObtain.writeStrongInterface(iImageProcessorImpl);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int submit(Request request, IRequestCallback iRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(request, 0);
                    parcelObtain.writeStrongInterface(iRequestCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int submitBurst(List<Request> list, IRequestCallback iRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongInterface(iRequestCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int setRepeating(Request request, IRequestCallback iRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(request, 0);
                    parcelObtain.writeStrongInterface(iRequestCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void abortCaptures() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void stopRepeating() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
