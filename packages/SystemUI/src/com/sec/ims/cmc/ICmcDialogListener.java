package com.sec.ims.cmc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ICmcDialogListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.cmc.ICmcDialogListener";

    void onNotifyCmcDialog(List<CmcDialog> list) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ICmcDialogListener {
        static final int TRANSACTION_onNotifyCmcDialog = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICmcDialogListener.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICmcDialogListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICmcDialogListener)) ? new Proxy(iBinder) : (ICmcDialogListener) queryLocalInterface;
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
            ArrayList createTypedArrayList = parcel.createTypedArrayList(CmcDialog.CREATOR);
            parcel.enforceNoDataAvail();
            onNotifyCmcDialog(createTypedArrayList);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
