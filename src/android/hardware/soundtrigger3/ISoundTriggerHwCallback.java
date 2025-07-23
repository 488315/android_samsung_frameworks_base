package android.hardware.soundtrigger3;

import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.RecognitionEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundTriggerHwCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$soundtrigger3$ISoundTriggerHwCallback".replace('$', '.');
    public static final String HASH = "f2ec48a74490bf9d5675f48cb89ecdb3e5cd9c35";
    public static final int VERSION = 3;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void modelUnloaded(int i) throws RemoteException;

    void phraseRecognitionCallback(int i, PhraseRecognitionEvent phraseRecognitionEvent) throws RemoteException;

    void recognitionCallback(int i, RecognitionEvent recognitionEvent) throws RemoteException;

    public static class Default implements ISoundTriggerHwCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void modelUnloaded(int i) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void phraseRecognitionCallback(int i, PhraseRecognitionEvent phraseRecognitionEvent) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void recognitionCallback(int i, RecognitionEvent recognitionEvent) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISoundTriggerHwCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_modelUnloaded = 1;
        static final int TRANSACTION_phraseRecognitionCallback = 2;
        static final int TRANSACTION_recognitionCallback = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerHwCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerHwCallback)) {
                return (ISoundTriggerHwCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                modelUnloaded(readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                PhraseRecognitionEvent phraseRecognitionEvent = (PhraseRecognitionEvent) parcel.readTypedObject(PhraseRecognitionEvent.CREATOR);
                parcel.enforceNoDataAvail();
                phraseRecognitionCallback(readInt2, phraseRecognitionEvent);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                RecognitionEvent recognitionEvent = (RecognitionEvent) parcel.readTypedObject(RecognitionEvent.CREATOR);
                parcel.enforceNoDataAvail();
                recognitionCallback(readInt3, recognitionEvent);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISoundTriggerHwCallback {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void modelUnloaded(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method modelUnloaded is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void phraseRecognitionCallback(int i, PhraseRecognitionEvent phraseRecognitionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(phraseRecognitionEvent, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method phraseRecognitionCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void recognitionCallback(int i, RecognitionEvent recognitionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recognitionEvent, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method recognitionCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
