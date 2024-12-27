package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.IDomainSelector;
import com.android.internal.telephony.ITransportSelectorResultCallback;

/* loaded from: classes5.dex */
public interface ITransportSelectorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ITransportSelectorCallback";

    void onCreated(IDomainSelector iDomainSelector) throws RemoteException;

    void onSelectionTerminated(int i) throws RemoteException;

    void onWlanSelected(boolean z) throws RemoteException;

    void onWwanSelectedAsync(ITransportSelectorResultCallback iTransportSelectorResultCallback) throws RemoteException;

    public static class Default implements ITransportSelectorCallback {
        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onCreated(IDomainSelector selector) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onWlanSelected(boolean useEmergencyPdn) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onWwanSelectedAsync(ITransportSelectorResultCallback cb) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onSelectionTerminated(int cause) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITransportSelectorCallback {
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onSelectionTerminated = 4;
        static final int TRANSACTION_onWlanSelected = 2;
        static final int TRANSACTION_onWwanSelectedAsync = 3;

        public Stub() {
            attachInterface(this, ITransportSelectorCallback.DESCRIPTOR);
        }

        public static ITransportSelectorCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITransportSelectorCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ITransportSelectorCallback)) {
                return (ITransportSelectorCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onCreated";
                case 2:
                    return "onWlanSelected";
                case 3:
                    return "onWwanSelectedAsync";
                case 4:
                    return "onSelectionTerminated";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITransportSelectorCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITransportSelectorCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IDomainSelector _arg0 = IDomainSelector.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onCreated(_arg0);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onWlanSelected(_arg02);
                    return true;
                case 3:
                    ITransportSelectorResultCallback _arg03 = ITransportSelectorResultCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onWwanSelectedAsync(_arg03);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onSelectionTerminated(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITransportSelectorCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransportSelectorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onCreated(IDomainSelector selector) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    _data.writeStrongInterface(selector);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onWlanSelected(boolean useEmergencyPdn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    _data.writeBoolean(useEmergencyPdn);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onWwanSelectedAsync(ITransportSelectorResultCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onSelectionTerminated(int cause) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITransportSelectorCallback.DESCRIPTOR);
                    _data.writeInt(cause);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
