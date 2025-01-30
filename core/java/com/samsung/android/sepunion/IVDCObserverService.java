package com.samsung.android.sepunion;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IVDCObserverService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IVDCObserverService";

    public static class Default implements IVDCObserverService {
        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVDCObserverService {
        public Stub() {
            attachInterface(this, IVDCObserverService.DESCRIPTOR);
        }

        public static IVDCObserverService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVDCObserverService.DESCRIPTOR);
            if (iin != null && (iin instanceof IVDCObserverService)) {
                return (IVDCObserverService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            return null;
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IVDCObserverService.DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVDCObserverService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVDCObserverService.DESCRIPTOR;
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
