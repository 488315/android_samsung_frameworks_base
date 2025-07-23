package android.os.epic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IEpicObject extends IInterface {
    public static final String DESCRIPTOR = "android.os.epic.IEpicObject";

    public static class Default implements IEpicObject {
        @Override // android.os.epic.IEpicObject
        public boolean acquire_lock() throws RemoteException {
            return false;
        }

        @Override // android.os.epic.IEpicObject
        public boolean acquire_lock_conditional(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.epic.IEpicObject
        public boolean acquire_lock_option(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.epic.IEpicObject
        public boolean acquire_lock_option_multi(int[] iArr, int[] iArr2) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.epic.IEpicObject
        public boolean hint_release(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.epic.IEpicObject
        public boolean perf_hint(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.epic.IEpicObject
        public boolean release_lock() throws RemoteException {
            return false;
        }

        @Override // android.os.epic.IEpicObject
        public boolean release_lock_conditional(String str) throws RemoteException {
            return false;
        }
    }

    boolean acquire_lock() throws RemoteException;

    boolean acquire_lock_conditional(String str) throws RemoteException;

    boolean acquire_lock_option(int i, int i2) throws RemoteException;

    boolean acquire_lock_option_multi(int[] iArr, int[] iArr2) throws RemoteException;

    boolean hint_release(String str) throws RemoteException;

    boolean perf_hint(String str) throws RemoteException;

    boolean release_lock() throws RemoteException;

    boolean release_lock_conditional(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IEpicObject {
        static final int TRANSACTION_acquire_lock = 1;
        static final int TRANSACTION_acquire_lock_conditional = 5;
        static final int TRANSACTION_acquire_lock_option = 3;
        static final int TRANSACTION_acquire_lock_option_multi = 4;
        static final int TRANSACTION_hint_release = 8;
        static final int TRANSACTION_perf_hint = 7;
        static final int TRANSACTION_release_lock = 2;
        static final int TRANSACTION_release_lock_conditional = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, IEpicObject.DESCRIPTOR);
        }

        public static IEpicObject asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEpicObject.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEpicObject)) {
                return (IEpicObject) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "acquire_lock";
                case 2:
                    return "release_lock";
                case 3:
                    return "acquire_lock_option";
                case 4:
                    return "acquire_lock_option_multi";
                case 5:
                    return "acquire_lock_conditional";
                case 6:
                    return "release_lock_conditional";
                case 7:
                    return "perf_hint";
                case 8:
                    return "hint_release";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEpicObject.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEpicObject.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean acquire_lock = acquire_lock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acquire_lock);
                    return true;
                case 2:
                    boolean release_lock = release_lock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(release_lock);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean acquire_lock_option = acquire_lock_option(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acquire_lock_option);
                    return true;
                case 4:
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean acquire_lock_option_multi = acquire_lock_option_multi(createIntArray, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acquire_lock_option_multi);
                    return true;
                case 5:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean acquire_lock_conditional = acquire_lock_conditional(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acquire_lock_conditional);
                    return true;
                case 6:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean release_lock_conditional = release_lock_conditional(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(release_lock_conditional);
                    return true;
                case 7:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean perf_hint = perf_hint(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(perf_hint);
                    return true;
                case 8:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hint_release = hint_release(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hint_release);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEpicObject {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEpicObject.DESCRIPTOR;
            }

            @Override // android.os.epic.IEpicObject
            public boolean acquire_lock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean release_lock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean acquire_lock_option(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean acquire_lock_option_multi(int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean acquire_lock_conditional(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean release_lock_conditional(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean perf_hint(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean hint_release(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
