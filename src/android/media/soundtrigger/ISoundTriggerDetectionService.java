package android.media.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger.ISoundTriggerDetectionServiceClient;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoundTriggerDetectionService extends IInterface {

    public static class Default implements ISoundTriggerDetectionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onError(ParcelUuid parcelUuid, int i, int i2) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onGenericRecognitionEvent(ParcelUuid parcelUuid, int i, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onStopOperation(ParcelUuid parcelUuid, int i) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void removeClient(ParcelUuid parcelUuid) throws RemoteException {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void setClient(ParcelUuid parcelUuid, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws RemoteException {
        }
    }

    void onError(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    void onGenericRecognitionEvent(ParcelUuid parcelUuid, int i, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException;

    void onStopOperation(ParcelUuid parcelUuid, int i) throws RemoteException;

    void removeClient(ParcelUuid parcelUuid) throws RemoteException;

    void setClient(ParcelUuid parcelUuid, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundTriggerDetectionService {
        public static final String DESCRIPTOR = "android.media.soundtrigger.ISoundTriggerDetectionService";
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onGenericRecognitionEvent = 3;
        static final int TRANSACTION_onStopOperation = 5;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_setClient = 1;

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

        public static ISoundTriggerDetectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundTriggerDetectionService)) {
                return (ISoundTriggerDetectionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setClient";
            }
            if (i == 2) {
                return "removeClient";
            }
            if (i == 3) {
                return "onGenericRecognitionEvent";
            }
            if (i == 4) {
                return "onError";
            }
            if (i != 5) {
                return null;
            }
            return "onStopOperation";
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
                ParcelUuid parcelUuid = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                ISoundTriggerDetectionServiceClient asInterface = ISoundTriggerDetectionServiceClient.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setClient(parcelUuid, bundle, asInterface);
            } else if (i == 2) {
                ParcelUuid parcelUuid2 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                parcel.enforceNoDataAvail();
                removeClient(parcelUuid2);
            } else if (i == 3) {
                ParcelUuid parcelUuid3 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                int readInt = parcel.readInt();
                SoundTrigger.GenericRecognitionEvent genericRecognitionEvent = (SoundTrigger.GenericRecognitionEvent) parcel.readTypedObject(SoundTrigger.GenericRecognitionEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onGenericRecognitionEvent(parcelUuid3, readInt, genericRecognitionEvent);
            } else if (i == 4) {
                ParcelUuid parcelUuid4 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(parcelUuid4, readInt2, readInt3);
            } else if (i == 5) {
                ParcelUuid parcelUuid5 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStopOperation(parcelUuid5, readInt4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISoundTriggerDetectionService {
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

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void setClient(ParcelUuid parcelUuid, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iSoundTriggerDetectionServiceClient);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void removeClient(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onGenericRecognitionEvent(ParcelUuid parcelUuid, int i, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(genericRecognitionEvent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onError(ParcelUuid parcelUuid, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onStopOperation(ParcelUuid parcelUuid, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
