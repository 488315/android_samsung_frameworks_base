package android.app;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUnsafeIntentStrictModeCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IUnsafeIntentStrictModeCallback";

    public static class Default implements IUnsafeIntentStrictModeCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUnsafeIntentStrictModeCallback
        public void onUnsafeIntent(int i, Intent intent) throws RemoteException {
        }
    }

    void onUnsafeIntent(int i, Intent intent) throws RemoteException;

    public static abstract class Stub extends Binder implements IUnsafeIntentStrictModeCallback {
        static final int TRANSACTION_onUnsafeIntent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUnsafeIntentStrictModeCallback.DESCRIPTOR);
        }

        public static IUnsafeIntentStrictModeCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUnsafeIntentStrictModeCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUnsafeIntentStrictModeCallback)) {
                return (IUnsafeIntentStrictModeCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onUnsafeIntent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUnsafeIntentStrictModeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUnsafeIntentStrictModeCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                parcel.enforceNoDataAvail();
                onUnsafeIntent(readInt, intent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUnsafeIntentStrictModeCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnsafeIntentStrictModeCallback.DESCRIPTOR;
            }

            @Override // android.app.IUnsafeIntentStrictModeCallback
            public void onUnsafeIntent(int i, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUnsafeIntentStrictModeCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
