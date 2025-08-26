package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.SrvccCall;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISrvccStartedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISrvccStartedCallback";

    public static class Default implements ISrvccStartedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ISrvccStartedCallback
        public void onSrvccCallNotified(List<SrvccCall> list) throws RemoteException {
        }
    }

    void onSrvccCallNotified(List<SrvccCall> list) throws RemoteException;

    public static abstract class Stub extends Binder implements ISrvccStartedCallback {
        static final int TRANSACTION_onSrvccCallNotified = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISrvccStartedCallback.DESCRIPTOR);
        }

        public static ISrvccStartedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISrvccStartedCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISrvccStartedCallback)) {
                return (ISrvccStartedCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSrvccCallNotified";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISrvccStartedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISrvccStartedCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SrvccCall.CREATOR);
                parcel.enforceNoDataAvail();
                onSrvccCallNotified(arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISrvccStartedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISrvccStartedCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISrvccStartedCallback
            public void onSrvccCallNotified(List<SrvccCall> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISrvccStartedCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
