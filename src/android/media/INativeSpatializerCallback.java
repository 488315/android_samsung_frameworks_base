package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface INativeSpatializerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.INativeSpatializerCallback";

    public static class Default implements INativeSpatializerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.INativeSpatializerCallback
        public void onLevelChanged(byte b) throws RemoteException {
        }

        @Override // android.media.INativeSpatializerCallback
        public void onOutputChanged(int i) throws RemoteException {
        }
    }

    void onLevelChanged(byte b) throws RemoteException;

    void onOutputChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements INativeSpatializerCallback {
        static final int TRANSACTION_onLevelChanged = 1;
        static final int TRANSACTION_onOutputChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, INativeSpatializerCallback.DESCRIPTOR);
        }

        public static INativeSpatializerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(INativeSpatializerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INativeSpatializerCallback)) {
                return (INativeSpatializerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INativeSpatializerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INativeSpatializerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte b = parcel.readByte();
                parcel.enforceNoDataAvail();
                onLevelChanged(b);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onOutputChanged(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements INativeSpatializerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INativeSpatializerCallback.DESCRIPTOR;
            }

            @Override // android.media.INativeSpatializerCallback
            public void onLevelChanged(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(INativeSpatializerCallback.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.INativeSpatializerCallback
            public void onOutputChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(INativeSpatializerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
