package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMuteAwaitConnectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.IMuteAwaitConnectionCallback";

    void dispatchOnMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr)
            throws RemoteException;

    void dispatchOnUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr)
            throws RemoteException;

    public static class Default implements IMuteAwaitConnectionCallback {
        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnMutedUntilConnection(AudioDeviceAttributes device, int[] mutedUsages)
                throws RemoteException {}

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnUnmutedEvent(
                int event, AudioDeviceAttributes device, int[] mutedUsages)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMuteAwaitConnectionCallback {
        static final int TRANSACTION_dispatchOnMutedUntilConnection = 1;
        static final int TRANSACTION_dispatchOnUnmutedEvent = 2;

        public Stub() {
            attachInterface(this, IMuteAwaitConnectionCallback.DESCRIPTOR);
        }

        public static IMuteAwaitConnectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMuteAwaitConnectionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IMuteAwaitConnectionCallback)) {
                return (IMuteAwaitConnectionCallback) iin;
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
                    return "dispatchOnMutedUntilConnection";
                case 2:
                    return "dispatchOnUnmutedEvent";
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
                data.enforceInterface(IMuteAwaitConnectionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMuteAwaitConnectionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AudioDeviceAttributes _arg0 =
                            (AudioDeviceAttributes)
                                    data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    int[] _arg1 = data.createIntArray();
                    data.enforceNoDataAvail();
                    dispatchOnMutedUntilConnection(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    AudioDeviceAttributes _arg12 =
                            (AudioDeviceAttributes)
                                    data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    int[] _arg2 = data.createIntArray();
                    data.enforceNoDataAvail();
                    dispatchOnUnmutedEvent(_arg02, _arg12, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMuteAwaitConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMuteAwaitConnectionCallback.DESCRIPTOR;
            }

            @Override // android.media.IMuteAwaitConnectionCallback
            public void dispatchOnMutedUntilConnection(
                    AudioDeviceAttributes device, int[] mutedUsages) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMuteAwaitConnectionCallback.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeIntArray(mutedUsages);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IMuteAwaitConnectionCallback
            public void dispatchOnUnmutedEvent(
                    int event, AudioDeviceAttributes device, int[] mutedUsages)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMuteAwaitConnectionCallback.DESCRIPTOR);
                    _data.writeInt(event);
                    _data.writeTypedObject(device, 0);
                    _data.writeIntArray(mutedUsages);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
