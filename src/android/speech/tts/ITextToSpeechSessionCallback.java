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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITextToSpeechSessionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITextToSpeechSessionCallback)) {
                return (ITextToSpeechSessionCallback) iInterfaceQueryLocalInterface;
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
                ITextToSpeechSession iTextToSpeechSessionAsInterface = ITextToSpeechSession.Stub.asInterface(parcel.readStrongBinder());
                IBinder strongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onConnected(iTextToSpeechSessionAsInterface, strongBinder);
            } else if (i == 2) {
                onDisconnected();
            } else if (i == 3) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(string);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITextToSpeechSessionCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTextToSpeechSession);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onDisconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITextToSpeechSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onError(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITextToSpeechSessionCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
