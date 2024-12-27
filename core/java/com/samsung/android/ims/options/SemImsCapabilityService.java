package com.samsung.android.ims.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface SemImsCapabilityService extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.ims.options.SemImsCapabilityService";

    SemCapabilities getCapabilities(String str, int i, int i2) throws RemoteException;

    SemCapabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException;

    SemCapabilities getCapabilitiesByNumber(String str, int i, boolean z, int i2)
            throws RemoteException;

    SemCapabilities getOwnCapabilities(int i) throws RemoteException;

    String registerListener(
            SemCapabilityServiceEventListener semCapabilityServiceEventListener, int i)
            throws RemoteException;

    void unregisterListener(String str, int i) throws RemoteException;

    public static class Default implements SemImsCapabilityService {
        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities getOwnCapabilities(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities getCapabilities(String uri, int refreshType, int phoneId)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities getCapabilitiesByNumber(
                String number, int refreshType, boolean delay, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities[] getCapabilitiesByContactId(
                String contactId, int refreshType, int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public String registerListener(SemCapabilityServiceEventListener listener, int phoneId)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public void unregisterListener(String token, int phoneId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements SemImsCapabilityService {
        static final int TRANSACTION_getCapabilities = 2;
        static final int TRANSACTION_getCapabilitiesByContactId = 4;
        static final int TRANSACTION_getCapabilitiesByNumber = 3;
        static final int TRANSACTION_getOwnCapabilities = 1;
        static final int TRANSACTION_registerListener = 5;
        static final int TRANSACTION_unregisterListener = 6;

        public Stub() {
            attachInterface(this, SemImsCapabilityService.DESCRIPTOR);
        }

        public static SemImsCapabilityService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(SemImsCapabilityService.DESCRIPTOR);
            if (iin != null && (iin instanceof SemImsCapabilityService)) {
                return (SemImsCapabilityService) iin;
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
                    return "getOwnCapabilities";
                case 2:
                    return "getCapabilities";
                case 3:
                    return "getCapabilitiesByNumber";
                case 4:
                    return "getCapabilitiesByContactId";
                case 5:
                    return "registerListener";
                case 6:
                    return "unregisterListener";
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
                data.enforceInterface(SemImsCapabilityService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(SemImsCapabilityService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    SemCapabilities _result = getOwnCapabilities(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    SemCapabilities _result2 = getCapabilities(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg12 = data.readInt();
                    boolean _arg22 = data.readBoolean();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    SemCapabilities _result3 =
                            getCapabilitiesByNumber(_arg03, _arg12, _arg22, _arg3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg13 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    SemCapabilities[] _result4 = getCapabilitiesByContactId(_arg04, _arg13, _arg23);
                    reply.writeNoException();
                    reply.writeTypedArray(_result4, 1);
                    return true;
                case 5:
                    SemCapabilityServiceEventListener _arg05 =
                            SemCapabilityServiceEventListener.Stub.asInterface(
                                    data.readStrongBinder());
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result5 = registerListener(_arg05, _arg14);
                    reply.writeNoException();
                    reply.writeString(_result5);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterListener(_arg06, _arg15);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements SemImsCapabilityService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsCapabilityService.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getOwnCapabilities(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    SemCapabilities _result =
                            (SemCapabilities) _reply.readTypedObject(SemCapabilities.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getCapabilities(String uri, int refreshType, int phoneId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    _data.writeString(uri);
                    _data.writeInt(refreshType);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    SemCapabilities _result =
                            (SemCapabilities) _reply.readTypedObject(SemCapabilities.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getCapabilitiesByNumber(
                    String number, int refreshType, boolean delay, int phoneId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    _data.writeString(number);
                    _data.writeInt(refreshType);
                    _data.writeBoolean(delay);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    SemCapabilities _result =
                            (SemCapabilities) _reply.readTypedObject(SemCapabilities.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities[] getCapabilitiesByContactId(
                    String contactId, int refreshType, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    _data.writeString(contactId);
                    _data.writeInt(refreshType);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    SemCapabilities[] _result =
                            (SemCapabilities[]) _reply.createTypedArray(SemCapabilities.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public String registerListener(SemCapabilityServiceEventListener listener, int phoneId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public void unregisterListener(String token, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
