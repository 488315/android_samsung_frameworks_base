package com.sec.ims.openapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISipDialogListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.openapi.ISipDialogListener";

    void onSipParamsReceived(int i, String str, boolean z) throws RemoteException;

    void onSipReceived(String str) throws RemoteException;

    public abstract class Stub extends Binder implements ISipDialogListener {
        static final int TRANSACTION_onSipParamsReceived = 2;
        static final int TRANSACTION_onSipReceived = 1;

        class Proxy implements ISipDialogListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDialogListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.openapi.ISipDialogListener
            public void onSipParamsReceived(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISipDialogListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.ISipDialogListener
            public void onSipReceived(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISipDialogListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISipDialogListener.DESCRIPTOR);
        }

        public static ISipDialogListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISipDialogListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISipDialogListener)) ? new Proxy(iBinder) : (ISipDialogListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISipDialogListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISipDialogListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onSipReceived(string);
                parcel2.writeNoException();
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int i3 = parcel.readInt();
                String string2 = parcel.readString();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSipParamsReceived(i3, string2, z);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements ISipDialogListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.openapi.ISipDialogListener
        public void onSipReceived(String str) throws RemoteException {
        }

        @Override // com.sec.ims.openapi.ISipDialogListener
        public void onSipParamsReceived(int i, String str, boolean z) throws RemoteException {
        }
    }
}
