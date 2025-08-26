package com.sec.ims.cmc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICmcDialogListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.cmc.ICmcDialogListener";

    void onNotifyCmcDialog(List<CmcDialog> list) throws RemoteException;

    public abstract class Stub extends Binder implements ICmcDialogListener {
        static final int TRANSACTION_onNotifyCmcDialog = 1;

        class Proxy implements ICmcDialogListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICmcDialogListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.cmc.ICmcDialogListener
            public void onNotifyCmcDialog(List<CmcDialog> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcDialogListener.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICmcDialogListener.DESCRIPTOR);
        }

        public static ICmcDialogListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICmcDialogListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICmcDialogListener)) ? new Proxy(iBinder) : (ICmcDialogListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICmcDialogListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICmcDialogListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CmcDialog.CREATOR);
            parcel.enforceNoDataAvail();
            onNotifyCmcDialog(arrayListCreateTypedArrayList);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements ICmcDialogListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.cmc.ICmcDialogListener
        public void onNotifyCmcDialog(List<CmcDialog> list) throws RemoteException {
        }
    }
}
