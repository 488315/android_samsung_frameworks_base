package com.android.internal.app;

import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.app.IHotwordRecognitionStatusCallback;

/* loaded from: classes5.dex */
public interface IVoiceInteractionSoundTriggerSession extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionSoundTriggerSession";

    public static class Default implements IVoiceInteractionSoundTriggerSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public void detach() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public SoundTrigger.ModuleProperties getDspModuleProperties() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int getParameter(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public SoundTrigger.ModelParamRange queryParameter(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int setParameter(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int startRecognition(int i, String str, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int stopRecognition(int i, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws RemoteException {
            return 0;
        }
    }

    void detach() throws RemoteException;

    SoundTrigger.ModuleProperties getDspModuleProperties() throws RemoteException;

    int getParameter(int i, int i2) throws RemoteException;

    SoundTrigger.ModelParamRange queryParameter(int i, int i2) throws RemoteException;

    int setParameter(int i, int i2, int i3) throws RemoteException;

    int startRecognition(int i, String str, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws RemoteException;

    int stopRecognition(int i, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractionSoundTriggerSession {
        static final int TRANSACTION_detach = 7;
        static final int TRANSACTION_getDspModuleProperties = 1;
        static final int TRANSACTION_getParameter = 5;
        static final int TRANSACTION_queryParameter = 6;
        static final int TRANSACTION_setParameter = 4;
        static final int TRANSACTION_startRecognition = 2;
        static final int TRANSACTION_stopRecognition = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
        }

        public static IVoiceInteractionSoundTriggerSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractionSoundTriggerSession)) {
                return (IVoiceInteractionSoundTriggerSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDspModuleProperties";
                case 2:
                    return "startRecognition";
                case 3:
                    return "stopRecognition";
                case 4:
                    return "setParameter";
                case 5:
                    return "getParameter";
                case 6:
                    return "queryParameter";
                case 7:
                    return "detach";
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
                parcel.enforceInterface(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SoundTrigger.ModuleProperties dspModuleProperties = getDspModuleProperties();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dspModuleProperties, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    IHotwordRecognitionStatusCallback asInterface = IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    SoundTrigger.RecognitionConfig recognitionConfig = (SoundTrigger.RecognitionConfig) parcel.readTypedObject(SoundTrigger.RecognitionConfig.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startRecognition = startRecognition(readInt, readString, asInterface, recognitionConfig, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRecognition);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    IHotwordRecognitionStatusCallback asInterface2 = IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopRecognition = stopRecognition(readInt2, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopRecognition);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter = setParameter(readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter2 = getParameter(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter2);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SoundTrigger.ModelParamRange queryParameter = queryParameter(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryParameter, 1);
                    return true;
                case 7:
                    detach();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVoiceInteractionSoundTriggerSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVoiceInteractionSoundTriggerSession.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public SoundTrigger.ModuleProperties getDspModuleProperties() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTrigger.ModuleProperties) obtain2.readTypedObject(SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int startRecognition(int i, String str, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int stopRecognition(int i, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int setParameter(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int getParameter(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public SoundTrigger.ModelParamRange queryParameter(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTrigger.ModelParamRange) obtain2.readTypedObject(SoundTrigger.ModelParamRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public void detach() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
