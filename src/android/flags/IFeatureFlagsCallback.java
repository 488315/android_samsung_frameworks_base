package android.flags;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IFeatureFlagsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.flags.IFeatureFlagsCallback";

    public static class Default implements IFeatureFlagsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.flags.IFeatureFlagsCallback
        public void onFlagChange(SyncableFlag syncableFlag) throws RemoteException {
        }
    }

    void onFlagChange(SyncableFlag syncableFlag) throws RemoteException;

    public static abstract class Stub extends Binder implements IFeatureFlagsCallback {
        static final int TRANSACTION_onFlagChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IFeatureFlagsCallback.DESCRIPTOR);
        }

        public static IFeatureFlagsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFeatureFlagsCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFeatureFlagsCallback)) {
                return (IFeatureFlagsCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onFlagChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFeatureFlagsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFeatureFlagsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SyncableFlag syncableFlag = (SyncableFlag) parcel.readTypedObject(SyncableFlag.CREATOR);
                parcel.enforceNoDataAvail();
                onFlagChange(syncableFlag);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IFeatureFlagsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFeatureFlagsCallback.DESCRIPTOR;
            }

            @Override // android.flags.IFeatureFlagsCallback
            public void onFlagChange(SyncableFlag syncableFlag) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFeatureFlagsCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(syncableFlag, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
