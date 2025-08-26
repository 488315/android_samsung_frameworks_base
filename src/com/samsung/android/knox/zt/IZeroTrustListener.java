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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IZeroTrustListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IZeroTrustListener)) {
                return (IZeroTrustListener) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onEventSimplified(i3, string);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                final HashMap map = i5 < 0 ? null : new HashMap();
                IntStream.range(0, i5).forEach(new IntConsumer() { // from class: com.samsung.android.knox.zt.IZeroTrustListener$Stub$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i6) {
                        Parcel parcel3 = parcel;
                        map.put(parcel3.readString(), parcel3.readString());
                    }
                });
                parcel.enforceNoDataAvail();
                onEventGeneralized(i4, map);
            } else if (i == 3) {
                int i6 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onEvent(i6, bundle);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEventGeneralized(int i, Map<String, String> map) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: com.samsung.android.knox.zt.IZeroTrustListener$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IZeroTrustListener.Stub.Proxy.lambda$onEventGeneralized$0(parcelObtain, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$onEventGeneralized$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // com.samsung.android.knox.zt.IZeroTrustListener
            public void onEvent(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IZeroTrustListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
