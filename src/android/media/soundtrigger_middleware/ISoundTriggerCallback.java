package android.media.soundtrigger_middleware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoundTriggerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerCallback";

    public static class Default implements ISoundTriggerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onModelUnloaded(int i) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onModuleDied() throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onResourcesAvailable() throws RemoteException {
        }
    }

    void onModelUnloaded(int i) throws RemoteException;

    void onModuleDied() throws RemoteException;

    void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws RemoteException;

    void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) throws RemoteException;

    void onResourcesAvailable() throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundTriggerCallback {
        static final int TRANSACTION_onModelUnloaded = 4;
        static final int TRANSACTION_onModuleDied = 5;
        static final int TRANSACTION_onPhraseRecognition = 2;
        static final int TRANSACTION_onRecognition = 1;
        static final int TRANSACTION_onResourcesAvailable = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISoundTriggerCallback.DESCRIPTOR);
        }

        public static ISoundTriggerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISoundTriggerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundTriggerCallback)) {
                return (ISoundTriggerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISoundTriggerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundTriggerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                RecognitionEventSys recognitionEventSys = (RecognitionEventSys) parcel.readTypedObject(RecognitionEventSys.CREATOR);
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRecognition(i3, recognitionEventSys, i4);
            } else if (i == 2) {
                int i5 = parcel.readInt();
                PhraseRecognitionEventSys phraseRecognitionEventSys = (PhraseRecognitionEventSys) parcel.readTypedObject(PhraseRecognitionEventSys.CREATOR);
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onPhraseRecognition(i5, phraseRecognitionEventSys, i6);
            } else if (i == 3) {
                onResourcesAvailable();
            } else if (i == 4) {
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onModelUnloaded(i7);
            } else if (i == 5) {
                onModuleDied();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISoundTriggerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundTriggerCallback.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(recognitionEventSys, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(phraseRecognitionEventSys, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onResourcesAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onModelUnloaded(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onModuleDied() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
