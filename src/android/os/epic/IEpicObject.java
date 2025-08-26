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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEpicObject.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEpicObject)) {
                return (IEpicObject) iInterfaceQueryLocalInterface;
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
                    boolean zAcquire_lock = acquire_lock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAcquire_lock);
                    return true;
                case 2:
                    boolean zRelease_lock = release_lock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRelease_lock);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAcquire_lock_option = acquire_lock_option(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAcquire_lock_option);
                    return true;
                case 4:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean zAcquire_lock_option_multi = acquire_lock_option_multi(iArrCreateIntArray, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAcquire_lock_option_multi);
                    return true;
                case 5:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAcquire_lock_conditional = acquire_lock_conditional(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAcquire_lock_conditional);
                    return true;
                case 6:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRelease_lock_conditional = release_lock_conditional(string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRelease_lock_conditional);
                    return true;
                case 7:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zPerf_hint = perf_hint(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPerf_hint);
                    return true;
                case 8:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHint_release = hint_release(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHint_release);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean release_lock() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean acquire_lock_option(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean acquire_lock_option_multi(int[] iArr, int[] iArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean acquire_lock_conditional(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean release_lock_conditional(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean perf_hint(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicObject
            public boolean hint_release(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicObject.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
