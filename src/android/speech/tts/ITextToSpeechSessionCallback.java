package android.speech.tts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.tts.ITextToSpeechSession;

/* loaded from: classes3.dex */
public interface ITextToSpeechSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.speech.tts.ITextToSpeechSessionCallback";

    public static class Default implements ITextToSpeechSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechSessionCallback
        public void onConnected(ITextToSpeechSession iTextToSpeechSession, IBinder iBinder) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechSessionCallback
        public void onDisconnected() throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechSessionCallback
        public void onError(String str) throws RemoteException {
        }
    }

    void onConnected(ITextToSpeechSession iTextToSpeechSession, IBinder iBinder) throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onError(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITextToSpeechSessionCallback {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onError = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ITextToSpeechSessionCallback.DESCRIPTOR);
        }

        public static ITextToSpeechSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITextToSpeechSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITextToSpeechSessionCallback)) {
                return (ITextToSpeechSessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConnected";
            }
            if (i == 2) {
                return "onDisconnected";
            }
            if (i != 3) {
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
                parcel.enforceInterface(ITextToSpeechSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITextToSpeechSessionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ITextToSpeechSession asInterface = ITextToSpeechSession.Stub.asInterface(parcel.readStrongBinder());
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onConnected(asInterface, readStrongBinder);
            } else if (i == 2) {
                onDisconnected();
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITextToSpeechSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITextToSpeechSessionCallback.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onConnected(ITextToSpeechSession iTextToSpeechSession, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITextToSpeechSessionCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iTextToSpeechSession);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onDisconnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITextToSpeechSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onError(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITextToSpeechSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
