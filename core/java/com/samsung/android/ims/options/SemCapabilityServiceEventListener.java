package com.samsung.android.ims.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.ims.util.SemImsUri;
import java.util.List;

/* loaded from: classes6.dex */
public interface SemCapabilityServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.options.SemCapabilityServiceEventListener";

    void onCapabilitiesChanged(SemImsUri semImsUri, SemCapabilities semCapabilities) throws RemoteException;

    void onCapabilityAndAvailabilityPublished(int i) throws RemoteException;

    void onMultipleCapabilitiesChanged(List<SemImsUri> list, List<SemCapabilities> list2) throws RemoteException;

    void onOwnCapabilitiesChanged() throws RemoteException;

    public static class Default implements SemCapabilityServiceEventListener {
        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onOwnCapabilitiesChanged() throws RemoteException {
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilitiesChanged(SemImsUri uri, SemCapabilities capa) throws RemoteException {
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onMultipleCapabilitiesChanged(List<SemImsUri> uris, List<SemCapabilities> capaList) throws RemoteException {
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilityAndAvailabilityPublished(int errorCode) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements SemCapabilityServiceEventListener {
        static final int TRANSACTION_onCapabilitiesChanged = 2;
        static final int TRANSACTION_onCapabilityAndAvailabilityPublished = 4;
        static final int TRANSACTION_onMultipleCapabilitiesChanged = 3;
        static final int TRANSACTION_onOwnCapabilitiesChanged = 1;

        public Stub() {
            attachInterface(this, SemCapabilityServiceEventListener.DESCRIPTOR);
        }

        public static SemCapabilityServiceEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(SemCapabilityServiceEventListener.DESCRIPTOR);
            if (iin != null && (iin instanceof SemCapabilityServiceEventListener)) {
                return (SemCapabilityServiceEventListener) iin;
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
                    return "onOwnCapabilitiesChanged";
                case 2:
                    return "onCapabilitiesChanged";
                case 3:
                    return "onMultipleCapabilitiesChanged";
                case 4:
                    return "onCapabilityAndAvailabilityPublished";
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
                data.enforceInterface(SemCapabilityServiceEventListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(SemCapabilityServiceEventListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onOwnCapabilitiesChanged();
                    reply.writeNoException();
                    return true;
                case 2:
                    SemImsUri _arg0 = (SemImsUri) data.readTypedObject(SemImsUri.CREATOR);
                    SemCapabilities _arg1 = (SemCapabilities) data.readTypedObject(SemCapabilities.CREATOR);
                    data.enforceNoDataAvail();
                    onCapabilitiesChanged(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 3:
                    List<SemImsUri> _arg02 = data.createTypedArrayList(SemImsUri.CREATOR);
                    List<SemCapabilities> _arg12 = data.createTypedArrayList(SemCapabilities.CREATOR);
                    data.enforceNoDataAvail();
                    onMultipleCapabilitiesChanged(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onCapabilityAndAvailabilityPublished(_arg03);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements SemCapabilityServiceEventListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemCapabilityServiceEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onOwnCapabilitiesChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onCapabilitiesChanged(SemImsUri uri, SemCapabilities capa) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeTypedObject(capa, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onMultipleCapabilitiesChanged(List<SemImsUri> uris, List<SemCapabilities> capaList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    _data.writeTypedList(uris, 0);
                    _data.writeTypedList(capaList, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onCapabilityAndAvailabilityPublished(int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
