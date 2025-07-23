package android.media.soundtrigger_middleware;

import android.media.permission.Identity;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoundTriggerMiddlewareService extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService";

    public static class Default implements ISoundTriggerMiddlewareService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public ISoundTriggerModule attachAsMiddleman(int i, Identity identity, Identity identity2, ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public ISoundTriggerModule attachAsOriginator(int i, Identity identity, ISoundTriggerCallback iSoundTriggerCallback) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public void attachFakeHalInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public SoundTriggerModuleDescriptor[] listModulesAsMiddleman(Identity identity, Identity identity2) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public SoundTriggerModuleDescriptor[] listModulesAsOriginator(Identity identity) throws RemoteException {
            return null;
        }
    }

    ISoundTriggerModule attachAsMiddleman(int i, Identity identity, Identity identity2, ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws RemoteException;

    ISoundTriggerModule attachAsOriginator(int i, Identity identity, ISoundTriggerCallback iSoundTriggerCallback) throws RemoteException;

    void attachFakeHalInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException;

    SoundTriggerModuleDescriptor[] listModulesAsMiddleman(Identity identity, Identity identity2) throws RemoteException;

    SoundTriggerModuleDescriptor[] listModulesAsOriginator(Identity identity) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundTriggerMiddlewareService {
        static final int TRANSACTION_attachAsMiddleman = 4;
        static final int TRANSACTION_attachAsOriginator = 3;
        static final int TRANSACTION_attachFakeHalInjection = 5;
        static final int TRANSACTION_listModulesAsMiddleman = 2;
        static final int TRANSACTION_listModulesAsOriginator = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISoundTriggerMiddlewareService.DESCRIPTOR);
        }

        public static ISoundTriggerMiddlewareService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISoundTriggerMiddlewareService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerMiddlewareService)) {
                return (ISoundTriggerMiddlewareService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISoundTriggerMiddlewareService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundTriggerMiddlewareService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Identity identity = (Identity) parcel.readTypedObject(Identity.CREATOR);
                parcel.enforceNoDataAvail();
                SoundTriggerModuleDescriptor[] listModulesAsOriginator = listModulesAsOriginator(identity);
                parcel2.writeNoException();
                parcel2.writeTypedArray(listModulesAsOriginator, 1);
            } else if (i == 2) {
                Identity identity2 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                Identity identity3 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                parcel.enforceNoDataAvail();
                SoundTriggerModuleDescriptor[] listModulesAsMiddleman = listModulesAsMiddleman(identity2, identity3);
                parcel2.writeNoException();
                parcel2.writeTypedArray(listModulesAsMiddleman, 1);
            } else if (i == 3) {
                int readInt = parcel.readInt();
                Identity identity4 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                ISoundTriggerCallback asInterface = ISoundTriggerCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ISoundTriggerModule attachAsOriginator = attachAsOriginator(readInt, identity4, asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(attachAsOriginator);
            } else if (i == 4) {
                int readInt2 = parcel.readInt();
                Identity identity5 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                Identity identity6 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                ISoundTriggerCallback asInterface2 = ISoundTriggerCallback.Stub.asInterface(parcel.readStrongBinder());
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                ISoundTriggerModule attachAsMiddleman = attachAsMiddleman(readInt2, identity5, identity6, asInterface2, readBoolean);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(attachAsMiddleman);
            } else if (i == 5) {
                ISoundTriggerInjection asInterface3 = ISoundTriggerInjection.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                attachFakeHalInjection(asInterface3);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISoundTriggerMiddlewareService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundTriggerMiddlewareService.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public SoundTriggerModuleDescriptor[] listModulesAsOriginator(Identity identity) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTriggerModuleDescriptor[]) obtain2.createTypedArray(SoundTriggerModuleDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public SoundTriggerModuleDescriptor[] listModulesAsMiddleman(Identity identity, Identity identity2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(identity2, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTriggerModuleDescriptor[]) obtain2.createTypedArray(SoundTriggerModuleDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public ISoundTriggerModule attachAsOriginator(int i, Identity identity, ISoundTriggerCallback iSoundTriggerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeStrongInterface(iSoundTriggerCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISoundTriggerModule.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public ISoundTriggerModule attachAsMiddleman(int i, Identity identity, Identity identity2, ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(identity2, 0);
                    obtain.writeStrongInterface(iSoundTriggerCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISoundTriggerModule.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public void attachFakeHalInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSoundTriggerInjection);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
