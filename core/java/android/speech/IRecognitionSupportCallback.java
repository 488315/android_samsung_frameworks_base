package android.speech;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRecognitionSupportCallback extends IInterface {
    public static final String DESCRIPTOR = "android.speech.IRecognitionSupportCallback";

    void onError(int i) throws RemoteException;

    void onSupportResult(RecognitionSupport recognitionSupport) throws RemoteException;

    public static class Default implements IRecognitionSupportCallback {
        @Override // android.speech.IRecognitionSupportCallback
        public void onSupportResult(RecognitionSupport recognitionSupport) throws RemoteException {}

        @Override // android.speech.IRecognitionSupportCallback
        public void onError(int error) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRecognitionSupportCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSupportResult = 1;

        public Stub() {
            attachInterface(this, IRecognitionSupportCallback.DESCRIPTOR);
        }

        public static IRecognitionSupportCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRecognitionSupportCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRecognitionSupportCallback)) {
                return (IRecognitionSupportCallback) iin;
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
                    return "onSupportResult";
                case 2:
                    return "onError";
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
                data.enforceInterface(IRecognitionSupportCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRecognitionSupportCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    RecognitionSupport _arg0 =
                            (RecognitionSupport) data.readTypedObject(RecognitionSupport.CREATOR);
                    data.enforceNoDataAvail();
                    onSupportResult(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onError(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRecognitionSupportCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRecognitionSupportCallback.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionSupportCallback
            public void onSupportResult(RecognitionSupport recognitionSupport)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRecognitionSupportCallback.DESCRIPTOR);
                    _data.writeTypedObject(recognitionSupport, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IRecognitionSupportCallback
            public void onError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRecognitionSupportCallback.DESCRIPTOR);
                    _data.writeInt(error);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
