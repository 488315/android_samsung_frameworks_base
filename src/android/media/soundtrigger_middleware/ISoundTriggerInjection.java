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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISoundTriggerInjection.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundTriggerInjection)) {
                return (ISoundTriggerInjection) iInterfaceQueryLocalInterface;
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
                    IInjectGlobalEvent iInjectGlobalEventAsInterface = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerGlobalEventInjection(iInjectGlobalEventAsInterface);
                    return true;
                case 2:
                    IInjectGlobalEvent iInjectGlobalEventAsInterface2 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRestarted(iInjectGlobalEventAsInterface2);
                    return true;
                case 3:
                    IInjectGlobalEvent iInjectGlobalEventAsInterface3 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFrameworkDetached(iInjectGlobalEventAsInterface3);
                    return true;
                case 4:
                    IBinder strongBinder = parcel.readStrongBinder();
                    IInjectGlobalEvent iInjectGlobalEventAsInterface4 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onClientAttached(strongBinder, iInjectGlobalEventAsInterface4);
                    return true;
                case 5:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onClientDetached(strongBinder2);
                    return true;
                case 6:
                    SoundModel soundModel = (SoundModel) parcel.readTypedObject(SoundModel.CREATOR);
                    Phrase[] phraseArr = (Phrase[]) parcel.createTypedArray(Phrase.CREATOR);
                    IInjectModelEvent iInjectModelEventAsInterface = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    IInjectGlobalEvent iInjectGlobalEventAsInterface5 = IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSoundModelLoaded(soundModel, phraseArr, iInjectModelEventAsInterface, iInjectGlobalEventAsInterface5);
                    return true;
                case 7:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    IInjectModelEvent iInjectModelEventAsInterface2 = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onParamSet(i3, i4, iInjectModelEventAsInterface2);
                    return true;
                case 8:
                    int i5 = parcel.readInt();
                    RecognitionConfig recognitionConfig = (RecognitionConfig) parcel.readTypedObject(RecognitionConfig.CREATOR);
                    IInjectRecognitionEvent iInjectRecognitionEventAsInterface = IInjectRecognitionEvent.Stub.asInterface(parcel.readStrongBinder());
                    IInjectModelEvent iInjectModelEventAsInterface3 = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRecognitionStarted(i5, recognitionConfig, iInjectRecognitionEventAsInterface, iInjectModelEventAsInterface3);
                    return true;
                case 9:
                    IInjectRecognitionEvent iInjectRecognitionEventAsInterface2 = IInjectRecognitionEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRecognitionStopped(iInjectRecognitionEventAsInterface2);
                    return true;
                case 10:
                    IInjectModelEvent iInjectModelEventAsInterface4 = IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSoundModelUnloaded(iInjectModelEventAsInterface4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onClientDetached(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onSoundModelLoaded(SoundModel soundModel, Phrase[] phraseArr, IInjectModelEvent iInjectModelEvent, IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeTypedObject(soundModel, 0);
                    parcelObtain.writeTypedArray(phraseArr, 0);
                    parcelObtain.writeStrongInterface(iInjectModelEvent);
                    parcelObtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onParamSet(int i, int i2, IInjectModelEvent iInjectModelEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRecognitionStarted(int i, RecognitionConfig recognitionConfig, IInjectRecognitionEvent iInjectRecognitionEvent, IInjectModelEvent iInjectModelEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(recognitionConfig, 0);
                    parcelObtain.writeStrongInterface(iInjectRecognitionEvent);
                    parcelObtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInjectRecognitionEvent);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onPreempted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerInjection.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
