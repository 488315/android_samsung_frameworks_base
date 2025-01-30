package android.content.p002pm;

import android.appwidget.AppWidgetProviderInfo;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IPinItemRequest extends IInterface {
    boolean accept(Bundle bundle) throws RemoteException;

    AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException;

    Bundle getExtras() throws RemoteException;

    ShortcutInfo getShortcutInfo() throws RemoteException;

    boolean isValid() throws RemoteException;

    public static class Default implements IPinItemRequest {
        @Override // android.content.p002pm.IPinItemRequest
        public boolean isValid() throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public boolean accept(Bundle options) throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public ShortcutInfo getShortcutInfo() throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public Bundle getExtras() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPinItemRequest {
        public static final String DESCRIPTOR = "android.content.pm.IPinItemRequest";
        static final int TRANSACTION_accept = 2;
        static final int TRANSACTION_getAppWidgetProviderInfo = 4;
        static final int TRANSACTION_getExtras = 5;
        static final int TRANSACTION_getShortcutInfo = 3;
        static final int TRANSACTION_isValid = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPinItemRequest asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPinItemRequest)) {
                return (IPinItemRequest) iin;
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
                    return "isValid";
                case 2:
                    return "accept";
                case 3:
                    return "getShortcutInfo";
                case 4:
                    return "getAppWidgetProviderInfo";
                case 5:
                    return "getExtras";
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
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _result = isValid();
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result2 = accept(_arg0);
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        case 3:
                            ShortcutInfo _result3 = getShortcutInfo();
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        case 4:
                            AppWidgetProviderInfo _result4 = getAppWidgetProviderInfo();
                            reply.writeNoException();
                            reply.writeTypedObject(_result4, 1);
                            return true;
                        case 5:
                            Bundle _result5 = getExtras();
                            reply.writeNoException();
                            reply.writeTypedObject(_result5, 1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IPinItemRequest {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.p002pm.IPinItemRequest
            public boolean isValid() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public boolean accept(Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public ShortcutInfo getShortcutInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ShortcutInfo _result = (ShortcutInfo) _reply.readTypedObject(ShortcutInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    AppWidgetProviderInfo _result = (AppWidgetProviderInfo) _reply.readTypedObject(AppWidgetProviderInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public Bundle getExtras() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
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
