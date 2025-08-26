package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsDmConfigListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IImsDmConfigListener";

    void onChangeDmValue(String str, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IImsDmConfigListener {
        static final int TRANSACTION_onChangeDmValue = 1;

        class Proxy implements IImsDmConfigListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsDmConfigListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IImsDmConfigListener
            public void onChangeDmValue(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsDmConfigListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsDmConfigListener.DESCRIPTOR);
        }

        public static IImsDmConfigListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsDmConfigListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsDmConfigListener)) ? new Proxy(iBinder) : (IImsDmConfigListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsDmConfigListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsDmConfigListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            onChangeDmValue(string, z);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IImsDmConfigListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IImsDmConfigListener
        public void onChangeDmValue(String str, boolean z) throws RemoteException {
        }
    }
}
