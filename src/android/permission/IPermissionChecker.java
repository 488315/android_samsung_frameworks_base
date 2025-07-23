package android.permission;

import android.content.AttributionSourceState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPermissionChecker extends IInterface {
    public static final String DESCRIPTOR = "android.permission.IPermissionChecker";
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;

    public static class Default implements IPermissionChecker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.permission.IPermissionChecker
        public int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionChecker
        public int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionChecker
        public void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException {
        }
    }

    int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) throws RemoteException;

    int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) throws RemoteException;

    void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IPermissionChecker {
        static final int TRANSACTION_checkOp = 3;
        static final int TRANSACTION_checkPermission = 1;
        static final int TRANSACTION_finishDataDelivery = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IPermissionChecker.DESCRIPTOR);
        }

        public static IPermissionChecker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPermissionChecker.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPermissionChecker)) {
                return (IPermissionChecker) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPermissionChecker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPermissionChecker.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                String readString2 = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                boolean readBoolean3 = parcel.readBoolean();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int checkPermission = checkPermission(readString, attributionSourceState, readString2, readBoolean, readBoolean2, readBoolean3, readInt);
                parcel2.writeNoException();
                parcel2.writeInt(checkPermission);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                boolean readBoolean4 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                finishDataDelivery(readInt2, attributionSourceState2, readBoolean4);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                AttributionSourceState attributionSourceState3 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                String readString3 = parcel.readString();
                boolean readBoolean5 = parcel.readBoolean();
                boolean readBoolean6 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                int checkOp = checkOp(readInt3, attributionSourceState3, readString3, readBoolean5, readBoolean6);
                parcel2.writeNoException();
                parcel2.writeInt(checkOp);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPermissionChecker {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPermissionChecker.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionChecker
            public int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionChecker.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionChecker
            public void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionChecker.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionChecker
            public int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionChecker.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
