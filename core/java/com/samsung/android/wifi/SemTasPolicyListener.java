package com.samsung.android.wifi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface SemTasPolicyListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.SemTasPolicyListener";

    void onTasPolicyChanged(int i, int i2) throws RemoteException;

    public static class Default implements SemTasPolicyListener {
        @Override // com.samsung.android.wifi.SemTasPolicyListener
        public void onTasPolicyChanged(int newTasPolicy, int windowSize) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements SemTasPolicyListener {
        static final int TRANSACTION_onTasPolicyChanged = 1;

        public Stub() {
            attachInterface(this, SemTasPolicyListener.DESCRIPTOR);
        }

        public static SemTasPolicyListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(SemTasPolicyListener.DESCRIPTOR);
            if (iin != null && (iin instanceof SemTasPolicyListener)) {
                return (SemTasPolicyListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onTasPolicyChanged";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(SemTasPolicyListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(SemTasPolicyListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onTasPolicyChanged(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements SemTasPolicyListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemTasPolicyListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.SemTasPolicyListener
            public void onTasPolicyChanged(int newTasPolicy, int windowSize) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(SemTasPolicyListener.DESCRIPTOR);
                    _data.writeInt(newTasPolicy);
                    _data.writeInt(windowSize);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
