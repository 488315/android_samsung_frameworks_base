package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IOnChecksumsReadyListener extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IOnChecksumsReadyListener";

    public static class Default implements IOnChecksumsReadyListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IOnChecksumsReadyListener
        public void onChecksumsReady(List<ApkChecksum> list) throws RemoteException {
        }
    }

    void onChecksumsReady(List<ApkChecksum> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnChecksumsReadyListener {
        static final int TRANSACTION_onChecksumsReady = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOnChecksumsReadyListener.DESCRIPTOR);
        }

        public static IOnChecksumsReadyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IOnChecksumsReadyListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOnChecksumsReadyListener)) {
                return (IOnChecksumsReadyListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onChecksumsReady";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnChecksumsReadyListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnChecksumsReadyListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ApkChecksum.CREATOR);
                parcel.enforceNoDataAvail();
                onChecksumsReady(arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOnChecksumsReadyListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnChecksumsReadyListener.DESCRIPTOR;
            }

            @Override // android.content.pm.IOnChecksumsReadyListener
            public void onChecksumsReady(List<ApkChecksum> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnChecksumsReadyListener.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
