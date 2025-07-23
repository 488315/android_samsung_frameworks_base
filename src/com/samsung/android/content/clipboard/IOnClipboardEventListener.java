package com.samsung.android.content.clipboard;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.content.clipboard.data.SemClipData;

/* loaded from: classes6.dex */
public interface IOnClipboardEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.content.clipboard.IOnClipboardEventListener";

    public static class Default implements IOnClipboardEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
        public void onClipboardEvent(int i, SemClipData semClipData) throws RemoteException {
        }

        @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
        public void onUpdateFilter(int i) throws RemoteException {
        }
    }

    void onClipboardEvent(int i, SemClipData semClipData) throws RemoteException;

    void onUpdateFilter(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnClipboardEventListener {
        static final int TRANSACTION_onClipboardEvent = 1;
        static final int TRANSACTION_onUpdateFilter = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IOnClipboardEventListener.DESCRIPTOR);
        }

        public static IOnClipboardEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOnClipboardEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnClipboardEventListener)) {
                return (IOnClipboardEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onClipboardEvent";
            }
            if (i != 2) {
                return null;
            }
            return "onUpdateFilter";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnClipboardEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnClipboardEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                SemClipData semClipData = (SemClipData) parcel.readTypedObject(SemClipData.CREATOR);
                parcel.enforceNoDataAvail();
                onClipboardEvent(readInt, semClipData);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onUpdateFilter(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOnClipboardEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnClipboardEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
            public void onClipboardEvent(int i, SemClipData semClipData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOnClipboardEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(semClipData, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
            public void onUpdateFilter(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOnClipboardEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
