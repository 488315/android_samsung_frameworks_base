package com.android.internal.app;

import android.content.ComponentName;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISoundTriggerSession extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.ISoundTriggerSession";

    public static class Default implements ISoundTriggerSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public void deleteSoundModel(ParcelUuid parcelUuid) throws RemoteException {
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int getModelState(ParcelUuid parcelUuid) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public SoundTrigger.ModuleProperties getModuleProperties() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int getParameter(ParcelUuid parcelUuid, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid parcelUuid) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int loadGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public SoundTrigger.ModelParamRange queryParameter(ParcelUuid parcelUuid, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int setParameter(ParcelUuid parcelUuid, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int startRecognition(SoundTrigger.GenericSoundModel genericSoundModel, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int startRecognitionForService(ParcelUuid parcelUuid, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int stopRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int stopRecognitionForService(ParcelUuid parcelUuid) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int unloadSoundModel(ParcelUuid parcelUuid) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public void updateSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
        }
    }

    void deleteSoundModel(ParcelUuid parcelUuid) throws RemoteException;

    int getModelState(ParcelUuid parcelUuid) throws RemoteException;

    SoundTrigger.ModuleProperties getModuleProperties() throws RemoteException;

    int getParameter(ParcelUuid parcelUuid, int i) throws RemoteException;

    SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid parcelUuid) throws RemoteException;

    boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException;

    int loadGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException;

    int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException;

    SoundTrigger.ModelParamRange queryParameter(ParcelUuid parcelUuid, int i) throws RemoteException;

    int setParameter(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    int startRecognition(SoundTrigger.GenericSoundModel genericSoundModel, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws RemoteException;

    int startRecognitionForService(ParcelUuid parcelUuid, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException;

    int stopRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback) throws RemoteException;

    int stopRecognitionForService(ParcelUuid parcelUuid) throws RemoteException;

    int unloadSoundModel(ParcelUuid parcelUuid) throws RemoteException;

    void updateSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundTriggerSession {
        static final int TRANSACTION_deleteSoundModel = 3;
        static final int TRANSACTION_getModelState = 12;
        static final int TRANSACTION_getModuleProperties = 13;
        static final int TRANSACTION_getParameter = 15;
        static final int TRANSACTION_getSoundModel = 1;
        static final int TRANSACTION_isRecognitionActive = 11;
        static final int TRANSACTION_loadGenericSoundModel = 6;
        static final int TRANSACTION_loadKeyphraseSoundModel = 7;
        static final int TRANSACTION_queryParameter = 16;
        static final int TRANSACTION_setParameter = 14;
        static final int TRANSACTION_startRecognition = 4;
        static final int TRANSACTION_startRecognitionForService = 8;
        static final int TRANSACTION_stopRecognition = 5;
        static final int TRANSACTION_stopRecognitionForService = 9;
        static final int TRANSACTION_unloadSoundModel = 10;
        static final int TRANSACTION_updateSoundModel = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub() {
            attachInterface(this, ISoundTriggerSession.DESCRIPTOR);
        }

        public static ISoundTriggerSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISoundTriggerSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerSession)) {
                return (ISoundTriggerSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSoundModel";
                case 2:
                    return "updateSoundModel";
                case 3:
                    return "deleteSoundModel";
                case 4:
                    return "startRecognition";
                case 5:
                    return "stopRecognition";
                case 6:
                    return "loadGenericSoundModel";
                case 7:
                    return "loadKeyphraseSoundModel";
                case 8:
                    return "startRecognitionForService";
                case 9:
                    return "stopRecognitionForService";
                case 10:
                    return "unloadSoundModel";
                case 11:
                    return "isRecognitionActive";
                case 12:
                    return "getModelState";
                case 13:
                    return "getModuleProperties";
                case 14:
                    return "setParameter";
                case 15:
                    return "getParameter";
                case 16:
                    return "queryParameter";
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
                parcel.enforceInterface(ISoundTriggerSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundTriggerSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ParcelUuid parcelUuid = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    SoundTrigger.GenericSoundModel soundModel = getSoundModel(parcelUuid);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(soundModel, 1);
                    return true;
                case 2:
                    SoundTrigger.GenericSoundModel genericSoundModel = (SoundTrigger.GenericSoundModel) parcel.readTypedObject(SoundTrigger.GenericSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSoundModel(genericSoundModel);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ParcelUuid parcelUuid2 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSoundModel(parcelUuid2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    SoundTrigger.GenericSoundModel genericSoundModel2 = (SoundTrigger.GenericSoundModel) parcel.readTypedObject(SoundTrigger.GenericSoundModel.CREATOR);
                    IRecognitionStatusCallback asInterface = IRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    SoundTrigger.RecognitionConfig recognitionConfig = (SoundTrigger.RecognitionConfig) parcel.readTypedObject(SoundTrigger.RecognitionConfig.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startRecognition = startRecognition(genericSoundModel2, asInterface, recognitionConfig, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRecognition);
                    return true;
                case 5:
                    ParcelUuid parcelUuid3 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    IRecognitionStatusCallback asInterface2 = IRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopRecognition = stopRecognition(parcelUuid3, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopRecognition);
                    return true;
                case 6:
                    SoundTrigger.GenericSoundModel genericSoundModel3 = (SoundTrigger.GenericSoundModel) parcel.readTypedObject(SoundTrigger.GenericSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadGenericSoundModel = loadGenericSoundModel(genericSoundModel3);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadGenericSoundModel);
                    return true;
                case 7:
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = (SoundTrigger.KeyphraseSoundModel) parcel.readTypedObject(SoundTrigger.KeyphraseSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadKeyphraseSoundModel = loadKeyphraseSoundModel(keyphraseSoundModel);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadKeyphraseSoundModel);
                    return true;
                case 8:
                    ParcelUuid parcelUuid4 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    SoundTrigger.RecognitionConfig recognitionConfig2 = (SoundTrigger.RecognitionConfig) parcel.readTypedObject(SoundTrigger.RecognitionConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startRecognitionForService = startRecognitionForService(parcelUuid4, bundle, componentName, recognitionConfig2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRecognitionForService);
                    return true;
                case 9:
                    ParcelUuid parcelUuid5 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    int stopRecognitionForService = stopRecognitionForService(parcelUuid5);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopRecognitionForService);
                    return true;
                case 10:
                    ParcelUuid parcelUuid6 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    int unloadSoundModel = unloadSoundModel(parcelUuid6);
                    parcel2.writeNoException();
                    parcel2.writeInt(unloadSoundModel);
                    return true;
                case 11:
                    ParcelUuid parcelUuid7 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRecognitionActive = isRecognitionActive(parcelUuid7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRecognitionActive);
                    return true;
                case 12:
                    ParcelUuid parcelUuid8 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    int modelState = getModelState(parcelUuid8);
                    parcel2.writeNoException();
                    parcel2.writeInt(modelState);
                    return true;
                case 13:
                    SoundTrigger.ModuleProperties moduleProperties = getModuleProperties();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(moduleProperties, 1);
                    return true;
                case 14:
                    ParcelUuid parcelUuid9 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter = setParameter(parcelUuid9, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter);
                    return true;
                case 15:
                    ParcelUuid parcelUuid10 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter2 = getParameter(parcelUuid10, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter2);
                    return true;
                case 16:
                    ParcelUuid parcelUuid11 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SoundTrigger.ModelParamRange queryParameter = queryParameter(parcelUuid11, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryParameter, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISoundTriggerSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundTriggerSession.DESCRIPTOR;
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTrigger.GenericSoundModel) obtain2.readTypedObject(SoundTrigger.GenericSoundModel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public void updateSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(genericSoundModel, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public void deleteSoundModel(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int startRecognition(SoundTrigger.GenericSoundModel genericSoundModel, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(genericSoundModel, 0);
                    obtain.writeStrongInterface(iRecognitionStatusCallback);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int stopRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeStrongInterface(iRecognitionStatusCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int loadGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(genericSoundModel, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseSoundModel, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int startRecognitionForService(ParcelUuid parcelUuid, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int stopRecognitionForService(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int unloadSoundModel(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int getModelState(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public SoundTrigger.ModuleProperties getModuleProperties() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTrigger.ModuleProperties) obtain2.readTypedObject(SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int setParameter(ParcelUuid parcelUuid, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int getParameter(ParcelUuid parcelUuid, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public SoundTrigger.ModelParamRange queryParameter(ParcelUuid parcelUuid, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTrigger.ModelParamRange) obtain2.readTypedObject(SoundTrigger.ModelParamRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
