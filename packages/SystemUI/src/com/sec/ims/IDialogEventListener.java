package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDialogEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IDialogEventListener";

    void onDialogEvent(DialogEvent dialogEvent) throws RemoteException;

    public abstract class Stub extends Binder implements IDialogEventListener {
        static final int TRANSACTION_onDialogEvent = 1;

        class Proxy implements IDialogEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDialogEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IDialogEventListener
            public void onDialogEvent(DialogEvent dialogEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDialogEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dialogEvent, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDialogEventListener.DESCRIPTOR);
        }

        public static IDialogEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDialogEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDialogEventListener)) ? new Proxy(iBinder) : (IDialogEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDialogEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDialogEventListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            DialogEvent dialogEvent = (DialogEvent) parcel.readTypedObject(DialogEvent.CREATOR);
            parcel.enforceNoDataAvail();
            onDialogEvent(dialogEvent);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IDialogEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IDialogEventListener
        public void onDialogEvent(DialogEvent dialogEvent) throws RemoteException {
        }
    }
}
