package com.samsung.android.wifi.intelligence.ins;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemInsTrService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.ins.ISemInsTrService";

    public static class Default implements ISemInsTrService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemInsTrService {
        public static String getDefaultTransactionName(int i) {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemInsTrService.DESCRIPTOR);
        }

        public static ISemInsTrService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemInsTrService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemInsTrService)) {
                return (ISemInsTrService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(ISemInsTrService.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemInsTrService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInsTrService.DESCRIPTOR;
            }
        }
    }
}
