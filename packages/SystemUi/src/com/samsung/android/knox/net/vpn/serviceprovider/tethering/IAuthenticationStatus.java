package com.samsung.android.knox.net.vpn.serviceprovider.tethering;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IAuthenticationStatus extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus";

    void getStatus(int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IAuthenticationStatus {
        public static final int TRANSACTION_getStatus = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IAuthenticationStatus {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IAuthenticationStatus.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus
            public final void getStatus(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IAuthenticationStatus.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAuthenticationStatus.DESCRIPTOR);
        }

        public static IAuthenticationStatus asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuthenticationStatus.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IAuthenticationStatus)) ? new Proxy(iBinder) : (IAuthenticationStatus) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAuthenticationStatus.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuthenticationStatus.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            getStatus(readInt);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IAuthenticationStatus {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus
        public final void getStatus(int i) {
        }
    }
}
