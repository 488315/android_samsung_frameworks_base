package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IDialogSubscribeStatusListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IDialogSubscribeStatusListener";

    void onDialogSubscribeStatus(int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDialogSubscribeStatusListener {
        static final int TRANSACTION_onDialogSubscribeStatus = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDialogSubscribeStatusListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDialogSubscribeStatusListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IDialogSubscribeStatusListener
            public void onDialogSubscribeStatus(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDialogSubscribeStatusListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDialogSubscribeStatusListener.DESCRIPTOR);
        }

        public static IDialogSubscribeStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDialogSubscribeStatusListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDialogSubscribeStatusListener)) ? new Proxy(iBinder) : (IDialogSubscribeStatusListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDialogSubscribeStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDialogSubscribeStatusListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            onDialogSubscribeStatus(readInt);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDialogSubscribeStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IDialogSubscribeStatusListener
        public void onDialogSubscribeStatus(int i) {
        }
    }
}
