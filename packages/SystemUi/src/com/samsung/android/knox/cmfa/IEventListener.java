package com.samsung.android.knox.cmfa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.cmfa.IEventListener";

    void onFail(String str);

    void onStateUpdate(boolean z, String str);

    void onSuccess();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IEventListener {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.cmfa.IEventListener
        public final void onSuccess() {
        }

        @Override // com.samsung.android.knox.cmfa.IEventListener
        public final void onFail(String str) {
        }

        @Override // com.samsung.android.knox.cmfa.IEventListener
        public final void onStateUpdate(boolean z, String str) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IEventListener {
        public static final int TRANSACTION_onFail = 2;
        public static final int TRANSACTION_onStateUpdate = 3;
        public static final int TRANSACTION_onSuccess = 1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IEventListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.cmfa.IEventListener
            public final void onFail(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IEventListener
            public final void onStateUpdate(boolean z, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.cmfa.IEventListener
            public final void onSuccess() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEventListener.DESCRIPTOR);
        }

        public static IEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEventListener)) ? new Proxy(iBinder) : (IEventListener) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i == 2) {
                return "onFail";
            }
            if (i != 3) {
                return null;
            }
            return "onStateUpdate";
        }

        public final int getMaxTransactionId() {
            return 2;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onFail(readString);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean readBoolean = parcel.readBoolean();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onStateUpdate(readBoolean, readString2);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
