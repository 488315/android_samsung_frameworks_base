package com.samsung.android.knox.zt;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.zt.IZeroTrustListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes6.dex */
public interface IZeroTrustListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.IZeroTrustListener";

    public static class Default implements IZeroTrustListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.IZeroTrustListener
        public void onEvent(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.IZeroTrustListener
        public void onEventGeneralized(int i, Map<String, String> map) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.IZeroTrustListener
        public void onEventSimplified(int i, String str) throws RemoteException {
        }
    }

    void onEvent(int i, Bundle bundle) throws RemoteException;

    void onEventGeneralized(int i, Map<String, String> map) throws RemoteException;

    void onEventSimplified(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IZeroTrustListener {
        static final int TRANSACTION_onEvent = 3;
        static final int TRANSACTION_onEventGeneralized = 2;
        static final int TRANSACTION_onEventSimplified = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IZeroTrustListener.DESCRIPTOR);
        }

        public static IZeroTrustListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IZeroTrustListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IZeroTrustListener)) {
                return (IZeroTrustListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEventSimplified";
            }
            if (i == 2) {
                return "onEventGeneralized";
            }
            if (i != 3) {
                return null;
            }
            return "onEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IZeroTrustListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IZeroTrustListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onEventSimplified(readInt, readString);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                final HashMap hashMap = readInt3 < 0 ? null : new HashMap();
                IntStream.range(0, readInt3).forEach(new IntConsumer() { // from class: com.samsung.android.knox.zt.IZeroTrustListener$Stub$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i3) {
                        hashMap.put(r0.readString(), Parcel.this.readString());
                    }
                });
                parcel.enforceNoDataAvail();
                onEventGeneralized(readInt2, hashMap);
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onEvent(readInt4, bundle);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IZeroTrustListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IZeroTrustListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEventSimplified(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEventGeneralized(int i, Map<String, String> map) throws RemoteException {
                final Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: com.samsung.android.knox.zt.IZeroTrustListener$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IZeroTrustListener.Stub.Proxy.lambda$onEventGeneralized$0(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$onEventGeneralized$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEvent(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
