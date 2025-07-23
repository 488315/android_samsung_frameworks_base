package com.samsung.android.multiwindow;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;

/* loaded from: classes6.dex */
public interface IKeyEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IKeyEventListener";

    public static class Default implements IKeyEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IKeyEventListener
        public void sendShortcutKey(KeyEvent keyEvent) throws RemoteException {
        }
    }

    void sendShortcutKey(KeyEvent keyEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyEventListener {
        static final int TRANSACTION_sendShortcutKey = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKeyEventListener.DESCRIPTOR);
        }

        public static IKeyEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKeyEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeyEventListener)) {
                return (IKeyEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "sendShortcutKey";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                parcel.enforceNoDataAvail();
                sendShortcutKey(keyEvent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeyEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IKeyEventListener
            public void sendShortcutKey(KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IKeyEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
