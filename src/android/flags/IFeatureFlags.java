package android.flags;

import android.flags.IFeatureFlagsCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IFeatureFlags extends IInterface {
    public static final String DESCRIPTOR = "android.flags.IFeatureFlags";

    public static class Default implements IFeatureFlags {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.flags.IFeatureFlags
        public void overrideFlag(SyncableFlag syncableFlag) throws RemoteException {
        }

        @Override // android.flags.IFeatureFlags
        public List<SyncableFlag> queryFlags(List<SyncableFlag> list) throws RemoteException {
            return null;
        }

        @Override // android.flags.IFeatureFlags
        public void registerCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException {
        }

        @Override // android.flags.IFeatureFlags
        public void resetFlag(SyncableFlag syncableFlag) throws RemoteException {
        }

        @Override // android.flags.IFeatureFlags
        public List<SyncableFlag> syncFlags(List<SyncableFlag> list) throws RemoteException {
            return null;
        }

        @Override // android.flags.IFeatureFlags
        public void unregisterCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException {
        }
    }

    void overrideFlag(SyncableFlag syncableFlag) throws RemoteException;

    List<SyncableFlag> queryFlags(List<SyncableFlag> list) throws RemoteException;

    void registerCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException;

    void resetFlag(SyncableFlag syncableFlag) throws RemoteException;

    List<SyncableFlag> syncFlags(List<SyncableFlag> list) throws RemoteException;

    void unregisterCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IFeatureFlags {
        static final int TRANSACTION_overrideFlag = 5;
        static final int TRANSACTION_queryFlags = 4;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_resetFlag = 6;
        static final int TRANSACTION_syncFlags = 1;
        static final int TRANSACTION_unregisterCallback = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IFeatureFlags.DESCRIPTOR);
        }

        public static IFeatureFlags asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFeatureFlags.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFeatureFlags)) {
                return (IFeatureFlags) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "syncFlags";
                case 2:
                    return "registerCallback";
                case 3:
                    return "unregisterCallback";
                case 4:
                    return "queryFlags";
                case 5:
                    return "overrideFlag";
                case 6:
                    return "resetFlag";
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
                parcel.enforceInterface(IFeatureFlags.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFeatureFlags.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SyncableFlag> listSyncFlags = syncFlags(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listSyncFlags, 1);
                    return true;
                case 2:
                    IFeatureFlagsCallback iFeatureFlagsCallbackAsInterface = IFeatureFlagsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(iFeatureFlagsCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IFeatureFlagsCallback iFeatureFlagsCallbackAsInterface2 = IFeatureFlagsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iFeatureFlagsCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SyncableFlag> listQueryFlags = queryFlags(arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listQueryFlags, 1);
                    return true;
                case 5:
                    SyncableFlag syncableFlag = (SyncableFlag) parcel.readTypedObject(SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    overrideFlag(syncableFlag);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SyncableFlag syncableFlag2 = (SyncableFlag) parcel.readTypedObject(SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetFlag(syncableFlag2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFeatureFlags {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFeatureFlags.DESCRIPTOR;
            }

            @Override // android.flags.IFeatureFlags
            public List<SyncableFlag> syncFlags(List<SyncableFlag> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SyncableFlag.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void registerCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFeatureFlagsCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void unregisterCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFeatureFlagsCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public List<SyncableFlag> queryFlags(List<SyncableFlag> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SyncableFlag.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void overrideFlag(SyncableFlag syncableFlag) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    parcelObtain.writeTypedObject(syncableFlag, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void resetFlag(SyncableFlag syncableFlag) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    parcelObtain.writeTypedObject(syncableFlag, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
