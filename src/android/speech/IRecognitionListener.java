package android.speech;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRecognitionListener extends IInterface {

    public static class Default implements IRecognitionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.IRecognitionListener
        public void onBeginningOfSpeech() throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onBufferReceived(byte[] bArr) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSegmentedSession() throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSpeech() throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onError(int i) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onEvent(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onLanguageDetection(Bundle bundle) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onPartialResults(Bundle bundle) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onReadyForSpeech(Bundle bundle) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onResults(Bundle bundle) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onRmsChanged(float f) throws RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onSegmentResults(Bundle bundle) throws RemoteException {
        }
    }

    void onBeginningOfSpeech() throws RemoteException;

    void onBufferReceived(byte[] bArr) throws RemoteException;

    void onEndOfSegmentedSession() throws RemoteException;

    void onEndOfSpeech() throws RemoteException;

    void onError(int i) throws RemoteException;

    void onEvent(int i, Bundle bundle) throws RemoteException;

    void onLanguageDetection(Bundle bundle) throws RemoteException;

    void onPartialResults(Bundle bundle) throws RemoteException;

    void onReadyForSpeech(Bundle bundle) throws RemoteException;

    void onResults(Bundle bundle) throws RemoteException;

    void onRmsChanged(float f) throws RemoteException;

    void onSegmentResults(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecognitionListener {
        public static final String DESCRIPTOR = "android.speech.IRecognitionListener";
        static final int TRANSACTION_onBeginningOfSpeech = 2;
        static final int TRANSACTION_onBufferReceived = 4;
        static final int TRANSACTION_onEndOfSegmentedSession = 10;
        static final int TRANSACTION_onEndOfSpeech = 5;
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onEvent = 12;
        static final int TRANSACTION_onLanguageDetection = 11;
        static final int TRANSACTION_onPartialResults = 8;
        static final int TRANSACTION_onReadyForSpeech = 1;
        static final int TRANSACTION_onResults = 7;
        static final int TRANSACTION_onRmsChanged = 3;
        static final int TRANSACTION_onSegmentResults = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecognitionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRecognitionListener)) {
                return (IRecognitionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onReadyForSpeech";
                case 2:
                    return "onBeginningOfSpeech";
                case 3:
                    return "onRmsChanged";
                case 4:
                    return "onBufferReceived";
                case 5:
                    return "onEndOfSpeech";
                case 6:
                    return "onError";
                case 7:
                    return "onResults";
                case 8:
                    return "onPartialResults";
                case 9:
                    return "onSegmentResults";
                case 10:
                    return "onEndOfSegmentedSession";
                case 11:
                    return "onLanguageDetection";
                case 12:
                    return "onEvent";
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
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onReadyForSpeech(bundle);
                    return true;
                case 2:
                    onBeginningOfSpeech();
                    return true;
                case 3:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onRmsChanged(readFloat);
                    return true;
                case 4:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onBufferReceived(createByteArray);
                    return true;
                case 5:
                    onEndOfSpeech();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt);
                    return true;
                case 7:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResults(bundle2);
                    return true;
                case 8:
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPartialResults(bundle3);
                    return true;
                case 9:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSegmentResults(bundle4);
                    return true;
                case 10:
                    onEndOfSegmentedSession();
                    return true;
                case 11:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLanguageDetection(bundle5);
                    return true;
                case 12:
                    int readInt2 = parcel.readInt();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvent(readInt2, bundle6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRecognitionListener {
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

            @Override // android.speech.IRecognitionListener
            public void onReadyForSpeech(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onBeginningOfSpeech() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onRmsChanged(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onBufferReceived(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onEndOfSpeech() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onResults(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onPartialResults(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onSegmentResults(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onEndOfSegmentedSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onLanguageDetection(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onEvent(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
