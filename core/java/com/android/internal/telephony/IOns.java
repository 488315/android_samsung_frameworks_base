package com.android.internal.telephony;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.telephony.AvailableNetworkInfo;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.IUpdateAvailableNetworksCallback;
import java.util.List;

/* loaded from: classes5.dex */
public interface IOns extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IOns";

    int getPreferredDataSubscriptionId(String str, String str2) throws RemoteException;

    boolean isEnabled(String str) throws RemoteException;

    boolean setEnable(boolean z, String str) throws RemoteException;

    void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback, String str) throws RemoteException;

    void updateAvailableNetworks(List<AvailableNetworkInfo> list, IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, String str) throws RemoteException;

    public static class Default implements IOns {
        @Override // com.android.internal.telephony.IOns
        public boolean setEnable(boolean enable, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IOns
        public boolean isEnabled(String callingPackage) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IOns
        public void setPreferredDataSubscriptionId(int subId, boolean needValidation, ISetOpportunisticDataCallback callbackStub, String callingPackage) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IOns
        public int getPreferredDataSubscriptionId(String callingPackage, String callingFeatureId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IOns
        public void updateAvailableNetworks(List<AvailableNetworkInfo> availableNetworks, IUpdateAvailableNetworksCallback callbackStub, String callingPackage) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOns {
        static final int TRANSACTION_getPreferredDataSubscriptionId = 4;
        static final int TRANSACTION_isEnabled = 2;
        static final int TRANSACTION_setEnable = 1;
        static final int TRANSACTION_setPreferredDataSubscriptionId = 3;
        static final int TRANSACTION_updateAvailableNetworks = 5;

        public Stub() {
            attachInterface(this, IOns.DESCRIPTOR);
        }

        public static IOns asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOns.DESCRIPTOR);
            if (iin != null && (iin instanceof IOns)) {
                return (IOns) iin;
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
                    return "setEnable";
                case 2:
                    return "isEnabled";
                case 3:
                    return "setPreferredDataSubscriptionId";
                case 4:
                    return "getPreferredDataSubscriptionId";
                case 5:
                    return "updateAvailableNetworks";
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
                data.enforceInterface(IOns.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IOns.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _arg0 = data.readBoolean();
                            String _arg1 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result = setEnable(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result2 = isEnabled(_arg02);
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            boolean _arg12 = data.readBoolean();
                            ISetOpportunisticDataCallback _arg2 = ISetOpportunisticDataCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg3 = data.readString();
                            data.enforceNoDataAvail();
                            setPreferredDataSubscriptionId(_arg03, _arg12, _arg2, _arg3);
                            reply.writeNoException();
                            return true;
                        case 4:
                            String _arg04 = data.readString();
                            String _arg13 = data.readString();
                            data.enforceNoDataAvail();
                            int _result3 = getPreferredDataSubscriptionId(_arg04, _arg13);
                            reply.writeNoException();
                            reply.writeInt(_result3);
                            return true;
                        case 5:
                            List<AvailableNetworkInfo> _arg05 = data.createTypedArrayList(AvailableNetworkInfo.CREATOR);
                            IUpdateAvailableNetworksCallback _arg14 = IUpdateAvailableNetworksCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg22 = data.readString();
                            data.enforceNoDataAvail();
                            updateAvailableNetworks(_arg05, _arg14, _arg22);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IOns {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOns.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IOns
            public boolean setEnable(boolean enable, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOns.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public boolean isEnabled(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOns.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public void setPreferredDataSubscriptionId(int subId, boolean needValidation, ISetOpportunisticDataCallback callbackStub, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOns.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeBoolean(needValidation);
                    _data.writeStrongInterface(callbackStub);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public int getPreferredDataSubscriptionId(String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOns.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public void updateAvailableNetworks(List<AvailableNetworkInfo> availableNetworks, IUpdateAvailableNetworksCallback callbackStub, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOns.DESCRIPTOR);
                    _data.writeTypedList(availableNetworks, 0);
                    _data.writeStrongInterface(callbackStub);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
