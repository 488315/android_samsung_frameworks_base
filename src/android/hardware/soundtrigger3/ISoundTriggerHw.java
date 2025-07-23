package android.hardware.soundtrigger3;

import android.hardware.soundtrigger3.ISoundTriggerHwCallback;
import android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundTriggerHw extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$soundtrigger3$ISoundTriggerHw".replace('$', '.');
    public static final String HASH = "f2ec48a74490bf9d5675f48cb89ecdb3e5cd9c35";
    public static final int VERSION = 3;

    void forceRecognitionEvent(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getParameter(int i, int i2) throws RemoteException;

    Properties getProperties() throws RemoteException;

    int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException;

    int loadSoundModel(SoundModel soundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException;

    ModelParameterRange queryParameter(int i, int i2) throws RemoteException;

    void registerGlobalCallback(ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws RemoteException;

    void setParameter(int i, int i2, int i3) throws RemoteException;

    void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) throws RemoteException;

    void stopRecognition(int i) throws RemoteException;

    void unloadSoundModel(int i) throws RemoteException;

    public static class Default implements ISoundTriggerHw {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void forceRecognitionEvent(int i) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int getParameter(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public Properties getProperties() throws RemoteException {
            return null;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int loadSoundModel(SoundModel soundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public ModelParameterRange queryParameter(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void registerGlobalCallback(ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void setParameter(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void stopRecognition(int i) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void unloadSoundModel(int i) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISoundTriggerHw {
        static final int TRANSACTION_forceRecognitionEvent = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getParameter = 10;
        static final int TRANSACTION_getProperties = 1;
        static final int TRANSACTION_loadPhraseSoundModel = 4;
        static final int TRANSACTION_loadSoundModel = 3;
        static final int TRANSACTION_queryParameter = 9;
        static final int TRANSACTION_registerGlobalCallback = 2;
        static final int TRANSACTION_setParameter = 11;
        static final int TRANSACTION_startRecognition = 6;
        static final int TRANSACTION_stopRecognition = 7;
        static final int TRANSACTION_unloadSoundModel = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerHw asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerHw)) {
                return (ISoundTriggerHw) queryLocalInterface;
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
            switch (i) {
                case 1:
                    Properties properties = getProperties();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(properties, 1);
                    return true;
                case 2:
                    ISoundTriggerHwGlobalCallback asInterface = ISoundTriggerHwGlobalCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerGlobalCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    SoundModel soundModel = (SoundModel) parcel.readTypedObject(SoundModel.CREATOR);
                    ISoundTriggerHwCallback asInterface2 = ISoundTriggerHwCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int loadSoundModel = loadSoundModel(soundModel, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadSoundModel);
                    return true;
                case 4:
                    PhraseSoundModel phraseSoundModel = (PhraseSoundModel) parcel.readTypedObject(PhraseSoundModel.CREATOR);
                    ISoundTriggerHwCallback asInterface3 = ISoundTriggerHwCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int loadPhraseSoundModel = loadPhraseSoundModel(phraseSoundModel, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadPhraseSoundModel);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadSoundModel(readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    RecognitionConfig recognitionConfig = (RecognitionConfig) parcel.readTypedObject(RecognitionConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRecognition(readInt2, readInt3, readInt4, recognitionConfig);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecognition(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRecognitionEvent(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ModelParameterRange queryParameter = queryParameter(readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryParameter, 1);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter = getParameter(readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter);
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setParameter(readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISoundTriggerHw {
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

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public Properties getProperties() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getProperties is unimplemented.");
                    }
                    obtain2.readException();
                    return (Properties) obtain2.readTypedObject(Properties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void registerGlobalCallback(ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSoundTriggerHwGlobalCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method registerGlobalCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int loadSoundModel(SoundModel soundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(soundModel, 0);
                    obtain.writeStrongInterface(iSoundTriggerHwCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method loadSoundModel is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(phraseSoundModel, 0);
                    obtain.writeStrongInterface(iSoundTriggerHwCallback);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method loadPhraseSoundModel is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void unloadSoundModel(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unloadSoundModel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method startRecognition is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void stopRecognition(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method stopRecognition is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void forceRecognitionEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method forceRecognitionEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public ModelParameterRange queryParameter(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method queryParameter is unimplemented.");
                    }
                    obtain2.readException();
                    return (ModelParameterRange) obtain2.readTypedObject(ModelParameterRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int getParameter(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getParameter is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void setParameter(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setParameter is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
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

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
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
