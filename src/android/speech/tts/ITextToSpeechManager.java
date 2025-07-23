package android.speech.tts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.tts.ITextToSpeechSessionCallback;

/* loaded from: classes3.dex */
public interface ITextToSpeechManager extends IInterface {
    public static final String DESCRIPTOR = "android.speech.tts.ITextToSpeechManager";

    public static class Default implements ITextToSpeechManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechManager
        public void createSession(String str, ITextToSpeechSessionCallback iTextToSpeechSessionCallback) throws RemoteException {
        }
    }

    void createSession(String str, ITextToSpeechSessionCallback iTextToSpeechSessionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITextToSpeechManager {
        static final int TRANSACTION_createSession = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITextToSpeechManager.DESCRIPTOR);
        }

        public static ITextToSpeechManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITextToSpeechManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITextToSpeechManager)) {
                return (ITextToSpeechManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "createSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITextToSpeechManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITextToSpeechManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                ITextToSpeechSessionCallback asInterface = ITextToSpeechSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                createSession(readString, asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITextToSpeechManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITextToSpeechManager.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechManager
            public void createSession(String str, ITextToSpeechSessionCallback iTextToSpeechSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITextToSpeechManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iTextToSpeechSessionCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
