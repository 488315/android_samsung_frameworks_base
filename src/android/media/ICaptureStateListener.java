package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICaptureStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.ICaptureStateListener";

    public static class Default implements ICaptureStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ICaptureStateListener
        public void setCaptureState(boolean z) throws RemoteException {
        }
    }

    void setCaptureState(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICaptureStateListener {
        static final int TRANSACTION_setCaptureState = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ICaptureStateListener.DESCRIPTOR);
        }

        public static ICaptureStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICaptureStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICaptureStateListener)) {
                return (ICaptureStateListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICaptureStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICaptureStateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setCaptureState(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICaptureStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICaptureStateListener.DESCRIPTOR;
            }

            @Override // android.media.ICaptureStateListener
            public void setCaptureState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICaptureStateListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
