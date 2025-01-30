package com.sec.ims.p052ft;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IImsOngoingFtEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ft.IImsOngoingFtEventListener";

    void onFtStateChanged(boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IImsOngoingFtEventListener {
        static final int TRANSACTION_onFtStateChanged = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IImsOngoingFtEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsOngoingFtEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.p052ft.IImsOngoingFtEventListener
            public void onFtStateChanged(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsOngoingFtEventListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsOngoingFtEventListener.DESCRIPTOR);
        }

        public static IImsOngoingFtEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsOngoingFtEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsOngoingFtEventListener)) ? new Proxy(iBinder) : (IImsOngoingFtEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsOngoingFtEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsOngoingFtEventListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            onFtStateChanged(readBoolean);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IImsOngoingFtEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.p052ft.IImsOngoingFtEventListener
        public void onFtStateChanged(boolean z) {
        }
    }
}
