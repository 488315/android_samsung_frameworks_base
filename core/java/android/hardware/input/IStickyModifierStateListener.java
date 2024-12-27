package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IStickyModifierStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IStickyModifierStateListener";

    void onStickyModifierStateChanged(int i, int i2) throws RemoteException;

    public static class Default implements IStickyModifierStateListener {
        @Override // android.hardware.input.IStickyModifierStateListener
        public void onStickyModifierStateChanged(int modifierState, int lockedModifierState) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStickyModifierStateListener {
        static final int TRANSACTION_onStickyModifierStateChanged = 1;

        public Stub() {
            attachInterface(this, IStickyModifierStateListener.DESCRIPTOR);
        }

        public static IStickyModifierStateListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStickyModifierStateListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IStickyModifierStateListener)) {
                return (IStickyModifierStateListener) iin;
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
                    return "onStickyModifierStateChanged";
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
                data.enforceInterface(IStickyModifierStateListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStickyModifierStateListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onStickyModifierStateChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStickyModifierStateListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStickyModifierStateListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IStickyModifierStateListener
            public void onStickyModifierStateChanged(int modifierState, int lockedModifierState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStickyModifierStateListener.DESCRIPTOR);
                    _data.writeInt(modifierState);
                    _data.writeInt(lockedModifierState);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
