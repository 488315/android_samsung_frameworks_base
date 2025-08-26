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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISoundTriggerMiddlewareService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundTriggerMiddlewareService)) {
                return (ISoundTriggerMiddlewareService) iInterfaceQueryLocalInterface;
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
                SoundTriggerModuleDescriptor[] soundTriggerModuleDescriptorArrListModulesAsOriginator = listModulesAsOriginator(identity);
                parcel2.writeNoException();
                parcel2.writeTypedArray(soundTriggerModuleDescriptorArrListModulesAsOriginator, 1);
            } else if (i == 2) {
                Identity identity2 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                Identity identity3 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                parcel.enforceNoDataAvail();
                SoundTriggerModuleDescriptor[] soundTriggerModuleDescriptorArrListModulesAsMiddleman = listModulesAsMiddleman(identity2, identity3);
                parcel2.writeNoException();
                parcel2.writeTypedArray(soundTriggerModuleDescriptorArrListModulesAsMiddleman, 1);
            } else if (i == 3) {
                int i3 = parcel.readInt();
                Identity identity4 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                ISoundTriggerCallback iSoundTriggerCallbackAsInterface = ISoundTriggerCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ISoundTriggerModule iSoundTriggerModuleAttachAsOriginator = attachAsOriginator(i3, identity4, iSoundTriggerCallbackAsInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iSoundTriggerModuleAttachAsOriginator);
            } else if (i == 4) {
                int i4 = parcel.readInt();
                Identity identity5 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                Identity identity6 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                ISoundTriggerCallback iSoundTriggerCallbackAsInterface2 = ISoundTriggerCallback.Stub.asInterface(parcel.readStrongBinder());
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                ISoundTriggerModule iSoundTriggerModuleAttachAsMiddleman = attachAsMiddleman(i4, identity5, identity6, iSoundTriggerCallbackAsInterface2, z);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iSoundTriggerModuleAttachAsMiddleman);
            } else if (i == 5) {
                ISoundTriggerInjection iSoundTriggerInjectionAsInterface = ISoundTriggerInjection.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                attachFakeHalInjection(iSoundTriggerInjectionAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SoundTriggerModuleDescriptor[]) parcelObtain2.createTypedArray(SoundTriggerModuleDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public SoundTriggerModuleDescriptor[] listModulesAsMiddleman(Identity identity, Identity identity2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    parcelObtain.writeTypedObject(identity2, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SoundTriggerModuleDescriptor[]) parcelObtain2.createTypedArray(SoundTriggerModuleDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public ISoundTriggerModule attachAsOriginator(int i, Identity identity, ISoundTriggerCallback iSoundTriggerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(identity, 0);
                    parcelObtain.writeStrongInterface(iSoundTriggerCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISoundTriggerModule.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public ISoundTriggerModule attachAsMiddleman(int i, Identity identity, Identity identity2, ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(identity, 0);
                    parcelObtain.writeTypedObject(identity2, 0);
                    parcelObtain.writeStrongInterface(iSoundTriggerCallback);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISoundTriggerModule.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public void attachFakeHalInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSoundTriggerInjection);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
