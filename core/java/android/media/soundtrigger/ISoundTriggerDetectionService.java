package android.media.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;

public interface ISoundTriggerDetectionService extends IInterface {
    void onError(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    void onGenericRecognitionEvent(
            ParcelUuid parcelUuid,
            int i,
            SoundTrigger.GenericRecognitionEvent genericRecognitionEvent)
            throws RemoteException;

    void onStopOperation(ParcelUuid parcelUuid, int i) throws RemoteException;

    void removeClient(ParcelUuid parcelUuid) throws RemoteException;

    void setClient(
            ParcelUuid parcelUuid,
            Bundle bundle,
            ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient)
            throws RemoteException;

    public static class Default implements ISoundTriggerDetectionService {
        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void setClient(
                ParcelUuid uuid, Bundle params, ISoundTriggerDetectionServiceClient client)
                throws RemoteException {}

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void removeClient(ParcelUuid uuid) throws RemoteException {}

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onGenericRecognitionEvent(
                ParcelUuid uuid, int opId, SoundTrigger.GenericRecognitionEvent event)
                throws RemoteException {}

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onError(ParcelUuid uuid, int opId, int status) throws RemoteException {}

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onStopOperation(ParcelUuid uuid, int opId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISoundTriggerDetectionService {
        public static final String DESCRIPTOR =
                "android.media.soundtrigger.ISoundTriggerDetectionService";
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onGenericRecognitionEvent = 3;
        static final int TRANSACTION_onStopOperation = 5;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_setClient = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerDetectionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISoundTriggerDetectionService)) {
                return (ISoundTriggerDetectionService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setClient";
                case 2:
                    return "removeClient";
                case 3:
                    return "onGenericRecognitionEvent";
                case 4:
                    return "onError";
                case 5:
                    return "onStopOperation";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelUuid _arg0 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    ISoundTriggerDetectionServiceClient _arg2 =
                            ISoundTriggerDetectionServiceClient.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setClient(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    ParcelUuid _arg02 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    data.enforceNoDataAvail();
                    removeClient(_arg02);
                    return true;
                case 3:
                    ParcelUuid _arg03 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    int _arg12 = data.readInt();
                    SoundTrigger.GenericRecognitionEvent _arg22 =
                            (SoundTrigger.GenericRecognitionEvent)
                                    data.readTypedObject(
                                            SoundTrigger.GenericRecognitionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onGenericRecognitionEvent(_arg03, _arg12, _arg22);
                    return true;
                case 4:
                    ParcelUuid _arg04 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    int _arg13 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    onError(_arg04, _arg13, _arg23);
                    return true;
                case 5:
                    ParcelUuid _arg05 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    onStopOperation(_arg05, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISoundTriggerDetectionService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void setClient(
                    ParcelUuid uuid, Bundle params, ISoundTriggerDetectionServiceClient client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uuid, 0);
                    _data.writeTypedObject(params, 0);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void removeClient(ParcelUuid uuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uuid, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onGenericRecognitionEvent(
                    ParcelUuid uuid, int opId, SoundTrigger.GenericRecognitionEvent event)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uuid, 0);
                    _data.writeInt(opId);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onError(ParcelUuid uuid, int opId, int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uuid, 0);
                    _data.writeInt(opId);
                    _data.writeInt(status);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger.ISoundTriggerDetectionService
            public void onStopOperation(ParcelUuid uuid, int opId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uuid, 0);
                    _data.writeInt(opId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
