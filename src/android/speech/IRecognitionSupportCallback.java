package android.speech;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRecognitionSupportCallback extends IInterface {
    public static final String DESCRIPTOR = "android.speech.IRecognitionSupportCallback";

    public static class Default implements IRecognitionSupportCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.IRecognitionSupportCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // android.speech.IRecognitionSupportCallback
        public void onSupportResult(RecognitionSupport recognitionSupport) throws RemoteException {
        }
    }

    void onError(int i) throws RemoteException;

    void onSupportResult(RecognitionSupport recognitionSupport) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecognitionSupportCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSupportResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IRecognitionSupportCallback.DESCRIPTOR);
        }

        public static IRecognitionSupportCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRecognitionSupportCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRecognitionSupportCallback)) {
                return (IRecognitionSupportCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSupportResult";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRecognitionSupportCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRecognitionSupportCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RecognitionSupport recognitionSupport = (RecognitionSupport) parcel.readTypedObject(RecognitionSupport.CREATOR);
                parcel.enforceNoDataAvail();
                onSupportResult(recognitionSupport);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRecognitionSupportCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRecognitionSupportCallback.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionSupportCallback
            public void onSupportResult(RecognitionSupport recognitionSupport) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRecognitionSupportCallback.DESCRIPTOR);
                    obtain.writeTypedObject(recognitionSupport, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionSupportCallback
            public void onError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRecognitionSupportCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
