package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMuteAwaitConnectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.IMuteAwaitConnectionCallback";

    public static class Default implements IMuteAwaitConnectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws RemoteException {
        }

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws RemoteException {
        }
    }

    void dispatchOnMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws RemoteException;

    void dispatchOnUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IMuteAwaitConnectionCallback {
        static final int TRANSACTION_dispatchOnMutedUntilConnection = 1;
        static final int TRANSACTION_dispatchOnUnmutedEvent = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IMuteAwaitConnectionCallback.DESCRIPTOR);
        }

        public static IMuteAwaitConnectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMuteAwaitConnectionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMuteAwaitConnectionCallback)) {
                return (IMuteAwaitConnectionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "dispatchOnMutedUntilConnection";
            }
            if (i != 2) {
                return null;
            }
            return "dispatchOnUnmutedEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMuteAwaitConnectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMuteAwaitConnectionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                dispatchOnMutedUntilConnection(audioDeviceAttributes, createIntArray);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                AudioDeviceAttributes audioDeviceAttributes2 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                int[] createIntArray2 = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                dispatchOnUnmutedEvent(readInt, audioDeviceAttributes2, createIntArray2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMuteAwaitConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMuteAwaitConnectionCallback.DESCRIPTOR;
            }

            @Override // android.media.IMuteAwaitConnectionCallback
            public void dispatchOnMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMuteAwaitConnectionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMuteAwaitConnectionCallback
            public void dispatchOnUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMuteAwaitConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
