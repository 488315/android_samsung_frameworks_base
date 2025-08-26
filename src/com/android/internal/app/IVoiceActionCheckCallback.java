package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IVoiceActionCheckCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVoiceActionCheckCallback";

    public static class Default implements IVoiceActionCheckCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVoiceActionCheckCallback
        public void onComplete(List<String> list) throws RemoteException {
        }
    }

    void onComplete(List<String> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceActionCheckCallback {
        static final int TRANSACTION_onComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IVoiceActionCheckCallback.DESCRIPTOR);
        }

        public static IVoiceActionCheckCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVoiceActionCheckCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVoiceActionCheckCallback)) {
                return (IVoiceActionCheckCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVoiceActionCheckCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVoiceActionCheckCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                onComplete(arrayListCreateStringArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IVoiceActionCheckCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVoiceActionCheckCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceActionCheckCallback
            public void onComplete(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVoiceActionCheckCallback.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
