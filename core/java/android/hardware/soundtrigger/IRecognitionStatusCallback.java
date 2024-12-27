package android.hardware.soundtrigger;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRecognitionStatusCallback extends IInterface {
    void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent)
            throws RemoteException;

    void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent)
            throws RemoteException;

    void onModuleDied() throws RemoteException;

    void onPauseFailed(int i) throws RemoteException;

    void onPreempted() throws RemoteException;

    void onRecognitionPaused() throws RemoteException;

    void onRecognitionResumed() throws RemoteException;

    void onResumeFailed(int i) throws RemoteException;

    public static class Default implements IRecognitionStatusCallback {
        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent recognitionEvent)
                throws RemoteException {}

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(
                SoundTrigger.GenericRecognitionEvent recognitionEvent) throws RemoteException {}

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionPaused() throws RemoteException {}

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionResumed() throws RemoteException {}

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPreempted() throws RemoteException {}

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onModuleDied() throws RemoteException {}

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onResumeFailed(int status) throws RemoteException {}

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPauseFailed(int status) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRecognitionStatusCallback {
        public static final String DESCRIPTOR =
                "android.hardware.soundtrigger.IRecognitionStatusCallback";
        static final int TRANSACTION_onGenericSoundTriggerDetected = 2;
        static final int TRANSACTION_onKeyphraseDetected = 1;
        static final int TRANSACTION_onModuleDied = 6;
        static final int TRANSACTION_onPauseFailed = 8;
        static final int TRANSACTION_onPreempted = 5;
        static final int TRANSACTION_onRecognitionPaused = 3;
        static final int TRANSACTION_onRecognitionResumed = 4;
        static final int TRANSACTION_onResumeFailed = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecognitionStatusCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRecognitionStatusCallback)) {
                return (IRecognitionStatusCallback) iin;
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
                    return "onKeyphraseDetected";
                case 2:
                    return "onGenericSoundTriggerDetected";
                case 3:
                    return "onRecognitionPaused";
                case 4:
                    return "onRecognitionResumed";
                case 5:
                    return "onPreempted";
                case 6:
                    return "onModuleDied";
                case 7:
                    return "onResumeFailed";
                case 8:
                    return "onPauseFailed";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SoundTrigger.KeyphraseRecognitionEvent _arg0 =
                            (SoundTrigger.KeyphraseRecognitionEvent)
                                    data.readTypedObject(
                                            SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onKeyphraseDetected(_arg0);
                    return true;
                case 2:
                    SoundTrigger.GenericRecognitionEvent _arg02 =
                            (SoundTrigger.GenericRecognitionEvent)
                                    data.readTypedObject(
                                            SoundTrigger.GenericRecognitionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onGenericSoundTriggerDetected(_arg02);
                    return true;
                case 3:
                    onRecognitionPaused();
                    return true;
                case 4:
                    onRecognitionResumed();
                    return true;
                case 5:
                    onPreempted();
                    return true;
                case 6:
                    onModuleDied();
                    return true;
                case 7:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onResumeFailed(_arg03);
                    return true;
                case 8:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onPauseFailed(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRecognitionStatusCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent recognitionEvent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(recognitionEvent, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onGenericSoundTriggerDetected(
                    SoundTrigger.GenericRecognitionEvent recognitionEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(recognitionEvent, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onRecognitionPaused() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onRecognitionResumed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onPreempted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onModuleDied() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onResumeFailed(int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onPauseFailed(int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
