package com.samsung.android.knox.zt;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public interface IZeroTrustListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.IZeroTrustListener";

    void onEvent(int i, Bundle bundle) throws RemoteException;

    void onEventGeneralized(int i, Map<String, String> map) throws RemoteException;

    void onEventSimplified(int i, String str) throws RemoteException;

    public static class Default implements IZeroTrustListener {
        @Override // com.samsung.android.knox.zt.IZeroTrustListener
        public void onEventSimplified(int event, String data) throws RemoteException {}

        @Override // com.samsung.android.knox.zt.IZeroTrustListener
        public void onEventGeneralized(int event, Map<String, String> data)
                throws RemoteException {}

        @Override // com.samsung.android.knox.zt.IZeroTrustListener
        public void onEvent(int event, Bundle data) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IZeroTrustListener {
        static final int TRANSACTION_onEvent = 3;
        static final int TRANSACTION_onEventGeneralized = 2;
        static final int TRANSACTION_onEventSimplified = 1;

        public Stub() {
            attachInterface(this, IZeroTrustListener.DESCRIPTOR);
        }

        public static IZeroTrustListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IZeroTrustListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IZeroTrustListener)) {
                return (IZeroTrustListener) iin;
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
                    return "onEventSimplified";
                case 2:
                    return "onEventGeneralized";
                case 3:
                    return "onEvent";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, final Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IZeroTrustListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IZeroTrustListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onEventSimplified(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int N = data.readInt();
                    final Map<String, String> _arg12 = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N)
                            .forEach(
                                    new IntConsumer() { // from class:
                                                        // com.samsung.android.knox.zt.IZeroTrustListener$Stub$$ExternalSyntheticLambda0
                                        @Override // java.util.function.IntConsumer
                                        public final void accept(int i) {
                                            IZeroTrustListener.Stub.lambda$onTransact$0(
                                                    Parcel.this, _arg12, i);
                                        }
                                    });
                    data.enforceNoDataAvail();
                    onEventGeneralized(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    Bundle _arg13 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onEvent(_arg03, _arg13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel data, Map _arg1, int i) {
            String k = data.readString();
            String v = data.readString();
            _arg1.put(k, v);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IZeroTrustListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IZeroTrustListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEventSimplified(int event, String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    _data.writeInt(event);
                    _data.writeString(data);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEventGeneralized(int event, Map<String, String> data)
                    throws RemoteException {
                final Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    _data.writeInt(event);
                    if (data == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(data.size());
                        data.forEach(
                                new BiConsumer() { // from class:
                                                   // com.samsung.android.knox.zt.IZeroTrustListener$Stub$Proxy$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        IZeroTrustListener.Stub.Proxy.lambda$onEventGeneralized$0(
                                                Parcel.this, (String) obj, (String) obj2);
                                    }
                                });
                    }
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$onEventGeneralized$0(
                    Parcel _data, String k, String v) {
                _data.writeString(k);
                _data.writeString(v);
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEvent(int event, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    _data.writeInt(event);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
