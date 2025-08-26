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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundTriggerHw)) {
                return (ISoundTriggerHw) iInterfaceQueryLocalInterface;
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
                    ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallbackAsInterface = ISoundTriggerHwGlobalCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerGlobalCallback(iSoundTriggerHwGlobalCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    SoundModel soundModel = (SoundModel) parcel.readTypedObject(SoundModel.CREATOR);
                    ISoundTriggerHwCallback iSoundTriggerHwCallbackAsInterface = ISoundTriggerHwCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iLoadSoundModel = loadSoundModel(soundModel, iSoundTriggerHwCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLoadSoundModel);
                    return true;
                case 4:
                    PhraseSoundModel phraseSoundModel = (PhraseSoundModel) parcel.readTypedObject(PhraseSoundModel.CREATOR);
                    ISoundTriggerHwCallback iSoundTriggerHwCallbackAsInterface2 = ISoundTriggerHwCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iLoadPhraseSoundModel = loadPhraseSoundModel(phraseSoundModel, iSoundTriggerHwCallbackAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLoadPhraseSoundModel);
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadSoundModel(i3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    RecognitionConfig recognitionConfig = (RecognitionConfig) parcel.readTypedObject(RecognitionConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRecognition(i4, i5, i6, recognitionConfig);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecognition(i7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRecognitionEvent(i8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ModelParameterRange modelParameterRangeQueryParameter = queryParameter(i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(modelParameterRangeQueryParameter, 1);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter = getParameter(i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter);
                    return true;
                case 11:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setParameter(i13, i14, i15);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getProperties is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (Properties) parcelObtain2.readTypedObject(Properties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void registerGlobalCallback(ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSoundTriggerHwGlobalCallback);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method registerGlobalCallback is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int loadSoundModel(SoundModel soundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(soundModel, 0);
                    parcelObtain.writeStrongInterface(iSoundTriggerHwCallback);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method loadSoundModel is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(phraseSoundModel, 0);
                    parcelObtain.writeStrongInterface(iSoundTriggerHwCallback);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method loadPhraseSoundModel is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void unloadSoundModel(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method unloadSoundModel is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(recognitionConfig, 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method startRecognition is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void stopRecognition(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method stopRecognition is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void forceRecognitionEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method forceRecognitionEvent is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public ModelParameterRange queryParameter(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method queryParameter is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (ModelParameterRange) parcelObtain2.readTypedObject(ModelParameterRange.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int getParameter(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getParameter is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void setParameter(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setParameter is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
