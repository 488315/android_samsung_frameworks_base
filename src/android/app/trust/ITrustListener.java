package android.app.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface ITrustListener extends IInterface {

    public static class Default implements ITrustListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.trust.ITrustListener
        public void onEnabledTrustAgentsChanged(int i) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onIsActiveUnlockRunningChanged(boolean z, int i) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustChanged(boolean z, boolean z2, int i, int i2, List<String> list) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustError(CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustManagedChanged(boolean z, int i) throws RemoteException {
        }
    }

    void onEnabledTrustAgentsChanged(int i) throws RemoteException;

    void onIsActiveUnlockRunningChanged(boolean z, int i) throws RemoteException;

    void onTrustChanged(boolean z, boolean z2, int i, int i2, List<String> list) throws RemoteException;

    void onTrustError(CharSequence charSequence) throws RemoteException;

    void onTrustManagedChanged(boolean z, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITrustListener {
        public static final String DESCRIPTOR = "android.app.trust.ITrustListener";
        static final int TRANSACTION_onEnabledTrustAgentsChanged = 1;
        static final int TRANSACTION_onIsActiveUnlockRunningChanged = 5;
        static final int TRANSACTION_onTrustChanged = 2;
        static final int TRANSACTION_onTrustError = 4;
        static final int TRANSACTION_onTrustManagedChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITrustListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITrustListener)) {
                return (ITrustListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEnabledTrustAgentsChanged";
            }
            if (i == 2) {
                return "onTrustChanged";
            }
            if (i == 3) {
                return "onTrustManagedChanged";
            }
            if (i == 4) {
                return "onTrustError";
            }
            if (i != 5) {
                return null;
            }
            return "onIsActiveUnlockRunningChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEnabledTrustAgentsChanged(readInt);
            } else if (i == 2) {
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                onTrustChanged(readBoolean, readBoolean2, readInt2, readInt3, createStringArrayList);
            } else if (i == 3) {
                boolean readBoolean3 = parcel.readBoolean();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTrustManagedChanged(readBoolean3, readInt4);
            } else if (i == 4) {
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                onTrustError(charSequence);
            } else if (i == 5) {
                boolean readBoolean4 = parcel.readBoolean();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onIsActiveUnlockRunningChanged(readBoolean4, readInt5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITrustListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.trust.ITrustListener
            public void onEnabledTrustAgentsChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustChanged(boolean z, boolean z2, int i, int i2, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustManagedChanged(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustError(CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onIsActiveUnlockRunningChanged(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
