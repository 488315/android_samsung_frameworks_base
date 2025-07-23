package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.SipDialogState;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISipDialogStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISipDialogStateCallback";

    public static class Default implements ISipDialogStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ISipDialogStateCallback
        public void onActiveSipDialogsChanged(List<SipDialogState> list) throws RemoteException {
        }
    }

    void onActiveSipDialogsChanged(List<SipDialogState> list) throws RemoteException;

    public static abstract class Stub extends Binder implements ISipDialogStateCallback {
        static final int TRANSACTION_onActiveSipDialogsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISipDialogStateCallback.DESCRIPTOR);
        }

        public static ISipDialogStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISipDialogStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISipDialogStateCallback)) {
                return (ISipDialogStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onActiveSipDialogsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISipDialogStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISipDialogStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(SipDialogState.CREATOR);
                parcel.enforceNoDataAvail();
                onActiveSipDialogsChanged(createTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISipDialogStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDialogStateCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISipDialogStateCallback
            public void onActiveSipDialogsChanged(List<SipDialogState> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDialogStateCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
