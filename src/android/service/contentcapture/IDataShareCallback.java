package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.contentcapture.IDataShareReadAdapter;
import com.android.internal.telephony.SemRILConstants;

/* loaded from: classes3.dex */
public interface IDataShareCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.contentcapture.IDataShareCallback";

    public static class Default implements IDataShareCallback {
        @Override // android.service.contentcapture.IDataShareCallback
        public void accept(IDataShareReadAdapter iDataShareReadAdapter) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.contentcapture.IDataShareCallback
        public void reject() throws RemoteException {
        }
    }

    void accept(IDataShareReadAdapter iDataShareReadAdapter) throws RemoteException;

    void reject() throws RemoteException;

    public static abstract class Stub extends Binder implements IDataShareCallback {
        static final int TRANSACTION_accept = 1;
        static final int TRANSACTION_reject = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IDataShareCallback.DESCRIPTOR);
        }

        public static IDataShareCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDataShareCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDataShareCallback)) {
                return (IDataShareCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "accept";
            }
            if (i != 2) {
                return null;
            }
            return SemRILConstants.CmcCall.CMC_CALL_SD_REJECT;
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDataShareCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDataShareCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IDataShareReadAdapter iDataShareReadAdapterAsInterface = IDataShareReadAdapter.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                accept(iDataShareReadAdapterAsInterface);
            } else if (i == 2) {
                reject();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDataShareCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataShareCallback.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IDataShareCallback
            public void accept(IDataShareReadAdapter iDataShareReadAdapter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDataShareCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDataShareReadAdapter);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IDataShareCallback
            public void reject() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDataShareCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
