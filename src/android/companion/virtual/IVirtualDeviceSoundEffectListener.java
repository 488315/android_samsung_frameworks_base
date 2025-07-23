package android.companion.virtual;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IVirtualDeviceSoundEffectListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceSoundEffectListener";

    public static class Default implements IVirtualDeviceSoundEffectListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceSoundEffectListener
        public void onPlaySoundEffect(int i) throws RemoteException {
        }
    }

    void onPlaySoundEffect(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDeviceSoundEffectListener {
        static final int TRANSACTION_onPlaySoundEffect = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IVirtualDeviceSoundEffectListener.DESCRIPTOR);
        }

        public static IVirtualDeviceSoundEffectListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualDeviceSoundEffectListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDeviceSoundEffectListener)) {
                return (IVirtualDeviceSoundEffectListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onPlaySoundEffect";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualDeviceSoundEffectListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDeviceSoundEffectListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onPlaySoundEffect(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IVirtualDeviceSoundEffectListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceSoundEffectListener.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceSoundEffectListener
            public void onPlaySoundEffect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceSoundEffectListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
