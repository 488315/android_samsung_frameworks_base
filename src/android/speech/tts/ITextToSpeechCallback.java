package android.speech.tts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITextToSpeechCallback extends IInterface {

    public static class Default implements ITextToSpeechCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechCallback
        public void onAudioAvailable(String str, byte[] bArr) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechCallback
        public void onBeginSynthesis(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechCallback
        public void onError(String str, int i) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechCallback
        public void onRangeStart(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechCallback
        public void onStart(String str) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechCallback
        public void onStop(String str, boolean z) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechCallback
        public void onSuccess(String str) throws RemoteException {
        }
    }

    void onAudioAvailable(String str, byte[] bArr) throws RemoteException;

    void onBeginSynthesis(String str, int i, int i2, int i3) throws RemoteException;

    void onError(String str, int i) throws RemoteException;

    void onRangeStart(String str, int i, int i2, int i3) throws RemoteException;

    void onStart(String str) throws RemoteException;

    void onStop(String str, boolean z) throws RemoteException;

    void onSuccess(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITextToSpeechCallback {
        public static final String DESCRIPTOR = "android.speech.tts.ITextToSpeechCallback";
        static final int TRANSACTION_onAudioAvailable = 6;
        static final int TRANSACTION_onBeginSynthesis = 5;
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onRangeStart = 7;
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 3;
        static final int TRANSACTION_onSuccess = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITextToSpeechCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITextToSpeechCallback)) {
                return (ITextToSpeechCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStart";
                case 2:
                    return "onSuccess";
                case 3:
                    return "onStop";
                case 4:
                    return "onError";
                case 5:
                    return "onBeginSynthesis";
                case 6:
                    return "onAudioAvailable";
                case 7:
                    return "onRangeStart";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onStart(readString);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onSuccess(readString2);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStop(readString3, readBoolean);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readString4, readInt);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBeginSynthesis(readString5, readInt2, readInt3, readInt4);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onAudioAvailable(readString6, createByteArray);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRangeStart(readString7, readInt5, readInt6, readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITextToSpeechCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechCallback
            public void onStart(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechCallback
            public void onSuccess(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechCallback
            public void onStop(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechCallback
            public void onError(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechCallback
            public void onBeginSynthesis(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechCallback
            public void onAudioAvailable(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechCallback
            public void onRangeStart(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
