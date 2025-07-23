package android.media.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoundTriggerModule extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerModule";

    public static class Default implements ISoundTriggerModule {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void detach() throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void forceRecognitionEvent(int i) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public int getModelParameter(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public int loadModel(SoundModel soundModel) throws RemoteException {
            return 0;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public int loadPhraseModel(PhraseSoundModel phraseSoundModel) throws RemoteException {
            return 0;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public ModelParameterRange queryModelParameterSupport(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void setModelParameter(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public IBinder startRecognition(int i, RecognitionConfig recognitionConfig) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void stopRecognition(int i) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void unloadModel(int i) throws RemoteException {
        }
    }

    void detach() throws RemoteException;

    void forceRecognitionEvent(int i) throws RemoteException;

    int getModelParameter(int i, int i2) throws RemoteException;

    int loadModel(SoundModel soundModel) throws RemoteException;

    int loadPhraseModel(PhraseSoundModel phraseSoundModel) throws RemoteException;

    ModelParameterRange queryModelParameterSupport(int i, int i2) throws RemoteException;

    void setModelParameter(int i, int i2, int i3) throws RemoteException;

    IBinder startRecognition(int i, RecognitionConfig recognitionConfig) throws RemoteException;

    void stopRecognition(int i) throws RemoteException;

    void unloadModel(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundTriggerModule {
        static final int TRANSACTION_detach = 10;
        static final int TRANSACTION_forceRecognitionEvent = 6;
        static final int TRANSACTION_getModelParameter = 8;
        static final int TRANSACTION_loadModel = 1;
        static final int TRANSACTION_loadPhraseModel = 2;
        static final int TRANSACTION_queryModelParameterSupport = 9;
        static final int TRANSACTION_setModelParameter = 7;
        static final int TRANSACTION_startRecognition = 4;
        static final int TRANSACTION_stopRecognition = 5;
        static final int TRANSACTION_unloadModel = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISoundTriggerModule.DESCRIPTOR);
        }

        public static ISoundTriggerModule asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISoundTriggerModule.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerModule)) {
                return (ISoundTriggerModule) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISoundTriggerModule.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundTriggerModule.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SoundModel soundModel = (SoundModel) parcel.readTypedObject(SoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadModel = loadModel(soundModel);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadModel);
                    return true;
                case 2:
                    PhraseSoundModel phraseSoundModel = (PhraseSoundModel) parcel.readTypedObject(PhraseSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadPhraseModel = loadPhraseModel(phraseSoundModel);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadPhraseModel);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadModel(readInt);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    RecognitionConfig recognitionConfig = (RecognitionConfig) parcel.readTypedObject(RecognitionConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder startRecognition = startRecognition(readInt2, recognitionConfig);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(startRecognition);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecognition(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRecognitionEvent(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setModelParameter(readInt5, readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int modelParameter = getModelParameter(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(modelParameter);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ModelParameterRange queryModelParameterSupport = queryModelParameterSupport(readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryModelParameterSupport, 1);
                    return true;
                case 10:
                    detach();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISoundTriggerModule {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundTriggerModule.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public int loadModel(SoundModel soundModel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeTypedObject(soundModel, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public int loadPhraseModel(PhraseSoundModel phraseSoundModel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeTypedObject(phraseSoundModel, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void unloadModel(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public IBinder startRecognition(int i, RecognitionConfig recognitionConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void stopRecognition(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void forceRecognitionEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void setModelParameter(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public int getModelParameter(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public ModelParameterRange queryModelParameterSupport(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ModelParameterRange) obtain2.readTypedObject(ModelParameterRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void detach() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerModule.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
