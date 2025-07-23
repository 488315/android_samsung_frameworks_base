package android.media.soundtrigger_middleware;

import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.media.soundtrigger_middleware.IInjectModelEvent;
import android.media.soundtrigger_middleware.IInjectRecognitionEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoundTriggerInjection extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerInjection";
    public static final String FAKE_HAL_ARCH = "injection";

    public static class Default implements ISoundTriggerInjection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientDetached(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onParamSet(int i, int i2, IInjectModelEvent iInjectModelEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onPreempted() throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStarted(int i, RecognitionConfig recognitionConfig, IInjectRecognitionEvent iInjectRecognitionEvent, IInjectModelEvent iInjectModelEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelLoaded(SoundModel soundModel, Phrase[] phraseArr, IInjectModelEvent iInjectModelEvent, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void registerGlobalEventInjection(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
        }
    }

    void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

    void onClientDetached(IBinder iBinder) throws RemoteException;

    void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

    void onParamSet(int i, int i2, IInjectModelEvent iInjectModelEvent) throws RemoteException;

    void onPreempted() throws RemoteException;

    void onRecognitionStarted(int i, RecognitionConfig recognitionConfig, IInjectRecognitionEvent iInjectRecognitionEvent, IInjectModelEvent iInjectModelEvent) throws RemoteException;

    void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) throws RemoteException;

    void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

    void onSoundModelLoaded(SoundModel soundModel, Phrase[] phraseArr, IInjectModelEvent iInjectModelEvent, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

    void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) throws RemoteException;

    void registerGlobalEventInjection(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundTriggerInjection {
        static final int TRANSACTION_onClientAttached = 4;
        static final int TRANSACTION_onClientDetached = 5;
        static final int TRANSACTION_onFrameworkDetached = 3;
        static final int TRANSACTION_onParamSet = 7;
        static final int TRANSACTION_onPreempted = 11;
        static final int TRANSACTION_onRecognitionStarted = 8;
        static final int TRANSACTION_onRecognitionStopped = 9;
        static final int TRANSACTION_onRestarted = 2;
        static final int TRANSACTION_onSoundModelLoaded = 6;
        static final int TRANSACTION_onSoundModelUnloaded = 10;
        static final int TRANSACTION_registerGlobalEventInjection = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISoundTriggerInjection.DESCRIPTOR);
        }

        public static ISoundTriggerInjection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISoundTriggerInjection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerInjection)) {
                return (ISoundTriggerInjection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISoundTriggerInjection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundTriggerInjection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IInjectGlobalEvent asInterface = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerGlobalEventInjection(asInterface);
                    return true;
                case 2:
                    IInjectGlobalEvent asInterface2 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRestarted(asInterface2);
                    return true;
                case 3:
                    IInjectGlobalEvent asInterface3 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFrameworkDetached(asInterface3);
                    return true;
                case 4:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IInjectGlobalEvent asInterface4 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onClientAttached(readStrongBinder, asInterface4);
                    return true;
                case 5:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onClientDetached(readStrongBinder2);
                    return true;
                case 6:
                    SoundModel soundModel = (SoundModel) parcel.readTypedObject(SoundModel.CREATOR);
                    Phrase[] phraseArr = (Phrase[]) parcel.createTypedArray(Phrase.CREATOR);
                    IInjectModelEvent asInterface5 = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    IInjectGlobalEvent asInterface6 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSoundModelLoaded(soundModel, phraseArr, asInterface5, asInterface6);
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    IInjectModelEvent asInterface7 = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onParamSet(readInt, readInt2, asInterface7);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    RecognitionConfig recognitionConfig = (RecognitionConfig) parcel.readTypedObject(RecognitionConfig.CREATOR);
                    IInjectRecognitionEvent asInterface8 = IInjectRecognitionEvent.Stub.asInterface(parcel.readStrongBinder());
                    IInjectModelEvent asInterface9 = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRecognitionStarted(readInt3, recognitionConfig, asInterface8, asInterface9);
                    return true;
                case 9:
                    IInjectRecognitionEvent asInterface10 = IInjectRecognitionEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRecognitionStopped(asInterface10);
                    return true;
                case 10:
                    IInjectModelEvent asInterface11 = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSoundModelUnloaded(asInterface11);
                    return true;
                case 11:
                    onPreempted();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISoundTriggerInjection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundTriggerInjection.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void registerGlobalEventInjection(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onClientDetached(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onSoundModelLoaded(SoundModel soundModel, Phrase[] phraseArr, IInjectModelEvent iInjectModelEvent, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeTypedObject(soundModel, 0);
                    obtain.writeTypedArray(phraseArr, 0);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onParamSet(int i, int i2, IInjectModelEvent iInjectModelEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRecognitionStarted(int i, RecognitionConfig recognitionConfig, IInjectRecognitionEvent iInjectRecognitionEvent, IInjectModelEvent iInjectModelEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    obtain.writeStrongInterface(iInjectRecognitionEvent);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectRecognitionEvent);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onPreempted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
