package com.samsung.android.content.clipboard;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.content.clipboard.data.SemClipData;

/* loaded from: classes5.dex */
public interface IOnClipboardEventListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.content.clipboard.IOnClipboardEventListener";

    void onClipboardEvent(int i, SemClipData semClipData) throws RemoteException;

    void onUpdateFilter(int i) throws RemoteException;

    public static class Default implements IOnClipboardEventListener {
        @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
        public void onClipboardEvent(int event, SemClipData data) throws RemoteException {}

        @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
        public void onUpdateFilter(int filter) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnClipboardEventListener {
        static final int TRANSACTION_onClipboardEvent = 1;
        static final int TRANSACTION_onUpdateFilter = 2;

        public Stub() {
            attachInterface(this, IOnClipboardEventListener.DESCRIPTOR);
        }

        public static IOnClipboardEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnClipboardEventListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnClipboardEventListener)) {
                return (IOnClipboardEventListener) iin;
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
                    return "onClipboardEvent";
                case 2:
                    return "onUpdateFilter";
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
                data.enforceInterface(IOnClipboardEventListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnClipboardEventListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    SemClipData _arg1 = (SemClipData) data.readTypedObject(SemClipData.CREATOR);
                    data.enforceNoDataAvail();
                    onClipboardEvent(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onUpdateFilter(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnClipboardEventListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnClipboardEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
            public void onClipboardEvent(int event, SemClipData data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnClipboardEventListener.DESCRIPTOR);
                    _data.writeInt(event);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
            public void onUpdateFilter(int filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnClipboardEventListener.DESCRIPTOR);
                    _data.writeInt(filter);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
