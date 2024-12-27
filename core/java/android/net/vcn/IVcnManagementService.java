package android.net.vcn;

import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;

import java.util.List;

public interface IVcnManagementService extends IInterface {
    public static final String DESCRIPTOR = "android.net.vcn.IVcnManagementService";

    void addVcnUnderlyingNetworkPolicyListener(
            IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener)
            throws RemoteException;

    void clearVcnConfig(ParcelUuid parcelUuid, String str) throws RemoteException;

    List<ParcelUuid> getConfiguredSubscriptionGroups(String str) throws RemoteException;

    VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(
            NetworkCapabilities networkCapabilities, LinkProperties linkProperties)
            throws RemoteException;

    void registerVcnStatusCallback(
            ParcelUuid parcelUuid, IVcnStatusCallback iVcnStatusCallback, String str)
            throws RemoteException;

    void removeVcnUnderlyingNetworkPolicyListener(
            IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener)
            throws RemoteException;

    void setVcnConfig(ParcelUuid parcelUuid, VcnConfig vcnConfig, String str)
            throws RemoteException;

    void unregisterVcnStatusCallback(IVcnStatusCallback iVcnStatusCallback) throws RemoteException;

    public static class Default implements IVcnManagementService {
        @Override // android.net.vcn.IVcnManagementService
        public void setVcnConfig(ParcelUuid subscriptionGroup, VcnConfig config, String opPkgName)
                throws RemoteException {}

        @Override // android.net.vcn.IVcnManagementService
        public void clearVcnConfig(ParcelUuid subscriptionGroup, String opPkgName)
                throws RemoteException {}

        @Override // android.net.vcn.IVcnManagementService
        public List<ParcelUuid> getConfiguredSubscriptionGroups(String opPkgName)
                throws RemoteException {
            return null;
        }

        @Override // android.net.vcn.IVcnManagementService
        public void addVcnUnderlyingNetworkPolicyListener(
                IVcnUnderlyingNetworkPolicyListener listener) throws RemoteException {}

        @Override // android.net.vcn.IVcnManagementService
        public void removeVcnUnderlyingNetworkPolicyListener(
                IVcnUnderlyingNetworkPolicyListener listener) throws RemoteException {}

        @Override // android.net.vcn.IVcnManagementService
        public VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(
                NetworkCapabilities nc, LinkProperties lp) throws RemoteException {
            return null;
        }

        @Override // android.net.vcn.IVcnManagementService
        public void registerVcnStatusCallback(
                ParcelUuid subscriptionGroup, IVcnStatusCallback callback, String opPkgName)
                throws RemoteException {}

        @Override // android.net.vcn.IVcnManagementService
        public void unregisterVcnStatusCallback(IVcnStatusCallback callback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVcnManagementService {
        static final int TRANSACTION_addVcnUnderlyingNetworkPolicyListener = 4;
        static final int TRANSACTION_clearVcnConfig = 2;
        static final int TRANSACTION_getConfiguredSubscriptionGroups = 3;
        static final int TRANSACTION_getUnderlyingNetworkPolicy = 6;
        static final int TRANSACTION_registerVcnStatusCallback = 7;
        static final int TRANSACTION_removeVcnUnderlyingNetworkPolicyListener = 5;
        static final int TRANSACTION_setVcnConfig = 1;
        static final int TRANSACTION_unregisterVcnStatusCallback = 8;

        public Stub() {
            attachInterface(this, IVcnManagementService.DESCRIPTOR);
        }

        public static IVcnManagementService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVcnManagementService.DESCRIPTOR);
            if (iin != null && (iin instanceof IVcnManagementService)) {
                return (IVcnManagementService) iin;
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
                    return "setVcnConfig";
                case 2:
                    return "clearVcnConfig";
                case 3:
                    return "getConfiguredSubscriptionGroups";
                case 4:
                    return "addVcnUnderlyingNetworkPolicyListener";
                case 5:
                    return "removeVcnUnderlyingNetworkPolicyListener";
                case 6:
                    return "getUnderlyingNetworkPolicy";
                case 7:
                    return "registerVcnStatusCallback";
                case 8:
                    return "unregisterVcnStatusCallback";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IVcnManagementService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVcnManagementService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelUuid _arg0 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    VcnConfig _arg1 = (VcnConfig) data.readTypedObject(VcnConfig.CREATOR);
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    setVcnConfig(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    ParcelUuid _arg02 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    clearVcnConfig(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    List<ParcelUuid> _result = getConfiguredSubscriptionGroups(_arg03);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 4:
                    IVcnUnderlyingNetworkPolicyListener _arg04 =
                            IVcnUnderlyingNetworkPolicyListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addVcnUnderlyingNetworkPolicyListener(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    IVcnUnderlyingNetworkPolicyListener _arg05 =
                            IVcnUnderlyingNetworkPolicyListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeVcnUnderlyingNetworkPolicyListener(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    NetworkCapabilities _arg06 =
                            (NetworkCapabilities) data.readTypedObject(NetworkCapabilities.CREATOR);
                    LinkProperties _arg13 =
                            (LinkProperties) data.readTypedObject(LinkProperties.CREATOR);
                    data.enforceNoDataAvail();
                    VcnUnderlyingNetworkPolicy _result2 =
                            getUnderlyingNetworkPolicy(_arg06, _arg13);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 7:
                    ParcelUuid _arg07 = (ParcelUuid) data.readTypedObject(ParcelUuid.CREATOR);
                    IVcnStatusCallback _arg14 =
                            IVcnStatusCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    registerVcnStatusCallback(_arg07, _arg14, _arg22);
                    reply.writeNoException();
                    return true;
                case 8:
                    IVcnStatusCallback _arg08 =
                            IVcnStatusCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterVcnStatusCallback(_arg08);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVcnManagementService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVcnManagementService.DESCRIPTOR;
            }

            @Override // android.net.vcn.IVcnManagementService
            public void setVcnConfig(
                    ParcelUuid subscriptionGroup, VcnConfig config, String opPkgName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeTypedObject(subscriptionGroup, 0);
                    _data.writeTypedObject(config, 0);
                    _data.writeString(opPkgName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void clearVcnConfig(ParcelUuid subscriptionGroup, String opPkgName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeTypedObject(subscriptionGroup, 0);
                    _data.writeString(opPkgName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public List<ParcelUuid> getConfiguredSubscriptionGroups(String opPkgName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeString(opPkgName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<ParcelUuid> _result = _reply.createTypedArrayList(ParcelUuid.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void addVcnUnderlyingNetworkPolicyListener(
                    IVcnUnderlyingNetworkPolicyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void removeVcnUnderlyingNetworkPolicyListener(
                    IVcnUnderlyingNetworkPolicyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(
                    NetworkCapabilities nc, LinkProperties lp) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeTypedObject(nc, 0);
                    _data.writeTypedObject(lp, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    VcnUnderlyingNetworkPolicy _result =
                            (VcnUnderlyingNetworkPolicy)
                                    _reply.readTypedObject(VcnUnderlyingNetworkPolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void registerVcnStatusCallback(
                    ParcelUuid subscriptionGroup, IVcnStatusCallback callback, String opPkgName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeTypedObject(subscriptionGroup, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(opPkgName);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.vcn.IVcnManagementService
            public void unregisterVcnStatusCallback(IVcnStatusCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVcnManagementService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
