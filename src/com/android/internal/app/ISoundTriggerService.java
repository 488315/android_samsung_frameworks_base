package com.android.internal.app;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.Identity;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.app.ISoundTriggerSession;
import java.util.List;

/* loaded from: classes5.dex */
public interface ISoundTriggerService extends IInterface {

    public static class Default implements ISoundTriggerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public ISoundTriggerSession attachAsMiddleman(Identity identity, Identity identity2, SoundTrigger.ModuleProperties moduleProperties, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public ISoundTriggerSession attachAsOriginator(Identity identity, SoundTrigger.ModuleProperties moduleProperties, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public void attachInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException {
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public List<SoundTrigger.ModuleProperties> listModuleProperties(Identity identity) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerService
        public void setInPhoneCallState(boolean z) throws RemoteException {
        }
    }

    ISoundTriggerSession attachAsMiddleman(Identity identity, Identity identity2, SoundTrigger.ModuleProperties moduleProperties, IBinder iBinder) throws RemoteException;

    ISoundTriggerSession attachAsOriginator(Identity identity, SoundTrigger.ModuleProperties moduleProperties, IBinder iBinder) throws RemoteException;

    void attachInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException;

    List<SoundTrigger.ModuleProperties> listModuleProperties(Identity identity) throws RemoteException;

    void setInPhoneCallState(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundTriggerService {
        public static final String DESCRIPTOR = "com.android.internal.app.ISoundTriggerService";
        static final int TRANSACTION_attachAsMiddleman = 2;
        static final int TRANSACTION_attachAsOriginator = 1;
        static final int TRANSACTION_attachInjection = 4;
        static final int TRANSACTION_listModuleProperties = 3;
        static final int TRANSACTION_setInPhoneCallState = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundTriggerService)) {
                return (ISoundTriggerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "attachAsOriginator";
            }
            if (i == 2) {
                return "attachAsMiddleman";
            }
            if (i == 3) {
                return "listModuleProperties";
            }
            if (i == 4) {
                return "attachInjection";
            }
            if (i != 5) {
                return null;
            }
            return "setInPhoneCallState";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Identity identity = (Identity) parcel.readTypedObject(Identity.CREATOR);
                SoundTrigger.ModuleProperties moduleProperties = (SoundTrigger.ModuleProperties) parcel.readTypedObject(SoundTrigger.ModuleProperties.CREATOR);
                IBinder strongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                ISoundTriggerSession iSoundTriggerSessionAttachAsOriginator = attachAsOriginator(identity, moduleProperties, strongBinder);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iSoundTriggerSessionAttachAsOriginator);
            } else if (i == 2) {
                Identity identity2 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                Identity identity3 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                SoundTrigger.ModuleProperties moduleProperties2 = (SoundTrigger.ModuleProperties) parcel.readTypedObject(SoundTrigger.ModuleProperties.CREATOR);
                IBinder strongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                ISoundTriggerSession iSoundTriggerSessionAttachAsMiddleman = attachAsMiddleman(identity2, identity3, moduleProperties2, strongBinder2);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iSoundTriggerSessionAttachAsMiddleman);
            } else if (i == 3) {
                Identity identity4 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                parcel.enforceNoDataAvail();
                List<SoundTrigger.ModuleProperties> listListModuleProperties = listModuleProperties(identity4);
                parcel2.writeNoException();
                parcel2.writeTypedList(listListModuleProperties, 1);
            } else if (i == 4) {
                ISoundTriggerInjection iSoundTriggerInjectionAsInterface = ISoundTriggerInjection.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                attachInjection(iSoundTriggerInjectionAsInterface);
                parcel2.writeNoException();
            } else if (i == 5) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setInPhoneCallState(z);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISoundTriggerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public ISoundTriggerSession attachAsOriginator(Identity identity, SoundTrigger.ModuleProperties moduleProperties, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    parcelObtain.writeTypedObject(moduleProperties, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISoundTriggerSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public ISoundTriggerSession attachAsMiddleman(Identity identity, Identity identity2, SoundTrigger.ModuleProperties moduleProperties, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    parcelObtain.writeTypedObject(identity2, 0);
                    parcelObtain.writeTypedObject(moduleProperties, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISoundTriggerSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public List<SoundTrigger.ModuleProperties> listModuleProperties(Identity identity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public void attachInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSoundTriggerInjection);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerService
            public void setInPhoneCallState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
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
